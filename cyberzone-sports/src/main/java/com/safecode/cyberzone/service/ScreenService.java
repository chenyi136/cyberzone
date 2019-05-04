package com.safecode.cyberzone.service;

import java.io.IOException;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.web.multipart.MultipartFile;

import com.safecode.cyberzone.pojo.DicItem;
import com.safecode.cyberzone.pojo.ScreenConfig;
import com.safecode.cyberzone.pojo.ScreenCorpsConfig;
import com.safecode.cyberzone.pojo.ScreenInfrastructureConfig;
import com.safecode.cyberzone.pojo.ScreenInfrastructureTm;
import com.safecode.cyberzone.pojo.TargetInfrastructure;
import com.safecode.cyberzone.vo.ScreenCorps;
import com.safecode.cyberzone.vo.ScreenInfrastructure;

public interface ScreenService {

	//大屏页面展示
	List<ScreenConfig> selectAllScreenConfig();
	//战队页面展示
	List<ScreenCorps> selectAllScreenCorpsConfig();
	//靶标页面展示
	List<ScreenInfrastructureConfig> selectAllScreenInfrastructureConfig();
	
	//字典 靶标分类显示所有
	List<DicItem> selectAllText();
	//靶标  关联靶标显示所有
	List<TargetInfrastructure> selectAllTarget();
	
	//大屏页面配置
	public int insertScreenConfig(String name,MultipartFile logo);
	//战队页面配置
	public int insertScreenCorpsConfig(Long corpsid,Long coordinatex,Long coordinatey);
	
	
	//靶标页面配置2
	public int insertScreenInfrastructureConfig2(ScreenInfrastructure screen);
	//靶标页面配置2 通过tmtext查所有
	public ScreenInfrastructureTm selectScreenInfrastructureTmByText(String tmtext);
	//靶标页面配置2  (业务的回显)
	public ScreenInfrastructure screenInfrastructureConfig(String tmtext);
	//靶标页面配置2 修改
	public int updateInfrastructure(ScreenInfrastructure screen);
	//靶标页面配置2 显示所有
	public List<ScreenInfrastructure> selectSScreenInfrastructureAll();
	//靶标  根据text差所有
	DicItem selectAllFromText(String text);
	
	
	//大屏页面配置(根据名字查所有)	
	public ScreenConfig selectScreenConfigName(String name);
	//大屏页面配置(根据id查所有)	
		public ScreenConfig selectScreenConfigId(Long id);
	//战队页面配置  回显
	public ScreenCorps selectScreenCorpsConfigId(String corpsname);
	//战队(根据corpsid查所有)	
	public ScreenCorpsConfig selectScreenConfigAllFromCorpsId(Long corpsid);
	
	//靶标页面配置(根据tmtext查所有)    没用  
	public List<ScreenInfrastructureConfig> selectScreenInfrastructureConfig(String tmtext);
	
	//靶标页面修改
	public int updateInfrastructureConfigTargetname(ScreenInfrastructure screen);
	//大屏页面修改
	public int updateConfig(Long id,String name,MultipartFile logo) throws IOException;
	//战队页面修改
	public int updateCorpsConfig(Long corpsid,Long coordinatex,Long coordinatey);
}
