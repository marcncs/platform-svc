package com.winsafe.drp.action.newreport;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.server.ReportServices;
import com.winsafe.drp.server.SalesConsumeInventoryReportService;
import com.winsafe.drp.util.ArithDouble;
import com.winsafe.drp.util.MapUtil;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.StringUtil;
import com.winsafe.hbm.util.pager2.PageQuery;

public class SalesLnventoryReportService extends ReportServices {
	private static Logger logger = Logger
			.getLogger(SalesLnventoryReportService.class);
	private SalesConsumeInventoryReportService scirs = new SalesConsumeInventoryReportService();
	// 根据条件查询
	public List<SalesLnventoryReportForm> querySalesDetailReport(
			HttpServletRequest request, int pageSize,
			SalesLnventoryReportForm scrForm, UsersBean users) throws Exception {
		
		//获取当前日期
		Calendar currentCalendar = Calendar.getInstance();
		int currentYear = currentCalendar.get(Calendar.YEAR);
		int currentMonth = currentCalendar.get(Calendar.MONTH) + 1;
		String currentMonthYear = DateUtil.formatDate(currentCalendar.getTime()).substring(0,7).replace("-", "");
		
		request.setAttribute("year", currentYear);
		request.setAttribute("yearMonth", currentYear + "/" + currentMonth);
		//获取年初
		Calendar beginOfYear = Calendar.getInstance();
		beginOfYear.set(Calendar.MONTH, 0);
		beginOfYear.set(Calendar.DAY_OF_MONTH, 1);
		String beginMonthYear = DateUtil.formatDate(beginOfYear.getTime()).substring(0,7).replace("-", "");
		
		Calendar inventoryDate = Calendar.getInstance();
		inventoryDate.set(Calendar.MONTH, 0);
		inventoryDate.set(Calendar.DAY_OF_MONTH, 0);
		String endDate = DateUtil.formatDate(inventoryDate.getTime()) + " 23:59:59";
		
		List<SalesLnventoryReportForm> resultList = new ArrayList<SalesLnventoryReportForm>();
		StringBuffer sql = new StringBuffer();
		sql.append(" \r\n  select t1.regionName regionName  ");
		sql.append(" \r\n  ,t1.provinceId provinceId ");
		sql.append(" \r\n   ,t1.province province ");
		sql.append(" \r\n  ,t1.organId   ");
		sql.append(" \r\n  ,t1.oecode oecode   ");
		sql.append(" \r\n   ,t1.organName organName  ");
		sql.append(" \r\n   ,t1.mCode mCode  ");
		sql.append(" \r\n   ,t1.boxquantity boxquantity  ");
		sql.append(" \r\n   ,t1.warehouseId warehouseId  ");
		sql.append(" \r\n   ,t1.warehouseName warehouseName  ");
		sql.append(" \r\n   ,t1.productName productName  ");
		sql.append(" \r\n    ,t1.productId productId  ");
		sql.append(" \r\n    ,t1.productNameen productNameen ");
		sql.append(" \r\n    ,t1.matericalChDes matericalChDes ");
		sql.append(" \r\n　　,t1.matericalEnDes matericalEnDes");
		sql.append(" \r\n 　 ,t1.packSizeNameEn packSizeNameEn ");
		sql.append(" \r\n 　　 ,t1.salesVolume * t1.boxquantity salesVolume ");
		sql.append(" \r\n 　　 ,(t1.consumptionVolume * t1.boxquantity)+(t1.otherConsumptionVolume * t1.boxquantity) consumptionVolume ");
		sql.append(" \r\n 　　 ,t1.otherConsumptionVolume * t1.boxquantity otherConsumptionVolume ");
		sql.append(" \r\n 　　 ,t2.monthSalesVolume * t1.boxquantity monthSalesVolume");   
		sql.append(" \r\n 　　 ,(t2.monthConsumptionVolume * t1.boxquantity) + (t2.monthOtherConsumptionVolume * t1.boxquantity) monthConsumptionVolume");
		sql.append(" \r\n 　　 ,t2.monthOtherConsumptionVolume * t1.boxquantity monthOtherConsumptionVolume");
		sql.append(" \r\n  from (select max(r.sortname) regionName ");
		sql.append(" \r\n 　 ,max(o.province) provinceId  ");
		sql.append(" \r\n 　 ,max(country.areaname) province  ");
 		sql.append(" \r\n 　  ,o.id organId  ");
  		sql.append(" \r\n 　  ,max(o.oecode) oecode  "); 
  		sql.append(" \r\n 　 ,max(p.boxquantity) boxquantity  "); 
		sql.append(" \r\n 　 ,max(o.organname) organName  ");
		sql.append(" \r\n 　 ,max(p.mcode) mCode   ");
		sql.append(" \r\n 　 ,sch.warehouseId warehouseId   ");
		sql.append(" \r\n 　 ,max(w.warehousename) warehouseName  ");
		sql.append(" \r\n 　 ,max(p.productname) productName   ");
		sql.append(" \r\n 　  ,sch.PRODUCTID productId  ");
		sql.append(" \r\n 　  ,max(p.productnameen) productNameen  ");
		sql.append(" \r\n　 ,max(p.matericalchdes) matericalChDes ");
		sql.append(" \r\n　 ,max(p.matericalendes) matericalEnDes  ");
		sql.append(" \r\n　  ,max(p.packsizenameen) packSizeNameEn ");
		sql.append(" \r\n　  ,sum(sch.SALES_VOLUME) salesVolume ");
		sql.append(" \r\n　  ,sum(sch.COMSUMPTION_VOLUME) consumptionVolume ");
		sql.append(" \r\n　  ,sum(sch.other_consum_volume) otherConsumptionVolume ");
		
		sql.append(" \r\n　  from (select productId,organId,warehouseId,SALES_VOLUME,COMSUMPTION_VOLUME,other_consum_volume");
		sql.append(" \r\n　  from SALES_CONSUM_HISTORY");
		sql.append(" \r\n　  WHERE YEAR='" +currentYear+ "' ");
		sql.append(" \r\n　  union ALL");
		sql.append(" \r\n　  select ps.productId, ps.organId, ps.warehouseId, 0,0,0 from (");
		sql.append(scirs.getInventorySql(scrForm.getProductName(), scrForm.getPackSizeName(), scrForm.getRegion(), scrForm.getOrganId(), scrForm.getWarehouseId(), endDate, users));
		sql.append(" \r\n having sum(ps.stockpile) <> 0 "); 
		sql.append(" \r\n　 ) sch");
		
		
		sql.append(" \r\n　 join organ o on sch.ORGANID=o.ID");
		if(DbUtil.isDealer(users)) {
			sql.append("\r\n  and warehouseId in (select wv.warehouse_Id from RULE_USER_WH wv where wv.user_Id="+users.getUserid()+")");
		} else {
			sql.append(" \r\n and "+DbUtil.getWhereCondition(users, "o"));
		}
		if (!StringUtil.isEmpty(scrForm.getOrganId())) {
			sql.append(" \r\n and sch.ORGANID = '" + scrForm.getOrganId() + "' "); // 机构条件
		}
		if (!StringUtil.isEmpty(scrForm.getWarehouseId())) {
			sql.append(" \r\n and sch.warehouseid = '"
					+ scrForm.getWarehouseId() + "' "); // 仓库条件
		}
		sql.append(" \r\n　 join warehouse w on sch.warehouseid=w.ID and w.USEFLAG = 1");
		sql.append(" \r\n　  join product p on sch.PRODUCTID=p.ID ");
		sql.append(" \r\n　  and p.USEFLAG = 1 ");
		if(!StringUtil.isEmpty(scrForm.getProductName()) && !StringUtil.isEmpty(scrForm.getPackSizeName())) { //产品条件
			sql.append(" \r\n and sch.PRODUCTID in (select id from product where productname = '"+scrForm.getProductName()+"' and specmode= '"+scrForm.getPackSizeName()+"') ");
		} else if(!StringUtil.isEmpty(scrForm.getProductName()) && StringUtil.isEmpty(scrForm.getPackSizeName())) { //产品条件
			sql.append(" \r\n and sch.PRODUCTID in (select id from product where productname = '"+scrForm.getProductName()+"') ");
		}
		sql.append(" \r\n　 JOIN PRODUCT_STRUCT pstr on p.PSID = pstr.STRUCTCODE   ");
		
		// 大区条件
		if (!StringUtil.isEmpty(scrForm.getRegion())) {
			sql.append(" \r\n　JOIN COUNTRY_AREA country on o.province=country.id ");
			sql.append(" \r\n　JOIN (SELECT max(REGIONCODEID) regioncodeid,max(AREAID) areaid  FROM REGION_AREA GROUP BY AREAID) ra on ra.areaid=country.id ");
			sql.append(" \r\n　JOIN REGION r on r.regioncode=ra.regioncodeid   ");
			sql.append(" \r\n and r.regioncode = '" + scrForm.getRegion()
					+ "' ");
		} else {
			sql.append(" \r\n　  LEFT JOIN COUNTRY_AREA country on o.province=country.id ");
			sql.append(" \r\n　LEFT JOIN (SELECT max(REGIONCODEID) regioncodeid,max(AREAID) areaid  FROM REGION_AREA GROUP BY AREAID) ra on ra.areaid=country.id ");
			sql.append(" \r\n　  LEFT JOIN REGION r on r.regioncode=ra.regioncodeid   ");
		}
//		sql.append(" \r\n　WHERE YEAR='" +currentYear+ "' ");
		sql.append(" \r\n　  GROUP BY o.id,sch.warehouseId,sch.PRODUCTID) t1");
		sql.append(" \r\n　  LEFT JOIN(select   ");
		sql.append(" \r\n　   sch.organId organId  ");
		sql.append(" \r\n　  ,sch.warehouseId warehouseId ");
		sql.append(" \r\n　  ,sch.PRODUCTID productId  ");
		sql.append(" \r\n　 ,sum(sch.SALES_VOLUME) monthSalesVolume  ");
		sql.append(" \r\n　  ,sum(sch.COMSUMPTION_VOLUME) monthConsumptionVolume ");
		sql.append(" \r\n　  ,sum(sch.other_consum_volume) monthOtherConsumptionVolume ");
		sql.append(" \r\n　   from SALES_CONSUM_HISTORY sch");
		sql.append(" \r\n　 WHERE  YEAR='" +currentYear+ "' and MONTH='" +currentMonth+ "' ");
		sql.append(" \r\n　  GROUP BY sch.organId,sch.warehouseId,sch.PRODUCTID) t2");
		sql.append(" \r\n　  on t1.organId =t2.organId  and t1.warehouseId = t2.warehouseId and t1.productId = t2.productId");
		sql.append("\r\n order by NLSSORT(t1.regionName,'NLS_SORT = SCHINESE_PINYIN_M'),NLSSORT(t1.province,'NLS_SORT = SCHINESE_PINYIN_M'),NLSSORT(t1.organName,'NLS_SORT = SCHINESE_PINYIN_M'), NLSSORT(t1.productNameen,'NLS_SORT = SCHINESE_PINYIN_M')");
		System.out.println(sql.toString());
		List<Map> list = new ArrayList<Map>();
		if (request == null || pageSize == 0) {
			list = EntityManager.jdbcquery(sql.toString());
		} else {
			list = PageQuery.jdbcSqlserverQuery(request, "NLSSORT(regionName,'NLS_SORT = SCHINESE_PINYIN_M'),NLSSORT(province,'NLS_SORT = SCHINESE_PINYIN_M'),NLSSORT(organName,'NLS_SORT = SCHINESE_PINYIN_M'), NLSSORT(productNameen,'NLS_SORT = SCHINESE_PINYIN_M')", sql.toString(), pageSize);
		}
		if (list != null && list.size() > 0) {
			
			Map<String, Double> monthBeginInventoryMap = scirs.getMonthBeginInventoryMap(scrForm.getProductName(), scrForm.getPackSizeName(), scrForm.getRegion(), scrForm.getOrganId(), scrForm.getWarehouseId(), currentCalendar, beginMonthYear, currentMonthYear, users);
			Map<String, Double> yearBeginInventoryMap = scirs.getMonthBeginInventoryMap(scrForm.getProductName(), scrForm.getPackSizeName(), scrForm.getRegion(), scrForm.getOrganId(), scrForm.getWarehouseId(), beginOfYear, beginMonthYear, currentMonthYear, users);
			
			
			for (Map map : list) {
				SalesLnventoryReportForm scForm = new SalesLnventoryReportForm();
				// 将Map中对应的值赋值给实例
				MapUtil.mapToObject(map, scForm);
				//价格
				scForm.setValue(scirs.getPrductRollingPrice(currentYear,currentMonth, scForm.getProductId(),scForm.getOrganId()));
				//月初库存和金额
				Double monthBeginInventory = monthBeginInventoryMap.get(scForm.getProductId()+"_"+scForm.getOrganId()+"_"+scForm.getWarehouseId());
				scForm.setMonthBeginInventory(monthBeginInventory != null ? monthBeginInventory : 0d);
				scForm.setMonthBeginInventoryValue(ArithDouble.mul(scForm.getMonthBeginInventory(), scForm.getValue()));
				//月末库存和金额
//				scForm.setMonthEndInventory(ArithDouble.sub(ArithDouble.sub(ArithDouble.add(scForm.getMonthBeginInventory(), scForm.getMonthSalesVolume()), scForm.getMonthConsumptionVolume()), scForm.getMonthOtherConsumptionVolume()));
				scForm.setMonthEndInventory(ArithDouble.sub(ArithDouble.add(scForm.getMonthBeginInventory(), scForm.getMonthSalesVolume()), scForm.getMonthConsumptionVolume()));
				scForm.setMonthEndInventoryValue(ArithDouble.mul(scForm.getMonthEndInventory(), scForm.getValue()));
				//年初库存和金额
				Double yearBeginInventory = yearBeginInventoryMap.get(scForm.getProductId()+"_"+scForm.getOrganId()+"_"+scForm.getWarehouseId());
				scForm.setYearBeginInventory(yearBeginInventory != null ? yearBeginInventory : 0d);
				scForm.setYearBeginInventoryValue(ArithDouble.mul(scForm.getYearBeginInventory(), scForm.getValue()));
				//年末库存和金额
//				scForm.setYearEndInventory(ArithDouble.sub(ArithDouble.sub(ArithDouble.add(scForm.getYearBeginInventory(), scForm.getSalesVolume()), scForm.getConsumptionVolume()), scForm.getOtherConsumptionVolume()));
				scForm.setYearEndInventory(ArithDouble.sub(ArithDouble.add(scForm.getYearBeginInventory(), scForm.getSalesVolume()), scForm.getConsumptionVolume()));
				scForm.setYearEndInventoryValue(ArithDouble.mul(scForm.getYearEndInventory(), scForm.getValue()));
				
				scForm.setMonthSalesValue(ArithDouble.mul(scForm.getMonthSalesVolume(), scForm.getValue()));
				scForm.setMonthConsumptionValue(ArithDouble.mul(scForm.getMonthConsumptionVolume(), scForm.getValue()));
				scForm.setMonthOtherConsumptionValue(ArithDouble.mul(scForm.getMonthOtherConsumptionVolume(), scForm.getValue()));
				
				scForm.setSalesValue(ArithDouble.mul(scForm.getSalesVolume(), scForm.getValue()));
				scForm.setConsumptionValue(ArithDouble.mul(scForm.getConsumptionVolume(), scForm.getValue()));
				scForm.setOtherConsumptionValue(ArithDouble.mul(scForm.getOtherConsumptionVolume(), scForm.getValue()));
				
				resultList.add(scForm);
			}
		}
		return resultList;
	}
	
	public static void main(String[] args) {
		
	}
	
}
