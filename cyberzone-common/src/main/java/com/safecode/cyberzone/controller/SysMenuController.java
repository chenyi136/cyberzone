package com.safecode.cyberzone.controller;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.PageInfo;
import com.safecode.cyberzone.base.dto.ResponseData;
import com.safecode.cyberzone.base.pojo.PageAttribute;
import com.safecode.cyberzone.base.pojo.SysUser;
import com.safecode.cyberzone.base.service.SessionProvider;
import com.safecode.cyberzone.base.support.Code;
import com.safecode.cyberzone.utils.Request2ModelUtil;
import com.safecode.cyberzone.base.utils.TicketUtil;
import com.safecode.cyberzone.controller.base.BaseController;
import com.safecode.cyberzone.pojo.SysMenu;
import com.safecode.cyberzone.pojo.SysRoleMenu;
import com.safecode.cyberzone.service.SysMenuService;
import com.safecode.cyberzone.utils.SysLogUtil;


@RestController
@RequestMapping(value = "sysMenu")
public class SysMenuController extends BaseController {

    @Autowired
    private SysMenuService sysMenuService;
    @Autowired
	private SysLogController sysLogController;
	@Autowired
	private SessionProvider sessionProvider;
	
	
    /**
     * 获取菜单列表
     * @param pageAttribute
     * @return
     */
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public Object get(PageAttribute pageAttribute) {
    	PageInfo<SysMenu> menuList = sysMenuService.queryList(pageAttribute);
		return new ResponseData<Object>(Code.OK.value(), null, menuList);
    }
	
    
    /**
     * 删除菜单
     * @param currentUserId
     * @param id
     * @return
     */
    @RequestMapping(value = "/delete/{currentUserId}", method = RequestMethod.POST)
    public Object delete(@PathVariable("currentUserId") Long currentUserId, Long id) {
    	SysMenu sysMenu = sysMenuService.selectByPrimaryKey(id);

    	if(!sysMenu.getLeafFlag()) {//树枝节点
    		//查询有没有子节点
    		List<SysMenu> menuList = sysMenuService.queryByparentId(sysMenu.getId());
            if (null != menuList && 0 < menuList.size()) {
        		return new ResponseData<>(Code.MENU_RELEVANCE.value(), "此菜单已和子菜单做了关联，请解除关联之后再进行删除操作", null);
            }
    	}else {//叶子节点
    		Map<String, Object> params = new HashMap<>();
    		params.put("menuId", sysMenu.getId());
    		List<SysRoleMenu> roleMenuList = sysMenuService.queryRoleMenu(params);
            if (null != roleMenuList && 0 < roleMenuList.size()) {
        		return new ResponseData<>(Code.MENU_ROLE_RELEVANCE.value(), "此菜单已和角色做了关联，请解除关联之后再进行删除操作", null);
            }
		}
    	
    	sysMenu.setUpdateTime(new Date());
        sysMenu.setUpdateBy(currentUserId);
    	sysMenu.setDelFlag(true);
    	
    	sysMenuService.delete(sysMenu);
		return new ResponseData<>(Code.OK.value(), null, null);
    }
    
    
    /**
     * 添加菜单
     * @param currentUserId
     * @param sysMenu
     * @return
     */
    @RequestMapping(value = "/add/{currentUserId}", method = RequestMethod.POST)
    public Object add(@PathVariable("currentUserId") Long currentUserId, SysMenu sysMenu) {
    	
    	sysMenu.setCreateTime(new Date());
    	sysMenu.setUpdateTime(new Date());
        sysMenu.setCreateBy(currentUserId);
        sysMenu.setUpdateBy(currentUserId);
    	sysMenu.setDelFlag(false);
        
    	sysMenuService.insert(sysMenu);
		return new ResponseData<>(Code.OK.value(), null, null);
    }
    
    
    /**
     * 查看详情 或 修改回显
     * @param id
     * @return
     */
    @RequestMapping(value = "/detail", method = RequestMethod.GET)
    public Object detail(@RequestParam(value = "id", required = false) Long id) {
    	Map<String, Object> params = new HashMap<>();

    	SysMenu sysMenu = sysMenuService.selectByPrimaryKey(id);
    	List<SysMenu> menuList = sysMenuService.queryAll();
    	
    	params.put("sysMenu", sysMenu);
    	params.put("menuList", menuList);
		return new ResponseData<Map<String, Object>>(Code.OK.value(), null, params);
    }
    
    
    /**
     * 修改菜单
     * @param currentUserId
     * @param sysMenu
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/update/{currentUserId}", method = RequestMethod.POST)
    public Object update(@PathVariable("currentUserId") Long currentUserId, SysMenu sysMenu) throws IOException {
    	sysMenuService.update(currentUserId, sysMenu);
		return new ResponseData<>(Code.OK.value(), null, null);
    }
    
    
    /**
     * 角色赋权限（第一步显示）查询所有父子菜单和角色关联的子菜单
     * 菜单添加（第一步显示）查询所有父子菜单
     * @param roleId 菜单添加时调用非必填
     * @return
     */
    @RequestMapping(value = "/queryRoleMenu", method = RequestMethod.POST)
    public Object queryRoleMenu(@RequestParam(value = "roleId", required = false) Long roleId) {
		Map<String, Object> responseMap = new HashMap<>();

    	//全部菜单
    	List<SysMenu> menuList = sysMenuService.queryAll();
    	responseMap.put("menuList", menuList);
    	
    	if(roleId != null) {
    		Map<String, Object> params = new HashMap<>();
    		params.put("roleId", roleId);
    		//角色关联的菜单 
    		List<SysRoleMenu> roleMenuList = sysMenuService.queryRoleMenu(params);
    		responseMap.put("roleMenuList", roleMenuList);
    	}
    	
		return new ResponseData<Map<String, Object>>(Code.OK.value(), null, responseMap);
    }
    
	
    /**
     * 角色赋权限（第二步保存）
     * @param currentUserId
     * @param roleId
     * @param menuIds
     * @return
     */
    @RequestMapping(value = "/addRoleMenu/{currentUserId}", method = RequestMethod.POST)
    public Object addRoleMenu(@PathVariable("currentUserId") Long currentUserId, Long roleId, String menuIds) {
    	//删除角色关联的所有子菜单数据
    	sysMenuService.deleteRoleMenu(roleId);
    	
		if(StringUtils.isNotEmpty(menuIds)) {
			
			String[] splitArray = menuIds.split(",");
			for(String menuId : splitArray) {
				SysRoleMenu roleMenu = new SysRoleMenu();
				
				roleMenu.setRoleId(roleId);
				roleMenu.setMenuId(Long.parseLong(menuId));
				
				roleMenu.setCreateTime(new Date());
				roleMenu.setUpdateTime(new Date());
				roleMenu.setCreateBy(currentUserId);
				roleMenu.setUpdateBy(currentUserId);
				roleMenu.setDelFlag(false);
				
				sysMenuService.insertRoleMenu(roleMenu);
			}
			
		}
		return new ResponseData<>(Code.OK.value(), null, null);
    }
    
    
    /**
     * 左侧菜单
     * @param currentUserId
     * @param subsystem
     * @return
     */
    @RequestMapping(value = "/queryUserMenu/{currentUserId}", method = RequestMethod.POST)
    public Object queryUserMenu(@PathVariable("currentUserId") Long currentUserId, Integer subsystem) {
    	Map<String, Object> params = new HashMap<>();
    	
    	params.put("currentUserId", currentUserId);
    	params.put("subsystem", subsystem);
    	
    	//根据用户账户查询用户id，根据用户id查询对应角色，根据角色查询对应菜单
    	List<SysMenu> menuList = sysMenuService.queryUserMenu(params);
    	
		return new ResponseData<List<SysMenu>>(Code.OK.value(), null, menuList);
    }
    
}
