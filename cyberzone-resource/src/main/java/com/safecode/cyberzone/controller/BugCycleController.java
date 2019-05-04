package com.safecode.cyberzone.controller;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dom4j.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.PageInfo;
import com.safecode.cyberzone.base.dto.ResponseData;
import com.safecode.cyberzone.base.service.SessionProvider;
import com.safecode.cyberzone.base.support.Code;
import com.safecode.cyberzone.base.utils.TicketUtil;
import com.safecode.cyberzone.controller.base.BaseController;
import com.safecode.cyberzone.pojo.BugCycle;
import com.safecode.cyberzone.service.BugCycleService;
import com.safecode.cyberzone.utils.FileUtil;
import com.safecode.cyberzone.utils.SysLogUtil;
import com.safecode.cyberzone.vo.BugCycleVo;

@RestController
@RequestMapping(value = "bugCycle")
public class BugCycleController extends BaseController {


    @Autowired
    private BugCycleService bugCycleService;
    @Autowired
    private SysLogController sysLogController;
    @Autowired
	private SessionProvider sessionProvider;
	
    /**
     * 获取漏洞库周期列表
     * @param request
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "/get", method = RequestMethod.POST)
    public ResponseData<Object> get(BugCycleVo bugCycleVo) {
    	PageInfo<BugCycle> cycleList = bugCycleService.queryPageList(bugCycleVo);
    	return new ResponseData<Object>(Code.OK.value(), "获取漏洞库周期列表", cycleList);
    }
		
    /**
     * 删除漏洞库周期
     * @param request
     * @param modelMap
     * @param id
     * @return
     */
    @RequestMapping(value = "/delete/{currentUserId}/{id}", method = RequestMethod.GET)
    public ResponseData<Object> delete(@PathVariable("currentUserId") Long currentUserId, @PathVariable("id") Long id) {
    	bugCycleService.delete(currentUserId, id);
    	return new ResponseData<Object>(Code.OK.value(), "删除成功", null);
    }
    
    /**
     * 导入 NVD CNVD XML
     * @param request
     * @param modelMap
     * @param nvdFile
     * @return
     * @throws IOException
     * @throws DocumentException
     */
    @RequestMapping(value = "/importBugXml/{currentUserId}", method = RequestMethod.POST)
    public ResponseData<Object> importBugXml(@PathVariable("currentUserId") Long currentUserId, MultipartFile bugFile, int source) 
    		throws IOException, DocumentException {
    	bugCycleService.importBugXml(bugFile,source,currentUserId);
    	return new ResponseData<Object>(Code.OK.value(), "导入成功", null);
    }
       
    /**
     * CNVD在线预览
     * 
     */
    @RequestMapping(value = "/onlinePreview", method = RequestMethod.GET)
    public ResponseData<Object> onlinePreview(HttpServletRequest req, HttpServletResponse resp,@RequestParam(value = "filePath", required = false) String filePath) 
    		throws IOException, DocumentException {
    	FileUtil.onlinePreview(req, resp, filePath);
    	return new ResponseData<Object>(Code.OK.value(), null, null);
    }
    
}
