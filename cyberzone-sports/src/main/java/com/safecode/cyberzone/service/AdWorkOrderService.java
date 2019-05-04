package com.safecode.cyberzone.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.ModelMap;
import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.PageInfo;
import com.safecode.cyberzone.pojo.AdWorkOrder;
import com.safecode.cyberzone.pojo.AdWorkOrderScore;

public interface AdWorkOrderService {

	void manipulateWorkOrderStatus(Long id, int status, Long currentUserId, Integer point);

	List<Map<String, Object>> queryUnfinished(Long taskId);

	PageInfo<Map<String, Object>> queryPageList(Map<String, Object> params);
	
	ModelMap show(HttpServletRequest request, ModelMap modelMap, Long id);

	void add(HttpServletRequest request, AdWorkOrder workOrder, MultipartFile[] createFiles) throws IOException;

	void changeWorkOrderStatus(HttpServletRequest request, Long id, int status);

	void recover(HttpServletRequest request, Long id, int status);

	void complete(HttpServletRequest request, AdWorkOrder adWorkOrder, MultipartFile[] completeFiles) throws IOException;

	void archive(HttpServletRequest request, Long id, int status, String archiveAnneRemark, MultipartFile[] archiveFiles) throws IOException;

	void score(HttpServletRequest request, AdWorkOrderScore adWorkOrderScore, MultipartFile[] scoreFiles) throws IOException;

	void delete(HttpServletRequest request, Long id);

	ModelMap detail(ModelMap modelMap, Long id);

}
