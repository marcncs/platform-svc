package com.winsafe.drp.ws;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.winsafe.drp.server.AutoAuditProductIncomeTask;

/**
 * Project:bright->Class:SysContextListener.java
 * <p style="font-size:16px;">Description：系统上下文监听器</p>
 * Create Time Oct 10, 2011 9:44:17 AM 
 * @author <a href='fazuo.du@winsafe.com'>dufazuo</a>
 * @version 0.8
 */
public class SysContextListener implements ServletContextListener
{
	//定时器
	private Timer timer = null;
	//自动执行任务（注这里可以有多个任务）
	private AutoAuditProductIncomeTask autoAuditProductIncomeTask = null;
	//任务执行周期
	private final long period = 24 * 60 * 60 * 1000;
	//定时任务启动时间模式
	private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
//	private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd 12:00:00");
	//定时任务启动时间
	private Date startTime;
	/* (non-Javadoc)
	 * @see javax.servlet.ServletContextListener#contextDestroyed(javax.servlet.ServletContextEvent)
	 */
	@Override
	public void contextDestroyed(ServletContextEvent event)
	{
		//如果定时器不为空，则关闭定时器并记录销毁日志
		if(null != timer)
		{
			timer.cancel();
			event.getServletContext().log("定时器销毁");
		}

	}

	/* (non-Javadoc)
	 * @see javax.servlet.ServletContextListener#contextInitialized(javax.servlet.ServletContextEvent)
	 */
	@Override
	public void contextInitialized(ServletContextEvent event)
	{
		//如果定时器为空，则创建定时器并记录启动日志
		if(null == timer)
		{
			timer = new Timer();
			event.getServletContext().log("定时器启动");
		}
		//定时自动执行任务
		autoAuditProductIncomeTask = new AutoAuditProductIncomeTask();
		try
		{
			//将启动服务的当天晚上23:59:59作为任务启动时间
			startTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(sdf.format(new Date()));
		}
		catch(ParseException e)
		{
			e.printStackTrace();
		}
		//如果启动任务的时间已过时，则将任务的启动时间设置为第二天的晚上23:59:59
		if(System.currentTimeMillis() > startTime.getTime())
		{
			startTime = new Date(startTime.getTime() + period);
		}
		timer.schedule(autoAuditProductIncomeTask, startTime, period);
		event.getServletContext().log("自动复核产成品入库单据任务已启动------每天" + new SimpleDateFormat("HH:mm:ss").format(startTime) + "开始自动复核前一天单据");
	}

}
