package com.winsafe.drp.server;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.winsafe.drp.dao.AppRegion;
import com.winsafe.drp.dao.AppStockAlterMoveDetail;
import com.winsafe.drp.dao.AppStockWasteBook;
import com.winsafe.drp.dao.FUnit;
import com.winsafe.drp.dao.PDGoodsReceivingStatusReport;
import com.winsafe.drp.dao.Product;
import com.winsafe.drp.dao.StockAlterMoveDetail;
import com.winsafe.drp.dao.StockWasteBook;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.util.ArithDouble;
import com.winsafe.drp.util.Constants;
import com.winsafe.drp.util.Dateutil;
import com.winsafe.drp.util.MapUtil;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.StringUtil;
import com.winsafe.hbm.util.pager2.PageQuery;

public class PDGoodsReceivingStatusReportService extends ReportServices {
	private static Logger logger = Logger
			.getLogger(PDGoodsReceivingStatusReportService.class);
	private AppRegion appRegion = new AppRegion();
	private AppStockAlterMoveDetail asamd = new AppStockAlterMoveDetail();
	private AppStockWasteBook appStockWasteBook = new AppStockWasteBook();

	// 根据条件查询
	public List<PDGoodsReceivingStatusReport> queryReport(
			HttpServletRequest request, int pageSize, Map paraMap,
			UsersBean users) throws Exception {

		// 对于结束日期增加一天 
		if (!StringUtil.isEmpty((String) paraMap.get("endDate"))) {
			String endDateStr = (String) paraMap.get("endDate");
			Date endDate = Dateutil.StringToDate(endDateStr);
			endDate = Dateutil.addDay2Date(1, endDate);
			paraMap.put("endDate", Dateutil.formatDate(endDate));
		}

		Integer bSort = 9;
		// if(!StringUtil.isEmpty((String)paraMap.get("bSort"))){
		// bSort = Integer.parseInt((String)paraMap.get("bSort"));
		// }
		Object obj = paraMap.get("BSort");
		if (obj != null && obj != "") {
			bSort = Integer.parseInt((String) obj);
		}

		String isComplete = (String) paraMap.get("isComplete");
		List<PDGoodsReceivingStatusReport> resultList = new ArrayList<PDGoodsReceivingStatusReport>();
		StringBuffer sql = new StringBuffer();

		switch (bSort) {
		case 1:
			// 出货单==1
			sql.append(" \r\n select organid ,max(organname) as organname,max(oecode) as oecode,max(province) as province,wid,max(warehousename) as warehousename,productid,batch,billcode,max(iscomplete) as iscomplete, sum(quantity) as quantity,max(shipmentdate) as shipmentdate, max(receivedate) as receivedate,max(received) as received from (  ");
			if (StringUtil.isEmpty(isComplete)) {
				sql.append(" \r\n select o.id as organid,o.organname,o.oecode,o.province,wa.id as wid,wa.warehousename,swb.productid,swb.batch,sam.id as billcode, 1 as iscomplete,CYCLEINQUANTITY as quantity,sam.shipmentdate,sam.receivedate,1 as received ");
				sql.append(" \r\n from STOCK_WASTE_BOOK swb");
				sql.append(" \r\n join WAREHOUSE wa on swb.warehouseid = wa.id");
				if(DbUtil.isDealer(users)) {
					sql.append(" \r\n and swb.warehouseid in (select wv.warehouse_Id from RULE_USER_WH wv where wv.user_Id="
							+ users.getUserid() + ")");
				}
				sql.append(" \r\n join STOCK_ALTER_MOVE sam on swb.billcode = sam.id");
				if (!StringUtil.isEmpty((String) paraMap.get("beginDate"))) {
					sql.append(" \r\n and sam.makedate >=to_date('"
							+ paraMap.get("beginDate")
							+ "','yyyy-MM-dd hh24:mi:ss') "); // 开始时间条件
				}
				if (!StringUtil.isEmpty((String) paraMap.get("endDate"))) {
					sql.append(" \r\n and sam.makedate < to_date('"
							+ paraMap.get("endDate")
							+ "','yyyy-MM-dd hh24:mi:ss') "); // 结束时间条件
				}
				if (!StringUtil.isEmpty((String) paraMap.get("sapOrderId"))) {
					sql.append(" \r\n and sam.nccode = '"
							+ paraMap.get("sapOrderId") + "'"); // SAP订单号条件
				}
				sql.append(" \r\n and swb.memo = '"
						+ Constants.TT_WASTE_BOOK_MEMO[bSort] + "' ");
				if (!StringUtil.isEmpty((String) paraMap.get("inWarehouseId"))) {
					sql.append(" \r\n and swb.warehouseid = '"
							+ paraMap.get("inWarehouseId") + "' "); // 仓库条件
				}
				if (!StringUtil.isEmpty((String) paraMap.get("ProductName"))
						&& !StringUtil.isEmpty((String) paraMap
								.get("packSizeName"))) { // 产品条件
					sql.append(" \r\n and swb.productid in (select id from product where productname = '"
									+ (String) paraMap.get("ProductName")
									+ "' and specmode= '"
									+ (String) paraMap.get("packSizeName")
									+ "') ");
				} else if (!StringUtil.isEmpty((String) paraMap
						.get("ProductName"))
						&& StringUtil.isEmpty((String) paraMap
								.get("packSizeName"))) { // 产品条件
					sql.append(" \r\n and swb.productid in (select id from product where productname = '"
									+ (String) paraMap.get("ProductName")
									+ "') ");
				}
				sql.append(" \r\n join ORGAN o on sam.receiveorganid = o.id ");
				if(!DbUtil.isDealer(users)) {
					sql.append(" \r\n and "+DbUtil.getWhereCondition(users, "o"));
				}
				if (sysPro != null
						&& "1".equals(sysPro.getProperty("genReportOnlyForPD"))) { // 是否只统计PD
					sql.append(" \r\n and o.organtype = 2 and o.organmodel = 1");
				}
				if (!StringUtil.isEmpty((String) paraMap.get("inOrganId"))) {
					sql.append(" \r\n and o.id = '" + paraMap.get("inOrganId")
							+ "' "); // 机构条件
				}
				if (!StringUtil.isEmpty((String) paraMap.get("region"))) {
					sql.append(" \r\n and o.province is not null and o.province in (select areaid from region_area where regioncodeid =  '"
									+ paraMap.get("region") + "')"); // 区域条件
				}
				sql.append("\r\n where swb.productid not in (select id from product where USEFLAG = 0)");

				sql.append(" \r\n UNION ALL");
				// 发货数量与码数量不一致的情况
				sql.append(" \r\n select o.id as organid,o.organname,o.oecode,o.province,wa.id as wid,wa.warehousename,swb.productid,swb.batch,sam.id as billcode, 1 as iscomplete,CYCLEOUTQUANTITY as quantity,sam.shipmentdate,sam.receivedate, 0 as received");
				sql.append(" \r\n from STOCK_WASTE_BOOK swb ");
				sql.append(" \r\n join TAKE_TICKET tt on swb.billcode = tt.id ");
				if (!StringUtil.isEmpty((String) paraMap.get("inWarehouseId"))) {
					sql.append(" \r\n and tt.inwarehouseid = '"
							+ paraMap.get("inWarehouseId") + "' "); // 仓库条件
				}
				if (!StringUtil.isEmpty((String) paraMap.get("ProductName"))
						&& !StringUtil.isEmpty((String) paraMap
								.get("packSizeName"))) { // 产品条件
					sql.append(" \r\n and swb.productid in (select id from product where productname = '"
									+ (String) paraMap.get("ProductName")
									+ "' and specmode= '"
									+ (String) paraMap.get("packSizeName")
									+ "') ");
				} else if (!StringUtil.isEmpty((String) paraMap
						.get("ProductName"))
						&& StringUtil.isEmpty((String) paraMap
								.get("packSizeName"))) { // 产品条件
					sql.append(" \r\n and swb.productid in (select id from product where productname = '"
									+ (String) paraMap.get("ProductName")
									+ "') ");
				}
				sql.append(" \r\n and swb.batch = '" + Constants.NO_BATCH
								+ "'");
				sql.append(" \r\n and swb.memo = '检货小票-出货'");
				if(DbUtil.isDealer(users)) {
					sql.append(" \r\n and tt.inwarehouseid in (select wv.warehouse_Id from RULE_USER_WH wv where wv.user_Id="
							+ users.getUserid() + ")");
				}
				sql.append(" \r\n and not EXISTS (select * from STOCK_WASTE_BOOK where billcode = tt.billno and warehouseid = tt.inwarehouseid and productid = swb.productid and batch = swb.batch)");
				sql.append(" \r\n join STOCK_ALTER_MOVE sam on sam.id = tt.billno");
				sql.append(" \r\n and sam.iscomplete=1");
				if (!StringUtil.isEmpty((String) paraMap.get("beginDate"))) {
					sql.append(" \r\n and sam.makedate >=to_date('"
							+ paraMap.get("beginDate")
							+ "','yyyy-MM-dd hh24:mi:ss') "); // 开始时间条件
				}
				if (!StringUtil.isEmpty((String) paraMap.get("endDate"))) {
					sql.append(" \r\n and sam.makedate < to_date('"
							+ paraMap.get("endDate")
							+ "','yyyy-MM-dd hh24:mi:ss') "); // 结束时间条件
				}
				if (!StringUtil.isEmpty((String) paraMap.get("sapOrderId"))) {
					sql.append(" \r\n and sam.nccode = '"
							+ paraMap.get("sapOrderId") + "'"); // SAP订单号条件
				}
				sql.append(" \r\n join ORGAN o on sam.receiveorganid = o.id  ");
				if(!DbUtil.isDealer(users)) {
					sql.append(" \r\n and "+DbUtil.getWhereCondition(users, "o"));
				}
				if (sysPro != null
						&& "1".equals(sysPro.getProperty("genReportOnlyForPD"))) { // 是否只统计PD
					sql.append(" \r\n and o.organtype = 2 and o.organmodel = 1");
				}
				if (!StringUtil.isEmpty((String) paraMap.get("inOrganId"))) {
					sql.append(" \r\n and o.id = '" + paraMap.get("inOrganId")
							+ "'"); // 机构条件
				}
				if (!StringUtil.isEmpty((String) paraMap.get("region"))) {
					sql.append(" \r\n and o.province is not null and o.province in (select areaid from region_area where regioncodeid =  '"
									+ paraMap.get("region") + "')"); // 区域条件
				}
				sql.append(" \r\n left join WAREHOUSE wa on tt.inwarehouseid = wa.id");
				sql.append("\r\n where swb.productid not in (select id from product where USEFLAG = 0)");
				sql.append(" \r\n UNION ALL");

				sql.append(" \r\n select o.id as organid,o.organname,o.oecode,o.province,wa.id as wid, wa.warehousename,swb.productid,swb.batch,sam.id as billcode, sam.iscomplete,swb.cycleoutquantity as quantity,shipmentdate, receivedate, 0 as received ");
				sql.append(" \r\n from STOCK_ALTER_MOVE sam");
				sql.append(" \r\n join ORGAN o on sam.receiveorganid = o.id and sam.isshipment=1 and sam.iscomplete=0 and sam.ISBLANKOUT=0");
				if(!DbUtil.isDealer(users)) {
					sql.append(" \r\n and "+DbUtil.getWhereCondition(users, "o"));
				}
				if (sysPro != null
						&& "1".equals(sysPro.getProperty("genReportOnlyForPD"))) { // 是否只统计PD
					sql.append(" \r\n and o.organtype = 2 and o.organmodel = 1");
				}
				if(DbUtil.isDealer(users)) {
					sql.append(" \r\n and sam.inwarehouseid in (select wv.warehouse_Id from RULE_USER_WH wv where wv.user_Id="
							+ users.getUserid() + ")");
				}
				if (!StringUtil.isEmpty((String) paraMap.get("inOrganId"))) {
					sql.append(" \r\n and o.id = '" + paraMap.get("inOrganId")
							+ "' "); // 机构条件
				}
				if (!StringUtil.isEmpty((String) paraMap.get("inWarehouseId"))) {
					sql.append(" \r\n and sam.inwarehouseid = '"
							+ paraMap.get("inWarehouseId") + "' "); // 仓库条件
				}
				if (!StringUtil.isEmpty((String) paraMap.get("sapOrderId"))) {
					sql.append(" \r\n and sam.nccode = '"
							+ paraMap.get("sapOrderId") + "'"); // SAP订单号条件
				}
				if (!StringUtil.isEmpty((String) paraMap.get("region"))) {
					sql.append(" \r\n and o.province is not null and o.province in (select areaid from region_area where regioncodeid =  '"
									+ paraMap.get("region") + "')"); // 机构条件
				}
				if (!StringUtil.isEmpty((String) paraMap.get("beginDate"))) {
					sql.append(" \r\n and sam.makedate >=to_date('"
							+ paraMap.get("beginDate")
							+ "','yyyy-MM-dd hh24:mi:ss') "); // 开始时间条件
				}
				if (!StringUtil.isEmpty((String) paraMap.get("endDate"))) {
					sql.append(" \r\n and sam.makedate < to_date('"
							+ paraMap.get("endDate")
							+ "','yyyy-MM-dd hh24:mi:ss') "); // 结束时间条件
				}
				sql.append(" \r\n join TAKE_TICKET tt on sam.id = tt.billno");
				sql.append(" \r\n join STOCK_WASTE_BOOK swb on swb.billcode = tt.id");
				if (!StringUtil.isEmpty((String) paraMap.get("ProductName"))
						&& !StringUtil.isEmpty((String) paraMap
								.get("packSizeName"))) { // 产品条件
					sql.append(" \r\n and swb.productid in (select id from product where productname = '"
									+ (String) paraMap.get("ProductName")
									+ "' and specmode= '"
									+ (String) paraMap.get("packSizeName")
									+ "') ");
				} else if (!StringUtil.isEmpty((String) paraMap
						.get("ProductName"))
						&& StringUtil.isEmpty((String) paraMap
								.get("packSizeName"))) { // 产品条件
					sql.append(" \r\n and swb.productid in (select id from product where productname = '"
									+ (String) paraMap.get("ProductName")
									+ "') ");
				}
				sql.append(" \r\n left join WAREHOUSE wa on sam.inwarehouseid = wa.id ");
				sql.append("\r\n where swb.productid not in (select id from product where USEFLAG = 0)");
			} else if ("1".equals(isComplete.trim())) {
				sql.append(" \r\n select o.id as organid,o.organname,o.oecode,o.province,wa.id as wid,wa.warehousename,swb.productid,swb.batch,sam.id as billcode, 1 as iscomplete,CYCLEINQUANTITY as quantity,sam.shipmentdate,sam.receivedate,1 as received ");
				sql.append(" \r\n from STOCK_WASTE_BOOK swb");
				sql.append(" \r\n join WAREHOUSE wa on swb.warehouseid = wa.id");
				if(DbUtil.isDealer(users)) {
					sql.append(" \r\n and swb.warehouseid in (select wv.warehouse_Id from RULE_USER_WH wv where wv.user_Id="
							+ users.getUserid() + ")");
				}
				sql.append(" \r\n join STOCK_ALTER_MOVE sam on swb.billcode = sam.id");
				if (!StringUtil.isEmpty((String) paraMap.get("beginDate"))) {
					sql.append(" \r\n and sam.makedate >=to_date('"
							+ paraMap.get("beginDate")
							+ "','yyyy-MM-dd hh24:mi:ss') "); // 开始时间条件
				}
				if (!StringUtil.isEmpty((String) paraMap.get("endDate"))) {
					sql.append(" \r\n and sam.makedate < to_date('"
							+ paraMap.get("endDate")
							+ "','yyyy-MM-dd hh24:mi:ss') "); // 结束时间条件
				}
				if (!StringUtil.isEmpty((String) paraMap.get("sapOrderId"))) {
					sql.append(" \r\n and sam.nccode = '"
							+ paraMap.get("sapOrderId") + "'"); // SAP订单号条件
				}
				sql.append(" \r\n and swb.memo = '"
						+ Constants.TT_WASTE_BOOK_MEMO[bSort] + "' ");
				if (!StringUtil.isEmpty((String) paraMap.get("inWarehouseId"))) {
					sql.append(" \r\n and swb.warehouseid = '"
							+ paraMap.get("inWarehouseId") + "' "); // 仓库条件
				}
				if (!StringUtil.isEmpty((String) paraMap.get("ProductName"))
						&& !StringUtil.isEmpty((String) paraMap
								.get("packSizeName"))) { // 产品条件
					sql.append(" \r\n and swb.productid in (select id from product where productname = '"
									+ (String) paraMap.get("ProductName")
									+ "' and specmode= '"
									+ (String) paraMap.get("packSizeName")
									+ "') ");
				} else if (!StringUtil.isEmpty((String) paraMap
						.get("ProductName"))
						&& StringUtil.isEmpty((String) paraMap
								.get("packSizeName"))) { // 产品条件
					sql.append(" \r\n and swb.productid in (select id from product where productname = '"
									+ (String) paraMap.get("ProductName")
									+ "') ");
				}
				sql.append(" \r\n join ORGAN o on sam.receiveorganid = o.id ");
				if(!DbUtil.isDealer(users)) {
					sql.append(" \r\n and "+DbUtil.getWhereCondition(users, "o"));
				}
				if (sysPro != null
						&& "1".equals(sysPro.getProperty("genReportOnlyForPD"))) { // 是否只统计PD
					sql.append(" \r\n and o.organtype = 2 and o.organmodel = 1");
				}
				if (!StringUtil.isEmpty((String) paraMap.get("inOrganId"))) {
					sql.append(" \r\n and o.id = '" + paraMap.get("inOrganId")
							+ "' "); // 机构条件
				}
				if (!StringUtil.isEmpty((String) paraMap.get("region"))) {
					sql.append(" \r\n and o.province is not null and o.province in (select areaid from region_area where regioncodeid =  '"
									+ paraMap.get("region") + "')"); // 区域条件
				}
				sql.append("\r\n where swb.productid not in (select id from product where USEFLAG = 0)");
				sql.append(" \r\n UNION ALL");

				sql.append(" \r\n select o.id as organid,o.organname,o.oecode,o.province,wa.id as wid,wa.warehousename,swb.productid,swb.batch,sam.id as billcode, 1 as iscomplete,CYCLEOUTQUANTITY as quantity,sam.shipmentdate,sam.receivedate, 0 as received");
				sql.append(" \r\n from STOCK_WASTE_BOOK swb ");
				sql.append(" \r\n join TAKE_TICKET tt on swb.billcode = tt.id ");
				if (!StringUtil.isEmpty((String) paraMap.get("inWarehouseId"))) {
					sql.append(" \r\n and tt.inwarehouseid = '"
							+ paraMap.get("inWarehouseId") + "' "); // 仓库条件
				}
				if (!StringUtil.isEmpty((String) paraMap.get("ProductName"))
						&& !StringUtil.isEmpty((String) paraMap
								.get("packSizeName"))) { // 产品条件
					sql.append(" \r\n and swb.productid in (select id from product where productname = '"
									+ (String) paraMap.get("ProductName")
									+ "' and specmode= '"
									+ (String) paraMap.get("packSizeName")
									+ "') ");
				} else if (!StringUtil.isEmpty((String) paraMap
						.get("ProductName"))
						&& StringUtil.isEmpty((String) paraMap
								.get("packSizeName"))) { // 产品条件
					sql.append(" \r\n and swb.productid in (select id from product where productname = '"
									+ (String) paraMap.get("ProductName")
									+ "') ");
				}
				sql.append(" \r\n and swb.batch = '" + Constants.NO_BATCH
								+ "'");
				sql.append(" \r\n and swb.memo = '检货小票-出货'");
				if(DbUtil.isDealer(users)) {
					sql.append(" \r\n and tt.inwarehouseid in (select wv.warehouse_Id from RULE_USER_WH wv where wv.user_Id="
							+ users.getUserid() + ")");
				}
				sql.append(" \r\n and not EXISTS (select * from STOCK_WASTE_BOOK where billcode = tt.billno and warehouseid = tt.inwarehouseid and productid = swb.productid and batch = swb.batch)");
				sql.append(" \r\n join STOCK_ALTER_MOVE sam on sam.id = tt.billno");
				sql.append(" \r\n and sam.iscomplete=1");
				if (!StringUtil.isEmpty((String) paraMap.get("beginDate"))) {
					sql.append(" \r\n and sam.makedate >=to_date('"
							+ paraMap.get("beginDate")
							+ "','yyyy-MM-dd hh24:mi:ss') "); // 开始时间条件
				}
				if (!StringUtil.isEmpty((String) paraMap.get("endDate"))) {
					sql.append(" \r\n and sam.makedate < to_date('"
							+ paraMap.get("endDate")
							+ "','yyyy-MM-dd hh24:mi:ss') "); // 结束时间条件
				}
				if (!StringUtil.isEmpty((String) paraMap.get("sapOrderId"))) {
					sql.append(" \r\n and sam.nccode = '"
							+ paraMap.get("sapOrderId") + "'"); // SAP订单号条件
				}
				sql.append(" \r\n join ORGAN o on sam.receiveorganid = o.id  ");
				if(!DbUtil.isDealer(users)) {
					sql.append(" \r\n and "+DbUtil.getWhereCondition(users, "o"));
				}
				if (sysPro != null
						&& "1".equals(sysPro.getProperty("genReportOnlyForPD"))) { // 是否只统计PD
					sql.append(" \r\n and o.organtype = 2 and o.organmodel = 1");
				}
				if (!StringUtil.isEmpty((String) paraMap.get("inOrganId"))) {
					sql.append(" \r\n and o.id = '" + paraMap.get("inOrganId")
							+ "' "); // 机构条件
				}
				if (!StringUtil.isEmpty((String) paraMap.get("region"))) {
					sql.append(" \r\n and o.province is not null and o.province in (select areaid from region_area where regioncodeid =  '"
									+ paraMap.get("region") + "')"); // 区域条件
				}
				sql.append(" \r\n left join WAREHOUSE wa on tt.inwarehouseid = wa.id");
				sql.append("\r\n where swb.productid not in (select id from product where USEFLAG = 0)");
			} else {
				sql.append(" \r\n select o.id as organid,o.organname,o.oecode,o.province,wa.id as wid, wa.warehousename,swb.productid,swb.batch,sam.id as billcode, sam.iscomplete,swb.cycleoutquantity as quantity,shipmentdate, receivedate, 0 as received ");
				sql.append(" \r\n from STOCK_ALTER_MOVE sam");
				sql.append(" \r\n join ORGAN o on sam.receiveorganid = o.id and sam.isshipment=1 and sam.iscomplete=0 and sam.ISBLANKOUT=0");
				if(!DbUtil.isDealer(users)) {
					sql.append(" \r\n and "+DbUtil.getWhereCondition(users, "o"));
				}
				if (sysPro != null
						&& "1".equals(sysPro.getProperty("genReportOnlyForPD"))) { // 是否只统计PD
					sql.append(" \r\n and o.organtype = 2 and o.organmodel = 1");
				}
				if(DbUtil.isDealer(users)) {
					sql.append(" \r\n and sam.inwarehouseid in (select wv.warehouse_Id from RULE_USER_WH wv where wv.user_Id="
							+ users.getUserid() + ")");
				}
				if (!StringUtil.isEmpty((String) paraMap.get("inOrganId"))) {
					sql.append(" \r\n and o.id = '" + paraMap.get("inOrganId")
							+ "' "); // 机构条件
				}
				if (!StringUtil.isEmpty((String) paraMap.get("inWarehouseId"))) {
					sql.append(" \r\n and sam.inwarehouseid = '"
							+ paraMap.get("inWarehouseId") + "' "); // 仓库条件
				}
				if (!StringUtil.isEmpty((String) paraMap.get("sapOrderId"))) {
					sql.append(" \r\n and sam.nccode = '"
							+ paraMap.get("sapOrderId") + "'"); // SAP订单号条件
				}
				if (!StringUtil.isEmpty((String) paraMap.get("region"))) {
					sql.append(" \r\n and o.province is not null and o.province in (select areaid from region_area where regioncodeid =  '"
									+ paraMap.get("region") + "')"); // 机构条件
				}
				if (!StringUtil.isEmpty((String) paraMap.get("beginDate"))) {
					sql.append(" \r\n and sam.makedate >=to_date('"
							+ paraMap.get("beginDate")
							+ "','yyyy-MM-dd hh24:mi:ss') "); // 开始时间条件
				}
				if (!StringUtil.isEmpty((String) paraMap.get("endDate"))) {
					sql.append(" \r\n and sam.makedate < to_date('"
							+ paraMap.get("endDate")
							+ "','yyyy-MM-dd hh24:mi:ss') "); // 结束时间条件
				}
				sql.append(" \r\n join TAKE_TICKET tt on sam.id = tt.billno");
				sql.append(" \r\n join STOCK_WASTE_BOOK swb on swb.billcode = tt.id");
				if (!StringUtil.isEmpty((String) paraMap.get("ProductName"))
						&& !StringUtil.isEmpty((String) paraMap
								.get("packSizeName"))) { // 产品条件
					sql.append(" \r\n and swb.productid in (select id from product where productname = '"
									+ (String) paraMap.get("ProductName")
									+ "' and specmode= '"
									+ (String) paraMap.get("packSizeName")
									+ "') ");
				} else if (!StringUtil.isEmpty((String) paraMap
						.get("ProductName"))
						&& StringUtil.isEmpty((String) paraMap
								.get("packSizeName"))) { // 产品条件
					sql.append(" \r\n and swb.productid in (select id from product where productname = '"
									+ (String) paraMap.get("ProductName")
									+ "') ");
				}
				sql.append(" \r\n left join WAREHOUSE wa on sam.inwarehouseid = wa.id ");
				sql.append("\r\n where swb.productid not in (select id from product where USEFLAG = 0)");
			}
			sql.append(" \r\n ) group by organid,wid,productid,batch,billcode");
			break;
		case 2:
			// 转仓单 ==2
			sql
					.append(" \r\n select organid ,max(organname) as organname,max(oecode) as oecode,max(province) as province,wid,max(warehousename) as warehousename,productid,batch,billcode,max(iscomplete) as iscomplete, sum(quantity) as quantity,max(shipmentdate) as shipmentdate, max(receivedate) as receivedate,max(received) as received from (  ");
			if (StringUtil.isEmpty(isComplete)) {
				sql.append(" \r\n select o.id as organid,o.organname,o.oecode,o.province,wa.id as wid,wa.warehousename,swb.productid,swb.batch,sam.id as billcode, 1 as iscomplete,CYCLEINQUANTITY as quantity,sam.shipmentdate,sam.receivedate,1 as received ");
				sql.append(" \r\n from STOCK_WASTE_BOOK swb");
				sql.append(" \r\n join WAREHOUSE wa on swb.warehouseid = wa.id");
				if(DbUtil.isDealer(users)) {
					sql.append(" \r\n and swb.warehouseid in (select wv.warehouse_Id from RULE_USER_WH wv where wv.user_Id="
							+ users.getUserid() + ")");
				}
				sql.append(" \r\n join STOCK_MOVE sam on swb.billcode = sam.id");
				if (!StringUtil.isEmpty((String) paraMap.get("beginDate"))) {
					sql.append(" \r\n and sam.makedate >=to_date('"
							+ paraMap.get("beginDate")
							+ "','yyyy-MM-dd hh24:mi:ss') "); // 开始时间条件
				}
				if (!StringUtil.isEmpty((String) paraMap.get("endDate"))) {
					sql.append(" \r\n and sam.makedate < to_date('"
							+ paraMap.get("endDate")
							+ "','yyyy-MM-dd hh24:mi:ss') "); // 结束时间条件
				}
				if (!StringUtil.isEmpty((String) paraMap.get("sapOrderId"))) {
					sql.append(" \r\n and sam.nccode = '"
							+ paraMap.get("sapOrderId") + "'"); // SAP订单号条件
				}
				sql.append(" \r\n and swb.memo = '"
						+ Constants.TT_WASTE_BOOK_MEMO[bSort] + "' ");
				if (!StringUtil.isEmpty((String) paraMap.get("inWarehouseId"))) {
					sql.append(" \r\n and swb.warehouseid = '"
							+ paraMap.get("inWarehouseId") + "' "); // 仓库条件
				}
				if (!StringUtil.isEmpty((String) paraMap.get("ProductName"))
						&& !StringUtil.isEmpty((String) paraMap
								.get("packSizeName"))) { // 产品条件
					sql.append(" \r\n and swb.productid in (select id from product where productname = '"
									+ (String) paraMap.get("ProductName")
									+ "' and specmode= '"
									+ (String) paraMap.get("packSizeName")
									+ "') ");
				} else if (!StringUtil.isEmpty((String) paraMap
						.get("ProductName"))
						&& StringUtil.isEmpty((String) paraMap
								.get("packSizeName"))) { // 产品条件
					sql.append(" \r\n and swb.productid in (select id from product where productname = '"
									+ (String) paraMap.get("ProductName")
									+ "') ");
				}
				sql.append(" \r\n join ORGAN o on sam.inorganid = o.id ");
				if(!DbUtil.isDealer(users)) {
					sql.append(" \r\n and "+DbUtil.getWhereCondition(users, "o"));
				}
				if (sysPro != null
						&& "1".equals(sysPro.getProperty("genReportOnlyForPD"))) { // 是否只统计PD
					sql.append(" \r\n and o.organtype = 2 and o.organmodel = 1");
				}
				if (!StringUtil.isEmpty((String) paraMap.get("inOrganId"))) {
					sql.append(" \r\n and o.id = '" + paraMap.get("inOrganId")
							+ "' "); // 机构条件
				}
				if (!StringUtil.isEmpty((String) paraMap.get("region"))) {
					sql.append(" \r\n and o.province is not null and o.province in (select areaid from region_area where regioncodeid =  '"
									+ paraMap.get("region") + "')"); // 区域条件
				}
				sql.append("\r\n where swb.productid not in (select id from product where USEFLAG = 0)");
				sql.append(" \r\n UNION ALL");

				// 发货数量与码数量不一致的情况
				sql.append(" \r\n select o.id as organid,o.organname,o.oecode,o.province,wa.id as wid,wa.warehousename,swb.productid,swb.batch,sam.id as billcode, 1 as iscomplete,CYCLEOUTQUANTITY as quantity,sam.shipmentdate,sam.receivedate, 0 as received");
				sql.append(" \r\n from STOCK_WASTE_BOOK swb ");
				sql.append(" \r\n join TAKE_TICKET tt on swb.billcode = tt.id ");
				if (!StringUtil.isEmpty((String) paraMap.get("inWarehouseId"))) {
					sql.append(" \r\n and tt.inwarehouseid = '"
							+ paraMap.get("inWarehouseId") + "' "); // 仓库条件
				}
				if (!StringUtil.isEmpty((String) paraMap.get("ProductName"))
						&& !StringUtil.isEmpty((String) paraMap
								.get("packSizeName"))) { // 产品条件
					sql.append(" \r\n and swb.productid in (select id from product where productname = '"
									+ (String) paraMap.get("ProductName")
									+ "' and specmode= '"
									+ (String) paraMap.get("packSizeName")
									+ "') ");
				} else if (!StringUtil.isEmpty((String) paraMap
						.get("ProductName"))
						&& StringUtil.isEmpty((String) paraMap
								.get("packSizeName"))) { // 产品条件
					sql.append(" \r\n and swb.productid in (select id from product where productname = '"
									+ (String) paraMap.get("ProductName")
									+ "') ");
				}
				sql.append(" \r\n and swb.batch = '" + Constants.NO_BATCH
								+ "'");
				sql.append(" \r\n and swb.memo = '检货小票-出货'");
				if(DbUtil.isDealer(users)) {
					sql.append(" \r\n and tt.inwarehouseid in (select wv.warehouse_Id from RULE_USER_WH wv where wv.user_Id="
							+ users.getUserid() + ")");
				}
				sql.append(" \r\n and not EXISTS (select * from STOCK_WASTE_BOOK where billcode = tt.billno and warehouseid = tt.inwarehouseid and productid = swb.productid and batch = swb.batch)");
				sql.append(" \r\n join STOCK_MOVE sam on sam.id = tt.billno");
				sql.append(" \r\n and sam.iscomplete=1");
				if (!StringUtil.isEmpty((String) paraMap.get("beginDate"))) {
					sql.append(" \r\n and sam.makedate >=to_date('"
							+ paraMap.get("beginDate")
							+ "','yyyy-MM-dd hh24:mi:ss') "); // 开始时间条件
				}
				if (!StringUtil.isEmpty((String) paraMap.get("endDate"))) {
					sql.append(" \r\n and sam.makedate < to_date('"
							+ paraMap.get("endDate")
							+ "','yyyy-MM-dd hh24:mi:ss') "); // 结束时间条件
				}
				if (!StringUtil.isEmpty((String) paraMap.get("sapOrderId"))) {
					sql.append(" \r\n and sam.nccode = '"
							+ paraMap.get("sapOrderId") + "'"); // SAP订单号条件
				}
				sql.append(" \r\n join ORGAN o on sam.inorganid = o.id  ");
				if(!DbUtil.isDealer(users)) {
					sql.append(" \r\n and "+DbUtil.getWhereCondition(users, "o"));
				}
				if (sysPro != null
						&& "1".equals(sysPro.getProperty("genReportOnlyForPD"))) { // 是否只统计PD
					sql.append(" \r\n and o.organtype = 2 and o.organmodel = 1");
				}
				if (!StringUtil.isEmpty((String) paraMap.get("inOrganId"))) {
					sql.append(" \r\n and o.id = '" + paraMap.get("inOrganId")
							+ "'"); // 机构条件
				}
				if (!StringUtil.isEmpty((String) paraMap.get("region"))) {
					sql.append(" \r\n and o.province is not null and o.province in (select areaid from region_area where regioncodeid =  '"
									+ paraMap.get("region") + "')"); // 区域条件
				}
				sql.append(" \r\n left join WAREHOUSE wa on tt.inwarehouseid = wa.id");
				sql.append("\r\n where swb.productid not in (select id from product where USEFLAG = 0)");
				sql.append(" \r\n UNION ALL");

				sql.append(" \r\n select o.id as organid,o.organname,o.oecode,o.province,wa.id as wid, wa.warehousename,swb.productid,swb.batch,sam.id as billcode, sam.iscomplete,swb.cycleoutquantity as quantity,shipmentdate, receivedate, 0 as received ");
				sql.append(" \r\n from STOCK_MOVE sam");
				sql.append(" \r\n join ORGAN o on sam.inorganid = o.id and sam.isshipment=1 and sam.iscomplete=0 and sam.ISBLANKOUT=0");
				if(!DbUtil.isDealer(users)) {
					sql.append(" \r\n and "+DbUtil.getWhereCondition(users, "o"));
				}
				if (sysPro != null
						&& "1".equals(sysPro.getProperty("genReportOnlyForPD"))) { // 是否只统计PD
					sql.append(" \r\n and o.organtype = 2 and o.organmodel = 1");
				}
				if(DbUtil.isDealer(users)) {
					sql.append(" \r\n and sam.inwarehouseid in (select wv.warehouse_Id from RULE_USER_WH wv where wv.user_Id="
							+ users.getUserid() + ")");
				}
				if (!StringUtil.isEmpty((String) paraMap.get("inOrganId"))) {
					sql.append(" \r\n and o.id = '" + paraMap.get("inOrganId")
							+ "' "); // 机构条件
				}
				if (!StringUtil.isEmpty((String) paraMap.get("inWarehouseId"))) {
					sql.append(" \r\n and sam.inwarehouseid = '"
							+ paraMap.get("inWarehouseId") + "' "); // 仓库条件
				}
				if (!StringUtil.isEmpty((String) paraMap.get("sapOrderId"))) {
					sql.append(" \r\n and sam.nccode = '"
							+ paraMap.get("sapOrderId") + "'"); // SAP订单号条件
				}
				if (!StringUtil.isEmpty((String) paraMap.get("region"))) {
					sql.append(" \r\n and o.province is not null and o.province in (select areaid from region_area where regioncodeid =  '"
									+ paraMap.get("region") + "')"); // 机构条件
				}
				if (!StringUtil.isEmpty((String) paraMap.get("beginDate"))) {
					sql.append(" \r\n and sam.makedate >=to_date('"
							+ paraMap.get("beginDate")
							+ "','yyyy-MM-dd hh24:mi:ss') "); // 开始时间条件
				}
				if (!StringUtil.isEmpty((String) paraMap.get("endDate"))) {
					sql.append(" \r\n and sam.makedate < to_date('"
							+ paraMap.get("endDate")
							+ "','yyyy-MM-dd hh24:mi:ss') "); // 结束时间条件
				}
				sql.append(" \r\n join TAKE_TICKET tt on sam.id = tt.billno");
				sql.append(" \r\n join STOCK_WASTE_BOOK swb on swb.billcode = tt.id");
				if (!StringUtil.isEmpty((String) paraMap.get("ProductName"))
						&& !StringUtil.isEmpty((String) paraMap
								.get("packSizeName"))) { // 产品条件
					sql.append(" \r\n and swb.productid in (select id from product where productname = '"
									+ (String) paraMap.get("ProductName")
									+ "' and specmode= '"
									+ (String) paraMap.get("packSizeName")
									+ "') ");
				} else if (!StringUtil.isEmpty((String) paraMap
						.get("ProductName"))
						&& StringUtil.isEmpty((String) paraMap
								.get("packSizeName"))) { // 产品条件
					sql.append(" \r\n and swb.productid in (select id from product where productname = '"
									+ (String) paraMap.get("ProductName")
									+ "') ");
				}
				sql.append(" \r\n left join WAREHOUSE wa on sam.inwarehouseid = wa.id ");
				sql.append("\r\n where swb.productid not in (select id from product where USEFLAG = 0)");
			} else if ("1".equals(isComplete.trim())) {
				sql.append(" \r\n select o.id as organid,o.organname,o.oecode,o.province,wa.id as wid,wa.warehousename,swb.productid,swb.batch,sam.id as billcode, 1 as iscomplete,CYCLEINQUANTITY as quantity,sam.shipmentdate,sam.receivedate,1 as received ");
				sql.append(" \r\n from STOCK_WASTE_BOOK swb");
				sql.append(" \r\n join WAREHOUSE wa on swb.warehouseid = wa.id");
				if(DbUtil.isDealer(users)) {
					sql.append(" \r\n and swb.warehouseid in (select wv.warehouse_Id from RULE_USER_WH wv where wv.user_Id="
							+ users.getUserid() + ")");
				}
				sql.append(" \r\n join STOCK_MOVE sam on swb.billcode = sam.id");
				if (!StringUtil.isEmpty((String) paraMap.get("beginDate"))) {
					sql.append(" \r\n and sam.makedate >=to_date('"
							+ paraMap.get("beginDate")
							+ "','yyyy-MM-dd hh24:mi:ss') "); // 开始时间条件
				}
				if (!StringUtil.isEmpty((String) paraMap.get("endDate"))) {
					sql.append(" \r\n and sam.makedate < to_date('"
							+ paraMap.get("endDate")
							+ "','yyyy-MM-dd hh24:mi:ss') "); // 结束时间条件
				}
				if (!StringUtil.isEmpty((String) paraMap.get("sapOrderId"))) {
					sql.append(" \r\n and sam.nccode = '"
							+ paraMap.get("sapOrderId") + "'"); // SAP订单号条件
				}
				sql.append(" \r\n and swb.memo = '"
						+ Constants.TT_WASTE_BOOK_MEMO[bSort] + "' ");
				if (!StringUtil.isEmpty((String) paraMap.get("inWarehouseId"))) {
					sql.append(" \r\n and swb.warehouseid = '"
							+ paraMap.get("inWarehouseId") + "' "); // 仓库条件
				}
				if (!StringUtil.isEmpty((String) paraMap.get("ProductName"))
						&& !StringUtil.isEmpty((String) paraMap
								.get("packSizeName"))) { // 产品条件
					sql.append(" \r\n and swb.productid in (select id from product where productname = '"
									+ (String) paraMap.get("ProductName")
									+ "' and specmode= '"
									+ (String) paraMap.get("packSizeName")
									+ "') ");
				} else if (!StringUtil.isEmpty((String) paraMap
						.get("ProductName"))
						&& StringUtil.isEmpty((String) paraMap
								.get("packSizeName"))) { // 产品条件
					sql.append(" \r\n and swb.productid in (select id from product where productname = '"
									+ (String) paraMap.get("ProductName")
									+ "') ");
				}
				sql.append(" \r\n join ORGAN o on sam.inorganid = o.id ");
				if(!DbUtil.isDealer(users)) {
					sql.append(" \r\n and "+DbUtil.getWhereCondition(users, "o"));
				}
				if (sysPro != null
						&& "1".equals(sysPro.getProperty("genReportOnlyForPD"))) { // 是否只统计PD
					sql.append(" \r\n and o.organtype = 2 and o.organmodel = 1");
				}
				if (!StringUtil.isEmpty((String) paraMap.get("inOrganId"))) {
					sql.append(" \r\n and o.id = '" + paraMap.get("inOrganId")
							+ "' "); // 机构条件
				}
				if (!StringUtil.isEmpty((String) paraMap.get("region"))) {
					sql.append(" \r\n and o.province is not null and o.province in (select areaid from region_area where regioncodeid =  '"
									+ paraMap.get("region") + "')"); // 区域条件
				}
				sql.append("\r\n where swb.productid not in (select id from product where USEFLAG = 0)");
				sql.append(" \r\n UNION ALL");

				sql.append(" \r\n select o.id as organid,o.organname,o.oecode,o.province,wa.id as wid,wa.warehousename,swb.productid,swb.batch,sam.id as billcode, 1 as iscomplete,CYCLEOUTQUANTITY as quantity,sam.shipmentdate,sam.receivedate, 0 as received");
				sql.append(" \r\n from STOCK_WASTE_BOOK swb ");
				sql.append(" \r\n join TAKE_TICKET tt on swb.billcode = tt.id ");
				if (!StringUtil.isEmpty((String) paraMap.get("inWarehouseId"))) {
					sql.append(" \r\n and tt.inwarehouseid = '"
							+ paraMap.get("inWarehouseId") + "' "); // 仓库条件
				}
				if (!StringUtil.isEmpty((String) paraMap.get("ProductName"))
						&& !StringUtil.isEmpty((String) paraMap
								.get("packSizeName"))) { // 产品条件
					sql.append(" \r\n and swb.productid in (select id from product where productname = '"
									+ (String) paraMap.get("ProductName")
									+ "' and specmode= '"
									+ (String) paraMap.get("packSizeName")
									+ "') ");
				} else if (!StringUtil.isEmpty((String) paraMap
						.get("ProductName"))
						&& StringUtil.isEmpty((String) paraMap
								.get("packSizeName"))) { // 产品条件
					sql.append(" \r\n and swb.productid in (select id from product where productname = '"
									+ (String) paraMap.get("ProductName")
									+ "') ");
				}
				sql.append(" \r\n and swb.batch = '" + Constants.NO_BATCH
								+ "'");
				sql.append(" \r\n and swb.memo = '检货小票-出货'");
				if(DbUtil.isDealer(users)) {
					sql.append(" \r\n and tt.inwarehouseid in (select wv.warehouse_Id from RULE_USER_WH wv where wv.user_Id="
							+ users.getUserid() + ")");
				}
				sql.append(" \r\n and not EXISTS (select * from STOCK_WASTE_BOOK where billcode = tt.billno and warehouseid = tt.inwarehouseid and productid = swb.productid and batch = swb.batch)");
				sql.append(" \r\n join STOCK_MOVE sam on sam.id = tt.billno");
				sql.append(" \r\n and sam.iscomplete=1");
				if (!StringUtil.isEmpty((String) paraMap.get("beginDate"))) {
					sql.append(" \r\n and sam.makedate >=to_date('"
							+ paraMap.get("beginDate")
							+ "','yyyy-MM-dd hh24:mi:ss') "); // 开始时间条件
				}
				if (!StringUtil.isEmpty((String) paraMap.get("endDate"))) {
					sql.append(" \r\n and sam.makedate < to_date('"
							+ paraMap.get("endDate")
							+ "','yyyy-MM-dd hh24:mi:ss') "); // 结束时间条件
				}
				if (!StringUtil.isEmpty((String) paraMap.get("sapOrderId"))) {
					sql.append(" \r\n and sam.nccode = '"
							+ paraMap.get("sapOrderId") + "'"); // SAP订单号条件
				}
				sql.append(" \r\n join ORGAN o on sam.inorganid = o.id  ");
				if(!DbUtil.isDealer(users)) {
					sql.append(" \r\n and "+DbUtil.getWhereCondition(users, "o"));
				}
				if (sysPro != null
						&& "1".equals(sysPro.getProperty("genReportOnlyForPD"))) { // 是否只统计PD
					sql.append(" \r\n and o.organtype = 2 and o.organmodel = 1");
				}
				if (!StringUtil.isEmpty((String) paraMap.get("inOrganId"))) {
					sql.append(" \r\n and o.id = '" + paraMap.get("inOrganId")
							+ "' "); // 机构条件
				}
				if (!StringUtil.isEmpty((String) paraMap.get("region"))) {
					sql.append(" \r\n and o.province is not null and o.province in (select areaid from region_area where regioncodeid =  '"
									+ paraMap.get("region") + "')"); // 区域条件
				}
				sql.append(" \r\n left join WAREHOUSE wa on tt.inwarehouseid = wa.id");
				sql.append("\r\n where swb.productid not in (select id from product where USEFLAG = 0)");
			} else {
				sql.append(" \r\n select o.id as organid,o.organname,o.oecode,o.province,wa.id as wid, wa.warehousename,swb.productid,swb.batch,sam.id as billcode, sam.iscomplete,swb.cycleoutquantity as quantity,shipmentdate, receivedate, 0 as received ");
				sql.append(" \r\n from STOCK_MOVE sam");
				sql.append(" \r\n join ORGAN o on sam.inorganid = o.id and sam.isshipment=1 and sam.iscomplete=0 and sam.ISBLANKOUT=0");
				if(!DbUtil.isDealer(users)) {
					sql.append(" \r\n and "+DbUtil.getWhereCondition(users, "o"));
				} else {
					sql.append(" \r\n and sam.inwarehouseid in (select wv.warehouse_Id from RULE_USER_WH wv where wv.user_Id="
							+ users.getUserid() + ")");
				}
				if (sysPro != null
						&& "1".equals(sysPro.getProperty("genReportOnlyForPD"))) { // 是否只统计PD
					sql.append(" \r\n and o.organtype = 2 and o.organmodel = 1");
				}
				if (!StringUtil.isEmpty((String) paraMap.get("inOrganId"))) {
					sql.append(" \r\n and o.id = '" + paraMap.get("inOrganId")
							+ "' "); // 机构条件
				}
				if (!StringUtil.isEmpty((String) paraMap.get("inWarehouseId"))) {
					sql.append(" \r\n and sam.inwarehouseid = '"
							+ paraMap.get("inWarehouseId") + "' "); // 仓库条件
				}
				if (!StringUtil.isEmpty((String) paraMap.get("sapOrderId"))) {
					sql.append(" \r\n and sam.nccode = '"
							+ paraMap.get("sapOrderId") + "'"); // SAP订单号条件
				}
				if (!StringUtil.isEmpty((String) paraMap.get("region"))) {
					sql.append(" \r\n and o.province is not null and o.province in (select areaid from region_area where regioncodeid =  '"
									+ paraMap.get("region") + "')"); // 机构条件
				}
				if (!StringUtil.isEmpty((String) paraMap.get("beginDate"))) {
					sql.append(" \r\n and sam.makedate >=to_date('"
							+ paraMap.get("beginDate")
							+ "','yyyy-MM-dd hh24:mi:ss') "); // 开始时间条件
				}
				if (!StringUtil.isEmpty((String) paraMap.get("endDate"))) {
					sql.append(" \r\n and sam.makedate < to_date('"
							+ paraMap.get("endDate")
							+ "','yyyy-MM-dd hh24:mi:ss') "); // 结束时间条件
				}
				sql.append(" \r\n join TAKE_TICKET tt on sam.id = tt.billno");
				sql.append(" \r\n join STOCK_WASTE_BOOK swb on swb.billcode = tt.id");
				if (!StringUtil.isEmpty((String) paraMap.get("ProductName"))
						&& !StringUtil.isEmpty((String) paraMap
								.get("packSizeName"))) { // 产品条件
					sql.append(" \r\n and swb.productid in (select id from product where productname = '"
									+ (String) paraMap.get("ProductName")
									+ "' and specmode= '"
									+ (String) paraMap.get("packSizeName")
									+ "') ");
				} else if (!StringUtil.isEmpty((String) paraMap
						.get("ProductName"))
						&& StringUtil.isEmpty((String) paraMap
								.get("packSizeName"))) { // 产品条件
					sql.append(" \r\n and swb.productid in (select id from product where productname = '"
									+ (String) paraMap.get("ProductName")
									+ "') ");
				}
				sql.append(" \r\n left join WAREHOUSE wa on sam.inwarehouseid = wa.id ");
				sql.append("\r\n where swb.productid not in (select id from product where USEFLAG = 0)");
			}
			sql.append(" \r\n ) group by organid,wid,productid,batch,billcode");
			break;

		case 7:

			// 退货单 ==7
			sql
					.append(" \r\n select organid ,max(organname) as organname,max(oecode) as oecode,max(province) as province,wid,max(warehousename) as warehousename,productid,batch,billcode,max(iscomplete) as iscomplete, sum(quantity) as quantity,max(makedate) as makedate, max(receivedate) as receivedate,max(received) as received,max(receivedate) as shipmentdate from (  ");
			if (StringUtil.isEmpty(isComplete)) {
				sql.append(" \r\n select o.id as organid,o.organname,o.oecode,o.province,wa.id as wid,wa.warehousename,swb.productid,swb.batch,sam.id as billcode, 1 as iscomplete,CYCLEINQUANTITY as quantity,sam.makedate,sam.receivedate,1 as received,receivedate as shipmentdate ");
				sql.append(" \r\n from STOCK_WASTE_BOOK swb");
				sql.append(" \r\n join WAREHOUSE wa on swb.warehouseid = wa.id");
				if(DbUtil.isDealer(users)) {
					sql.append(" \r\n and swb.warehouseid in (select wv.warehouse_Id from RULE_USER_WH wv where wv.user_Id="
							+ users.getUserid() + ")");
				}
				sql.append(" \r\n join ORGAN_WITHDRAW sam on swb.billcode = sam.id");
				if (!StringUtil.isEmpty((String) paraMap.get("beginDate"))) {
					sql.append(" \r\n and sam.makedate >=to_date('"
							+ paraMap.get("beginDate")
							+ "','yyyy-MM-dd hh24:mi:ss') "); // 开始时间条件
				}
				if (!StringUtil.isEmpty((String) paraMap.get("endDate"))) {
					sql.append(" \r\n and sam.makedate < to_date('"
							+ paraMap.get("endDate")
							+ "','yyyy-MM-dd hh24:mi:ss') "); // 结束时间条件
				}
				if (!StringUtil.isEmpty((String) paraMap.get("sapOrderId"))) {
					sql.append(" \r\n and sam.nccode = '"
							+ paraMap.get("sapOrderId") + "'"); // SAP订单号条件
				}
				sql.append(" \r\n and swb.memo = '"
						+ Constants.TT_WASTE_BOOK_MEMO[bSort] + "' ");
				if (!StringUtil.isEmpty((String) paraMap.get("inWarehouseId"))) {
					sql.append(" \r\n and swb.warehouseid = '"
							+ paraMap.get("inWarehouseId") + "' "); // 仓库条件
				}
				if (!StringUtil.isEmpty((String) paraMap.get("ProductName"))
						&& !StringUtil.isEmpty((String) paraMap
								.get("packSizeName"))) { // 产品条件
					sql.append(" \r\n and swb.productid in (select id from product where productname = '"
									+ (String) paraMap.get("ProductName")
									+ "' and specmode= '"
									+ (String) paraMap.get("packSizeName")
									+ "') ");
				} else if (!StringUtil.isEmpty((String) paraMap
						.get("ProductName"))
						&& StringUtil.isEmpty((String) paraMap
								.get("packSizeName"))) { // 产品条件
					sql.append(" \r\n and swb.productid in (select id from product where productname = '"
									+ (String) paraMap.get("ProductName")
									+ "') ");
				}
				sql.append(" \r\n join ORGAN o on sam.receiveorganid = o.id ");
				if(!DbUtil.isDealer(users)) {
					sql.append(" \r\n and "+DbUtil.getWhereCondition(users, "o"));
				}
				if (sysPro != null
						&& "1".equals(sysPro.getProperty("genReportOnlyForPD"))) { // 是否只统计PD
					sql.append(" \r\n and o.organtype = 2 and o.organmodel = 1");
				}
				if (!StringUtil.isEmpty((String) paraMap.get("inOrganId"))) {
					sql.append(" \r\n and o.id = '" + paraMap.get("inOrganId")
							+ "' "); // 机构条件
				}
				if (!StringUtil.isEmpty((String) paraMap.get("region"))) {
					sql.append(" \r\n and o.province is not null and o.province in (select areaid from region_area where regioncodeid =  '"
									+ paraMap.get("region") + "')"); // 区域条件
				}
				sql.append("\r\n where swb.productid not in (select id from product where USEFLAG = 0)");
				sql.append(" \r\n UNION ALL");
				// 发货数量与码数量不一致的情况
				sql.append(" \r\n select o.id as organid,o.organname,o.oecode,o.province,wa.id as wid,wa.warehousename,swb.productid,swb.batch,sam.id as billcode, 1 as iscomplete,CYCLEOUTQUANTITY as quantity,sam.makedate,sam.receivedate, 0 as received, receivedate as shipmentdate");
				sql.append(" \r\n from STOCK_WASTE_BOOK swb ");
				sql.append(" \r\n join TAKE_TICKET tt on swb.billcode = tt.id ");
				if (!StringUtil.isEmpty((String) paraMap.get("inWarehouseId"))) {
					sql.append(" \r\n and tt.inwarehouseid = '"
							+ paraMap.get("inWarehouseId") + "' "); // 仓库条件
				}
				if (!StringUtil.isEmpty((String) paraMap.get("ProductName"))
						&& !StringUtil.isEmpty((String) paraMap
								.get("packSizeName"))) { // 产品条件
					sql.append(" \r\n and swb.productid in (select id from product where productname = '"
									+ (String) paraMap.get("ProductName")
									+ "' and specmode= '"
									+ (String) paraMap.get("packSizeName")
									+ "') ");
				} else if (!StringUtil.isEmpty((String) paraMap
						.get("ProductName"))
						&& StringUtil.isEmpty((String) paraMap
								.get("packSizeName"))) { // 产品条件
					sql.append(" \r\n and swb.productid in (select id from product where productname = '"
									+ (String) paraMap.get("ProductName")
									+ "') ");
				}
				sql.append(" \r\n and swb.batch = '" + Constants.NO_BATCH
								+ "'");
				sql.append(" \r\n and swb.memo = '检货小票-出货'");
				if(DbUtil.isDealer(users)) {
					sql.append(" \r\n and tt.inwarehouseid in (select wv.warehouse_Id from RULE_USER_WH wv where wv.user_Id="
							+ users.getUserid() + ")");
				}
				sql.append(" \r\n and not EXISTS (select * from STOCK_WASTE_BOOK where billcode = tt.billno and warehouseid = tt.inwarehouseid and productid = swb.productid and batch = swb.batch)");
				sql.append(" \r\n join ORGAN_WITHDRAW sam on sam.id = tt.billno");
				sql.append(" \r\n and sam.iscomplete=1");
				if (!StringUtil.isEmpty((String) paraMap.get("beginDate"))) {
					sql.append(" \r\n and sam.makedate >=to_date('"
							+ paraMap.get("beginDate")
							+ "','yyyy-MM-dd hh24:mi:ss') "); // 开始时间条件
				}
				if (!StringUtil.isEmpty((String) paraMap.get("endDate"))) {
					sql.append(" \r\n and sam.makedate < to_date('"
							+ paraMap.get("endDate")
							+ "','yyyy-MM-dd hh24:mi:ss') "); // 结束时间条件
				}
				if (!StringUtil.isEmpty((String) paraMap.get("sapOrderId"))) {
					sql.append(" \r\n and sam.nccode = '"
							+ paraMap.get("sapOrderId") + "'"); // SAP订单号条件
				}
				sql.append(" \r\n join ORGAN o on sam.receiveorganid = o.id  ");
				if(!DbUtil.isDealer(users)) {
					sql.append(" \r\n and "+DbUtil.getWhereCondition(users, "o"));
				}
				if (sysPro != null
						&& "1".equals(sysPro.getProperty("genReportOnlyForPD"))) { // 是否只统计PD
					sql.append(" \r\n and o.organtype = 2 and o.organmodel = 1");
				}
				if (!StringUtil.isEmpty((String) paraMap.get("inOrganId"))) {
					sql.append(" \r\n and o.id = '" + paraMap.get("inOrganId")
							+ "'"); // 机构条件
				}
				if (!StringUtil.isEmpty((String) paraMap.get("region"))) {
					sql.append(" \r\n and o.province is not null and o.province in (select areaid from region_area where regioncodeid =  '"
									+ paraMap.get("region") + "')"); // 区域条件
				}
				sql.append(" \r\n left join WAREHOUSE wa on tt.inwarehouseid = wa.id");
				sql.append("\r\n where swb.productid not in (select id from product where USEFLAG = 0)");
				sql.append(" \r\n UNION ALL");

				sql.append(" \r\n select o.id as organid,o.organname,o.oecode,o.province,wa.id as wid, wa.warehousename,swb.productid,swb.batch,sam.id as billcode, sam.iscomplete,swb.cycleoutquantity as quantity,sam.makedate, sam.receivedate, 0 as received, receivedate as shipmentdate ");
				sql.append(" \r\n from ORGAN_WITHDRAW sam");
				sql.append(" \r\n join ORGAN o on sam.receiveorganid = o.id and sam.iscomplete=1 and sam.iscomplete=0 and sam.ISCOMPLETE=0");
				if(!DbUtil.isDealer(users)) {
					sql.append(" \r\n and "+DbUtil.getWhereCondition(users, "o"));
				} else {
					sql.append(" \r\n and sam.inwarehouseid in (select wv.warehouse_Id from RULE_USER_WH wv where wv.user_Id="
							+ users.getUserid() + ")");
				}
				if (sysPro != null
						&& "1".equals(sysPro.getProperty("genReportOnlyForPD"))) { // 是否只统计PD
					sql.append(" \r\n and o.organtype = 2 and o.organmodel = 1");
				}
				if (!StringUtil.isEmpty((String) paraMap.get("inOrganId"))) {
					sql.append(" \r\n and o.id = '" + paraMap.get("inOrganId")
							+ "' "); // 机构条件
				}
				if (!StringUtil.isEmpty((String) paraMap.get("inWarehouseId"))) {
					sql.append(" \r\n and sam.inwarehouseid = '"
							+ paraMap.get("inWarehouseId") + "' "); // 仓库条件
				}
				if (!StringUtil.isEmpty((String) paraMap.get("sapOrderId"))) {
					sql.append(" \r\n and sam.nccode = '"
							+ paraMap.get("sapOrderId") + "'"); // SAP订单号条件
				}
				if (!StringUtil.isEmpty((String) paraMap.get("region"))) {
					sql.append(" \r\n and o.province is not null and o.province in (select areaid from region_area where regioncodeid =  '"
									+ paraMap.get("region") + "')"); // 机构条件
				}
				if (!StringUtil.isEmpty((String) paraMap.get("beginDate"))) {
					sql.append(" \r\n and sam.makedate >=to_date('"
							+ paraMap.get("beginDate")
							+ "','yyyy-MM-dd hh24:mi:ss') "); // 开始时间条件
				}
				if (!StringUtil.isEmpty((String) paraMap.get("endDate"))) {
					sql.append(" \r\n and sam.makedate < to_date('"
							+ paraMap.get("endDate")
							+ "','yyyy-MM-dd hh24:mi:ss') "); // 结束时间条件
				}
				sql.append(" \r\n join TAKE_TICKET tt on sam.id = tt.billno");
				sql.append(" \r\n join STOCK_WASTE_BOOK swb on swb.billcode = tt.id");
				if (!StringUtil.isEmpty((String) paraMap.get("ProductName"))
						&& !StringUtil.isEmpty((String) paraMap
								.get("packSizeName"))) { // 产品条件
					sql.append(" \r\n and swb.productid in (select id from product where productname = '"
									+ (String) paraMap.get("ProductName")
									+ "' and specmode= '"
									+ (String) paraMap.get("packSizeName")
									+ "') ");
				} else if (!StringUtil.isEmpty((String) paraMap
						.get("ProductName"))
						&& StringUtil.isEmpty((String) paraMap
								.get("packSizeName"))) { // 产品条件
					sql.append(" \r\n and swb.productid in (select id from product where productname = '"
									+ (String) paraMap.get("ProductName")
									+ "') ");
				}
				sql.append(" \r\n left join WAREHOUSE wa on sam.inwarehouseid = wa.id ");
				sql.append("\r\n where swb.productid not in (select id from product where USEFLAG = 0)");
			} else if ("1".equals(isComplete.trim())) {
				sql.append(" \r\n select o.id as organid,o.organname,o.oecode,o.province,wa.id as wid,wa.warehousename,swb.productid,swb.batch,sam.id as billcode, 1 as iscomplete,CYCLEINQUANTITY as quantity,sam.makedate,sam.receivedate,1 as received, receivedate as shipmentdate ");
				sql.append(" \r\n from STOCK_WASTE_BOOK swb");
				sql.append(" \r\n join WAREHOUSE wa on swb.warehouseid = wa.id");
				if(DbUtil.isDealer(users)) {
					sql.append(" \r\n and swb.warehouseid in (select wv.warehouse_Id from RULE_USER_WH wv where wv.user_Id="
							+ users.getUserid() + ")");
				}
				sql.append(" \r\n join ORGAN_WITHDRAW sam on swb.billcode = sam.id");
				if (!StringUtil.isEmpty((String) paraMap.get("beginDate"))) {
					sql.append(" \r\n and sam.makedate >=to_date('"
							+ paraMap.get("beginDate")
							+ "','yyyy-MM-dd hh24:mi:ss') "); // 开始时间条件
				}
				if (!StringUtil.isEmpty((String) paraMap.get("endDate"))) {
					sql.append(" \r\n and sam.makedate < to_date('"
							+ paraMap.get("endDate")
							+ "','yyyy-MM-dd hh24:mi:ss') "); // 结束时间条件
				}
				if (!StringUtil.isEmpty((String) paraMap.get("sapOrderId"))) {
					sql.append(" \r\n and sam.nccode = '"
							+ paraMap.get("sapOrderId") + "'"); // SAP订单号条件
				}
				sql.append(" \r\n and swb.memo = '"
						+ Constants.TT_WASTE_BOOK_MEMO[bSort] + "' ");
				if (!StringUtil.isEmpty((String) paraMap.get("inWarehouseId"))) {
					sql.append(" \r\n and swb.warehouseid = '"
							+ paraMap.get("inWarehouseId") + "' "); // 仓库条件
				}
				if (!StringUtil.isEmpty((String) paraMap.get("ProductName"))
						&& !StringUtil.isEmpty((String) paraMap
								.get("packSizeName"))) { // 产品条件
					sql.append(" \r\n and swb.productid in (select id from product where productname = '"
									+ (String) paraMap.get("ProductName")
									+ "' and specmode= '"
									+ (String) paraMap.get("packSizeName")
									+ "') ");
				} else if (!StringUtil.isEmpty((String) paraMap
						.get("ProductName"))
						&& StringUtil.isEmpty((String) paraMap
								.get("packSizeName"))) { // 产品条件
					sql.append(" \r\n and swb.productid in (select id from product where productname = '"
									+ (String) paraMap.get("ProductName")
									+ "') ");
				}
				sql.append(" \r\n join ORGAN o on sam.receiveorganid = o.id ");
				if(!DbUtil.isDealer(users)) {
					sql.append(" \r\n and "+DbUtil.getWhereCondition(users, "o"));
				}
				if (sysPro != null
						&& "1".equals(sysPro.getProperty("genReportOnlyForPD"))) { // 是否只统计PD
					sql.append(" \r\n and o.organtype = 2 and o.organmodel = 1");
				}
				if (!StringUtil.isEmpty((String) paraMap.get("inOrganId"))) {
					sql.append(" \r\n and o.id = '" + paraMap.get("inOrganId")
							+ "' "); // 机构条件
				}
				if (!StringUtil.isEmpty((String) paraMap.get("region"))) {
					sql.append(" \r\n and o.province is not null and o.province in (select areaid from region_area where regioncodeid =  '"
									+ paraMap.get("region") + "')"); // 区域条件
				}
				sql.append("\r\n where swb.productid not in (select id from product where USEFLAG = 0)");
				sql.append(" \r\n UNION ALL");

				sql.append(" \r\n select o.id as organid,o.organname,o.oecode,o.province,wa.id as wid,wa.warehousename,swb.productid,swb.batch,sam.id as billcode, 1 as iscomplete,CYCLEOUTQUANTITY as quantity,sam.makedate,sam.receivedate, 0 as received, receivedate as shipmentdate");
				sql.append(" \r\n from STOCK_WASTE_BOOK swb ");
				sql.append(" \r\n join TAKE_TICKET tt on swb.billcode = tt.id ");
				if (!StringUtil.isEmpty((String) paraMap.get("inWarehouseId"))) {
					sql.append(" \r\n and tt.inwarehouseid = '"
							+ paraMap.get("inWarehouseId") + "' "); // 仓库条件
				}
				if (!StringUtil.isEmpty((String) paraMap.get("ProductName"))
						&& !StringUtil.isEmpty((String) paraMap
								.get("packSizeName"))) { // 产品条件
					sql.append(" \r\n and swb.productid in (select id from product where productname = '"
									+ (String) paraMap.get("ProductName")
									+ "' and specmode= '"
									+ (String) paraMap.get("packSizeName")
									+ "') ");
				} else if (!StringUtil.isEmpty((String) paraMap
						.get("ProductName"))
						&& StringUtil.isEmpty((String) paraMap
								.get("packSizeName"))) { // 产品条件
					sql.append(" \r\n and swb.productid in (select id from product where productname = '"
									+ (String) paraMap.get("ProductName")
									+ "') ");
				}
				sql.append(" \r\n and swb.batch = '" + Constants.NO_BATCH
								+ "'");
				sql.append(" \r\n and swb.memo = '检货小票-出货'");
				if(DbUtil.isDealer(users)) {
					sql.append(" \r\n and tt.inwarehouseid in (select wv.warehouse_Id from RULE_USER_WH wv where wv.user_Id="
							+ users.getUserid() + ")");
				}
				sql.append(" \r\n and not EXISTS (select * from STOCK_WASTE_BOOK where billcode = tt.billno and warehouseid = tt.inwarehouseid and productid = swb.productid and batch = swb.batch)");
				sql.append(" \r\n join ORGAN_WITHDRAW sam on sam.id = tt.billno");
				sql.append(" \r\n and sam.iscomplete=1");
				if (!StringUtil.isEmpty((String) paraMap.get("beginDate"))) {
					sql.append(" \r\n and sam.makedate >=to_date('"
							+ paraMap.get("beginDate")
							+ "','yyyy-MM-dd hh24:mi:ss') "); // 开始时间条件
				}
				if (!StringUtil.isEmpty((String) paraMap.get("endDate"))) {
					sql.append(" \r\n and sam.makedate < to_date('"
							+ paraMap.get("endDate")
							+ "','yyyy-MM-dd hh24:mi:ss') "); // 结束时间条件
				}
				if (!StringUtil.isEmpty((String) paraMap.get("sapOrderId"))) {
					sql.append(" \r\n and sam.nccode = '"
							+ paraMap.get("sapOrderId") + "'"); // SAP订单号条件
				}
				sql.append(" \r\n join ORGAN o on sam.receiveorganid = o.id  ");
				if(!DbUtil.isDealer(users)) {
					sql.append(" \r\n and "+DbUtil.getWhereCondition(users, "o"));
				}
				if (sysPro != null
						&& "1".equals(sysPro.getProperty("genReportOnlyForPD"))) { // 是否只统计PD
					sql.append(" \r\n and o.organtype = 2 and o.organmodel = 1");
				}
				if (!StringUtil.isEmpty((String) paraMap.get("inOrganId"))) {
					sql.append(" \r\n and o.id = '" + paraMap.get("inOrganId")
							+ "' "); // 机构条件
				}
				if (!StringUtil.isEmpty((String) paraMap.get("region"))) {
					sql.append(" \r\n and o.province is not null and o.province in (select areaid from region_area where regioncodeid =  '"
									+ paraMap.get("region") + "')"); // 区域条件
				}
				sql.append(" \r\n left join WAREHOUSE wa on tt.inwarehouseid = wa.id");
				sql.append("\r\n where swb.productid not in (select id from product where USEFLAG = 0)");
			} else {
				sql.append(" \r\n select o.id as organid,o.organname,o.oecode,o.province,wa.id as wid, wa.warehousename,swb.productid,swb.batch,sam.id as billcode, sam.iscomplete,swb.cycleoutquantity as quantity,sam.makedate, sam.receivedate, 0 as received, receivedate as shipmentdate ");
				sql.append(" \r\n from ORGAN_WITHDRAW sam");
				sql.append(" \r\n join ORGAN o on sam.receiveorganid = o.id and sam.iscomplete=1 and sam.iscomplete=0 and sam.ISCOMPLETE=0");
				if(!DbUtil.isDealer(users)) {
					sql.append(" \r\n and "+DbUtil.getWhereCondition(users, "o"));
				} else {
					sql.append(" \r\n and sam.inwarehouseid in (select wv.warehouse_Id from RULE_USER_WH wv where wv.user_Id="
							+ users.getUserid() + ")");
				}
				if (sysPro != null
						&& "1".equals(sysPro.getProperty("genReportOnlyForPD"))) { // 是否只统计PD
					sql.append(" \r\n and o.organtype = 2 and o.organmodel = 1");
				}
				if (!StringUtil.isEmpty((String) paraMap.get("inOrganId"))) {
					sql.append(" \r\n and o.id = '" + paraMap.get("inOrganId")
							+ "' "); // 机构条件
				}
				if (!StringUtil.isEmpty((String) paraMap.get("inWarehouseId"))) {
					sql.append(" \r\n and sam.inwarehouseid = '"
							+ paraMap.get("inWarehouseId") + "' "); // 仓库条件
				}
				if (!StringUtil.isEmpty((String) paraMap.get("sapOrderId"))) {
					sql.append(" \r\n and sam.nccode = '"
							+ paraMap.get("sapOrderId") + "'"); // SAP订单号条件
				}
				if (!StringUtil.isEmpty((String) paraMap.get("region"))) {
					sql.append(" \r\n and o.province is not null and o.province in (select areaid from region_area where regioncodeid =  '"
									+ paraMap.get("region") + "')"); // 机构条件
				}
				if (!StringUtil.isEmpty((String) paraMap.get("beginDate"))) {
					sql.append(" \r\n and sam.makedate >=to_date('"
							+ paraMap.get("beginDate")
							+ "','yyyy-MM-dd hh24:mi:ss') "); // 开始时间条件
				}
				if (!StringUtil.isEmpty((String) paraMap.get("endDate"))) {
					sql.append(" \r\n and sam.makedate < to_date('"
							+ paraMap.get("endDate")
							+ "','yyyy-MM-dd hh24:mi:ss') "); // 结束时间条件
				}
				sql.append(" \r\n join TAKE_TICKET tt on sam.id = tt.billno");
				sql.append(" \r\n join STOCK_WASTE_BOOK swb on swb.billcode = tt.id");
				if (!StringUtil.isEmpty((String) paraMap.get("ProductName"))
						&& !StringUtil.isEmpty((String) paraMap
								.get("packSizeName"))) { // 产品条件
					sql.append(" \r\n and swb.productid in (select id from product where productname = '"
									+ (String) paraMap.get("ProductName")
									+ "' and specmode= '"
									+ (String) paraMap.get("packSizeName")
									+ "') ");
				} else if (!StringUtil.isEmpty((String) paraMap
						.get("ProductName"))
						&& StringUtil.isEmpty((String) paraMap
								.get("packSizeName"))) { // 产品条件
					sql.append(" \r\n and swb.productid in (select id from product where productname = '"
									+ (String) paraMap.get("ProductName")
									+ "') ");
				}
				sql.append(" \r\n left join WAREHOUSE wa on sam.inwarehouseid = wa.id ");
				sql.append("\r\n where swb.productid not in (select id from product where USEFLAG = 0)");
			}
			sql.append(" \r\n ) group by organid,wid,productid,batch,billcode");
			break;

		default:
			sql.append(" \r\n select organid ,max(organname) as organname,max(oecode) as oecode,max(province) as province,wid,max(warehousename) as warehousename,productid,batch,billcode,max(iscomplete) as iscomplete, sum(quantity) as quantity,max(shipmentdate) as shipmentdate, max(receivedate) as receivedate,max(received) as received from (  ");
			if (StringUtil.isEmpty(isComplete)) {
				sql.append(" \r\n select o.id as organid,o.organname,o.oecode,o.province,wa.id as wid,wa.warehousename,swb.productid,swb.batch,sam.id as billcode, 1 as iscomplete,CYCLEINQUANTITY as quantity,sam.shipmentdate,sam.receivedate,1 as received ");
				sql.append(" \r\n from STOCK_WASTE_BOOK swb");
				sql.append(" \r\n join WAREHOUSE wa on swb.warehouseid = wa.id");
				if(DbUtil.isDealer(users)) {
					sql.append(" \r\n and swb.warehouseid in (select wv.warehouse_Id from RULE_USER_WH wv where wv.user_Id="
							+ users.getUserid() + ")");
				}
				sql.append(" \r\n join STOCK_ALTER_MOVE sam on swb.billcode = sam.id");
				if (!StringUtil.isEmpty((String) paraMap.get("beginDate"))) {
					sql.append(" \r\n and sam.makedate >=to_date('"
							+ paraMap.get("beginDate")
							+ "','yyyy-MM-dd hh24:mi:ss') "); // 开始时间条件
				}
				if (!StringUtil.isEmpty((String) paraMap.get("endDate"))) {
					sql.append(" \r\n and sam.makedate < to_date('"
							+ paraMap.get("endDate")
							+ "','yyyy-MM-dd hh24:mi:ss') "); // 结束时间条件
				}
				if (!StringUtil.isEmpty((String) paraMap.get("sapOrderId"))) {
					sql.append(" \r\n and sam.nccode = '"
							+ paraMap.get("sapOrderId") + "'"); // SAP订单号条件
				}
				sql.append(" \r\n and swb.memo = '发货单签收-入库' ");
				if (!StringUtil.isEmpty((String) paraMap.get("inWarehouseId"))) {
					sql.append(" \r\n and swb.warehouseid = '"
							+ paraMap.get("inWarehouseId") + "' "); // 仓库条件
				}
				if (!StringUtil.isEmpty((String) paraMap.get("ProductName"))
						&& !StringUtil.isEmpty((String) paraMap
								.get("packSizeName"))) { // 产品条件
					sql.append(" \r\n and swb.productid in (select id from product where productname = '"
									+ (String) paraMap.get("ProductName")
									+ "' and specmode= '"
									+ (String) paraMap.get("packSizeName")
									+ "') ");
				} else if (!StringUtil.isEmpty((String) paraMap
						.get("ProductName"))
						&& StringUtil.isEmpty((String) paraMap
								.get("packSizeName"))) { // 产品条件
					sql.append(" \r\n and swb.productid in (select id from product where productname = '"
									+ (String) paraMap.get("ProductName")
									+ "') ");
				}
				sql.append(" \r\n join ORGAN o on sam.receiveorganid = o.id ");
				if(!DbUtil.isDealer(users)) {
					sql.append(" \r\n and "+DbUtil.getWhereCondition(users, "o"));
				}
				if (sysPro != null
						&& "1".equals(sysPro.getProperty("genReportOnlyForPD"))) { // 是否只统计PD
					sql.append(" \r\n and o.organtype = 2 and o.organmodel = 1");
				}
				if (!StringUtil.isEmpty((String) paraMap.get("inOrganId"))) {
					sql.append(" \r\n and o.id = '" + paraMap.get("inOrganId")
							+ "' "); // 机构条件
				}
				if (!StringUtil.isEmpty((String) paraMap.get("region"))) {
					sql.append(" \r\n and o.province is not null and o.province in (select areaid from region_area where regioncodeid =  '"
									+ paraMap.get("region") + "')"); // 区域条件
				}
				sql.append("\r\n where swb.productid not in (select id from product where USEFLAG = 0)");
				sql.append(" \r\n UNION ALL");
				// 发货数量与码数量不一致的情况
				sql.append(" \r\n select o.id as organid,o.organname,o.oecode,o.province,wa.id as wid,wa.warehousename,swb.productid,swb.batch,sam.id as billcode, 1 as iscomplete,CYCLEOUTQUANTITY as quantity,sam.shipmentdate,sam.receivedate, 0 as received");
				sql.append(" \r\n from STOCK_WASTE_BOOK swb ");
				sql.append(" \r\n join TAKE_TICKET tt on swb.billcode = tt.id ");
				if (!StringUtil.isEmpty((String) paraMap.get("inWarehouseId"))) {
					sql.append(" \r\n and tt.inwarehouseid = '"
							+ paraMap.get("inWarehouseId") + "' "); // 仓库条件
				}
				if (!StringUtil.isEmpty((String) paraMap.get("ProductName"))
						&& !StringUtil.isEmpty((String) paraMap
								.get("packSizeName"))) { // 产品条件
					sql.append(" \r\n and swb.productid in (select id from product where productname = '"
									+ (String) paraMap.get("ProductName")
									+ "' and specmode= '"
									+ (String) paraMap.get("packSizeName")
									+ "') ");
				} else if (!StringUtil.isEmpty((String) paraMap
						.get("ProductName"))
						&& StringUtil.isEmpty((String) paraMap
								.get("packSizeName"))) { // 产品条件
					sql.append(" \r\n and swb.productid in (select id from product where productname = '"
									+ (String) paraMap.get("ProductName")
									+ "') ");
				}
				sql.append(" \r\n and swb.batch = '" + Constants.NO_BATCH
								+ "'");
				sql.append(" \r\n and swb.memo = '检货小票-出货'");
				if(DbUtil.isDealer(users)) {
					sql.append(" \r\n and tt.inwarehouseid in (select wv.warehouse_Id from RULE_USER_WH wv where wv.user_Id="
							+ users.getUserid() + ")");
				}
				sql.append(" \r\n and not EXISTS (select * from STOCK_WASTE_BOOK where billcode = tt.billno and warehouseid = tt.inwarehouseid and productid = swb.productid and batch = swb.batch)");
				sql.append(" \r\n join STOCK_ALTER_MOVE sam on sam.id = tt.billno");
				sql.append(" \r\n and sam.iscomplete=1");
				if (!StringUtil.isEmpty((String) paraMap.get("beginDate"))) {
					sql.append(" \r\n and sam.makedate >=to_date('"
							+ paraMap.get("beginDate")
							+ "','yyyy-MM-dd hh24:mi:ss') "); // 开始时间条件
				}
				if (!StringUtil.isEmpty((String) paraMap.get("endDate"))) {
					sql.append(" \r\n and sam.makedate < to_date('"
							+ paraMap.get("endDate")
							+ "','yyyy-MM-dd hh24:mi:ss') "); // 结束时间条件
				}
				if (!StringUtil.isEmpty((String) paraMap.get("sapOrderId"))) {
					sql.append(" \r\n and sam.nccode = '"
							+ paraMap.get("sapOrderId") + "'"); // SAP订单号条件
				}
				sql.append(" \r\n join ORGAN o on sam.receiveorganid = o.id  ");
				if(!DbUtil.isDealer(users)) {
					sql.append(" \r\n and "+DbUtil.getWhereCondition(users, "o"));
				}
				if (sysPro != null
						&& "1".equals(sysPro.getProperty("genReportOnlyForPD"))) { // 是否只统计PD
					sql.append(" \r\n and o.organtype = 2 and o.organmodel = 1");
				}
				if (!StringUtil.isEmpty((String) paraMap.get("inOrganId"))) {
					sql.append(" \r\n and o.id = '" + paraMap.get("inOrganId")
							+ "'"); // 机构条件
				}
				if (!StringUtil.isEmpty((String) paraMap.get("region"))) {
					sql.append(" \r\n and o.province is not null and o.province in (select areaid from region_area where regioncodeid =  '"
									+ paraMap.get("region") + "')"); // 区域条件
				}
				sql.append(" \r\n left join WAREHOUSE wa on tt.inwarehouseid = wa.id");
				sql.append("\r\n where swb.productid not in (select id from product where USEFLAG = 0)");
				sql.append(" \r\n UNION ALL");

				sql.append(" \r\n select o.id as organid,o.organname,o.oecode,o.province,wa.id as wid, wa.warehousename,swb.productid,swb.batch,sam.id as billcode, sam.iscomplete,swb.cycleoutquantity as quantity,shipmentdate, receivedate, 0 as received ");
				sql.append(" \r\n from STOCK_ALTER_MOVE sam");
				sql.append(" \r\n join ORGAN o on sam.receiveorganid = o.id and sam.isshipment=1 and sam.iscomplete=0 and sam.ISBLANKOUT=0");
				if(!DbUtil.isDealer(users)) {
					sql.append(" \r\n and "+DbUtil.getWhereCondition(users, "o"));
				} else {
					sql.append(" \r\n and sam.inwarehouseid in (select wv.warehouse_Id from RULE_USER_WH wv where wv.user_Id="
							+ users.getUserid() + ")");
				}
				if (sysPro != null
						&& "1".equals(sysPro.getProperty("genReportOnlyForPD"))) { // 是否只统计PD
					sql.append(" \r\n and o.organtype = 2 and o.organmodel = 1");
				}
				if (!StringUtil.isEmpty((String) paraMap.get("inOrganId"))) {
					sql.append(" \r\n and o.id = '" + paraMap.get("inOrganId")
							+ "' "); // 机构条件
				}
				if (!StringUtil.isEmpty((String) paraMap.get("inWarehouseId"))) {
					sql.append(" \r\n and sam.inwarehouseid = '"
							+ paraMap.get("inWarehouseId") + "' "); // 仓库条件
				}
				if (!StringUtil.isEmpty((String) paraMap.get("sapOrderId"))) {
					sql.append(" \r\n and sam.nccode = '"
							+ paraMap.get("sapOrderId") + "'"); // SAP订单号条件
				}
				if (!StringUtil.isEmpty((String) paraMap.get("region"))) {
					sql.append(" \r\n and o.province is not null and o.province in (select areaid from region_area where regioncodeid =  '"
									+ paraMap.get("region") + "')"); // 机构条件
				}
				if (!StringUtil.isEmpty((String) paraMap.get("beginDate"))) {
					sql.append(" \r\n and sam.makedate >=to_date('"
							+ paraMap.get("beginDate")
							+ "','yyyy-MM-dd hh24:mi:ss') "); // 开始时间条件
				}
				if (!StringUtil.isEmpty((String) paraMap.get("endDate"))) {
					sql.append(" \r\n and sam.makedate < to_date('"
							+ paraMap.get("endDate")
							+ "','yyyy-MM-dd hh24:mi:ss') "); // 结束时间条件
				}
				sql.append(" \r\n join TAKE_TICKET tt on sam.id = tt.billno");
				sql.append(" \r\n join STOCK_WASTE_BOOK swb on swb.billcode = tt.id");
				if (!StringUtil.isEmpty((String) paraMap.get("ProductName"))
						&& !StringUtil.isEmpty((String) paraMap
								.get("packSizeName"))) { // 产品条件
					sql.append(" \r\n and swb.productid in (select id from product where productname = '"
									+ (String) paraMap.get("ProductName")
									+ "' and specmode= '"
									+ (String) paraMap.get("packSizeName")
									+ "') ");
				} else if (!StringUtil.isEmpty((String) paraMap
						.get("ProductName"))
						&& StringUtil.isEmpty((String) paraMap
								.get("packSizeName"))) { // 产品条件
					sql.append(" \r\n and swb.productid in (select id from product where productname = '"
									+ (String) paraMap.get("ProductName")
									+ "') ");
				}
				sql.append(" \r\n left join WAREHOUSE wa on sam.inwarehouseid = wa.id ");
				sql.append("\r\n where swb.productid not in (select id from product where USEFLAG = 0)");
			} else if ("1".equals(isComplete.trim())) {
				sql.append(" \r\n select o.id as organid,o.organname,o.oecode,o.province,wa.id as wid,wa.warehousename,swb.productid,swb.batch,sam.id as billcode, 1 as iscomplete,CYCLEINQUANTITY as quantity,sam.shipmentdate,sam.receivedate,1 as received ");
				sql.append(" \r\n from STOCK_WASTE_BOOK swb");
				sql.append(" \r\n join WAREHOUSE wa on swb.warehouseid = wa.id");
				if(DbUtil.isDealer(users)) {
					sql.append(" \r\n and swb.warehouseid in (select wv.warehouse_Id from RULE_USER_WH wv where wv.user_Id="
							+ users.getUserid() + ")");
				}
				sql.append(" \r\n join STOCK_ALTER_MOVE sam on swb.billcode = sam.id");
				if (!StringUtil.isEmpty((String) paraMap.get("beginDate"))) {
					sql.append(" \r\n and sam.makedate >=to_date('"
							+ paraMap.get("beginDate")
							+ "','yyyy-MM-dd hh24:mi:ss') "); // 开始时间条件
				}
				if (!StringUtil.isEmpty((String) paraMap.get("endDate"))) {
					sql.append(" \r\n and sam.makedate < to_date('"
							+ paraMap.get("endDate")
							+ "','yyyy-MM-dd hh24:mi:ss') "); // 结束时间条件
				}
				if (!StringUtil.isEmpty((String) paraMap.get("sapOrderId"))) {
					sql.append(" \r\n and sam.nccode = '"
							+ paraMap.get("sapOrderId") + "'"); // SAP订单号条件
				}
				sql.append(" \r\n and swb.memo = '发货单签收-入库' ");
				if (!StringUtil.isEmpty((String) paraMap.get("inWarehouseId"))) {
					sql.append(" \r\n and swb.warehouseid = '"
							+ paraMap.get("inWarehouseId") + "' "); // 仓库条件
				}
				if (!StringUtil.isEmpty((String) paraMap.get("ProductName"))
						&& !StringUtil.isEmpty((String) paraMap
								.get("packSizeName"))) { // 产品条件
					sql.append(" \r\n and swb.productid in (select id from product where productname = '"
									+ (String) paraMap.get("ProductName")
									+ "' and specmode= '"
									+ (String) paraMap.get("packSizeName")
									+ "') ");
				} else if (!StringUtil.isEmpty((String) paraMap
						.get("ProductName"))
						&& StringUtil.isEmpty((String) paraMap
								.get("packSizeName"))) { // 产品条件
					sql.append(" \r\n and swb.productid in (select id from product where productname = '"
									+ (String) paraMap.get("ProductName")
									+ "') ");
				}
				sql.append(" \r\n join ORGAN o on sam.receiveorganid = o.id ");
				if(!DbUtil.isDealer(users)) {
					sql.append(" \r\n and "+DbUtil.getWhereCondition(users, "o"));
				}
				if (sysPro != null
						&& "1".equals(sysPro.getProperty("genReportOnlyForPD"))) { // 是否只统计PD
					sql.append(" \r\n and o.organtype = 2 and o.organmodel = 1");
				}
				if (!StringUtil.isEmpty((String) paraMap.get("inOrganId"))) {
					sql.append(" \r\n and o.id = '" + paraMap.get("inOrganId")
							+ "' "); // 机构条件
				}
				if (!StringUtil.isEmpty((String) paraMap.get("region"))) {
					sql.append(" \r\n and o.province is not null and o.province in (select areaid from region_area where regioncodeid =  '"
									+ paraMap.get("region") + "')"); // 区域条件
				}
				sql.append("\r\n where swb.productid not in (select id from product where USEFLAG = 0)");
				sql.append(" \r\n UNION ALL");

				sql.append(" \r\n select o.id as organid,o.organname,o.oecode,o.province,wa.id as wid,wa.warehousename,swb.productid,swb.batch,sam.id as billcode, 1 as iscomplete,CYCLEOUTQUANTITY as quantity,sam.shipmentdate,sam.receivedate, 0 as received");
				sql.append(" \r\n from STOCK_WASTE_BOOK swb ");
				sql.append(" \r\n join TAKE_TICKET tt on swb.billcode = tt.id ");
				if (!StringUtil.isEmpty((String) paraMap.get("inWarehouseId"))) {
					sql.append(" \r\n and tt.inwarehouseid = '"
							+ paraMap.get("inWarehouseId") + "' "); // 仓库条件
				}
				if (!StringUtil.isEmpty((String) paraMap.get("ProductName"))
						&& !StringUtil.isEmpty((String) paraMap
								.get("packSizeName"))) { // 产品条件
					sql.append(" \r\n and swb.productid in (select id from product where productname = '"
									+ (String) paraMap.get("ProductName")
									+ "' and specmode= '"
									+ (String) paraMap.get("packSizeName")
									+ "') ");
				} else if (!StringUtil.isEmpty((String) paraMap
						.get("ProductName"))
						&& StringUtil.isEmpty((String) paraMap
								.get("packSizeName"))) { // 产品条件
					sql.append(" \r\n and swb.productid in (select id from product where productname = '"
									+ (String) paraMap.get("ProductName")
									+ "') ");
				}
				sql.append(" \r\n and swb.batch = '" + Constants.NO_BATCH
								+ "'");
				sql.append(" \r\n and swb.memo = '检货小票-出货'");
				if(DbUtil.isDealer(users)) {
					sql.append(" \r\n and tt.inwarehouseid in (select wv.warehouse_Id from RULE_USER_WH wv where wv.user_Id="
							+ users.getUserid() + ")");
				}
				sql.append(" \r\n and not EXISTS (select * from STOCK_WASTE_BOOK where billcode = tt.billno and warehouseid = tt.inwarehouseid and productid = swb.productid and batch = swb.batch)");
				sql.append(" \r\n join STOCK_ALTER_MOVE sam on sam.id = tt.billno");
				sql.append(" \r\n and sam.iscomplete=1");
				if (!StringUtil.isEmpty((String) paraMap.get("beginDate"))) {
					sql.append(" \r\n and sam.makedate >=to_date('"
							+ paraMap.get("beginDate")
							+ "','yyyy-MM-dd hh24:mi:ss') "); // 开始时间条件
				}
				if (!StringUtil.isEmpty((String) paraMap.get("endDate"))) {
					sql.append(" \r\n and sam.makedate < to_date('"
							+ paraMap.get("endDate")
							+ "','yyyy-MM-dd hh24:mi:ss') "); // 结束时间条件
				}
				if (!StringUtil.isEmpty((String) paraMap.get("sapOrderId"))) {
					sql.append(" \r\n and sam.nccode = '"
							+ paraMap.get("sapOrderId") + "'"); // SAP订单号条件
				}
				sql.append(" \r\n join ORGAN o on sam.receiveorganid = o.id  ");
				if(!DbUtil.isDealer(users)) {
					sql.append(" \r\n and "+DbUtil.getWhereCondition(users, "o"));
				}
				if (sysPro != null
						&& "1".equals(sysPro.getProperty("genReportOnlyForPD"))) { // 是否只统计PD
					sql.append(" \r\n and o.organtype = 2 and o.organmodel = 1");
				}
				if (!StringUtil.isEmpty((String) paraMap.get("inOrganId"))) {
					sql.append(" \r\n and o.id = '" + paraMap.get("inOrganId")
							+ "' "); // 机构条件
				}
				if (!StringUtil.isEmpty((String) paraMap.get("region"))) {
					sql.append(" \r\n and o.province is not null and o.province in (select areaid from region_area where regioncodeid =  '"
									+ paraMap.get("region") + "')"); // 区域条件
				}
				sql.append(" \r\n left join WAREHOUSE wa on tt.inwarehouseid = wa.id");
				sql.append("\r\n where swb.productid not in (select id from product where USEFLAG = 0)");
			} else {
				sql.append(" \r\n select o.id as organid,o.organname,o.oecode,o.province,wa.id as wid, wa.warehousename,swb.productid,swb.batch,sam.id as billcode, sam.iscomplete,swb.cycleoutquantity as quantity,shipmentdate, receivedate, 0 as received ");
				sql.append(" \r\n from STOCK_ALTER_MOVE sam");
				sql.append(" \r\n join ORGAN o on sam.receiveorganid = o.id and sam.isshipment=1 and sam.iscomplete=0 and sam.ISBLANKOUT=0");
				if(!DbUtil.isDealer(users)) {
					sql.append(" \r\n and "+DbUtil.getWhereCondition(users, "o"));
				} else {
					sql.append(" \r\n and sam.inwarehouseid in (select wv.warehouse_Id from RULE_USER_WH wv where wv.user_Id="
							+ users.getUserid() + ")");
				}
				if (sysPro != null
						&& "1".equals(sysPro.getProperty("genReportOnlyForPD"))) { // 是否只统计PD
					sql.append(" \r\n and o.organtype = 2 and o.organmodel = 1");
				}
				if (!StringUtil.isEmpty((String) paraMap.get("inOrganId"))) {
					sql.append(" \r\n and o.id = '" + paraMap.get("inOrganId")
							+ "' "); // 机构条件
				}
				if (!StringUtil.isEmpty((String) paraMap.get("inWarehouseId"))) {
					sql.append(" \r\n and sam.inwarehouseid = '"
							+ paraMap.get("inWarehouseId") + "' "); // 仓库条件
				}
				if (!StringUtil.isEmpty((String) paraMap.get("sapOrderId"))) {
					sql.append(" \r\n and sam.nccode = '"
							+ paraMap.get("sapOrderId") + "'"); // SAP订单号条件
				}
				if (!StringUtil.isEmpty((String) paraMap.get("region"))) {
					sql.append(" \r\n and o.province is not null and o.province in (select areaid from region_area where regioncodeid =  '"
									+ paraMap.get("region") + "')"); // 机构条件
				}
				if (!StringUtil.isEmpty((String) paraMap.get("beginDate"))) {
					sql.append(" \r\n and sam.makedate >=to_date('"
							+ paraMap.get("beginDate")
							+ "','yyyy-MM-dd hh24:mi:ss') "); // 开始时间条件
				}
				if (!StringUtil.isEmpty((String) paraMap.get("endDate"))) {
					sql.append(" \r\n and sam.makedate < to_date('"
							+ paraMap.get("endDate")
							+ "','yyyy-MM-dd hh24:mi:ss') "); // 结束时间条件
				}
				sql.append(" \r\n join TAKE_TICKET tt on sam.id = tt.billno");
				sql.append(" \r\n join STOCK_WASTE_BOOK swb on swb.billcode = tt.id");
				if (!StringUtil.isEmpty((String) paraMap.get("ProductName"))
						&& !StringUtil.isEmpty((String) paraMap
								.get("packSizeName"))) { // 产品条件
					sql.append(" \r\n and swb.productid in (select id from product where productname = '"
									+ (String) paraMap.get("ProductName")
									+ "' and specmode= '"
									+ (String) paraMap.get("packSizeName")
									+ "') ");
				} else if (!StringUtil.isEmpty((String) paraMap
						.get("ProductName"))
						&& StringUtil.isEmpty((String) paraMap
								.get("packSizeName"))) { // 产品条件
					sql.append(" \r\n and swb.productid in (select id from product where productname = '"
									+ (String) paraMap.get("ProductName")
									+ "') ");
				}
				sql.append(" \r\n left join WAREHOUSE wa on sam.inwarehouseid = wa.id ");
				sql.append("\r\n where swb.productid not in (select id from product where USEFLAG = 0)");
			}
			sql.append(" \r\n UNION ALL");
			// 转仓单 ==2
			if (StringUtil.isEmpty(isComplete)) {
				sql.append(" \r\n select o.id as organid,o.organname,o.oecode,o.province,wa.id as wid,wa.warehousename,swb.productid,swb.batch,sam.id as billcode, 1 as iscomplete,CYCLEINQUANTITY as quantity,sam.shipmentdate,sam.receivedate,1 as received ");
				sql.append(" \r\n from STOCK_WASTE_BOOK swb");
				sql.append(" \r\n join WAREHOUSE wa on swb.warehouseid = wa.id");
				if(DbUtil.isDealer(users)) {
					sql.append(" \r\n and swb.warehouseid in (select wv.warehouse_Id from RULE_USER_WH wv where wv.user_Id="
							+ users.getUserid() + ")");
				}
				sql.append(" \r\n join STOCK_MOVE sam on swb.billcode = sam.id");
				if (!StringUtil.isEmpty((String) paraMap.get("beginDate"))) {
					sql.append(" \r\n and sam.makedate >=to_date('"
							+ paraMap.get("beginDate")
							+ "','yyyy-MM-dd hh24:mi:ss') "); // 开始时间条件
				}
				if (!StringUtil.isEmpty((String) paraMap.get("endDate"))) {
					sql.append(" \r\n and sam.makedate < to_date('"
							+ paraMap.get("endDate")
							+ "','yyyy-MM-dd hh24:mi:ss') "); // 结束时间条件
				}
				if (!StringUtil.isEmpty((String) paraMap.get("sapOrderId"))) {
					sql.append(" \r\n and sam.nccode = '"
							+ paraMap.get("sapOrderId") + "'"); // SAP订单号条件
				}
				sql.append(" \r\n and swb.memo = '"
						+ Constants.TT_WASTE_BOOK_MEMO[2] + "' ");
				if (!StringUtil.isEmpty((String) paraMap.get("inWarehouseId"))) {
					sql.append(" \r\n and swb.warehouseid = '"
							+ paraMap.get("inWarehouseId") + "' "); // 仓库条件
				}
				if (!StringUtil.isEmpty((String) paraMap.get("ProductName"))
						&& !StringUtil.isEmpty((String) paraMap
								.get("packSizeName"))) { // 产品条件
					sql.append(" \r\n and swb.productid in (select id from product where productname = '"
									+ (String) paraMap.get("ProductName")
									+ "' and specmode= '"
									+ (String) paraMap.get("packSizeName")
									+ "') ");
				} else if (!StringUtil.isEmpty((String) paraMap
						.get("ProductName"))
						&& StringUtil.isEmpty((String) paraMap
								.get("packSizeName"))) { // 产品条件
					sql.append(" \r\n and swb.productid in (select id from product where productname = '"
									+ (String) paraMap.get("ProductName")
									+ "') ");
				}
				sql.append(" \r\n join ORGAN o on sam.inorganid = o.id ");
				if(!DbUtil.isDealer(users)) {
					sql.append(" \r\n and "+DbUtil.getWhereCondition(users, "o"));
				}
				if (sysPro != null
						&& "1".equals(sysPro.getProperty("genReportOnlyForPD"))) { // 是否只统计PD
					sql.append(" \r\n and o.organtype = 2 and o.organmodel = 1");
				}
				if (!StringUtil.isEmpty((String) paraMap.get("inOrganId"))) {
					sql.append(" \r\n and o.id = '" + paraMap.get("inOrganId")
							+ "' "); // 机构条件
				}
				if (!StringUtil.isEmpty((String) paraMap.get("region"))) {
					sql.append(" \r\n and o.province is not null and o.province in (select areaid from region_area where regioncodeid =  '"
									+ paraMap.get("region") + "')"); // 区域条件
				}
				sql.append("\r\n where swb.productid not in (select id from product where USEFLAG = 0)");
				sql.append(" \r\n UNION ALL");

				// 发货数量与码数量不一致的情况
				sql.append(" \r\n select o.id as organid,o.organname,o.oecode,o.province,wa.id as wid,wa.warehousename,swb.productid,swb.batch,sam.id as billcode, 1 as iscomplete,CYCLEOUTQUANTITY as quantity,sam.shipmentdate,sam.receivedate, 0 as received");
				sql.append(" \r\n from STOCK_WASTE_BOOK swb ");
				sql.append(" \r\n join TAKE_TICKET tt on swb.billcode = tt.id ");
				if (!StringUtil.isEmpty((String) paraMap.get("inWarehouseId"))) {
					sql.append(" \r\n and tt.inwarehouseid = '"
							+ paraMap.get("inWarehouseId") + "' "); // 仓库条件
				}
				if (!StringUtil.isEmpty((String) paraMap.get("ProductName"))
						&& !StringUtil.isEmpty((String) paraMap
								.get("packSizeName"))) { // 产品条件
					sql.append(" \r\n and swb.productid in (select id from product where productname = '"
									+ (String) paraMap.get("ProductName")
									+ "' and specmode= '"
									+ (String) paraMap.get("packSizeName")
									+ "') ");
				} else if (!StringUtil.isEmpty((String) paraMap
						.get("ProductName"))
						&& StringUtil.isEmpty((String) paraMap
								.get("packSizeName"))) { // 产品条件
					sql.append(" \r\n and swb.productid in (select id from product where productname = '"
									+ (String) paraMap.get("ProductName")
									+ "') ");
				}
				sql.append(" \r\n and swb.batch = '" + Constants.NO_BATCH
								+ "'");
				sql.append(" \r\n and swb.memo = '检货小票-出货'");
				if(DbUtil.isDealer(users)) {
					sql.append(" \r\n and tt.inwarehouseid in (select wv.warehouse_Id from RULE_USER_WH wv where wv.user_Id="
							+ users.getUserid() + ")");
				}
				sql.append(" \r\n and not EXISTS (select * from STOCK_WASTE_BOOK where billcode = tt.billno and warehouseid = tt.inwarehouseid and productid = swb.productid and batch = swb.batch)");
				sql.append(" \r\n join STOCK_MOVE sam on sam.id = tt.billno");
				sql.append(" \r\n and sam.iscomplete=1");
				if (!StringUtil.isEmpty((String) paraMap.get("beginDate"))) {
					sql.append(" \r\n and sam.makedate >=to_date('"
							+ paraMap.get("beginDate")
							+ "','yyyy-MM-dd hh24:mi:ss') "); // 开始时间条件
				}
				if (!StringUtil.isEmpty((String) paraMap.get("endDate"))) {
					sql.append(" \r\n and sam.makedate < to_date('"
							+ paraMap.get("endDate")
							+ "','yyyy-MM-dd hh24:mi:ss') "); // 结束时间条件
				}
				if (!StringUtil.isEmpty((String) paraMap.get("sapOrderId"))) {
					sql.append(" \r\n and sam.nccode = '"
							+ paraMap.get("sapOrderId") + "'"); // SAP订单号条件
				}
				sql.append(" \r\n join ORGAN o on sam.inorganid = o.id  ");
				if(!DbUtil.isDealer(users)) {
					sql.append(" \r\n and "+DbUtil.getWhereCondition(users, "o"));
				}
				if (sysPro != null
						&& "1".equals(sysPro.getProperty("genReportOnlyForPD"))) { // 是否只统计PD
					sql.append(" \r\n and o.organtype = 2 and o.organmodel = 1");
				}
				if (!StringUtil.isEmpty((String) paraMap.get("inOrganId"))) {
					sql.append(" \r\n and o.id = '" + paraMap.get("inOrganId")
							+ "'"); // 机构条件
				}
				if (!StringUtil.isEmpty((String) paraMap.get("region"))) {
					sql.append(" \r\n and o.province is not null and o.province in (select areaid from region_area where regioncodeid =  '"
									+ paraMap.get("region") + "')"); // 区域条件
				}
				sql.append(" \r\n left join WAREHOUSE wa on tt.inwarehouseid = wa.id");
				sql.append("\r\n where swb.productid not in (select id from product where USEFLAG = 0)");
				sql.append(" \r\n UNION ALL");

				sql.append(" \r\n select o.id as organid,o.organname,o.oecode,o.province,wa.id as wid, wa.warehousename,swb.productid,swb.batch,sam.id as billcode, sam.iscomplete,swb.cycleoutquantity as quantity,shipmentdate, receivedate, 0 as received ");
				sql.append(" \r\n from STOCK_MOVE sam");
				sql.append(" \r\n join ORGAN o on sam.inorganid = o.id and sam.isshipment=1 and sam.iscomplete=0 and sam.ISBLANKOUT=0");
				if(!DbUtil.isDealer(users)) {
					sql.append(" \r\n and "+DbUtil.getWhereCondition(users, "o"));
				} else {
					sql.append(" \r\n and sam.inwarehouseid in (select wv.warehouse_Id from RULE_USER_WH wv where wv.user_Id="
							+ users.getUserid() + ")");
				}
				if (sysPro != null
						&& "1".equals(sysPro.getProperty("genReportOnlyForPD"))) { // 是否只统计PD
					sql.append(" \r\n and o.organtype = 2 and o.organmodel = 1");
				}
				if (!StringUtil.isEmpty((String) paraMap.get("inOrganId"))) {
					sql.append(" \r\n and o.id = '" + paraMap.get("inOrganId")
							+ "' "); // 机构条件
				}
				if (!StringUtil.isEmpty((String) paraMap.get("inWarehouseId"))) {
					sql.append(" \r\n and sam.inwarehouseid = '"
							+ paraMap.get("inWarehouseId") + "' "); // 仓库条件
				}
				if (!StringUtil.isEmpty((String) paraMap.get("sapOrderId"))) {
					sql.append(" \r\n and sam.nccode = '"
							+ paraMap.get("sapOrderId") + "'"); // SAP订单号条件
				}
				if (!StringUtil.isEmpty((String) paraMap.get("region"))) {
					sql.append(" \r\n and o.province is not null and o.province in (select areaid from region_area where regioncodeid =  '"
									+ paraMap.get("region") + "')"); // 机构条件
				}
				if (!StringUtil.isEmpty((String) paraMap.get("beginDate"))) {
					sql.append(" \r\n and sam.makedate >=to_date('"
							+ paraMap.get("beginDate")
							+ "','yyyy-MM-dd hh24:mi:ss') "); // 开始时间条件
				}
				if (!StringUtil.isEmpty((String) paraMap.get("endDate"))) {
					sql.append(" \r\n and sam.makedate < to_date('"
							+ paraMap.get("endDate")
							+ "','yyyy-MM-dd hh24:mi:ss') "); // 结束时间条件
				}
				sql.append(" \r\n join TAKE_TICKET tt on sam.id = tt.billno");
				sql.append(" \r\n join STOCK_WASTE_BOOK swb on swb.billcode = tt.id");
				if (!StringUtil.isEmpty((String) paraMap.get("ProductName"))
						&& !StringUtil.isEmpty((String) paraMap
								.get("packSizeName"))) { // 产品条件
					sql.append(" \r\n and swb.productid in (select id from product where productname = '"
									+ (String) paraMap.get("ProductName")
									+ "' and specmode= '"
									+ (String) paraMap.get("packSizeName")
									+ "') ");
				} else if (!StringUtil.isEmpty((String) paraMap
						.get("ProductName"))
						&& StringUtil.isEmpty((String) paraMap
								.get("packSizeName"))) { // 产品条件
					sql.append(" \r\n and swb.productid in (select id from product where productname = '"
									+ (String) paraMap.get("ProductName")
									+ "') ");
				}
				sql.append(" \r\n left join WAREHOUSE wa on sam.inwarehouseid = wa.id ");
				sql.append("\r\n where swb.productid not in (select id from product where USEFLAG = 0)");
			} else if ("1".equals(isComplete.trim())) {
				sql.append(" \r\n select o.id as organid,o.organname,o.oecode,o.province,wa.id as wid,wa.warehousename,swb.productid,swb.batch,sam.id as billcode, 1 as iscomplete,CYCLEINQUANTITY as quantity,sam.shipmentdate,sam.receivedate,1 as received ");
				sql.append(" \r\n from STOCK_WASTE_BOOK swb");
				sql.append(" \r\n join WAREHOUSE wa on swb.warehouseid = wa.id");
				if(DbUtil.isDealer(users)) {
					sql.append(" \r\n and swb.warehouseid in (select wv.warehouse_Id from RULE_USER_WH wv where wv.user_Id="
							+ users.getUserid() + ")");
				}
				sql.append(" \r\n join STOCK_MOVE sam on swb.billcode = sam.id");
				if (!StringUtil.isEmpty((String) paraMap.get("beginDate"))) {
					sql.append(" \r\n and sam.makedate >=to_date('"
							+ paraMap.get("beginDate")
							+ "','yyyy-MM-dd hh24:mi:ss') "); // 开始时间条件
				}
				if (!StringUtil.isEmpty((String) paraMap.get("endDate"))) {
					sql.append(" \r\n and sam.makedate < to_date('"
							+ paraMap.get("endDate")
							+ "','yyyy-MM-dd hh24:mi:ss') "); // 结束时间条件
				}
				if (!StringUtil.isEmpty((String) paraMap.get("sapOrderId"))) {
					sql.append(" \r\n and sam.nccode = '"
							+ paraMap.get("sapOrderId") + "'"); // SAP订单号条件
				}
				sql.append(" \r\n and swb.memo = '"
						+ Constants.TT_WASTE_BOOK_MEMO[2] + "' ");
				if (!StringUtil.isEmpty((String) paraMap.get("inWarehouseId"))) {
					sql.append(" \r\n and swb.warehouseid = '"
							+ paraMap.get("inWarehouseId") + "' "); // 仓库条件
				}
				if (!StringUtil.isEmpty((String) paraMap.get("ProductName"))
						&& !StringUtil.isEmpty((String) paraMap
								.get("packSizeName"))) { // 产品条件
					sql.append(" \r\n and swb.productid in (select id from product where productname = '"
									+ (String) paraMap.get("ProductName")
									+ "' and specmode= '"
									+ (String) paraMap.get("packSizeName")
									+ "') ");
				} else if (!StringUtil.isEmpty((String) paraMap
						.get("ProductName"))
						&& StringUtil.isEmpty((String) paraMap
								.get("packSizeName"))) { // 产品条件
					sql.append(" \r\n and swb.productid in (select id from product where productname = '"
									+ (String) paraMap.get("ProductName")
									+ "') ");
				}
				sql.append(" \r\n join ORGAN o on sam.inorganid = o.id ");
				if(!DbUtil.isDealer(users)) {
					sql.append(" \r\n and "+DbUtil.getWhereCondition(users, "o"));
				}
				if (sysPro != null
						&& "1".equals(sysPro.getProperty("genReportOnlyForPD"))) { // 是否只统计PD
					sql.append(" \r\n and o.organtype = 2 and o.organmodel = 1");
				}
				if (!StringUtil.isEmpty((String) paraMap.get("inOrganId"))) {
					sql.append(" \r\n and o.id = '" + paraMap.get("inOrganId")
							+ "' "); // 机构条件
				}
				if (!StringUtil.isEmpty((String) paraMap.get("region"))) {
					sql.append(" \r\n and o.province is not null and o.province in (select areaid from region_area where regioncodeid =  '"
									+ paraMap.get("region") + "')"); // 区域条件
				}
				sql.append("\r\n where swb.productid not in (select id from product where USEFLAG = 0)");
				sql.append(" \r\n UNION ALL");

				sql.append(" \r\n select o.id as organid,o.organname,o.oecode,o.province,wa.id as wid,wa.warehousename,swb.productid,swb.batch,sam.id as billcode, 1 as iscomplete,CYCLEOUTQUANTITY as quantity,sam.shipmentdate,sam.receivedate, 0 as received");
				sql.append(" \r\n from STOCK_WASTE_BOOK swb ");
				sql.append(" \r\n join TAKE_TICKET tt on swb.billcode = tt.id ");
				if (!StringUtil.isEmpty((String) paraMap.get("inWarehouseId"))) {
					sql.append(" \r\n and tt.inwarehouseid = '"
							+ paraMap.get("inWarehouseId") + "' "); // 仓库条件
				}
				if (!StringUtil.isEmpty((String) paraMap.get("ProductName"))
						&& !StringUtil.isEmpty((String) paraMap
								.get("packSizeName"))) { // 产品条件
					sql.append(" \r\n and swb.productid in (select id from product where productname = '"
									+ (String) paraMap.get("ProductName")
									+ "' and specmode= '"
									+ (String) paraMap.get("packSizeName")
									+ "') ");
				} else if (!StringUtil.isEmpty((String) paraMap
						.get("ProductName"))
						&& StringUtil.isEmpty((String) paraMap
								.get("packSizeName"))) { // 产品条件
					sql.append(" \r\n and swb.productid in (select id from product where productname = '"
									+ (String) paraMap.get("ProductName")
									+ "') ");
				}
				sql.append(" \r\n and swb.batch = '" + Constants.NO_BATCH
								+ "'");
				sql.append(" \r\n and swb.memo = '检货小票-出货'");
				if(DbUtil.isDealer(users)) {
					sql.append(" \r\n and tt.inwarehouseid in (select wv.warehouse_Id from RULE_USER_WH wv where wv.user_Id="
							+ users.getUserid() + ")");
				}
				sql.append(" \r\n and not EXISTS (select * from STOCK_WASTE_BOOK where billcode = tt.billno and warehouseid = tt.inwarehouseid and productid = swb.productid and batch = swb.batch)");
				sql.append(" \r\n join STOCK_MOVE sam on sam.id = tt.billno");
				sql.append(" \r\n and sam.iscomplete=1");
				if (!StringUtil.isEmpty((String) paraMap.get("beginDate"))) {
					sql.append(" \r\n and sam.makedate >=to_date('"
							+ paraMap.get("beginDate")
							+ "','yyyy-MM-dd hh24:mi:ss') "); // 开始时间条件
				}
				if (!StringUtil.isEmpty((String) paraMap.get("endDate"))) {
					sql.append(" \r\n and sam.makedate < to_date('"
							+ paraMap.get("endDate")
							+ "','yyyy-MM-dd hh24:mi:ss') "); // 结束时间条件
				}
				if (!StringUtil.isEmpty((String) paraMap.get("sapOrderId"))) {
					sql.append(" \r\n and sam.nccode = '"
							+ paraMap.get("sapOrderId") + "'"); // SAP订单号条件
				}
				sql.append(" \r\n join ORGAN o on sam.inorganid = o.id  ");
				if(!DbUtil.isDealer(users)) {
					sql.append(" \r\n and "+DbUtil.getWhereCondition(users, "o"));
				}
				if (sysPro != null
						&& "1".equals(sysPro.getProperty("genReportOnlyForPD"))) { // 是否只统计PD
					sql.append(" \r\n and o.organtype = 2 and o.organmodel = 1");
				}
				if (!StringUtil.isEmpty((String) paraMap.get("inOrganId"))) {
					sql.append(" \r\n and o.id = '" + paraMap.get("inOrganId")
							+ "' "); // 机构条件
				}
				if (!StringUtil.isEmpty((String) paraMap.get("region"))) {
					sql.append(" \r\n and o.province is not null and o.province in (select areaid from region_area where regioncodeid =  '"
									+ paraMap.get("region") + "')"); // 区域条件
				}
				sql.append(" \r\n left join WAREHOUSE wa on tt.inwarehouseid = wa.id");
				sql.append("\r\n where swb.productid not in (select id from product where USEFLAG = 0)");
			} else {
				sql.append(" \r\n select o.id as organid,o.organname,o.oecode,o.province,wa.id as wid, wa.warehousename,swb.productid,swb.batch,sam.id as billcode, sam.iscomplete,swb.cycleoutquantity as quantity,shipmentdate, receivedate, 0 as received ");
				sql.append(" \r\n from STOCK_MOVE sam");
				sql.append(" \r\n join ORGAN o on sam.inorganid = o.id and sam.isshipment=1 and sam.iscomplete=0 and sam.ISBLANKOUT=0");
				if(!DbUtil.isDealer(users)) {
					sql.append(" \r\n and "+DbUtil.getWhereCondition(users, "o"));
				} else {
					sql.append(" \r\n and sam.inwarehouseid in (select wv.warehouse_Id from RULE_USER_WH wv where wv.user_Id="
							+ users.getUserid() + ")");
				}
				if (sysPro != null
						&& "1".equals(sysPro.getProperty("genReportOnlyForPD"))) { // 是否只统计PD
					sql.append(" \r\n and o.organtype = 2 and o.organmodel = 1");
				}
				if (!StringUtil.isEmpty((String) paraMap.get("inOrganId"))) {
					sql.append(" \r\n and o.id = '" + paraMap.get("inOrganId")
							+ "' "); // 机构条件
				}
				if (!StringUtil.isEmpty((String) paraMap.get("inWarehouseId"))) {
					sql.append(" \r\n and sam.inwarehouseid = '"
							+ paraMap.get("inWarehouseId") + "' "); // 仓库条件
				}
				if (!StringUtil.isEmpty((String) paraMap.get("sapOrderId"))) {
					sql.append(" \r\n and sam.nccode = '"
							+ paraMap.get("sapOrderId") + "'"); // SAP订单号条件
				}
				if (!StringUtil.isEmpty((String) paraMap.get("region"))) {
					sql.append(" \r\n and o.province is not null and o.province in (select areaid from region_area where regioncodeid =  '"
									+ paraMap.get("region") + "')"); // 机构条件
				}
				if (!StringUtil.isEmpty((String) paraMap.get("beginDate"))) {
					sql.append(" \r\n and sam.makedate >=to_date('"
							+ paraMap.get("beginDate")
							+ "','yyyy-MM-dd hh24:mi:ss') "); // 开始时间条件
				}
				if (!StringUtil.isEmpty((String) paraMap.get("endDate"))) {
					sql.append(" \r\n and sam.makedate < to_date('"
							+ paraMap.get("endDate")
							+ "','yyyy-MM-dd hh24:mi:ss') "); // 结束时间条件
				}
				sql.append(" \r\n join TAKE_TICKET tt on sam.id = tt.billno");
				sql.append(" \r\n join STOCK_WASTE_BOOK swb on swb.billcode = tt.id");
				if (!StringUtil.isEmpty((String) paraMap.get("ProductName"))
						&& !StringUtil.isEmpty((String) paraMap
								.get("packSizeName"))) { // 产品条件
					sql.append(" \r\n and swb.productid in (select id from product where productname = '"
									+ (String) paraMap.get("ProductName")
									+ "' and specmode= '"
									+ (String) paraMap.get("packSizeName")
									+ "') ");
				} else if (!StringUtil.isEmpty((String) paraMap
						.get("ProductName"))
						&& StringUtil.isEmpty((String) paraMap
								.get("packSizeName"))) { // 产品条件
					sql.append(" \r\n and swb.productid in (select id from product where productname = '"
									+ (String) paraMap.get("ProductName")
									+ "') ");
				}
				sql.append(" \r\n left join WAREHOUSE wa on sam.inwarehouseid = wa.id ");
				sql.append("\r\n where swb.productid not in (select id from product where USEFLAG = 0)");
			}
			sql.append(" \r\n UNION ALL");
			// 退货单 ==7
			if (StringUtil.isEmpty(isComplete)) {
				sql.append(" \r\n select o.id as organid,o.organname,o.oecode,o.province,wa.id as wid,wa.warehousename,swb.productid,swb.batch,sam.id as billcode, 1 as iscomplete,CYCLEINQUANTITY as quantity,sam.makedate,sam.receivedate,1 as received ");
				sql.append(" \r\n from STOCK_WASTE_BOOK swb");
				sql.append(" \r\n join WAREHOUSE wa on swb.warehouseid = wa.id");
				if(DbUtil.isDealer(users)) {
					sql.append(" \r\n and swb.warehouseid in (select wv.warehouse_Id from RULE_USER_WH wv where wv.user_Id="
							+ users.getUserid() + ")");
				}
				sql.append(" \r\n join ORGAN_WITHDRAW sam on swb.billcode = sam.id");
				if (!StringUtil.isEmpty((String) paraMap.get("beginDate"))) {
					sql.append(" \r\n and sam.makedate >=to_date('"
							+ paraMap.get("beginDate")
							+ "','yyyy-MM-dd hh24:mi:ss') "); // 开始时间条件
				}
				if (!StringUtil.isEmpty((String) paraMap.get("endDate"))) {
					sql.append(" \r\n and sam.makedate < to_date('"
							+ paraMap.get("endDate")
							+ "','yyyy-MM-dd hh24:mi:ss') "); // 结束时间条件
				}
				if (!StringUtil.isEmpty((String) paraMap.get("sapOrderId"))) {
					sql.append(" \r\n and sam.nccode = '"
							+ paraMap.get("sapOrderId") + "'"); // SAP订单号条件
				}
				sql.append(" \r\n and swb.memo = '"
						+ Constants.TT_WASTE_BOOK_MEMO[7] + "' ");
				if (!StringUtil.isEmpty((String) paraMap.get("inWarehouseId"))) {
					sql.append(" \r\n and swb.warehouseid = '"
							+ paraMap.get("inWarehouseId") + "' "); // 仓库条件
				}
				if (!StringUtil.isEmpty((String) paraMap.get("ProductName"))
						&& !StringUtil.isEmpty((String) paraMap
								.get("packSizeName"))) { // 产品条件
					sql.append(" \r\n and swb.productid in (select id from product where productname = '"
									+ (String) paraMap.get("ProductName")
									+ "' and specmode= '"
									+ (String) paraMap.get("packSizeName")
									+ "') ");
				} else if (!StringUtil.isEmpty((String) paraMap
						.get("ProductName"))
						&& StringUtil.isEmpty((String) paraMap
								.get("packSizeName"))) { // 产品条件
					sql.append(" \r\n and swb.productid in (select id from product where productname = '"
									+ (String) paraMap.get("ProductName")
									+ "') ");
				}
				sql.append(" \r\n join ORGAN o on sam.receiveorganid = o.id ");
				if(!DbUtil.isDealer(users)) {
					sql.append(" \r\n and "+DbUtil.getWhereCondition(users, "o"));
				}
				if (sysPro != null
						&& "1".equals(sysPro.getProperty("genReportOnlyForPD"))) { // 是否只统计PD
					sql.append(" \r\n and o.organtype = 2 and o.organmodel = 1");
				}
				if (!StringUtil.isEmpty((String) paraMap.get("inOrganId"))) {
					sql.append(" \r\n and o.id = '" + paraMap.get("inOrganId")
							+ "' "); // 机构条件
				}
				if (!StringUtil.isEmpty((String) paraMap.get("region"))) {
					sql.append(" \r\n and o.province is not null and o.province in (select areaid from region_area where regioncodeid =  '"
									+ paraMap.get("region") + "')"); // 区域条件
				}
				sql.append("\r\n where swb.productid not in (select id from product where USEFLAG = 0)");
				sql.append(" \r\n UNION ALL");
				// 发货数量与码数量不一致的情况
				sql.append(" \r\n select o.id as organid,o.organname,o.oecode,o.province,wa.id as wid,wa.warehousename,swb.productid,swb.batch,sam.id as billcode, 1 as iscomplete,CYCLEOUTQUANTITY as quantity,sam.makedate,sam.receivedate, 0 as received");
				sql.append(" \r\n from STOCK_WASTE_BOOK swb ");
				sql.append(" \r\n join TAKE_TICKET tt on swb.billcode = tt.id ");
				if (!StringUtil.isEmpty((String) paraMap.get("inWarehouseId"))) {
					sql.append(" \r\n and tt.inwarehouseid = '"
							+ paraMap.get("inWarehouseId") + "' "); // 仓库条件
				}
				if (!StringUtil.isEmpty((String) paraMap.get("ProductName"))
						&& !StringUtil.isEmpty((String) paraMap
								.get("packSizeName"))) { // 产品条件
					sql.append(" \r\n and swb.productid in (select id from product where productname = '"
									+ (String) paraMap.get("ProductName")
									+ "' and specmode= '"
									+ (String) paraMap.get("packSizeName")
									+ "') ");
				} else if (!StringUtil.isEmpty((String) paraMap
						.get("ProductName"))
						&& StringUtil.isEmpty((String) paraMap
								.get("packSizeName"))) { // 产品条件
					sql.append(" \r\n and swb.productid in (select id from product where productname = '"
									+ (String) paraMap.get("ProductName")
									+ "') ");
				}
				sql.append(" \r\n and swb.batch = '" + Constants.NO_BATCH
								+ "'");
				sql.append(" \r\n and swb.memo = '检货小票-出货'");
				if(DbUtil.isDealer(users)) {
					sql.append(" \r\n and tt.inwarehouseid in (select wv.warehouse_Id from RULE_USER_WH wv where wv.user_Id="
							+ users.getUserid() + ")");
				}
				sql.append(" \r\n and not EXISTS (select * from STOCK_WASTE_BOOK where billcode = tt.billno and warehouseid = tt.inwarehouseid and productid = swb.productid and batch = swb.batch)");
				sql.append(" \r\n join ORGAN_WITHDRAW sam on sam.id = tt.billno");
				sql.append(" \r\n and sam.iscomplete=1");
				if (!StringUtil.isEmpty((String) paraMap.get("beginDate"))) {
					sql.append(" \r\n and sam.makedate >=to_date('"
							+ paraMap.get("beginDate")
							+ "','yyyy-MM-dd hh24:mi:ss') "); // 开始时间条件
				}
				if (!StringUtil.isEmpty((String) paraMap.get("endDate"))) {
					sql.append(" \r\n and sam.makedate < to_date('"
							+ paraMap.get("endDate")
							+ "','yyyy-MM-dd hh24:mi:ss') "); // 结束时间条件
				}
				if (!StringUtil.isEmpty((String) paraMap.get("sapOrderId"))) {
					sql.append(" \r\n and sam.nccode = '"
							+ paraMap.get("sapOrderId") + "'"); // SAP订单号条件
				}
				sql.append(" \r\n join ORGAN o on sam.receiveorganid = o.id  ");
				if(!DbUtil.isDealer(users)) {
					sql.append(" \r\n and "+DbUtil.getWhereCondition(users, "o"));
				}
				if (sysPro != null
						&& "1".equals(sysPro.getProperty("genReportOnlyForPD"))) { // 是否只统计PD
					sql.append(" \r\n and o.organtype = 2 and o.organmodel = 1");
				}
				if (!StringUtil.isEmpty((String) paraMap.get("inOrganId"))) {
					sql.append(" \r\n and o.id = '" + paraMap.get("inOrganId")
							+ "'"); // 机构条件
				}
				if (!StringUtil.isEmpty((String) paraMap.get("region"))) {
					sql.append(" \r\n and o.province is not null and o.province in (select areaid from region_area where regioncodeid =  '"
									+ paraMap.get("region") + "')"); // 区域条件
				}
				sql.append(" \r\n left join WAREHOUSE wa on tt.inwarehouseid = wa.id");
				sql.append("\r\n where swb.productid not in (select id from product where USEFLAG = 0)");
				sql.append(" \r\n UNION ALL");

				sql.append(" \r\n select o.id as organid,o.organname,o.oecode,o.province,wa.id as wid, wa.warehousename,swb.productid,swb.batch,sam.id as billcode, sam.iscomplete,swb.cycleoutquantity as quantity,sam.makedate, sam.receivedate, 0 as received ");
				sql.append(" \r\n from ORGAN_WITHDRAW sam");
				sql.append(" \r\n join ORGAN o on sam.receiveorganid = o.id and sam.iscomplete=1 and sam.iscomplete=0 and sam.ISCOMPLETE=0");
				if(!DbUtil.isDealer(users)) {
					sql.append(" \r\n and "+DbUtil.getWhereCondition(users, "o"));
				} else {
					sql.append(" \r\n and sam.inwarehouseid in (select wv.warehouse_Id from RULE_USER_WH wv where wv.user_Id="
							+ users.getUserid() + ")");
				}
				if (sysPro != null
						&& "1".equals(sysPro.getProperty("genReportOnlyForPD"))) { // 是否只统计PD
					sql.append(" \r\n and o.organtype = 2 and o.organmodel = 1");
				}
				if (!StringUtil.isEmpty((String) paraMap.get("inOrganId"))) {
					sql.append(" \r\n and o.id = '" + paraMap.get("inOrganId")
							+ "' "); // 机构条件
				}
				if (!StringUtil.isEmpty((String) paraMap.get("inWarehouseId"))) {
					sql.append(" \r\n and sam.inwarehouseid = '"
							+ paraMap.get("inWarehouseId") + "' "); // 仓库条件
				}
				if (!StringUtil.isEmpty((String) paraMap.get("sapOrderId"))) {
					sql.append(" \r\n and sam.nccode = '"
							+ paraMap.get("sapOrderId") + "'"); // SAP订单号条件
				}
				if (!StringUtil.isEmpty((String) paraMap.get("region"))) {
					sql.append(" \r\n and o.province is not null and o.province in (select areaid from region_area where regioncodeid =  '"
									+ paraMap.get("region") + "')"); // 机构条件
				}
				if (!StringUtil.isEmpty((String) paraMap.get("beginDate"))) {
					sql.append(" \r\n and sam.makedate >=to_date('"
							+ paraMap.get("beginDate")
							+ "','yyyy-MM-dd hh24:mi:ss') "); // 开始时间条件
				}
				if (!StringUtil.isEmpty((String) paraMap.get("endDate"))) {
					sql.append(" \r\n and sam.makedate < to_date('"
							+ paraMap.get("endDate")
							+ "','yyyy-MM-dd hh24:mi:ss') "); // 结束时间条件
				}
				sql.append(" \r\n join TAKE_TICKET tt on sam.id = tt.billno");
				sql.append(" \r\n join STOCK_WASTE_BOOK swb on swb.billcode = tt.id");
				if (!StringUtil.isEmpty((String) paraMap.get("ProductName"))
						&& !StringUtil.isEmpty((String) paraMap
								.get("packSizeName"))) { // 产品条件
					sql.append(" \r\n and swb.productid in (select id from product where productname = '"
									+ (String) paraMap.get("ProductName")
									+ "' and specmode= '"
									+ (String) paraMap.get("packSizeName")
									+ "') ");
				} else if (!StringUtil.isEmpty((String) paraMap
						.get("ProductName"))
						&& StringUtil.isEmpty((String) paraMap
								.get("packSizeName"))) { // 产品条件
					sql.append(" \r\n and swb.productid in (select id from product where productname = '"
									+ (String) paraMap.get("ProductName")
									+ "') ");
				}
				sql.append(" \r\n left join WAREHOUSE wa on sam.inwarehouseid = wa.id ");
				sql.append("\r\n where swb.productid not in (select id from product where USEFLAG = 0)");
			} else if ("1".equals(isComplete.trim())) {
				sql.append(" \r\n select o.id as organid,o.organname,o.oecode,o.province,wa.id as wid,wa.warehousename,swb.productid,swb.batch,sam.id as billcode, 1 as iscomplete,CYCLEINQUANTITY as quantity,sam.makedate,sam.receivedate,1 as received ");
				sql.append(" \r\n from STOCK_WASTE_BOOK swb");
				sql.append(" \r\n join WAREHOUSE wa on swb.warehouseid = wa.id");
				if(DbUtil.isDealer(users)) {
					sql.append(" \r\n and swb.warehouseid in (select wv.warehouse_Id from RULE_USER_WH wv where wv.user_Id="
							+ users.getUserid() + ")");
				}
				sql.append(" \r\n join ORGAN_WITHDRAW sam on swb.billcode = sam.id");
				if (!StringUtil.isEmpty((String) paraMap.get("beginDate"))) {
					sql.append(" \r\n and sam.makedate >=to_date('"
							+ paraMap.get("beginDate")
							+ "','yyyy-MM-dd hh24:mi:ss') "); // 开始时间条件
				}
				if (!StringUtil.isEmpty((String) paraMap.get("endDate"))) {
					sql.append(" \r\n and sam.makedate < to_date('"
							+ paraMap.get("endDate")
							+ "','yyyy-MM-dd hh24:mi:ss') "); // 结束时间条件
				}
				if (!StringUtil.isEmpty((String) paraMap.get("sapOrderId"))) {
					sql.append(" \r\n and sam.nccode = '"
							+ paraMap.get("sapOrderId") + "'"); // SAP订单号条件
				}
				sql.append(" \r\n and swb.memo = '"
						+ Constants.TT_WASTE_BOOK_MEMO[7] + "' ");
				if (!StringUtil.isEmpty((String) paraMap.get("inWarehouseId"))) {
					sql.append(" \r\n and swb.warehouseid = '"
							+ paraMap.get("inWarehouseId") + "' "); // 仓库条件
				}
				if (!StringUtil.isEmpty((String) paraMap.get("ProductName"))
						&& !StringUtil.isEmpty((String) paraMap
								.get("packSizeName"))) { // 产品条件
					sql.append(" \r\n and swb.productid in (select id from product where productname = '"
									+ (String) paraMap.get("ProductName")
									+ "' and specmode= '"
									+ (String) paraMap.get("packSizeName")
									+ "') ");
				} else if (!StringUtil.isEmpty((String) paraMap
						.get("ProductName"))
						&& StringUtil.isEmpty((String) paraMap
								.get("packSizeName"))) { // 产品条件
					sql.append(" \r\n and swb.productid in (select id from product where productname = '"
									+ (String) paraMap.get("ProductName")
									+ "') ");
				}
				sql.append(" \r\n join ORGAN o on sam.receiveorganid = o.id ");
				if(!DbUtil.isDealer(users)) {
					sql.append(" \r\n and "+DbUtil.getWhereCondition(users, "o"));
				}
				if (sysPro != null
						&& "1".equals(sysPro.getProperty("genReportOnlyForPD"))) { // 是否只统计PD
					sql.append(" \r\n and o.organtype = 2 and o.organmodel = 1");
				}
				if (!StringUtil.isEmpty((String) paraMap.get("inOrganId"))) {
					sql.append(" \r\n and o.id = '" + paraMap.get("inOrganId")
							+ "' "); // 机构条件
				}
				if (!StringUtil.isEmpty((String) paraMap.get("region"))) {
					sql.append(" \r\n and o.province is not null and o.province in (select areaid from region_area where regioncodeid =  '"
									+ paraMap.get("region") + "')"); // 区域条件
				}
				sql.append("\r\n where swb.productid not in (select id from product where USEFLAG = 0)");
				sql.append(" \r\n UNION ALL");

				sql.append(" \r\n select o.id as organid,o.organname,o.oecode,o.province,wa.id as wid,wa.warehousename,swb.productid,swb.batch,sam.id as billcode, 1 as iscomplete,CYCLEOUTQUANTITY as quantity,sam.makedate,sam.receivedate, 0 as received");
				sql.append(" \r\n from STOCK_WASTE_BOOK swb ");
				sql.append(" \r\n join TAKE_TICKET tt on swb.billcode = tt.id ");
				if (!StringUtil.isEmpty((String) paraMap.get("inWarehouseId"))) {
					sql.append(" \r\n and tt.inwarehouseid = '"
							+ paraMap.get("inWarehouseId") + "' "); // 仓库条件
				}
				if (!StringUtil.isEmpty((String) paraMap.get("ProductName"))
						&& !StringUtil.isEmpty((String) paraMap
								.get("packSizeName"))) { // 产品条件
					sql.append(" \r\n and swb.productid in (select id from product where productname = '"
									+ (String) paraMap.get("ProductName")
									+ "' and specmode= '"
									+ (String) paraMap.get("packSizeName")
									+ "') ");
				} else if (!StringUtil.isEmpty((String) paraMap
						.get("ProductName"))
						&& StringUtil.isEmpty((String) paraMap
								.get("packSizeName"))) { // 产品条件
					sql.append(" \r\n and swb.productid in (select id from product where productname = '"
									+ (String) paraMap.get("ProductName")
									+ "') ");
				}
				sql.append(" \r\n and swb.batch = '" + Constants.NO_BATCH
								+ "'");
				sql.append(" \r\n and swb.memo = '检货小票-出货'");
				if(DbUtil.isDealer(users)) {
					sql.append(" \r\n and tt.inwarehouseid in (select wv.warehouse_Id from RULE_USER_WH wv where wv.user_Id="
							+ users.getUserid() + ")");
				}
				sql.append(" \r\n and not EXISTS (select * from STOCK_WASTE_BOOK where billcode = tt.billno and warehouseid = tt.inwarehouseid and productid = swb.productid and batch = swb.batch)");
				sql.append(" \r\n join ORGAN_WITHDRAW sam on sam.id = tt.billno");
				sql.append(" \r\n and sam.iscomplete=1");
				if (!StringUtil.isEmpty((String) paraMap.get("beginDate"))) {
					sql.append(" \r\n and sam.makedate >=to_date('"
							+ paraMap.get("beginDate")
							+ "','yyyy-MM-dd hh24:mi:ss') "); // 开始时间条件
				}
				if (!StringUtil.isEmpty((String) paraMap.get("endDate"))) {
					sql.append(" \r\n and sam.makedate < to_date('"
							+ paraMap.get("endDate")
							+ "','yyyy-MM-dd hh24:mi:ss') "); // 结束时间条件
				}
				if (!StringUtil.isEmpty((String) paraMap.get("sapOrderId"))) {
					sql.append(" \r\n and sam.nccode = '"
							+ paraMap.get("sapOrderId") + "'"); // SAP订单号条件
				}
				sql.append(" \r\n join ORGAN o on sam.receiveorganid = o.id  ");
				if(!DbUtil.isDealer(users)) {
					sql.append(" \r\n and "+DbUtil.getWhereCondition(users, "o"));
				}
				if (sysPro != null
						&& "1".equals(sysPro.getProperty("genReportOnlyForPD"))) { // 是否只统计PD
					sql.append(" \r\n and o.organtype = 2 and o.organmodel = 1");
				}
				if (!StringUtil.isEmpty((String) paraMap.get("inOrganId"))) {
					sql.append(" \r\n and o.id = '" + paraMap.get("inOrganId")
							+ "' "); // 机构条件
				}
				if (!StringUtil.isEmpty((String) paraMap.get("region"))) {
					sql.append(" \r\n and o.province is not null and o.province in (select areaid from region_area where regioncodeid =  '"
									+ paraMap.get("region") + "')"); // 区域条件
				}
				sql.append(" \r\n left join WAREHOUSE wa on tt.inwarehouseid = wa.id");
				sql.append("\r\n where swb.productid not in (select id from product where USEFLAG = 0)");
			} else {
				sql.append(" \r\n select o.id as organid,o.organname,o.oecode,o.province,wa.id as wid, wa.warehousename,swb.productid,swb.batch,sam.id as billcode, sam.iscomplete,swb.cycleoutquantity as quantity,sam.makedate, sam.receivedate, 0 as received ");
				sql.append(" \r\n from ORGAN_WITHDRAW sam");
				sql.append(" \r\n join ORGAN o on sam.receiveorganid = o.id and sam.iscomplete=1 and sam.iscomplete=0 and sam.ISCOMPLETE=0");
				if(!DbUtil.isDealer(users)) {
					sql.append(" \r\n and "+DbUtil.getWhereCondition(users, "o"));
				} else {
					sql.append(" \r\n and sam.inwarehouseid in (select wv.warehouse_Id from RULE_USER_WH wv where wv.user_Id="
							+ users.getUserid() + ")");
				}
				if (sysPro != null
						&& "1".equals(sysPro.getProperty("genReportOnlyForPD"))) { // 是否只统计PD
					sql.append(" \r\n and o.organtype = 2 and o.organmodel = 1");
				}
				if (!StringUtil.isEmpty((String) paraMap.get("inOrganId"))) {
					sql.append(" \r\n and o.id = '" + paraMap.get("inOrganId")
							+ "' "); // 机构条件
				}
				if (!StringUtil.isEmpty((String) paraMap.get("inWarehouseId"))) {
					sql.append(" \r\n and sam.inwarehouseid = '"
							+ paraMap.get("inWarehouseId") + "' "); // 仓库条件
				}
				if (!StringUtil.isEmpty((String) paraMap.get("sapOrderId"))) {
					sql.append(" \r\n and sam.nccode = '"
							+ paraMap.get("sapOrderId") + "'"); // SAP订单号条件
				}
				if (!StringUtil.isEmpty((String) paraMap.get("region"))) {
					sql.append(" \r\n and o.province is not null and o.province in (select areaid from region_area where regioncodeid =  '"
									+ paraMap.get("region") + "')"); // 机构条件
				}
				if (!StringUtil.isEmpty((String) paraMap.get("beginDate"))) {
					sql.append(" \r\n and sam.makedate >=to_date('"
							+ paraMap.get("beginDate")
							+ "','yyyy-MM-dd hh24:mi:ss') "); // 开始时间条件
				}
				if (!StringUtil.isEmpty((String) paraMap.get("endDate"))) {
					sql.append(" \r\n and sam.makedate < to_date('"
							+ paraMap.get("endDate")
							+ "','yyyy-MM-dd hh24:mi:ss') "); // 结束时间条件
				}
				sql.append(" \r\n join TAKE_TICKET tt on sam.id = tt.billno");
				sql.append(" \r\n join STOCK_WASTE_BOOK swb on swb.billcode = tt.id");
				if (!StringUtil.isEmpty((String) paraMap.get("ProductName"))
						&& !StringUtil.isEmpty((String) paraMap
								.get("packSizeName"))) { // 产品条件
					sql.append(" \r\n and swb.productid in (select id from product where productname = '"
									+ (String) paraMap.get("ProductName")
									+ "' and specmode= '"
									+ (String) paraMap.get("packSizeName")
									+ "') ");
				} else if (!StringUtil.isEmpty((String) paraMap
						.get("ProductName"))
						&& StringUtil.isEmpty((String) paraMap
								.get("packSizeName"))) { // 产品条件
					sql.append(" \r\n and swb.productid in (select id from product where productname = '"
									+ (String) paraMap.get("ProductName")
									+ "') ");
				}
				sql.append(" \r\n left join WAREHOUSE wa on sam.inwarehouseid = wa.id ");
				sql.append("\r\n where swb.productid not in (select id from product where USEFLAG = 0)");
			}
			sql.append(" \r\n ) group by organid,wid,productid,batch,billcode");
			break;
		}
		List<Map> list = new ArrayList<Map>();
		if (request == null && pageSize == 0) {
			list = EntityManager.jdbcquery(sql.toString());
		} else {
			list = PageQuery.jdbcSqlserverQuery(request, "billcode", sql
					.toString(), pageSize);
		}
		if (list != null && list.size() > 0) {
			Map<String, FUnit> funitMap = appFUnit.getAllMap(); // 获取所有的单位换算信息
			Map<Integer, String> countUnitMap = appBr
					.getBaseResourceMap("CountUnit");
			Map<Integer, String> provinceMap = appCountryArea.getProvinceMap();
			for (Map map : list) {
				PDGoodsReceivingStatusReport report = new PDGoodsReceivingStatusReport();
				// 将Map中对应的值赋值给实例
				MapUtil.mapToObject(map, report);

				// 大区
				List<String> regionNames = appRegion
						.getRegionSortNameByAreaId((String) map.get("province"));
				if (regionNames != null && regionNames.size() > 0) {
					report.setRegionName(regionNames.get(0));
				}
				if (!StringUtil.isEmpty((String) map.get("province"))) {
					report.setAreaName(provinceMap.get(Integer
							.parseInt((String) map.get("province"))));
				}

				Product product = appProduct.getProductByID((String) map
						.get("productid"));
				report.setmCode(product.getmCode());
				report.setProductName(product.getProductname());
				report.setSpecMode(product.getSpecmode());
				report.setProductNameen(product.getProductnameen());
				report.setPackSizeNameEn(product.getPackSizeNameEn());
				report.setMatericalChDes(product.getMatericalChDes());
				report.setMatericalEnDes(product.getMatericalEnDes());

				if (report.getIsComplete() == 1) {
					double quantity = Double.valueOf((String) map
							.get("quantity"));
					if (StringUtil.isEmpty((String) paraMap.get("countByUnit"))) {
						// 检查单位是否可以正常转化,如不能则不转化
						if (checkRate(product.getId(), product.getSunit(),
								Constants.DEFAULT_UNIT_ID, funitMap)) {
							quantity = changeUnit(product.getId(), product
									.getSunit(), quantity,
									Constants.DEFAULT_UNIT_ID, funitMap);
							report.setUnit(countUnitMap
									.get(Constants.DEFAULT_UNIT_ID));
						}
					} else {
						// 转换成计量单位
						if (product.getBoxquantity() != null
								&& product.getBoxquantity() != 0d) {
							quantity = ArithDouble.mul(quantity, product
									.getBoxquantity());
							report.setUnit(countUnitMap.get(product
									.getCountunit()));
						}
					}
					report.setQuantity(quantity);
					StockAlterMoveDetail samd = asamd
							.getStockAlterMoveDetailBySamIDAndPid(report
									.getBillCode(), product.getId());
					if (samd != null) {
						if (samd.getTakequantity() != null
								&& samd.getReceiveQuantity() != null
								&& samd.getTakequantity() == samd
										.getReceiveQuantity()) {
							report.setReason(samd.getReceiveRemark());
						} else {
							if (Constants.NO_BATCH.equals(report.getBatch())) {
								report.setReason(samd.getReceiveRemark());
							}
						}
					}
					if ("0".equals((String) map.get("received"))) {
						report.setTakequantity(Double.toString(report
								.getQuantity()));
						report.setReceiveQuantity(Double.toString(0d));
					} else {
						if (Constants.NO_BATCH.equals(report.getBatch())) {
							StockWasteBook swb = appStockWasteBook
									.getStockWastBookByBillCodeAndBatch(report
											.getBillCode(), product.getId(),
											Constants.NO_BATCH);
							if (swb != null) {
								double takeQuantity = swb.getCycleoutquantity();

								if (StringUtil.isEmpty((String) paraMap
										.get("countByUnit"))) {
									// 检查单位是否可以正常转化,如不能则不转化
									if (checkRate(product.getId(), product
											.getSunit(),
											Constants.DEFAULT_UNIT_ID, funitMap)) {
										takeQuantity = changeUnit(product
												.getId(), product.getSunit(),
												takeQuantity,
												Constants.DEFAULT_UNIT_ID,
												funitMap);
									}
								} else {
									// 转换成计量单位
									if (product.getBoxquantity() != null
											&& product.getBoxquantity() != 0d) {
										takeQuantity = ArithDouble.mul(
												takeQuantity, product
														.getBoxquantity());
									}
								}
								report.setTakequantity(Double
										.toString(takeQuantity));
							}
							report.setReceiveQuantity(Double.toString(report
									.getQuantity()));
						} else {
							report.setTakequantity(Double.toString(report
									.getQuantity()));
							report.setReceiveQuantity(Double.toString(report
									.getQuantity()));
						}
					}
				} else {
					double takeQuantity = report.getQuantity();
					if (StringUtil.isEmpty((String) paraMap.get("countByUnit"))) {
						// 检查单位是否可以正常转化,如不能则不转化
						if (checkRate(product.getId(), product.getSunit(),
								Constants.DEFAULT_UNIT_ID, funitMap)) {
							takeQuantity = changeUnit(product.getId(), product
									.getSunit(), takeQuantity,
									Constants.DEFAULT_UNIT_ID, funitMap);
							report.setUnit(countUnitMap
									.get(Constants.DEFAULT_UNIT_ID));
						}
					} else {
						// 转换成计量单位
						if (product.getBoxquantity() != null
								&& product.getBoxquantity() != 0d) {
							takeQuantity = ArithDouble.mul(takeQuantity,
									product.getBoxquantity());
							report.setUnit(countUnitMap.get(product
									.getCountunit()));
						}
					}

					report.setTakequantity(Double.toString(takeQuantity));
				}
				resultList.add(report);
			}
		}
		return resultList;
	}

}
