package com.winsafe.drp.action.machin;

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

import com.winsafe.drp.dao.AppAssembleRelationDetail;
import com.winsafe.drp.dao.AssembleRelationDetail;
import com.winsafe.drp.dao.AssembleRelationDetailForm;
import com.winsafe.hbm.util.Internation;

public class AjaxGetAssembleProductAction extends Action {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		String id = request.getParameter("id");
		try {
			AppAssembleRelationDetail app = new AppAssembleRelationDetail();
			List dlist = app.getAssembleRelationDetailBySIID(id);
			JSONArray ja = new JSONArray();
			for (int i = 0; i < dlist.size(); i++) {
				AssembleRelationDetail ard = (AssembleRelationDetail) dlist
						.get(i);
				AssembleRelationDetailForm f = new AssembleRelationDetailForm();
				f.setId(ard.getId());
				f.setArid(ard.getArid());
				f.setProductid(ard.getProductid());
				f.setProductname(ard.getProductname());
				f.setSpecmode(ard.getSpecmode());
				f.setUnitid(ard.getUnitid());
				f.setUnitidname(Internation.getStringByKeyPositionDB(
						"CountUnit", ard.getUnitid()));
				f.setUnitprice(ard.getUnitprice());
				f.setQuantity(ard.getQuantity());
				f.setSubsum(ard.getSubsum());
				ja.put(f);
			}

			JSONObject json = new JSONObject();
			json.put("dlist", ja);
			response.setContentType("text/html; charset=UTF-8");
			// response.setHeader("X-JSON", json.toString());
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
