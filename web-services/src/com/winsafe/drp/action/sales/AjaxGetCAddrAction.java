package com.winsafe.drp.action.sales;

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppCAddr;
import com.winsafe.drp.dao.CAddr;

public class AjaxGetCAddrAction extends BaseAction {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String cid = request.getParameter("cid");

		try {
			AppCAddr aca = new AppCAddr();
			List cals = aca.getCAddrByCid(cid);
			JSONArray calist = new JSONArray();
			for (int i = 0; i < cals.size(); i++) {
				CAddr ca = (CAddr) cals.get(i);
				calist.put(ca);
			}
			
			JSONObject json = new JSONObject();			
			json.put("calist", calist);				
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
