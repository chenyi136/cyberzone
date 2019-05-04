package com.safecode.cyberzone.controller;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.safecode.cyberzone.service.DemoService;
import com.safecode.cyberzone.utils.FileUtil;


@RestController
@RequestMapping(value = "demo")
public class DemoController {
	
    @Autowired
    private DemoService demoService;

//    @RequestMapping(value = "/demoGet", method = RequestMethod.GET)
//	public Object demoGet(HttpServletRequest request, ModelMap modelMap) {
//    	
//    	Map<String, Object> params = BaseController.getParameterMap(request);
//    	PageInfo<Map> adTaskList = demoService.queryPageList(params);
//    	
//    	return setSuccessModelMap(modelMap,adTaskList);
//	}

  @RequestMapping(value = "/demoGet", method = RequestMethod.GET)
	public Object demoGet(HttpServletRequest request, ModelMap modelMap) {
  	
	Map<String, Object> params = new HashMap<>();
  	PageInfo<Map> adTaskList = demoService.queryPageList(params);
  	
  	return adTaskList;
	}
    
  @RequestMapping(value = "/tempTest", method = RequestMethod.GET)
	public void tempTest(HttpServletRequest request, HttpServletResponse response) throws IOException {
  	
//	  ClassPathResource resource = new ClassPathResource("export/aaa.txt");
//	  InputStream inputStream = resource.getInputStream();
	  //目前没有这个HTML文件
  		FileUtil.onlinePreview(request, response, "offlineSync.html");
	  
	}
  
}
