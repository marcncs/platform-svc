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

import com.winsafe.common.util.StringUtil;
import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppFUnit;
import com.winsafe.drp.dao.AppProductStockpile;
import com.winsafe.drp.dao.Product;
import com.winsafe.drp.dao.ProductStockpile;
import com.winsafe.drp.dao.ProductStockpileForm;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.server.WarehouseService;
import com.winsafe.drp.util.Constants;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DbUtil;

public class StockStatAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// 初始化操作
		initdata(request);
		int pagesize = 20;
		WarehouseService aw = new WarehouseService();
		AppFUnit af = new AppFUnit();
		AppProductStockpile aps = new AppProductStockpile();
		
		// 条件
		String strCondition = " ps.warehouseid=wv.wid and wv.userid=" + userid + " and p.id=ps.productid and ps.stockpile!=0 ";
		Map map = new HashMap(request.getParameterMap());
		Map tmpMap = EntityManager.scatterMap(map);

		String[] tablename = { "ProductStockpile", "WarehouseVisit","Product" };
		String whereSql = EntityManager.getTmpWhereSql(map, tablename);
		if(!StringUtil.isEmpty((String)map.get("Batch"))) {
			whereSql = whereSql + " batch='"+map.get("Batch")+"' and ";
		}
		String blur = DbUtil.getOrBlur(map, tmpMap, "p.id", "ProductName",
				"PYCode", "SpecMode", "ps.batch");
		String leftblur = DbUtil.getBlurLeft(map, tmpMap, "PSID");

		whereSql = whereSql + blur + leftblur + strCondition;
		whereSql = DbUtil.getWhereSql(whereSql);

		List pls = aps.getStockStat(request, pagesize, whereSql);
		List alp = new ArrayList();

		for (int i = 0; i < pls.size(); i++) {
			ProductStockpileForm psf = new ProductStockpileForm();
			Object[] o = (Object[]) pls.get(i);
			ProductStockpile ps = (ProductStockpile) o[0];
			Product p = (Product) o[1];
			psf.setId(ps.getId());
			psf.setProductid(ps.getProductid());
			psf.setPsproductname(p.getProductname());
			psf.setPsspecmode(p.getSpecmode());
			psf.setBatch(ps.getBatch());
			//设置默认单位
			psf.setCountunit(Constants.DEFAULT_UNIT_ID);
			//数量换算
			Double xquantity = af.getXQuantity(psf.getProductid(), psf.getCountunit());
			if (xquantity != 0) {
				psf.setStrstockpile((ps.getStockpile() + ps.getPrepareout())/xquantity);
			}else {
				psf.setStrstockpile(0.0);
			}
			psf.setWarehouseid(ps.getWarehouseid());
			psf.setWarehourseidname(aw.getWarehouseName(ps.getWarehouseid()));
			psf.setWarehousebit(ps.getWarehousebit());
			 psf.setNccode(p.getmCode());
			alp.add(psf);
		}
		
		request.setAttribute("Batch", request.getParameter("Batch"));
		request.setAttribute("alp", alp);
		DBUserLog.addUserLog(request,"列表");
		return mapping.findForward("liststock");
	}
}
