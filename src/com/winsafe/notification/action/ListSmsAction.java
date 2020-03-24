package com.winsafe.notification.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.StringUtil; 
import com.winsafe.sms.dao.AppSms;

public class ListSmsAction extends BaseAction{
	private AppSms appSms = new AppSms();
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Map<String,Object> param = new HashMap<>();
		int pagesize = 10;
		String type = request.getParameter("type");
		String id = request.getParameter("id");
		String condition = "";
		try {
			if(!StringUtil.isEmpty(type)) {
				condition += " type="+type+" and ";
			}
			if(!StringUtil.isEmpty(id)) {
				condition += " id = :id and ";
				param.put("id", Long.valueOf(id));
			}
		} catch (Exception e) {
			param.put("id", -1l);
		}
		initdata(request,type);
		
		Map map = new HashMap(request.getParameterMap());
		Map tmpMap = EntityManager.scatterMap(map);
		map.remove("type");
		tmpMap.remove("type");
		String[] tablename = { "Sms" };
		String whereSql = EntityManager.getTmpWhereSql2ForHql(map, tablename, param);
		String timeCondition = DbUtil.getTimeCondition4ForHql(map, tmpMap," sendTime",  param);
		whereSql = whereSql + timeCondition;
		String blur = DbUtil.getOrBlur2ForHql(map, tmpMap, param, "mobileNo", "content");
		whereSql = whereSql + blur + condition;
		whereSql = DbUtil.getWhereSql(whereSql);
		if(param.containsKey("sendStatus")) {
			param.put("sendStatus", Integer.valueOf((String)param.get("sendStatus")));
		}
		List sms = appSms.getSms(request, pagesize, whereSql, param);
		
		request.setAttribute("sms", sms);
		DBUserLog.addUserLog(request, "列表");
		return mapping.findForward("list");
	}
}
