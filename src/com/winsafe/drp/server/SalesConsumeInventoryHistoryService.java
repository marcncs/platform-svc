package com.winsafe.drp.server;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;  
import java.util.Map;

import javax.servlet.http.HttpServletRequest;


import org.apache.log4j.Logger;

import com.winsafe.drp.dao.FUnit;
import com.winsafe.drp.dao.InventoryDetailReport;
import com.winsafe.drp.dao.Product;
import com.winsafe.drp.dao.SalesConsumeDetailReportForm;
import com.winsafe.drp.dao.StockAlterMove;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.util.ArithDouble;
import com.winsafe.drp.util.Constants;
import com.winsafe.drp.util.MapUtil;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.StringUtil;
import com.winsafe.hbm.util.pager2.PageQuery;
import com.winsafe.sap.pojo.PrintJob;

public class SalesConsumeInventoryHistoryService extends ReportServices{
	private static Logger logger = Logger.getLogger(SalesConsumeInventoryHistoryService.class);

	public List<SalesConsumeDetailReportForm> querySalesDetailHistory(
			HttpServletRequest request, int pageSize,
			SalesConsumeDetailReportForm queryForm, UsersBean users) throws Exception {


		StringBuffer sql = new StringBuffer();
		// 工厂给PD
		sql.append(" \r\n select (year || case when month < 10 THEN '0'||month ELSE to_char(month) END) yearmonth, sdh.billNo");
		sql.append(" \r\n ,r.sortname regionName");
		sql.append(" \r\n ,o.province provinceId");
		sql.append(" \r\n ,o.oecode oecode");
		sql.append(" \r\n ,ca.areaname province");
		sql.append(" \r\n ,o.id organId ");
		sql.append(" \r\n ,o.organname organName");
		sql.append(" \r\n ,sdh.productid productId  ");
		sql.append(" \r\n ,pstr.sortname psName");
		sql.append(" \r\n ,p.productname productName");
		sql.append(" \r\n ,p.productnameen productNameen ");
		sql.append(" \r\n ,p.mcode mCode");
		sql.append(" \r\n ,p.matericalchdes matericalChDes ");
		sql.append(" \r\n ,p.matericalendes matericalEnDes ");
		sql.append(" \r\n ,p.sunit unitId ");
		sql.append(" \r\n ,p.specmode packSizeName");
		sql.append(" \r\n ,p.packsizenameen packSizeNameEn");
		sql.append(" \r\n ,sdh.batch batch ");
		sql.append(" \r\n ,sdh.salesQuantity"); // 销售数量
		sql.append(" \r\n ,0 consumeQuantity"); //消耗数量
		sql.append(" \r\n ,sdh.makeDate ,w.warehousename");
		sql.append(" \r\n from SALES_DETAIL_HISTORY sdh");
		sql.append(" \r\n join organ o on o.id =sdh.organid and o.isrepeal=0");
		//日期条件
		if(!StringUtil.isEmpty(queryForm.getBeginDate())) {
			sql.append("\r\n and (year || case when month < 10 THEN '0'||month ELSE to_char(month) END) >= '"+(queryForm.getBeginDate()).substring(0,7).replace("-", "")+"'");
		}
		if(!StringUtil.isEmpty(queryForm.getEndDate())) {
			sql.append("\r\n and (year || case when month < 10 THEN '0'||month ELSE to_char(month) END) <= '"+(queryForm.getEndDate()).substring(0,7).replace("-", "")+"'");
		}
		if(!StringUtil.isEmpty(queryForm.getOrganId())){
			sql.append(" \r\n and sdh.organid = '" + queryForm.getOrganId() + "' "); //机构条件
		} else if(DbUtil.isDealer(users)) {
			sql.append(" \r\n JOIN RULE_USER_WH rw on sdh.warehouseid = rw.warehouse_id  ");
			sql.append(" \r\n and rw.user_id = '" + users.getUserid() + "'");
		} else {
			sql.append(" \r\n and "+DbUtil.getWhereCondition(users, "o"));
		}
		sql.append(" \r\n join PRODUCT p on p.ID=sdh.productid and p.useflag=1");
		if(!StringUtil.isEmpty(queryForm.getProductName()) && !StringUtil.isEmpty(queryForm.getPackSizeName())) { //产品条件
			sql.append(" \r\n and p.productname = '"+queryForm.getProductName()+"' and p.specmode= '"+queryForm.getPackSizeName()+"' ");
		} else if(!StringUtil.isEmpty(queryForm.getProductName()) && StringUtil.isEmpty(queryForm.getPackSizeName())) { //产品条件
			sql.append(" \r\n and p.productname = '"+queryForm.getProductName()+"' ");
		}
		sql.append(" \r\n join PRODUCT_STRUCT pstr on pstr.structcode=p.psid ");
		sql.append(" \r\n join WAREHOUSE w on w.id=sdh.warehouseid and w.useflag=1");
		if(!StringUtil.isEmpty(queryForm.getWarehouseId())){
			sql.append(" \r\n and sdh.warehouseid = '" + queryForm.getWarehouseId() + "' "); //仓库条件
		}
		sql.append(" \r\n left join COUNTRY_AREA ca on ca.id = o.province");
		sql.append(" \r\n LEFT join REGION_AREA ra on RA.AREAID=o.province");
		sql.append(" \r\n LEFT JOIN REGION r on r.regioncode = ra.regioncodeid");
		//大区条件
		if(!StringUtil.isEmpty(queryForm.getRegion())){
			sql.append(" \r\n where r.regioncode = '" + queryForm.getRegion() + "' "); 
		}
		
		System.out.println(sql.toString());
		List<SalesConsumeDetailReportForm> resultList = new ArrayList<SalesConsumeDetailReportForm>();
		List<Map> list =  new ArrayList<Map>();
		if(request == null && pageSize == 0){
			list =  EntityManager.jdbcquery(sql.toString());
		}else {
			list =  PageQuery.jdbcSqlserverQuery(request,"yearmonth desc,billNo", sql.toString(), pageSize);
		}
		if(list != null && list.size()>0){
			Map<String,String> sapDeliveryNos = new HashMap<String, String>();
			Map<String,FUnit>  funitMap = appFUnit.getAllMap(); //获取所有的单位换算信息
			Map<Integer, String> countUnitMap = appBr.getBaseResourceMap("CountUnit");
			for(Map map : list){
				SalesConsumeDetailReportForm scForm = new SalesConsumeDetailReportForm();
				//将Map中对应的值赋值给实例
				MapUtil.mapToObject(map, scForm);
				//报告日期主当前日期
//				scForm.setReportDate(new Timestamp(System.currentTimeMillis()));
				//销售日期
				if(scForm.getMakeDate() != null){
					scForm.setMakeDate(new Timestamp(scForm.getMakeDate().getTime()));
				}
				
				//获取生产日期与过期日期
				PrintJob printJob = appPrintJob.getPrintJobByBatAMc(scForm.getmCode(), scForm.getBatch());
				if(printJob != null) {
					scForm.setProduceDate(printJob.getProductionDate());
					scForm.setExpiryDate(printJob.getExpiryDate());
				}
				
				if(StringUtil.isEmpty(queryForm.getCountByUnit())) {
					if(checkRate(scForm, funitMap,Constants.DEFAULT_UNIT_ID)){
						//将数量换算为件
						Double salesQuantity = changeUnit(scForm.getProductId(),scForm.getUnitId(),scForm.getSalesQuantity(),Constants.DEFAULT_UNIT_ID,funitMap);
						scForm.setSalesQuantity(salesQuantity);
						Double consumeQuantity = changeUnit(scForm.getProductId(),scForm.getUnitId(),scForm.getConsumeQuantity(),Constants.DEFAULT_UNIT_ID,funitMap);
						scForm.setConsumeQuantity(consumeQuantity);
						//单位默认显示为件
						scForm.setUnitId(Constants.DEFAULT_UNIT_ID);
					}
				} else {
					Product product = appProduct.loadProductById(scForm.getProductId());
					if(product != null) {
						//检查单位是否可以正常转化,如不能则不转化
						if(scForm.getUnitId() != product.getSunit() && checkRate(scForm, funitMap,product.getSunit())){
							Double salesQuantity = changeUnit(scForm.getProductId(),scForm.getUnitId(),scForm.getSalesQuantity(),product.getSunit(),funitMap);
							scForm.setSalesQuantity(salesQuantity);
							Double consumeQuantity = changeUnit(scForm.getProductId(),scForm.getUnitId(),scForm.getConsumeQuantity(),product.getSunit(),funitMap);
							scForm.setConsumeQuantity(consumeQuantity);
							scForm.setUnitId(product.getSunit());
						}
						if(product.getBoxquantity() != null && product.getBoxquantity() != 0d) {
							Double salesQuantity = ArithDouble.mul(scForm.getSalesQuantity(), product.getBoxquantity());
							scForm.setSalesQuantity(salesQuantity);
							Double consumeQuantity = ArithDouble.mul(scForm.getConsumeQuantity(), product.getBoxquantity());
							scForm.setConsumeQuantity(consumeQuantity);
							scForm.setUnitId(product.getCountunit());
						}
					}
				}
				scForm.setUnitName(countUnitMap.get(scForm.getUnitId()));
				
				if(sapDeliveryNos.containsKey(scForm.getBillNo())) {
					scForm.setNccode(sapDeliveryNos.get(scForm.getBillNo()));
				} else {
					StockAlterMove stockAlterMove = appStockAlterMove.getStockAlterMoveByID(scForm.getBillNo());
					if(stockAlterMove != null) {
						sapDeliveryNos.put(scForm.getBillNo(), stockAlterMove.getNccode());
						scForm.setNccode(stockAlterMove.getNccode());
					} else {
						sapDeliveryNos.put(scForm.getBillNo(), null);
						scForm.setNccode(null);
					}
				}
				resultList.add(scForm);
			}
		}
		return resultList;
	}

	public List<SalesConsumeDetailReportForm> queryConsumeDetailHistory(
			HttpServletRequest request, int pageSize,
			SalesConsumeDetailReportForm queryForm, UsersBean users) throws Exception { 
		StringBuffer sql = new StringBuffer();
		sql.append(" \r\n select (year || case when month < 10 THEN '0'||month ELSE to_char(month) END) yearmonth,sdh.billNo ");
		sql.append(" \r\n ,r.sortname regionName ");
		sql.append(" \r\n ,ino.province provinceId ");
		sql.append(" \r\n ,ino.oecode oecode ");
		sql.append(" \r\n ,ca.areaname province ");
		sql.append(" \r\n ,ino.id  organId ");
		sql.append(" \r\n ,ino.organname organName ");
		sql.append(" \r\n ,sdh.productid productId  ");
		sql.append(" \r\n ,pstr.sortname psName ");
		sql.append(" \r\n ,p.productname productName ");
		sql.append(" \r\n ,p.productnameen productNameen ");
		sql.append(" \r\n ,p.mcode mCode ");
		sql.append(" \r\n ,p.matericalchdes matericalChDes ");
		sql.append(" \r\n ,p.matericalendes matericalEnDes ");
		sql.append(" \r\n ,p.sunit unitId ");
		sql.append(" \r\n ,p.specmode packSizeName");
		sql.append(" \r\n ,p.packsizenameen packSizeNameEn");
		sql.append(" \r\n ,sdh.batch batch ");
		sql.append(" \r\n ,0 salesQuantity ");  // 销售数量
		sql.append(" \r\n ,sdh.consumeQuantity"); //消耗数量
		sql.append(" \r\n ,sdh.makeDate ");
		sql.append(" \r\n ,o.organname outOrganName,w.warehousename "); 
		sql.append(" \r\n from CONSUME_DETAIL_HISTORY sdh ");
		sql.append(" \r\n join organ o on o.id =sdh.outorganid and o.isrepeal=0 ");
		//日期条件
		if(!StringUtil.isEmpty(queryForm.getBeginDate())) {
			sql.append("\r\n and (year || case when month < 10 THEN '0'||month ELSE to_char(month) END) >= '"+(queryForm.getBeginDate()).substring(0,7).replace("-", "")+"'");
		}
		if(!StringUtil.isEmpty(queryForm.getEndDate())) {
			sql.append("\r\n and (year || case when month < 10 THEN '0'||month ELSE to_char(month) END) <= '"+(queryForm.getEndDate()).substring(0,7).replace("-", "")+"'");
		}
		if(!StringUtil.isEmpty(queryForm.getOrganId())){
			sql.append(" \r\n and o.id = '" + queryForm.getOrganId() + "' "); //机构条件
		} else if(DbUtil.isDealer(users)) {
			sql.append(" \r\n JOIN RULE_USER_WH rw on sdh.outwarehouseid = rw.warehouse_id  ");
			sql.append(" \r\n and rw.user_id = '" + users.getUserid() + "'");
		} else {
			sql.append(" \r\n and "+DbUtil.getWhereCondition(users, "o"));
		}
		sql.append(" \r\n join PRODUCT p on p.ID=sdh.productid and p.useflag=1 ");
		if(!StringUtil.isEmpty(queryForm.getProductName()) && !StringUtil.isEmpty(queryForm.getPackSizeName())) { //产品条件
			sql.append(" \r\n and p.productname = '"+queryForm.getProductName()+"' and p.specmode= '"+queryForm.getPackSizeName()+"' ");
		} else if(!StringUtil.isEmpty(queryForm.getProductName()) && StringUtil.isEmpty(queryForm.getPackSizeName())) { //产品条件
			sql.append(" \r\n and p.productname = '"+queryForm.getProductName()+"' ");
		}
		sql.append(" \r\n join PRODUCT_STRUCT pstr on pstr.structcode=p.psid  ");
		sql.append(" \r\n join WAREHOUSE w on w.id=sdh.outwarehouseid and w.useflag=1 ");
		if(!StringUtil.isEmpty(queryForm.getWarehouseId())){
			sql.append(" \r\n and w.id = '" + queryForm.getWarehouseId() + "' "); //仓库条件
		}
		sql.append(" \r\n join organ ino on ino.id =sdh.organid and o.isrepeal=0 ");
		sql.append(" \r\n left join COUNTRY_AREA ca on ca.id = ino.province ");
		sql.append(" \r\n LEFT join REGION_AREA ra on RA.AREAID=ino.province ");
		sql.append(" \r\n LEFT JOIN REGION r on r.regioncode = ra.regioncodeid");
		//大区条件
		if(!StringUtil.isEmpty(queryForm.getRegion())){
			sql.append(" \r\n where r.regioncode = '" + queryForm.getRegion() + "' "); 
		}
		System.out.println(sql.toString());
		List<SalesConsumeDetailReportForm> resultList = new ArrayList<SalesConsumeDetailReportForm>();
		List<Map> list =  new ArrayList<Map>();
		if(request == null && pageSize == 0){
			list =  EntityManager.jdbcquery(sql.toString());
		}else {
			list =  PageQuery.jdbcSqlserverQuery(request,"yearmonth desc,billNo", sql.toString(), pageSize);
		}
		if(list != null && list.size()>0){
			Map<String,String> sapDeliveryNos = new HashMap<String, String>();
			Map<String,FUnit>  funitMap = appFUnit.getAllMap(); //获取所有的单位换算信息
			Map<Integer, String> countUnitMap = appBr.getBaseResourceMap("CountUnit");
			for(Map map : list){
				SalesConsumeDetailReportForm scForm = new SalesConsumeDetailReportForm();
				//将Map中对应的值赋值给实例
				MapUtil.mapToObject(map, scForm);
				//报告日期主当前日期
//				scForm.setReportDate(new Timestamp(System.currentTimeMillis()));
				//消耗日期
				if(scForm.getMakeDate() != null){
					scForm.setMakeDate(new Timestamp(scForm.getMakeDate().getTime()));
				}
				//获取生产日期与过期日期
				PrintJob printJob = appPrintJob.getPrintJobByBatAMc(scForm.getmCode(), scForm.getBatch());
				if(printJob != null) {
					scForm.setProduceDate(printJob.getProductionDate());
					scForm.setExpiryDate(printJob.getExpiryDate());
				}
				if(StringUtil.isEmpty(queryForm.getCountByUnit())) {
					if(checkRate(scForm, funitMap,Constants.DEFAULT_UNIT_ID)){
						//将数量换算为件
						Double salesQuantity = changeUnit(scForm.getProductId(),scForm.getUnitId(),scForm.getSalesQuantity(),Constants.DEFAULT_UNIT_ID,funitMap);
						scForm.setSalesQuantity(salesQuantity);
						Double consumeQuantity = changeUnit(scForm.getProductId(),scForm.getUnitId(),scForm.getConsumeQuantity(),Constants.DEFAULT_UNIT_ID,funitMap);
						scForm.setConsumeQuantity(consumeQuantity);
						//单位默认显示为件
						scForm.setUnitId(Constants.DEFAULT_UNIT_ID);
					}
				} else {
					Product product = appProduct.loadProductById(scForm.getProductId());
					if(product != null) {
						//检查单位是否可以正常转化,如不能则不转化
						if(scForm.getUnitId() != product.getSunit() && checkRate(scForm, funitMap,product.getSunit())){
							//将数量换算为件
							Double salesQuantity = changeUnit(scForm.getProductId(),scForm.getUnitId(),scForm.getSalesQuantity(),product.getSunit(),funitMap);
							scForm.setSalesQuantity(salesQuantity);
							Double consumeQuantity = changeUnit(scForm.getProductId(),scForm.getUnitId(),scForm.getConsumeQuantity(),product.getSunit(),funitMap);
							scForm.setConsumeQuantity(consumeQuantity);
							scForm.setUnitId(product.getSunit());
						}
						
						if(product.getBoxquantity() != null && product.getBoxquantity() != 0d) {
							Double salesQuantity = ArithDouble.mul(scForm.getSalesQuantity(), product.getBoxquantity());
							scForm.setSalesQuantity(salesQuantity);
							Double consumeQuantity = ArithDouble.mul(scForm.getConsumeQuantity(), product.getBoxquantity());
							scForm.setConsumeQuantity(consumeQuantity);
							scForm.setUnitId(product.getCountunit());
						}
					}
				}
				scForm.setUnitName(countUnitMap.get(scForm.getUnitId()));
				resultList.add(scForm);
			}
		}
		return resultList;
	}

	public List<InventoryDetailReport> queryInventoryDetailHistory(HttpServletRequest request,
			int pageSize, Map<String,String> paraMap, UsersBean users) throws Exception { 
		StringBuffer sql = new StringBuffer();
		sql.append("select (year || case when month < 10 THEN '0'||month ELSE to_char(month) END) yearmonth,r.sortname regionName,ca.areaname,o.id organId,o.organname,o.oecode,w.id wid,w.warehousename,p.id proid,p.mcode,p.productName ,p.productNameen ,p.matericalchdes ,p.matericalendes ,p.Specmode PackSizeName,p.PackSizeNameEn,p.sunit,idh.stockpile,idh.stockpileToShip,idh.stockpileInTransit stockInTransit,ps.sortname,p.boxquantity,p.countunit,idh.productid,idh.batch ");
		sql.append(" from INVENTORY_DETAIL_HISTORY idh ");
		
		sql.append("join organ o on o.id = idh.organid and o.ISREPEAL = 0 ");
//		sql.append("and idh.year="+endYear+" and idh.month="+endMonth+" ");
		//日期条件
		if(!StringUtil.isEmpty(paraMap.get("beginDate"))) {
			sql.append("\r\n and (year || case when month < 10 THEN '0'||month ELSE to_char(month) END) >= '"+(paraMap.get("beginDate")).substring(0,7).replace("-", "")+"'");
		}
		if(!StringUtil.isEmpty(paraMap.get("endDate"))) {
			sql.append("\r\n and (year || case when month < 10 THEN '0'||month ELSE to_char(month) END) <= '"+(paraMap.get("endDate")).substring(0,7).replace("-", "")+"'");
		}
		if(!StringUtil.isEmpty(paraMap.get("inOrganId"))){
			sql.append(" \r\n and idh.ORGANID = '" + paraMap.get("inOrganId") + "' "); //机构条件
		}
		if(!StringUtil.isEmpty(paraMap.get("inWarehouseId"))){
			sql.append(" \r\n and idh.WAREHOUSEID = '" + paraMap.get("inWarehouseId") + "' "); //仓库条件
		}
		if(DbUtil.isDealer(users)) {
			sql.append(" and idh.warehouseid in (select wv.warehouse_Id from RULE_USER_WH wv where wv.user_Id="+users.getUserid()+")");
		} else {
			sql.append(" and "+DbUtil.getWhereCondition(users, "o"));
		}
		
		sql.append("join WAREHOUSE w on w.id = idh.warehouseid and w.USEFLAG = 1 ");
		sql.append("join PRODUCT p on p.id =idh.productid and p.USEFLAG=1 ");
		if(!StringUtil.isEmpty((String)paraMap.get("ProductName")) && !StringUtil.isEmpty((String)paraMap.get("packSizeName"))) { //产品条件
			sql.append(" \r\n and p.productname = '"+(String)paraMap.get("ProductName")+"' and p.specmode= '"+(String)paraMap.get("packSizeName")+"' ");
		} else if(!StringUtil.isEmpty((String)paraMap.get("ProductName")) && StringUtil.isEmpty((String)paraMap.get("packSizeName"))) { //产品条件
			sql.append(" \r\n and p.productname = '"+(String)paraMap.get("ProductName")+"' ");
		}
		sql.append("join PRODUCT_STRUCT ps on ps.structcode=p.psid ");
		sql.append("left join COUNTRY_AREA ca on ca.id = o.province ");
		sql.append("LEFT join REGION_AREA ra on RA.AREAID=o.province ");
		sql.append("LEFT JOIN REGION r on r.regioncode = ra.regioncodeid ");
		sql.append("where  (stockpile <> 0 or idh.stockpileInTransit <> 0 or stockpiletoship <> 0) ");
		if(!StringUtil.isEmpty((String)paraMap.get("region"))) {
			sql.append(" \r\n and ra.regioncodeid =  '" + paraMap.get("region") + "' "); //大区条件 
		}
		System.out.println(sql.toString()); 
		List<Map> list =  new ArrayList<Map>();
		List<InventoryDetailReport> resultList = new ArrayList<InventoryDetailReport>();
		if(request == null && pageSize == 0){
			list =  EntityManager.jdbcquery(sql.toString());
		}else {
			list =  PageQuery.jdbcSqlserverQuery(request, "yearmonth desc,organid,productid", sql.toString(), pageSize);
		}
		if(list != null && list.size()>0){
			Map<String,FUnit>  funitMap = appFUnit.getAllMap(); //获取所有的单位换算信息
			Map<Integer, String> countUnitMap = appBr.getBaseResourceMap("CountUnit");
			for(Map map : list){
				InventoryDetailReport report = new InventoryDetailReport();
				//将Map中对应的值赋值给实例
				MapUtil.mapToObject(map, report);
//				report.setViewDate(((String)paraMap.get("EndDate")).substring(0, 10));
				
				//获取生产日期与过期日期
				PrintJob printJob = appPrintJob.getPrintJobByBatAMc(report.getmCode(), report.getBatch());
				if(printJob != null) {
					report.setProductionDate(printJob.getProductionDate());
					report.setExpiryDate(printJob.getExpiryDate());
				}
				
				double stockpile = Double.valueOf((String)map.get("stockpile"));
				double stockInTransit = Double.valueOf((String)map.get("stockintransit"));
				double stockToShip = Double.valueOf((String)map.get("stockpiletoship"));
				double boxquantity = Double.valueOf((String)map.get("boxquantity"));
				Integer countunit = Integer.valueOf((String)map.get("countunit"));
				Integer sunit = Integer.valueOf((String)map.get("sunit"));
				if(StringUtil.isEmpty((String)paraMap.get("countByUnit"))) {
					//检查单位是否可以正常转化,如不能则不转化
					if(checkRate(report.getProductId(),sunit,Constants.DEFAULT_UNIT_ID, funitMap)){
						stockpile = changeUnit(report.getProductId(), sunit, stockpile, Constants.DEFAULT_UNIT_ID,funitMap);
						report.setStockpile(decimalFormat.format(stockpile));
						stockInTransit = changeUnit(report.getProductId(), sunit, stockInTransit, Constants.DEFAULT_UNIT_ID,funitMap);
						report.setStockInTransit(decimalFormat.format(stockInTransit));
						stockToShip = changeUnit(report.getProductId(), sunit, stockToShip, Constants.DEFAULT_UNIT_ID,funitMap);
						report.setStockpileToShip(decimalFormat.format(stockToShip));
						report.setTotalStockpile(decimalFormat.format(ArithDouble.add(ArithDouble.add(stockpile, stockInTransit), stockToShip)));
						report.setUnit(countUnitMap.get(Constants.DEFAULT_UNIT_ID));
					}
				} else {
					//转换成计量单位
					if(boxquantity != 0d) {
						stockpile = ArithDouble.mul(stockpile, boxquantity);
						report.setStockpile(decimalFormat.format(stockpile));
						stockInTransit = ArithDouble.mul(stockInTransit, boxquantity);
						report.setStockInTransit(decimalFormat.format(stockInTransit));
						stockToShip = ArithDouble.mul(stockToShip, boxquantity);
						report.setStockpileToShip(decimalFormat.format(stockToShip));
						report.setTotalStockpile(decimalFormat.format(ArithDouble.add(ArithDouble.add(stockpile, stockInTransit), stockToShip)));
						report.setUnit(countUnitMap.get(countunit));
					}
				}
				resultList.add(report);
			}
		}
		return resultList;
		
	}

	
}
