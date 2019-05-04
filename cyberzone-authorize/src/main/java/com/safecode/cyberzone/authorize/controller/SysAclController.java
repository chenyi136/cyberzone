package com.safecode.cyberzone.authorize.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import com.safecode.cyberzone.authorize.entity.SysAcl;
import com.safecode.cyberzone.authorize.entity.SysRole;
import com.safecode.cyberzone.authorize.param.AclParam;
import com.safecode.cyberzone.authorize.service.SysAclService;
import com.safecode.cyberzone.authorize.service.SysRoleService;
import com.safecode.cyberzone.base.dto.ResponseData;

@RestController
@RequestMapping("/sys/acl")
@CrossOrigin
public class SysAclController {

	@Autowired
	private SysAclService sysAclService;

	@Autowired
	private SysRoleService sysRoleService;

	/**
	 * 
	 * <p>Title: saveAclModule</p>  
	 * <p>Description: 新增权限点</p>  
	 * @param param
	 * @return
	 */
	@PostMapping("/save")
	public ResponseData<SysAcl> saveAcl(@RequestBody AclParam param) {
		ResponseData<SysAcl> data = new ResponseData<SysAcl>();
		sysAclService.save(param);
		data.setCode(HttpStatus.OK.value());
		data.setMsg("OK");
		return data;
	}

	/**
	 * 
	 * <p>Title: saveAclModule</p>  
	 * <p>Description: 修改权限点</p>  
	 * @param param
	 * @return
	 */
	@PostMapping("/update")
	public ResponseData<SysAcl> updateAcl(@RequestBody AclParam param) {
		ResponseData<SysAcl> data = new ResponseData<SysAcl>();
		sysAclService.update(param);
		data.setCode(HttpStatus.OK.value());
		data.setMsg("OK");
		return data;
	}

	/**
	 * 
	 * <p>Title: page</p>  
	 * <p>Description: 根据权限模块获取权限点</p>  
	 * @param aclModuleId
	 * @param pageable
	 * @return
	 */
	@PostMapping("/page")
	public ResponseData<PageInfo<SysAcl>> page(@RequestParam("aclModuleId") Integer aclModuleId,
			@PageableDefault(page = 1, size = 10, sort = "id,asc") Pageable pageable) {
		ResponseData<PageInfo<SysAcl>> data = new ResponseData<PageInfo<SysAcl>>();
		PageInfo<SysAcl> pageInfo = sysAclService.getPageByAclModuleId(aclModuleId, pageable);
		data.setCode(HttpStatus.OK.value());
		data.setMsg("OK");
		data.setData(pageInfo);
		return data;
	}

	/**
	 * 
	 * <p>Title: acls</p>  
	 * <p>Description: 获取权限点下所有的角色和用户</p>  
	 * @param aclId
	 * @return
	 */
	@PostMapping("/acls")
	public ResponseData<Map<String, Object>> acls(@RequestParam("aclId") int aclId) {
		ResponseData<Map<String, Object>> data = new ResponseData<Map<String, Object>>();
		Map<String, Object> map = Maps.newHashMap();
		List<SysRole> roleList = sysRoleService.getRoleListByAclId(aclId);
		map.put("roles", roleList);
		map.put("users", sysRoleService.getUserListByRoleList(roleList));
		data.setCode(HttpStatus.OK.value());
		data.setMsg("OK");
		data.setData(map);
		return data;
	}
}
