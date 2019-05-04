package com.safecode.cyberzone.service;

import java.util.List;
import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.safecode.cyberzone.pojo.BugCnvd;
import com.safecode.cyberzone.vo.BugCnvdVo;

public interface BugCnvdService {

	PageInfo<BugCnvd> queryPageList(BugCnvdVo cnvdVo);

	BugCnvdVo queryById(Long id);

	List<Map<String, Object>> getBugCountByServerity();

	List<Map<String, Object>> getBugCountByYear();

	List<BugCnvd> getKeyNews();

	List<Map<String, Object>> getBugCountByClassify();

}
