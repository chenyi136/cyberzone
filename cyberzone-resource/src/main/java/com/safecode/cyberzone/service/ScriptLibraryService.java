package com.safecode.cyberzone.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.PageInfo;
import com.safecode.cyberzone.pojo.ScriptLibrary;
import com.safecode.cyberzone.vo.ScriptLibraryVo;

public interface ScriptLibraryService {
	
	void add(HttpServletRequest request, ScriptLibrary scriptLibrary, MultipartFile scriptFile) throws Exception;

	List<ScriptLibrary> queryAll(ScriptLibraryVo scriptLibraryVo);

	void delete(Long id, Long currentUserId);

	PageInfo<ScriptLibrary> queryPageList(ScriptLibraryVo scriptLibraryVo);

	ScriptLibrary selectById(Long id);

	void update(ScriptLibrary scriptLibrary, MultipartFile scriptFile, Long currentUserId) throws Exception;


}
