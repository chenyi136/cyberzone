package com.safecode.cyberzone.service.impl;

import com.safecode.cyberzone.mapper.YaraRuleTypeMapper;
import com.safecode.cyberzone.base.dto.ResponseData;
import com.safecode.cyberzone.base.support.Code;
import com.safecode.cyberzone.entity.YaraRuleType;
import com.safecode.cyberzone.service.YaraRuleTypeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class YaraRuleTypeServiceImpl implements YaraRuleTypeService {

    @Autowired
    private YaraRuleTypeMapper yaraRuleTypeMapper;

    @Override
    public ResponseData<List<YaraRuleType>> findYaraRuleTypes(YaraRuleType yaraRuleType) {

        ResponseData<List<YaraRuleType>> commonJsonResponse = new ResponseData<>();
        commonJsonResponse.setCode(Code.OK.value());
        List<YaraRuleType> yaraRuleTypes = this.yaraRuleTypeMapper.findYaraRuleTypes(yaraRuleType);

        commonJsonResponse.setData(yaraRuleTypes);

        return commonJsonResponse;
    }
}
