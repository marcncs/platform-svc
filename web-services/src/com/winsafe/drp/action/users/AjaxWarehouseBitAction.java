package com.winsafe.drp.action.users;

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

import com.winsafe.drp.dao.WarehouseBit;
import com.winsafe.drp.server.WarehouseService;


public class AjaxWarehouseBitAction extends Action {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String wid =request.getParameter("wid");
		try {
			
			WarehouseService applm = new WarehouseService();
			List<WarehouseBit> dlist = applm.getWarehouseBitByWid(wid);
			JSONArray deptlist = new JSONArray();
			for (WarehouseBit w : dlist ){
				deptlist.put(w);
			}
			
			JSONObject json = new JSONObject();			
			json.put("warehousebit", deptlist);				
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
