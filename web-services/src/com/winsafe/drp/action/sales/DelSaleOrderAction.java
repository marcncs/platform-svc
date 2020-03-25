package com.winsafe.drp.action.sales;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppCIntegralDeal;
import com.winsafe.drp.dao.AppOIntegralDeal;
import com.winsafe.drp.dao.AppSaleOrder;
import com.winsafe.drp.dao.AppSaleOrderDetail;
import com.winsafe.drp.dao.SaleOrder;
import com.winsafe.drp.util.DBUserLog;

public class DelSaleOrderAction extends BaseAction {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		try {

			String soid = request.getParameter("SOID");
			AppSaleOrder aso = new AppSaleOrder();
			AppSaleOrderDetail appsod = new AppSaleOrderDetail();
			SaleOrder so = aso.getSaleOrderByID(soid);
			if (so.getIsaudit() == 1) {
				String result = "databases.record.nodel";
				request.setAttribute("result", result);
				return new ActionForward("/sys/lockrecordclose.jsp");
			}

			aso.delSaleOrder(soid);
			appsod.delSaleOrderBySOID(soid);
			
			
			AppCIntegralDeal acid = new AppCIntegralDeal();
			acid.delCIntegralDeal(soid);
			
			
			AppOIntegralDeal aoid = new AppOIntegralDeal();
			aoid.delOIntegralDeal(soid);
			
			request.setAttribute("result", "databases.del.success");
			
			initdata(request);
			DBUserLog.addUserLog(userid, 6,"零售单>>删除零售单,编号："+soid, so);

			return mapping.findForward("del");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
