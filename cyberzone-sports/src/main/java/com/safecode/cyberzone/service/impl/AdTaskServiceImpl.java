package com.safecode.cyberzone.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.safecode.cyberzone.base.service.SessionProvider;
import com.safecode.cyberzone.base.utils.DataUtil;
import com.safecode.cyberzone.base.utils.Request2ModelUtil;
import com.safecode.cyberzone.base.utils.TicketUtil;
import com.safecode.cyberzone.mapper.AdTaskMapper;
import com.safecode.cyberzone.mapper.AdTaskProcessMapper;
import com.safecode.cyberzone.mapper.AdTaskTargetMapper;
import com.safecode.cyberzone.mapper.AdTaskTeamsMapper;
import com.safecode.cyberzone.mapper.AdWorkOrderMapper;
import com.safecode.cyberzone.pojo.AdTask;
import com.safecode.cyberzone.pojo.AdTaskProcess;
import com.safecode.cyberzone.pojo.AdTaskTarget;
import com.safecode.cyberzone.pojo.AdTaskTeams;
import com.safecode.cyberzone.pojo.AdWorkOrder;
import com.safecode.cyberzone.service.AdMessageService;
import com.safecode.cyberzone.service.AdTaskService;
import com.safecode.cyberzone.service.AdWorkOrderService;


@Service
@Transactional
public class AdTaskServiceImpl implements AdTaskService {

	@Autowired
	private SessionProvider sessionProvider;
    @Autowired
    private AdTaskMapper taskMapper;
    @Autowired
    private AdTaskTargetMapper taskTargetMapper;
    @Autowired
    private AdTaskTeamsMapper taskTeamsMapper;
    @Autowired
    private AdTaskProcessMapper taskProcessMapper;
    @Autowired
    private AdWorkOrderMapper workOrderMapper;
    @Autowired
    private AdWorkOrderService workOrderService;
    @Autowired
    private AdMessageService messageService;
    
    
	@Override
	public PageInfo<AdTask> queryPageList(Map<String, Object> params) {
		// TODO Auto-generated method stub
		if (DataUtil.isEmpty(params.get("pageNum"))) {
            params.put("pageNum", 1);
        }
        if (DataUtil.isEmpty(params.get("pageSize"))) {
            params.put("pageSize", 10);
        }
        if (DataUtil.isEmpty(params.get("orderBy"))) {
            params.put("orderBy", "id_ desc");
        }

		PageHelper.startPage(params);
		Page<AdTask> page = taskMapper.queryPageList(params);
		
		return new PageInfo<AdTask>(page);	
	}
	
	
	@Override
	public PageInfo<AdTask> queryPageAdList(Map<String, Object> params) {
		// TODO Auto-generated method stub
		if (DataUtil.isEmpty(params.get("pageNum"))) {
            params.put("pageNum", 1);
        }
        if (DataUtil.isEmpty(params.get("pageSize"))) {
            params.put("pageSize", 10);
        }
        if (DataUtil.isEmpty(params.get("orderBy"))) {
            params.put("orderBy", "a.id_ desc");
        }
        
		PageHelper.startPage(params);
		Page<AdTask> page = taskMapper.queryPageAdList(params);
		
		return new PageInfo<AdTask>(page);	
	}
	
	
	@Override
	public ModelMap delete(HttpServletRequest request, ModelMap modelMap, Long id) {
		// TODO Auto-generated method stub
		Long currentUserId = getCurrentUserId(request);

		AdTask adTask = taskMapper.selectByPrimaryKey(id);
		if(adTask.getDelFlag() || adTask.getStatus() > 0) {
			/**
			 * 不能删除已删除数据
				不能删除已提交数据
			    不能删除已结束数据
				不能删除已终止数据
			 */
        	modelMap.put("msg", 1);
        	return modelMap;
		}
		
		adTask.setUpdateBy(currentUserId);
		adTask.setUpdateTime(new Date());
		adTask.setDelFlag(true);
		taskMapper.updateByPrimaryKey(adTask);
		
		modelMap.put("msg", 0);
		return modelMap;
	}
	
	
	@Override
	public ModelMap show(ModelMap modelMap, Long id) {
		// TODO Auto-generated method stub
		//兼容 添加回显 和 修改回显 共同一个接口
		if(id != null) {//代表修改回显
			Map<String, Object> params = new HashMap<>();
			params.put("taskId", id);
			
			//查询任务数据
			AdTask adTask = taskMapper.selectByPrimaryKey(id);
			modelMap.put("adTask", adTask);
			
			//查询任务选中的靶标数据
			List<AdTaskTarget> checkedTarget = taskTargetMapper.queryBy(params);
			modelMap.put("checkedTarget", checkedTarget);
			
			//查询任务选中的战队数据
			List<AdTaskTeams> checkedTeams = taskTeamsMapper.queryBy(params);
			modelMap.put("checkedTeams", checkedTeams);
		}
		
		//查询全部靶标数据
		List<Map<String, Object>> allTarget = taskMapper.queryAllTargetInfrastructure();
		modelMap.put("allTarget", allTarget);
		
		//查询全部战队数据
		List<Map<String, Object>> allTeams = taskMapper.queryAllSysCorps();
		modelMap.put("allTeams", allTeams);
		
		return modelMap;
	}
	
	
	@Override
	public void add(Long currentUserId, AdTask adTask, String targets, String teams) {
		// TODO Auto-generated method stub

		//维护任务主表
		adTask.setStatus(1);
		adTask.setCreateTime(new Date());
		adTask.setUpdateTime(new Date());
		adTask.setCreateBy(currentUserId);
		adTask.setUpdateBy(currentUserId);
		adTask.setDelFlag(false);

		taskMapper.insert(adTask);

		//维护 任务靶标表 和 任务战队表
		saveTargetAndTeams(adTask, targets, teams, currentUserId);

		//维护流程节点表
		saveProcess(adTask.getId(), adTask.getStatus(), currentUserId);

		//维护消息推送表
		List<Long> corpsIds = taskTeamsMapper.queryTeamIdsByTaskId(adTask.getId());
		messageService.addMessage(currentUserId, "任务已下发", "任务已下发", 2, corpsIds);
	}


	@Override
	public ModelMap update(HttpServletRequest request, ModelMap modelMap, AdTask adTask, String targets, String teams) {
		// TODO Auto-generated method stub
		Long currentUserId = getCurrentUserId(request);

		//修改任务主表
		AdTask task = taskMapper.selectByPrimaryKey(adTask.getId());
		if(task.getDelFlag() || task.getStatus() > 0) {
			/**
			 * 不能修改已删除数据
				不能修改已提交数据
				不能修改已结束数据
				不能修改已终止数据
			 */
        	modelMap.put("msg", 1);
        	return modelMap;
		}
		
		Request2ModelUtil.covertObj(task, adTask);
		task.setUpdateTime(new Date());
		task.setUpdateBy(currentUserId);
		taskMapper.updateByPrimaryKey(task);
		
    	//先删除任务关联的 靶标数据 和 战队数据
		taskTargetMapper.deleteTaskTarget(task.getId());
		taskTeamsMapper.deleteTaskTeams(task.getId());
		
		//再维护 任务靶标表 和 任务战队表
		saveTargetAndTeams(adTask, targets, teams, currentUserId);
		
		modelMap.put("msg", 0);
		return modelMap;
	}
	
	
	private void saveTargetAndTeams(AdTask adTask, String targets, String teams, Long currentUserId) {
		//维护任务靶标表
		String[] targetArray = targets.split(",");
		for(String targetId : targetArray) {
			AdTaskTarget adTaskTarget = new AdTaskTarget();
			adTaskTarget.setTaskId(adTask.getId());
			adTaskTarget.setTargetId(Long.valueOf(targetId));
			adTaskTarget.setCreateTime(new Date());
			adTaskTarget.setUpdateTime(new Date());
			adTaskTarget.setCreateBy(currentUserId);
			adTaskTarget.setUpdateBy(currentUserId);
			adTaskTarget.setDelFlag(false);
			taskTargetMapper.insert(adTaskTarget);
		}
		
		//维护任务战队表
		String[] teamArray = teams.split(",");
		for(String teamId : teamArray) {
			AdTaskTeams adTaskTeams = new AdTaskTeams();
			adTaskTeams.setTaskId(adTask.getId());
			adTaskTeams.setTeamId(Long.valueOf(teamId));
			//自动接受（之前版本手动接受或拒绝）
			adTaskTeams.setStatus(1);
			adTaskTeams.setCreateTime(new Date());
			adTaskTeams.setUpdateTime(new Date());
			adTaskTeams.setCreateBy(currentUserId);
			adTaskTeams.setUpdateBy(currentUserId);
			adTaskTeams.setDelFlag(false);
			taskTeamsMapper.insert(adTaskTeams);
		}
	}

	
	@Override
	public ModelMap detail(ModelMap modelMap, Long id) {
		// TODO Auto-generated method stub
		
		//查询任务数据
		AdTask adTask = taskMapper.selectByPrimaryKey(id);
		modelMap.put("adTask", adTask);
		
		//查询任务选中的靶标数据(需要联查获取靶标名称)
		List<Map<String, Object>> checkedTarget = taskTargetMapper.queryCheckedTargetName(id);
		modelMap.put("checkedTarget", checkedTarget);
		
		//查询任务选中的战队数据(需要联查获取战队名称)
		List<Map<String, Object>> checkedTeams = taskTeamsMapper.queryCheckedTeamsName(id);
		modelMap.put("checkedTeams", checkedTeams);

		//查询任务流程节点
		List<AdTaskProcess> taskProcessList = taskProcessMapper.queryByTaskId(id);
		modelMap.put("taskProcessList", taskProcessList);

		return modelMap;
	}
	
	
	@Override
	public ModelMap submitData(HttpServletRequest request, ModelMap modelMap, Long id, int status) {
		// TODO Auto-generated method stub
		Long currentUserId = getCurrentUserId(request);
		
		AdTask adTask = taskMapper.selectByPrimaryKey(id);
		if(adTask.getDelFlag() || adTask.getStatus() > 0) {
			/**
			 * 不能提交已删除数据
				不能提交已提交数据
				不能提交已结束数据
				不能提交已终止数据
			 */
        	modelMap.put("msg", 1);
        	return modelMap;
		}
		
		manipulateTaskStatus(id, status, currentUserId);
		
		//维护消息推送表
		List<Long> corpsIds = taskTeamsMapper.queryTeamIdsByTaskId(id);
		messageService.addMessage(currentUserId, "任务已下发", "任务已下发", 2, corpsIds);
		
		modelMap.put("msg", 0);
		return modelMap;
	}

	
	@Override
	public void breakData(HttpServletRequest request, Long id, int status) {
		// TODO Auto-generated method stub
		Long currentUserId = getCurrentUserId(request);
		
		//查询当前任务是否有 待终止工单（待提交、待审核、已通过、已挂起）
		List<AdWorkOrder> notOver = workOrderMapper.queryNotBreak(id);
		for (AdWorkOrder adWorkOrder : notOver) {
			//转为工单终止状态，并维护工单流程节点表
			workOrderService.manipulateWorkOrderStatus(adWorkOrder.getId(), 7, currentUserId, null);
		}
		
		manipulateTaskStatus(id, status, currentUserId);
		
		//维护消息推送表
		List<Long> corpsIds = taskTeamsMapper.queryTeamIdsByTaskId(id);
		messageService.addMessage(currentUserId, "任务已终止", "任务已终止", 2, corpsIds);
	}
	
	
	@Override
	public ModelMap overData(HttpServletRequest request, ModelMap modelMap, Long id, int status) {
		// TODO Auto-generated method stub
		Long currentUserId = getCurrentUserId(request);
		
		//查询当前任务是否有 未完成工单（已通过、已挂起）
		List<Map<String, Object>> unfinishedWorkOrder = workOrderMapper.queryUnfinished(id);
        if (null != unfinishedWorkOrder && 0 < unfinishedWorkOrder.size()) {
        	modelMap.put("msg", 1);//有未完成工单，进行相应提示
        	return modelMap;
        }

		//查询当前任务是否有 待结束工单（待提交、待审核）
		List<AdWorkOrder> notOver = workOrderMapper.queryNotOver(id);
		for (AdWorkOrder adWorkOrder : notOver) {
			//转为工单结束状态，并维护工单流程节点表
			workOrderService.manipulateWorkOrderStatus(adWorkOrder.getId(), 8, currentUserId, null);
		}
		
		manipulateTaskStatus(id, status, currentUserId);

		//维护消息推送表
		List<Long> corpsIds = taskTeamsMapper.queryTeamIdsByTaskId(id);
		messageService.addMessage(currentUserId, "任务已结束", "任务已结束", 2, corpsIds);
		
    	modelMap.put("msg", 0);
		return modelMap;
	}
	
	
	@Override
	public ModelMap acceptOrReject(HttpServletRequest request, ModelMap modelMap, Long taskId, int status) {
		// TODO Auto-generated method stub
		Long currentUserId = getCurrentUserId(request);
		Long teamId = taskMapper.queryUserCorpsId(currentUserId);
		
		AdTask adTask = taskMapper.selectByPrimaryKey(taskId);
		
		AdTaskTeams adTaskTeams = taskTeamsMapper.queryBytaskIdAndteamId(taskId, teamId);
		if(adTaskTeams.getStatus() != null || adTask.getStatus() == 2 || adTask.getStatus() == 3) {
        	/**
        	 * 不能接受或拒绝已接受或已拒绝数据
			    不能接受或拒绝已结束数据
				不能接受或拒绝已终止数据
        	 */
			modelMap.put("msg", 1);
        	return modelMap;
		}
		adTaskTeams.setStatus(status);
		adTaskTeams.setUpdateBy(currentUserId);
		adTaskTeams.setUpdateTime(new Date());
		taskTeamsMapper.updateByPrimaryKey(adTaskTeams);
		
		modelMap.put("msg", 0);
		return modelMap;
	}
	

	/**
	 * 修改任务表状态 并 维护流程节点表
	 * @param id 任务id
	 * @param status 任务状态（流程节点状态）
	 * @param currentUserId 当前用户id
	 */
	private void manipulateTaskStatus(Long id, int status, Long currentUserId) {
		//修改任务主表状态
		AdTask adTask = taskMapper.selectByPrimaryKey(id);
		adTask.setStatus(status);
		adTask.setUpdateBy(currentUserId);
		adTask.setUpdateTime(new Date());
		taskMapper.updateByPrimaryKey(adTask);
		
		//维护流程节点表
		saveProcess(id, status, currentUserId);
	}
	/**
	 * 维护流程节点表
	 * @param id 任务id
	 * @param status 流程节点状态
	 * @param currentUserId 当前用户id
	 */
	private void saveProcess(Long id, int status, Long currentUserId) {
		//维护流程节点表
        AdTaskProcess adTaskProcess = new AdTaskProcess();
        adTaskProcess.setTaskId(id);
        adTaskProcess.setPoint(status);
        adTaskProcess.setCreateBy(currentUserId);
        adTaskProcess.setCreateTime(new Date());
        adTaskProcess.setDelFlag(false);
        taskProcessMapper.insert(adTaskProcess);
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
