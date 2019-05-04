package com.safecode.cyberzone.mapper;

import com.github.pagehelper.Page;
import com.safecode.cyberzone.pojo.BugCycle;
import com.safecode.cyberzone.pojo.BugNvd;
import com.safecode.cyberzone.vo.BugNvdVo;

import java.util.List;
import java.util.Map;

public interface BugNvdMapper {
    int deleteByPrimaryKey(Long id);

    int insert(BugNvd record);

    BugNvd selectByPrimaryKey(Long id);

    List<BugNvd> selectAll();

    int updateByPrimaryKey(BugNvd record);

	void deleteByCycleId(Long id);

	Page<BugNvd> queryPageList(BugNvdVo bugNvdVo);

	BugNvdVo queryById(Long id);

	void batchInsert(List<BugNvd> bugNvdList);
}