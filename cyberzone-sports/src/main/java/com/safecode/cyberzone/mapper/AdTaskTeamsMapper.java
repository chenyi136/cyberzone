package com.safecode.cyberzone.mapper;

import com.safecode.cyberzone.pojo.AdTaskTeams;
import java.util.List;
import java.util.Map;

public interface AdTaskTeamsMapper {
    int deleteByPrimaryKey(Long id);

    int insert(AdTaskTeams record);

    AdTaskTeams selectByPrimaryKey(Long id);

    List<AdTaskTeams> selectAll();

    int updateByPrimaryKey(AdTaskTeams record);

	List<AdTaskTeams> queryBy(Map<String, Object> params);

	void deleteTaskTeams(Long taskId);

	AdTaskTeams queryBytaskIdAndteamId(Long taskId, Long teamId);

	List<Map<String, Object>> queryCheckedTeamsName(Long taskId);

	List<Long> queryTeamIdsByTaskId(Long taskId);
}