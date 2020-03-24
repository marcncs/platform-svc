package com.winsafe.drp.action.common;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


public abstract class ListBaseIdcodeDetailAction extends Action {

    public ActionForward execute(ActionMapping mapping, ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response) throws
            IOException, ServletException {
    	
    	String billid = request.getParameter("billid");
    	
    	String prid = request.getParameter("prid");
    	
    	String batch =  request.getParameter("batch");
    	
    	String isaudit = request.getParameter("isaudit");    	
    	
    	String specmode = request.getParameter("specmode");
    	
    	String flag = request.getParameter("flag");
    	if ( billid == null ){
    		billid = (String)request.getSession().getAttribute("billid");
    	}
    	if ( prid == null ){
    		prid = (String)request.getSession().getAttribute("prid");
    	}
    	if ( isaudit == null ){
    		isaudit = (String)request.getSession().getAttribute("isaudit");
    	}
    	
    	if ( batch == null ){
    		batch = (String)request.getSession().getAttribute("batch");
    	}
    	request.getSession().setAttribute("billid", billid);
    	request.getSession().setAttribute("prid", prid);
    	request.getSession().setAttribute("isaudit",isaudit);
    	request.getSession().setAttribute("batch",batch);
    	request.setAttribute("flag", flag);
    	
        try{        	
            List vulist = getIdcodeList(request, prid, billid);
            request.setAttribute("vulist", vulist);
            request.setAttribute("specmode", specmode);
            if("PW".equals(flag)) {
            	return mapping.findForward("listPw");
            } else {
            	return mapping.findForward("list");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
    
    protected abstract List getIdcodeList(HttpServletRequest request, String prid,String billid) throws Exception;
}
