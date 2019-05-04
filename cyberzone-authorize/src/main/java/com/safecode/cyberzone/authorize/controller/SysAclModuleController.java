package com.safecode.cyberzone.authorize.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.safecode.cyberzone.authorize.dto.AclModuleLevelDto;
import com.safecode.cyberzone.authorize.entity.SysAclModule;
import com.safecode.cyberzone.authorize.param.AclModuleParam;
import com.safecode.cyberzone.authorize.service.SysAclModuleService;
import com.safecode.cyberzone.authorize.service.impl.SysTreeService;
import com.safecode.cyberzone.base.dto.ResponseData;

@RestController
@RequestMapping("/sys/aclModule")
@CrossOrigin
public class SysAclModuleController {
	
	@Autowired
	private SysAclModuleService sysAclModuleService;
	
	@Autowired
	private SysTreeService sysTreeService;
	
	/**
	 * 
	 * <p>Title: saveAclModule</p>  
	 * <p>Description: 新增权限模块</p>  
	 * @param param
	 * @return
	 */
	@PostMapping("/save")
	public ResponseData<SysAclModule> saveAclModule(@RequestBody AclModuleParam param){
		ResponseData<SysAclModule> data = new ResponseData<SysAclModule>();
		sysAclModuleService.save(param);
		data.setCode(HttpStatus.OK.value());
		data.setMsg("OK");
		return data;
	}
	
	/**
	 * 
	 * <p>Title: saveAclModule</p>  
	 * <p>Description: 修改权限模块</p>  
	 * @param param
	 * @return
	 */
	@PostMapping("/update")
	public ResponseData<SysAclModule> updateAclModule(@RequestBody AclModuleParam param){
		ResponseData<SysAclModule> data = new ResponseData<SysAclModule>();
		sysAclModuleService.update(param);
		data.setCode(HttpStatus.OK.value());
		data.setMsg("OK");
		return data;
	}
	
	/**
	 * 
	 * <p>Title: saveAclModule</p>  
	 * <p>Description: 权限模块树</p>  
	 * @param param
	 * @return
	 */
	@GetMapping("/tree")
	public ResponseData<List<AclModuleLevelDto>> tree(){
		ResponseData<List<AclModuleLevelDto>> data = new ResponseData<List<AclModuleLevelDto>>();
		List<AclModuleLevelDto> aclModuleTree = sysTreeService.aclModuleTree();
		data.setCode(HttpStatus.OK.value());
		data.setMsg("OK");
		data.setData(aclModuleTree);
		return data;
	}
	
	/**
	 * 
	 * <p>Title: saveAclModule</p>  
	 * <p>Description: 删除权限模块</p>  
	 * @param param
	 * @return
	 */
	@PostMapping("/delete")
    public ResponseData<SysAclModule> delete(@RequestParam("id") int id) {
		ResponseData<SysAclModule> data = new ResponseData<SysAclModule>();
        sysAclModuleService.delete(id);
        data.setCode(HttpStatus.OK.value());
        data.setMsg("OK");
        return data;
    }
}
