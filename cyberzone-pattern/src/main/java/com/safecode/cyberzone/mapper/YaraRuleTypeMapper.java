package com.safecode.cyberzone.mapper;

import com.safecode.cyberzone.entity.YaraRuleType;

import java.util.List;

public interface YaraRuleTypeMapper {
    List<YaraRuleType> findYaraRuleTypes(YaraRuleType yaraRuleType);

    List<YaraRuleType> findAllYaraRuleTypes(YaraRuleType yaraRuleType);
}
