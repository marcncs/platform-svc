package com.winsafe.drp.action.sys;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppApproveFlow;
import com.winsafe.drp.dao.ApproveFlow;
import com.winsafe.drp.dao.ApproveFlowForm;

public class ToUpdApproveFlowAction extends Action {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {

		Long id=Long.valueOf(request.getParameter("ID"));
		
        try {

        	ApproveFlow af=new ApproveFlow();
        	AppApproveFlow aw=new AppApproveFlow();
        	af=aw.getApproveFlowByID(id);
        	
        	ApproveFlowForm aff = new ApproveFlowForm();
        	aff.setId(af.getId());
        	aff.setFlowname(af.getFlowname());
        	aff.setMemo(af.getMemo());

        	request.setAttribute("aff", aff);
        	
        	return mapping.findForward("toupd");
        } catch (Exception e) {
             	 e.printStackTrace();
           
              } finally {
             	 //HibernateUtil.closeSession();
              }
		return mapping.findForward("updDept");	
	}

}
