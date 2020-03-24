package com.winsafe.drp.action.report;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppOrganWithdraw;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DbUtil;

public class OrganWithdrawBillTotalAction extends BaseAction {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		super.initdata(request);
		try {
			int pagesize = 20;
			String visitorgan = "";
			if (users.getVisitorgan() != null
					&& users.getVisitorgan().length() > 0) {
				visitorgan = getAndVisitOrgan("makeorganid");
			}

			String[] tablename = { "OrganWithdraw" };
			String whereSql = getWhereSql(tablename);
			String timeCondition = getTimeCondition("MakeDate");
			String Condition = "(iscomplete=1 and isblankout=0  "
					+ visitorgan + ")";
			String blur =getKeyWordCondition("MakeOrganID","POrganID","POrganName","ID");
			whereSql = whereSql + timeCondition +blur+ Condition; 
			whereSql = DbUtil.getWhereSql(whereSql); 


			AppOrganWithdraw aso = new AppOrganWithdraw();
			List pils = aso.getOrganWithdrawBillTotal(request, pagesize, whereSql);
			Double allsum = aso.getOrganWithdrawTotalSum(whereSql);

			request.setAttribute("allsum", allsum);
			request.setAttribute("list", pils);

			request.setAttribute("MakeOrganID", request.getParameter("MakeOrganID"));
			request.setAttribute("POrganID", request.getParameter("POrganID"));
			request.setAttribute("POrganName", request.getParameter("POrganName"));
			request.setAttribute("BeginDate", map.get("BeginDate"));
			request.setAttribute("EndDate", map.get("EndDate"));
			request.setAttribute("ID", request.getParameter("ID"));
			request.setAttribute("KeyWord", request.getParameter("KeyWord"));
			DBUserLog.addUserLog(userid, 10,"报表分析>>渠道>>列表渠道退货按单据汇总");
			return mapping.findForward("show");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
