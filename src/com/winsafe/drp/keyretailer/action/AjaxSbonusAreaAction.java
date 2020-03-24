package com.winsafe.drp.keyretailer.action;

import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.keyretailer.dao.AppSalesAreaCountry;
public class AjaxSbonusAreaAction extends Action {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String regionId =request.getParameter("regionId");
		try {
			
			AppSalesAreaCountry abic = new AppSalesAreaCountry();
			List<Map<String,String>> dlist = abic.getCountryAreasBySalesAreaId(regionId);
			JSONObject json = new JSONObject();			
			json.put("areaList", dlist);				
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
