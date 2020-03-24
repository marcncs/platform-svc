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
import com.winsafe.drp.dao.AppProductStockpile;
import com.winsafe.drp.dao.ProductStockpileForm;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.HtmlSelect;

public class SelectSingleCheckProductAction
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
        
        AppProductStockpile aps = new AppProductStockpile();
        List pls = aps.getDistinctProductStockpile(request, pagesize, whereSql);
        ArrayList sls = new ArrayList();

        for(int i=0;i<pls.size();i++){	          
        	ProductStockpileForm psf = new ProductStockpileForm();
			Map ppobj = (Map) pls.get(i);
			psf.setProductid(ppobj.get("id").toString());
			psf.setPsproductname(ppobj.get("productname").toString());
			psf.setSpecmode(ppobj.get("specmode").toString());
			psf.setCountunit(Integer.valueOf(ppobj.get("countunit").toString()));
			psf.setUnitname(HtmlSelect.getResourceName(request, "CountUnit", psf.getCountunit()));		
			sls.add(psf);
        }
        

        request.setAttribute("wid", wid);
        request.setAttribute("IsIDCode", isidcode);
        request.setAttribute("wbit", wbit);
        request.setAttribute("sls",sls);
        return mapping.findForward("selectproduct");
      }
    catch (Exception e) {
      e.printStackTrace();
    }
    return new ActionForward(mapping.getInput());
  }
}
