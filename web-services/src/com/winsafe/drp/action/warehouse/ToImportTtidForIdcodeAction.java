package com.winsafe.drp.action.warehouse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;

public class ToImportTtidForIdcodeAction extends BaseAction {
	private static Logger logger = Logger.getLogger(ToImportTtidForIdcodeAction.class);
	
	public ActionForward execute (ActionMapping mapping,ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		super.initdata(request);
		try{
			return  mapping.findForward("success");
		}catch(Exception e){
			
			logger.error("",e);
		}
		return new ActionForward(mapping.getInput());
	}
	
}