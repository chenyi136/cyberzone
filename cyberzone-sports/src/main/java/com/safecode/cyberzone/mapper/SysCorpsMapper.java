package com.safecode.cyberzone.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.safecode.cyberzone.pojo.SysCorps;
import com.safecode.cyberzone.pojo.SysUser;
import com.safecode.cyberzone.pojo.SysUserCorps;

public interface SysCorpsMapper {

    //战队管理（可参加战队的所有空闲成员）
    public List<SysUser> selectFreeUser();


    //战队管理(修改)
    public int updateCorps(@Param("name") String name, @Param("logo") String logo, @Param("describe") String describe, @Param("id") Long id);

    //战队管理（修改 删除之前的中间表战队）
    public int deleteSysCorpsUser(@Param("corpsid") Long corpsid);

    //战队管理（假删除） corps
    public int deleteCorps(@Param("delflag") boolean delflag, @Param("id") Long id);

    //战队管理（假删除） usercorps
    public int deleteUserCorps(@Param("delflag") boolean delflag, @Param("corpsid") Long corpsid);

    //战队成员管理（假删除） 
    public int deleteUserCorpsMember(@Param("delflag") boolean delflag, @Param("corpsid") Long corpsid, @Param("userid") Long userid);

    //战队成员管理（修改队长）
    public int updateMemberCaptain(@Param("userid") Long userid, @Param("corpsid") Long corpsid, @Param("userided") Long userided);

    //战队管理（添加）
    public int insertCorps(@Param("name") String name, @Param("logo") String logo, @Param("describe") String describe, @Param("delflag") boolean delflag);

    //战队管理（队员 中间表添加）
    public int insertSysUserCorps(@Param("userid") Long userid, @Param("corpsid") Long corpsid, @Param("identity") String identity, @Param("delflag") boolean delflag);

    //战队管理（队员 中间表修改 针对已被删除的队员）
    int updateSysUserCorps(@Param("delflag") boolean delflag, @Param("corpsid") Long corpsid, @Param("userid") Long userid);

    //战队管理（根据name查id）
    public SysCorps selectCorpsId(@Param("name") String name);

    //战队管理（根据id查所有）
    public List<SysCorps> selectCorps(@Param("pagesize") int pagesize, @Param("num") int num);

    //战队管理（返回未删除队伍数量）
    public int selectCorpsNum();

    //修改队长
    public int updateMemberCaptain1(@Param("userid1") Long userid1, @Param("userid2") Long userid2, @Param("corpsid") Long corpsid);

    //根据userid查中间表
    public SysUserCorps selectUserCorpsByUserId(@Param("userid") Long userid);


    //战队管理（根据用户id查所在队伍信息）
    SysUserCorps selectCorpsById(@Param("userid") long id);

    //战队管理(查询该队的队长，没有为空)
    SysUserCorps selectCorpsHead(@Param("corpsid") long corpsid);

    //战队管理（查看该用户是否被假删除）
    SysUserCorps selectisdelete(@Param("userid") long id);

    //战队管理(根据战队id查询战队信息)
    SysCorps selectsyscorpsByid(@Param("corpsid") long corpsid);

    //战队管理(对已删除队员赋予队长)
    int updateCorpsHeadForUid(@Param("corpsid") long corpsid, @Param("userid") long id);
    
    List<SysCorps> selectCorpsScreen();
    
    SysCorps selectCorpsScreenFromLogo(@Param("name") String name);
    
}

