package com.winsafe.drp.action.warehouse;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppWarehouse;
public class ToInputProductIncomeAction extends BaseAction{

  public ActionForward execute(ActionMapping mapping, ActionForm form,
                               HttpServletRequest request,
                               HttpServletResponse response) throws Exception {
    String strpbid = request.getParameter("PBID");
    Long pbid = Long.valueOf(strpbid);
    super.initdata(request);try{
      AppWarehouse aw = new AppWarehouse();
      List wls = aw.getCanUseWarehouse();
      

      request.setAttribute("alw",wls);
      request.setAttribute("pbid",pbid);

      return mapping.findForward("toinput");
    }catch(Exception e){
      e.printStackTrace();
    }
    return new ActionForward(mapping.getInput());
  }
}
