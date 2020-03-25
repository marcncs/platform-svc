package com.winsafe.drp.action.report;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppPurchaseBill;
import com.winsafe.drp.dao.PurchaseBill;
import com.winsafe.drp.dao.PurchaseBillTotal;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.DbUtil;

public class PurchaseBillTotalAction extends BaseAction {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		initdata(request);
		try {
			int pagesize = 20;
			String visitorgan = "";
			if (users.getVisitorgan() != null
					&& users.getVisitorgan().length() > 0) {
				visitorgan = getAndVisitOrgan("pb.makeorganid");
			}
			String Condition = " pb.isratify=1  " + visitorgan + "   ";

		
			String[] tablename = { "PurchaseBill" };
			String whereSql = getWhereSql(tablename);
			
			String timeCondition = DbUtil.getTimeCondition(map, tmpMap,
					" MakeDate");
			whereSql = whereSql +  timeCondition + Condition; 
			whereSql = DbUtil.getWhereSql(whereSql); 

			ArrayList str = new ArrayList();

			AppPurchaseBill aso = new AppPurchaseBill();
			List pils = aso.getPurchaseBill(request, pagesize, whereSql);
			double allsum = aso.getTotalPayment(whereSql);
			double totalsum = 0.00;
			for (Iterator it = pils.iterator(); it.hasNext();) {
				PurchaseBillTotal pbt = new PurchaseBillTotal();
				PurchaseBill pb = (PurchaseBill) it.next();
				pbt.setPid(pb.getId());
				pbt.setTotalsum(pb.getTotalsum());
				pbt.setMakedate(DateUtil.formatDateTime(pb.getMakedate()));
				totalsum += pbt.getTotalsum();
				str.add(pbt);
			}
			request.setAttribute("totalsum", totalsum);
			request.setAttribute("allsum", allsum);
			request.setAttribute("str", str);

			request.setAttribute("makeorganid", request.getParameter("MakeOrganID"));
			request.setAttribute("PurchaseDept", request.getParameter("PurchaseDept"));
			request.setAttribute("BeginDate", map.get("BeginDate"));
			request.setAttribute("EndDate", map.get("EndDate"));
			request.setAttribute("ID", request.getParameter("ID"));
			request.setAttribute("KeyWord", request.getParameter("KeyWord"));
			DBUserLog.addUserLog(userid, 10,"报表分析>>采购>>列表采购订单按单据汇总");
			return mapping.findForward("show");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
