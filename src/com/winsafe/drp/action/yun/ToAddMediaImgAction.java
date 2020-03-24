package com.winsafe.drp.action.yun;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;

public class ToAddMediaImgAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping , ActionForward forward , HttpServletRequest request , HttpServletResponse response) throws Exception{
		
		return mapping.findForward("toadd"); 
	}
}
