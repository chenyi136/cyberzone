package com.safecode.cyberzone.service;

import com.safecode.cyberzone.base.dto.ResponseData;
import com.safecode.cyberzone.entity.YaraRuleType;

import java.util.List;

public interface YaraRuleTypeService {
    ResponseData<List<YaraRuleType>> findYaraRuleTypes(YaraRuleType yaraRuleType);
}
