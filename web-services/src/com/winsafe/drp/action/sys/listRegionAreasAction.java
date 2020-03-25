package com.winsafe.drp.action.sys;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class listRegionAreasAction  extends Action{

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
	    try {
	      return mapping.findForward("list");
	    }
	    catch (Exception e) {
	      e.printStackTrace();
	    }
	    return new ActionForward(mapping.getInput());
	  }

}