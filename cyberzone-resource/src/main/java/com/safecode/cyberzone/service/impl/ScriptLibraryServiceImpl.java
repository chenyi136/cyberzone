package com.safecode.cyberzone.service.impl;

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
import com.safecode.cyberzone.mapper.ScriptLibraryMapper;
import com.safecode.cyberzone.pojo.ScriptLibrary;
import com.safecode.cyberzone.pojo.StrategyLibrary;
import com.safecode.cyberzone.service.ScriptLibraryService;
import com.safecode.cyberzone.utils.FileUtil;
import com.safecode.cyberzone.vo.ScriptLibraryVo;
@Service
@Transactional
public class ScriptLibraryServiceImpl implements ScriptLibraryService {
	
	@Autowired
	private SessionProvider sessionProvider;
	@Autowired
	private ScriptLibraryMapper scriptLibraryMapper;

	@Override
	/** 添加攻防策略 @throws Exception */
	public void add(HttpServletRequest request, ScriptLibrary scriptLibrary, MultipartFile scriptFile)
			throws Exception {
		if (scriptFile != null && !scriptFile.isEmpty()
				&& StringUtils.isNotEmpty(scriptFile.getOriginalFilename())) {
			FileInfo fileInfo = FileUtil.saveFile(scriptFile, FileUtil.UPLOADPATH_SCRIPT);
			Request2ModelUtil.covertObj(scriptLibrary, fileInfo);
		}

		scriptLibrary.setCreateTime(new Date());
		scriptLibrary.setUpdateTime(new Date());
		scriptLibrary.setDelFlag(false);

		// 获取当前用户id
		Long userId = 1L;
		scriptLibrary.setCreateBy(userId);
		scriptLibrary.setUpdateBy(userId);

		scriptLibraryMapper.insert(scriptLibrary);

	}

	@Override
	/** 获取策略库文件列表(有条件全查) */
	public List<ScriptLibrary> queryAll(ScriptLibraryVo scriptLibraryVo) {
		List<ScriptLibrary> scriptLibraryList = scriptLibraryMapper.queryAll(scriptLibraryVo);
		return scriptLibraryList;
	}

	@Override
	/** 删除策略库文件 */
	public void delete(Long id, Long currentUserId) {
		ScriptLibrary scriptLibrary = scriptLibraryMapper.selectByPrimaryKey(id);

		if (scriptLibrary != null) {
			// 设置当前用户id
			scriptLibrary.setUpdateBy(currentUserId);
			scriptLibrary.setUpdateTime(new Date());
			scriptLibrary.setDelFlag(true);
			// 删除策略库文件
			scriptLibraryMapper.updateByPrimaryKey(scriptLibrary);
		}
	}

	@Override
	/** 获取策略库列表(分页列表) */
	public PageInfo<ScriptLibrary> queryPageList(ScriptLibraryVo scriptLibraryVo) {
		Map<Object, Object> params = new HashMap<>();
		if (scriptLibraryVo == null || DataUtil.isEmpty(scriptLibraryVo.getPageNum())) {
			params.put("pageNum", 1);
		}else{
			params.put("pageNum", scriptLibraryVo.getPageNum());
		}
		if (scriptLibraryVo == null || DataUtil.isEmpty(scriptLibraryVo.getPageSize())) {
			params.put("pageSize", 10);
		}else{
			params.put("pageSize", scriptLibraryVo.getPageSize());
		}
		PageHelper.startPage(params);
		Page<ScriptLibrary> page = scriptLibraryMapper.queryPageList(scriptLibraryVo);
		return new PageInfo<ScriptLibrary>(page);
	}

	@Override
	/** 策略库详情(数据回显) */
	public ScriptLibrary selectById(Long id) {
		ScriptLibrary scriptLibrary = scriptLibraryMapper.selectByPrimaryKey(id);
		return scriptLibrary;
	}

	@Override
	/** 策略库修改 */
	public void update(ScriptLibrary scriptLibrary, MultipartFile scriptFile, Long currentUserId) throws Exception {
		ScriptLibrary originScriptLibrary = scriptLibraryMapper.selectByPrimaryKey(scriptLibrary.getId());
		FileInfo fileInfo = null;
		if (scriptFile != null && !scriptFile.isEmpty()
				&& StringUtils.isNotEmpty(scriptFile.getOriginalFilename())) {
			fileInfo = FileUtil.saveFile(scriptFile, FileUtil.UPLOADPATH_SCRIPT);
			Request2ModelUtil.covertObj(originScriptLibrary, fileInfo);
		}
		Request2ModelUtil.covertObj(originScriptLibrary, scriptLibrary);

		originScriptLibrary.setUpdateTime(new Date());
		originScriptLibrary.setUpdateBy(currentUserId);
		
		scriptLibraryMapper.updateByPrimaryKey(originScriptLibrary);

	}
}
