package com.winsafe.drp.action.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppProvider;
import com.winsafe.hbm.util.Internation;

public class ToAddPlinkmanAction extends Action{

  public ActionForward execute(ActionMapping mapping, ActionForm form,
                               HttpServletRequest request,
                               HttpServletResponse response) throws Exception {
	  String pid = (String) request.getSession().getAttribute("pid");
    try{

      String sexselect = Internation.getSelectTagByKeyAll("Sex", request,
          "sex", false, null);
      String wedlockselect = Internation.getSelectTagByKeyAll("Wedlock", request,
              "wedlock", false, null);
      String ismainselect = Internation.getSelectTagByKeyAll("YesOrNo", request,
          "ismain", false, null);

      AppProvider apv = new AppProvider();
      
      request.setAttribute("pid",pid);
      request.setAttribute("pname", apv.getProviderByID(pid).getPname());
      request.setAttribute("sexselect",sexselect);
      request.setAttribute("wedlockselect",wedlockselect);
      request.setAttribute("ismainselect",ismainselect);

      return mapping.findForward("toadd");
    }catch(Exception e){
      e.printStackTrace();
    }
    return new ActionForward(mapping.getInput());
  }
}
