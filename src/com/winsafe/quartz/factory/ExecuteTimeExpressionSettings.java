package com.winsafe.quartz.factory;

import java.util.Date;

/**
 * Project:aladdinMMS->Class:ExecuteTimeExpressionSettings.java
 * <p style="font-size:16px;">Description：任务执行的时间</p>
 * Create Time 2012-3-22 上午09:43:25 
 * @author <a href='fuming.zhang@winsafe.cn'>zhangfuming</a>
 * @version 0.8
 */
public class ExecuteTimeExpressionSettings {
	
	public static interface CYCLE_TYPR{
		
		/** 间隔秒*/
		Integer DELAY_SECONDS = 0;
		
		/** 间隔分*/
		Integer DELAY_MINUTES = 1;
		
		/** 每天几点*/
		Integer DELAY_EVERY_DAY_HOUR = 2;
		
	}
	
	public static interface CYCLE_PATTERN_TYPR{
		
		/** 间隔秒*/
		String DELAY_SECONDS = "每隔%s秒";
		
		/** 间隔分*/
		String DELAY_MINUTES = "每隔%s分";
		
		/** 每天几点*/
		String DELAY_EVERY_DAY_HOUR = "每天%s时%s分%s秒";
		
		/**每周%s %s时%s分%秒*/
		String DELAY_EVERY_WEEK = "每周%s %s时%s分%s秒";
		
		/**每月%s号 %s时%s分%秒*/
		String DELAY_EVERY_MONTH = "每月%s号 %s时%s分%s秒";
		
	}
	
	
	
	/** 周期类型*/
	private Integer type; 
	
	/** 延迟的时间秒*/
	private Long delaySeconds;
	
	/** 延时时间分*/
	private Long delayMinutes;
	
	/** 延时时间小时*/
	private Long delayEveryDayHour;
	
	/** 触发的时间*/
	private Date triggerDate;

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
 
	public Date getTriggerDate() {
		return triggerDate;
	}

	public void setTriggerDate(Date triggerDate) {
		this.triggerDate = triggerDate;
	}

	public Long getDelaySeconds() {
		return delaySeconds;
	}

	public void setDelaySeconds(Long delaySeconds) {
		this.delaySeconds = delaySeconds;
	}

	public Long getDelayMinutes() {
		return delayMinutes;
	}

	public void setDelayMinutes(Long delayMinutes) {
		this.delayMinutes = delayMinutes;
	}

	public Long getDelayEveryDayHour() {
		return delayEveryDayHour;
	}

	public void setDelayEveryDayHour(Long delayEveryDayHour) {
		this.delayEveryDayHour = delayEveryDayHour;
	}
 
	
}
