package com.winsafe.drp.action.report;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppTakeTicket;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DbUtil;

public class CostProductDetailAction extends BaseAction {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		initdata(request);
		try {
			int pagesize = 20;
			String visitorgan = "";
			if (users.getVisitorgan() != null
					&& users.getVisitorgan().length() > 0) {
				visitorgan = getAndVisitOrgan("makeorganid");
			}
			String Condition = " so.id=sod.ttid and (so.isblankout=0 "
					+ visitorgan
					+ " )  ";

			String[] tablename = { "TakeTicket", "TakeTicketDetail" };
			String whereSql = getWhereSql(tablename);
			String blur = getKeyWordCondition("OID","OName","Tel", "BillNo", "ProductID",
					"ProductName", "SpecMode","Batch");

			String timeCondition = getTimeCondition("MakeDate");
			whereSql = whereSql + blur + timeCondition + Condition; 
			whereSql = DbUtil.getWhereSql(whereSql); 

			AppTakeTicket asod = new AppTakeTicket();
			List pils = asod.getCostProductDetail(request, pagesize, whereSql);
			List sumobj = asod.getTotalSubum(whereSql);

			request.setAttribute("sumobj", sumobj);
			request.setAttribute("list", pils);

			request.setAttribute("MakeOrganID", request.getParameter("MakeOrganID"));
			request.setAttribute("EquipOrganID", request.getParameter("EquipOrganID"));
			request.setAttribute("BeginDate", map.get("BeginDate"));
			request.setAttribute("EndDate", map.get("EndDate"));
			request.setAttribute("ProductID", request.getParameter("ProductID"));
			request.setAttribute("ProductName", request.getParameter("ProductName"));
			request.setAttribute("BSort", request.getParameter("BSort"));
			request.setAttribute("KeyWord", request.getParameter("KeyWord"));
			DBUserLog.addUserLog(userid, 10,"报表分析>>财务>>营业成本明细");
			return mapping.findForward("detail");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
