package com.winsafe.drp.action.users;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppOrgan;
import com.winsafe.drp.dao.Organ;
import com.winsafe.drp.dao.OrganForm;

public class ToCopyOrganPriceAction extends Action {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String id = request.getParameter("ID");

		try {
			AppOrgan ao = new AppOrgan();
			Organ organ = ao.getOrganByID(id);

			List oals = ao.getAllOrgan();
			ArrayList als = new ArrayList();

			for (int i = 0; i < oals.size(); i++) {
				OrganForm caf = new OrganForm();
				Organ o = (Organ) oals.get(i);
				caf.setId(o.getId());
				caf.setOrganname(o.getOrganname());
				caf.setParentid(o.getParentid());
				if (o.getParentid().equals("0")) {
					caf.setParentidname("");
				} else {
					caf.setParentidname(ao.getOrganByID(o.getParentid())
							.getOrganname());
				}
				caf.setRank(o.getRank());
				
				if ( !o.getId().equals(id) ){
					als.add(caf);
				}
			}

			request.setAttribute("als", als);
			request.setAttribute("organ", organ);
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return mapping.findForward("toupd");
	}

}
