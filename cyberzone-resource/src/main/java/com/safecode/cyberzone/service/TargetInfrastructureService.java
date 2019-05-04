package com.safecode.cyberzone.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.PageInfo;
import com.safecode.cyberzone.pojo.TargetInfrastructure;
import com.safecode.cyberzone.pojo.TargetRisk;
import com.safecode.cyberzone.vo.TargetInfrastructureVo;

public interface TargetInfrastructureService {

	PageInfo<TargetInfrastructure> queryPageList(TargetInfrastructureVo targetInfrastructureVo);
	int delete(List id);
	void add(HttpServletRequest request, TargetInfrastructure targetInfrastructure, MultipartFile targetFile, MultipartFile vncFile) throws IOException;
	TargetInfrastructure selectById(long id);
	void update(HttpServletRequest request, TargetInfrastructure targetInfrastructure, MultipartFile targetFile, MultipartFile vncFile) throws IOException;
	List<TargetInfrastructure> queryAll();
	List<TargetRisk> selectTargetRiskById(Long id);
	void insertTargetRisk(HttpServletRequest request, List<TargetRisk> targetRiskList, Long targetId);
	void deleteTargetRisk(List ids);
	void deleteTargetRiskByTargetIds(List id);
	List<TargetRisk> selectRiskByTargetIdAndClassify(Map<String, Object> params);
	List<Map<String,Object>> getRiskCount();
	List<Map<String, Object>> getHotKey();
	
}
