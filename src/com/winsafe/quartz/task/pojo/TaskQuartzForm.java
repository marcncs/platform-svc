package com.winsafe.quartz.task.pojo;

import java.util.Date;

import org.apache.struts.action.ActionForm;

public class TaskQuartzForm extends ActionForm
{

	/** 标识符 */
	private Integer id;
	
	/** 任务名称 */
	private String taskName;
	
	/** 任务起始执行时间 */
	private Date startDateTime;
	
	/** 任务终止执行时间 */
	private Date endDateTime;
	
	/** 任务状态 */
	private Integer status;
	
	/** 依赖任务 */
	private Integer dependOnTaskId;
	
	/** 操作 */
	private Integer operation = 0;
	
	/** 父JOB */
	private Integer parentJob;
	
	/** 子JOB */
	private Integer childJob;
	
	/** 类型（1：循环；2：单次运行） */
	private Integer type;
	
	/** 任务描述 */
	private String remark;
	
	/** 执行任务的类（此处只需指定执行类的类名即可，且此类为Spring可感知的类） */
	private String performClass;
	
	/** 是否绑定执行线程 */
	private Integer isBindPerformThread;
	
	/** 循环任务执行间隔 */
	private Integer cycleJobInterval;
	
	/** 间隔类型 */
	private Integer delayType;
	
	private String cronExpression;
	
	
	private Integer createUserID;
	
	private Date createDate;
	
	private Integer updateUserID;
	
	private Date updateDate;
	
	private Integer createOrganID;
	
	private Integer isDelete;
	
	
	/** 间隔小时*/
	private Integer delayHour;
	/** 间隔分*/
	private Integer delayMinute;
	/** 间隔秒*/
	private Integer delaySecond;
	/**每周几*/
	private Integer delayWeek;
	/**每月几号*/
	private Integer delayMonthDay;
	
	private Integer taskCategory;
	
	private Integer otherId;
	
	private String displayName;
	
	/********************************************setter,getter******************************************/

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public Date getStartDateTime() {
		return startDateTime;
	}

	public void setStartDateTime(Date startDateTime) {
		this.startDateTime = startDateTime;
	}

	public Date getEndDateTime() {
		return endDateTime;
	}

	public void setEndDateTime(Date endDateTime) {
		this.endDateTime = endDateTime;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getDependOnTaskId() {
		return dependOnTaskId;
	}

	public void setDependOnTaskId(Integer dependOnTaskId) {
		this.dependOnTaskId = dependOnTaskId;
	}

	public Integer getOperation() {
		return operation;
	}

	public void setOperation(Integer operation) {
		this.operation = operation;
	}

	public Integer getParentJob() {
		return parentJob;
	}

	public void setParentJob(Integer parentJob) {
		this.parentJob = parentJob;
	}

	public Integer getChildJob() {
		return childJob;
	}

	public void setChildJob(Integer childJob) {
		this.childJob = childJob;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getPerformClass() {
		return performClass;
	}

	public void setPerformClass(String performClass) {
		this.performClass = performClass;
	}

	public Integer getIsBindPerformThread() {
		return isBindPerformThread;
	}

	public void setIsBindPerformThread(Integer isBindPerformThread) {
		this.isBindPerformThread = isBindPerformThread;
	}

	public Integer getCycleJobInterval() {
		return cycleJobInterval;
	}

	public void setCycleJobInterval(Integer cycleJobInterval) {
		this.cycleJobInterval = cycleJobInterval;
	}

	public Integer getDelayType() {
		return delayType;
	}

	public void setDelayType(Integer delayType) {
		this.delayType = delayType;
	}

	public String getCronExpression() {
		return cronExpression;
	}

	public void setCronExpression(String cronExpression) {
		this.cronExpression = cronExpression;
	}

	public Integer getDelayHour() {
		return delayHour;
	}

	public void setDelayHour(Integer delayHour) {
		this.delayHour = delayHour;
	}

	public Integer getDelayMinute() {
		return delayMinute;
	}

	public void setDelayMinute(Integer delayMinute) {
		this.delayMinute = delayMinute;
	}

	public Integer getDelaySecond() {
		return delaySecond;
	}

	public void setDelaySecond(Integer delaySecond) {
		this.delaySecond = delaySecond;
	}

	public Integer getDelayWeek() {
		return delayWeek;
	}

	public void setDelayWeek(Integer delayWeek) {
		this.delayWeek = delayWeek;
	}

	public Integer getDelayMonthDay() {
		return delayMonthDay;
	}

	public void setDelayMonthDay(Integer delayMonthDay) {
		this.delayMonthDay = delayMonthDay;
	}

	public Integer getTaskCategory() {
		return taskCategory;
	}

	public void setTaskCategory(Integer taskCategory) {
		this.taskCategory = taskCategory;
	}

	public Integer getOtherId() {
		return otherId;
	}

	public void setOtherId(Integer otherId) {
		this.otherId = otherId;
	}

	public Integer getCreateUserID() {
		return createUserID;
	}

	public void setCreateUserID(Integer createUserID) {
		this.createUserID = createUserID;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Integer getUpdateUserID() {
		return updateUserID;
	}

	public void setUpdateUserID(Integer updateUserID) {
		this.updateUserID = updateUserID;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public Integer getCreateOrganID() {
		return createOrganID;
	}

	public void setCreateOrganID(Integer createOrganID) {
		this.createOrganID = createOrganID;
	}

	public Integer getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Integer isDelete) {
		this.isDelete = isDelete;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	
	
	
	
	
}
