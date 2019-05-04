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
import com.safecode.cyberzone.mapper.StrategyLibraryMapper;
import com.safecode.cyberzone.pojo.StrategyLibrary;
import com.safecode.cyberzone.service.StrategyLibraryService;
import com.safecode.cyberzone.utils.FileUtil;
import com.safecode.cyberzone.vo.StrategyLibraryVo;

@Service
@Transactional
public class StrategyLibraryServiceImpl implements StrategyLibraryService {

	@Autowired
	private SessionProvider sessionProvider;
	@Autowired
	private StrategyLibraryMapper strategyLibraryMapper;

	@Override
	/** 添加攻防策略 @throws Exception */
	public void add(HttpServletRequest request, StrategyLibrary strategyLibrary, MultipartFile strategyFile)
			throws Exception {
		if (strategyFile != null && !strategyFile.isEmpty()
				&& StringUtils.isNotEmpty(strategyFile.getOriginalFilename())) {
			FileInfo fileInfo = FileUtil.saveFile(strategyFile, FileUtil.UPLOADPATH_STRATEGY);
			Request2ModelUtil.covertObj(strategyLibrary, fileInfo);
		}

		strategyLibrary.setCreateTime(new Date());
		strategyLibrary.setUpdateTime(new Date());
		strategyLibrary.setDelFlag(false);

		Long userId = 1L;
		strategyLibrary.setCreateBy(userId);
		strategyLibrary.setUpdateBy(userId);

		strategyLibraryMapper.insert(strategyLibrary);

	}

	@Override
	/** 获取策略库文件列表(有条件全查) */
	public List<StrategyLibrary> queryAll(StrategyLibraryVo strategyLibraryVo) {
		List<StrategyLibrary> strategyLibraryList = strategyLibraryMapper.queryAll(strategyLibraryVo);
		return strategyLibraryList;
	}

	@Override
	/** 删除策略库文件 */
	public void delete(Long id, HttpServletRequest request) {
		StrategyLibrary strategyLibrary = strategyLibraryMapper.selectByPrimaryKey(id);
		if (strategyLibrary != null) {
			// 获取当前用户id
			Long userId = 1L;
			strategyLibrary.setUpdateBy(userId);
			strategyLibrary.setUpdateTime(new Date());
			strategyLibrary.setDelFlag(true);
			// 删除策略库文件
			strategyLibraryMapper.updateByPrimaryKey(strategyLibrary);
		}
	}

	@Override
	/** 获取策略库列表(分页列表) */
	public PageInfo<StrategyLibrary> queryPageList(StrategyLibraryVo strategyLibraryVo) {
		Map<Object, Object> params = new HashMap<>();
		if (strategyLibraryVo == null || DataUtil.isEmpty(strategyLibraryVo.getPageNum())) {
			params.put("pageNum", 1);
		}else{
			params.put("pageNum", strategyLibraryVo.getPageNum());
		}
		if (strategyLibraryVo == null || DataUtil.isEmpty(strategyLibraryVo.getPageSize())) {
			params.put("pageSize", 10);
		}else{
			params.put("pageSize", strategyLibraryVo.getPageSize());
		}
		PageHelper.startPage(params);
		Page<StrategyLibrary> page = strategyLibraryMapper.queryPageList(strategyLibraryVo);
		return new PageInfo<StrategyLibrary>(page);
	}

	@Override
	/** 策略库详情(数据回显) */
	public StrategyLibrary selectById(Long id) {
		StrategyLibrary strategyLibrary = strategyLibraryMapper.selectByPrimaryKey(id);
		return strategyLibrary;
	}

	@Override
	/** 策略库修改 */
	public void update(HttpServletRequest request, StrategyLibrary strategyLibrary, MultipartFile strategyFile, Long currentUserId) throws Exception {
		StrategyLibrary originStrategyLibrary = strategyLibraryMapper.selectByPrimaryKey(strategyLibrary.getId());
		FileInfo fileInfo = null;
		if (strategyFile != null && !strategyFile.isEmpty()
				&& StringUtils.isNotEmpty(strategyFile.getOriginalFilename())) {
			fileInfo = FileUtil.saveFile(strategyFile, FileUtil.UPLOADPATH_STRATEGY);
			Request2ModelUtil.covertObj(originStrategyLibrary, fileInfo);
		}
		Request2ModelUtil.covertObj(originStrategyLibrary, strategyLibrary);

		originStrategyLibrary.setUpdateTime(new Date());
		////////////////////////////提交代码时需要修改/////////////////////////////////////
//		originStrategyLibrary.setUpdateBy(sessionProvider.getAttributeForSysUser(TicketUtil.getTOKEN(request, null)).getId());
		originStrategyLibrary.setUpdateBy(currentUserId);
		
		strategyLibraryMapper.updateByPrimaryKey(originStrategyLibrary);

	}

}
