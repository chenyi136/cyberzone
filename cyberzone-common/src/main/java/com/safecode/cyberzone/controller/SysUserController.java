package com.safecode.cyberzone.controller;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.PageInfo;
import com.safecode.cyberzone.base.dto.ResponseData;
import com.safecode.cyberzone.base.pojo.PageAttribute;
import com.safecode.cyberzone.base.pojo.SysUser;
import com.safecode.cyberzone.base.support.Code;
import com.safecode.cyberzone.pojo.SystemLog;
import com.safecode.cyberzone.service.SysMenuService;
import com.safecode.cyberzone.service.SysUserService;
import com.safecode.cyberzone.service.SystemLogService;
import com.safecode.cyberzone.utils.FileUtil;


@RestController
@RequestMapping(value = "sysUser")
public class SysUserController {

    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysMenuService sysMenuService;
    @Autowired
	private SystemLogService systemLogService;
    
	
    /**
     * 获取用户列表
     * @param pageAttribute
     * @return
     */
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public Object get(PageAttribute pageAttribute) {
    	PageInfo<SysUser> userList = sysUserService.queryList(pageAttribute);
		return new ResponseData<Object>(Code.OK.value(), null, userList);
    }
    
    
    /**
     * 删除用户
     * @param currentUserId
     * @param id
     * @return
     */
    @RequestMapping(value = "/delete/{currentUserId}", method = RequestMethod.POST)
    public Object delete(@PathVariable("currentUserId") Long currentUserId, Long id) {
    	SysUser user = sysUserService.selectByPrimaryKey(id);

        user.setUpdateTime(new Date());
        user.setUpdateBy(currentUserId);
        user.setDelFlag(true);
    	
    	sysUserService.delete(user);
		return new ResponseData<>(Code.OK.value(), null, null);
    }
    
    
    /**
     * 添加用户
     * @param currentUserId
     * @param sysUser
     * @return
     */
    @RequestMapping(value = "/add/{currentUserId}", method = RequestMethod.POST)
    public Object add(@PathVariable("currentUserId") Long currentUserId, SysUser sysUser) {
    	Map<String, Object> params = new HashMap<>();
    	params.put("account", sysUser.getAccount());
    	
    	List<SysUser> userList = sysUserService.queryBy(params);
        if (null != userList && 0 < userList.size()) {
    		return new ResponseData<>(Code.ACCOUNT_AGAIN.value(), "账户名重复", null);
        }

        sysUser.setCreateTime(new Date());
        sysUser.setUpdateTime(new Date());
        sysUser.setCreateBy(currentUserId);
        sysUser.setUpdateBy(currentUserId);
        sysUser.setDelFlag(false);
        
        sysUserService.insert(sysUser);
		return new ResponseData<>(Code.OK.value(), null, null);
    }
    
    
    /**
     * 查看详情 或 修改回显
     * @param id
     * @return
     */
    @RequestMapping(value = "/detail", method = RequestMethod.GET)
    public Object detail(@RequestParam(value = "id", required = true) Long id) {
    	SysUser user = sysUserService.selectByPrimaryKey(id);
		return new ResponseData<SysUser>(Code.OK.value(), null, user);
    }
    
    
    /**
     * 修改用户
     * @param currentUserId
     * @param avatarFile
     * @param sysUser
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/update/{currentUserId}", method = RequestMethod.POST)
    public Object update(@PathVariable("currentUserId") Long currentUserId, MultipartFile avatarFile, SysUser sysUser) throws IOException {
    	Map<String, Object> params = new HashMap<>();
    	params.put("account", sysUser.getAccount());
    	
    	List<SysUser> userList = sysUserService.queryBy(params);
        if (null != userList && 0 < userList.size() && !sysUser.getId().equals(userList.get(0).getId())) {
    		return new ResponseData<>(Code.ACCOUNT_AGAIN.value(), "账户名重复", null);
        }
        
        sysUserService.update(currentUserId, sysUser, avatarFile);
		return new ResponseData<>(Code.OK.value(), null, null);
    }

    
    /**
     * 个人中心 或 权限控制（高亮）
     * @return
     */
    @RequestMapping(value = "/personalCenter/{currentUserId}", method = RequestMethod.GET)
    public Object personalCenter(@PathVariable("currentUserId") Long currentUserId) {
    	Map<String, Object> params = new HashMap<>();

		SysUser user = sysUserService.selectByPrimaryKey(currentUserId);
    	List subsystem = sysMenuService.queryUserSubsystem(user.getId());
    	SystemLog loginData = systemLogService.queryLastLoginData(user.getAccount());

    	params.put("user", user);
    	params.put("subsystem", subsystem);
    	params.put("loginData", loginData);

		return new ResponseData<Map<String, Object>>(Code.OK.value(), null, params);
    }
    

    /**
     * 个人中心显示头像
     * @param request
     * @param response
     * @param modelMap
     * @param filePath
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/showPicture", method = RequestMethod.GET)
    public Object showPicture(HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "filePath", required = true) String filePath) throws IOException {
    	FileUtil.onlinePreview(request, response, filePath);
		return new ResponseData<>(Code.OK.value(), null, null);
    }
}
