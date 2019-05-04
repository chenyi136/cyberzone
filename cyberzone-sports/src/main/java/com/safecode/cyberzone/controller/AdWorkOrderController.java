package com.safecode.cyberzone.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.PageInfo;
import com.safecode.cyberzone.base.service.SessionProvider;
import com.safecode.cyberzone.utils.Request2ModelUtil;
import com.safecode.cyberzone.base.utils.TicketUtil;
import com.safecode.cyberzone.controller.base.BaseController;
import com.safecode.cyberzone.mapper.AdTaskMapper;
import com.safecode.cyberzone.pojo.AdWorkOrder;
import com.safecode.cyberzone.pojo.AdWorkOrderScore;
import com.safecode.cyberzone.service.AdTaskService;
import com.safecode.cyberzone.service.AdWorkOrderService;


/**
 * 攻防工单Controller
 * @author huocf
 *
 */
@RestController
@RequestMapping(value = "adWorkOrder")
public class AdWorkOrderController extends BaseController {

	@Autowired
	private SessionProvider sessionProvider;
    @Autowired
    private AdWorkOrderService workOrderService;
    @Autowired
    private AdTaskService adTaskService;
    @Autowired
    private AdTaskMapper taskMapper;

    
    /**
     * 获取工单列表
     * 查询当前用户所属战队对应的工单数据
     * @param request
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public Object get(HttpServletRequest request, ModelMap modelMap) {
		
    	Map<String, Object> params = BaseController.getParameterMap(request);
    	if(params.get("type").toString().equals("1")) {//type=1	 攻防工单 	type=2  指挥工单 
        	Long currentUserId = getCurrentUserId(request);
        	Long teamId = taskMapper.queryUserCorpsId(currentUserId);
    		params.put("teamId", teamId);
    	}
    	PageInfo<Map<String, Object>> workOrderList = workOrderService.queryPageList(params);
    	
    	return setSuccessModelMap(modelMap,workOrderList);
    }

    
    /**
     * 删除工单
     * @param request
     * @param modelMap
     * @param id 工单id
     * @return
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public Object delete(HttpServletRequest request, ModelMap modelMap,Long id) {
    	workOrderService.delete(request, id);
    	return setSuccessModelMap(modelMap);
    }

    
    /**
     * 添加回显 或 修改回显
     * @param request
     * @param modelMap
     * @param id
     * @return
     */
    @RequestMapping(value = "/show", method = RequestMethod.GET)
    public Object show(HttpServletRequest request, ModelMap modelMap, @RequestParam(value = "id", required = false) Long id) {
    	modelMap = workOrderService.show(request, modelMap, id);
    	return setSuccessModelMap(modelMap);
    }


    /**
     * 添加工单
     * @param request
     * @param modelMap
     * @return
     * @throws IOException 
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Object add(HttpServletRequest request, ModelMap modelMap, @RequestParam(value="createFiles", required = false)MultipartFile[] createFiles) throws IOException {
    	AdWorkOrder workOrder = Request2ModelUtil.covert(AdWorkOrder.class, request);
    	workOrderService.add(request, workOrder, createFiles);
    	return setSuccessModelMap(modelMap);
    }

    
    /**
     * 查看详情
     * @param request
     * @param modelMap
     * @param id 工单id
     * @return
     */
    @RequestMapping(value = "/detail", method = RequestMethod.GET)
    public Object detail(HttpServletRequest request, ModelMap modelMap, @RequestParam(value = "id", required = true) Long id) {
    	modelMap = workOrderService.detail(modelMap, id);
    	return setSuccessModelMap(modelMap);
    }
    
    
    /**
     * 1：提交工单；2：审核通过；6：审核驳回；4：挂起工单；
     * @param request
     * @param modelMap
     * @param id  工单id
     * @param status  
     * @return
     */
    @RequestMapping(value = "/changeWorkOrderStatus", method = RequestMethod.POST)
    public Object changeWorkOrderStatus(HttpServletRequest request, ModelMap modelMap, @RequestParam(value = "id", required = true) Long id, int status) {
    	workOrderService.changeWorkOrderStatus(request, id, status);
    	return setSuccessModelMap(modelMap);
    }

    
    /**
     * 3：已完成；
     * @param request
     * @param modelMap
     * @param id  工单id
     * @param status  
     * @param operationResult  操作结果   
     * @param completeWay  	完成方式 
     * @param completeAnneRemark  	完成附件备注 
     * @return
     * @throws IOException 
     */
    @RequestMapping(value = "/complete", method = RequestMethod.POST)
    public Object complete(HttpServletRequest request, ModelMap modelMap, @RequestParam(value="completeFiles", required = false)MultipartFile[] completeFiles) throws IOException {
    	AdWorkOrder adWorkOrder = Request2ModelUtil.covert(AdWorkOrder.class, request);
    	workOrderService.complete(request, adWorkOrder, completeFiles);
    	return setSuccessModelMap(modelMap);
    }
    
    
    /**
     * 5：已归档；
     * @param request
     * @param modelMap
     * @param id  工单id
     * @param status  
     * @param archiveAnneRemark	归档附件备注  
     * @return
     */
    @RequestMapping(value = "/archive", method = RequestMethod.POST)
    public Object archive(HttpServletRequest request, ModelMap modelMap, @RequestParam(value = "id", required = true) Long id, int status, 
    						String archiveAnneRemark, @RequestParam(value="archiveFiles", required = false)MultipartFile[] archiveFiles) throws IOException {
    	workOrderService.archive(request, id, status, archiveAnneRemark, archiveFiles);
    	return setSuccessModelMap(modelMap);
    }
    
    
    /**
     * 恢复工单（改回  2：已通过；  状态）
     * @param request
     * @param modelMap
     * @param id  工单id
     * @param status  
     * @return
     */
    @RequestMapping(value = "/recover", method = RequestMethod.POST)
    public Object recover(HttpServletRequest request, ModelMap modelMap, @RequestParam(value = "id", required = true) Long id, int status) {
    	workOrderService.recover(request, id, status);
    	return setSuccessModelMap(modelMap);
    }
    
    
    /**
     * 评分工单
     * @param request
     * @param modelMap
     * @param id  工单id
     * @return
     */
    @RequestMapping(value = "/score", method = RequestMethod.POST)
    public Object score(HttpServletRequest request, ModelMap modelMap, @RequestParam(value="scoreFiles", required = false)MultipartFile[] scoreFiles) throws IOException {
    	AdWorkOrderScore adWorkOrderScore = Request2ModelUtil.covert(AdWorkOrderScore.class, request);
    	workOrderService.score(request, adWorkOrderScore, scoreFiles);
    	return setSuccessModelMap(modelMap);
    }
    
    
    /**
     * 查询未完成工单
     * @param request
     * @param modelMap
     * @param taskId 任务id
     * @return
     */
    @RequestMapping(value = "/queryUnfinished", method = RequestMethod.GET)
    public Object queryUnfinished(HttpServletRequest request, ModelMap modelMap, @RequestParam(value = "taskId", required = true) Long taskId) {
		List<Map<String, Object>> unfinishedWorkOrder = workOrderService.queryUnfinished(taskId);
    	return setSuccessModelMap(modelMap,unfinishedWorkOrder);
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
