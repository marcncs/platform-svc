package com.winsafe.drp.action.sys;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppUserLog;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.DbUtil;

public class ListNcLogAction
    extends Action {

  public ActionForward execute(ActionMapping mapping, ActionForm form,
                               HttpServletRequest request,
                               HttpServletResponse response) throws Exception {
    UsersBean users = UserManager.getUser(request);
    Integer userid = users.getUserid();
    int pagesize = 20;

    try {
      Map map = new HashMap(request.getParameterMap());
      Map tmpMap = EntityManager.scatterMap(map);
      String[] tablename={"UserLog"};
      String whereSql = EntityManager.getTmpWhereSql(map, tablename); 
      String timeCondition = DbUtil.getTimeCondition(map, tmpMap, " LogTime"); 
      String blur = DbUtil.getBlur(map, tmpMap, " Detail"); 
      whereSql = whereSql + blur + timeCondition ; 

      whereSql = DbUtil.getWhereSql(whereSql); 
//      int a =1;
//      if(a==1){
//    	 System.exit(0);
//      }

      AppUserLog aul = new AppUserLog();
      if("".equals(whereSql)){
    	  whereSql+=" where modeltype=13";
      }
      else{
    	  whereSql+=" and modeltype=13";
      }
      List ulls = aul.getUserLog(request, pagesize, whereSql);
      
     
      request.setAttribute("uls", ulls);
      request.setAttribute("UserID", request.getParameter("UserID"));
      request.setAttribute("UserName", request.getParameter("UserName"));
      request.setAttribute("begindate", request.getParameter("BeginDate"));
      request.setAttribute("enddate", request.getParameter("EndDate"));
      request.setAttribute("keyword", request.getParameter("KeyWord"));
      request.setAttribute("ModelType", request.getParameter("ModelType"));
      



      //DBUserLog.addUserLog(userid,"列表用户日志");
      return mapping.findForward("list");
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    return new ActionForward(mapping.getInput());
  }
  
   
}
