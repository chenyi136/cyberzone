package com.safecode.cyberzone.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.safecode.cyberzone.base.dto.ResponseData;
import com.safecode.cyberzone.base.service.SessionProvider;
import com.safecode.cyberzone.base.support.Code;
import com.safecode.cyberzone.utils.Request2ModelUtil;
import com.safecode.cyberzone.controller.base.BaseController;
import com.safecode.cyberzone.pojo.TargetCity;
import com.safecode.cyberzone.pojo.ToolsLibrary;
import com.safecode.cyberzone.service.TargetCityService;
import com.safecode.cyberzone.utils.SysLogUtil;;

//@CrossOrigin
@RestController
@RequestMapping(value = "targetCity")
public class TargetCityController extends BaseController {
	
	@Autowired
	private TargetCityService targetCityService;
	@Autowired
	private SessionProvider sessionProvider;
	@Autowired
	private SysLogController sysLogController;
	
	/**
     * 获取城市靶标首页统计图数据
     * @param request
     * @param modelMap
     * @return
     */
	@RequestMapping(value = "/getCityView", method = RequestMethod.GET)
    public ResponseData getCityView() {
		Map<String, Object> map = targetCityService.getCityView();
		return new ResponseData(Code.OK.value(),"查询成功", map);
	}
	
	/**
     * 获取城市靶标数据列表
     * @param request
     * @param modelMap
     * @return
     */
	@RequestMapping(value = "/get", method = RequestMethod.POST)
    public ResponseData<PageInfo> get(TargetCity targetCity, @PageableDefault(page = 1, size = 10, sort="id,desc")Pageable pageable) {
    	PageInfo<TargetCity> targetCityList = targetCityService.queryPageList(targetCity,pageable);
    	return new ResponseData(Code.OK.value(),"查询成功", targetCityList);
    }
	
	/**
     * 获取城市靶标数据详情
     * @param request
     * @param modelMap
     * @param id  主键id
     * @return
     */
    @RequestMapping(value = "/detail", method = RequestMethod.POST)
    public ResponseData detail(Long id) {
    	TargetCity targetCity = targetCityService.selectById(id);
    	return new ResponseData(Code.OK.value(),"删除工程", targetCity);
    }
	
	
	/**
     * 删除选中的城市靶标数据
     * @param request
     * @param modelMap
     * @param id  主键id
     * @return
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public ResponseData delete(String ids) {
    	if(ids.length()>0) {
    		String[] idList = ids.split(",");
    		List id = new ArrayList();
    		for(int i=0; i<idList.length; i++) {
    			id.add(idList[i]);
    		}
    		targetCityService.delete(id);
    		return new ResponseData(Code.OK.value(),"删除成功", null);
    	} else {
    		return new ResponseData(Code.INTERNAL_SERVER_ERROR.value(),"无删除选项", null);
    	}
    }
    
    /**
     * 添加城市靶标数据
     * @param request
     * @param modelMap
     * @param toolFile  工具文件
     * @param toolFile  说明文档文件
     * @return
     * @throws IOException 
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseData<TargetCity> add(TargetCity targetCity) throws IOException {
    	try {
        	targetCity.setCreateTime(new Date());
        	targetCity.setUpdateTime(new Date());
        	targetCity.setDelFlag(false);
        	targetCityService.insert(targetCity);
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return new ResponseData<TargetCity>(Code.OK.value(),"创建成功", null);
    }
    
    /**
     * 修改城市靶标数据
     * @param request
     * @param modelMap
     * @return
     * @throws IOException 
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResponseData<TargetCity> update(TargetCity targetCity) throws IOException {
    	try {
			targetCity.setUpdateTime(new Date());
			targetCityService.updateById(targetCity);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
    	return new ResponseData<TargetCity>(Code.OK.value(),"修改成功", null);
    }
    
}
