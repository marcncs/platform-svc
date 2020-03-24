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
import com.winsafe.drp.dao.AppPurchaseIncome;
import com.winsafe.drp.dao.PurchaseBillTotal;
import com.winsafe.drp.dao.PurchaseIncome;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.DbUtil;

public class PrintPurchaseIncomeBillTotalAction extends BaseAction {
	@SuppressWarnings("unchecked")
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		initdata(request);
		try {
			String visitorgan = "";
			if (users.getVisitorgan() != null
					&& users.getVisitorgan().length() > 0) {
				visitorgan = getAndVisitOrgan("makeorganid");
			}
			String Condition = "  pb.istally=1  " + visitorgan + "  ";

			String[] tablename = { "PurchaseIncome" };
			String whereSql = getWhereSql(tablename);
			String blur = getKeyWordCondition("PID", "PLinkman","Tel");
			String timeCondition =getTimeCondition("MakeDate");
			whereSql = whereSql + blur + timeCondition + Condition; 
			whereSql = DbUtil.getWhereSql(whereSql); 

			ArrayList str = new ArrayList();
			AppPurchaseIncome aso = new AppPurchaseIncome();
			List pils = aso.getPurchaseBill(whereSql);
			double totalsum = 0.00;
			for (Iterator it = pils.iterator(); it.hasNext();) {
				PurchaseBillTotal pbt = new PurchaseBillTotal();
				PurchaseIncome pb = (PurchaseIncome) it.next();
				pbt.setPid(pb.getId());
				pbt.setTotalsum(pb.getTotalsum());
				pbt.setMakedate(DateUtil.formatDateTime(pb.getMakedate()));
				totalsum += pbt.getTotalsum();
				str.add(pbt);
			}
			DBUserLog.addUserLog(userid, 10,"报表分析>>打印采购入库按单据汇总");
			request.setAttribute("list", str);
			request.setAttribute("totalsum", totalsum);
			return mapping.findForward("toprint");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
