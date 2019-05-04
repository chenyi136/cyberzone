package com.safecode.cyberzone.controller;

import com.safecode.cyberzone.base.dto.ResponseData;
import com.safecode.cyberzone.entity.YaraRuleType;
import com.safecode.cyberzone.service.YaraRuleTypeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

//@CrossOrigin
@Controller
@PropertySource(value = "classpath:malwareDetection.properties", encoding = "utf-8")
@RequestMapping(value = "api/v1/yararuletypes")
@Api(description = "yara规则类型")
public class YaraRuleTypeController {


    @Autowired
    private YaraRuleTypeService yaraRuleTypeService;

    @ApiOperation(value = "查询yara规则类型列表")
    @ApiImplicitParams({
//            @ApiImplicitParam(name="upload",value = "文件名称",dataType = "file",required = false)
    })

    @RequestMapping(value = "findYaraRuleTypes", method = RequestMethod.GET)
    @ResponseBody
    public ResponseData<List<YaraRuleType>> findYaraRuleTypes(HttpServletRequest request, HttpSession session, YaraRuleType yaraRuleType) {

        ResponseData<List<YaraRuleType>> commonJsonResponse = new ResponseData<>();
        commonJsonResponse = this.yaraRuleTypeService.findYaraRuleTypes(yaraRuleType);
        return commonJsonResponse;
    }

}
