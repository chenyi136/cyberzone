package com.safecode.cyberzone.mapper;

import com.github.pagehelper.Page;
import com.safecode.cyberzone.pojo.TargetInfrastructure;
import com.safecode.cyberzone.vo.TargetInfrastructureVo;

import java.util.List;
import java.util.Map;

public interface TargetInfrastructureMapper {
    int deleteByPrimaryKey(Long id);

    int insert(TargetInfrastructure record);

    TargetInfrastructure selectByPrimaryKey(long id);

    List<TargetInfrastructure> selectAll();

    int updateByPrimaryKey(TargetInfrastructure record);

	Page<TargetInfrastructure> queryPageList(TargetInfrastructureVo targetVo);

	void updateById(TargetInfrastructure targetInfrastructure);
	
	List<TargetInfrastructure> queryAll();

	int deleteByIds(List id);
}