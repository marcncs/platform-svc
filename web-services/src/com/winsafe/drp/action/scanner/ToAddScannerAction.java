package com.winsafe.drp.action.scanner;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;

/**
 * 增加采集器
* @Title: ToAddScannerAction.java
* @author: jason.huang 
* @CreateTime: sec ,17 2014 13:53:13 
* @version: 
 */
public class ToAddScannerAction extends BaseAction {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
	 super.initdata(request);
		try {

			return mapping.findForward("success");
	} catch (Exception e) {
		e.printStackTrace();
	}
	return new ActionForward(mapping.getInput());
	}

}
