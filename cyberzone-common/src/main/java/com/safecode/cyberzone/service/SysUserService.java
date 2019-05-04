package com.safecode.cyberzone.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.PageInfo;
import com.safecode.cyberzone.base.pojo.PageAttribute;
import com.safecode.cyberzone.base.pojo.SysUser;

public interface SysUserService {

	PageInfo<SysUser> queryList(PageAttribute pageAttribute);

	SysUser selectByPrimaryKey(long parseLong);

	void insert(SysUser sysUser);

	void delete(SysUser user);

	void update(Long currentUserId, SysUser sysUser, MultipartFile avatarFile) throws IOException;

	List<SysUser> queryBy(Map<String, Object> params);

}
