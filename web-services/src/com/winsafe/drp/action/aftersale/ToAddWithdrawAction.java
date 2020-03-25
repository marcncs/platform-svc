package com.winsafe.drp.action.aftersale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppCustomer;
import com.winsafe.drp.dao.Customer;

public class ToAddWithdrawAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		super.initdata(request);
		try {
			
			String cid = request.getParameter("cid");
			Customer customer = null;
			if ( cid == null ){
				cid= "";
			}
			if ( !cid.equals("") ){
				AppCustomer ac = new AppCustomer();
				customer = ac.getCustomer(cid);
			}

			
//			AppWarehouse aw = new AppWarehouse();
//			List wls = aw.getEnableWarehouseByVisit(userid);
//			ArrayList alw = new ArrayList();
//			for (int i = 0; i < wls.size(); i++) {
//				Warehouse w = new Warehouse();
//				Object[] o = (Object[]) wls.get(i);
//				w.setId(o[0].toString());
//				w.setWarehousename(o[1].toString());
//				alw.add(w);
//			}
//			String paymentmodename = Internation.getSelectPayByAllDB(
//					"paymentmode",false);
//			String withdrawsort = Internation.getSelectTagByKeyAllDB(
//					"WithdrawSort", "withdrawsort",false);
//			request.setAttribute("paymentmodename", paymentmodename);
//			request.setAttribute("withdrawsort", withdrawsort);
			request.setAttribute("customer", customer);
//			request.setAttribute("alw", alw);
			return mapping.findForward("toadd");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return mapping.getInputForward();
	}

}
