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
import com.winsafe.drp.dao.AppStockWasteBook;
import com.winsafe.drp.dao.StockWasteBookForm;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DbUtil;

public class PrintStockWasteBookAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		UsersBean users = UserManager.getUser(request);
		int userid = users.getUserid();

		super.initdata(request);try{
			String condition = " s.productid=p.id ";
			Map map = new HashMap(request.getParameterMap());
			Map tmpMap = EntityManager.scatterMap(map);

			String[] tablename = { "StockWasteBook" };
			String whereSql = EntityManager.getTmpWhereSql(map, tablename);

			String timeCondition = DbUtil.getTimeCondition(map, tmpMap,
					" RecordDate");
			whereSql = whereSql + timeCondition + condition; 
			whereSql = DbUtil.getWhereSql(whereSql); 

			AppFUnit af = new AppFUnit();
			AppStockWasteBook aswb = new AppStockWasteBook();
			List sals = aswb.getStockWastBook(whereSql);
			
			List list = new ArrayList();
			for ( int i=0; i<sals.size(); i++ ){
				Map p =(Map)sals.get(i);
				StockWasteBookForm sf = new StockWasteBookForm();
				sf.setRecorddate(p.get("recorddate").toString());
				sf.setProductid(p.get("productid").toString());
				sf.setProductidname(p.get("productname").toString());
				sf.setBatch(p.get("batch").toString());
				sf.setWarehouseid(p.get("warehouseid").toString());
				sf.setWarehousebit(p.get("warehousebit").toString());
				sf.setBillcode(p.get("billcode").toString());
				sf.setMemo(p.get("memo").toString());
				sf.setUnitid(Integer.valueOf(p.get("sunit").toString()));
				sf.setCyclefirstquantity(af.getStockpileQuantity2(sf.getProductid(), sf.getUnitid(),
						Double.valueOf(p.get("cyclefirstquantity").toString())));
				sf.setCycleinquantity(af.getStockpileQuantity2(sf.getProductid(), sf.getUnitid(),
						Double.valueOf(p.get("cycleinquantity").toString())));
				sf.setCycleoutquantity(af.getStockpileQuantity2(sf.getProductid(), sf.getUnitid(),
						Double.valueOf(p.get("cycleoutquantity").toString())));
				sf.setCyclebalancequantity(af.getStockpileQuantity2(sf.getProductid(), sf.getUnitid(),
						Double.valueOf(p.get("cyclebalancequantity").toString())));
				list.add(sf);
			}

			request.setAttribute("als", list);
			DBUserLog.addUserLog(userid, 7, "打印库存台账");
			

			return mapping.findForward("toprint");
		} catch (Exception e) {
			e.printStackTrace();
			String result = "databases.settlement.noexist";
			request.setAttribute("result", result);
			return new ActionForward("/sys/lockrecordclose.jsp");
		}

	}
}
