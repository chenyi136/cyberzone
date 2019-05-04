package com.safecode.cyberzone.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.safecode.cyberzone.base.pojo.SysUser;
import com.safecode.cyberzone.base.service.SessionProvider;
import com.safecode.cyberzone.utils.Request2ModelUtil;
import com.safecode.cyberzone.base.utils.TicketUtil;
import com.safecode.cyberzone.controller.base.BaseController;
import com.safecode.cyberzone.pojo.VmApply;
import com.safecode.cyberzone.service.VmApplyService;
import com.safecode.cyberzone.utils.SysLogUtil;


/**
 * VM申请
 * @author huocf
 *
 */
@RestController
@RequestMapping(value = "vmApply")
public class VmApplyController extends BaseController {


    @Autowired
    private VmApplyService vmApplyService;
	@Autowired
	private SysLogController sysLogController;
	@Autowired
	private SessionProvider sessionProvider;

    /**
     * 获取VM申请列表
     * @param request
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "/get", method = RequestMethod.POST)
    public Object get(HttpServletRequest request, ModelMap modelMap) {
    	
    	Map<String, Object> params = BaseController.getParameterMap(request);
    	if(params.get("type").toString().equals("1")) {//type=1	VM申请页面 	type=2	审批页面 
    		params.put("userId", sessionProvider.getAttributeForSysUser(TicketUtil.getTOKEN(request, null)).getId());
    	}
    	PageInfo<VmApply> vmApplyList = vmApplyService.queryPageList(params);
    	
    	return setSuccessModelMap(modelMap,vmApplyList);
    }

    /**
     * 删除VM申请
     * @param request
     * @param modelMap
     * @param id
     * @return
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public Object delete(HttpServletRequest request, ModelMap modelMap,Long id) {
    	vmApplyService.delete(request, id);
		//////////////////////////////添加系统日志/////////////////////////	
		try {
			//记录操作日志
			String account = "admin";
			Map<String, String> map = new HashMap<String,String>();
			map = SysLogUtil.save(request,account,"删除VM申请 id为:"+id);
			sysLogController.add(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		///////////////////////////////////////////////////////////////////////////////////
    	return setSuccessModelMap(modelMap);
    }

    /**
     * 添加VM申请
     * @param request
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Object add(HttpServletRequest request, ModelMap modelMap) {
    	VmApply vmApply = Request2ModelUtil.covert(VmApply.class, request);
    	vmApplyService.add(request, vmApply);
    	return setSuccessModelMap(modelMap);
    }

    /**
     * 提交审核 或 驳回审核
     * @param request
     * @param modelMap
     * @param id
     * @param status （1：待审核；3：审核驳回；）
     * @param remark
     * @return
     */
    @RequestMapping(value = "/submitData", method = RequestMethod.POST)
    public Object submitData(HttpServletRequest request, ModelMap modelMap, @RequestParam(value = "id", required = false) Long id, int status,String remark) {
    	vmApplyService.submitData(request, id, status, remark);
		//////////////////////////////添加系统日志/////////////////////////	
		try {
			//记录操作日志
			String statusStr = "";
			if(status == 1) {
				statusStr = "VM申请提交审核";
			} else if(status == 3) {
				statusStr = "VM申请审核驳回";
			}
			String account = "admin";
			Map<String, String> map = new HashMap<String,String>();
			map = SysLogUtil.save(request,account,statusStr+" id为:"+id);
			sysLogController.add(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		///////////////////////////////////////////////////////////////////////////////////
        return setSuccessModelMap(modelMap);
    }

    /**
     * 查看详情 或 修改回显
     * @param request
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "/detail", method = RequestMethod.GET)
    public Object detail(HttpServletRequest request, ModelMap modelMap, @RequestParam(value = "id", required = false) Long id) {
    	modelMap = vmApplyService.detail(modelMap, id);
    	return setSuccessModelMap(modelMap);
    }
    
    /**
     * 修改申请
     * @param request
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Object update(HttpServletRequest request, ModelMap modelMap) {
    	
    	VmApply vmApply = Request2ModelUtil.covert(VmApply.class, request);
    	vmApplyService.update(request, vmApply);
    	
		//////////////////////////////添加系统日志/////////////////////////	
		try {
			//记录操作日志
			String account = "admin";
			Map<String, String> map = new HashMap<String,String>();
			map = SysLogUtil.save(request,account,"修改VM申请 id为:"+vmApply.getId());
			sysLogController.add(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		///////////////////////////////////////////////////////////////////////////////////
    	return setSuccessModelMap(modelMap);
    }
    
    /**
     * 通过
     * 2018.10.26 弃用，需求改为：点通过，都需要手动填写。不进行二次匹配VM列表
     * @param request
     * @param modelMap
     * @return	msg = "0"  提示 未通过，无可分配VM
						msg = "1"  提示 申请成功
     */
    @RequestMapping(value = "/pass", method = RequestMethod.POST)
    public Object pass(HttpServletRequest request, ModelMap modelMap, @RequestParam(value = "id", required = true) Long id) {
    	
    	String msg = vmApplyService.pass(request, id);
    	modelMap.put("msg", msg);
    	
		//////////////////////////////添加系统日志/////////////////////////	
    	if("1".equals(msg)) {
    		try {
    			//记录操作日志
    			String account = "admin";
    			Map<String, String> map = new HashMap<String,String>();
    			map = SysLogUtil.save(request,account,"VM申请通过 id为:"+id);
    			sysLogController.add(map);
    		} catch (Exception e) {
    			e.printStackTrace();
    		}
    	}
		///////////////////////////////////////////////////////////////////////////////////

    	return setSuccessModelMap(modelMap);
    }
    
    /**
     * 通过(操作系统为：其它)
     * @param request
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "/otherPass", method = RequestMethod.POST)
    public Object otherPass(HttpServletRequest request, ModelMap modelMap) {
    	
    	VmApply apply = Request2ModelUtil.covert(VmApply.class, request);
    	vmApplyService.otherPass(request, apply);
    	
		//////////////////////////////添加系统日志/////////////////////////	
		try {
			//记录操作日志
			String account = "admin";
			Map<String, String> map = new HashMap<String,String>();
			map = SysLogUtil.save(request,account,"VM申请通过 id为:"+apply.getId());
			sysLogController.add(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		///////////////////////////////////////////////////////////////////////////////////

    	return setSuccessModelMap(modelMap);
    }
    
}
