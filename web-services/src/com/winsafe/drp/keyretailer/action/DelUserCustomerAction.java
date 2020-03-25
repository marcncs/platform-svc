package com.winsafe.drp.keyretailer.action;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.keyretailer.dao.AppSalesAreaCountry;
import com.winsafe.drp.keyretailer.dao.AppUserCustomer;

public class DelUserCustomerAction extends BaseAction {
	private static Logger logger = Logger.getLogger(DelUserCustomerAction.class);
	
	private AppUserCustomer appSalesAreaCountry = new AppUserCustomer();
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		//初始化
		initdata(request);
		try {
			String id = request.getParameter("id");
			appSalesAreaCountry.delUserCustomerById(id);
			
			request.setAttribute("result", "databases.del.success");
			return mapping.findForward("del");
		} catch (Exception e) {
			logger.error("",e);
			throw e;
		}
	}
	

}
