package com.winsafe.drp.keyretailer.action;

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
import com.winsafe.drp.keyretailer.dao.AppSBonusLog;
import com.winsafe.drp.keyretailer.pojo.SBonusLog;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DbUtil;

public class ListSBonusLogAction extends BaseAction{
//	private static Logger logger = Logger.getLogger(ListSBonusLogAction.class);
	
	private AppSBonusLog appSBonusLog = new AppSBonusLog();
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		int pagesize = 10;
		initdata(request);
		
		Map map = new HashMap(request.getParameterMap());
		Map tmpMap = EntityManager.scatterMap(map);
//		String[] tablename = { "SBonusLog" };
//		String whereSql = EntityManager.getTmpWhereSql2(map, tablename);
		String timeCondition = DbUtil.getTimeCondition4(map, tmpMap," sbl.makeDate");
		String blur = DbUtil.getOrBlur(map, tmpMap, "sbl.billNo","sbl.logMsg","o.organname");
		String condition = " and "+timeCondition + blur;
		condition = DbUtil.getWhereSql(condition);
//		List<SBonusLog> sBonusLogs = appSBonusLog.getSBonusLog(request, pagesize, whereSql);
		
		List<Map<String, String>> sBonusLogs = appSBonusLog.getSBonusLogs(request, pagesize, condition);
		
		request.setAttribute("sBonusLogs", sBonusLogs);
		DBUserLog.addUserLog(request, "列表");
		return mapping.findForward("list");
	}
		
}
