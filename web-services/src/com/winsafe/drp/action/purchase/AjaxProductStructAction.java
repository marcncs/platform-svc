package com.winsafe.drp.action.purchase;
 
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppProductStruct;
import com.winsafe.drp.dao.ProductStruct;


public class AjaxProductStructAction extends Action {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		//初始化
		AppProductStruct app = new AppProductStruct();
		
		try {
			
			String psid =request.getParameter("psid");
			ProductStruct ps = app.getProductStructById(psid);
			JSONObject json = new JSONObject();			
			json.put("ps", ps);				
			response.setContentType("text/html; charset=UTF-8");
		    response.setHeader("Cache-Control", "no-cache");
		    PrintWriter out = response.getWriter();
		    out.print(json.toString());
		    out.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
