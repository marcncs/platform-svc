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

import com.winsafe.drp.dao.AppPaymentMode;
import com.winsafe.drp.dao.PaymentMode;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DbUtil;

public class ListPaymentModeAction
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
      String[] tablename={"PaymentMode"};
      String whereSql = EntityManager.getTmpWhereSql(map, tablename); 

      whereSql = whereSql  ; 

      whereSql = DbUtil.getWhereSql(whereSql); 
      

      AppPaymentMode awr = new AppPaymentMode();
      List wrls = awr.getPaymentMode(request, pagesize, whereSql);

      ArrayList wls = new ArrayList();
      for (int w = 0; w < wrls.size(); w++) {
    	  PaymentMode o = (PaymentMode) wrls.get(w);
        
        wls.add(o);
      }

      request.setAttribute("wls", wls);

      DBUserLog.addUserLog(userid,11,"基础设置>>列表收款方式");
      return mapping.findForward("list");
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    return new ActionForward(mapping.getInput());
  }
}
