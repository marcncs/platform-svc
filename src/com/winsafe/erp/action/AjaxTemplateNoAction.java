package com.winsafe.erp.action;

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

import com.winsafe.erp.dao.AppBillImportConfig;

public class AjaxTemplateNoAction extends Action {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String organId =request.getParameter("organId");
		try {
			
			AppBillImportConfig abic = new AppBillImportConfig();
			List<String> dlist = abic.getTemplateNoByOrganId(organId);
			JSONArray templateNoList = new JSONArray();
			for (String str : dlist){
				templateNoList.put(str);
			}
			
			JSONObject json = new JSONObject();			
			json.put("templateNoList", templateNoList);				
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
