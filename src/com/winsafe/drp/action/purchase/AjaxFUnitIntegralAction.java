package com.winsafe.drp.action.purchase;

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppFUnit;
import com.winsafe.drp.dao.AppIntegralExchange;
import com.winsafe.drp.dao.FUnit;
import com.winsafe.hbm.util.HtmlSelect;


public class AjaxFUnitIntegralAction extends Action {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String pid =request.getParameter("pid");		
		try {
			
			AppFUnit applm = new AppFUnit();
			AppIntegralExchange ae = new AppIntegralExchange();
			List secondlist = applm.getFUnitByProductID(pid);
			JSONArray fulist = new JSONArray();
			JSONObject unitobj; 
			for (int i=0; i<secondlist.size(); i++ ){
				FUnit funit = (FUnit)secondlist.get(i);
				unitobj = new JSONObject();
				unitobj.put("unitid", funit.getFunitid());
				unitobj.put("unitname", HtmlSelect.getResourceName(request, "CountUnit", funit.getFunitid()));
				unitobj.put("quantity", funit.getXquantity());
				unitobj.put("integral", ae.getUnitintegralByPidUnitId(funit.getProductid(), funit.getFunitid()));
				
				fulist.put(unitobj);
			}
			
			JSONObject json = new JSONObject();			
			json.put("fulist", fulist);				
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
