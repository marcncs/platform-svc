package com.winsafe.drp.action.warehouse;

import java.sql.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppProductStockpileAll;
import com.winsafe.drp.util.Dateutil;

public class ToProductStockpileVerifyAction extends BaseAction{
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		AppProductStockpileAll apsa = new AppProductStockpileAll();
		
		String id =request.getParameter("id");
		String ids =request.getParameter("ids");
		//ProductStockpileAll psa =apsa.getProductStockpileAllByID(Long.valueOf(id));
		
		//request.setAttribute("psa", psa);		
		Integer verifyStatus = 1;
		Date verifydate = new Date(System.currentTimeMillis());
		String remark = "";
		request.setAttribute("verifyStatus", verifyStatus);
		request.setAttribute("verifydate", verifydate);
		request.setAttribute("remark", remark);
		request.setAttribute("pid", id);
		request.setAttribute("ids", ids);
		return mapping.findForward("toverify");
		

	}
}
