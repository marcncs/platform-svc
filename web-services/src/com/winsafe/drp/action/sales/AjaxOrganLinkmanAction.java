package com.winsafe.drp.action.sales;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppOlinkMan;
import com.winsafe.drp.dao.Olinkman;


public class AjaxOrganLinkmanAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String cid = request.getParameter("cid");
		try {
			AppOlinkMan al = new AppOlinkMan();
			Olinkman linkman = al.getMainLinkmanByCid(cid);


			
			JSONObject json = new JSONObject();			
			json.put("linkman", linkman);				
			response.setContentType("text/html; charset=UTF-8");
			//response.setHeader("X-JSON", json.toString());			
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
