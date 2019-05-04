package com.safecode.cyberzone.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.safecode.cyberzone.base.pojo.FileInfo;
import com.safecode.cyberzone.base.service.SessionProvider;
import com.safecode.cyberzone.base.utils.DataUtil;
import com.safecode.cyberzone.base.utils.Request2ModelUtil;
import com.safecode.cyberzone.base.utils.TicketUtil;
import com.safecode.cyberzone.mapper.AdAnneMapper;
import com.safecode.cyberzone.mapper.AdTaskMapper;
import com.safecode.cyberzone.mapper.AdTaskTargetMapper;
import com.safecode.cyberzone.mapper.AdWorkOrderMapper;
import com.safecode.cyberzone.mapper.AdWorkOrderProcessMapper;
import com.safecode.cyberzone.mapper.AdWorkOrderScoreMapper;
import com.safecode.cyberzone.pojo.AdAnne;
import com.safecode.cyberzone.pojo.AdTask;
import com.safecode.cyberzone.pojo.AdTaskProcess;
import com.safecode.cyberzone.pojo.AdWorkOrder;
import com.safecode.cyberzone.pojo.AdWorkOrderProcess;
import com.safecode.cyberzone.pojo.AdWorkOrderScore;
import com.safecode.cyberzone.service.AdMessageService;
import com.safecode.cyberzone.service.AdTaskService;
import com.safecode.cyberzone.service.AdWorkOrderService;
import com.safecode.cyberzone.utils.FileUtil;


@Service
@Transactional
public class AdWorkOrderServiceImpl implements AdWorkOrderService {
	
    @Autowired
    private AdWorkOrderMapper workOrderMapper;
    @Autowired
    private AdWorkOrderProcessMapper workOrderProcessMapper;
    @Autowired
    private AdWorkOrderScoreMapper workOrderScoreMapper;
    @Autowired
    private AdAnneMapper adAnneMapper;
    @Autowired
    private AdMessageService messageService;
    @Autowired
    private AdTaskService adTaskService;        
    @Autowired
    private AdTaskMapper taskMapper;
    @Autowired
    private AdTaskTargetMapper taskTargetMapper;
	@Autowired
	private SessionProvider sessionProvider;
    
    @Override
    public PageInfo<Map<String, Object>> queryPageList(Map<String, Object> params) {
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
		Page<Map<String, Object>> page = workOrderMapper.queryPageList(params);
		
		return new PageInfo<Map<String, Object>>(page);	
	}
    
    
    @Override
    public void delete(HttpServletRequest request, Long id) {
    	// TODO Auto-generated method stub
    	Long currentUserId = getCurrentUserId(request);

		AdWorkOrder workOrder = workOrderMapper.selectByPrimaryKey(id);

		workOrder.setUpdateBy(currentUserId);
		workOrder.setUpdateTime(new Date());
		workOrder.setDelFlag(true);
		workOrderMapper.updateByPrimaryKey(workOrder);
    }
    
    
    @Override
    public ModelMap show(HttpServletRequest request, ModelMap modelMap, Long id) {
    	// TODO Auto-generated method stub
    	Long currentUserId = getCurrentUserId(request);
    	Long teamId = taskMapper.queryUserCorpsId(currentUserId);
		
		//兼容 添加回显 和 修改回显 共同一个接口
		if(id != null) {//代表修改回显
			
			//查询工单数据
			AdWorkOrder workOrder = workOrderMapper.selectByPrimaryKey(id);
			modelMap.put("workOrder", workOrder);

			//查询工单创建附件
			Map<String, Object> params = new HashMap<>();
			params.put("adId", id);
			params.put("source", 1);//附件来源  1：工单创建；
			List<AdAnne> anneList = adAnneMapper.queryBy(params);
			modelMap.put("anneList", anneList);

			//查询工单选中的任务 任务所关联的靶标数据
			List<Map<String, Object>> checkedTarget = taskTargetMapper.queryCheckedTargetName(workOrder.getTaskId());
			modelMap.put("checkedTarget", checkedTarget);
		}
		
		//查询对应自己战队的，并且战队已接受，并且任务状态=已提交 的任务数据
		List<AdTask> teamAcceptTask = taskMapper.queryTeamAcceptData(teamId);
		modelMap.put("teamAcceptTask", teamAcceptTask);
		
    	return modelMap;
    }
    
    
    @Override
    public void add(HttpServletRequest request, AdWorkOrder workOrder, MultipartFile[] createFiles) throws IOException {
    	// TODO Auto-generated method stub
    	Long currentUserId = getCurrentUserId(request);
    	Long teamId = taskMapper.queryUserCorpsId(currentUserId);

		//维护工单表
		workOrder.setTeamId(teamId);
		workOrder.setStatus(0);
		workOrder.setCreateTime(new Date());
		workOrder.setUpdateTime(new Date());
		workOrder.setCreateBy(currentUserId);
		workOrder.setUpdateBy(currentUserId);
		workOrder.setDelFlag(false);
        
		workOrderMapper.insert(workOrder);
		
		//维护攻防附件表
		saveAdFiles(createFiles, workOrder.getId(), 1, currentUserId, null);
    }
    
    
    @Override
    public ModelMap detail(ModelMap modelMap, Long id) {
    	// TODO Auto-generated method stub
    	
    	//查询工单数据
		AdWorkOrder workOrder = workOrderMapper.selectByPrimaryKey(id);
		modelMap.put("workOrder", workOrder);

    	//查询工单评分数据
		List<AdWorkOrderScore> workOrderScoreList = workOrderScoreMapper.queryByWorkOrderId(id);
		modelMap.put("workOrderScoreList", workOrderScoreList);
		
		//查询工单附件
		Map<String, Object> params = new HashMap<>();
		params.put("adId", id);
		params.put("source", "1,2,3,4");//附件来源   1：工单创建；2：工单完成；3：工单归档；4：工单评分；
		List<AdAnne> anneList = adAnneMapper.queryBy(params);
		modelMap.put("anneList", anneList);

		//查询工单流程节点
		List<AdWorkOrderProcess> workOrderProcessList = workOrderProcessMapper.queryByWorkOrderId(id);
		modelMap.put("workOrderProcessList", workOrderProcessList);
		
    	return modelMap;
    }


    @Override
    public void changeWorkOrderStatus(HttpServletRequest request, Long id, int status) {
    	// TODO Auto-generated method stub
    	Long currentUserId = getCurrentUserId(request);
		
		manipulateWorkOrderStatus(id, status, currentUserId, null);
		
		//维护消息推送表
		if(status != 1) {
			AdWorkOrder workOrder = workOrderMapper.selectByPrimaryKey(id);
			List<Long> corpsIds = new ArrayList<Long>();
			corpsIds.add(workOrder.getTeamId());
			
			if(status == 2) {
				messageService.addMessage(currentUserId, "工单审核通过", "工单审核通过", 3, corpsIds);
			} else if(status == 4) {
				messageService.addMessage(currentUserId, "工单已挂起", "工单已挂起", 3, corpsIds);
			} else if(status == 6) {
				messageService.addMessage(currentUserId, "工单审核驳回", "工单审核驳回", 3, corpsIds);
			}
		}
		
    }
    
    
    @Override
    public void complete(HttpServletRequest request, AdWorkOrder adWorkOrder, MultipartFile[] completeFiles) throws IOException {
    	// TODO Auto-generated method stub
    	Long currentUserId = getCurrentUserId(request);

		AdWorkOrder workOrder = workOrderMapper.selectByPrimaryKey(adWorkOrder.getId());
		Request2ModelUtil.covertObj(workOrder, adWorkOrder);
		
		workOrder.setUpdateBy(currentUserId);
		workOrder.setUpdateTime(new Date());
		workOrderMapper.updateByPrimaryKey(workOrder);
		
		//维护流程节点表
		createProcess(workOrder.getId(), workOrder.getStatus(), currentUserId, null);
		
		//维护攻防附件表
		saveAdFiles(completeFiles, workOrder.getId(), 2, currentUserId, null);
    }
    
    
    @Override
    public void archive(HttpServletRequest request, Long id, int status, String archiveAnneRemark, MultipartFile[] archiveFiles) throws IOException {
    	// TODO Auto-generated method stub
    	Long currentUserId = getCurrentUserId(request);
		
		//修改工单主表状态
		AdWorkOrder workOrder = workOrderMapper.selectByPrimaryKey(id);
		workOrder.setStatus(status);
		workOrder.setArchiveAnneRemark(archiveAnneRemark);
		workOrder.setUpdateBy(currentUserId);
		workOrder.setUpdateTime(new Date());
		workOrderMapper.updateByPrimaryKey(workOrder);
		
		//维护流程节点表
		createProcess(id, status, currentUserId, null);
		
		//维护攻防附件表
		saveAdFiles(archiveFiles, workOrder.getId(), 3, currentUserId, null);

    }
    
	
	@Override
	public void recover(HttpServletRequest request, Long id, int status) {
		// TODO Auto-generated method stub
		Long currentUserId = getCurrentUserId(request);
		
		manipulateWorkOrderStatus(id, status, currentUserId, 9);
		
		//维护消息推送表
		AdWorkOrder workOrder = workOrderMapper.selectByPrimaryKey(id);
		List<Long> corpsIds = new ArrayList<Long>();
		corpsIds.add(workOrder.getTeamId());
		messageService.addMessage(currentUserId, "工单已恢复", "工单已恢复", 3, corpsIds);
	}
	
	
	@Override
	public void score(HttpServletRequest request, AdWorkOrderScore adWorkOrderScore, MultipartFile[] scoreFiles) throws IOException {
		// TODO Auto-generated method stub
		Long currentUserId = getCurrentUserId(request);

		//维护评分表
		adWorkOrderScore.setCreateTime(new Date());
		adWorkOrderScore.setCreateBy(currentUserId);
		adWorkOrderScore.setDelFlag(false);
        
		workOrderScoreMapper.insert(adWorkOrderScore);
		
		//维护攻防附件表
		saveAdFiles(scoreFiles, adWorkOrderScore.getWorkOrderId(), 4, currentUserId, adWorkOrderScore.getId());
	}
	
	
	@Override
	public List<Map<String, Object>> queryUnfinished(Long taskId) {
		// TODO Auto-generated method stub
		return workOrderMapper.queryUnfinished(taskId);
	}
    

	/**
	 * 修改工单表状态 并 维护流程节点表
	 * @param id 工单id
	 * @param status 工单状态
	 * @param currentUserId 当前用户id
	 * @param point 流程节点（日前只用于恢复，其它情况下传null）
	 */
	public void manipulateWorkOrderStatus(Long id, int status, Long currentUserId, Integer point) {
		//修改工单主表状态
		AdWorkOrder workOrder = workOrderMapper.selectByPrimaryKey(id);
		workOrder.setStatus(status);
		workOrder.setUpdateBy(currentUserId);
		workOrder.setUpdateTime(new Date());
		workOrderMapper.updateByPrimaryKey(workOrder);
		
		createProcess(id, status, currentUserId, point);
	}
	
	private void createProcess(Long id, int status, Long currentUserId, Integer point) {
		//维护流程节点表
		AdWorkOrderProcess workOrderProcess = new AdWorkOrderProcess();
		workOrderProcess.setWorkOrderId(id);
		if(point != null) {
			workOrderProcess.setPoint(point);
		} else {
			workOrderProcess.setPoint(status);
		}
		workOrderProcess.setCreateBy(currentUserId);
		workOrderProcess.setCreateTime(new Date());
		workOrderProcess.setDelFlag(false);
		workOrderProcessMapper.insert(workOrderProcess);
	}
	
	
	/**
	 * 保存攻防附件
	 * @param adFiles
	 * @param adId
	 * @param source
	 * @param currentUserId
	 * @param scoreId		评分id（日前只有工单评分时维护，其它情况下传null）
	 * @throws IOException
	 */
	private void saveAdFiles(MultipartFile[] adFiles, Long adId, int source, Long currentUserId, Long scoreId) throws IOException {
		if(adFiles != null) {
			for (MultipartFile adFile : adFiles) {
				if (adFile != null && !adFile.isEmpty() && StringUtils.isNotEmpty(adFile.getOriginalFilename())) {
					FileInfo fileInfo = FileUtil.saveFile(adFile, FileUtil.UPLOADPATH_AD);
					
					//维护攻防附件表
					AdAnne anne = new AdAnne();
					Request2ModelUtil.covertObj(anne, fileInfo);
					
					anne.setAdId(adId);
					if(scoreId != null) {
						anne.setScoreId(scoreId);
					}
					anne.setSource(source);
					anne.setCreateTime(new Date());
					anne.setUpdateTime(new Date());
					anne.setCreateBy(currentUserId);
					anne.setUpdateBy(currentUserId);
					anne.setDelFlag(false);
					
					adAnneMapper.insert(anne);
				}
			}
		}
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
