package com.winsafe.drp.action.report;

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
import com.winsafe.hbm.util.DateUtil;

public class RevenueWasteInitAction extends Action {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		try {
			String begindate = request.getParameter("BeginDate");
			if ( null == begindate || "".equals(begindate) ){
				begindate = DateUtil.getMonthFirstDay();				
			}
			String enddate = request.getParameter("EndDate");
			if ( null == enddate || "".equals(enddate) ){
				enddate = DateUtil.getMonthLastDay();				
			}
			request.getSession().setAttribute("BeginDate", begindate);
			request.getSession().setAttribute("EndDate", enddate);
			
			AppOrgan ao = new AppOrgan();
			List ols = ao.getAllOrgan();
			ArrayList alos = new ArrayList();

			for (int o = 0; o < ols.size(); o++) {
				OrganForm ub = new OrganForm();
				Organ of = (Organ) ols.get(o);
				ub.setId(of.getId());
				ub.setOrganname(of.getOrganname());
				alos.add(ub);
			}
			
//			AppUsers au = new AppUsers();
//			List als = au.getIDAndLoginNameByOID2(users.getMakeorganid());
			
			request.setAttribute("alos", alos);
//			request.setAttribute("als", als);

			return mapping.findForward("success");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
