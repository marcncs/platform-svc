package com.winsafe.drp.action.warehouse;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppFUnit;
import com.winsafe.drp.dao.AppStockWasteBook;
import com.winsafe.drp.dao.ProductStockpileForm;
import com.winsafe.drp.server.WarehouseService;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DbUtil;

public class PrintStockWasteBookTotalAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		super.initdata(request);
		try {
			String condition = " s.productid=p.id ";			

			String[] tablename = { "StockWasteBook" };
			String whereSql = getWhereSql(tablename);

			String timeCondition = getTimeCondition("RecordDate");
			String blur = getKeyWordCondition("s.batch");
			whereSql = whereSql + timeCondition + blur +condition; 
			whereSql = DbUtil.getWhereSql(whereSql); 

			AppStockWasteBook aswb = new AppStockWasteBook();
			List sals = aswb.getStockWasteBookTotal2(whereSql);

			request.setAttribute("als", sals);
			
			WarehouseService aw = new WarehouseService();
			List wls = aw.getEnableWarehouseByVisit(userid);
			List alp = new ArrayList();
			AppFUnit af = new AppFUnit();
			for (int i = 0; i < sals.size(); i++) {
				ProductStockpileForm psf = new ProductStockpileForm();
				Map p = (Map) sals.get(i);

				psf.setWarehouseid(p.get("warehouseid").toString());
				psf.setWarehourseidname(aw.getWarehouseName(p
						.get("warehouseid").toString()));
				String productid = p.get("productid").toString();
				psf.setProductid(p.get("productid").toString());
				psf.setBarcode(p.get("synid").toString());
				psf.setPsproductname(p.get("productname").toString());
				psf.setPsspecmode(p.get("specmode").toString());
				psf.setBatch(p.get("batch").toString());
				Integer sunit = Integer.valueOf(p.get("sunit").toString());
				psf.setCountunit(sunit);
				psf.setPrepareout(af.getStockpileQuantity2(productid, sunit,
						Double.valueOf(p.get("inquantity").toString())));
				psf.setStockpile(af.getStockpileQuantity2(productid, sunit,
						Double.valueOf(p.get("outquantity").toString())));
				psf.setAllstockpile(af.getStockpileQuantity2(productid, sunit,
						Double.valueOf(p.get("quantity").toString())));

				alp.add(psf);
			}

			request.setAttribute("als", alp);
			
			
			DBUserLog.addUserLog(userid, 7, "库存管理>>打印库存报表");
			
			request.setAttribute("BeginDate", map.get("BeginDate"));
			request.setAttribute("EndDate", map.get("EndDate"));

			return mapping.findForward("list");
		} catch (Exception e) {
			e.printStackTrace();
			String result = "databases.settlement.noexist";
			request.setAttribute("result", result);
			return new ActionForward("/sys/lockrecordclose.jsp");
		}

	}
}
