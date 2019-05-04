package com.safecode.cyberzone.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.PageInfo;
import com.safecode.cyberzone.pojo.StrategyLibrary;
import com.safecode.cyberzone.vo.StrategyLibraryVo;

public interface StrategyLibraryService {

	void add(HttpServletRequest request, StrategyLibrary strategyLibrary, MultipartFile strategyFile) throws Exception;

	List<StrategyLibrary> queryAll(StrategyLibraryVo strategyLibraryVo);

	void delete(Long id, HttpServletRequest request);

	PageInfo<StrategyLibrary> queryPageList(StrategyLibraryVo strategyLibraryVo);

	StrategyLibrary selectById(Long id);

	void update(HttpServletRequest request, StrategyLibrary strategyLibrary, MultipartFile strategyFile, Long currentUserId) throws Exception;


}
