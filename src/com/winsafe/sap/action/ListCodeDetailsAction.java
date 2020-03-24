package com.winsafe.sap.action;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.sap.dao.AppCartonCode;

public class ListCodeDetailsAction extends Action{
	
	AppCartonCode appCartonCode = new AppCartonCode();
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		int pagesize = 10;
//		String printJobId = request.getParameter("printJobId");
//		String whereSql = " where o.printJobId = " + printJobId;
		Map map = new HashMap(request.getParameterMap());
		Map tmpMap = EntityManager.scatterMap(map);
		String[] tablename = { "CartonCode" };
		String whereSql = EntityManager.getTmpWhereSql2(map, tablename);
//		String blur = DbUtil.getOrBlur(map, tmpMap, "cartonCode", "outPinCode");
//		whereSql = whereSql + blur;
		whereSql = DbUtil.getWhereSql(whereSql);
		
		List cartonCodes = appCartonCode.getCartonCode(request, pagesize, whereSql);
		
		request.setAttribute("cartonCodes", cartonCodes);
		
		return mapping.findForward("detail");
	}
}
