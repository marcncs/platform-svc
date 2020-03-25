package com.winsafe.drp.action.sales;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppInvoiceConf;

public class AjaxGetInvMsgAction extends BaseAction {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Integer invmsg = Integer.valueOf(request.getParameter("invmsg"));

		try {
			Double ivrate =0d;
			AppInvoiceConf aic = new AppInvoiceConf();
			
			ivrate = aic.getInvoiceConfById(invmsg).getIvrate();
			
//				for (int i = 0; i < cals.size(); i++) {
//					CAddr ca = (CAddr) cals.get(i);
//					caddr += ca.getCaddr() + ",";
//				}
//				
				//caddr = caddr.substring(0, caddr.length() - 1);
			
			

			//System.out.println(caddr);

			response.setContentType("text/xml;charset=utf-8");
			response.setHeader("Cache-Control", "no-cache");
			PrintWriter out = response.getWriter();
			// String s = root.asXML();
			out.write(ivrate.toString());

			out.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
