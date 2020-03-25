package com.winsafe.drp.action.sales;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppInvoiceConf;
import com.winsafe.drp.dao.InvoiceConf;
import com.winsafe.hbm.util.Internation;

public class ToAddIntegralOrderAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		super.initdata(request);
		try {


			String transportmodeselect = Internation.getSelectTagByKeyAllDB(
					"TransportMode", "transportmode", false);
			String paymentmodeselect = Internation.getSelectPayByAllDB(
					"paymentmode", false);
			String transitselect = Internation.getSelectTagByKeyAllDB(
					"Transit", "transit", false);
			String ticketselect = Internation.getSelectTagByKeyAll("YesOrNo", request,
		  	          "isneedticket", false, null);
			String sourcename = Internation.getSelectTagByKeyAllDB("Source",
					"source", false);

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
//
			AppInvoiceConf aic = new AppInvoiceConf();
			List uls = aic.getAllInvoiceConf();
			ArrayList icls = new ArrayList();
			for (int u = 0; u < uls.size(); u++) {
					InvoiceConf ic = (InvoiceConf) uls.get(u);

					icls.add(ic);
			}

//			String cid = "";
//			String cname = "";
//			AppCustomer appCustomer = new AppCustomer();
//			cid = (String) request.getSession().getAttribute("cid");
//			if (cid != null && !cid.equals("") && !cid.equals("null")) {
//				Customer customer = appCustomer.getCustomer(cid);
//				cname = customer.getCname();
//			}
//
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
//			request.setAttribute("cid", cid);
//			request.setAttribute("cname", cname);

			// request.setAttribute("saletypeselect", saletypeselect);
//			request.setAttribute("fundattachselect", fundattachselect);
			
//			AppOrgan ao = new AppOrgan();
//		     List ols = ao.getAllOrgan();
//		     ArrayList alos = new ArrayList();
//		     
//		     for(int o=0;o<ols.size();o++){
//		    	 OrganForm ub = new OrganForm();
//		    	 Organ of = (Organ)ols.get(o);
//		    	 ub.setId(of.getId());
//		    	 ub.setOrganname(of.getOrganname());
//		    	 alos.add(ub);
//		     }
//		     
//		     request.setAttribute("alos",alos);
			
			request.setAttribute("ticketselect", ticketselect);
			request.setAttribute("transportmodeselect", transportmodeselect);
			request.setAttribute("paymentmodeselect", paymentmodeselect);
			request.setAttribute("transitselect", transitselect);
			request.setAttribute("sourcename", sourcename);
//			request.setAttribute("aldept", aldept);
			request.setAttribute("icls", icls);
			request.setAttribute("makeorganid",users.getMakeorganid());
			request.setAttribute("equiporganid", users.getMakeorganid());

			return mapping.findForward("toadd");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
}
