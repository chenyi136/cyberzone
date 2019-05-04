package com.safecode.cyberzone.authorize.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import com.safecode.cyberzone.authorize.entity.SysAcl;
import com.safecode.cyberzone.authorize.entity.SysUser;
import com.safecode.cyberzone.authorize.param.UserParam;
import com.safecode.cyberzone.authorize.service.SysRoleService;
import com.safecode.cyberzone.authorize.service.SysUserService;
import com.safecode.cyberzone.authorize.service.impl.SysCoreService;
import com.safecode.cyberzone.authorize.service.impl.SysTreeService;
import com.safecode.cyberzone.base.dto.ResponseData;

@RestController
@RequestMapping("/sys/user")
@CrossOrigin
public class SysUserController {

	@Autowired
	private SysUserService sysUserService;

	@Autowired
	private SysTreeService sysTreeService;

	@Autowired
	private SysRoleService sysRoleService;

	@Autowired
	private SysCoreService sysCoreService;

	/**
	 * 
	 * <p>Title: saveUser</p>  
	 * <p>Description: 新增用户</p>  
	 * @param param
	 * @return
	 */
	@PostMapping("/save")
	public ResponseData<SysUser> saveUser(@RequestBody UserParam param) {
		ResponseData<SysUser> data = new ResponseData<SysUser>();
		sysUserService.save(param);
		data.setCode(HttpStatus.OK.value());
		data.setMsg("OK");
		return data;
	}

	/**
	 * 
	 * <p>Title: updateUser</p>  
	 * <p>Description:修改用户 </p>  
	 * @param param
	 * @return
	 */
	@PostMapping("/update")
	public ResponseData<SysUser> updateUser(@RequestBody UserParam param) {
		ResponseData<SysUser> data = new ResponseData<SysUser>();
		sysUserService.update(param);
		data.setCode(HttpStatus.OK.value());
		data.setMsg("OK");
		return data;
	}

	/**
	 * 
	 * <p>Title: page</p>  
	 * <p>Description: 获取该部门用户列表</p>  
	 * @param deptId
	 * @param pageable
	 * @return
	 */
	@PostMapping("/page")
	public ResponseData<PageInfo<SysUser>> page(@RequestParam("deptId") Integer deptId,
			@PageableDefault(page = 1, size = 10, sort = "id,asc") Pageable pageable) {
		ResponseData<PageInfo<SysUser>> data = new ResponseData<PageInfo<SysUser>>();
		PageInfo<SysUser> pageInfo = sysUserService.getPageByDeptId(deptId, pageable);
		data.setCode(HttpStatus.OK.value());
		data.setMsg("OK");
		data.setData(pageInfo);
		return data;
	}

	/**
	 * 
	 * <p>Title: acls</p>  
	 * <p>Description: 获取用户权限树</p>  
	 * @param userId
	 * @return
	 */
	@PostMapping("/acls")
	public ResponseData<Map<String, Object>> acls(@RequestParam("userId") int userId) {
		ResponseData<Map<String, Object>> data = new ResponseData<Map<String, Object>>();
		Map<String, Object> map = Maps.newHashMap();
		map.put("acls", sysTreeService.userAclTree(userId));
		map.put("roles", sysRoleService.getRoleListByUserId(userId));
		data.setCode(HttpStatus.OK.value());
		data.setMsg("OK");
		data.setData(map);
		return data;
	}

	/**
	 * 
	 * <p>Title: acl</p>  
	 * <p>Description: 取出当前用户所有权限点</p>  
	 * @return
	 */
	@PostMapping("/acl/{currentUserId}")
	public ResponseData<List<SysAcl>> acl(@PathVariable int currentUserId) {
		ResponseData<List<SysAcl>> data = new ResponseData<List<SysAcl>>();
		List<SysAcl> aclList = sysCoreService.getCurrentUserAclList(currentUserId);
		data.setCode(HttpStatus.OK.value());
		data.setMsg("OK");
		data.setData(aclList);
		return data;
	}
}
