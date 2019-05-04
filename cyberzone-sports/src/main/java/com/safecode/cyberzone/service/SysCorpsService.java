package com.safecode.cyberzone.service;

import java.util.List;

import com.safecode.cyberzone.pojo.SysUserCorps;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import com.safecode.cyberzone.pojo.SysCorps;
import com.safecode.cyberzone.pojo.SysUser;

@Service
public interface SysCorpsService {
    //战队管理（可参加战队的所有空闲成员）
    public List<SysUser> selectFreeUser();


    //战队管理（更新）
    public int updateCorps(String name, String logo, String describe, Long long1);

    //战队管理（添加）
    public int insertCorps(String name, String logo, String describe, boolean delflag);

    //战队管理（中间表添加）
    public int insertSysUserCorps(Long userid, Long corpsid, String identity, boolean delflag);

    //战队管理（根据name查id）
    public SysCorps selectCorpsByname(String name);

    //战队管理（假删除） corps
    public int deleteCorps(boolean delflag, Long id);

    //战队管理（假删除） usercorps
    public int deleteUserCorps(boolean delflag, Long id);

    //战队管理（查所有战队）
    public List<SysCorps> selectCorps(int pagesize, int num);

    //战队管理（查所有战队的数量）
    int selectCorpsNum();

    //战队管理（修改 删除之前的中间表战队）
    public int deleteSysCorpsUser(Long corpsid);

    //战队成员管理（假删除）
    public int deleteUserCorpsMember(boolean delflag, Long corpsid, Long userid);

    //战队成员管理（修改队长）
    public int updateMemberCaptain(Long userid, Long corpsid, Long userided);

    //修改队长
    public int updateMemberCaptain1(Long userid1, Long userid2, Long corpsid);


    //根据userid查中间表
    public SysUserCorps selectUserCorpsByUserId(Long userid);


    //战队管理（根据用户id查所在队伍信息）
    SysUserCorps selectCorpsById(long id);

    //战队管理(查询该队的队长，没有为空)
    SysUserCorps selectCorpsHead(long corpsid);

    //战队管理(查询该队的队员，是否被假删除)
    SysUserCorps selectisdelete(long id);

    //战队管理(根据战队id查询战队信息)
    SysCorps selectsyscorpsByid(long corpsid);

    //战队管理(对已删除队员赋予队长)
    int updateCorpsHeadForUid(long corpsid, long id);


    //战队管理（队员 中间表修改 针对已被删除的队员）
    int updateSysUserCorps(boolean delflag, Long corpsid, Long userid);
    
    
    
    List<SysCorps> selectCorpsScreen();
    
    SysCorps selectCorpsScreenFromLogo(String name);

}

