package com.winsafe.drp.action.sys;


import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppBaseResource;
import com.winsafe.drp.dao.BaseResource;

public class ToUpdBaseResourceAction extends Action {
	
	
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
		String tagName = request.getParameter("tagName");
		Integer tagSubKey=Integer.valueOf(request.getParameter("tagSubKey"));
		String fromPage = request.getParameter("fromPage");
        try {

        	BaseResource br=new BaseResource();
        	AppBaseResource abr=new AppBaseResource();
        	br=abr.getBaseResourceValue(tagName,tagSubKey);

        	request.setAttribute("br", br);
        	
        	
        	if("3".equals(fromPage)) {
        		request.setAttribute("istime", request.getParameter("istime"));
        		return mapping.findForward("toupd3");
        	}
        	if(tagName.equals("UploadIdcodeFlag")){
        		return mapping.findForward("toupd2");
        	}else{
        		return mapping.findForward("toupd");
        	}
        	
        	
        } catch (Exception e) {
             	 e.printStackTrace();
           
              } finally {
             	 //HibernateUtil.closeSession();
              }
		return mapping.findForward("updDept");	
	}

	

}
