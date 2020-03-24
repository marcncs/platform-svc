package com.winsafe.drp.action.self;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppCalendarAwake;
import com.winsafe.drp.dao.CalendarAwakeForm;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.DbUtil;

public class ListAllCalendarAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		initdata(request);
		int pagesize = 15;
		try {
			String userCondition = " userid=" + userid;
			Map map = new HashMap(request.getParameterMap());
			String BeginDate = request.getParameter("BeginDate");
			if (BeginDate == null) {
				BeginDate = DateUtil.getCurrentDateString();
				map.put("BeginDate", BeginDate);
			}
			Map tmpMap = EntityManager.scatterMap(map);
			String[] tablename = { "CalendarAwake" };
		
			String whereSql = EntityManager.getTmpWhereSql(map, tablename);
			
			String timeCondition = DbUtil.getTimeConditionhql(map, tmpMap, " AwakeDateTime"); 
			String blur = DbUtil.getBlur(map, tmpMap, " AwakeContent ");
			whereSql = whereSql + blur +timeCondition+ userCondition; 
			whereSql = DbUtil.getWhereSql(whereSql);
			

			AppCalendarAwake aca = new AppCalendarAwake();

			List tpls = aca.searchAwake(request,pagesize, whereSql);
			ArrayList cals = new ArrayList();
			for (int t = 0; t < tpls.size(); t++) {
				CalendarAwakeForm caf = new CalendarAwakeForm();
				Object[] ob = (Object[]) tpls.get(t);
				caf.setId(Integer.valueOf(ob[0].toString()));
				caf.setAwakecontent(String.valueOf(ob[1]));
				caf.setAwakedatetime((Date) ob[2]);
				caf.setIsawake(Integer.valueOf(ob[4].toString()));
				cals.add(caf);
			}

			request.setAttribute("cals", cals);
			DBUserLog.addUserLog(userid, 0,"我的办公桌>>列表所有日程");
			return mapping.findForward("listall");
		} catch (Throwable e) {
			e.printStackTrace();
		}

		return new ActionForward(mapping.getInput());
	}

}
