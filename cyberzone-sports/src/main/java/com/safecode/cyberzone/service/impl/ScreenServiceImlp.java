package com.safecode.cyberzone.service.impl;



import java.io.IOException;

import java.util.ArrayList;
import java.util.List;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.safecode.cyberzone.mapper.ScreenMapper;
import com.safecode.cyberzone.mapper.SysCorpsMapper;
import com.safecode.cyberzone.pojo.DicItem;
import com.safecode.cyberzone.pojo.ScreenConfig;
import com.safecode.cyberzone.pojo.ScreenCorpsConfig;
import com.safecode.cyberzone.pojo.ScreenInfrastructureConfig;
import com.safecode.cyberzone.pojo.ScreenInfrastructureTarget;
import com.safecode.cyberzone.pojo.ScreenInfrastructureTm;
import com.safecode.cyberzone.pojo.SysCorps;
import com.safecode.cyberzone.pojo.TargetInfrastructure;
import com.safecode.cyberzone.service.ScreenService;
import com.safecode.cyberzone.utils.CorpsFileUtils;
import com.safecode.cyberzone.utils.ScreenFileUtils;
import com.safecode.cyberzone.vo.ScreenCorps;
import com.safecode.cyberzone.vo.ScreenInfrastructure;

@Service
@Transactional
@PropertySource(value="classpath:screen.properties")
public class ScreenServiceImlp implements ScreenService{

	@Value("${screenconfigpath}")
	private String screenconfigpath;
	
	@Value("${screeninfrastructureconfig}")
	private String screeninfrastructureconfig;
	
	@Value("${screencorpsconfig}")
	private String screencorpsconfig;
	
	@Autowired
	private ScreenMapper screenMapper;
	
	@Autowired
	private SysCorpsMapper sysCorpsMapper;
	private final static Logger logger = LoggerFactory.getLogger(ScreenService.class);
	ScreenFileUtils corpsFileUtils = new ScreenFileUtils();
	
	
	@Override
	public int insertScreenConfig(String name, MultipartFile logo) {
		
		try {
			String backgroundphoto=corpsFileUtils.headphotoupdownload(logo, screenconfigpath);
			screenMapper.insertScreenConfig(name, backgroundphoto);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
		
		
	}


	@Override
	public int insertScreenCorpsConfig(Long corpsid, Long coordinatex, Long coordinatey) {
		try {
			screenMapper.insertScreenCorpsConfig(corpsid, coordinatex, coordinatey);
		} catch (Exception e) {
			e.printStackTrace();
		}



		return 0;
	}


	@Override
	public ScreenConfig selectScreenConfigName(String name) {
		// TODO Auto-generated method stub
		return screenMapper.selectScreenConfigName(name);
	}


	@Override
	public ScreenCorps selectScreenCorpsConfigId(String corpsname) {
		ScreenCorps screenCorps=new ScreenCorps();
	SysCorps sysCorps = sysCorpsMapper.selectCorpsId(corpsname);
		screenCorps.setName(corpsname);
		screenCorps.setPhoto(sysCorps.getLogo());
		
		ScreenCorpsConfig screenCorpsConfig=screenMapper.selectScreenCorpsConfigId(sysCorps.getId());
		screenCorps.setId(screenCorpsConfig.getId());
		screenCorps.setCoordinatex(screenCorpsConfig.getCoordinatex());
		screenCorps.setCoordinatey(screenCorpsConfig.getCoordinatey());
		screenCorps.setCorpsid(screenCorpsConfig.getCorpsid());
		
		
		
		// TODO Auto-generated method stub
		return screenCorps;
	}


//	@Override
//	public int insertScreenInfrastructureConfig(ScreenInfrastructure screen) {
//		List<String> list=new ArrayList<String>();
//		list.addAll(screen.getTargetname());
//
//		for(String ss:list){
//			try {
//				String backgroundphoto=corpsFileUtils.headphotoupdownload(screen.getLogo(), screeninfrastructureconfig);
//				screenMapper.insertScreenInfrastructureConfig(screen.getTmtext(), ss, screen.getCoordinatex(), screen.getCoordinatey(),backgroundphoto);
//
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			
//		}
//
//		return 0;
//	}
	
	
	/* (non-Javadoc)
	 * @see com.safecode.cyberzone.service.ScreenService#insertScreenInfrastructureConfig2(com.safecode.cyberzone.vo.ScreenInfrastructure)
	 */
	@Override
	public int insertScreenInfrastructureConfig2(ScreenInfrastructure screen) {
		// TODO Auto-generated method stub
		
		Long id=screenMapper.selectAllFromText(screen.getTmtext()).getId();
		
		screenMapper.insertScreenInfrastructureTm(id, screen.getCoordinatex(), screen.getCoordinatey());
		
		List<String> list=new ArrayList<String>();
		list.addAll(screen.getTargetname());

		for(String ss:list){
			screenMapper.updateTargetInfrastructureDicId(id, ss);
			
		}
		
		
		return 0;
	}

	@Override
	public List<ScreenInfrastructureConfig> selectScreenInfrastructureConfig(String tmtext) {
		// TODO Auto-generated method stub
		return screenMapper.selectScreenInfrastructureConfig(tmtext);
	}


	@Override
	public int updateInfrastructureConfigTargetname(ScreenInfrastructure screen) {
		String textname=screen.getTmtext();
		//数据库中的target
		List<ScreenInfrastructureConfig> target=new ArrayList<ScreenInfrastructureConfig>();
		target.addAll(screenMapper.selectScreenInfrastructureConfig(textname));
		for(ScreenInfrastructureConfig tar:target){
			screenMapper.deleteInfrastructureConfigTargetnameTrue(tar.getTmtext());
		}

		//修改过后的target
		List<String> list=new ArrayList<String>();
		list.addAll(screen.getTargetname());

		for(String ll:list){
			if(screenMapper.selectScreenInfrastructureUp(textname,ll)!=null){
				screenMapper.deleteInfrastructureConfigTargetnameFalse(textname, ll);
				continue;
			}
			screenMapper.insertScreenInfrastructureConfig(textname, ll, screen.getCoordinatex(), screen.getCoordinatey(),screen.getPhoto());
		}
		return 0;
	}
	

	@Override
	public int updateConfig(Long id,String name, MultipartFile logo) throws IOException {
		
		
		ScreenFileUtils corpsFileUtils = new ScreenFileUtils();
		
		String a=screenMapper.selectScreenConfigId(id).getBackgroundphoto();
		corpsFileUtils.deleteUpLoadFile(a);
		
		
	
		//String path="/home/hmj/saveFile/screenConfig/";
		String backgroundphoto;
		try {
			backgroundphoto = corpsFileUtils.headphotoupdownload(logo, screenconfigpath);
			screenMapper.updateScreenConfig(name,backgroundphoto, id);
			
		} catch (IOException e) {
			throw e;

		}
		
		
		return 0;
	}


	@Override
	public int updateCorpsConfig(Long corpsid, Long coordinatex, Long coordinatey) {
		
		screenMapper.updateScreenCorpsConfig(coordinatex, coordinatey, corpsid);
		
		return 0;
	}


	/* (non-Javadoc)
	 * @see com.safecode.cyberzone.service.ScreenService#selectAllScreenConfig()
	 */
	@Override
	public List<ScreenConfig> selectAllScreenConfig() {

		
		return screenMapper.selectAllScreenConfig();
	}


	/* (non-Javadoc)
	 * @see com.safecode.cyberzone.service.ScreenService#selectAllScreenCorpsConfig()
	 */
	@Override
	public List<ScreenCorps> selectAllScreenCorpsConfig() {
		
		List<ScreenCorps> list=new ArrayList<ScreenCorps>();

		List<ScreenCorpsConfig> screenCorpsConfig=screenMapper.selectAllScreenCorpsConfig();
		
		for(ScreenCorpsConfig sc:screenCorpsConfig){
			ScreenCorps scc=new ScreenCorps();
			String corpsphoto=sysCorpsMapper.selectsyscorpsByid(sc.getCorpsid()).getLogo();
			String name=sysCorpsMapper.selectsyscorpsByid(sc.getCorpsid()).getName();
			Long id=sysCorpsMapper.selectsyscorpsByid(sc.getCorpsid()).getId();
			scc.setPhoto(corpsphoto);
			scc.setName(name);
			scc.setCorpsid(id);
			scc.setId(sc.getId());
			scc.setCoordinatex(sc.getCoordinatex());
			scc.setCoordinatey(sc.getCoordinatey());
			list.add(scc);
		}
		
		
		
		return list;
	}


	/* (non-Javadoc)
	 * @see com.safecode.cyberzone.service.ScreenService#selectAllScreenInfrastructureConfig()
	 */
	@Override
	public List<ScreenInfrastructureConfig> selectAllScreenInfrastructureConfig() {
		// TODO Auto-generated method stub
		return screenMapper.selectAllScreenInfrastructureConfig();
	}


	/* (non-Javadoc)
	 * @see com.safecode.cyberzone.service.ScreenService#selectScreenInfrastructureTmByText(java.lang.String)
	 */
	@Override
	public ScreenInfrastructureTm selectScreenInfrastructureTmByText(String tmtext) {
		// TODO Auto-generated method stub
		Long id=screenMapper.selectAllFromText(tmtext).getId();
		return screenMapper.selectScreenInfrastructureTmByText(id);
	}


	

	@Override
	public int updateInfrastructure(ScreenInfrastructure screen) {
		
		//靶标分类
		String tmtext=screen.getTmtext();
		Long id=screenMapper.selectAllFromText(tmtext).getId();
		
		
		//主表修改
		screenMapper.updateScreenInfrastructuretm(screen.getCoordinatex(), screen.getCoordinatey(),id);
		
		//靶标的关联
		
		//修改后的target
		List<String> tar=screen.getTargetname();
		//修改前的target
		List<TargetInfrastructure> targetInfrastructure=screenMapper.selectAllFromDicId(id);
		for(TargetInfrastructure ta:targetInfrastructure){
			//附表修改
			screenMapper.updateTargetInfrastructureDicIdFalse(ta.getInfrastructureName());
		}
		
		for(String st:tar){
			screenMapper.updateTargetInfrastructureDicId(id, st);
		}
		

		
		return 0;
	}


	@Override
	public List<ScreenInfrastructure> selectSScreenInfrastructureAll() {

		List<ScreenInfrastructure> listAll=new ArrayList<ScreenInfrastructure>();
		
		
		List<ScreenInfrastructureTm>  tm=screenMapper.selectScreenInfrastructureTmAll();
	
		for(ScreenInfrastructureTm st1:tm){
			ScreenInfrastructure screenInfrastructure=new  ScreenInfrastructure();
			screenInfrastructure.setCoordinatex(st1.getCoordinatex());
			screenInfrastructure.setCoordinatey(st1.getCoordinatey());
			screenInfrastructure.setId(st1.getId());
			screenInfrastructure.setPhoto(st1.getPhoto());
			
			List<TargetInfrastructure> targetInfrastructure=screenMapper.selectAllFromDicId(st1.getTmid());
			List<String> targetname=new ArrayList<String>();
			String tmtext=screenMapper.selectAllFromId(st1.getTmid()).getText();
			screenInfrastructure.setTmtext(tmtext);
			for(TargetInfrastructure ta:targetInfrastructure){
				targetname.add(ta.getInfrastructureName());
			}
			screenInfrastructure.setTargetname(targetname);
			
			
			listAll.add(screenInfrastructure);
		}
		
//	
		
		return listAll;
	}


	@Override
	public List<DicItem> selectAllText() {
		// TODO Auto-generated method stub
		return screenMapper.selectAllText();
	}


	@Override
	public List<TargetInfrastructure> selectAllTarget() {
		// TODO Auto-generated method stub
		return screenMapper.selectAllTarget();
	}


	@Override
	public ScreenInfrastructure screenInfrastructureConfig(String tmtext) {
		DicItem dicItem = screenMapper.selectAllFromText(tmtext);
		
		
		Long tmid=dicItem.getId();
		ScreenInfrastructureTm screenInfrastructureTm=screenMapper.selectScreenInfrastructureTmByText(tmid);
		
		ScreenInfrastructure list=new ScreenInfrastructure();
		list.setCoordinatex(screenInfrastructureTm.getCoordinatex());
		list.setCoordinatey(screenInfrastructureTm.getCoordinatey());
		list.setTmtext(tmtext);
		list.setId(screenInfrastructureTm.getId());
		
		
		List<String> ll=new ArrayList<String>();
		List<TargetInfrastructure> target=screenMapper.selectAllFromDicId(tmid);
		for(TargetInfrastructure st:target){
			ll.add(st.getInfrastructureName());
		}
		list.setTargetname(ll);
		return list;
	}


	@Override
	public ScreenCorpsConfig selectScreenConfigAllFromCorpsId(Long corpsid) {
		// TODO Auto-generated method stub
		return screenMapper.selectScreenCorpsConfigId(corpsid);
	}


	@Override
	public DicItem selectAllFromText(String text) {
		// TODO Auto-generated method stub
		return screenMapper.selectAllFromText(text);
	}


	@Override
	public ScreenConfig selectScreenConfigId(Long id) {
		// TODO Auto-generated method stub
		return screenMapper.selectScreenConfigId(id);
	}


	

}
