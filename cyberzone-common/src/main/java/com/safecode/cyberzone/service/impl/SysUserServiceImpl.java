package com.safecode.cyberzone.service.impl;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.safecode.cyberzone.base.pojo.FileInfo;
import com.safecode.cyberzone.base.pojo.PageAttribute;
import com.safecode.cyberzone.base.pojo.SysUser;
import com.safecode.cyberzone.base.service.SessionProvider;
import com.safecode.cyberzone.base.utils.DataUtil;
import com.safecode.cyberzone.base.utils.Request2ModelUtil;
import com.safecode.cyberzone.base.utils.TicketUtil;
import com.safecode.cyberzone.mapper.SysUserMapper;
import com.safecode.cyberzone.service.SysUserService;
import com.safecode.cyberzone.utils.FileUtil;



@Service
@Transactional
public class SysUserServiceImpl implements SysUserService {

    @Autowired
    private SysUserMapper sysUserMapper;
	@Autowired
	private SessionProvider sessionProvider;
	
	
	@Override
	public PageInfo<SysUser> queryList(PageAttribute pageAttribute) {
		// TODO Auto-generated method stub
		if (DataUtil.isEmpty(pageAttribute.getPageNum())) {
			pageAttribute.setPageNum(1);
        }
        if (DataUtil.isEmpty(pageAttribute.getPageSize())) {
        	pageAttribute.setPageSize(10);
        }
        
		PageHelper.startPage(pageAttribute);
		Page<SysUser> page = sysUserMapper.queryList();
		
		return new PageInfo<SysUser>(page);
	}
	
	@Override
	public SysUser selectByPrimaryKey(long parseLong) {
		// TODO Auto-generated method stub
		return sysUserMapper.selectByPrimaryKey(parseLong);
	}
	
	@Override
	public void delete(SysUser user) {
		// TODO Auto-generated method stub
		sysUserMapper.updateByPrimaryKey(user);
	}
	
	@Override
	public void insert(SysUser sysUser) {
		// TODO Auto-generated method stub
		sysUserMapper.insert(sysUser);
	}

	@Override
	public void update(Long currentUserId, SysUser sysUser, MultipartFile avatarFile) throws IOException {
		// TODO Auto-generated method stub
		FileInfo fileInfo = null;
		if (avatarFile != null && !avatarFile.isEmpty() && StringUtils.isNotEmpty(avatarFile.getOriginalFilename())) {
			fileInfo = FileUtil.saveFile(avatarFile, FileUtil.UPLOADPATH);
		}

    	SysUser user = sysUserMapper.selectByPrimaryKey(sysUser.getId());
		Request2ModelUtil.covertObj(user, sysUser);
		
		if(fileInfo != null) {
			user.setAvatar(fileInfo.getFilePath());
		}
		user.setUpdateTime(new Date());
		user.setUpdateBy(currentUserId);
		sysUserMapper.updateByPrimaryKey(user);
	}
	
	@Override
	public List<SysUser> queryBy(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return sysUserMapper.queryBy(params);
	}
	
}
