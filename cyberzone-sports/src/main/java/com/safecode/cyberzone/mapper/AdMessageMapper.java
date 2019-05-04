package com.safecode.cyberzone.mapper;

import com.github.pagehelper.Page;
import com.safecode.cyberzone.pojo.AdMessage;
import com.safecode.cyberzone.pojo.AdMessageUser;

import java.util.List;
import java.util.Map;

public interface AdMessageMapper {
    int deleteByPrimaryKey(Long id);

    int insert(AdMessage record);

    AdMessage selectByPrimaryKey(Long id);

    List<AdMessage> selectAll();

    int updateByPrimaryKey(AdMessage record);

	Page<AdMessage> queryPageList(Map<String, Object> params);

	int getUnreadCount(Long userId);

	void setAllReaded(Long userId);

	Page<Map<String, Object>> queryAllAnnouncement(Map<String, Object> params);

	Map<String, Object> selectById(Long id);

	void setReadedById(Map<String, Object> params);

	AdMessageUser getAdMessageUserById(Map<String, Object> params);

	List<Long> selectCorpsUserIds();

	List<Long> selectCorpsUserIdsByCorpsId(Long corpsId);
}