package com.safecode.cyberzone.mapper;

import com.safecode.cyberzone.pojo.DicItem;
import com.safecode.cyberzone.vo.DicItemVo;
//import org.apache.ibatis.annotations.*;
import java.util.List;

/**
* <p>Title: UserService</p>  
* <p>Description:操作用户 </p>  
* @author ludongbin  
* @date 2018年6月11日  
*/
public interface DicItemMapper  {
    /**
     *
     * @param dicItemVo
     * @return
     */
     List<DicItem> findDicItemsByPage(DicItemVo dicItemVo);

    Integer findTotal(DicItemVo dicItemVo);

    DicItem findDicItemById(Integer id);

    void delDicItemById(Integer id);

    void putDicItem(DicItem dicItem);

    void addDicItem(DicItem dicItem);

    List<DicItem> findDicItems(DicItem dicItem);

    List<DicItem> findDicItemsToRmDuplicate(DicItem dicItem);

    void delDicItemByIds(List<Integer> ids);

    List<DicItem> getItemsByParentId(DicItem dicItem);

    List<DicItem> getItemsByType(DicItem dicItem);
}
