package com.winsafe.drp.action.purchase;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppPurchasePlan;
import com.winsafe.drp.dao.AppPurchasePlanDetail;
import com.winsafe.drp.dao.PurchasePlan;
import com.winsafe.drp.dao.PurchasePlanDetail;
import com.winsafe.drp.dao.PurchasePlanDetailForm;
import com.winsafe.hbm.util.DateUtil;

public class ToUpdPurchasePlanAction extends Action {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String id = request.getParameter("ID");

		try {
			AppPurchasePlan app = new AppPurchasePlan();
			PurchasePlan pp = app.getPurchasePlanByID(id);

			if (pp.getIsaudit() == 1) { 
				String result = "databases.record.lock";
				request.setAttribute("result", result);
				return mapping.findForward("lock");
			}



			AppPurchasePlanDetail apad = new AppPurchasePlanDetail();
			List padls = apad.getPurchasePlanDetailByPaID(id);
			ArrayList als = new ArrayList();
			for (int i = 0; i < padls.size(); i++) {
				PurchasePlanDetailForm padf = new PurchasePlanDetailForm();
				PurchasePlanDetail ppd = (PurchasePlanDetail) padls.get(i);
				padf.setProductid(ppd.getProductid());
				padf.setProductname(ppd.getProductname());
				padf.setSpecmode(ppd.getSpecmode());
				padf.setUnitid(ppd.getUnitid());
				padf.setUnitprice(ppd.getUnitprice());
				padf.setQuantity(ppd.getQuantity());
				padf.setRequiredate(DateUtil.formatDate(ppd.getRequiredate()));
				padf.setAdvicedate(DateUtil.formatDate(ppd.getAdvicedate()));
				padf.setRequireexplain(ppd.getRequireexplain());
				als.add(padf);
			}


			request.setAttribute("ppf", pp);
			request.setAttribute("als", als);

			return mapping.findForward("toupd");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
}
