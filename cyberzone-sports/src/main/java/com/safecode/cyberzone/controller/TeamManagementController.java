package com.safecode.cyberzone.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.safecode.cyberzone.dto.JsonCorps;
import com.safecode.cyberzone.mapper.SysCorpsMapper;
import com.safecode.cyberzone.pojo.SysCorps;
import com.safecode.cyberzone.pojo.SysUser;
import com.safecode.cyberzone.pojo.SysUserCorps;
import com.safecode.cyberzone.service.SysCorpsService;
import com.safecode.cyberzone.utils.CorpsFileUtils;
import com.safecode.cyberzone.vo.SysUserCorpsVo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 攻防战队管理
 *
 * @author zcj&xz BY 2018.10.16
 * @version v1.0.0
 */
		
@CrossOrigin
@Controller
@RequestMapping("/team")
public class TeamManagementController {

    @Autowired
    private SysCorpsService sysCorpsService;

  
    /**
     * 战队管理（添加）
     *
     * @param sysCorps name      sysCorps战队对象
     * @param sysCorps name      战队名称
     * @param sysCorps logo      战队标志
     * @param sysCorps describe  战队描述
     * @return 登录状态,
     * @throws Exception
     */
    @RequestMapping(value = "/addcorps", method = RequestMethod.POST)
    @ResponseBody                ///     战队   										成员
    public JsonCorps addCorps(SysCorps sysCorps) throws IOException {
        JsonCorps jsonCorps = new JsonCorps();
        if (sysCorps.getName().equals("")) {
            jsonCorps.setStatus(false);
            jsonCorps.setErrorMsg("战队不能为空");
            return jsonCorps;
        }
        if (sysCorpsService.selectCorpsByname(sysCorps.getName()) != null) {
            jsonCorps.setStatus(false);
            jsonCorps.setErrorMsg("战队已存在");
            return jsonCorps;
        } else if (sysCorps.getName() == "" || sysCorps.getName() == null) {
            jsonCorps.setStatus(false);
            jsonCorps.setErrorMsg("战队名不能为空");
            return jsonCorps;
        }
        //调用file工具类进行io读写
        if (sysCorps.getLogofile() != null) {
            CorpsFileUtils fileUtils = new CorpsFileUtils();
            String logo = fileUtils.headphotoupdownload(sysCorps.getLogofile());
            //corp表的数据插入
            sysCorpsService.insertCorps(sysCorps.getName(), logo, sysCorps.getDescribe(), false);
        } else {
            sysCorpsService.insertCorps(sysCorps.getName(), null, sysCorps.getDescribe(), false);
        }

        jsonCorps.setStatus(true);
        jsonCorps.setSuccessMsg("添加成功");
        return jsonCorps;
    }

  
     

    //战队管理（显示所有自由用户信息）
    @RequestMapping(value = "/selectfreeuser")
    @ResponseBody
    public List<SysUser> selectFreeUser() {
        List<SysUser> list = sysCorpsService.selectFreeUser();
        return list;
    }

    
    /**
     * 战队管理（显示所有战队信息）
     *
     * @param num      每页显示的数量
     * @param pagesize 需要显示的页数
     */
    @RequestMapping(value = "/selectallcorps")
    @ResponseBody
    public List<SysCorps> selectAllCorps(int pagesize, int num) {
        int page = (pagesize - 1) * num;
        List<SysCorps> list1 = sysCorpsService.selectCorps(page, num);
        return list1;
    }

    /**
     * 战队管理（按照团队id回显战队信息）
     *
     * @param corpsid 战队id
     */
    @RequestMapping(value = "/selectallcorpsbyid")
    @ResponseBody
    public SysCorps selectAllCorpsById(long corpsid) {
        SysCorps sysCorps = sysCorpsService.selectsyscorpsByid(corpsid);
        return sysCorps;
    }


    /**
     * 战队管理（战队修改）
     *
     * @param sysCorps name      战队名称
     * @param sysCorps logo      战队标志
     * @param sysCorps describe  战队描述
     * @param sysCorps listSysUser ArrayList 战队内的成员集合
     * @param sysCorps listSysUser userid    用户id
     * @param sysCorps listSysUser realname  用户名字
     * @return 修改状态,
     * @throws Exception
     */
    @RequestMapping(value = "/updatecorps")
    @ResponseBody
    public JsonCorps updateCorps(SysCorps sysCorps) throws IOException {
        JsonCorps jsonCorps = new JsonCorps();

        if (sysCorps.getId() == null) {
            jsonCorps.setStatus(false);
            jsonCorps.setErrorMsg("战队为空或者找不到");
            return jsonCorps;
        }

        SysCorps sysCorps1 = sysCorpsService.selectsyscorpsByid(sysCorps.getId());
        if (sysCorpsService.selectsyscorpsByid(sysCorps.getId()) == null) {
            jsonCorps.setStatus(false);
            jsonCorps.setErrorMsg("战队不存在");
            return jsonCorps;
        }
        if (sysCorps.getLogofile() != null) {
            CorpsFileUtils fileUtils = new CorpsFileUtils();
            //删除原有的战队logo
            boolean b = fileUtils.deleteUpLoadFile(sysCorps1.getLogo());
            //替换新的logo上传路径
            String logo = fileUtils.headphotoupdownload(sysCorps.getLogofile());
            sysCorpsService.updateCorps(sysCorps.getName(), logo, sysCorps.getDescribe(), sysCorps.getId());
        } else {
            sysCorpsService.updateCorps(sysCorps.getName(), sysCorps1.getLogo(), sysCorps.getDescribe(), sysCorps.getId());
        }

        jsonCorps.setStatus(true);
        jsonCorps.setSuccessMsg("修改成功");
        return jsonCorps;
    }

    /**
     * 战队管理（删除）
     *
     * @param corpsId 战队id
     * @return 删除状态 删除状态 0(未删):false 1(已删):true,
     * @throws Exception
     */
    @RequestMapping(value = "/deletecorps")
    @ResponseBody                ///     战队   										成员
    public JsonCorps deleteCorps(Long corpsId) {
        JsonCorps jsonCorps = new JsonCorps();
        sysCorpsService.deleteCorps(true, corpsId);
        sysCorpsService.deleteUserCorps(true, corpsId);
        jsonCorps.setStatus(true);
        jsonCorps.setSuccessMsg("删除成功");
        return jsonCorps;

    }

    /**
     * 战队成员管理（删除）
     * <p>
     * //     * @param fsdf corpsId 所在战队id
     *
     * @return 删除状态 0(未删):false 1(已删):true
     * @throws Exception
     */
    @RequestMapping(value = "/memberdelete", method = RequestMethod.POST)
    @ResponseBody
    public JsonCorps memberDelete(@RequestBody SysUserCorpsVo sysUserCorpsVo) {
        JsonCorps jsonCorps = new JsonCorps();
        
        List<SysUserCorps> sysUserCorps=sysUserCorpsVo.getSysUserCorps();
        
     

        for (SysUserCorps userCorps : sysUserCorps) {
        	
        	if(userCorps.getUserid()==null){
        		jsonCorps.setStatus(false);
        		jsonCorps.setErrorMsg("该队员没有参加战队或者已被删除");
        		return jsonCorps;
        	}
        	
            boolean delflag = sysCorpsService.selectCorpsById(userCorps.getUserid()).isDelflag();
            if (delflag == true) {
                jsonCorps.setStatus(false);
                jsonCorps.setErrorMsg("成员没找到或不能为空");
                return jsonCorps;
            }
            sysCorpsService.deleteUserCorpsMember(true, userCorps.getCorpsid(), userCorps.getUserid());
        }
        jsonCorps.setStatus(true);
        jsonCorps.setSuccessMsg("成员删除成功");
        return jsonCorps;
    }


    /**
     * 战队成员管理（团队的队员添加）
     * <p>
     * //     * @param sysCorps corpsId 所在战队id
     * //     * @param sysCorps userId  成员id
     *
     * @return 增加状态,
     * @throws Exception
     */
    @RequestMapping(value = "/memberadd", method = RequestMethod.POST)
    @ResponseBody
    public JsonCorps memberAdd(@RequestBody SysUserCorpsVo sysUserCorpsVo) {
        JsonCorps jsonCorps = new JsonCorps();
        
        List<SysUserCorps> sysUserCorps=sysUserCorpsVo.getSysUserCorps();
        
 

        for (SysUserCorps student : sysUserCorps) {
        	
        	 if (student.getUserid() == null) {
                 jsonCorps.setStatus(false);
                 jsonCorps.setErrorMsg("成员找不到或不能为空");
                 return jsonCorps;
             }
            //战队管理（根据用户id查所在队伍信息）
            SysUserCorps UserCorps = sysCorpsService.selectCorpsById(student.getUserid());
            //删除状态 0(未删):false 1(已删):true
            if (UserCorps == null) {
                sysCorpsService.insertSysUserCorps(student.getUserid(), student.getCorpsid(), "队员", false);
                //如果deflag=1  代表之前该成员参加过战队并退出
            } else if (UserCorps.isDelflag() == true) {
//                sysCorpsService.deleteUserCorpsMember(false, student.getCorpsid(), student.getUserid());
                sysCorpsService.updateSysUserCorps(true, student.getCorpsid(), student.getUserid());
            } else if (UserCorps.isDelflag() == false) {
                jsonCorps.setStatus(false);
                jsonCorps.setErrorMsg("已参加战队");
                return jsonCorps;
            }
        }
        jsonCorps.setStatus(true);
        jsonCorps.setSuccessMsg("添加成功");
        return jsonCorps;
    }

    /**
     * 战队成员管理（更换队长）
     *
     * @param userid1 更改后的队长id
     * @param corpsid 所在的战队id
     * @param userid2 修改前的队长id
     * @return 增加状态,
     * @throws Exception
     */
    @RequestMapping(value = "/memberupdatecaptain", method = RequestMethod.POST)
    @ResponseBody
    public JsonCorps memberUpdateCaptain(Long userid1, Long userid2, Long corpsid) {
        JsonCorps jsonCorps = new JsonCorps();
        sysCorpsService.updateMemberCaptain1(userid1, userid2, corpsid);
        jsonCorps.setStatus(true);
        jsonCorps.setSuccessMsg("战队修改成功");
        return jsonCorps;
    }


    /**
     * 战队成员管理（队长添加）
     *
     * @return
     */
    @RequestMapping(value = "/corpscaptainadd", method = RequestMethod.POST)
    @ResponseBody
    public JsonCorps sysCorpsHeadAdd(SysUserCorps sysUserCorps) {
        JsonCorps jsonCorps = new JsonCorps();
        SysUserCorps UserCorps = sysCorpsService.selectCorpsHead(sysUserCorps.getCorpsid());
        SysUserCorps sys = sysCorpsService.selectUserCorpsByUserId(sysUserCorps.getUserid());
//        if (sys != null) {
//            jsonCorps.setStatus(false);
//            jsonCorps.setErrorMsg("成员已参加战队");
//            return jsonCorps;
//        }
        if (UserCorps == null && sysCorpsService.selectUserCorpsByUserId(sysUserCorps.getUserid()) == null) {
            sysCorpsService.insertSysUserCorps(sysUserCorps.getUserid(), sysUserCorps.getCorpsid(), "队长", false);
            jsonCorps.setStatus(true);
            jsonCorps.setSuccessMsg("队长添加成功");
            return jsonCorps;
        } else if (sys != null && sys.isDelflag()) {
            sysCorpsService.updateCorpsHeadForUid(sysUserCorps.getCorpsid(), sysUserCorps.getUserid());
            jsonCorps.setStatus(true);
            jsonCorps.setSuccessMsg("队长添加成功");
            return jsonCorps;
        } else {
            jsonCorps.setStatus(false);
            jsonCorps.setErrorMsg("该队伍已有队长");
            return jsonCorps;
        }

    }

    /**
     * 回显战队logo图片
     *
     * @param request
     * @param resp
     * @param filepath
     * @throws Exception
     */
    @RequestMapping(value = "/teamphoto")
    @ResponseBody
    public void teamphoto(HttpServletRequest request, HttpServletResponse resp, String filepath) throws Exception {
        CorpsFileUtils corpsFileUtils = new CorpsFileUtils();
        corpsFileUtils.showImageByType(request, resp, filepath);
    }

    /**
     * 战队数量
     *
     * @throws Exception
     */
    @RequestMapping(value = "/cropsnum")
    @ResponseBody
    public int cropsnum() {
        return sysCorpsService.selectCorpsNum();
    }

    /**
     * 根据队员id查找该队员所在战队信息
     */
    @RequestMapping(value = "/selectcropsforid", method = RequestMethod.POST)
    @ResponseBody
    public SysCorps selectcropsforid(long sysUserid) {
        //战队管理（根据用户id查所在队伍信息）
        SysUserCorps UserCorps = sysCorpsService.selectCorpsById(sysUserid);
        SysCorps sysCorps = null;
        try {
            sysCorps = sysCorpsService.selectsyscorpsByid(UserCorps.getCorpsid());
            return sysCorps;
        } catch (Exception e) {
            e.printStackTrace();
            return new SysCorps();
        }
    }
    
    //大屏    查找所有战队
    @RequestMapping(value = "/selectCorpsScreen", method = RequestMethod.POST)
    @ResponseBody
    public List<SysCorps> selectCorpsScreen(){
    	List<SysCorps> list=new ArrayList<SysCorps>();
    	list.addAll(sysCorpsService.selectCorpsScreen());
		return list;
    	
    }
//    //大屏   根据战队查找图片路径
//    @RequestMapping(value = "/selectCorpsScreenFromLogo", method = RequestMethod.POST)
//    @ResponseBody
//    public String selectCorpsScreenFromLogo(String name){
//    	SysCorps sysCorps=sysCorpsService.selectCorpsScreenFromLogo(name);
//    	
//		return sysCorps.getLogo();
//    	
//    }
  
    
}