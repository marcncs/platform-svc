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
import com.winsafe.drp.dao.AppProduct;
import com.winsafe.drp.dao.FUnit;
import com.winsafe.drp.server.OrganService;
import com.winsafe.hbm.util.HtmlSelect;


public class AjaxFUnitAction extends Action {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		AppFUnit applm = new AppFUnit();
		AppProduct ap = new AppProduct();
		OrganService os = new OrganService();
		
		try {
			String pid =request.getParameter("pid");	
			String cid =request.getParameter("cid");	
			String oid = request.getParameter("oid");
			String billtype = request.getParameter("billtype");
			
			List secondlist = applm.getFUnitByProductID(pid);
			JSONArray fulist = new JSONArray();
			JSONObject unitobj; 
			for (int i=0; i<secondlist.size(); i++ ){
				FUnit funit = (FUnit)secondlist.get(i);
				
				if(funit == null) continue;
				// PD只能选择 件 一个单位
				if(funit.getFunitid() != 2 ) continue;
				
				unitobj = new JSONObject();
				unitobj.put("unitid", funit.getFunitid());
				unitobj.put("unitname", HtmlSelect.getResourceName(request, "CountUnit", funit.getFunitid()));
				unitobj.put("quantity", funit.getXquantity());
				
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
