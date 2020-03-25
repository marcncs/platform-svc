package com.winsafe.drp.action.sales;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppBaseResource;
import com.winsafe.drp.dao.AppCustomer;
import com.winsafe.drp.dao.AppDitch;
import com.winsafe.drp.dao.BaseResource;
import com.winsafe.drp.dao.Ditch;
import com.winsafe.hbm.util.Internation;

public class ToAddContactDitchAction extends BaseAction {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		
//		Long userid = users.getUserid();

		try {

			AppBaseResource abr = new AppBaseResource();
			List uls = abr.getBaseResource("SelectContact");
			ArrayList als = new ArrayList();
			for (int u = 0; u < uls.size(); u++) {
				BaseResource br = new BaseResource();
				Object[] ub = (Object[]) uls.get(u);
				br.setTagsubkey(Integer.valueOf(ub[2].toString()));
				br.setTagsubvalue(ub[3].toString());
				als.add(br);
			}

			String contactmodeselect = Internation.getSelectTagByKeyAll(
					"ContactMode", request, "contactmode", false, null);
			String contactpropertyselect = Internation.getSelectTagByKeyAll(
					"ContactProperty", request, "contactproperty", false, null);

			String did = "";
			String dname = "";
			AppCustomer appCustomer = new AppCustomer();
			AppDitch ad = new AppDitch();
			did = (String) request.getSession().getAttribute("did");
			if (did != null && !did.equals("")&&!did.equals("null")) {
				Ditch d = ad.getDitchByID(Long.valueOf(did));
				dname = d.getDname();
			}
			request.setAttribute("did", did);
			request.setAttribute("dname", dname);

			request.setAttribute("contactmodeselect", contactmodeselect);
			request
					.setAttribute("contactpropertyselect",
							contactpropertyselect);
			request.setAttribute("als", als);
			return mapping.findForward("toadd");

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			//HibernateUtil.closeSession();
		}
		return mapping.getInputForward();
	}

}
