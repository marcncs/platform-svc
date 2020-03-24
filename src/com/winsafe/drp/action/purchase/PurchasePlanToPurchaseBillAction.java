package com.winsafe.drp.action.purchase;

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

import com.winsafe.drp.dao.AppPurchasePlan;
import com.winsafe.drp.dao.AppUsers;
import com.winsafe.drp.dao.PurchasePlanForm;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.pager.SimplePageInfo;

public class PurchasePlanToPurchaseBillAction
    extends Action {

  public ActionForward execute(ActionMapping mapping, ActionForm form,
                               HttpServletRequest request,
                               HttpServletResponse response) throws Exception {
    int pagesize = 10;
    try {
      String findCondition = " pp.isratify=1 and pp.iscomplete=0 ";
      Map map = new HashMap(request.getParameterMap());
      Map tmpMap = EntityManager.scatterMap(map);
      //String sql = "select * from sale_log as sl where "+findCondition;
      String[] tablename={"PurchasePlan"};
      String whereSql = EntityManager.getTmpWhereSql(map, tablename);

      String timeCondition = DbUtil.getTimeCondition(map, tmpMap,
          " PlanDate");
      whereSql = whereSql + timeCondition + findCondition; 
      whereSql = DbUtil.getWhereSql(whereSql); 
      Object obj[] = (DbUtil.setPager(request,
                                      "PurchasePlan as pp",
                                      whereSql,
                                      pagesize));

      SimplePageInfo tmpPgInfo = (SimplePageInfo) obj[0]; 
      whereSql = (String) obj[1];

      //AppCustomer ac = new AppCustomer();
      AppUsers au = new AppUsers();
      AppPurchasePlan apa = new AppPurchasePlan();
      List pals = apa.getPurchasePlanToPurchase(request,pagesize,whereSql);
      ArrayList alpa = new ArrayList();

      for (int i = 0; i < pals.size(); i++) {
        PurchasePlanForm paf = new PurchasePlanForm();
        Object[] o = (Object[]) pals.get(i);
        paf.setId(o[0].toString());
       
       
        alpa.add(paf);
      }

      request.setAttribute("alpa",alpa);
      UsersBean users = UserManager.getUser(request);
      Integer userid = users.getUserid();
      //DBUserLog.addUserLog(userid,"采购计划转采购单");
      return mapping.findForward("toinput");
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    return new ActionForward(mapping.getInput());
  }
}
