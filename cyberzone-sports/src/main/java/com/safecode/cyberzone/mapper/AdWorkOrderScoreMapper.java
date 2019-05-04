package com.safecode.cyberzone.mapper;

import com.safecode.cyberzone.pojo.AdWorkOrderScore;
import java.util.List;

public interface AdWorkOrderScoreMapper {
    int deleteByPrimaryKey(Long id);

    int insert(AdWorkOrderScore record);

    AdWorkOrderScore selectByPrimaryKey(Long id);

    List<AdWorkOrderScore> selectAll();

    int updateByPrimaryKey(AdWorkOrderScore record);

	List<AdWorkOrderScore> queryByWorkOrderId(Long workOrderId);
}