package com.safecode.cyberzone.service.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.http.entity.ContentType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.safecode.cyberzone.base.utils.DateUtil;
import com.safecode.cyberzone.base.utils.Request2ModelUtil;
import com.safecode.cyberzone.base.utils.UUIDUtil;
import com.safecode.cyberzone.base.utils.XmlUtil;
import com.safecode.cyberzone.mapper.VulnerabilityCnvdMapper;
import com.safecode.cyberzone.mapper.VulnerabilityNvdMapper;
import com.safecode.cyberzone.po.VulnerabilityCnvd;
import com.safecode.cyberzone.po.VulnerabilityNvd;
import com.safecode.cyberzone.service.SyncService;
import com.safecode.cyberzone.utils.FileUtil;

@Service
@Transactional
@PropertySource(value = {"classpath:xmlurl.properties"} , ignoreResourceNotFound = true)
public class SyncServiceImpl implements SyncService {

	Logger logger = LogManager.getLogger(this.getClass());

	@Value("${nvdC}")
	private String nvdC;
	
	@Value("${cnvdC}")
	private String cnvdC;
	
	@Autowired
	private VulnerabilityNvdMapper nvdMapper;
	@Autowired
	private VulnerabilityCnvdMapper cnvdMapper;

	
	@Override
	public List<VulnerabilityNvd> onlineSyncNvd(String nvdUpdateTime) {
		// TODO Auto-generated method stub
		return nvdMapper.onlineSyncNvd(nvdUpdateTime);
	}
	
	@Override
	public List<VulnerabilityCnvd> onlineSyncCnvd(String cnvdUpdateTime) {
		// TODO Auto-generated method stub
		return cnvdMapper.onlineSyncCnvd(cnvdUpdateTime);
	}
	
	@Override
	public void importVulnerabilityXml(int source, String bPath) throws IOException, DocumentException {
		// TODO Auto-generated method stub

		// 获取B文件夹
		File dir = new File(bPath);
		// 获取B文件夹所有文件
		File[] files = dir.listFiles();
		
		for (File currentFile : files) {
			String fileName = currentFile.getName();
			String pathName = currentFile.getPath();
			if (currentFile.isFile() && fileName.substring(fileName.lastIndexOf(".") + 1).equals("xml")) {

				InputStream inputStream = null;
				InputStreamReader read = null;
				BufferedReader bufferedReader = null;

				try {
					// 通过File获取InputStream
					FileInputStream fileInputStream = new FileInputStream(currentFile);
					MultipartFile multipartFile = new MockMultipartFile(fileName, fileName,
							ContentType.APPLICATION_OCTET_STREAM.toString(), fileInputStream);
					inputStream = multipartFile.getInputStream();

					// 通过InputStream获取BufferedReader
					String encoding = "UTF-8";
					read = new InputStreamReader(inputStream, encoding);// 考虑到编码格式
					bufferedReader = new BufferedReader(read);

					// 通过BufferedReader获取Document
					SAXReader saxReader = new SAXReader();
					Document doc = saxReader.read(bufferedReader);

					// 通过Document获取Element
					Element root = doc.getRootElement();
					Iterator<Element> iterator = root.elementIterator();

					//根据source来源，进行xml解析入库
					iteratorElement(source, iterator, fileName, pathName);
					logger.warn("导入文件："+ fileName +"；导入时间：" + DateUtil.getDateTime());

					// 复制一个删除一个
					FileUtil.deleteFile(pathName);
					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					logger.error("", e);
				} finally {
					try {
						// 关流
						if (null != inputStream) {
							inputStream.close();
						}
						if (null != read) {
							read.close();
						}
						if (null != bufferedReader) {
							bufferedReader.close();
						}
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} // finally end

			} // if end
		} // for end

	}

	private void iteratorElement(int source, Iterator<Element> iterator, String fileName, String pathName) {
		if (source == 1) { // NVD
			while (iterator.hasNext()) {

				Element entry = iterator.next();
				// 获取 entry 标签中的每个属性
				Map entryAttribute = XmlUtil.parseXml2DtoBasedProperty(entry.asXML(), "entry");
				Map map = new HashMap();
				Set keySet = entryAttribute.keySet();
				for (Object obj : keySet) {
					map.put((Object) transform(obj.toString()), entryAttribute.get(obj));
				}
				VulnerabilityNvd nvd = Request2ModelUtil.covertByMap(VulnerabilityNvd.class, map);
				String descript_source = entry.element("desc").element("descript").attribute("source")
						.getStringValue();
				nvd.setDescriptSource(descript_source);
				String descript = entry.element("desc").element("descript").getTextTrim();
				nvd.setDescript(descript);
				if (entry.element("loss_types") != null) {
					String lossTypesXml = entry.element("loss_types").asXML();
					nvd.setLossTypesXml(lossTypesXml);
				}
				if (entry.element("refs") != null) {
					String refsXml = entry.element("refs").asXML();
					nvd.setRefsXml(refsXml);
				}
				if (entry.element("vuln_soft") != null) {
					String vulnStr = entry.element("vuln_soft").asXML();
					nvd.setVulnSoftXml(vulnStr);
				}

				nvd.setId(UUIDUtil.getUUID());
				nvd.setCreateTime(new Date());
				nvd.setUpdateTime(nvd.getCreateTime());
				nvd.setDelFlag(false);
				
				//先查询数据库是否存在，没有就添加，有就修改
				VulnerabilityNvd exNvd = nvdMapper.findByName(nvd.getName());

				if (null == exNvd) {
					nvdMapper.insert(nvd);
				} else {
					nvd.setId(exNvd.getId());
					nvd.setCreateTime(exNvd.getCreateTime());
					nvdMapper.updateByPrimaryKey(nvd);
				}

				//使用完清空，使得JVM回收
				entryAttribute.clear();
				map.clear();
				
			} // while end
			
			//解析完复制到C文件夹
			FileUtil.copyFile(pathName, nvdC + File.separator + fileName);
			
		} else if (source == 2) { // CNVD
			while (iterator.hasNext()) {

				Element vulnerability = iterator.next();
				Map map = XmlUtil.Dom2Map(vulnerability);
				// 将信息封装到CNVD实体中
				@SuppressWarnings("unchecked")
				VulnerabilityCnvd cnvd = Request2ModelUtil.covertByMap(VulnerabilityCnvd.class, map);
				if (vulnerability.element("bids") != null) {
					String bidsXml = vulnerability.element("bids").asXML();
					cnvd.setBidsXml(bidsXml);
				}
				if (vulnerability.element("products") != null) {
					String productsXml = vulnerability.element("products").asXML();
					cnvd.setProductsXml(productsXml);
				}
				if (vulnerability.element("cves") != null) {
					if (vulnerability.element("cves").element("cve") != null) {
						if (vulnerability.element("cves").element("cve").element("cveNumber") != null) {
							String cveNumber = vulnerability.element("cves").element("cve")
									.element("cveNumber").getStringValue();
							cnvd.setCveNumber(cveNumber);
						}
					}
				}
				
				cnvd.setId(UUIDUtil.getUUID());
				cnvd.setCreateTime(new Date());
				cnvd.setUpdateTime(cnvd.getCreateTime());
				cnvd.setDelFlag(false);
				
				//先查询数据库是否存在，没有就添加，有就修改
				VulnerabilityCnvd exCnvd = cnvdMapper.findByNumber(cnvd.getNumber());

				if (null == exCnvd) {
					cnvdMapper.insert(cnvd);
				} else {
					cnvd.setCreateTime(exCnvd.getCreateTime());
					cnvd.setId(exCnvd.getId());								
					cnvdMapper.updateByPrimaryKey(cnvd);
				}
				
				//使用完清空，使得JVM回收
				map.clear();

			} // while end
			
			//解析完复制到C文件夹
			FileUtil.copyFile(pathName, cnvdC + File.separator + fileName);
			
		} // else if end
	}

	private static String transform(String str) {
		str = str.trim();
		while (str.contains("_")) {
			int i = str.indexOf("_");
			if (i + 1 < str.length()) {
				char c = str.charAt(i + 1);
				String temp = (c + "").toUpperCase();
				str = str.replace("_" + c, temp);
			}
		}
		return str;
	}

}
