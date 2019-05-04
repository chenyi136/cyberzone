package com.safecode.cyberzone.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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
import com.safecode.cyberzone.base.pojo.SysUser;
import com.safecode.cyberzone.base.service.SessionProvider;
import com.safecode.cyberzone.base.support.Code;
import com.safecode.cyberzone.utils.Request2ModelUtil;
import com.safecode.cyberzone.base.utils.TicketUtil;
import com.safecode.cyberzone.controller.base.BaseController;
import com.safecode.cyberzone.mapper.AdTaskMapper;
import com.safecode.cyberzone.pojo.AdTask;
import com.safecode.cyberzone.service.AdTaskService;
import com.safecode.cyberzone.utils.SysLogUtil;


/**
 * 攻防任务Controller
 * @author huocf
 *
 */
@RestController
@RequestMapping(value = "adTask")
public class AdTaskController extends BaseController {

	@Autowired
	private SessionProvider sessionProvider;
    @Autowired
    private AdTaskService adTaskService;
    @Autowired
    private AdTaskMapper taskMapper;
	
    /**
     * 获取攻防任务列表（指挥团队）
     * @param request
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public Object get(HttpServletRequest request, ModelMap modelMap) {
    	
    	Map<String, Object> params = BaseController.getParameterMap(request);
    	PageInfo<AdTask> adTaskList = adTaskService.queryPageList(params);
    	
    	return setSuccessModelMap(modelMap,adTaskList);
    }
    
    
    /**
     * 获取攻防任务列表（攻防团队）
     * 查询任务状态=已提交，并且对应自己战队的数据
     * @param request
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "/getAdList", method = RequestMethod.GET)
    public Object getAdList(HttpServletRequest request, ModelMap modelMap) {

    	Long currentUserId = getCurrentUserId(request);
    	Long teamId = taskMapper.queryUserCorpsId(currentUserId);
    	
    	Map<String, Object> params = BaseController.getParameterMap(request);
    	params.put("status", "1,2,3");//1：已提交；2：已终止；3：已结束；
    	params.put("teamId", teamId);
    	PageInfo<AdTask> adTaskList = adTaskService.queryPageAdList(params);
    	
    	return setSuccessModelMap(modelMap,adTaskList);
    }
    
    
    /**
     * 删除任务（仅0：待提交；状态下可删除）
     * @param request
     * @param modelMap
     * @param id
     * @return
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public Object delete(HttpServletRequest request, ModelMap modelMap,Long id) {
    	modelMap = adTaskService.delete(request, modelMap, id);
    	return setSuccessModelMap(modelMap);
    }
    
    
    /**
     * 添加回显 或 修改回显
     * @param request
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "/show", method = RequestMethod.GET)
    public Object show(HttpServletRequest request, ModelMap modelMap, @RequestParam(value = "id", required = false) Long id) {
    	modelMap = adTaskService.show(modelMap, id);
    	return setSuccessModelMap(modelMap);
    }
    
    
    /**
     * 添加 并 提交 攻防任务
     * @param request
     * @param modelMap
     * @param targets 选择的靶标（多）
     * @param teams 选择的战队（多）
     * @return 
     */
    @RequestMapping(value = "/add/{currentUserId}", method = RequestMethod.POST)
    public Object add(HttpServletRequest request, @PathVariable("currentUserId") Long currentUserId, AdTask adTask, String targets, String teams) {
    	adTaskService.add(currentUserId, adTask, targets, teams);
		return new ResponseData<Map<Object, Object>>(Code.OK.value(), null, null);
    }
    
    
    /**
     * 修改攻防任务
     * @param request
     * @param modelMap
     * @return
     * @throws IOException 
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Object update(HttpServletRequest request, ModelMap modelMap, String targets, String teams) throws IOException {
    	AdTask adTask = Request2ModelUtil.covert(AdTask.class, request);
    	modelMap = adTaskService.update(request, modelMap, adTask, targets, teams);
    	return setSuccessModelMap(modelMap);
    }
    
    
    /**
     * 查看详情
     * @param request
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "/detail", method = RequestMethod.GET)
    public Object detail(HttpServletRequest request, ModelMap modelMap, @RequestParam(value = "id", required = true) Long id) {
    	modelMap = adTaskService.detail(modelMap, id);
    	return setSuccessModelMap(modelMap);
    }
    
    
    /**
     * 1：提交任务；
     * @param request
     * @param modelMap
     * @param id
     * @param status
     * @return
     */
    @RequestMapping(value = "/submitData", method = RequestMethod.POST)
    public Object submitData(HttpServletRequest request, ModelMap modelMap, @RequestParam(value = "id", required = true) Long id, int status) {
    	modelMap = adTaskService.submitData(request, modelMap, id, status);
    	return setSuccessModelMap(modelMap);
    }
    
    
    /**
     * 2：终止任务；
     * @param request
     * @param modelMap
     * @param id
     * @param status
     * @return
     */
    @RequestMapping(value = "/breakData", method = RequestMethod.POST)
    public Object breakData(HttpServletRequest request, ModelMap modelMap, @RequestParam(value = "id", required = true) Long id, int status) {
    	adTaskService.breakData(request, id, status);
    	return setSuccessModelMap(modelMap);
    }
    
    
    /**
     * 3：结束任务；
     * @param request
     * @param modelMap
     * @param id
     * @param status
     * @return
     */
    @RequestMapping(value = "/overData", method = RequestMethod.POST)
    public Object overData(HttpServletRequest request, ModelMap modelMap, @RequestParam(value = "id", required = true) Long id, int status) {
    	modelMap = adTaskService.overData(request, modelMap, id, status);
    	return setSuccessModelMap(modelMap);
    }
    
    
    /**
     * 1：接受；2：拒绝；
     * @param request
     * @param modelMap
     * @param id
     * @param status
     * @return
     */
    @RequestMapping(value = "/acceptOrReject", method = RequestMethod.POST)
    public Object acceptOrReject(HttpServletRequest request, ModelMap modelMap, @RequestParam(value = "id", required = true) Long id, int status) {
    	modelMap = adTaskService.acceptOrReject(request, modelMap, id, status);
    	return setSuccessModelMap(modelMap);
    }
    
    
	/**
	 * 获取当前用户id
	 * */
	private Long getCurrentUserId(HttpServletRequest request) {
		Long currentUserId = null;
		// true = 线上 			false = 本地开发
		if(true) {
			currentUserId = sessionProvider.getAttributeForSysUser(TicketUtil.getTOKEN(request, null)).getId();
		} else {
			currentUserId = 1L;
		}
		return currentUserId;
	}
	
}
