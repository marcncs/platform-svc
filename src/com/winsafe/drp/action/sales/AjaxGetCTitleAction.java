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
import com.winsafe.drp.dao.AppCTitle;
import com.winsafe.drp.dao.CTitle;

public class AjaxGetCTitleAction extends BaseAction {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String cid = request.getParameter("cid");

		try {
			AppCTitle act = new AppCTitle();
			JSONArray ctlist = new JSONArray();
			if (cid != null && !cid.equals("")) {
				List cals = act.getCTitleByCid(cid);
				for (int i = 0; i < cals.size(); i++) {
					CTitle ca = (CTitle) cals.get(i);
					ctlist.put(ca);
				}
				
			}

			JSONObject json = new JSONObject();			
			json.put("ctlist", ctlist);				
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
