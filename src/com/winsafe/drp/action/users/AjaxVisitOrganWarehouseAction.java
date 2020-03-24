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

import com.winsafe.drp.dao.AppWarehouse;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.dao.Warehouse;
import com.winsafe.drp.server.WarehouseService;


public class AjaxVisitOrganWarehouseAction extends Action {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		UsersBean users = UserManager.getUser(request);
		String oid =request.getParameter("oid");
		try {

//			WarehouseService applm = new WarehouseService();
//			List<Warehouse> dlist = applm.getEnableWarehouseByVisit(users.getUserid(), oid);
			AppWarehouse aw = new AppWarehouse();
			List<Warehouse> dlist = aw.getCanUseWarehouseByOid(oid);
			if(dlist==null)return null;
			JSONArray deptlist = new JSONArray();
			for (Warehouse w : dlist ){
				deptlist.put(w);
			}
			
			JSONObject json = new JSONObject();			
			json.put("warehouselist", deptlist);				
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
