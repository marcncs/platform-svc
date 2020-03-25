package com.winsafe.drp.action.sales;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppCIntegralDeal;
import com.winsafe.drp.dao.AppOIntegralDeal;
import com.winsafe.drp.dao.AppVocationOrder;
import com.winsafe.drp.dao.AppVocationOrderDetail;
import com.winsafe.drp.dao.VocationOrder;

public class DelVocationOrderAction extends BaseAction {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		try {

			String soid = request.getParameter("SOID");
			AppVocationOrder aso = new AppVocationOrder();
			AppVocationOrderDetail appsod = new AppVocationOrderDetail();
			VocationOrder so = aso.getVocationOrderByID(soid);
			if (so.getIsaudit() == 1) {
				String result = "databases.record.nodel";
				request.setAttribute("result", result);
				return new ActionForward("/sys/lockrecordclose.jsp");
			}

			aso.delVocationOrder(soid);
			appsod.delVocationOrderBySOID(soid);
			
			
			AppCIntegralDeal acid = new AppCIntegralDeal();
			acid.delCIntegralDeal(soid);
			
			
			AppOIntegralDeal aoid = new AppOIntegralDeal();
			aoid.delOIntegralDeal(soid);
			
			request.setAttribute("result", "databases.del.success");
			
//			Long userid = users.getUserid();
//			DBUserLog.addUserLog(userid, "删除行业销售单,编号："+soid);

			return mapping.findForward("del");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
