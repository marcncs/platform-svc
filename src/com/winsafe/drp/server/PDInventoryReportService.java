package com.winsafe.drp.server;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap; 
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import com.winsafe.drp.dao.AppBaseResource;
import com.winsafe.drp.dao.BaseResource;
import com.winsafe.drp.dao.FUnit;
import com.winsafe.drp.dao.PDInventoryReport;
import com.winsafe.drp.dao.Product;
import com.winsafe.drp.dao.ProductStruct;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.util.ArithDouble;
import com.winsafe.drp.util.Constants;
import com.winsafe.drp.util.Dateutil;
import com.winsafe.drp.util.MapUtil;
import com.winsafe.hbm.util.DbUtil;  
import com.winsafe.hbm.util.StringUtil;
import com.winsafe.hbm.util.pager2.PageQuery;

public class PDInventoryReportService extends ReportServices{
	private WarehouseService warehouseService = new WarehouseService();
	private AppBaseResource appBr = new AppBaseResource();
	
	
	// 根据条件查询
	public List<PDInventoryReport> queryReport2(HttpServletRequest request, int pageSize,Map paraMap,UsersBean users) throws Exception{
		Map<String, Object> param = new LinkedHashMap<String, Object>();
		boolean hasHistoryData = false;
		boolean isBefore = false;
		if(!StringUtil.isEmpty((String)paraMap.get("EndDate"))){
			String endDateStr = (String)paraMap.get("EndDate") + " 23:59:59";
			paraMap.put("EndDate", endDateStr);
			
//			Date historeyDate = appInventoryReport.getEarlierHistoryInventoryDate(endDateStr);
			Date historeyDate = null;
			if(historeyDate == null) {
//				historeyDate = appInventoryReport.getLaterHistoryInventoryDate(endDateStr);
				if(historeyDate != null) {
					hasHistoryData = true;
					paraMap.put("historeyDate", Dateutil.formatDate(historeyDate, "yyyy-MM-dd HH:mm:ss"));
				} else {
					Date endDate = Dateutil.formatDatetime(endDateStr);
					Date currentDate = Dateutil.getCurrentDate();
					if(endDate.compareTo(currentDate) > 0) {
						isBefore = true;
					}
					paraMap.put("historeyDate", Dateutil.formatDate(Dateutil.getCurrentDate(), "yyyy-MM-dd HH:mm:ss"));
				}
			} else {
				hasHistoryData = true;
				isBefore = true;
				paraMap.put("historeyDate", Dateutil.formatDate(historeyDate, "yyyy-MM-dd HH:mm:ss"));
			}
			
		}
		
		List<PDInventoryReport> resultList = new ArrayList<PDInventoryReport>();
		
		String inventoryTable = "PRODUCT_STOCKPILE_ALL";
		if(hasHistoryData) {
			inventoryTable = "PRODUCT_STOCKPILE_HISTORY";
		}
		StringBuffer inventorySql = new StringBuffer();
		inventorySql.append(" \r\n select o.id as organid,o.organname,o.oecode, o.province,ps.productid, ps.stockpile as stockpile, 0 as stockintransit,wa.warehousename,wa.id as warehousid,0 as stockpiletoship  from ").append(inventoryTable +  " ps ");
		inventorySql.append(" \r\n join WAREHOUSE wa on PS.WAREHOUSEID = wa.id");
		if(DbUtil.isDealer(users)) {
			inventorySql.append(" \r\n and ps.warehouseid in (select wv.warehouse_Id from RULE_USER_WH wv where wv.user_Id=?)");
			param.put(UUID.randomUUID().toString(), users.getUserid());
		}
		if(!StringUtil.isEmpty((String)paraMap.get("inWarehouseId"))){
			inventorySql.append(" \r\n and wa.id = ? "); //仓库条件 
			param.put(UUID.randomUUID().toString(), paraMap.get("inWarehouseId"));
		}
		if(!StringUtil.isEmpty((String)paraMap.get("ProductName")) && !StringUtil.isEmpty((String)paraMap.get("packSizeName"))) { //产品条件
			inventorySql.append(" \r\n and ps.productid in (select id from product where productname = ? and specmode= ?) ");
			param.put(UUID.randomUUID().toString(), paraMap.get("ProductName"));
			param.put(UUID.randomUUID().toString(), paraMap.get("packSizeName"));
		} else if(!StringUtil.isEmpty((String)paraMap.get("ProductName")) && StringUtil.isEmpty((String)paraMap.get("packSizeName"))) { //产品条件
			inventorySql.append(" \r\n and ps.productid in (select id from product where productname = ?) ");
			param.put(UUID.randomUUID().toString(), paraMap.get("ProductName"));
		}
		inventorySql.append(" \r\n join ORGAN o on o.id = wa.makeorganid");
		if(!DbUtil.isDealer(users)) {
			inventorySql.append(" \r\n and "+DbUtil.getWhereCondition(users, "o"));
		}
		if(sysPro != null &&"1".equals(sysPro.getProperty("genReportOnlyForPD"))){ //是否只统计PD
			inventorySql.append(" \r\n and o.organtype = 2 and o.organmodel = 1"); 
		}
		if(!StringUtil.isEmpty((String)paraMap.get("inOrganId"))){
			inventorySql.append(" \r\n and o.id = ? "); //机构条件
			param.put(UUID.randomUUID().toString(), paraMap.get("inOrganId"));
		}
		if(!StringUtil.isEmpty((String)paraMap.get("region"))) {
			inventorySql.append(" \r\n and o.province is not null and o.province in (select areaid from region_area where regioncodeid =  ?)"); //机构条件 
			param.put(UUID.randomUUID().toString(), paraMap.get("region"));
		}
		if(hasHistoryData) {
			inventorySql.append(" \r\n where makedate = to_date(?,'yyyy-MM-dd hh24:mi:ss') ");
			param.put(UUID.randomUUID().toString(), paraMap.get("historeyDate"));
		}
		
		StringBuffer sql = new StringBuffer();
		sql.append(" \r\n select organid,organname,oecode,province,productid,stockpile, stockintransit, warehousename, warehousid,stockpiletoship from (");
		sql.append(" \r\n select organid,max(organname) as organname,max(oecode) as oecode,max(province) as province,productid,sum(stockpile) as stockpile,sum(stockintransit) as stockintransit,max(warehousename) as warehousename,warehousid,sum(stockpiletoship) as stockpiletoship  from (");
		//历史库存
		sql.append(inventorySql.toString());
		if(isBefore) {
			sql.append(" \r\n UNION ALL");
			//加上拒签的发货单
			sql.append(" \r\n select o.id as organid,o.organname,o.oecode,o.province,samd.productid,(samd.takequantity * f.xquantity) as stockpile,0 as stockintransit,'' as warehousename,sam.OUTWAREHOUSEID as warehouseid, 0 as stockpiletoship  from STOCK_ALTER_MOVE sam ");
			sql.append(" \r\n join ORGAN o on o.id = sam.outorganid and sam.isblankout = 1");
			if(!DbUtil.isDealer(users)) {
				sql.append(" \r\n and "+DbUtil.getWhereCondition(users, "o"));
			}
			sql.append(" \r\n and blankoutdate > to_date(?,'yyyy-MM-dd hh24:mi:ss') ");
			param.put(UUID.randomUUID().toString(), paraMap.get("historeyDate"));
			sql.append(" \r\n and shipmentdate < to_date(?,'yyyy-MM-dd hh24:mi:ss') ");
			param.put(UUID.randomUUID().toString(), paraMap.get("EndDate"));
			if(sysPro != null &&"1".equals(sysPro.getProperty("genReportOnlyForPD"))){ //是否只统计PD
				sql.append(" \r\n and o.organtype = 2 and o.organmodel = 1"); 
			}
			if(!StringUtil.isEmpty((String)paraMap.get("inOrganId"))){
				sql.append(" \r\n and sam.outorganid = ? "); //机构条件
				param.put(UUID.randomUUID().toString(), paraMap.get("inOrganId"));
			}
			if(!StringUtil.isEmpty((String)paraMap.get("region"))) {
				sql.append(" \r\n and o.province is not null and o.province in (select areaid from region_area where regioncodeid =  ?)"); //机构条件 
				param.put(UUID.randomUUID().toString(), paraMap.get("region"));
			}
			if(DbUtil.isDealer(users)) {
				sql.append(" \r\n  and sam.OUTWAREHOUSEID in (select wv.warehouse_Id from RULE_USER_WH wv where wv.user_Id=?)");
				param.put(UUID.randomUUID().toString(), users.getUserid());
			}
			if(!StringUtil.isEmpty((String)paraMap.get("inWarehouseId"))){
				sql.append(" \r\n and sam.OUTWAREHOUSEID = ? "); //仓库条件
				param.put(UUID.randomUUID().toString(), paraMap.get("inWarehouseId"));
			}
			sql.append(" \r\n join STOCK_ALTER_MOVE_DETAIL samd on sam.id = samd.samid ");
			if(!StringUtil.isEmpty((String)paraMap.get("ProductName")) && !StringUtil.isEmpty((String)paraMap.get("packSizeName"))) { //产品条件
				sql.append(" \r\n and samd.productid in (select id from product where productname = ? and specmode= ?) ");
				param.put(UUID.randomUUID().toString(), paraMap.get("ProductName"));
				param.put(UUID.randomUUID().toString(), paraMap.get("packSizeName"));
			} else if(!StringUtil.isEmpty((String)paraMap.get("ProductName")) && StringUtil.isEmpty((String)paraMap.get("packSizeName"))) { //产品条件
				sql.append(" \r\n and samd.productid in (select id from product where productname = ?) ");
				param.put(UUID.randomUUID().toString(), paraMap.get("ProductName"));
			}
			sql.append(" \r\n LEFT JOIN F_UNIT f on samd.productid = f.productid and samd.unitid = f.funitid  ");
			sql.append(" \r\n UNION ALL");
			//--发货单(入库)
			sql.append(" \r\n select o.id as organid,o.organname,o.oecode,o.province,samd.productid,(samd.receivequantity * f.xquantity) as stockpile,0 as stockintransit,'' as warehousename,sam.INWAREHOUSEID as warehouseid, 0 as stockpiletoship  from STOCK_ALTER_MOVE sam ");
			sql.append(" \r\n join ORGAN o on o.id = sam.receiveorganid and sam.iscomplete = 1");
			if(!DbUtil.isDealer(users)) {
				sql.append(" \r\n and "+DbUtil.getWhereCondition(users, "o"));
			}
			sql.append(" \r\n and sam.receivedate >  to_date(?,'yyyy-MM-dd hh24:mi:ss')");
			param.put(UUID.randomUUID().toString(), paraMap.get("historeyDate"));
			sql.append(" \r\n and sam.movedate < to_date(?,'yyyy-MM-dd hh24:mi:ss')");
			param.put(UUID.randomUUID().toString(), paraMap.get("EndDate"));
			if(sysPro != null &&"1".equals(sysPro.getProperty("genReportOnlyForPD"))){ //是否只统计PD
				sql.append(" \r\n and o.organtype = 2 and o.organmodel = 1"); 
			}
			if(!StringUtil.isEmpty((String)paraMap.get("inOrganId"))){
				sql.append(" \r\n and sam.receiveorganid = ? "); //机构条件
				param.put(UUID.randomUUID().toString(), paraMap.get("inOrganId"));
			}
			if(!StringUtil.isEmpty((String)paraMap.get("region"))) {
				sql.append(" \r\n and o.province is not null and o.province in (select areaid from region_area where regioncodeid =  ?)"); //机构条件 
				param.put(UUID.randomUUID().toString(), paraMap.get("region"));
			}
			if(DbUtil.isDealer(users)) {
				sql.append(" \r\n  and sam.INWAREHOUSEID in (select wv.warehouse_Id from RULE_USER_WH wv where wv.user_Id=?) ");
				param.put(UUID.randomUUID().toString(), users.getUserid());
			}
			if(!StringUtil.isEmpty((String)paraMap.get("inWarehouseId"))){
				sql.append(" \r\n and sam.INWAREHOUSEID = ? "); //仓库条件
				param.put(UUID.randomUUID().toString(), paraMap.get("inWarehouseId"));
			}
			sql.append(" \r\n join STOCK_ALTER_MOVE_DETAIL samd on sam.id = samd.samid ");
			if(!StringUtil.isEmpty((String)paraMap.get("ProductName")) && !StringUtil.isEmpty((String)paraMap.get("packSizeName"))) { //产品条件
				sql.append(" \r\n and samd.productid in (select id from product where productname = ? and specmode= ?) ");
				param.put(UUID.randomUUID().toString(), paraMap.get("ProductName"));
				param.put(UUID.randomUUID().toString(), paraMap.get("packSizeName"));
			} else if(!StringUtil.isEmpty((String)paraMap.get("ProductName")) && StringUtil.isEmpty((String)paraMap.get("packSizeName"))) { //产品条件
				sql.append(" \r\n and samd.productid in (select id from product where productname = ?) ");
				param.put(UUID.randomUUID().toString(), paraMap.get("ProductName"));
			}
			sql.append(" \r\n  LEFT JOIN F_UNIT f on samd.productid = f.productid and samd.unitid = f.funitid  ");
			
			sql.append(" \r\n UNION ALL");
			//--发货单(出库)
			sql.append(" \r\n select o.id as organid,o.organname,o.oecode,o.province,samd.productid,-(samd.takequantity * f.xquantity) as stockpile,0 as stockintransit,'' as warehousename,sam.OUTWAREHOUSEID as warehouseid, 0 as stockpiletoship  from STOCK_ALTER_MOVE sam ");
			sql.append(" \r\n join ORGAN o on o.id = sam.outorganid and sam.isshipment = 1");
			if(!DbUtil.isDealer(users)) {
				sql.append(" \r\n and "+DbUtil.getWhereCondition(users, "o"));
			}
			sql.append(" \r\n and sam.shipmentdate >  to_date(?,'yyyy-MM-dd hh24:mi:ss')");
			param.put(UUID.randomUUID().toString(), paraMap.get("historeyDate"));
			sql.append(" \r\n and sam.movedate < to_date(?,'yyyy-MM-dd hh24:mi:ss')");
			param.put(UUID.randomUUID().toString(), paraMap.get("EndDate"));
			if(sysPro != null &&"1".equals(sysPro.getProperty("genReportOnlyForPD"))){ //是否只统计PD
				sql.append(" \r\n and o.organtype = 2 and o.organmodel = 1"); 
			}
			if(!StringUtil.isEmpty((String)paraMap.get("inOrganId"))){
				sql.append(" \r\n and sam.outorganid = ? "); //机构条件
				param.put(UUID.randomUUID().toString(), paraMap.get("inOrganId"));
			}
			if(!StringUtil.isEmpty((String)paraMap.get("region"))) {
				sql.append(" \r\n and o.province is not null and o.province in (select areaid from region_area where regioncodeid =  ?)"); //机构条件 
				param.put(UUID.randomUUID().toString(), paraMap.get("region"));
			}
			if(DbUtil.isDealer(users)) {
				sql.append(" \r\n  and sam.OUTWAREHOUSEID in (select wv.warehouse_Id from RULE_USER_WH wv where wv.user_Id=?) ");
				param.put(UUID.randomUUID().toString(), users.getUserid());
			}
			if(!StringUtil.isEmpty((String)paraMap.get("inWarehouseId"))){
				sql.append(" \r\n and sam.OUTWAREHOUSEID = ? "); //仓库条件
				param.put(UUID.randomUUID().toString(), paraMap.get("inWarehouseId"));
			}
			sql.append(" \r\n join STOCK_ALTER_MOVE_DETAIL samd on sam.id = samd.samid ");
			if(!StringUtil.isEmpty((String)paraMap.get("ProductName")) && !StringUtil.isEmpty((String)paraMap.get("packSizeName"))) { //产品条件
				sql.append(" \r\n and samd.productid in (select id from product where productname = ? and specmode= ?) ");
				param.put(UUID.randomUUID().toString(), paraMap.get("ProductName"));
				param.put(UUID.randomUUID().toString(), paraMap.get("packSizeName"));
			} else if(!StringUtil.isEmpty((String)paraMap.get("ProductName")) && StringUtil.isEmpty((String)paraMap.get("packSizeName"))) { //产品条件
				sql.append(" \r\n and samd.productid in (select id from product where productname = ?) ");
				param.put(UUID.randomUUID().toString(), paraMap.get("ProductName"));
			}
			sql.append(" \r\n  LEFT JOIN F_UNIT f on samd.productid = f.productid and samd.unitid = f.funitid  ");
			
			
			sql.append(" \r\n UNION ALL");
			// --转仓单(入库)
			sql.append(" \r\n select o.id as organid,o.organname,o.oecode,o.province,smd.productid,(smd.takequantity * f.xquantity) as stockpile,0 as stockintransit,'' as warehousename,sm.INWAREHOUSEID as warehouseid, 0 as stockpiletoship  from STOCK_MOVE sm ");
			sql.append(" \r\n join ORGAN o on o.id = sm.inorganid and sm.iscomplete = 1");
			if(!DbUtil.isDealer(users)) {
				sql.append(" \r\n and "+DbUtil.getWhereCondition(users, "o"));
			}
			sql.append(" \r\n and sm.receivedate >  to_date(?,'yyyy-MM-dd hh24:mi:ss')");
			param.put(UUID.randomUUID().toString(), paraMap.get("historeyDate"));
			sql.append(" \r\n and sm.movedate < to_date(?,'yyyy-MM-dd hh24:mi:ss')");
			param.put(UUID.randomUUID().toString(), paraMap.get("EndDate"));
			if(sysPro != null &&"1".equals(sysPro.getProperty("genReportOnlyForPD"))){ //是否只统计PD
				sql.append(" \r\n and o.organtype = 2 and o.organmodel = 1"); 
			}
			if(!StringUtil.isEmpty((String)paraMap.get("inOrganId"))){
				sql.append(" \r\n and sm.inorganid = ? "); //机构条件
				param.put(UUID.randomUUID().toString(), paraMap.get("inOrganId"));
			}
			if(!StringUtil.isEmpty((String)paraMap.get("region"))) {
				sql.append(" \r\n and o.province is not null and o.province in (select areaid from region_area where regioncodeid =  ?)"); //机构条件 
				param.put(UUID.randomUUID().toString(), paraMap.get("region"));
			}
			if(DbUtil.isDealer(users)) {
				sql.append(" \r\n  and sm.INWAREHOUSEID in (select wv.warehouse_Id from RULE_USER_WH wv where wv.user_Id=?) ");
				param.put(UUID.randomUUID().toString(), users.getUserid());
			}
			if(!StringUtil.isEmpty((String)paraMap.get("inWarehouseId"))){
				sql.append(" \r\n and sm.INWAREHOUSEID = ? "); //仓库条件
				param.put(UUID.randomUUID().toString(), paraMap.get("inWarehouseId"));
			}
			sql.append(" \r\n join STOCK_MOVE_DETAIL smd on sm.id = smd.smid  ");
			if(!StringUtil.isEmpty((String)paraMap.get("ProductName")) && !StringUtil.isEmpty((String)paraMap.get("packSizeName"))) { //产品条件
				sql.append(" \r\n and smd.productid in (select id from product where productname = ? and specmode= ?) ");
				param.put(UUID.randomUUID().toString(), paraMap.get("ProductName"));
				param.put(UUID.randomUUID().toString(), paraMap.get("packSizeName"));
			} else if(!StringUtil.isEmpty((String)paraMap.get("ProductName")) && StringUtil.isEmpty((String)paraMap.get("packSizeName"))) { //产品条件
				sql.append(" \r\n and smd.productid in (select id from product where productname = ?) ");
				param.put(UUID.randomUUID().toString(), paraMap.get("ProductName"));
			}
			sql.append(" \r\n  LEFT JOIN F_UNIT f on smd.productid = f.productid and smd.unitid = f.funitid  ");
			
			sql.append(" \r\n UNION ALL");
			//--转仓单(出库)
			sql.append(" \r\n select o.id as organid,o.organname,o.oecode,o.province,smd.productid,-(smd.takequantity * f.xquantity) as stockpile,0 as stockintransit,'' as warehousename,sm.OUTWAREHOUSEID as warehouseid, 0 as stockpiletoship  from STOCK_MOVE sm ");
			sql.append(" \r\n join ORGAN o on o.id = sm.outorganid and sm.isshipment = 1");
			if(!DbUtil.isDealer(users)) {
				sql.append(" \r\n and "+DbUtil.getWhereCondition(users, "o"));
			}
			sql.append(" \r\n and sm.shipmentdate >  to_date(?,'yyyy-MM-dd hh24:mi:ss')");
			param.put(UUID.randomUUID().toString(), paraMap.get("historeyDate"));
			sql.append(" \r\n and sm.movedate < to_date(?,'yyyy-MM-dd hh24:mi:ss')");
			param.put(UUID.randomUUID().toString(), paraMap.get("EndDate"));
			if(sysPro != null &&"1".equals(sysPro.getProperty("genReportOnlyForPD"))){ //是否只统计PD
				sql.append(" \r\n and o.organtype = 2 and o.organmodel = 1"); 
			}
			if(!StringUtil.isEmpty((String)paraMap.get("inOrganId"))){
				sql.append(" \r\n and sm.outorganid = ? "); //机构条件
				param.put(UUID.randomUUID().toString(), paraMap.get("inOrganId"));
			}
			if(!StringUtil.isEmpty((String)paraMap.get("region"))) {
				sql.append(" \r\n and o.province is not null and o.province in (select areaid from region_area where regioncodeid =  ?)"); //机构条件 
				param.put(UUID.randomUUID().toString(), paraMap.get("region"));
			}
			if(DbUtil.isDealer(users)) {
				sql.append(" \r\n  and sm.OUTWAREHOUSEID in (select wv.warehouse_Id from RULE_USER_WH wv where wv.user_Id=?) ");
				param.put(UUID.randomUUID().toString(), users.getUserid());
			}
			if(!StringUtil.isEmpty((String)paraMap.get("inWarehouseId"))){
				sql.append(" \r\n and sm.OUTWAREHOUSEID = ? "); //仓库条件
				param.put(UUID.randomUUID().toString(), paraMap.get("inWarehouseId"));
			}
			sql.append(" \r\n join STOCK_MOVE_DETAIL smd on sm.id = smd.smid  ");
			if(!StringUtil.isEmpty((String)paraMap.get("ProductName")) && !StringUtil.isEmpty((String)paraMap.get("packSizeName"))) { //产品条件
				sql.append(" \r\n and smd.productid in (select id from product where productname = ? and specmode= ?) ");
				param.put(UUID.randomUUID().toString(), paraMap.get("ProductName"));
				param.put(UUID.randomUUID().toString(), paraMap.get("packSizeName"));
			} else if(!StringUtil.isEmpty((String)paraMap.get("ProductName")) && StringUtil.isEmpty((String)paraMap.get("packSizeName"))) { //产品条件
				sql.append(" \r\n and smd.productid in (select id from product where productname = ?) ");
				param.put(UUID.randomUUID().toString(), paraMap.get("ProductName"));
			}
			sql.append(" \r\n  LEFT JOIN F_UNIT f on smd.productid = f.productid and smd.unitid = f.funitid  ");
			
			sql.append(" \r\n UNION ALL");
			//--退货单(入库)
			sql.append(" \r\n select o.id as organid,o.organname,o.oecode,o.province,owd.productid,(owd.takequantity * f.xquantity) as stockpile,0 as stockintransit,'' as warehousename,ow.INWAREHOUSEID as warehouseid, 0 as stockpiletoship from ORGAN_WITHDRAW ow  ");
			sql.append(" \r\n join ORGAN o on o.id = ow.receiveorganid and ow.iscomplete = 1");
			if(!DbUtil.isDealer(users)) {
				sql.append(" \r\n and "+DbUtil.getWhereCondition(users, "o"));
			}
			sql.append(" \r\n and ow.receivedate >  to_date(?,'yyyy-MM-dd hh24:mi:ss')");
			param.put(UUID.randomUUID().toString(), paraMap.get("historeyDate"));
			sql.append(" \r\n and ow.makedate < to_date(?,'yyyy-MM-dd hh24:mi:ss')");
			param.put(UUID.randomUUID().toString(), paraMap.get("EndDate"));
			if(sysPro != null &&"1".equals(sysPro.getProperty("genReportOnlyForPD"))){ //是否只统计PD
				sql.append(" \r\n and o.organtype = 2 and o.organmodel = 1"); 
			}
			if(!StringUtil.isEmpty((String)paraMap.get("inOrganId"))){
				sql.append(" \r\n and ow.receiveorganid = ? "); //机构条件
				param.put(UUID.randomUUID().toString(), paraMap.get("inOrganId"));
			}
			if(!StringUtil.isEmpty((String)paraMap.get("region"))) {
				sql.append(" \r\n and o.province is not null and o.province in (select areaid from region_area where regioncodeid =  ?)"); //机构条件 
				param.put(UUID.randomUUID().toString(), paraMap.get("region"));
			}
			if(DbUtil.isDealer(users)) {
				sql.append(" \r\n  and ow.INWAREHOUSEID in (select wv.warehouse_Id from RULE_USER_WH wv where wv.user_Id=?) ");
				param.put(UUID.randomUUID().toString(), users.getUserid());
			}
			if(!StringUtil.isEmpty((String)paraMap.get("inWarehouseId"))){
				sql.append(" \r\n and ow.INWAREHOUSEID = ? "); //仓库条件
				param.put(UUID.randomUUID().toString(), paraMap.get("inWarehouseId"));
			}
			sql.append(" \r\n join ORGAN_WITHDRAW_DETAIL owd on ow.id = owd.owid  ");
			if(!StringUtil.isEmpty((String)paraMap.get("ProductName")) && !StringUtil.isEmpty((String)paraMap.get("packSizeName"))) { //产品条件
				sql.append(" \r\n and owd.productid in (select id from product where productname = ? and specmode= ?) ");
				param.put(UUID.randomUUID().toString(), paraMap.get("ProductName"));
				param.put(UUID.randomUUID().toString(), paraMap.get("packSizeName"));
			} else if(!StringUtil.isEmpty((String)paraMap.get("ProductName")) && StringUtil.isEmpty((String)paraMap.get("packSizeName"))) { //产品条件
				sql.append(" \r\n and owd.productid in (select id from product where productname = ?) ");
				param.put(UUID.randomUUID().toString(), paraMap.get("ProductName"));
			}
			sql.append(" \r\n  LEFT JOIN F_UNIT f on owd.productid = f.productid and owd.unitid = f.funitid   ");
			sql.append(" \r\n UNION ALL");
			//--退货单(出库)
			sql.append(" \r\n select o.id as organid,o.organname,o.oecode,o.province,owd.productid,-(owd.takequantity * f.xquantity) as stockpile,0 as stockintransit,'' as warehousename,ow.WAREHOUSEID as warehouseid, 0 as stockpiletoship from ORGAN_WITHDRAW ow  ");
			sql.append(" \r\n join ORGAN o on o.id = ow.porganid and ow.takestatus = 1");
			if(!DbUtil.isDealer(users)) {
				sql.append(" \r\n and "+DbUtil.getWhereCondition(users, "o"));
			}
//			sql.append(" \r\n and ow.receivedate >  to_date('" + paraMap.get("historeyDate") + "','yyyy-MM-dd hh24:mi:ss')");
			sql.append(" \r\n and ow.makedate < to_date(?,'yyyy-MM-dd hh24:mi:ss')");
			param.put(UUID.randomUUID().toString(), paraMap.get("EndDate"));
			if(sysPro != null &&"1".equals(sysPro.getProperty("genReportOnlyForPD"))){ //是否只统计PD
				sql.append(" \r\n and o.organtype = 2 and o.organmodel = 1"); 
			}
			if(!StringUtil.isEmpty((String)paraMap.get("inOrganId"))){
				sql.append(" \r\n and ow.porganid = ? "); //机构条件
				param.put(UUID.randomUUID().toString(), paraMap.get("inOrganId"));
			}
			if(!StringUtil.isEmpty((String)paraMap.get("region"))) {
				sql.append(" \r\n and o.province is not null and o.province in (select areaid from region_area where regioncodeid =  ?)"); //机构条件 
				param.put(UUID.randomUUID().toString(), paraMap.get("region"));
			}
			if(DbUtil.isDealer(users)) {
				sql.append(" \r\n  and ow.WAREHOUSEID in (select wv.warehouse_Id from RULE_USER_WH wv where wv.user_Id=?) ");
				param.put(UUID.randomUUID().toString(), users.getUserid());
			}
			if(!StringUtil.isEmpty((String)paraMap.get("inWarehouseId"))){
				sql.append(" \r\n and ow.WAREHOUSEID = ? "); //仓库条件
				param.put(UUID.randomUUID().toString(), paraMap.get("inWarehouseId"));
			}
			sql.append(" \r\n join TAKE_TICKET tt on tt.billno = ow.id ");
			sql.append(" \r\n and tt.auditdate >  to_date(?,'yyyy-MM-dd hh24:mi:ss')");
			param.put(UUID.randomUUID().toString(), paraMap.get("historeyDate"));
			sql.append(" \r\n join ORGAN_WITHDRAW_DETAIL owd on ow.id = owd.owid  ");
			if(!StringUtil.isEmpty((String)paraMap.get("ProductName")) && !StringUtil.isEmpty((String)paraMap.get("packSizeName"))) { //产品条件
				sql.append(" \r\n and owd.productid in (select id from product where productname = ? and specmode= ?) ");
				param.put(UUID.randomUUID().toString(), paraMap.get("ProductName"));
				param.put(UUID.randomUUID().toString(), paraMap.get("packSizeName"));
			} else if(!StringUtil.isEmpty((String)paraMap.get("ProductName")) && StringUtil.isEmpty((String)paraMap.get("packSizeName"))) { //产品条件
				sql.append(" \r\n and owd.productid in (select id from product where productname = ?) ");
				param.put(UUID.randomUUID().toString(), paraMap.get("ProductName"));
			}
			sql.append(" \r\n  LEFT JOIN F_UNIT f on owd.productid = f.productid and owd.unitid = f.funitid   ");
			sql.append(" \r\n UNION ALL");
			//-- 未签收的单据（在途库存）
			// --发货单
			sql.append(" \r\n select o.id as organid,o.organname,o.oecode,o.province,samd.productid,0 as stockpile,(samd.takequantity * f.xquantity) as stockintransit,'' as warehousename,sam.INWAREHOUSEID as warehouseid, 0 as stockpiletoship  from STOCK_ALTER_MOVE sam ");
			sql.append(" \r\n join ORGAN o on o.id = sam.receiveorganid and sam.isshipment = 1 and sam.iscomplete = 0 and sam.isblankout = 0 ");
			if(!DbUtil.isDealer(users)) {
				sql.append(" \r\n and "+DbUtil.getWhereCondition(users, "o"));
			}
//			sql.append(" \r\n and sam.shipmentdate >  to_date('" + paraMap.get("historeyDate") + "','yyyy-MM-dd hh24:mi:ss')");
			sql.append(" \r\n and sam.movedate < to_date(?,'yyyy-MM-dd hh24:mi:ss')");
			param.put(UUID.randomUUID().toString(), paraMap.get("EndDate"));
			if(sysPro != null &&"1".equals(sysPro.getProperty("genReportOnlyForPD"))){ //是否只统计PD
				sql.append(" \r\n and o.organtype = 2 and o.organmodel = 1"); 
			}
			if(!StringUtil.isEmpty((String)paraMap.get("inOrganId"))){
				sql.append(" \r\n and sam.receiveorganid = ? "); //机构条件
				param.put(UUID.randomUUID().toString(), paraMap.get("inOrganId"));
			}
			if(!StringUtil.isEmpty((String)paraMap.get("region"))) {
				sql.append(" \r\n and o.province is not null and o.province in (select areaid from region_area where regioncodeid =  ?)"); //机构条件 
				param.put(UUID.randomUUID().toString(), paraMap.get("region"));
			}
			if(DbUtil.isDealer(users)) {
				sql.append(" \r\n  and sam.INWAREHOUSEID in (select wv.warehouse_Id from RULE_USER_WH wv where wv.user_Id=?) ");
				param.put(UUID.randomUUID().toString(), users.getUserid());
			}
			if(!StringUtil.isEmpty((String)paraMap.get("inWarehouseId"))){
				sql.append(" \r\n and sam.INWAREHOUSEID = ? "); //仓库条件
				param.put(UUID.randomUUID().toString(), paraMap.get("inWarehouseId"));
			}
			sql.append(" \r\n join STOCK_ALTER_MOVE_DETAIL samd on sam.id = samd.samid ");
			if(!StringUtil.isEmpty((String)paraMap.get("ProductName")) && !StringUtil.isEmpty((String)paraMap.get("packSizeName"))) { //产品条件
				sql.append(" \r\n and samd.productid in (select id from product where productname = ? and specmode= ?) ");
				param.put(UUID.randomUUID().toString(), paraMap.get("ProductName"));
				param.put(UUID.randomUUID().toString(), paraMap.get("packSizeName"));
			} else if(!StringUtil.isEmpty((String)paraMap.get("ProductName")) && StringUtil.isEmpty((String)paraMap.get("packSizeName"))) { //产品条件
				sql.append(" \r\n and samd.productid in (select id from product where productname = ?) ");
				param.put(UUID.randomUUID().toString(), paraMap.get("ProductName"));
			}
			sql.append(" \r\n  LEFT JOIN F_UNIT f on samd.productid = f.productid and samd.unitid = f.funitid  ");
			sql.append(" \r\n UNION ALL");
			//--转仓单
			sql.append(" \r\n select o.id as organid,o.organname,o.oecode,o.province,smd.productid,0 as stockpile,(smd.takequantity * f.xquantity) as stockintransit,'' as warehousename,sm.INWAREHOUSEID as warehouseid, 0 as stockpiletoship  from STOCK_MOVE sm ");
			sql.append(" \r\n join ORGAN o on o.id = sm.inorganid and sm.isshipment = 1 and sm.iscomplete = 0 and sm.isblankout = 0 ");
			if(!DbUtil.isDealer(users)) {
				sql.append(" \r\n and "+DbUtil.getWhereCondition(users, "o"));
			}
//			sql.append(" \r\n and sm.shipmentdate >  to_date('" + paraMap.get("historeyDate") + "','yyyy-MM-dd hh24:mi:ss')");
			sql.append(" \r\n and sm.movedate < to_date(?,'yyyy-MM-dd hh24:mi:ss')");
			param.put(UUID.randomUUID().toString(), paraMap.get("EndDate"));
			if(sysPro != null &&"1".equals(sysPro.getProperty("genReportOnlyForPD"))){ //是否只统计PD
				sql.append(" \r\n and o.organtype = 2 and o.organmodel = 1"); 
			}
			if(!StringUtil.isEmpty((String)paraMap.get("inOrganId"))){
				sql.append(" \r\n and sm.inorganid = ? "); //机构条件
				param.put(UUID.randomUUID().toString(), paraMap.get("inOrganId"));
			}
			if(!StringUtil.isEmpty((String)paraMap.get("region"))) {
				sql.append(" \r\n and o.province is not null and o.province in (select areaid from region_area where regioncodeid =  ?)"); //机构条件 
				param.put(UUID.randomUUID().toString(), paraMap.get("region"));
			}
			if(DbUtil.isDealer(users)) {
				sql.append(" \r\n  and sm.INWAREHOUSEID in (select wv.warehouse_Id from RULE_USER_WH wv where wv.user_Id=?) ");
				param.put(UUID.randomUUID().toString(), users.getUserid());
			}
			if(!StringUtil.isEmpty((String)paraMap.get("inWarehouseId"))){
				sql.append(" \r\n and sm.INWAREHOUSEID = ? "); //仓库条件
				param.put(UUID.randomUUID().toString(), paraMap.get("inWarehouseId"));
			}
			sql.append(" \r\n join STOCK_MOVE_DETAIL smd on sm.id = smd.smid  ");
			if(!StringUtil.isEmpty((String)paraMap.get("ProductName")) && !StringUtil.isEmpty((String)paraMap.get("packSizeName"))) { //产品条件
				sql.append(" \r\n and smd.productid in (select id from product where productname = ? and specmode= ?) ");
				param.put(UUID.randomUUID().toString(), paraMap.get("ProductName"));
				param.put(UUID.randomUUID().toString(), paraMap.get("packSizeName"));
			} else if(!StringUtil.isEmpty((String)paraMap.get("ProductName")) && StringUtil.isEmpty((String)paraMap.get("packSizeName"))) { //产品条件
				sql.append(" \r\n and smd.productid in (select id from product where productname = ?) ");
				param.put(UUID.randomUUID().toString(), paraMap.get("ProductName"));
			}
			sql.append(" \r\n  LEFT JOIN F_UNIT f on smd.productid = f.productid and smd.unitid = f.funitid  ");
			
			sql.append(" \r\n UNION ALL");
			// --退货单
			sql.append(" \r\n select o.id as organid,o.organname,o.oecode,o.province,owd.productid,0 as stockpile,(owd.takequantity * f.xquantity) as stockintransit,'' as warehousename,ow.INWAREHOUSEID as warehouseid, 0 as stockpiletoship from ORGAN_WITHDRAW ow  ");
			sql.append(" \r\n join ORGAN o on o.id = ow.receiveorganid and ow.takestatus = 1 and ow.iscomplete = 0 and ow.isblankout = 0 ");
			if(!DbUtil.isDealer(users)) {
				sql.append(" \r\n and "+DbUtil.getWhereCondition(users, "o"));
			}
			sql.append(" \r\n and ow.makedate < to_date(?,'yyyy-MM-dd hh24:mi:ss')");
			param.put(UUID.randomUUID().toString(), paraMap.get("EndDate"));
			if(sysPro != null &&"1".equals(sysPro.getProperty("genReportOnlyForPD"))){ //是否只统计PD
				sql.append(" \r\n and o.organtype = 2 and o.organmodel = 1"); 
			}
			if(!StringUtil.isEmpty((String)paraMap.get("inOrganId"))){
				sql.append(" \r\n and ow.receiveorganid = ? "); //机构条件
				param.put(UUID.randomUUID().toString(), paraMap.get("inOrganId"));
			}
			if(!StringUtil.isEmpty((String)paraMap.get("region"))) {
				sql.append(" \r\n and o.province is not null and o.province in (select areaid from region_area where regioncodeid =  ?)"); //机构条件 
				param.put(UUID.randomUUID().toString(), paraMap.get("region"));
			}
			if(DbUtil.isDealer(users)) {
				sql.append(" \r\n  and ow.INWAREHOUSEID in (select wv.warehouse_Id from RULE_USER_WH wv where wv.user_Id=?) ");
				param.put(UUID.randomUUID().toString(), users.getUserid());
			}
			if(!StringUtil.isEmpty((String)paraMap.get("inWarehouseId"))){
				sql.append(" \r\n and ow.INWAREHOUSEID = ? "); //仓库条件
				param.put(UUID.randomUUID().toString(), paraMap.get("inWarehouseId"));
			}
//			sql.append(" \r\n join TAKE_TICKET tt on tt.billno = ow.id ");
//			sql.append(" \r\n and tt.auditdate >  to_date('" + paraMap.get("historeyDate") + "','yyyy-MM-dd hh24:mi:ss')");
			sql.append(" \r\n join ORGAN_WITHDRAW_DETAIL owd on ow.id = owd.owid  ");
			if(!StringUtil.isEmpty((String)paraMap.get("ProductName")) && !StringUtil.isEmpty((String)paraMap.get("packSizeName"))) { //产品条件
				sql.append(" \r\n and owd.productid in (select id from product where productname = ? and specmode= ?) ");
				param.put(UUID.randomUUID().toString(), paraMap.get("ProductName"));
				param.put(UUID.randomUUID().toString(), paraMap.get("packSizeName"));
			} else if(!StringUtil.isEmpty((String)paraMap.get("ProductName")) && StringUtil.isEmpty((String)paraMap.get("packSizeName"))) { //产品条件
				sql.append(" \r\n and owd.productid in (select id from product where productname = ?) ");
				param.put(UUID.randomUUID().toString(), paraMap.get("ProductName"));
			}
			sql.append(" \r\n  LEFT JOIN F_UNIT f on owd.productid = f.productid and owd.unitid = f.funitid   ");
			
			
			
		} else {
			sql.append(" \r\n UNION ALL");
			//加上拒签的发货单
			sql.append(" \r\n select o.id as organid,o.organname,o.oecode,o.province,samd.productid,(samd.takequantity * f.xquantity) as stockpile,0 as stockintransit,'' as warehousename,sam.OUTWAREHOUSEID as warehouseid, 0 as stockpiletoship  from STOCK_ALTER_MOVE sam ");
			sql.append(" \r\n join ORGAN o on o.id = sam.outorganid and sam.isblankout = 1");
			if(!DbUtil.isDealer(users)) {
				sql.append(" \r\n and "+DbUtil.getWhereCondition(users, "o"));
			}
			sql.append(" \r\n and sam.blankoutdate > to_date(?,'yyyy-MM-dd hh24:mi:ss') ");
			param.put(UUID.randomUUID().toString(), paraMap.get("historeyDate"));
			sql.append(" \r\n and sam.shipmentdate < to_date(?,'yyyy-MM-dd hh24:mi:ss') ");
			param.put(UUID.randomUUID().toString(), paraMap.get("historeyDate"));
			if(sysPro != null &&"1".equals(sysPro.getProperty("genReportOnlyForPD"))){ //是否只统计PD
				sql.append(" \r\n and o.organtype = 2 and o.organmodel = 1"); 
			}
			if(!StringUtil.isEmpty((String)paraMap.get("inOrganId"))){
				sql.append(" \r\n and sam.outorganid = ? "); //机构条件
				param.put(UUID.randomUUID().toString(), paraMap.get("inOrganId"));
			}
			if(!StringUtil.isEmpty((String)paraMap.get("region"))) {
				sql.append(" \r\n and o.province is not null and o.province in (select areaid from region_area where regioncodeid =  ?)"); //机构条件 
				param.put(UUID.randomUUID().toString(), paraMap.get("region"));
			}
			if(DbUtil.isDealer(users)) {
				sql.append(" \r\n  and sam.OUTWAREHOUSEID in (select wv.warehouse_Id from RULE_USER_WH wv where wv.user_Id=?)");
				param.put(UUID.randomUUID().toString(), users.getUserid());
			}
			if(!StringUtil.isEmpty((String)paraMap.get("inWarehouseId"))){
				sql.append(" \r\n and sam.OUTWAREHOUSEID = ? "); //仓库条件
				param.put(UUID.randomUUID().toString(), paraMap.get("inWarehouseId"));
			}
			sql.append(" \r\n join STOCK_ALTER_MOVE_DETAIL samd on sam.id = samd.samid ");
			if(!StringUtil.isEmpty((String)paraMap.get("ProductName")) && !StringUtil.isEmpty((String)paraMap.get("packSizeName"))) { //产品条件
				sql.append(" \r\n and samd.productid in (select id from product where productname = ? and specmode= ?) ");
				param.put(UUID.randomUUID().toString(), paraMap.get("ProductName"));
				param.put(UUID.randomUUID().toString(), paraMap.get("packSizeName"));
			} else if(!StringUtil.isEmpty((String)paraMap.get("ProductName")) && StringUtil.isEmpty((String)paraMap.get("packSizeName"))) { //产品条件
				sql.append(" \r\n and samd.productid in (select id from product where productname = ?) ");
				param.put(UUID.randomUUID().toString(), paraMap.get("ProductName"));
			}
			sql.append(" \r\n LEFT JOIN F_UNIT f on samd.productid = f.productid and samd.unitid = f.funitid  ");
			sql.append(" \r\n UNION ALL");
			//--发货单(入库)
			sql.append(" \r\n select o.id as organid,o.organname,o.oecode,o.province,samd.productid,(samd.receivequantity * f.xquantity) as stockpile,0 as stockintransit,'' as warehousename,sam.INWAREHOUSEID as warehouseid, 0 as stockpiletoship  from STOCK_ALTER_MOVE sam ");
			sql.append(" \r\n join ORGAN o on o.id = sam.receiveorganid and sam.iscomplete = 1");
			if(!DbUtil.isDealer(users)) {
				sql.append(" \r\n and "+DbUtil.getWhereCondition(users, "o"));
			}
			sql.append(" \r\n and sam.receivedate >  to_date(?,'yyyy-MM-dd hh24:mi:ss')");
			param.put(UUID.randomUUID().toString(), paraMap.get("historeyDate"));
			sql.append(" \r\n and sam.movedate < to_date(?,'yyyy-MM-dd hh24:mi:ss')");
			param.put(UUID.randomUUID().toString(), paraMap.get("EndDate"));
			if(sysPro != null &&"1".equals(sysPro.getProperty("genReportOnlyForPD"))){ //是否只统计PD
				sql.append(" \r\n and o.organtype = 2 and o.organmodel = 1"); 
			}
			if(!StringUtil.isEmpty((String)paraMap.get("inOrganId"))){
				sql.append(" \r\n and sam.receiveorganid = ? "); //机构条件
				param.put(UUID.randomUUID().toString(), paraMap.get("inOrganId"));
			}
			if(!StringUtil.isEmpty((String)paraMap.get("region"))) {
				sql.append(" \r\n and o.province is not null and o.province in (select areaid from region_area where regioncodeid =  ?)"); //机构条件 
				param.put(UUID.randomUUID().toString(), paraMap.get("region"));
			}
			if(DbUtil.isDealer(users)) {
				sql.append(" \r\n  and sam.INWAREHOUSEID in (select wv.warehouse_Id from RULE_USER_WH wv where wv.user_Id=?) ");
				param.put(UUID.randomUUID().toString(), users.getUserid());
			}
			if(!StringUtil.isEmpty((String)paraMap.get("inWarehouseId"))){
				sql.append(" \r\n and sam.INWAREHOUSEID = ? "); //仓库条件
				param.put(UUID.randomUUID().toString(), paraMap.get("inWarehouseId"));
			}
			sql.append(" \r\n join STOCK_ALTER_MOVE_DETAIL samd on sam.id = samd.samid ");
			if(!StringUtil.isEmpty((String)paraMap.get("ProductName")) && !StringUtil.isEmpty((String)paraMap.get("packSizeName"))) { //产品条件
				sql.append(" \r\n and samd.productid in (select id from product where productname = ? and specmode= ?) ");
				param.put(UUID.randomUUID().toString(), paraMap.get("ProductName"));
				param.put(UUID.randomUUID().toString(), paraMap.get("packSizeName"));
			} else if(!StringUtil.isEmpty((String)paraMap.get("ProductName")) && StringUtil.isEmpty((String)paraMap.get("packSizeName"))) { //产品条件
				sql.append(" \r\n and samd.productid in (select id from product where productname = ?) ");
				param.put(UUID.randomUUID().toString(), paraMap.get("ProductName"));
			}
			sql.append(" \r\n  LEFT JOIN F_UNIT f on samd.productid = f.productid and samd.unitid = f.funitid  ");
			
			sql.append(" \r\n UNION ALL");
			//--发货单(出库)
			sql.append(" \r\n select o.id as organid,o.organname,o.oecode,o.province,samd.productid,-(samd.takequantity * f.xquantity) as stockpile,0 as stockintransit,'' as warehousename,sam.OUTWAREHOUSEID as warehouseid, 0 as stockpiletoship  from STOCK_ALTER_MOVE sam ");
			sql.append(" \r\n join ORGAN o on o.id = sam.outorganid and sam.isshipment = 1");
			if(!DbUtil.isDealer(users)) {
				sql.append(" \r\n and "+DbUtil.getWhereCondition(users, "o"));
			}
			sql.append(" \r\n and sam.shipmentdate >  to_date(?,'yyyy-MM-dd hh24:mi:ss')");
			param.put(UUID.randomUUID().toString(), paraMap.get("historeyDate"));
			sql.append(" \r\n and sam.movedate < to_date(?,'yyyy-MM-dd hh24:mi:ss')");
			param.put(UUID.randomUUID().toString(), paraMap.get("EndDate"));
			if(sysPro != null &&"1".equals(sysPro.getProperty("genReportOnlyForPD"))){ //是否只统计PD
				sql.append(" \r\n and o.organtype = 2 and o.organmodel = 1"); 
			}
			if(!StringUtil.isEmpty((String)paraMap.get("inOrganId"))){
				sql.append(" \r\n and sam.outorganid = ? "); //机构条件
				param.put(UUID.randomUUID().toString(), paraMap.get("inOrganId"));
			}
			if(!StringUtil.isEmpty((String)paraMap.get("region"))) {
				sql.append(" \r\n and o.province is not null and o.province in (select areaid from region_area where regioncodeid =  ?)"); //机构条件 
				param.put(UUID.randomUUID().toString(), paraMap.get("region"));
			}
			if(DbUtil.isDealer(users)) {
				sql.append(" \r\n  and sam.OUTWAREHOUSEID in (select wv.warehouse_Id from RULE_USER_WH wv where wv.user_Id=?) ");
				param.put(UUID.randomUUID().toString(), users.getUserid());
			}
			if(!StringUtil.isEmpty((String)paraMap.get("inWarehouseId"))){
				sql.append(" \r\n and sam.OUTWAREHOUSEID = ? "); //仓库条件
				param.put(UUID.randomUUID().toString(), paraMap.get("inWarehouseId"));
			}
			sql.append(" \r\n join STOCK_ALTER_MOVE_DETAIL samd on sam.id = samd.samid ");
			if(!StringUtil.isEmpty((String)paraMap.get("ProductName")) && !StringUtil.isEmpty((String)paraMap.get("packSizeName"))) { //产品条件
				sql.append(" \r\n and samd.productid in (select id from product where productname = ? and specmode= ?) ");
				param.put(UUID.randomUUID().toString(), paraMap.get("ProductName"));
				param.put(UUID.randomUUID().toString(), paraMap.get("packSizeName"));
			} else if(!StringUtil.isEmpty((String)paraMap.get("ProductName")) && StringUtil.isEmpty((String)paraMap.get("packSizeName"))) { //产品条件
				sql.append(" \r\n and samd.productid in (select id from product where productname = ?) ");
				param.put(UUID.randomUUID().toString(), paraMap.get("ProductName"));
			}
			sql.append(" \r\n  LEFT JOIN F_UNIT f on samd.productid = f.productid and samd.unitid = f.funitid  ");
			
			
			sql.append(" \r\n UNION ALL");
			// --转仓单(入库)
			sql.append(" \r\n select o.id as organid,o.organname,o.oecode,o.province,smd.productid,(smd.takequantity * f.xquantity) as stockpile,0 as stockintransit,'' as warehousename,sm.INWAREHOUSEID as warehouseid, 0 as stockpiletoship  from STOCK_MOVE sm ");
			sql.append(" \r\n join ORGAN o on o.id = sm.inorganid and sm.iscomplete = 1 and sm.inwarehouseid <> outwarehouseid");
			if(!DbUtil.isDealer(users)) {
				sql.append(" \r\n and "+DbUtil.getWhereCondition(users, "o"));
			}
			sql.append(" \r\n and sm.receivedate >  to_date(?,'yyyy-MM-dd hh24:mi:ss')");
			param.put(UUID.randomUUID().toString(), paraMap.get("historeyDate"));
			sql.append(" \r\n and sm.SHIPMENTDATE < to_date(?,'yyyy-MM-dd hh24:mi:ss')");
			param.put(UUID.randomUUID().toString(), paraMap.get("EndDate"));
			if(sysPro != null &&"1".equals(sysPro.getProperty("genReportOnlyForPD"))){ //是否只统计PD
				sql.append(" \r\n and o.organtype = 2 and o.organmodel = 1"); 
			}
			if(!StringUtil.isEmpty((String)paraMap.get("inOrganId"))){
				sql.append(" \r\n and sm.inorganid = ? "); //机构条件
				param.put(UUID.randomUUID().toString(), paraMap.get("inOrganId"));
			}
			if(!StringUtil.isEmpty((String)paraMap.get("region"))) {
				sql.append(" \r\n and o.province is not null and o.province in (select areaid from region_area where regioncodeid =  ?)"); //机构条件 
				param.put(UUID.randomUUID().toString(), paraMap.get("region"));
			}
			if(DbUtil.isDealer(users)) {
				sql.append(" \r\n  and sm.INWAREHOUSEID in (select wv.warehouse_Id from RULE_USER_WH wv where wv.user_Id=?) ");
				param.put(UUID.randomUUID().toString(), users.getUserid());
			}
			if(!StringUtil.isEmpty((String)paraMap.get("inWarehouseId"))){
				sql.append(" \r\n and sm.INWAREHOUSEID = ? "); //仓库条件
				param.put(UUID.randomUUID().toString(), paraMap.get("inWarehouseId"));
			}
			sql.append(" \r\n join STOCK_MOVE_DETAIL smd on sm.id = smd.smid  ");
			if(!StringUtil.isEmpty((String)paraMap.get("ProductName")) && !StringUtil.isEmpty((String)paraMap.get("packSizeName"))) { //产品条件
				sql.append(" \r\n and smd.productid in (select id from product where productname = ? and specmode= ?) ");
				param.put(UUID.randomUUID().toString(), paraMap.get("ProductName"));
				param.put(UUID.randomUUID().toString(), paraMap.get("packSizeName"));
			} else if(!StringUtil.isEmpty((String)paraMap.get("ProductName")) && StringUtil.isEmpty((String)paraMap.get("packSizeName"))) { //产品条件
				sql.append(" \r\n and smd.productid in (select id from product where productname = ?) ");
				param.put(UUID.randomUUID().toString(), paraMap.get("ProductName"));
			}
			sql.append(" \r\n  LEFT JOIN F_UNIT f on smd.productid = f.productid and smd.unitid = f.funitid  ");
			
			sql.append(" \r\n UNION ALL");
			//--转仓单(出库)
			sql.append(" \r\n select o.id as organid,o.organname,o.oecode,o.province,smd.productid,-(smd.takequantity * f.xquantity) as stockpile,0 as stockintransit,'' as warehousename,sm.OUTWAREHOUSEID as warehouseid, 0 as stockpiletoship  from STOCK_MOVE sm ");
			sql.append(" \r\n join ORGAN o on o.id = sm.outorganid and sm.isshipment = 1 and sm.inwarehouseid <> outwarehouseid");
			if(!DbUtil.isDealer(users)) {
				sql.append(" \r\n and "+DbUtil.getWhereCondition(users, "o"));
			}
			sql.append(" \r\n and sm.shipmentdate >  to_date(?,'yyyy-MM-dd hh24:mi:ss')");
			param.put(UUID.randomUUID().toString(), paraMap.get("historeyDate"));
			sql.append(" \r\n and sm.SHIPMENTDATE < to_date(?,'yyyy-MM-dd hh24:mi:ss')");
			param.put(UUID.randomUUID().toString(), paraMap.get("EndDate"));
			if(sysPro != null &&"1".equals(sysPro.getProperty("genReportOnlyForPD"))){ //是否只统计PD
				sql.append(" \r\n and o.organtype = 2 and o.organmodel = 1"); 
			}
			if(!StringUtil.isEmpty((String)paraMap.get("inOrganId"))){
				sql.append(" \r\n and sm.outorganid = ? "); //机构条件
				param.put(UUID.randomUUID().toString(), paraMap.get("inOrganId"));
			}
			if(!StringUtil.isEmpty((String)paraMap.get("region"))) {
				sql.append(" \r\n and o.province is not null and o.province in (select areaid from region_area where regioncodeid =  ?)"); //机构条件 
				param.put(UUID.randomUUID().toString(), paraMap.get("region"));
			}
			if(DbUtil.isDealer(users)) {
				sql.append(" \r\n  and sm.OUTWAREHOUSEID in (select wv.warehouse_Id from RULE_USER_WH wv where wv.user_Id=?) ");
				param.put(UUID.randomUUID().toString(), users.getUserid());
			}
			if(!StringUtil.isEmpty((String)paraMap.get("inWarehouseId"))){
				sql.append(" \r\n and sm.OUTWAREHOUSEID = ? "); //仓库条件
				param.put(UUID.randomUUID().toString(), paraMap.get("inWarehouseId"));
			}
			sql.append(" \r\n join STOCK_MOVE_DETAIL smd on sm.id = smd.smid  ");
			if(!StringUtil.isEmpty((String)paraMap.get("ProductName")) && !StringUtil.isEmpty((String)paraMap.get("packSizeName"))) { //产品条件
				sql.append(" \r\n and smd.productid in (select id from product where productname = ? and specmode= ?) ");
				param.put(UUID.randomUUID().toString(), paraMap.get("ProductName"));
				param.put(UUID.randomUUID().toString(), paraMap.get("packSizeName"));
			} else if(!StringUtil.isEmpty((String)paraMap.get("ProductName")) && StringUtil.isEmpty((String)paraMap.get("packSizeName"))) { //产品条件
				sql.append(" \r\n and smd.productid in (select id from product where productname = ?) ");
				param.put(UUID.randomUUID().toString(), paraMap.get("ProductName"));
			}
			sql.append(" \r\n  LEFT JOIN F_UNIT f on smd.productid = f.productid and smd.unitid = f.funitid  ");
			
			sql.append(" \r\n UNION ALL");
			//--退货单(入库)
			sql.append(" \r\n select o.id as organid,o.organname,o.oecode,o.province,owd.productid,(owd.takequantity * f.xquantity) as stockpile,0 as stockintransit,'' as warehousename,ow.INWAREHOUSEID as warehouseid, 0 as stockpiletoship from ORGAN_WITHDRAW ow  ");
			sql.append(" \r\n join take_ticket tt on tt.billno = ow.id and ow.iscomplete = 1");
			sql.append(" \r\n and ow.receivedate >  to_date(?,'yyyy-MM-dd hh24:mi:ss')");
			param.put(UUID.randomUUID().toString(), paraMap.get("historeyDate"));
			sql.append(" \r\n and tt.auditdate < to_date(?,'yyyy-MM-dd hh24:mi:ss')");
			param.put(UUID.randomUUID().toString(), paraMap.get("EndDate"));
			sql.append(" \r\n join ORGAN o on o.id = ow.receiveorganid");
			if(!DbUtil.isDealer(users)) {
				sql.append(" \r\n and "+DbUtil.getWhereCondition(users, "o"));
			}
			if(sysPro != null &&"1".equals(sysPro.getProperty("genReportOnlyForPD"))){ //是否只统计PD
				sql.append(" \r\n and o.organtype = 2 and o.organmodel = 1"); 
			}
			if(!StringUtil.isEmpty((String)paraMap.get("inOrganId"))){
				sql.append(" \r\n and ow.receiveorganid = ? "); //机构条件
				param.put(UUID.randomUUID().toString(), paraMap.get("inOrganId"));
			}
			if(!StringUtil.isEmpty((String)paraMap.get("region"))) {
				sql.append(" \r\n and o.province is not null and o.province in (select areaid from region_area where regioncodeid =  ?)"); //机构条件 
				param.put(UUID.randomUUID().toString(), paraMap.get("region"));
			}
			if(DbUtil.isDealer(users)) {
				sql.append(" \r\n  and ow.INWAREHOUSEID in (select wv.warehouse_Id from RULE_USER_WH wv where wv.user_Id=?) ");
				param.put(UUID.randomUUID().toString(), users.getUserid());
			}
			if(!StringUtil.isEmpty((String)paraMap.get("inWarehouseId"))){
				sql.append(" \r\n and ow.INWAREHOUSEID = ? "); //仓库条件
				param.put(UUID.randomUUID().toString(), paraMap.get("inWarehouseId"));
			}
			sql.append(" \r\n join ORGAN_WITHDRAW_DETAIL owd on ow.id = owd.owid  ");
			if(!StringUtil.isEmpty((String)paraMap.get("ProductName")) && !StringUtil.isEmpty((String)paraMap.get("packSizeName"))) { //产品条件
				sql.append(" \r\n and owd.productid in (select id from product where productname = ? and specmode= ?) ");
				param.put(UUID.randomUUID().toString(), paraMap.get("ProductName"));
				param.put(UUID.randomUUID().toString(), paraMap.get("packSizeName"));
			} else if(!StringUtil.isEmpty((String)paraMap.get("ProductName")) && StringUtil.isEmpty((String)paraMap.get("packSizeName"))) { //产品条件
				sql.append(" \r\n and owd.productid in (select id from product where productname = ?) ");
				param.put(UUID.randomUUID().toString(), paraMap.get("ProductName"));
			}
			sql.append(" \r\n  LEFT JOIN F_UNIT f on owd.productid = f.productid and owd.unitid = f.funitid   ");
			sql.append(" \r\n UNION ALL");
			//--退货单(出库)
			sql.append(" \r\n select o.id as organid,o.organname,o.oecode,o.province,owd.productid,-(owd.takequantity * f.xquantity) as stockpile,0 as stockintransit,'' as warehousename,ow.WAREHOUSEID as warehouseid, 0 as stockpiletoship from ORGAN_WITHDRAW ow  ");
			sql.append(" \r\n join ORGAN o on o.id = ow.porganid and ow.takestatus = 1");
			if(!DbUtil.isDealer(users)) {
				sql.append(" \r\n and "+DbUtil.getWhereCondition(users, "o"));
			}
			if(sysPro != null &&"1".equals(sysPro.getProperty("genReportOnlyForPD"))){ //是否只统计PD
				sql.append(" \r\n and o.organtype = 2 and o.organmodel = 1"); 
			}
			if(!StringUtil.isEmpty((String)paraMap.get("inOrganId"))){
				sql.append(" \r\n and ow.porganid = ? "); //机构条件
				param.put(UUID.randomUUID().toString(), paraMap.get("inOrganId"));
			}
			if(!StringUtil.isEmpty((String)paraMap.get("region"))) {
				sql.append(" \r\n and o.province is not null and o.province in (select areaid from region_area where regioncodeid =  ?)"); //机构条件 
				param.put(UUID.randomUUID().toString(), paraMap.get("region"));
			}
			if(DbUtil.isDealer(users)) {
				sql.append(" \r\n  and ow.WAREHOUSEID in (select wv.warehouse_Id from RULE_USER_WH wv where wv.user_Id=?) ");
				param.put(UUID.randomUUID().toString(), users.getUserid());
			}
			if(!StringUtil.isEmpty((String)paraMap.get("inWarehouseId"))){
				sql.append(" \r\n and ow.WAREHOUSEID = ? "); //仓库条件
				param.put(UUID.randomUUID().toString(), paraMap.get("inWarehouseId"));
			}
			sql.append(" \r\n join TAKE_TICKET tt on tt.billno = ow.id ");
			sql.append(" \r\n and tt.auditdate >  to_date(?,'yyyy-MM-dd hh24:mi:ss')");
			param.put(UUID.randomUUID().toString(), paraMap.get("historeyDate"));
			sql.append(" \r\n join ORGAN_WITHDRAW_DETAIL owd on ow.id = owd.owid  ");
			if(!StringUtil.isEmpty((String)paraMap.get("ProductName")) && !StringUtil.isEmpty((String)paraMap.get("packSizeName"))) { //产品条件
				sql.append(" \r\n and owd.productid in (select id from product where productname = ? and specmode= ?) ");
				param.put(UUID.randomUUID().toString(), paraMap.get("ProductName"));
				param.put(UUID.randomUUID().toString(), paraMap.get("packSizeName"));
			} else if(!StringUtil.isEmpty((String)paraMap.get("ProductName")) && StringUtil.isEmpty((String)paraMap.get("packSizeName"))) { //产品条件
				sql.append(" \r\n and owd.productid in (select id from product where productname = ?) ");
				param.put(UUID.randomUUID().toString(), paraMap.get("ProductName"));
			}
			sql.append(" \r\n  LEFT JOIN F_UNIT f on owd.productid = f.productid and owd.unitid = f.funitid   ");

			
			sql.append(" \r\n UNION ALL");
			//--发货单(入库)
			sql.append(" \r\n --发货单(入库)");
			sql.append(" \r\n select o.id as organid,o.organname,o.oecode,o.province,samd.productid,-(samd.receivequantity * f.xquantity) as stockpile,0 as stockintransit,'' as warehousename,sam.INWAREHOUSEID as warehouseid, 0 as stockpiletoship  from STOCK_ALTER_MOVE sam ");
			sql.append(" \r\n join ORGAN o on o.id = sam.receiveorganid and sam.iscomplete = 1");
			if(!DbUtil.isDealer(users)) {
				sql.append(" \r\n and "+DbUtil.getWhereCondition(users, "o"));
			}
			sql.append(" \r\n and sam.receivedate > to_date(?,'yyyy-MM-dd hh24:mi:ss')");
			param.put(UUID.randomUUID().toString(), paraMap.get("EndDate"));
			sql.append(" \r\n and sam.receivedate <  to_date(?,'yyyy-MM-dd hh24:mi:ss')");
			param.put(UUID.randomUUID().toString(), paraMap.get("historeyDate"));
			sql.append(" \r\n and sam.movedate > to_date(?,'yyyy-MM-dd hh24:mi:ss')");
			param.put(UUID.randomUUID().toString(), paraMap.get("EndDate"));
			if(sysPro != null &&"1".equals(sysPro.getProperty("genReportOnlyForPD"))){ //是否只统计PD
				sql.append(" \r\n and o.organtype = 2 and o.organmodel = 1"); 
			}
			if(!StringUtil.isEmpty((String)paraMap.get("inOrganId"))){
				sql.append(" \r\n and sam.receiveorganid = ? "); //机构条件
				param.put(UUID.randomUUID().toString(), paraMap.get("inOrganId"));
			}
			if(!StringUtil.isEmpty((String)paraMap.get("region"))) {
				sql.append(" \r\n and o.province is not null and o.province in (select areaid from region_area where regioncodeid =  ?)"); //机构条件 
				param.put(UUID.randomUUID().toString(), paraMap.get("region"));
			}
			if(DbUtil.isDealer(users)) {
				sql.append(" \r\n  and sam.INWAREHOUSEID in (select wv.warehouse_Id from RULE_USER_WH wv where wv.user_Id=?) ");
				param.put(UUID.randomUUID().toString(), users.getUserid());
			}
			if(!StringUtil.isEmpty((String)paraMap.get("inWarehouseId"))){
				sql.append(" \r\n and sam.INWAREHOUSEID = ? "); //仓库条件
				param.put(UUID.randomUUID().toString(), paraMap.get("inWarehouseId"));
			}
			sql.append(" \r\n join STOCK_ALTER_MOVE_DETAIL samd on sam.id = samd.samid ");
			if(!StringUtil.isEmpty((String)paraMap.get("ProductName")) && !StringUtil.isEmpty((String)paraMap.get("packSizeName"))) { //产品条件
				sql.append(" \r\n and samd.productid in (select id from product where productname = ? and specmode= ?) ");
				param.put(UUID.randomUUID().toString(), paraMap.get("ProductName"));
				param.put(UUID.randomUUID().toString(), paraMap.get("packSizeName"));
			} else if(!StringUtil.isEmpty((String)paraMap.get("ProductName")) && StringUtil.isEmpty((String)paraMap.get("packSizeName"))) { //产品条件
				sql.append(" \r\n and samd.productid in (select id from product where productname = ?) ");
				param.put(UUID.randomUUID().toString(), paraMap.get("ProductName"));
			}
			sql.append(" \r\n  LEFT JOIN F_UNIT f on samd.productid = f.productid and samd.unitid = f.funitid  ");
			
			sql.append(" \r\n UNION ALL");
			//--发货单(出库)
			sql.append(" \r\n --发货单(出库)");
			sql.append(" \r\n select o.id as organid,o.organname,o.oecode,o.province,samd.productid,(samd.takequantity * f.xquantity) as stockpile,0 as stockintransit,'' as warehousename,sam.OUTWAREHOUSEID as warehouseid, 0 as stockpiletoship  from STOCK_ALTER_MOVE sam ");
			sql.append(" \r\n join ORGAN o on o.id = sam.outorganid and sam.isshipment = 1");
			if(!DbUtil.isDealer(users)) {
				sql.append(" \r\n and "+DbUtil.getWhereCondition(users, "o"));
			}
			sql.append(" \r\n and sam.shipmentdate > to_date(?,'yyyy-MM-dd hh24:mi:ss')");
			param.put(UUID.randomUUID().toString(), paraMap.get("EndDate"));
			sql.append(" \r\n and sam.shipmentdate <  to_date(?,'yyyy-MM-dd hh24:mi:ss')");
			param.put(UUID.randomUUID().toString(), paraMap.get("historeyDate"));
			sql.append(" \r\n and sam.movedate > to_date(?,'yyyy-MM-dd hh24:mi:ss')");
			param.put(UUID.randomUUID().toString(), paraMap.get("EndDate"));
			if(sysPro != null &&"1".equals(sysPro.getProperty("genReportOnlyForPD"))){ //是否只统计PD
				sql.append(" \r\n and o.organtype = 2 and o.organmodel = 1"); 
			}
			if(!StringUtil.isEmpty((String)paraMap.get("inOrganId"))){
				sql.append(" \r\n and sam.outorganid = ? "); //机构条件
				param.put(UUID.randomUUID().toString(), paraMap.get("inOrganId"));
			}
			if(!StringUtil.isEmpty((String)paraMap.get("region"))) {
				sql.append(" \r\n and o.province is not null and o.province in (select areaid from region_area where regioncodeid =  ?)"); //机构条件 
				param.put(UUID.randomUUID().toString(), paraMap.get("region"));
			}
			if(DbUtil.isDealer(users)) {
				sql.append(" \r\n  and sam.OUTWAREHOUSEID in (select wv.warehouse_Id from RULE_USER_WH wv where wv.user_Id=?) ");
				param.put(UUID.randomUUID().toString(), users.getUserid());
			}
			if(!StringUtil.isEmpty((String)paraMap.get("inWarehouseId"))){
				sql.append(" \r\n and sam.OUTWAREHOUSEID = ? "); //仓库条件
				param.put(UUID.randomUUID().toString(), paraMap.get("inWarehouseId"));
			}
			sql.append(" \r\n join STOCK_ALTER_MOVE_DETAIL samd on sam.id = samd.samid ");
			if(!StringUtil.isEmpty((String)paraMap.get("ProductName")) && !StringUtil.isEmpty((String)paraMap.get("packSizeName"))) { //产品条件
				sql.append(" \r\n and samd.productid in (select id from product where productname = ? and specmode= ?) ");
				param.put(UUID.randomUUID().toString(), paraMap.get("ProductName"));
				param.put(UUID.randomUUID().toString(), paraMap.get("packSizeName"));
			} else if(!StringUtil.isEmpty((String)paraMap.get("ProductName")) && StringUtil.isEmpty((String)paraMap.get("packSizeName"))) { //产品条件
				sql.append(" \r\n and samd.productid in (select id from product where productname = ?) ");
				param.put(UUID.randomUUID().toString(), paraMap.get("ProductName"));
			}
			sql.append(" \r\n  LEFT JOIN F_UNIT f on samd.productid = f.productid and samd.unitid = f.funitid  ");
			
			
			sql.append(" \r\n UNION ALL"); 
			
			//--发货单(待发货)
			sql.append(" \r\n --发货单(待发货)");
			sql.append(" \r\n select o.id as organid,o.organname,o.oecode,o.province,samd.productid,0 as stockpile,0 as stockintransit,'' as warehousename,sam.OUTWAREHOUSEID as warehouseid, (samd.takequantity * f.xquantity) as stockpiletoship  from STOCK_ALTER_MOVE sam ");
			sql.append(" \r\n join ORGAN o on o.id = sam.outorganid and sam.isshipment = 1");
			if(!DbUtil.isDealer(users)) {
				sql.append(" \r\n and "+DbUtil.getWhereCondition(users, "o"));
			}
			sql.append(" \r\n and sam.shipmentdate > to_date(?,'yyyy-MM-dd hh24:mi:ss')");
			param.put(UUID.randomUUID().toString(), paraMap.get("EndDate"));
			sql.append(" \r\n and sam.shipmentdate <  to_date(?,'yyyy-MM-dd hh24:mi:ss')");
			param.put(UUID.randomUUID().toString(), paraMap.get("historeyDate"));
			sql.append(" \r\n and sam.movedate <= to_date(?,'yyyy-MM-dd hh24:mi:ss')");
			param.put(UUID.randomUUID().toString(), paraMap.get("EndDate"));
			if(sysPro != null &&"1".equals(sysPro.getProperty("genReportOnlyForPD"))){ //是否只统计PD
				sql.append(" \r\n and o.organtype = 2 and o.organmodel = 1"); 
			}
			if(!StringUtil.isEmpty((String)paraMap.get("inOrganId"))){
				sql.append(" \r\n and sam.outorganid = ? "); //机构条件
				param.put(UUID.randomUUID().toString(), paraMap.get("inOrganId"));
			}
			if(!StringUtil.isEmpty((String)paraMap.get("region"))) {
				sql.append(" \r\n and o.province is not null and o.province in (select areaid from region_area where regioncodeid =  ?)"); //机构条件 
				param.put(UUID.randomUUID().toString(), paraMap.get("region"));
			}
			if(DbUtil.isDealer(users)) {
				sql.append(" \r\n  and sam.OUTWAREHOUSEID in (select wv.warehouse_Id from RULE_USER_WH wv where wv.user_Id=?) ");
				param.put(UUID.randomUUID().toString(), users.getUserid());
			}
			if(!StringUtil.isEmpty((String)paraMap.get("inWarehouseId"))){
				sql.append(" \r\n and sam.OUTWAREHOUSEID = ? "); //仓库条件
				param.put(UUID.randomUUID().toString(), paraMap.get("inWarehouseId"));
			}
			sql.append(" \r\n join STOCK_ALTER_MOVE_DETAIL samd on sam.id = samd.samid ");
			if(!StringUtil.isEmpty((String)paraMap.get("ProductName")) && !StringUtil.isEmpty((String)paraMap.get("packSizeName"))) { //产品条件
				sql.append(" \r\n and samd.productid in (select id from product where productname = ? and specmode= ?) ");
				param.put(UUID.randomUUID().toString(), paraMap.get("ProductName"));
				param.put(UUID.randomUUID().toString(), paraMap.get("packSizeName"));
			} else if(!StringUtil.isEmpty((String)paraMap.get("ProductName")) && StringUtil.isEmpty((String)paraMap.get("packSizeName"))) { //产品条件
				sql.append(" \r\n and samd.productid in (select id from product where productname = ?) ");
				param.put(UUID.randomUUID().toString(), paraMap.get("ProductName"));
			}
			sql.append(" \r\n  LEFT JOIN F_UNIT f on samd.productid = f.productid and samd.unitid = f.funitid  ");
			
			
			sql.append(" \r\n UNION ALL");
			
			// --转仓单(入库)
			sql.append(" \r\n --转仓单(入库)");
			sql.append(" \r\n select o.id as organid,o.organname,o.oecode,o.province,smd.productid,-(smd.takequantity * f.xquantity) as stockpile,0 as stockintransit,'' as warehousename,sm.INWAREHOUSEID as warehouseid, 0 as stockpiletoship  from STOCK_MOVE sm ");
			sql.append(" \r\n join ORGAN o on o.id = sm.inorganid and sm.iscomplete = 1 and sm.inwarehouseid <> outwarehouseid");
			if(!DbUtil.isDealer(users)) {
				sql.append(" \r\n and "+DbUtil.getWhereCondition(users, "o"));
			}
			sql.append(" \r\n and sm.receivedate > to_date(?,'yyyy-MM-dd hh24:mi:ss')");
			param.put(UUID.randomUUID().toString(), paraMap.get("EndDate"));
			sql.append(" \r\n and sm.receivedate <  to_date(?,'yyyy-MM-dd hh24:mi:ss')");
			param.put(UUID.randomUUID().toString(), paraMap.get("historeyDate"));
			sql.append(" \r\n and sm.SHIPMENTDATE > to_date(?,'yyyy-MM-dd hh24:mi:ss')");
			param.put(UUID.randomUUID().toString(), paraMap.get("EndDate"));
			if(sysPro != null &&"1".equals(sysPro.getProperty("genReportOnlyForPD"))){ //是否只统计PD
				sql.append(" \r\n and o.organtype = 2 and o.organmodel = 1"); 
			}
			if(!StringUtil.isEmpty((String)paraMap.get("inOrganId"))){
				sql.append(" \r\n and sm.inorganid = ? "); //机构条件
				param.put(UUID.randomUUID().toString(), paraMap.get("inOrganId"));
			}
			if(!StringUtil.isEmpty((String)paraMap.get("region"))) {
				sql.append(" \r\n and o.province is not null and o.province in (select areaid from region_area where regioncodeid =  ?)"); //机构条件 
				param.put(UUID.randomUUID().toString(), paraMap.get("region"));
			}
			if(DbUtil.isDealer(users)) {
				sql.append(" \r\n  and sm.INWAREHOUSEID in (select wv.warehouse_Id from RULE_USER_WH wv where wv.user_Id=?) ");
				param.put(UUID.randomUUID().toString(), users.getUserid());
			}
			if(!StringUtil.isEmpty((String)paraMap.get("inWarehouseId"))){
				sql.append(" \r\n and sm.INWAREHOUSEID = ? "); //仓库条件
				param.put(UUID.randomUUID().toString(), paraMap.get("inWarehouseId"));
			}
			sql.append(" \r\n join STOCK_MOVE_DETAIL smd on sm.id = smd.smid  ");
			if(!StringUtil.isEmpty((String)paraMap.get("ProductName")) && !StringUtil.isEmpty((String)paraMap.get("packSizeName"))) { //产品条件
				sql.append(" \r\n and smd.productid in (select id from product where productname = ? and specmode= ?) ");
				param.put(UUID.randomUUID().toString(), paraMap.get("ProductName"));
				param.put(UUID.randomUUID().toString(), paraMap.get("packSizeName"));
			} else if(!StringUtil.isEmpty((String)paraMap.get("ProductName")) && StringUtil.isEmpty((String)paraMap.get("packSizeName"))) { //产品条件
				sql.append(" \r\n and smd.productid in (select id from product where productname = ?) ");
				param.put(UUID.randomUUID().toString(), paraMap.get("ProductName"));
			}
			sql.append(" \r\n  LEFT JOIN F_UNIT f on smd.productid = f.productid and smd.unitid = f.funitid  ");
			
			sql.append(" \r\n UNION ALL");
			//--转仓单(出库)
			sql.append(" \r\n --转仓单(出库)");
			sql.append(" \r\n select o.id as organid,o.organname,o.oecode,o.province,smd.productid,(smd.takequantity * f.xquantity) as stockpile,0 as stockintransit,'' as warehousename,sm.OUTWAREHOUSEID as warehouseid, 0 as stockpiletoship  from STOCK_MOVE sm ");
			sql.append(" \r\n join ORGAN o on o.id = sm.outorganid and sm.isshipment = 1 and sm.inwarehouseid <> outwarehouseid");
			if(!DbUtil.isDealer(users)) {
				sql.append(" \r\n and "+DbUtil.getWhereCondition(users, "o"));
			}
			sql.append(" \r\n and sm.shipmentdate > to_date(?,'yyyy-MM-dd hh24:mi:ss')");
			param.put(UUID.randomUUID().toString(), paraMap.get("EndDate"));
			sql.append(" \r\n and sm.shipmentdate <  to_date(?,'yyyy-MM-dd hh24:mi:ss')");
			param.put(UUID.randomUUID().toString(), paraMap.get("historeyDate"));
//			sql.append(" \r\n and sm.SHIPMENTDATE > to_date('" + paraMap.get("EndDate") + "','yyyy-MM-dd hh24:mi:ss')");
			if(sysPro != null &&"1".equals(sysPro.getProperty("genReportOnlyForPD"))){ //是否只统计PD
				sql.append(" \r\n and o.organtype = 2 and o.organmodel = 1"); 
			}
			if(!StringUtil.isEmpty((String)paraMap.get("inOrganId"))){
				sql.append(" \r\n and sm.outorganid = ? "); //机构条件
				param.put(UUID.randomUUID().toString(), paraMap.get("inOrganId"));
			}
			if(!StringUtil.isEmpty((String)paraMap.get("region"))) {
				sql.append(" \r\n and o.province is not null and o.province in (select areaid from region_area where regioncodeid =  ?)"); //机构条件 
				param.put(UUID.randomUUID().toString(), paraMap.get("region"));
			}
			if(DbUtil.isDealer(users)) {
				sql.append(" \r\n  and sm.OUTWAREHOUSEID in (select wv.warehouse_Id from RULE_USER_WH wv where wv.user_Id=?) ");
				param.put(UUID.randomUUID().toString(), users.getUserid());
			}
			if(!StringUtil.isEmpty((String)paraMap.get("inWarehouseId"))){
				sql.append(" \r\n and sm.OUTWAREHOUSEID = ? "); //仓库条件
				param.put(UUID.randomUUID().toString(), paraMap.get("inWarehouseId"));
			}
			sql.append(" \r\n join STOCK_MOVE_DETAIL smd on sm.id = smd.smid  ");
			if(!StringUtil.isEmpty((String)paraMap.get("ProductName")) && !StringUtil.isEmpty((String)paraMap.get("packSizeName"))) { //产品条件
				sql.append(" \r\n and smd.productid in (select id from product where productname = ? and specmode= ?) ");
				param.put(UUID.randomUUID().toString(), paraMap.get("ProductName"));
				param.put(UUID.randomUUID().toString(), paraMap.get("packSizeName"));
			} else if(!StringUtil.isEmpty((String)paraMap.get("ProductName")) && StringUtil.isEmpty((String)paraMap.get("packSizeName"))) { //产品条件
				sql.append(" \r\n and smd.productid in (select id from product where productname = ?) ");
				param.put(UUID.randomUUID().toString(), paraMap.get("ProductName"));
			}
			sql.append(" \r\n  LEFT JOIN F_UNIT f on smd.productid = f.productid and smd.unitid = f.funitid  ");
			
			sql.append(" \r\n UNION ALL");
			//--退货单(入库)
			sql.append(" \r\n --退货单(入库)");
			sql.append(" \r\n select o.id as organid,o.organname,o.oecode,o.province,owd.productid,-(owd.takequantity * f.xquantity) as stockpile,0 as stockintransit,'' as warehousename,ow.INWAREHOUSEID as warehouseid, 0 as stockpiletoship from ORGAN_WITHDRAW ow  ");
			sql.append(" \r\n join take_ticket tt on tt.billno = ow.id and ow.iscomplete = 1");
			sql.append(" \r\n and tt.auditdate > to_date(?,'yyyy-MM-dd hh24:mi:ss')");
			param.put(UUID.randomUUID().toString(), paraMap.get("EndDate"));
			sql.append(" \r\n and tt.auditdate <  to_date(?,'yyyy-MM-dd hh24:mi:ss')");
			param.put(UUID.randomUUID().toString(), paraMap.get("historeyDate"));
			sql.append(" \r\n join ORGAN o on o.id = ow.receiveorganid ");
			if(!DbUtil.isDealer(users)) {
				sql.append(" \r\n and "+DbUtil.getWhereCondition(users, "o"));
			}
//			sql.append(" \r\n and ow.makedate > to_date('" + paraMap.get("EndDate") + "','yyyy-MM-dd hh24:mi:ss')");
			if(sysPro != null &&"1".equals(sysPro.getProperty("genReportOnlyForPD"))){ //是否只统计PD
				sql.append(" \r\n and o.organtype = 2 and o.organmodel = 1"); 
			}
			
			if(!StringUtil.isEmpty((String)paraMap.get("inOrganId"))){
				sql.append(" \r\n and ow.receiveorganid = ? "); //机构条件
				param.put(UUID.randomUUID().toString(), paraMap.get("inOrganId"));
			}
			if(!StringUtil.isEmpty((String)paraMap.get("region"))) {
				sql.append(" \r\n and o.province is not null and o.province in (select areaid from region_area where regioncodeid =  ?)"); //机构条件 
				param.put(UUID.randomUUID().toString(), paraMap.get("region"));
			}
			if(DbUtil.isDealer(users)) {
				sql.append(" \r\n  and ow.INWAREHOUSEID in (select wv.warehouse_Id from RULE_USER_WH wv where wv.user_Id=?) ");
				param.put(UUID.randomUUID().toString(), users.getUserid());
			}
			if(!StringUtil.isEmpty((String)paraMap.get("inWarehouseId"))){
				sql.append(" \r\n and ow.INWAREHOUSEID = ? "); //仓库条件
				param.put(UUID.randomUUID().toString(), paraMap.get("inWarehouseId"));
			}
			sql.append(" \r\n join ORGAN_WITHDRAW_DETAIL owd on ow.id = owd.owid  ");
			if(!StringUtil.isEmpty((String)paraMap.get("ProductName")) && !StringUtil.isEmpty((String)paraMap.get("packSizeName"))) { //产品条件
				sql.append(" \r\n and owd.productid in (select id from product where productname = ? and specmode= ?) ");
				param.put(UUID.randomUUID().toString(), paraMap.get("ProductName"));
				param.put(UUID.randomUUID().toString(), paraMap.get("packSizeName"));
			} else if(!StringUtil.isEmpty((String)paraMap.get("ProductName")) && StringUtil.isEmpty((String)paraMap.get("packSizeName"))) { //产品条件
				sql.append(" \r\n and owd.productid in (select id from product where productname = ?) ");
				param.put(UUID.randomUUID().toString(), paraMap.get("ProductName"));
			}
			sql.append(" \r\n  LEFT JOIN F_UNIT f on owd.productid = f.productid and owd.unitid = f.funitid   ");
			sql.append(" \r\n UNION ALL");
			//--退货单(出库)
			sql.append(" \r\n --退货单(出库)");
			sql.append(" \r\n select o.id as organid,o.organname,o.oecode,o.province,owd.productid,(owd.takequantity * f.xquantity) as stockpile,0 as stockintransit,'' as warehousename,ow.WAREHOUSEID as warehouseid, 0 as stockpiletoship from ORGAN_WITHDRAW ow  ");
			sql.append(" \r\n join ORGAN o on o.id = ow.porganid and ow.takestatus = 1");
			if(!DbUtil.isDealer(users)) {
				sql.append(" \r\n and "+DbUtil.getWhereCondition(users, "o"));
			}
//			sql.append(" \r\n and ow.receivedate >  to_date('" + paraMap.get("historeyDate") + "','yyyy-MM-dd hh24:mi:ss')");
			if(sysPro != null &&"1".equals(sysPro.getProperty("genReportOnlyForPD"))){ //是否只统计PD
				sql.append(" \r\n and o.organtype = 2 and o.organmodel = 1"); 
			}
			if(!StringUtil.isEmpty((String)paraMap.get("inOrganId"))){
				sql.append(" \r\n and ow.porganid = ? "); //机构条件
				param.put(UUID.randomUUID().toString(), paraMap.get("inOrganId"));
			}
			if(!StringUtil.isEmpty((String)paraMap.get("region"))) {
				sql.append(" \r\n and o.province is not null and o.province in (select areaid from region_area where regioncodeid =  ?)"); //机构条件 
				param.put(UUID.randomUUID().toString(), paraMap.get("region"));
			}
			if(DbUtil.isDealer(users)) {
				sql.append(" \r\n  and ow.WAREHOUSEID in (select wv.warehouse_Id from RULE_USER_WH wv where wv.user_Id=?) ");
				param.put(UUID.randomUUID().toString(), users.getUserid());
			}
			if(!StringUtil.isEmpty((String)paraMap.get("inWarehouseId"))){
				sql.append(" \r\n and ow.WAREHOUSEID = ? "); //仓库条件
				param.put(UUID.randomUUID().toString(), paraMap.get("inWarehouseId"));
			}
			sql.append(" \r\n join TAKE_TICKET tt on tt.billno = ow.id ");
			sql.append(" \r\n and tt.auditdate >  to_date(?,'yyyy-MM-dd hh24:mi:ss')");
			param.put(UUID.randomUUID().toString(), paraMap.get("EndDate"));
			sql.append(" \r\n and tt.auditdate <  to_date(?,'yyyy-MM-dd hh24:mi:ss')");
			param.put(UUID.randomUUID().toString(), paraMap.get("historeyDate"));
			sql.append(" \r\n join ORGAN_WITHDRAW_DETAIL owd on ow.id = owd.owid  ");
			if(!StringUtil.isEmpty((String)paraMap.get("ProductName")) && !StringUtil.isEmpty((String)paraMap.get("packSizeName"))) { //产品条件
				sql.append(" \r\n and owd.productid in (select id from product where productname = ? and specmode= ?) ");
				param.put(UUID.randomUUID().toString(), paraMap.get("ProductName"));
				param.put(UUID.randomUUID().toString(), paraMap.get("packSizeName"));
			} else if(!StringUtil.isEmpty((String)paraMap.get("ProductName")) && StringUtil.isEmpty((String)paraMap.get("packSizeName"))) { //产品条件
				sql.append(" \r\n and owd.productid in (select id from product where productname = ?) ");
				param.put(UUID.randomUUID().toString(), paraMap.get("ProductName"));
			}
			sql.append(" \r\n  LEFT JOIN F_UNIT f on owd.productid = f.productid and owd.unitid = f.funitid   ");
			sql.append(" \r\n UNION ALL");
			
			
			
			
			
			
			//-- 未签收的单据（在途库存）
			// --发货单
			sql.append(" \r\n --发货单（在途库存）");
			sql.append(" \r\n select o.id as organid,o.organname,o.oecode,o.province,samd.productid,0 as stockpile,(samd.takequantity * f.xquantity) as stockintransit,'' as warehousename,sam.INWAREHOUSEID as warehouseid, 0 as stockpiletoship  from STOCK_ALTER_MOVE sam ");
			sql.append(" \r\n join ORGAN o on o.id = sam.receiveorganid and sam.isshipment = 1 and sam.isblankout = 0 and sam.iscomplete = 0 ");
			if(!DbUtil.isDealer(users)) {
				sql.append(" \r\n and "+DbUtil.getWhereCondition(users, "o"));
			}
			sql.append(" \r\n and sam.movedate < to_date(?,'yyyy-MM-dd hh24:mi:ss')");
			param.put(UUID.randomUUID().toString(), paraMap.get("EndDate"));
			if(sysPro != null &&"1".equals(sysPro.getProperty("genReportOnlyForPD"))){ //是否只统计PD
				sql.append(" \r\n and o.organtype = 2 and o.organmodel = 1"); 
			}
			if(!StringUtil.isEmpty((String)paraMap.get("inOrganId"))){
				sql.append(" \r\n and sam.receiveorganid = ? "); //机构条件
				param.put(UUID.randomUUID().toString(), paraMap.get("inOrganId"));
			}
			if(!StringUtil.isEmpty((String)paraMap.get("region"))) {
				sql.append(" \r\n and o.province is not null and o.province in (select areaid from region_area where regioncodeid =  ?)"); //机构条件 
				param.put(UUID.randomUUID().toString(), paraMap.get("region"));
			}
			if(DbUtil.isDealer(users)) {
				sql.append(" \r\n  and sam.INWAREHOUSEID in (select wv.warehouse_Id from RULE_USER_WH wv where wv.user_Id=?) ");
				param.put(UUID.randomUUID().toString(), users.getUserid());
			}
			if(!StringUtil.isEmpty((String)paraMap.get("inWarehouseId"))){
				sql.append(" \r\n and sam.INWAREHOUSEID = ? "); //仓库条件
				param.put(UUID.randomUUID().toString(), paraMap.get("inWarehouseId"));
			}
			sql.append(" \r\n join STOCK_ALTER_MOVE_DETAIL samd on sam.id = samd.samid ");
			if(!StringUtil.isEmpty((String)paraMap.get("ProductName")) && !StringUtil.isEmpty((String)paraMap.get("packSizeName"))) { //产品条件
				sql.append(" \r\n and samd.productid in (select id from product where productname = ? and specmode= ?) ");
				param.put(UUID.randomUUID().toString(), paraMap.get("ProductName"));
				param.put(UUID.randomUUID().toString(), paraMap.get("packSizeName"));
			} else if(!StringUtil.isEmpty((String)paraMap.get("ProductName")) && StringUtil.isEmpty((String)paraMap.get("packSizeName"))) { //产品条件
				sql.append(" \r\n and samd.productid in (select id from product where productname = ?) ");
				param.put(UUID.randomUUID().toString(), paraMap.get("ProductName"));
			}
			sql.append(" \r\n  LEFT JOIN F_UNIT f on samd.productid = f.productid and samd.unitid = f.funitid  ");
			sql.append(" \r\n UNION ALL");
			//--转仓单
			sql.append(" \r\n --转仓单（在途库存）");
			sql.append(" \r\n select o.id as organid,o.organname,o.oecode,o.province,smd.productid,0 as stockpile,(smd.takequantity * f.xquantity) as stockintransit,'' as warehousename,sm.INWAREHOUSEID as warehouseid, 0 as stockpiletoship  from STOCK_MOVE sm ");
			sql.append(" \r\n join ORGAN o on o.id = sm.inorganid and sm.isshipment = 1 and sm.iscomplete = 0 and sm.isblankout = 0 and sm.inwarehouseid <> outwarehouseid");
			if(!DbUtil.isDealer(users)) {
				sql.append(" \r\n and "+DbUtil.getWhereCondition(users, "o"));
			}
			sql.append(" \r\n and sm.SHIPMENTDATE < to_date(?,'yyyy-MM-dd hh24:mi:ss')");
			param.put(UUID.randomUUID().toString(), paraMap.get("EndDate"));
			if(sysPro != null &&"1".equals(sysPro.getProperty("genReportOnlyForPD"))){ //是否只统计PD
				sql.append(" \r\n and o.organtype = 2 and o.organmodel = 1"); 
			}
			if(!StringUtil.isEmpty((String)paraMap.get("inOrganId"))){
				sql.append(" \r\n and sm.inorganid = ? "); //机构条件
				param.put(UUID.randomUUID().toString(), paraMap.get("inOrganId"));
			}
			if(!StringUtil.isEmpty((String)paraMap.get("region"))) {
				sql.append(" \r\n and o.province is not null and o.province in (select areaid from region_area where regioncodeid =  ?)"); //机构条件 
				param.put(UUID.randomUUID().toString(), paraMap.get("region"));
			}
			if(DbUtil.isDealer(users)) {
				sql.append(" \r\n  and sm.INWAREHOUSEID in (select wv.warehouse_Id from RULE_USER_WH wv where wv.user_Id=?) ");
				param.put(UUID.randomUUID().toString(), users.getUserid());
			}
			if(!StringUtil.isEmpty((String)paraMap.get("inWarehouseId"))){
				sql.append(" \r\n and sm.INWAREHOUSEID = ? "); //仓库条件
				param.put(UUID.randomUUID().toString(), paraMap.get("inWarehouseId"));
			}
			sql.append(" \r\n join STOCK_MOVE_DETAIL smd on sm.id = smd.smid  ");
			if(!StringUtil.isEmpty((String)paraMap.get("ProductName")) && !StringUtil.isEmpty((String)paraMap.get("packSizeName"))) { //产品条件
				sql.append(" \r\n and smd.productid in (select id from product where productname = ? and specmode= ?) ");
				param.put(UUID.randomUUID().toString(), paraMap.get("ProductName"));
				param.put(UUID.randomUUID().toString(), paraMap.get("packSizeName"));
			} else if(!StringUtil.isEmpty((String)paraMap.get("ProductName")) && StringUtil.isEmpty((String)paraMap.get("packSizeName"))) { //产品条件
				sql.append(" \r\n and smd.productid in (select id from product where productname = ?) ");
				param.put(UUID.randomUUID().toString(), paraMap.get("ProductName"));
			}
			sql.append(" \r\n  LEFT JOIN F_UNIT f on smd.productid = f.productid and smd.unitid = f.funitid  ");
			
			sql.append(" \r\n UNION ALL");
			// --退货单
			sql.append(" \r\n --退货单（在途库存）");
			sql.append(" \r\n select o.id as organid,o.organname,o.oecode,o.province,owd.productid,0 as stockpile,(owd.takequantity * f.xquantity) as stockintransit,'' as warehousename,ow.INWAREHOUSEID as warehouseid, 0 as stockpiletoship from ORGAN_WITHDRAW ow  ");
			sql.append(" \r\n join take_ticket tt on tt.billno = ow.id and ow.takestatus = 1 and ow.iscomplete = 0 and ow.isblankout = 0 ");
			sql.append(" \r\n and tt.auditdate < to_date(?,'yyyy-MM-dd hh24:mi:ss')");
			param.put(UUID.randomUUID().toString(), paraMap.get("EndDate"));
			sql.append(" \r\n join ORGAN o on o.id = ow.receiveorganid ");
			if(!DbUtil.isDealer(users)) {
				sql.append(" \r\n and "+DbUtil.getWhereCondition(users, "o"));
			}
			if(sysPro != null &&"1".equals(sysPro.getProperty("genReportOnlyForPD"))){ //是否只统计PD
				sql.append(" \r\n and o.organtype = 2 and o.organmodel = 1"); 
			}
			if(!StringUtil.isEmpty((String)paraMap.get("inOrganId"))){
				sql.append(" \r\n and ow.receiveorganid = ? "); //机构条件
				param.put(UUID.randomUUID().toString(), paraMap.get("inOrganId"));
			}
			if(!StringUtil.isEmpty((String)paraMap.get("region"))) {
				sql.append(" \r\n and o.province is not null and o.province in (select areaid from region_area where regioncodeid =  ?)"); //机构条件 
				param.put(UUID.randomUUID().toString(), paraMap.get("region"));
			}
			if(DbUtil.isDealer(users)) {
				sql.append(" \r\n  and ow.INWAREHOUSEID in (select wv.warehouse_Id from RULE_USER_WH wv where wv.user_Id=?) ");
				param.put(UUID.randomUUID().toString(), users.getUserid());
			}
			if(!StringUtil.isEmpty((String)paraMap.get("inWarehouseId"))){
				sql.append(" \r\n and ow.INWAREHOUSEID = ? "); //仓库条件
				param.put(UUID.randomUUID().toString(), paraMap.get("inWarehouseId"));
			}
			sql.append(" \r\n join ORGAN_WITHDRAW_DETAIL owd on ow.id = owd.owid  ");
			if(!StringUtil.isEmpty((String)paraMap.get("ProductName")) && !StringUtil.isEmpty((String)paraMap.get("packSizeName"))) { //产品条件
				sql.append(" \r\n and owd.productid in (select id from product where productname = ? and specmode= ?) ");
				param.put(UUID.randomUUID().toString(), paraMap.get("ProductName"));
				param.put(UUID.randomUUID().toString(), paraMap.get("packSizeName"));
			} else if(!StringUtil.isEmpty((String)paraMap.get("ProductName")) && StringUtil.isEmpty((String)paraMap.get("packSizeName"))) { //产品条件
				sql.append(" \r\n and owd.productid in (select id from product where productname = ?) ");
				param.put(UUID.randomUUID().toString(), paraMap.get("ProductName"));
			}
			sql.append(" \r\n  LEFT JOIN F_UNIT f on owd.productid = f.productid and owd.unitid = f.funitid   ");
			
			//#start modified by ryan.xi at 20150714
			//分包入库
			sql.append(" \r\n --分包入库");
			sql.append(" \r\n UNION ALL");
			sql.append(" \r\n select o.id as organid,o.organname,o.oecode,o.province,samd.inproductid,-(samd.inquantity) as stockpile,0 as stockintransit,'' as warehousename,sam.WAREHOUSEID as warehouseid, 0 as stockpiletoship from PACK_SEPARATE sam   ");
			sql.append(" \r\n join ORGAN o on o.id = sam.ORGANID and sam.ISAUDIT = 1 ");
			if(!DbUtil.isDealer(users)) {
				sql.append(" \r\n and "+DbUtil.getWhereCondition(users, "o"));
			}
			sql.append(" \r\n and sam.AUDITDATE > to_date(?,'yyyy-MM-dd hh24:mi:ss')");
			param.put(UUID.randomUUID().toString(), paraMap.get("EndDate"));
			sql.append(" \r\n and sam.AUDITDATE <  to_date(?,'yyyy-MM-dd hh24:mi:ss')");
			param.put(UUID.randomUUID().toString(), paraMap.get("historeyDate"));
//			sql.append(" \r\n and sam.BILLDATE > to_date('" + paraMap.get("EndDate") + "','yyyy-MM-dd hh24:mi:ss')");
			if(sysPro != null &&"1".equals(sysPro.getProperty("genReportOnlyForPD"))){ //是否只统计PD
				sql.append(" \r\n and o.organtype = 2 and o.organmodel = 1"); 
			}
			if(!StringUtil.isEmpty((String)paraMap.get("inOrganId"))){
				sql.append(" \r\n and sam.ORGANID = ? "); //机构条件
				param.put(UUID.randomUUID().toString(), paraMap.get("inOrganId"));
			}
			if(!StringUtil.isEmpty((String)paraMap.get("region"))) {
				sql.append(" \r\n and o.province is not null and o.province in (select areaid from region_area where regioncodeid =  ?)"); //机构条件 
				param.put(UUID.randomUUID().toString(), paraMap.get("region"));
			}
			if(DbUtil.isDealer(users)) {
				sql.append(" \r\n  and sam.WAREHOUSEID in (select wv.warehouse_Id from RULE_USER_WH wv where wv.user_Id=?) ");
				param.put(UUID.randomUUID().toString(), users.getUserid());
			}
			if(!StringUtil.isEmpty((String)paraMap.get("inWarehouseId"))){
				sql.append(" \r\n and sam.WAREHOUSEID = ? "); //仓库条件
				param.put(UUID.randomUUID().toString(), paraMap.get("inWarehouseId"));
			}
			sql.append(" \r\n join PACK_SEPARATE_DETAIL samd on sam.id = samd.psid ");
			if(!StringUtil.isEmpty((String)paraMap.get("ProductName")) && !StringUtil.isEmpty((String)paraMap.get("packSizeName"))) { //产品条件
				sql.append(" \r\n and samd.inproductid in (select id from product where productname = ? and specmode= ?) ");
				param.put(UUID.randomUUID().toString(), paraMap.get("ProductName"));
				param.put(UUID.randomUUID().toString(), paraMap.get("packSizeName"));
			} else if(!StringUtil.isEmpty((String)paraMap.get("ProductName")) && StringUtil.isEmpty((String)paraMap.get("packSizeName"))) { //产品条件
				sql.append(" \r\n and samd.inproductid in (select id from product where productname = ?) ");
				param.put(UUID.randomUUID().toString(), paraMap.get("ProductName"));
			}
			
			
			
			//分包出库
			sql.append(" \r\n UNION ALL");
			sql.append(" \r\n --分包出库");
			sql.append(" \r\n select o.id as organid,o.organname,o.oecode,o.province,samd.outproductid,samd.outquantity as stockpile,0 as stockintransit,'' as warehousename,sam.WAREHOUSEID as warehouseid, 0 as stockpiletoship from PACK_SEPARATE sam ");
			sql.append(" \r\n join ORGAN o on o.id = sam.ORGANID and sam.ISAUDIT = 1 ");
			if(!DbUtil.isDealer(users)) {
				sql.append(" \r\n and "+DbUtil.getWhereCondition(users, "o"));
			}
			sql.append(" \r\n and sam.AUDITDATE > to_date(?,'yyyy-MM-dd hh24:mi:ss')");
			param.put(UUID.randomUUID().toString(), paraMap.get("EndDate"));
			sql.append(" \r\n and sam.AUDITDATE <  to_date(?,'yyyy-MM-dd hh24:mi:ss')");
			param.put(UUID.randomUUID().toString(), paraMap.get("historeyDate"));
//			sql.append(" \r\n and sam.BILLDATE > to_date('" + paraMap.get("EndDate") + "','yyyy-MM-dd hh24:mi:ss')");
			if(sysPro != null &&"1".equals(sysPro.getProperty("genReportOnlyForPD"))){ //是否只统计PD
				sql.append(" \r\n and o.organtype = 2 and o.organmodel = 1"); 
			}
			if(!StringUtil.isEmpty((String)paraMap.get("inOrganId"))){
				sql.append(" \r\n and sam.ORGANID = ? "); //机构条件
				param.put(UUID.randomUUID().toString(), paraMap.get("inOrganId"));
			}
			if(!StringUtil.isEmpty((String)paraMap.get("region"))) {
				sql.append(" \r\n and o.province is not null and o.province in (select areaid from region_area where regioncodeid =  ?)"); //机构条件 
				param.put(UUID.randomUUID().toString(), paraMap.get("region"));
			}
			if(DbUtil.isDealer(users)) {
				sql.append(" \r\n  and sam.WAREHOUSEID in (select wv.warehouse_Id from RULE_USER_WH wv where wv.user_Id=?) ");
				param.put(UUID.randomUUID().toString(), users.getUserid());
			}
			if(!StringUtil.isEmpty((String)paraMap.get("inWarehouseId"))){
				sql.append(" \r\n and sam.WAREHOUSEID = ? "); //仓库条件
				param.put(UUID.randomUUID().toString(), paraMap.get("inWarehouseId"));
			}
			sql.append(" \r\n join PACK_SEPARATE_DETAIL samd on sam.id = samd.psid ");
			if(!StringUtil.isEmpty((String)paraMap.get("ProductName")) && !StringUtil.isEmpty((String)paraMap.get("packSizeName"))) { //产品条件
				sql.append(" \r\n and samd.outproductid in (select id from product where productname = ? and specmode= ?) ");
				param.put(UUID.randomUUID().toString(), paraMap.get("ProductName"));
				param.put(UUID.randomUUID().toString(), paraMap.get("packSizeName"));
			} else if(!StringUtil.isEmpty((String)paraMap.get("ProductName")) && StringUtil.isEmpty((String)paraMap.get("packSizeName"))) { //产品条件
				sql.append(" \r\n and samd.outproductid in (select id from product where productname = ?) ");
				param.put(UUID.randomUUID().toString(), paraMap.get("ProductName"));
			}
			
			//分包损耗
			sql.append(" \r\n UNION ALL");
			sql.append(" \r\n select o.id as organid,o.organname,o.oecode,o.province,samd.outproductid,samd.wastage as stockpile,0 as stockintransit,'' as warehousename,sam.WAREHOUSEID as warehouseid, 0 as stockpiletoship from PACK_SEPARATE sam ");
			sql.append(" \r\n join ORGAN o on o.id = sam.ORGANID and sam.ISAUDIT = 1 ");
			sql.append(" \r\n and sam.AUDITDATE > to_date(?,'yyyy-MM-dd hh24:mi:ss')");
			param.put(UUID.randomUUID().toString(), paraMap.get("EndDate"));
			sql.append(" \r\n and sam.AUDITDATE <  to_date(?,'yyyy-MM-dd hh24:mi:ss')");
			param.put(UUID.randomUUID().toString(), paraMap.get("historeyDate"));
			if(sysPro != null &&"1".equals(sysPro.getProperty("genReportOnlyForPD"))){ //是否只统计PD
				sql.append(" \r\n and o.organtype = 2 and o.organmodel = 1"); 
			}
			if(!StringUtil.isEmpty((String)paraMap.get("inOrganId"))){
				sql.append(" \r\n and sam.ORGANID = ? "); //机构条件
				param.put(UUID.randomUUID().toString(), paraMap.get("inOrganId"));
			}
			if(!StringUtil.isEmpty((String)paraMap.get("region"))) {
				sql.append(" \r\n and o.province is not null and o.province in (select areaid from region_area where regioncodeid =  ?)"); //机构条件 
				param.put(UUID.randomUUID().toString(), paraMap.get("region"));
			}
			sql.append(" \r\n  and sam.WAREHOUSEID in (select wv.warehouse_Id from RULE_USER_WH wv where wv.user_Id=?) ");
			param.put(UUID.randomUUID().toString(), users.getUserid());
			if(!StringUtil.isEmpty((String)paraMap.get("inWarehouseId"))){
				sql.append(" \r\n and sam.WAREHOUSEID = ? "); //仓库条件
				param.put(UUID.randomUUID().toString(), paraMap.get("inWarehouseId"));
			}
			sql.append(" \r\n join PACK_SEPARATE_DETAIL samd on sam.id = samd.psid ");
			if(!StringUtil.isEmpty((String)paraMap.get("ProductName")) && !StringUtil.isEmpty((String)paraMap.get("packSizeName"))) { //产品条件
				sql.append(" \r\n and samd.outproductid in (select id from product where productname = ? and specmode= ?) ");
				param.put(UUID.randomUUID().toString(), paraMap.get("ProductName"));
				param.put(UUID.randomUUID().toString(), paraMap.get("packSizeName"));
			} else if(!StringUtil.isEmpty((String)paraMap.get("ProductName")) && StringUtil.isEmpty((String)paraMap.get("packSizeName"))) { //产品条件
				sql.append(" \r\n and samd.outproductid in (select id from product where productname = ?) ");
				param.put(UUID.randomUUID().toString(), paraMap.get("ProductName"));
			}
			//#start modified by ryan.xi at 20150714
			
			//#start modified by ryan.xi at 20150805

			//其他入库-减库存-20150429
			sql.append(" \r\n UNION ALL ");
			sql.append(" \r\n --其他入库-减库存");
			sql.append(" \r\n select o.id as organid,o.organname,o.oecode,o.province,oida.productid,-(oida.quantity * f.xquantity) as stockpile,0 as stockintransit,'' as warehousename,oia.warehouseid as warehouseid, 0 as stockpiletoship ");
			sql.append(" \r\n from OTHER_INCOME_ALL oia INNER JOIN OTHER_INCOME_DETAIL_ALL oida on oia.id = oida.oiid and oia.isaudit = 1");
			if(!StringUtil.isEmpty((String)paraMap.get("inOrganId"))){
				sql.append(" \r\n and oia.organid = ? "); //机构条件
				param.put(UUID.randomUUID().toString(), paraMap.get("inOrganId"));
			}
			if(!StringUtil.isEmpty((String)paraMap.get("inWarehouseId"))){
				sql.append(" \r\n and oia.warehouseid = ? "); //仓库条件
				param.put(UUID.randomUUID().toString(), paraMap.get("inWarehouseId"));
			}
			if(!StringUtil.isEmpty((String)paraMap.get("ProductName")) && !StringUtil.isEmpty((String)paraMap.get("packSizeName"))) { //产品条件
				sql.append(" \r\n and oida.productid in (select id from product where productname = ? and packsizename= ?) ");
				param.put(UUID.randomUUID().toString(), paraMap.get("ProductName"));
				param.put(UUID.randomUUID().toString(), paraMap.get("packSizeName"));
			} else if(!StringUtil.isEmpty((String)paraMap.get("ProductName")) && StringUtil.isEmpty((String)paraMap.get("packSizeName"))) { //产品条件
				sql.append(" \r\n and oida.productid in (select id from product where productname = ?) ");
				param.put(UUID.randomUUID().toString(), paraMap.get("ProductName"));
			}
			if(!StringUtil.isEmpty((String)paraMap.get("EndDate"))){
				sql.append(" \r\n and oia.auditdate >=to_date(?,'yyyy-MM-dd hh24:mi:ss') "); //开始时间条件
				param.put(UUID.randomUUID().toString(), paraMap.get("EndDate"));
			}
			if(!StringUtil.isEmpty((String)paraMap.get("historeyDate"))){
				sql.append(" \r\n and oia.auditdate < to_date(?,'yyyy-MM-dd hh24:mi:ss') "); //结束时间条件
				param.put(UUID.randomUUID().toString(), paraMap.get("historeyDate"));
			}
			sql.append(" \r\n INNER JOIN ORGAN o on oia.organid= o.id");
			if(!DbUtil.isDealer(users)) {
				sql.append(" \r\n and "+DbUtil.getWhereCondition(users, "o"));
			}
			if(sysPro != null &&"1".equals(sysPro.getProperty("genReportOnlyForPD"))){ //是否只统计PD
				sql.append(" \r\n and o.organtype = 2 and o.organmodel = 1"); 
			}
			if(!StringUtil.isEmpty((String)paraMap.get("region"))) {
				sql.append(" \r\n and o.province is not null and o.province in (select areaid from region_area where regioncodeid =  ?)"); //机构条件 
				param.put(UUID.randomUUID().toString(), paraMap.get("region"));
			}
			sql.append(" \r\n LEFT JOIN F_UNIT f on oida.productid = f.productid and oida.unitid = f.funitid  "); 
			if(DbUtil.isDealer(users)) {
				sql.append(" \r\n INNER JOIN RULE_USER_WH rw on oia.warehouseid = rw.warehouse_id ");
				sql.append(" \r\n where rw.user_id = ?"); 
				param.put(UUID.randomUUID().toString(), users.getUserid());
			}
			
			//其他出库-加库存-20150429
			sql.append(" \r\n UNION ALL ");
			sql.append(" \r\n --其他出库-加库存");
			sql.append(" \r\n select o.id as organid,o.organname,o.oecode,o.province,oida.productid,(oida.quantity * f.xquantity) as stockpile,0 as stockintransit,'' as warehousename,oia.warehouseid as warehouseid, 0 as stockpiletoship ");
			sql.append(" \r\n from OTHER_SHIPMENT_BILL_ALL oia INNER JOIN OTHER_SHIPMENT_BILL_D_ALL oida on oia.id = oida.osid and oia.isaudit = 1");
			if(!StringUtil.isEmpty((String)paraMap.get("inOrganId"))){
				sql.append(" \r\n and oia.organid = ? "); //机构条件
				param.put(UUID.randomUUID().toString(), paraMap.get("inOrganId"));
			}
			if(!StringUtil.isEmpty((String)paraMap.get("inWarehouseId"))){
				sql.append(" \r\n and oia.warehouseid = ? "); //仓库条件
				param.put(UUID.randomUUID().toString(), paraMap.get("inWarehouseId"));
			}
			if(!StringUtil.isEmpty((String)paraMap.get("ProductName")) && !StringUtil.isEmpty((String)paraMap.get("packSizeName"))) { //产品条件
				sql.append(" \r\n and oida.productid in (select id from product where productname = ? and packsizename= ?) ");
				param.put(UUID.randomUUID().toString(), paraMap.get("ProductName"));
				param.put(UUID.randomUUID().toString(), paraMap.get("packSizeName"));
			} else if(!StringUtil.isEmpty((String)paraMap.get("ProductName")) && StringUtil.isEmpty((String)paraMap.get("packSizeName"))) { //产品条件
				sql.append(" \r\n and oida.productid in (select id from product where productname = ?) ");
				param.put(UUID.randomUUID().toString(), paraMap.get("ProductName"));
			}
			if(!StringUtil.isEmpty((String)paraMap.get("EndDate"))){
				sql.append(" \r\n and oia.auditdate >=to_date(?,'yyyy-MM-dd hh24:mi:ss') "); //开始时间条件
				param.put(UUID.randomUUID().toString(), paraMap.get("EndDate"));
			}
			if(!StringUtil.isEmpty((String)paraMap.get("historeyDate"))){
				sql.append(" \r\n and oia.auditdate < to_date(?,'yyyy-MM-dd hh24:mi:ss') "); //结束时间条件
				param.put(UUID.randomUUID().toString(), paraMap.get("historeyDate"));
			}
			sql.append(" \r\n INNER JOIN ORGAN o on oia.organid= o.id");
			if(!DbUtil.isDealer(users)) {
				sql.append(" \r\n and "+DbUtil.getWhereCondition(users, "o"));
			}
			if(sysPro != null && "1".equals(sysPro.getProperty("genReportOnlyForPD"))){ //是否只统计PD
				sql.append(" \r\n and o.organtype = 2 and o.organmodel = 1"); 
			}
			if(!StringUtil.isEmpty((String)paraMap.get("region"))) {
				sql.append(" \r\n and o.province is not null and o.province in (select areaid from region_area where regioncodeid =  ?)"); //机构条件 
				param.put(UUID.randomUUID().toString(), paraMap.get("region"));
			}
			sql.append(" \r\n LEFT JOIN F_UNIT f on oida.productid = f.productid and oida.unitid = f.funitid  "); 
			if(DbUtil.isDealer(users)) {
				sql.append(" \r\n INNER JOIN RULE_USER_WH rw on oia.warehouseid = rw.warehouse_id ");
				sql.append(" \r\n where rw.user_id = ?"); 
				param.put(UUID.randomUUID().toString(), users.getUserid());
			}
			//#end modified by ryan.xi at 20150805
			
		}
		
		sql.append(" \r\n ) GROUP BY  organid,productid,warehousid ");
		sql.append(" \r\n ) where  (stockpile <> 0 or stockintransit <> 0 or stockpiletoship <> 0 or stockpiletoship <> 0) and warehousid not in (select id from WAREHOUSE where USEFLAG = 0)");
		sql.append(" \r\n and  productid not in (select id from product where USEFLAG = 0) ");
		System.out.println(sql.toString());	
		List<Map> list =  new ArrayList<Map>();
		if(request == null && pageSize == 0){
			list =  EntityManager.jdbcquery(sql.toString(), param);
		}else {
			list =  PageQuery.jdbcSqlserverQuery(request, "organid,productid", sql.toString(), pageSize, param);
		}
		if(list != null && list.size()>0){
			Map<String,FUnit>  funitMap = appFUnit.getAllMap(); //获取所有的单位换算信息
			Map<Integer, String> countUnitMap = appBr.getBaseResourceMap("CountUnit");
			Map<Integer, String> provinceMap = appCountryArea.getProvinceMap();
			for(Map map : list){
				PDInventoryReport report = new PDInventoryReport();
				//将Map中对应的值赋值给实例
				MapUtil.mapToObject(map, report);
				report.setViewDate(((String)paraMap.get("EndDate")).substring(0, 10));
				Product product = appProduct.getProductByID((String)map.get("productid"));
				report.setmCode(product.getmCode());
				report.setProductName(product.getMatericalChDes());
				report.setProductNameen(product.getMatericalEnDes());
				report.setPackSizeName(product.getSpecmode());
				report.setProductChnName(product.getProductname());
				report.setProductEngName(product.getProductnameen());
				report.setPackSizeNameEn(product.getPackSizeNameEn());
				
				if(!StringUtil.isEmpty((String)paraMap.get("psname"))) {
					report.setSortName((String)paraMap.get("psname"));
				} else {
					ProductStruct productStruct = appProductStruct.getProductStructById(product.getPsid());
					report.setSortName(productStruct.getSortname());
				}
				
				if(!StringUtil.isEmpty((String)map.get("province"))) {
					report.setAreaName(provinceMap.get(Integer.parseInt((String)map.get("province"))));
				}
				double stockpile = Double.valueOf((String)map.get("stockpile"));
				double stockInTransit = Double.valueOf((String)map.get("stockintransit"));
				double stockToShip = Double.valueOf((String)map.get("stockpiletoship"));
				if(StringUtil.isEmpty((String)paraMap.get("countByUnit"))) {
					//检查单位是否可以正常转化,如不能则不转化
					if(checkRate(product.getId(),product.getSunit(),Constants.DEFAULT_UNIT_ID, funitMap)){
						stockpile = changeUnit(product.getId(), product.getSunit(), stockpile, Constants.DEFAULT_UNIT_ID,funitMap);
						report.setStockpile(decimalFormat.format(stockpile));
						stockInTransit = changeUnit(product.getId(), product.getSunit(), stockInTransit, Constants.DEFAULT_UNIT_ID,funitMap);
						report.setStockInTransit(decimalFormat.format(stockInTransit));
						stockToShip = changeUnit(product.getId(), product.getSunit(), stockToShip, Constants.DEFAULT_UNIT_ID,funitMap);
						report.setStockpileToShip(decimalFormat.format(stockToShip));
						report.setTotalStockpile(decimalFormat.format(ArithDouble.add(ArithDouble.add(stockpile, stockInTransit), stockToShip)));
						report.setUnit(countUnitMap.get(Constants.DEFAULT_UNIT_ID));
					}
				} else {
					//转换成计量单位
					if(product.getBoxquantity() != null && product.getBoxquantity() != 0d) {
						stockpile = ArithDouble.mul(stockpile, product.getBoxquantity());
						report.setStockpile(decimalFormat.format(stockpile));
						stockInTransit = ArithDouble.mul(stockInTransit, product.getBoxquantity());
						report.setStockInTransit(decimalFormat.format(stockInTransit));
						stockToShip = ArithDouble.mul(stockToShip, product.getBoxquantity());
						report.setStockpileToShip(decimalFormat.format(stockToShip));
						report.setTotalStockpile(decimalFormat.format(ArithDouble.add(ArithDouble.add(stockpile, stockInTransit), stockToShip)));
						report.setUnit(countUnitMap.get(product.getCountunit()));
					}
				}
				
				List<String> regionNames = appRegion.getRegionSortNameByAreaId((String)map.get("province"));
				if(regionNames != null && regionNames.size() > 0) {
					report.setRegionName(regionNames.get(0));
				}
				if(!StringUtil.isEmpty((String)map.get("warehousename"))) {
					report.setWarehouseName((String)map.get("warehousename"));
				} else {
					report.setWarehouseName(warehouseService.getWarehouseName((String)map.get("warehousid")));
				}
				
				resultList.add(report);
			}
		}
		return resultList;
	}
	
	// 根据条件查询
	public List<PDInventoryReport> queryReport(HttpServletRequest request, int pageSize,Map paraMap,UsersBean users) throws Exception{
		
		List<PDInventoryReport> inventoryList = new ArrayList<PDInventoryReport>();
		//查看是否有统计过历史数据
		BaseResource inventoryBr = appBr.getBaseResourceValue("InventoryDetailDate", 1);
		if(inventoryBr == null) {
			return inventoryList;
		}
		String endDateStr = (String)paraMap.get("EndDate");
		//当前日期是否已统计过历史数据
		if(endDateStr.compareTo(inventoryBr.getTagsubvalue()) < 0) {
			Calendar endDate = Calendar.getInstance();
			endDate.setTime(Dateutil.StringToDate(endDateStr));
			//是否是月末
			if(isLastDayOfMonth(endDate)) {
				return getInventoryHistory(endDate,request,pageSize,paraMap,users);
			} else {
				return getInventorySalesConsumeHistory(endDate,request,pageSize,paraMap,users);
			}
		} else {
			return getInventoryHistoryAndSalesConsume(inventoryBr.getTagsubvalue(),request,pageSize,paraMap,users);
		}
	}
	
	private List<PDInventoryReport> getInventoryHistoryAndSalesConsume(
			String historyDateStr, HttpServletRequest request, int pageSize,
			Map<String,String> paraMap, UsersBean users) throws Exception {
		Map<String, Object> param = new LinkedHashMap<String, Object>();
		Calendar historyDate = Calendar.getInstance();
		historyDate.setTime(Dateutil.StringToDate(historyDateStr));
		historyDate.add(Calendar.MONTH, -1);
		//历史库存年月
		int year = historyDate.get(Calendar.YEAR);
		int month = historyDate.get(Calendar.MONTH)+1;
		String endDate = paraMap.get("EndDate") + " 23:59:59";
		List<PDInventoryReport> resultList = new ArrayList<PDInventoryReport>();
		
		StringBuffer sql = new StringBuffer();
		sql.append("select r.sortname regionName,ca.areaname,o.id organId,o.organname,o.oecode,w.id wid,w.warehousename,p.id proid,p.mcode,p.productName productChnName,p.productNameen productEngName,p.matericalchdes ProductName,p.matericalendes ProductNameen,p.Specmode PackSizeName,p.PackSizeNameEn,p.sunit,idh.stockpile,idh.stockpileToShip,idh.stockInTransit stockInTransit,ps.sortname,p.boxquantity,p.countunit,idh.productid from ");
		sql.append("(select organid,productid,sum(stockpile) stockpile,sum(stockpileToShip) stockpileToShip, sum(stockpileInTransit) stockInTransit,WAREHOUSEID from ( ");
		sql.append("select organid,productid,stockpile,stockpileToShip, stockpileInTransit,WAREHOUSEID from INVENTORY_DETAIL_HISTORY ");
		sql.append("where year=? and month=? ");
		param.put(UUID.randomUUID().toString(), year);
		param.put(UUID.randomUUID().toString(), month);
		if(!StringUtil.isEmpty(paraMap.get("inOrganId"))){
			sql.append(" \r\n and ORGANID = ? "); //机构条件
			param.put(UUID.randomUUID().toString(), paraMap.get("inOrganId"));
		}
		if(!StringUtil.isEmpty(paraMap.get("inWarehouseId"))){
			sql.append(" \r\n and WAREHOUSEID = ? "); //仓库条件
			param.put(UUID.randomUUID().toString(), paraMap.get("inWarehouseId"));
		}
		if(DbUtil.isDealer(users)) {
			sql.append(" and warehouseid in (select wv.warehouse_Id from RULE_USER_WH wv where wv.user_Id=?)");
			param.put(UUID.randomUUID().toString(), users.getUserid());
		}
		sql.append(" \r\n UNION ALL ");
		sql.append(" \r\n --工厂给PD的数量");
		sql.append(" \r\n select tt.inoid oid,ttd.productid pid ,case when SAM.ISCOMPLETE=1 then ttd.quantity * f.xquantity else 0 end quantity,0,case when SAM.ISCOMPLETE<>1 then ttd.quantity * f.xquantity else 0 end,w.id wid  ");
		sql.append(" \r\n from TAKE_TICKET tt INNER JOIN TAKE_TICKET_DETAIL ttd on tt.id = ttd.ttid  and tt.isaudit = 1  and tt.bsort = 1 ");
		if(!StringUtil.isEmpty(paraMap.get("inOrganId"))){
			sql.append(" \r\n and tt.inoid = ? "); //机构条件
			param.put(UUID.randomUUID().toString(), paraMap.get("inOrganId"));
		}
		if(!StringUtil.isEmpty(paraMap.get("inWarehouseId"))){
			sql.append(" \r\n and tt.inwarehouseid = ? "); //仓库条件
			param.put(UUID.randomUUID().toString(), paraMap.get("inWarehouseId"));
		}
		if(!StringUtil.isEmpty(paraMap.get("ProductName")) && !StringUtil.isEmpty(paraMap.get("packSizeName"))) { //产品条件
			sql.append(" \r\n and ttd.productid in (select id from product where productname = ? and specmode= ?) ");
			param.put(UUID.randomUUID().toString(), paraMap.get("ProductName"));
			param.put(UUID.randomUUID().toString(), paraMap.get("packSizeName"));
		} else if(!StringUtil.isEmpty(paraMap.get("ProductName")) && StringUtil.isEmpty(paraMap.get("packSizeName"))) { //产品条件
			sql.append(" \r\n and ttd.productid in (select id from product where productname = ?) ");
			param.put(UUID.randomUUID().toString(), paraMap.get("ProductName"));
		}
		//过滤拒签的单据
		sql.append(" \r\n INNER JOIN STOCK_ALTER_MOVE sam on sam.id = tt.billno and sam.isblankout = 0");
		if(!StringUtil.isEmpty(historyDateStr)){
			sql.append(" \r\n and sam.movedate >=to_date(?,'yyyy-MM-dd hh24:mi:ss') "); //开始时间条件
			param.put(UUID.randomUUID().toString(), historyDateStr);
		}
		if(!StringUtil.isEmpty(endDate)){
			sql.append(" \r\n and sam.movedate < to_date(?,'yyyy-MM-dd hh24:mi:ss') "); //结束时间条件
			param.put(UUID.randomUUID().toString(), endDate);
		}
		sql.append(" \r\n INNER JOIN ORGAN o on tt.oid = o.id and o.organtype = 1  "); // 出库仓库为工厂
		sql.append(" \r\n INNER JOIN ORGAN ino on tt.inoid = ino.id and ino.organtype = 2 and ino.organmodel = 1"); // 出库机构为PD
		sql.append(" \r\n join WAREHOUSE w on tt.inwarehouseid = w.id and w.USEFLAG = 1  ");
		sql.append(" \r\n LEFT JOIN F_UNIT f on ttd.productid = f.productid and ttd.unitid = f.funitid ");
		if(DbUtil.isDealer(users)) {
			sql.append(" \r\n LEFT JOIN RULE_USER_WH rw on tt.inwarehouseid = rw.warehouse_id  ");
			sql.append(" \r\n where rw.user_id = ?");
			param.put(UUID.randomUUID().toString(), users.getUserid());
		} else {
			sql.append(" \r\n where "+DbUtil.getWhereCondition(users, "ino"));
		}
		
		sql.append(" \r\n UNION ALL ");
		
		sql.append(" \r\n --PD调拨回来的");
		sql.append(" \r\n select tt.inoid oid,ttd.productid pid ,case when sm.ISCOMPLETE=1 then ttd.realquantity * f.xquantity else 0 end quantity,0,case when sm.ISCOMPLETE<>1 then ttd.realquantity * f.xquantity else 0 end,w.id wid  ");
		sql.append(" \r\n from TAKE_TICKET tt INNER JOIN TAKE_TICKET_DETAIL ttd on tt.id = ttd.ttid  and tt.isaudit = 1  and tt.bsort = 2 ");
		if(!StringUtil.isEmpty(paraMap.get("inOrganId"))){
			sql.append(" \r\n and tt.inoid = ? "); //机构条件
			param.put(UUID.randomUUID().toString(), paraMap.get("inOrganId"));
		}
		if(!StringUtil.isEmpty(paraMap.get("inWarehouseId"))){
			sql.append(" \r\n and tt.inwarehouseid = ? "); //仓库条件
			param.put(UUID.randomUUID().toString(), paraMap.get("inWarehouseId"));
		}
		if(!StringUtil.isEmpty(paraMap.get("ProductName")) && !StringUtil.isEmpty(paraMap.get("packSizeName"))) { //产品条件
			sql.append(" \r\n and ttd.productid in (select id from product where productname = ? and specmode= ?) ");
			param.put(UUID.randomUUID().toString(), paraMap.get("ProductName"));
			param.put(UUID.randomUUID().toString(), paraMap.get("packSizeName"));
		} else if(!StringUtil.isEmpty(paraMap.get("ProductName")) && StringUtil.isEmpty(paraMap.get("packSizeName"))) { //产品条件
			sql.append(" \r\n and ttd.productid in (select id from product where productname = ?) ");
			param.put(UUID.randomUUID().toString(), paraMap.get("ProductName"));
		}
		sql.append(" \r\n INNER JOIN STOCK_MOVE sm on sm.id = tt.billno");
		if(!StringUtil.isEmpty(historyDateStr)){
			sql.append(" \r\n and tt.auditdate >=to_date(?,'yyyy-MM-dd hh24:mi:ss') "); //开始时间条件
			param.put(UUID.randomUUID().toString(), historyDateStr);
		}
		if(!StringUtil.isEmpty(endDate)){
			sql.append(" \r\n and tt.auditdate < to_date(?,'yyyy-MM-dd hh24:mi:ss') "); //结束时间条件
			param.put(UUID.randomUUID().toString(), endDate);
		}
		sql.append(" \r\n INNER JOIN ORGAN o on tt.oid = o.id and o.organtype = 2 and o.organmodel = 1"); // 出库机构为PD
		sql.append(" \r\n join WAREHOUSE w on tt.inwarehouseid = w.id and w.USEFLAG = 1  ");
		sql.append(" \r\n LEFT JOIN F_UNIT f on ttd.productid = f.productid and ttd.unitid = f.funitid ");
		if(DbUtil.isDealer(users)) {
			sql.append(" \r\n LEFT JOIN RULE_USER_WH rw on tt.inwarehouseid = rw.warehouse_id  ");
			sql.append(" \r\n where rw.user_id = ?");
			param.put(UUID.randomUUID().toString(), users.getUserid());
		} else {
			sql.append(" \r\n where "+DbUtil.getWhereCondition(users, "o"));
		}
		
		sql.append(" \r\n UNION ALL ");
		//调拨给其他PD的
		sql.append(" \r\n --调拨给其他PD的");
		sql.append(" \r\n select tt.oid,ttd.productid pid ,-(ttd.realquantity * f.xquantity) quantity,0,0,w.id wid    ");
		sql.append(" \r\n from TAKE_TICKET tt INNER JOIN TAKE_TICKET_DETAIL ttd on tt.id = ttd.ttid  and tt.isaudit = 1  and tt.bsort = 2 ");
		if(!StringUtil.isEmpty(paraMap.get("inOrganId"))){
			sql.append(" \r\n and tt.oid = ? "); //机构条件
			param.put(UUID.randomUUID().toString(), paraMap.get("inOrganId"));
		}
		if(!StringUtil.isEmpty(paraMap.get("inWarehouseId"))){
			sql.append(" \r\n and tt.warehouseid = ? "); //仓库条件
			param.put(UUID.randomUUID().toString(), paraMap.get("inWarehouseId"));
		}
		if(!StringUtil.isEmpty(paraMap.get("ProductName")) && !StringUtil.isEmpty(paraMap.get("packSizeName"))) { //产品条件
			sql.append(" \r\n and ttd.productid in (select id from product where productname = ? and specmode= ?) ");
			param.put(UUID.randomUUID().toString(), paraMap.get("ProductName"));
			param.put(UUID.randomUUID().toString(), paraMap.get("packSizeName"));
		} else if(!StringUtil.isEmpty(paraMap.get("ProductName")) && StringUtil.isEmpty(paraMap.get("packSizeName"))) { //产品条件
			sql.append(" \r\n and ttd.productid in (select id from product where productname = ?) ");
			param.put(UUID.randomUUID().toString(), paraMap.get("ProductName"));
		}
		sql.append(" \r\n INNER JOIN STOCK_MOVE sm on sm.id = tt.billno");
		if(!StringUtil.isEmpty(historyDateStr)){
			sql.append(" \r\n and tt.auditdate >=to_date(?,'yyyy-MM-dd hh24:mi:ss') "); //开始时间条件
			param.put(UUID.randomUUID().toString(), historyDateStr);
		}
		if(!StringUtil.isEmpty(endDate)){
			sql.append(" \r\n and tt.auditdate < to_date(?,'yyyy-MM-dd hh24:mi:ss') "); //结束时间条件
			param.put(UUID.randomUUID().toString(), endDate);
		}
		sql.append(" \r\n INNER JOIN ORGAN o on tt.inoid = o.id and o.organtype = 2 and  o.organmodel = 1 "); // 收货机构为PD
		sql.append(" \r\n join WAREHOUSE w on tt.warehouseid = w.id and w.USEFLAG = 1  ");
		sql.append(" \r\n LEFT JOIN F_UNIT f on ttd.productid = f.productid and ttd.unitid = f.funitid ");
		if(DbUtil.isDealer(users)) {
			sql.append(" \r\n LEFT JOIN RULE_USER_WH rw on tt.warehouseid = rw.warehouse_id  ");
			sql.append(" \r\n where rw.user_id = ?");
			param.put(UUID.randomUUID().toString(), users.getUserid());
		} else {
			sql.append(" \r\n where "+DbUtil.getWhereCondition(users, "o"));
		}
		
		
		//#start modified by ryan.xi at 20150714
		//扣减分包时大包的销售数量
		sql.append(" \r\n UNION ALL ");
		sql.append(" \r\n --扣减分包时大包的销售数量");
		sql.append(" \r\n select tt.ORGANID oid,ttd.OUTPRODUCTID pid ,-(OUTQUANTITY-ttd.wastage) quantity,0,0,w.id wid  ");
		sql.append(" \r\n from PACK_SEPARATE tt INNER JOIN PACK_SEPARATE_DETAIL ttd on tt.id = ttd.psid  and tt.isaudit = 1 ");
		if(!StringUtil.isEmpty(paraMap.get("inOrganId"))){
			sql.append(" \r\n and tt.ORGANID = ? "); //机构条件
			param.put(UUID.randomUUID().toString(), paraMap.get("inOrganId"));
		}
		if(!StringUtil.isEmpty(paraMap.get("inWarehouseId"))){
			sql.append(" \r\n and tt.WAREHOUSEID = ? "); //仓库条件
			param.put(UUID.randomUUID().toString(), paraMap.get("inWarehouseId"));
		}
		if(!StringUtil.isEmpty(paraMap.get("ProductName")) && !StringUtil.isEmpty(paraMap.get("packSizeName"))) { //产品条件
			sql.append(" \r\n and ttd.OUTPRODUCTID in (select id from product where productname = ? and specmode= ?) ");
			param.put(UUID.randomUUID().toString(), paraMap.get("ProductName"));
			param.put(UUID.randomUUID().toString(), paraMap.get("packSizeName"));
		} else if(!StringUtil.isEmpty(paraMap.get("ProductName")) && StringUtil.isEmpty(paraMap.get("packSizeName"))) { //产品条件
			sql.append(" \r\n and ttd.OUTPRODUCTID in (select id from product where productname = ?) ");
			param.put(UUID.randomUUID().toString(), paraMap.get("ProductName"));
		}
		if(!StringUtil.isEmpty(historyDateStr)){
			sql.append(" \r\n and tt.auditdate >=to_date(?,'yyyy-MM-dd hh24:mi:ss') "); //开始时间条件
			param.put(UUID.randomUUID().toString(), historyDateStr);
		}
		if(!StringUtil.isEmpty(endDate)){
			sql.append(" \r\n and tt.auditdate < to_date(?,'yyyy-MM-dd hh24:mi:ss') "); //结束时间条件
			param.put(UUID.randomUUID().toString(), endDate);
		}
		sql.append(" \r\n join WAREHOUSE w on tt.warehouseid = w.id and w.USEFLAG = 1  ");
		if(DbUtil.isDealer(users)) {
			sql.append(" \r\n JOIN RULE_USER_WH rw on tt.WAREHOUSEID = rw.warehouse_id ");
			sql.append(" \r\n where rw.user_id = ?");
			param.put(UUID.randomUUID().toString(), users.getUserid());
		} else {
			sql.append(" \r\n JOIN Organ o on o.id = tt.ORGANID ");
			sql.append(" \r\n where "+DbUtil.getWhereCondition(users, "o"));
		}
		
		
		//增加分包时小包的销售数量
		sql.append(" \r\n UNION ALL ");
		sql.append(" \r\n --增加分包时小包的销售数量");
		sql.append(" \r\n select tt.ORGANID oid,ttd.INPRODUCTID pid ,INQUANTITY quantity,0,0,w.id wid  ");
		sql.append(" \r\n from PACK_SEPARATE tt INNER JOIN PACK_SEPARATE_DETAIL ttd on tt.id = ttd.psid  and tt.isaudit = 1 ");
		if(!StringUtil.isEmpty(paraMap.get("inOrganId"))){
			sql.append(" \r\n and tt.ORGANID = ? "); //机构条件
			param.put(UUID.randomUUID().toString(), paraMap.get("inOrganId"));
		}
		if(!StringUtil.isEmpty(paraMap.get("inWarehouseId"))){
			sql.append(" \r\n and tt.WAREHOUSEID = ? "); //仓库条件
			param.put(UUID.randomUUID().toString(), paraMap.get("inWarehouseId"));
		}
		if(!StringUtil.isEmpty(paraMap.get("ProductName")) && !StringUtil.isEmpty(paraMap.get("packSizeName"))) { //产品条件
			sql.append(" \r\n and ttd.INPRODUCTID in (select id from product where productname = ? and specmode= ?) ");
			param.put(UUID.randomUUID().toString(), paraMap.get("ProductName"));
			param.put(UUID.randomUUID().toString(), paraMap.get("packSizeName"));
		} else if(!StringUtil.isEmpty(paraMap.get("ProductName")) && StringUtil.isEmpty(paraMap.get("packSizeName"))) { //产品条件
			sql.append(" \r\n and ttd.INPRODUCTID in (select id from product where productname = ?) ");
			param.put(UUID.randomUUID().toString(), paraMap.get("ProductName"));
		}
		if(!StringUtil.isEmpty(historyDateStr)){
			sql.append(" \r\n and tt.auditdate >=to_date(?,'yyyy-MM-dd hh24:mi:ss') "); //开始时间条件
			param.put(UUID.randomUUID().toString(), historyDateStr);
		}
		if(!StringUtil.isEmpty(endDate)){
			sql.append(" \r\n and tt.auditdate < to_date(?,'yyyy-MM-dd hh24:mi:ss') "); //结束时间条件
			param.put(UUID.randomUUID().toString(), endDate);
		}
		sql.append(" \r\n join WAREHOUSE w on tt.warehouseid = w.id and w.USEFLAG = 1  ");
		if(DbUtil.isDealer(users)) {
			sql.append(" \r\n JOIN RULE_USER_WH rw on tt.WAREHOUSEID = rw.warehouse_id ");
			sql.append(" \r\n where rw.user_id = ?");
			param.put(UUID.randomUUID().toString(), users.getUserid());
		} else {
			sql.append(" \r\n JOIN Organ o on o.id = tt.ORGANID ");
			sql.append(" \r\n where "+DbUtil.getWhereCondition(users, "o"));
		}
		//#end modified by ryan.xi at 20150714
		
		//#start modified by ryan at 20150805
		//采购入库-加销售
		sql.append(" \r\n UNION ALL ");
		sql.append(" \r\n --采购入库-加销售");
		sql.append(" \r\n select oia.organid, oida.productid pid, (oida.quantity * f.xquantity) quantity,0,0,w.id wid   ");
		sql.append(" \r\n from OTHER_INCOME_ALL oia INNER JOIN OTHER_INCOME_DETAIL_ALL oida on oia.id = oida.oiid and oia.isaudit = 1 and oia.incomesort = 6");
		if(!StringUtil.isEmpty(paraMap.get("inOrganId"))){
			sql.append(" \r\n and oia.organid = ? "); //机构条件
			param.put(UUID.randomUUID().toString(), paraMap.get("inOrganId"));
		}
		if(!StringUtil.isEmpty(paraMap.get("inWarehouseId"))){
			sql.append(" \r\n and oia.warehouseid = ? "); //仓库条件
			param.put(UUID.randomUUID().toString(), paraMap.get("inWarehouseId"));
		}
		if(!StringUtil.isEmpty(paraMap.get("ProductName")) && !StringUtil.isEmpty(paraMap.get("packSizeName"))) { //产品条件
			sql.append(" \r\n and oida.productid in (select id from product where productname = ? and packsizename= ?) ");
			param.put(UUID.randomUUID().toString(), paraMap.get("ProductName"));
			param.put(UUID.randomUUID().toString(), paraMap.get("packSizeName"));
		} else if(!StringUtil.isEmpty(paraMap.get("ProductName")) && StringUtil.isEmpty(paraMap.get("packSizeName"))) { //产品条件
			sql.append(" \r\n and oida.productid in (select id from product where productname = ?) ");
			param.put(UUID.randomUUID().toString(), paraMap.get("ProductName"));
		}
		if(!StringUtil.isEmpty(historyDateStr)){
			sql.append(" \r\n and oia.auditdate >=to_date(?,'yyyy-MM-dd hh24:mi:ss') "); //开始时间条件
			param.put(UUID.randomUUID().toString(), historyDateStr);
		}
		if(!StringUtil.isEmpty(endDate)){
			sql.append(" \r\n and oia.auditdate < to_date(?,'yyyy-MM-dd hh24:mi:ss') "); //结束时间条件
			param.put(UUID.randomUUID().toString(), endDate);
		}
		sql.append(" \r\n INNER JOIN ORGAN o on oia.organid= o.id and o.organtype = 2 and  organmodel = 1 ");
		sql.append(" \r\n join WAREHOUSE w on oia.warehouseid = w.id and w.USEFLAG = 1  ");
		sql.append(" \r\n LEFT JOIN F_UNIT f on oida.productid = f.productid and oida.unitid = f.funitid  "); 
		if(DbUtil.isDealer(users)) {
			sql.append(" \r\n INNER JOIN RULE_USER_WH rw on oia.warehouseid = rw.warehouse_id ");
			sql.append(" \r\n where rw.user_id = ?"); 
			param.put(UUID.randomUUID().toString(), users.getUserid());
		} else {
			sql.append(" \r\n where "+DbUtil.getWhereCondition(users, "o"));
		}
		
		sql.append(" \r\n UNION ALL ");
		sql.append(" \r\n --退回工厂");
		
		sql.append(" \r\n select tt.oid oid,ttd.productid pid ,-(ttd.realquantity * f.xquantity) quantity,0,0,w.id wid  ");
		sql.append(" \r\n from TAKE_TICKET tt INNER JOIN TAKE_TICKET_DETAIL ttd on tt.id = ttd.ttid  and tt.isaudit = 1  and tt.bsort = 7 and tt.billno like 'PW%' ");
		if(!StringUtil.isEmpty(paraMap.get("inOrganId"))){
			sql.append(" \r\n and tt.oid = ? "); //机构条件
			param.put(UUID.randomUUID().toString(), paraMap.get("inOrganId"));
		}
		if(!StringUtil.isEmpty(paraMap.get("inWarehouseId"))){
			sql.append(" \r\n and tt.warehouseid = ? "); //仓库条件
			param.put(UUID.randomUUID().toString(), paraMap.get("inWarehouseId"));
		}
		if(!StringUtil.isEmpty(paraMap.get("ProductName")) && !StringUtil.isEmpty(paraMap.get("packSizeName"))) { //产品条件
			sql.append(" \r\n and ttd.productid in (select id from product where productname = ? and specmode= ?) ");
			param.put(UUID.randomUUID().toString(), paraMap.get("ProductName"));
			param.put(UUID.randomUUID().toString(), paraMap.get("packSizeName"));
		} else if(!StringUtil.isEmpty(paraMap.get("ProductName")) && StringUtil.isEmpty(paraMap.get("packSizeName"))) { //产品条件
			sql.append(" \r\n and ttd.productid in (select id from product where productname = ?) ");
			param.put(UUID.randomUUID().toString(), paraMap.get("ProductName"));
		}
		if(!StringUtil.isEmpty(historyDateStr)){
			sql.append(" \r\n and tt.auditdate >=to_date(?,'yyyy-MM-dd hh24:mi:ss') "); //开始时间条件
			param.put(UUID.randomUUID().toString(), historyDateStr);
		}
		if(!StringUtil.isEmpty(endDate)){
			sql.append(" \r\n and tt.auditdate < to_date(?,'yyyy-MM-dd hh24:mi:ss') "); //结束时间条件
			param.put(UUID.randomUUID().toString(), endDate);
		}
		sql.append(" \r\n INNER JOIN ORGAN o on tt.oid = o.id and o.organtype = 2 and  organmodel = 1 "); //出库仓库为PD
		sql.append(" \r\n join WAREHOUSE w on tt.warehouseid = w.id and w.USEFLAG = 1  ");
		sql.append(" \r\n LEFT JOIN F_UNIT f on ttd.productid = f.productid and ttd.unitid = f.funitid ");
		if(DbUtil.isDealer(users)) {
			sql.append(" \r\n LEFT JOIN RULE_USER_WH rw on tt.warehouseid = rw.warehouse_id  ");
			sql.append(" \r\n where rw.user_id = ?");
			param.put(UUID.randomUUID().toString(), users.getUserid());
		} else {
			sql.append(" \r\n where "+DbUtil.getWhereCondition(users, "o"));
		}
		sql.append(" \r\n UNION ALL ");
		sql.append(" \r\n --实际出库数量算消耗");
		//#start modified by ryan.xi 20150518 按实际出库数量算消耗
		sql.append(" \r\n  select tt.oid, ttd.productid pid, -(ttd.realquantity * f.xquantity) quantity,0,0,w.id wid  ");
		//#end
		sql.append(" \r\n from TAKE_TICKET tt INNER JOIN TAKE_TICKET_DETAIL ttd on tt.id = ttd.ttid  and tt.isaudit = 1  and tt.bsort = 1 ");
		if(!StringUtil.isEmpty(paraMap.get("inOrganId"))){
			sql.append(" \r\n and tt.oid = ? "); //机构条件
			param.put(UUID.randomUUID().toString(), paraMap.get("inOrganId"));
		}
		if(!StringUtil.isEmpty(paraMap.get("inWarehouseId"))){
			sql.append(" \r\n and tt.warehouseid = ? "); //仓库条件
			param.put(UUID.randomUUID().toString(), paraMap.get("inWarehouseId"));
		}
		if(!StringUtil.isEmpty(paraMap.get("ProductName")) && !StringUtil.isEmpty(paraMap.get("packSizeName"))) { //产品条件
			sql.append(" \r\n and ttd.productid in (select id from product where productname = ? and specmode= ?) ");
			param.put(UUID.randomUUID().toString(), paraMap.get("ProductName"));
			param.put(UUID.randomUUID().toString(), paraMap.get("packSizeName"));
		} else if(!StringUtil.isEmpty(paraMap.get("ProductName")) && StringUtil.isEmpty(paraMap.get("packSizeName"))) { //产品条件
			sql.append(" \r\n and ttd.productid in (select id from product where productname = ?) ");
			param.put(UUID.randomUUID().toString(), paraMap.get("ProductName"));
		}
		if(!StringUtil.isEmpty(historyDateStr)){
			sql.append(" \r\n and tt.auditdate >=to_date(?,'yyyy-MM-dd hh24:mi:ss') "); //开始时间条件
			param.put(UUID.randomUUID().toString(), historyDateStr);
		}
		if(!StringUtil.isEmpty(endDate)){
			sql.append(" \r\n and tt.auditdate < to_date(?,'yyyy-MM-dd hh24:mi:ss') "); //结束时间条件
			param.put(UUID.randomUUID().toString(), endDate);
		}
		//过滤拒签的单据
		sql.append(" \r\n INNER JOIN STOCK_ALTER_MOVE sam on sam.id = tt.billno and sam.isblankout = 0");
		sql.append(" \r\n INNER JOIN ORGAN o on tt.oid = o.id and o.organtype = 2 and  organmodel = 1 "); //出库仓库为PD
		sql.append(" \r\n join WAREHOUSE w on tt.warehouseid = w.id and w.USEFLAG = 1  ");
		sql.append(" \r\n LEFT JOIN F_UNIT f on ttd.productid = f.productid and ttd.unitid = f.funitid ");
		if(DbUtil.isDealer(users)) {
			sql.append(" \r\n JOIN RULE_USER_WH rw on tt.warehouseid = rw.warehouse_id  ");
			//#start modified by ryan.xi 20150518 过滤消耗为0的单据
			sql.append(" \r\n where rw.user_id = ?");
			param.put(UUID.randomUUID().toString(), users.getUserid());
		} else {
			sql.append(" \r\n where "+DbUtil.getWhereCondition(users, "o"));
		}
		sql.append(" and (ttd.realquantity * f.xquantity) <> 0 ");
		//#end
		sql.append(" \r\n UNION ALL ");
		
		
		//#start modified by ryan.xi at 20150714
		//分包时的损耗记到消耗
		sql.append(" \r\n --分包时的损耗记到消耗");
		sql.append(" \r\n select tt.ORGANID, ttd.OUTPRODUCTID pid, -ttd.wastage quantity,0,0,w.id wid  ");
		sql.append(" \r\n from PACK_SEPARATE tt INNER JOIN PACK_SEPARATE_DETAIL ttd on tt.id = ttd.psid  and tt.isaudit = 1 ");
		if(!StringUtil.isEmpty(paraMap.get("inOrganId"))){
			sql.append(" \r\n and tt.ORGANID = ? "); //机构条件
			param.put(UUID.randomUUID().toString(), paraMap.get("inOrganId"));
		}
		if(!StringUtil.isEmpty(paraMap.get("inWarehouseId"))){
			sql.append(" \r\n and tt.WAREHOUSEID = ? "); //仓库条件
			param.put(UUID.randomUUID().toString(), paraMap.get("inWarehouseId"));
		}
		if(!StringUtil.isEmpty(paraMap.get("ProductName")) && !StringUtil.isEmpty(paraMap.get("packSizeName"))) { //产品条件
			sql.append(" \r\n and ttd.OUTPRODUCTID in (select id from product where productname = ? and specmode= ?) ");
			param.put(UUID.randomUUID().toString(), paraMap.get("ProductName"));
			param.put(UUID.randomUUID().toString(), paraMap.get("packSizeName"));
		} else if(!StringUtil.isEmpty(paraMap.get("ProductName")) && StringUtil.isEmpty(paraMap.get("packSizeName"))) { //产品条件
			sql.append(" \r\n and ttd.OUTPRODUCTID in (select id from product where productname = ?) ");
			param.put(UUID.randomUUID().toString(), paraMap.get("ProductName"));
		}
		if(!StringUtil.isEmpty(historyDateStr)){
			sql.append(" \r\n and tt.auditdate >=to_date(?,'yyyy-MM-dd hh24:mi:ss') "); //开始时间条件
			param.put(UUID.randomUUID().toString(), historyDateStr);
		}
		if(!StringUtil.isEmpty(endDate)){
			sql.append(" \r\n and tt.auditdate < to_date(?,'yyyy-MM-dd hh24:mi:ss') "); //结束时间条件
			param.put(UUID.randomUUID().toString(), endDate);
		}
		sql.append(" \r\n join WAREHOUSE w on tt.warehouseid = w.id and w.USEFLAG = 1  ");
		if(DbUtil.isDealer(users)) {
			sql.append(" \r\n JOIN RULE_USER_WH rw on tt.WAREHOUSEID = rw.warehouse_id ");
			sql.append(" \r\n where rw.user_id = ?");
			param.put(UUID.randomUUID().toString(), users.getUserid());
		} else {
			sql.append(" \r\n JOIN Organ o on o.id = tt.ORGANID ");
			sql.append(" \r\n where "+DbUtil.getWhereCondition(users, "o"));
		}
		sql.append(" and ttd.wastage <> 0 ");
		sql.append(" \r\n UNION ALL ");
		//#end modified by ryan.xi at 20150714
		
		
		//工厂发货给PD, 收货差异计入PD消耗
		sql.append(" \r\n --工厂发货给PD, 收货差异计入PD消耗");
		sql.append(" \r\n select tt.inoid, ttd.productid pid, -((samd.quantity-samd.receivequantity) * f.xquantity) quantity,0,0,w.id wid  ");
		sql.append(" \r\n from TAKE_TICKET tt INNER JOIN TAKE_TICKET_DETAIL ttd on tt.id = ttd.ttid  and tt.isaudit = 1  and tt.bsort = 1  ");
		if(!StringUtil.isEmpty(paraMap.get("inOrganId"))){
			sql.append(" \r\n and tt.inoid = ? "); //机构条件
			param.put(UUID.randomUUID().toString(), paraMap.get("inOrganId"));
		}
		if(!StringUtil.isEmpty(paraMap.get("inWarehouseId"))){
			sql.append(" \r\n and tt.inwarehouseid = ? "); //仓库条件
			param.put(UUID.randomUUID().toString(), paraMap.get("inWarehouseId"));
		}
		if(!StringUtil.isEmpty(paraMap.get("ProductName")) && !StringUtil.isEmpty(paraMap.get("packSizeName"))) { //产品条件
			sql.append(" \r\n and ttd.productid in (select id from product where productname = ? and specmode= ?) ");
			param.put(UUID.randomUUID().toString(), paraMap.get("ProductName"));
			param.put(UUID.randomUUID().toString(), paraMap.get("packSizeName"));
		} else if(!StringUtil.isEmpty(paraMap.get("ProductName")) && StringUtil.isEmpty(paraMap.get("packSizeName"))) { //产品条件
			sql.append(" \r\n and ttd.productid in (select id from product where productname = ?) ");
			param.put(UUID.randomUUID().toString(), paraMap.get("ProductName"));
		}
		sql.append(" \r\n join STOCK_ALTER_MOVE sam on sam.id=tt.billno and sam.iscomplete =1");
		if(!StringUtil.isEmpty(historyDateStr)){
			sql.append(" \r\n and sam.movedate >=to_date(?,'yyyy-MM-dd hh24:mi:ss') "); //开始时间条件
			param.put(UUID.randomUUID().toString(), historyDateStr);
		}
		if(!StringUtil.isEmpty(endDate)){
			sql.append(" \r\n and sam.movedate < to_date(?,'yyyy-MM-dd hh24:mi:ss') "); //结束时间条件
			param.put(UUID.randomUUID().toString(), endDate);
		}
		sql.append(" \r\n join STOCK_ALTER_MOVE_DETAIL samd on samd.samid = sam.id and samd.productid=ttd.productid");
		sql.append(" \r\n INNER JOIN ORGAN o on tt.oid = o.id and o.organtype = 1 ");
		sql.append(" \r\n join WAREHOUSE w on tt.inwarehouseid = w.id and w.USEFLAG = 1  ");
		sql.append(" \r\n LEFT JOIN F_UNIT f on ttd.productid = f.productid and ttd.unitid = f.funitid ");
		sql.append(" \r\n LEFT JOIN ORGAN ino on tt.inoid = ino.id  and ino.organtype = 2 and ino.organmodel = 1 ");
		if(DbUtil.isDealer(users)) {
			sql.append(" \r\n LEFT JOIN RULE_USER_WH rw on tt.inwarehouseid = rw.warehouse_id ");
			sql.append(" \r\n where rw.user_id = ?");
			param.put(UUID.randomUUID().toString(), users.getUserid());
		} else {
			sql.append(" \r\n where "+DbUtil.getWhereCondition(users, "ino"));
		}
		sql.append(" and (samd.quantity-samd.receivequantity) > 0 ");
		
		
		
		
		//#start modified by ryan at 20150805
		//其他入库-减消耗-20150429
		sql.append(" \r\n --其他入库-减消耗");
		sql.append(" \r\n UNION ALL ");
		sql.append(" \r\n select oia.organid, oida.productid pid, (oida.quantity * f.xquantity) quantity,0,0,w.id wid   ");
		sql.append(" \r\n from OTHER_INCOME_ALL oia INNER JOIN OTHER_INCOME_DETAIL_ALL oida on oia.id = oida.oiid and oia.isaudit = 1 and oia.incomesort <> 6 ");
		if(!StringUtil.isEmpty(paraMap.get("inOrganId"))){
			sql.append(" \r\n and oia.organid = ? "); //机构条件
			param.put(UUID.randomUUID().toString(), paraMap.get("inOrganId"));
		} 
		if(!StringUtil.isEmpty(paraMap.get("inWarehouseId"))){
			sql.append(" \r\n and oia.warehouseid = ? "); //仓库条件
			param.put(UUID.randomUUID().toString(), paraMap.get("inWarehouseId"));
		}
		if(!StringUtil.isEmpty(paraMap.get("ProductName")) && !StringUtil.isEmpty(paraMap.get("packSizeName"))) { //产品条件
			sql.append(" \r\n and oida.productid in (select id from product where productname = ? and packsizename= ?) ");
			param.put(UUID.randomUUID().toString(), paraMap.get("ProductName"));
			param.put(UUID.randomUUID().toString(), paraMap.get("packSizeName"));
		} else if(!StringUtil.isEmpty(paraMap.get("ProductName")) && StringUtil.isEmpty(paraMap.get("packSizeName"))) { //产品条件
			sql.append(" \r\n and oida.productid in (select id from product where productname = ?) ");
			param.put(UUID.randomUUID().toString(), paraMap.get("ProductName"));
		}
		if(!StringUtil.isEmpty(historyDateStr)){
			sql.append(" \r\n and oia.auditdate >=to_date(?,'yyyy-MM-dd hh24:mi:ss') "); //开始时间条件
			param.put(UUID.randomUUID().toString(), historyDateStr);
		}
		if(!StringUtil.isEmpty(endDate)){
			sql.append(" \r\n and oia.auditdate < to_date(?,'yyyy-MM-dd hh24:mi:ss') "); //结束时间条件
			param.put(UUID.randomUUID().toString(), endDate);
		}
		sql.append(" \r\n INNER JOIN ORGAN o on oia.organid= o.id and o.organtype = 2 and  organmodel = 1 ");
		sql.append(" \r\n join WAREHOUSE w on oia.warehouseid = w.id and w.USEFLAG = 1  ");
		sql.append(" \r\n LEFT JOIN F_UNIT f on oida.productid = f.productid and oida.unitid = f.funitid  "); 
		if(DbUtil.isDealer(users)) {
			sql.append(" \r\n INNER JOIN RULE_USER_WH rw on oia.warehouseid = rw.warehouse_id ");
			sql.append(" \r\n where rw.user_id = ?"); 
			param.put(UUID.randomUUID().toString(), users.getUserid());
		} else {
			sql.append(" \r\n where "+DbUtil.getWhereCondition(users, "o"));
		}
		
		
		//其他出库-加消耗-20150429
		sql.append(" \r\n UNION ALL ");
		sql.append(" \r\n --其他出库-加消耗");
		sql.append(" \r\n select oia.organid, oida.productid pid, -(oida.quantity * f.xquantity) quantity,0,0,w.id wid   ");
		sql.append(" \r\n from OTHER_SHIPMENT_BILL_ALL oia INNER JOIN OTHER_SHIPMENT_BILL_D_ALL oida on oia.id = oida.osid and oia.isaudit = 1 ");
		if(!StringUtil.isEmpty(paraMap.get("inOrganId"))){
			sql.append(" \r\n and oia.organid = ? "); //机构条件
			param.put(UUID.randomUUID().toString(), paraMap.get("inOrganId"));
		}
		if(!StringUtil.isEmpty(paraMap.get("inWarehouseId"))){
			sql.append(" \r\n and oia.warehouseid = ? "); //仓库条件
			param.put(UUID.randomUUID().toString(), paraMap.get("inWarehouseId"));
		}
		if(!StringUtil.isEmpty(paraMap.get("ProductName")) && !StringUtil.isEmpty(paraMap.get("packSizeName"))) { //产品条件
			sql.append(" \r\n and oida.productid in (select id from product where productname = ? and packsizename= ?) ");
			param.put(UUID.randomUUID().toString(), paraMap.get("ProductName"));
			param.put(UUID.randomUUID().toString(), paraMap.get("packSizeName"));
		} else if(!StringUtil.isEmpty(paraMap.get("ProductName")) && StringUtil.isEmpty(paraMap.get("packSizeName"))) { //产品条件
			sql.append(" \r\n and oida.productid in (select id from product where productname = ?) ");
			param.put(UUID.randomUUID().toString(), paraMap.get("ProductName"));
		}
		if(!StringUtil.isEmpty(historyDateStr)){
			sql.append(" \r\n and oia.auditdate >=to_date(?,'yyyy-MM-dd hh24:mi:ss') "); //开始时间条件
			param.put(UUID.randomUUID().toString(), historyDateStr);
		}
		if(!StringUtil.isEmpty(endDate)){
			sql.append(" \r\n and oia.auditdate < to_date(?,'yyyy-MM-dd hh24:mi:ss') "); //结束时间条件
			param.put(UUID.randomUUID().toString(), endDate);
		}
		sql.append(" \r\n INNER JOIN ORGAN o on oia.organid= o.id and o.organtype = 2 and  organmodel = 1 ");
		sql.append(" \r\n join WAREHOUSE w on oia.warehouseid = w.id and w.USEFLAG = 1  ");
		sql.append(" \r\n LEFT JOIN F_UNIT f on oida.productid = f.productid and oida.unitid = f.funitid  "); 
		if(DbUtil.isDealer(users)) {
			sql.append(" \r\n INNER JOIN RULE_USER_WH rw on oia.warehouseid = rw.warehouse_id ");
			sql.append(" \r\n where rw.user_id = ?"); 
			param.put(UUID.randomUUID().toString(), users.getUserid());
		} else { 
			sql.append(" \r\n where "+DbUtil.getWhereCondition(users, "o"));
		}
		sql.append(" \r\n UNION ALL ");
		sql.append(" \r\n --退货");
		sql.append(" \r\n select tt.inoid oid,ttd.productid pid,(ttd.realquantity * f.xquantity) quantity,0,0,w.id wid   ");
		sql.append(" \r\n from TAKE_TICKET tt INNER JOIN TAKE_TICKET_DETAIL ttd on tt.id = ttd.ttid  and tt.isaudit = 1  and tt.bsort = 7 and tt.billno like 'OW%' ");
		if(!StringUtil.isEmpty(paraMap.get("inOrganId"))){
			sql.append(" \r\n and tt.inoid = ? "); //机构条件
			param.put(UUID.randomUUID().toString(), paraMap.get("inOrganId"));
		}
		if(!StringUtil.isEmpty(paraMap.get("inWarehouseId"))){
			sql.append(" \r\n and tt.inwarehouseid = ? "); //仓库条件
			param.put(UUID.randomUUID().toString(), paraMap.get("inWarehouseId"));
		}
		if(!StringUtil.isEmpty(paraMap.get("ProductName")) && !StringUtil.isEmpty(paraMap.get("packSizeName"))) { //产品条件
			sql.append(" \r\n and ttd.productid in (select id from product where productname = ? and specmode= ?) ");
			param.put(UUID.randomUUID().toString(), paraMap.get("ProductName"));
			param.put(UUID.randomUUID().toString(), paraMap.get("packSizeName"));
		} else if(!StringUtil.isEmpty(paraMap.get("ProductName")) && StringUtil.isEmpty(paraMap.get("packSizeName"))) { //产品条件
			sql.append(" \r\n and ttd.productid in (select id from product where productname = ?) ");
			param.put(UUID.randomUUID().toString(), paraMap.get("ProductName"));
		}
		if(!StringUtil.isEmpty(historyDateStr)){
			sql.append(" \r\n and tt.auditdate >=to_date(?,'yyyy-MM-dd hh24:mi:ss') "); //开始时间条件
			param.put(UUID.randomUUID().toString(), historyDateStr);
		}
		if(!StringUtil.isEmpty(endDate)){
			sql.append(" \r\n and tt.auditdate < to_date(?,'yyyy-MM-dd hh24:mi:ss') "); //结束时间条件
			param.put(UUID.randomUUID().toString(), endDate);
		}
		sql.append(" \r\n INNER JOIN ORGAN o on tt.inoid = o.id and o.organtype = 2 and  organmodel = 1 "); // 入库仓库为PD
		sql.append(" \r\n join WAREHOUSE w on tt.inwarehouseid = w.id and w.USEFLAG = 1  ");
		sql.append(" \r\n LEFT JOIN F_UNIT f on ttd.productid = f.productid and ttd.unitid = f.funitid ");
		
		if(DbUtil.isDealer(users)) {
			sql.append(" \r\n LEFT JOIN RULE_USER_WH rw on tt.inwarehouseid = rw.warehouse_id ");
			sql.append(" \r\n where rw.user_id = ?");
			param.put(UUID.randomUUID().toString(), users.getUserid());
		} else {
			sql.append(" \r\n where "+DbUtil.getWhereCondition(users, "o"));
		}
		
		sql.append(")temp ");
		sql.append(" where 1=1 ");
		if(!StringUtil.isEmpty(paraMap.get("inOrganId"))){
			sql.append(" \r\n and ORGANID = ? "); //机构条件
			param.put(UUID.randomUUID().toString(), paraMap.get("inOrganId"));
		}
		if(!StringUtil.isEmpty(paraMap.get("inWarehouseId"))){
			sql.append(" \r\n and WAREHOUSEID = ? "); //仓库条件
			param.put(UUID.randomUUID().toString(), paraMap.get("inWarehouseId"));
		}
		if(DbUtil.isDealer(users)) {
			sql.append(" and warehouseid in (select wv.warehouse_Id from RULE_USER_WH wv where wv.user_Id=?)");
			param.put(UUID.randomUUID().toString(), users.getUserid());
		}
		sql.append("GROUP BY organid,productid,WAREHOUSEID ");
		sql.append(") idh ");
		sql.append("join organ o on o.id = idh.organid  and o.ISREPEAL = 0  ");
		if(!DbUtil.isDealer(users)) {
			sql.append(" and "+DbUtil.getWhereCondition(users, "o"));
		}
		sql.append("join WAREHOUSE w on w.id = idh.warehouseid and w.USEFLAG = 1 ");
		sql.append("join PRODUCT p on p.id =idh.productid and p.USEFLAG=1 ");
		if(!StringUtil.isEmpty((String)paraMap.get("ProductName")) && !StringUtil.isEmpty((String)paraMap.get("packSizeName"))) { //产品条件
			sql.append(" \r\n and p.productname = ? and p.specmode= ? ");
			param.put(UUID.randomUUID().toString(), paraMap.get("ProductName"));
			param.put(UUID.randomUUID().toString(), paraMap.get("packSizeName"));
		} else if(!StringUtil.isEmpty((String)paraMap.get("ProductName")) && StringUtil.isEmpty((String)paraMap.get("packSizeName"))) { //产品条件
			sql.append(" \r\n and p.productname = ? ");
			param.put(UUID.randomUUID().toString(), paraMap.get("ProductName"));
		}
		sql.append("join PRODUCT_STRUCT ps on ps.structcode=p.psid ");
		sql.append("left join COUNTRY_AREA ca on ca.id = o.province ");
		sql.append("LEFT join REGION_AREA ra on RA.AREAID=o.province ");
		sql.append("LEFT JOIN REGION r on r.regioncode = ra.regioncodeid ");
		sql.append("where  (stockpile <> 0 or stockintransit <> 0 or stockpiletoship <> 0 or stockpiletoship <> 0) ");
		if(!StringUtil.isEmpty((String)paraMap.get("region"))) {
			sql.append(" \r\n and ra.regioncodeid =  ? "); //大区条件 
			param.put(UUID.randomUUID().toString(), paraMap.get("region"));
		}
		System.out.println(sql.toString()); 
		List<Map> list =  new ArrayList<Map>();
		if(request == null && pageSize == 0){
			list =  EntityManager.jdbcquery(sql.toString(), param);
		}else {
			list =  PageQuery.jdbcSqlserverQuery(request, "organid,productid", sql.toString(), pageSize, param);
		}
		if(list != null && list.size()>0){
			Map<String,FUnit>  funitMap = appFUnit.getAllMap(); //获取所有的单位换算信息
			Map<Integer, String> countUnitMap = appBr.getBaseResourceMap("CountUnit");
			for(Map map : list){
				PDInventoryReport report = new PDInventoryReport();
				//将Map中对应的值赋值给实例
				MapUtil.mapToObject(map, report);
				report.setViewDate(((String)paraMap.get("EndDate")).substring(0, 10));
				
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

	private List<PDInventoryReport> getInventorySalesConsumeHistory(Calendar endDate,
			HttpServletRequest request, int pageSize, Map<String,String> paraMap,
			UsersBean users) throws Exception {
		Map<String, Object> param = new LinkedHashMap<String, Object>();
		int endYear = endDate.get(Calendar.YEAR);
		int endMonth = endDate.get(Calendar.MONTH)+1;
		String endDateStr = paraMap.get("EndDate") + " 23:59:59";
		endDate.add(Calendar.MONTH, -1);
		int startYear = endDate.get(Calendar.YEAR);
		int startMonth = endDate.get(Calendar.MONTH)+1;
		
		List<PDInventoryReport> resultList = new ArrayList<PDInventoryReport>();
		StringBuffer sql = new StringBuffer();
		sql.append("select r.sortname regionName,ca.areaname,o.id organId,o.organname,o.oecode,w.id wid,w.warehousename,p.id proid,p.mcode,p.productName productChnName,p.productNameen productEngName,p.matericalchdes ProductName,p.matericalendes ProductNameen,p.Specmode PackSizeName,p.PackSizeNameEn,p.sunit,idh.stockpile,idh.stockpileToShip,idh.stockInTransit stockInTransit,ps.sortname,p.boxquantity,p.countunit,idh.productid from ");
		sql.append("(select organid,productid,sum(stockpile) stockpile,sum(stockpileToShip) stockpileToShip, sum(stockpileInTransit) stockInTransit,WAREHOUSEID from ( ");
		
		sql.append("select organid,productid,stockpile,stockpileToShip, stockpileInTransit,WAREHOUSEID from INVENTORY_DETAIL_HISTORY ");
		sql.append("where year=? and month=? ");
		param.put(UUID.randomUUID().toString(), startYear);
		param.put(UUID.randomUUID().toString(), startMonth);
		sql.append("UNION ALL ");
		sql.append("select organid,productid,case when iscomplete <> 0 then SALESQUANTITY else 0 end,0,case when iscomplete = 0 then SALESQUANTITY else 0 end,WAREHOUSEID from SALES_DETAIL_HISTORY ");
		sql.append("where year=? and month=? and makedate <= to_date(?,'yyyy-MM-dd hh24:mi:ss')");
		param.put(UUID.randomUUID().toString(), endYear);
		param.put(UUID.randomUUID().toString(), endMonth);
		param.put(UUID.randomUUID().toString(), endDateStr);
		sql.append("UNION ALL ");
		sql.append("select outorganid,productid,-consumequantity,0,0,OUTWAREHOUSEID from CONSUME_DETAIL_HISTORY ");
		sql.append("where year=? and month=? and makedate <= to_date(?,'yyyy-MM-dd hh24:mi:ss')");
		param.put(UUID.randomUUID().toString(), endYear);
		param.put(UUID.randomUUID().toString(), endMonth);
		param.put(UUID.randomUUID().toString(), endDateStr);
		sql.append(")temp where 1=1 ");
		if(!StringUtil.isEmpty(paraMap.get("inOrganId"))){
			sql.append(" \r\n and ORGANID = ? "); //机构条件
			param.put("ORGANID", paraMap.get("inOrganId"));
		}
		if(!StringUtil.isEmpty(paraMap.get("inWarehouseId"))){
			sql.append(" \r\n and WAREHOUSEID = ? "); //仓库条件
			param.put("WAREHOUSEID", paraMap.get("inWarehouseId"));
		}
		if(DbUtil.isDealer(users)) {
			sql.append(" and warehouseid in (select wv.warehouse_Id from RULE_USER_WH wv where wv.user_Id=?)");
			param.put("wv.user_Id", users.getUserid());
		}
		sql.append("GROUP BY organid,productid,WAREHOUSEID ");
		sql.append(") idh ");
		sql.append("join organ o on o.id = idh.organid  and o.ISREPEAL = 0  ");
		if(!DbUtil.isDealer(users)) {
			sql.append(" and "+DbUtil.getWhereCondition(users, "o"));
		}
		sql.append("join WAREHOUSE w on w.id = idh.warehouseid and w.USEFLAG = 1 ");
		sql.append("join PRODUCT p on p.id =idh.productid and p.USEFLAG=1 ");
		if(!StringUtil.isEmpty((String)paraMap.get("ProductName")) && !StringUtil.isEmpty((String)paraMap.get("packSizeName"))) { //产品条件
			sql.append(" \r\n and p.productname = ? and p.specmode= ? ");
			param.put("productname", (String)paraMap.get("ProductName"));
			param.put("specmode", (String)paraMap.get("packSizeName"));
		} else if(!StringUtil.isEmpty((String)paraMap.get("ProductName")) && StringUtil.isEmpty((String)paraMap.get("packSizeName"))) { //产品条件
			sql.append(" \r\n and p.productname = ? ");
			param.put("productname", (String)paraMap.get("ProductName"));
		}
		sql.append("join PRODUCT_STRUCT ps on ps.structcode=p.psid ");
		sql.append("left join COUNTRY_AREA ca on ca.id = o.province ");
		sql.append("LEFT join REGION_AREA ra on RA.AREAID=o.province ");
		sql.append("LEFT JOIN REGION r on r.regioncode = ra.regioncodeid ");
		sql.append("where  (stockpile <> 0 or stockintransit <> 0 or stockpiletoship <> 0 or stockpiletoship <> 0) ");
		if(!StringUtil.isEmpty((String)paraMap.get("region"))) {
			sql.append(" \r\n and ra.regioncodeid =  ? "); //大区条件 
			param.put("regioncodeid", paraMap.get("region"));
		}
		System.out.println(sql.toString()); 
		List<Map> list =  new ArrayList<Map>();
		if(request == null && pageSize == 0){
			list =  EntityManager.jdbcquery(sql.toString(), param);
		}else {
			list =  PageQuery.jdbcSqlserverQuery(request, "organid,productid", sql.toString(), pageSize, param);
		}
		if(list != null && list.size()>0){
			Map<String,FUnit>  funitMap = appFUnit.getAllMap(); //获取所有的单位换算信息
			Map<Integer, String> countUnitMap = appBr.getBaseResourceMap("CountUnit");
			for(Map map : list){
				PDInventoryReport report = new PDInventoryReport();
				//将Map中对应的值赋值给实例
				MapUtil.mapToObject(map, report);
				report.setViewDate(((String)paraMap.get("EndDate")).substring(0, 10));
				
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

	private List<PDInventoryReport> getInventoryHistory(Calendar endDate,
			HttpServletRequest request, int pageSize, Map<String,String> paraMap,
			UsersBean users) throws Exception { 
		Map<String, Object> param = new LinkedHashMap<String, Object>();
		int endYear = endDate.get(Calendar.YEAR);
		int endMonth = endDate.get(Calendar.MONTH)+1;
		List<PDInventoryReport> resultList = new ArrayList<PDInventoryReport>();
		StringBuffer sql = new StringBuffer();
		sql.append("select r.sortname regionName,ca.areaname,o.id organId,o.organname,o.oecode,w.id wid,w.warehousename,p.id proid,p.mcode,p.productName productChnName,p.productNameen productEngName,p.matericalchdes ProductName,p.matericalendes ProductNameen,p.Specmode PackSizeName,p.PackSizeNameEn,p.sunit,idh.stockpile,idh.stockpileToShip,idh.stockInTransit stockInTransit,ps.sortname,p.boxquantity,p.countunit,idh.productid from ");
		sql.append("(select organid,productid,sum(stockpile) stockpile,sum(stockpileToShip) stockpileToShip, sum(stockpileInTransit) stockInTransit,WAREHOUSEID from INVENTORY_DETAIL_HISTORY ");
		sql.append("where year=? and month=? ");
		param.put("endYear", endYear);
		param.put("endMonth", endMonth);
		if(!StringUtil.isEmpty(paraMap.get("inOrganId"))){
			sql.append(" \r\n and ORGANID = ? "); //机构条件
			param.put("inOrganId", paraMap.get("inOrganId"));
		}
		if(!StringUtil.isEmpty(paraMap.get("inWarehouseId"))){
			sql.append(" \r\n and WAREHOUSEID = ? "); //仓库条件
			param.put("inWarehouseId", paraMap.get("inWarehouseId"));
		}
		if(DbUtil.isDealer(users)) {
			sql.append(" and warehouseid in (select wv.warehouse_Id from RULE_USER_WH wv where wv.user_Id=?)");
			param.put("wv.user_Id", users.getUserid());
		}
		sql.append("GROUP BY organid,productid,WAREHOUSEID ");
		sql.append(") idh ");
		sql.append("join organ o on o.id = idh.organid and o.ISREPEAL = 0 ");
		if(!DbUtil.isDealer(users)) {
			sql.append(" and "+DbUtil.getWhereCondition(users, "o"));
		}
		sql.append("join WAREHOUSE w on w.id = idh.warehouseid and w.USEFLAG = 1 ");
		sql.append("join PRODUCT p on p.id =idh.productid and p.USEFLAG=1 ");
		if(!StringUtil.isEmpty((String)paraMap.get("ProductName")) && !StringUtil.isEmpty((String)paraMap.get("packSizeName"))) { //产品条件
			sql.append(" \r\n and p.productname = ? and p.specmode= ? ");
			param.put("productname", (String)paraMap.get("ProductName"));
			param.put("specmode", (String)paraMap.get("packSizeName"));
		} else if(!StringUtil.isEmpty((String)paraMap.get("ProductName")) && StringUtil.isEmpty((String)paraMap.get("packSizeName"))) { //产品条件
			sql.append(" \r\n and p.productname = ? ");
			param.put(UUID.randomUUID().toString(), (String)paraMap.get("ProductName"));
		}
		sql.append("join PRODUCT_STRUCT ps on ps.structcode=p.psid ");
		sql.append("left join COUNTRY_AREA ca on ca.id = o.province ");
		sql.append("LEFT join REGION_AREA ra on RA.AREAID=o.province ");
		sql.append("LEFT JOIN REGION r on r.regioncode = ra.regioncodeid ");
		sql.append("where  (stockpile <> 0 or stockintransit <> 0 or stockpiletoship <> 0 or stockpiletoship <> 0) ");
		if(!StringUtil.isEmpty((String)paraMap.get("region"))) {
			sql.append(" \r\n and ra.regioncodeid =  ? "); //大区条件 
			param.put("region", paraMap.get("region"));
		}
		System.out.println(sql.toString()); 
		List<Map> list =  new ArrayList<Map>();
		if(request == null && pageSize == 0){
			list =  EntityManager.jdbcquery(sql.toString(), param);
		}else {
			list =  PageQuery.jdbcSqlserverQuery(request, "organid,productid", sql.toString(), pageSize, param);
		}
		if(list != null && list.size()>0){
			Map<String,FUnit>  funitMap = appFUnit.getAllMap(); //获取所有的单位换算信息
			Map<Integer, String> countUnitMap = appBr.getBaseResourceMap("CountUnit");
			for(Map map : list){
				PDInventoryReport report = new PDInventoryReport();
				//将Map中对应的值赋值给实例
				MapUtil.mapToObject(map, report);
				report.setViewDate(((String)paraMap.get("EndDate")).substring(0, 10));
				
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

	/** 
    * 判断给定日期是否为月末的一天 
    * 
    * @param date 
    * @return true:是|false:不是 
    */ 
    public boolean isLastDayOfMonth(Date date) { 
        Calendar calendar = Calendar.getInstance(); 
        calendar.setTime(date); 
        calendar.set(Calendar.DATE, (calendar.get(Calendar.DATE) + 1)); 
        if (calendar.get(Calendar.DAY_OF_MONTH) == 1) { 
            return true; 
        } 
        return false; 
    } 
    
    public boolean isLastDayOfMonth(Calendar calendar) { 
        calendar.set(Calendar.DATE, (calendar.get(Calendar.DATE) + 1)); 
        if (calendar.get(Calendar.DAY_OF_MONTH) == 1) { 
            return true; 
        } 
        return false; 
    } 
	
	public static void main(String[] args) throws Exception {
		String datestr = "2017-05-01";
		Date date = Dateutil.StringToDate(datestr);
		System.out.println(date); 
		Map<String,String> paraMap = new HashMap<>();
		paraMap.put("region", "107");
		paraMap.put("wname", "pd仓库001");
		paraMap.put("queryFlag", "1");
		paraMap.put("packSizeName", "50毫升");
		paraMap.put("countByUnit", "true");
		paraMap.put("inWarehouseId", "14849");
		paraMap.put("oname2", "PD经销商20150109");
		paraMap.put("EndDate", "2018-05-01");
		paraMap.put("ProductName", "艾美乐");
		paraMap.put("inOrganId", "10001541");
		PDInventoryReportService serv = new PDInventoryReportService();
		UsersBean users = new UsersBean();
		users.setUserid(10462);
		Calendar endDate = Calendar.getInstance();
		endDate.setTime(Dateutil.StringToDate("2018-05-01"));
		Object result = serv.getInventoryHistory(endDate ,null, 0, paraMap, users);
		System.out.println("end...");
//		Calendar 
	}
	
}
