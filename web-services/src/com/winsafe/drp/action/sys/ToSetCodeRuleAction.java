package com.winsafe.drp.action.sys;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
public class ToSetCodeRuleAction extends BaseAction{

  public ActionForward execute(ActionMapping mapping, ActionForm form,
                               HttpServletRequest request,
                               HttpServletResponse response) throws Exception {
    try{
    	initdata(request);
      return mapping.findForward("success");
    }catch(Exception e){
      e.printStackTrace();
    }
    return new ActionForward(mapping.getInput());
  }
}
