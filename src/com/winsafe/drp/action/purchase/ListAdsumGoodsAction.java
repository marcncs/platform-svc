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

import com.winsafe.drp.dao.AdsumGoodsForm;
import com.winsafe.drp.dao.AppAdsumGoods;
import com.winsafe.drp.dao.AppCustomer;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.Internation;
import com.winsafe.hbm.util.pager.SimplePageInfo;

public class ListAdsumGoodsAction extends Action{

  public ActionForward execute(ActionMapping mapping, ActionForm form,
                               HttpServletRequest request,
                               HttpServletResponse response) throws Exception {
    int pagesize = 10;

    String isreferselect = "";
    isreferselect = Internation.getSelectTagByKeyAll("IsRefer", request,
        "IsRefer", true, null);

  String isauditselect = Internation.getSelectTagByKeyAll("YesOrNo", request,
        "IsAudit", true, null);

    try{
      Map map = new HashMap(request.getParameterMap());
      Map tmpMap = EntityManager.scatterMap(map);
      //String sql = "select * from purchase_bill";
      String[] tablename={"AdsumGoods"};
      String whereSql = EntityManager.getTmpWhereSql(map, tablename);

      String timeCondition = DbUtil.getTimeCondition(map, tmpMap, " ReceiveDate");
      whereSql = whereSql + timeCondition ; 
      whereSql = DbUtil.getWhereSql(whereSql); 
      Object obj[] = (DbUtil.setDynamicPager(request,
              "AdsumGoods as ag",
              whereSql,
              pagesize,"subCondition"));
      SimplePageInfo tmpPgInfo = (SimplePageInfo)obj[0]; 
      whereSql = (String)obj[1];

      AppAdsumGoods apb=new AppAdsumGoods();
      AppCustomer apv = new AppCustomer();
      List pbls = apb.getAdsumGoods(pagesize, whereSql, tmpPgInfo);
      ArrayList alpb = new ArrayList();
      for(int i=0;i<pbls.size();i++){
    	  AdsumGoodsForm pbf = new AdsumGoodsForm();
        Object[] o=(Object[])pbls.get(i);
        pbf.setId(o[0].toString());
        pbf.setPidname(apv.getCustomer(o[2].toString()).getCname());
        pbf.setTotalsum(Double.valueOf(o[3].toString()));
        pbf.setReceivedate(String.valueOf(o[4]).substring(0,10));
        pbf.setIsauditname(Internation.getStringByKeyPosition("YesOrNo",
            request,
            Integer.parseInt(o[5].toString()), "global.sys.SystemResource"));

        alpb.add(pbf);
      }
      
//      
//      AppPurchaseBill apa = new AppPurchaseBill();
//      int wic = apa.waitIncomePurchaseBill();


      request.setAttribute("alpb",alpb);
      request.setAttribute("isreferselect",isreferselect);
      request.setAttribute("isauditselect",isauditselect);
     // request.setAttribute("wic", wic);
      UsersBean users = UserManager.getUser(request);
      Integer userid = users.getUserid();
      //DBUserLog.addUserLog(userid,"列表到货通知");
      return mapping.findForward("list");
    }catch(Exception e){
      e.printStackTrace();
    }
    return new ActionForward(mapping.getInput());
  }
}
