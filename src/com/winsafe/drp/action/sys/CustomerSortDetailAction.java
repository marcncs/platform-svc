package com.winsafe.drp.action.sys;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppCustomerSort;
import com.winsafe.drp.dao.AppUserSort;
import com.winsafe.drp.dao.AppUsers;
import com.winsafe.drp.dao.CustomerSort;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UserSort;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.dao.WarehouseVisitForm;

public class CustomerSortDetailAction extends Action {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Long id = Long.valueOf(request.getParameter("ID"));
		// Long cid = Long.valueOf((String)
		// request.getSession().getAttribute("cid"));
		UsersBean users = UserManager.getUser(request);
		Integer userid = users.getUserid();
		AppUsers au = new AppUsers();
		CustomerSort cs = new CustomerSort();

		AppCustomerSort aw = new AppCustomerSort();

		try {
			cs = aw.getCustomerSortById(id);

			AppUserSort aus = new AppUserSort();
			List slrv = aus.getUserSortBySortId(id);
			List rvls = new ArrayList();
			for (int r = 0; r < slrv.size(); r++) {
				WarehouseVisitForm wvf = new WarehouseVisitForm();
				UserSort us = (UserSort) slrv.get(r);
				// wvf.setWidname(aw.getWarehouseByID(Long.valueOf(or[1].toString())).getWarehousename());
				wvf.setUserid(us.getUserid());
				wvf.setUseridname(au.getUsersByid(us.getUserid())
						.getRealname());

				rvls.add(wvf);
			}

			request.setAttribute("cs", cs);
			request.setAttribute("rvls", rvls);

			return mapping.findForward("detail");

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			//HibernateUtil.closeSession();
		}

		return mapping.getInputForward();
	}

}
