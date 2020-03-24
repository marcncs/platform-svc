package com.winsafe.drp.action.sys;

import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppWarehouseVisit;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.dao.WarehouseVisit;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.MakeCode;

public class VisitWarehouseAction extends Action{

  public ActionForward execute(ActionMapping mapping, ActionForm form,
                               HttpServletRequest request,
                               HttpServletResponse response) throws Exception {
    String strwid = request.getParameter("wid");
    String strspeed = request.getParameter("speedstr");
    int count = Integer.parseInt(request.getParameter("uscount"));

    try{
      AppWarehouseVisit awv = new AppWarehouseVisit();
      awv.delWarehouseVisitByWID(strwid);

      StringTokenizer st = new StringTokenizer(strspeed, ",");
      
      for(int p=0;p<count;p++){
    	  WarehouseVisit wv = new WarehouseVisit();
    	  wv.setId(Integer.valueOf(MakeCode.getExcIDByRandomTableName("warehouse_visit",0,"")));
    	  wv.setWid(strwid);
    	  wv.setUserid(Integer.valueOf(st.nextToken().trim()));

        awv.addWarehouseVisit(wv);
        
      }
     
      request.setAttribute("result", "databases.add.success");

      UsersBean users = UserManager.getUser(request);
      Integer userid = users.getUserid();
      DBUserLog.addUserLog(userid, 11, "仓库设置>>新增许可仓库用户，仓库编号："+strwid);
      return mapping.findForward("visit");
    }catch(Exception e){
      e.printStackTrace();
    }finally {

    }

    return new ActionForward(mapping.getInput());
  }
}
