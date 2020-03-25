package com.winsafe.drp.action.sys;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppUsers;
import com.winsafe.drp.dao.Users;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.Encrypt;

public class InitSysAction
    extends Action {

  public ActionForward execute(ActionMapping mapping, ActionForm form,
                               HttpServletRequest request,
                               HttpServletResponse response) throws Exception {

    AppUsers appUsers = new AppUsers();
    Users users = new Users();
    String sysloginname = "Admin";
    String syspassword = "admin";
    String sysapprovepwd = "admin";
    syspassword = Encrypt.getSecret(syspassword, 1);
    sysapprovepwd = Encrypt.getSecret(sysapprovepwd, 1);
    //Session 
    ////Connection 
    

    try {
      users = appUsers.getUsers(sysloginname);
      if (users == null) {
        Users us = new Users();
        us.setUserid(1);
        us.setLoginname("Admin");
        us.setPassword(syspassword);
        us.setApprovepwd(sysapprovepwd);
        us.setRealname("系统管理员");
        us.setNameen("abee");
        us.setSex(new Integer(2));
        us.setBirthday(DateUtil.StringToDate("1978-05-05"));
        us.setIdcard("423503197803214563");
        us.setMobile("13325645875");
        us.setOfficetel("45454564654");
        us.setHometel("54545454545");
        us.setEmail("fjdsf#ofw.ocm");
        us.setQq("14545454");
        us.setMsn("fdsfsd@ofe.com");
        us.setAddr("sa");
        us.setCreatedate(DateUtil.StringToDate("2005-04-05"));
        us.setLastlogin(DateUtil.StringToDate("2005-06-05"));
        us.setLogintimes(new Integer(2));
        us.setMakeorganid("1");
        us.setMakedeptid(1);
        us.setStatus(new Integer(1));
        us.setIsonline(new Integer(0));

        appUsers.InsertUsers(us);
        
        return mapping.findForward("success");

      }
      else {
        return mapping.findForward("fail");
      }
    }
    catch (Exception e) {
      
      e.printStackTrace();
    }
    finally {
      //
      //  ConnectionEntityManager.close(conn);
    }

    return new ActionForward(mapping.getInput());
  }
}
