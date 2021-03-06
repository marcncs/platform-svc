package com.winsafe.quartz.task.dao;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.winsafe.drp.dao.Product;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.pager2.PageQuery;
import com.winsafe.quartz.factory.QuartzUtil;
import com.winsafe.quartz.task.pojo.TaskQuartz;

public class TaskService
{
	
	
	public List getProductAll() throws Exception {
		return EntityManager.getAllByHql("from Product ");
	}
	/**
	 * 查询状态为“未开始”且未绑定到执行线程的任务
	 * Create Time: Dec 2, 2011 11:28:37 AM 
	 * @return List<Task> 状态为“未开始”且未绑定到执行线程的任务集合
	 * @author dufazuo
	 */
	public List<TaskQuartz> queryNotStartAndBindTask()
	{
		return EntityManager.getAllByHql(" from TaskQuartz ");
	}
	
	public List<TaskQuartz> queryTask(HttpServletRequest request, int pagesize,
			String pWhereClause) throws Exception
	{
		String hql = " from TaskQuartz " + pWhereClause + " order by id ";
		return PageQuery.hbmQuery(request, hql, pagesize);
	}
	
	public TaskQuartz queryTaskByID(String ID) throws Exception
	{
		String hql = " from TaskQuartz where id=" + ID ;
		return (TaskQuartz)EntityManager.find(hql);
	}
	
	public void updTask(TaskQuartz t) throws Exception {
		EntityManager.update(t);
	}
	
	public TaskQuartz getTaskByPeformClass(String peformClass) {
		String hql = " from TaskQuartz where performClass='" + peformClass +"'";
		return (TaskQuartz)EntityManager.find(hql);
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
	private String getCronExpressionPattern(TaskQuartz task){
		
		String[] cronExp = { "0", "*", "*", "*", "*", "?", "*" };
		String[] cronExp2 = { "0", "*", "*", "*", "*", "?" };
	 
		//每天几点几分几秒
		if(task.getDelayType().intValue() == 2){
			//0 0 10 * * ? *
			cronExp[0] = "" + task.getDelaySecond(); //秒
			cronExp[1] = "" + task.getDelayMinute(); //分
			cronExp[2] = "" + task.getDelayHour(); //小时
			return QuartzUtil.join(cronExp, " ");
		}
		//每周几几点几分几秒
		else if(task.getDelayType().intValue() == 3){
			//0 15 10 ? * 1  每周一上午10:15
			cronExp2[0] = "" + task.getDelaySecond(); //秒
			cronExp2[1] = "" + task.getDelayMinute(); //分
			cronExp2[2] = "" + task.getDelayHour(); //小时
			cronExp2[3] = "?"; //
			cronExp2[4] = "*"; //
			cronExp2[5] = "" + task.getDelayWeek(); //
			
			return QuartzUtil.join(cronExp2, " ");
		}
		//每月几号几点几分几秒
		else if(task.getDelayType().intValue() == 4){
			// "0 15 10 15 * ?"	 每月15日上午10:15触发 
			cronExp2[0] = "" + task.getDelaySecond(); //秒
			cronExp2[1] = "" + task.getDelayMinute(); //分
			cronExp2[2] = "" + task.getDelayHour(); //小时
			cronExp2[3] = "" + task.getDelayMonthDay(); //每月几号
			
			return QuartzUtil.join(cronExp2, " ");
		}
		 return "";
	}
	
	
}
