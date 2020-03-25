package com.winsafe.drp.action.report;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppCountryArea;
import com.winsafe.drp.dao.AppOrgan;
import com.winsafe.drp.dao.AppUsers;
import com.winsafe.drp.dao.CountryArea;
import com.winsafe.drp.dao.Organ;
import com.winsafe.drp.dao.OrganForm;
import com.winsafe.drp.dao.UsersBean;

public class SaleOrderCustomerInitAction extends Action {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {

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
			AppUsers au = new AppUsers();

			List uls = au.getIDAndLoginName();
			ArrayList als = new ArrayList();
			for (int u = 0; u < uls.size(); u++) {
				UsersBean ubs = new UsersBean();
				Object[] ub = (Object[]) uls.get(u);
				ubs.setUserid(Integer.valueOf(ub[0].toString()));
				ubs.setRealname(ub[2].toString());
				als.add(ubs);
			}
			
			AppOrgan ao = new AppOrgan();
		     List ols = ao.getAllOrgan();
		     ArrayList alos = new ArrayList();
		     
		     for(int o=0;o<ols.size();o++){
		    	 OrganForm ub = new OrganForm();
		    	 Organ of = (Organ)ols.get(o);
		    	 ub.setId(of.getId());
		    	 ub.setOrganname(of.getOrganname());
		    	 alos.add(ub);
		     }
		     
		     request.setAttribute("alos",alos);

			request.setAttribute("als", als);
			request.setAttribute("cals", cals);

			return mapping.findForward("saleordercustomer");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
