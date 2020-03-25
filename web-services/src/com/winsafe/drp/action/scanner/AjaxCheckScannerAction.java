package com.winsafe.drp.action.scanner;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppScanner;
import com.winsafe.drp.dao.Scanner;

public class AjaxCheckScannerAction extends BaseAction {
	Logger logger = Logger.getLogger(AjaxCheckScannerAction.class);
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String ln = request.getParameter("ln");
		try {
			AppScanner as = new AppScanner();
			Scanner s = as.findScannerByImei(ln);
			JSONObject json = new JSONObject();
			json.put("s", s);
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
