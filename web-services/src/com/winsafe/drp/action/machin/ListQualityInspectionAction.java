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
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.sap.dao.AppQualityInspection;
import com.winsafe.sap.pojo.QualityInspection;

public class ListQualityInspectionAction extends BaseAction{
	private static Logger logger = Logger.getLogger(ListQualityInspectionAction.class);
	
	AppQualityInspection appQualityInspection = new AppQualityInspection();
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		int pagesize = 15;
		initdata(request);
		
		Map map = new HashMap(request.getParameterMap());
		Map tmpMap = EntityManager.scatterMap(map);
		String[] tablename = { "QualityInspection" };
		String whereSql = EntityManager.getTmpWhereSql2(map, tablename);
		String timeCondition = DbUtil.getTimeCondition(map, tmpMap,"inspectDate");
		whereSql = whereSql + timeCondition;
		String blur = DbUtil.getOrBlur2(map, tmpMap, "mCode","batch","inspector");
		whereSql = whereSql + blur;
		whereSql = DbUtil.getWhereSql(whereSql);
		List<QualityInspection> qiList = appQualityInspection.getQualityInspection(request, pagesize, whereSql);
		
		request.setAttribute("qiList", qiList);
		DBUserLog.addUserLog(request, "列表");
		return mapping.findForward("list");
	}
}
