package com.winsafe.drp.action.finance;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppCustomer;
import com.winsafe.drp.dao.CustomerUser;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.pager.SimplePageInfo;

public class SelectAllCustomerAction extends BaseAction{

  public ActionForward execute(ActionMapping mapping, ActionForm form,
                               HttpServletRequest request,
                               HttpServletResponse response) throws Exception {
    int pagesize = 5;
    String keyword = request.getParameter("KeyWord");

    super.initdata(request);
    try {
      String Condition = " isactivate=1 and c.isdel=0 ";
      Map map = new HashMap(request.getParameterMap());
      Map tmpMap = EntityManager.scatterMap(map);
      //String sql = "select * from customer ";
      String[] tablename={"Customer"};
      String whereSql = EntityManager.getTmpWhereSql(map, tablename);

      String blur = DbUtil.getBlur(map, tmpMap, "CName");

      whereSql = whereSql + blur +Condition;
      whereSql = DbUtil.getWhereSql(whereSql); 

      Object obj[] = (DbUtil.setPager(request,"Customer as c ",whereSql,pagesize));
      
      SimplePageInfo tmpPgInfo = (SimplePageInfo) obj[0]; 
      whereSql = (String) obj[1];
      AppCustomer ac = new AppCustomer();
      List pls = ac.getAllCustomer(pagesize, whereSql, tmpPgInfo);
      ArrayList sls = new ArrayList();

      for(int i=0;i<pls.size();i++){
        CustomerUser cu = new CustomerUser();
        Object[] o=(Object[])pls.get(i);
        cu.setCid(String.valueOf(o[0]));
        cu.setCname(String.valueOf(o[1]));
        cu.setOfficetel(String.valueOf(o[3]));
        sls.add(cu);
      }

      request.setAttribute("sls",sls);
      return mapping.findForward("selectcustomer");
    }
    catch (Exception e) {
      e.printStackTrace();
    }

    return new ActionForward(mapping.getInput());
  }
}
