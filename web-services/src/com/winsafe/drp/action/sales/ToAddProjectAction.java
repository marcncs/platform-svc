package com.winsafe.drp.action.sales;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppCustomer;
import com.winsafe.drp.dao.Customer;
import com.winsafe.hbm.util.Internation;

public class ToAddProjectAction extends BaseAction {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		
//		Long userid = users.getUserid();

		try {

			String pcontentselect = Internation.getSelectTagByKeyAllDB(
					"HapContent", "pcontent", false);
			String pstatusselect = Internation.getSelectTagByKeyAllDB(
					"PStatus", "pstatus", false);
			
			String cid = "";
			String cname = "";
			AppCustomer appCustomer = new AppCustomer();
			cid = (String) request.getSession().getAttribute("cid");
			if (cid != null && !cid.equals("")&&!cid.equals("null")) {
				Customer customer = appCustomer.getCustomer(cid);
				cname = customer.getCname();
			}
			request.setAttribute("cid", cid);
			request.setAttribute("cname", cname);
			
			request.setAttribute("pcontentselect", pcontentselect);
			request.setAttribute("pstatusselect", pstatusselect);

			return mapping.findForward("toadd");

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			//HibernateUtil.closeSession();
		}
		return mapping.getInputForward();
	}

}
