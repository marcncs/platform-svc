package com.winsafe.drp.action.users;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppDept;
import com.winsafe.drp.dao.Dept;

public class ToUpdDeptAction extends Action {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {

		Integer id=Integer.valueOf(request.getParameter("ID"));
		
        try {
        	AppDept ad=new AppDept();
        	Dept d=ad.getDeptByID(id);
        	
        	request.setAttribute("d", d);
        } catch (Exception e) {
             	 e.printStackTrace();
           
              } finally {
             	 //HibernateUtil.closeSession();
              }
		return mapping.findForward("updDept");	
	}

}
