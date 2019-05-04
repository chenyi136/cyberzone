package com.safecode.cyberzone.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.github.pagehelper.PageInfo;
import com.safecode.cyberzone.pojo.AdMessage;

public interface AdMessageService {

	PageInfo<AdMessage> queryPageList(HttpServletRequest request, Map<String, Object> params);

	void add(HttpServletRequest request, AdMessage adMessage);

	int getUnreadCount(HttpServletRequest request);

	void setAllReaded(HttpServletRequest request);

	PageInfo<Map<String, Object>> queryAllAnnouncement(HttpServletRequest request, Map<String, Object> params);

	Map<String, Object> selectById(HttpServletRequest request,Long id);

	void setCollectById(HttpServletRequest request,Long id);

	void addMessage(Long currentUserId, String title, String content, int source, List<Long> corpsIds);
}
