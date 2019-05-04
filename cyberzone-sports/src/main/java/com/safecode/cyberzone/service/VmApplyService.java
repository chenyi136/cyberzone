package com.safecode.cyberzone.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.ModelMap;

import com.github.pagehelper.PageInfo;
import com.safecode.cyberzone.pojo.VmApply;

public interface VmApplyService {

	PageInfo<VmApply> queryPageList(Map<String, Object> params);

	void delete(HttpServletRequest request, Long id);

	void submitData(HttpServletRequest request, Long id, int status, String remark);

	void add(HttpServletRequest request, VmApply vmApply);

	ModelMap detail(ModelMap modelMap, Long id);

	void update(HttpServletRequest request, VmApply vmApply);
	
	String pass(HttpServletRequest request, Long id);

	void otherPass(HttpServletRequest request, VmApply apply);

}
