package com.winsafe.drp.action.sales;

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
import com.winsafe.drp.dao.AppWarehouse;
import com.winsafe.drp.dao.Product;
import com.winsafe.drp.dao.ProductStockpile;
import com.winsafe.drp.dao.ProductStockpileForm;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.Internation;
import com.winsafe.hbm.util.pager.SimplePageInfo;

public class SelectProductStockpileAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		int pagesize = 20;
		//String keyword = request.getParameter("KeyWord");
		String pid = request.getParameter("pid");
		if ( pid == null ){
			pid = (String)request.getSession().getAttribute("pid");
		}
		request.getSession().setAttribute("pid", pid);
		 
		try {
			String strCondition = " ps.productid='"+pid+"' and ps.productid=p.id and ps.stockpile>0 ";
			Map map = new HashMap(request.getParameterMap());
			Map tmpMap = EntityManager.scatterMap(map);
			String[] tablename = { "ProductStockpile","Product" };
			String whereSql = EntityManager.getTmpWhereSql(map, tablename);

			//String blur = DbUtil.getBlur(map, tmpMap, "PSProductName");
			String blur = DbUtil.getOrBlur(map, tmpMap, "p.productname","p.specmode");
			 String leftblur = DbUtil.getBlurLeft(map, tmpMap, "PSID");

			whereSql = whereSql + blur + leftblur + strCondition;
			whereSql = DbUtil.getWhereSql(whereSql); 

			Object obj[] = (DbUtil.setPager(request,
					"ProductStockpile as ps, Product as p", whereSql, pagesize));
			SimplePageInfo tmpPgInfo = (SimplePageInfo) obj[0]; 
			whereSql = (String) obj[1];

			AppProductStockpile ap = new AppProductStockpile();
			AppWarehouse aw = new AppWarehouse();
			List pls = ap.searchProductStockpile(pagesize, whereSql, tmpPgInfo);
			ArrayList sls = new ArrayList();			
			AppProduct aproduct = new AppProduct();
			ProductStockpile ps = null;
			for (int i = 0; i < pls.size(); i++) {
				ps = (ProductStockpile)pls.get(i);
				ProductStockpileForm pf = new ProductStockpileForm();
				Product product = aproduct.getProductByID(ps.getProductid());
				pf.setId(ps.getId());
				pf.setProductid(product.getId());
				pf.setPsproductname(product.getProductname());
				pf.setWarehouseid(ps.getWarehouseid());
				pf.setWarehourseidname(aw.getWarehouseByID(ps.getWarehouseid())
						.getWarehousename());
				pf.setBatch(ps.getBatch());				
				pf.setCost(ps.getCost()==null?0:ps.getCost());	
				pf.setStockpile(ps.getStockpile());
				pf.setSpecmode(product.getSpecmode());
				sls.add(pf);
			}
			
			AppProductStruct appProductStruct = new AppProductStruct();
			List uls = appProductStruct.getProductStructCanUse();
			String brand = Internation.getSelectTagByKeyAllDB("Brand", "Brand", true);
	        request.setAttribute("brand", brand);
	        request.setAttribute("uls", uls);
			
			request.setAttribute("sls", sls);
			request.setAttribute("pid", pid);
			return mapping.findForward("selectproduct");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
}
