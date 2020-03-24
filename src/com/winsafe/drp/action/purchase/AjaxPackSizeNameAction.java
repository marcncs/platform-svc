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

import com.winsafe.drp.dao.AppProduct;

public class AjaxPackSizeNameAction extends Action {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String productName =request.getParameter("productName");
		try {
			
			AppProduct ap = new AppProduct();
			//#start modified by ryan.xi at 20150602
//			List<String> dlist = ap.getPackSizeNameByProductName(productName);
			List<String> dlist = ap.getSpecmodeByProductName(productName);
			//#end modified by ryan.xi at 20150602
			JSONArray packSizeList = new JSONArray();
			for (String str : dlist){
				packSizeList.put(str);
			}
			
			JSONObject json = new JSONObject();			
			json.put("packSizeList", packSizeList);				
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
