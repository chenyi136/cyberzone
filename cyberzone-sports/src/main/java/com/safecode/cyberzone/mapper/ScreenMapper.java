package com.safecode.cyberzone.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.safecode.cyberzone.pojo.DicItem;
import com.safecode.cyberzone.pojo.ScreenConfig;
import com.safecode.cyberzone.pojo.ScreenCorpsConfig;
import com.safecode.cyberzone.pojo.ScreenInfrastructureConfig;
import com.safecode.cyberzone.pojo.ScreenInfrastructureTarget;
import com.safecode.cyberzone.pojo.ScreenInfrastructureTm;
import com.safecode.cyberzone.pojo.TargetInfrastructure;

public interface ScreenMapper {
	
	
	
	List<ScreenConfig> selectAllScreenConfig();
	
	List<ScreenCorpsConfig> selectAllScreenCorpsConfig();
	
	List<ScreenInfrastructureConfig> selectAllScreenInfrastructureConfig();
	//
	List<DicItem> selectAllText();
	
	List<TargetInfrastructure> selectAllTarget();

	DicItem selectAllFromText(@Param("text") String text);
	
	DicItem selectAllFromId(@Param("id") Long id);
	
	TargetInfrastructure selectAllFromName(@Param("infrastructurename") String infrastructurename);
	
	List<TargetInfrastructure> selectAllFromDicId(@Param("dicid") Long dicid);

	int updateTargetInfrastructureDicId(@Param("dicid") Long dicid,@Param("infrastructurename") String infrastructurename);
	
	int updateTargetInfrastructureDicIdFalse(@Param("infrastructurename") String infrastructurename);
	
	///
	
	ScreenInfrastructureTm selectScreenInfrastructureTmByText(@Param("tmid") Long tmid);
	
	List<ScreenInfrastructureTarget> selectScreenInfrastructureTargetById(@Param("infratmid") Long infratmid);
	
	List<ScreenInfrastructureTarget> selectScreenInfrastructureTargetByIdAll(@Param("infratmid") Long infratmid);
	
	ScreenInfrastructureTarget selectTarget(@Param("infratmid") Long infratmid,@Param("targetname") String targetname);
	
	int insertScreenInfrastructureTm(@Param("tmid") Long tmid,@Param("coordinatex") Long coordinatex,@Param("coordinatey") Long coordinatey);
	
	int insertScreenInfrastructureTarget(@Param("infratmid") Long infratmid,@Param("targetname") String targetname);
	
	int deleteInfrastructureConfigTargetDelFlag(@Param("infratmid") Long infratmid);
	
	int addInfrastructureConfigTargetDelFlag(@Param("infratmid") Long infratmid,@Param("targetname") String targetname);
	
	int updateScreenInfrastructuretm(@Param("coordinatex") Long coordinatex,@Param("coordinatey") Long coordinatey,@Param("tmid") Long tmid);
	
	int updateScreenInfrastructuretarget(@Param("targetname") String targetname,@Param("infratmid") Long infratmid);
	//
	List<ScreenInfrastructureTm> selectScreenInfrastructureTmAll();
	
	List<ScreenInfrastructureTarget> selectScreenInfrastructureTargetAll();
	
	int insertScreenConfig(@Param("name") String name,@Param("backgroundphoto") String backgroundphoto);

	int insertScreenCorpsConfig(@Param("corpsid") Long corpsid,@Param("coordinatex") Long coordinatex,@Param("coordinatey") Long coordinatey);	

	int insertScreenInfrastructureConfig(@Param("tmtext") String tmtext,@Param("targetname") String targetname,@Param("coordinatex") Long coordinatex,@Param("coordinatey") Long coordinatey,@Param("photo") String photo);
	
	ScreenConfig selectScreenConfigName(@Param("name")String name);
	
	ScreenConfig selectScreenConfigId(@Param("id")Long id);

	ScreenCorpsConfig selectScreenCorpsConfigId(@Param("corpsid") Long corpsid);
	
	List<ScreenInfrastructureConfig> selectScreenInfrastructureConfig(@Param("tmtext")String tmtext);
	
	int updateScreenInfrastructureConfig(@Param("tmtext") String tmtext,@Param("targetname") String targetname,@Param("coordinatex") Long coordinatex,@Param("coordinatey") Long coordinatey);

	int deleteInfrastructureConfigTargetnameTrue(@Param("tmtext") String tmtext);

	int deleteInfrastructureConfigTargetnameFalse(@Param("tmtext") String tmtext,@Param("targetname")String targetname);
	
	ScreenInfrastructureConfig selectScreenInfrastructureUp(@Param("tmtext") String tmtext,@Param("targetname")String targetname);
	
	int updateScreenConfig(@Param("name") String name,@Param("backgroundphoto") String backgroundphoto,@Param("id") Long id);
	
	int updateScreenCorpsConfig(@Param("coordinatex") Long coordinatex,@Param("coordinatey") Long coordinatey,@Param("corpsid") Long corpsid);
}
