package com.winsafe.drp.action.machin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
/**
 * @author : Qhonge
 * @version : 2010-1-25 下午05:15:22
 * www.winsafe.cn
 */
public class ToExportProductIncomeAction extends BaseAction {
	 public ActionForward execute(ActionMapping mapping, ActionForm form,
             HttpServletRequest request,
             HttpServletResponse response) throws Exception {
//super.initdata(request);
super.initdata(request);try{
	  
	  return mapping.findForward("toExport");
	  }catch(Exception e){
  e.printStackTrace();
  }
	  return new ActionForward(mapping.getInput());
	  }
}
