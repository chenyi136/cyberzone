package com.safecode.cyberzone.controller;

import java.io.IOException;
import java.util.Date;
import java.util.Map;

import javax.servlet.ServletException;
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

import com.github.pagehelper.PageInfo;
import com.safecode.cyberzone.base.pojo.FileInfo;
import com.safecode.cyberzone.base.service.SessionProvider;
import com.safecode.cyberzone.utils.Request2ModelUtil;
import com.safecode.cyberzone.base.utils.TicketUtil;
import com.safecode.cyberzone.controller.base.BaseController;
import com.safecode.cyberzone.pojo.ToolsLibraryApply;
import com.safecode.cyberzone.service.ToolsLibraryApplyService;
import com.safecode.cyberzone.utils.FileUtil;
import com.safecode.cyberzone.utils.SysLogUtil;;

//@CrossOrigin
@RestController
@RequestMapping(value = "toolsLibraryApply")
public class ToolsLibraryApplyController extends BaseController {

    @Autowired
    private ToolsLibraryApplyService toolsLibraryApplyService;
    @Autowired
    private SysLogController sysLogController;
    @Autowired
    private SessionProvider sessionProvider;

    /**
     * 获取工具申请列表
     *
     * @param request
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "/get", method = RequestMethod.POST)
    public Object get(HttpServletRequest request, ModelMap modelMap) {
//		int userId = sessionProvider.getAttributeForSysUser(TicketUtil.getTOKEN(request, null)).getId();
//		String userAccount = sessionProvider.getAttributeForSysUser(TicketUtil.getTOKEN(request, null)).getAccount();

        Map<String, Object> params = BaseController.getParameterMap(request);
//    	params.put("createBy", userId);
        PageInfo<ToolsLibraryApply> toolsLibraryList = toolsLibraryApplyService.queryPageList(params);

        return setSuccessModelMap(modelMap, toolsLibraryList);
    }

    /**
     * 获取工具申请列表
     *
     * @param request
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "/getApply", method = RequestMethod.POST)
    public Object getApply(HttpServletRequest request, ModelMap modelMap) {
//		int userId = sessionProvider.getAttributeForSysUser(TicketUtil.getTOKEN(request, null)).getId();
//		String userAccount = sessionProvider.getAttributeForSysUser(TicketUtil.getTOKEN(request, null)).getAccount();

        Map<String, Object> params = BaseController.getParameterMap(request);
//    	params.put("createBy", userId);
        PageInfo<ToolsLibraryApply> toolsLibraryList = toolsLibraryApplyService.queryApplyPageList(params);

        return setSuccessModelMap(modelMap, toolsLibraryList);
    }


    /**
     * 删除工具申请
     *
     * @param request
     * @param modelMap
     * @param id       主键id
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public Object delete(HttpServletRequest request, ModelMap modelMap, Long id) throws IOException {
//		Long userId = (long) 1;
//		Integer userId = sessionProvider.getAttributeForSysUser(TicketUtil.getTOKEN(request, null)).getId();
//		String userAccount = "admin";
        String userAccount = sessionProvider.getAttributeForSysUser(TicketUtil.getTOKEN(request, null)).getAccount();
        toolsLibraryApplyService.delete(id);

        //记录操作日志
//		String account = "admin";
        Map<String, String> map = SysLogUtil.save(request, userAccount, "工具申请删除  id:" + id);
        sysLogController.add(map);

        return setSuccessModelMap(modelMap);
    }


    /**
     * 工具申请-提交审批，通过，驳回
     *
     * @param request
     * @param modelMap
     * @param id          主键id
     * @param applyStatus 审批状态   待提交-0, 待审核-1, 审核通过-2, 审核驳回-3
     * @param remark      驳回原因
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/examination", method = RequestMethod.POST)
    public Object adopt(HttpServletRequest request, ModelMap modelMap, Long id, String applyStatus, String remark) throws Exception {
        try {
//    		Long userId = (long) 1;
//    		Long userId = sessionProvider.getAttributeForSysUser(TicketUtil.getTOKEN(request, null)).getId();
//    		String userAccount = "admin";
            String userAccount = sessionProvider.getAttributeForSysUser(TicketUtil.getTOKEN(request, null)).getAccount();
            ToolsLibraryApply toolsLibraryApply = Request2ModelUtil.covert(ToolsLibraryApply.class, request);
            toolsLibraryApply.setApprovedTime(new Date());
            toolsLibraryApply.setApplyStatus(applyStatus);
            toolsLibraryApply.setRemark(remark);
            toolsLibraryApplyService.examination(toolsLibraryApply);

            //记录操作日志

            String log = "";
            if (applyStatus.equals("0")) {
                log = "创建";
            } else if (applyStatus.equals("1")) {
                log = "提交审核";
            } else if (applyStatus.equals("2")) {
                log = "审核通过";
                toolsLibraryApplyService.addToToolsLibrary(toolsLibraryApply);
            } else if (applyStatus.equals("3")) {
                log = "审核驳回";
            }
//    		String account = "admin";
            Map<String, String> map = SysLogUtil.save(request, userAccount, "工具申请" + log + "  id:" + id);
            sysLogController.add(map);

            return setSuccessModelMap(modelMap);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * 获取工具申请的详情
     *
     * @param request
     * @param modelMap
     * @param id       主键id
     * @return
     */
    @RequestMapping(value = "/detail", method = RequestMethod.POST)
    public Object detail(HttpServletRequest request, ModelMap modelMap, @RequestParam(value = "id", required = false) Long id) {
        Map<String, Object> map = toolsLibraryApplyService.selectById(id);
        return setSuccessModelMap(modelMap, map);
    }

    /**
     * 创建工具申请
     *
     * @param request
     * @param modelMap
     * @param toolFile 工具文件
     * @param toolFile 说明文档文件
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Object add(HttpServletRequest request, ModelMap modelMap, MultipartFile toolFile, MultipartFile introFile) throws IOException {

        try {
//    		Long userId = (long) 1;
            Long userId = sessionProvider.getAttributeForSysUser(TicketUtil.getTOKEN(request, null)).getId();
//    		String userAccount = "admin";
            String userAccount = sessionProvider.getAttributeForSysUser(TicketUtil.getTOKEN(request, null)).getAccount();
            ToolsLibraryApply toolsLibraryApply = Request2ModelUtil.covert(ToolsLibraryApply.class, request);
            toolsLibraryApply.setCreateTime(new Date());
            toolsLibraryApply.setUpdateTime(new Date());
            toolsLibraryApply.setDelFlag(false);
            toolsLibraryApply.setApplyStatus("0");
            toolsLibraryApply.setCreateBy(userId);
            toolsLibraryApply.setUpdateBy(userId);
            toolsLibraryApply.setApprovedBy(userId);

            //上传工具文件
            if (toolFile != null && !toolFile.isEmpty() && StringUtils.isNotEmpty(toolFile.getOriginalFilename())) {
                FileInfo toolFileInfo = FileUtil.saveFile(toolFile, FileUtil.UPLOADPATH_TOOLS);
                Request2ModelUtil.covertObj(toolsLibraryApply, toolFileInfo);

                String realName = toolFileInfo.getRealName();
                String realNamePath = realName.substring(0, realName.indexOf("."));
                String oldPath = toolFileInfo.getFilePath();
                FileUtil.copyFile(oldPath, FileUtil.REALNAMEPATH + "/" + realNamePath + "/" + toolFileInfo.getFileName());
                toolsLibraryApply.setRealPath(FileUtil.FTPPATH + "/" + realNamePath + "/" + toolFileInfo.getFileName());

            }

            //上传说明文档
            if (introFile != null && !introFile.isEmpty() && StringUtils.isNotEmpty(introFile.getOriginalFilename())) {
                FileInfo menuFileInfo = FileUtil.saveFile(introFile, FileUtil.UPLOADPATH_TOOLS);
                toolsLibraryApply.setDocName(menuFileInfo.getName());
                toolsLibraryApply.setDocFileName(menuFileInfo.getFileName());
                toolsLibraryApply.setDocRealName(menuFileInfo.getRealName());
                toolsLibraryApply.setDocFileSuffix(menuFileInfo.getFileSuffix());
                toolsLibraryApply.setDocFileSize(menuFileInfo.getFileSize());
                toolsLibraryApply.setDocFilePath(menuFileInfo.getFilePath());

                String realName = menuFileInfo.getRealName();
                String realNamePath = realName.substring(0, realName.indexOf("."));
                String oldPath = menuFileInfo.getFilePath();
                FileUtil.copyFile(oldPath, FileUtil.REALNAMEPATH + "/" + realNamePath + "/" + menuFileInfo.getFileName());
                toolsLibraryApply.setDocRealPath(FileUtil.FTPPATH + "/" + realNamePath + "/" + menuFileInfo.getFileName());
            }
            toolsLibraryApplyService.insert(toolsLibraryApply);

//        	String account = "admin";
            Map<String, String> map = SysLogUtil.save(request, userAccount, "工具申请创建  id:" + toolsLibraryApply.getId());
            sysLogController.add(map);

            return setSuccessModelMap(modelMap);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }


    }

    /**
     * 修改工具申请
     *
     * @param request
     * @param modelMap
     * @param toolFile 工具文件
     * @param toolFile 说明文档文件
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Object update(HttpServletRequest request, ModelMap modelMap, MultipartFile toolFile, MultipartFile introFile) throws IOException {

        try {
//    		Long userId = (long) 1;
            Long userId = sessionProvider.getAttributeForSysUser(TicketUtil.getTOKEN(request, null)).getId();
//    		String userAccount = "admin";
            String userAccount = sessionProvider.getAttributeForSysUser(TicketUtil.getTOKEN(request, null)).getAccount();
            ToolsLibraryApply toolsLibraryApply = Request2ModelUtil.covert(ToolsLibraryApply.class, request);
            toolsLibraryApply.setUpdateTime(new Date());
            toolsLibraryApply.setUpdateBy(userId);
            //上传工具文件
            if (toolFile != null && !toolFile.isEmpty() && StringUtils.isNotEmpty(toolFile.getOriginalFilename())) {
                FileInfo toolFileInfo = FileUtil.saveFile(toolFile, FileUtil.UPLOADPATH_TOOLS);
                Request2ModelUtil.covertObj(toolsLibraryApply, toolFileInfo);

                String realName = toolFileInfo.getRealName();
                String realNamePath = realName.substring(0, realName.indexOf("."));
                String oldPath = toolFileInfo.getFilePath();
                FileUtil.copyFile(oldPath, FileUtil.REALNAMEPATH + "/" + realNamePath + "/" + toolFileInfo.getFileName());
                toolsLibraryApply.setRealPath(FileUtil.FTPPATH + "/" + realNamePath + "/" + toolFileInfo.getFileName());
            }


            //上传说明文档
            if (introFile != null && !introFile.isEmpty() && StringUtils.isNotEmpty(introFile.getOriginalFilename())) {
                FileInfo menuFileInfo = FileUtil.saveFile(introFile, FileUtil.UPLOADPATH_TOOLS);
                toolsLibraryApply.setDocName(menuFileInfo.getName());
                toolsLibraryApply.setDocFileName(menuFileInfo.getFileName());
                toolsLibraryApply.setDocRealName(menuFileInfo.getRealName());
                toolsLibraryApply.setDocFileSuffix(menuFileInfo.getFileSuffix());
                toolsLibraryApply.setDocFileSize(menuFileInfo.getFileSize());
                toolsLibraryApply.setDocFilePath(menuFileInfo.getFilePath());

                String realName = menuFileInfo.getRealName();
                String realNamePath = realName.substring(0, realName.indexOf("."));
                String oldPath = menuFileInfo.getFilePath();
                FileUtil.copyFile(oldPath, FileUtil.REALNAMEPATH + "/" + realNamePath + "/" + menuFileInfo.getFileName());
                toolsLibraryApply.setDocRealPath(FileUtil.FTPPATH + "/" + realNamePath + "/" + menuFileInfo.getFileName());
            }

            toolsLibraryApplyService.updateById(toolsLibraryApply);

//        	String account = "admin";
            Map<String, String> map = SysLogUtil.save(request, userAccount, "工具申请修改  id:" + toolsLibraryApply.getId());
            sysLogController.add(map);

            return setSuccessModelMap(modelMap);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

    }

    /**
     * 下载
     *
     * @param request
     * @param modelMap
     * @param url          下载路径
     * @param fileName     文件名（带后缀）
     * @param downloadPath 保存本地的路径
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/downLoad", method = RequestMethod.POST)
    public void downLoad(HttpServletRequest request, HttpServletResponse resp, String url, String fileName, String downloadPath) throws ServletException, IOException {
//		Long userId = (long) 1;
//		Long userId = sessionProvider.getAttributeForSysUser(TicketUtil.getTOKEN(request, null)).getId();
//		String userAccount = "admin";
        String userAccount = sessionProvider.getAttributeForSysUser(TicketUtil.getTOKEN(request, null)).getAccount();
        FileUtil.downLoadFile(request, resp, url, fileName);

//    	String account = "admin";
        Map<String, String> map = SysLogUtil.save(request, userAccount, "工具库下载:" + fileName);
        sysLogController.add(map);
    }

}
