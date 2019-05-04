package com.safecode.cyberzone.mapper;

import java.util.List;
import java.util.Map;

import com.safecode.cyberzone.pojo.TargetRisk;

public interface TargetRiskMapper {
    int deleteByPrimaryKey(Long id);

    int insert(TargetRisk record);

    TargetRisk selectByPrimaryKey(Long id);

    List<TargetRisk> selectAll();

    int updateByPrimaryKey(TargetRisk record);
    
    List<TargetRisk> queryAll();

	void deleteByIds(List ids);

	void deleteTargetRiskByTargetIds(List id);

	List<TargetRisk> selectTargetRisksByTargetId(Long id);

	List<TargetRisk> selectRiskByTargetIdAndClassify(Map<String, Object> params);

	List<Map<String,Object>> getRiskCount();

	List<Map<String, Object>> getHotKey();
}