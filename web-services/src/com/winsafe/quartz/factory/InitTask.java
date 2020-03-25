package com.winsafe.quartz.factory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.ServletContext;

import org.apache.log4j.Logger;
import org.quartz.CronExpression;
import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.impl.StdSchedulerFactory;

import com.winsafe.common.util.StringUtil;
import com.winsafe.drp.util.JProperties;
import com.winsafe.quartz.task.dao.TaskService;
import com.winsafe.quartz.task.pojo.TaskQuartz;
import com.winsafe.quartz.task.type.TaskEnum;

public class InitTask  {
	
	private static Logger logger = Logger.getLogger(InitTask.class);
	
	/** 存储全部任务的工厂 key:任务id,value:Scheduler*/
	public static Map<Integer,Scheduler> factoryMap = new HashMap<Integer, Scheduler>();
	
	/** 任务服务对象 */
	private TaskService taskService = new TaskService();
	
	/**servlet上下文*/
	private static ServletContext application ;
	
	private static Integer retryInterval = 1;
	
	private Properties sysPro = null;
	
	private void init() {
		try {
			sysPro = JProperties.loadProperties("system.properties", JProperties.BY_CLASSLOADER);
			retryInterval = Integer.parseInt(sysPro.getProperty("retryInitTaskIntervalMinutes"));
		} catch (Exception e) {
			logger.error("加载配置文件system.properties出错", e);
		}
		
	}
	
	public void run() {
		init();
	    //初始化全部的任务
		//从数据库中获取全部的任务
		//查询所有状态为“未开始”且“未绑定执行线程”的任务
		List<TaskQuartz> taskList = null;
		try {
			taskList = taskService.queryNotStartAndBindTask();
		} catch (Exception e) {
			logger.error("获取定时任务失败",e);
			registInitTask();
			return;
		}
		if(taskList == null || taskList.size() == 0) {
			registInitTask();
			return;
		}
		//循环任务添加任务
		for(TaskQuartz task : taskList){
			
			try{
				//过滤出已注册的任务task.getPerformClass()
				if(doesAlreadyRegister(task.getPerformClass())){
					registTask(task);
				}
			}catch(Exception e){
				System.out.println("任务初始化失败![" + task.getId() + "(" + task.getPerformClass() + ")]" +e);
			}
		}
	}

	/**
	 * 
	 */
	/**
	 * 
	 */
	/**
	 * @throws Exception 
	 * 
	 */
	private void registInitTask() {
		//每隔60秒执行一次任务
		TaskQuartz task = new TaskQuartz();
		task.setId(9999);
		task.setPerformClass("RetryIntialTask");
		task.setStatus(0);
		task.setType(2);
		task.setDelayType(1);
		task.setCycleJobInterval(retryInterval);
		
		try {
			registTask(task);
		} catch (Exception e) {
			logger.error("初始化任务启动重试任务失败",e);
		}
	}



	/**
	 * 注册任务
	 */
	public static void registTask(TaskQuartz task)throws Exception{
		
		//启动任务
		if(TaskEnum.START.intValue() == task.getStatus().intValue()){
			Scheduler scheduler;
			
			SchedulerFactoryImpl impSchedulerFactory =  new SchedulerFactoryImpl(task.getId());
			
			Properties props = new Properties();
			props.put("org.quartz.scheduler.instanceName", task.getId().toString());
			props.put(StdSchedulerFactory.PROP_THREAD_POOL_CLASS,"org.quartz.simpl.SimpleThreadPool");   
			props.put("org.quartz.threadPool.threadCount", "1");   
			impSchedulerFactory.initialize(props);
			
			scheduler = impSchedulerFactory.getScheduler();
			
			//实例化一个具体任务
			JobDetail jobDetail = new JobDetail("jobDetail"+task.getId(),
					"jobDetailGroup"+task.getId(), getHandlerClass(task.getPerformClass()));
			//定义触发器
			CronTrigger cronTrigger = new CronTrigger("cronTrigger"+task.getId(),
					"triggerGroup"+task.getId());
			//设置时间
			CronExpression cexp = new CronExpression(getCronExpressionPattern(task));
			//指派表达式
			cronTrigger.setCronExpression(cexp);
			//安排一个任务
			scheduler.scheduleJob(jobDetail, cronTrigger);
			
			// 启动任务
			if(TaskEnum.START.intValue() == task.getStatus().intValue()){
				scheduler.start();
			}
//			//暂停任务
//			else if(TaskEnum.STOP.intValue() == task.getStatus().intValue()){
//				scheduler.standby();
//			}
			factoryMap.put(task.getId(),scheduler);
		}
	}
	
	/*
	  字段	 允许值	 允许的特殊字符 
	  秒	 0-59	 , - * / 
	  分	 0-59	 , - * / 
	  小时	 0-23	 , - * / 
	  日期	 1-31	 , - * ? / L W C 
	  月份	 1-12 或者 JAN-DEC	 , - * / 
	  星期	 1-7 或者 SUN-SAT	 , - * ? / L C # 
	  年（可选）	 留空, 1970-2099	 , - * / 
	  表达式	 意义 
	  "0 0 12 * * ?"	 每天中午12点触发 
	  "0 15 10 ? * *"	 每天上午10:15触发 
	  "0 15 10 * * ?"	 每天上午10:15触发 
	  "0 15 10 * * ? *"	 每天上午10:15触发 
	  "0 15 10 * * ? 2005"	 2005年的每天上午10:15触发 
	  "0 * 14 * * ?"	 在每天下午2点到下午2:59期间的每1分钟触发 
	  "0 0/5 14 * * ?"	 在每天下午2点到下午2:55期间的每5分钟触发 
	  "0 0/5 14,18 * * ?"	 在每天下午2点到2:55期间和下午6点到6:55期间的每5分钟触发 
	  "0 0-5 14 * * ?"	 在每天下午2点到下午2:05期间的每1分钟触发 
	  "0 10,44 14 ? 3 WED"	 每年三月的星期三的下午2:10和2:44触发 
	  "0 15 10 ? * MON-FRI"	 周一至周五的上午10:15触发 
	  "0 15 10 15 * ?"	 每月15日上午10:15触发 
	  "0 15 10 L * ?"	 每月最后一日的上午10:15触发 
	  "0 15 10 ? * 6L"	 每月的最后一个星期五上午10:15触发 
	  "0 15 10 ? * 6L 2002-2005"	 2002年至2005年的每月的最后一个星期五上午10:15触发 
	  "0 15 10 ? * 6#3"	 每月的第三个星期五上午10:15触发 
	  特殊字符	 意义 
	  *	 表示所有值； 
	  ?	 表示未说明的值，即不关心它为何值； 
	  -	 表示一个指定的范围； 
	  ,	 表示附加一个可能值； 
	  /	 符号前表示开始时间，符号后表示每次递增的值； 
	  L("last")	 ("last") "L" 用在day-of-month字段意思是 "这个月最后一天"；用在 day-of-week字段, 它简单意思是 "7" or "SAT"。 如果在day-of-week字段里和数字联合使用，它的意思就是 "这个月的最后一个星期几" – 例如： "6L" means "这个月的最后一个星期五". 当我们用“L”时，不指明一个列表值或者范围是很重要的，不然的话，我们会得到一些意想不到的结果。 
	  W("weekday")	 只能用在day-of-month字段。用来描叙最接近指定天的工作日（周一到周五）。例如：在day-of-month字段用“15W”指“最接近这个 月第15天的工作日”，即如果这个月第15天是周六，那么触发器将会在这个月第14天即周五触发；如果这个月第15天是周日，那么触发器将会在这个月第 16天即周一触发；如果这个月第15天是周二，那么就在触发器这天触发。注意一点：这个用法只会在当前月计算值，不会越过当前月。“W”字符仅能在 day-of-month指明一天，不能是一个范围或列表。也可以用“LW”来指定这个月的最后一个工作日。 
	  #	 只能用在day-of-week字段。用来指定这个月的第几个周几。例：在day-of-week字段用"6#3"指这个月第3个周五（6指周五，3指第3个）。如果指定的日期不存在，触发器就不会触发。 
	  C	 指和calendar联系后计算过的值。例：在day-of-month 字段用“5C”指在这个月第5天或之后包括calendar的第一天；在day-of-week字段用“1C”指在这周日或之后包括calendar的第一天。
    */
	public static String getCronExpressionPattern(TaskQuartz task){
		
		String[] cronExp = { "0", "*", "*", "*", "*", "?", "*" };
		
		//单次
		 if(task.getType().intValue() == 1){
			 //2012-03-21 13:57:09
			 return "";
		 }
		 //循环
		 else if(task.getType().intValue() == 2){
			switch (task.getDelayType()) {
			//每隔(秒)
			case 0:{
				cronExp[0] = "0/" + task.getCycleJobInterval();
				return QuartzUtil.join(cronExp, " ");
			}
			//每隔(分)
			case 1:{
				cronExp[1] = "0/" + task.getCycleJobInterval();
				return QuartzUtil.join(cronExp, " ");
			}
			//每天(时)
			case 2:{
			}
			case 3:{
			}
			case 4:{
				if(!StringUtil.isEmpty(task.getCronExpression())){
					return task.getCronExpression();
				}
				//0 0 10 * * ? *
				cronExp[1] = "0";
				cronExp[2] = "" + task.getCycleJobInterval();
				return QuartzUtil.join(cronExp, " ");
			}
			default:
				return "";
			}
		 }
		 return "";
	}
	
	/**
	 * 获取处理类的Class
	 */
	@SuppressWarnings("unchecked")
	public static Class getHandlerClass(String key)throws Exception{
		WsScheduledTimerTask t = (WsScheduledTimerTask) application.getAttribute("schTimerTask");
		String clazz = t.getRegTaskHandler().get(key);
		Class c = Class.forName(clazz);
		return c.newInstance().getClass();
	}
	
	public static boolean doesAlreadyRegister(String key){
		WsScheduledTimerTask t = (WsScheduledTimerTask) application.getAttribute("schTimerTask");
		return t.getRegTaskHandler().containsKey(key);
	}
 
	public TaskService getTaskService() {
		return taskService;
	}

	public void setTaskService(TaskService taskService) {
		this.taskService = taskService;
	}
	
	public ServletContext getApplication() {
		return InitTask.application;
	}

	public void setApplication(ServletContext application) {
		InitTask.application = application;
	}
}
