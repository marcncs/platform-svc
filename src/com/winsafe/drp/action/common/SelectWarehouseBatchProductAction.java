package com.winsafe.drp.action.common;

import java.text.DecimalFormat;
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
import com.winsafe.drp.dao.AppProduct;
import com.winsafe.drp.dao.AppProductStockpileAll;
import com.winsafe.drp.dao.Product;
import com.winsafe.drp.dao.ProductStockpileAll;
import com.winsafe.drp.dao.WarehouseProductForm;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.util.Constants;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.HtmlSelect;

public class SelectWarehouseBatchProductAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		int pagesize = 20;
		super.initdata(request);
		try {
			String strwid = request.getParameter("wid");
			String oid = users.getMakeorganid();
			if (strwid == null) {
				strwid = (String) request.getSession().getAttribute("wid");
			}
			request.getSession().setAttribute("wid", strwid);
			
			String strCondition = " ps.productid=p.id and ps.warehouseid='"+ strwid+"' ";
			Map map = new HashMap(request.getParameterMap());
			Map tmpMap = EntityManager.scatterMap(map);
			String[] tablename = { "ProductStockpileAll", "Product" };
			String whereSql = EntityManager.getTmpWhereSql(map, tablename);

			String leftblur = DbUtil.getBlurLeft(map, tmpMap, "PSID");
			String blur = DbUtil.getOrBlur(map, tmpMap, "p.id",
					"p.productname", "p.specmode");

			whereSql = whereSql + blur + leftblur + strCondition;
			whereSql = DbUtil.getWhereSql(whereSql); 

			AppProductStockpileAll aps = new AppProductStockpileAll();
			List pls = aps.getProductStockpileAllNewTwo(request, pagesize,
					whereSql);
			ArrayList sls = new ArrayList();
			AppFUnit af = new AppFUnit();
			AppProduct ap = new AppProduct();

			Double squantity;
			DecimalFormat df=new DecimalFormat("#.##");
			for (int i = 0; i < pls.size(); i++) {
				WarehouseProductForm psf = new WarehouseProductForm();
				Object[] ppobj = (Object[]) pls.get(i);
				Product o = (Product) ppobj[0];
				ProductStockpileAll ps = (ProductStockpileAll) ppobj[1];
				psf.setProductid(o.getId());
				psf.setProductname(o.getProductname());
				psf.setSpecmode(o.getSpecmode());
				psf.setUnitid(Constants.DEFAULT_UNIT_ID);
				psf.setBatch(ps.getBatch());
				psf.setUnitidname(HtmlSelect.getResourceName(request, "CountUnit", Constants.DEFAULT_UNIT_ID));
				if (ps.getCost() != null) {
					psf.setCost(ps.getCost()*af.getXQuantity(ps.getProductid(), o.getSunit()));
				}
//				psf.setPrice(ap.getDitchPriceByOIDPID(oid, psf.getProductid(), o.getSunit()));
				psf.setStockpile(ps.getStockpile());
				
				squantity=af.getStockpileQuantity2(o.getId(), o
						.getSunit(), ps.getStockpile());
				squantity=Double.valueOf(df.format(squantity));
				psf.setSquantity(squantity);

				sls.add(psf);
			}
			
			request.setAttribute("wid", strwid);
			request.setAttribute("oid", oid);
			request.setAttribute("sls", sls);
			return mapping.findForward("selectproduct");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
}
