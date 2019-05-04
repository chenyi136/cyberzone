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

import com.safecode.cyberzone.authorize.dto.DeptLevelDto;
import com.safecode.cyberzone.authorize.entity.SysDept;
import com.safecode.cyberzone.authorize.param.DeptParam;
import com.safecode.cyberzone.authorize.service.SysDeptService;
import com.safecode.cyberzone.authorize.service.impl.SysTreeService;
import com.safecode.cyberzone.base.dto.ResponseData;

@RestController
@RequestMapping("/sys/dept")
@CrossOrigin
public class SysDeptController {
	@Autowired
	private SysDeptService sysDeptService;
	@Autowired
	private SysTreeService sysTreeService;

	/**
	 * 
	 * <p>Title: saveDept</p>  
	 * <p>Description: 新增部门</p>  
	 * @param param
	 * @return
	 */

	@PostMapping("/save")
	public ResponseData<SysDept> saveDept(@RequestBody DeptParam param) {
		ResponseData<SysDept> data = new ResponseData<SysDept>();
		sysDeptService.save(param);
		data.setCode(HttpStatus.OK.value());
		data.setMsg("ok");
		return data;
	}

	/**
	 * 
	 * <p>Title: updateDept</p>  
	 * <p>Description: 修改部门</p>  
	 * @param param
	 * @return
	 */

	@PostMapping("/update")
	public ResponseData<SysDept> updateDept(@RequestBody DeptParam param) {
		ResponseData<SysDept> data = new ResponseData<SysDept>();
		sysDeptService.update(param);
		data.setCode(HttpStatus.OK.value());
		data.setMsg("ok");
		return data;
	}

	/**
	 * 
	 * <p>Title: tree</p>  
	 * <p>Description: 部门树</p>  
	 * @return
	 */

	@GetMapping("/tree")
	public ResponseData<List<DeptLevelDto>> tree() {
		ResponseData<List<DeptLevelDto>> data = new ResponseData<List<DeptLevelDto>>();
		List<DeptLevelDto> dtoList = sysTreeService.deptTree();
		data.setCode(HttpStatus.OK.value());
		data.setMsg("ok");
		data.setData(dtoList);
		return data;
	}

	/**
	 * 
	 * <p>Title: delete</p>  
	 * <p>Description: 删除部门</p>  
	 * @param id
	 * @return
	 */

	@PostMapping("/delete")
	public ResponseData<SysDept> delete(@RequestParam("id") int id) {
		ResponseData<SysDept> data = new ResponseData<SysDept>();
		sysDeptService.delete(id);
		data.setCode(HttpStatus.OK.value());
		data.setMsg("ok");
		return data;
	}

}
