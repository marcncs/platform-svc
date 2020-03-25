package com.winsafe.drp.action.yun;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppProductFeedback;
import com.winsafe.drp.dao.ProductFeedback;


public class EditProductFeedBackAuditStatusAction extends BaseAction {

	private AppProductFeedback apf = new AppProductFeedback();
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		String ppid = request.getParameter("PPID");
		Integer auditStatus = Integer.valueOf(request.getParameter("auditStatus"));
		
		try { 
			ProductFeedback pf = new ProductFeedback();
			
			pf = apf.getProductFeedbackById(Integer.valueOf(ppid));
			pf.setAuditStatus(auditStatus);
			
			apf.updProductFeedBack(pf);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
}
