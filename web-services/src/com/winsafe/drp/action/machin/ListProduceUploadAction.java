package com.winsafe.drp.action.machin;

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
import com.winsafe.sap.pojo.UploadProduceLog;

public class ListProduceUploadAction extends BaseAction{
	private static Logger logger = Logger.getLogger(ListProduceUploadAction.class);
	
	AppUploadProduceLog appUploadProduceLog = new AppUploadProduceLog();
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		int pagesize = 15;
		initdata(request); 
		
//		String Condition = DbUtil.getWhereCondition(users, "o");
		Map map = new HashMap(request.getParameterMap());
		Map tmpMap = EntityManager.scatterMap(map); 
		map.put("fileType", ProduceFileType.HZ_PLANT.getValue());
		String[] tablename = { "UploadProduceLog" };
		String whereSql = EntityManager.getTmpWhereSql2(map, tablename);
		String timeCondition = DbUtil.getTimeCondition(map, tmpMap," makeDate");
		whereSql = whereSql + timeCondition;
		request.setAttribute("category", "delivery");
		String blur = DbUtil.getOrBlur2(map, tmpMap, "fileName");
		whereSql = whereSql + blur;
		whereSql = DbUtil.getWhereSql(whereSql);
		List<UploadProduceLog> sapUploadLogs = appUploadProduceLog.getUploadProduceLog(request, pagesize, whereSql);
		
		request.setAttribute("uploadLogs", sapUploadLogs);
		DBUserLog.addUserLog(request, "列表");
		return mapping.findForward("list");
	}
}
