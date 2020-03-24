package com.winsafe.drp.action.warehouse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;

public class ToImportTakeBillAction extends BaseAction {

	public ActionForward execute (ActionMapping mapping,ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		super.initdata(request);
		try{
			return  mapping.findForward("success");
		}catch(Exception e){
			
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
}

