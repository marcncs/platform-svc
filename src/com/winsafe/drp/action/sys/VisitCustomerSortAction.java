package com.winsafe.drp.action.sys;

import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppUserSort;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UserSort;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.MakeCode;

public class VisitCustomerSortAction extends Action{

  public ActionForward execute(ActionMapping mapping, ActionForm form,
                               HttpServletRequest request,
                               HttpServletResponse response) throws Exception {
    MakeCode mc = new MakeCode();
    String strwid = request.getParameter("wid");
    Integer wid = Integer.valueOf(strwid);
    String strspeed = request.getParameter("speedstr");
    int count = Integer.parseInt(request.getParameter("uscount"));


    try{
      AppUserSort awv = new AppUserSort();
      awv.delUserSortBySortid(wid);

      StringTokenizer st = new StringTokenizer(strspeed, ",");
      //AppServiceAgreement asa= new AppServiceAgreement();
      
      for(int p=0;p<count;p++){
    	  UserSort wv = new UserSort();
    	  wv.setId(Integer.valueOf(mc.getExcIDByRandomTableName("user_sort",0,"")));
    	  wv.setSortid(wid);
    	  wv.setUserid(Integer.valueOf(st.nextToken().trim()));

        awv.addUserSort(wv);
        
      }
      
      request.setAttribute("result", "databases.add.success");

      UsersBean users = UserManager.getUser(request);
      Integer userid = users.getUserid();
      DBUserLog.addUserLog(userid,11,"机构设置>>许可仓库用户，仓库编号："+wid);
      
      return mapping.findForward("visit");
    }catch(Exception e){
      
      e.printStackTrace();
    }finally {
      //
      //  ConnectionEntityManager.close(conn);
    }

    return new ActionForward(mapping.getInput());
  }
}
