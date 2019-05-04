package com.safecode.cyberzone.mapper;

import com.safecode.cyberzone.pojo.AdWorkOrderProcess;
import java.util.List;

public interface AdWorkOrderProcessMapper {
    int deleteByPrimaryKey(Long id);

    int insert(AdWorkOrderProcess record);

    AdWorkOrderProcess selectByPrimaryKey(Long id);

    List<AdWorkOrderProcess> selectAll();

    int updateByPrimaryKey(AdWorkOrderProcess record);

	List<AdWorkOrderProcess> queryByWorkOrderId(Long workOrderId);
}