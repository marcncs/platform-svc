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

import com.winsafe.drp.dao.AppCustomer;
import com.winsafe.drp.dao.Customer;
import com.winsafe.drp.dao.CustomerUser;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.pager.SimplePageInfo;

public class SelectCustomerProvideAction extends Action{

  public ActionForward execute(ActionMapping mapping, ActionForm form,
                               HttpServletRequest request,
                               HttpServletResponse response) throws Exception {
    int pagesize = 5;
    UsersBean users = UserManager.getUser(request);
    Integer userid = users.getUserid();
    String keyword = request.getParameter("KeyWord");
    try {
    	String Condition = " isactivate=1 and isdel=0 ";
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
        List pls = ac.searchCustomer2( whereSql);
        ArrayList sls = new ArrayList();

        Customer c = null;
        for(int i=0;i<pls.size();i++){
      	  c = (Customer)pls.get(i);
          CustomerUser cu = new CustomerUser();
          cu.setCid(c.getCid());
          cu.setCname(c.getCname());
          cu.setOfficetel(c.getOfficetel());
          sls.add(cu);
        }

        request.setAttribute("sls",sls);
      return mapping.findForward("selectprovide");
    }
    catch (Exception e) {
      e.printStackTrace();
    }

    return new ActionForward(mapping.getInput());
  }
}
