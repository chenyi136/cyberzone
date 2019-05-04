package com.safecode.cyberzone.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.safecode.cyberzone.base.service.SessionProvider;
import com.safecode.cyberzone.controller.base.BaseController;
import com.safecode.cyberzone.service.AdMonitorService;

/**
 * 攻防监控Controller
 * @author huocf
 *
 */
@RestController
@RequestMapping(value = "adMonitor")
public class AdMonitorController  extends BaseController {


	@Autowired
	private SessionProvider sessionProvider;
    @Autowired
    private AdMonitorService adMonitorService;

    
    /**
     * 团队模式-查看详情（战队相关VM列表）
     * 
     * 只查询此系统产生的VM	2018.12.04
     * @param request
     * @param modelMap
     * @param corpsId	战队id
     * @return
     */
    @RequestMapping(value = "/detail", method = RequestMethod.GET)
    public Object detail(HttpServletRequest request, ModelMap modelMap, @RequestParam(value = "corpsId", required = true) Long corpsId) {
    	List<Map<String, Object>> list = adMonitorService.queryCorpsVm(corpsId);
    	return setSuccessModelMap(modelMap, list);
    }
    
    
    /**
     * 任务模式-大屏
     * @param request
     * @param modelMap
     * @param corpsId	战队id
     * @return
     */
    @RequestMapping(value = "/bigScreen", method = RequestMethod.GET)
    public Object bigScreen(HttpServletRequest request, ModelMap modelMap) {
    	modelMap = adMonitorService.bigScreen();
    	return setSuccessModelMap(modelMap);
    }
    
    
}
