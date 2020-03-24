package com.winsafe.drp.action.warehouse;

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
import com.winsafe.drp.dao.AppProduct;
import com.winsafe.drp.dao.AppProductStruct;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.Internation;
import com.winsafe.hbm.util.pager.SimplePageInfo;

public class SelectSingleProductAction extends BaseAction{

  public ActionForward execute(ActionMapping mapping, ActionForm form,
                               HttpServletRequest request,
                               HttpServletResponse response) throws Exception {
    int pagesize = 20;
    String keyword = request.getParameter("KeyWord");
    super.initdata(request);try{
    	//String Condition =" (pp.propertycode=1 or pp.propertycode=2 or pp.propertycode=3) and p.id=pp.productid and p.useflag=1 ";
    	String Condition =" wise=0 and useflag=1 ";
    	Map map = new HashMap(request.getParameterMap());
      Map tmpMap = EntityManager.scatterMap(map);
      //String[] tablename={"Product","ProductProperty"};
      String[] tablename={"Product"};
      String whereSql = EntityManager.getTmpWhereSql(map, tablename);

      //String blur = DbUtil.getBlur(map, tmpMap, "ProductName");
      String blur = DbUtil.getOrBlur(map, tmpMap, "ID","ProductName","PYCode","SpecMode");
      String leftblur = DbUtil.getBlurLeft(map, tmpMap, "PSID");


      whereSql = whereSql + blur + leftblur+ Condition;
      whereSql = DbUtil.getWhereSql(whereSql); 

//      Object obj[] = (DbUtil.setExtraPager(request, "Product as p,Product_Property as pp", whereSql,
//					pagesize,"p.id"));
      Object obj[] = (DbUtil.setPager(request,"Product",whereSql,pagesize));
			SimplePageInfo tmpPgInfo = (SimplePageInfo) obj[0]; 
			whereSql = (String) obj[1];
      
      AppProduct ap = new AppProduct();
      //List pls = ap.getSingleProduct(pagesize, whereSql, tmpPgInfo);
//      List pls = ap.getSelectAllProduct(pagesize, whereSql, tmpPgInfo);
      ArrayList sls = new ArrayList();

//      for(int i=0;i<pls.size();i++){
//        ProductForm pf = new ProductForm();
//        Product o=(Product)pls.get(i);
//        pf.setId(o.getId());
//        pf.setProductname(o.getProductname());
//        pf.setSpecmode(o.getSpecmode());
//        pf.setCountunitname(Internation.getStringByKeyPositionDB("CountUnit",               
//                o.getCountunit()));
//        pf.setBarcode(o.getBarcode());
//        sls.add(pf);
//      }
      
      AppProductStruct appProductStruct = new AppProductStruct();
		List uls = appProductStruct.getProductStructCanUse();
		String brandselect=Internation.getSelectTagByKeyAllDB("Brand","Brand",true );
		  
		request.setAttribute("brandselect",brandselect);
		request.setAttribute("uls", uls);

      request.setAttribute("sls",sls);
      return mapping.findForward("selectproduct");
    }
    catch (Exception e) {
      e.printStackTrace();
    }

    return new ActionForward(mapping.getInput());
  }
}
