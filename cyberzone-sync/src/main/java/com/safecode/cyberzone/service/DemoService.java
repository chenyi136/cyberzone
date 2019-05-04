package com.safecode.cyberzone.service;

import java.util.Map;

import com.github.pagehelper.PageInfo;

public interface DemoService {

	PageInfo<Map> queryPageList(Map<String, Object> params);

}
