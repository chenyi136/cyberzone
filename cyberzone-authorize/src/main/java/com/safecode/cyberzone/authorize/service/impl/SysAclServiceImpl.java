package com.safecode.cyberzone.authorize.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.base.Preconditions;
import com.safecode.cyberzone.authorize.entity.SysAcl;
import com.safecode.cyberzone.authorize.exception.ParamException;
import com.safecode.cyberzone.authorize.mapper.SysAclMapper;
import com.safecode.cyberzone.authorize.param.AclParam;
import com.safecode.cyberzone.authorize.service.SysAclService;
import com.safecode.cyberzone.authorize.utils.BeanValidator;

@Service("sysAclService")
@Transactional
public class SysAclServiceImpl implements SysAclService {

	@Autowired
	private SysAclMapper sysAclMapper;

	@Override
	public void save(AclParam param) {
		BeanValidator.check(param);
		if (checkExist(param.getAclModuleId(), param.getName(), param.getId())) {
			throw new ParamException("当前权限模块下面存在相同名称的权限点");
		}
		SysAcl acl = SysAcl.builder().name(param.getName()).aclModuleId(param.getAclModuleId()).url(param.getUrl())
				.type(param.getType()).status(param.getStatus()).seq(param.getSeq()).remark(param.getRemark()).build();
		acl.setCode(generateCode());
		acl.setOperator("ludongbin");
		acl.setOperateIp("127.0.0.1");
		acl.setOperateTime(new Date());
		sysAclMapper.insertSelective(acl);
	}

	@Override
	public void update(AclParam param) {
		BeanValidator.check(param);
		if (checkExist(param.getAclModuleId(), param.getName(), param.getId())) {
			throw new ParamException("当前权限模块下面存在相同名称的权限点");
		}
		SysAcl before = sysAclMapper.selectByPrimaryKey(param.getId());
		Preconditions.checkNotNull(before, "待更新的权限点不存在");

		SysAcl after = SysAcl.builder().id(param.getId()).name(param.getName()).aclModuleId(param.getAclModuleId())
				.url(param.getUrl()).type(param.getType()).status(param.getStatus()).seq(param.getSeq())
				.remark(param.getRemark()).build();
		after.setOperator("ludongbin");
		after.setOperateIp("127.0.0.1");
		after.setOperateTime(new Date());
		sysAclMapper.updateByPrimaryKeySelective(after);
	}
	
	@Override
	public PageInfo<SysAcl> getPageByAclModuleId(Integer aclModuleId, Pageable pageable) {
		PageHelper.startPage(pageable.getPageNumber(), pageable.getPageSize());
		Page<SysAcl> page = sysAclMapper.getPageByAclModuleId(aclModuleId);
		return new PageInfo<SysAcl>(page);
	}


	private boolean checkExist(Integer aclModuleId, String name, Integer id) {
		return sysAclMapper.countByNameAndAclModuleId(aclModuleId, name, id) > 0;
	}

	private String generateCode() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		return dateFormat.format(new Date()) + "_" + (int) (Math.random() * 100);
	}

	
}
