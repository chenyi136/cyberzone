package com.safecode.cyberzone.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.github.pagehelper.Page;
import com.safecode.cyberzone.pojo.AdWorkOrder;
import com.safecode.cyberzone.pojo.Loopholes;
import com.safecode.cyberzone.pojo.SysCorps;
import com.safecode.cyberzone.vo.LoopholesVO;

public interface LoopholesMapper {
	// 新增漏洞申报表
	public void addLoopholes(Loopholes loopholes);

	public void updateLoopholesByUuid(Loopholes loopholes);
	
	public void updateLoopholesStautsByUuid(Loopholes loopholes);

	// 根据战队id查询工单
	public List<AdWorkOrder> queryWorkIdByTeamId(@Param("teamId") Integer teamId);

	// 根据用户id获取用户对应的战队id
	public Integer queryTeamIdByUserId(@Param("userId") Integer userId);

	// 成果提交列表
	public Page<LoopholesVO> queryLoopholesByPage(LoopholesVO loopholesVO);

	public LoopholesVO queryLoopholesByUuid(@Param("uuid") Integer uuid);

	// 指挥方漏洞管理 status(2,3,4,5)
	public Page<LoopholesVO> queryCommandLoopholesByPage(LoopholesVO loopholesVO);

	// 获取所有战队信息
	public List<SysCorps> queryAllSysCorps();

	// 显示所有个战队得分
	public List<LoopholesVO> queryCorpsScore();

	// 战队攻击成果展示
	public List<LoopholesVO> queryTeamLoopholesByTeamId(@Param("teamId") Integer teamId);

	// 导出漏洞信息
	public List<LoopholesVO> exportLoopholes(LoopholesVO loopholesVO);

	public void delLoopholesByUuid(@Param("uuid") Integer uuid);

}
