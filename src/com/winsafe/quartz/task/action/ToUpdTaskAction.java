package com.winsafe.quartz.task.action;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.common.util.StringUtil;
import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.quartz.task.dao.TaskService;
import com.winsafe.quartz.task.pojo.TaskQuartz;

public class ToUpdTaskAction extends BaseAction{
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		TaskService appTask = new TaskService();
		String tid = request.getParameter("ID");
		
		TaskQuartz task = appTask.queryTaskByID(tid);
		parseExpress(task);
		request.setAttribute("task", task);
		
		//初始化页面选项
		Map<Integer, String> hourMap = getNumberSelectList(0,23);
		Map<Integer, String> minMap = getNumberSelectList(0,59);
		Map<Integer, String> intervalMinMap = getNumberSelectList(1,59);
		Map<Integer, String> intervalSecMap = getNumberSelectList(1,59);
		Map<Integer, String> secMap = getNumberSelectList(0,59);
		Map<Integer, String> monthMap = getNumberSelectList(1,28);
		Map<Integer, String> weekMap = getDelayWeekList();
		
		request.setAttribute("hourMap", hourMap);
		request.setAttribute("minMap", minMap);
		request.setAttribute("secMap", secMap);
		request.setAttribute("monthMap", monthMap);
		request.setAttribute("weekMap", weekMap);
		request.setAttribute("intervalMinMap", intervalMinMap);
		request.setAttribute("intervalSecMap", intervalSecMap);
		
	    return mapping.findForward("upd");
	    
	  }
	/**
	 * 解析表达式
	 * 
	 */
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
	/**
	 * 获取周
	 * Create Time 2014-3-20 下午03:39:30 
	 * @return
	 * @throws Exception
	 * @author lipeng
	 */
	public Map<Integer, String> getDelayWeekList() throws Exception {
		Map<Integer, String> delayMap = new TreeMap<Integer, String>();
        String month[] = new String[]{"日","一","二","三","四","五","六"};
        for(int i=0 ;i<month.length ; i++){
        	delayMap.put(i+1, month[i]);
        }
		return delayMap;
	}
	/***
	 * 获取数字
	 * Create Time 2014-3-20 下午03:39:09 
	 * @param begin
	 * @param end
	 * @return
	 * @throws Exception
	 * @author lipeng
	 */
	public Map<Integer, String> getNumberSelectList(Integer begin,Integer end) throws Exception {
		Map<Integer, String> delayMap = new TreeMap<Integer, String>();
		for (int i = begin; i <= end; i++) {
			if (i < 10) {
				delayMap.put(i, "0" + i);
			} else if (i >= 10) {
				delayMap.put(i, "" + i);
			}
		}
		return delayMap;
	}
	
}
