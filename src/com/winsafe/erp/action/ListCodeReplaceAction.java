package com.winsafe.erp.action;

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
import com.winsafe.erp.metadata.ProduceFileType;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.sap.dao.AppUploadProduceLog;

public class ListCodeReplaceAction extends BaseAction{
	private static Logger logger = Logger.getLogger(ListCodeReplaceAction.class);
	
	AppUploadProduceLog appUploadProduceLog = new AppUploadProduceLog();
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		int pagesize = 15;
		initdata(request);
		
		String Condition = DbUtil.getWhereCondition(users, "o");
		Condition += " and upl.fileType=" + ProduceFileType.TOLLING_REPLACE.getValue();
		Map map = new HashMap(request.getParameterMap());
		Map tmpMap = EntityManager.scatterMap(map);
		String[] tablename = { "UploadProduceLog" };
		String whereSql = EntityManager.getTmpWhereSql2(map, tablename);
		String timeCondition = DbUtil.getTimeCondition(map, tmpMap," makeDate");
		whereSql = whereSql + timeCondition + Condition;
		String blur = DbUtil.getOrBlur2(map, tmpMap, "fileName");
		whereSql = whereSql + blur;
		whereSql = DbUtil.getWhereSql(whereSql);
		List<Map<String,String>> sapUploadLogs = appUploadProduceLog.getUploadProduceLogBySql(request, pagesize, whereSql);
		
		request.setAttribute("uploadLogs", sapUploadLogs);
		DBUserLog.addUserLog(request, "列表");
		return mapping.findForward("list");
	}
}
