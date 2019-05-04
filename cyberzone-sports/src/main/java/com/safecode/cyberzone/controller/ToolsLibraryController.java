package com.safecode.cyberzone.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.safecode.cyberzone.controller.base.BaseController;
import com.safecode.cyberzone.service.ToolsLibraryService;
import com.safecode.cyberzone.utils.FileUtil;
import com.safecode.cyberzone.utils.SysLogUtil;;

//@CrossOrigin
@RestController
@RequestMapping(value = "toolsLibrary")
public class ToolsLibraryController extends BaseController {
	
	@Autowired
	private ToolsLibraryService toolsLibraryService;
    @Autowired
	private SysLogController sysLogController;
    
	/**
     * 获取工具列表(条件查询)
     * @param request
     * @param modelMap
     * @return
     */
	@RequestMapping(value = "/get", method = RequestMethod.POST)
    public Object get(HttpServletRequest request, ModelMap modelMap) {
    	
    	Map<String, Object> params = BaseController.getParameterMap(request);
    	PageInfo<Map<String, Object>> toolsLibraryList = toolsLibraryService.queryPageList(params, request);
    	
    	return setSuccessModelMap(modelMap,toolsLibraryList);
    }
	
	
    /**
     * 获取工具详情
     * @param request
     * @param modelMap
     * @param id  主键id
     * @return
     */
    @RequestMapping(value = "/detail", method = RequestMethod.POST)
    public Object detail(HttpServletRequest request, ModelMap modelMap, @RequestParam(value = "id", required = false) Long id) {
    	Map<String, Object> map = toolsLibraryService.selectById(id);
    	return setSuccessModelMap(modelMap, map);
    }
    
    
    /**
     * 工具(文档)下载
     * @param request
     * @param modelMap
     * @param url 下载路径
     * @param fileName  文件名（带后缀）
     * @return
     * @throws IOException 
     */
    @RequestMapping(value = "/downLoad", method = RequestMethod.POST)
    public void downLoad(HttpServletRequest req, HttpServletResponse resp, String url, String fileName) throws ServletException, IOException {
    	FileUtil.downLoadFile(req, resp, url, fileName);
		//////////////////////////////添加系统日志/////////////////////////	
		try {
			//记录操作日志
			String account = "admin";
			Map<String, String> map = new HashMap<String,String>();
			map = SysLogUtil.save(req,account,"下载工具:" + fileName);
			sysLogController.add(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		///////////////////////////////////////////////////////////////////////////////////
	 }
    
}
