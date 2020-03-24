package com.winsafe.drp.action.sys;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppICode;
import com.winsafe.drp.dao.ICode;


public class AjaxICodeAction extends Action {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String lcode =request.getParameter("lcode");
		try {
			AppICode applm = new AppICode();
			ICode icode = applm.getICodeByLcode(lcode);
			
			JSONObject json = new JSONObject();			
			json.put("icode", icode);				
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
