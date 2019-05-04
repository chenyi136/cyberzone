package com.safecode.cyberzone.service.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.safecode.cyberzone.base.pojo.FileInfo;
import com.safecode.cyberzone.base.pojo.SysUser;
import com.safecode.cyberzone.base.service.SessionProvider;
import com.safecode.cyberzone.base.utils.DataUtil;
import com.safecode.cyberzone.base.utils.Request2ModelUtil;
import com.safecode.cyberzone.base.utils.TicketUtil;
import com.safecode.cyberzone.base.utils.XmlUtil;
import com.safecode.cyberzone.controller.SysLogController;
import com.safecode.cyberzone.mapper.BugCnvdMapper;
import com.safecode.cyberzone.mapper.BugCycleMapper;
import com.safecode.cyberzone.mapper.BugNvdMapper;
import com.safecode.cyberzone.pojo.BugCnvd;
import com.safecode.cyberzone.pojo.BugCycle;
import com.safecode.cyberzone.pojo.BugNvd;
import com.safecode.cyberzone.service.BugCycleService;
import com.safecode.cyberzone.utils.FileUtil;
import com.safecode.cyberzone.utils.SysLogUtil;
import com.safecode.cyberzone.vo.BugCycleVo;


@Service
@Transactional
public class BugCycleServiceImpl implements BugCycleService {

	@Autowired
	private BugCycleMapper bugCycleMapper;
	@Autowired
	private BugCnvdMapper bugCnvdMapper;
	@Autowired
	private BugNvdMapper bugNvdMapper;
	@Autowired
	private SysLogController sysLogController;
	@Autowired
	private SessionProvider sessionProvider;

	@Override
	public PageInfo<BugCycle> queryPageList(BugCycleVo bugCycleVo) {
		Map<Object, Object> params = new HashMap<>();
		if (bugCycleVo == null || DataUtil.isEmpty(bugCycleVo.getPageNum())) {
			params.put("pageNum", 1);
		}else{
			params.put("pageNum", 1);
		}
		if (bugCycleVo == null || DataUtil.isEmpty(bugCycleVo.getPageSize())) {
			params.put("pageSize", 10);
		}else{
			params.put("pageSize", bugCycleVo.getPageSize());
		}
		PageHelper.startPage(params);
		Page<BugCycle> page = bugCycleMapper.queryPageList(bugCycleVo);

		return new PageInfo<BugCycle>(page);
	}

	@Override
	public void delete(Long currentUserId, Long id) {
		BugCycle cycle = bugCycleMapper.selectByPrimaryKey(id);
		if (cycle != null) {
			cycle.setUpdateTime(new Date());
			cycle.setUpdateBy(currentUserId); 
			cycle.setDelFlag(true);
			// 删除漏洞周期表
			bugCycleMapper.updateByPrimaryKey(cycle);
			// 级联删除漏洞表
			if (cycle.getSource() == 1) {
				bugNvdMapper.deleteByCycleId(id);
			} else {
				bugCnvdMapper.deleteByCycleId(id);
			}
		}
	}

	@Override
	public void importBugXml(MultipartFile bugFile, int source, Long currentUserId) throws IOException, DocumentException {
		if (bugFile != null && !bugFile.isEmpty() && StringUtils.isNotEmpty(bugFile.getOriginalFilename())) {
			InputStream inputStream = null;
			InputStreamReader read = null;
			BufferedReader bufferedReader = null;
			try {
				inputStream = bugFile.getInputStream();
				FileInfo fileInfo = FileUtil.saveFile(bugFile, FileUtil.UPLOADPATH_BUG);

				// 维护漏洞周期表
				BugCycle bugCycle = new BugCycle();
				Request2ModelUtil.covertObj(bugCycle, fileInfo);
				bugCycle.setSource(source);
				bugCycle.setCreateTime(new Date());
				bugCycle.setUpdateTime(new Date());
				//获取当前用户id
				bugCycle.setCreateBy(currentUserId);
				bugCycle.setUpdateBy(currentUserId);
				bugCycle.setDelFlag(false);
				bugCycleMapper.insert(bugCycle);
				String encoding = "UTF-8";
				read = new InputStreamReader(inputStream, encoding);// 考虑到编码格式
				bufferedReader = new BufferedReader(read);

				SAXReader saxReader = new SAXReader();
				Document document = saxReader.read(bufferedReader);
				// 获取根节点
				Element root = document.getRootElement();
				Iterator<Element> iterator = root.elementIterator();
				if(source == 1) {	//nvd
					List<BugNvd> bugNvdList = new ArrayList<>();
					while (iterator.hasNext()) {
						Element entry = iterator.next();
						// 获取 entry 标签中的每个属性
						Map entryAttribute = XmlUtil.parseXml2DtoBasedProperty(entry.asXML(), "entry");
						String descript = entry.element("desc").element("descript").getTextTrim();
						
						// 维护NVD漏洞表
						BugNvd bugNvd = Request2ModelUtil.covertByMap(BugNvd.class, entryAttribute);
						bugNvd.setCycleId(bugCycle.getId());
						bugNvd.setDescript(descript);
						bugNvd.setCreateTime(new Date());
						bugNvd.setUpdateTime(new Date());
						
						//获取当前用户id
						bugNvd.setCreateBy(currentUserId);
					    bugNvd.setUpdateBy(currentUserId);
					
						bugNvd.setDelFlag(false);
						// bugVndMapper.insert(bugNvd);单条插入效率过低，改为批量插入
						bugNvdList.add(bugNvd);
					}
					bugNvdMapper.batchInsert(bugNvdList);
				} else if(source == 2) {	//cnvd
		    		List<BugCnvd> bugCnvdList = new ArrayList<>();
					//遍历获取每个漏洞的具体信息
					while (iterator.hasNext()) {
						Map map = XmlUtil.Dom2Map((Element)iterator.next());
						//将信息封装到CNVD实体中
						BugCnvd cnvd = Request2ModelUtil.covertByMap(BugCnvd.class, map);
						cnvd.setDelFlag(false);
						cnvd.setCreateTime(new Date());
						cnvd.setUpdateTime(new Date());
						cnvd.setCycleId(bugCycle.getId());
						
						//获取当前用户id
						cnvd.setCreateBy(currentUserId);
						cnvd.setUpdateBy(currentUserId);
				        
//						bugCnvdMapper.insert(cnvd);单条插入效率过低，改为批量插入
						bugCnvdList.add(cnvd);
					}
					bugCnvdMapper.batchInsert(bugCnvdList);
				}
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw e; 
			} finally {
				try {
					// 关流
					if (inputStream != null) {
						inputStream.close();
					}
					if (read != null) {
						read.close();
					}
					if (bufferedReader != null) {
						bufferedReader.close();
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
}
