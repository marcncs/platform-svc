package com.winsafe.drp.action.warehouse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppPurchaseSalesReport;
import com.winsafe.drp.dao.AppStockPileAgeing;
import com.winsafe.drp.dao.ProductStockpileForm;
import com.winsafe.drp.dao.StockPileAgeing;
import com.winsafe.drp.dao.StockPileReportService;
import com.winsafe.drp.dao.Warehouse;
import com.winsafe.drp.server.WarehouseService;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.StringUtil;

public class ListStockPileAgeingTotalReportAction extends BaseAction {
	
	AppPurchaseSalesReport apsr = new AppPurchaseSalesReport();
	AppStockPileAgeing aspa = new AppStockPileAgeing();
	StockPileReportService sprs = new StockPileReportService();
	@SuppressWarnings("unchecked")
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		int pagesize = 20;
		super.initdata(request);
		
		String isshowBatch = request.getParameter("isShowbatch");
		
		 String orderSql = request.getParameter("orderbySql");
		 String orderSqlName = request.getParameter("orderbySqlShowName");
		 Map<String, StockPileAgeing> ageingMap = aspa.getAgeingMap();
		 String ageingSql = sprs.getSql(ageingMap, "ceil(sysdate-to_date(batch,'yyyymmdd'))");
		
		try {
			String condition = " s.productid=p.id and ps.structcode = p.psid ";
			condition += " and s.warehouseid in (select wv.wid from  Warehouse_Visit  wv where wv.userid=" + userid + ")";
			
			WarehouseService aw = new WarehouseService();
			String[] tablename = { "PurchaseSalesReport" };
			String whereSql = getWhereSql(tablename);
			//String timeCondition = getTimeCondition("recorddate");
			String color = request.getParameter("ageing");
			if(!StringUtil.isEmpty(color)){
				StockPileAgeing spa =ageingMap.get(color);
				condition+=" and ceil(sysdate-to_date(batch,'yyyymmdd')) >="+spa.getTagMinValue()+" and ceil(sysdate-to_date(batch,'yyyymmdd'))<="+spa.getTagMaxValue();
			}
			
			String blur = getKeyWordCondition("s.batch");
	        String leftblur = DbUtil.getBlurLeft(map, tmpMap, "PSID");
			//whereSql = whereSql +leftblur +  timeCondition + blur  + condition;
			whereSql = whereSql +leftblur + blur  + condition;
			whereSql = DbUtil.getWhereSql(whereSql);
			
			List sals = new ArrayList();
			if(isshowBatch!=null){
				sals = sprs.getAgeingTotalByPage(request, pagesize, whereSql,orderSql, ageingSql);
			}else{
				if(orderSql!=null){
					orderSql = orderSql.replaceAll("batch", "productid");
				}
				
				sals = sprs.getAgeingTotalByPageNoBatch(request, pagesize, whereSql,orderSql, ageingSql);
			}
			List wls = aw.getEnableWarehouseByVisit(userid);
			List alp = new ArrayList();
			for (int i = 0; i < sals.size(); i++) {
				ProductStockpileForm psf = new ProductStockpileForm();
				Map p = (Map) sals.get(i);
				Double quantity = Double.valueOf(p.get("quantity").toString());

				psf.setWarehouseid(p.get("warehouseid").toString());
				psf.setWarehourseidname(aw.getWarehouseName(p.get("warehouseid").toString()));
				psf.setProductid(p.get("productid").toString());
				psf.setBarcode(p.get("nccode")==null?"":p.get("nccode").toString());
				psf.setPsproductname(p.get("productname").toString());
				psf.setPsspecmode(p.get("specmode").toString());
				psf.setSortName(p.get("sortname").toString());
				//psf.setStockPileAgeing(Integer.valueOf(p.get("ageing").toString()));
//				if(StringUtil.isEmpty(color)){
//					psf.setTagColor(getColor(ageingMap, psf.getStockPileAgeing()));
//				}else{
//					psf.setTagColor(color);
//				}
				psf.setTagColor(p.get("color").toString());
				
				psf.setMinValue(ageingMap.get(psf.getTagColor()).getTagMinValue());
				psf.setMaxValue(ageingMap.get(psf.getTagColor()).getTagMaxValue());
				if(isshowBatch!=null){
					psf.setBatch(p.get("batch").toString());
				}
				Integer sunit = Integer.valueOf(p.get("countunit").toString());
				psf.setCountunit(sunit);
				//psf.setDate(p.get("recorddate").toString().substring(0, 10));
				psf.setStockpile(quantity);
				alp.add(psf);
			}
			
			List spas = aspa.getStockPileAgeing();
			
			Map<String,String> orderColumnMap = new HashMap<String, String>();
	      	generateMap(orderColumnMap);
			
			request.setAttribute("orderSql", orderSql);
			request.setAttribute("orderSqlName", orderSqlName);
			request.getSession().setAttribute("orderColumnMap", orderColumnMap);

			request.setAttribute("als", alp);
			request.setAttribute("alw", wls);

			DBUserLog.addUserLog(userid, 7, "库存管理>>查询入库报表");
			request.setAttribute("spas", spas);
			request.setAttribute("color", color);
			request.setAttribute("BeginDate", map.get("BeginDate"));
			request.setAttribute("EndDate", map.get("EndDate"));
			request.setAttribute("isshowBatch", isshowBatch);

			return mapping.findForward("list");
		} catch (Exception e) {
			e.printStackTrace();
			String result = "databases.settlement.noexist";
			request.setAttribute("result", result);
			return new ActionForward("/sys/lockrecordclose.jsp");
		}
	}

	@SuppressWarnings("unchecked")
	public String getWarehouseId(List wlist) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < wlist.size(); i++) {
			Warehouse w = (Warehouse) wlist.get(i);
			if (i == 0) {
				sb.append("'").append(w.getId()).append("'");
			} else {
				sb.append(",'").append(w.getId()).append("'");
			}
		}
		return sb.toString();
	}
	
	
	private void generateMap(Map<String,String> orderColumnMap) {
		  orderColumnMap.put("warehouseid", "仓库编号");
		  orderColumnMap.put("batch", "批次");
		  orderColumnMap.put("recorddate", "日期");
		  //orderColumnMap.put("nccode", "产品内部编号");
		 // orderColumnMap.put("p.psid", "产品类别");
	}
	
	

	
	
	
}
