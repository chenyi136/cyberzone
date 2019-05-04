package com.safecode.cyberzone.mapper;

import com.safecode.cyberzone.pojo.AdTaskTarget;
import java.util.List;
import java.util.Map;

public interface AdTaskTargetMapper {
    int deleteByPrimaryKey(Long id);

    int insert(AdTaskTarget record);

    AdTaskTarget selectByPrimaryKey(Long id);

    List<AdTaskTarget> selectAll();

    int updateByPrimaryKey(AdTaskTarget record);

	List<AdTaskTarget> queryBy(Map<String, Object> params);

	void deleteTaskTarget(Long taskId);

	List<Map<String, Object>> queryCheckedTargetName(Long taskId);
}