package com.safecode.cyberzone.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.safecode.cyberzone.base.dto.ResponseData;
import com.safecode.cyberzone.base.pojo.PageAttribute;
import com.safecode.cyberzone.base.support.Code;
import com.safecode.cyberzone.pojo.SysRole;
import com.safecode.cyberzone.pojo.SysUserRole;
import com.safecode.cyberzone.service.SysRoleService;



@RestController
@RequestMapping(value = "sysRole")
public class SysRoleController {

	
    @Autowired
    private SysRoleService sysRoleService;
	
	
    /**
     * 获取角色列表
     * @param pageAttribute
     * @return
     */
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public Object get(PageAttribute pageAttribute) {
    	PageInfo<SysRole> roleList = sysRoleService.queryList(pageAttribute);
		return new ResponseData<Object>(Code.OK.value(), null, roleList);
    }
    
    
    /**
     * 添加角色
     * @param currentUserId
     * @param sysRole
     * @return
     */
    @RequestMapping(value = "/add/{currentUserId}", method = RequestMethod.POST)
    public Object add(@PathVariable("currentUserId") Long currentUserId, SysRole sysRole) {
        sysRole.setCreateTime(new Date());
        sysRole.setUpdateTime(new Date());
        sysRole.setCreateBy(currentUserId);
        sysRole.setUpdateBy(currentUserId);
        sysRole.setDelFlag(false);
        
        sysRoleService.insert(sysRole);
		return new ResponseData<>(Code.OK.value(), null, null);
    }
    
    
    /**
     * 查询所有角色和用户选中的角色
     * @param userId
     * @return
     */
    @RequestMapping(value = "/queryUserRole", method = RequestMethod.POST)
    public ResponseData<Map<String, Object>> queryUserRole(Long userId) {
    	Map<String, Object> params = new HashMap<>();
    	
    	//全部角色
    	List<SysRole> roleList = sysRoleService.queryAll();
    	params.put("roleList", roleList);
    	
    	//用户选中的数据
    	List<SysUserRole> userRoleList = sysRoleService.queryUserRole(userId);
    	params.put("userRoleList", userRoleList);
    	
		return new ResponseData<Map<String, Object>>(Code.OK.value(), null, params);
    }
    
    
    /**
     * 用户赋角色
     * @param currentUserId
     * @param userId
     * @param roleIds
     * @return
     */
    @RequestMapping(value = "/addUserRole/{currentUserId}", method = RequestMethod.POST)
    public Object addUserRole(@PathVariable("currentUserId") Long currentUserId, Long userId, String roleIds) {

    	//删除用户关联的所有角色数据
		sysRoleService.deleteUserRole(userId);
    	
		if(StringUtils.isNotEmpty(roleIds)) {
			
			String[] splitArray = roleIds.split(",");
			for(String roleId : splitArray) {
				SysUserRole userRole = new SysUserRole();
				
				userRole.setUserId(userId);
				userRole.setRoleId(Long.parseLong(roleId));
				
				userRole.setCreateTime(new Date());
				userRole.setUpdateTime(new Date());
				userRole.setCreateBy(currentUserId);
				userRole.setUpdateBy(currentUserId);
				userRole.setDelFlag(false);
				
				sysRoleService.insertUserRole(userRole);
			}
			
		}
		return new ResponseData<>(Code.OK.value(), null, null);
    }
    
}
