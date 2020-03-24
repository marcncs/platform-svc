package com.winsafe.drp.action.report;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppPurchaseTrades;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DbUtil;

public class PurchaseTradesBillTotalAction extends BaseAction {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		initdata(request);
		try{
			int pagesize = 20;
			String visitorgan = "";
			if (users.getVisitorgan() != null
					&& users.getVisitorgan().length() > 0) {
				visitorgan = getAndVisitOrgan("pt.makeorganid");
			}
			String Condition = " pt.id=ptd.ptid and  pt.isreceive=1 and pt.isblankout=0 " + visitorgan
			+ "   ";

			String[] tablename = { "PurchaseTrades","PurchaseTradesDetail" };
			String whereSql = getWhereSql(tablename);
			String timeCondition = getTimeCondition("MakeDate");
			String blur = getKeyWordCondition("pt.id");
			whereSql = whereSql + timeCondition + blur + Condition; 
			whereSql = DbUtil.getWhereSql(whereSql); 

			
			AppPurchaseTrades asod=new AppPurchaseTrades(); 

			
			List pils = asod.getPurchaseTradesBill(request, pagesize, whereSql);
			double allsum = asod.getTotalSum(whereSql);
			
			request.setAttribute("allsum", allsum);
			request.setAttribute("list", pils);

			request.setAttribute("MakeOrganID", request.getParameter("MakeOrganID"));
			request.setAttribute("MakeDeptID", request.getParameter("MakeDeptID"));
			request.setAttribute("BeginDate", map.get("BeginDate"));
			request.setAttribute("EndDate", map.get("EndDate"));
			request.setAttribute("ID", request.getParameter("ID"));
			request.setAttribute("KeyWord", request.getParameter("KeyWord"));
			DBUserLog.addUserLog(userid, 10,"报表分析>>采购>>列表采购换货按单据汇总");
			return mapping.findForward("show");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
