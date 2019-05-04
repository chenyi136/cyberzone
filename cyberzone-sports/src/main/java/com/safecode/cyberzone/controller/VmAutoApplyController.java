package com.safecode.cyberzone.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.safecode.cyberzone.utils.Request2ModelUtil;
import com.safecode.cyberzone.controller.base.BaseController;
import com.safecode.cyberzone.pojo.VmApply;
import com.safecode.cyberzone.service.VmAutoApplyService;


/**
 * VM自动申请（对外）
 * @author huocf
 *
 */
@RestController
@RequestMapping(value = "vmAutoApply")
public class VmAutoApplyController extends BaseController {

    @Autowired
    private VmAutoApplyService vmAutoApplyService;

    /**
     * 添加 VM自动申请
     * @param request
     * @param modelMap
     * @return	msg = "4"  提示 一个用户最多申请4个
						msg = "0"  提示 无可分配VM，已转为手动申请
						msg = "1"  提示 申请成功
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Object add(HttpServletRequest request, ModelMap modelMap) {
    	VmApply vmApply = Request2ModelUtil.covert(VmApply.class, request);
    	String msg = vmAutoApplyService.add(vmApply);
    	modelMap.put("msg", msg);
    	return setSuccessModelMap(modelMap);
    }
    
    /**
     * 获取VM自动申请列表
     * @param request
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public Object get(HttpServletRequest request, ModelMap modelMap, String applyName ) {
    	List<Map<String, Object>> vmList = vmAutoApplyService.queryUserAutoApply(applyName);
    	return setSuccessModelMap(modelMap,vmList);
    }
}
