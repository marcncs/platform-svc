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

public class SupplySaleMoveProductTotalAction extends BaseAction {
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

			String[] tablename = { "SupplySaleMove", "SupplySaleMoveDetail" };
			String whereSql = getWhereSql(tablename);
			String timeCondition = getTimeCondition("MoveDate");
			String Condition = "sam.id=samd.ssmid and (iscomplete=1 and isblankout=0  "
					+ visitorgan + ")";
			String blur = getKeyWordCondition("ProductID","ProductName","MoveDate");
			whereSql = whereSql + timeCondition +blur+ Condition; 
			whereSql = DbUtil.getWhereSql(whereSql); 

			AppSupplySaleMove assm = new AppSupplySaleMove();
			
//			AppStockAlterMove aso = new AppStockAlterMove();
			List pils = assm.getSupplySaleMoveProductTotal(request, pagesize, whereSql);
			List allsum = assm.getSupplySaleMoveProductTotalSum(whereSql);

			double totalsum = 0.00;

			request.setAttribute("totalsum", totalsum);
			request.setAttribute("allsum", allsum);
			request.setAttribute("list", pils);
			
			request.setAttribute("MakeOrganID", request.getParameter("MakeOrganID"));
			request.setAttribute("ReceiveOrganID", request.getParameter("ReceiveOrganID"));
			request.setAttribute("BeginDate", map.get("BeginDate"));
			request.setAttribute("EndDate", map.get("EndDate"));
			request.setAttribute("ProductID", request.getParameter("ProductID"));
			request.setAttribute("ProductName", request.getParameter("ProductName"));
			request.setAttribute("KeyWord", request.getParameter("KeyWord"));
			DBUserLog.addUserLog(userid, 10,"报表分析>>渠道>>列表代销按产品汇总");

			return mapping.findForward("show");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
