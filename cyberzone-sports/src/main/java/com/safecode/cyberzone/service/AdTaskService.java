package com.safecode.cyberzone.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.ModelMap;

import com.github.pagehelper.PageInfo;
import com.safecode.cyberzone.pojo.AdTask;

public interface AdTaskService {

	PageInfo<AdTask> queryPageList(Map<String, Object> params);

	PageInfo<AdTask> queryPageAdList(Map<String, Object> params);

	ModelMap delete(HttpServletRequest request, ModelMap modelMap, Long id);
	
	ModelMap show(ModelMap modelMap, Long id);

	void add(Long currentUserId, AdTask adTask, String targets, String teams);

	ModelMap update(HttpServletRequest request, ModelMap modelMap, AdTask adTask, String targets, String teams);

	ModelMap detail(ModelMap modelMap, Long id);

	ModelMap submitData(HttpServletRequest request, ModelMap modelMap, Long id, int status);

	void breakData(HttpServletRequest request, Long id, int status);

	ModelMap overData(HttpServletRequest request, ModelMap modelMap, Long id, int status);

	ModelMap acceptOrReject(HttpServletRequest request, ModelMap modelMap, Long id, int status);

}
