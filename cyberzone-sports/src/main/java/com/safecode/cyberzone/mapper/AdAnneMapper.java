package com.safecode.cyberzone.mapper;

import com.safecode.cyberzone.pojo.AdAnne;
import java.util.List;
import java.util.Map;

public interface AdAnneMapper {
    int deleteByPrimaryKey(Long id);

    int insert(AdAnne record);

    AdAnne selectByPrimaryKey(Long id);

    List<AdAnne> selectAll();

    int updateByPrimaryKey(AdAnne record);

	List<AdAnne> queryBy(Map<String, Object> params);
}