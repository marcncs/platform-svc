package com.winsafe.drp.action.common;

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
import com.winsafe.drp.dao.AppOrganProduct;
import com.winsafe.drp.dao.TradesProductForm;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.server.WarehouseService;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.HtmlSelect;

public class SelectTradesProductAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		int pagesize = 20;
		String cid = request.getParameter("cid");
		if ( cid == null ){
			cid = (String)request.getSession().getAttribute("cid");
		}
		request.getSession().setAttribute("cid", cid);
		
		 super.initdata(request);
		try {
			
			String organid = users.getMakeorganid();
			String strCondition = " op.organid='"+organid+"' and p.id=ps.productid and p.id=op.productid and p.wise=0 ";
			Map map = new HashMap(request.getParameterMap());
			Map tmpMap = EntityManager.scatterMap(map);
			String[] tablename = { "OrganProduct", "Product","ProductStockpile" };
			String whereSql = EntityManager.getTmpWhereSql(map, tablename);

			String leftblur = DbUtil.getBlurLeft(map, tmpMap, "PSID");
			String blur = DbUtil.getOrBlur(map, tmpMap, "p.id","p.productname","p.specmode");

			whereSql = whereSql + blur + leftblur+strCondition;
			whereSql = DbUtil.getWhereSql(whereSql); 

			WarehouseService ws = new WarehouseService();
			AppFUnit appfu = new AppFUnit();
			AppOrganProduct ap = new AppOrganProduct();
			List pls = ap.getOrganProductByTrades(request,pagesize, whereSql);
			ArrayList sls = new ArrayList();

			for (int i = 0; i < pls.size(); i++) {
				Map o = (Map)pls.get(i);
				TradesProductForm pf = new TradesProductForm();				
				pf.setProductid(o.get("id").toString());
				pf.setProductname(o.get("productname").toString());
				pf.setSpecmode(o.get("specmode").toString());
				pf.setUnitid(Integer.valueOf(o.get("sunit").toString()));
				pf.setUnitidname(HtmlSelect.getResourceName(request, "CountUnit", pf.getUnitid()));
				pf.setWarehouseid(o.get("warehouseid").toString());
				pf.setWarehouseidname(ws.getWarehouseName(pf.getWarehouseid()));
				pf.setStockpile(Double.valueOf(o.get("stockpile").toString()));
				pf.setQuantity(appfu.getStockpileQuantity2(pf.getProductid(), pf.getUnitid(), pf.getStockpile()));
				sls.add(pf);
			}


			request.setAttribute("sls", sls);
			request.setAttribute("cid", cid);
			request.setAttribute("organid", organid);
			return mapping.findForward("selectproduct");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
}
