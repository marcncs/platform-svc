package com.winsafe.drp.action.sys;

import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppUserGrade;
import com.winsafe.drp.dao.UserGrade;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.MakeCode;

public class VisitMemberGradeAction extends Action{

  public ActionForward execute(ActionMapping mapping, ActionForm form,
                               HttpServletRequest request,
                               HttpServletResponse response) throws Exception {
    MakeCode mc = new MakeCode();
    Integer ppid = Integer.valueOf(request.getParameter("ppid"));
    String strspeed = request.getParameter("speedstr");
    int count = Integer.parseInt(request.getParameter("uscount"));

    try{
      AppUserGrade awv = new AppUserGrade();
      awv.delUserGradeByUGID(ppid);

      StringTokenizer st = new StringTokenizer(strspeed, ",");
      //AppServiceAgreement asa= new AppServiceAgreement();
      
      for(int p=0;p<count;p++){
    	  UserGrade wv = new UserGrade();
    	  wv.setId(Integer.valueOf(mc.getExcIDByRandomTableName("user_grade",0,"")));
    	  wv.setGradeid(ppid);
    	  wv.setUserid(Integer.valueOf(st.nextToken().trim()));

        awv.addUserGradeVisit(wv);
        
      }
     
      request.setAttribute("result", "databases.add.success");

      UsersBean users = UserManager.getUser(request);
      Integer userid = users.getUserid();
      DBUserLog.addUserLog(userid,11,"基础设置>>许可会员级别,编号："+ppid);
      return mapping.findForward("visit");
    }catch(Exception e){
      e.printStackTrace();
    }finally {

    }

    return new ActionForward(mapping.getInput());
  }
}
