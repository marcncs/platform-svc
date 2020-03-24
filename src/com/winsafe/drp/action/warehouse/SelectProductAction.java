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
import com.winsafe.drp.dao.AppProductStockpile;
import com.winsafe.drp.dao.AppProductStruct;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.Internation;
import com.winsafe.hbm.util.pager.SimplePageInfo;

public class SelectProductAction
    extends BaseAction {

  public ActionForward execute(ActionMapping mapping, ActionForm form,
                               HttpServletRequest request,
                               HttpServletResponse response) throws Exception {
    int pagesize = 20;
    UsersBean users = UserManager.getUser(request);
//	Long userid = users.getUserid();
    super.initdata(request);try{
    	 String strwid = request.getParameter("wid");
//    	 String batch="";
    	
    	    if (strwid == null) {
    	    	strwid = (String) request.getSession().getAttribute("wid");
    	    }
    	    Long wid = Long.valueOf(strwid);
    	    request.getSession().setAttribute("wid",strwid);
//    	    String visitorgan = "";
//			 if (users.getVisitorgan() != null
//			 && users.getVisitorgan().length() > 0) {
//			 visitorgan = "  and ps.makeorganid in(" + users.getVisitorgan()
//			 + ")";
//			 }
    	    
        String strCondition = " ps.productid=p.id and ps.warehouseid=" + wid;// + visitorgan;
        Map map = new HashMap(request.getParameterMap());
        Map tmpMap = EntityManager.scatterMap(map);
        //String sql = "select * from product where " + strCondition;
        String[] tablename={"ProductStockpile","Product"};
        String whereSql = EntityManager.getTmpWhereSql(map, tablename);

        String leftblur = DbUtil.getBlurLeft(map, tmpMap, "PSID");
        //String blur = DbUtil.getBlur(map, tmpMap, "ProductName");
        String blur = DbUtil.getOrBlur(map, tmpMap, "p.id","p.productname","p.specmode");

        whereSql = whereSql + blur + leftblur +strCondition;
//        if(batch!=null && !"".equals(batch)){
//        	whereSql = whereSql+ " and ps.batch='" + batch + "'";
//        }
        whereSql = DbUtil.getWhereSql(whereSql); 
        
        Object obj[] = DbUtil.setDynamicPager(request,
				"ProductStockpile as ps,Product as p ", whereSql, pagesize, "subCondition");
		SimplePageInfo tmpPgInfo = (SimplePageInfo) obj[0]; 
		whereSql = (String) obj[1];

		AppProduct ap = new AppProduct();
        AppProductStockpile aps = new AppProductStockpile();
//        List pls = aps.getProductStockpileNewTwo(pagesize, whereSql, tmpPgInfo);
        ArrayList sls = new ArrayList();

//        for(int i=0;i<pls.size();i++){
//        	ProductStockpileForm psf = new ProductStockpileForm();
//        	Object[] ppobj = (Object[])pls.get(i);
//        	Product o=(Product)ppobj[0];
//        	ProductStockpile ps=(ProductStockpile)ppobj[1];
//          //psf.setId(o.getId());
//          psf.setProductid(o.getId());
//          psf.setPsproductname(o.getProductname());
//          psf.setPsspecmode(o.getSpecmode());
//          psf.setCountunit(o.getCountunit());
//          psf.setUnitname(Internation.getStringByKeyPositionDB("CountUnit",             
//              o.getCountunit()));
//          psf.setBatch("");
//          psf.setCost(o.getCost());
//          //psf.setStockpile(aps.getSumByProductID(o.getId()));
//          psf.setStockpile(ps.getStockpile());
//
//          sls.add(psf);
//        }
        AppProductStruct appProductStruct = new AppProductStruct();
		List uls = appProductStruct.getProductStructCanUse();
		String brand = Internation.getSelectTagByKeyAllDB("Brand", "Brand", true);
        request.setAttribute("brand", brand);
        request.setAttribute("uls", uls);

        request.setAttribute("wid", wid);
        request.setAttribute("sls",sls);
        return mapping.findForward("selectproduct");
      }
    catch (Exception e) {
      e.printStackTrace();
    }
    return new ActionForward(mapping.getInput());
  }
}
