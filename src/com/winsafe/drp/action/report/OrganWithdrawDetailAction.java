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

public class OrganWithdrawDetailAction extends BaseAction {
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
			String Condition = "ow.id=owd.owid and (ow.iscomplete=1 and ow.isblankout=0 "
					+ visitorgan + " )  ";
			String[] tablename = { "OrganWithdraw", "OrganWithdrawDetail" };
			String whereSql = getWhereSql(tablename);
			String timeCondition = getTimeCondition("MakeDate");
			String blur =getKeyWordCondition("MakeOrganID","POrganID","POrganName","ID","ProductID","ProductName");
			whereSql = whereSql + timeCondition +blur+ Condition; 
			whereSql = DbUtil.getWhereSql(whereSql); 

			AppOrganWithdraw asod = new AppOrganWithdraw();
			List sumobj = asod.getOrganWithdrawProductTotalSum(whereSql);
			List list = asod
					.getOrganWithdrawDetail(request, pagesize, whereSql);

			request.setAttribute("allsum", sumobj);
			request.setAttribute("list", list);
			request.setAttribute("MakeOrganID", request.getParameter("MakeOrganID"));
			request.setAttribute("POrganID", request.getParameter("POrganID"));
			request.setAttribute("BeginDate", map.get("BeginDate"));
			request.setAttribute("EndDate", map.get("EndDate"));
			request.setAttribute("ProductID", request.getParameter("ProductID"));
			request.setAttribute("ProductName", request.getParameter("ProductName"));
			request.setAttribute("MakeID", request.getParameter("MakeID"));
			request.setAttribute("KeyWord", request.getParameter("KeyWord"));
			
			request.setAttribute("type", request.getParameter("type"));
			
			DBUserLog.addUserLog(userid, 10,"报表分析>>渠道>>列表渠道退货明细");
			return mapping.findForward("show");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
