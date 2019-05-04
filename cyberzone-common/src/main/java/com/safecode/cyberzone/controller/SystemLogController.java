package com.safecode.cyberzone.controller;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.safecode.cyberzone.base.dto.ResponseData;
import com.safecode.cyberzone.dto.JsonResponse;
import com.safecode.cyberzone.pojo.SystemLogDic;
import com.safecode.cyberzone.vo.SysLogVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import com.github.pagehelper.PageInfo;
import com.safecode.cyberzone.controller.base.BaseController;
import com.safecode.cyberzone.pojo.SysLog;
import com.safecode.cyberzone.pojo.SysLogSaveTime;
import com.safecode.cyberzone.pojo.SystemLog;
import com.safecode.cyberzone.service.SystemLogService;

//@CrossOrigin
@RestController
@Component //使spring管理
@EnableScheduling //定时任务注解
@RequestMapping(value = "systemLog")
public class SystemLogController extends BaseController {

	@Autowired
	private SystemLogService sysLogService;

	/**
     * 获取系统操作日志列表
     * @return
     */
    @RequestMapping(value = "/get", method = RequestMethod.POST)
    public ResponseData<PageInfo> get(@RequestBody(required = false) SysLogVo sysLogVo) {
        ResponseData<PageInfo> data = new ResponseData<PageInfo>();
        try {
            PageInfo<SysLog> sysLog = sysLogService.queryPageList(sysLogVo);
            data.setCode(200);
            data.setMsg("查询成功");
            data.setData(sysLog);
        } catch (Exception e) {
            data.setMsg("查询失败");
            e.printStackTrace();
        }

        return data;
    }


	/**
     * 设置系统操作日志保存天数
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ResponseData add(int id, int saveTime) throws IOException {
        ResponseData data = new ResponseData();
    	try {
//    		SysLogSaveTime sysLogSaveTime = Request2ModelUtil.covert(SysLogSaveTime.class, request);
            SysLogSaveTime sysLogSaveTime = new SysLogSaveTime();
            sysLogSaveTime.setId(id);
            sysLogSaveTime.setSaveTime(saveTime);
            sysLogService.insert(sysLogSaveTime);
            data.setData(sysLogSaveTime);
            data.setMsg("保存成功");
            data.setCode(200);
            return data;
    	}catch (Exception e) {
    		e.printStackTrace();
			throw e;
		}
    }


    /**
     * 获取设置的日志保存时间
     * @param modelMap
     * @param id  主键id
     * @return
     */
    @RequestMapping(value = "/detail", method = RequestMethod.POST)
    public ResponseData detail(HttpServletRequest request, ModelMap modelMap, @RequestParam(value = "id", required = false) Long id) {
        ResponseData data = new ResponseData();
        try {
            List list = sysLogService.selectSaveTimeById();
            data.setData(list);
            data.setMsg("获取成功");
            data.setCode(200);
        } catch (Exception e) {
            data.setMsg("获取失败");
            e.printStackTrace();
        }
        return data;
    }

    /**
     * 导出csv文件
     * @param request
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "/exportCsv", method = RequestMethod.GET)
    public Object exportCsv(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) throws IOException {
    	String fileName = "系统日志.csv";
        response.setContentType("application/csv;charset=GBK");
        response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(fileName, "UTF-8"));
        PrintWriter out = response.getWriter();
        BufferedWriter csvFileOutputStream = new BufferedWriter(out);
        try {

            List<String> titleList = new ArrayList<>();
            Map<String, Object> params = BaseController.getParameterMap(request);
            List dataList = sysLogService.exportSysLogList(params);
            csvFileOutputStream.append("操作人账号").append(",");
            csvFileOutputStream.append("操作时间").append(",");
            csvFileOutputStream.append("ip地址").append(",");
            csvFileOutputStream.append("操作内容").append(",");
            csvFileOutputStream.append("操作模块").append("\r");
			if(dataList != null && !dataList.isEmpty()){
				for(int i=0; i<dataList.size(); i++){
					SystemLog sysLog = (SystemLog) dataList.get(i);
					csvFileOutputStream.append(sysLog.getUserName()).append(",");
					java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					csvFileOutputStream.append(sdf.format(sysLog.getCreateTime())).append(",");
					csvFileOutputStream.append(sysLog.getIpAddr()).append(",");
					csvFileOutputStream.append(sysLog.getRemark()).append(",");
					csvFileOutputStream.append(sysLog.getProjectName()).append("\r");
				}
			}
            csvFileOutputStream.flush(); //刷出数据到文件中
        } catch (Exception e) {
        } finally {
            try {
                if(csvFileOutputStream!=null){
                    csvFileOutputStream.close();}
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
		return csvFileOutputStream;
    }
 
    /**
     * 根据设置的日志保存天数删除过期日志
     */
    @Scheduled(cron = "0 0 1 * * ?")
    public void deleteSysLogBySaveTime(){
    	sysLogService.deleteSysLogBySaveTime();
    }

    /**
     * 查询系统下拉列表
     * @return
     */
    @RequestMapping(value = "/systemList", method = RequestMethod.GET)
    @ResponseBody
    public ResponseData systemList() {
        ResponseData data = new ResponseData();
        try {
            List systemList = sysLogService.querysystemList();
            data.setData(systemList);
            data.setMsg("查询成功");
            data.setCode(200);
        } catch (Exception e) {
            data.setMsg("出现失败");
            e.printStackTrace();
        }
        return data;
    }


}
