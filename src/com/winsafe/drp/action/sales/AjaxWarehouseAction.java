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
import com.winsafe.drp.dao.AppWarehouse;
import com.winsafe.drp.dao.Warehouse;


public class AjaxWarehouseAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String eqid =request.getParameter("eqid");		
		try {

			
			AppWarehouse aw = new AppWarehouse();
			List wlist = aw.getWarehouseListByOID(eqid);
			JSONArray whlist = new JSONArray();
			for (int i=0; i<wlist.size(); i++ ){
				Warehouse w = (Warehouse)wlist.get(i);				
				whlist.put(w);
			}
			
			JSONObject json = new JSONObject();			
			json.put("whlist", whlist);				
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
