package com.safecode.cyberzone.service.impl;

import com.netflix.discovery.util.StringUtil;
import com.safecode.cyberzone.base.dto.ResponseData;
import com.safecode.cyberzone.controller.DicItemController;
import com.safecode.cyberzone.global.consts.DicItemConsts;
import com.safecode.cyberzone.global.consts.HttpCodeEnum;
import com.safecode.cyberzone.mapper.DicItemMapper;
import com.safecode.cyberzone.pojo.DicItem;
import com.safecode.cyberzone.service.DicItemService;
import com.safecode.cyberzone.vo.DicItemVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xuq on 2018/6/21.
 */
@Service
@Transactional
public class DicItemServiceImpl implements DicItemService{

    @Autowired
    private DicItemMapper dicItemMapper;

    private static Logger log = LoggerFactory.getLogger(DicItemController.class);
    /**
     *
     * @param page
     * @param rows
     * @param dicItem
     * @return
     */
    @Override
    public ResponseData<List<DicItem>> getDicItemsByPage(Integer page, Integer rows, DicItem dicItem) {
        ResponseData<List<DicItem>> responseData = new ResponseData();
        DicItemVo dicItemVo = new DicItemVo();
        dicItemVo.setText(dicItem.getText());
        dicItemVo.setIsLeaf(dicItem.getIsLeaf());
        dicItemVo.setRows(rows);
        dicItemVo.setStartIndex((page - 1) * rows);
        List<DicItem> dicItems = dicItemMapper.findDicItemsByPage(dicItemVo);
        responseData.setData(dicItems);

        return responseData;
    }

    /*
     * @author xuq
     * @date 2018/6/22 10:14
     * @param [dicItem]
     * @return com.safecode.cyberzone.dto.JsonResponse<com.safecode.cyberzone.pojo.DicItem>
     */
    @Override
    public ResponseData<Integer> findTotal(DicItem dicItem) {
        ResponseData<Integer> responseData = new ResponseData<>();
        DicItemVo dicItemVo = new DicItemVo();
        dicItemVo.setDicTypeId(dicItem.getDicTypeId());
        Integer total = dicItemMapper.findTotal(dicItemVo);
        responseData.setCode(total);
        return  responseData;
    }

    @Override
    public ResponseData<DicItem> delDicItemById(Integer id, DicItem dicItem) throws Exception {
        ResponseData<DicItem> responseData = new ResponseData<>();

        if(id ==null ){
            responseData.setCode(HttpCodeEnum.INTERNAL_SERVER_ERROR.value());
            responseData.setMsg("id不能为空");
            return responseData;
        }
        DicItem d = dicItemMapper.findDicItemById(id);
        if(d == null){
            responseData.setCode(HttpCodeEnum.INTERNAL_SERVER_ERROR.value());
            responseData.setMsg("根据id没有查询到数据，不能删除");
            return  responseData;
        }
        //如果该节点是叶子节点，查询父节点是否还有子节点，有子节点父节点就还是根节点，
        // 如果没有子节点，改为叶子节点

        if (DicItemConsts.LEAF_NODE_YES == d.getIsLeaf()){ //是叶子节点
            DicItem dicItemBrother = new DicItem();
            dicItemBrother.setParentId(d.getParentId());
            List<DicItem> dicItemBrothers = dicItemMapper.findDicItems(dicItemBrother);
            if (dicItemBrothers !=null &&dicItemBrothers.size()<2){
                DicItem dicItemParent = dicItemMapper.findDicItemById(d.getParentId());
                if (dicItemParent !=null){
                    dicItemParent.setIsLeaf(DicItemConsts.LEAF_NODE_YES);
                    dicItemMapper.putDicItem(dicItemParent);
                }
            }
        }else { //不是叶子节点，迭代删除子孙节点
            List<Integer> ids = new ArrayList<>();
            getleafNodeLists(d.getId(), ids);
            if(ids!=null && ids.size()>0){
                dicItemMapper.delDicItemByIds(ids);
            }
            //查询还有没有兄弟节点
            DicItem dicItemBrother = new DicItem();
            dicItemBrother.setParentId(d.getParentId());
            List<DicItem> dicItemBrothers = dicItemMapper.findDicItems(dicItemBrother);
            if (dicItemBrothers !=null &&dicItemBrothers.size()<2){
                DicItem dicItemParent = dicItemMapper.findDicItemById(d.getParentId());
                if (dicItemParent !=null){
                    dicItemParent.setIsLeaf(DicItemConsts.LEAF_NODE_YES);
                    dicItemMapper.putDicItem(dicItemParent);
                }
            }
        }
        dicItemMapper.delDicItemById(id);
        responseData.setData(d);
        return responseData;

    }

    @Override
    public ResponseData<DicItem> putDicItem(Integer id, DicItem dicItem) throws Exception {
        ResponseData<DicItem> responseData = new ResponseData<>();

        dicItem.setId(id);
        if(id ==null ){
            responseData.setCode(HttpCodeEnum.INTERNAL_SERVER_ERROR.value());
            responseData.setMsg("id不能为空");
            return responseData;
        }
        DicItem d = dicItemMapper.findDicItemById(id);

        if(d == null){
            responseData.setCode(HttpCodeEnum.INTERNAL_SERVER_ERROR.value());
            responseData.setMsg("数据表中没有该条数据，不能修改");
            return  responseData;
        }
        //验证类型名称，排序序号等
        boolean wasCheckPass = toCheckDuplicate(dicItem,responseData);
        if(wasCheckPass){
            return responseData;
        }
        dicItem.setId(id);
        dicItemMapper.putDicItem(dicItem);
        DicItem dicItemUpdate = dicItemMapper.findDicItemById(id);
        responseData.setData(dicItem);

        return responseData;
    }

    @Override
    public ResponseData<DicItem> addDicItem(DicItem dicItem) throws Exception{
        ResponseData<DicItem> responseData = new ResponseData<>();

        if(dicItem.getId() !=null){
            responseData.setCode(HttpCodeEnum.INTERNAL_SERVER_ERROR.value());
            responseData.setMsg("违规插入数据！");
            return responseData;
        }
        //默认为叶子节点
        dicItem.setIsLeaf(DicItemConsts.LEAF_NODE_YES);

        boolean wasCheckPass = toCheckDuplicate(dicItem,responseData);
        if(wasCheckPass){
            return responseData;
        }
        //查询父节点，改为根节点
        if(dicItem.getParentId()!=null){
            DicItem dicItemParent = dicItemMapper.findDicItemById(dicItem.getParentId());
            if (dicItemParent!=null && dicItemParent.getIsLeaf()==DicItemConsts.LEAF_NODE_YES){
                dicItemParent.setIsLeaf(DicItemConsts.LEAF_NODE_NO);
                dicItemMapper.putDicItem(dicItemParent);
            }
        }

        //默认为叶子节点
        dicItem.setIsLeaf(DicItemConsts.LEAF_NODE_YES);
        dicItemMapper.addDicItem(dicItem);
        //查询父节点，改为根节点
        if(dicItem.getParentId()!=null){
            DicItem dicItemParent = dicItemMapper.findDicItemById(dicItem.getParentId());
            if (dicItemParent!=null && dicItemParent.getIsLeaf()==DicItemConsts.LEAF_NODE_YES){
                dicItemParent.setIsLeaf(DicItemConsts.LEAF_NODE_NO);
                dicItemMapper.putDicItem(dicItemParent);
            }
        }
        responseData.setData(dicItem);

        return  responseData;
    }
    /*
        根据条件查询所有
     * @author xuq
     * @date 2018/7/16 11:22
     * @param [dicItem]
     * @return com.safecode.cyberzone.dto.CommonJsonResponse<com.safecode.cyberzone.pojo.DicItem>
     */
    @Override
    public ResponseData<List<DicItem>> getItems(DicItem dicItem) {
        ResponseData<List<DicItem>> responseDate = new ResponseData();
        List<DicItem> dicItems = dicItemMapper.findDicItems(dicItem);
        responseDate.setData(dicItems);
        return responseDate;
    }

    @Override
    public ResponseData< List<DicItem>> getItemsByParentId(DicItem dicItem) {
        //判断是否有parentId
        ResponseData< List<DicItem>> responseData = new ResponseData();
        if(StringUtils.isEmpty(dicItem.getParentId())){
            responseData.setMsg("父id不能为空");
            responseData.setCode(HttpCodeEnum.INTERNAL_SERVER_ERROR.value());
            return responseData;
        }
        try {
            List<DicItem> dicItems = this.dicItemMapper.getItemsByParentId(dicItem);
            responseData.setData(dicItems);
            return responseData;
        }catch (Exception e){
            responseData.setCode(HttpCodeEnum.INTERNAL_SERVER_ERROR.value());
            responseData.setMsg("根据类型查询数据失败");
            return  responseData;
        }
    }

    @Override
    public ResponseData< List<DicItem>>  getItemsByType(DicItem dicItem) {
        ResponseData< List<DicItem>>  responseData = new ResponseData<>();
        if(StringUtils.isEmpty(dicItem.getType())){
            responseData.setCode(HttpCodeEnum.INTERNAL_SERVER_ERROR.value());
            responseData.setMsg("类型不能为空");
            return responseData;
        }
        List<DicItem> dicItems = this.dicItemMapper.getItemsByType(dicItem);
        if(dicItems !=null && dicItems.size()>0){
            DicItem d = new DicItem();
            d.setParentId(dicItems.get(0).getId());
            List<DicItem> itemList =  this.dicItemMapper.getItemsByParentId(d);
            responseData.setData(itemList);
        }
        //commonJsonResponse.setDeptList(dicItems);
        return responseData;

    }

    /*
       根据父id获得所有的子孙叶子节点
     * @author xuq
     * @date 2018/7/16 14:32
     * @param [parendId, list]
     * @return void
     */
    public void getleafNodeLists(Integer parendId,List list){
        DicItem itemParent = new DicItem();
        itemParent.setParentId(parendId);
        List<DicItem> dicItems = dicItemMapper.findDicItems(itemParent);
        for (DicItem dicItem : dicItems){
            if (DicItemConsts.LEAF_NODE_YES ==dicItem.getIsLeaf()){ //叶子节点
                list.add(dicItem.getId());
            }else {
                getleafNodeLists(dicItem.getId(),list);
            }
        }
    }

    public boolean toCheckDuplicate( DicItem dicItem,ResponseData<DicItem> responseData){

        if(dicItem.getParentId() ==null){
            responseData.setCode(HttpCodeEnum.INTERNAL_SERVER_ERROR.value());
            responseData.setMsg("父id不能为空");
            return true;
        }

        if(dicItem.getParentId() == 0){//顶级节点
            if(org.apache.commons.lang.StringUtils.isEmpty(dicItem.getType())){
                responseData.setCode(HttpCodeEnum.INTERNAL_SERVER_ERROR.value());
                responseData.setMsg("类型序号不能为空！");
                return true;
            }
            DicItem dicItemValidate = new DicItem();
            dicItemValidate.setParentId(dicItem.getParentId());
            dicItemValidate.setType(dicItem.getType());
            List<DicItem> dicItems = dicItemMapper.findDicItemsToRmDuplicate(dicItemValidate);

            //当修改时，不对本条数据的重复属性校验
            if(!StringUtils.isEmpty(dicItem.getId())){
                DicItem d = dicItemMapper.findDicItemById(dicItem.getId());
                dicItems.remove(d);
            }
            if(dicItems!=null && dicItems.size()>0){
                responseData.setCode(HttpCodeEnum.INTERNAL_SERVER_ERROR.value());
                responseData.setMsg("类型序号不能重复！");
                return true;
            }
        }
        DicItem dicItemValidate = new DicItem();
        dicItemValidate.setText(dicItem.getText());
        dicItemValidate.setParentId(dicItem.getParentId());
        List<DicItem> dicItems = dicItemMapper.findDicItemsToRmDuplicate(dicItemValidate);
        //当修改时，不对本条数据的重复属性校验
        if(!StringUtils.isEmpty(dicItem.getId())){
            DicItem d = dicItemMapper.findDicItemById(dicItem.getId());
            dicItems.remove(d);
        }
        if(dicItems!=null && dicItems.size()>0){
            responseData.setCode(HttpCodeEnum.INTERNAL_SERVER_ERROR.value());
            responseData.setMsg("条目名称不能重复！");
            return true;
        }
        return  false;
    }

    //

}
