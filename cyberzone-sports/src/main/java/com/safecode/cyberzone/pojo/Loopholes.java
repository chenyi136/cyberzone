package com.safecode.cyberzone.pojo;

import java.util.Date;

public class Loopholes {
	private Integer uuid;// 漏洞编号 表中主键自增
	private Integer userId;// 漏洞发现者id
	private Integer teamId;// 团队id
	private Integer workId;// 工单id
	private String attribute;// 漏洞类型
	private String attributeOther;// 漏洞类型-其他
	private String ranges;// 影响范围
	private String description;// 漏洞说明
	private String screenshot;// 漏洞详情截图 富文本编辑器
	private String attachment;// 附件路径
	private String verifier;// 验证人员
	private Integer archivist;// 评分人员
	private Integer status;// 1待提交;2:待评分;3评分中;4已评分;5已驳回;6删除
	private String refuseOpinion;// 驳回意见
	private Double score;// 评分
	private String scoreOpinion;// 评分意见
	private Date createdAt;// 创建时间
	private Date updatedAt;// 修改时间

	public Integer getUuid() {
		return uuid;
	}

	public void setUuid(Integer uuid) {
		this.uuid = uuid;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getTeamId() {
		return teamId;
	}

	public void setTeamId(Integer teamId) {
		this.teamId = teamId;
	}

	public Integer getWorkId() {
		return workId;
	}

	public void setWorkId(Integer workId) {
		this.workId = workId;
	}

	public String getAttribute() {
		return attribute;
	}

	public void setAttribute(String attribute) {
		this.attribute = attribute;
	}

	public String getAttributeOther() {
		return attributeOther;
	}

	public void setAttributeOther(String attributeOther) {
		this.attributeOther = attributeOther;
	}

	public String getRanges() {
		return ranges;
	}

	public void setRanges(String ranges) {
		this.ranges = ranges;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getScreenshot() {
		return screenshot;
	}

	public void setScreenshot(String screenshot) {
		this.screenshot = screenshot;
	}

	public String getAttachment() {
		return attachment;
	}

	public void setAttachment(String attachment) {
		this.attachment = attachment;
	}

	public String getVerifier() {
		return verifier;
	}

	public void setVerifier(String verifier) {
		this.verifier = verifier;
	}

	public Integer getArchivist() {
		return archivist;
	}

	public void setArchivist(Integer archivist) {
		this.archivist = archivist;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getRefuseOpinion() {
		return refuseOpinion;
	}

	public void setRefuseOpinion(String refuseOpinion) {
		this.refuseOpinion = refuseOpinion;
	}

	public Double getScore() {
		return score;
	}

	public void setScore(Double score) {
		this.score = score;
	}

	public String getScoreOpinion() {
		return scoreOpinion;
	}

	public void setScoreOpinion(String scoreOpinion) {
		this.scoreOpinion = scoreOpinion;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

}
