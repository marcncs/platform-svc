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

import com.winsafe.drp.dao.AppProduct;
import com.winsafe.drp.dao.ProductForm;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.Internation;
import com.winsafe.hbm.util.pager.SimplePageInfo;

public class SelectSingleProductAction extends Action{

  public ActionForward execute(ActionMapping mapping, ActionForm form,
                               HttpServletRequest request,
                               HttpServletResponse response) throws Exception {
    int pagesize = 20;
    String keyword = request.getParameter("KeyWord");
    try {
    	String Condition =" p.useflag=1 and p.wise=0 ";
      Map map = new HashMap(request.getParameterMap());
      Map tmpMap = EntityManager.scatterMap(map);
      String[] tablename={"Product"};
      String whereSql = EntityManager.getTmpWhereSql(map, tablename);

      String blur = DbUtil.getBlur(map, tmpMap, "ProductName");

      whereSql = whereSql + blur + Condition;
      whereSql = DbUtil.getWhereSql(whereSql); 

      Object obj[] = (DbUtil.setDynamicPager(request, "Product as p", whereSql,
					pagesize,"Condition"));
			SimplePageInfo tmpPgInfo = (SimplePageInfo) obj[0]; 
			whereSql = (String) obj[1];
      
      AppProduct ap = new AppProduct();
      List pls = ap.getSingleProduct(request,pagesize, whereSql);
      ArrayList sls = new ArrayList();

      for(int i=0;i<pls.size();i++){
        ProductForm pf = new ProductForm();
        Object[] o=(Object[])pls.get(i);
        pf.setId(o[0].toString());
        pf.setProductname(o[1].toString());
        pf.setSpecmode(o[2].toString());
        pf.setCountunitname(Internation.getStringByKeyPositionDB("CountUnit",             
                Integer.parseInt(o[4].toString())));
        pf.setBarcode(String.valueOf(o[6]));
        sls.add(pf);
      }

      request.setAttribute("sls",sls);
      return mapping.findForward("selectproduct");
    }
    catch (Exception e) {
      e.printStackTrace();
    }

    return new ActionForward(mapping.getInput());
  }
}
