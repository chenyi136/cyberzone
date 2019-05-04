package com.safecode.cyberzone.service;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.dom4j.DocumentException;
import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.PageInfo;
import com.safecode.cyberzone.pojo.BugCycle;
import com.safecode.cyberzone.vo.BugCycleVo;

public interface BugCycleService {

	PageInfo<BugCycle> queryPageList(BugCycleVo bugCycleVo);

	void delete(Long currentUserId, Long id);
	
	void importBugXml(MultipartFile bugFile, int source, Long currentUserId) throws IOException, DocumentException;
}

