package com.safecode.cyberzone.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.safecode.cyberzone.base.dto.ResponseData;
import com.safecode.cyberzone.base.service.SessionProvider;
import com.safecode.cyberzone.base.support.Code;
import com.safecode.cyberzone.utils.Request2ModelUtil;
import com.safecode.cyberzone.controller.base.BaseController;
import com.safecode.cyberzone.pojo.TargetInfrastructure;
import com.safecode.cyberzone.pojo.TargetRisk;
import com.safecode.cyberzone.service.TargetInfrastructureService;
import com.safecode.cyberzone.utils.FileUtil;
import com.safecode.cyberzone.utils.SysLogUtil;
import com.safecode.cyberzone.vo.TargetInfrastructureVo;

@RestController
@RequestMapping(value = "targetInfrastructure")
public class TargetInfrastructureController extends BaseController {

    @Autowired
    private TargetInfrastructureService targetInfrastructureService;
    @Autowired
	private SessionProvider sessionProvider;
	@Autowired
	private SysLogController sysLogController;
	
    /**
     * 获取关键基础设施靶标列表
     * @param request
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "/get", method = RequestMethod.POST)
    public ResponseData<Object> get(HttpServletRequest request, TargetInfrastructureVo targetInfrastructureVo) {
    	PageInfo<TargetInfrastructure> targetList = targetInfrastructureService.queryPageList(targetInfrastructureVo);
    	return new ResponseData<Object>(Code.OK.value(),"关键基础设施靶标列表", targetList);
    }
    
    /**
     * 获取关键基础设施靶标列表(全查)
     * @param request
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "/queryAll", method = RequestMethod.GET)
    public ResponseData<Object> selectAll(HttpServletRequest request) {
    	List<TargetInfrastructure> tartgetList = targetInfrastructureService.queryAll();
    	return new ResponseData<Object>(Code.OK.value(),"关键基础设施靶标列表", tartgetList);
    }
		
    /**
     * 删除关键基础设施靶标
     * @param request
     * @param modelMap
     * @param id
     * @return
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public ResponseData<Object> delete(HttpServletRequest request, String ids) {
    	
    		String[] idList = ids.split(",");
    		List<String> id = new ArrayList<String>();
    		for(int i=0; i<idList.length; i++) {
    			id.add(idList[i]);
    		}
			targetInfrastructureService.delete(id);
			targetInfrastructureService.deleteTargetRiskByTargetIds(id);
	    	return new ResponseData<Object>(Code.OK.value(),"删除成功", null);
    }
    
    /**
     * 新增关键基础设施靶标
     * 
     * 2018年7月31日下午1:50:10
     * @param request
     * @param modelMap
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseData<Object> add(HttpServletRequest request, TargetInfrastructure targetInfrastructure, MultipartFile targetFile, MultipartFile vncFile) 
    		throws Exception {
    	targetInfrastructureService.add(request, targetInfrastructure, targetFile, vncFile);
    	return new ResponseData<Object>(Code.OK.value(),"添加成功", null);
    }
    
  /**
   * 基础设施靶标数据回显/基础设施靶标详情
   * 2018年7月31日下午5:22:15
   * @param request
   * @param modelMap
   * @param id
   * @return
   * @throws Exception
   */
    @RequestMapping(value = "/show", method = RequestMethod.POST)
    public ResponseData<Object> show(HttpServletRequest request,Long id)throws Exception {
    	TargetInfrastructure targetInfrastructure = targetInfrastructureService.selectById(id);
    	List<TargetRisk> targetRiskList = targetInfrastructureService.selectTargetRiskById(id);
    	Map<Object,Object> map = new HashMap<>();
    	map.put("targetInfrastructure", targetInfrastructure);
    	map.put("targetRiskList", targetRiskList);
    	return new ResponseData<Object>(Code.OK.value(),"靶标详情", map);
    }
    
    /**
     * 基础设施靶标数据回显(根据靶标id和靶标漏洞分类筛选靶标漏洞)
     * @param request
     * @param modelMap
     * @param id
     * @param riskClassify
     * @return
     */
    @RequestMapping(value = "/queryByClassify", method = RequestMethod.POST)
    public ResponseData<Object> detailByIds(HttpServletRequest request, Long infrastructureId, Long riskClassify) {
    	Map<String, Object> params = new HashMap<>();
    	params.put("infrastructureId", infrastructureId);
    	params.put("riskClassify", riskClassify);
    	List<TargetRisk> targetRiskList =targetInfrastructureService.selectRiskByTargetIdAndClassify(params);
    	return new ResponseData<Object>(Code.OK.value(),"基础设施靶标数据回显", targetRiskList);
    }
    
    
    /**
     * 修改关键基础设施靶标
     * 2018年7月31日下午5:39:48
     * @param request
     * @param modelMap
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResponseData<Object> update(HttpServletRequest request,TargetInfrastructure targetInfrastructure, MultipartFile targetFile, MultipartFile vncFile, String jsonString, String deleteIds)
    		throws Exception {
		//修改基础设施靶标
    	targetInfrastructureService.update(request, targetInfrastructure, targetFile, vncFile);
    	//添加或修改靶标对应的靶标风险
    	List<TargetRisk> targetRiskList = null;
    	if(jsonString != null && !"".equals(jsonString)){
    		targetRiskList = JSON.parseArray(jsonString, TargetRisk.class);
    	}
    	if(targetRiskList != null && targetRiskList.size() >0){
    		targetInfrastructureService.insertTargetRisk(request, targetRiskList, targetInfrastructure.getId());
    	}
    	//获取需要删除的靶标风险idList
    	List ids = new ArrayList();
    	if(deleteIds != null && !"".equals(deleteIds)){
    		String[] idlist = deleteIds.split(",");
    		for(int i=0; i<idlist.length; i++) {
    			ids.add(idlist[i]);
    		}
    	}
		//删除靶标对应的靶标风险
    	if(ids != null && ids.size() >0){
    		targetInfrastructureService.deleteTargetRisk(ids);
    	}
    	return new ResponseData<Object>(Code.OK.value(),"修改成功", null);
    }
    
    /**
     * 架构图展示
     * @param request
     * @param response
     * @param modelMap
     * @param filePath
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/showPicture", method = RequestMethod.GET)
    public ResponseData<Object> showPicture(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap, @RequestParam(value = "filePath", required = false) String filePath) throws IOException {
    	FileUtil.onlinePreview(request, response, filePath);
    	return new ResponseData<Object>(Code.OK.value(),"架构图展示", null);
    }
    /**
     * 获取靶标漏洞部署环境分类及数量("已处理"和"未处理")
     * @param request
     * @param response
     * @param modelMap
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/riskCount", method = RequestMethod.GET)
    public ResponseData<Object> getRiskCount(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	List<Map<String,Object>> countMapList = targetInfrastructureService.getRiskCount();
    	return new ResponseData<Object>(Code.OK.value(),"靶标漏洞部署环境分类及数量", countMapList);
    }
    /**
     * 获取靶标风险说明及数量
     * @param request
     * @param response
     * @param modelMap
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/hotKey", method = RequestMethod.GET)
    public ResponseData<Object> getHotKey(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) throws Exception {
    	List<Map<String,Object>> hotKeyMapList = targetInfrastructureService.getHotKey();
    	return new ResponseData<Object>(Code.OK.value(),"靶标漏洞部署环境分类及数量", hotKeyMapList);
    }
    
}
