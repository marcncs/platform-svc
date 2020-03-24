package com.winsafe.drp.action.report;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppCustomer;
import com.winsafe.drp.dao.AppReceivableObject;
import com.winsafe.drp.dao.AppUsers;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.Internation;
import com.winsafe.hbm.util.pager.SimplePageInfo;

public class SelectReceivableObjectAction extends BaseAction{

  public ActionForward execute(ActionMapping mapping, ActionForm form,
                               HttpServletRequest request,
                               HttpServletResponse response) throws Exception {
    int pagesize = 5;
    String keyword = request.getParameter("KeyWord");

    super.initdata(request);
    try {
        String Condition = " ro.makeid like '"+userid+"%' ";
        Map map = new HashMap(request.getParameterMap());
        Map tmpMap = EntityManager.scatterMap(map);
        //String sql = "select * from income_log as il where "+Condition;
        String[] tablename={"ReceivableObject"};
        String whereSql = EntityManager.getTmpWhereSql(map, tablename);
       //String timeCondition = DbUtil.getTimeCondition(map, tmpMap," MakeDate");
        whereSql = whereSql + Condition ; 
        whereSql = DbUtil.getWhereSql(whereSql); 
        Object obj[] = (DbUtil.setPager(request,
                                        "ReceivableObject as ro",
                                        whereSql,
                                        pagesize));
        SimplePageInfo tmpPgInfo = (SimplePageInfo)obj[0]; 
        whereSql = (String)obj[1];


        AppReceivableObject aro=new AppReceivableObject();
        AppCustomer ac = new AppCustomer();
        AppUsers au = new AppUsers();
       // AppProvide ap = new AppProvide();
//
//        List pbls = aro.getReceivableObject(pagesize, whereSql, tmpPgInfo);
//        ArrayList alpl = new ArrayList();
//        for(int i=0;i<pbls.size();i++){
//      	  ReceivableObjectForm rf = new ReceivableObjectForm();
//          Object[] o=(Object[])pbls.get(i);
//          rf.setId(Long.valueOf(o[0].toString()));
//          rf.setObjectsort(Integer.valueOf(o[1].toString()));
//          rf.setObjectsortname(Internation.getStringByKeyPosition("ObjectSort",
//                  request,
//                  Integer.parseInt(o[1].toString()), "global.sys.SystemResource"));
//          
//          if (rf.getObjectsort() == 0) {
//    			rf.setPayer(au.getUsersByid(Long.valueOf(o[2].toString()))
//    					.getRealname());
//    		}
//    		if (rf.getObjectsort() == 1) {
//    			rf.setPayer(ac.getCustomer(o[2].toString())
//    					.getCname());
//    		}
//    		if (rf.getObjectsort() == 2) {
//    			rf.setPayer(ac.getCustomer(o[2].toString())
//    					.getCname());
//    		}
//    		
////          
////          rf.setTotalreceivablesum(Double.valueOf(o[3].toString()));
////          rf.setAlreadyreceivablesum(Double.valueOf(o[4].toString()));
////          rf.setShouldreceivablesum(rf.getTotalreceivablesum() - rf.getAlreadyreceivablesum());
//          rf.setMakeidname(au.getUsersByid(Long.valueOf(o[3].toString())).getRealname());
//          rf.setMakedate(String.valueOf(o[4]).substring(0,10));
//         
//          alpl.add(rf);
//        }

        String objectsortselect =Internation.getSelectTagByKeyAll("ObjectSort",
              request,
              "ObjectSort", true,null);

        request.setAttribute("objectsortselect",objectsortselect);
//        request.setAttribute("alpl", alpl);
        
      return mapping.findForward("selectobject");
    }
    catch (Exception e) {
      e.printStackTrace();
    }

    return new ActionForward(mapping.getInput());
  }
}
