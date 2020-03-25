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
import com.winsafe.sap.dao.AppSapUploadLog;
import com.winsafe.sap.metadata.SapFileErrorType;
import com.winsafe.sap.metadata.SapFileType;
import com.winsafe.sap.pojo.UploadSAPLog;

public class ListSapDeliveryLogAction extends BaseAction{
	private static Logger logger = Logger.getLogger(ListSapDeliveryLogAction.class);
	
	AppSapUploadLog appSapUploadLog = new AppSapUploadLog();
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		int pagesize = 15;
		initdata(request);
		
		Map map = new HashMap(request.getParameterMap());
		Map tmpMap = EntityManager.scatterMap(map);
		String[] tablename = { "UploadSAPLog" };
		String whereSql = EntityManager.getTmpWhereSql2(map, tablename);
		String timeCondition = DbUtil.getTimeCondition(map, tmpMap," makeDate");
		whereSql = whereSql + timeCondition;
		String fileType= (String)map.get("category");
		//tommy 发货日志，与发票日志放在一起
		//whereSql = whereSql + "fileType = "+SapFileType.DELIVERY.getDatabaseValue();
		whereSql = whereSql + "(fileType = "+SapFileType.DELIVERY.getDatabaseValue()+ " or fileType = "+SapFileType.INVOICE.getDatabaseValue() + ") and ";
		request.setAttribute("category", "delivery");
		String blur = DbUtil.getOrBlur2(map, tmpMap, "fileName", "billNo" );
		whereSql = whereSql + blur;
		whereSql = DbUtil.getWhereSql(whereSql);
		List<UploadSAPLog> sapUploadLogs = appSapUploadLog.getSapUploadLog(request, pagesize, whereSql);
		
		request.setAttribute("errTypes", SapFileErrorType.values());
		request.setAttribute("sapUploadLogs", sapUploadLogs);
		DBUserLog.addUserLog(request, "列表");
		return mapping.findForward("list");
	}
}
