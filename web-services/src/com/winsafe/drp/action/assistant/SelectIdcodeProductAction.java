package com.winsafe.drp.action.assistant;

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
import com.winsafe.drp.dao.AppProductStruct;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.pager.SimplePageInfo;

public class SelectIdcodeProductAction
    extends Action {

  public ActionForward execute(ActionMapping mapping, ActionForm form,
                               HttpServletRequest request,
                               HttpServletResponse response) throws Exception {
    int pagesize = 20;
    try {
      String strCondition = " wise=0 and useflag=1 and isidcode=1 ";
      Map map = new HashMap(request.getParameterMap());
      Map tmpMap = EntityManager.scatterMap(map);
      String[] tablename={"Product"};
      String whereSql = EntityManager.getTmpWhereSql(map, tablename);

      String leftblur = DbUtil.getBlurLeft(map, tmpMap, "PSID");
      String blur = DbUtil.getOrBlur(map, tmpMap, "ProductName","PYCode", "SpecMode");

      whereSql = whereSql +leftblur+ blur + strCondition;
      whereSql = DbUtil.getWhereSql(whereSql); 
      Object obj[] = (DbUtil.setPager(request,
              "Product",
              whereSql,
              pagesize));
      SimplePageInfo tmpPgInfo = (SimplePageInfo) obj[0]; 
      whereSql = (String) obj[1];

      AppProduct ap = new AppProduct();
//      List pls = ap.getSelectAllProduct(pagesize, whereSql, tmpPgInfo);
      ArrayList sls = new ArrayList();

//      for(int i=0;i<pls.size();i++){
//        ProductForm pf = new ProductForm();
//        Product o=(Product)pls.get(i);
//        pf.setId(o.getId());
//        pf.setProductname(o.getProductname());
//        pf.setSpecmode(o.getSpecmode());
//        pf.setCountunit(o.getCountunit());
//        pf.setCountunitname(Internation.getStringByKeyPositionDB("CountUnit",          
//            o.getCountunit()));
//        //pf.setStandardpurchase(0.00);
//        sls.add(pf);
//      }
		//产品结构
	  AppProductStruct appProductStruct = new AppProductStruct();
		List uls = appProductStruct.getProductStructCanUse();

		//String brandselect=Internation.getSelectTagByKeyAllDB("Brand","Brand",true );
		  
		//request.setAttribute("brandselect",brandselect);
	  request.setAttribute("uls",uls);
      request.setAttribute("sls",sls);
      return mapping.findForward("selectproduct");
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    return new ActionForward(mapping.getInput());
  }
}
