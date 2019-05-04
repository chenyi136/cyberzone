package com.safecode.cyberzone.mapper;

import com.github.pagehelper.Page;
import com.safecode.cyberzone.pojo.VmApply;
import java.util.List;
import java.util.Map;

public interface VmApplyMapper {
    int deleteByPrimaryKey(Long id);

    int insert(VmApply record);

    VmApply selectByPrimaryKey(Long id);

    List<VmApply> selectAll();

    int updateByPrimaryKey(VmApply record);

	Page<VmApply> queryPageList(Map<String, Object> params);

	List<VmApply> queryBy(Map<String, Object> map);

	List<Map<String, Object>> queryUserAutoApply(Map<String, Object> params);

	List<Map<String, Object>> queryCorpsVm(List<Long> corpsUserIdlist);
}