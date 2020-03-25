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

import com.winsafe.drp.dao.AppDept;
import com.winsafe.drp.dao.AppPurchasePlan;
import com.winsafe.drp.dao.AppUsers;
import com.winsafe.drp.dao.PurchasePlanForm;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.Internation;
import com.winsafe.hbm.util.pager.SimplePageInfo;

public class WaitPurchasePlanAction
    extends Action {

  public ActionForward execute(ActionMapping mapping, ActionForm form,
                               HttpServletRequest request,
                               HttpServletResponse response) throws Exception {
    int pagesize = 10;
    UsersBean users = UserManager.getUser(request);
    Integer userid = users.getUserid();
    String approveselect = "";
    approveselect = Internation.getSelectTagByKeyAll("SubApproveStatus", request,
            "Approve", true, null);

    try {
      String condition = "pp.id=ppa.ppid and ppa.approveid=" + userid;
      Map map = new HashMap(request.getParameterMap());
      Map tmpMap = EntityManager.scatterMap(map);
//      String sql =
//          "select * from purchase_apply as pa ,purchase_apply_approve as paa where " +
//          condition;
      String[] tablename={"PurchasePlan","PurchasePlanApprove"};
      String whereSql = EntityManager.getTmpWhereSql(map, tablename);

     // String blur = DbUtil.getBlur(map, tmpMap, " and wr.reportcontent");
      String timeCondition = DbUtil.getTimeCondition(map, tmpMap, " PlanDate"); 
      whereSql = whereSql + timeCondition + condition; 
      whereSql = DbUtil.getWhereSql(whereSql);
      Object obj[] = (DbUtil.setPager(request,
                                      "PurchasePlan as pp,PurchasePlanApprove as ppa ",
                                      whereSql,
                                      pagesize));
      SimplePageInfo tmpPgInfo = (SimplePageInfo) obj[0]; 
      whereSql = (String) obj[1];

      AppPurchasePlan apa = new AppPurchasePlan();
      List wrls = apa.waitApprovePurchasePlan(pagesize, whereSql, tmpPgInfo);
      ArrayList arpa = new ArrayList();

      AppUsers au = new AppUsers();
      AppDept ad = new AppDept();
      for(int i=0;i<wrls.size();i++){
        PurchasePlanForm ppf = new PurchasePlanForm();
        Object[] o = (Object[])wrls.get(i);
        ppf.setId(o[0].toString());
        
       
     
        ppf.setActid(Integer.valueOf(o[5].toString()));
        ppf.setActidname(Internation.getStringByKeyPositionDB("ActID",
            Integer.parseInt(o[5].toString())));
        ppf.setApprovestatusname(Internation.getStringByKeyPosition("SubApproveStatus",
            request,
            Integer.parseInt(o[6].toString()), "global.sys.SystemResource"));
        arpa.add(ppf);
      }

      request.setAttribute("approveselect",approveselect);
      request.setAttribute("arpa",arpa);
      //DBUserLog.addUserLog(userid,"待审阅采购计划");
      return mapping.findForward("purchaseplan");
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    return new ActionForward(mapping.getInput());
  }
}
