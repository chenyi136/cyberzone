package com.safecode.cyberzone.service;

import java.io.IOException;
import java.util.List;

import org.dom4j.DocumentException;

import com.safecode.cyberzone.po.VulnerabilityCnvd;
import com.safecode.cyberzone.po.VulnerabilityNvd;

public interface SyncService {

	/**
	 * 定时导入xml
	 * @param source 来源（1：nvd，2：cnvd）
	 * @param bPath	 B文件夹
	 * @throws IOException
	 * @throws DocumentException
	 */
	void importVulnerabilityXml(int source, String bPath) throws IOException, DocumentException;

	List<VulnerabilityNvd> onlineSyncNvd(String nvdUpdateTime);

	List<VulnerabilityCnvd> onlineSyncCnvd(String cnvdUpdateTime);

}
