package com.safecode.cyberzone.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.safecode.cyberzone.mapper.LoopholesMapper;
import com.safecode.cyberzone.pojo.AdWorkOrder;
import com.safecode.cyberzone.pojo.Loopholes;
import com.safecode.cyberzone.pojo.SysCorps;
import com.safecode.cyberzone.service.LoopholesService;
import com.safecode.cyberzone.vo.LoopholesVO;

@Service("loopholesService")
@Transactional
public class LoopholesServiceImpl implements LoopholesService {
	@Autowired
	private LoopholesMapper loopholesMapper;

	@Override
	public void addLoopholes(Loopholes loopholes) {
		loopholes.setStatus(1);
		loopholes.setCreatedAt(new Date());
		loopholesMapper.addLoopholes(loopholes);
	}

	@Override
	public void updateLoopholesByUuid(Loopholes loopholes) {
		loopholes.setUpdatedAt(new Date());
		loopholesMapper.updateLoopholesByUuid(loopholes);
	}

	// 1待提交;2:待评分;3评分中;4已评分;5已驳回;6删除
	public void updateLoopholesStautsByUuid(Loopholes loopholes) {
		Integer status = loopholes.getStatus();
		Integer dbStatus = loopholesMapper.queryLoopholesByUuid(loopholes.getUuid()).getStatus();
		
//		1待提交
		if (dbStatus == 1 && (status == 2 || status == 6)) {
			loopholesMapper.updateLoopholesStautsByUuid(loopholes);
			return ;
		}
//		2:待评分
		if(dbStatus ==2 && (status ==1 || status ==3 || status == 6)){
			loopholesMapper.updateLoopholesStautsByUuid(loopholes);
			return ;
		}
//		3评分中
		if(dbStatus ==3 && (status == 4 || status == 5)){
			loopholesMapper.updateLoopholesStautsByUuid(loopholes);
			return ;
		}
//		5已驳回
		if(dbStatus == 5 && (status == 2 || status == 6)){
			loopholesMapper.updateLoopholesStautsByUuid(loopholes);
			return ;
		}
	}

	@Override
	public List<AdWorkOrder> queryWorkIdByTeamId(Integer teamId) {
		List<AdWorkOrder> list = loopholesMapper.queryWorkIdByTeamId(teamId);
		return list;
	}

	@Override
	public Integer queryTeamIdByUserId(Integer userId) {
		Integer teamId = loopholesMapper.queryTeamIdByUserId(userId);
		return teamId;
	}

	@Override
	public PageInfo<LoopholesVO> queryLoopholesByPage(LoopholesVO loopholesVO, Integer pageNum, Integer pageSize) {
//		String order = loopholesVO.getOrder() == null ? "DESC" :loopholesVO.getOrder();
//		PageHelper.startPage(pageNum, pageSize ,"created_at " + order);
		PageHelper.startPage(pageNum, pageSize);
		Page<LoopholesVO> page = loopholesMapper.queryLoopholesByPage(loopholesVO);
		return new PageInfo<LoopholesVO>(page);
	}

	@Override
	public LoopholesVO queryLoopholesByUuid(Integer uuid) {
		LoopholesVO loopholesSysUser = loopholesMapper.queryLoopholesByUuid(uuid);
		return loopholesSysUser;
	}

	@Override
	public void delLoopholesByUuid(Integer uuid) {
		loopholesMapper.delLoopholesByUuid(uuid);
	}

	@Override
	public PageInfo<LoopholesVO> queryCommandLoopholesByPage(LoopholesVO loopholesVO, Integer pageNum,
			Integer pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		Page<LoopholesVO> page = loopholesMapper.queryCommandLoopholesByPage(loopholesVO);
		return new PageInfo<LoopholesVO>(page);
	}

	@Override
	public List<SysCorps> queryAllSysCorps() {
		List<SysCorps> list = loopholesMapper.queryAllSysCorps();
		return list;
	}

	@Override
	public List<LoopholesVO> queryCorpsScore() {
		List<LoopholesVO> list = loopholesMapper.queryCorpsScore();
		return list;
	}

	@Override
	public List<LoopholesVO> queryTeamLoopholesByTeamId(Integer teamId) {
		List<LoopholesVO> list = loopholesMapper.queryTeamLoopholesByTeamId(teamId);
		return list;

	}

	@Override
	public List<LoopholesVO> exportLoopholes(LoopholesVO loopholesVO) {
		List<LoopholesVO> list = loopholesMapper.exportLoopholes(loopholesVO);
		return list;
	}

}
