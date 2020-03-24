package com.winsafe.drp.action.sys;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppOrganGrade;
import com.winsafe.drp.dao.AppStockPileAgeing;
import com.winsafe.drp.dao.OrganGrade;
import com.winsafe.drp.dao.StockPileAgeing;

public class ToUpdStockPileAgeingAction extends Action {
	AppStockPileAgeing aspa = new AppStockPileAgeing();
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		Integer id = Integer.valueOf(request.getParameter("ID"));

		try {
			StockPileAgeing spa = aspa.getStockPileAgeingById(id);
			request.setAttribute("wf", spa);

		} catch (Exception e) {
			e.printStackTrace();
		} 
		return mapping.findForward("toupd");
	}
	
}
