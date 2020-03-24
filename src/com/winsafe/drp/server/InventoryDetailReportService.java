package com.winsafe.drp.server;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.winsafe.drp.dao.BaseResource;
import com.winsafe.drp.dao.FUnit;
import com.winsafe.drp.dao.InventoryDetailReport;
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
import com.winsafe.sap.pojo.PrintJob;

public class InventoryDetailReportService extends ReportServices{
	private static Logger logger = Logger.getLogger(InventoryDetailReportService.class);
	
	
	
	// 根据条件查询
	public List<InventoryDetailReport> queryReport2(HttpServletRequest request, int pageSize,Map paraMap,UsersBean users) throws Exception{
		//所选日期是否
		boolean isEarlier = false;
		if(!StringUtil.isEmpty((String)paraMap.get("EndDate"))){
			String endDateStr = (String)paraMap.get("EndDate") + " 23:59:59";
			paraMap.put("EndDate", endDateStr);
			Date endDate = Dateutil.formatDatetime(endDateStr);
			Date currentDate = Dateutil.getCurrentDate();
			if(endDate.compareTo(currentDate) < 0) {
				isEarlier = true;
			}
		}
		
		List<InventoryDetailReport> resultList = new ArrayList<InventoryDetailReport>();
		
		StringBuffer sql = new StringBuffer();
		
		sql.append(" \r\n select res.*,wa.warehousename,ca.areaname,r.sortname as regionName  from (");
		
		sql.append(" \r\n SELECT organid, max(organname) as organname, max(oecode) as oecode, max(province) as province, productid,sum(stockpile) as stockpile,sum(stockintransit) as stockintransit, warehouseid, batch,sum(stockpiletoship) as stockpiletoship from (");
		
		sql.append(" \r\n select ");
		sql.append(" \r\n  o.id as organid");
		sql.append(" \r\n ,o.organname");
		sql.append(" \r\n ,o.oecode");
		sql.append(" \r\n ,o.province");
		sql.append(" \r\n ,ps.productid ");
		sql.append(" \r\n ,ps.stockpile as stockpile"); 
		sql.append(" \r\n ,0 as stockintransit");
		sql.append(" \r\n ,ps.warehouseid");
		sql.append(" \r\n ,ps.batch");
		sql.append(" \r\n ,0 as stockpiletoship");
		sql.append(" \r\n from PRODUCT_STOCKPILE_ALL ps");
		sql.append(" \r\n join WAREHOUSE wa on PS.WAREHOUSEID = wa.id ");
		sql.append(" \r\n join ORGAN o on o.id = wa.makeorganid ");
		if(DbUtil.isDealer(users)) {
			sql.append(" \r\n and ps.warehouseid in (select wv.warehouse_Id from RULE_USER_WH wv where wv.user_Id="+users.getUserid()+")");
		} else {
			sql.append(" \r\n and "+DbUtil.getWhereCondition(users, "o"));
		}
		if(!StringUtil.isEmpty((String)paraMap.get("inWarehouseId"))){
			sql.append(" \r\n and ps.warehouseid = '" + paraMap.get("inWarehouseId") + "' "); //仓库条件
		}
		if(!StringUtil.isEmpty((String)paraMap.get("batch"))){
			sql.append(" \r\n and ps.batch = '" + paraMap.get("batch") + "' "); //批次条件
		}
		if(!StringUtil.isEmpty((String)paraMap.get("ProductName")) && !StringUtil.isEmpty((String)paraMap.get("packSizeName"))) { //产品条件
			sql.append(" \r\n and ps.productid in (select id from product where productname = '"+(String)paraMap.get("ProductName")+"' and specmode= '"+(String)paraMap.get("packSizeName")+"') ");
		} else if(!StringUtil.isEmpty((String)paraMap.get("ProductName")) && StringUtil.isEmpty((String)paraMap.get("packSizeName"))) { //产品条件
			sql.append(" \r\n and ps.productid in (select id from product where productname = '"+(String)paraMap.get("ProductName")+"') ");
		}
		if(!StringUtil.isEmpty((String)paraMap.get("inOrganId"))){
			sql.append(" \r\n and o.id = '" + paraMap.get("inOrganId") + "' "); //机构条件
		}
		if(sysPro != null &&"1".equals(sysPro.getProperty("genReportOnlyForPD"))){ //是否只统计PD
			sql.append(" \r\n and o.organtype = 2 and o.organmodel = 1"); 
		}
		if(!StringUtil.isEmpty((String)paraMap.get("region"))) {
			sql.append(" \r\n and o.province is not null and o.province in (select areaid from region_area where regioncodeid =  '" + paraMap.get("region") + "')"); //大区条件 
		}
		
		sql.append(" \r\n UNION ALL ");
		
		if(isEarlier) {
			//更早的库存明细
			//--发货单(入库)
			sql.append(" \r\n --发货单(入库)");
			sql.append(" \r\n select o.id as organid,o.organname,o.oecode,o.province,swb.productid,-swb.cycleinquantity as stockpile,0 as stockintransit,sam.INWAREHOUSEID as warehouseid ,swb.batch, 0 as stockpiletoship  from STOCK_ALTER_MOVE sam ");
			sql.append(" \r\n join ORGAN o on o.id = sam.receiveorganid and sam.iscomplete = 1");
			if(DbUtil.isDealer(users)) {
				sql.append(" \r\n  and sam.INWAREHOUSEID in (select wv.warehouse_Id from RULE_USER_WH wv where wv.user_Id="+users.getUserid()+") ");
			} else {
				sql.append(" \r\n and "+DbUtil.getWhereCondition(users, "o"));
			}
			sql.append(" \r\n and sam.receivedate > to_date('" + paraMap.get("EndDate") + "','yyyy-MM-dd hh24:mi:ss')");
//			sql.append(" \r\n and sam.receivedate <  to_date('" + paraMap.get("historeyDate") + "','yyyy-MM-dd hh24:mi:ss')");
			sql.append(" \r\n and sam.movedate > to_date('" + paraMap.get("EndDate") + "','yyyy-MM-dd hh24:mi:ss')");
			if(sysPro != null &&"1".equals(sysPro.getProperty("genReportOnlyForPD"))){ //是否只统计PD
				sql.append(" \r\n and o.organtype = 2 and o.organmodel = 1"); 
			}
			if(!StringUtil.isEmpty((String)paraMap.get("inOrganId"))){
				sql.append(" \r\n and sam.receiveorganid = '" + paraMap.get("inOrganId") + "' "); //机构条件
			}
			if(!StringUtil.isEmpty((String)paraMap.get("region"))) {
				sql.append(" \r\n and o.province is not null and o.province in (select areaid from region_area where regioncodeid =  '" + paraMap.get("region") + "')"); //机构条件 
			}
			if(!StringUtil.isEmpty((String)paraMap.get("inWarehouseId"))){
				sql.append(" \r\n and sam.INWAREHOUSEID = '" + paraMap.get("inWarehouseId") + "' "); //仓库条件
			}
			sql.append(" \r\n join STOCK_WASTE_BOOK swb on sam.id = swb.billcode ");
			if(!StringUtil.isEmpty((String)paraMap.get("ProductName")) && !StringUtil.isEmpty((String)paraMap.get("packSizeName"))) { //产品条件
				sql.append(" \r\n and swb.productid in (select id from product where productname = '"+(String)paraMap.get("ProductName")+"' and specmode= '"+(String)paraMap.get("packSizeName")+"') ");
			} else if(!StringUtil.isEmpty((String)paraMap.get("ProductName")) && StringUtil.isEmpty((String)paraMap.get("packSizeName"))) { //产品条件
				sql.append(" \r\n and swb.productid in (select id from product where productname = '"+(String)paraMap.get("ProductName")+"') ");
			}
			
			//--盘库前发货单签收-减去
			sql.append(" \r\n UNION ALL");
			sql.append(" \r\n --盘库前发货单签收-减去");
			sql.append(" \r\n select o.id as organid,o.organname,o.oecode,o.province,swb.productid,-swb.cycleoutquantity as stockpile,0 as stockintransit,sam.INWAREHOUSEID as warehouseid ,swb.batch, 0 as stockpiletoship  from STOCK_ALTER_MOVE sam ");
			sql.append(" \r\n join ORGAN o on o.id = sam.receiveorganid and sam.iscomplete = 1 and not exists (select 1 from STOCK_WASTE_BOOK where billcode=sam.id)");
			if(DbUtil.isDealer(users)) {
				sql.append(" \r\n  and sam.INWAREHOUSEID in (select wv.warehouse_Id from RULE_USER_WH wv where wv.user_Id="+users.getUserid()+") ");
			} else {
				sql.append(" \r\n and "+DbUtil.getWhereCondition(users, "o"));
			}
//			sql.append(" \r\n and sam.receivedate > to_date('" + paraMap.get("EndDate") + "','yyyy-MM-dd hh24:mi:ss')");
//			sql.append(" \r\n and sam.receivedate <  to_date('" + paraMap.get("historeyDate") + "','yyyy-MM-dd hh24:mi:ss')");
			sql.append(" \r\n and sam.movedate > to_date('" + paraMap.get("EndDate") + "','yyyy-MM-dd hh24:mi:ss')");
			if(sysPro != null &&"1".equals(sysPro.getProperty("genReportOnlyForPD"))){ //是否只统计PD
				sql.append(" \r\n and o.organtype = 2 and o.organmodel = 1"); 
			}
			if(!StringUtil.isEmpty((String)paraMap.get("inOrganId"))){
				sql.append(" \r\n and sam.receiveorganid = '" + paraMap.get("inOrganId") + "' "); //机构条件
			}
			if(!StringUtil.isEmpty((String)paraMap.get("region"))) {
				sql.append(" \r\n and o.province is not null and o.province in (select areaid from region_area where regioncodeid =  '" + paraMap.get("region") + "')"); //机构条件 
			}
			if(!StringUtil.isEmpty((String)paraMap.get("inWarehouseId"))){
				sql.append(" \r\n and sam.INWAREHOUSEID = '" + paraMap.get("inWarehouseId") + "' "); //仓库条件
			}
			sql.append(" \r\n join take_ticket tt on sam.id = tt.billno ");
			sql.append(" \r\n join STOCK_WASTE_BOOK swb on tt.id = swb.billcode ");
			if(!StringUtil.isEmpty((String)paraMap.get("ProductName")) && !StringUtil.isEmpty((String)paraMap.get("packSizeName"))) { //产品条件
				sql.append(" \r\n and swb.productid in (select id from product where productname = '"+(String)paraMap.get("ProductName")+"' and specmode= '"+(String)paraMap.get("packSizeName")+"') ");
			} else if(!StringUtil.isEmpty((String)paraMap.get("ProductName")) && StringUtil.isEmpty((String)paraMap.get("packSizeName"))) { //产品条件
				sql.append(" \r\n and swb.productid in (select id from product where productname = '"+(String)paraMap.get("ProductName")+"') ");
			}
			
			
			sql.append(" \r\n UNION ALL");
			//--发货单(出库)
			sql.append(" \r\n --发货单(出库)");
			sql.append(" \r\n select o.id as organid,o.organname,o.oecode,o.province,swb.productid,swb.cycleoutquantity as stockpile,0 as stockintransit,sam.OUTWAREHOUSEID as warehouseid,swb.batch, 0 as stockpiletoship  from STOCK_ALTER_MOVE sam ");
			sql.append(" \r\n join ORGAN o on o.id = sam.outorganid and sam.isshipment = 1");
			if(DbUtil.isDealer(users)) {
				sql.append(" \r\n  and sam.OUTWAREHOUSEID in (select wv.warehouse_Id from RULE_USER_WH wv where wv.user_Id="+users.getUserid()+") ");
			} else {
				sql.append(" \r\n and "+DbUtil.getWhereCondition(users, "o"));
			}
			sql.append(" \r\n and sam.shipmentdate > to_date('" + paraMap.get("EndDate") + "','yyyy-MM-dd hh24:mi:ss')");
//			sql.append(" \r\n and sam.shipmentdate <  to_date('" + paraMap.get("historeyDate") + "','yyyy-MM-dd hh24:mi:ss')");
			sql.append(" \r\n and sam.movedate > to_date('" + paraMap.get("EndDate") + "','yyyy-MM-dd hh24:mi:ss')");
			if(sysPro != null &&"1".equals(sysPro.getProperty("genReportOnlyForPD"))){ //是否只统计PD
				sql.append(" \r\n and o.organtype = 2 and o.organmodel = 1"); 
			}
			if(!StringUtil.isEmpty((String)paraMap.get("inOrganId"))){
				sql.append(" \r\n and sam.outorganid = '" + paraMap.get("inOrganId") + "' "); //机构条件
			}
			if(!StringUtil.isEmpty((String)paraMap.get("region"))) {
				sql.append(" \r\n and o.province is not null and o.province in (select areaid from region_area where regioncodeid =  '" + paraMap.get("region") + "')"); //机构条件 
			}
			if(!StringUtil.isEmpty((String)paraMap.get("inWarehouseId"))){
				sql.append(" \r\n and sam.OUTWAREHOUSEID = '" + paraMap.get("inWarehouseId") + "' "); //仓库条件
			}
			sql.append(" \r\n join TAKE_TICKET tt on tt.billno = sam.id ");
			sql.append(" \r\n join STOCK_WASTE_BOOK swb on tt.id = swb.billcode");
			if(!StringUtil.isEmpty((String)paraMap.get("ProductName")) && !StringUtil.isEmpty((String)paraMap.get("packSizeName"))) { //产品条件
				sql.append(" \r\n and swb.productid in (select id from product where productname = '"+(String)paraMap.get("ProductName")+"' and specmode= '"+(String)paraMap.get("packSizeName")+"') ");
			} else if(!StringUtil.isEmpty((String)paraMap.get("ProductName")) && StringUtil.isEmpty((String)paraMap.get("packSizeName"))) { //产品条件
				sql.append(" \r\n and swb.productid in (select id from product where productname = '"+(String)paraMap.get("ProductName")+"') ");
			}
			
			
			sql.append(" \r\n UNION ALL");
			
			//--发货单(待发货)
			sql.append(" \r\n --发货单(待发货)");
			sql.append(" \r\n select o.id as organid,o.organname,o.oecode,o.province,swb.productid,0 as stockpile,0 as stockintransit,sam.OUTWAREHOUSEID as warehouseid,swb.batch, swb.cycleoutquantity as stockpiletoship  from STOCK_ALTER_MOVE sam ");
			sql.append(" \r\n join ORGAN o on o.id = sam.outorganid and sam.isshipment = 1");
			if(DbUtil.isDealer(users)) {
				sql.append(" \r\n  and sam.OUTWAREHOUSEID in (select wv.warehouse_Id from RULE_USER_WH wv where wv.user_Id="+users.getUserid()+") ");
			} else {
				sql.append(" \r\n and "+DbUtil.getWhereCondition(users, "o"));
			}
			sql.append(" \r\n and sam.shipmentdate > to_date('" + paraMap.get("EndDate") + "','yyyy-MM-dd hh24:mi:ss')");
//			sql.append(" \r\n and sam.shipmentdate <  to_date('" + paraMap.get("historeyDate") + "','yyyy-MM-dd hh24:mi:ss')");
			sql.append(" \r\n and sam.movedate < to_date('" + paraMap.get("EndDate") + "','yyyy-MM-dd hh24:mi:ss')");
			if(sysPro != null &&"1".equals(sysPro.getProperty("genReportOnlyForPD"))){ //是否只统计PD
				sql.append(" \r\n and o.organtype = 2 and o.organmodel = 1"); 
			}
			if(!StringUtil.isEmpty((String)paraMap.get("inOrganId"))){
				sql.append(" \r\n and sam.outorganid = '" + paraMap.get("inOrganId") + "' "); //机构条件
			}
			if(!StringUtil.isEmpty((String)paraMap.get("region"))) {
				sql.append(" \r\n and o.province is not null and o.province in (select areaid from region_area where regioncodeid =  '" + paraMap.get("region") + "')"); //机构条件 
			}
			if(!StringUtil.isEmpty((String)paraMap.get("inWarehouseId"))){
				sql.append(" \r\n and sam.OUTWAREHOUSEID = '" + paraMap.get("inWarehouseId") + "' "); //仓库条件
			}
			sql.append(" \r\n join TAKE_TICKET tt on tt.billno = sam.id ");
			sql.append(" \r\n join STOCK_WASTE_BOOK swb on tt.id = swb.billcode");
			if(!StringUtil.isEmpty((String)paraMap.get("ProductName")) && !StringUtil.isEmpty((String)paraMap.get("packSizeName"))) { //产品条件
				sql.append(" \r\n and swb.productid in (select id from product where productname = '"+(String)paraMap.get("ProductName")+"' and specmode= '"+(String)paraMap.get("packSizeName")+"') ");
			} else if(!StringUtil.isEmpty((String)paraMap.get("ProductName")) && StringUtil.isEmpty((String)paraMap.get("packSizeName"))) { //产品条件
				sql.append(" \r\n and swb.productid in (select id from product where productname = '"+(String)paraMap.get("ProductName")+"') ");
			}
			
			sql.append(" \r\n UNION ALL");
			
			// --转仓单(入库)
			sql.append(" \r\n --转仓单(入库)");
			sql.append(" \r\n select o.id as organid,o.organname,o.oecode,o.province,swb.productid,-swb.cycleinquantity as stockpile,0 as stockintransit,sm.INWAREHOUSEID as warehouseid,swb.batch, 0 as stockpiletoship from STOCK_MOVE sm ");
			sql.append(" \r\n join ORGAN o on o.id = sm.inorganid and sm.iscomplete = 1 and sm.inwarehouseid <> outwarehouseid");
			if(DbUtil.isDealer(users)) {
				sql.append(" \r\n  and sm.INWAREHOUSEID in (select wv.warehouse_Id from RULE_USER_WH wv where wv.user_Id="+users.getUserid()+") ");
			} else {
				sql.append(" \r\n and "+DbUtil.getWhereCondition(users, "o"));
			}
			sql.append(" \r\n and sm.receivedate > to_date('" + paraMap.get("EndDate") + "','yyyy-MM-dd hh24:mi:ss')");
//			sql.append(" \r\n and sm.receivedate <  to_date('" + paraMap.get("historeyDate") + "','yyyy-MM-dd hh24:mi:ss')");
			sql.append(" \r\n and sm.SHIPMENTDATE > to_date('" + paraMap.get("EndDate") + "','yyyy-MM-dd hh24:mi:ss')");
			if(sysPro != null &&"1".equals(sysPro.getProperty("genReportOnlyForPD"))){ //是否只统计PD
				sql.append(" \r\n and o.organtype = 2 and o.organmodel = 1"); 
			}
			if(!StringUtil.isEmpty((String)paraMap.get("inOrganId"))){
				sql.append(" \r\n and sm.inorganid = '" + paraMap.get("inOrganId") + "' "); //机构条件
			}
			if(!StringUtil.isEmpty((String)paraMap.get("region"))) {
				sql.append(" \r\n and o.province is not null and o.province in (select areaid from region_area where regioncodeid =  '" + paraMap.get("region") + "')"); //机构条件 
			}
			if(!StringUtil.isEmpty((String)paraMap.get("inWarehouseId"))){
				sql.append(" \r\n and sm.INWAREHOUSEID = '" + paraMap.get("inWarehouseId") + "' "); //仓库条件
			}
			sql.append(" \r\n join STOCK_WASTE_BOOK swb on sm.id = swb.billcode ");
			if(!StringUtil.isEmpty((String)paraMap.get("ProductName")) && !StringUtil.isEmpty((String)paraMap.get("packSizeName"))) { //产品条件
				sql.append(" \r\n and swb.productid in (select id from product where productname = '"+(String)paraMap.get("ProductName")+"' and specmode= '"+(String)paraMap.get("packSizeName")+"') ");
			} else if(!StringUtil.isEmpty((String)paraMap.get("ProductName")) && StringUtil.isEmpty((String)paraMap.get("packSizeName"))) { //产品条件
				sql.append(" \r\n and swb.productid in (select id from product where productname = '"+(String)paraMap.get("ProductName")+"') ");
			}
			
			sql.append(" \r\n UNION ALL");
			//--转仓单(出库)
			sql.append(" \r\n --转仓单(出库)");
			sql.append(" \r\n select o.id as organid,o.organname,o.oecode,o.province,swb.productid,swb.cycleoutquantity as stockpile,0 as stockintransit,sm.OUTWAREHOUSEID as warehouseid,swb.batch, 0 as stockpiletoship from STOCK_MOVE sm ");
			sql.append(" \r\n join ORGAN o on o.id = sm.outorganid and sm.isshipment = 1 and sm.inwarehouseid <> outwarehouseid");
			if(DbUtil.isDealer(users)) {
				sql.append(" \r\n  and sm.OUTWAREHOUSEID in (select wv.warehouse_Id from RULE_USER_WH wv where wv.user_Id="+users.getUserid()+") ");
			} else {
				sql.append(" \r\n and "+DbUtil.getWhereCondition(users, "o"));
			}
			sql.append(" \r\n and sm.shipmentdate > to_date('" + paraMap.get("EndDate") + "','yyyy-MM-dd hh24:mi:ss')");
//			sql.append(" \r\n and sm.shipmentdate <  to_date('" + paraMap.get("historeyDate") + "','yyyy-MM-dd hh24:mi:ss')");
//			sql.append(" \r\n and sm.SHIPMENTDATE > to_date('" + paraMap.get("EndDate") + "','yyyy-MM-dd hh24:mi:ss')");
			if(sysPro != null &&"1".equals(sysPro.getProperty("genReportOnlyForPD"))){ //是否只统计PD
				sql.append(" \r\n and o.organtype = 2 and o.organmodel = 1"); 
			}
			if(!StringUtil.isEmpty((String)paraMap.get("inOrganId"))){
				sql.append(" \r\n and sm.outorganid = '" + paraMap.get("inOrganId") + "' "); //机构条件
			}
			if(!StringUtil.isEmpty((String)paraMap.get("region"))) {
				sql.append(" \r\n and o.province is not null and o.province in (select areaid from region_area where regioncodeid =  '" + paraMap.get("region") + "')"); //机构条件 
			}
			if(!StringUtil.isEmpty((String)paraMap.get("inWarehouseId"))){
				sql.append(" \r\n and sm.OUTWAREHOUSEID = '" + paraMap.get("inWarehouseId") + "' "); //仓库条件
			}
			sql.append(" \r\n join TAKE_TICKET tt on tt.billno = sm.id ");
			sql.append(" \r\n join STOCK_WASTE_BOOK swb on tt.id = swb.billcode ");
			if(!StringUtil.isEmpty((String)paraMap.get("ProductName")) && !StringUtil.isEmpty((String)paraMap.get("packSizeName"))) { //产品条件
				sql.append(" \r\n and swb.productid in (select id from product where productname = '"+(String)paraMap.get("ProductName")+"' and specmode= '"+(String)paraMap.get("packSizeName")+"') ");
			} else if(!StringUtil.isEmpty((String)paraMap.get("ProductName")) && StringUtil.isEmpty((String)paraMap.get("packSizeName"))) { //产品条件
				sql.append(" \r\n and swb.productid in (select id from product where productname = '"+(String)paraMap.get("ProductName")+"') ");
			}
			
			sql.append(" \r\n UNION ALL");
			//--退货单(入库)
			sql.append(" \r\n --退货单(入库)");
			sql.append(" \r\n select o.id as organid,o.organname,o.oecode,o.province,swb.productid,-swb.cycleinquantity as stockpile,0 as stockintransit,ow.INWAREHOUSEID as warehouseid,swb.batch, 0 as stockpiletoship from ORGAN_WITHDRAW ow ");
			sql.append(" \r\n join take_ticket tt on tt.billno = ow.id and ow.iscomplete = 1");
			sql.append(" \r\n and tt.auditdate > to_date('" + paraMap.get("EndDate") + "','yyyy-MM-dd hh24:mi:ss')");
			sql.append(" \r\n join ORGAN o on o.id = ow.receiveorganid");
			if(DbUtil.isDealer(users)) {
				sql.append(" \r\n  and ow.INWAREHOUSEID in (select wv.warehouse_Id from RULE_USER_WH wv where wv.user_Id="+users.getUserid()+") ");
			} else {
				sql.append(" \r\n and "+DbUtil.getWhereCondition(users, "o"));
			}
//			sql.append(" \r\n and ow.auditdate <  to_date('" + paraMap.get("historeyDate") + "','yyyy-MM-dd hh24:mi:ss')");
//			sql.append(" \r\n and ow.makedate > to_date('" + paraMap.get("EndDate") + "','yyyy-MM-dd hh24:mi:ss')");
			if(sysPro != null &&"1".equals(sysPro.getProperty("genReportOnlyForPD"))){ //是否只统计PD
				sql.append(" \r\n and o.organtype = 2 and o.organmodel = 1"); 
			}
			
			if(!StringUtil.isEmpty((String)paraMap.get("inOrganId"))){
				sql.append(" \r\n and ow.receiveorganid = '" + paraMap.get("inOrganId") + "' "); //机构条件
			}
			if(!StringUtil.isEmpty((String)paraMap.get("region"))) {
				sql.append(" \r\n and o.province is not null and o.province in (select areaid from region_area where regioncodeid =  '" + paraMap.get("region") + "')"); //机构条件 
			}
			if(!StringUtil.isEmpty((String)paraMap.get("inWarehouseId"))){
				sql.append(" \r\n and ow.INWAREHOUSEID = '" + paraMap.get("inWarehouseId") + "' "); //仓库条件
			}
			sql.append(" \r\n join STOCK_WASTE_BOOK swb on ow.id = swb.billcode ");
			if(!StringUtil.isEmpty((String)paraMap.get("ProductName")) && !StringUtil.isEmpty((String)paraMap.get("packSizeName"))) { //产品条件
				sql.append(" \r\n and swb.productid in (select id from product where productname = '"+(String)paraMap.get("ProductName")+"' and specmode= '"+(String)paraMap.get("packSizeName")+"') ");
			} else if(!StringUtil.isEmpty((String)paraMap.get("ProductName")) && StringUtil.isEmpty((String)paraMap.get("packSizeName"))) { //产品条件
				sql.append(" \r\n and swb.productid in (select id from product where productname = '"+(String)paraMap.get("ProductName")+"') ");
			}
			
			sql.append(" \r\n UNION ALL");
			//--退货单(出库)
			sql.append(" \r\n --退货单(出库)");
			sql.append(" \r\n select o.id as organid,o.organname,o.oecode,o.province,swb.productid,swb.cycleoutquantity as stockpile,0 as stockintransit,ow.WAREHOUSEID as warehouseid,swb.batch, 0 as stockpiletoship from ORGAN_WITHDRAW ow ");
			sql.append(" \r\n join ORGAN o on o.id = ow.porganid and ow.takestatus = 1");
			if(DbUtil.isDealer(users)) {
				sql.append(" \r\n  and ow.WAREHOUSEID in (select wv.warehouse_Id from RULE_USER_WH wv where wv.user_Id="+users.getUserid()+") ");
			} else {
				sql.append(" \r\n and "+DbUtil.getWhereCondition(users, "o"));
			}
//			sql.append(" \r\n and ow.receivedate >  to_date('" + paraMap.get("historeyDate") + "','yyyy-MM-dd hh24:mi:ss')");
//			sql.append(" \r\n and makedate > to_date('" + paraMap.get("EndDate") + "','yyyy-MM-dd hh24:mi:ss')");
			if(sysPro != null &&"1".equals(sysPro.getProperty("genReportOnlyForPD"))){ //是否只统计PD
				sql.append(" \r\n and o.organtype = 2 and o.organmodel = 1"); 
			}
			if(!StringUtil.isEmpty((String)paraMap.get("inOrganId"))){
				sql.append(" \r\n and ow.porganid = '" + paraMap.get("inOrganId") + "' "); //机构条件
			}
			if(!StringUtil.isEmpty((String)paraMap.get("region"))) {
				sql.append(" \r\n and o.province is not null and o.province in (select areaid from region_area where regioncodeid =  '" + paraMap.get("region") + "')"); //机构条件 
			}
			if(!StringUtil.isEmpty((String)paraMap.get("inWarehouseId"))){
				sql.append(" \r\n and ow.WAREHOUSEID = '" + paraMap.get("inWarehouseId") + "' "); //仓库条件
			}
			sql.append(" \r\n join TAKE_TICKET tt on tt.billno = ow.id ");
			sql.append(" \r\n and tt.auditdate >  to_date('" + paraMap.get("EndDate") + "','yyyy-MM-dd hh24:mi:ss')");
//			sql.append(" \r\n and tt.auditdate <  to_date('" + paraMap.get("historeyDate") + "','yyyy-MM-dd hh24:mi:ss')");
			sql.append(" \r\n join STOCK_WASTE_BOOK swb on tt.id = swb.billcode ");
			if(!StringUtil.isEmpty((String)paraMap.get("ProductName")) && !StringUtil.isEmpty((String)paraMap.get("packSizeName"))) { //产品条件
				sql.append(" \r\n and swb.productid in (select id from product where productname = '"+(String)paraMap.get("ProductName")+"' and specmode= '"+(String)paraMap.get("packSizeName")+"') ");
			} else if(!StringUtil.isEmpty((String)paraMap.get("ProductName")) && StringUtil.isEmpty((String)paraMap.get("packSizeName"))) { //产品条件
				sql.append(" \r\n and swb.productid in (select id from product where productname = '"+(String)paraMap.get("ProductName")+"') ");
			}
//			sql.append(" \r\n  LEFT JOIN F_UNIT f on owd.productid = f.productid and owd.unitid = f.funitid   ");
			sql.append(" \r\n UNION ALL");
			
			
			//-- 未签收的单据（在途库存）
			// --发货单
			sql.append(" \r\n --未签收的单据（在途库存）--发货单");
			sql.append(" \r\n select o.id as organid,o.organname,o.oecode,o.province,swb.productid,0 as stockpile,swb.cycleoutquantity as stockintransit,sam.INWAREHOUSEID as warehouseid,swb.batch, 0 as stockpiletoship from STOCK_ALTER_MOVE sam ");
			sql.append(" \r\n join ORGAN o on o.id = sam.receiveorganid and sam.isshipment = 1 and sam.isblankout = 0 and sam.iscomplete = 0 ");
			if(DbUtil.isDealer(users)) {
				sql.append(" \r\n  and sam.INWAREHOUSEID in (select wv.warehouse_Id from RULE_USER_WH wv where wv.user_Id="+users.getUserid()+") ");
			} else {
				sql.append(" \r\n and "+DbUtil.getWhereCondition(users, "o"));
			}
			sql.append(" \r\n and sam.movedate < to_date('" + paraMap.get("EndDate") + "','yyyy-MM-dd hh24:mi:ss')");
			if(sysPro != null &&"1".equals(sysPro.getProperty("genReportOnlyForPD"))){ //是否只统计PD
				sql.append(" \r\n and o.organtype = 2 and o.organmodel = 1"); 
			}
			if(!StringUtil.isEmpty((String)paraMap.get("inOrganId"))){
				sql.append(" \r\n and sam.receiveorganid = '" + paraMap.get("inOrganId") + "' "); //机构条件
			}
			if(!StringUtil.isEmpty((String)paraMap.get("region"))) {
				sql.append(" \r\n and o.province is not null and o.province in (select areaid from region_area where regioncodeid =  '" + paraMap.get("region") + "')"); //机构条件 
			}
			if(!StringUtil.isEmpty((String)paraMap.get("inWarehouseId"))){
				sql.append(" \r\n and sam.INWAREHOUSEID = '" + paraMap.get("inWarehouseId") + "' "); //仓库条件
			}
			sql.append(" \r\n join TAKE_TICKET tt on tt.billno = sam.id ");
			sql.append(" \r\n join STOCK_WASTE_BOOK swb on tt.id = swb.billcode ");
			if(!StringUtil.isEmpty((String)paraMap.get("ProductName")) && !StringUtil.isEmpty((String)paraMap.get("packSizeName"))) { //产品条件
				sql.append(" \r\n and swb.productid in (select id from product where productname = '"+(String)paraMap.get("ProductName")+"' and specmode= '"+(String)paraMap.get("packSizeName")+"') ");
			} else if(!StringUtil.isEmpty((String)paraMap.get("ProductName")) && StringUtil.isEmpty((String)paraMap.get("packSizeName"))) { //产品条件
				sql.append(" \r\n and swb.productid in (select id from product where productname = '"+(String)paraMap.get("ProductName")+"') ");
			}
			sql.append(" \r\n UNION ALL");
			//--转仓单
			sql.append(" \r\n --未签收的单据（在途库存）--转仓单");
			sql.append(" \r\n select o.id as organid,o.organname,o.oecode,o.province,swb.productid,0 as stockpile,swb.cycleoutquantity as stockintransit,sm.INWAREHOUSEID as warehouseid,swb.batch, 0 as stockpiletoship from STOCK_MOVE sm ");
			sql.append(" \r\n join ORGAN o on o.id = sm.inorganid and sm.isshipment = 1 and sm.iscomplete = 0 and sm.isblankout = 0 and sm.inwarehouseid <> outwarehouseid");
			if(DbUtil.isDealer(users)) {
				sql.append(" \r\n  and sm.INWAREHOUSEID in (select wv.warehouse_Id from RULE_USER_WH wv where wv.user_Id="+users.getUserid()+") ");
			} else {
				sql.append(" \r\n and "+DbUtil.getWhereCondition(users, "o"));
			}
			sql.append(" \r\n and sm.SHIPMENTDATE < to_date('" + paraMap.get("EndDate") + "','yyyy-MM-dd hh24:mi:ss')");
			if(sysPro != null &&"1".equals(sysPro.getProperty("genReportOnlyForPD"))){ //是否只统计PD
				sql.append(" \r\n and o.organtype = 2 and o.organmodel = 1"); 
			}
			if(!StringUtil.isEmpty((String)paraMap.get("inOrganId"))){
				sql.append(" \r\n and sm.inorganid = '" + paraMap.get("inOrganId") + "' "); //机构条件
			}
			if(!StringUtil.isEmpty((String)paraMap.get("region"))) {
				sql.append(" \r\n and o.province is not null and o.province in (select areaid from region_area where regioncodeid =  '" + paraMap.get("region") + "')"); //机构条件 
			}
			if(!StringUtil.isEmpty((String)paraMap.get("inWarehouseId"))){
				sql.append(" \r\n and sm.INWAREHOUSEID = '" + paraMap.get("inWarehouseId") + "' "); //仓库条件
			}
			sql.append(" \r\n join TAKE_TICKET tt on tt.billno = sm.id ");
			sql.append(" \r\n join STOCK_WASTE_BOOK swb on tt.id = swb.billcode ");
			if(!StringUtil.isEmpty((String)paraMap.get("ProductName")) && !StringUtil.isEmpty((String)paraMap.get("packSizeName"))) { //产品条件
				sql.append(" \r\n and swb.productid in (select id from product where productname = '"+(String)paraMap.get("ProductName")+"' and specmode= '"+(String)paraMap.get("packSizeName")+"') ");
			} else if(!StringUtil.isEmpty((String)paraMap.get("ProductName")) && StringUtil.isEmpty((String)paraMap.get("packSizeName"))) { //产品条件
				sql.append(" \r\n and swb.productid in (select id from product where productname = '"+(String)paraMap.get("ProductName")+"') ");
			}
			
			sql.append(" \r\n UNION ALL");
			// --退货单
			sql.append(" \r\n --未签收的单据（在途库存）--退货单");
			sql.append(" \r\n select o.id as organid,o.organname,o.oecode,o.province,swb.productid,0 as stockpile,swb.cycleoutquantity  as stockintransit,ow.INWAREHOUSEID as warehouseid,swb.batch, 0 as stockpiletoship from ORGAN_WITHDRAW ow ");
			sql.append(" \r\n join ORGAN o on o.id = ow.receiveorganid and ow.takestatus = 1 and ow.iscomplete = 0 and ow.isblankout = 0 ");
			if(DbUtil.isDealer(users)) {
				sql.append(" \r\n  and ow.INWAREHOUSEID in (select wv.warehouse_Id from RULE_USER_WH wv where wv.user_Id="+users.getUserid()+") ");
			} else {
				sql.append(" \r\n and "+DbUtil.getWhereCondition(users, "o"));
			}
			if(sysPro != null &&"1".equals(sysPro.getProperty("genReportOnlyForPD"))){ //是否只统计PD
				sql.append(" \r\n and o.organtype = 2 and o.organmodel = 1"); 
			}
			if(!StringUtil.isEmpty((String)paraMap.get("inOrganId"))){
				sql.append(" \r\n and ow.receiveorganid = '" + paraMap.get("inOrganId") + "' "); //机构条件
			}
			if(!StringUtil.isEmpty((String)paraMap.get("region"))) {
				sql.append(" \r\n and o.province is not null and o.province in (select areaid from region_area where regioncodeid =  '" + paraMap.get("region") + "')"); //机构条件 
			}
			if(!StringUtil.isEmpty((String)paraMap.get("inWarehouseId"))){
				sql.append(" \r\n and ow.INWAREHOUSEID = '" + paraMap.get("inWarehouseId") + "' "); //仓库条件
			}
			sql.append(" \r\n join TAKE_TICKET tt on tt.billno = ow.id ");
			sql.append(" \r\n and tt.auditdate < to_date('" + paraMap.get("EndDate") + "','yyyy-MM-dd hh24:mi:ss')");
			sql.append(" \r\n join STOCK_WASTE_BOOK swb on tt.id = swb.billcode ");
			if(!StringUtil.isEmpty((String)paraMap.get("ProductName")) && !StringUtil.isEmpty((String)paraMap.get("packSizeName"))) { //产品条件
				sql.append(" \r\n and swb.productid in (select id from product where productname = '"+(String)paraMap.get("ProductName")+"' and specmode= '"+(String)paraMap.get("packSizeName")+"') ");
			} else if(!StringUtil.isEmpty((String)paraMap.get("ProductName")) && StringUtil.isEmpty((String)paraMap.get("packSizeName"))) { //产品条件
				sql.append(" \r\n and swb.productid in (select id from product where productname = '"+(String)paraMap.get("ProductName")+"') ");
			}
			
			
			//#start modified by ryan.xi at 20150714
			//--分包单(入库)
			sql.append(" \r\n --分包单(入库)");
			sql.append(" \r\n UNION ALL");
			sql.append(" \r\n select o.id as organid,o.organname,o.oecode,o.province,swb.inproductid,-swb.inquantity as stockpile,0 as stockintransit,sam.WAREHOUSEID as warehouseid ,swb.inbatch, 0 as stockpiletoship  from PACK_SEPARATE sam ");
			sql.append(" \r\n join ORGAN o on o.id = sam.ORGANID and sam.ISAUDIT = 1 ");
			if(DbUtil.isDealer(users)) {
				sql.append(" \r\n  and sam.WAREHOUSEID in (select wv.warehouse_Id from RULE_USER_WH wv where wv.user_Id="+users.getUserid()+") ");
			} else {
				sql.append(" \r\n and "+DbUtil.getWhereCondition(users, "o"));
			}
			sql.append(" \r\n and sam.AUDITDATE > to_date('" + paraMap.get("EndDate") + "','yyyy-MM-dd hh24:mi:ss')");
//			sql.append(" \r\n and sam.BILLDATE > to_date('" + paraMap.get("EndDate") + "','yyyy-MM-dd hh24:mi:ss')");
			if(sysPro != null &&"1".equals(sysPro.getProperty("genReportOnlyForPD"))){ //是否只统计PD
				sql.append(" \r\n and o.organtype = 2 and o.organmodel = 1"); 
			}
			if(!StringUtil.isEmpty((String)paraMap.get("inOrganId"))){
				sql.append(" \r\n and sam.ORGANID = '" + paraMap.get("inOrganId") + "' "); //机构条件
			}
			if(!StringUtil.isEmpty((String)paraMap.get("region"))) {
				sql.append(" \r\n and o.province is not null and o.province in (select areaid from region_area where regioncodeid =  '" + paraMap.get("region") + "')"); //机构条件 
			}
			if(!StringUtil.isEmpty((String)paraMap.get("inWarehouseId"))){
				sql.append(" \r\n and sam.WAREHOUSEID = '" + paraMap.get("inWarehouseId") + "' "); //仓库条件
			}
			sql.append(" \r\n join PACK_SEPARATE_DETAIL swb on sam.id = swb.psid ");
			if(!StringUtil.isEmpty((String)paraMap.get("ProductName")) && !StringUtil.isEmpty((String)paraMap.get("packSizeName"))) { //产品条件
				sql.append(" \r\n and swb.inproductid in (select id from product where productname = '"+(String)paraMap.get("ProductName")+"' and specmode= '"+(String)paraMap.get("packSizeName")+"') ");
			} else if(!StringUtil.isEmpty((String)paraMap.get("ProductName")) && StringUtil.isEmpty((String)paraMap.get("packSizeName"))) { //产品条件
				sql.append(" \r\n and swb.inproductid in (select id from product where productname = '"+(String)paraMap.get("ProductName")+"') ");
			}
			
			
			//--分包单(出库)
			sql.append(" \r\n UNION ALL");
			sql.append(" \r\n --分包单(出库)");
			sql.append(" \r\n select o.id as organid,o.organname,o.oecode,o.province,swb.outproductid,swb.outquantity as stockpile,0 as stockintransit,sam.WAREHOUSEID as warehouseid ,swb.outbatch, 0 as stockpiletoship  from PACK_SEPARATE sam ");
			sql.append(" \r\n join ORGAN o on o.id = sam.ORGANID and sam.ISAUDIT = 1 ");
			if(DbUtil.isDealer(users)) {
				sql.append(" \r\n  and sam.WAREHOUSEID in (select wv.warehouse_Id from RULE_USER_WH wv where wv.user_Id="+users.getUserid()+") ");
			} else {
				sql.append(" \r\n and "+DbUtil.getWhereCondition(users, "o"));
			}
			sql.append(" \r\n and sam.AUDITDATE > to_date('" + paraMap.get("EndDate") + "','yyyy-MM-dd hh24:mi:ss')");
//			sql.append(" \r\n and sam.BILLDATE > to_date('" + paraMap.get("EndDate") + "','yyyy-MM-dd hh24:mi:ss')");
			if(sysPro != null &&"1".equals(sysPro.getProperty("genReportOnlyForPD"))){ //是否只统计PD
				sql.append(" \r\n and o.organtype = 2 and o.organmodel = 1"); 
			}
			if(!StringUtil.isEmpty((String)paraMap.get("inOrganId"))){
				sql.append(" \r\n and sam.ORGANID = '" + paraMap.get("inOrganId") + "' "); //机构条件
			}
			if(!StringUtil.isEmpty((String)paraMap.get("region"))) {
				sql.append(" \r\n and o.province is not null and o.province in (select areaid from region_area where regioncodeid =  '" + paraMap.get("region") + "')"); //机构条件 
			}
			if(!StringUtil.isEmpty((String)paraMap.get("inWarehouseId"))){
				sql.append(" \r\n and sam.WAREHOUSEID = '" + paraMap.get("inWarehouseId") + "' "); //仓库条件
			}
			sql.append(" \r\n join PACK_SEPARATE_DETAIL swb on sam.id = swb.psid ");
			if(!StringUtil.isEmpty((String)paraMap.get("ProductName")) && !StringUtil.isEmpty((String)paraMap.get("packSizeName"))) { //产品条件
				sql.append(" \r\n and swb.outproductid in (select id from product where productname = '"+(String)paraMap.get("ProductName")+"' and specmode= '"+(String)paraMap.get("packSizeName")+"') ");
			} else if(!StringUtil.isEmpty((String)paraMap.get("ProductName")) && StringUtil.isEmpty((String)paraMap.get("packSizeName"))) { //产品条件
				sql.append(" \r\n and swb.outproductid in (select id from product where productname = '"+(String)paraMap.get("ProductName")+"') ");
			}
			
			/*
			//--分包单(损耗)
			sql.append(" \r\n UNION ALL");
			sql.append(" \r\n select o.id as organid,o.organname,o.oecode,o.province,swb.outproductid,swb.wastage as stockpile,0 as stockintransit,sam.WAREHOUSEID as warehouseid ,swb.outbatch, 0 as stockpiletoship  from PACK_SEPARATE sam ");
			sql.append(" \r\n join ORGAN o on o.id = sam.ORGANID and sam.ISAUDIT = 1 ");
			sql.append(" \r\n and sam.AUDITDATE > to_date('" + paraMap.get("EndDate") + "','yyyy-MM-dd hh24:mi:ss')");
			if(sysPro != null &&"1".equals(sysPro.getProperty("genReportOnlyForPD"))){ //是否只统计PD
				sql.append(" \r\n and o.organtype = 2 and o.organmodel = 1"); 
			}
			if(!StringUtil.isEmpty((String)paraMap.get("inOrganId"))){
				sql.append(" \r\n and sam.ORGANID = '" + paraMap.get("inOrganId") + "' "); //机构条件
			}
			if(!StringUtil.isEmpty((String)paraMap.get("region"))) {
				sql.append(" \r\n and o.province is not null and o.province in (select areaid from region_area where regioncodeid =  '" + paraMap.get("region") + "')"); //机构条件 
			}
			sql.append(" \r\n  and sam.WAREHOUSEID in (select wv.warehouse_Id from RULE_USER_WH wv where wv.user_Id="+users.getUserid()+") ");
			if(!StringUtil.isEmpty((String)paraMap.get("inWarehouseId"))){
				sql.append(" \r\n and sam.WAREHOUSEID = '" + paraMap.get("inWarehouseId") + "' "); //仓库条件
			}
			sql.append(" \r\n join PACK_SEPARATE_DETAIL swb on sam.id = swb.psid ");
			if(!StringUtil.isEmpty((String)paraMap.get("ProductName")) && !StringUtil.isEmpty((String)paraMap.get("packSizeName"))) { //产品条件
				sql.append(" \r\n and swb.outproductid in (select id from product where productname = '"+(String)paraMap.get("ProductName")+"' and specmode= '"+(String)paraMap.get("packSizeName")+"') ");
			} else if(!StringUtil.isEmpty((String)paraMap.get("ProductName")) && StringUtil.isEmpty((String)paraMap.get("packSizeName"))) { //产品条件
				sql.append(" \r\n and swb.outproductid in (select id from product where productname = '"+(String)paraMap.get("ProductName")+"') ");
			}
			//#end modified by ryan.xi at 20150714
			*/

			//其他入库-减库存-20150429
			sql.append(" \r\n UNION ALL ");
			sql.append(" \r\n --其他入库-减库存-20150429");
			sql.append(" \r\n select o.id as organid,o.organname,o.oecode,o.province,oida.productid,-(oida.quantity * f.xquantity) as stockpile,0 as stockintransit,oia.warehouseid as warehouseid,oida.batch, 0 as stockpiletoship ");
			sql.append(" \r\n from OTHER_INCOME_ALL oia INNER JOIN OTHER_INCOME_DETAIL_ALL oida on oia.id = oida.oiid and oia.isaudit = 1 ");
			if(!StringUtil.isEmpty((String)paraMap.get("inOrganId"))){
				sql.append(" \r\n and oia.organid = '" + paraMap.get("inOrganId") + "' "); //机构条件
			}
			if(!StringUtil.isEmpty((String)paraMap.get("inWarehouseId"))){
				sql.append(" \r\n and oia.warehouseid = '" + paraMap.get("inWarehouseId") + "' "); //仓库条件
			}
			if(!StringUtil.isEmpty((String)paraMap.get("ProductName")) && !StringUtil.isEmpty((String)paraMap.get("packSizeName"))) { //产品条件
				sql.append(" \r\n and oida.productid in (select id from product where productname = '"+paraMap.get("ProductName")+"' and packsizename= '"+paraMap.get("packSizeName")+"') ");
			} else if(!StringUtil.isEmpty((String)paraMap.get("ProductName")) && StringUtil.isEmpty((String)paraMap.get("packSizeName"))) { //产品条件
				sql.append(" \r\n and oida.productid in (select id from product where productname = '"+paraMap.get("ProductName")+"') ");
			}
			if(!StringUtil.isEmpty((String)paraMap.get("EndDate"))){
				sql.append(" \r\n and oia.auditdate >=to_date('" + paraMap.get("EndDate") + "','yyyy-MM-dd hh24:mi:ss') "); //开始时间条件
			}
			if(!StringUtil.isEmpty((String)paraMap.get("historeyDate"))){
				sql.append(" \r\n and oia.auditdate < to_date('" + paraMap.get("historeyDate") + "','yyyy-MM-dd hh24:mi:ss') "); //结束时间条件
			}
			sql.append(" \r\n INNER JOIN ORGAN o on oia.organid= o.id");
			if(!DbUtil.isDealer(users)) {
				sql.append(" \r\n and "+DbUtil.getWhereCondition(users, "o"));
			} 
			if(sysPro != null &&"1".equals(sysPro.getProperty("genReportOnlyForPD"))){ //是否只统计PD
				sql.append(" \r\n and o.organtype = 2 and o.organmodel = 1"); 
			}
			if(!StringUtil.isEmpty((String)paraMap.get("region"))) {
				sql.append(" \r\n and o.province is not null and o.province in (select areaid from region_area where regioncodeid =  '" + paraMap.get("region") + "')"); //机构条件 
			}
			sql.append(" \r\n LEFT JOIN F_UNIT f on oida.productid = f.productid and oida.unitid = f.funitid  "); 
			if(DbUtil.isDealer(users)) {
				sql.append(" \r\n INNER JOIN RULE_USER_WH rw on oia.warehouseid = rw.warehouse_id ");
				sql.append(" \r\n where rw.user_id = " + users.getUserid());
			}
			
			
			
			//其他出库-加库存-20150429
			sql.append(" \r\n UNION ALL ");
			sql.append(" \r\n --其他出库-加库存-20150429");
			sql.append(" \r\n select o.id as organid,o.organname,o.oecode,o.province,oida.productid,(oida.quantity * f.xquantity) as stockpile,0 as stockintransit,oia.warehouseid as warehouseid,oida.batch, 0 as stockpiletoship ");
			sql.append(" \r\n from OTHER_SHIPMENT_BILL_ALL oia INNER JOIN OTHER_SHIPMENT_BILL_D_ALL oida on oia.id = oida.osid and oia.isaudit = 1");
			if(!StringUtil.isEmpty((String)paraMap.get("inOrganId"))){
				sql.append(" \r\n and oia.organid = '" + paraMap.get("inOrganId") + "' "); //机构条件
			}
			if(!StringUtil.isEmpty((String)paraMap.get("inWarehouseId"))){
				sql.append(" \r\n and oia.warehouseid = '" + paraMap.get("inWarehouseId") + "' "); //仓库条件
			}
			if(!StringUtil.isEmpty((String)paraMap.get("ProductName")) && !StringUtil.isEmpty((String)paraMap.get("packSizeName"))) { //产品条件
				sql.append(" \r\n and oida.productid in (select id from product where productname = '"+paraMap.get("ProductName")+"' and packsizename= '"+paraMap.get("packSizeName")+"') ");
			} else if(!StringUtil.isEmpty((String)paraMap.get("ProductName")) && StringUtil.isEmpty((String)paraMap.get("packSizeName"))) { //产品条件
				sql.append(" \r\n and oida.productid in (select id from product where productname = '"+paraMap.get("ProductName")+"') ");
			}
			if(!StringUtil.isEmpty((String)paraMap.get("EndDate"))){
				sql.append(" \r\n and oia.auditdate >=to_date('" + paraMap.get("EndDate") + "','yyyy-MM-dd hh24:mi:ss') "); //开始时间条件
			}
			if(!StringUtil.isEmpty((String)paraMap.get("historeyDate"))){
				sql.append(" \r\n and oia.auditdate < to_date('" + paraMap.get("historeyDate") + "','yyyy-MM-dd hh24:mi:ss') "); //结束时间条件
			}
			sql.append(" \r\n INNER JOIN ORGAN o on oia.organid= o.id");
			if(!DbUtil.isDealer(users)) {
				sql.append(" \r\n and "+DbUtil.getWhereCondition(users, "o"));
			} 
			if(sysPro != null && "1".equals(sysPro.getProperty("genReportOnlyForPD"))){ //是否只统计PD
				sql.append(" \r\n and o.organtype = 2 and o.organmodel = 1"); 
			}
			if(!StringUtil.isEmpty((String)paraMap.get("region"))) {
				sql.append(" \r\n and o.province is not null and o.province in (select areaid from region_area where regioncodeid =  '" + paraMap.get("region") + "')"); //机构条件 
			}
			sql.append(" \r\n LEFT JOIN F_UNIT f on oida.productid = f.productid and oida.unitid = f.funitid  "); 
			if(DbUtil.isDealer(users)) {
				sql.append(" \r\n INNER JOIN RULE_USER_WH rw on oia.warehouseid = rw.warehouse_id ");
				sql.append(" \r\n where rw.user_id = " + users.getUserid());
			}
			
			
		} else {
			//当前的库存明细
			
			
			//在途库存-未签收的单据
			//发货单
			sql.append(" \r\n  select o.id as organid,o.organname,o.oecode,o.province,swb.productid,0 as stockpile,swb.cycleoutquantity as stockintransit,sam.INWAREHOUSEID as warehouseid ,swb.batch, 0 as stockpiletoship");
			sql.append(" \r\n  from STOCK_ALTER_MOVE sam ");
			sql.append(" \r\n  join ORGAN o on o.id = sam.receiveorganid and sam.isshipment = 1 and sam.iscomplete = 0 and sam.isblankout = 0 ");
			if(DbUtil.isDealer(users)) {
				sql.append(" \r\n and sam.INWAREHOUSEID in (select wv.warehouse_Id from RULE_USER_WH wv where wv.user_Id="+users.getUserid()+") ");
			} else {
				sql.append(" \r\n and "+DbUtil.getWhereCondition(users, "o"));
			}
			sql.append(" \r\n and sam.movedate < to_date('" + paraMap.get("EndDate") + "','yyyy-MM-dd hh24:mi:ss')");
			if(!StringUtil.isEmpty((String)paraMap.get("inOrganId"))){
				sql.append(" \r\n and o.id = '" + paraMap.get("inOrganId") + "' "); //机构条件
			}
			if(sysPro != null &&"1".equals(sysPro.getProperty("genReportOnlyForPD"))){ //是否只统计PD
				sql.append(" \r\n and o.organtype = 2 and o.organmodel = 1"); 
			}
			if(!StringUtil.isEmpty((String)paraMap.get("region"))) {
				sql.append(" \r\n and o.province is not null and o.province in (select areaid from region_area where regioncodeid =  '" + paraMap.get("region") + "')"); //大区条件 
			}
			if(!StringUtil.isEmpty((String)paraMap.get("inWarehouseId"))){
				sql.append(" \r\n and sam.INWAREHOUSEID = '" + paraMap.get("inWarehouseId") + "' "); //仓库条件
			}
			
			sql.append(" \r\n join TAKE_TICKET tt on tt.billno = sam.id");
			sql.append(" \r\n join STOCK_WASTE_BOOK swb on tt.id = swb.billcode");
			
			if(!StringUtil.isEmpty((String)paraMap.get("batch"))){
				sql.append(" \r\n and swb.batch = '" + paraMap.get("batch") + "' "); //批次条件
			}
			if(!StringUtil.isEmpty((String)paraMap.get("ProductName")) && !StringUtil.isEmpty((String)paraMap.get("packSizeName"))) { //产品条件
				sql.append(" \r\n and swb.productid in (select id from product where productname = '"+(String)paraMap.get("ProductName")+"' and specmode= '"+(String)paraMap.get("packSizeName")+"') ");
			} else if(!StringUtil.isEmpty((String)paraMap.get("ProductName")) && StringUtil.isEmpty((String)paraMap.get("packSizeName"))) { //产品条件
				sql.append(" \r\n and swb.productid in (select id from product where productname = '"+(String)paraMap.get("ProductName")+"') ");
			}
			
			sql.append(" \r\n UNION ALL ");
			
			//转仓单
			sql.append(" \r\n  select o.id as organid,o.organname,o.oecode,o.province,swb.productid,0 as stockpile,swb.cycleoutquantity as stockintransit,sm.INWAREHOUSEID as warehouseid,swb.batch, 0 as stockpiletoship ");
			sql.append(" \r\n  from STOCK_MOVE sm ");
			sql.append(" \r\n  join ORGAN o on o.id = sm.inorganid and sm.isshipment = 1 and sm.iscomplete = 0 and sm.isblankout = 0 and sm.inwarehouseid <> outwarehouseid");
			if(DbUtil.isDealer(users)) {
				sql.append(" \r\n and sm.INWAREHOUSEID in (select wv.warehouse_Id from RULE_USER_WH wv where wv.user_Id="+users.getUserid()+") ");
			} else {
				sql.append(" \r\n and "+DbUtil.getWhereCondition(users, "o"));
			}
			sql.append(" \r\n and sm.SHIPMENTDATE < to_date('" + paraMap.get("EndDate") + "','yyyy-MM-dd hh24:mi:ss')");
			if(!StringUtil.isEmpty((String)paraMap.get("inOrganId"))){
				sql.append(" \r\n and o.id = '" + paraMap.get("inOrganId") + "' "); //机构条件
			}
			if(sysPro != null &&"1".equals(sysPro.getProperty("genReportOnlyForPD"))){ //是否只统计PD
				sql.append(" \r\n and o.organtype = 2 and o.organmodel = 1"); 
			}
			if(!StringUtil.isEmpty((String)paraMap.get("region"))) {
				sql.append(" \r\n and o.province is not null and o.province in (select areaid from region_area where regioncodeid =  '" + paraMap.get("region") + "')"); //大区条件 
			}
			if(!StringUtil.isEmpty((String)paraMap.get("inWarehouseId"))){
				sql.append(" \r\n and sm.INWAREHOUSEID = '" + paraMap.get("inWarehouseId") + "' "); //仓库条件
			}
			
			sql.append(" \r\n join TAKE_TICKET tt on tt.billno = sm.id");
			sql.append(" \r\n join STOCK_WASTE_BOOK swb on tt.id = swb.billcode");
			
			if(!StringUtil.isEmpty((String)paraMap.get("batch"))){
				sql.append(" \r\n and swb.batch = '" + paraMap.get("batch") + "' "); //批次条件
			}
			if(!StringUtil.isEmpty((String)paraMap.get("ProductName")) && !StringUtil.isEmpty((String)paraMap.get("packSizeName"))) { //产品条件
				sql.append(" \r\n and swb.productid in (select id from product where productname = '"+(String)paraMap.get("ProductName")+"' and specmode= '"+(String)paraMap.get("packSizeName")+"') ");
			} else if(!StringUtil.isEmpty((String)paraMap.get("ProductName")) && StringUtil.isEmpty((String)paraMap.get("packSizeName"))) { //产品条件
				sql.append(" \r\n and swb.productid in (select id from product where productname = '"+(String)paraMap.get("ProductName")+"') ");
			}
			
			sql.append(" \r\n UNION ALL ");
			
			//退货单
			sql.append(" \r\n  select o.id as organid,o.organname,o.oecode,o.province,swb.productid,0 as stockpile,swb.cycleoutquantity as stockintransit,ow.INWAREHOUSEID as warehouseid,swb.batch, 0 as stockpiletoship ");
			sql.append(" \r\n  from ORGAN_WITHDRAW ow  ");
			sql.append(" \r\n  join ORGAN o on o.id = ow.receiveorganid and ow.takestatus = 1 and ow.iscomplete = 0 and ow.isblankout = 0  ");
			if(DbUtil.isDealer(users)) {
				sql.append(" \r\n and ow.INWAREHOUSEID in (select wv.warehouse_Id from RULE_USER_WH wv where wv.user_Id="+users.getUserid()+") ");
			} else {
				sql.append(" \r\n and "+DbUtil.getWhereCondition(users, "o"));
			}
			sql.append(" \r\n and ow.makedate < to_date('" + paraMap.get("EndDate") + "','yyyy-MM-dd hh24:mi:ss')");
			if(!StringUtil.isEmpty((String)paraMap.get("inOrganId"))){
				sql.append(" \r\n and o.id = '" + paraMap.get("inOrganId") + "' "); //机构条件
			}
			if(sysPro != null &&"1".equals(sysPro.getProperty("genReportOnlyForPD"))){ //是否只统计PD
				sql.append(" \r\n and o.organtype = 2 and o.organmodel = 1"); 
			}
			if(!StringUtil.isEmpty((String)paraMap.get("region"))) {
				sql.append(" \r\n and o.province is not null and o.province in (select areaid from region_area where regioncodeid =  '" + paraMap.get("region") + "')"); //大区条件 
			}
			if(!StringUtil.isEmpty((String)paraMap.get("inWarehouseId"))){
				sql.append(" \r\n and ow.INWAREHOUSEID = '" + paraMap.get("inWarehouseId") + "' "); //仓库条件
			}
			
			sql.append(" \r\n join TAKE_TICKET tt on tt.billno = ow.id");
			sql.append(" \r\n join STOCK_WASTE_BOOK swb on tt.id = swb.billcode");
			
			if(!StringUtil.isEmpty((String)paraMap.get("batch"))){
				sql.append(" \r\n and swb.batch = '" + paraMap.get("batch") + "' "); //批次条件
			}
			if(!StringUtil.isEmpty((String)paraMap.get("ProductName")) && !StringUtil.isEmpty((String)paraMap.get("packSizeName"))) { //产品条件
				sql.append(" \r\n and swb.productid in (select id from product where productname = '"+(String)paraMap.get("ProductName")+"' and specmode= '"+(String)paraMap.get("packSizeName")+"') ");
			} else if(!StringUtil.isEmpty((String)paraMap.get("ProductName")) && StringUtil.isEmpty((String)paraMap.get("packSizeName"))) { //产品条件
				sql.append(" \r\n and swb.productid in (select id from product where productname = '"+(String)paraMap.get("ProductName")+"') ");
			}
		}
		
		sql.append(" \r\n ) GROUP BY organid,productid,warehouseid, batch ");
		sql.append(" \r\n ) res  ");
		sql.append(" \r\n join WAREHOUSE wa on res.WAREHOUSEID = wa.id and wa.USEFLAG = 1");
		sql.append(" \r\n LEFT JOIN COUNTRY_AREA ca on ca.id = res.province ");
		
		sql.append(" \r\n LEFT JOIN REGION_AREA ra on ra.areaid = res.province ");
		sql.append(" \r\n LEFT JOIN REGION r on r.regioncode = ra.regioncodeid ");
		sql.append(" \r\n where  (stockpile <> 0 or stockintransit <> 0 or stockpiletoship <> 0)");
		sql.append(" \r\n and  res.productid not in (select id from product where USEFLAG = 0) ");
		
		System.out.println(sql);
//		logger.debug(sql.toString());
		List<Map> list =  new ArrayList<Map>();
		if(request == null && pageSize == 0){
			list =  EntityManager.jdbcquery(sql.toString());
		}else {
			list =  PageQuery.jdbcSqlserverQuery(request, "organid,productid", sql.toString(), pageSize);
		}
		if(list != null && list.size()>0){
			Map<String,FUnit>  funitMap = appFUnit.getAllMap(); //获取所有的单位换算信息
			Map<String,ProductStruct> pStructMap = appProductStruct.getAllMap(); //获取所有产品类型信息
			Map<Integer, String> countUnitMap = appBr.getBaseResourceMap("CountUnit");
			
			for(Map map : list){
				InventoryDetailReport report = new InventoryDetailReport();
				//将Map中对应的值赋值给实例
				MapUtil.mapToObject(map, report);
				Product product = appProduct.loadProductById((String)map.get("productid"));
				//设置产品信息
				report.setmCode(product.getmCode());
				report.setMatericalChDes(product.getMatericalChDes());
				report.setMatericalEnDes(product.getMatericalEnDes());
				report.setPackSizeName(product.getSpecmode());
				report.setPackSizeNameEn(product.getPackSizeNameEn());
				report.setProductName(product.getProductname());
				report.setProductNameen(product.getProductnameen());
				//设置产品类别
				if(pStructMap.get(product.getPsid()) != null) {
					report.setSortName(pStructMap.get(product.getPsid()).getSortname());
				} 
				//获取生产日期与过期日期
				PrintJob printJob = appPrintJob.getPrintJobByBatAMc(product.getmCode(), report.getBatch());
				if(printJob != null) {
					report.setProductionDate(printJob.getProductionDate());
					report.setExpiryDate(printJob.getExpiryDate());
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
								
				resultList.add(report);
			}
		}
		return resultList;
	}
	
	
	// 根据条件查询
	public List<InventoryDetailReport> queryReport(HttpServletRequest request, int pageSize,Map paraMap,UsersBean users) throws Exception{
		
		List<InventoryDetailReport> inventoryList = new ArrayList<InventoryDetailReport>();
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
				return getInventoryDetailHistory(endDate,request,pageSize,paraMap,users);
			} else {
				return getInventoryDetailSalesConsumeHistory(endDate,request,pageSize,paraMap,users);
			}
		} else {
			return getInventoryDetailHistoryAndSalesConsume(inventoryBr.getTagsubvalue(),request,pageSize,paraMap,users);
		}
	}
	
	private List<InventoryDetailReport> getInventoryDetailHistoryAndSalesConsume(
			String historyDateStr, HttpServletRequest request, int pageSize, 
			Map<String,String> paraMap, UsersBean users) throws Exception {
		Calendar historyDate = Calendar.getInstance();
		historyDate.setTime(Dateutil.StringToDate(historyDateStr));
		historyDate.add(Calendar.MONTH, -1);
		//历史库存年月
		int year = historyDate.get(Calendar.YEAR);
		int month = historyDate.get(Calendar.MONTH)+1;
		String endDate = paraMap.get("EndDate") + " 23:59:59";
		List<InventoryDetailReport> resultList = new ArrayList<InventoryDetailReport>();
		StringBuffer sql = new StringBuffer();
		sql.append("select r.sortname regionName,ca.areaname,o.id organId,o.organname,o.oecode,w.id wid,w.warehousename,p.id proid,p.mcode,p.productName,p.productNameen,p.matericalchdes,p.matericalendes,p.Specmode PackSizeName,p.PackSizeNameEn,p.sunit,idh.stockpile,idh.stockpileToShip,idh.stockInTransit stockInTransit,ps.sortname,p.boxquantity,p.countunit,idh.productid,idh.batch from ");
		sql.append("(select organid,productid,sum(stockpile) stockpile,sum(stockpileToShip) stockpileToShip, sum(stockpileInTransit) stockInTransit,WAREHOUSEID,batch from ( ");
		sql.append("select organid,productid,stockpile,stockpileToShip, stockpileInTransit,WAREHOUSEID,batch from INVENTORY_DETAIL_HISTORY ");
		sql.append("where year="+year+" and month="+month+" ");
		if(!StringUtil.isEmpty(paraMap.get("inOrganId"))){
			sql.append(" \r\n and ORGANID = '" + paraMap.get("inOrganId") + "' "); //机构条件
		}
		if(!StringUtil.isEmpty(paraMap.get("inWarehouseId"))){
			sql.append(" \r\n and WAREHOUSEID = '" + paraMap.get("inWarehouseId") + "' "); //仓库条件
		}
		if(DbUtil.isDealer(users)) {
			sql.append(" and warehouseid in (select wv.warehouse_Id from RULE_USER_WH wv where wv.user_Id="+users.getUserid()+")");
		}
		sql.append(" \r\n UNION ALL ");
		
		// 工厂给PD
		sql.append(" \r\n select ");
		sql.append(" \r\n tt.inoid organId ");
		sql.append(" \r\n ,ttd.productid productId  ");
		sql.append(" \r\n ,case when sam.ISCOMPLETE=1 then swb.cycleoutquantity else 0 end salesQuantity"); // 销售数量
		sql.append(" \r\n ,0 ,case when sam.ISCOMPLETE<>1 then swb.cycleoutquantity else 0 end ");
		sql.append(" \r\n ,w.id");
		sql.append(" \r\n ,swb.batch batch ");
		sql.append(" \r\n from TAKE_TICKET tt INNER JOIN TAKE_TICKET_DETAIL ttd on tt.id = ttd.ttid  and tt.isaudit = 1  and tt.bsort = 1 ");
		if(!StringUtil.isEmpty(paraMap.get("inOrganId"))){
			sql.append(" \r\n and tt.inoid = '" + paraMap.get("inOrganId") + "' "); //机构条件
		}
		if(!StringUtil.isEmpty(paraMap.get("inWarehouseId"))){
			sql.append(" \r\n and tt.inwarehouseid = '" + paraMap.get("inWarehouseId") + "' "); //仓库条件
		}
		if(!StringUtil.isEmpty(paraMap.get("ProductName")) && !StringUtil.isEmpty(paraMap.get("packSizeName"))) { //产品条件
			sql.append(" \r\n and ttd.productid in (select id from product where productname = '"+paraMap.get("ProductName")+"' and specmode= '"+paraMap.get("packSizeName")+"') ");
		} else if(!StringUtil.isEmpty(paraMap.get("ProductName")) && StringUtil.isEmpty(paraMap.get("packSizeName"))) { //产品条件
			sql.append(" \r\n and ttd.productid in (select id from product where productname = '"+paraMap.get("ProductName")+"') ");
		}
		//过滤拒签的单据
		sql.append(" \r\n join STOCK_ALTER_MOVE sam on sam.id=tt.billno and sam.isblankout = 0");
		if(!StringUtil.isEmpty(historyDateStr)){
			sql.append(" \r\n and sam.movedate >=to_date('" + historyDateStr + "','yyyy-MM-dd hh24:mi:ss') "); //开始时间条件
		}
		if(!StringUtil.isEmpty(endDate)){
			sql.append(" \r\n and sam.movedate < to_date('" + endDate + "','yyyy-MM-dd hh24:mi:ss') "); //结束时间条件
		}
		sql.append(" \r\n join WAREHOUSE w on w.id = tt.inwarehouseid and w.USEFLAG = 1 ");
		sql.append(" \r\n INNER JOIN STOCK_WASTE_BOOK swb on swb.billcode = tt.id and ttd.productid = swb.productid ");
		sql.append(" \r\n INNER JOIN ORGAN o on tt.oid = o.id and o.organtype = 1  ");
		sql.append(" \r\n INNER JOIN ORGAN ino on tt.inoid = ino.id and ino.ISREPEAL = 0 ");
		//机构类型条件
		sql.append(" \r\n and ino.organtype = 2 and ino.organmodel = 1 ");//经销商
		sql.append(" \r\n LEFT JOIN F_UNIT f on ttd.productid = f.productid and ttd.unitid = f.funitid ");
		if(DbUtil.isDealer(users)) {
			sql.append(" \r\n LEFT JOIN RULE_USER_WH rw on tt.inwarehouseid = rw.warehouse_id  ");
			sql.append(" \r\n where rw.user_id = '" + users.getUserid() + "'");
		} else {
			sql.append(" \r\n where "+DbUtil.getWhereCondition(users, "ino"));
		}
		
		sql.append(" \r\n union all");
		//PD调拨回来的
		sql.append(" \r\n select  ");
		sql.append(" \r\n tt.inoid organId ");
		sql.append(" \r\n ,ttd.productid  ");
		sql.append(" \r\n ,case when sm.ISCOMPLETE=1 then swb.cycleoutquantity else 0 end salesQuantity"); // 销售数量
		sql.append(" \r\n ,0 ,case when sm.ISCOMPLETE<>1 then swb.cycleoutquantity else 0 end"); //消耗数量
		sql.append(" \r\n ,w.id ");
		sql.append(" \r\n ,swb.batch batch ");
		sql.append(" \r\n from TAKE_TICKET tt INNER JOIN TAKE_TICKET_DETAIL ttd on tt.id = ttd.ttid  and tt.isaudit = 1 and tt.bsort = 2 ");
		if(!StringUtil.isEmpty(paraMap.get("inOrganId"))){
			sql.append(" \r\n and tt.inoid = '" + paraMap.get("inOrganId") + "' "); //机构条件
		}
		if(!StringUtil.isEmpty(paraMap.get("inWarehouseId"))){
			sql.append(" \r\n and tt.inwarehouseid = '" + paraMap.get("inWarehouseId") + "' "); //仓库条件
		}
		if(!StringUtil.isEmpty(paraMap.get("ProductName")) && !StringUtil.isEmpty(paraMap.get("packSizeName"))) { //产品条件
			sql.append(" \r\n and ttd.productid in (select id from product where productname = '"+paraMap.get("ProductName")+"' and specmode= '"+paraMap.get("packSizeName")+"') ");
		} else if(!StringUtil.isEmpty(paraMap.get("ProductName")) && StringUtil.isEmpty(paraMap.get("packSizeName"))) { //产品条件
			sql.append(" \r\n and ttd.productid in (select id from product where productname = '"+paraMap.get("ProductName")+"') ");
		}
		sql.append(" \r\n join STOCK_MOVE sm on sm.id=tt.billno");
		if(!StringUtil.isEmpty(historyDateStr)){
			sql.append(" \r\n and sm.SHIPMENTDATE >=to_date('" + historyDateStr + "','yyyy-MM-dd hh24:mi:ss') "); //开始时间条件
		}
		if(!StringUtil.isEmpty(endDate)){
			sql.append(" \r\n and sm.SHIPMENTDATE < to_date('" + endDate + "','yyyy-MM-dd hh24:mi:ss') "); //结束时间条件
		}
		sql.append(" \r\n join WAREHOUSE w on w.id = tt.inwarehouseid and w.USEFLAG = 1 ");
		sql.append(" \r\n INNER JOIN STOCK_WASTE_BOOK swb on swb.billcode = tt.id and ttd.productid = swb.productid ");
		sql.append(" \r\n INNER JOIN ORGAN o on tt.oid = o.id and o.organtype = 2 and o.organmodel = 1 ");
		sql.append(" \r\n LEFT JOIN ORGAN ino on tt.inoid = ino.id and ino.organtype = 2 and ino.organmodel = 1 and ino.ISREPEAL = 0");
		if(DbUtil.isDealer(users)) {
			sql.append(" \r\n LEFT JOIN RULE_USER_WH rw on tt.inwarehouseid = rw.warehouse_id  ");
			sql.append(" \r\n where rw.user_id = '" + users.getUserid() + "'");
		} else {
			sql.append(" \r\n where "+DbUtil.getWhereCondition(users, "o"));
		}
		sql.append(" \r\n union all");
		
		//调拨给其他PD的
		sql.append(" \r\n select  ");
		sql.append(" \r\n tt.oid organId ");
		sql.append(" \r\n ,ttd.productid productId  ");
		sql.append(" \r\n ,-swb.cycleoutquantity salesQuantity"); // 销售数量
		sql.append(" \r\n ,0 ,0"); //消耗数量
		sql.append(" \r\n ,w.id ");
		sql.append(" \r\n ,swb.batch batch ");
		sql.append(" \r\n from TAKE_TICKET tt INNER JOIN TAKE_TICKET_DETAIL ttd on tt.id = ttd.ttid  and tt.isaudit = 1 and tt.bsort = 2 ");
		if(!StringUtil.isEmpty(paraMap.get("inOrganId"))){
			sql.append(" \r\n and tt.oid = '" + paraMap.get("inOrganId") + "' "); //机构条件
		}
		if(!StringUtil.isEmpty(paraMap.get("inWarehouseId"))){
			sql.append(" \r\n and tt.warehouseid = '" + paraMap.get("inWarehouseId") + "' "); //仓库条件
		}
		if(!StringUtil.isEmpty(paraMap.get("ProductName")) && !StringUtil.isEmpty(paraMap.get("packSizeName"))) { //产品条件
			sql.append(" \r\n and ttd.productid in (select id from product where productname = '"+paraMap.get("ProductName")+"' and specmode= '"+paraMap.get("packSizeName")+"') ");
		} else if(!StringUtil.isEmpty(paraMap.get("ProductName")) && StringUtil.isEmpty(paraMap.get("packSizeName"))) { //产品条件
			sql.append(" \r\n and ttd.productid in (select id from product where productname = '"+paraMap.get("ProductName")+"') ");
		}
		sql.append(" \r\n join STOCK_MOVE sm on sm.id=tt.billno");
		if(!StringUtil.isEmpty(historyDateStr)){
			sql.append(" \r\n and sm.SHIPMENTDATE >=to_date('" + historyDateStr + "','yyyy-MM-dd hh24:mi:ss') "); //开始时间条件
		}
		if(!StringUtil.isEmpty(endDate)){
			sql.append(" \r\n and sm.SHIPMENTDATE < to_date('" + endDate + "','yyyy-MM-dd hh24:mi:ss') "); //结束时间条件
		}
		sql.append(" \r\n join WAREHOUSE w on w.id = tt.warehouseid and w.USEFLAG = 1 ");
		sql.append(" \r\n INNER JOIN STOCK_WASTE_BOOK swb on swb.billcode = tt.id and ttd.productid = swb.productid ");
		sql.append(" \r\n INNER JOIN ORGAN o on tt.oid = o.id and o.organtype = 2 and o.organmodel = 1 and o.ISREPEAL = 0");
		sql.append(" \r\n LEFT JOIN ORGAN ino on tt.inoid = ino.id and ino.organtype = 2 and ino.organmodel = 1");
		if(DbUtil.isDealer(users)) {
			sql.append(" \r\n LEFT JOIN RULE_USER_WH rw on tt.warehouseid = rw.warehouse_id  ");
			sql.append(" \r\n where rw.user_id = '" + users.getUserid() + "'");
		} else {
			sql.append(" \r\n where "+DbUtil.getWhereCondition(users, "o"));
		}
		//分包大包减销售
		sql.append(" \r\n union all");
		sql.append(" \r\n select ");
		sql.append(" \r\n o.id organId ");
		sql.append(" \r\n ,ttd.OUTPRODUCTID productId  ");
		sql.append(" \r\n ,-(ttd.outquantity-ttd.wastage) salesQuantity"); // 销售数量
		sql.append(" \r\n ,0 ,0"); //消耗数量
		sql.append(" \r\n ,w.id ");
		sql.append(" \r\n ,ttd.outbatch batch ");
		sql.append(" \r\n from PACK_SEPARATE tt INNER JOIN PACK_SEPARATE_DETAIL ttd on tt.id = ttd.psid  and tt.isaudit = 1 ");
		if(!StringUtil.isEmpty(paraMap.get("inOrganId"))){
			sql.append(" \r\n and tt.ORGANID = '" + paraMap.get("inOrganId") + "' "); //机构条件
		}
		if(!StringUtil.isEmpty(paraMap.get("inWarehouseId"))){
			sql.append(" \r\n and tt.WAREHOUSEID = '" + paraMap.get("inWarehouseId") + "' "); //仓库条件
		}
		if(!StringUtil.isEmpty(paraMap.get("ProductName")) && !StringUtil.isEmpty(paraMap.get("packSizeName"))) { //产品条件
			sql.append(" \r\n and ttd.OUTPRODUCTID in (select id from product where productname = '"+paraMap.get("ProductName")+"' and specmode= '"+paraMap.get("packSizeName")+"') ");
		} else if(!StringUtil.isEmpty(paraMap.get("ProductName")) && StringUtil.isEmpty(paraMap.get("packSizeName"))) { //产品条件
			sql.append(" \r\n and ttd.OUTPRODUCTID in (select id from product where productname = '"+paraMap.get("ProductName")+"') ");
		}
		if(!StringUtil.isEmpty(historyDateStr)){
			sql.append(" \r\n and tt.auditdate >=to_date('" + historyDateStr + "','yyyy-MM-dd hh24:mi:ss') "); //开始时间条件
		}
		if(!StringUtil.isEmpty(endDate)){
			sql.append(" \r\n and tt.auditdate < to_date('" + endDate + "','yyyy-MM-dd hh24:mi:ss') "); //结束时间条件
		}
		sql.append(" \r\n join WAREHOUSE w on w.id = tt.warehouseid and w.USEFLAG = 1 ");
		sql.append(" \r\n INNER JOIN ORGAN o on tt.ORGANID = o.id and o.organtype = 2 and o.organmodel = 1 and o.ISREPEAL = 0");
		if(DbUtil.isDealer(users)) {
			sql.append(" \r\n LEFT JOIN RULE_USER_WH rw on tt.warehouseid = rw.warehouse_id  ");
			sql.append(" \r\n where rw.user_id = '" + users.getUserid() + "'");
		} else {
			sql.append(" \r\n where "+DbUtil.getWhereCondition(users, "o"));
		}
		//分包小包加销售
		sql.append(" \r\n union all");
		sql.append(" \r\n select  ");
		sql.append(" \r\n o.id organId ");
		sql.append(" \r\n ,ttd.INPRODUCTID productId  ");
		sql.append(" \r\n ,ttd.inquantity salesQuantity"); // 销售数量
		sql.append(" \r\n ,0 ,0"); //消耗数量
		sql.append(" \r\n ,w.id ");
		sql.append(" \r\n ,ttd.inbatch batch ");
		
		sql.append(" \r\n from PACK_SEPARATE tt INNER JOIN PACK_SEPARATE_DETAIL ttd on tt.id = ttd.psid  and tt.isaudit = 1 ");
		if(!StringUtil.isEmpty(paraMap.get("inOrganId"))){
			sql.append(" \r\n and tt.ORGANID = '" + paraMap.get("inOrganId") + "' "); //机构条件
		}
		if(!StringUtil.isEmpty(paraMap.get("inWarehouseId"))){
			sql.append(" \r\n and tt.WAREHOUSEID = '" + paraMap.get("inWarehouseId") + "' "); //仓库条件
		}
		if(!StringUtil.isEmpty(paraMap.get("ProductName")) && !StringUtil.isEmpty(paraMap.get("packSizeName"))) { //产品条件
			sql.append(" \r\n and ttd.INPRODUCTID in (select id from product where productname = '"+paraMap.get("ProductName")+"' and specmode= '"+paraMap.get("packSizeName")+"') ");
		} else if(!StringUtil.isEmpty(paraMap.get("ProductName")) && StringUtil.isEmpty(paraMap.get("packSizeName"))) { //产品条件
			sql.append(" \r\n and ttd.INPRODUCTID in (select id from product where productname = '"+paraMap.get("ProductName")+"') ");
		}
		if(!StringUtil.isEmpty(historyDateStr)){
			sql.append(" \r\n and tt.auditdate >=to_date('" + historyDateStr + "','yyyy-MM-dd hh24:mi:ss') "); //开始时间条件
		}
		if(!StringUtil.isEmpty(endDate)){
			sql.append(" \r\n and tt.auditdate < to_date('" + endDate + "','yyyy-MM-dd hh24:mi:ss') "); //结束时间条件
		}
		sql.append(" \r\n join WAREHOUSE w on w.id = tt.warehouseid and w.USEFLAG = 1 ");
		sql.append(" \r\n INNER JOIN ORGAN o on tt.ORGANID = o.id and o.organtype = 2 and o.organmodel = 1 and o.ISREPEAL = 0");
		if(DbUtil.isDealer(users)) {
			sql.append(" \r\n LEFT JOIN RULE_USER_WH rw on tt.warehouseid = rw.warehouse_id  ");
			sql.append(" \r\n where rw.user_id = '" + users.getUserid() + "'");
		} else {
			sql.append(" \r\n where "+DbUtil.getWhereCondition(users, "o"));
		}
		//工厂退回--减销售
		sql.append(" \r\n union all");
		
		sql.append(" \r\n select  ");
		sql.append(" \r\n tt.oid organId ");
		sql.append(" \r\n ,ttd.productid productId  ");
		sql.append(" \r\n ,-swb.cycleoutquantity salesQuantity"); // 销售数量
		sql.append(" \r\n ,0 ,0"); //消耗数量
		sql.append(" \r\n ,w.id");
		sql.append(" \r\n ,swb.batch batch ");
		sql.append(" \r\n from TAKE_TICKET tt INNER JOIN TAKE_TICKET_DETAIL ttd on tt.id = ttd.ttid  and tt.isaudit = 1  and tt.bsort = 7 and tt.billno like 'PW%' ");
		if(!StringUtil.isEmpty(paraMap.get("inOrganId"))){
			sql.append(" \r\n and tt.oid = '" + paraMap.get("inOrganId") + "' "); //机构条件
		}
		if(!StringUtil.isEmpty(paraMap.get("inWarehouseId"))){
			sql.append(" \r\n and tt.warehouseid = '" + paraMap.get("inWarehouseId") + "' "); //仓库条件
		}
		if(!StringUtil.isEmpty(paraMap.get("ProductName")) && !StringUtil.isEmpty(paraMap.get("packSizeName"))) { //产品条件
			sql.append(" \r\n and ttd.productid in (select id from product where productname = '"+paraMap.get("ProductName")+"' and specmode= '"+paraMap.get("packSizeName")+"') ");
		} else if(!StringUtil.isEmpty(paraMap.get("ProductName")) && StringUtil.isEmpty(paraMap.get("packSizeName"))) { //产品条件
			sql.append(" \r\n and ttd.productid in (select id from product where productname = '"+paraMap.get("ProductName")+"') ");
		}
		if(!StringUtil.isEmpty(historyDateStr)){
			sql.append(" \r\n and tt.auditdate >=to_date('" + historyDateStr + "','yyyy-MM-dd hh24:mi:ss') "); //开始时间条件
		}
		if(!StringUtil.isEmpty(endDate)){
			sql.append(" \r\n and tt.auditdate < to_date('" + endDate + "','yyyy-MM-dd hh24:mi:ss') "); //结束时间条件
		}
		sql.append(" \r\n join WAREHOUSE w on w.id = tt.warehouseid and w.USEFLAG = 1 ");
		sql.append(" \r\n INNER JOIN STOCK_WASTE_BOOK swb on swb.billcode = tt.id and ttd.productid = swb.productid   ");
		sql.append(" \r\n INNER JOIN ORGAN o on tt.oid = o.id and o.organtype = 2 and  organmodel = 1 and o.ISREPEAL = 0");
		sql.append(" \r\n LEFT JOIN F_UNIT f on ttd.productid = f.productid and ttd.unitid = f.funitid ");
		if(DbUtil.isDealer(users)) {
			sql.append(" \r\n LEFT JOIN RULE_USER_WH rw on tt.warehouseid = rw.warehouse_id  ");
			sql.append(" \r\n where rw.user_id = '" + users.getUserid() + "'");
		} else {
			sql.append(" \r\n where "+DbUtil.getWhereCondition(users, "o"));
		}
		//采购入库-加销售-20150805
		sql.append(" \r\n union all");
		
		sql.append(" \r\n select  ");
		sql.append(" \r\n o.id  organId");
		sql.append(" \r\n ,oida.productid productId ");
		sql.append(" \r\n ,(oida.quantity * f.xquantity) salesQuantity  ");
		sql.append(" \r\n ,0 ,0  ");
		sql.append(" \r\n ,w.id ");
		sql.append(" \r\n ,oida.batch batch  ");
		sql.append(" \r\n from OTHER_INCOME_ALL oia INNER JOIN OTHER_INCOME_DETAIL_ALL oida on oia.id = oida.oiid and oia.isaudit = 1 and oia.incomesort = 6");
		if(!StringUtil.isEmpty(paraMap.get("inOrganId"))){
			sql.append(" \r\n and oia.organid = '" + paraMap.get("inOrganId") + "' "); //机构条件
		}
		if(!StringUtil.isEmpty(paraMap.get("inWarehouseId"))){
			sql.append(" \r\n and oia.warehouseid = '" + paraMap.get("inWarehouseId") + "' "); //仓库条件
		}
		if(!StringUtil.isEmpty(paraMap.get("ProductName")) && !StringUtil.isEmpty(paraMap.get("packSizeName"))) { //产品条件
			sql.append(" \r\n and oida.productid in (select id from product where productname = '"+paraMap.get("ProductName")+"' and packsizename= '"+paraMap.get("packSizeName")+"') ");
		} else if(!StringUtil.isEmpty(paraMap.get("ProductName")) && StringUtil.isEmpty(paraMap.get("packSizeName"))) { //产品条件
			sql.append(" \r\n and oida.productid in (select id from product where productname = '"+paraMap.get("ProductName")+"') ");
		}
		if(!StringUtil.isEmpty(historyDateStr)){
			sql.append(" \r\n and oia.auditdate >=to_date('" + historyDateStr + "','yyyy-MM-dd hh24:mi:ss') "); //开始时间条件
		}
		if(!StringUtil.isEmpty(endDate)){
			sql.append(" \r\n and oia.auditdate < to_date('" + endDate + "','yyyy-MM-dd hh24:mi:ss') "); //结束时间条件
		}
		sql.append(" \r\n join WAREHOUSE w on w.id = oia.warehouseid and w.USEFLAG = 1 ");
		sql.append(" \r\n INNER JOIN ORGAN o on oia.organid= o.id and o.organtype = 2 and  organmodel = 1 and o.ISREPEAL = 0");
		sql.append(" \r\n LEFT JOIN F_UNIT f on oida.productid = f.productid and oida.unitid = f.funitid ");
		if(DbUtil.isDealer(users)) {
			sql.append(" \r\n INNER JOIN RULE_USER_WH rw on oia.warehouseid = rw.warehouse_id ");
			sql.append(" \r\n where rw.user_id = '" + users.getUserid() +"'");
		} else {
			sql.append(" \r\n where "+DbUtil.getWhereCondition(users, "o"));
		}
		sql.append(" \r\n union all");
		//消耗数量
		sql.append(" \r\n --正常出库消耗 ");
		sql.append(" \r\n select  ");
		sql.append(" \r\n o.id  organId ");
		sql.append(" \r\n ,ttd.productid productId  ");
		sql.append(" \r\n ,-swb.cycleoutquantity  consumeQuantity"); //消耗数量
		sql.append(" \r\n ,0 ,0 ");  // 销售数量
		sql.append(" \r\n ,w.id ");
		sql.append(" \r\n ,swb.batch batch ");
		sql.append(" \r\n from TAKE_TICKET tt INNER JOIN TAKE_TICKET_DETAIL ttd on tt.id = ttd.ttid  and tt.isaudit = 1  and tt.bsort = 1 and tt.oid <> tt.inoid ");
		if(!StringUtil.isEmpty(paraMap.get("inOrganId"))){
			sql.append(" \r\n and tt.oid = '" + paraMap.get("inOrganId") + "' "); //机构条件
		}
		if(!StringUtil.isEmpty(paraMap.get("inWarehouseId"))){
			sql.append(" \r\n and tt.warehouseid = '" + paraMap.get("inWarehouseId") + "' "); //仓库条件
		}
		if(!StringUtil.isEmpty(paraMap.get("ProductName")) && !StringUtil.isEmpty(paraMap.get("packSizeName"))) { //产品条件
			sql.append(" \r\n and ttd.productid in (select id from product where productname = '"+paraMap.get("ProductName")+"' and specmode= '"+paraMap.get("packSizeName")+"') ");
		} else if(!StringUtil.isEmpty(paraMap.get("ProductName")) && StringUtil.isEmpty(paraMap.get("packSizeName"))) { //产品条件
			sql.append(" \r\n and ttd.productid in (select id from product where productname = '"+paraMap.get("ProductName")+"') ");
		}
		if(!StringUtil.isEmpty(historyDateStr)){
			sql.append(" \r\n and tt.auditdate >=to_date('" + historyDateStr + "','yyyy-MM-dd hh24:mi:ss') "); //开始时间条件
		}
		if(!StringUtil.isEmpty(endDate)){
			sql.append(" \r\n and tt.auditdate < to_date('" + endDate + "','yyyy-MM-dd hh24:mi:ss') "); //结束时间条件
		}
		
		sql.append(" \r\n join WAREHOUSE w on w.id = tt.warehouseid and w.USEFLAG = 1");
		
		sql.append(" \r\n INNER JOIN STOCK_WASTE_BOOK swb on swb.billcode = tt.id and ttd.productid = swb.productid ");
		sql.append(" \r\n INNER JOIN ORGAN o on tt.oid = o.id and o.organtype = 2 and  organmodel = 1 ");
		sql.append(" \r\n INNER JOIN ORGAN ino on tt.inoid = ino.id");
		sql.append(" \r\n LEFT JOIN F_UNIT f on ttd.productid = f.productid and ttd.unitid = f.funitid ");
		
		if(DbUtil.isDealer(users)) {
			sql.append(" \r\n LEFT JOIN RULE_USER_WH rw on tt.warehouseid = rw.warehouse_id  ");
			sql.append(" \r\n where rw.user_id = '" + users.getUserid() +"'");
		} else {
			sql.append(" \r\n where "+DbUtil.getWhereCondition(users, "o"));
		}
		sql.append(" \r\n union all");
		sql.append(" \r\n --工厂给PD收发差异记到消耗 ");
		sql.append(" \r\n select  ");
		sql.append(" \r\n ino.id  organId");
		sql.append(" \r\n ,ttd.productid productId ");
		sql.append(" \r\n ,-((samd.quantity-samd.receivequantity) * f.xquantity)  consumeQuantity ");
		sql.append(" \r\n ,0 ,0  ");
		sql.append(" \r\n ,w.id ");
		sql.append(" \r\n ,'"+Constants.NO_BATCH+"' batch  ");
		sql.append(" \r\n from TAKE_TICKET tt INNER JOIN TAKE_TICKET_DETAIL ttd on tt.id = ttd.ttid  and tt.isaudit = 1  and tt.bsort = 1 ");
		if(!StringUtil.isEmpty(paraMap.get("inOrganId"))){
			sql.append(" \r\n and tt.inoid = '" + paraMap.get("inOrganId") + "' "); //机构条件
		}
		if(!StringUtil.isEmpty(paraMap.get("inWarehouseId"))){
			sql.append(" \r\n and tt.inwarehouseid = '" + paraMap.get("inWarehouseId") + "' "); //仓库条件
		}
		if(!StringUtil.isEmpty(paraMap.get("ProductName")) && !StringUtil.isEmpty(paraMap.get("packSizeName"))) { //产品条件
			sql.append(" \r\n and ttd.productid in (select id from product where productname = '"+paraMap.get("ProductName")+"' and specmode= '"+paraMap.get("packSizeName")+"') ");
		} else if(!StringUtil.isEmpty(paraMap.get("ProductName")) && StringUtil.isEmpty(paraMap.get("packSizeName"))) { //产品条件
			sql.append(" \r\n and ttd.productid in (select id from product where productname = '"+paraMap.get("ProductName")+"') ");
		}
		sql.append(" \r\n join STOCK_ALTER_MOVE sam on sam.id=tt.billno and sam.iscomplete =1");
		if(!StringUtil.isEmpty(historyDateStr)){
			sql.append(" \r\n and sam.movedate >=to_date('" + historyDateStr + "','yyyy-MM-dd hh24:mi:ss') "); //开始时间条件
		}
		if(!StringUtil.isEmpty(endDate)){
			sql.append(" \r\n and sam.movedate < to_date('" + endDate + "','yyyy-MM-dd hh24:mi:ss') "); //结束时间条件
		}
		sql.append(" \r\n join WAREHOUSE w on w.id = tt.inwarehouseid and w.USEFLAG = 1");
		sql.append(" \r\n join STOCK_ALTER_MOVE_DETAIL samd on samd.samid = sam.id and samd.productid=ttd.productid");
		sql.append(" \r\n INNER JOIN ORGAN o on tt.oid = o.id and o.organtype = 1   ");
		sql.append(" \r\n LEFT JOIN F_UNIT f on ttd.productid = f.productid and ttd.unitid = f.funitid  ");
		sql.append(" \r\n LEFT JOIN ORGAN ino on tt.inoid = ino.id  and ino.organtype = 2 and ino.organmodel = 1");
		if(DbUtil.isDealer(users)) {
			sql.append(" \r\n LEFT JOIN RULE_USER_WH rw on tt.inwarehouseid = rw.warehouse_id   ");
			sql.append(" \r\n where rw.user_id = " + users.getUserid());
		} else {
			sql.append(" \r\n where "+DbUtil.getWhereCondition(users, "ino"));
		}
		sql.append(" and (samd.quantity-samd.receivequantity) > 0 ");
		//经销商退货-减消耗-20150429
		sql.append(" \r\n union all");
		sql.append(" \r\n --经销商退货-减消耗");
		sql.append(" \r\n select  ");
		sql.append(" \r\n ino.id  organId");
		sql.append(" \r\n ,ttd.productid productId ");
		sql.append(" \r\n ,swb.cycleoutquantity  consumeQuantity ");
		sql.append(" \r\n ,0 ,0  ");
		sql.append(" \r\n ,w.id  ");
		sql.append(" \r\n ,swb.batch batch  ");
		sql.append(" \r\n from TAKE_TICKET tt INNER JOIN TAKE_TICKET_DETAIL ttd on tt.id = ttd.ttid  and tt.isaudit = 1  and tt.bsort = 7 and tt.billno like 'OW%' ");
		if(!StringUtil.isEmpty(paraMap.get("inOrganId"))){
			sql.append(" \r\n and tt.inoid = '" + paraMap.get("inOrganId") + "' "); //机构条件
		}
		if(!StringUtil.isEmpty(paraMap.get("inWarehouseId"))){
			sql.append(" \r\n and tt.inwarehouseid = '" + paraMap.get("inWarehouseId") + "' "); //仓库条件
		}
		if(!StringUtil.isEmpty(paraMap.get("ProductName")) && !StringUtil.isEmpty(paraMap.get("packSizeName"))) { //产品条件
			sql.append(" \r\n and ttd.productid in (select id from product where productname = '"+paraMap.get("ProductName")+"' and specmode= '"+paraMap.get("packSizeName")+"') ");
		} else if(!StringUtil.isEmpty(paraMap.get("ProductName")) && StringUtil.isEmpty(paraMap.get("packSizeName"))) { //产品条件
			sql.append(" \r\n and ttd.productid in (select id from product where productname = '"+paraMap.get("ProductName")+"') ");
		}
		if(!StringUtil.isEmpty(historyDateStr)){
			sql.append(" \r\n and tt.auditdate >=to_date('" + historyDateStr + "','yyyy-MM-dd hh24:mi:ss') "); //开始时间条件
		}
		if(!StringUtil.isEmpty(endDate)){
			sql.append(" \r\n and tt.auditdate < to_date('" + endDate + "','yyyy-MM-dd hh24:mi:ss') "); //结束时间条件
		}
		sql.append(" \r\n join WAREHOUSE w on w.id = tt.inwarehouseid and w.USEFLAG = 1");
		sql.append(" \r\n INNER JOIN ORGAN ino on tt.inoid = ino.id and ino.organtype = 2 and  ino.organmodel = 1 ");
		sql.append(" \r\n INNER JOIN ORGAN o on tt.oid = o.id ");
		sql.append(" \r\n INNER JOIN STOCK_WASTE_BOOK swb on swb.billcode = tt.id and ttd.productid = swb.productid  ");
		sql.append(" \r\n LEFT JOIN F_UNIT f on ttd.productid = f.productid and ttd.unitid = f.funitid  ");
		if(DbUtil.isDealer(users)) {
			sql.append(" \r\n LEFT JOIN RULE_USER_WH rw on tt.inwarehouseid = rw.warehouse_id   ");
			sql.append(" \r\n where rw.user_id = '" + users.getUserid() +"'");
		} else {
			sql.append(" \r\n where "+DbUtil.getWhereCondition(users, "ino"));
		}
		//分包损耗记到消耗
		sql.append(" \r\n --分包损耗记到消耗");
		sql.append(" \r\n union all");
		sql.append(" \r\n select  ");
		sql.append(" \r\n o.id  organId ");
		sql.append(" \r\n ,ttd.OUTPRODUCTID productId  ");
		sql.append(" \r\n ,-ttd.wastage consumeQuantity"); //消耗数量
		sql.append(" \r\n ,0 ,0 ");  // 销售数量
		sql.append(" \r\n ,w.id ");
		sql.append(" \r\n ,ttd.outbatch batch ");
		sql.append(" \r\n from PACK_SEPARATE tt INNER JOIN PACK_SEPARATE_DETAIL ttd on tt.id = ttd.psid  and tt.isaudit = 1 ");
		if(!StringUtil.isEmpty(paraMap.get("inOrganId"))){
			sql.append(" \r\n and tt.ORGANID = '" + paraMap.get("inOrganId") + "' "); //机构条件
		}
		if(!StringUtil.isEmpty(paraMap.get("inWarehouseId"))){
			sql.append(" \r\n and tt.WAREHOUSEID = '" + paraMap.get("inWarehouseId") + "' "); //仓库条件
		}
		if(!StringUtil.isEmpty(paraMap.get("ProductName")) && !StringUtil.isEmpty(paraMap.get("packSizeName"))) { //产品条件
			sql.append(" \r\n and ttd.OUTPRODUCTID in (select id from product where productname = '"+paraMap.get("ProductName")+"' and specmode= '"+paraMap.get("packSizeName")+"') ");
		} else if(!StringUtil.isEmpty(paraMap.get("ProductName")) && StringUtil.isEmpty(paraMap.get("packSizeName"))) { //产品条件
			sql.append(" \r\n and ttd.OUTPRODUCTID in (select id from product where productname = '"+paraMap.get("ProductName")+"') ");
		}
		if(!StringUtil.isEmpty(historyDateStr)){
			sql.append(" \r\n and tt.auditdate >=to_date('" + historyDateStr + "','yyyy-MM-dd hh24:mi:ss') "); //开始时间条件
		}
		if(!StringUtil.isEmpty(endDate)){
			sql.append(" \r\n and tt.auditdate < to_date('" + endDate + "','yyyy-MM-dd hh24:mi:ss') "); //结束时间条件
		}
		
		sql.append(" \r\n join WAREHOUSE w on w.id = tt.warehouseid and w.USEFLAG = 1 ");
		sql.append(" \r\n INNER JOIN ORGAN o on tt.ORGANID = o.id and o.organtype = 2 and  organmodel = 1 ");
		if(DbUtil.isDealer(users)) {
			sql.append(" \r\n LEFT JOIN RULE_USER_WH rw on tt.warehouseid = rw.warehouse_id  ");
			sql.append(" \r\n where rw.user_id = '" + users.getUserid() +"'");
		} else {
			sql.append(" \r\n where "+DbUtil.getWhereCondition(users, "o"));
		}
		sql.append(" \r\n and ttd.wastage <> 0 ");
		//其他入库-减消耗-20150429
		sql.append(" \r\n union all");
		sql.append(" \r\n --其他入库-减消耗");
		sql.append(" \r\n select  ");
		sql.append(" \r\n o.id  organId");
		sql.append(" \r\n ,oida.productid productId ");
		sql.append(" \r\n ,(oida.quantity * f.xquantity) consumeQuantity  ");
		sql.append(" \r\n ,0 ,0  ");
		sql.append(" \r\n ,w.id  ");
		sql.append(" \r\n ,oida.batch batch  ");
		sql.append(" \r\n from OTHER_INCOME_ALL oia INNER JOIN OTHER_INCOME_DETAIL_ALL oida on oia.id = oida.oiid and oia.isaudit = 1 and oia.incomesort <> 6 ");
		if(!StringUtil.isEmpty(paraMap.get("inOrganId"))){
			sql.append(" \r\n and oia.organid = '" + paraMap.get("inOrganId") + "' "); //机构条件
		}
		if(!StringUtil.isEmpty(paraMap.get("inWarehouseId"))){
			sql.append(" \r\n and oia.warehouseid = '" + paraMap.get("inWarehouseId") + "' "); //仓库条件
		}
		if(!StringUtil.isEmpty(paraMap.get("ProductName")) && !StringUtil.isEmpty(paraMap.get("packSizeName"))) { //产品条件
			sql.append(" \r\n and oida.productid in (select id from product where productname = '"+paraMap.get("ProductName")+"' and packsizename= '"+paraMap.get("packSizeName")+"') ");
		} else if(!StringUtil.isEmpty(paraMap.get("ProductName")) && StringUtil.isEmpty(paraMap.get("packSizeName"))) { //产品条件
			sql.append(" \r\n and oida.productid in (select id from product where productname = '"+paraMap.get("ProductName")+"') ");
		}
		if(!StringUtil.isEmpty(historyDateStr)){
			sql.append(" \r\n and oia.auditdate >=to_date('" + historyDateStr + "','yyyy-MM-dd hh24:mi:ss') "); //开始时间条件
		}
		if(!StringUtil.isEmpty(endDate)){
			sql.append(" \r\n and oia.auditdate < to_date('" + endDate + "','yyyy-MM-dd hh24:mi:ss') "); //结束时间条件
		}
		sql.append(" \r\n join WAREHOUSE w on w.id = oia.warehouseid and w.USEFLAG = 1");
		sql.append(" \r\n INNER JOIN ORGAN o on oia.organid= o.id and o.organtype = 2 and  organmodel = 1 ");
		sql.append(" \r\n LEFT JOIN F_UNIT f on oida.productid = f.productid and oida.unitid = f.funitid ");
		if(DbUtil.isDealer(users)) {
			sql.append(" \r\n INNER JOIN RULE_USER_WH rw on oia.warehouseid = rw.warehouse_id ");
			sql.append(" \r\n where rw.user_id = '" + users.getUserid() +"'");
		} else {
			sql.append(" \r\n where "+DbUtil.getWhereCondition(users, "o"));
		}
		//其他出库-加消耗-20150429
		sql.append(" \r\n union all");
		sql.append(" \r\n --其他出库-加消耗");
		sql.append(" \r\n select  ");
		sql.append(" \r\n o.id  organId");
		sql.append(" \r\n ,oida.productid productId ");
		sql.append(" \r\n ,-(oida.quantity * f.xquantity) consumeQuantity  ");
		sql.append(" \r\n ,0 ,0  ");
		sql.append(" \r\n ,w.id  ");
		sql.append(" \r\n ,oida.batch batch  ");
		sql.append(" \r\n from OTHER_SHIPMENT_BILL_ALL oia INNER JOIN OTHER_SHIPMENT_BILL_D_ALL oida on oia.id = oida.osid and oia.isaudit = 1 ");
		if(!StringUtil.isEmpty(paraMap.get("inOrganId"))){
			sql.append(" \r\n and oia.organid = '" + paraMap.get("inOrganId") + "' "); //机构条件
		}
		if(!StringUtil.isEmpty(paraMap.get("inWarehouseId"))){
			sql.append(" \r\n and oia.warehouseid = '" + paraMap.get("inWarehouseId") + "' "); //仓库条件
		}
		if(!StringUtil.isEmpty(paraMap.get("ProductName")) && !StringUtil.isEmpty(paraMap.get("packSizeName"))) { //产品条件
			sql.append(" \r\n and oida.productid in (select id from product where productname = '"+paraMap.get("ProductName")+"' and packsizename= '"+paraMap.get("packSizeName")+"') ");
		} else if(!StringUtil.isEmpty(paraMap.get("ProductName")) && StringUtil.isEmpty(paraMap.get("packSizeName"))) { //产品条件
			sql.append(" \r\n and oida.productid in (select id from product where productname = '"+paraMap.get("ProductName")+"') ");
		}
		if(!StringUtil.isEmpty(historyDateStr)){
			sql.append(" \r\n and oia.auditdate >=to_date('" + historyDateStr + "','yyyy-MM-dd hh24:mi:ss') "); //开始时间条件
		}
		if(!StringUtil.isEmpty(endDate)){
			sql.append(" \r\n and oia.auditdate < to_date('" + endDate + "','yyyy-MM-dd hh24:mi:ss') "); //结束时间条件
		}
		sql.append(" \r\n join WAREHOUSE w on w.id = oia.warehouseid and w.USEFLAG = 1");
		sql.append(" \r\n INNER JOIN ORGAN o on oia.organid= o.id and o.organtype = 2 and  organmodel = 1 ");
		sql.append(" \r\n LEFT JOIN F_UNIT f on oida.productid = f.productid and oida.unitid = f.funitid ");
		if(DbUtil.isDealer(users)) {
			sql.append(" \r\n INNER JOIN RULE_USER_WH rw on oia.warehouseid = rw.warehouse_id ");
			sql.append(" \r\n where rw.user_id = '" + users.getUserid() +"'");
		} else {
			sql.append(" \r\n where "+DbUtil.getWhereCondition(users, "o"));
		}
		sql.append(")temp where 1=1 ");
		if(!StringUtil.isEmpty(paraMap.get("inOrganId"))){
			sql.append(" \r\n and ORGANID = '" + paraMap.get("inOrganId") + "' "); //机构条件
		}
		if(!StringUtil.isEmpty(paraMap.get("inWarehouseId"))){
			sql.append(" \r\n and WAREHOUSEID = '" + paraMap.get("inWarehouseId") + "' "); //仓库条件
		}
		if(DbUtil.isDealer(users)) {
			sql.append(" and warehouseid in (select wv.warehouse_Id from RULE_USER_WH wv where wv.user_Id="+users.getUserid()+")");
		}
		sql.append("GROUP BY organid,productid,WAREHOUSEID,batch ");
		sql.append(") idh ");
		sql.append("join organ o on o.id = idh.organid  and o.ISREPEAL = 0  ");
		if(!DbUtil.isDealer(users)) {
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
		sql.append("where  (stockpile <> 0 or stockintransit <> 0 or stockpiletoship <> 0) ");
		if(!StringUtil.isEmpty((String)paraMap.get("region"))) {
			sql.append(" \r\n and ra.regioncodeid =  '" + paraMap.get("region") + "' "); //大区条件 
		}
		//#end modified by ryan.xi at 20150805
		System.out.println(sql.toString());
		List<Map> list =  new ArrayList<Map>();
		if(request == null && pageSize == 0){
			list =  EntityManager.jdbcquery(sql.toString());
		}else {
			list =  PageQuery.jdbcSqlserverQuery(request, "organid,productid", sql.toString(), pageSize);
		}
		if(list != null && list.size()>0){
			Map<String,FUnit>  funitMap = appFUnit.getAllMap(); //获取所有的单位换算信息
			Map<Integer, String> countUnitMap = appBr.getBaseResourceMap("CountUnit");
			for(Map map : list){
				InventoryDetailReport report = new InventoryDetailReport();
				//将Map中对应的值赋值给实例
				MapUtil.mapToObject(map, report);
				report.setViewDate(((String)paraMap.get("EndDate")).substring(0, 10));
				
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


	private List<InventoryDetailReport> getInventoryDetailSalesConsumeHistory(
			Calendar endDate, HttpServletRequest request, int pageSize,
			Map<String,String> paraMap, UsersBean users) throws Exception { 
		int endYear = endDate.get(Calendar.YEAR);
		int endMonth = endDate.get(Calendar.MONTH)+1;
		String endDateStr = paraMap.get("EndDate") + " 23:59:59";
		endDate.add(Calendar.MONTH, -1);
		int startYear = endDate.get(Calendar.YEAR);
		int startMonth = endDate.get(Calendar.MONTH)+1;
		
		List<InventoryDetailReport> resultList = new ArrayList<InventoryDetailReport>();
		StringBuffer sql = new StringBuffer();
		sql.append("select r.sortname regionName,ca.areaname,o.id organId,o.organname,o.oecode,w.id wid,w.warehousename,p.id proid,p.mcode,p.productName,p.productNameen,p.matericalchdes,p.matericalendes,p.Specmode PackSizeName,p.PackSizeNameEn,p.sunit,idh.stockpile,idh.stockpileToShip,idh.stockInTransit stockInTransit,ps.sortname,p.boxquantity,p.countunit,idh.productid,idh.batch from ");
		sql.append("(select organid,productid,sum(stockpile) stockpile,sum(stockpileToShip) stockpileToShip, sum(stockpileInTransit) stockInTransit,WAREHOUSEID,batch from ( ");
		
		sql.append("select organid,productid,stockpile,stockpileToShip, stockpileInTransit,WAREHOUSEID,batch from INVENTORY_DETAIL_HISTORY ");
		sql.append("where year="+startYear+" and month="+startMonth+" ");
		sql.append("UNION ALL ");
		sql.append("select organid,productid,case when iscomplete <> 0 then SALESQUANTITY else 0 end,0,case when iscomplete = 0 then SALESQUANTITY else 0 end,WAREHOUSEID,batch from SALES_DETAIL_HISTORY ");
		sql.append("where year="+endYear+" and month="+endMonth+" and makedate <= to_date('" + endDateStr + "','yyyy-MM-dd hh24:mi:ss')");
		sql.append("UNION ALL ");
		sql.append("select outorganid,productid,-consumequantity,0,0,OUTWAREHOUSEID,batch from CONSUME_DETAIL_HISTORY ");
		sql.append("where year="+endYear+" and month="+endMonth+" and makedate <= to_date('" + endDateStr + "','yyyy-MM-dd hh24:mi:ss')");
		sql.append(")temp where 1=1 ");
		if(!StringUtil.isEmpty(paraMap.get("inOrganId"))){
			sql.append(" \r\n and ORGANID = '" + paraMap.get("inOrganId") + "' "); //机构条件
		}
		if(!StringUtil.isEmpty(paraMap.get("inWarehouseId"))){
			sql.append(" \r\n and WAREHOUSEID = '" + paraMap.get("inWarehouseId") + "' "); //仓库条件
		}
		if(DbUtil.isDealer(users)) {
			sql.append(" and warehouseid in (select wv.warehouse_Id from RULE_USER_WH wv where wv.user_Id="+users.getUserid()+")");
		}
		sql.append("GROUP BY organid,productid,WAREHOUSEID,batch ");
		sql.append(") idh ");
		sql.append("join organ o on o.id = idh.organid  and o.ISREPEAL = 0  ");
		if(!DbUtil.isDealer(users)) {
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
		sql.append("where  (stockpile <> 0 or stockintransit <> 0 or stockpiletoship <> 0) ");
		if(!StringUtil.isEmpty((String)paraMap.get("region"))) {
			sql.append(" \r\n and ra.regioncodeid =  '" + paraMap.get("region") + "' "); //大区条件 
		}
		System.out.println(sql.toString()); 
		List<Map> list =  new ArrayList<Map>();
		if(request == null && pageSize == 0){
			list =  EntityManager.jdbcquery(sql.toString());
		}else {
			list =  PageQuery.jdbcSqlserverQuery(request, "organid,productid", sql.toString(), pageSize);
		}
		if(list != null && list.size()>0){
			Map<String,FUnit>  funitMap = appFUnit.getAllMap(); //获取所有的单位换算信息
			Map<Integer, String> countUnitMap = appBr.getBaseResourceMap("CountUnit");
			for(Map map : list){
				InventoryDetailReport report = new InventoryDetailReport();
				//将Map中对应的值赋值给实例
				MapUtil.mapToObject(map, report);
				report.setViewDate(((String)paraMap.get("EndDate")).substring(0, 10));
				
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


	private List<InventoryDetailReport> getInventoryDetailHistory(
			Calendar endDate, HttpServletRequest request, int pageSize,
			Map<String,String> paraMap, UsersBean users) throws Exception { 
		int endYear = endDate.get(Calendar.YEAR);
		int endMonth = endDate.get(Calendar.MONTH)+1;
		List<InventoryDetailReport> resultList = new ArrayList<InventoryDetailReport>();
		StringBuffer sql = new StringBuffer();
		sql.append("select r.sortname regionName,ca.areaname,o.id organId,o.organname,o.oecode,w.id wid,w.warehousename,p.id proid,p.mcode,p.productName ,p.productNameen ,p.matericalchdes ,p.matericalendes ,p.Specmode PackSizeName,p.PackSizeNameEn,p.sunit,idh.stockpile,idh.stockpileToShip,idh.stockpileInTransit stockInTransit,ps.sortname,p.boxquantity,p.countunit,idh.productid,idh.batch ");
		sql.append(" from INVENTORY_DETAIL_HISTORY idh ");
		
		sql.append("join organ o on o.id = idh.organid and o.ISREPEAL = 0 ");
		sql.append("and idh.year="+endYear+" and idh.month="+endMonth+" ");
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
		if(request == null && pageSize == 0){
			list =  EntityManager.jdbcquery(sql.toString());
		}else {
			list =  PageQuery.jdbcSqlserverQuery(request, "organid,productid", sql.toString(), pageSize);
		}
		if(list != null && list.size()>0){
			Map<String,FUnit>  funitMap = appFUnit.getAllMap(); //获取所有的单位换算信息
			Map<Integer, String> countUnitMap = appBr.getBaseResourceMap("CountUnit");
			for(Map map : list){
				InventoryDetailReport report = new InventoryDetailReport();
				//将Map中对应的值赋值给实例
				MapUtil.mapToObject(map, report);
				report.setViewDate(((String)paraMap.get("EndDate")).substring(0, 10));
				
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


	public boolean isLastDayOfMonth(Calendar calendar) { 
        calendar.set(Calendar.DATE, (calendar.get(Calendar.DATE) + 1)); 
        if (calendar.get(Calendar.DAY_OF_MONTH) == 1) { 
            return true; 
        } 
        return false; 
    } 
	
}
