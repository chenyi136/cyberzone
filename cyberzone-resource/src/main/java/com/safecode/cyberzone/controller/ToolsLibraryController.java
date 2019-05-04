package com.safecode.cyberzone.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.websocket.server.PathParam;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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
import com.safecode.cyberzone.base.pojo.FileInfo;
import com.safecode.cyberzone.base.service.SessionProvider;
import com.safecode.cyberzone.base.support.Code;
import com.safecode.cyberzone.utils.Request2ModelUtil;
import com.safecode.cyberzone.base.utils.TicketUtil;
import com.safecode.cyberzone.controller.base.BaseController;
import com.safecode.cyberzone.pojo.ToolsLibrary;
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
	private SessionProvider sessionProvider;
	@Autowired
	private SysLogController sysLogController;
	
	
	/**
	 * 首页面各种展示图数据获取
	 */
	@RequestMapping(value = "/getToolsView", method = RequestMethod.GET)
	public ResponseData getToolsView() {
		Map<String, Object> map = toolsLibraryService.getToolsView();
		return new ResponseData(Code.OK.value(),"查询成功", map);
	}
	
	
	/** 
     * 获取上传的工具列表
     * @param request
     * @param modelMap
     * @return
     */
	@RequestMapping(value = "/get", method = RequestMethod.POST)
    public ResponseData<PageInfo> get(ToolsLibrary toolsLibrary, @PageableDefault(page = 1, size = 10, sort="id,desc")Pageable pageable) {
    	PageInfo<ToolsLibrary> toolsLibraryList = toolsLibraryService.queryPageList(toolsLibrary,pageable);
    	return new ResponseData(Code.OK.value(),"查询成功", toolsLibraryList);
    }
	
	
	/**
     * 删除上传的工具
     * @param request
     * @param modelMap
     * @param id  主键id
     * @return
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public ResponseData delete(String ids) {
    	if(ids.length()>0) {
    		String[] idList = ids.split(",");
    		List id = new ArrayList();
    		for(int i=0; i<idList.length; i++) {
    			id.add(idList[i]);
    		}
    		toolsLibraryService.delete(id);
    		return new ResponseData(Code.OK.value(),"删除工程", null);
    	} else {
    		return new ResponseData(Code.INTERNAL_SERVER_ERROR.value(),"无删除选项", null);
    	}
    }
    
    
    /**
     * 获取工具详情
     * @param request
     * @param modelMap
     * @param id  主键id
     * @return
     */
    @RequestMapping(value = "/detail", method = RequestMethod.POST)
    public ResponseData detail(Long id) {
    	ToolsLibrary toolsLibrary = toolsLibraryService.selectById(id);
    	return new ResponseData(Code.OK.value(),"删除成功", toolsLibrary);
    }
    
    /**
     * 添加并上传工具
     * @param request
     * @param modelMap
     * @param toolFile  工具文件
     * @param toolFile  说明文档文件
     * @return
     * @throws IOException 
     */
    @RequestMapping(value = "/add/{currentUserId}", method = RequestMethod.POST)
    public ResponseData<ToolsLibrary> add(ToolsLibrary toolsLibrary, MultipartFile toolFile, MultipartFile introFile, @PathVariable("currentUserId")long currentUserId) throws IOException {
    	try {
        	toolsLibrary.setCreateTime(new Date());
        	toolsLibrary.setUpdateTime(new Date());
        	toolsLibrary.setDelFlag(false);
        	toolsLibrary.setCreateBy(currentUserId);
        	toolsLibrary.setUpdateBy(currentUserId);
        	toolsLibrary.setIsOpen("1");
        	
        	//上传工具文件
        	if (toolFile != null && !toolFile.isEmpty() && StringUtils.isNotEmpty(toolFile.getOriginalFilename())) {
        		FileInfo toolFileInfo = FileUtil.saveFile(toolFile, FileUtil.UPLOADPATH_TOOLS);
        		Request2ModelUtil.covertObj(toolsLibrary, toolFileInfo);
        		
        		String realName = toolFileInfo.getRealName();
        		String realNamePath = realName.substring(0, realName.indexOf("."));
        		String oldPath = toolFileInfo.getFilePath();
        		FileUtil.copyFile(oldPath, FileUtil.REALNAMEPATH + "/" + realNamePath + "/" +toolFileInfo.getFileName());
        		toolsLibrary.setRealPath(FileUtil.FTPPATH + "/" + realNamePath + "/" +toolFileInfo.getFileName());
        	}
        	
        	//上传说明文档
        	if (introFile != null && !introFile.isEmpty() && StringUtils.isNotEmpty(introFile.getOriginalFilename())) {
        		FileInfo menuFileInfo = FileUtil.saveFile(introFile, FileUtil.UPLOADPATH_TOOLS);
        		toolsLibrary.setDocName(menuFileInfo.getName());
        		toolsLibrary.setDocFileName(menuFileInfo.getFileName());
        		toolsLibrary.setDocRealName(menuFileInfo.getRealName());
        		toolsLibrary.setDocFileSuffix(menuFileInfo.getFileSuffix());
        		toolsLibrary.setDocFileSize(menuFileInfo.getFileSize());
        		toolsLibrary.setDocFilePath(menuFileInfo.getFilePath());
        		
        		String realName = menuFileInfo.getRealName();
        		String realNamePath = realName.substring(0, realName.indexOf("."));
        		String oldPath = menuFileInfo.getFilePath();
        		FileUtil.copyFile(oldPath, FileUtil.REALNAMEPATH + "/" + realNamePath + "/" +menuFileInfo.getFileName());
        		toolsLibrary.setDocRealPath(FileUtil.FTPPATH + "/" + realNamePath + "/" +menuFileInfo.getFileName());
        	}
        	toolsLibraryService.insert(toolsLibrary);
        	
        	return new ResponseData<ToolsLibrary>(Code.OK.value(),"创建成功", null);
    	}catch (Exception e) {
    		e.printStackTrace();
			throw e; 
		}
    	
    	
    }


	/**
     * 修改工具详情
     * @param request
     * @param modelMap
     * @param toolFile  工具文件
     * @param toolFile  说明文档文件
     * @return
     * @throws IOException 
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResponseData<ToolsLibrary> update(ToolsLibrary toolsLibrary, MultipartFile toolFile, MultipartFile introFile, @PathVariable("currentUserId")long currentUserId) throws IOException {
    	
    	try {
        	toolsLibrary.setUpdateTime(new Date());
        	toolsLibrary.setUpdateBy(currentUserId);
        	//上传工具文件
        	if (toolFile != null && !toolFile.isEmpty() && StringUtils.isNotEmpty(toolFile.getOriginalFilename())) {
        		FileInfo toolFileInfo = FileUtil.saveFile(toolFile, FileUtil.UPLOADPATH_TOOLS);
        		Request2ModelUtil.covertObj(toolsLibrary, toolFileInfo);
        		String realName = toolFileInfo.getRealName();
        		String realNamePath = realName.substring(0, realName.indexOf("."));
        		FileInfo toolFileInfo1 = FileUtil.saveFile1(toolFile, FileUtil.REALNAMEPATH + "/" + realNamePath);
        		toolsLibrary.setRealPath(toolFileInfo1.getFilePath());
        		
        	}
        	 
        	//上传说明文档
        	if (introFile != null && !introFile.isEmpty() && StringUtils.isNotEmpty(introFile.getOriginalFilename())) {
        		FileInfo menuFileInfo = FileUtil.saveFile(introFile, FileUtil.UPLOADPATH_TOOLS);
        		toolsLibrary.setDocName(menuFileInfo.getName());
        		toolsLibrary.setDocFileName(menuFileInfo.getFileName());
        		toolsLibrary.setDocRealName(menuFileInfo.getRealName());
        		toolsLibrary.setDocFileSuffix(menuFileInfo.getFileSuffix());
        		toolsLibrary.setDocFileSize(menuFileInfo.getFileSize());
        		toolsLibrary.setDocFilePath(menuFileInfo.getFilePath());
        		
        		String realName = menuFileInfo.getRealName();
        		String realNamePath = realName.substring(0, realName.indexOf("."));
        		FileInfo toolFileInfo1 = FileUtil.saveFile1(toolFile, FileUtil.REALNAMEPATH + "/" + realNamePath);
        		toolsLibrary.setDocRealPath(toolFileInfo1.getFilePath());
        	}
        	
        	toolsLibraryService.updateById(toolsLibrary);
        	return new ResponseData<ToolsLibrary>(Code.OK.value(),"修改成功", null);
    	}catch (Exception e) {
    		e.printStackTrace();
			throw e; 
		}
    	
    }
    
    /**
     * 下载
     * @param request
     * @param modelMap
     * @param url 下载路径
     * @param fileName  文件名（带后缀）
     * @param downloadPath 保存本地的路径
     * @return
     * @throws IOException 
     */
    @RequestMapping(value = "/downLoad", method = RequestMethod.POST)
    public void downLoad(HttpServletRequest request, HttpServletResponse resp, String url, String fileName, String downloadPath) throws ServletException, IOException {
		Long userId = sessionProvider.getAttributeForSysUser(TicketUtil.getTOKEN(request, null)).getId();
//		Long userId = (long) 1;
		String userAccount = sessionProvider.getAttributeForSysUser(TicketUtil.getTOKEN(request, null)).getAccount();
//		String userAccount = "admin";
    	FileUtil.downLoadFile(request, resp, url, fileName);
    	
//    	String account = "admin";
    	Map<String, String> map = SysLogUtil.save(request,userAccount,"工具库下载:" + fileName);
    	sysLogController.add(map);
	 }
}
