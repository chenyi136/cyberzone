package com.safecode.cyberzone.authorize.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.base.Preconditions;
import com.safecode.cyberzone.authorize.entity.SysUser;
import com.safecode.cyberzone.authorize.exception.ParamException;
import com.safecode.cyberzone.authorize.mapper.SysUserMapper;
import com.safecode.cyberzone.authorize.param.UserParam;
import com.safecode.cyberzone.authorize.service.SysUserService;
import com.safecode.cyberzone.authorize.utils.BeanValidator;

@Service("sysUserService")
@Transactional
public class SysUserServiceImpl implements SysUserService {

	@Autowired
	private SysUserMapper sysUserMapper;
	
	@Override
	public void save(UserParam param) {
		BeanValidator.check(param);
		if (checkEmailExist(param.getMail(), param.getId())) {
			throw new ParamException("邮箱已被占用");
		}
		if (checkTelephoneExist(param.getTelephone(), param.getId())) {
			throw new ParamException("电话已被占用");
		}

		String password = "123456";
		SysUser sysUser = SysUser.builder().username(param.getUsername()).telephone(param.getTelephone())
				.mail(param.getMail()).password(password).deptId(param.getDeptId()).status(param.getStatus())
				.remark(param.getRemark()).build();
		sysUser.setOperateIp("127.0.0.1");
		sysUser.setOperator("ludongbin");
		sysUser.setOperateTime(new Date());

		// TODO: sendEmail 邮件通知用户后保存数据库

		sysUserMapper.insertSelective(sysUser);
	}

	@Override
	public void update(UserParam param) {
		BeanValidator.check(param);
		if (checkEmailExist(param.getMail(), param.getId())) {
			throw new ParamException("邮箱已被占用");
		}
		if (checkTelephoneExist(param.getTelephone(), param.getId())) {
			throw new ParamException("电话已被占用");
		}
		SysUser before = sysUserMapper.selectByPrimaryKey(param.getId());
		Preconditions.checkNotNull(before, "待更新的用户不存在");
		SysUser after = SysUser.builder().id(param.getId()).username(param.getUsername())
				.telephone(param.getTelephone()).mail(param.getMail()).deptId(param.getDeptId())
				.status(param.getStatus()).remark(param.getRemark()).build();
		after.setOperateIp("127.0.0.1");
		after.setOperateIp("ludongbin");
		after.setOperateTime(new Date());
		sysUserMapper.updateByPrimaryKeySelective(after);
	}

	@Override
	public PageInfo<SysUser> getPageByDeptId(Integer deptId, Pageable pageable) {
		PageHelper.startPage(pageable.getPageNumber(), pageable.getPageSize());
		Page<SysUser> page = sysUserMapper.getPageByDeptId(deptId);
		return new PageInfo<SysUser>(page);
	}

	@Override
	public List<SysUser> getAll() {
		return sysUserMapper.getAll();
	}

	public boolean checkTelephoneExist(String telephone, Integer userId) {
		return sysUserMapper.countByTelephone(telephone, userId) > 0;
	}

	public boolean checkEmailExist(String mail, Integer userId) {
		return sysUserMapper.countByMail(mail, userId) > 0;
	}

}
