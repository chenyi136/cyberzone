package com.safecode.cyberzone.authorize.controller;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.assertj.core.util.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.Maps;
import com.safecode.cyberzone.authorize.dto.AclModuleLevelDto;
import com.safecode.cyberzone.authorize.entity.SysAcl;
import com.safecode.cyberzone.authorize.entity.SysRole;
import com.safecode.cyberzone.authorize.entity.SysUser;
import com.safecode.cyberzone.authorize.param.RoleParam;
import com.safecode.cyberzone.authorize.service.SysRoleService;
import com.safecode.cyberzone.authorize.service.SysUserService;
import com.safecode.cyberzone.authorize.service.impl.SysRoleAclService;
import com.safecode.cyberzone.authorize.service.impl.SysRoleUserService;
import com.safecode.cyberzone.authorize.service.impl.SysTreeService;
import com.safecode.cyberzone.authorize.utils.StringUtil;
import com.safecode.cyberzone.base.dto.ResponseData;

@RestController
@RequestMapping("/sys/role")
@CrossOrigin
public class SysRoleController {

	@Autowired
	private SysRoleService sysRoleService;

	@Autowired
	private SysTreeService sysTreeService;

	@Autowired
	private SysRoleAclService sysRoleAclService;

	@Autowired
	private SysRoleUserService sysRoleUserService;

	@Autowired
	private SysUserService sysUserService;

	
	/**
	 * 
	 * <p>Title: saveRole</p>  
	 * <p>Description: 新增角色</p>  
	 * @param param
	 * @return
	 */
	@PostMapping("/save")
	public ResponseData<SysRole> saveRole(@RequestBody RoleParam param) {
		ResponseData<SysRole> data = new ResponseData<SysRole>();
		sysRoleService.save(param);
		data.setCode(HttpStatus.OK.value());
		data.setMsg("OK");
		return data;
	}

	/**
	 * 
	 * <p>Title: updateRole</p>  
	 * <p>Description: 修改角色</p>  
	 * @param param
	 * @return
	 */
	@PostMapping("/update")
	public ResponseData<SysRole> updateRole(@RequestBody RoleParam param) {
		ResponseData<SysRole> data = new ResponseData<SysRole>();
		sysRoleService.update(param);
		data.setCode(HttpStatus.OK.value());
		data.setMsg("OK");
		return data;
	}

	/**
	 * 
	 * <p>Title: list</p>  
	 * <p>Description: 获取角色列表</p>  
	 * @return
	 */
	@GetMapping("/list")
	public ResponseData<List<SysRole>> list() {
		ResponseData<List<SysRole>> data = new ResponseData<List<SysRole>>();
		List<SysRole> list = sysRoleService.getAll();
		data.setCode(HttpStatus.OK.value());
		data.setMsg("OK");
		data.setData(list);
		return data;
	}

	/**
	 * 
	 * <p>Title: roleTree</p>  
	 * <p>Description: 取出当前角色的权限树</p>  
	 * @param currentUserId
	 * @param roleId
	 * @return
	 */
	@PostMapping("/roleTree/{currentUserId}")
	public ResponseData<List<AclModuleLevelDto>> roleTree(@PathVariable Integer currentUserId , @RequestParam("roleId") Integer roleId) {
		ResponseData<List<AclModuleLevelDto>> data = new ResponseData<List<AclModuleLevelDto>>();
		List<AclModuleLevelDto> roleTree = sysTreeService.roleTree(roleId , currentUserId);
		data.setCode(HttpStatus.OK.value());
		data.setMsg("OK");
		data.setData(roleTree);
		return data;
	}

	/**
	 * 
	 * <p>Title: changeAcls</p>  
	 * <p>Description: 根据角色ID更新权限</p>  
	 * @param roleId
	 * @param aclIds
	 * @return
	 */
	@PostMapping("/changeAcls")
	public ResponseData<SysAcl> changeAcls(@RequestParam("roleId") int roleId,
			@RequestParam(value = "aclIds", required = false, defaultValue = "") String aclIds) {
		ResponseData<SysAcl> data = new ResponseData<SysAcl>();
		List<Integer> aclIdList = StringUtil.splitToListInt(aclIds);
		sysRoleAclService.changRoleAcls(roleId, aclIdList);
		data.setCode(HttpStatus.OK.value());
		data.setMsg("OK");
		return data;
	}

	/**
	 * 
	 * <p>Title: changeUsers</p>  
	 * <p>Description: 根据角色Id更新用户</p>  
	 * @param roleId
	 * @param userIds
	 * @return
	 */
	@PostMapping("/changeUsers")
	public ResponseData<SysUser> changeUsers(@RequestParam("roleId") int roleId,
			@RequestParam(value = "userIds", required = false, defaultValue = "") String userIds) {
		ResponseData<SysUser> data = new ResponseData<SysUser>();
		List<Integer> userIdList = StringUtil.splitToListInt(userIds);
		sysRoleUserService.changeRoleUsers(roleId, userIdList);
		data.setCode(HttpStatus.OK.value());
		data.setMsg("OK");
		return data;
	}

	/**
	 * 
	 * <p>Title: users</p>  
	 * <p>Description: 根据角色id获取已选中和未选中的用户</p>  
	 * @param roleId
	 * @return
	 */
	
	@PostMapping("/users")
	public ResponseData<Map<String, List<SysUser>>> users(@RequestParam("roleId") int roleId) {
		ResponseData<Map<String, List<SysUser>>> data = new ResponseData<Map<String, List<SysUser>>>();
		List<SysUser> selectedUserList = sysRoleUserService.getListByRoleId(roleId);

		List<SysUser> allUserList = sysUserService.getAll();

		List<SysUser> unSelectedUserList = Lists.newArrayList();

		Set<Integer> selectUserIdSet = selectedUserList.stream().map(sysUser -> sysUser.getId())
				.collect(Collectors.toSet());

		for (SysUser sysUser : allUserList) {
			if (sysUser.getStatus() == 1 && !selectUserIdSet.contains(sysUser.getId())) {
				unSelectedUserList.add(sysUser);
			}
		}

		Map<String, List<SysUser>> map = Maps.newHashMap();
		map.put("selected", selectedUserList);
		map.put("unSelected", unSelectedUserList);
		data.setCode(HttpStatus.OK.value());
		data.setMsg("OK");
		data.setData(map);
		return data;
	}

}
