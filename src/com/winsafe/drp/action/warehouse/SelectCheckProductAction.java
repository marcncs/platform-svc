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
import com.winsafe.drp.dao.AppFUnit;
import com.winsafe.drp.dao.AppProductStockpile;
import com.winsafe.drp.dao.Product;
import com.winsafe.drp.dao.ProductStockpile;
import com.winsafe.drp.dao.ProductStockpileForm;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.server.ProductCostService;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.HtmlSelect;

public class SelectCheckProductAction
    extends BaseAction {

  public ActionForward execute(ActionMapping mapping, ActionForm form,
                               HttpServletRequest request,
                               HttpServletResponse response) throws Exception {
    int pagesize = 20;
    super.initdata(request);try{
    String wid = request.getParameter("wid");
    String wbit = request.getParameter("wbit"); 
    int isidcode = Integer.valueOf(request.getParameter("IsIDCode"));
    
        String strCondition = "  ps.productid=p.id and ps.warehouseid='" + wid+"' ";
        if ( wbit != null && !wbit.equals("") ){
        	strCondition =strCondition+" and ps.warehousebit='"+wbit+"' ";
        }
        
        Map map = new HashMap(request.getParameterMap());
        Map tmpMap = EntityManager.scatterMap(map);
        String[] tablename={"Product"};
        String whereSql = EntityManager.getTmpWhereSql(map, tablename);

        String leftblur = DbUtil.getBlurLeft(map, tmpMap, "PSID");
		String blur = DbUtil.getOrBlur(map, tmpMap, "p.productname","p.specmode");

        whereSql = whereSql + blur + leftblur + strCondition;
        whereSql = DbUtil.getWhereSql(whereSql); 
        
        AppFUnit af = new AppFUnit();
        AppProductStockpile aps = new AppProductStockpile();
        List pls = aps.getProductStockpileNewTwo(request, pagesize, whereSql);
        ArrayList sls = new ArrayList();

        for(int i=0;i<pls.size();i++){	          
        	ProductStockpileForm psf = new ProductStockpileForm();
			Object[] ppobj = (Object[]) pls.get(i);
			Product o = (Product) ppobj[0];
			ProductStockpile ps = (ProductStockpile) ppobj[1];
			psf.setWarehousebit(ps.getWarehousebit());
			psf.setProductid(o.getId());
			psf.setPsproductname(o.getProductname());
			psf.setSpecmode(o.getSpecmode());			
			psf.setBatch(ps.getBatch());	
			double cost = ProductCostService.getCost(wid, o.getId(), ps.getBatch());
			if ( isidcode == 0 ){
				psf.setCountunit(o.getSunit());
				psf.setUnitname(HtmlSelect.getResourceName(request, "CountUnit", o.getSunit()));			
				psf.setIntstockpile(af.getStockpileQuantity(o.getId(), o
						.getSunit(), ps.getStockpile()));
				psf.setCost(cost*af.getXQuantity(o.getId(), o.getSunit()));
			}else{				
				psf.setCountunit(o.getCountunit());
				psf.setUnitname(HtmlSelect.getResourceName(request, "CountUnit", o.getCountunit()));		
				psf.setIntstockpile(ps.getStockpile().intValue());
				psf.setCost(cost);
			}

			sls.add(psf);
        }
        

        request.setAttribute("wid", wid);
        request.setAttribute("IsIDCode", isidcode);
        request.setAttribute("sls",sls);
        return mapping.findForward("selectproduct");
      }
    catch (Exception e) {
      e.printStackTrace();
    }
    return new ActionForward(mapping.getInput());
  }
}
