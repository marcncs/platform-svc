package com.winsafe.drp.action.purchase;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppProviderProduct;

public class AjaxValidateProvideProductAction extends Action{
	public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
		String productid=request.getParameter("productid");
		String provideid= request.getParameter("provideid");
		
		//parmet=new String(parmet.getBytes("iso-8859-1"), "UTF-8");
		try{
			AppProviderProduct ac=new AppProviderProduct();
			int count = ac.getProviderProductByPPID(provideid,productid);
			

			JSONObject json = new JSONObject();			
			json.put("count", count);				
			response.setContentType("text/html; charset=UTF-8");
		    response.setHeader("Cache-Control", "no-cache");
		    PrintWriter out = response.getWriter();
		    out.print(json.toString());
		    out.close();

			
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
}

