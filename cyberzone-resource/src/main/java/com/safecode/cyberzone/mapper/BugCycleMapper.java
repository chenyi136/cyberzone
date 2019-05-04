package com.safecode.cyberzone.mapper;

import com.github.pagehelper.Page;
import com.safecode.cyberzone.pojo.BugCycle;
import com.safecode.cyberzone.vo.BugCycleVo;

import java.util.List;
import java.util.Map;

public interface BugCycleMapper {
    int deleteByPrimaryKey(Long id);

    int insert(BugCycle record);

    BugCycle selectByPrimaryKey(Long id);

    List<BugCycle> selectAll();

    int updateByPrimaryKey(BugCycle record);

	Page<BugCycle> queryPageList(BugCycleVo bugCycleVo);
}