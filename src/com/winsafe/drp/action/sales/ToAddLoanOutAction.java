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
import com.winsafe.drp.dao.AppDept;
import com.winsafe.drp.dao.AppUsers;
import com.winsafe.drp.dao.AppWarehouse;
import com.winsafe.drp.dao.Customer;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.hbm.util.Internation;

public class ToAddLoanOutAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
//		Long userid = users.getUserid();

		try {

			String transportmodeselect = Internation.getSelectTagByKeyAllDB(
					"TransportMode", "transportmode", false);			
			String transitselect = Internation.getSelectTagByKeyAllDB(
					"Transit", "transit", false);

			AppDept ad = new AppDept();
//			List dls = ad.getDept();
			ArrayList aldept = new ArrayList();
//			for (int i = 0; i < dls.size(); i++) {
//				Dept d = new Dept();
//				Object[] ob = (Object[]) dls.get(i);
//				d.setId(Long.valueOf(ob[0].toString()));
//				d.setDeptname(ob[1].toString());
//				aldept.add(d);
//			}

			AppUsers au = new AppUsers();
			List uls = au.getIDAndLoginName();
			ArrayList als = new ArrayList();
			for (int u = 0; u < uls.size(); u++) {
				UsersBean ubs = new UsersBean();
				Object[] ub = (Object[]) uls.get(u);
//				ubs.setUserid(Long.valueOf(ub[0].toString()));
				ubs.setRealname(ub[2].toString());
				als.add(ubs);
			}

			String cid = "";
			String cname = "";
			AppCustomer appCustomer = new AppCustomer();
			cid = (String) request.getSession().getAttribute("cid");
			if (cid != null && !cid.equals("") && !cid.equals("null")) {
				Customer customer = appCustomer.getCustomer(cid);
				cname = customer.getCname();
			}

			
			AppWarehouse aw = new AppWarehouse();
//			List wls = aw.getEnableWarehouseByVisit(userid);
			StringBuffer sb = new StringBuffer();
			sb.append("<select name=\"warehouseid\">");
//			for (int i = 0; i < wls.size(); i++) {
//				Object[] o = (Object[]) wls.get(i);
//				sb.append("<option value=\"").append(
//						Long.valueOf(o[0].toString())).append("\">").append(
//						o[1].toString()).append("</option>");
//			}
			sb.append("</select>");
			request.setAttribute("cid", cid);
			request.setAttribute("cname", cname);


			request.setAttribute("transportmodeselect", transportmodeselect);
			request.setAttribute("transitselect", transitselect);
			request.setAttribute("aldept", aldept);
			request.setAttribute("als", als);
			request.setAttribute("warehouseselect", sb.toString());
			return mapping.findForward("toadd");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
}
