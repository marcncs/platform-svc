package com.winsafe.drp.action.sys;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppSystemResource;
import com.winsafe.drp.dao.SystemResource;

public class ToUpdSystemResourceAction extends Action {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
		String id = request.getParameter("id");
		
        try {
        	SystemResource br=new SystemResource();
        	AppSystemResource abr=new AppSystemResource();
        	br=abr.getSystemResourceByid(Long.parseLong(id));

        	request.setAttribute("br", br);
       		return mapping.findForward("toupd");

        } catch (Exception e) {
             	 e.printStackTrace();
           
          } finally {
          }
		return mapping.findForward("updDept");	
	}

}
