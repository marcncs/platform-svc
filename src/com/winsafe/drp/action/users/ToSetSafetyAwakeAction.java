package com.winsafe.drp.action.users;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppOrganAwake;
import com.winsafe.drp.dao.AppUsers;
import com.winsafe.drp.dao.OrganAwake;
import com.winsafe.drp.dao.Users;
import com.winsafe.drp.dao.UsersBean;

public class ToSetSafetyAwakeAction extends Action{

  public ActionForward execute(ActionMapping mapping, ActionForm form,
                               HttpServletRequest request,
                               HttpServletResponse response) throws Exception {
    String oid = request.getParameter("OID");

    try{
      AppUsers au = new AppUsers();
      List<Users> uls = au.getUsersByOrgan(oid);
      ArrayList auls = new ArrayList();
      for(Users u : uls ){
          UsersBean ub = new UsersBean();
          ub.setUserid(u.getUserid());
          ub.setLoginname(u.getLoginname());
          ub.setRealname(u.getRealname());
          auls.add(ub);
        }

      AppOrganAwake awv = new AppOrganAwake();
      List alrd = awv.getOrganAwakeOID(oid);
      ArrayList alls = new ArrayList();
      for(int l=0;l<alrd.size();l++){
        UsersBean alub = new UsersBean();
        OrganAwake ob = (OrganAwake)alrd.get(l);
        alub.setUserid(ob.getUserid());
        alub.setRealname(au.getUsersByID(alub.getUserid()).getRealname());
        alls.add(alub);
      }

      request.setAttribute("oid",oid);
      request.setAttribute("alls",alls);
      request.setAttribute("auls",auls);

      return mapping.findForward("toselect");
    }catch(Exception e){
      e.printStackTrace();
    }
    return new ActionForward(mapping.getInput());
  }
}
