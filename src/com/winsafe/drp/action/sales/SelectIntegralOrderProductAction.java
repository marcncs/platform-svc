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
import com.winsafe.drp.dao.AppFUnit;
import com.winsafe.drp.dao.AppIntegralExchange;
import com.winsafe.drp.dao.AppProductStockpileAll;
import com.winsafe.drp.dao.AppWarehouse;
import com.winsafe.drp.dao.SaleOrderProductForm;
import com.winsafe.drp.dao.Warehouse;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.server.WarehouseService;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.HtmlSelect;

public class SelectIntegralOrderProductAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		int pagesize = 20;
		super.initdata(request);
		try {
		String cid = request.getParameter("cid");
		if (null == cid) {
			cid = (String) request.getSession().getAttribute("cid");
		}
		String organid = request.getParameter("organid");
		if (null == organid) {
			organid = (String) request.getSession().getAttribute("organid");
		}
		if (null == organid) {
			organid = users.getMakeorganid();
		}

		String wid = request.getParameter("warehouseid");

		if (wid == null) {
			AppWarehouse aw = new AppWarehouse();
			wid = aw.getWarehouseByOID(organid).getId();
		}

		request.getSession().setAttribute("cid", cid);
		request.getSession().setAttribute("organid", organid);

		
			String strCondition = " ps.warehouseid=w.id and w.warehouseproperty=1 " +
			" and ps.productid=p.id and w.makeorganid='"+organid+"' and ps.stockpile>0 and p.wise=0 "; 
			Map map = new HashMap(request.getParameterMap());
			Map tmpMap = EntityManager.scatterMap(map);

			String[] tablename = { "ProductStockpile", "Product"};
			String whereSql = EntityManager.getTmpWhereSql(map, tablename);

			String leftblur = DbUtil.getBlurLeft(map, tmpMap, "PSID");
			String blur = DbUtil.getOrBlur(map, tmpMap, "ProductID", "ProductName",
					"SpecMode", "PYCode");

			whereSql = whereSql + leftblur + blur + strCondition;
			whereSql = DbUtil.getWhereSql(whereSql); 


			AppIntegralExchange ap = new AppIntegralExchange();
			WarehouseService aw = new WarehouseService();
			AppFUnit appfu = new AppFUnit();
			AppProductStockpileAll aps = new AppProductStockpileAll();

			List pls = aps.getProductStockpilePresent(request, pagesize, whereSql);
			ArrayList sls = new ArrayList();

			for (int i = 0; i < pls.size(); i++) {
				SaleOrderProductForm pf = new SaleOrderProductForm();
				Object[] o = (Object[]) pls.get(i);
				pf.setProductid(String.valueOf(o[0]));
				pf.setPsproductname(String.valueOf(o[1]));
				pf.setPsspecmode(String.valueOf(o[2]));
				pf.setCountunit(Integer.valueOf(String.valueOf(o[4])));
				pf.setCountunitname(HtmlSelect.getResourceName(request, "CountUnit", pf.getCountunit()));
				pf.setWarehouseid(String.valueOf(o[5].toString()));
				pf.setWarehouseidname(aw.getWarehouseName(pf.getWarehouseid()));
				pf.setBatch("");
				pf.setStockpile(Double.valueOf(o[7].toString()));
				pf.setSquantity(appfu.getStockpileQuantity2(pf.getProductid(), pf.getCountunit(), pf.getStockpile()));
				double unitintegral =ap.getUnitintegralByPidUnitId(pf.getProductid(),
						pf.getCountunit());
				pf.setPrice(unitintegral);
				if ( unitintegral > 0 ){
					sls.add(pf);
				}
			}


			List wls = aw.getWarehouseListByOID(organid);
			ArrayList alw = new ArrayList();
			for (int i = 0; i < wls.size(); i++) {
				Warehouse w = (Warehouse) wls.get(i);
				if ( w.getWarehouseproperty().intValue() == 1 ){
					alw.add(w);
				}				
			}

			request.setAttribute("whls", alw);
			request.setAttribute("sls", sls);
			request.setAttribute("cid", cid);
			return mapping.findForward("selectproduct");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
}
