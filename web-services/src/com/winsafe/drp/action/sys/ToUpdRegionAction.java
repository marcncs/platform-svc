package com.winsafe.drp.action.sys;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppRegion;
import com.winsafe.drp.dao.Region;

public class ToUpdRegionAction extends Action{

	  public ActionForward execute(ActionMapping mapping, ActionForm form,
	                             HttpServletRequest request,
	                             HttpServletResponse response) throws Exception {

	  try{
		  AppRegion ar  =new AppRegion();
		  String code = request.getParameter("acode");
		  Region ps = ar.getRegionById(code);
		  request.setAttribute("typename", ps.getTypename());
		  request.setAttribute("ps", ps);
	    return mapping.findForward("toadd");
	  }catch(Exception e){
	    e.printStackTrace();
	  }
	  return new ActionForward(mapping.getInput());
	  }
	}