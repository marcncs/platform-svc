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
import com.winsafe.drp.dao.PurchasePlanForm;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.hbm.util.DateUtil;

public class PurchasePlanDetailAction extends Action {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String id = request.getParameter("ID");

		request.getSession().setAttribute("purchaseplanid", id);

		try {
			AppPurchasePlan app = new AppPurchasePlan();			
			PurchasePlan pp = app.getPurchasePlanByID(id);
			PurchasePlanForm ppf = new PurchasePlanForm();
			ppf.setId(id);
			ppf.setPurchasesort(pp.getPurchasesort());
			ppf.setBillno(pp.getBillno());
			ppf.setPlandate(pp.getPlandate());
			ppf.setPlandept(pp.getPlandept());
			ppf.setPlanid(pp.getPlanid());
			ppf.setIsaudit(pp.getIsaudit());

			ppf.setActid(pp.getAuditid());
			ppf.setAuditdate(pp.getAuditdate());

			ppf.setIsratify(pp.getIsratify());
			ppf.setRatifyid(pp.getRatifyid());

			
			ppf.setRatifydate(pp.getRatifydate());
			ppf.setIscomplete(pp.getIscomplete());
			ppf.setRemark(pp.getRemark());

			AppPurchasePlanDetail apad = new AppPurchasePlanDetail();
			List padls = apad.getPurchasePlanDetailByPaID(id);
			ArrayList als = new ArrayList();
			for (int i = 0; i < padls.size(); i++) {
				PurchasePlanDetailForm ppdf = new PurchasePlanDetailForm();
				PurchasePlanDetail o = (PurchasePlanDetail) padls.get(i);
				ppdf.setProductid(o.getProductid());
				
		        ppdf.setProductname(o.getProductname());
		        ppdf.setSpecmode(o.getSpecmode());
		        ppdf.setUnitid(o.getUnitid());
		        ppdf.setUnitprice(o.getUnitprice());
		        ppdf.setQuantity(o.getQuantity());
		        ppdf.setChangequantity(o.getChangequantity());
		        ppdf.setRequiredate(DateUtil.formatDate(o.getRequiredate()));
		         ppdf.setAdvicedate(DateUtil.formatDate(o.getAdvicedate()));
				als.add(ppdf);
			}


			request.setAttribute("als", als);
			request.setAttribute("ppf", pp);
			UsersBean users = UserManager.getUser(request);
			return mapping.findForward("detail");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
}
