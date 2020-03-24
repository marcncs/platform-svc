package com.winsafe.drp.action.sys;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppSendTime;
import com.winsafe.drp.dao.SendTime;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DbUtil;

public class ListSendTimeAction
    extends Action {

  public ActionForward execute(ActionMapping mapping, ActionForm form,
                               HttpServletRequest request,
                               HttpServletResponse response) throws Exception {
    UsersBean users = UserManager.getUser(request);
    Integer userid = users.getUserid();
    int pagesize = 10;

    try {

      Map map = new HashMap(request.getParameterMap());
      Map tmpMap = EntityManager.scatterMap(map);
      //String sql = "select * from warehouse ";
      String[] tablename={"SendTime"};
      String whereSql = EntityManager.getTmpWhereSql(map, tablename); 

      whereSql = whereSql  ; 

      whereSql = DbUtil.getWhereSql(whereSql); 
     

      AppSendTime awr = new AppSendTime();
      List wrls = awr.getSendTime(request, pagesize, whereSql);

      ArrayList wls = new ArrayList();
      for (int w = 0; w < wrls.size(); w++) {
    	  //PricePolicy wf = new PricePolicy();
    	  SendTime o = (SendTime) wrls.get(w);
        
        wls.add(o);
      }

//      request.setAttribute("useflagselect", useflagselect);
      request.setAttribute("wls", wls);

      DBUserLog.addUserLog(userid,11,"基础设置>>列表送货时间积分");
      return mapping.findForward("list");
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    return new ActionForward(mapping.getInput());
  }
}
