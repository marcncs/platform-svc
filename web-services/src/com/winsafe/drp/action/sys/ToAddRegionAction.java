package com.winsafe.drp.action.sys;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppRegion;
import com.winsafe.drp.dao.Region;

public class ToAddRegionAction extends Action{

	  public ActionForward execute(ActionMapping mapping, ActionForm form,
	                             HttpServletRequest request,
	                             HttpServletResponse response) throws Exception {
	  try{
		  String typeid=null;
		  String typename=null;
		  AppRegion ar  =new AppRegion();
		  String code = request.getParameter("acode");
		  Region ps = ar.getRegionById(code);
		  //添加typeid
		  if(ps.getRegioncode().length()==1){
			  typeid="1";
			  typename="大区";
		  }
		  if(ps.getRegioncode().length()==3){
			  typeid="2";
			  typename="办事处";
		  }
		  request.setAttribute("ps", ps);
		  request.setAttribute("typeid", typeid);
		  request.setAttribute("typename", typename);
		  request.setAttribute("code", code);
	    return mapping.findForward("toadd");
	  }catch(Exception e){
	    e.printStackTrace();
	  }
	  return new ActionForward(mapping.getInput());
	  }
	}
