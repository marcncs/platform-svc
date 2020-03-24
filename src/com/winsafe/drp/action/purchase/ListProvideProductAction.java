package com.winsafe.drp.action.purchase;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppProviderProduct;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DbUtil;

public class ListProvideProductAction extends BaseAction{

  public ActionForward execute(ActionMapping mapping, ActionForm form,
                               HttpServletRequest request,
                               HttpServletResponse response) throws Exception {
     int pagesize = 10;
    super.initdata(request);
    String strpid = request.getParameter("pid");
    if (strpid == null) {
    	strpid = (String) request.getSession().getAttribute("pid");
    }
    String pid = strpid;
    try{
    	String Condition = " pp.providerid='"+pid +"' ";
      Map map = new HashMap(request.getParameterMap());
      Map tmpMap = EntityManager.scatterMap(map);

      String[] tablename={"ProviderProduct"};
      String whereSql = EntityManager.getTmpWhereSql(map, tablename); 

      whereSql = whereSql +Condition  ; 
      whereSql = DbUtil.getWhereSql(whereSql); 
//      Object obj[] = (DbUtil.setDynamicPager(request,
//                                      "ProviderProduct as pp",
//                                      whereSql,
//                                      pagesize,"subCondition"));
//      SimplePageInfo tmpPgInfo = (SimplePageInfo)obj[0]; 
//      whereSql = (String)obj[1];


      AppProviderProduct apl = new AppProviderProduct();
      List apls = apl.getProviderProduct(request, pagesize, whereSql);
      /*
      ArrayList alpl = new ArrayList();
      for(int i=0;i<apls.size();i++){
        ProviderProductForm ppf = new ProviderProductForm();
        Object[] o = (Object[])apls.get(i);
        ppf.setId(Long.valueOf(o[0].toString()));
        ppf.setPvproductname(String.valueOf(o[3]));
        ppf.setPvspecmode(String.valueOf(o[4]));
        ppf.setUnitname(Internation.getStringByKeyPositionDB("CountUnit",         
            Integer.parseInt(o[5].toString())));
        ppf.setBarcode(String.valueOf(o[6]));
        ppf.setPrice(Double.valueOf(o[7].toString()));
        ppf.setPricedate(String.valueOf(o[8]).substring(0,10));
        alpl.add(ppf);
      }
      */

      request.getSession().setAttribute("pid", strpid);
      request.setAttribute("pid",pid);
      request.setAttribute("alpl",apls);
     
      DBUserLog.addUserLog(userid, 2 ,"供应商资料>>列表供应商存货");
      return mapping.findForward("list");
    }catch(Exception e){
      e.printStackTrace();
    }
    return new ActionForward(mapping.getInput());
  }
}
