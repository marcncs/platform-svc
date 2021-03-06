package com.winsafe.drp.action.sales;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppCustomer;
import com.winsafe.drp.dao.AppSampleBill;
import com.winsafe.drp.dao.AppUsers;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.Internation;
import com.winsafe.hbm.util.pager.SimplePageInfo;

public class ListSampleBillByCustomerAction
    extends BaseAction {

  public ActionForward execute(ActionMapping mapping, ActionForm form,
                               HttpServletRequest request,
                               HttpServletResponse response) throws Exception {
    int pagesize = 10;

    String isauditselect = Internation.getSelectTagByKeyAll("YesOrNo", request,
          "IsAudit", true, null);
          
      
//      Long userid = users.getUserid();

    try {
    	String strCid=request.getParameter("CID");
        
        if(strCid==null||strCid.equals("")){
          strCid=(String)request.getSession().getAttribute("cid");
        }
      String cid=strCid;
      request.getSession().setAttribute("cid", cid);

//      String Condition=" sb.cid='"+cid+"' and sb.makeid like '"+userid+"%' " ;
    	
      Map map = new HashMap(request.getParameterMap());
      Map tmpMap = EntityManager.scatterMap(map);
      String[] tablename={"SampleBill"};
      String whereSql = EntityManager.getTmpWhereSql(map, tablename);

      String timeCondition = DbUtil.getTimeCondition(map, tmpMap," MakeDate");
//      whereSql = whereSql + timeCondition + Condition; 
      whereSql = DbUtil.getWhereSql(whereSql); 
      //Object obj[] = (DbUtil.setPager(request,"SaleLog as sl ",whereSql,pagesize));
      Object obj[] = DbUtil.setDynamicPager(request,"SampleBill as sb ",whereSql,pagesize,"subCondition");
      SimplePageInfo tmpPgInfo = (SimplePageInfo) obj[0]; 
      whereSql = (String) obj[1];

      AppUsers au = new AppUsers();
      AppCustomer ac= new AppCustomer();
      AppSampleBill asl = new AppSampleBill();
//      List pils = asl.getSampleBill(pagesize, whereSql, tmpPgInfo);
      ArrayList also = new ArrayList();
//      for (int i = 0; i < pils.size(); i++) {
//    	SampleBillForm sbf = new SampleBillForm();
//        Object[] o = (Object[]) pils.get(i);
//        sbf.setId(Long.valueOf(o[0].toString()));
//        sbf.setCidname(ac.getCustomer(o[1].toString()).getCname());
//        sbf.setLinkman(String.valueOf(o[2]));
//        sbf.setTel(String.valueOf(o[3]));
//        sbf.setShipmentdate(String.valueOf(o[6]).substring(0,10));
//        sbf.setIsauditname(Internation.getStringByKeyPosition("YesOrNo",
//            request,
//            Integer.parseInt(o[12].toString()), "global.sys.SystemResource"));
//        sbf.setMakeidname(au.getUsersAllByID(Long.valueOf(o[10].toString())).getRealname());
//        sbf.setMakedate(String.valueOf(o[11]).substring(0,10));
//
//        also.add(sbf);
//      }

      request.setAttribute("also", also);
      request.setAttribute("isauditselect", isauditselect);

//      DBUserLog.addUserLog(userid,"列表样品记录"); 
      return mapping.findForward("list");
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    return new ActionForward(mapping.getInput());
  }
}
