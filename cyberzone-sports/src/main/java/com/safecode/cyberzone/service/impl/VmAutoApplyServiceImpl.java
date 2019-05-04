package com.safecode.cyberzone.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.safecode.cyberzone.mapper.CommonProcessMapper;
import com.safecode.cyberzone.mapper.VmApplyMapper;
import com.safecode.cyberzone.pojo.CommonProcess;
import com.safecode.cyberzone.pojo.VmApply;
import com.safecode.cyberzone.service.VmAutoApplyService;
import com.safecode.cyberzone.utils.HttpsUtils;


@Service
@Transactional
public class VmAutoApplyServiceImpl implements VmAutoApplyService {
	
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
    
    
    @Override
    public List<Map<String, Object>> queryUserAutoApply(String applyName) {
    	// TODO Auto-generated method stub
    	Map<String, Object> params = new HashMap<>();
    	params.put("applyName", applyName);
    	List <Map<String, Object>> applyNameList = vmApplyMapper.queryUserAutoApply(params);
    	return applyNameList;
    }
    
    @Override
    public String add(VmApply vmApply) {
    	// TODO Auto-generated method stub
    	String msg = "1";//成功

    	Map<String, Object> params = new HashMap<>();
    	params.put("applyName", vmApply.getApplyName());
    	params.put("notStatus", 3);//计算是否>=4，排除驳回的数据

    	//一个用户最多申请4个
    	List <VmApply> applyNameList = vmApplyMapper.queryBy(params);
    	if(null != applyNameList && 4 <= applyNameList.size()) {
    		return msg = "4";//一个用户最多申请4个
    	}

    	//匹配成功的VM map
    	Map vmMap = null;
    	
    	if(! (vmApply.getSystemEnvironment().equals("其它") || vmApply.getResourceAllocation() == 2)) {//操作系统为其它、资源分配为自定义，直接转手工，无法匹配VM列表的
    		
    		//调用获取深信服VM列表
    		String doGet = HttpsUtils.doGet(VMSURL, new HashMap<>(), null, null);
    		
    		Map<String, Object> resultMap = new HashMap<String, Object>();
    		resultMap = JSON.parseObject(doGet);
    		resultMap = (Map<String, Object>) resultMap.get("data");
    		List<Map> vmData = (List<Map>) resultMap.get("data");
    		
    		for (Map map : vmData) {
    			
    			String ostype = map.get("ostype").toString();
    			String osname = String.valueOf(map.get("osname"));
    			long vmid = Long.parseLong(String.valueOf(map.get("vmid")));
    			
    			if(ostype.contains(vmApply.getSystemEnvironment())) {//win7   win10
    				params.clear();
    				params.put("vmId", vmid);
    				//匹配数据没有被分配
    				List <VmApply> vmIdList = vmApplyMapper.queryBy(params);
    				if(vmIdList.size() == 0) {
    					vmMap = map;
    					break;
    				}
    			} else if(ostype.equals("l2664") && osname != null && !osname.equals("") && osname.equals(vmApply.getSystemEnvironment())) {//linux-centos 	linux-ubuntu 
    				params.clear();
    				params.put("vmId", vmid);
    				//匹配数据没有被分配
    				List <VmApply> vmIdList = vmApplyMapper.queryBy(params);
    				if(vmIdList.size() == 0) {
    					vmMap = map;
    					break;
    				} 
    			} else if (ostype.equals("l2664") && osname.equals("null") && vmApply.getSystemEnvironment().equals("linux-kali")) {// 	linux-kali
    				params.clear();
    				params.put("vmId", vmid);
    				//匹配数据没有被分配
    				List <VmApply> vmIdList = vmApplyMapper.queryBy(params);
    				if(vmIdList.size() == 0) {
    					vmMap = map;
    					break;
    				} 
    			}
    			
    		}//for end
    		
    	}//if end
    	
    	//1、其它		2、自定义	3、标准没有匹配到		4、匹配数据被分配
    	if(null == vmMap) {
    		msg = "0";//提示：无可分配VM，已转为手动申请
    		vmApply.setStatus(1);//转为1：待审核 
    	} else {
    		//匹配到 并且 匹配数据没有被分配 	转为2：审核通过 		设置vmId
			vmApply.setVmId(Long.parseLong(String.valueOf(vmMap.get("vmid"))));
			vmApply.setVmUrl(VMURL+"?vmId="+Long.parseLong(String.valueOf(vmMap.get("vmid"))));
			vmApply.setVmIp(vmMap.get("vmip").toString());
	    	vmApply.setStatus(2);
    	}

    	vmApply.setSource(1);
		vmApply.setCreateTime(new Date());
		vmApply.setUpdateTime(new Date());
        //暂时没有获取当前用户的公用方法(此接口第三方调用，可能不用维护以下两个字段)
		//vmApply.setCreateBy(uid == null ? "" : uid);
		//vmApply.setUpdateBy(uid == null ? "" : uid);
		vmApply.setDelFlag(false);
        
		//维护VM申请表
		vmApplyMapper.insert(vmApply);
		
		//维护流程节点表
        CommonProcess commonProcess = new CommonProcess();
        commonProcess.setApplyId(vmApply.getId());
        commonProcess.setSource(1);
        commonProcess.setPoint(1);
        //commonProcess.setCreateBy(createBy); 暂时没有获取当前用户的公用方法(此接口第三方调用，可能不用维护)
        commonProcess.setCreateTime(new Date());
        commonProcess.setDelFlag(false);
        commonProcessMapper.insert(commonProcess);
        
		if(null != vmMap) {
			//维护流程节点表
	        commonProcess.setPoint(2);
	        commonProcess.setCreateTime(new Date());
	        commonProcessMapper.insert(commonProcess);
		}

		return msg;
    }
    
    
}
