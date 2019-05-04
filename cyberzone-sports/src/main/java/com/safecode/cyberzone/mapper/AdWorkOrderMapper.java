package com.safecode.cyberzone.mapper;

import java.util.List;
import java.util.Map;

import com.github.pagehelper.Page;
import com.safecode.cyberzone.pojo.AdWorkOrder;

public interface AdWorkOrderMapper {
    int deleteByPrimaryKey(Long id);

    int insert(AdWorkOrder record);

    AdWorkOrder selectByPrimaryKey(Long id);

    List<AdWorkOrder> selectAll();

    int updateByPrimaryKey(AdWorkOrder record);
    
    Page<Map<String, Object>> queryPageList(Map<String, Object> params);

    List<Map<String, Object>> queryUnfinished(Long taskId);

	List<AdWorkOrder> queryNotOver(Long taskId);

	List<AdWorkOrder> queryNotBreak(Long taskId);

}