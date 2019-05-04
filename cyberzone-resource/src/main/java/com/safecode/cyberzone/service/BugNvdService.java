package com.safecode.cyberzone.service;

import com.github.pagehelper.PageInfo;
import com.safecode.cyberzone.pojo.BugNvd;
import com.safecode.cyberzone.vo.BugNvdVo;

public interface BugNvdService {

	PageInfo<BugNvd> queryPageList(BugNvdVo bugNvdVo);

	BugNvdVo queryById(Long id);

}
