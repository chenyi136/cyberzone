package com.safecode.cyberzone.controller;

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
import com.safecode.cyberzone.base.dto.ResponseData;
import com.safecode.cyberzone.base.support.Code;
import com.safecode.cyberzone.controller.base.BaseController;
import com.safecode.cyberzone.pojo.BugCycle;
import com.safecode.cyberzone.pojo.BugNvd;
import com.safecode.cyberzone.service.BugCycleService;
import com.safecode.cyberzone.service.BugNvdService;
import com.safecode.cyberzone.vo.BugNvdVo;

@RestController
@RequestMapping(value = "bugNvd")
public class BugNvdController extends BaseController {


    @Autowired
    private BugNvdService bugNvdService;
    
	
    /**
     * 获取NVD漏洞库列表
     * @param request
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "/get", method = RequestMethod.POST)
    public ResponseData<Object> get(BugNvdVo bugNvdVo) {
    	PageInfo<BugNvd> cycleList = bugNvdService.queryPageList(bugNvdVo);
    	return new ResponseData<Object>(Code.OK.value(), "获取NVD列表", cycleList);
    }
    
    /**
     * NVD漏洞库漏洞详情
     * @param request
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "/detail", method = RequestMethod.GET)
    public ResponseData<Object> detail(@RequestParam(value = "id") Long id) {
    	BugNvdVo bugNvdVo = bugNvdService.queryById(id);
    	return new ResponseData<Object>(Code.OK.value(), "获取NVD详情", bugNvdVo);
    }
	
    
}
