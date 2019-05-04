package com.safecode.cyberzone.mapper;

import com.github.pagehelper.Page;
import com.safecode.cyberzone.pojo.BugCnvd;
import com.safecode.cyberzone.vo.BugCnvdVo;

import java.util.List;
import java.util.Map;

public interface BugCnvdMapper {
    int deleteByPrimaryKey(Long id);

    int insert(BugCnvd record);

    BugCnvd selectByPrimaryKey(Long id);

    List<BugCnvd> selectAll();

    int updateByPrimaryKey(BugCnvd record);

	void deleteByCycleId(Long id);

	Page<BugCnvd> queryPageList(BugCnvdVo cnvdVo);

	BugCnvdVo queryById(Long id);

	void batchInsert(List<BugCnvd> bugCnvdList);

	List<Map<String, Object>> getBugCountByServerity();

	List<Map<String, Object>> getBugCountByYear();

	List<BugCnvd> getKeyNews();

	List<Map<String, Object>> getBugCountByClassify();
}