package com.safecode.cyberzone.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.safecode.cyberzone.base.service.SessionProvider;
import com.safecode.cyberzone.base.utils.DataUtil;
import com.safecode.cyberzone.base.utils.TicketUtil;
import com.safecode.cyberzone.mapper.AdMessageMapper;
import com.safecode.cyberzone.mapper.AdMessageUserMapper;
import com.safecode.cyberzone.pojo.AdMessage;
import com.safecode.cyberzone.pojo.AdMessageUser;
import com.safecode.cyberzone.service.AdMessageService;


@Service
@Transactional
public class AdMessageServiceImpl implements AdMessageService {

	@Autowired
	private AdMessageMapper adMessageMapper;
	@Autowired
	private AdMessageUserMapper adMessageUserMapper;
	@Autowired
	private SessionProvider sessionProvider;
	
	@Override
	/** 消息分页列表 */
	public PageInfo<AdMessage> queryPageList(HttpServletRequest request, Map<String, Object> params) {
		if (DataUtil.isEmpty(params.get("pageNum"))) {
			params.put("pageNum", 1);
		}
		if (DataUtil.isEmpty(params.get("pageSize"))) {
			params.put("pageSize", 10);
		}
		//获取当前用户id
		Long userId = sessionProvider.getAttributeForSysUser(TicketUtil.getTOKEN(request, null)).getId();

		params.put("userId", userId);
		PageHelper.startPage(params);
		Page<AdMessage> page = adMessageMapper.queryPageList(params);

		return new PageInfo<AdMessage>(page);
	}

	@Override
	/** 公告新增  */
	public void add(HttpServletRequest request, AdMessage adMessage) {
		//获取当前用户id
		Long userId = sessionProvider.getAttributeForSysUser(TicketUtil.getTOKEN(request, null)).getId();

		//公告主表插入数据,xml中配置主键自增返回
		adMessage.setTitle("公告");
		adMessage.setSource(1);
		adMessage.setCreateTime(new Date());
		adMessage.setUpdateTime(new Date());
		adMessage.setCreateBy(userId);
		adMessage.setUpdateBy(userId);
		adMessage.setDelFlag(false);
		adMessageMapper.insert(adMessage);
		
		//获取所有战队表中的用户id,维护消息用户表
		/////////////////////获取团队用户列表////////////////////////
		List<Long> corpsUserIdlist = adMessageMapper.selectCorpsUserIds();
		/////////////////////////////////////////////////////////
		for (Long corpsUserId : corpsUserIdlist) {
			AdMessageUser adMessageUser = new AdMessageUser();
			adMessageUser.setMessageId(adMessage.getId());
			adMessageUser.setUserId(corpsUserId);
			adMessageUser.setCreateTime(new Date());
			adMessageUser.setUpdateTime(new Date());
			adMessageUser.setCreateBy(userId);
			adMessageUser.setUpdateBy(userId);
			adMessageUser.setIfRead(false);
			adMessageUser.setIfCollect(false);
			adMessageUser.setDelFlag(false);
			adMessageUserMapper.insert(adMessageUser);
		}
	}

	@Override
	/** 获取未读消息数量   */
	public int getUnreadCount(HttpServletRequest request) {
		//获取当前用户id
		Long userId = sessionProvider.getAttributeForSysUser(TicketUtil.getTOKEN(request, null)).getId();

		int unreadCount = adMessageMapper.getUnreadCount(userId);
		return unreadCount;
	}

	@Override
	/** 全部设置为已读   */
	public void setAllReaded(HttpServletRequest request) {
		//获取当前用户id
		Long userId = sessionProvider.getAttributeForSysUser(TicketUtil.getTOKEN(request, null)).getId();

		adMessageMapper.setAllReaded(userId);
	}

	@Override
	/** 公告全查  */
	public PageInfo<Map<String, Object>> queryAllAnnouncement(HttpServletRequest request, Map<String, Object> params) {
		if (DataUtil.isEmpty(params.get("pageNum"))) {
			params.put("pageNum", 1);
		}
		if (DataUtil.isEmpty(params.get("pageSize"))) {
			params.put("pageSize", 10);
		}
		PageHelper.startPage(params);
		Page<Map<String, Object>> page = adMessageMapper.queryAllAnnouncement(params);

		return new PageInfo<Map<String, Object>>(page);
	}

	@Override
	/** 消息详情,将消息用户表标记为已读  */
	public Map<String, Object> selectById(HttpServletRequest request,Long id) {
		//获取当前用户id
		Long userId = sessionProvider.getAttributeForSysUser(TicketUtil.getTOKEN(request, null)).getId();

		Map<String, Object> messageMap = adMessageMapper.selectById(id);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		params.put("userId", userId);
		adMessageMapper.setReadedById(params);
		return messageMap;
	}

	@Override
	/** 收藏消息  */
	public void setCollectById(HttpServletRequest request, Long id) {
		//获取当前用户id
		Long userId = sessionProvider.getAttributeForSysUser(TicketUtil.getTOKEN(request, null)).getId();

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		params.put("userId", userId);
		AdMessageUser adMessageUser=  adMessageMapper.getAdMessageUserById(params);		
		if(adMessageUser != null){
			if(adMessageUser.getIfCollect()){
				adMessageUser.setIfCollect(false);
			}else{
				adMessageUser.setIfCollect(true);
			}
		}
		adMessageUser.setUpdateTime(new Date());
		adMessageUserMapper.updateByPrimaryKey(adMessageUser);
	}

	@Override
	/** 指挥团队创建消息,分发给相关的各个战队 */
	public void addMessage(Long currentUserId, String title, String content, int source, List<Long> corpsIds) {

				//创建消息实体类
				AdMessage adMessage = new AdMessage();
				//公告主表插入数据,xml中配置主键自增返回
				adMessage.setTitle(title);
				adMessage.setContent(content);
				adMessage.setSource(source);
				adMessage.setCreateTime(new Date());
				adMessage.setUpdateTime(new Date());
				adMessage.setCreateBy(currentUserId);
				adMessage.setUpdateBy(currentUserId);
				adMessage.setDelFlag(false);
				adMessageMapper.insert(adMessage);
				
				//获取选中战队表中的用户id,维护消息用户表
				/////////////////////获取团队用户列表////////////////////////
				for (Long corpsId : corpsIds) {
					List<Long> corpsUserIdlist = adMessageMapper.selectCorpsUserIdsByCorpsId(corpsId);
					/////////////////////////////////////////////////////////
					for (Long corpsUserId : corpsUserIdlist) {
						AdMessageUser adMessageUser = new AdMessageUser();
						adMessageUser.setMessageId(adMessage.getId());
						adMessageUser.setUserId(corpsUserId);
						adMessageUser.setCreateTime(new Date());
						adMessageUser.setUpdateTime(new Date());
						adMessageUser.setCreateBy(currentUserId);
						adMessageUser.setUpdateBy(currentUserId);
						adMessageUser.setIfRead(false);
						adMessageUser.setIfCollect(false);
						adMessageUser.setDelFlag(false);
						adMessageUserMapper.insert(adMessageUser);
					}
				}
				
	}


	

	

	

	
}

