package com.winsafe.sap.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.StringUtil;
import com.winsafe.sap.dao.AppPrintJob;

public class ListPrintJobAction extends BaseAction{
	private static Logger logger = Logger.getLogger(ListPrintJobAction.class);
	
	AppPrintJob appPrintJob = new AppPrintJob();
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		int pagesize = 10;
		initdata(request);
		
		String condition = " and p.plantCode in (select o.oecode from UserVisit as uv, Organ as o where uv.visitorgan = o.id and uv.userid=" + userid + ")";
		
		Map map = new HashMap(request.getParameterMap());
		Map tmpMap = EntityManager.scatterMap(map);
		String printJobId = (String)map.get("printJobId");
		if(!StringUtil.isEmpty(printJobId)) {
			try {
				Integer.parseInt(printJobId);
			} catch (Exception e) {
				map.put("printJobId", 0);
			}
		}
		String[] tablename = { "PrintJob" };
		String whereSql = EntityManager.getTmpWhereSql2(map, tablename);
		String timeCondition = DbUtil.getTimeCondition4(map, tmpMap," transOrderDate");
		whereSql = whereSql + " isDelete = 0 and";
		whereSql = whereSql + timeCondition;
		whereSql = DbUtil.getWhereSql(whereSql);
		whereSql = whereSql + condition;
		List printJobs = appPrintJob.getPrintJob(request, pagesize, whereSql);
		
		request.setAttribute("printJobs", printJobs);
		DBUserLog.addUserLog(request, "列表");
		return mapping.findForward("list");
	}
		
}
