package com.safecode.cyberzone.mapper;

import java.util.Map;

import com.github.pagehelper.Page;

public interface DemoMapper {

	Page<Map> queryPageList(Map<String, Object> params);

}
