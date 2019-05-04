package com.safecode.cyberzone.mapper;

import com.safecode.cyberzone.pojo.AdMessageUser;
import java.util.List;

public interface AdMessageUserMapper {
    int deleteByPrimaryKey(Long id);

    int insert(AdMessageUser record);

    AdMessageUser selectByPrimaryKey(Long id);

    List<AdMessageUser> selectAll();

    int updateByPrimaryKey(AdMessageUser record);
}