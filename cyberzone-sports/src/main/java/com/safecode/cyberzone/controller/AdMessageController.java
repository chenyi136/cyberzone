package com.safecode.cyberzone.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.safecode.cyberzone.utils.Request2ModelUtil;
import com.safecode.cyberzone.controller.base.BaseController;
import com.safecode.cyberzone.pojo.AdMessage;
import com.safecode.cyberzone.service.AdMessageService;
import com.safecode.cyberzone.utils.SysLogUtil;;

@RestController
@RequestMapping(value = "adMessage")
public class AdMessageController extends BaseController {
	
	@Autowired
	private AdMessageService adMessageService;
    @Autowired
	private SysLogController sysLogController;
   
    /**
	 * 消息分页列表(含公告)
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "/get", method = RequestMethod.POST)
	public Object get(HttpServletRequest request, ModelMap modelMap) {
		
		Map<String, Object> params = BaseController.getParameterMap(request);
    	PageInfo<AdMessage> adMessageList = adMessageService.queryPageList(request, params);
    	
    	return setSuccessModelMap(modelMap,adMessageList);
	}

	/**
	 * 新增公告
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public Object add(HttpServletRequest request, ModelMap modelMap){
		AdMessage adMessage = Request2ModelUtil.covert(AdMessage.class, request);
		adMessageService.add(request, adMessage);

		try {
			String account = "admin";
			Map<String, String> map = SysLogUtil.save(request, account, "新增公告 id:" + adMessage.getId());
			sysLogController.add(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return setSuccessModelMap(modelMap);
	}
	
	/**
	 * 获取未读消息数量(含公告)
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "/getUnreadCount", method = RequestMethod.GET)
	public Object getUnreadCount(HttpServletRequest request, ModelMap modelMap) {
    	
		int unreadCount = adMessageService.getUnreadCount(request);
    	modelMap.put("unreadCount", unreadCount);
    	
    	return setSuccessModelMap(modelMap);
	}
	
	/**
	 * 全部标记为已读
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "/setAllReaded", method = RequestMethod.GET)
	public Object setAllReaded(HttpServletRequest request, ModelMap modelMap) {
    	
		adMessageService.setAllReaded(request);
    	return setSuccessModelMap(modelMap);
	}
	
	/**
	 * 公告分页列表
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "/queryAllAnnouncement", method = RequestMethod.POST)
	public Object queryAllAnnouncement(HttpServletRequest request, ModelMap modelMap) {
		Map<String, Object> params = BaseController.getParameterMap(request);
		PageInfo<Map<String, Object>> adMessageList = adMessageService.queryAllAnnouncement(request, params);
    	return setSuccessModelMap(modelMap, adMessageList);
	}
	/**
	 * 消息详情&&消息用户表标记为已读
	 * @param request
	 * @param modelMap
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/detail", method = RequestMethod.POST)
	public Object detail(HttpServletRequest request, ModelMap modelMap, @RequestParam(value = "id", required = false) Long id) {
		Map<String, Object> messageMap = adMessageService.selectById(request,id);
    	return setSuccessModelMap(modelMap, messageMap);
	}
	/**
	 * 消息收藏
	 * @param request
	 * @param modelMap
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/setCollectById", method = RequestMethod.POST)
	public Object setCollectById(HttpServletRequest request, ModelMap modelMap, @RequestParam(value = "id", required = false) Long id) {
		adMessageService.setCollectById(request,id);
    	return setSuccessModelMap(modelMap);
	}



	
}
