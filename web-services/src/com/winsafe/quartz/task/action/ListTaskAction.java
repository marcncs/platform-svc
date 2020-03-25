package com.winsafe.quartz.task.action;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.quartz.Scheduler;

import com.winsafe.common.util.StringUtil;
import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.quartz.factory.ExecuteTimeExpressionSettings;
import com.winsafe.quartz.factory.InitTask;
import com.winsafe.quartz.factory.WsScheduledTimerTask;
import com.winsafe.quartz.task.dao.TaskService;
import com.winsafe.quartz.task.pojo.TaskQuartz;

public class ListTaskAction extends BaseAction{
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		//初始化
		initdata(request);
	    int pagesize = 20;
		TaskService appTask = new TaskService();
		
		String taskName = request.getParameter("taskName");
		String whereSql = " where isDelete = 0 ";
		//显示注册服务
		ServletContext application = request.getSession().getServletContext();
		WsScheduledTimerTask registerTask = (WsScheduledTimerTask) application.getAttribute("schTimerTask");
		Map<String,String> regTaskHandler = registerTask.getRegTaskHandler();
		Set<String> taskNameSet = regTaskHandler.keySet();
		String taskNameWhere = "";
		for(String tname : taskNameSet){
			taskNameWhere += ",'" + tname + "'";
		}
		if(!StringUtil.isEmpty(taskNameWhere)){
			taskNameWhere = taskNameWhere.substring(1);
			taskNameWhere = " and performClass in (" + taskNameWhere +")";
			whereSql  += taskNameWhere;
		}
		
		if(!StringUtil.isEmpty(taskName)){
			whereSql += " and taskName like '%" + taskName + "%'";
		}
		
		List<TaskQuartz> tList = appTask.queryTask(request, pagesize, whereSql);
		Map<Integer,Scheduler> factoryMap = InitTask.factoryMap;
		Set<Integer> factorySet = factoryMap.keySet();
		for(TaskQuartz t : tList){
			//根据内存中
			if(factorySet.contains(t.getId())){
				t.setStatusDisplay("运行");
			}else {
				t.setStatusDisplay("挂起");
			}
			t.setDisplayName(getDelayTypeDisplayName(t));
		}

		request.setAttribute("tList", tList);
		request.setAttribute("taskName", taskName);
		
		 DBUserLog.addUserLog(userid,"系统管理","任务>>列表任务");
	    
	    return mapping.findForward("list");
	  }
	
	public String getDelayTypeDisplayName(TaskQuartz task) throws Exception{
		Integer type = task.getType();
		// 单次任务
		if (type.intValue() == 1) {
			return DateUtil.formatDateTime(task.getStartDateTime());
		}
		// 循环任务
		else {
			Integer delayType = task.getDelayType();
			switch (delayType) {
			case 0: {
				// 每隔%s秒
				return String.format(ExecuteTimeExpressionSettings.CYCLE_PATTERN_TYPR.DELAY_SECONDS, task.getCycleJobInterval());
			}
			case 1: {
				// 每隔%s分
				return String.format(ExecuteTimeExpressionSettings.CYCLE_PATTERN_TYPR.DELAY_MINUTES, task.getCycleJobInterval());
			}
			case 2: {
				// 时分秒
				parseExpress(task);
				// 每天%s时%s分%秒
				return String.format(ExecuteTimeExpressionSettings.CYCLE_PATTERN_TYPR.DELAY_EVERY_DAY_HOUR, 
						null != task.getDelayHour() ? task .getDelayHour() : task.getCycleJobInterval(), 
						null != task.getDelayMinute() ? task.getDelayMinute() : 0, 
						null != task .getDelaySecond() ? task.getDelaySecond() : 0);
			}
			case 3: {
				// 时分秒
				parseExpress(task);
				String month[] = new String[]{"日","一","二","三","四","五","六"};
				// 每周%s %s时%s分%s秒
				return String.format(ExecuteTimeExpressionSettings.CYCLE_PATTERN_TYPR.DELAY_EVERY_WEEK,
						month[task.getDelayWeek()-1],
						task.getDelayHour(), 
						task.getDelayMinute(), 
						task .getDelaySecond());
			}
			case 4: {
				// 时分秒
				parseExpress(task);
				// 每月%s号 %s时%s分%秒
				return String.format(ExecuteTimeExpressionSettings.CYCLE_PATTERN_TYPR.DELAY_EVERY_MONTH, 
						task.getDelayMonthDay(),
						task.getDelayHour(), 
						task.getDelayMinute(), 
						task .getDelaySecond());
			}
			default:
				return "";
			}
		}
	}
	
	private void parseExpress(TaskQuartz task)throws Exception{
		
		String con = task.getCronExpression();
		if (!StringUtil.isEmpty(con)) {
			try {
				String arr[] = con.split(" ");
				task.setDelaySecond(Integer.parseInt(arr[0]));
				task.setDelayMinute(Integer.parseInt(arr[1]));
				task.setDelayHour(Integer.parseInt(arr[2]));
				//每周几几点几分几秒
				if(task.getDelayType().intValue() == 3){
					task.setDelayWeek(Integer.parseInt(arr[5]));
				}
				//每月几号几点几分几秒
				else if(task.getDelayType().intValue() == 4){
					task.setDelayMonthDay(Integer.parseInt(arr[3]));
				}
			} catch (Exception e) {
				task.setDelayHour(task.getCycleJobInterval());
				task.setDelayMinute(0);
				task.setDelaySecond(0);
			}
		} else {
			task.setDelayHour(task.getCycleJobInterval());
			task.setDelayMinute(0);
			task.setDelaySecond(0);
		}
		
	}

	
}
