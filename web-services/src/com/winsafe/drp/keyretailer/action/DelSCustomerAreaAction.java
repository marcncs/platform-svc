package com.winsafe.drp.keyretailer.action;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.keyretailer.dao.AppSBonusTarget;
import com.winsafe.drp.keyretailer.dao.AppSCustomerArea;
import com.winsafe.drp.keyretailer.dao.AppSUserArea;

public class DelSCustomerAreaAction extends BaseAction {
	private static Logger logger = Logger.getLogger(DelSCustomerAreaAction.class);
	
	private AppSCustomerArea app = new AppSCustomerArea();
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		//初始化
		initdata(request);
		try {
			String id = request.getParameter("id");
			app.delSCustomerAreaById(id);
			
			request.setAttribute("result", "databases.del.success");
			return mapping.findForward("del");
		} catch (Exception e) {
			logger.error("",e);
			throw e;
		}
	}
	

}
