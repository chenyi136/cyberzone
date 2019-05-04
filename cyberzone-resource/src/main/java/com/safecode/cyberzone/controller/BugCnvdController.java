package com.safecode.cyberzone.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.safecode.cyberzone.base.dto.ResponseData;
import com.safecode.cyberzone.base.support.Code;
import com.safecode.cyberzone.controller.base.BaseController;
import com.safecode.cyberzone.pojo.BugCnvd;
import com.safecode.cyberzone.service.BugCnvdService;
import com.safecode.cyberzone.vo.BugCnvdVo;


@RestController
@RequestMapping(value = "bugCnvd")
public class BugCnvdController extends BaseController {


    @Autowired
    private BugCnvdService bugCnvdService;
    
	
    /**
     * 获取CNVD漏洞库列表
     * @param request
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "/get", method = RequestMethod.POST)
    public ResponseData<Object> get(BugCnvdVo cnvdVo) {
    	PageInfo<BugCnvd> cnvdList = bugCnvdService.queryPageList(cnvdVo);
    	return new ResponseData<Object>(Code.OK.value(), "获取CNVD列表数据成功", cnvdList);
    }
	
    /**
     * 查看详情
     * @param request
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "/detail", method = RequestMethod.GET)
    public ResponseData<Object> detail(@RequestParam(value = "id", required = false) Long id) {
    	BugCnvdVo cnvdVo = bugCnvdService.queryById(id);
    	return new ResponseData<Object>(Code.OK.value(), "获取CNVD详情", cnvdVo);
    }
    /**
     * 获取威胁情报库的威胁等级及数量(威胁情报库首页的威胁等级)
     * @param request
     * @param response
     * @param modelMap
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/bugCountByServerity", method = RequestMethod.GET)
    public ResponseData<Object> getBugCountByServerity(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) throws Exception {
    	List<Map<String,Object>> bugCountMapList = bugCnvdService.getBugCountByServerity();
    	return new ResponseData<Object>(Code.OK.value(), "威胁等级及数量", bugCountMapList);
    }
    /**
     * 按年度获取威胁数量(威胁情报库首页的时间趋势图)
     * @param request
     * @param response
     * @param modelMap
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/bugCountByYear", method = RequestMethod.GET)
    public Object getBugCountByYear(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) throws Exception {
    	List<Map<String,Object>> bugCountMapList = bugCnvdService.getBugCountByYear();
    	return new ResponseData<Object>(Code.OK.value(), "按年度获取威胁数量", bugCountMapList);
    }
    
    /**
     * 按时间倒序获取10条数据(威胁情报库首页的重点新闻)
     * @param request
     * @param response
     * @param modelMap
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/keyNews", method = RequestMethod.GET)
    public Object getKeyNews(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) throws Exception {
    	List<BugCnvd> cnvdList = bugCnvdService.getKeyNews();
    	return new ResponseData<Object>(Code.OK.value(), "按时间倒序获取10条数据", cnvdList);
    }
    
    /**
     * 获取威胁情报的类别及数量(威胁情报库首页的情报类别)
     * @param request
     * @param response
     * @param modelMap
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/bugCountByClassify", method = RequestMethod.GET)
    public Object getBugCountByClassify(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) throws Exception {
    	List<Map<String,Object>> bugCountMapList = bugCnvdService.getBugCountByClassify();
    	return new ResponseData<Object>(Code.OK.value(), "获取威胁情报的类别及数量", bugCountMapList);
    }
    
}
