package com.safecode.cyberzone.web.sports.controller;

import java.io.File;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.safecode.cyberzone.base.dto.ResponseData;
import com.safecode.cyberzone.web.auth.holder.AuthHolder;
import com.safecode.cyberzone.web.sports.entity.AdWorkOrder;
import com.safecode.cyberzone.web.sports.entity.Loopholes;
import com.safecode.cyberzone.web.sports.entity.SysCorps;
import com.safecode.cyberzone.web.sports.vo.LoopholesVO;

@RestController
@RequestMapping("/sports/loopholes")
public class LoopholesController {
	@Autowired
	private RestTemplate restTemplate;

	// 新增漏洞申报表 保存
	@PostMapping("/saveLoopholes")
	public ResponseData<Loopholes> addLoopholes(@RequestBody Loopholes loopholes) throws Exception {
		Integer currentUserId = AuthHolder.getCurrentUser().getUserId();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
		HttpEntity<Loopholes> entity = new HttpEntity<Loopholes>(loopholes, headers);
		ResponseEntity<ResponseData<Loopholes>> exchange = restTemplate.exchange(
				"http://CYBERZONE-GATEWAY/sports/loopholes/saveLoopholes/" + currentUserId, HttpMethod.POST, entity,
				new ParameterizedTypeReference<ResponseData<Loopholes>>() {
				});
		return exchange.getBody();
	}

	// 修改status
	@PostMapping("/status")
	public ResponseData<Loopholes> upDateLoopholesToStatus(@RequestBody Loopholes loopholes) throws Exception {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
		HttpEntity<Loopholes> entity = new HttpEntity<Loopholes>(loopholes, headers);
		ResponseEntity<ResponseData<Loopholes>> exchange = restTemplate.exchange(
				"http://CYBERZONE-GATEWAY/sports/loopholes/status", HttpMethod.POST, entity,
				new ParameterizedTypeReference<ResponseData<Loopholes>>() {
				});
		return exchange.getBody();
	}

	@GetMapping("/workList")
	public Object workList() {
		Integer currentUserId = AuthHolder.getCurrentUser().getUserId();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
		HttpEntity<Integer> entity = new HttpEntity<>(headers);
		ResponseEntity<ResponseData<List<AdWorkOrder>>> exchange = restTemplate.exchange(
				"http://CYBERZONE-GATEWAY/sports/loopholes/workList/" + currentUserId, HttpMethod.GET, entity,
				new ParameterizedTypeReference<ResponseData<List<AdWorkOrder>>>() {
				});
		return exchange.getBody();
	}

	// 成果申报列表 分页查询
	@SuppressWarnings("rawtypes")
	@PostMapping("/declarePage/{pageNum}/{pageSize}")
	public Object queryLoopholesByPage(@PathVariable("pageNum") Integer pageNum,
			@PathVariable("pageSize") Integer pageSize, @RequestBody LoopholesVO loopholesVO) throws Exception {

		Integer currentUserId = AuthHolder.getCurrentUser().getUserId();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
		HttpEntity<LoopholesVO> entity = new HttpEntity<LoopholesVO>(loopholesVO, headers);
		ResponseEntity<Object> exchange = restTemplate.exchange(
				"http://CYBERZONE-GATEWAY/sports/loopholes/declarePage/" + currentUserId + "/" + pageNum + "/" + pageSize,
				HttpMethod.POST, entity, new ParameterizedTypeReference<Object>() {
				});
		return exchange.getBody();

	}

	// 根据漏洞编号查询漏洞申请表
	@GetMapping("/query/{uuid}")
	public ResponseData<Loopholes> queryLoopholesByUuid(@PathVariable("uuid") Integer uuid) throws Exception {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
		HttpEntity<Integer> entity = new HttpEntity<Integer>(uuid);
		ResponseEntity<ResponseData<Loopholes>> exchange = restTemplate.exchange(
				"http://CYBERZONE-GATEWAY/sports/loopholes/query/" + uuid, HttpMethod.GET, entity,
				new ParameterizedTypeReference<ResponseData<Loopholes>>() {
				});
		return exchange.getBody();
	}

	// 根据漏洞uuid删除漏洞申请表 修改status的值为6
	@GetMapping("/del")
	public ResponseData<Loopholes> delLoopholesByUuid(@RequestBody Loopholes loopholes) throws Exception {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
		HttpEntity<Loopholes> entity = new HttpEntity<Loopholes>(loopholes);
		ResponseEntity<ResponseData<Loopholes>> exchange = restTemplate.exchange(
				"http://CYBERZONE-GATEWAY/sports/loopholes/del", HttpMethod.GET, entity,
				new ParameterizedTypeReference<ResponseData<Loopholes>>() {
				});
		return exchange.getBody();
	}

	// 指挥方漏洞管理 分页条件查询
	@SuppressWarnings("rawtypes")
	@PostMapping("/commandPage/{pageNum}/{pageSize}")
	public Object queryCommandLoopholesByPage(@RequestBody LoopholesVO loopholesVO,
			@PathVariable("pageNum") Integer pageNum, @PathVariable("pageSize") Integer pageSize) throws Exception {

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
		HttpEntity<LoopholesVO> entity = new HttpEntity<LoopholesVO>(loopholesVO);
		ResponseEntity<Object> exchange = restTemplate.exchange(
				"http://CYBERZONE-GATEWAY/sports/loopholes/commandPage/" + pageNum + "/" + pageSize, HttpMethod.POST,
				entity, new ParameterizedTypeReference<Object>() {
				});
		return exchange.getBody();
	}

	// 根据漏洞uuid修改漏洞申请表
	@PostMapping("/update")
	public ResponseData<Loopholes> updateLoopholes(@RequestBody Loopholes loopholes) throws Exception {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
		HttpEntity<Loopholes> entity = new HttpEntity<Loopholes>(loopholes);
		ResponseEntity<ResponseData<Loopholes>> exchange = restTemplate.exchange(
				"http://CYBERZONE-GATEWAY/sports/loopholes/update", HttpMethod.POST, entity,
				new ParameterizedTypeReference<ResponseData<Loopholes>>() {
				});
		return exchange.getBody();
	}

	// 获取所有战队信息
	@GetMapping("/sysCorpsList")
	public ResponseData<List<SysCorps>> queryAllSysCorps() throws Exception {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
		HttpEntity entity = new HttpEntity("");
		ResponseEntity<ResponseData<List<SysCorps>>> exchange = restTemplate.exchange(
				"http://CYBERZONE-GATEWAY/sports/loopholes/sysCorpsList", HttpMethod.GET, entity,
				new ParameterizedTypeReference<ResponseData<List<SysCorps>>>() {
				});
		return exchange.getBody();
	}

	// 显示所有个战队得分
	@GetMapping("/corpsScore")
	public ResponseData<List<LoopholesVO>> queryCorpsScore() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
		HttpEntity entity = new HttpEntity("");
		ResponseEntity<ResponseData<List<LoopholesVO>>> exchange = restTemplate.exchange(
				"http://CYBERZONE-GATEWAY/sports/loopholes/corpsScore", HttpMethod.GET, entity,
				new ParameterizedTypeReference<ResponseData<List<LoopholesVO>>>() {
				});
		return exchange.getBody();
	}

	// 战队攻击成果展示
	@GetMapping("/teamLoopholes/{teamId}")
	public ResponseData<List<LoopholesVO>> queryTeamLoopholesByTeamId(@PathVariable("teamId") Integer teamId) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
		HttpEntity<Integer> entity = new HttpEntity<Integer>(teamId);
		ResponseEntity<ResponseData<List<LoopholesVO>>> exchange = restTemplate.exchange(
				"http://CYBERZONE-GATEWAY/sports/loopholes/teamLoopholes/" + teamId, HttpMethod.GET, entity,
				new ParameterizedTypeReference<ResponseData<List<LoopholesVO>>>() {
				});
		return exchange.getBody();
	}

	// 根据漏洞编号查询漏洞图片
	@GetMapping("/images/{uuid}")
	public ResponseData<List<String>> queryLoopholesImages(@PathVariable("uuid") Integer uuid) throws Exception {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
		HttpEntity<Integer> entity = new HttpEntity<Integer>(uuid);
		ResponseEntity<ResponseData<List<String>>> exchange = restTemplate.exchange(
				"http://CYBERZONE-GATEWAY/sports/loopholes/images/" + uuid, HttpMethod.GET, entity,
				new ParameterizedTypeReference<ResponseData<List<String>>>() {
				});
		return exchange.getBody();
	}

	// 图片上传 url查看
	@PostMapping("/uploadImages")
	public ResponseData<List<String>> upload(@RequestParam(required = true) MultipartFile[] pics) throws Exception {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.MULTIPART_FORM_DATA);

		MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
		map.add("pics", pics);

		HttpEntity<MultiValueMap<String, Object>> entity = new HttpEntity<MultiValueMap<String, Object>>(map, headers);

		ResponseEntity<ResponseData<List<String>>> exchange = restTemplate.exchange(
				"http://CYBERZONE-GATEWAY/sports/loopholes/uploadImages", HttpMethod.POST, entity,
				new ParameterizedTypeReference<ResponseData<List<String>>>() {
				});
		return exchange.getBody();
	}

	// 附件上传 url 直接下载
	@PostMapping("/uploadWord")
	public ResponseData<List<String>> uploadWord(@RequestParam(required = true) MultipartFile word) throws Exception {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.MULTIPART_FORM_DATA);
		FileSystemResource resource = new FileSystemResource(new File(word.getOriginalFilename()));

		MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
		map.add("word", resource);

		HttpEntity<MultiValueMap<String, Object>> entity = new HttpEntity<MultiValueMap<String, Object>>(map, headers);

		ResponseEntity<ResponseData<List<String>>> exchange = restTemplate.exchange(
				"http://CYBERZONE-GATEWAY/sports/loopholes/uploadWord", HttpMethod.POST, entity,
				new ParameterizedTypeReference<ResponseData<List<String>>>() {
				});
		return exchange.getBody();
	}

	// 附件下载
	@GetMapping("/downLoad/{uuid}")
	public void downLoad(@PathVariable("uuid") Integer uuid, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		response.setHeader("content-type", "application/octet-stream");
		response.setContentType("application/octet-stream");

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON_UTF8);

		HttpEntity<Integer> entity = new HttpEntity<Integer>(uuid, headers);

		ResponseEntity<byte[]> exchange = restTemplate.exchange(
				"http://CYBERZONE-GATEWAY/sports/loopholes/downLoad/" + uuid, HttpMethod.GET, entity,
				new ParameterizedTypeReference<byte[]>() {
				});
		HttpHeaders head = exchange.getHeaders();

		List<String> list = head.get("Content-Disposition");
		String fileName = String.join(",", list).split("=")[1];

		response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
		byte[] bt = exchange.getBody();

		OutputStream output = response.getOutputStream();
		IOUtils.write(bt, output);
		output.flush();
	}

	// 附件预览
	@GetMapping("/preview")
	public void preview(HttpServletRequest request, HttpServletResponse response) throws Exception {

	}

	// 下载
	@GetMapping("/downloadLoopholes/{uuid}")
	public void downloadLoopholesByUuid(@PathVariable("uuid") Integer uuid, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.setHeader("content-type", "application/octet-stream");
		response.setContentType("application/octet-stream");

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON_UTF8);

		HttpEntity<Integer> entity = new HttpEntity<>(uuid, headers);

		ResponseEntity<byte[]> exchange = restTemplate.exchange(
				"http://CYBERZONE-GATEWAY/sports/loopholes/downloadLoopholes/" + uuid, HttpMethod.GET, entity,
				byte[].class);
		HttpHeaders head = exchange.getHeaders();

		List<String> list = head.get("Content-Disposition");
		String fileName = String.join(",", list).split("=")[1];

		response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
		byte[] bt = exchange.getBody();

		OutputStream output = response.getOutputStream();
		IOUtils.write(bt, output);
		output.flush();
	}

	// 导出
	@PostMapping("/exportLoopholes")
	public void exportLoopholes(@RequestBody LoopholesVO loopholesVO, HttpServletResponse response) throws Exception {
		response.setHeader("content-type", "application/octet-stream");
		response.setContentType("application/octet-stream");

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON_UTF8);

		HttpEntity<LoopholesVO> entity = new HttpEntity<LoopholesVO>(loopholesVO, headers);

		ResponseEntity<byte[]> exchange = restTemplate.exchange(
				"http://CYBERZONE-GATEWAY/sports/loopholes/exportLoopholes", HttpMethod.POST, entity,
				new ParameterizedTypeReference<byte[]>() {
				});
		HttpHeaders head = exchange.getHeaders();

		List<String> list = head.get("Content-Disposition");
		String fileName = String.join(",", list).split("=")[1];

		response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
		byte[] bt = exchange.getBody();

		OutputStream output = response.getOutputStream();
		IOUtils.write(bt, output);
		output.flush();
	}

}
