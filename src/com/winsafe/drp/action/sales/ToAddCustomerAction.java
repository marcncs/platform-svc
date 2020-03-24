package com.winsafe.drp.action.sales;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppCountryArea;
import com.winsafe.drp.dao.AppCustomer;
import com.winsafe.drp.dao.AppCustomerSort;
import com.winsafe.drp.dao.AppUsers;
import com.winsafe.drp.dao.CountryArea;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.hbm.util.Internation;

public class ToAddCustomerAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {

			AppUsers au = new AppUsers();

			String mac = "-387";
			String disk = "009842e29b2e52b00f005583d830c20";
			//String mac = "009842e29b2e52b00f005583d830c201";
			// String mac = "Y26T99ZC";
			// String mac = "PVF804Z32203UN";
			AppCustomer ac = new AppCustomer();
			AppCustomerSort appcs = new AppCustomerSort();
			int cc = ac.getCustomerCount("");
//			if (cc > 2000) {
//				if (!mac.equals(MACAddress.getMACAddress())) {
//					String result = "sys.noregedit";
//					request.setAttribute("result", result);
//					return new ActionForward("/sys/lockrecord.jsp");// ;mapping.findForward("lock");
//				}
//			}
//			
//			if (cc > 2000) {
//				if (!disk.equals(MACAddress.getDiskAddress())) {
//					String result = "sys.noregedit";
//					request.setAttribute("result", result);
//					return new ActionForward("/sys/lockrecord.jsp");// ;mapping.findForward("lock");
//				}
//			}

//			if (au.getCountUsers() > 100) {
//				String result = "sys.userlimit";
//				request.setAttribute("result", result);
//				return new ActionForward("/sys/lockrecord.jsp");// ;mapping.findForward("lock");
//			}
			List sortlist = appcs.getSortByUserid(userid);
			String kindname = Internation.getSelectTagByKeyAll("Kind", request,
					"kind", "0", null);
			String transitname = Internation.getSelectTagByKeyAllDB("Transit",
			 "transit", false);
			String ratename = Internation.getSelectTagByKeyAllDB("PricePolicy",
					 "rate", false);

			String vocationname = Internation.getSelectTagByKeyAllDB(
					"Vocation", "vocation", false);
			String customertypename = Internation.getSelectTagByKeyAll(
					"CustomerType", request, "customertype", false, null);
			String customerstatusname = Internation.getSelectTagByKeyAll(
					"CustomerStatus", request, "customerstatus", false, null);
			String yauldname = Internation.getSelectTagByKeyAll("Yauld",
					request, "yauld", false, null);
			String sourcename = Internation.getSelectTagByKeyAllDB("Source",
					"source", false);
			String paymentmodeselect = Internation.getSelectPayByAllDB(
					"paymentmode", false);
			String transportmode = Internation.getSelectTagByKeyAllDB("TransportMode",
					 "transportmode", false);
			
			
			

			 AppCountryArea aca = new AppCountryArea();
		      List list = aca.getProvince();

		      ArrayList cals = new ArrayList();

				for (int i = 0; i < list.size(); i++) {
					CountryArea ca = new CountryArea();
					Object ob[] = (Object[]) list.get(i);
					ca.setId(Integer.valueOf(ob[0].toString()));
					ca.setAreaname(ob[1].toString());
					ca.setParentid(Integer.valueOf(ob[2].toString()));
					ca.setRank(Integer.valueOf(ob[3].toString()));
					cals.add(ca);
				}

			// List list1 = aca.getProvince(us.getUserid());
			// ArrayList cls = new ArrayList();
			//
			// for (int i = 0; i < list1.size(); i++) {
			// CountryArea ca = new CountryArea();
			// Object ob[] = (Object[]) list1.get(i);
			// ca.setId(Long.valueOf(ob[0].toString()));
			// ca.setAreaname(ob[1].toString());
			// ca.setParentid(Long.valueOf(ob[2].toString()));
			// ca.setRank(Integer.valueOf(ob[3].toString()));
			// cls.add(ca);
			// }

			// AppUsers au = new AppUsers();
			List uls = au.getIDAndLoginName();
			ArrayList als = new ArrayList();
			for (int u = 0; u < uls.size(); u++) {
				UsersBean ubs = new UsersBean();
				Object[] ub = (Object[]) uls.get(u);
				ubs.setUserid(Integer.valueOf(ub[0].toString()));
				ubs.setRealname(ub[2].toString());
				als.add(ubs);
			}
			
//			AppDept ad = new AppDept();
//			List dls = ad.getDept();
//			ArrayList aldept = new ArrayList();
//			for (int i = 0; i < dls.size(); i++) {
//				Dept d = new Dept();
//				Object[] ob = (Object[]) dls.get(i);
//				d.setId(Integer.valueOf(ob[0].toString()));
//				d.setDeptname(ob[1].toString());
//				aldept.add(d);
//			}

			request.setAttribute("kindname", kindname);
			request.setAttribute("sortlist", sortlist);
			request.setAttribute("transitname", transitname);
			request.setAttribute("ratename", ratename);
			request.setAttribute("vocationname", vocationname);
			request.setAttribute("customertypename", customertypename);
			request.setAttribute("customerstatusname", customerstatusname);
			request.setAttribute("yauldname", yauldname);
			request.setAttribute("sourcename", sourcename);
			request.setAttribute("paymentmodeselect", paymentmodeselect);
			request.setAttribute("transportmode", transportmode);
			request.setAttribute("als", als);
			request.setAttribute("cals", cals);
			
//			request.setAttribute("aldept", aldept);
			return mapping.findForward("toadd");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
}
