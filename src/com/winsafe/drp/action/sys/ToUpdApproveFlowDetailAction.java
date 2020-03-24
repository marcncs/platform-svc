package com.winsafe.drp.action.sys;


import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppApproveFlowDetail;
import com.winsafe.drp.dao.AppUsers;
import com.winsafe.drp.dao.ApproveFlowDetail;
import com.winsafe.drp.dao.ApproveFlowDetailForm;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.hbm.util.Internation;

public class ToUpdApproveFlowDetailAction extends Action {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {

		Long id=Long.valueOf(request.getParameter("ID"));
		UsersBean users = UserManager.getUser(request);
		Integer userid = users.getUserid();
        try {

        	ApproveFlowDetail afd=new ApproveFlowDetail();
        	AppApproveFlowDetail aw=new AppApproveFlowDetail();
        	afd=aw.getApproveFlowDetailByID(id);
        	
        	ApproveFlowDetailForm afdf = new ApproveFlowDetailForm();
        	afdf.setId(afd.getId());
        	afdf.setAfid(afd.getAfid());
        	afdf.setApproveid(afd.getApproveid());
        	afdf.setActidname(Internation.getSelectTagByKeyAllDBDef("ActID", 
        	          "actid", afd.getActid()));
        	afdf.setApproveorder(afd.getApproveorder());

        	AppUsers au = new AppUsers();
            List als = au.getIDAndLoginNameByOID2(users.getMakeorganid());
           
            
            request.setAttribute("als", als);
        	
        	request.setAttribute("afdf", afdf);
        	
        	return mapping.findForward("toupd");
        } catch (Exception e) {
             	 e.printStackTrace();
           
              } finally {
             	 //HibernateUtil.closeSession();
              }
		return mapping.findForward("updDept");	
	}

}
