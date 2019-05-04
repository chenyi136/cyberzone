package com.safecode.cyberzone.mapper;

import com.safecode.cyberzone.pojo.DicItem;

import com.safecode.cyberzone.pojo.DicType;
import com.safecode.cyberzone.vo.DicTypeVo;

import java.util.List;

/**
* <p>Title: UserService</p>  
* <p>Description:操作用户 </p>  
* @author ludongbin  
* @date 2018年6月11日  
*/ 
public interface DicTypeMapper {



    List<DicType> findDicTypesByPage(DicTypeVo dicTypeVo);


    Integer findTotal(DicType dicType);

    DicType findDicTypeById(Integer id);

    void delDicTypeById(Integer id);

    void putDicType(DicType dicType);

    void addDicType(DicType dicType);

    List<DicType> findDicTypes(DicType dicType);
}
