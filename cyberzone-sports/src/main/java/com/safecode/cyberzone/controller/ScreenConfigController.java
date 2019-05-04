package com.safecode.cyberzone.controller;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.safecode.cyberzone.dto.JsonResponse;
import com.safecode.cyberzone.pojo.DicItem;
import com.safecode.cyberzone.pojo.ScreenConfig;
import com.safecode.cyberzone.pojo.ScreenCorpsConfig;
import com.safecode.cyberzone.pojo.ScreenInfrastructureTm;
import com.safecode.cyberzone.pojo.SysCorps;
import com.safecode.cyberzone.pojo.TargetInfrastructure;
import com.safecode.cyberzone.service.ScreenService;
import com.safecode.cyberzone.service.SysCorpsService;
import com.safecode.cyberzone.vo.ScreenCorps;
import com.safecode.cyberzone.vo.ScreenInfrastructure;


/**
 * 大屏管理
 *
 * @author xz BY 2018.12.28
 * @version v1.0.0
 */
@CrossOrigin
@RestController
@RequestMapping(value = "screen")
public class ScreenConfigController {
	@Autowired
	private SysCorpsService sysCorpsService;

	@Autowired
	private ScreenService scrService;

	private static Logger log = LoggerFactory.getLogger(ScreenConfigController.class);

	// 字典显示所有
	@RequestMapping(value = "/selectAllText", method = RequestMethod.POST)
	public List<DicItem> selectAllScreenConfigsdfsdf() {
		return scrService.selectAllText();

	}

	// 靶标显示所有
	@RequestMapping(value = "/selectAllTarget", method = RequestMethod.POST)
	public List<TargetInfrastructure> selectAllTarget() {
		return scrService.selectAllTarget();

	}

	// 大屏页面展示
	@RequestMapping(value = "/selectAllScreenConfig", method = RequestMethod.POST)
	public List<ScreenConfig> selectAllScreenConfig() {
		return scrService.selectAllScreenConfig();

	}

	// 战队页面展示
	@RequestMapping(value = "/selectAllScreenCorpsConfig", method = RequestMethod.POST)
	public List<ScreenCorps> selectAllScreenCorpsConfig() {
		return scrService.selectAllScreenCorpsConfig();

	}

	// 靶标页面展示
	@RequestMapping(value = "/selectAllScreenInfrastructureConfig", method = RequestMethod.POST)
	public List<ScreenInfrastructure> selectAllScreenInfrastructureConfig() {
		return scrService.selectSScreenInfrastructureAll();
	}

	/**
	 * 大屏页面配置
	 *
	 * @param String
	 *            name 演练活动名称
	 * @param MultipartFile
	 *            logo 演练活动背景图片
	 * @throws Exception
	 * @return
	 */
	@RequestMapping(value = "/insertScreenConfig", method = RequestMethod.POST)
	public JsonResponse<ScreenConfig> insertScreenConfig(String name, MultipartFile logo) {
		JsonResponse<ScreenConfig> jsonResponse = new JsonResponse<ScreenConfig>();

		if (scrService.selectScreenConfigName(name) != null) {
			jsonResponse.setErrorMsg("已存在");
			jsonResponse.setStatus(false);
			log.error("演练活动名称已存在");
			return jsonResponse;
		}

		scrService.insertScreenConfig(name, logo);
		jsonResponse.setSuccessMsg("保存成功");
		jsonResponse.setStatus(true);
		ScreenConfig screenConfig = scrService.selectScreenConfigName(name);
		jsonResponse.setSingleDept(screenConfig);
		return jsonResponse;

	}

	/**
	 * 战队页面配置
	 *
	 * @param Long
	 *            corpsid 战队id
	 * @param Long
	 *            coordinatex 战队x坐标
	 * @param Long
	 *            coordinatey 战队y坐标
	 * @throws Exception
	 * @return
	 */
	@RequestMapping(value = "/insertScreenCorpsConfig", method = RequestMethod.POST)
	public JsonResponse<ScreenCorps> insertScreenCorpsConfig(String corpsname, Long coordinatex, Long coordinatey) {
		JsonResponse<ScreenCorps> jsonResponse = new JsonResponse<ScreenCorps>();

		SysCorps sysCorps = sysCorpsService.selectCorpsScreenFromLogo(corpsname);

		if (sysCorps == null) {
			jsonResponse.setErrorMsg("战队名不存在");
			jsonResponse.setStatus(false);
			log.error("战队不存在");
			return jsonResponse;
		}
		Long corpsid = sysCorps.getId();
		if (scrService.selectScreenConfigAllFromCorpsId(corpsid) != null) {
			jsonResponse.setErrorMsg("已存在");
			jsonResponse.setStatus(false);
			log.error("战队已存在");
			return jsonResponse;
		}

		scrService.insertScreenCorpsConfig(corpsid, coordinatex, coordinatey);
		jsonResponse.setSuccessMsg("保存成功");
		jsonResponse.setStatus(true);
		ScreenCorps screenCorpsConfig = scrService.selectScreenCorpsConfigId(corpsname);
		jsonResponse.setSingleDept(screenCorpsConfig);
		return jsonResponse;

	}

	/**
	 * 靶标页面配置2
	 *
	 * @param String
	 *            tmtext tm_dic表的靶标分类
	 * @param List<String>
	 *            targetname target_infrastructure表的靶标名称
	 * @param Long
	 *            coordinatex 靶标x坐标
	 * @param Long
	 *            coordinatey 靶标y坐标
	 * @throws Exception
	 * @return
	 */
	@RequestMapping(value = "/insertScreenInfrastuctureConfig", method = RequestMethod.POST)

	public JsonResponse<ScreenInfrastructure> insertScreeninfrastuctureConfig(
			@RequestBody ScreenInfrastructure screen) {
		JsonResponse<ScreenInfrastructure> jsonResponse = new JsonResponse<ScreenInfrastructure>();

		if (scrService.selectScreenInfrastructureTmByText(screen.getTmtext()) == null) {
			scrService.insertScreenInfrastructureConfig2(screen);

			ScreenInfrastructure ss = scrService.screenInfrastructureConfig(screen.getTmtext());
			jsonResponse.setSingleDept(ss);
			jsonResponse.setSuccessMsg("保存成功");
			jsonResponse.setStatus(true);
			return jsonResponse;
		}
		jsonResponse.setErrorMsg("已存在");
		jsonResponse.setStatus(false);
		log.error("靶标分类已存在");
		return jsonResponse;

	}
	// @RequestMapping(value = "/ssss", method = RequestMethod.POST)
	//
	// public ScreenInfrastructure ssss( String tmtext){
	// return scrService.screenInfrastructureConfig(tmtext);
	// }

	// 靶标回显
	@RequestMapping(value = "/echoScreenInfrastructure", method = RequestMethod.POST)

	public JsonResponse<ScreenInfrastructure> echoScreenInfrastucture(String tmtext) {
		JsonResponse<ScreenInfrastructure> jsonResponse = new JsonResponse<ScreenInfrastructure>();

		ScreenInfrastructureTm screenInfrastructureTm = scrService.selectScreenInfrastructureTmByText(tmtext);

		if (screenInfrastructureTm == null) {
			jsonResponse.setErrorMsg("靶标名称不存在");
			jsonResponse.setStatus(false);
			log.error("靶标不存在");
			return jsonResponse;
		}

		ScreenInfrastructure screenInfrastructure = scrService.screenInfrastructureConfig(tmtext);
		jsonResponse.setSingleDept(screenInfrastructure);
		jsonResponse.setSuccessMsg("回显成功");
		jsonResponse.setStatus(true);
		return jsonResponse;
	}

	// 战队回显
	@RequestMapping(value = "/echoScreenCorps", method = RequestMethod.POST)

	public JsonResponse<ScreenCorps> echoScreenCorps(String corpsname) {
		JsonResponse<ScreenCorps> jsonResponse = new JsonResponse<ScreenCorps>();
		SysCorps selectCorpsByname = sysCorpsService.selectCorpsByname(corpsname);
		Long corpsid = selectCorpsByname.getId();
		ScreenCorpsConfig screenCorpsConfig = scrService.selectScreenConfigAllFromCorpsId(corpsid);
		if (screenCorpsConfig == null) {
			jsonResponse.setErrorMsg("战队名称未添加");
			jsonResponse.setStatus(false);
			log.error("战队名称未添加");
			return jsonResponse;
		}

		ScreenCorps screenCorps = scrService.selectScreenCorpsConfigId(corpsname);
		jsonResponse.setSingleDept(screenCorps);
		jsonResponse.setSuccessMsg("回显成功");
		jsonResponse.setStatus(true);
		return jsonResponse;

	}

	// 大屏回显
	@RequestMapping(value = "/echoScreen", method = RequestMethod.POST)

	public JsonResponse<ScreenConfig> echoScreen(String name) {
		JsonResponse<ScreenConfig> jsonResponse = new JsonResponse<ScreenConfig>();

		ScreenConfig screenConfig = scrService.selectScreenConfigName(name);

		if (screenConfig == null) {
			jsonResponse.setErrorMsg("活动名称未添加");
			jsonResponse.setStatus(false);
			log.error("活动名称未添加");
			return jsonResponse;
		}

		jsonResponse.setSingleDept(screenConfig);
		jsonResponse.setSuccessMsg("回显成功");
		jsonResponse.setStatus(true);
		return jsonResponse;

	}

	/**
	 * 靶标页面修改2
	 *
	 * @param String
	 *            tmtext tm_dic表的靶标分类
	 * @param List<String>
	 *            targetname target_infrastructure表的靶标名称
	 * @param Long
	 *            coordinatex 战队x坐标
	 * @param Long
	 *            coordinatey 战队y坐标
	 * @throws Exception
	 * @return
	 */
	@RequestMapping(value = "/updateScreenInfrastuctureConfig", method = RequestMethod.POST)
	public JsonResponse<ScreenInfrastructure> updateScreeninfrastuctureConfigUp(
			@RequestBody ScreenInfrastructure screen) {

		JsonResponse<ScreenInfrastructure> jsonResponse = new JsonResponse<ScreenInfrastructure>();

		if (scrService.selectScreenInfrastructureTmByText(screen.getTmtext()) == null) {
			jsonResponse.setErrorMsg("靶标不存在");
			jsonResponse.setStatus(false);
			log.error("靶标不存在");
			return jsonResponse;
		}
		scrService.updateInfrastructure(screen);
		jsonResponse.setSuccessMsg("靶标修改成功");
		ScreenInfrastructure ss = scrService.screenInfrastructureConfig(screen.getTmtext());
		jsonResponse.setSingleDept(ss);
		jsonResponse.setStatus(true);
		return jsonResponse;

	}

	/**
	 * 大屏页面修改
	 *
	 * @param String
	 *            name 演练活动名称
	 * @param MultipartFile
	 *            logo 演练活动背景图片
	 * @throws Exception
	 * @return
	 */
	@RequestMapping(value = "/updateScreenConfig", method = RequestMethod.POST)
	public JsonResponse<ScreenConfig> updateScreenConfigUp(Long id, String name, MultipartFile logo)
			throws IOException {
		JsonResponse<ScreenConfig> jsonResponse = new JsonResponse<ScreenConfig>();

		if (scrService.selectScreenConfigId(id) == null) {
			jsonResponse.setErrorMsg("演练活动名称不存在，无法修改");
			jsonResponse.setStatus(false);
			log.error("演练活动名称不存在，无法修改");
			return jsonResponse;
		}

		scrService.updateConfig(id, name, logo);
		jsonResponse.setSuccessMsg("修改成功");
		jsonResponse.setStatus(true);
		ScreenConfig screenConfig = scrService.selectScreenConfigName(name);
		jsonResponse.setSingleDept(screenConfig);
		return jsonResponse;
	}

	/**
	 * 战队页面修改
	 *
	 * @param Long
	 *            corpsid 战队id
	 * @param Long
	 *            coordinatex 战队x坐标
	 * @param Long
	 *            coordinatey 战队y坐标
	 * @throws Exception
	 * @return
	 */
	@RequestMapping(value = "/updateScreenCorpsConfig", method = RequestMethod.POST)
	public JsonResponse<ScreenCorps> updateScreenCorpsConfigUp(String corpsname, Long coordinatex, Long coordinatey) {
		JsonResponse<ScreenCorps> jsonResponse = new JsonResponse<ScreenCorps>();
		Long corpsid = sysCorpsService.selectCorpsScreenFromLogo(corpsname).getId();
		if (scrService.selectScreenConfigAllFromCorpsId(corpsid) == null) {
			jsonResponse.setErrorMsg("战队名称不存在，无法修改");
			jsonResponse.setStatus(false);
			log.error("战队名称不存在，无法修改");
			return jsonResponse;
		}

		scrService.updateCorpsConfig(corpsid, coordinatex, coordinatey);
		jsonResponse.setSuccessMsg("修改成功");
		jsonResponse.setStatus(true);
		ScreenCorps screenCorpsConfig = scrService.selectScreenCorpsConfigId(corpsname);
		jsonResponse.setSingleDept(screenCorpsConfig);
		return jsonResponse;

	}
	// @Autowired
	// private ScreenMapper scs;
	// @RequestMapping(value = "/sss", method = RequestMethod.POST)
	// public void ssddp(String name){
	//
	// scs.updateTargetInfrastructureDicIdFalse(name);
	//
	// }
}
