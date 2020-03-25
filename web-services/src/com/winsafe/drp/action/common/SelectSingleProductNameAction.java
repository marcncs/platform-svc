package com.winsafe.drp.action.common;

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
import com.winsafe.drp.dao.AppBaseResource;
import com.winsafe.drp.dao.AppProduct;
import com.winsafe.drp.dao.Product;
import com.winsafe.drp.dao.ProductForm;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.HtmlSelect;

public class SelectSingleProductNameAction extends BaseAction{

  public ActionForward execute(ActionMapping mapping, ActionForm form,
                               HttpServletRequest request,
                               HttpServletResponse response) throws Exception {
	  int pagesize = 20;
	  super.initdata(request);
    try {
    	String Condition ="  p.useflag=1 ";
      Map map = new HashMap(request.getParameterMap());
      Map tmpMap = EntityManager.scatterMap(map);
      String[] tablename={"Product"};
      String whereSql = EntityManager.getTmpWhereSql(map, tablename);
      String leftblur = DbUtil.getBlurLeft(map, tmpMap, "PSID");
      String blur = DbUtil.getOrBlur2(map, tmpMap, "productname");

      whereSql = whereSql + leftblur + blur + Condition;
      whereSql = DbUtil.getWhereSql(whereSql);       
      
      AppProduct ap = new AppProduct();
      List pls = ap.getSelectProductName(request,pagesize, whereSql);
      ArrayList sls = new ArrayList(); 
      AppBaseResource appBr = new AppBaseResource();
      Map<Integer, String> countUnitMap = appBr.getBaseResourceMap("CountUnit");
      for(int i=0;i<pls.size();i++){
    	Map name = (Map)pls.get(i);
        ProductForm pf = new ProductForm();
        pf.setProductname((String)name.get("productname")); 
//        pf.setCountunit(Integer.parseInt((String)name.get("countunit")));
//        pf.setCountunitname(countUnitMap.get(pf.getCountunit()));
        sls.add(pf);
      }
      
      request.setAttribute("sls",sls);
      request.setAttribute("KeyWordLeft", request.getParameter("KeyWordLeft"));
      return mapping.findForward("selectproductname");
    }
    catch (Exception e) {
      e.printStackTrace();
    }

    return new ActionForward(mapping.getInput());
  }
}
