package com.winsafe.quartz;

import java.util.HashMap;
import java.util.Map;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;

import com.winsafe.quartz.factory.InitTask;
import com.winsafe.quartz.factory.WsScheduledTimerTask;

public class QuartzListener implements ServletContextListener {
	private InitTask initTask;
	public static String REAL_PATH;
	
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		//程序结束时,结束所有的任务
		Map<Integer,Scheduler> scheduledMap = InitTask.factoryMap;
		for(Integer id : scheduledMap.keySet()){
			try {
				scheduledMap.get(id).shutdown();
			} catch (SchedulerException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		
		//注册任务
		regTask(sce);
		//初始化任务
		ServletContext application = sce.getServletContext();
		//保存跟目录
		REAL_PATH = application.getRealPath("/");
		initTask = new InitTask();
		initTask.setApplication(application);
		application.setAttribute("initTask", initTask);
		ResourceBundle bundle = PropertyResourceBundle.getBundle("task");
		String isAuto = bundle.getString("autoStart");
		//autoStart值为1时,启动程序时自动启动已注册的任务(状态为运行)
		if(isAuto.equals("1")){
			initTask.run();
		} 
	}
	/**
	 * 注册任务
	 * Create Time 2014-3-20 下午03:36:28 
	 * @param sce
	 * @author lipeng
	 */
	private void regTask(ServletContextEvent sce){
		//注册可使用的任务
		WsScheduledTimerTask wsScheduledTimerTask = new WsScheduledTimerTask();
		//从配置(task.properties)文件中获取注册的任务
		//取得配置文件
		ResourceBundle bundle = PropertyResourceBundle.getBundle("task");
		//autoStart值为1时,启动程序时自动启动任务(状态为运行)
		Set<String> taskSet = bundle.keySet();
		taskSet.remove("autoStart");
		taskSet.remove("runAtStart");
		Map<String,String> regTaskHandler = new HashMap<String, String>();
		if(taskSet != null && taskSet.size()>0){
			for(String task : taskSet){
				regTaskHandler.put(task, bundle.getString(task));
			}
		}
		regTaskHandler.put("RetryIntialTask", "com.winsafe.quartz.task.RetryIntialTask");
		wsScheduledTimerTask.setRegTaskHandler(regTaskHandler);
		//将注册的任务加到上下文中
		ServletContext application = sce.getServletContext();
		application.setAttribute("schTimerTask", wsScheduledTimerTask);
	}
	
}
