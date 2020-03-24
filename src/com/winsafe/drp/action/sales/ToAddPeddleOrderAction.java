package com.winsafe.drp.action.sales;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppCustomer;
import com.winsafe.drp.dao.AppInvoiceConf;
import com.winsafe.drp.dao.Customer;
import com.winsafe.drp.dao.InvoiceConf;

public class ToAddPeddleOrderAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		super.initdata(request);
		try {

			// String saletypeselect =
			// Internation.getSelectTagByKeyAllDB("SaleType","saletype", false);
//			String fundattachselect = Internation.getSelectTagByKeyAllDB(
//					"FundAttach", "fundattach", false);
//			String transportmodeselect = Internation.getSelectTagByKeyAllDB(
//					"TransportMode", "transportmode", false);
//			String paymentmodeselect = Internation.getSelectPayByAllDB(
//					"paymentmode", false);
//			String transitselect = Internation.getSelectTagByKeyAllDB(
//					"Transit", "transit", false);
//			
//			String isneedticketselect =Internation.getSelectTagByKeyAll(
//					"YesOrNo", request, "isneedticket", false, null);
//			
//			String isaccountselect =Internation.getSelectTagByKeyAll(
//					"YesOrNo", request, "isaccount", false, null);
//
//			AppDept ad = new AppDept();
//			List dls = ad.getDept();
//			ArrayList aldept = new ArrayList();
//			for (int i = 0; i < dls.size(); i++) {
//				Dept d = new Dept();
//				Object[] ob = (Object[]) dls.get(i);
//				d.setId(Long.valueOf(ob[0].toString()));
//				d.setDeptname(ob[1].toString());
//				aldept.add(d);
//			}

//			AppUsers au = new AppUsers();
//			List uls = au.getIDAndLoginName();
//			ArrayList als = new ArrayList();
//			for (int u = 0; u < uls.size(); u++) {
//				UsersBean ubs = new UsersBean();
//				Object[] ub = (Object[]) uls.get(u);
//				ubs.setUserid(Long.valueOf(ub[0].toString()));
//				ubs.setRealname(ub[2].toString());
//				als.add(ubs);
//			}

			String cid = "";
			String cname = "";
			AppCustomer appCustomer = new AppCustomer();
			cid = (String) request.getSession().getAttribute("cid");
			if (cid != null && !cid.equals("") && !cid.equals("null")) {
				Customer customer = appCustomer.getCustomer(cid);
				cname = customer.getCname();
			}
			request.setAttribute("cid", cid);
			request.setAttribute("cname", cname);

//			
//			AppWarehouse aw = new AppWarehouse();
//			List wls = aw.getEnableWarehouseByVisit(userid);
//			StringBuffer sb = new StringBuffer();
//			sb.append("<select name=\"warehouseid\">");
//			for (int i = 0; i < wls.size(); i++) {
//				Object[] o = (Object[]) wls.get(i);
//				sb.append("<option value=\"").append(
//						Long.valueOf(o[0].toString())).append("\">").append(
//						o[1].toString()).append("</option>");
//			}
//			sb.append("</select>");
			
			
			
			AppInvoiceConf aic = new AppInvoiceConf();
			List vls = aic.getAllInvoiceConf();
			ArrayList icls = new ArrayList();
			for (int u = 0; u < vls.size(); u++) {
					InvoiceConf ic = (InvoiceConf) vls.get(u);

					icls.add(ic);
			}
			
			request.setAttribute("icls", icls);

			// request.setAttribute("saletypeselect", saletypeselect);
//			request.setAttribute("fundattachselect", fundattachselect);
//			request.setAttribute("isneedticketselect", isneedticketselect);
//			request.setAttribute("transportmodeselect", transportmodeselect);
//			request.setAttribute("paymentmodeselect", paymentmodeselect);
//			request.setAttribute("transitselect", transitselect);
//			request.setAttribute("isaccountselect", isaccountselect);
//			request.setAttribute("aldept", aldept);
//			request.setAttribute("als", als);
//			request.setAttribute("warehouseselect", sb.toString());
			return mapping.findForward("toadd");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
}
