package com.safecode.cyberzone.mapper;

import com.safecode.cyberzone.pojo.AdTaskProcess;
import java.util.List;

public interface AdTaskProcessMapper {
    int deleteByPrimaryKey(Long id);

    int insert(AdTaskProcess record);

    AdTaskProcess selectByPrimaryKey(Long id);

    List<AdTaskProcess> selectAll();

    int updateByPrimaryKey(AdTaskProcess record);

	List<AdTaskProcess> queryByTaskId(Long taskId);
}