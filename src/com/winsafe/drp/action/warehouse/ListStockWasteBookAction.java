package com.winsafe.drp.action.warehouse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppFUnit;
import com.winsafe.drp.dao.AppProduct;
import com.winsafe.drp.dao.AppStockWasteBook;
import com.winsafe.drp.dao.StockWasteBookForm;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DbUtil;

public class ListStockWasteBookAction extends BaseAction {
	private static Logger logger = Logger.getLogger(ListStockWasteBookAction.class);

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		//初始化
		initdata(request);
		int pagesize = 15;
		AppProduct ap =new AppProduct();
		AppFUnit af = new AppFUnit();
		AppStockWasteBook aswb = new AppStockWasteBook();
		  
		try {
			String condition = " s.productid=p.id and s.warehouseid=w.id and p.psid=pstr.structcode ";
			condition += " and s.warehouseid in (select wv.wid from  WarehouseVisit as wv where wv.userid=" + userid + ")";
			Map map = new HashMap(request.getParameterMap());
			Map tmpMap = EntityManager.scatterMap(map);
			String[] tablename = { "StockWasteBook" };
			String whereSql = EntityManager.getTmpWhereSql(map, tablename);
			String timeCondition = DbUtil.getTimeCondition(map, tmpMap, " RecordDate");
			String leftblur = DbUtil.getBlurLeft(map, tmpMap, "PSID");
			whereSql = whereSql +leftblur +  timeCondition + condition;
			whereSql = DbUtil.getWhereSql(whereSql);
			
			List sals = aswb.getStockWastBook(request, pagesize, whereSql,"");

			List list = new ArrayList();
			for (int i = 0; i < sals.size(); i++) {
				Map p = (Map) sals.get(i);
				StockWasteBookForm sf = new StockWasteBookForm();
				sf.setWarehouseid(p.get("warehouseid").toString());
				sf.setWarehouseidname(p.get("warehousename").toString());
				sf.setSortname(p.get("sortname").toString());
				sf.setNccode(p.get("nccode")==null?"":p.get("nccode").toString());
				sf.setProductname(p.get("productname").toString());
				sf.setSpecmode(p.get("specmode").toString());
				sf.setBatch(p.get("batch").toString());
				sf.setRecorddate(p.get("recorddate").toString());
				sf.setBillcode(p.get("billcode").toString());
				sf.setProductid(p.get("productid").toString());
				sf.setWarehousebit(p.get("warehousebit").toString());
				sf.setCyclefirstquantity(Double.valueOf(p.get("cyclefirstquantity").toString()));
				sf.setCycleinquantity(Double.valueOf(p.get("cycleinquantity").toString()));
				sf.setCycleoutquantity(Double.valueOf(p.get("cycleoutquantity").toString()));
				sf.setCyclebalancequantity(Double.valueOf(p.get("cyclebalancequantity").toString()));

				list.add(sf);
			}
			
			request.setAttribute("als", list);
			DBUserLog.addUserLog(userid, "库存查询", "查询库存台账");
			return mapping.findForward("list");
		} catch (Exception e) {
			logger.error("", e);
			String result = "databases.settlement.noexist";
			request.setAttribute("result", result);
			return new ActionForward("/sys/lockrecordclose.jsp");
		}

	}
	
	private void generateMap(Map<String,String> orderColumnMap) {
		  orderColumnMap.put("warehouseid", "仓库编号");
		  orderColumnMap.put("s.batch", "批次");
		  orderColumnMap.put("p.nccode", "产品内部编号");
		  orderColumnMap.put("recorddate", "日期");
		  orderColumnMap.put("cyclefirstquantity", "期初数量");
		  orderColumnMap.put("cycleinquantity", "收入数量");
		  orderColumnMap.put("cycleoutquantity", "发出数量");
		  orderColumnMap.put("cyclebalancequantity", "结存数量");
	}
}
