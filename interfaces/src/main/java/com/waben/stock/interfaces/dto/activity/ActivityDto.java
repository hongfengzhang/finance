package com.waben.stock.interfaces.dto.activity;

import java.util.Date;
import java.util.List;
import java.util.Map;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

@ApiModel(value="ActivityDto",description="活动对象")
public class ActivityDto {
	@ApiModelProperty(value = "活动id")
	private long activityId;
	
	/**
	 * 活动主标题
	 */
	@ApiModelProperty(value = "活动主标题")
	private String subject;
	
	/**
	 * 活动副标题
	 */
	@ApiModelProperty(value = "活动副标题")
	private String subject2;
	
	/**
	 * 开始时间
	 */
	@ApiModelProperty(value = "开始时间")
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date startTime;
	
	/**
	 * 结束时间
	 */
	@ApiModelProperty(value = "结束时间")
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date endTime;
	
	/**
	 * 是否生效
	 */
	@ApiModelProperty(value = "是否生效")
	private boolean isvalid;
	
	/**
	 * 活动详情
	 */
	@ApiModelProperty(value = "开始时间")
	private String content;
	
	/**
	 * 活动链接
	 */
	@ApiModelProperty(value = "活动链接")
	private String href;
	
	/**
	 * 活动页pv
	 */
	@ApiModelProperty(value = "活动页页面浏览量")
	private int pv;
	
	/**
	 * 活动页uv
	 */
	@ApiModelProperty(value = "活动页用户浏览量")
	private int uv;
	
	/**
	 * 活动类型 0优股网 1合作方
	 */
	@ApiModelProperty(value = "活动类型 0优股网 1合作方")
	private int activityType;
	
	/**
	 * 活动资格  0 所有用户
	 */
	@ApiModelProperty(value = "活动资格  0 所有用户")
	private int qualification;
	
	/**
	 * 活动参与用户数
	 */
	@ApiModelProperty(value = "活动参与用户数")
	private int usercount;
	
	/**
	 * 活动是否结束
	 */
	@ApiModelProperty(value = "开始时间")
	private boolean isend;
	
	/**
	 * 奖品类型 1:期权抵扣券  2：话费券  3：实物奖品
	 */
	@ApiModelProperty(value = "奖品类型 1:期权抵扣券  2：话费券  3：实物奖品")
	private String awardType;


	@ApiModelProperty(value = "中奖信息")
	private List<Map<String,String>> listWinningInfo;

	/**
	 * 活动展示位置
	 */
	@ApiModelProperty(value = "活动展示位置")
	private String location;

	public ActivityDto(){
		
	}
	
	public long getActivityId() {
		return activityId;
	}

	public void setActivityId(long activityId) {
		this.activityId = activityId;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getSubject2() {
		return subject2;
	}

	public void setSubject2(String subject2) {
		this.subject2 = subject2;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public boolean isIsvalid() {
		return isvalid;
	}

	public void setIsvalid(boolean isvalid) {
		this.isvalid = isvalid;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}

	public int getPv() {
		return pv;
	}

	public void setPv(int pv) {
		this.pv = pv;
	}

	public int getUv() {
		return uv;
	}

	public void setUv(int uv) {
		this.uv = uv;
	}

	public int getActivityType() {
		return activityType;
	}

	public void setActivityType(int activityType) {
		this.activityType = activityType;
	}

	public int getQualification() {
		return qualification;
	}

	public void setQualification(int qualification) {
		this.qualification = qualification;
	}

	public int getUsercount() {
		return usercount;
	}

	public void setUsercount(int usercount) {
		this.usercount = usercount;
	}

	public boolean isIsend() {
		return isend;
	}

	public void setIsend(boolean isend) {
		this.isend = isend;
	}

	public String getAwardType() {
		return awardType;
	}

	public void setAwardType(String awardType) {
		this.awardType = awardType;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public List<Map<String, String>> getListWinningInfo() {
		return listWinningInfo;
	}

	public void setListWinningInfo(List<Map<String, String>> listWinningInfo) {
		this.listWinningInfo = listWinningInfo;
	}
}
