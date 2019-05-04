package com.safecode.cyberzone.service.impl;

import com.safecode.cyberzone.entity.YaraRuleType;
import com.safecode.cyberzone.mapper.YaraRuleMapper;
import com.safecode.cyberzone.base.dto.ResponseData;
import com.safecode.cyberzone.base.support.Code;
import com.safecode.cyberzone.entity.YaraRule;
import com.safecode.cyberzone.mapper.YaraRuleTypeMapper;
import com.safecode.cyberzone.service.YaraRuleService;
import com.safecode.cyberzone.utils.UUIDUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Transactional
@Service
public class YaraRuleServiceImpl implements YaraRuleService {

    @Autowired
    private YaraRuleMapper yaraRuleMapper;

    @Autowired
    private YaraRuleTypeMapper yaraRuleTypeMapper;

    @Override
    public List<YaraRule> findRules(YaraRule yaraRule) {

        List<YaraRule> yaraRules = this.yaraRuleMapper.findYaraRules(yaraRule);
        return yaraRules;
    }

    @Override
    public ResponseData<List<YaraRule>> flushRule(List<String> rules) {

        //每次部署前先清库
        this.yaraRuleMapper.yaraRuleClear();
        List<YaraRuleType> yaraRuleTypeList = this.yaraRuleTypeMapper.findAllYaraRuleTypes(new YaraRuleType());
        ResponseData<List<YaraRule>> objectCommonJsonResponse = new ResponseData<>();
        objectCommonJsonResponse.setCode(Code.OK.value());
        for (String ruleStr : rules) {

            String ruleType ="";
            String fileName = ruleStr.substring(ruleStr.lastIndexOf("/") + 1, ruleStr.lastIndexOf("."));//文件名
            c:
            for (YaraRuleType yaraRuleType : yaraRuleTypeList) {
                //获得规则类型的英文名称
                String enName = yaraRuleType.getEnName();
                //如果规则文件的名称含有类型名称的话,就设置类型的id
                if(fileName.contains(enName)){
                    ruleType = yaraRuleType.getType();
                    break c;
                }
            }

                YaraRule yaraRule = new YaraRule();
                String suffix = ruleStr.substring(ruleStr.lastIndexOf(".") + 1); //文件后缀
                yaraRule.setId(UUIDUtils.getUUID());
                yaraRule.setPath(ruleStr);
                yaraRule.setRuleName(fileName);
                yaraRule.setFileName(fileName);
                yaraRule.setFileSuffix(suffix);
                //设置类型id
                yaraRule.setRuleTypeId(ruleType);
                yaraRule.setCreateTime(new Date());
                this.yaraRuleMapper.addYaraRule(yaraRule);

        }
        return objectCommonJsonResponse;
    }

    @Override
    public List<String> findRulesPaths(YaraRule yaraRule) {

        List<String> rules = this.yaraRuleMapper.findRulesPaths(yaraRule);
        return rules;
    }

    @Override
    public List<String> findRulesByTypes(List<String> types) {
        return this.yaraRuleMapper.findRulesByTypes(types);
    }
}
