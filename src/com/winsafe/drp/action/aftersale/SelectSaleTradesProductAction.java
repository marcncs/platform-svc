package com.winsafe.drp.action.aftersale;

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
import com.winsafe.drp.dao.AppFUnit;
import com.winsafe.drp.dao.AppProductStockpileAll;
import com.winsafe.drp.dao.ProductStockpileForm;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.HtmlSelect;

public class SelectSaleTradesProductAction
    extends BaseAction {

  public ActionForward execute(ActionMapping mapping, ActionForm form,
                               HttpServletRequest request,
                               HttpServletResponse response) throws Exception {
    int pagesize = 20;
    super.initdata(request);
    try {
    	String strwid = request.getParameter("wid");    
    	String cid = request.getParameter("cid");   
	    if (strwid == null) {
	    	strwid = (String) request.getSession().getAttribute("wid");
	    }
	    if (cid == null) {
	    	cid = (String) request.getSession().getAttribute("cid");
	    }
	    request.getSession().setAttribute("wid",strwid);
	    request.getSession().setAttribute("cid",cid);

    	    
        String strCondition = " ps.productid=p.id and ps.warehouseid='" + strwid+"' ";// + visitorgan;
        //限制已销售产品
//		strCondition += " and exists (select d.productid from Sale_Order as so, Sale_Order_Detail as d "+
//		" where so.id=d.soid and so.isendcase=1 and so.isblankout=0 "+
//		" and so.cid='"+cid+"' and p.id=d.productid) ";
        Map map = new HashMap(request.getParameterMap());
        Map tmpMap = EntityManager.scatterMap(map);
        String[] tablename={"Product"};
        String whereSql = EntityManager.getTmpWhereSql(map, tablename);

        String leftblur = DbUtil.getBlurLeft(map, tmpMap, "PSID");
        String blur = DbUtil.getOrBlur(map, tmpMap, "p.id","p.productname","p.specmode");
        whereSql = whereSql + blur + leftblur +strCondition;
        whereSql = DbUtil.getWhereSql(whereSql); 
        
        AppProductStockpileAll aps = new AppProductStockpileAll();
        List pls = aps.getProductStockpileGroup(request, pagesize, whereSql);
        ArrayList sls = new ArrayList();
        AppFUnit af = new AppFUnit();
		for(int i=0;i<pls.size();i++){
			ProductStockpileForm psf = new ProductStockpileForm();
			Map o = (Map)pls.get(i);        	
			psf.setProductid(o.get("id").toString());
			psf.setPsproductname(o.get("productname").toString());
			psf.setSpecmode(o.get("specmode").toString());
			psf.setCountunit(Integer.valueOf(o.get("sunit").toString()));
			psf.setUnitname(HtmlSelect.getResourceName(request, "CountUnit", psf.getCountunit()));             
			psf.setStockpile(Double.valueOf(o.get("stockpile").toString()));
			psf.setSquantity(af.getStockpileQuantity2(psf.getProductid(), psf.getCountunit(), psf.getStockpile()));
			
			sls.add(psf);
		}


        request.setAttribute("wid", strwid);
        request.setAttribute("sls",sls);
        return mapping.findForward("selectproduct");
      }
    catch (Exception e) {
      e.printStackTrace();
    }
    return new ActionForward(mapping.getInput());
  }
}
