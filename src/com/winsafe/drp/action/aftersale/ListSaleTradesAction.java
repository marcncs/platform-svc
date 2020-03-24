package com.winsafe.drp.action.aftersale;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppSaleTrades;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DbUtil;

public class ListSaleTradesAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		int pagesize = 10;
		super.initdata(request);

		try {

			String Condition = " (st.makeid="+userid+" "+getOrVisitOrgan("st.makeorganid")+") ";
			Map map = new HashMap(request.getParameterMap());
			Map tmpMap = EntityManager.scatterMap(map);
			String[] tablename = { "SaleTrades" };
			String whereSql = EntityManager.getTmpWhereSql(map, tablename);

			String timeCondition = DbUtil.getTimeCondition(map, tmpMap,
					" MakeDate");
			whereSql = whereSql + timeCondition + Condition; 
			whereSql = DbUtil.getWhereSql(whereSql); 

			AppSaleTrades asl = new AppSaleTrades();
			List pils = asl.getSaleTrades(request,pagesize, whereSql);


			request.setAttribute("also", pils);
			request.setAttribute("CID", request.getParameter("CID"));
			request.setAttribute("IsAudit", map.get("IsAudit"));
			request.setAttribute("IsBlankOut", map.get("IsBlankOut"));
			request.setAttribute("BeginDate", request.getParameter("BeginDate"));
			request.setAttribute("EndDate", request.getParameter("EndDate"));

			DBUserLog.addUserLog(userid,6, "销售换货>>列表销售换货");
			return mapping.findForward("list");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
}
