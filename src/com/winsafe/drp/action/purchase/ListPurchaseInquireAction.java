package com.winsafe.drp.action.purchase;

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
import com.winsafe.drp.dao.AppOrgan;
import com.winsafe.drp.dao.AppProvider;
import com.winsafe.drp.dao.AppPurchaseInquire;
import com.winsafe.drp.dao.PurchaseInquire;
import com.winsafe.drp.dao.PurchaseInquireForm;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.pager.SimplePageInfo;

public class ListPurchaseInquireAction extends BaseAction{

  public ActionForward execute(ActionMapping mapping, ActionForm form,
                               HttpServletRequest request,
                               HttpServletResponse response) throws Exception {
    super.initdata(request);
	  int pagesize = 5;
    String strpid = request.getParameter("pid");
    if (strpid == null) {
    	strpid = (String) request.getSession().getAttribute("pid");
    }
    String pid = strpid;
    

    try{
    	String Condition = "pi.pid='"+pid+"' ";
      Map map = new HashMap(request.getParameterMap());
      Map tmpMap = EntityManager.scatterMap(map);

      String[] tablename={"PurchaseInquire"};
      String whereSql = EntityManager.getTmpWhereSql(map, tablename);

      String timeCondition = DbUtil.getTimeCondition(map, tmpMap, " MakeDate");
      whereSql = whereSql + timeCondition +Condition; 
      whereSql = DbUtil.getWhereSql(whereSql); 
      Object obj[] = (DbUtil.setDynamicPager(request,
              "PurchaseInquire as pi",
              whereSql,
              pagesize,"inquireCondition"));
      SimplePageInfo tmpPgInfo = (SimplePageInfo)obj[0]; 
      whereSql = (String)obj[1];

      AppPurchaseInquire api=new AppPurchaseInquire();
      AppProvider ap = new AppProvider();
      List pils = api.getPurchaseInquire(request, pagesize, whereSql);
      ArrayList alpi = new ArrayList();
      for(int i=0;i<pils.size();i++){
        PurchaseInquireForm pif = new PurchaseInquireForm();
        PurchaseInquire o=(PurchaseInquire)pils.get(i);
        pif.setId(o.getId());
        pif.setInquiretitle(o.getInquiretitle());
        pif.setProvidename(ap.getProviderByID(o.getPid()).getPname());
        pif.setPlinkman(o.getPlinkman());
        pif.setMakedate(o.getMakedate());
        pif.setValiddate(o.getValiddate());
        pif.setMakeid(o.getMakeid());
        pif.setIsaudit(o.getIsaudit());
        //pif.setMakename(au.getUsersByid(Integer.valueOf(o[5].toString())).getRealname());
//        pif.setIsauditname(Internation.getStringByKeyPosition("YesOrNo",
//            request,
//            Integer.parseInt(o[9].toString()), "global.sys.SystemResource"));
        alpi.add(pif);
      }
      AppOrgan ao = new AppOrgan();
      List ols = ao.getOrganToDown(users.getMakeorganid());

      request.setAttribute("ols",ols);      
      request.setAttribute("alpi",alpi);

      //DBUserLog.addUserLog(userid,"列表采购询价");
      return mapping.findForward("list");
    }catch(Exception e){
      e.printStackTrace();
    }
    return new ActionForward(mapping.getInput());
  }
}
