package com.safecode.cyberzone.service;

import java.util.List;
import java.util.Map;

import com.safecode.cyberzone.pojo.VmApply;

public interface VmAutoApplyService {

	String add(VmApply vmApply);

	List<Map<String, Object>> queryUserAutoApply(String applyName);

}
