package com.winsafe.drp.action.yun;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppProductFeedback;

public class DelProductFeedBackAction extends BaseAction {

	private AppProductFeedback apf = new AppProductFeedback();
	
	public ActionForward execute(ActionMapping mapping , ActionForm form , HttpServletRequest request , HttpServletResponse response) throws Exception{
		try {
			String ppid = request.getParameter("PPID");
			
			apf.delProductFeedBackById(ppid);
			
		} catch (Exception e) { 
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
	}
}
