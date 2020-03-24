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
import com.winsafe.sap.dao.AppCovertErrorLog;
import com.winsafe.sap.dao.AppCovertUploadReport;
import com.winsafe.sap.pojo.CovertErrorLog;
import com.winsafe.sap.pojo.CovertUploadReport;

public class ListCovertErrorLogAction extends BaseAction{
	private static Logger logger = Logger.getLogger(ListCovertErrorLogAction.class);
	
	private AppCovertErrorLog appCovertErrorLog = new AppCovertErrorLog();
	private AppCovertUploadReport appCovertUploadReport = new AppCovertUploadReport();
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		int pagesize = 10;
		initdata(request);
		String ids = "";
		Map map = new HashMap(request.getParameterMap());
		Map tmpMap = EntityManager.scatterMap(map);
		String id = (String)map.get("uploadPrId");
		if(!StringUtil.isEmpty(id)) {
			try {
				Integer.parseInt(id);
			} catch (Exception e) {
				map.put("uploadPrId", "0");
			}
		}
		if(!StringUtil.isEmpty((String)map.get("isDetail"))) {
			if(!StringUtil.isEmpty((String)map.get("id"))) {
				ids = (String)map.get("id");
			} else {
				String[] tablename = { "CovertUploadReport" };
				String whereSql = EntityManager.getTmpWhereSql2(map, tablename);
				String timeCondition = DbUtil.getTimeCondition4(map, tmpMap," uploadDate");
				whereSql = whereSql + timeCondition;
				whereSql = DbUtil.getWhereSql(whereSql);
				if(StringUtil.isEmpty(whereSql)) {
					whereSql = " where materialCode is null and batch is null ";
				}
				ids = "select id from CovertUploadReport " + whereSql;
			}
		}
		String[] tablename = { "CovertErrorLog" };
		String whereSql = EntityManager.getTmpWhereSql2(map, tablename);
		if(!StringUtil.isEmpty(ids)) {
			whereSql = whereSql + " uploadPrId in ("+ids+") and";
		}
		if(!StringUtil.isEmpty((String)map.get("uploadPrId"))) {
			whereSql = whereSql + " uploadPrId in (select id from CovertUploadReport where uploadPrId = "+(String)map.get("uploadPrId")+") and";
		}
		String timeCondition = DbUtil.getTimeCondition4(map, tmpMap," uploadDate");
		whereSql = whereSql + timeCondition;
		whereSql = DbUtil.getWhereSql(whereSql);
		
		List<CovertErrorLog> logs = appCovertErrorLog.getCovertErrorLogs(request, pagesize, whereSql);
		
//		if(StringUtil.isEmpty((String)map.get("isDetail"))) {
			for(CovertErrorLog log : logs) {
				try {
					CovertUploadReport report = appCovertUploadReport.loadCovertUploadReportById(log.getUploadPrId());
					if(report != null) {
						log.setCovertUploadId(report.getUploadPrId());
						log.setProductionLine(report.getLineNo());
					}
				} catch (Exception e) {
					logger.error("CovertUploadReport not found by id " + log.getUploadPrId(), e);
				}
				
			}
//		}
		
		request.setAttribute("id", ids);
		request.setAttribute("logs", logs);
		DBUserLog.addUserLog(request, "查看");
		return mapping.findForward("list");
	}
		
}
