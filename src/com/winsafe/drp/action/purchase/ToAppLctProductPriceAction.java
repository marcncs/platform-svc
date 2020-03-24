package com.winsafe.drp.action.purchase;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppOrgan;
import com.winsafe.drp.dao.AppPriceOrganLog;
import com.winsafe.drp.dao.Organ;
import com.winsafe.drp.dao.OrganForm;
import com.winsafe.drp.dao.PriceOrganLog;

public class ToAppLctProductPriceAction extends Action{

  public ActionForward execute(ActionMapping mapping, ActionForm form,
                               HttpServletRequest request,
                               HttpServletResponse response) throws Exception {
	 // String pid = (String) request.getSession().getAttribute("pid");

	  String pid = (String) request.getSession().getAttribute("pid");
    try{
      AppOrgan ao = new AppOrgan();
      List ols = ao.getAllOrgan();
      ArrayList aols = new ArrayList();
      for(int i=0;i<ols.size();i++){
        OrganForm of = new OrganForm();
        Organ o = (Organ)ols.get(i);
        of.setId(o.getId());
        of.setOrganname(o.getOrganname());

        aols.add(of);
      }
      AppPriceOrganLog apppo = new AppPriceOrganLog();
      List polist = apppo.getPriceOrganLogByPid(pid);
      List pols = new ArrayList();
      for ( int i=0;i<polist.size(); i++){
    	  PriceOrganLog pol = (PriceOrganLog)polist.get(i);
    	  pol.setOrganidname(ao.getOrganByID(pol.getOrganid()).getOrganname());
    	  pols.add(pol);    	  
      }
      

      request.setAttribute("aols",aols);
      request.setAttribute("pols",pols);

      return mapping.findForward("toapp");
    }catch(Exception e){
      e.printStackTrace();
    }
    return new ActionForward(mapping.getInput());
  }
}
