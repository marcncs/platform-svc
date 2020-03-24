package com.winsafe.drp.action.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppPlinkman;
import com.winsafe.drp.dao.Plinkman;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.Internation;

public class ToUpdPlinkmanAction extends Action{

  public ActionForward execute(ActionMapping mapping, ActionForm form,
                               HttpServletRequest request,
                               HttpServletResponse response) throws Exception {
    String strid = request.getParameter("id");
    Integer id = Integer.valueOf(strid);
    try{
      Plinkman pl = new Plinkman();
      
   
      AppPlinkman apl = new AppPlinkman();
    
      pl = apl.getProvideLinkmanByID(id);
      
      String sexselect =Internation.getSelectTagByKeyAll("Sex", request,
          "sex", String.valueOf(pl.getSex()), null);
       String birthday=DateUtil.formatDate(pl.getBirthday());


      String ismainselect = Internation.getSelectTagByKeyAll("YesOrNo", request,
          "ismain", String.valueOf(pl.getIsmain()), null);
      
      
      request.setAttribute("sexselect",sexselect);
      request.setAttribute("birthday",birthday);
      request.setAttribute("ismainselect",ismainselect);
      request.setAttribute("pl",pl);

      return mapping.findForward("toupd");
    }catch(Exception e){
      e.printStackTrace();
    }
    return new ActionForward(mapping.getInput());
  }
}
