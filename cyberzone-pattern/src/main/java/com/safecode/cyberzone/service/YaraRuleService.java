package com.safecode.cyberzone.service;

import com.safecode.cyberzone.base.dto.ResponseData;
import com.safecode.cyberzone.entity.YaraRule;

import java.util.List;

public interface YaraRuleService {

    List<YaraRule> findRules(YaraRule yaraRule);

    ResponseData<List<YaraRule>> flushRule(List<String> rules);

    List<String> findRulesPaths(YaraRule yaraRule);

    List<String> findRulesByTypes(List<String> types);
}
