package com.winsafe.drp.action.scanner;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppScanner;
import com.winsafe.drp.dao.Scanner;

/**
 * 增加采集器
 * 
 * @Title: ToConfigScannerAction.java
 * @author: jason.huang
 * @CreateTime: sec ,21 2014 13:53:13
 * @version:
 */
public class ToUpdateScannerAction extends BaseAction {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		String strid = request.getParameter("ID");
		Long id = Long.parseLong(strid);
		super.initdata(request);
		try {

			AppScanner as = new AppScanner();
			Scanner s = as.getScannerByID(id);
			request.setAttribute("p", s);
			return mapping.findForward("success");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}

}
