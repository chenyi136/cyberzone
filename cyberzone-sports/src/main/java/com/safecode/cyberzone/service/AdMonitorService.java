package com.safecode.cyberzone.service;

import java.util.List;
import java.util.Map;

import org.springframework.ui.ModelMap;

public interface AdMonitorService {

	List<Map<String, Object>> queryCorpsVm(Long corpsId);

	ModelMap bigScreen();

}
