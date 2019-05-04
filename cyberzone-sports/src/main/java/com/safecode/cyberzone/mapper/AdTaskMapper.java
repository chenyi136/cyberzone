package com.safecode.cyberzone.mapper;

import com.github.pagehelper.Page;
import com.safecode.cyberzone.pojo.AdTask;
import com.safecode.cyberzone.pojo.AdWorkOrder;

import java.util.List;
import java.util.Map;

public interface AdTaskMapper {
    int deleteByPrimaryKey(Long id);

    int insert(AdTask record);

    AdTask selectByPrimaryKey(Long id);

    List<AdTask> selectAll();

    int updateByPrimaryKey(AdTask record);

	Page<AdTask> queryPageList(Map<String, Object> params);

	Page<AdTask> queryPageAdList(Map<String, Object> params);

	List<Map<String, Object>> queryAllTargetInfrastructure();

	List<Map<String, Object>> queryAllSysCorps();

	List<AdTask> queryTeamAcceptData(Long teamId);

	Long queryUserCorpsId(Long userId);
}