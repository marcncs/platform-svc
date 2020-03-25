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

import com.winsafe.drp.dao.AppOrganGrade;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DbUtil;

public class ListOrganGradeAction
    extends Action {

  public ActionForward execute(ActionMapping mapping, ActionForm form,
                               HttpServletRequest request,
                               HttpServletResponse response) throws Exception {
    UsersBean users = UserManager.getUser(request);
    int userid = users.getUserid();

    try {

      Map map = new HashMap(request.getParameterMap());
      Map tmpMap = EntityManager.scatterMap(map);
      String[] tablename={"OrganGrade"};
      String whereSql = EntityManager.getTmpWhereSql(map, tablename); 
//      String timeCondition = DbUtil.getTimeCondition(map, tmpMap, " ReferDate"); 
//      String blur = DbUtil.getBlur(map, tmpMap, " ReportContent"); 
    //  whereSql = whereSql  ; 

      whereSql = DbUtil.getWhereSql(whereSql); 
      

      AppOrganGrade awr = new AppOrganGrade();
      List wrls = awr.getOrganGrade(request, whereSql);

      request.setAttribute("wls", wrls);

      DBUserLog.addUserLog(userid,11, "基础设置>>列表经销商级别");
      return mapping.findForward("list");
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    return new ActionForward(mapping.getInput());
  }
}
