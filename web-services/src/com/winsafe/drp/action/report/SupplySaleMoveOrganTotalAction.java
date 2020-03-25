package com.winsafe.drp.action.report;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppSupplySaleMove;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DbUtil;

public class SupplySaleMoveOrganTotalAction extends BaseAction {
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

			String[] tablename = { "SupplySaleMove" };
			String whereSql = getWhereSql(tablename);
			String timeCondition = getTimeCondition("MoveDate");
			String Condition = " (iscomplete=1 and isblankout=0 "
					+ visitorgan + ")";
			String blur = getKeyWordCondition("MakeOrganID","MakeOrganIDName","ReceiveOrganIDName","OLinkman","OTel");
			whereSql = whereSql + timeCondition +blur+ Condition; 

			whereSql = DbUtil.getWhereSql(whereSql); 
			
			AppSupplySaleMove aso = new AppSupplySaleMove();
			List pils = aso.getSupplySaleMoveOrganTotal(request, pagesize, whereSql);
			List totalls = aso.getSupplySaleMoveOrganTotalSum(whereSql);
			double totalsum = 0.00;

			request.setAttribute("totalsum", totalsum);
			request.setAttribute("allsum", totalls);
			request.setAttribute("list", pils);

			request.setAttribute("MakeOrganID", request.getParameter("MakeOrganID"));
			request.setAttribute("InOrganID", request.getParameter("InOrganID"));
			request.setAttribute("OLinkman", request.getParameter("OLinkman"));
			request.setAttribute("OTel", request.getParameter("OTel"));
			request.setAttribute("BeginDate", map.get("BeginDate"));
			request.setAttribute("EndDate", map.get("EndDate"));
			request.setAttribute("KeyWord", request.getParameter("KeyWord"));
			DBUserLog.addUserLog(userid, 10,"报表分析>>渠道>>列表代销按机构汇总");
			return mapping.findForward("show");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
