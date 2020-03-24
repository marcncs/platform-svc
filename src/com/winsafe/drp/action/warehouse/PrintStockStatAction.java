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
import com.winsafe.drp.server.WarehouseService;
import com.winsafe.drp.util.ArithDouble;
import com.winsafe.drp.util.Constants;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DbUtil;

public class PrintStockStatAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		WarehouseService aw = new WarehouseService();
		super.initdata(request);
		try {
			String strCondition = " ps.warehouseid=wv.wid and wv.userid="
					+ userid + " and p.id=ps.productid and ps.stockpile!=0 ";
			Map map = new HashMap(request.getParameterMap());
			Map tmpMap = EntityManager.scatterMap(map);

			String[] tablename = { "ProductStockpile", "WarehouseVisit",
					"Product" };
			String whereSql = EntityManager.getTmpWhereSql(map, tablename);
			String blur = DbUtil.getOrBlur(map, tmpMap, "p.id", "ProductName",
					"PYCode", "SpecMode", "ps.batch");
			String leftblur = DbUtil.getBlurLeft(map, tmpMap, "PSID");

			whereSql = whereSql + blur + leftblur + strCondition;
			whereSql = DbUtil.getWhereSql(whereSql);

			AppFUnit af = new AppFUnit();
			AppProductStockpile aps = new AppProductStockpile();
			List pls = aps.getStockStat(whereSql);
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
				psf.setCountunit(Constants.DEFAULT_UNIT_ID);
				
				psf.setStrstockpile(af.getStockpileQuantity2(ps.getProductid(), psf.getCountunit(), ps.getStockpile()));
				psf.setWarehouseid(ps.getWarehouseid());
				psf.setWarehourseidname(aw
						.getWarehouseName(ps.getWarehouseid()));
				psf.setWarehousebit(ps.getWarehousebit());
				alp.add(psf);
			}
			request.setAttribute("list", alp);
			
			DBUserLog.addUserLog(request,"列表");
			return mapping.findForward("toprint");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
}
