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

import com.winsafe.drp.dao.AppDept;
import com.winsafe.drp.dao.AppProduct;

public class AjaxProductNameAction extends Action {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		try {
			
			AppProduct ap = new AppProduct();
			List<String> dlist = ap.getAllProductName();
			JSONArray deptlist = new JSONArray();
			for (String str : dlist){
				deptlist.put(str);
			}
			
			JSONObject json = new JSONObject();			
			json.put("deptlist", deptlist);				
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
