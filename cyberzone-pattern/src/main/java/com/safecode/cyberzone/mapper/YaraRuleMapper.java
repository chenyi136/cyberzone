package com.safecode.cyberzone.mapper;

import com.safecode.cyberzone.entity.YaraRule;

import java.util.List;

public interface YaraRuleMapper {

    void addYaraRule(YaraRule yaraRule);

    List<YaraRule> findYaraRules(YaraRule yaraRule);

    List<String> findRulesPaths(YaraRule yaraRule);

    List<String> findRulesByTypes(List<String> types);

    void yaraRuleClear();

}
