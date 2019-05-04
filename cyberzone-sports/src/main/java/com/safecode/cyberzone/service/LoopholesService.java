package com.safecode.cyberzone.service;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.safecode.cyberzone.pojo.AdWorkOrder;
import com.safecode.cyberzone.pojo.Loopholes;
import com.safecode.cyberzone.pojo.SysCorps;
import com.safecode.cyberzone.vo.LoopholesVO;

public interface LoopholesService {
	// 新增漏洞申报表
	public void addLoopholes(Loopholes loopholes);

	public void updateLoopholesByUuid(Loopholes loopholes);

	public void updateLoopholesStautsByUuid(Loopholes loopholes);

	// 根据战队id查询工单
	public List<AdWorkOrder> queryWorkIdByTeamId(Integer teamId);

	// 根据用户id获取用户对应的战队id
	public Integer queryTeamIdByUserId(Integer userId);

	public PageInfo<LoopholesVO> queryLoopholesByPage(LoopholesVO loopholesVO, Integer pageNum, Integer pageSize);

	public LoopholesVO queryLoopholesByUuid(Integer uuid);

	public void delLoopholesByUuid(Integer uuid);

	// 指挥方漏洞管理
	public PageInfo<LoopholesVO> queryCommandLoopholesByPage(LoopholesVO loopholesVO, Integer pageNum,
			Integer pageSize);

	// 获取所有战队信息
	public List<SysCorps> queryAllSysCorps();

	// 显示所有个战队得分
	public List<LoopholesVO> queryCorpsScore();

	// 战队攻击成果展示
	public List<LoopholesVO> queryTeamLoopholesByTeamId(Integer teamId);

	// 指挥方漏洞管理 导出
	public List<LoopholesVO> exportLoopholes(LoopholesVO loopholesVO);

}
