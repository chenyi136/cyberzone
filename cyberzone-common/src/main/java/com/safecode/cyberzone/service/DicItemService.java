package com.safecode.cyberzone.service;
import com.safecode.cyberzone.base.dto.ResponseData;
import com.safecode.cyberzone.pojo.DicItem;

import java.util.List;

/**
 * Created by xuq on 2018/6/21.
 */
public interface DicItemService {
    ResponseData<List<DicItem>> getDicItemsByPage(Integer page, Integer rows, DicItem dicItem);

    ResponseData<Integer> findTotal(DicItem dicItem);

    ResponseData<DicItem> delDicItemById(Integer id, DicItem dicItem) throws Exception;

    ResponseData<DicItem> putDicItem(Integer id, DicItem dicItem) throws Exception;

    ResponseData<DicItem> addDicItem(DicItem dicItem) throws Exception;

    ResponseData<List<DicItem>> getItems(DicItem dicItem);

    ResponseData<List<DicItem>> getItemsByParentId(DicItem dicItem);

    ResponseData<List<DicItem>> getItemsByType(DicItem dicItem);
}

