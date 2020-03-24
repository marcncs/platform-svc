package com.winsafe.drp.action.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppProductPriceHistory;
import com.winsafe.drp.dao.ProductPriceHistory;

public class ToUpdProductPriceHistoryAction extends Action {
	 public ActionForward execute(ActionMapping mapping, ActionForm form,
             HttpServletRequest request,
             HttpServletResponse response) throws Exception {
		 AppProductPriceHistory apph = new AppProductPriceHistory();
		try{
			String pphid = request.getParameter("pphid");
			ProductPriceHistory pph = apph.getProductPriceHistoryById(Long.valueOf(pphid));
			request.setAttribute("pph", pph);
			return mapping.findForward("toupd");
		}catch(Exception e){
		e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	 }
}
