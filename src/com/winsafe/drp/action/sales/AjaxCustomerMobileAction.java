package com.winsafe.drp.action.sales;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppCustomer;

public class AjaxCustomerMobileAction extends BaseAction{
	public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
		String mobile=request.getParameter("Mobile");
		
		//parmet=new String(parmet.getBytes("iso-8859-1"), "UTF-8");

		try{
			AppCustomer ac=new AppCustomer();
			int count = ac.getRepeatCustomerByMobile(mobile);
			
			response.setContentType("text/xml;charset=utf-8");
			response.setHeader("Cache-Control", "no-cache");
			PrintWriter out = response.getWriter();
			//String s = root.asXML();
			out.write(String.valueOf(count));
			
			out.close();
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
}

