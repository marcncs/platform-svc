package com.winsafe.drp.action.assistant;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppTrackApply;
import com.winsafe.drp.dao.TrackApply;

public class AjaxViewTrackApply extends BaseAction {
	Logger logger = Logger.getLogger(AjaxViewTrackApply.class);
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		super.initdata(request);
		String ln = request.getParameter("ln").trim();
		try {
			AppTrackApply ata = new AppTrackApply();
			TrackApply ta = new TrackApply();
			ta = ata.getTrackapplyByIdcodeAndOrg(ln,users.getMakeorganid());
			
			JSONObject json = new JSONObject();
			json.put("s", ta);
			response.setContentType("text/html; charset=UTF-8");
			response.setHeader("Cache-Control", "no-cache");
			PrintWriter out = response.getWriter();
			logger.debug(out);
			out.print(json.toString());
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}