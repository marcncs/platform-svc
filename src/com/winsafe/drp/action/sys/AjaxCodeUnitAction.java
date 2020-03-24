package com.winsafe.drp.action.sys;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppCodeUnit;
import com.winsafe.drp.dao.CodeUnit;


public class AjaxCodeUnitAction extends Action {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String id =request.getParameter("ucode");
		try {
			AppCodeUnit applm = new AppCodeUnit();
			CodeUnit cu = applm.getCodeUnitByID(id);
			
			JSONObject json = new JSONObject();			
			json.put("cu", cu);				
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
