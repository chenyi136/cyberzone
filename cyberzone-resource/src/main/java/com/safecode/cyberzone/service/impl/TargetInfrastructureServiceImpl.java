package com.safecode.cyberzone.service.impl;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.safecode.cyberzone.base.pojo.FileInfo;
import com.safecode.cyberzone.base.service.SessionProvider;
import com.safecode.cyberzone.base.utils.DataUtil;
import com.safecode.cyberzone.base.utils.Request2ModelUtil;
import com.safecode.cyberzone.base.utils.TicketUtil;
import com.safecode.cyberzone.mapper.TargetInfrastructureMapper;
import com.safecode.cyberzone.mapper.TargetRiskMapper;
import com.safecode.cyberzone.pojo.TargetInfrastructure;
import com.safecode.cyberzone.pojo.TargetRisk;
import com.safecode.cyberzone.service.TargetInfrastructureService;
import com.safecode.cyberzone.utils.FileUtil;
import com.safecode.cyberzone.vo.TargetInfrastructureVo;


@Service
@Transactional
public class TargetInfrastructureServiceImpl implements TargetInfrastructureService {

	@Autowired
	private TargetInfrastructureMapper targetInfrastructureMapper;
	@Autowired
	private TargetRiskMapper targetRiskMapper;
	@Autowired
	private SessionProvider sessionProvider;
	
	@Override
	/** 关键基础设施靶标列表查询(分页) */
	public PageInfo<TargetInfrastructure> queryPageList(TargetInfrastructureVo targetVo) {
		Map<Object, Object> params = new HashMap<>();
		if (targetVo == null || DataUtil.isEmpty(targetVo.getPageNum())) {
			params.put("pageNum", 1);
		}else{
			params.put("pageNum", targetVo.getPageNum());
		}
		if (targetVo == null || DataUtil.isEmpty(targetVo.getPageSize())) {
			params.put("pageSize", 10);
		}else{
			params.put("pageSize", targetVo.getPageSize());
		}
		PageHelper.startPage(params);
		Page<TargetInfrastructure> page = targetInfrastructureMapper.queryPageList(targetVo);
		return new PageInfo<TargetInfrastructure>(page);
	}

	@Override
	/** 关键基础设施靶标删除 */
	public int delete(List id) {
		return targetInfrastructureMapper.deleteByIds(id);
	}

	@Override
	/** 添加关键基础设施靶标 */
	public void add(HttpServletRequest request, TargetInfrastructure targetInfrastructure, MultipartFile targetFile, MultipartFile vncFile) throws IOException {
		if (targetFile != null && !targetFile.isEmpty() && StringUtils.isNotEmpty(targetFile.getOriginalFilename())){
			FileInfo fileInfo = FileUtil.saveFile(targetFile, FileUtil.UPLOADPATH_TARGETINFRASTRUCTURE);
			Request2ModelUtil.covertObj(targetInfrastructure, fileInfo);
		}
		if (vncFile != null && !vncFile.isEmpty() && StringUtils.isNotEmpty(vncFile.getOriginalFilename())){
			FileInfo vncFileInfo = FileUtil.saveFile(vncFile, FileUtil.UPLOADPATH_TARGETINFRASTRUCTURE);
			targetInfrastructure.setVncFileName(vncFileInfo.getFileName());
			targetInfrastructure.setVncFilePath(vncFileInfo.getFilePath());
			targetInfrastructure.setVncFileSize(vncFileInfo.getFileSize());
			targetInfrastructure.setVncFileSuffix(vncFileInfo.getFileSuffix());
			targetInfrastructure.setVncName(vncFileInfo.getName());
			targetInfrastructure.setVncRealName(vncFileInfo.getRealName());
		}
		
		targetInfrastructure.setCreateTime(new Date());
		targetInfrastructure.setUpdateTime(new Date());
		targetInfrastructure.setDelFlag(false);
		
		//获取当前用户id
		Long userId = sessionProvider.getAttributeForSysUser(TicketUtil.getTOKEN(request, null)).getId();
		targetInfrastructure.setCreateBy(userId);
		targetInfrastructure.setUpdateBy(userId);
		
		targetInfrastructureMapper.insert(targetInfrastructure);
	}

	@Override
	/** 数据回显(修改基础设施靶标第一步) */
	public TargetInfrastructure selectById(long id) {
		TargetInfrastructure targetInfrastructure = targetInfrastructureMapper.selectByPrimaryKey(id);
		return targetInfrastructure;
	}

	@Override
	/** 保存(修改基础设施靶标第二步) */
	public void update(HttpServletRequest request, TargetInfrastructure targetInfrastructure, MultipartFile targetFile, MultipartFile vncFile) throws IOException {
		if (targetFile != null && !targetFile.isEmpty() && StringUtils.isNotEmpty(targetFile.getOriginalFilename())){
			FileInfo fileInfo = FileUtil.saveFile(targetFile, FileUtil.UPLOADPATH_TARGETINFRASTRUCTURE);
			Request2ModelUtil.covertObj(targetInfrastructure, fileInfo);
		}
		if (vncFile != null && !vncFile.isEmpty() && StringUtils.isNotEmpty(vncFile.getOriginalFilename())){
			FileInfo vncFileInfo = FileUtil.saveFile(vncFile, FileUtil.UPLOADPATH_TARGETINFRASTRUCTURE);
			targetInfrastructure.setVncFileName(vncFileInfo.getFileName());
			targetInfrastructure.setVncFilePath(vncFileInfo.getFilePath());
			targetInfrastructure.setVncFileSize(vncFileInfo.getFileSize());
			targetInfrastructure.setVncFileSuffix(vncFileInfo.getFileSuffix());
			targetInfrastructure.setVncName(vncFileInfo.getName());
			targetInfrastructure.setVncRealName(vncFileInfo.getRealName());
		}
		targetInfrastructure.setUpdateTime(new Date());
		
		//////////////////获取当前用户id////////////////////
		Long userId = sessionProvider.getAttributeForSysUser(TicketUtil.getTOKEN(request, null)).getId();
		targetInfrastructure.setCreateBy(userId);
		targetInfrastructure.setUpdateBy(userId);
		
		targetInfrastructureMapper.updateById(targetInfrastructure);
	}

	@Override
	/** 查询基础设施靶标(全查)*/
	public List<TargetInfrastructure> queryAll() {
		List<TargetInfrastructure> targetList = targetInfrastructureMapper.queryAll();
		return targetList;
	}

	@Override
	/** 根据靶标id查询对应的靶标风险列表  */
	public List<TargetRisk> selectTargetRiskById(Long id) {
		List<TargetRisk> targetRiskList = targetRiskMapper.selectTargetRisksByTargetId(id);
		return targetRiskList;
	}

	@Override
	/** 添加靶标对应的靶标风险(靶标风险有id则进行修改操作) */
	public void insertTargetRisk(HttpServletRequest request, List<TargetRisk> targetRiskList, Long targetId) {
		for (TargetRisk targetRisk : targetRiskList) {
			
			if(targetRisk.getId()!= null){
				TargetRisk OrinRisk = targetRiskMapper.selectByPrimaryKey(targetRisk.getId());
				Request2ModelUtil.covertObj(OrinRisk, targetRisk);
				OrinRisk.setUpdateTime(new Date());
				
				////////设置当前用户的id/////////
				OrinRisk.setUpdateBy(1L);
				
				targetRiskMapper.updateByPrimaryKey(OrinRisk);
			}else{
				targetRisk.setInfrastructureId(targetId);
				targetRisk.setCreateTime(new Date());
				targetRisk.setUpdateTime(new Date());
				
				/////////设置当前用户的id///////
				targetRisk.setCreateBy(1L);
				targetRisk.setUpdateBy(1L);
				
				targetRisk.setDelFlag(false);
				targetRiskMapper.insert(targetRisk);
			}
			
		}
	}

	@Override
	/** 删除靶标对应的靶标风险 */
	public void deleteTargetRisk(List ids) {
		targetRiskMapper.deleteByIds(ids);
	}

	@Override
	/** 根据靶标id级联删除靶标漏洞 */
	public void deleteTargetRiskByTargetIds(List id) {
		targetRiskMapper.deleteTargetRiskByTargetIds(id);
	}

	@Override
	public List<TargetRisk> selectRiskByTargetIdAndClassify(Map<String, Object> params) {
		List<TargetRisk> TargetRiskList = targetRiskMapper.selectRiskByTargetIdAndClassify(params);
		return TargetRiskList;
	}

	@Override
	public List<Map<String,Object>> getRiskCount() {
		// TODO Auto-generated method stub
		List<Map<String,Object>> countList = targetRiskMapper.getRiskCount();
		return countList;
	}

	@Override
	public List<Map<String, Object>> getHotKey() {
		// TODO Auto-generated method stub
		List<Map<String,Object>> hotKeyList = targetRiskMapper.getHotKey();
		return hotKeyList;
	}

	
}

