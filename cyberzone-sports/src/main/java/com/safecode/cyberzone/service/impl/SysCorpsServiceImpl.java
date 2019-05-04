package com.safecode.cyberzone.service.impl;

import java.util.List;

import com.safecode.cyberzone.pojo.SysUserCorps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safecode.cyberzone.mapper.SysCorpsMapper;
import com.safecode.cyberzone.pojo.SysCorps;
import com.safecode.cyberzone.pojo.SysUser;
import com.safecode.cyberzone.service.SysCorpsService;

@Service
public class SysCorpsServiceImpl implements SysCorpsService {
    @Autowired
    private SysCorpsMapper sysCorpsMapper;

    @Override
    public int updateCorps(String name, String logo, String describe, Long id) {

        sysCorpsMapper.updateCorps(name, logo, describe, id);
        return 0;
    }


    @Override
    public int insertCorps(String name, String logo, String describe, boolean delflag) {

        sysCorpsMapper.insertCorps(name, logo, describe, delflag);
        return 0;
    }


    @Override
    public SysCorps selectCorpsByname(String name) {
        return sysCorpsMapper.selectCorpsId(name);
    }


    @Override
    public int insertSysUserCorps(Long userid, Long corpsid, String identity, boolean delflag) {
        sysCorpsMapper.insertSysUserCorps(userid, corpsid, identity, delflag);
        return 0;
    }


    @Override
    public int deleteCorps(boolean delflag, Long id) {
        sysCorpsMapper.deleteCorps(delflag, id);
        return 0;
    }


    @Override
    public int deleteUserCorps(boolean delflag, Long id) {
        sysCorpsMapper.deleteUserCorps(delflag, id);
        return 0;
    }


    @Override
    public List<SysCorps> selectCorps(int pagesize, int num) {
        // TODO Auto-generated method stub
        return sysCorpsMapper.selectCorps(pagesize, num);
    }

    @Override
    public int selectCorpsNum() {
        return sysCorpsMapper.selectCorpsNum();
    }


    @Override
    public int deleteSysCorpsUser(Long corpsid) {
        sysCorpsMapper.deleteSysCorpsUser(corpsid);
        return 0;
    }


    @Override
    public int deleteUserCorpsMember(boolean delflag, Long corpsid, Long userid) {
        sysCorpsMapper.deleteUserCorpsMember(delflag, corpsid, userid);
        return 0;
    }


    @Override
    public int updateMemberCaptain(Long userid, Long corpsid, Long userided) {
        sysCorpsMapper.updateMemberCaptain(userid, corpsid, userided);
        return 0;
    }

    @Override
    public SysUserCorps selectCorpsById(long id) {
        return sysCorpsMapper.selectCorpsById(id);
    }

    @Override
    public SysUserCorps selectCorpsHead(long corpsid) {
        return sysCorpsMapper.selectCorpsHead(corpsid);
    }

    @Override
    public SysUserCorps selectisdelete(long id) {
        return sysCorpsMapper.selectisdelete(id);
    }

    @Override
    public SysCorps selectsyscorpsByid(long corpsid) {
        return sysCorpsMapper.selectsyscorpsByid(corpsid);
    }

    @Override
    public int updateCorpsHeadForUid(long corpsid, long id) {
        return sysCorpsMapper.updateCorpsHeadForUid(corpsid, id);
    }


    @Override
    public int updateSysUserCorps(boolean delflag, Long corpsid, Long userid) {
        return sysCorpsMapper.updateSysUserCorps(delflag, corpsid, userid);
    }

    @Override
    public SysUserCorps selectUserCorpsByUserId(Long userid) {
        return sysCorpsMapper.selectUserCorpsByUserId(userid);
    }


    @Override
    public int updateMemberCaptain1(Long userid1, Long userid2, Long corpsid) {
        // TODO Auto-generated method stub
        return sysCorpsMapper.updateMemberCaptain1(userid1, userid2, corpsid);
    }


    @Override
    public List<SysUser> selectFreeUser() {
        // TODO Auto-generated method stub
        return sysCorpsMapper.selectFreeUser();
    }


	@Override
	public List<SysCorps> selectCorpsScreen() {
		
		return sysCorpsMapper.selectCorpsScreen();
	}


	@Override
	public SysCorps selectCorpsScreenFromLogo(String name) {
		
		return sysCorpsMapper.selectCorpsScreenFromLogo(name);
	}


}
