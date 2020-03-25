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
import com.winsafe.drp.dao.AppOrgan;
import com.winsafe.drp.dao.AppProvider;
import com.winsafe.drp.dao.AppReceivableObject;
import com.winsafe.drp.dao.AppUsers;
import com.winsafe.drp.dao.ReceivableObject;
import com.winsafe.drp.dao.ReceivableObjectForm;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.Internation;
import com.winsafe.hbm.util.pager.SimplePageInfo;

public class SelectReceivableObjectAction extends BaseAction{

  public ActionForward execute(ActionMapping mapping, ActionForm form,
                               HttpServletRequest request,
                               HttpServletResponse response) throws Exception {
    int pagesize = 20;
    //String keyword = request.getParameter("KeyWord");
    
    super.initdata(request);
    try {
    	String visitorgan = "";
		if (users.getVisitorgan() != null
				&& users.getVisitorgan().length() > 0) {
			visitorgan = " or ro.makeorganid in(" + users.getVisitorgan()
					+ ")";
		}
		String Condition = " (ro.makeid=" + userid + " " + visitorgan
				+ ") ";
       // String Condition = " ro.makeid like '"+userid+"%' ";
        Map map = new HashMap(request.getParameterMap());
        Map tmpMap = EntityManager.scatterMap(map);
        //String sql = "select * from income_log as il where "+Condition;
        String[] tablename={"ReceivableObject"};
        String whereSql = EntityManager.getTmpWhereSql(map, tablename);
       //String timeCondition = DbUtil.getTimeCondition(map, tmpMap," MakeDate");
        String blur = DbUtil.getBlur(map, tmpMap, "Payer"); 
        whereSql = whereSql +blur + Condition ; 
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
        AppOrgan ao = new AppOrgan();
        AppProvider ap = new AppProvider();

        List pbls = aro.getReceivableObject(request,pagesize,whereSql);
        ArrayList alpl = new ArrayList();
        String oid = "";
        for(int i=0;i<pbls.size();i++){
      	  ReceivableObjectForm rf = new ReceivableObjectForm();
      	ReceivableObject o=(ReceivableObject)pbls.get(i);
          oid= o.getOid();
          rf.setOid(oid);
          rf.setObjectsort(o.getObjectsort());
          rf.setObjectsortname(Internation.getStringByKeyPosition("ObjectSort",
                  request,
                  o.getObjectsort(), "global.sys.SystemResource"));
          if (rf.getObjectsort() == 0) {
  			rf.setPayer(ao.getOrganByID(oid).getOrganname());
  		}
  		if (rf.getObjectsort() == 1) {
  			rf.setPayer(ac.getCustomer(oid)
  					.getCname());
  		}
  		if (rf.getObjectsort() == 2) {
  			rf.setPayer(ap.getProviderByID(oid)
  					.getPname());
  		}
  		rf.setMakeorganidname(ao.getOrganByID(o.getMakeorganid()).getOrganname());
//  		rf.setTotalreceivablesum(ar.getReceivableSumByROID(id));
//        rf.setAlreadyreceivablesum(ai.getIncomeLogSumByROID(id));
//        rf.setWaitreceivablesum(rf.getTotalreceivablesum()-rf.getAlreadyreceivablesum());
          rf.setMakeidname(au.getUsersByid(o.getMakeid()).getRealname());
          rf.setMakedate(String.valueOf(o.getMakedate()).substring(0,10));
         
          alpl.add(rf);
        }
        
        List ols = ao.getOrganToDown(users.getMakeorganid());

		request.setAttribute("ols",ols);

        String objectsortselect =Internation.getSelectTagByKeyAll("ObjectSort",
              request,
              "ObjectSort", true,null);

        request.setAttribute("objectsortselect",objectsortselect);
        request.setAttribute("alpl", alpl);
        
      return mapping.findForward("selectobject");
    }
    catch (Exception e) {
      e.printStackTrace();
    }

    return new ActionForward(mapping.getInput());
  }
}
