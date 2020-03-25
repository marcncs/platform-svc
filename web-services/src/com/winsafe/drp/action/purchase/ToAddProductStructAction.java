package com.winsafe.drp.action.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppProductStruct;
import com.winsafe.drp.dao.ProductStruct;

public class ToAddProductStructAction extends Action{

  public ActionForward execute(ActionMapping mapping, ActionForm form,
                             HttpServletRequest request,
                             HttpServletResponse response) throws Exception {

  try{
	  AppProductStruct app = new AppProductStruct();
//    List uls = appProductStruct.getProductStructCanUse();
//    request.setAttribute("uls",uls);
	  String code = request.getParameter("acode");
	  ProductStruct ps = app.getProductStructById(code);
	  request.setAttribute("ps", ps);
    return mapping.findForward("toadd");
  }catch(Exception e){
    e.printStackTrace();
  }
  return new ActionForward(mapping.getInput());
  }
}
