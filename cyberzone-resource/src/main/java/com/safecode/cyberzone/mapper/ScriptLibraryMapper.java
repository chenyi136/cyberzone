package com.safecode.cyberzone.mapper;

import com.github.pagehelper.Page;
import com.safecode.cyberzone.pojo.ScriptLibrary;
import com.safecode.cyberzone.pojo.StrategyLibrary;
import com.safecode.cyberzone.vo.ScriptLibraryVo;

import java.util.List;
import java.util.Map;

public interface ScriptLibraryMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ScriptLibrary record);

    ScriptLibrary selectByPrimaryKey(Long id);

    List<ScriptLibrary> selectAll();

    int updateByPrimaryKey(ScriptLibrary record);
    
    List<ScriptLibrary> queryAll(ScriptLibraryVo scriptLibraryVo);

	Page<ScriptLibrary> queryPageList(ScriptLibraryVo scriptLibraryVo);
}