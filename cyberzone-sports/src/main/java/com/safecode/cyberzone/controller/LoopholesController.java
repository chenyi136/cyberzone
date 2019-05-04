package com.safecode.cyberzone.controller;

import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.data.PictureRenderData;
import com.deepoove.poi.util.BytePictureUtils;
import com.github.pagehelper.PageInfo;
import com.safecode.cyberzone.base.dto.ResponseData;
import com.safecode.cyberzone.fdfs.FastDFSClientWrapper;
import com.safecode.cyberzone.pojo.AdWorkOrder;
import com.safecode.cyberzone.pojo.Loopholes;
import com.safecode.cyberzone.pojo.SysCorps;
import com.safecode.cyberzone.service.LoopholesService;
import com.safecode.cyberzone.utils.CodeUtils;
import com.safecode.cyberzone.utils.CyberzoneContants;
import com.safecode.cyberzone.vo.LoopholesVO;

@RestController
@RequestMapping("/loopholes")
@CrossOrigin
public class LoopholesController {
	@Autowired
	private LoopholesService loopholesService;
	@Autowired
	private FastDFSClientWrapper fastDFSClientWrapper;

	// 新增漏洞申报表 保存
	@PostMapping("/saveLoopholes/{currentUserId}")
	public ResponseData<Loopholes> addLoopholes(@PathVariable("currentUserId") Integer currentUserId,
			@RequestBody Loopholes loopholes) throws Exception {
		
		ResponseData<Loopholes> data = new ResponseData<Loopholes>();
		Integer teamId = loopholesService.queryTeamIdByUserId(currentUserId);
		loopholes.setTeamId(teamId);
		loopholesService.addLoopholes(loopholes);
		data.setCode(HttpStatus.OK.value());
		data.setMsg("保存成功");
		return data;
	}

	// 修改status
	@PostMapping("/status")
	public ResponseData<Loopholes> upDateLoopholesToStatus(@RequestBody Loopholes loopholes) throws Exception {
		ResponseData<Loopholes> data = new ResponseData<Loopholes>();
		if (loopholes.getStatus() != null) {
			loopholesService.updateLoopholesStautsByUuid(loopholes);
		}
		data.setCode(HttpStatus.OK.value());
		data.setMsg("保存成功");
		return data;
	}
	
	// 工单下拉列表
	@GetMapping("/workList/{currentUserId}")
	public ResponseData<List<AdWorkOrder>> workList(@PathVariable("currentUserId") Integer currentUserId) throws Exception {
		ResponseData<List<AdWorkOrder>> data = new ResponseData<List<AdWorkOrder>>();
		Integer teamId = loopholesService.queryTeamIdByUserId(currentUserId);
		// 获取工单
		List<AdWorkOrder> list = loopholesService.queryWorkIdByTeamId(teamId);
		data.setCode(HttpStatus.OK.value());
		data.setMsg("查询成功");
		data.setData(list);
		return data;
	}

	// 成果申报列表 分页查询
	@SuppressWarnings("rawtypes")
	@PostMapping("/declarePage/{currentUserId}/{pageNum}/{pageSize}")
	public ResponseData<PageInfo> queryLoopholesByPage(@PathVariable("currentUserId") Integer currentUserId,
			@PathVariable("pageNum") Integer pageNum, @PathVariable("pageSize") Integer pageSize,
			@RequestBody LoopholesVO loopholesVO) throws Exception {
		ResponseData<PageInfo> data = new ResponseData<PageInfo>();
		loopholesVO.setUserId(currentUserId);
		PageInfo<LoopholesVO> pageInfo = loopholesService.queryLoopholesByPage(loopholesVO, pageNum, pageSize);
		data.setCode(HttpStatus.OK.value());
		data.setMsg("查询成功");
		data.setData(pageInfo);
		return data;
	}

	// 根据漏洞编号查询漏洞申请表
	@GetMapping("/query/{uuid}")
	public ResponseData<Loopholes> queryLoopholesByUuid(@PathVariable("uuid") Integer uuid) throws Exception {
		ResponseData<Loopholes> data = new ResponseData<Loopholes>();
		LoopholesVO loopholesVO = loopholesService.queryLoopholesByUuid(uuid);
		data.setCode(HttpStatus.OK.value());
		data.setMsg("查询成功");
		data.setData(loopholesVO);
		return data;
	}

	public String[] getHtmlImages(String news) {
		// 从一个URL加载一个Document对象。
		Document doc = Jsoup.parse(news);
		Elements elements = doc.select("img");
		String[] imageArr = new String[elements.size() > 3 ? 3 : elements.size()];
		for (int i = 0; i < elements.size() && i < 3; i++) {
			imageArr[i] = elements.get(i).attr("src");
		}
		return imageArr;
	}

	// 根据漏洞uuid删除漏洞申请表 修改status的值为6
	@GetMapping("/del")
	public ResponseData<Loopholes> delLoopholesByUuid(@RequestBody Loopholes loopholes) throws Exception {
		ResponseData<Loopholes> data = new ResponseData<Loopholes>();
		loopholesService.updateLoopholesStautsByUuid(loopholes);
		data.setCode(HttpStatus.OK.value());
		data.setMsg("删除成功");
		return data;
	}

	// 指挥方漏洞管理 分页条件查询
	@SuppressWarnings("rawtypes")
	@PostMapping("/commandPage/{pageNum}/{pageSize}")
	public ResponseData<PageInfo> queryCommandLoopholesByPage(@RequestBody LoopholesVO loopholesVO,
			@PathVariable("pageNum") Integer pageNum, @PathVariable("pageSize") Integer pageSize) throws Exception {
		ResponseData<PageInfo> data = new ResponseData<PageInfo>();
		PageInfo<LoopholesVO> pageInfo = loopholesService.queryCommandLoopholesByPage(loopholesVO, pageNum, pageSize);
		data.setCode(HttpStatus.OK.value());
		data.setMsg("查询成功");
		data.setData(pageInfo);
		return data;
	}

	// 根据漏洞uuid修改漏洞申请表
	@PostMapping("/update")
	public ResponseData<Loopholes> updateLoopholes(@RequestBody Loopholes loopholes) throws Exception {
		ResponseData<Loopholes> data = new ResponseData<Loopholes>();
		loopholesService.updateLoopholesByUuid(loopholes);
		data.setCode(HttpStatus.OK.value());
		data.setMsg("修改成功");
		return data;
	}

	// 获取所有战队信息
	@GetMapping("/sysCorpsList")
	public ResponseData<List<SysCorps>> queryAllSysCorps() throws Exception {
		ResponseData<List<SysCorps>> data = new ResponseData<List<SysCorps>>();
		List<SysCorps> list = loopholesService.queryAllSysCorps();
		data.setCode(HttpStatus.OK.value());
		data.setMsg("查询成功");
		data.setData(list);
		return data;
	}

	// 显示所有个战队得分
	@GetMapping("/corpsScore")
	public ResponseData<List<LoopholesVO>> queryCorpsScore() {
		ResponseData<List<LoopholesVO>> data = new ResponseData<List<LoopholesVO>>();
		List<LoopholesVO> list = loopholesService.queryCorpsScore();
		data.setCode(HttpStatus.OK.value());
		data.setMsg("查询成功");
		data.setData(list);
		return data;
	}

	// 战队攻击成果展示
	@GetMapping("/teamLoopholes/{teamId}")
	public ResponseData<List<LoopholesVO>> queryTeamLoopholesByTeamId(@PathVariable("teamId") Integer teamId) {
		ResponseData<List<LoopholesVO>> data = new ResponseData<List<LoopholesVO>>();
		List<LoopholesVO> list = loopholesService.queryTeamLoopholesByTeamId(teamId);
		data.setCode(HttpStatus.OK.value());
		data.setMsg("查询成功");
		data.setData(list);
		return data;
	}

	// 根据漏洞编号查询漏洞图片
	@GetMapping("/images/{uuid}")
	public ResponseData<List<String>> queryLoopholesImages(@PathVariable("uuid") Integer uuid) throws Exception {
		ResponseData<List<String>> data = new ResponseData<List<String>>();
		LoopholesVO loopholesSysUser = loopholesService.queryLoopholesByUuid(uuid);
		String screenshot = loopholesSysUser.getScreenshot();
		String[] htmlImages = getHtmlImages(screenshot);
		data.setCode(HttpStatus.OK.value());
		data.setMsg("查询成功");
		data.setData(Arrays.asList(htmlImages));
		return data;
	}

	// 图片上传 url查看
	@PostMapping("/uploadImages")
	public ResponseData<List<String>> upload(@RequestParam(required = true) MultipartFile[] pics) throws Exception {
		ResponseData<List<String>> data = new ResponseData<List<String>>();
		List<String> urls = new ArrayList<>();
		if (pics.length > 0 && pics != null) {
			for (MultipartFile pic : pics) {
				String path = fastDFSClientWrapper.uploadFile(pic);
				urls.add(CyberzoneContants.FastDFS_URL + path);
			}
			data.setCode(HttpStatus.OK.value());
			data.setMsg("上传成功");
			data.setData(urls);
		}
		return data;
	}

	// 附件上传 url 直接下载
	@PostMapping("/uploadWord")
	public ResponseData<List<String>> uploadWord(@RequestParam(required = true) MultipartFile word) throws Exception {
		ResponseData<List<String>> data = new ResponseData<List<String>>();
		List<String> url = new ArrayList<>();
		if (word.getSize() > 0 && word != null) {
			String path = fastDFSClientWrapper.uploadFile(word);
			url.add(path);
			data.setCode(HttpStatus.OK.value());
			data.setMsg("上传成功");
			data.setData(url);
		}
		return data;
	}

	// 附件下载
	@GetMapping("/downLoad/{uuid}")
	public void downLoad(@PathVariable("uuid") Integer uuid, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String header = request.getHeader("User-Agent");
		response.setHeader("content-type", "application/octet-stream");
		response.setContentType("application/octet-stream");

		LoopholesVO loopholesVO = loopholesService.queryLoopholesByUuid(uuid);
		// String fileUrl =
		// "group1/M00/00/00/wKgyo1w2FFSANeWXAAAyjYZ9jpc48.docx";
		String fileUrl = loopholesVO.getAttachment();
		String suffix = fileUrl.split("\\.")[1];
		String fileName = "漏洞信息" + "." + suffix;
		if (header.contains("Firefox")) {// 判断当前用户使用的是什么浏览（是不是火狐）
			// 是，则使用base64
			fileName = CodeUtils.base64EncodeFileName(fileName);
		} else {
			// 否，则使用URLEncode
			fileName = URLEncoder.encode(fileName, "UTF-8");
		}
		response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
		byte[] bt = fastDFSClientWrapper.downLoad(fileUrl);
		OutputStream output = response.getOutputStream();
		IOUtils.write(bt, output);
		output.flush();
	}

	// 附件预览
	@GetMapping("/preview")
	public void preview(HttpServletRequest request, HttpServletResponse response) throws Exception {
		BufferedInputStream bis = null;
		InputStream input = null;

		String fileName = "文件名数据库.docx";
		String fileUrl = "group1/M00/00/00/wKgyo1w2FFSANeWXAAAyjYZ9jpc48.docx";
		byte[] bt = fastDFSClientWrapper.downLoad(fileUrl);
		OutputStream output = response.getOutputStream();
		IOUtils.write(bt, output);
		byte[] buff = new byte[1024];
		bis = new BufferedInputStream(input);
		int i = bis.read(buff);
		while (i != -1) {
			output.write(buff, 0, buff.length);
			output.flush();
			i = bis.read(buff);
		}
	}

	// 下载
	@GetMapping("/downloadLoopholes/{uuid}")
	public void downloadLoopholesByUuid(@PathVariable("uuid") Integer uuid, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		OutputStream output = response.getOutputStream();
		InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("templates/template.docx");
		XWPFTemplate template = XWPFTemplate.compile(inputStream);
		try {
			LoopholesVO loopholesVO = loopholesService.queryLoopholesByUuid(uuid);

			String header = request.getHeader("User-Agent");
			response.setHeader("content-type", "application/octet-stream");
			response.setContentType("application/octet-stream");

			String fileName = loopholesVO.getUserName() + ".docx";
			if (header.contains("Firefox")) {
				fileName = CodeUtils.base64EncodeFileName(fileName);
			} else {
				fileName = URLEncoder.encode(fileName, "UTF-8");
			}
			response.setHeader("Content-Disposition", "attachment;filename=" + fileName);

			String attribute = loopholesVO.getAttribute();
			String attributeOther = loopholesVO.getAttributeOther() != null ? loopholesVO.getAttributeOther() : "";
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("uuid", loopholesVO.getUuid());
			map.put("userName", loopholesVO.getUserName());
			map.put("name", loopholesVO.getName());
			map.put("attribute", attribute + "," + attributeOther);
			map.put("ranges", loopholesVO.getRanges());
			map.put("description", loopholesVO.getDescription());
			map.put("verifier", loopholesVO.getVerifier());
			map.put("operationTitle", loopholesVO.getOperationTitle());
			map.put("taskTitle", loopholesVO.getTaskTitle());
			map.put("infrastructureName", loopholesVO.getInfrastructureName());
			map.put("vncUrl", loopholesVO.getVncUrl());
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			map.put("createdAt", sdf.format(loopholesVO.getCreatedAt()));
			map.put("screenshot", loopholesVO.getScreenshot());
			String screenshot = loopholesVO.getScreenshot();
			String[] htmlImages = getHtmlImages(screenshot);
			for (int i = 0; i < htmlImages.length; i++) {
				map.put("screenshot" + i,
						new PictureRenderData(240, 240, ".png", BytePictureUtils.getUrlByteArray(htmlImages[i])));
			}
			template.render(map);
			template.write(output);
			output.flush();
		} finally {
			output.close();
			template.close();
		}
	}

	// 导出
	@PostMapping("/exportLoopholes")
	public void exportLoopholes(@RequestBody LoopholesVO loopholesVO, HttpServletResponse response) throws Exception {
		String fileName = "漏洞信息.csv";
		response.setContentType("application/csv;charset=GBK");
		response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(fileName, "UTF-8"));
		PrintWriter out = response.getWriter();
		BufferedWriter buffer = new BufferedWriter(out);
		try {
			List<LoopholesVO> lists = loopholesService.exportLoopholes(loopholesVO);
			buffer.append("漏洞编号").append(",");
			buffer.append("用户").append(",");
			buffer.append("队伍").append(",");
			buffer.append("漏洞类型").append(",");
			buffer.append("影响范围").append(",");
			buffer.append("漏洞说明").append(",");
			buffer.append("验证人员").append(",");
			buffer.append("工单操作名称").append(",");
			buffer.append("任务名称").append(",");
			buffer.append("靶标名称").append(",");
			buffer.append("VNC地址").append(",");
			buffer.append("申报时间").append("\r");
			if (lists != null && lists.size() > 0) {
				for (int i = 0; i < lists.size(); i++) {
					LoopholesVO list = (LoopholesVO) lists.get(i);
					String attribute = list.getAttribute();
					String attributeOther = list.getAttributeOther() != null ? list.getAttributeOther() : "";
					buffer.append(list.getUuid() + "").append(",");
					buffer.append(list.getUserName()).append(",");
					buffer.append(list.getName()).append(",");
					buffer.append(attribute + "," + attributeOther).append(",");
					buffer.append(list.getRanges()).append(",");
					buffer.append(list.getDescription()).append(",");
					buffer.append(list.getVerifier()).append(",");
					buffer.append(list.getOperationTitle()).append(",");
					buffer.append(list.getTaskTitle()).append(",");
					buffer.append(list.getInfrastructureName()).append(",");
					buffer.append(list.getVncUrl()).append(",");
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					buffer.append(sdf.format(list.getCreatedAt())).append("\r");
				}
			}
			buffer.flush();
		} finally {
			buffer.close();
		}
	}

}