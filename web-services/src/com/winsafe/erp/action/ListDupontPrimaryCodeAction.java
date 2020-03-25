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
import com.winsafe.erp.pojo.DupontPrimaryCode;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.sap.dao.AppDupontCodeUploadLog;

public class ListDupontPrimaryCodeAction extends BaseAction{
	private static Logger logger = Logger.getLogger(ListDupontPrimaryCodeAction.class);
	
	AppDupontCodeUploadLog appUploadLog = new AppDupontCodeUploadLog();
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		int pagesize = 15;
		initdata(request);
		
		Map map = new HashMap(request.getParameterMap());
		Map tmpMap = EntityManager.scatterMap(map);
		String[] tablename = { "DupontPrimaryCode" };
		String whereSql = EntityManager.getTmpWhereSql2(map, tablename);
//		String timeCondition = DbUtil.getTimeCondition(map, tmpMap," makeDate");
//		whereSql = whereSql + timeCondition;
//		String blur = DbUtil.getOrBlur2(map, tmpMap, "fileName" );
//		whereSql = whereSql + blur;
		whereSql = DbUtil.getWhereSql(whereSql);
		List<DupontPrimaryCode> codes = appUploadLog.getDupontPrimaryCode(request, pagesize, whereSql);
		request.setAttribute("codes", codes);
		
		DBUserLog.addUserLog(request, "列表");
		return mapping.findForward("list");
	}
}
