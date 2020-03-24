package com.winsafe.drp.action.sales;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppSaleInvoice;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DbUtil;

public class ListSaleInvoiceAction extends BaseAction {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		int pagesize = 10;
		super.initdata(request);
		try {
			String Condition = " (si.makeid="+userid+" "+getOrVisitOrgan("si.makeorganid")+") ";
			Map map = new HashMap(request.getParameterMap());
			Map tmpMap = EntityManager.scatterMap(map);
			String[] tablename = { "SaleInvoice" };
			String whereSql = EntityManager.getTmpWhereSql(map, tablename);

			String timeCondition = DbUtil.getTimeCondition(map, tmpMap,
					" MakeDate");
			String blur = DbUtil.getOrBlur(map, tmpMap, "CID","CNAME","InvoiceCode");
			whereSql = whereSql + blur + timeCondition + Condition;
			whereSql = DbUtil.getWhereSql(whereSql);

			AppSaleInvoice asl = new AppSaleInvoice();
			List pils = asl.getSaleInvoiceAll(request, pagesize, whereSql);

			request.setAttribute("alsi", pils);

			DBUserLog.addUserLog(userid, 6,"零售管理>>列表零售发票");
			return mapping.findForward("list");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
}
