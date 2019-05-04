package com.safecode.cyberzone.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.safecode.cyberzone.base.pojo.SysUser;
import com.safecode.cyberzone.base.service.SessionProvider;
import com.safecode.cyberzone.base.utils.DataUtil;
import com.safecode.cyberzone.base.utils.Request2ModelUtil;
import com.safecode.cyberzone.base.utils.TicketUtil;
import com.safecode.cyberzone.controller.SysLogController;
import com.safecode.cyberzone.mapper.CommonProcessMapper;
import com.safecode.cyberzone.mapper.VmApplyMapper;
import com.safecode.cyberzone.pojo.CommonProcess;
import com.safecode.cyberzone.pojo.VmApply;
import com.safecode.cyberzone.service.VmApplyService;
import com.safecode.cyberzone.utils.HttpsUtils;
import com.safecode.cyberzone.utils.SysLogUtil;


@Service
@Transactional
public class VmApplyServiceImpl implements VmApplyService {

	//测试环境
	public static final String VMURL = "https://192.168.4.29/fxnoVNC/vnc_auto.html";
	public static final String VMSURL = "https://192.168.4.29:8443/cyberzone-vm/vm/vms";
	//正式环境
//	public static final String VMURL = "https://192.168.190.30/fxnoVNC/vnc_auto.html";
//	public static final String VMSURL = "https://192.168.190.30:8443/cyberzone-vm/vm/vms";

	
    @Autowired
    private VmApplyMapper vmApplyMapper;
    @Autowired
    private CommonProcessMapper commonProcessMapper;
	 @Autowired
	 private SessionProvider sessionProvider;
	 @Autowired
	 private SysLogController sysLogController;

    
	@Override
	public PageInfo<VmApply> queryPageList(Map<String, Object> params) {
		// TODO Auto-generated method stub
		if (DataUtil.isEmpty(params.get("pageNum"))) {
            params.put("pageNum", 1);
        }
        if (DataUtil.isEmpty(params.get("pageSize"))) {
            params.put("pageSize", 10);
        }
        
		PageHelper.startPage(params);
		Page<VmApply> page = vmApplyMapper.queryPageList(params);
		
		return new PageInfo<VmApply>(page);
	}
	
	@Override
	public void delete(HttpServletRequest request, Long id) {
		// TODO Auto-generated method stub
		VmApply vmApply = vmApplyMapper.selectByPrimaryKey(id);
		if (vmApply != null) {
			vmApply.setUpdateTime(new Date());
			 vmApply.setUpdateBy(sessionProvider.getAttributeForSysUser(TicketUtil.getTOKEN(request, null)).getId());
			vmApply.setDelFlag(true);
			vmApplyMapper.updateByPrimaryKey(vmApply);
		}
	}
	
	@Override
	public void add(HttpServletRequest request, VmApply vmApply) {
		// TODO Auto-generated method stub
		vmApply.setSource(2);
		vmApply.setStatus(0);
		vmApply.setCreateTime(new Date());
		vmApply.setUpdateTime(new Date());
		vmApply.setCreateBy(sessionProvider.getAttributeForSysUser(TicketUtil.getTOKEN(request, null)).getId());
		vmApply.setUpdateBy(sessionProvider.getAttributeForSysUser(TicketUtil.getTOKEN(request, null)).getId());
		vmApply.setDelFlag(false);
        
		vmApplyMapper.insert(vmApply);
		
		//////////////////////////////添加系统日志/////////////////////////	
		try {
			//记录操作日志
			String account = "admin";
			Map<String, String> map = new HashMap<String,String>();
			map = SysLogUtil.save(request,account,"添加VM申请 id为:"+vmApply.getId());
			sysLogController.add(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		///////////////////////////////////////////////////////////////////////////////////

	}
	
	@Override
	public void submitData(HttpServletRequest request, Long id, int status, String remark) {
		// TODO Auto-generated method stub
		VmApply vmApply = vmApplyMapper.selectByPrimaryKey(id);

		vmApply.setStatus(status);
    	vmApply.setUpdateBy(sessionProvider.getAttributeForSysUser(TicketUtil.getTOKEN(request, null)).getId());
    	vmApply.setUpdateTime(new Date());
		vmApplyMapper.updateByPrimaryKey(vmApply);
		
		//维护流程节点表
        CommonProcess commonProcess = new CommonProcess();
        commonProcess.setApplyId(vmApply.getId());
        commonProcess.setSource(1);
        commonProcess.setPoint(status);
        if(status == 3) {
        	commonProcess.setRemark(remark);
        }
        commonProcess.setCreateBy(sessionProvider.getAttributeForSysUser(TicketUtil.getTOKEN(request, null)).getId());
        commonProcess.setCreateTime(new Date());
        commonProcess.setDelFlag(false);
        commonProcessMapper.insert(commonProcess);
	}
	
	@Override
	public ModelMap detail(ModelMap modelMap, Long id) {
		// TODO Auto-generated method stub
    	Map<String, Object> params = new HashMap<>();
    	params.put("applyId", id);
    	
		VmApply vmApply = vmApplyMapper.selectByPrimaryKey(id);
		List<CommonProcess> commonProcessList = commonProcessMapper.queryBy(params);
		
		modelMap.put("vmApply", vmApply);
		modelMap.put("commonProcessList", commonProcessList);
		return modelMap;
	}
	
	@Override
	public void update(HttpServletRequest request, VmApply vmApply) {
		// TODO Auto-generated method stub
		VmApply apply = vmApplyMapper.selectByPrimaryKey(vmApply.getId());
		Request2ModelUtil.covertObj(apply, vmApply);

		apply.setUpdateTime(new Date());
        //暂时没有获取当前用户的公用方法
        apply.setUpdateBy(sessionProvider.getAttributeForSysUser(TicketUtil.getTOKEN(request, null)).getId());
		vmApplyMapper.updateByPrimaryKey(apply);
	}
	
    @Override
    public String pass(HttpServletRequest request, Long id) {
    	// TODO Auto-generated method stub
    	String msg = "1";//成功

		VmApply vmApply = vmApplyMapper.selectByPrimaryKey(id);

    	//调用获取深信服VM列表
    	String doGet = HttpsUtils.doGet(VMSURL, new HashMap<>(), null, null);

    	Map<String, Object> resultMap = new HashMap<String, Object>();
    	resultMap = JSON.parseObject(doGet);
    	resultMap = (Map<String, Object>) resultMap.get("data");
    	List<Map> vmData = (List<Map>) resultMap.get("data");
    	
    	//匹配成功的VM map
    	Map vmMap = null;
    	Map<String, Object> params = new HashMap<>();

    	for (Map map : vmData) {
    		
    		String ostype = map.get("ostype").toString();
    		String osname = String.valueOf(map.get("osname"));
    		long vmid = Long.parseLong(String.valueOf(map.get("vmid")));
    			
			if(ostype.contains(vmApply.getSystemEnvironment())) {//win7   win10
    			params.put("vmId", vmid);
    			//匹配数据没有被分配
    	    	List <VmApply> vmIdList = vmApplyMapper.queryBy(params);
    	    	if(vmIdList.size() == 0) {
    	    		vmMap = map;
    	    		break;
    	    	}
			} else if(ostype.equals("l2664") && osname != null && !osname.equals("") && osname.equals(vmApply.getSystemEnvironment())) {//	linux-centos 	linux-ubuntu 
    			params.put("vmId", vmid);
    			//匹配数据没有被分配
    	    	List <VmApply> vmIdList = vmApplyMapper.queryBy(params);
    	    	if(vmIdList.size() == 0) {
    	    		vmMap = map;
    	    		break;
    	    	} 
			} else if (ostype.equals("l2664") && osname.equals("null") && vmApply.getSystemEnvironment().equals("linux-kali")) {// 	linux-kali
    			params.put("vmId", vmid);
    			//匹配数据没有被分配
    	    	List <VmApply> vmIdList = vmApplyMapper.queryBy(params);
    	    	if(vmIdList.size() == 0) {
    	    		vmMap = map;
    	    		break;
    	    	} 
			}
    			
		}
    	
    	//没有匹配到 或 匹配数据被分配
    	if(null == vmMap) {
    		return msg = "0";//未通过，无可分配VM
    	} else {
    		//匹配到 并且 匹配数据没有被分配 	转为2：审核通过 		设置vmId
			vmApply.setVmId(Long.parseLong(String.valueOf(vmMap.get("vmid"))));
			vmApply.setVmUrl(VMURL+"?vmId="+Long.parseLong(String.valueOf(vmMap.get("vmid"))));
			vmApply.setVmIp(vmMap.get("vmip").toString());
	    	vmApply.setStatus(2);
	    	vmApply.setUpdateTime(new Date());
	    	vmApply.setUpdateBy(sessionProvider.getAttributeForSysUser(TicketUtil.getTOKEN(request, null)).getId());
	    	
	    	//维护VM申请表
	    	vmApplyMapper.updateByPrimaryKey(vmApply);
	    	
	        CommonProcess commonProcess = new CommonProcess();
	        commonProcess.setApplyId(vmApply.getId());
	        commonProcess.setSource(1);
	        commonProcess.setPoint(2);
	        commonProcess.setCreateBy(sessionProvider.getAttributeForSysUser(TicketUtil.getTOKEN(request, null)).getId());
	        commonProcess.setCreateTime(new Date());
	        commonProcess.setDelFlag(false);
	        
	      //维护流程节点表
	        commonProcessMapper.insert(commonProcess);
    	}

		return msg;
    }

    @Override
    public void otherPass(HttpServletRequest request, VmApply apply) {
    	// TODO Auto-generated method stub
		VmApply vmApply = vmApplyMapper.selectByPrimaryKey(apply.getId());
		
		Request2ModelUtil.covertObj(vmApply, apply);
		vmApply.setVmUrl(VMURL+"?vmId="+vmApply.getVmId());
    	vmApply.setStatus(2);
    	vmApply.setUpdateTime(new Date());
    	vmApply.setUpdateBy(sessionProvider.getAttributeForSysUser(TicketUtil.getTOKEN(request, null)).getId());
    	
    	//维护VM申请表
    	vmApplyMapper.updateByPrimaryKey(vmApply);
    	
        CommonProcess commonProcess = new CommonProcess();
        commonProcess.setApplyId(vmApply.getId());
        commonProcess.setSource(1);
        commonProcess.setPoint(2);
        commonProcess.setCreateBy(sessionProvider.getAttributeForSysUser(TicketUtil.getTOKEN(request, null)).getId());
        commonProcess.setCreateTime(new Date());
        commonProcess.setDelFlag(false);
        
      //维护流程节点表
        commonProcessMapper.insert(commonProcess);
    }
    
}
