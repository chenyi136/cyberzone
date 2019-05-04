package com.safecode.cyberzone.mapper;

import com.safecode.cyberzone.pojo.CommonProcess;
import java.util.List;
import java.util.Map;

public interface CommonProcessMapper {
    int deleteByPrimaryKey(Long id);

    int insert(CommonProcess record);

    CommonProcess selectByPrimaryKey(Long id);

    List<CommonProcess> selectAll();

    int updateByPrimaryKey(CommonProcess record);

	List<CommonProcess> queryBy(Map<String, Object> params);
}