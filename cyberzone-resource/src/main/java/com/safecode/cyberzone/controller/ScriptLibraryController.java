package com.safecode.cyberzone.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
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
import com.safecode.cyberzone.controller.base.BaseController;
import com.safecode.cyberzone.pojo.ScriptLibrary;
import com.safecode.cyberzone.service.ScriptLibraryService;
import com.safecode.cyberzone.utils.FileUtil;
import com.safecode.cyberzone.utils.Request2ModelUtil;
import com.safecode.cyberzone.utils.SysLogUtil;
import com.safecode.cyberzone.vo.ScriptLibraryVo;


@RestController
@RequestMapping(value = "scriptLibrary")
public class ScriptLibraryController extends BaseController {


    @Autowired
    private ScriptLibraryService scriptLibraryService;
	@Autowired
	private SessionProvider sessionProvider;
	@Autowired
	private SysLogController sysLogController;

	/**
	 * 获取脚本库列表(分页)
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "/get", method = RequestMethod.POST)
	public ResponseData<Object> get(ScriptLibraryVo scriptLibraryVo) {
		PageInfo<ScriptLibrary> scriptLibraryList = scriptLibraryService.queryPageList(scriptLibraryVo);
		return new ResponseData<Object>(Code.OK.value(), "获取脚本库列表", scriptLibraryList);
	}

	/**
	 * 获取脚本库列表(有条件全查)
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "/queryAll", method = RequestMethod.POST)
	public ResponseData<Object> queryAll(ScriptLibraryVo scriptLibraryVo) {
		List<ScriptLibrary> scriptLibraryList = scriptLibraryService.queryAll(scriptLibraryVo);
		return new ResponseData<Object>(Code.OK.value(), "获取脚本库列表", scriptLibraryList);
	}
	
	/**
	 * 新增脚本库
	 * 
	 * @param request
	 * @param modelMap
	 * @param scriptFile
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public Object add(HttpServletRequest request, ScriptLibrary scriptLibrary, MultipartFile scriptFile)
			throws Exception {
		scriptLibraryService.add(request, scriptLibrary, scriptFile);
		return new ResponseData<Object>(Code.OK.value(), "添加成功", null);
	}

	/**
	 * 删除脚本库
	 * 
	 * @param request
	 * @param modelMap
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/delete/{currentUserId}", method = RequestMethod.POST)
	public Object delete(HttpServletRequest request, String ids, @PathVariable("currentUserId") Long currentUserId) {
		if(StringUtils.isNotBlank(ids)){
			String[] idsList = ids.split(",");
			for (String idStr : idsList) {
				long id = Long.parseLong(idStr);
				scriptLibraryService.delete(id, currentUserId);
			}
		}
		return new ResponseData<Object>(Code.OK.value(), "删除成功", null);
	}

	/**
	 * 脚本库详情 
	 * 2018年7月31日下午5:22:15
	 * @param request
	 * @param modelMap
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/detail", method = RequestMethod.GET)
	public Object show(@RequestParam(value = "id")Long id) throws Exception {
		ScriptLibrary scriptLibrary = scriptLibraryService.selectById(id);
		return new ResponseData<Object>(Code.OK.value(), "脚本详情", scriptLibrary);
	}

	/**
	 * 修改脚本库
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "/update/{currentUserId}", method = RequestMethod.POST)
	public Object update(HttpServletRequest request, ScriptLibrary scriptLibrary, MultipartFile scriptFile, @PathVariable("currentUserId") Long currentUserId) throws Exception {
		scriptLibraryService.update(scriptLibrary, scriptFile, currentUserId);
		return new ResponseData<Object>(Code.OK.value(), "修改成功", null);
	}
	
	 /**
     * 脚本库文件下载
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
	 }
    
    /**
     * 文件预览
     * @param request
     * @param response
     * @param modelMap
     * @param filePath
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/previewOnline", method = RequestMethod.GET)
    public ResponseData<Object> showPicture(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap, @RequestParam(value = "filePath", required = false) String filePath) throws IOException {
    	FileUtil.onlinePreview(request, response, filePath);
		return new ResponseData<Object>(Code.OK.value(), "文件预览", null);
    }

}
