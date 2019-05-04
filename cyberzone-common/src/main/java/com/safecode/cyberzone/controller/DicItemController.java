package com.safecode.cyberzone.controller;

import com.safecode.cyberzone.base.dto.ResponseData;
import com.safecode.cyberzone.base.pojo.SysUser;
import com.safecode.cyberzone.base.utils.TicketUtil;
import com.safecode.cyberzone.global.consts.HttpCodeEnum;
import com.safecode.cyberzone.pojo.DicItem;
import com.safecode.cyberzone.service.DicItemService;
import com.safecode.cyberzone.utils.SysLogUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**  
* <p>Title: LoginController</p>  
* <p>Description:用户登录控制器 </p>  
* @author ludongbin  
* @date 2018年6月11日  
*/
//@CrossOrigin
@Api(description = "通用字典条目接口")
@Controller
@RequestMapping("api/v1/dicitems")
    public class DicItemController {

    private static Logger log = LoggerFactory.getLogger(DicItemController.class);
    @Autowired
    private DicItemService dicItemService;

    @Autowired
    private SysLogController sysLogController;

    @ApiOperation(value = "查询所有字典类型列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name="id",value = "条目id",paramType = "int"),
            @ApiImplicitParam(name="sort",value = "排序序号",paramType = "int"),
            @ApiImplicitParam(name="text",value = "条目名称",paramType = "String"),
            @ApiImplicitParam(name="value",value = "条目值",paramType = "String"),
            @ApiImplicitParam(name="dicTypeId",value = "字典类型id",paramType = "int"),
            @ApiImplicitParam(name="parentId",value = "父节点Id",paramType = "int"),
            @ApiImplicitParam(name="isLeaf",value = "是否为叶子节点",paramType = "int"),
            @ApiImplicitParam(name="state",value = "状态",paramType = "int"),

    })
    @RequestMapping(value ="getItems/{currentUserId}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseData<List<DicItem>> getItems(HttpServletRequest request , DicItem dicItem, @PathVariable("currentUserId") Long currentUserId){
        log.info("send {} request to {}", request.getMethod(), request.getRequestURL().toString());
        ResponseData<List<DicItem>> responseData = dicItemService.getItems(dicItem);
        return responseData;
    }

     /**
     * 分页查询字典类型列表
     * @author xuq
     * @date 2018/7/6 8:58
     * @param [request, page, rows, dicItem]
     * @return com.safecode.cyberzone.dto.CommonJsonResponse<com.safecode.cyberzone.pojo.DicItem>
     */
    @ApiOperation(value = "分页查询字典类型列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name="page",value = "当前页数",paramType = "int",required = true),
            @ApiImplicitParam(name="rows",value = "每页显示条数",paramType = "int",required = true),
            @ApiImplicitParam(name="id",value = "条目id",paramType = "int"),
            @ApiImplicitParam(name="sort",value = "排序序号",paramType = "int"),
            @ApiImplicitParam(name="text",value = "条目名称",paramType = "String"),
            @ApiImplicitParam(name="value",value = "条目值",paramType = "String"),
            @ApiImplicitParam(name="dicTypeId",value = "字典类型id",paramType = "int"),
            @ApiImplicitParam(name="parentId",value = "父节点Id",paramType = "int"),
            @ApiImplicitParam(name="isLeaf",value = "是否为叶子节点",paramType = "int"),
            @ApiImplicitParam(name="state",value = "状态",paramType = "int"),
    })
    @RequestMapping(value = "{currentUserId}/{page}/{rows}",method = RequestMethod.GET)
    @ResponseBody
    public ResponseData<List<DicItem>> getDicItemsByPage(HttpServletRequest request
      ,@PathVariable Integer page, @PathVariable Integer rows,DicItem dicItem,@PathVariable("currentUserId") Long currentUserId){
        log.info("send {} request to {}", request.getMethod(), request.getRequestURL().toString());
        ResponseData<List<DicItem>> responseData = dicItemService.getDicItemsByPage(page,rows,dicItem);
        return responseData;
    }

    /*
       获得通用字典条目的总条数
     * @author xuq
     * @date 2018/7/6 8:58
     * @param [request, dicItem]
     * @return com.safecode.cyberzone.dto.CommonJsonResponse<java.lang.Integer>
     */
    @ApiOperation(value = "获得通用字典条目的总条数")
    @ApiImplicitParams({
            @ApiImplicitParam(name="id",value = "条目id",paramType = "int"),
            @ApiImplicitParam(name="sort",value = "排序序号",paramType = "int"),
            @ApiImplicitParam(name="text",value = "条目名称",paramType = "String"),
            @ApiImplicitParam(name="value",value = "条目值",paramType = "String"),
            @ApiImplicitParam(name="dicTypeId",value = "字典类型id",paramType = "int"),
            @ApiImplicitParam(name="parentId",value = "父节点Id",paramType = "int"),
            @ApiImplicitParam(name="isLeaf",value = "是否为叶子节点",paramType = "int"),
            @ApiImplicitParam(name="state",value = "状态",paramType = "int"),
    })
    @RequestMapping(value = "{currentUserId}/total",method = RequestMethod.GET)
    @ResponseBody
    public  ResponseData<Integer> findTotal(HttpServletRequest request, DicItem dicItem,@PathVariable("currentUserId") Long currentUserId){
        log.info("send {} request to {}", request.getMethod(), request.getRequestURL().toString());
        ResponseData<Integer> responseData = dicItemService.findTotal(dicItem);
        return responseData;
    }

    /**
        根据id删除一条条目数据
     * @author xuq
     * @date 2018/7/6 9:00
     * @param [dicItem, id]
     * @return com.safecode.cyberzone.dto.CommonJsonResponse<com.safecode.cyberzone.pojo.DicItem>
     */
    @ApiOperation(value = "根据id删除一条条目数据")
    @ApiImplicitParam(name = "id",value = "条目id",paramType = "int",required = true)
    @RequestMapping(value = "del/{currentUserId}/{id}",method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseData<DicItem> delDicItemById(DicItem dicItem,
            @PathVariable Integer id,HttpServletRequest request,@PathVariable("currentUserId") Long currentUserId) throws Exception {
        log.info("send {} request to {}", request.getMethod(), request.getRequestURL().toString());
        ResponseData<DicItem> responseData = new ResponseData();
        try {
            responseData = dicItemService.delDicItemById(id, dicItem);
            if(responseData.getData() !=null){
                String account = "admin";
                Map<String, String> map = SysLogUtil.save(request,account,"删除字典条目id:"+id);
                sysLogController.add(map);
            }
        } catch (IOException e) {

        } catch (Exception e) {
//            e.printStackTrace();
            log.error("根据条目id "+id+"删除失败", request.getMethod(), request.getRequestURL().toString());
            responseData.setCode(HttpCodeEnum.INTERNAL_SERVER_ERROR.value());
            responseData.setMsg("删除失败");
            throw new Exception("根据id删除失败");
        }
        return  responseData;
    }

    /**
       修改条目信息
     * @author xuq
     * @date 2018/7/6 9:01
     * @param [id, dicItem]
     * @return com.safecode.cyberzone.dto.CommonJsonResponse<com.safecode.cyberzone.pojo.DicItem>
     */
    @ApiOperation(value = "根据id修改条目信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name="id",value = "条目id",paramType = "int",required = true),
            @ApiImplicitParam(name="sort",value = "排序序号",paramType = "int"),
            @ApiImplicitParam(name="text",value = "条目名称",paramType = "String"),
            @ApiImplicitParam(name="value",value = "条目值",paramType = "String"),
            @ApiImplicitParam(name="dicTypeId",value = "字典类型id",paramType = "int"),
            @ApiImplicitParam(name="parentId",value = "父节点Id",paramType = "int"),
            @ApiImplicitParam(name="isLeaf",value = "是否为叶子节点",paramType = "int"),
            @ApiImplicitParam(name="state",value = "状态",paramType = "int"),
    })
    @RequestMapping(value = "put/{currentUserId}/{id}",method = RequestMethod.PUT)
    @ResponseBody
    public ResponseData<DicItem> putDicItem(@PathVariable Integer id,
           @RequestBody DicItem dicItem,HttpServletRequest request,@PathVariable("currentUserId") Long currentUserId) throws Exception {
        ResponseData<DicItem> responseData = new ResponseData<>();
        log.info("send {} request to {}", request.getMethod(), request.getRequestURL().toString());
        try {
            responseData = dicItemService.putDicItem(id, dicItem);
            if(responseData.getData() !=null ){
                String account = "admin";
                Map<String, String> map = SysLogUtil.save(request,account,"修改字典条目id:"+id);
                sysLogController.add(map);
            }
        }catch (IOException e) {

        }
        catch (Exception e) {
//            e.printStackTrace();
            log.error("根据条目id "+id+"修改失败", request.getMethod(), request.getRequestURL().toString());
            responseData.setCode(HttpCodeEnum.INTERNAL_SERVER_ERROR.value());
            responseData.setMsg("修改失败");
            throw new Exception("根据id修改失败");
        }
        return responseData;
    }
    /**
       添加一条条目数据
     * @author xuq
     * @date 2018/7/6 9:02
     * @param [dicItem, request]
     * @return com.safecode.cyberzone.dto.CommonJsonResponse<com.safecode.cyberzone.pojo.DicItem>
     */
    @ApiOperation(value = "添加一条条目信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name="sort",value = "排序序号",paramType = "int"),
            @ApiImplicitParam(name="text",value = "条目名称",paramType = "String"),
            @ApiImplicitParam(name="value",value = "条目值",paramType = "String"),
            @ApiImplicitParam(name="dicTypeId",value = "字典类型id",paramType = "int"),
            @ApiImplicitParam(name="parentId",value = "父节点Id",paramType = "int"),
            @ApiImplicitParam(name="isLeaf",value = "是否为叶子节点",paramType = "int"),
            @ApiImplicitParam(name="state",value = "状态",paramType = "int"),
    })
    @RequestMapping(value = "add/{currentUserId}",method = RequestMethod.POST)
    @ResponseBody
    public ResponseData<DicItem> addDicItem(@RequestBody DicItem dicItem,HttpServletRequest request,@PathVariable("currentUserId") Long currentUserId) throws Exception {
        ResponseData<DicItem> responseData = new ResponseData<>();
        log.info("send {} request to {}", request.getMethod(), request.getRequestURL().toString());
        try {
            responseData = dicItemService.addDicItem(dicItem);
            String account = "admin";
            if(responseData.getData() !=null){
                Map<String, String> map = SysLogUtil.save(request,account,"添加字典条目id:"+responseData.getData().getId());
                sysLogController.add(map);
            }

        } catch (IOException e) {

        }
        catch (Exception e) {
            e.printStackTrace();
            log.error("添加失败", request.getMethod(), request.getRequestURL().toString());
            responseData.setCode(HttpCodeEnum.INTERNAL_SERVER_ERROR.value());
            responseData.setMsg("添加失败");
            throw new Exception("添加条目失败");
        }
        return  responseData;
    }

    @ApiOperation(value = "根父id查询列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name="getItemsByParentId",value = "父id",paramType = "integer",required = true),
    })
    @RequestMapping(value = "getItemsByParentId/{currentUserId}",method = RequestMethod.GET)
    @ResponseBody
    public ResponseData<List<DicItem>> getItemsByParentId( DicItem dicItem,HttpServletRequest request,@PathVariable("currentUserId") Long currentUserId){
        log.info("send {} request to {}", request.getMethod(), request.getRequestURL().toString());
        ResponseData<List<DicItem>> responseData = dicItemService.getItemsByParentId(dicItem);
        return  responseData;
    }

    @ApiOperation(value = "根据类型查询列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name="type",value = "类型",paramType = "string",required = true),
    })
    @RequestMapping(value = "getItemsByType/{currentUserId}",method = RequestMethod.GET)
    @ResponseBody
    public ResponseData<List<DicItem>> getItemsByType( DicItem dicItem,HttpServletRequest request,@PathVariable("currentUserId") Long currentUserId){
        log.info("send {} request to {}",request.getMethod(), request.getRequestURL().toString());
        ResponseData<List<DicItem>> responseData = dicItemService.getItemsByType(dicItem);
        return  responseData;
    }
//    /**
//     *  获取用户信息
//     * @param request
//     * @param user
//     * @return
//     */
//    public SysUser wasFindUserMessage(HttpServletRequest request){
//
//        SysUser  user = sessionProvider.getAttributeForSysUser(TicketUtil.getTOKEN(request, null));
//        if(wasProd){  //生产环境下
//            if(user == null){
//                return null;
//            }
//            return user;
//        }else {  //开发环境下
//            SysUser sysUser = new SysUser();
//            return  sysUser;
//        }
//
//    }
}
