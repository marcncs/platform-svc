package com.winsafe.drp.server;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.winsafe.drp.dao.BaseResource;
import com.winsafe.drp.dao.FUnit;  
import com.winsafe.drp.dao.Product;
import com.winsafe.drp.dao.SalesConsumeDetailReportForm;
import com.winsafe.drp.dao.SalesConsumeReportForm;
import com.winsafe.drp.dao.StockAlterMove;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.metadata.OrganType;
import com.winsafe.drp.util.ArithDouble;
import com.winsafe.drp.util.Constants;
import com.winsafe.drp.util.Dateutil;
import com.winsafe.drp.util.MapUtil;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.StringUtil;
import com.winsafe.hbm.util.pager2.PageQuery;
import com.winsafe.sap.pojo.PrintJob;

public class SalesConsumeReportService extends ReportServices{
	private static Logger logger = Logger.getLogger(SalesConsumeReportService.class);
	
	// 根据条件查询 
	public List<SalesConsumeReportForm> queryReport2(HttpServletRequest request, int pageSize,SalesConsumeReportForm queryForm,UsersBean users) throws Exception{
		Map<String, Object> param = new LinkedHashMap<String, Object>();
		//对于结束日期增加一天
		if(!StringUtil.isEmpty(queryForm.getEndDate())){
			String endDateStr = queryForm.getEndDate();
			Date endDate = Dateutil.StringToDate(endDateStr);
			endDate = Dateutil.addDay2Date(1, endDate);
			queryForm.setEndDate(Dateutil.formatDate(endDate));
		}
		List<SalesConsumeReportForm> resultList = new ArrayList<SalesConsumeReportForm>();
		StringBuffer sql = new StringBuffer();
		
		sql.append(" \r\n select ");
		sql.append(" \r\n  r.sortname regionName");
		sql.append(" \r\n ,o.province provinceId");
		sql.append(" \r\n ,country.areaname province");
		sql.append(" \r\n ,o.id organId ");
		sql.append(" \r\n ,o.oecode oecode ");
		sql.append(" \r\n ,o.organname organName");
		sql.append(" \r\n ,pstr.sortname psName");
		sql.append(" \r\n ,p.id productId ");
		sql.append(" \r\n ,p.mcode mCode ");
		sql.append(" \r\n ,p.productname productName ");
		sql.append(" \r\n ,p.productnameen productNameen ");
		sql.append(" \r\n ,p.matericalchdes matericalChDes");
		sql.append(" \r\n ,p.matericalendes matericalEnDes");
		sql.append(" \r\n ,p.sunit unitId");
		sql.append(" \r\n ,p.specmode packSizeName");
		sql.append(" \r\n ,p.packsizenameen packSizeNameEn");
		sql.append(" \r\n ,sales.quantity salesQuantity");
		sql.append(" \r\n ,pw.quantity pwQuantity");
		sql.append(" \r\n ,consume.quantity consumeQuantity");
		sql.append(" \r\n ,ow.quantity owQuantity ");
		sql.append(" \r\n ,w.WAREHOUSENAME ");
		
		sql.append(" \r\n from organ o cross join product p ");
		sql.append(" \r\n join WAREHOUSE w on w.makeorganid = o.id ");
		sql.append(" \r\n LEFT JOIN PRODUCT_STRUCT pstr on p.PSID = pstr.STRUCTCODE ");
		sql.append(" \r\n LEFT JOIN COUNTRY_AREA country on o.province=country.id  ");
		
		sql.append(" \r\n LEFT JOIN (SELECT max(REGIONCODEID) regioncodeid,max(AREAID) areaid  FROM REGION_AREA GROUP BY AREAID) ra on ra.areaid=country.id  ");
		sql.append(" \r\n LEFT JOIN REGION r on r.regioncode=ra.regioncodeid  ");
		// 销售数量(工厂给PD的数量， 扣除调拨给其他PD的数量，加上其他PD调拨回来的)
		sql.append(" \r\n LEFT JOIN ( ");
		sql.append(" \r\n select max(oid) as oid, max(pid) as pid, sum(quantity) as quantity, wid from ( ");
		//工厂给PD的数量
		sql.append(" \r\n --工厂给PD的数量");
		sql.append(" \r\n select tt.inoid oid,ttd.productid pid ,(ttd.quantity * f.xquantity) quantity,w.id wid  ");
		sql.append(" \r\n from TAKE_TICKET tt INNER JOIN TAKE_TICKET_DETAIL ttd on tt.id = ttd.ttid  and tt.isaudit = 1  and tt.bsort = 1 ");
		if(!StringUtil.isEmpty(queryForm.getOrganId())){
			sql.append(" \r\n and tt.inoid = ? ");
			param.put(UUID.randomUUID().toString(), queryForm.getOrganId()); //机构条件
		}
		if(!StringUtil.isEmpty(queryForm.getWarehouseId())){
			sql.append(" \r\n and tt.inwarehouseid = ? ");param.put(UUID.randomUUID().toString(), queryForm.getWarehouseId()); //仓库条件
		}
		if(!StringUtil.isEmpty(queryForm.getProductName()) && !StringUtil.isEmpty(queryForm.getPackSizeName())) { //产品条件
			sql.append(" \r\n and ttd.productid in (select id from product where productname = ? and specmode=?) ");param.put(UUID.randomUUID().toString(), queryForm.getProductName());param.put(UUID.randomUUID().toString(), queryForm.getPackSizeName());
		} else if(!StringUtil.isEmpty(queryForm.getProductName()) && StringUtil.isEmpty(queryForm.getPackSizeName())) { //产品条件
			sql.append(" \r\n and ttd.productid in (select id from product where productname = ?) ");param.put(UUID.randomUUID().toString(), queryForm.getProductName());
		}
		//过滤拒签的单据
		sql.append(" \r\n INNER JOIN STOCK_ALTER_MOVE sam on sam.id = tt.billno and sam.isblankout = 0");
		if(!StringUtil.isEmpty(queryForm.getBeginDate())){
			sql.append(" \r\n and sam.movedate >=to_date(?,'yyyy-MM-dd hh24:mi:ss') ");param.put(UUID.randomUUID().toString(), queryForm.getBeginDate()); //开始时间条件
		}
		if(!StringUtil.isEmpty(queryForm.getEndDate())){
			sql.append(" \r\n and sam.movedate < to_date(?,'yyyy-MM-dd hh24:mi:ss') ");param.put(UUID.randomUUID().toString(), queryForm.getEndDate()); //结束时间条件
		}
		sql.append(" \r\n INNER JOIN ORGAN o on tt.oid = o.id and o.organtype = 1  "); // 出库仓库为工厂
		sql.append(" \r\n INNER JOIN ORGAN ino on tt.inoid = ino.id and ino.organtype = 2 and ino.organmodel = 1"); // 出库机构为PD
		sql.append(" \r\n join WAREHOUSE w on tt.inwarehouseid = w.id and w.USEFLAG = 1  ");
		sql.append(" \r\n LEFT JOIN F_UNIT f on ttd.productid = f.productid and ttd.unitid = f.funitid ");
		if(DbUtil.isDealer(users)) {
			sql.append(" \r\n LEFT JOIN RULE_USER_WH rw on tt.inwarehouseid = rw.warehouse_id  ");
			sql.append(" \r\n where rw.user_id = ?");param.put(UUID.randomUUID().toString(), users.getUserid());
		} else {
			sql.append(" \r\n where "+DbUtil.getWhereCondition(users, "ino"));
		}
		
		sql.append(" \r\n UNION ALL ");
		//PD调拨回来的
		sql.append(" \r\n --PD调拨回来的");
		sql.append(" \r\n select tt.inoid oid,ttd.productid pid ,(ttd.realquantity * f.xquantity) quantity,w.id wid  ");
		sql.append(" \r\n from TAKE_TICKET tt INNER JOIN TAKE_TICKET_DETAIL ttd on tt.id = ttd.ttid  and tt.isaudit = 1  and tt.bsort = 2 ");
		if(!StringUtil.isEmpty(queryForm.getOrganId())){
			sql.append(" \r\n and tt.inoid = ? ");param.put(UUID.randomUUID().toString(), queryForm.getOrganId()); //机构条件
		}
		if(!StringUtil.isEmpty(queryForm.getWarehouseId())){
			sql.append(" \r\n and tt.inwarehouseid = ? ");param.put(UUID.randomUUID().toString(), queryForm.getWarehouseId()); //仓库条件
		}
		if(!StringUtil.isEmpty(queryForm.getProductName()) && !StringUtil.isEmpty(queryForm.getPackSizeName())) { //产品条件
			sql.append(" \r\n and ttd.productid in (select id from product where productname = ? and specmode=?) ");param.put(UUID.randomUUID().toString(), queryForm.getProductName());param.put(UUID.randomUUID().toString(), queryForm.getPackSizeName());
		} else if(!StringUtil.isEmpty(queryForm.getProductName()) && StringUtil.isEmpty(queryForm.getPackSizeName())) { //产品条件
			sql.append(" \r\n and ttd.productid in (select id from product where productname = ?) ");param.put(UUID.randomUUID().toString(), queryForm.getProductName());
		}
		sql.append(" \r\n INNER JOIN STOCK_MOVE sm on sm.id = tt.billno");
		if(!StringUtil.isEmpty(queryForm.getBeginDate())){
			sql.append(" \r\n and tt.auditdate >=to_date(?,'yyyy-MM-dd hh24:mi:ss') ");param.put(UUID.randomUUID().toString(), queryForm.getBeginDate()); //开始时间条件
		}
		if(!StringUtil.isEmpty(queryForm.getEndDate())){
			sql.append(" \r\n and tt.auditdate < to_date(?,'yyyy-MM-dd hh24:mi:ss') ");param.put(UUID.randomUUID().toString(), queryForm.getEndDate()); //结束时间条件
		}
		sql.append(" \r\n INNER JOIN ORGAN o on tt.oid = o.id and o.organtype = 2 and o.organmodel = 1"); // 出库机构为PD
		sql.append(" \r\n join WAREHOUSE w on tt.inwarehouseid = w.id and w.USEFLAG = 1  ");
		sql.append(" \r\n LEFT JOIN F_UNIT f on ttd.productid = f.productid and ttd.unitid = f.funitid ");
		if(DbUtil.isDealer(users)) {
			sql.append(" \r\n LEFT JOIN RULE_USER_WH rw on tt.inwarehouseid = rw.warehouse_id  ");
			sql.append(" \r\n where rw.user_id = ?");param.put(UUID.randomUUID().toString(), users.getUserid());
		} else {
			sql.append(" \r\n where "+DbUtil.getWhereCondition(users, "o"));
		}
		
		sql.append(" \r\n UNION ALL ");
		//调拨给其他PD的
		sql.append(" \r\n --调拨给其他PD的");
		sql.append(" \r\n select tt.oid,ttd.productid pid ,-(ttd.realquantity * f.xquantity) quantity,w.id wid    ");
		sql.append(" \r\n from TAKE_TICKET tt INNER JOIN TAKE_TICKET_DETAIL ttd on tt.id = ttd.ttid  and tt.isaudit = 1  and tt.bsort = 2 ");
		if(!StringUtil.isEmpty(queryForm.getOrganId())){
			sql.append(" \r\n and tt.oid = ? ");param.put(UUID.randomUUID().toString(), queryForm.getOrganId()); //机构条件
		}
		if(!StringUtil.isEmpty(queryForm.getWarehouseId())){
			sql.append(" \r\n and tt.WAREHOUSEID = ? ");param.put(UUID.randomUUID().toString(), queryForm.getWarehouseId()); //仓库条件
		}
		if(!StringUtil.isEmpty(queryForm.getProductName()) && !StringUtil.isEmpty(queryForm.getPackSizeName())) { //产品条件
			sql.append(" \r\n and ttd.productid in (select id from product where productname = ? and specmode=?) ");param.put(UUID.randomUUID().toString(), queryForm.getProductName());param.put(UUID.randomUUID().toString(), queryForm.getPackSizeName());
		} else if(!StringUtil.isEmpty(queryForm.getProductName()) && StringUtil.isEmpty(queryForm.getPackSizeName())) { //产品条件
			sql.append(" \r\n and ttd.productid in (select id from product where productname = ?) ");param.put(UUID.randomUUID().toString(), queryForm.getProductName());
		}
		sql.append(" \r\n INNER JOIN STOCK_MOVE sm on sm.id = tt.billno");
		if(!StringUtil.isEmpty(queryForm.getBeginDate())){
			sql.append(" \r\n and tt.auditdate >=to_date(?,'yyyy-MM-dd hh24:mi:ss') ");param.put(UUID.randomUUID().toString(), queryForm.getBeginDate()); //开始时间条件
		}
		if(!StringUtil.isEmpty(queryForm.getEndDate())){
			sql.append(" \r\n and tt.auditdate < to_date(?,'yyyy-MM-dd hh24:mi:ss') ");param.put(UUID.randomUUID().toString(), queryForm.getEndDate()); //结束时间条件
		}
		sql.append(" \r\n INNER JOIN ORGAN o on tt.inoid = o.id and o.organtype = 2 and  o.organmodel = 1 "); // 收货机构为PD
		sql.append(" \r\n join WAREHOUSE w on tt.warehouseid = w.id and w.USEFLAG = 1  ");
		sql.append(" \r\n LEFT JOIN F_UNIT f on ttd.productid = f.productid and ttd.unitid = f.funitid ");
		if(DbUtil.isDealer(users)) {
			sql.append(" \r\n LEFT JOIN RULE_USER_WH rw on tt.warehouseid = rw.warehouse_id  ");
			sql.append(" \r\n where rw.user_id = ?");param.put(UUID.randomUUID().toString(), users.getUserid());
		} else {
			sql.append(" \r\n where "+DbUtil.getWhereCondition(users, "o"));
		}
		
		
		//#start modified by ryan.xi at 20150714
		//扣减分包时大包的销售数量
		sql.append(" \r\n UNION ALL ");
		sql.append(" \r\n --扣减分包时大包的销售数量");
		sql.append(" \r\n select tt.ORGANID oid,ttd.OUTPRODUCTID pid ,-(OUTQUANTITY-ttd.wastage) quantity,w.id wid  ");
		sql.append(" \r\n from PACK_SEPARATE tt INNER JOIN PACK_SEPARATE_DETAIL ttd on tt.id = ttd.psid  and tt.isaudit = 1 ");
		if(!StringUtil.isEmpty(queryForm.getOrganId())){
			sql.append(" \r\n and tt.ORGANID = ? ");param.put(UUID.randomUUID().toString(), queryForm.getOrganId()); //机构条件
		}
		if(!StringUtil.isEmpty(queryForm.getWarehouseId())){
			sql.append(" \r\n and tt.WAREHOUSEID = ? ");param.put(UUID.randomUUID().toString(), queryForm.getWarehouseId()); //仓库条件
		}
		if(!StringUtil.isEmpty(queryForm.getProductName()) && !StringUtil.isEmpty(queryForm.getPackSizeName())) { //产品条件
			sql.append(" \r\n and ttd.OUTPRODUCTID in (select id from product where productname = ? and specmode= ?) ");param.put(UUID.randomUUID().toString(), queryForm.getProductName());param.put(UUID.randomUUID().toString(), queryForm.getPackSizeName());
		} else if(!StringUtil.isEmpty(queryForm.getProductName()) && StringUtil.isEmpty(queryForm.getPackSizeName())) { //产品条件
			sql.append(" \r\n and ttd.OUTPRODUCTID in (select id from product where productname = ?) ");param.put(UUID.randomUUID().toString(), queryForm.getProductName());
		}
		if(!StringUtil.isEmpty(queryForm.getBeginDate())){
			sql.append(" \r\n and tt.auditdate >=to_date(?,'yyyy-MM-dd hh24:mi:ss') ");param.put(UUID.randomUUID().toString(), queryForm.getBeginDate()); //开始时间条件
		}
		if(!StringUtil.isEmpty(queryForm.getEndDate())){
			sql.append(" \r\n and tt.auditdate < to_date(?,'yyyy-MM-dd hh24:mi:ss') ");param.put(UUID.randomUUID().toString(), queryForm.getEndDate()); //结束时间条件
		}
		sql.append(" \r\n join WAREHOUSE w on tt.warehouseid = w.id and w.USEFLAG = 1  ");
		if(DbUtil.isDealer(users)) {
			sql.append(" \r\n JOIN RULE_USER_WH rw on tt.WAREHOUSEID = rw.warehouse_id ");
			sql.append(" \r\n where rw.user_id = ?");param.put(UUID.randomUUID().toString(), users.getUserid());
		} else {
			sql.append(" \r\n JOIN Organ o on o.id = tt.ORGANID ");
			sql.append(" \r\n where "+DbUtil.getWhereCondition(users, "o"));
		}
		
		
		//增加分包时小包的销售数量
		sql.append(" \r\n UNION ALL ");
		sql.append(" \r\n --增加分包时小包的销售数量");
		sql.append(" \r\n select tt.ORGANID oid,ttd.INPRODUCTID pid ,INQUANTITY quantity,w.id wid  ");
		sql.append(" \r\n from PACK_SEPARATE tt INNER JOIN PACK_SEPARATE_DETAIL ttd on tt.id = ttd.psid  and tt.isaudit = 1 ");
		if(!StringUtil.isEmpty(queryForm.getOrganId())){
			sql.append(" \r\n and tt.ORGANID = ? ");param.put(UUID.randomUUID().toString(), queryForm.getOrganId()); //机构条件
		}
		if(!StringUtil.isEmpty(queryForm.getWarehouseId())){
			sql.append(" \r\n and tt.WAREHOUSEID = ? ");param.put(UUID.randomUUID().toString(), queryForm.getWarehouseId()); //仓库条件
		}
		if(!StringUtil.isEmpty(queryForm.getProductName()) && !StringUtil.isEmpty(queryForm.getPackSizeName())) { //产品条件
			sql.append(" \r\n and ttd.INPRODUCTID in (select id from product where productname = ? and specmode= ?) ");param.put(UUID.randomUUID().toString(), queryForm.getProductName());param.put(UUID.randomUUID().toString(), queryForm.getPackSizeName());
		} else if(!StringUtil.isEmpty(queryForm.getProductName()) && StringUtil.isEmpty(queryForm.getPackSizeName())) { //产品条件
			sql.append(" \r\n and ttd.INPRODUCTID in (select id from product where productname = ?) ");param.put(UUID.randomUUID().toString(), queryForm.getProductName());
		}
		if(!StringUtil.isEmpty(queryForm.getBeginDate())){
			sql.append(" \r\n and tt.auditdate >=to_date(?,'yyyy-MM-dd hh24:mi:ss') ");param.put(UUID.randomUUID().toString(), queryForm.getBeginDate()); //开始时间条件
		}
		if(!StringUtil.isEmpty(queryForm.getEndDate())){
			sql.append(" \r\n and tt.auditdate < to_date(?,'yyyy-MM-dd hh24:mi:ss') ");param.put(UUID.randomUUID().toString(), queryForm.getEndDate()); //结束时间条件
		}
		sql.append(" \r\n join WAREHOUSE w on tt.warehouseid = w.id and w.USEFLAG = 1  ");
		if(DbUtil.isDealer(users)) {
			sql.append(" \r\n JOIN RULE_USER_WH rw on tt.WAREHOUSEID = rw.warehouse_id ");
			sql.append(" \r\n where rw.user_id = ?");param.put(UUID.randomUUID().toString(), users.getUserid());
		} else {
			sql.append(" \r\n JOIN Organ o on o.id = tt.ORGANID ");
			sql.append(" \r\n where "+DbUtil.getWhereCondition(users, "o"));
		}
		//#end modified by ryan.xi at 20150714
		
		//#start modified by ryan at 20150805
		//采购入库-加销售
		sql.append(" \r\n UNION ALL ");
		sql.append(" \r\n --采购入库-加销售");
		sql.append(" \r\n select oia.organid, oida.productid pid, (oida.quantity * f.xquantity) quantity,w.id wid   ");
		sql.append(" \r\n from OTHER_INCOME_ALL oia INNER JOIN OTHER_INCOME_DETAIL_ALL oida on oia.id = oida.oiid and oia.isaudit = 1 and oia.incomesort = 6");
		if(!StringUtil.isEmpty(queryForm.getOrganId())){
			sql.append(" \r\n and oia.organid = ? ");param.put(UUID.randomUUID().toString(), queryForm.getOrganId()); //机构条件
		}
		if(!StringUtil.isEmpty(queryForm.getWarehouseId())){
			sql.append(" \r\n and oia.warehouseid = ? ");param.put(UUID.randomUUID().toString(), queryForm.getWarehouseId()); //仓库条件
		}
		if(!StringUtil.isEmpty(queryForm.getProductName()) && !StringUtil.isEmpty(queryForm.getPackSizeName())) { //产品条件
			sql.append(" \r\n and oida.productid in (select id from product where productname = ? and packsizename= ?) ");param.put(UUID.randomUUID().toString(), queryForm.getProductName());param.put(UUID.randomUUID().toString(), queryForm.getPackSizeName());
		} else if(!StringUtil.isEmpty(queryForm.getProductName()) && StringUtil.isEmpty(queryForm.getPackSizeName())) { //产品条件
			sql.append(" \r\n and oida.productid in (select id from product where productname = ?) ");param.put(UUID.randomUUID().toString(), queryForm.getProductName());
		}
		if(!StringUtil.isEmpty(queryForm.getBeginDate())){
			sql.append(" \r\n and oia.auditdate >=to_date(?,'yyyy-MM-dd hh24:mi:ss') ");param.put(UUID.randomUUID().toString(), queryForm.getBeginDate()); //开始时间条件
		}
		if(!StringUtil.isEmpty(queryForm.getEndDate())){
			sql.append(" \r\n and oia.auditdate < to_date(?,'yyyy-MM-dd hh24:mi:ss') ");param.put(UUID.randomUUID().toString(), queryForm.getEndDate()); //结束时间条件
		}
		sql.append(" \r\n INNER JOIN ORGAN o on oia.organid= o.id and o.organtype = 2 and  organmodel = 1 ");
		sql.append(" \r\n join WAREHOUSE w on oia.warehouseid = w.id and w.USEFLAG = 1  ");
		sql.append(" \r\n LEFT JOIN F_UNIT f on oida.productid = f.productid and oida.unitid = f.funitid  "); 
		if(DbUtil.isDealer(users)) {
			sql.append(" \r\n INNER JOIN RULE_USER_WH rw on oia.warehouseid = rw.warehouse_id ");
			sql.append(" \r\n where rw.user_id = ?");param.put(UUID.randomUUID().toString(), users.getUserid()); 
		} else {
			sql.append(" \r\n where "+DbUtil.getWhereCondition(users, "o"));
		}
		
		//#end modified by ryan at 20150805
		
		
		
		sql.append(" \r\n ) GROUP BY oid,pid,wid ");
		sql.append(" \r\n ) sales on o.id = sales.oid and p.id = sales.pid and sales.wid = w.id  ");
		// 退回工厂
		sql.append(" \r\n --退回工厂");
		sql.append(" \r\n LEFT JOIN ( ");
		sql.append(" \r\n select max(tt.oid) oid,max(ttd.productid) pid ,sum(ttd.realquantity * f.xquantity) quantity,w.id wid  ");
		sql.append(" \r\n from TAKE_TICKET tt INNER JOIN TAKE_TICKET_DETAIL ttd on tt.id = ttd.ttid  and tt.isaudit = 1  and tt.bsort = 7 and tt.billno like 'PW%' ");
		if(!StringUtil.isEmpty(queryForm.getOrganId())){
			sql.append(" \r\n and tt.oid = ? ");param.put(UUID.randomUUID().toString(), queryForm.getOrganId()); //机构条件
		}
		if(!StringUtil.isEmpty(queryForm.getWarehouseId())){
			sql.append(" \r\n and tt.WAREHOUSEID = ? ");param.put(UUID.randomUUID().toString(), queryForm.getWarehouseId()); //仓库条件
		}
		if(!StringUtil.isEmpty(queryForm.getProductName()) && !StringUtil.isEmpty(queryForm.getPackSizeName())) { //产品条件
			sql.append(" \r\n and ttd.productid in (select id from product where productname = ? and specmode=?) ");param.put(UUID.randomUUID().toString(), queryForm.getProductName());param.put(UUID.randomUUID().toString(), queryForm.getPackSizeName());
		} else if(!StringUtil.isEmpty(queryForm.getProductName()) && StringUtil.isEmpty(queryForm.getPackSizeName())) { //产品条件
			sql.append(" \r\n and ttd.productid in (select id from product where productname = ?) ");param.put(UUID.randomUUID().toString(), queryForm.getProductName());
		}
		if(!StringUtil.isEmpty(queryForm.getBeginDate())){
			sql.append(" \r\n and tt.auditdate >=to_date(?,'yyyy-MM-dd hh24:mi:ss') ");param.put(UUID.randomUUID().toString(), queryForm.getBeginDate()); //开始时间条件
		}
		if(!StringUtil.isEmpty(queryForm.getEndDate())){
			sql.append(" \r\n and tt.auditdate < to_date(?,'yyyy-MM-dd hh24:mi:ss') ");param.put(UUID.randomUUID().toString(), queryForm.getEndDate()); //结束时间条件
		}
		/*
		//按退回工厂单创建日期-20150429
		sql.append(" \r\n join ORGAN_WITHDRAW ow on tt.billno = ow.id ");
		if(!StringUtil.isEmpty(queryForm.getBeginDate())){
			sql.append(" \r\n and ow.makedate >=to_date('" + queryForm.getBeginDate() + "','yyyy-MM-dd hh24:mi:ss') "); //开始时间条件
		}
		if(!StringUtil.isEmpty(queryForm.getEndDate())){
			sql.append(" \r\n and ow.makedate < to_date('" + queryForm.getEndDate() + "','yyyy-MM-dd hh24:mi:ss') "); //结束时间条件
		}*/
		sql.append(" \r\n INNER JOIN ORGAN o on tt.oid = o.id and o.organtype = 2 and  organmodel = 1 "); //出库仓库为PD
		sql.append(" \r\n join WAREHOUSE w on tt.warehouseid = w.id and w.USEFLAG = 1  ");
		sql.append(" \r\n LEFT JOIN F_UNIT f on ttd.productid = f.productid and ttd.unitid = f.funitid ");
		if(DbUtil.isDealer(users)) {
			sql.append(" \r\n LEFT JOIN RULE_USER_WH rw on tt.warehouseid = rw.warehouse_id  ");
			sql.append(" \r\n where rw.user_id = ?");param.put(UUID.randomUUID().toString(), users.getUserid());
		} else {
			sql.append(" \r\n where "+DbUtil.getWhereCondition(users, "o"));
		}
		
		sql.append(" \r\n GROUP BY tt.oid,ttd.productid,w.id  ");
		sql.append(" \r\n ) pw on  o.id = pw.oid and p.id = pw.pid and pw.wid = w.id ");
		// 消耗
		sql.append(" \r\n LEFT JOIN ( ");
		sql.append(" \r\n --实际出库数量算消耗");
		sql.append(" \r\n select max(oid) oid,max(pid) pid,sum(quantity) quantity, wid   from ( ");
		//#start modified by ryan.xi 20150518 按实际出库数量算消耗
		sql.append(" \r\n  select tt.oid, ttd.productid pid, (ttd.realquantity * f.xquantity) quantity,w.id wid  ");
		//#end
		sql.append(" \r\n from TAKE_TICKET tt INNER JOIN TAKE_TICKET_DETAIL ttd on tt.id = ttd.ttid  and tt.isaudit = 1  and tt.bsort = 1 ");
		if(!StringUtil.isEmpty(queryForm.getOrganId())){
			sql.append(" \r\n and tt.oid = ? ");param.put(UUID.randomUUID().toString(), queryForm.getOrganId()); //机构条件
		}
		if(!StringUtil.isEmpty(queryForm.getWarehouseId())){
			sql.append(" \r\n and tt.WAREHOUSEID = ? ");param.put(UUID.randomUUID().toString(), queryForm.getWarehouseId()); //仓库条件
		}
		if(!StringUtil.isEmpty(queryForm.getProductName()) && !StringUtil.isEmpty(queryForm.getPackSizeName())) { //产品条件
			sql.append(" \r\n and ttd.productid in (select id from product where productname = ? and specmode=?) ");param.put(UUID.randomUUID().toString(), queryForm.getProductName());param.put(UUID.randomUUID().toString(), queryForm.getPackSizeName());
		} else if(!StringUtil.isEmpty(queryForm.getProductName()) && StringUtil.isEmpty(queryForm.getPackSizeName())) { //产品条件
			sql.append(" \r\n and ttd.productid in (select id from product where productname = ?) ");param.put(UUID.randomUUID().toString(), queryForm.getProductName());
		}
		if(!StringUtil.isEmpty(queryForm.getBeginDate())){
			sql.append(" \r\n and tt.auditdate >=to_date(?,'yyyy-MM-dd hh24:mi:ss') ");param.put(UUID.randomUUID().toString(), queryForm.getBeginDate()); //开始时间条件
		}
		if(!StringUtil.isEmpty(queryForm.getEndDate())){
			sql.append(" \r\n and tt.auditdate < to_date(?,'yyyy-MM-dd hh24:mi:ss') ");param.put(UUID.randomUUID().toString(), queryForm.getEndDate()); //结束时间条件
		}
		//过滤拒签的单据
		sql.append(" \r\n INNER JOIN STOCK_ALTER_MOVE sam on sam.id = tt.billno and sam.isblankout = 0");
		sql.append(" \r\n INNER JOIN ORGAN o on tt.oid = o.id and o.organtype = 2 and  organmodel = 1 "); //出库仓库为PD
		sql.append(" \r\n join WAREHOUSE w on tt.warehouseid = w.id and w.USEFLAG = 1  ");
		sql.append(" \r\n LEFT JOIN F_UNIT f on ttd.productid = f.productid and ttd.unitid = f.funitid ");
		if(DbUtil.isDealer(users)) {
			sql.append(" \r\n JOIN RULE_USER_WH rw on tt.warehouseid = rw.warehouse_id  ");
			//#start modified by ryan.xi 20150518 过滤消耗为0的单据
			sql.append(" \r\n where rw.user_id = ?");param.put(UUID.randomUUID().toString(), users.getUserid());
		} else {
			sql.append(" \r\n where "+DbUtil.getWhereCondition(users, "o"));
		}
		sql.append(" and (ttd.realquantity * f.xquantity) <> 0 ");
		//#end
		sql.append(" \r\n UNION ALL ");
		
		
		//#start modified by ryan.xi at 20150714
		//分包时的损耗记到消耗
		sql.append(" \r\n --分包时的损耗记到消耗");
		sql.append(" \r\n select tt.ORGANID, ttd.OUTPRODUCTID pid, ttd.wastage quantity,w.id wid  ");
		sql.append(" \r\n from PACK_SEPARATE tt INNER JOIN PACK_SEPARATE_DETAIL ttd on tt.id = ttd.psid  and tt.isaudit = 1 ");
		if(!StringUtil.isEmpty(queryForm.getOrganId())){
			sql.append(" \r\n and tt.ORGANID = ? ");param.put(UUID.randomUUID().toString(), queryForm.getOrganId()); //机构条件
		}
		if(!StringUtil.isEmpty(queryForm.getWarehouseId())){
			sql.append(" \r\n and tt.WAREHOUSEID = ? ");param.put(UUID.randomUUID().toString(), queryForm.getWarehouseId()); //仓库条件
		}
		if(!StringUtil.isEmpty(queryForm.getProductName()) && !StringUtil.isEmpty(queryForm.getPackSizeName())) { //产品条件
			sql.append(" \r\n and ttd.OUTPRODUCTID in (select id from product where productname = ? and specmode= ?) ");param.put(UUID.randomUUID().toString(), queryForm.getProductName());param.put(UUID.randomUUID().toString(), queryForm.getPackSizeName());
		} else if(!StringUtil.isEmpty(queryForm.getProductName()) && StringUtil.isEmpty(queryForm.getPackSizeName())) { //产品条件
			sql.append(" \r\n and ttd.OUTPRODUCTID in (select id from product where productname = ?) ");param.put(UUID.randomUUID().toString(), queryForm.getProductName());
		}
		if(!StringUtil.isEmpty(queryForm.getBeginDate())){
			sql.append(" \r\n and tt.auditdate >=to_date(?,'yyyy-MM-dd hh24:mi:ss') ");param.put(UUID.randomUUID().toString(), queryForm.getBeginDate()); //开始时间条件
		}
		if(!StringUtil.isEmpty(queryForm.getEndDate())){
			sql.append(" \r\n and tt.auditdate < to_date(?,'yyyy-MM-dd hh24:mi:ss') ");param.put(UUID.randomUUID().toString(), queryForm.getEndDate()); //结束时间条件
		}
		sql.append(" \r\n join WAREHOUSE w on tt.warehouseid = w.id and w.USEFLAG = 1  ");
		if(DbUtil.isDealer(users)) {
			sql.append(" \r\n JOIN RULE_USER_WH rw on tt.WAREHOUSEID = rw.warehouse_id ");
			sql.append(" \r\n where rw.user_id = ?");param.put(UUID.randomUUID().toString(), users.getUserid());
		} else {
			sql.append(" \r\n JOIN Organ o on o.id = tt.ORGANID ");
			sql.append(" \r\n where "+DbUtil.getWhereCondition(users, "o"));
		}
		sql.append(" and ttd.wastage <> 0 ");
		sql.append(" \r\n UNION ALL ");
		//#end modified by ryan.xi at 20150714
		
		
		//工厂发货给PD, 收货差异计入PD消耗
		sql.append(" \r\n --工厂发货给PD, 收货差异计入PD消耗");
		sql.append(" \r\n select tt.inoid, ttd.productid pid, ((samd.quantity-samd.receivequantity) * f.xquantity) quantity,w.id wid  ");
		sql.append(" \r\n from TAKE_TICKET tt INNER JOIN TAKE_TICKET_DETAIL ttd on tt.id = ttd.ttid  and tt.isaudit = 1  and tt.bsort = 1  ");
		if(!StringUtil.isEmpty(queryForm.getOrganId())){
			sql.append(" \r\n and tt.inoid = ? ");param.put(UUID.randomUUID().toString(), queryForm.getOrganId()); //机构条件
		}
		if(!StringUtil.isEmpty(queryForm.getWarehouseId())){
			sql.append(" \r\n and tt.inwarehouseid = ? ");param.put(UUID.randomUUID().toString(), queryForm.getWarehouseId()); //仓库条件
		}
		if(!StringUtil.isEmpty(queryForm.getProductName()) && !StringUtil.isEmpty(queryForm.getPackSizeName())) { //产品条件
			sql.append(" \r\n and ttd.productid in (select id from product where productname = ? and specmode=?) ");param.put(UUID.randomUUID().toString(), queryForm.getProductName());param.put(UUID.randomUUID().toString(), queryForm.getPackSizeName());
		} else if(!StringUtil.isEmpty(queryForm.getProductName()) && StringUtil.isEmpty(queryForm.getPackSizeName())) { //产品条件
			sql.append(" \r\n and ttd.productid in (select id from product where productname = ?) ");param.put(UUID.randomUUID().toString(), queryForm.getProductName());
		}
		sql.append(" \r\n join STOCK_ALTER_MOVE sam on sam.id=tt.billno and sam.iscomplete =1");
		if(!StringUtil.isEmpty(queryForm.getBeginDate())){
			sql.append(" \r\n and sam.movedate >=to_date(?,'yyyy-MM-dd hh24:mi:ss') ");param.put(UUID.randomUUID().toString(), queryForm.getBeginDate()); //开始时间条件
		}
		if(!StringUtil.isEmpty(queryForm.getEndDate())){
			sql.append(" \r\n and sam.movedate < to_date(?,'yyyy-MM-dd hh24:mi:ss') ");param.put(UUID.randomUUID().toString(), queryForm.getEndDate()); //结束时间条件
		}
		sql.append(" \r\n join STOCK_ALTER_MOVE_DETAIL samd on samd.samid = sam.id and samd.productid=ttd.productid");
		sql.append(" \r\n INNER JOIN ORGAN o on tt.oid = o.id and o.organtype = 1 ");
		sql.append(" \r\n join WAREHOUSE w on tt.inwarehouseid = w.id and w.USEFLAG = 1  ");
		sql.append(" \r\n LEFT JOIN F_UNIT f on ttd.productid = f.productid and ttd.unitid = f.funitid ");
		sql.append(" \r\n LEFT JOIN ORGAN ino on tt.inoid = ino.id  and ino.organtype = 2 and ino.organmodel = 1 ");
		if(DbUtil.isDealer(users)) {
			sql.append(" \r\n LEFT JOIN RULE_USER_WH rw on tt.inwarehouseid = rw.warehouse_id ");
			sql.append(" \r\n where rw.user_id = ?");param.put(UUID.randomUUID().toString(), users.getUserid());
		} else {
			sql.append(" \r\n where "+DbUtil.getWhereCondition(users, "ino"));
		}
		sql.append(" and (samd.quantity-samd.receivequantity) > 0 ");
		
		
		
		
		//#start modified by ryan at 20150805
		//其他入库-减消耗-20150429
		sql.append(" \r\n --其他入库-减消耗");
		sql.append(" \r\n UNION ALL ");
		sql.append(" \r\n select oia.organid, oida.productid pid, -(oida.quantity * f.xquantity) quantity,w.id wid   ");
		sql.append(" \r\n from OTHER_INCOME_ALL oia INNER JOIN OTHER_INCOME_DETAIL_ALL oida on oia.id = oida.oiid and oia.isaudit = 1 and oia.incomesort <> 6 ");
		if(!StringUtil.isEmpty(queryForm.getOrganId())){
			sql.append(" \r\n and oia.organid = ? ");param.put(UUID.randomUUID().toString(), queryForm.getOrganId()); //机构条件
		} 
		if(!StringUtil.isEmpty(queryForm.getWarehouseId())){
			sql.append(" \r\n and oia.warehouseid = ? ");param.put(UUID.randomUUID().toString(), queryForm.getWarehouseId()); //仓库条件
		}
		if(!StringUtil.isEmpty(queryForm.getProductName()) && !StringUtil.isEmpty(queryForm.getPackSizeName())) { //产品条件
			sql.append(" \r\n and oida.productid in (select id from product where productname = ? and packsizename= ?) ");param.put(UUID.randomUUID().toString(), queryForm.getProductName());param.put(UUID.randomUUID().toString(), queryForm.getPackSizeName());
		} else if(!StringUtil.isEmpty(queryForm.getProductName()) && StringUtil.isEmpty(queryForm.getPackSizeName())) { //产品条件
			sql.append(" \r\n and oida.productid in (select id from product where productname = ?) ");param.put(UUID.randomUUID().toString(), queryForm.getProductName());
		}
		if(!StringUtil.isEmpty(queryForm.getBeginDate())){
			sql.append(" \r\n and oia.auditdate >=to_date(?,'yyyy-MM-dd hh24:mi:ss') ");param.put(UUID.randomUUID().toString(), queryForm.getBeginDate()); //开始时间条件
		}
		if(!StringUtil.isEmpty(queryForm.getEndDate())){
			sql.append(" \r\n and oia.auditdate < to_date(?,'yyyy-MM-dd hh24:mi:ss') ");param.put(UUID.randomUUID().toString(), queryForm.getEndDate()); //结束时间条件
		}
		sql.append(" \r\n INNER JOIN ORGAN o on oia.organid= o.id and o.organtype = 2 and  organmodel = 1 ");
		sql.append(" \r\n join WAREHOUSE w on oia.warehouseid = w.id and w.USEFLAG = 1  ");
		sql.append(" \r\n LEFT JOIN F_UNIT f on oida.productid = f.productid and oida.unitid = f.funitid  "); 
		if(DbUtil.isDealer(users)) {
			sql.append(" \r\n INNER JOIN RULE_USER_WH rw on oia.warehouseid = rw.warehouse_id ");
			sql.append(" \r\n where rw.user_id = ?");param.put(UUID.randomUUID().toString(), users.getUserid()); 
		} else {
			sql.append(" \r\n where "+DbUtil.getWhereCondition(users, "o"));
		}
		
		
		//其他出库-加消耗-20150429
		sql.append(" \r\n UNION ALL ");
		sql.append(" \r\n --其他出库-加消耗");
		sql.append(" \r\n select oia.organid, oida.productid pid, (oida.quantity * f.xquantity) quantity,w.id wid   ");
		sql.append(" \r\n from OTHER_SHIPMENT_BILL_ALL oia INNER JOIN OTHER_SHIPMENT_BILL_D_ALL oida on oia.id = oida.osid and oia.isaudit = 1 ");
		if(!StringUtil.isEmpty(queryForm.getOrganId())){
			sql.append(" \r\n and oia.organid = ? ");param.put(UUID.randomUUID().toString(), queryForm.getOrganId()); //机构条件
		}
		if(!StringUtil.isEmpty(queryForm.getWarehouseId())){
			sql.append(" \r\n and oia.warehouseid = ? ");param.put(UUID.randomUUID().toString(), queryForm.getWarehouseId()); //仓库条件
		}
		if(!StringUtil.isEmpty(queryForm.getProductName()) && !StringUtil.isEmpty(queryForm.getPackSizeName())) { //产品条件
			sql.append(" \r\n and oida.productid in (select id from product where productname = ? and packsizename= ?) ");param.put(UUID.randomUUID().toString(), queryForm.getProductName());param.put(UUID.randomUUID().toString(), queryForm.getPackSizeName());
		} else if(!StringUtil.isEmpty(queryForm.getProductName()) && StringUtil.isEmpty(queryForm.getPackSizeName())) { //产品条件
			sql.append(" \r\n and oida.productid in (select id from product where productname = ?) ");param.put(UUID.randomUUID().toString(), queryForm.getProductName());
		}
		if(!StringUtil.isEmpty(queryForm.getBeginDate())){
			sql.append(" \r\n and oia.auditdate >=to_date(?,'yyyy-MM-dd hh24:mi:ss') ");param.put(UUID.randomUUID().toString(), queryForm.getBeginDate()); //开始时间条件
		}
		if(!StringUtil.isEmpty(queryForm.getEndDate())){
			sql.append(" \r\n and oia.auditdate < to_date(?,'yyyy-MM-dd hh24:mi:ss') ");param.put(UUID.randomUUID().toString(), queryForm.getEndDate()); //结束时间条件
		}
		sql.append(" \r\n INNER JOIN ORGAN o on oia.organid= o.id and o.organtype = 2 and  organmodel = 1 ");
		sql.append(" \r\n join WAREHOUSE w on oia.warehouseid = w.id and w.USEFLAG = 1  ");
		sql.append(" \r\n LEFT JOIN F_UNIT f on oida.productid = f.productid and oida.unitid = f.funitid  "); 
		if(DbUtil.isDealer(users)) {
			sql.append(" \r\n INNER JOIN RULE_USER_WH rw on oia.warehouseid = rw.warehouse_id ");
			sql.append(" \r\n where rw.user_id = ?");param.put(UUID.randomUUID().toString(), users.getUserid()); 
		} else { 
			sql.append(" \r\n where "+DbUtil.getWhereCondition(users, "o"));
		}
		//#end modified by ryan at 20150805
			
		sql.append(" \r\n ) notmatchconsume  ");
		
		sql.append(" \r\n GROUP BY notmatchconsume.oid,notmatchconsume.pid,notmatchconsume.wid  ");
		sql.append(" \r\n )  consume on o.id = consume.oid and p.id = consume.pid and w.id = consume.wid  ");
		// 退货
		sql.append(" \r\n --退货");
		sql.append(" \r\n LEFT JOIN ( ");
		sql.append(" \r\n select max(tt.inoid) oid,max(ttd.productid) pid,sum(ttd.realquantity * f.xquantity) quantity,w.id wid   ");
		sql.append(" \r\n from TAKE_TICKET tt INNER JOIN TAKE_TICKET_DETAIL ttd on tt.id = ttd.ttid  and tt.isaudit = 1  and tt.bsort = 7 and tt.billno like 'OW%' ");
		if(!StringUtil.isEmpty(queryForm.getOrganId())){
			sql.append(" \r\n and tt.inoid = ? ");param.put(UUID.randomUUID().toString(), queryForm.getOrganId()); //机构条件
		}
		if(!StringUtil.isEmpty(queryForm.getWarehouseId())){
			sql.append(" \r\n and tt.inwarehouseid = ? ");param.put(UUID.randomUUID().toString(), queryForm.getWarehouseId()); //仓库条件
		}
		if(!StringUtil.isEmpty(queryForm.getProductName()) && !StringUtil.isEmpty(queryForm.getPackSizeName())) { //产品条件
			sql.append(" \r\n and ttd.productid in (select id from product where productname = ? and specmode=?) ");param.put(UUID.randomUUID().toString(), queryForm.getProductName());param.put(UUID.randomUUID().toString(), queryForm.getPackSizeName());
		} else if(!StringUtil.isEmpty(queryForm.getProductName()) && StringUtil.isEmpty(queryForm.getPackSizeName())) { //产品条件
			sql.append(" \r\n and ttd.productid in (select id from product where productname = ?) ");param.put(UUID.randomUUID().toString(), queryForm.getProductName());
		}
		if(!StringUtil.isEmpty(queryForm.getBeginDate())){
			sql.append(" \r\n and tt.auditdate >=to_date(?,'yyyy-MM-dd hh24:mi:ss') ");param.put(UUID.randomUUID().toString(), queryForm.getBeginDate()); //开始时间条件
		}
		if(!StringUtil.isEmpty(queryForm.getEndDate())){
			sql.append(" \r\n and tt.auditdate < to_date(?,'yyyy-MM-dd hh24:mi:ss') ");param.put(UUID.randomUUID().toString(), queryForm.getEndDate()); //结束时间条件
		}
		sql.append(" \r\n INNER JOIN ORGAN o on tt.inoid = o.id and o.organtype = 2 and  organmodel = 1 "); // 入库仓库为PD
		sql.append(" \r\n join WAREHOUSE w on tt.inwarehouseid = w.id and w.USEFLAG = 1  ");
		sql.append(" \r\n LEFT JOIN F_UNIT f on ttd.productid = f.productid and ttd.unitid = f.funitid ");
		
		if(DbUtil.isDealer(users)) {
			sql.append(" \r\n LEFT JOIN RULE_USER_WH rw on tt.inwarehouseid = rw.warehouse_id ");
			sql.append(" \r\n where rw.user_id = ?");param.put(UUID.randomUUID().toString(), users.getUserid());
		} else {
			sql.append(" \r\n where "+DbUtil.getWhereCondition(users, "o"));
		}
		
		sql.append(" \r\n GROUP BY tt.inoid,ttd.productid,w.id  ");
		sql.append(" \r\n ) ow on o.id = ow.oid and p.id = ow.pid and w.id = ow.wid  ");
//		sql.append(" \r\n LEFT JOIN RULE_USER_WH rw on w.id = rw.warehouse_id  ");
//		sql.append(" \r\n where rw.user_id = ?");param.put(UUID.randomUUID().toString(), users.getUserid());
		sql.append(" \r\n where  o.organtype = 2 and  o.organmodel = 1 and o.ISREPEAL = 0 "); // 机构类型为PD
		
		if(OrganType.Dealer.getValue().equals(users.getOrganType())) {
			sql.append(" \r\n and w.id in ( select warehouse_id from RULE_USER_WH where user_id = ? ) ");param.put(UUID.randomUUID().toString(), users.getUserid());
		} else { 
			sql.append(" \r\n and "+DbUtil.getWhereCondition(users, "o"));
		}
		
		if(!StringUtil.isEmpty(queryForm.getOrganId())){
			sql.append(" \r\n and o.id = ? ");param.put(UUID.randomUUID().toString(), queryForm.getOrganId()); //机构条件
		}
		if(!StringUtil.isEmpty(queryForm.getProductName()) && !StringUtil.isEmpty(queryForm.getPackSizeName())) { //产品条件
			sql.append(" \r\n and p.id in (select id from product where productname = ? and specmode= ?) ");param.put(UUID.randomUUID().toString(), queryForm.getProductName());param.put(UUID.randomUUID().toString(), queryForm.getPackSizeName());
		} else if(!StringUtil.isEmpty(queryForm.getProductName()) && StringUtil.isEmpty(queryForm.getPackSizeName())) { //产品条件
			sql.append(" \r\n and p.id in (select id from product where productname = ?) ");param.put(UUID.randomUUID().toString(), queryForm.getProductName());
		}
		//大区条件
		if(!StringUtil.isEmpty(queryForm.getRegion())){
			sql.append(" \r\n and r.regioncode = ? "); param.put(UUID.randomUUID().toString(), queryForm.getRegion());
		}
		sql.append(" \r\n  and p.USEFLAG = 1 ");
		System.out.println(sql.toString());
		List<Map> list =  new ArrayList<Map>();
		if(request == null && pageSize == 0){
			list =  EntityManager.jdbcquery(sql.toString(), param);
		}else {
			list =  PageQuery.jdbcSqlserverQuery(request, "organId,mCode", sql.toString(), pageSize, param);
		}
		if(list != null && list.size()>0){
			Map<String,FUnit>  funitMap = appFUnit.getAllMap(); //获取所有的单位换算信息
			Map<Integer, String> countUnitMap = appBr.getBaseResourceMap("CountUnit");
			for(Map map : list){
				SalesConsumeReportForm scForm = new SalesConsumeReportForm();
				//将Map中对应的值赋值给实例
				MapUtil.mapToObject(map, scForm);
				//报告日期主当前日期
				scForm.setReportDate(new Timestamp(System.currentTimeMillis()));
				
				if(StringUtil.isEmpty(queryForm.getCountByUnit())) {
					if(checkRate(scForm, funitMap,Constants.DEFAULT_UNIT_ID)){
						//将数量换算为件
						Double salesQuantity = changeUnit(scForm.getProductId(),scForm.getUnitId(),scForm.getSalesQuantity(),Constants.DEFAULT_UNIT_ID,funitMap);
						scForm.setSalesQuantity(salesQuantity);
						Double pwQuantity = changeUnit(scForm.getProductId(),scForm.getUnitId(),scForm.getPwQuantity(),Constants.DEFAULT_UNIT_ID,funitMap);
						scForm.setPwQuantity(pwQuantity);
						Double consumeQuantity = changeUnit(scForm.getProductId(),scForm.getUnitId(),scForm.getConsumeQuantity(),Constants.DEFAULT_UNIT_ID,funitMap);
						scForm.setConsumeQuantity(consumeQuantity);
						Double owQuantity = changeUnit(scForm.getProductId(),scForm.getUnitId(),scForm.getOwQuantity(),Constants.DEFAULT_UNIT_ID,funitMap);
						scForm.setOwQuantity(owQuantity);
						//单位默认显示为件
						scForm.setUnitId(Constants.DEFAULT_UNIT_ID);
					}
				} else {
					Product product = appProduct.loadProductById(scForm.getProductId());
					if(product != null) {
						//检查单位是否需要转化,是否可以正常转化,如不能则不转化
						if(scForm.getUnitId() != product.getSunit() && checkRate(scForm, funitMap,product.getSunit())){
							Double salesQuantity = changeUnit(scForm.getProductId(),scForm.getUnitId(),scForm.getSalesQuantity(),product.getSunit(),funitMap);
							scForm.setSalesQuantity(salesQuantity);
							Double pwQuantity = changeUnit(scForm.getProductId(),scForm.getUnitId(),scForm.getPwQuantity(),product.getSunit(),funitMap);
							scForm.setPwQuantity(pwQuantity);
							Double consumeQuantity = changeUnit(scForm.getProductId(),scForm.getUnitId(),scForm.getConsumeQuantity(),product.getSunit(),funitMap);
							scForm.setConsumeQuantity(consumeQuantity);
							Double owQuantity = changeUnit(scForm.getProductId(),scForm.getUnitId(),scForm.getOwQuantity(),product.getSunit(),funitMap);
							scForm.setOwQuantity(owQuantity);
							scForm.setUnitId(product.getSunit());
						}
						
						//转换成计量单位
						if(product.getBoxquantity() != null && product.getBoxquantity() != 0d) {
							Double salesQuantity = ArithDouble.mul(scForm.getSalesQuantity(), product.getBoxquantity());
							scForm.setSalesQuantity(salesQuantity);
							Double pwQuantity = ArithDouble.mul(scForm.getPwQuantity(), product.getBoxquantity());
							scForm.setPwQuantity(pwQuantity);
							Double consumeQuantity = ArithDouble.mul(scForm.getConsumeQuantity(), product.getBoxquantity());
							scForm.setConsumeQuantity(consumeQuantity);
							Double owQuantity = ArithDouble.mul(scForm.getOwQuantity(), product.getBoxquantity());
							scForm.setOwQuantity(owQuantity);
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
	
	// 根据条件查询 
	public List<SalesConsumeReportForm> queryReport(HttpServletRequest request, int pageSize,SalesConsumeReportForm queryForm,UsersBean users) throws Exception{
		//查看是否有统计过销售消耗历史数据
		BaseResource sacBr = appBr.getBaseResourceValue("ConsumeDetailDate", 1);
		if(sacBr == null) {//无历史数据按单据统计
			return queryReport2(request, pageSize, queryForm, users);
		} else {
			Date sacDate = DateUtil.StringToDate(sacBr.getTagsubvalue());
			Date startDate = DateUtil.StringToDate(queryForm.getBeginDate());
			Date endDate = DateUtil.StringToDate(queryForm.getEndDate());
			if(sacDate.before(startDate)) {
				//历史数据在查询起始日期之前,则按单据实时统计
				return queryReport2(request, pageSize, queryForm, users);
			} else if(sacDate.before(endDate)) {
				//历史数据在查询起始日期之后,结束日期之前,则按历史数据结合单据实时统计
				queryForm.setHistoryDate(sacBr.getTagsubvalue());
				return queryReportFromHistoryAndBill(request, pageSize, queryForm, users);
			} else {
				return queryReportFromHistory(request, pageSize, queryForm, users);
			}
		}
	}
	
	private List<SalesConsumeReportForm> queryReportFromHistory(
			HttpServletRequest request, int pageSize,
			SalesConsumeReportForm queryForm, UsersBean users) throws Exception { 
		Map<String, Object> param = new LinkedHashMap<String, Object>();
		//对于结束日期增加一天
		if(!StringUtil.isEmpty(queryForm.getEndDate())){
			String endDateStr = queryForm.getEndDate();
			Date endDate = Dateutil.StringToDate(endDateStr);
			endDate = Dateutil.addDay2Date(1, endDate);
			queryForm.setEndDate(Dateutil.formatDate(endDate));
		}
		List<SalesConsumeReportForm> resultList = new ArrayList<SalesConsumeReportForm>();
		StringBuffer sql = new StringBuffer();
		
		sql.append(" \r\n select ");
		sql.append(" \r\n  r.sortname regionName");
		sql.append(" \r\n ,o.province provinceId");
		sql.append(" \r\n ,ca.areaname province");
		sql.append(" \r\n ,o.id organId ");
		sql.append(" \r\n ,o.oecode oecode ");
		sql.append(" \r\n ,o.organname organName");
		sql.append(" \r\n ,pstr.sortname psName");
		sql.append(" \r\n ,p.id productId ");
		sql.append(" \r\n ,p.mcode mCode ");
		sql.append(" \r\n ,p.productname productName ");
		sql.append(" \r\n ,p.productnameen productNameen ");
		sql.append(" \r\n ,p.matericalchdes matericalChDes");
		sql.append(" \r\n ,p.matericalendes matericalEnDes");
		sql.append(" \r\n ,p.sunit unitId");
		sql.append(" \r\n ,p.specmode packSizeName");
		sql.append(" \r\n ,p.packsizenameen packSizeNameEn");
		sql.append(" \r\n ,temp.salesQuantity");
		sql.append(" \r\n ,temp.pwQuantity");
		sql.append(" \r\n ,temp.consumeQuantity");
		sql.append(" \r\n ,temp.owQuantity ");
		sql.append(" \r\n ,w.WAREHOUSENAME ");
		sql.append(" \r\n from (");
		sql.append(" \r\n select organid,productid,sum(salesQuantity) salesQuantity,sum(pwQuantity) pwQuantity,sum(consumeQuantity) consumeQuantity,sum(owQuantity) owQuantity, WAREHOUSEID from (");
		sql.append(" \r\n select organid,productid,case when billNo like 'PW%' then 0 else salesquantity end salesQuantity,");
		sql.append(" \r\n case when billNo like 'PW%' then -salesquantity else 0 end pwQuantity,0 consumeQuantity,0 owQuantity,WAREHOUSEID");
		sql.append(" \r\n FROM SALES_DETAIL_HISTORY where 1=1");
		if(!StringUtil.isEmpty(queryForm.getBeginDate())){
			sql.append(" \r\n and makedate >=to_date(?,'yyyy-MM-dd hh24:mi:ss') ");param.put(UUID.randomUUID().toString(), queryForm.getBeginDate()); //开始时间条件
		}
		if(!StringUtil.isEmpty(queryForm.getEndDate())){
			sql.append(" \r\n and makedate < to_date(?,'yyyy-MM-dd hh24:mi:ss') ");param.put(UUID.randomUUID().toString(), queryForm.getEndDate()); //结束时间条件
		}
		if(!StringUtil.isEmpty(queryForm.getOrganId())){
			sql.append(" \r\n and organid = ? ");param.put(UUID.randomUUID().toString(), queryForm.getOrganId()); //机构条件
		}
		if(!StringUtil.isEmpty(queryForm.getWarehouseId())){
			sql.append(" \r\n and WAREHOUSEID = ? ");param.put(UUID.randomUUID().toString(), queryForm.getWarehouseId()); //仓库条件
		}
		sql.append(" \r\n UNION ALL");
		sql.append(" \r\n select outorganid,productid,0,0,case when billNo like 'OW%' then 0 else consumequantity end,");
		sql.append(" \r\n case when billNo like 'OW%' then -consumequantity else 0 end,OUTWAREHOUSEID");
		sql.append(" \r\n from CONSUME_DETAIL_HISTORY where 1=1");
		if(!StringUtil.isEmpty(queryForm.getBeginDate())){
			sql.append(" \r\n and makedate >=to_date(?,'yyyy-MM-dd hh24:mi:ss') ");param.put(UUID.randomUUID().toString(), queryForm.getBeginDate()); //开始时间条件
		}
		if(!StringUtil.isEmpty(queryForm.getEndDate())){
			sql.append(" \r\n and makedate < to_date(?,'yyyy-MM-dd hh24:mi:ss') ");param.put(UUID.randomUUID().toString(), queryForm.getEndDate()); //结束时间条件
		}
		if(!StringUtil.isEmpty(queryForm.getOrganId())){
			sql.append(" \r\n and outorganid = ? ");param.put(UUID.randomUUID().toString(), queryForm.getOrganId()); //机构条件
		}
		if(!StringUtil.isEmpty(queryForm.getWarehouseId())){
			sql.append(" \r\n and OUTWAREHOUSEID = ? ");param.put(UUID.randomUUID().toString(), queryForm.getWarehouseId()); //仓库条件
		}
		sql.append(" \r\n ) temp0 ");
		sql.append(" \r\n GROUP BY organid,productid,WAREHOUSEID ");
		sql.append(" \r\n ) temp ");
		sql.append(" \r\n join organ o on o.id = temp.organid and o.isrepeal=0 ");
		sql.append(" \r\n join PRODUCT p on p.id = temp.productid and p.useflag=1");
		//产品规格条件
		if(!StringUtil.isEmpty(queryForm.getProductName()) && !StringUtil.isEmpty(queryForm.getPackSizeName())) { //产品条件
			sql.append(" \r\n and p.productname = ? and p.specmode= ? ");param.put(UUID.randomUUID().toString(), queryForm.getProductName());param.put(UUID.randomUUID().toString(), queryForm.getPackSizeName());
		} else if(!StringUtil.isEmpty(queryForm.getProductName()) && StringUtil.isEmpty(queryForm.getPackSizeName())) { //产品条件
			sql.append(" \r\n and p.productname = ? ");param.put(UUID.randomUUID().toString(), queryForm.getProductName());
		}
		sql.append(" \r\n join WAREHOUSE w on w.id = temp.warehouseid and w.useflag=1");
		//权限控制
		if(DbUtil.isDealer(users)) {
			sql.append(" \r\n JOIN RULE_USER_WH rw on w.id = rw.warehouse_id  ");
			sql.append(" \r\n and rw.user_id = ?");param.put(UUID.randomUUID().toString(), users.getUserid());
		} else {
			sql.append(" \r\n and "+DbUtil.getWhereCondition(users, "o"));
		}
		sql.append(" \r\n join PRODUCT_STRUCT pstr on pstr.structcode=p.psid");
		sql.append(" \r\n LEFT JOIN COUNTRY_AREA ca on ca.id = o.province");
		sql.append(" \r\n LEFT JOIN REGION_AREA ra on RA.AREAID=o.province");
		sql.append(" \r\n LEFT JOIN REGION r on r.regioncode = ra.regioncodeid");
		//大区条件
		if(!StringUtil.isEmpty(queryForm.getRegion())){
			sql.append(" \r\n where r.regioncode = ? ");param.put(UUID.randomUUID().toString(), queryForm.getRegion()); 
		}
		System.out.println(sql.toString());
		List<Map> list =  new ArrayList<Map>();
		if(request == null && pageSize == 0){
			list =  EntityManager.jdbcquery(sql.toString(), param);
		}else {
			list =  PageQuery.jdbcSqlserverQuery(request, "organId,mCode", sql.toString(), pageSize, param);
		}
		if(list != null && list.size()>0){
			Map<String,FUnit>  funitMap = appFUnit.getAllMap(); //获取所有的单位换算信息
			Map<Integer, String> countUnitMap = appBr.getBaseResourceMap("CountUnit");
			for(Map map : list){
				SalesConsumeReportForm scForm = new SalesConsumeReportForm();
				//将Map中对应的值赋值给实例
				MapUtil.mapToObject(map, scForm);
				//报告日期主当前日期
				scForm.setReportDate(new Timestamp(System.currentTimeMillis()));
				
				if(StringUtil.isEmpty(queryForm.getCountByUnit())) {
					if(checkRate(scForm, funitMap,Constants.DEFAULT_UNIT_ID)){
						//将数量换算为件
						Double salesQuantity = changeUnit(scForm.getProductId(),scForm.getUnitId(),scForm.getSalesQuantity(),Constants.DEFAULT_UNIT_ID,funitMap);
						scForm.setSalesQuantity(salesQuantity);
						Double pwQuantity = changeUnit(scForm.getProductId(),scForm.getUnitId(),scForm.getPwQuantity(),Constants.DEFAULT_UNIT_ID,funitMap);
						scForm.setPwQuantity(pwQuantity);
						Double consumeQuantity = changeUnit(scForm.getProductId(),scForm.getUnitId(),scForm.getConsumeQuantity(),Constants.DEFAULT_UNIT_ID,funitMap);
						scForm.setConsumeQuantity(consumeQuantity);
						Double owQuantity = changeUnit(scForm.getProductId(),scForm.getUnitId(),scForm.getOwQuantity(),Constants.DEFAULT_UNIT_ID,funitMap);
						scForm.setOwQuantity(owQuantity);
						//单位默认显示为件
						scForm.setUnitId(Constants.DEFAULT_UNIT_ID);
					}
				} else {
					Product product = appProduct.loadProductById(scForm.getProductId());
					if(product != null) {
						//检查单位是否需要转化,是否可以正常转化,如不能则不转化
						if(scForm.getUnitId() != product.getSunit() && checkRate(scForm, funitMap,product.getSunit())){
							Double salesQuantity = changeUnit(scForm.getProductId(),scForm.getUnitId(),scForm.getSalesQuantity(),product.getSunit(),funitMap);
							scForm.setSalesQuantity(salesQuantity);
							Double pwQuantity = changeUnit(scForm.getProductId(),scForm.getUnitId(),scForm.getPwQuantity(),product.getSunit(),funitMap);
							scForm.setPwQuantity(pwQuantity);
							Double consumeQuantity = changeUnit(scForm.getProductId(),scForm.getUnitId(),scForm.getConsumeQuantity(),product.getSunit(),funitMap);
							scForm.setConsumeQuantity(consumeQuantity);
							Double owQuantity = changeUnit(scForm.getProductId(),scForm.getUnitId(),scForm.getOwQuantity(),product.getSunit(),funitMap);
							scForm.setOwQuantity(owQuantity);
							scForm.setUnitId(product.getSunit());
						}
						
						//转换成计量单位
						if(product.getBoxquantity() != null && product.getBoxquantity() != 0d) {
							Double salesQuantity = ArithDouble.mul(scForm.getSalesQuantity(), product.getBoxquantity());
							scForm.setSalesQuantity(salesQuantity);
							Double pwQuantity = ArithDouble.mul(scForm.getPwQuantity(), product.getBoxquantity());
							scForm.setPwQuantity(pwQuantity);
							Double consumeQuantity = ArithDouble.mul(scForm.getConsumeQuantity(), product.getBoxquantity());
							scForm.setConsumeQuantity(consumeQuantity);
							Double owQuantity = ArithDouble.mul(scForm.getOwQuantity(), product.getBoxquantity());
							scForm.setOwQuantity(owQuantity);
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

	private List<SalesConsumeReportForm> queryReportFromHistoryAndBill(
			HttpServletRequest request, int pageSize,
			SalesConsumeReportForm queryForm, UsersBean users) throws Exception {
		Map<String, Object> param = new LinkedHashMap<String, Object>();
		//对于结束日期增加一天
		if(!StringUtil.isEmpty(queryForm.getEndDate())){
			String endDateStr = queryForm.getEndDate();
			Date endDate = Dateutil.StringToDate(endDateStr);
			endDate = Dateutil.addDay2Date(1, endDate);
			queryForm.setEndDate(Dateutil.formatDate(endDate));
		}
		List<SalesConsumeReportForm> resultList = new ArrayList<SalesConsumeReportForm>();
		StringBuffer sql = new StringBuffer();
		
		sql.append(" \r\n select ");
		sql.append(" \r\n  r.sortname regionName");
		sql.append(" \r\n ,o.province provinceId");
		sql.append(" \r\n ,country.areaname province");
		sql.append(" \r\n ,o.id organId ");
		sql.append(" \r\n ,o.oecode oecode ");
		sql.append(" \r\n ,o.organname organName");
		sql.append(" \r\n ,pstr.sortname psName");
		sql.append(" \r\n ,p.id productId ");
		sql.append(" \r\n ,p.mcode mCode ");
		sql.append(" \r\n ,p.productname productName ");
		sql.append(" \r\n ,p.productnameen productNameen ");
		sql.append(" \r\n ,p.matericalchdes matericalChDes");
		sql.append(" \r\n ,p.matericalendes matericalEnDes");
		sql.append(" \r\n ,p.sunit unitId");
		sql.append(" \r\n ,p.specmode packSizeName");
		sql.append(" \r\n ,p.packsizenameen packSizeNameEn");
		sql.append(" \r\n ,sales.quantity salesQuantity");
		sql.append(" \r\n ,pw.quantity pwQuantity");
		sql.append(" \r\n ,consume.quantity consumeQuantity");
		sql.append(" \r\n ,ow.quantity owQuantity ");
		sql.append(" \r\n ,w.WAREHOUSENAME ");
		
		sql.append(" \r\n from organ o cross join product p ");
		sql.append(" \r\n join WAREHOUSE w on w.makeorganid = o.id ");
		sql.append(" \r\n LEFT JOIN PRODUCT_STRUCT pstr on p.PSID = pstr.STRUCTCODE ");
		sql.append(" \r\n LEFT JOIN COUNTRY_AREA country on o.province=country.id  ");
		
		sql.append(" \r\n LEFT JOIN (SELECT max(REGIONCODEID) regioncodeid,max(AREAID) areaid  FROM REGION_AREA GROUP BY AREAID) ra on ra.areaid=country.id  ");
		sql.append(" \r\n LEFT JOIN REGION r on r.regioncode=ra.regioncodeid  ");
		// 销售数量(工厂给PD的数量， 扣除调拨给其他PD的数量，加上其他PD调拨回来的)
		sql.append(" \r\n LEFT JOIN ( ");
		sql.append(" \r\n select max(oid) as oid, max(pid) as pid, sum(quantity) as quantity, wid from ( ");
		//工厂给PD的数量
		sql.append(" \r\n --工厂给PD的数量");
		sql.append(" \r\n select tt.inoid oid,ttd.productid pid ,(ttd.quantity * f.xquantity) quantity,w.id wid  ");
		sql.append(" \r\n from TAKE_TICKET tt INNER JOIN TAKE_TICKET_DETAIL ttd on tt.id = ttd.ttid  and tt.isaudit = 1  and tt.bsort = 1 ");
		if(!StringUtil.isEmpty(queryForm.getOrganId())){
			sql.append(" \r\n and tt.inoid = ? ");param.put(UUID.randomUUID().toString(), queryForm.getOrganId()); //机构条件
		}
		if(!StringUtil.isEmpty(queryForm.getWarehouseId())){
			sql.append(" \r\n and tt.inwarehouseid = ? ");param.put(UUID.randomUUID().toString(), queryForm.getWarehouseId()); //仓库条件
		}
		if(!StringUtil.isEmpty(queryForm.getProductName()) && !StringUtil.isEmpty(queryForm.getPackSizeName())) { //产品条件
			sql.append(" \r\n and ttd.productid in (select id from product where productname = ? and specmode=?) ");param.put(UUID.randomUUID().toString(), queryForm.getProductName());param.put(UUID.randomUUID().toString(), queryForm.getPackSizeName());
		} else if(!StringUtil.isEmpty(queryForm.getProductName()) && StringUtil.isEmpty(queryForm.getPackSizeName())) { //产品条件
			sql.append(" \r\n and ttd.productid in (select id from product where productname = ?) ");param.put(UUID.randomUUID().toString(), queryForm.getProductName());
		}
		//过滤拒签的单据
		sql.append(" \r\n INNER JOIN STOCK_ALTER_MOVE sam on sam.id = tt.billno and sam.isblankout = 0");
		if(!StringUtil.isEmpty(queryForm.getHistoryDate())){
			sql.append(" \r\n and sam.movedate >=to_date(?,'yyyy-MM-dd hh24:mi:ss') ");param.put(UUID.randomUUID().toString(), queryForm.getHistoryDate()); //开始时间条件
		}
		if(!StringUtil.isEmpty(queryForm.getEndDate())){
			sql.append(" \r\n and sam.movedate < to_date(?,'yyyy-MM-dd hh24:mi:ss') ");param.put(UUID.randomUUID().toString(), queryForm.getEndDate()); //结束时间条件
		}
		sql.append(" \r\n INNER JOIN ORGAN o on tt.oid = o.id and o.organtype = 1  "); // 出库仓库为工厂
		sql.append(" \r\n INNER JOIN ORGAN ino on tt.inoid = ino.id and ino.organtype = 2 and ino.organmodel = 1"); // 出库机构为PD
		sql.append(" \r\n join WAREHOUSE w on tt.inwarehouseid = w.id and w.USEFLAG = 1  ");
		sql.append(" \r\n LEFT JOIN F_UNIT f on ttd.productid = f.productid and ttd.unitid = f.funitid ");
		if(DbUtil.isDealer(users)) {
			sql.append(" \r\n LEFT JOIN RULE_USER_WH rw on tt.inwarehouseid = rw.warehouse_id  ");
			sql.append(" \r\n where rw.user_id = ?");param.put(UUID.randomUUID().toString(), users.getUserid());
		} else {
			sql.append(" \r\n where "+DbUtil.getWhereCondition(users, "ino"));
		}
		
		sql.append(" \r\n UNION ALL ");
		//PD调拨回来的
		sql.append(" \r\n --PD调拨回来的");
		sql.append(" \r\n select tt.inoid oid,ttd.productid pid ,(ttd.realquantity * f.xquantity) quantity,w.id wid  ");
		sql.append(" \r\n from TAKE_TICKET tt INNER JOIN TAKE_TICKET_DETAIL ttd on tt.id = ttd.ttid  and tt.isaudit = 1  and tt.bsort = 2 ");
		if(!StringUtil.isEmpty(queryForm.getOrganId())){
			sql.append(" \r\n and tt.inoid = ? ");param.put(UUID.randomUUID().toString(), queryForm.getOrganId()); //机构条件
		}
		if(!StringUtil.isEmpty(queryForm.getWarehouseId())){
			sql.append(" \r\n and tt.inwarehouseid = ? ");param.put(UUID.randomUUID().toString(), queryForm.getWarehouseId()); //仓库条件
		}
		if(!StringUtil.isEmpty(queryForm.getProductName()) && !StringUtil.isEmpty(queryForm.getPackSizeName())) { //产品条件
			sql.append(" \r\n and ttd.productid in (select id from product where productname = ? and specmode=?) ");param.put(UUID.randomUUID().toString(), queryForm.getProductName());param.put(UUID.randomUUID().toString(), queryForm.getPackSizeName());
		} else if(!StringUtil.isEmpty(queryForm.getProductName()) && StringUtil.isEmpty(queryForm.getPackSizeName())) { //产品条件
			sql.append(" \r\n and ttd.productid in (select id from product where productname = ?) ");param.put(UUID.randomUUID().toString(), queryForm.getProductName());
		}
		sql.append(" \r\n INNER JOIN STOCK_MOVE sm on sm.id = tt.billno");
		if(!StringUtil.isEmpty(queryForm.getHistoryDate())){
			sql.append(" \r\n and tt.auditdate >=to_date(?,'yyyy-MM-dd hh24:mi:ss') ");param.put(UUID.randomUUID().toString(), queryForm.getHistoryDate()); //开始时间条件
		}
		if(!StringUtil.isEmpty(queryForm.getEndDate())){
			sql.append(" \r\n and tt.auditdate < to_date(?,'yyyy-MM-dd hh24:mi:ss') ");param.put(UUID.randomUUID().toString(), queryForm.getEndDate()); //结束时间条件
		}
		sql.append(" \r\n INNER JOIN ORGAN o on tt.oid = o.id and o.organtype = 2 and o.organmodel = 1"); // 出库机构为PD
		sql.append(" \r\n join WAREHOUSE w on tt.inwarehouseid = w.id and w.USEFLAG = 1  ");
		sql.append(" \r\n LEFT JOIN F_UNIT f on ttd.productid = f.productid and ttd.unitid = f.funitid ");
		if(DbUtil.isDealer(users)) {
			sql.append(" \r\n LEFT JOIN RULE_USER_WH rw on tt.inwarehouseid = rw.warehouse_id  ");
			sql.append(" \r\n where rw.user_id = ?");param.put(UUID.randomUUID().toString(), users.getUserid());
		} else {
			sql.append(" \r\n where "+DbUtil.getWhereCondition(users, "o"));
		}
		
		sql.append(" \r\n UNION ALL ");
		//调拨给其他PD的
		sql.append(" \r\n --调拨给其他PD的");
		sql.append(" \r\n select tt.oid,ttd.productid pid ,-(ttd.realquantity * f.xquantity) quantity,w.id wid    ");
		sql.append(" \r\n from TAKE_TICKET tt INNER JOIN TAKE_TICKET_DETAIL ttd on tt.id = ttd.ttid  and tt.isaudit = 1  and tt.bsort = 2 ");
		if(!StringUtil.isEmpty(queryForm.getOrganId())){
			sql.append(" \r\n and tt.oid = ? ");param.put(UUID.randomUUID().toString(), queryForm.getOrganId()); //机构条件
		}
		if(!StringUtil.isEmpty(queryForm.getWarehouseId())){
			sql.append(" \r\n and tt.WAREHOUSEID = ? ");param.put(UUID.randomUUID().toString(), queryForm.getWarehouseId()); //仓库条件
		}
		if(!StringUtil.isEmpty(queryForm.getProductName()) && !StringUtil.isEmpty(queryForm.getPackSizeName())) { //产品条件
			sql.append(" \r\n and ttd.productid in (select id from product where productname = ? and specmode=?) ");param.put(UUID.randomUUID().toString(), queryForm.getProductName());param.put(UUID.randomUUID().toString(), queryForm.getPackSizeName());
		} else if(!StringUtil.isEmpty(queryForm.getProductName()) && StringUtil.isEmpty(queryForm.getPackSizeName())) { //产品条件
			sql.append(" \r\n and ttd.productid in (select id from product where productname = ?) ");param.put(UUID.randomUUID().toString(), queryForm.getProductName());
		}
		sql.append(" \r\n INNER JOIN STOCK_MOVE sm on sm.id = tt.billno");
		if(!StringUtil.isEmpty(queryForm.getHistoryDate())){
			sql.append(" \r\n and tt.auditdate >=to_date(?,'yyyy-MM-dd hh24:mi:ss') ");param.put(UUID.randomUUID().toString(), queryForm.getHistoryDate()); //开始时间条件
		}
		if(!StringUtil.isEmpty(queryForm.getEndDate())){
			sql.append(" \r\n and tt.auditdate < to_date(?,'yyyy-MM-dd hh24:mi:ss') ");param.put(UUID.randomUUID().toString(), queryForm.getEndDate()); //结束时间条件
		}
		sql.append(" \r\n INNER JOIN ORGAN o on tt.inoid = o.id and o.organtype = 2 and  o.organmodel = 1 "); // 收货机构为PD
		sql.append(" \r\n join WAREHOUSE w on tt.warehouseid = w.id and w.USEFLAG = 1  ");
		sql.append(" \r\n LEFT JOIN F_UNIT f on ttd.productid = f.productid and ttd.unitid = f.funitid ");
		if(DbUtil.isDealer(users)) {
			sql.append(" \r\n LEFT JOIN RULE_USER_WH rw on tt.warehouseid = rw.warehouse_id  ");
			sql.append(" \r\n where rw.user_id = ?");param.put(UUID.randomUUID().toString(), users.getUserid());
		} else {
			sql.append(" \r\n where "+DbUtil.getWhereCondition(users, "o"));
		}
		
		
		//#start modified by ryan.xi at 20150714
		//扣减分包时大包的销售数量
		sql.append(" \r\n UNION ALL ");
		sql.append(" \r\n --扣减分包时大包的销售数量");
		sql.append(" \r\n select tt.ORGANID oid,ttd.OUTPRODUCTID pid ,-(OUTQUANTITY-ttd.wastage) quantity,w.id wid  ");
		sql.append(" \r\n from PACK_SEPARATE tt INNER JOIN PACK_SEPARATE_DETAIL ttd on tt.id = ttd.psid  and tt.isaudit = 1 ");
		if(!StringUtil.isEmpty(queryForm.getOrganId())){
			sql.append(" \r\n and tt.ORGANID = ? ");param.put(UUID.randomUUID().toString(), queryForm.getOrganId()); //机构条件
		}
		if(!StringUtil.isEmpty(queryForm.getWarehouseId())){
			sql.append(" \r\n and tt.WAREHOUSEID = ? ");param.put(UUID.randomUUID().toString(), queryForm.getWarehouseId()); //仓库条件
		}
		if(!StringUtil.isEmpty(queryForm.getProductName()) && !StringUtil.isEmpty(queryForm.getPackSizeName())) { //产品条件
			sql.append(" \r\n and ttd.OUTPRODUCTID in (select id from product where productname = ? and specmode= ?) ");param.put(UUID.randomUUID().toString(), queryForm.getProductName());param.put(UUID.randomUUID().toString(), queryForm.getPackSizeName());
		} else if(!StringUtil.isEmpty(queryForm.getProductName()) && StringUtil.isEmpty(queryForm.getPackSizeName())) { //产品条件
			sql.append(" \r\n and ttd.OUTPRODUCTID in (select id from product where productname = ?) ");param.put(UUID.randomUUID().toString(), queryForm.getProductName());
		}
		if(!StringUtil.isEmpty(queryForm.getHistoryDate())){
			sql.append(" \r\n and tt.auditdate >=to_date(?,'yyyy-MM-dd hh24:mi:ss') ");param.put(UUID.randomUUID().toString(), queryForm.getHistoryDate()); //开始时间条件
		}
		if(!StringUtil.isEmpty(queryForm.getEndDate())){
			sql.append(" \r\n and tt.auditdate < to_date(?,'yyyy-MM-dd hh24:mi:ss') ");param.put(UUID.randomUUID().toString(), queryForm.getEndDate()); //结束时间条件
		}
		sql.append(" \r\n join WAREHOUSE w on tt.warehouseid = w.id and w.USEFLAG = 1  ");
		if(DbUtil.isDealer(users)) {
			sql.append(" \r\n JOIN RULE_USER_WH rw on tt.WAREHOUSEID = rw.warehouse_id ");
			sql.append(" \r\n where rw.user_id = ?");param.put(UUID.randomUUID().toString(), users.getUserid());
		} else {
			sql.append(" \r\n JOIN Organ o on o.id = tt.ORGANID ");
			sql.append(" \r\n where "+DbUtil.getWhereCondition(users, "o"));
		}
		
		
		//增加分包时小包的销售数量
		sql.append(" \r\n UNION ALL ");
		sql.append(" \r\n --增加分包时小包的销售数量");
		sql.append(" \r\n select tt.ORGANID oid,ttd.INPRODUCTID pid ,INQUANTITY quantity,w.id wid  ");
		sql.append(" \r\n from PACK_SEPARATE tt INNER JOIN PACK_SEPARATE_DETAIL ttd on tt.id = ttd.psid  and tt.isaudit = 1 ");
		if(!StringUtil.isEmpty(queryForm.getOrganId())){
			sql.append(" \r\n and tt.ORGANID = ? ");param.put(UUID.randomUUID().toString(), queryForm.getOrganId()); //机构条件
		}
		if(!StringUtil.isEmpty(queryForm.getWarehouseId())){
			sql.append(" \r\n and tt.WAREHOUSEID = ? ");param.put(UUID.randomUUID().toString(), queryForm.getWarehouseId()); //仓库条件
		}
		if(!StringUtil.isEmpty(queryForm.getProductName()) && !StringUtil.isEmpty(queryForm.getPackSizeName())) { //产品条件
			sql.append(" \r\n and ttd.INPRODUCTID in (select id from product where productname = ? and specmode= ?) ");param.put(UUID.randomUUID().toString(), queryForm.getProductName());param.put(UUID.randomUUID().toString(), queryForm.getPackSizeName());
		} else if(!StringUtil.isEmpty(queryForm.getProductName()) && StringUtil.isEmpty(queryForm.getPackSizeName())) { //产品条件
			sql.append(" \r\n and ttd.INPRODUCTID in (select id from product where productname = ?) ");param.put(UUID.randomUUID().toString(), queryForm.getProductName());
		}
		if(!StringUtil.isEmpty(queryForm.getHistoryDate())){
			sql.append(" \r\n and tt.auditdate >=to_date(?,'yyyy-MM-dd hh24:mi:ss') ");param.put(UUID.randomUUID().toString(), queryForm.getHistoryDate()); //开始时间条件
		}
		if(!StringUtil.isEmpty(queryForm.getEndDate())){
			sql.append(" \r\n and tt.auditdate < to_date(?,'yyyy-MM-dd hh24:mi:ss') ");param.put(UUID.randomUUID().toString(), queryForm.getEndDate()); //结束时间条件
		}
		sql.append(" \r\n join WAREHOUSE w on tt.warehouseid = w.id and w.USEFLAG = 1  ");
		if(DbUtil.isDealer(users)) {
			sql.append(" \r\n JOIN RULE_USER_WH rw on tt.WAREHOUSEID = rw.warehouse_id ");
			sql.append(" \r\n where rw.user_id = ?");param.put(UUID.randomUUID().toString(), users.getUserid());
		} else {
			sql.append(" \r\n JOIN Organ o on o.id = tt.ORGANID ");
			sql.append(" \r\n where "+DbUtil.getWhereCondition(users, "o"));
		}
		//#end modified by ryan.xi at 20150714
		
		//#start modified by ryan at 20150805
		//采购入库-加销售
		sql.append(" \r\n UNION ALL ");
		sql.append(" \r\n --采购入库-加销售");
		sql.append(" \r\n select oia.organid, oida.productid pid, (oida.quantity * f.xquantity) quantity,w.id wid   ");
		sql.append(" \r\n from OTHER_INCOME_ALL oia INNER JOIN OTHER_INCOME_DETAIL_ALL oida on oia.id = oida.oiid and oia.isaudit = 1 and oia.incomesort = 6");
		if(!StringUtil.isEmpty(queryForm.getOrganId())){
			sql.append(" \r\n and oia.organid = ? ");param.put(UUID.randomUUID().toString(), queryForm.getOrganId()); //机构条件
		}
		if(!StringUtil.isEmpty(queryForm.getWarehouseId())){
			sql.append(" \r\n and oia.warehouseid = ? ");param.put(UUID.randomUUID().toString(), queryForm.getWarehouseId()); //仓库条件
		}
		if(!StringUtil.isEmpty(queryForm.getProductName()) && !StringUtil.isEmpty(queryForm.getPackSizeName())) { //产品条件
			sql.append(" \r\n and oida.productid in (select id from product where productname = ? and packsizename= ?) ");param.put(UUID.randomUUID().toString(), queryForm.getProductName());param.put(UUID.randomUUID().toString(), queryForm.getPackSizeName());
		} else if(!StringUtil.isEmpty(queryForm.getProductName()) && StringUtil.isEmpty(queryForm.getPackSizeName())) { //产品条件
			sql.append(" \r\n and oida.productid in (select id from product where productname = ?) ");param.put(UUID.randomUUID().toString(), queryForm.getProductName());
		}
		if(!StringUtil.isEmpty(queryForm.getHistoryDate())){
			sql.append(" \r\n and oia.auditdate >=to_date(?,'yyyy-MM-dd hh24:mi:ss') ");param.put(UUID.randomUUID().toString(), queryForm.getHistoryDate()); //开始时间条件
		}
		if(!StringUtil.isEmpty(queryForm.getEndDate())){
			sql.append(" \r\n and oia.auditdate < to_date(?,'yyyy-MM-dd hh24:mi:ss') ");param.put(UUID.randomUUID().toString(), queryForm.getEndDate()); //结束时间条件
		}
		sql.append(" \r\n INNER JOIN ORGAN o on oia.organid= o.id and o.organtype = 2 and  organmodel = 1 ");
		sql.append(" \r\n join WAREHOUSE w on oia.warehouseid = w.id and w.USEFLAG = 1  ");
		sql.append(" \r\n LEFT JOIN F_UNIT f on oida.productid = f.productid and oida.unitid = f.funitid  "); 
		if(DbUtil.isDealer(users)) {
			sql.append(" \r\n INNER JOIN RULE_USER_WH rw on oia.warehouseid = rw.warehouse_id ");
			sql.append(" \r\n where rw.user_id = ?");param.put(UUID.randomUUID().toString(), users.getUserid()); 
		} else {
			sql.append(" \r\n where "+DbUtil.getWhereCondition(users, "o"));
		}
		
		//#end modified by ryan at 20150805
		
		sql.append(" \r\n UNION ALL ");
		sql.append(" \r\n --历史销售数据");
		sql.append(" \r\n select sdh.organid,sdh.productid,sdh.salesQuantity,sdh.WAREHOUSEID");
		sql.append(" \r\n FROM SALES_DETAIL_HISTORY sdh ");
		if(DbUtil.isDealer(users)) {
			sql.append(" \r\n INNER JOIN RULE_USER_WH rw on sdh.warehouseid = rw.warehouse_id ");
			sql.append(" \r\n and rw.user_id = ?");param.put(UUID.randomUUID().toString(), users.getUserid()); 
		} else {
			sql.append(" \r\n join organ o on o.id = sdh.organid ");
			sql.append(" \r\n and "+DbUtil.getWhereCondition(users, "o"));
		}
		sql.append(" \r\n where billNo NOT like 'PW%'");
		if(!StringUtil.isEmpty(queryForm.getOrganId())){
			sql.append(" \r\n and sdh.organid = ? ");param.put(UUID.randomUUID().toString(), queryForm.getOrganId()); //机构条件
		}
		if(!StringUtil.isEmpty(queryForm.getWarehouseId())){
			sql.append(" \r\n and sdh.warehouseid = ? ");param.put(UUID.randomUUID().toString(), queryForm.getWarehouseId()); //仓库条件
		}
		if(!StringUtil.isEmpty(queryForm.getProductName()) && !StringUtil.isEmpty(queryForm.getPackSizeName())) { //产品条件
			sql.append(" \r\n and sdh.productid in (select id from product where productname = ? and packsizename= ?) ");param.put(UUID.randomUUID().toString(), queryForm.getProductName());param.put(UUID.randomUUID().toString(), queryForm.getPackSizeName());
		} else if(!StringUtil.isEmpty(queryForm.getProductName()) && StringUtil.isEmpty(queryForm.getPackSizeName())) { //产品条件
			sql.append(" \r\n and sdh.productid in (select id from product where productname = ?) ");param.put(UUID.randomUUID().toString(), queryForm.getProductName());
		}
		if(!StringUtil.isEmpty(queryForm.getBeginDate())){
			sql.append(" \r\n and sdh.makeDate >=to_date(?,'yyyy-MM-dd hh24:mi:ss') ");param.put(UUID.randomUUID().toString(), queryForm.getBeginDate()); //开始时间条件
		}
		if(!StringUtil.isEmpty(queryForm.getHistoryDate())){
			sql.append(" \r\n and sdh.makeDate < to_date(?,'yyyy-MM-dd hh24:mi:ss') ");param.put(UUID.randomUUID().toString(), queryForm.getHistoryDate()); //结束时间条件
		}
		
		sql.append(" \r\n ) GROUP BY oid,pid,wid ");
		sql.append(" \r\n ) sales on o.id = sales.oid and p.id = sales.pid and sales.wid = w.id  ");
		// 退回工厂
		sql.append(" \r\n --退回工厂");
		sql.append(" \r\n LEFT JOIN ( ");
		sql.append(" \r\n select oid,pid ,sum(quantity) quantity,wid from ( ");
		
		sql.append(" \r\n select tt.oid oid,ttd.productid pid ,ttd.realquantity * f.xquantity quantity,w.id wid  ");
		sql.append(" \r\n from TAKE_TICKET tt INNER JOIN TAKE_TICKET_DETAIL ttd on tt.id = ttd.ttid  and tt.isaudit = 1  and tt.bsort = 7 and tt.billno like 'PW%' ");
		if(!StringUtil.isEmpty(queryForm.getOrganId())){
			sql.append(" \r\n and tt.oid = ? ");param.put(UUID.randomUUID().toString(), queryForm.getOrganId()); //机构条件
		}
		if(!StringUtil.isEmpty(queryForm.getWarehouseId())){
			sql.append(" \r\n and tt.WAREHOUSEID = ? ");param.put(UUID.randomUUID().toString(), queryForm.getWarehouseId()); //仓库条件
		}
		if(!StringUtil.isEmpty(queryForm.getProductName()) && !StringUtil.isEmpty(queryForm.getPackSizeName())) { //产品条件
			sql.append(" \r\n and ttd.productid in (select id from product where productname = ? and specmode=?) ");param.put(UUID.randomUUID().toString(), queryForm.getProductName());param.put(UUID.randomUUID().toString(), queryForm.getPackSizeName());
		} else if(!StringUtil.isEmpty(queryForm.getProductName()) && StringUtil.isEmpty(queryForm.getPackSizeName())) { //产品条件
			sql.append(" \r\n and ttd.productid in (select id from product where productname = ?) ");param.put(UUID.randomUUID().toString(), queryForm.getProductName());
		}
		if(!StringUtil.isEmpty(queryForm.getHistoryDate())){
			sql.append(" \r\n and tt.auditdate >=to_date(?,'yyyy-MM-dd hh24:mi:ss') ");param.put(UUID.randomUUID().toString(), queryForm.getHistoryDate()); //开始时间条件
		}
		if(!StringUtil.isEmpty(queryForm.getEndDate())){
			sql.append(" \r\n and tt.auditdate < to_date(?,'yyyy-MM-dd hh24:mi:ss') ");param.put(UUID.randomUUID().toString(), queryForm.getEndDate()); //结束时间条件
		}
		/*
		//按退回工厂单创建日期-20150429
		sql.append(" \r\n join ORGAN_WITHDRAW ow on tt.billno = ow.id ");
		if(!StringUtil.isEmpty(queryForm.getHistoryDate())){
			sql.append(" \r\n and ow.makedate >=to_date('" + queryForm.getHistoryDate() + "','yyyy-MM-dd hh24:mi:ss') "); //开始时间条件
		}
		if(!StringUtil.isEmpty(queryForm.getEndDate())){
			sql.append(" \r\n and ow.makedate < to_date('" + queryForm.getEndDate() + "','yyyy-MM-dd hh24:mi:ss') "); //结束时间条件
		}*/
		sql.append(" \r\n INNER JOIN ORGAN o on tt.oid = o.id and o.organtype = 2 and  organmodel = 1 "); //出库仓库为PD
		sql.append(" \r\n join WAREHOUSE w on tt.warehouseid = w.id and w.USEFLAG = 1  ");
		sql.append(" \r\n LEFT JOIN F_UNIT f on ttd.productid = f.productid and ttd.unitid = f.funitid ");
		if(DbUtil.isDealer(users)) {
			sql.append(" \r\n LEFT JOIN RULE_USER_WH rw on tt.warehouseid = rw.warehouse_id  ");
			sql.append(" \r\n where rw.user_id = ?");param.put(UUID.randomUUID().toString(), users.getUserid());
		} else {
			sql.append(" \r\n where "+DbUtil.getWhereCondition(users, "o"));
		}
		
		sql.append(" \r\n UNION ALL ");
		sql.append(" \r\n --历史退回工厂数据");
		sql.append(" \r\n select sdh.organid,sdh.productid,-sdh.salesQuantity,sdh.WAREHOUSEID");
		sql.append(" \r\n FROM SALES_DETAIL_HISTORY sdh ");
		if(DbUtil.isDealer(users)) {
			sql.append(" \r\n INNER JOIN RULE_USER_WH rw on sdh.warehouseid = rw.warehouse_id ");
			sql.append(" \r\n and rw.user_id = ?");param.put(UUID.randomUUID().toString(), users.getUserid()); 
		} else {
			sql.append(" \r\n join organ o on o.id = sdh.organid ");
			sql.append(" \r\n and "+DbUtil.getWhereCondition(users, "o"));
		}
		sql.append(" \r\n where billNo like 'PW%'");
		if(!StringUtil.isEmpty(queryForm.getOrganId())){
			sql.append(" \r\n and organid = ? ");param.put(UUID.randomUUID().toString(), queryForm.getOrganId()); //机构条件
		}
		if(!StringUtil.isEmpty(queryForm.getWarehouseId())){
			sql.append(" \r\n and WAREHOUSEID = ? ");param.put(UUID.randomUUID().toString(), queryForm.getWarehouseId()); //仓库条件
		}
		if(!StringUtil.isEmpty(queryForm.getProductName()) && !StringUtil.isEmpty(queryForm.getPackSizeName())) { //产品条件
			sql.append(" \r\n and productid in (select id from product where productname = ? and packsizename= ?) ");param.put(UUID.randomUUID().toString(), queryForm.getProductName());param.put(UUID.randomUUID().toString(), queryForm.getPackSizeName());
		} else if(!StringUtil.isEmpty(queryForm.getProductName()) && StringUtil.isEmpty(queryForm.getPackSizeName())) { //产品条件
			sql.append(" \r\n and productid in (select id from product where productname = ?) ");param.put(UUID.randomUUID().toString(), queryForm.getProductName());
		}
		if(!StringUtil.isEmpty(queryForm.getBeginDate())){
			sql.append(" \r\n and makedate >=to_date(?,'yyyy-MM-dd hh24:mi:ss') ");param.put(UUID.randomUUID().toString(), queryForm.getBeginDate()); //开始时间条件
		}
		if(!StringUtil.isEmpty(queryForm.getHistoryDate())){
			sql.append(" \r\n and makedate < to_date(?,'yyyy-MM-dd hh24:mi:ss') ");param.put(UUID.randomUUID().toString(), queryForm.getHistoryDate()); //结束时间条件
		}
		sql.append(" \r\n ) temp0 ");
		
		sql.append(" \r\n GROUP BY oid,pid,wid  ");
		sql.append(" \r\n ) pw on  o.id = pw.oid and p.id = pw.pid and pw.wid = w.id ");
		// 消耗
		sql.append(" \r\n LEFT JOIN ( ");
		sql.append(" \r\n --实际出库数量算消耗");
		sql.append(" \r\n select max(oid) oid,max(pid) pid,sum(quantity) quantity, wid   from ( ");
		//#start modified by ryan.xi 20150518 按实际出库数量算消耗
		sql.append(" \r\n  select tt.oid, ttd.productid pid, (ttd.realquantity * f.xquantity) quantity,w.id wid  ");
		//#end
		sql.append(" \r\n from TAKE_TICKET tt INNER JOIN TAKE_TICKET_DETAIL ttd on tt.id = ttd.ttid  and tt.isaudit = 1  and tt.bsort = 1 ");
		if(!StringUtil.isEmpty(queryForm.getOrganId())){
			sql.append(" \r\n and tt.oid = ? ");param.put(UUID.randomUUID().toString(), queryForm.getOrganId()); //机构条件
		}
		if(!StringUtil.isEmpty(queryForm.getWarehouseId())){
			sql.append(" \r\n and tt.WAREHOUSEID = ? ");param.put(UUID.randomUUID().toString(), queryForm.getWarehouseId()); //仓库条件
		}
		if(!StringUtil.isEmpty(queryForm.getProductName()) && !StringUtil.isEmpty(queryForm.getPackSizeName())) { //产品条件
			sql.append(" \r\n and ttd.productid in (select id from product where productname = ? and specmode=?) ");param.put(UUID.randomUUID().toString(), queryForm.getProductName());param.put(UUID.randomUUID().toString(), queryForm.getPackSizeName());
		} else if(!StringUtil.isEmpty(queryForm.getProductName()) && StringUtil.isEmpty(queryForm.getPackSizeName())) { //产品条件
			sql.append(" \r\n and ttd.productid in (select id from product where productname = ?) ");param.put(UUID.randomUUID().toString(), queryForm.getProductName());
		}
		if(!StringUtil.isEmpty(queryForm.getHistoryDate())){
			sql.append(" \r\n and tt.auditdate >=to_date(?,'yyyy-MM-dd hh24:mi:ss') ");param.put(UUID.randomUUID().toString(), queryForm.getHistoryDate()); //开始时间条件
		}
		if(!StringUtil.isEmpty(queryForm.getEndDate())){
			sql.append(" \r\n and tt.auditdate < to_date(?,'yyyy-MM-dd hh24:mi:ss') ");param.put(UUID.randomUUID().toString(), queryForm.getEndDate()); //结束时间条件
		}
		//过滤拒签的单据
		sql.append(" \r\n INNER JOIN STOCK_ALTER_MOVE sam on sam.id = tt.billno and sam.isblankout = 0");
		sql.append(" \r\n INNER JOIN ORGAN o on tt.oid = o.id and o.organtype = 2 and  organmodel = 1 "); //出库仓库为PD
		sql.append(" \r\n join WAREHOUSE w on tt.warehouseid = w.id and w.USEFLAG = 1  ");
		sql.append(" \r\n LEFT JOIN F_UNIT f on ttd.productid = f.productid and ttd.unitid = f.funitid ");
		if(DbUtil.isDealer(users)) {
			sql.append(" \r\n JOIN RULE_USER_WH rw on tt.warehouseid = rw.warehouse_id  ");
			//#start modified by ryan.xi 20150518 过滤消耗为0的单据
			sql.append(" \r\n where rw.user_id = ?");param.put(UUID.randomUUID().toString(), users.getUserid());
		} else {
			sql.append(" \r\n where "+DbUtil.getWhereCondition(users, "o"));
		}
		sql.append(" and (ttd.realquantity * f.xquantity) <> 0 ");
		//#end
		sql.append(" \r\n UNION ALL ");
		
		
		//#start modified by ryan.xi at 20150714
		//分包时的损耗记到消耗
		sql.append(" \r\n --分包时的损耗记到消耗");
		sql.append(" \r\n select tt.ORGANID, ttd.OUTPRODUCTID pid, ttd.wastage quantity,w.id wid  ");
		sql.append(" \r\n from PACK_SEPARATE tt INNER JOIN PACK_SEPARATE_DETAIL ttd on tt.id = ttd.psid  and tt.isaudit = 1 ");
		if(!StringUtil.isEmpty(queryForm.getOrganId())){
			sql.append(" \r\n and tt.ORGANID = ? ");param.put(UUID.randomUUID().toString(), queryForm.getOrganId()); //机构条件
		}
		if(!StringUtil.isEmpty(queryForm.getWarehouseId())){
			sql.append(" \r\n and tt.WAREHOUSEID = ? ");param.put(UUID.randomUUID().toString(), queryForm.getWarehouseId()); //仓库条件
		}
		if(!StringUtil.isEmpty(queryForm.getProductName()) && !StringUtil.isEmpty(queryForm.getPackSizeName())) { //产品条件
			sql.append(" \r\n and ttd.OUTPRODUCTID in (select id from product where productname = ? and specmode= ?) ");param.put(UUID.randomUUID().toString(), queryForm.getProductName());param.put(UUID.randomUUID().toString(), queryForm.getPackSizeName());
		} else if(!StringUtil.isEmpty(queryForm.getProductName()) && StringUtil.isEmpty(queryForm.getPackSizeName())) { //产品条件
			sql.append(" \r\n and ttd.OUTPRODUCTID in (select id from product where productname = ?) ");param.put(UUID.randomUUID().toString(), queryForm.getProductName());
		}
		if(!StringUtil.isEmpty(queryForm.getHistoryDate())){
			sql.append(" \r\n and tt.auditdate >=to_date(?,'yyyy-MM-dd hh24:mi:ss') ");param.put(UUID.randomUUID().toString(), queryForm.getHistoryDate()); //开始时间条件
		}
		if(!StringUtil.isEmpty(queryForm.getEndDate())){
			sql.append(" \r\n and tt.auditdate < to_date(?,'yyyy-MM-dd hh24:mi:ss') ");param.put(UUID.randomUUID().toString(), queryForm.getEndDate()); //结束时间条件
		}
		sql.append(" \r\n join WAREHOUSE w on tt.warehouseid = w.id and w.USEFLAG = 1  ");
		if(DbUtil.isDealer(users)) {
			sql.append(" \r\n JOIN RULE_USER_WH rw on tt.WAREHOUSEID = rw.warehouse_id ");
			sql.append(" \r\n where rw.user_id = ?");param.put(UUID.randomUUID().toString(), users.getUserid());
		} else {
			sql.append(" \r\n JOIN Organ o on o.id = tt.ORGANID ");
			sql.append(" \r\n where "+DbUtil.getWhereCondition(users, "o"));
		}
		sql.append(" and ttd.wastage <> 0 ");
		sql.append(" \r\n UNION ALL ");
		//#end modified by ryan.xi at 20150714
		
		
		//工厂发货给PD, 收货差异计入PD消耗
		sql.append(" \r\n --工厂发货给PD, 收货差异计入PD消耗");
		sql.append(" \r\n select tt.inoid, ttd.productid pid, ((samd.quantity-samd.receivequantity) * f.xquantity) quantity,w.id wid  ");
		sql.append(" \r\n from TAKE_TICKET tt INNER JOIN TAKE_TICKET_DETAIL ttd on tt.id = ttd.ttid  and tt.isaudit = 1  and tt.bsort = 1  ");
		if(!StringUtil.isEmpty(queryForm.getOrganId())){
			sql.append(" \r\n and tt.inoid = ? ");param.put(UUID.randomUUID().toString(), queryForm.getOrganId()); //机构条件
		}
		if(!StringUtil.isEmpty(queryForm.getWarehouseId())){
			sql.append(" \r\n and tt.inwarehouseid = ? ");param.put(UUID.randomUUID().toString(), queryForm.getWarehouseId()); //仓库条件
		}
		if(!StringUtil.isEmpty(queryForm.getProductName()) && !StringUtil.isEmpty(queryForm.getPackSizeName())) { //产品条件
			sql.append(" \r\n and ttd.productid in (select id from product where productname = ? and specmode=?) ");param.put(UUID.randomUUID().toString(), queryForm.getProductName());param.put(UUID.randomUUID().toString(), queryForm.getPackSizeName());
		} else if(!StringUtil.isEmpty(queryForm.getProductName()) && StringUtil.isEmpty(queryForm.getPackSizeName())) { //产品条件
			sql.append(" \r\n and ttd.productid in (select id from product where productname = ?) ");param.put(UUID.randomUUID().toString(), queryForm.getProductName());
		}
		sql.append(" \r\n join STOCK_ALTER_MOVE sam on sam.id=tt.billno and sam.iscomplete =1");
		if(!StringUtil.isEmpty(queryForm.getHistoryDate())){
			sql.append(" \r\n and sam.movedate >=to_date(?,'yyyy-MM-dd hh24:mi:ss') ");param.put(UUID.randomUUID().toString(), queryForm.getHistoryDate()); //开始时间条件
		}
		if(!StringUtil.isEmpty(queryForm.getEndDate())){
			sql.append(" \r\n and sam.movedate < to_date(?,'yyyy-MM-dd hh24:mi:ss') ");param.put(UUID.randomUUID().toString(), queryForm.getEndDate()); //结束时间条件
		}
		sql.append(" \r\n join STOCK_ALTER_MOVE_DETAIL samd on samd.samid = sam.id and samd.productid=ttd.productid");
		sql.append(" \r\n INNER JOIN ORGAN o on tt.oid = o.id and o.organtype = 1 ");
		sql.append(" \r\n join WAREHOUSE w on tt.inwarehouseid = w.id and w.USEFLAG = 1  ");
		sql.append(" \r\n LEFT JOIN F_UNIT f on ttd.productid = f.productid and ttd.unitid = f.funitid ");
		sql.append(" \r\n LEFT JOIN ORGAN ino on tt.inoid = ino.id  and ino.organtype = 2 and ino.organmodel = 1 ");
		if(DbUtil.isDealer(users)) {
			sql.append(" \r\n LEFT JOIN RULE_USER_WH rw on tt.inwarehouseid = rw.warehouse_id ");
			sql.append(" \r\n where rw.user_id = ?");param.put(UUID.randomUUID().toString(), users.getUserid());
		} else {
			sql.append(" \r\n where "+DbUtil.getWhereCondition(users, "ino"));
		}
		sql.append(" and (samd.quantity-samd.receivequantity) > 0 ");
		
		
		
		
		//#start modified by ryan at 20150805
		//其他入库-减消耗-20150429
		sql.append(" \r\n --其他入库-减消耗");
		sql.append(" \r\n UNION ALL ");
		sql.append(" \r\n select oia.organid, oida.productid pid, -(oida.quantity * f.xquantity) quantity,w.id wid   ");
		sql.append(" \r\n from OTHER_INCOME_ALL oia INNER JOIN OTHER_INCOME_DETAIL_ALL oida on oia.id = oida.oiid and oia.isaudit = 1 and oia.incomesort <> 6 ");
		if(!StringUtil.isEmpty(queryForm.getOrganId())){
			sql.append(" \r\n and oia.organid = ? ");param.put(UUID.randomUUID().toString(), queryForm.getOrganId()); //机构条件
		} 
		if(!StringUtil.isEmpty(queryForm.getWarehouseId())){
			sql.append(" \r\n and oia.warehouseid = ? ");param.put(UUID.randomUUID().toString(), queryForm.getWarehouseId()); //仓库条件
		}
		if(!StringUtil.isEmpty(queryForm.getProductName()) && !StringUtil.isEmpty(queryForm.getPackSizeName())) { //产品条件
			sql.append(" \r\n and oida.productid in (select id from product where productname = ? and packsizename= ?) ");param.put(UUID.randomUUID().toString(), queryForm.getProductName());param.put(UUID.randomUUID().toString(), queryForm.getPackSizeName());
		} else if(!StringUtil.isEmpty(queryForm.getProductName()) && StringUtil.isEmpty(queryForm.getPackSizeName())) { //产品条件
			sql.append(" \r\n and oida.productid in (select id from product where productname = ?) ");param.put(UUID.randomUUID().toString(), queryForm.getProductName());
		}
		if(!StringUtil.isEmpty(queryForm.getHistoryDate())){
			sql.append(" \r\n and oia.auditdate >=to_date(?,'yyyy-MM-dd hh24:mi:ss') ");param.put(UUID.randomUUID().toString(), queryForm.getHistoryDate()); //开始时间条件
		}
		if(!StringUtil.isEmpty(queryForm.getEndDate())){
			sql.append(" \r\n and oia.auditdate < to_date(?,'yyyy-MM-dd hh24:mi:ss') ");param.put(UUID.randomUUID().toString(), queryForm.getEndDate()); //结束时间条件
		}
		sql.append(" \r\n INNER JOIN ORGAN o on oia.organid= o.id and o.organtype = 2 and  organmodel = 1 ");
		sql.append(" \r\n join WAREHOUSE w on oia.warehouseid = w.id and w.USEFLAG = 1  ");
		sql.append(" \r\n LEFT JOIN F_UNIT f on oida.productid = f.productid and oida.unitid = f.funitid  "); 
		if(DbUtil.isDealer(users)) {
			sql.append(" \r\n INNER JOIN RULE_USER_WH rw on oia.warehouseid = rw.warehouse_id ");
			sql.append(" \r\n where rw.user_id = ?");param.put(UUID.randomUUID().toString(), users.getUserid()); 
		} else {
			sql.append(" \r\n where "+DbUtil.getWhereCondition(users, "o"));
		}
		
		
		//其他出库-加消耗-20150429
		sql.append(" \r\n UNION ALL ");
		sql.append(" \r\n --其他出库-加消耗");
		sql.append(" \r\n select oia.organid, oida.productid pid, (oida.quantity * f.xquantity) quantity,w.id wid   ");
		sql.append(" \r\n from OTHER_SHIPMENT_BILL_ALL oia INNER JOIN OTHER_SHIPMENT_BILL_D_ALL oida on oia.id = oida.osid and oia.isaudit = 1 ");
		if(!StringUtil.isEmpty(queryForm.getOrganId())){
			sql.append(" \r\n and oia.organid = ? ");param.put(UUID.randomUUID().toString(), queryForm.getOrganId()); //机构条件
		}
		if(!StringUtil.isEmpty(queryForm.getWarehouseId())){
			sql.append(" \r\n and oia.warehouseid = ? ");param.put(UUID.randomUUID().toString(), queryForm.getWarehouseId()); //仓库条件
		}
		if(!StringUtil.isEmpty(queryForm.getProductName()) && !StringUtil.isEmpty(queryForm.getPackSizeName())) { //产品条件
			sql.append(" \r\n and oida.productid in (select id from product where productname = ? and packsizename= ?) ");param.put(UUID.randomUUID().toString(), queryForm.getProductName());param.put(UUID.randomUUID().toString(), queryForm.getPackSizeName());
		} else if(!StringUtil.isEmpty(queryForm.getProductName()) && StringUtil.isEmpty(queryForm.getPackSizeName())) { //产品条件
			sql.append(" \r\n and oida.productid in (select id from product where productname = ?) ");param.put(UUID.randomUUID().toString(), queryForm.getProductName());
		}
		if(!StringUtil.isEmpty(queryForm.getHistoryDate())){
			sql.append(" \r\n and oia.auditdate >=to_date(?,'yyyy-MM-dd hh24:mi:ss') ");param.put(UUID.randomUUID().toString(), queryForm.getHistoryDate()); //开始时间条件
		}
		if(!StringUtil.isEmpty(queryForm.getEndDate())){
			sql.append(" \r\n and oia.auditdate < to_date(?,'yyyy-MM-dd hh24:mi:ss') ");param.put(UUID.randomUUID().toString(), queryForm.getEndDate()); //结束时间条件
		}
		sql.append(" \r\n INNER JOIN ORGAN o on oia.organid= o.id and o.organtype = 2 and  organmodel = 1 ");
		sql.append(" \r\n join WAREHOUSE w on oia.warehouseid = w.id and w.USEFLAG = 1  ");
		sql.append(" \r\n LEFT JOIN F_UNIT f on oida.productid = f.productid and oida.unitid = f.funitid  "); 
		if(DbUtil.isDealer(users)) {
			sql.append(" \r\n INNER JOIN RULE_USER_WH rw on oia.warehouseid = rw.warehouse_id ");
			sql.append(" \r\n where rw.user_id = ?");param.put(UUID.randomUUID().toString(), users.getUserid()); 
		} else { 
			sql.append(" \r\n where "+DbUtil.getWhereCondition(users, "o"));
		}
		//#end modified by ryan at 20150805
		
		sql.append(" \r\n UNION ALL ");
		sql.append(" \r\n --历史消耗数据");
		sql.append(" \r\n select sdh.outorganid,sdh.productid,sdh.consumequantity,sdh.OUTWAREHOUSEID");
		sql.append(" \r\n FROM CONSUME_DETAIL_HISTORY sdh ");
		if(DbUtil.isDealer(users)) {
			sql.append(" \r\n INNER JOIN RULE_USER_WH rw on sdh.OUTWAREHOUSEID = rw.warehouse_id ");
			sql.append(" \r\n and rw.user_id = ?");param.put(UUID.randomUUID().toString(), users.getUserid()); 
		} else {
			sql.append(" \r\n join organ o on o.id = sdh.outorganid ");
			sql.append(" \r\n and "+DbUtil.getWhereCondition(users, "o"));
		}
		sql.append(" \r\n where billNo NOT like 'OW%'");
		if(!StringUtil.isEmpty(queryForm.getOrganId())){
			sql.append(" \r\n and sdh.outorganid = ? ");param.put(UUID.randomUUID().toString(), queryForm.getOrganId()); //机构条件
		}
		if(!StringUtil.isEmpty(queryForm.getWarehouseId())){
			sql.append(" \r\n and sdh.outwarehouseid = ? ");param.put(UUID.randomUUID().toString(), queryForm.getWarehouseId()); //仓库条件
		}
		if(!StringUtil.isEmpty(queryForm.getProductName()) && !StringUtil.isEmpty(queryForm.getPackSizeName())) { //产品条件
			sql.append(" \r\n and sdh.productid in (select id from product where productname = ? and packsizename= ?) ");param.put(UUID.randomUUID().toString(), queryForm.getProductName());param.put(UUID.randomUUID().toString(), queryForm.getPackSizeName());
		} else if(!StringUtil.isEmpty(queryForm.getProductName()) && StringUtil.isEmpty(queryForm.getPackSizeName())) { //产品条件
			sql.append(" \r\n and sdh.productid in (select id from product where productname = ?) ");param.put(UUID.randomUUID().toString(), queryForm.getProductName());
		}
		if(!StringUtil.isEmpty(queryForm.getBeginDate())){
			sql.append(" \r\n and sdh.makeDate >=to_date(?,'yyyy-MM-dd hh24:mi:ss') ");param.put(UUID.randomUUID().toString(), queryForm.getBeginDate()); //开始时间条件
		}
		if(!StringUtil.isEmpty(queryForm.getHistoryDate())){
			sql.append(" \r\n and sdh.makeDate < to_date(?,'yyyy-MM-dd hh24:mi:ss') ");param.put(UUID.randomUUID().toString(), queryForm.getHistoryDate()); //结束时间条件
		}
		
		sql.append(" \r\n ) notmatchconsume  ");
		
		sql.append(" \r\n GROUP BY notmatchconsume.oid,notmatchconsume.pid,notmatchconsume.wid  ");
		sql.append(" \r\n )  consume on o.id = consume.oid and p.id = consume.pid and w.id = consume.wid  ");
		// 退货
		sql.append(" \r\n --退货");
		sql.append(" \r\n LEFT JOIN ( ");
		sql.append(" \r\n select oid,pid,sum(quantity) quantity, wid from ( ");
		
		sql.append(" \r\n select tt.inoid oid,ttd.productid pid,ttd.realquantity * f.xquantity quantity,w.id wid   ");
		sql.append(" \r\n from TAKE_TICKET tt INNER JOIN TAKE_TICKET_DETAIL ttd on tt.id = ttd.ttid  and tt.isaudit = 1  and tt.bsort = 7 and tt.billno like 'OW%' ");
		if(!StringUtil.isEmpty(queryForm.getOrganId())){
			sql.append(" \r\n and tt.inoid = ? ");param.put(UUID.randomUUID().toString(), queryForm.getOrganId()); //机构条件
		}
		if(!StringUtil.isEmpty(queryForm.getWarehouseId())){
			sql.append(" \r\n and tt.inwarehouseid = ? ");param.put(UUID.randomUUID().toString(), queryForm.getWarehouseId()); //仓库条件
		}
		if(!StringUtil.isEmpty(queryForm.getProductName()) && !StringUtil.isEmpty(queryForm.getPackSizeName())) { //产品条件
			sql.append(" \r\n and ttd.productid in (select id from product where productname = ? and specmode=?) ");param.put(UUID.randomUUID().toString(), queryForm.getProductName());param.put(UUID.randomUUID().toString(), queryForm.getPackSizeName());
		} else if(!StringUtil.isEmpty(queryForm.getProductName()) && StringUtil.isEmpty(queryForm.getPackSizeName())) { //产品条件
			sql.append(" \r\n and ttd.productid in (select id from product where productname = ?) ");param.put(UUID.randomUUID().toString(), queryForm.getProductName());
		}
		if(!StringUtil.isEmpty(queryForm.getHistoryDate())){
			sql.append(" \r\n and tt.auditdate >=to_date(?,'yyyy-MM-dd hh24:mi:ss') ");param.put(UUID.randomUUID().toString(), queryForm.getHistoryDate()); //开始时间条件
		}
		if(!StringUtil.isEmpty(queryForm.getEndDate())){
			sql.append(" \r\n and tt.auditdate < to_date(?,'yyyy-MM-dd hh24:mi:ss') ");param.put(UUID.randomUUID().toString(), queryForm.getEndDate()); //结束时间条件
		}
		sql.append(" \r\n INNER JOIN ORGAN o on tt.inoid = o.id and o.organtype = 2 and  organmodel = 1 "); // 入库仓库为PD
		sql.append(" \r\n join WAREHOUSE w on tt.inwarehouseid = w.id and w.USEFLAG = 1  ");
		sql.append(" \r\n LEFT JOIN F_UNIT f on ttd.productid = f.productid and ttd.unitid = f.funitid ");
		
		if(DbUtil.isDealer(users)) {
			sql.append(" \r\n LEFT JOIN RULE_USER_WH rw on tt.inwarehouseid = rw.warehouse_id ");
			sql.append(" \r\n where rw.user_id = ?");param.put(UUID.randomUUID().toString(), users.getUserid());
		} else {
			sql.append(" \r\n where "+DbUtil.getWhereCondition(users, "o"));
		}

		sql.append(" \r\n UNION ALL ");
		sql.append(" \r\n --历史渠道退货数据");
		sql.append(" \r\n select sdh.outorganid,sdh.productid,-sdh.consumequantity,sdh.OUTWAREHOUSEID");
		sql.append(" \r\n FROM CONSUME_DETAIL_HISTORY sdh ");
		if(DbUtil.isDealer(users)) {
			sql.append(" \r\n INNER JOIN RULE_USER_WH rw on sdh.OUTWAREHOUSEID = rw.warehouse_id ");
			sql.append(" \r\n and rw.user_id = ?");param.put(UUID.randomUUID().toString(), users.getUserid()); 
		} else {
			sql.append(" \r\n join organ o on o.id = sdh.outorganid ");
			sql.append(" \r\n and "+DbUtil.getWhereCondition(users, "o"));
		}
		sql.append(" \r\n where billNo like 'OW%'");
		if(!StringUtil.isEmpty(queryForm.getOrganId())){
			sql.append(" \r\n and sdh.outorganid = ? ");param.put(UUID.randomUUID().toString(), queryForm.getOrganId()); //机构条件
		}
		if(!StringUtil.isEmpty(queryForm.getWarehouseId())){
			sql.append(" \r\n and sdh.outwarehouseid = ? ");param.put(UUID.randomUUID().toString(), queryForm.getWarehouseId()); //仓库条件
		}
		if(!StringUtil.isEmpty(queryForm.getProductName()) && !StringUtil.isEmpty(queryForm.getPackSizeName())) { //产品条件
			sql.append(" \r\n and sdh.productid in (select id from product where productname = ? and packsizename= ?) ");param.put(UUID.randomUUID().toString(), queryForm.getProductName());param.put(UUID.randomUUID().toString(), queryForm.getPackSizeName());
		} else if(!StringUtil.isEmpty(queryForm.getProductName()) && StringUtil.isEmpty(queryForm.getPackSizeName())) { //产品条件
			sql.append(" \r\n and sdh.productid in (select id from product where productname = ?) ");param.put(UUID.randomUUID().toString(), queryForm.getProductName());
		}
		if(!StringUtil.isEmpty(queryForm.getBeginDate())){
			sql.append(" \r\n and sdh.makeDate >=to_date(?,'yyyy-MM-dd hh24:mi:ss') ");param.put(UUID.randomUUID().toString(), queryForm.getBeginDate()); //开始时间条件
		}
		if(!StringUtil.isEmpty(queryForm.getHistoryDate())){
			sql.append(" \r\n and sdh.makeDate < to_date(?,'yyyy-MM-dd hh24:mi:ss') ");param.put(UUID.randomUUID().toString(), queryForm.getHistoryDate()); //结束时间条件
		}
		
		sql.append(" \r\n ) temp1  ");
		
		sql.append(" \r\n GROUP BY oid,pid,wid  ");
		sql.append(" \r\n ) ow on o.id = ow.oid and p.id = ow.pid and w.id = ow.wid  ");
//		sql.append(" \r\n LEFT JOIN RULE_USER_WH rw on w.id = rw.warehouse_id  ");
//		sql.append(" \r\n where rw.user_id = ?");param.put(UUID.randomUUID().toString(), users.getUserid());
		sql.append(" \r\n where  o.organtype = 2 and  o.organmodel = 1 and o.ISREPEAL = 0 "); // 机构类型为PD
		
		if(OrganType.Dealer.getValue().equals(users.getOrganType())) {
			sql.append(" \r\n and w.id in ( select warehouse_id from RULE_USER_WH where user_id = ? ) ");param.put(UUID.randomUUID().toString(), users.getUserid());
		} else { 
			sql.append(" \r\n and "+DbUtil.getWhereCondition(users, "o"));
		}
		
		if(!StringUtil.isEmpty(queryForm.getOrganId())){
			sql.append(" \r\n and o.id = ? ");param.put(UUID.randomUUID().toString(), queryForm.getOrganId()); //机构条件
		}
		if(!StringUtil.isEmpty(queryForm.getProductName()) && !StringUtil.isEmpty(queryForm.getPackSizeName())) { //产品条件
			sql.append(" \r\n and p.id in (select id from product where productname = ? and specmode= ?) ");param.put(UUID.randomUUID().toString(), queryForm.getProductName());param.put(UUID.randomUUID().toString(), queryForm.getPackSizeName());
		} else if(!StringUtil.isEmpty(queryForm.getProductName()) && StringUtil.isEmpty(queryForm.getPackSizeName())) { //产品条件
			sql.append(" \r\n and p.id in (select id from product where productname = ?) ");param.put(UUID.randomUUID().toString(), queryForm.getProductName());
		}
		//大区条件
		if(!StringUtil.isEmpty(queryForm.getRegion())){
			sql.append(" \r\n and r.regioncode = ? "); param.put(UUID.randomUUID().toString(), queryForm.getRegion());
		}
		sql.append(" \r\n  and p.USEFLAG = 1 ");
		System.out.println(sql.toString());
		List<Map> list =  new ArrayList<Map>();
		if(request == null && pageSize == 0){
			list =  EntityManager.jdbcquery(sql.toString(), param);
		}else {
			list =  PageQuery.jdbcSqlserverQuery(request, "organId,mCode", sql.toString(), pageSize, param);
		}
		if(list != null && list.size()>0){
			Map<String,FUnit>  funitMap = appFUnit.getAllMap(); //获取所有的单位换算信息
			Map<Integer, String> countUnitMap = appBr.getBaseResourceMap("CountUnit");
			for(Map map : list){
				SalesConsumeReportForm scForm = new SalesConsumeReportForm();
				//将Map中对应的值赋值给实例
				MapUtil.mapToObject(map, scForm);
				//报告日期主当前日期
				scForm.setReportDate(new Timestamp(System.currentTimeMillis()));
				
				if(StringUtil.isEmpty(queryForm.getCountByUnit())) {
					if(checkRate(scForm, funitMap,Constants.DEFAULT_UNIT_ID)){
						//将数量换算为件
						Double salesQuantity = changeUnit(scForm.getProductId(),scForm.getUnitId(),scForm.getSalesQuantity(),Constants.DEFAULT_UNIT_ID,funitMap);
						scForm.setSalesQuantity(salesQuantity);
						Double pwQuantity = changeUnit(scForm.getProductId(),scForm.getUnitId(),scForm.getPwQuantity(),Constants.DEFAULT_UNIT_ID,funitMap);
						scForm.setPwQuantity(pwQuantity);
						Double consumeQuantity = changeUnit(scForm.getProductId(),scForm.getUnitId(),scForm.getConsumeQuantity(),Constants.DEFAULT_UNIT_ID,funitMap);
						scForm.setConsumeQuantity(consumeQuantity);
						Double owQuantity = changeUnit(scForm.getProductId(),scForm.getUnitId(),scForm.getOwQuantity(),Constants.DEFAULT_UNIT_ID,funitMap);
						scForm.setOwQuantity(owQuantity);
						//单位默认显示为件
						scForm.setUnitId(Constants.DEFAULT_UNIT_ID);
					}
				} else {
					Product product = appProduct.loadProductById(scForm.getProductId());
					if(product != null) {
						//检查单位是否需要转化,是否可以正常转化,如不能则不转化
						if(scForm.getUnitId() != product.getSunit() && checkRate(scForm, funitMap,product.getSunit())){
							Double salesQuantity = changeUnit(scForm.getProductId(),scForm.getUnitId(),scForm.getSalesQuantity(),product.getSunit(),funitMap);
							scForm.setSalesQuantity(salesQuantity);
							Double pwQuantity = changeUnit(scForm.getProductId(),scForm.getUnitId(),scForm.getPwQuantity(),product.getSunit(),funitMap);
							scForm.setPwQuantity(pwQuantity);
							Double consumeQuantity = changeUnit(scForm.getProductId(),scForm.getUnitId(),scForm.getConsumeQuantity(),product.getSunit(),funitMap);
							scForm.setConsumeQuantity(consumeQuantity);
							Double owQuantity = changeUnit(scForm.getProductId(),scForm.getUnitId(),scForm.getOwQuantity(),product.getSunit(),funitMap);
							scForm.setOwQuantity(owQuantity);
							scForm.setUnitId(product.getSunit());
						}
						
						//转换成计量单位
						if(product.getBoxquantity() != null && product.getBoxquantity() != 0d) {
							Double salesQuantity = ArithDouble.mul(scForm.getSalesQuantity(), product.getBoxquantity());
							scForm.setSalesQuantity(salesQuantity);
							Double pwQuantity = ArithDouble.mul(scForm.getPwQuantity(), product.getBoxquantity());
							scForm.setPwQuantity(pwQuantity);
							Double consumeQuantity = ArithDouble.mul(scForm.getConsumeQuantity(), product.getBoxquantity());
							scForm.setConsumeQuantity(consumeQuantity);
							Double owQuantity = ArithDouble.mul(scForm.getOwQuantity(), product.getBoxquantity());
							scForm.setOwQuantity(owQuantity);
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
	
	// 根据条件查询
	public List<SalesConsumeDetailReportForm> querySalesDetailReport(HttpServletRequest request, int pageSize,SalesConsumeDetailReportForm queryForm,UsersBean users) throws Exception{
		//查看是否有统计过销售消耗历史数据
		BaseResource sacBr = appBr.getBaseResourceValue("SalesDetailDate", 1);
		if(sacBr == null) {//无历史数据按单据统计
			return querySalesDetailReport2(request, pageSize, queryForm, users);
		} else {
			Date sacDate = DateUtil.StringToDate(sacBr.getTagsubvalue());
			Date startDate = DateUtil.StringToDate(queryForm.getBeginDate());
			Date endDate = DateUtil.StringToDate(queryForm.getEndDate());
			if(sacDate.before(startDate)) {
				//历史数据在查询起始日期之前,则按单据实时统计
				return querySalesDetailReport2(request, pageSize, queryForm, users);
			} else if(sacDate.before(endDate)) {
				//历史数据在查询起始日期之后,结束日期之前,则按历史数据结合单据实时统计
				queryForm.setHistoryDate(sacBr.getTagsubvalue());
				return querySalesDetailReportFromHistoryAndBill(request, pageSize, queryForm, users);
			} else {
				return querySalesDetailReportFromHistory(request, pageSize, queryForm, users);
			}
		}
	}

	private List<SalesConsumeDetailReportForm> querySalesDetailReportFromHistory(
			HttpServletRequest request, int pageSize,
			SalesConsumeDetailReportForm queryForm, UsersBean users) throws Exception {
		Map<String, Object> param = new LinkedHashMap<String, Object>();
		//对于结束日期增加一天
		if(!StringUtil.isEmpty(queryForm.getEndDate())){
			String endDateStr = queryForm.getEndDate();
			Date endDate = Dateutil.StringToDate(endDateStr);
			endDate = Dateutil.addDay2Date(1, endDate);
			queryForm.setEndDate(Dateutil.formatDate(endDate));
		}
		
		List<SalesConsumeDetailReportForm> resultList = new ArrayList<SalesConsumeDetailReportForm>();
		StringBuffer sql = new StringBuffer();
		// 历史销售数据
		sql.append(" \r\n select sdh.billNo billNo");
		sql.append(" \r\n ,r.sortname regionName");
		sql.append(" \r\n ,o.province provinceId");
		sql.append(" \r\n ,o.oecode oecode");
		sql.append(" \r\n ,ca.areaname province");
		sql.append(" \r\n ,o.id organId ");
		sql.append(" \r\n ,o.organname organName");
		sql.append(" \r\n ,p.id productId  ");
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
		sql.append(" \r\n join organ o on o.id = sdh.organid and o.isrepeal=0 ");
		if(DbUtil.isDealer(users)) {
			sql.append(" \r\n INNER JOIN RULE_USER_WH rw on sdh.warehouseid = rw.warehouse_id ");
			sql.append(" \r\n and rw.user_id = ?");param.put(UUID.randomUUID().toString(), users.getUserid()); 
		} else {
			sql.append(" \r\n and "+DbUtil.getWhereCondition(users, "o"));
		}
		if(!StringUtil.isEmpty(queryForm.getOrganId())){
			sql.append(" \r\n and sdh.organid = ? ");param.put(UUID.randomUUID().toString(), queryForm.getOrganId()); //机构条件
		}
		if(!StringUtil.isEmpty(queryForm.getWarehouseId())){
			sql.append(" \r\n and sdh.warehouseid = ? ");param.put(UUID.randomUUID().toString(), queryForm.getWarehouseId()); //仓库条件
		}
		if(!StringUtil.isEmpty(queryForm.getBeginDate())){
			sql.append(" \r\n and sdh.makeDate >=to_date(?,'yyyy-MM-dd hh24:mi:ss') ");param.put(UUID.randomUUID().toString(), queryForm.getBeginDate()); //开始时间条件
		}
		if(!StringUtil.isEmpty(queryForm.getEndDate())){
			sql.append(" \r\n and sdh.makedate < to_date(?,'yyyy-MM-dd hh24:mi:ss') ");param.put(UUID.randomUUID().toString(), queryForm.getEndDate()); //结束时间条件
		}
		sql.append(" \r\n join PRODUCT p on p.id = sdh.productid and p.useflag=1");
		if(!StringUtil.isEmpty(queryForm.getProductName()) && !StringUtil.isEmpty(queryForm.getPackSizeName())) { //产品条件
			sql.append(" \r\n and p.productname = ? and packsizename= ? ");param.put(UUID.randomUUID().toString(), queryForm.getProductName());param.put(UUID.randomUUID().toString(), queryForm.getPackSizeName());
		} else if(!StringUtil.isEmpty(queryForm.getProductName()) && StringUtil.isEmpty(queryForm.getPackSizeName())) { //产品条件
			sql.append(" \r\n and p.productname = ? ");param.put(UUID.randomUUID().toString(), queryForm.getProductName());
		}
		sql.append(" \r\n join WAREHOUSE w on w.id = sdh.warehouseid and w.useflag=1");
		sql.append(" \r\n join PRODUCT_STRUCT pstr on pstr.structcode=p.psid");
		sql.append(" \r\n LEFT JOIN COUNTRY_AREA ca on ca.id = o.province");
		sql.append(" \r\n LEFT JOIN REGION_AREA ra on RA.AREAID=o.province");
		sql.append(" \r\n LEFT JOIN REGION r on r.regioncode = ra.regioncodeid");
		//大区条件
		if(!StringUtil.isEmpty(queryForm.getRegion())){
			sql.append(" \r\n where r.regioncode = ? ");param.put(UUID.randomUUID().toString(), queryForm.getRegion()); 
		}
		
		System.out.println(sql.toString());
		List<Map> list =  new ArrayList<Map>();
		if(request == null && pageSize == 0){
			list =  EntityManager.jdbcquery(sql.toString(), param);
		}else {
			list =  PageQuery.jdbcSqlserverQuery(request,"billNo", sql.toString(), pageSize, param);
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
				scForm.setReportDate(new Timestamp(System.currentTimeMillis()));
				//单据日期
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

	private List<SalesConsumeDetailReportForm> querySalesDetailReportFromHistoryAndBill(
			HttpServletRequest request, int pageSize,
			SalesConsumeDetailReportForm queryForm, UsersBean users) throws Exception {
		Map<String, Object> param = new LinkedHashMap<String, Object>();
		//对于结束日期增加一天
		if(!StringUtil.isEmpty(queryForm.getEndDate())){
			String endDateStr = queryForm.getEndDate();
			Date endDate = Dateutil.StringToDate(endDateStr);
			endDate = Dateutil.addDay2Date(1, endDate);
			queryForm.setEndDate(Dateutil.formatDate(endDate));
		}
		
		List<SalesConsumeDetailReportForm> resultList = new ArrayList<SalesConsumeDetailReportForm>();
		StringBuffer sql = new StringBuffer();
		// 工厂给PD
		sql.append(" \r\n select DISTINCT tt.billNo billNo");
		sql.append(" \r\n ,r.sortname regionName");
		sql.append(" \r\n ,ino.province provinceId");
		sql.append(" \r\n ,ino.oecode oecode");
		sql.append(" \r\n ,country.areaname province");
		sql.append(" \r\n ,tt.inoid organId ");
		sql.append(" \r\n ,ino.organname organName");
		sql.append(" \r\n ,ttd.productid productId  ");
		sql.append(" \r\n ,pstr.sortname psName");
		sql.append(" \r\n ,p.productname productName");
		sql.append(" \r\n ,p.productnameen productNameen ");
		sql.append(" \r\n ,p.mcode mCode");
		sql.append(" \r\n ,p.matericalchdes matericalChDes ");
		sql.append(" \r\n ,p.matericalendes matericalEnDes ");
		sql.append(" \r\n ,p.sunit unitId ");
		sql.append(" \r\n ,p.specmode packSizeName");
		sql.append(" \r\n ,p.packsizenameen packSizeNameEn");
		sql.append(" \r\n ,swb.batch batch ");
		sql.append(" \r\n ,swb.cycleoutquantity salesQuantity"); // 销售数量
		sql.append(" \r\n ,0 consumeQuantity"); //消耗数量
		sql.append(" \r\n ,sam.movedate makeDate ,w.warehousename");
//		sql.append(" \r\n ,pj.production_date produceDate ");
//		sql.append(" \r\n ,pj.expiry_date expiryDate ");
		sql.append(" \r\n from TAKE_TICKET tt INNER JOIN TAKE_TICKET_DETAIL ttd on tt.id = ttd.ttid  and tt.isaudit = 1  and tt.bsort = 1 ");
		if(!StringUtil.isEmpty(queryForm.getOrganId())){
			sql.append(" \r\n and tt.inoid = ? ");param.put(UUID.randomUUID().toString(), queryForm.getOrganId()); //机构条件
		}
		if(!StringUtil.isEmpty(queryForm.getWarehouseId())){
			sql.append(" \r\n and tt.inwarehouseid = ? ");param.put(UUID.randomUUID().toString(), queryForm.getWarehouseId()); //仓库条件
		}
		if(!StringUtil.isEmpty(queryForm.getProductName()) && !StringUtil.isEmpty(queryForm.getPackSizeName())) { //产品条件
			sql.append(" \r\n and ttd.productid in (select id from product where productname = ? and specmode=?) ");param.put(UUID.randomUUID().toString(), queryForm.getProductName());param.put(UUID.randomUUID().toString(), queryForm.getPackSizeName());
		} else if(!StringUtil.isEmpty(queryForm.getProductName()) && StringUtil.isEmpty(queryForm.getPackSizeName())) { //产品条件
			sql.append(" \r\n and ttd.productid in (select id from product where productname = ?) ");param.put(UUID.randomUUID().toString(), queryForm.getProductName());
		}
		//过滤拒签的单据
		sql.append(" \r\n join STOCK_ALTER_MOVE sam on sam.id=tt.billno and sam.isblankout = 0");
		if(!StringUtil.isEmpty(queryForm.getHistoryDate())){
			sql.append(" \r\n and sam.movedate >=to_date(?,'yyyy-MM-dd hh24:mi:ss') ");param.put(UUID.randomUUID().toString(), queryForm.getHistoryDate()); //开始时间条件
		}
		if(!StringUtil.isEmpty(queryForm.getEndDate())){
			sql.append(" \r\n and sam.movedate < to_date(?,'yyyy-MM-dd hh24:mi:ss') ");param.put(UUID.randomUUID().toString(), queryForm.getEndDate()); //结束时间条件
		}
		sql.append(" \r\n join WAREHOUSE w on w.id = tt.inwarehouseid and w.USEFLAG = 1 ");
		sql.append(" \r\n INNER JOIN STOCK_WASTE_BOOK swb on swb.billcode = tt.id and ttd.productid = swb.productid ");
		sql.append(" \r\n INNER JOIN ORGAN o on tt.oid = o.id and o.organtype = 1  ");
		sql.append(" \r\n INNER JOIN ORGAN ino on tt.inoid = ino.id and ino.ISREPEAL = 0 ");
		//机构类型条件
		if(queryForm.getOrganType() == 1) {
			sql.append(" \r\n and ino.organtype = 1 ");//工厂
		} else if(queryForm.getOrganType() ==2){
			sql.append(" \r\n and ino.organtype = 2 and ino.organmodel = 1 ");//经销商
		} else {
			sql.append(" \r\n and ((ino.organtype = 1) or (ino.organtype = 2 and ino.organmodel = 1)) ");
		}
		sql.append(" \r\n LEFT JOIN F_UNIT f on ttd.productid = f.productid and ttd.unitid = f.funitid ");
		sql.append(" \r\n LEFT JOIN PRODUCT p on ttd.productid = p.id  ");
		sql.append(" \r\n LEFT JOIN PRODUCT_STRUCT pstr on p.PSID = pstr.STRUCTCODE  ");
		
		sql.append(" \r\n LEFT JOIN COUNTRY_AREA country on ino.province=country.id  ");
		
		sql.append(" \r\n LEFT JOIN (SELECT max(REGIONCODEID) regioncodeid,max(AREAID) areaid  FROM REGION_AREA GROUP BY AREAID) ra on ra.areaid=country.id  ");
		sql.append(" \r\n LEFT JOIN REGION r on r.regioncode=ra.regioncodeid  ");
		if(DbUtil.isDealer(users)) {
			sql.append(" \r\n LEFT JOIN RULE_USER_WH rw on tt.inwarehouseid = rw.warehouse_id  ");
			sql.append(" \r\n where rw.user_id = ?");param.put(UUID.randomUUID().toString(), users.getUserid());
		} else {
			sql.append(" \r\n where "+DbUtil.getWhereCondition(users, "ino"));
		}
		//大区条件
		if(!StringUtil.isEmpty(queryForm.getRegion())){
			sql.append(" \r\n and r.regioncode = ? "); param.put(UUID.randomUUID().toString(), queryForm.getRegion());
		}
		sql.append(" \r\n  and p.USEFLAG = 1 ");
		if(queryForm.getOrganType() != 1) {
			sql.append(" \r\n union all");
			//PD调拨回来的
			sql.append(" \r\n select DISTINCT tt.billNo billNo");
			sql.append(" \r\n ,r.sortname regionName");
			sql.append(" \r\n ,ino.province provinceId");
			sql.append(" \r\n ,ino.oecode oecode");
			sql.append(" \r\n ,country.areaname province");
			sql.append(" \r\n ,tt.inoid organId ");
			sql.append(" \r\n ,ino.organname organName");
			sql.append(" \r\n ,ttd.productid productId  ");
			sql.append(" \r\n ,pstr.sortname psName");
			sql.append(" \r\n ,p.productname productName");
			sql.append(" \r\n  ,p.productnameen productNameen ");
			sql.append(" \r\n ,p.mcode mCode");
			sql.append(" \r\n ,p.matericalchdes matericalChDes ");
			sql.append(" \r\n ,p.matericalendes matericalEnDes ");
			sql.append(" \r\n ,p.sunit unitId ");
			sql.append(" \r\n ,p.specmode packSizeName");
			sql.append(" \r\n  ,p.packsizenameen packSizeNameEn");
			sql.append(" \r\n ,swb.batch batch ");
			sql.append(" \r\n ,swb.cycleoutquantity salesQuantity"); // 销售数量
			sql.append(" \r\n ,0 consumeQuantity"); //消耗数量
			sql.append(" \r\n ,sm.movedate makeDate,w.warehousename ");
//			sql.append(" \r\n ,pj.production_date produceDate ");
//			sql.append(" \r\n ,pj.expiry_date expiryDate ");
			sql.append(" \r\n from TAKE_TICKET tt INNER JOIN TAKE_TICKET_DETAIL ttd on tt.id = ttd.ttid  and tt.isaudit = 1 and tt.bsort = 2 ");
			if(!StringUtil.isEmpty(queryForm.getOrganId())){
				sql.append(" \r\n and tt.inoid = ? ");param.put(UUID.randomUUID().toString(), queryForm.getOrganId()); //机构条件
			}
			if(!StringUtil.isEmpty(queryForm.getWarehouseId())){
				sql.append(" \r\n and tt.inwarehouseid = ? ");param.put(UUID.randomUUID().toString(), queryForm.getWarehouseId()); //仓库条件
			}
			if(!StringUtil.isEmpty(queryForm.getProductName()) && !StringUtil.isEmpty(queryForm.getPackSizeName())) { //产品条件
				sql.append(" \r\n and ttd.productid in (select id from product where productname = ? and specmode=?) ");param.put(UUID.randomUUID().toString(), queryForm.getProductName());param.put(UUID.randomUUID().toString(), queryForm.getPackSizeName());
			} else if(!StringUtil.isEmpty(queryForm.getProductName()) && StringUtil.isEmpty(queryForm.getPackSizeName())) { //产品条件
				sql.append(" \r\n and ttd.productid in (select id from product where productname = ?) ");param.put(UUID.randomUUID().toString(), queryForm.getProductName());
			}
			sql.append(" \r\n join STOCK_MOVE sm on sm.id=tt.billno");
			if(!StringUtil.isEmpty(queryForm.getHistoryDate())){
				sql.append(" \r\n and sm.SHIPMENTDATE >=to_date(?,'yyyy-MM-dd hh24:mi:ss') ");param.put(UUID.randomUUID().toString(), queryForm.getHistoryDate()); //开始时间条件
			}
			if(!StringUtil.isEmpty(queryForm.getEndDate())){
				sql.append(" \r\n and sm.SHIPMENTDATE < to_date(?,'yyyy-MM-dd hh24:mi:ss') ");param.put(UUID.randomUUID().toString(), queryForm.getEndDate()); //结束时间条件
			}
			sql.append(" \r\n join WAREHOUSE w on w.id = tt.inwarehouseid and w.USEFLAG = 1 ");
			sql.append(" \r\n INNER JOIN STOCK_WASTE_BOOK swb on swb.billcode = tt.id and ttd.productid = swb.productid ");
			sql.append(" \r\n INNER JOIN ORGAN o on tt.oid = o.id and o.organtype = 2 and o.organmodel = 1 ");
			sql.append(" \r\n LEFT JOIN F_UNIT f on ttd.productid = f.productid and ttd.unitid = f.funitid ");
			sql.append(" \r\n LEFT JOIN PRODUCT p on ttd.productid = p.id  ");
			sql.append(" \r\n LEFT JOIN PRODUCT_STRUCT pstr on p.PSID = pstr.STRUCTCODE  ");
			sql.append(" \r\n LEFT JOIN ORGAN ino on tt.inoid = ino.id and ino.organtype = 2 and ino.organmodel = 1 and ino.ISREPEAL = 0");
			sql.append(" \r\n LEFT JOIN COUNTRY_AREA country on ino.province=country.id  ");
			sql.append(" \r\n LEFT JOIN (SELECT max(REGIONCODEID) regioncodeid,max(AREAID) areaid  FROM REGION_AREA GROUP BY AREAID) ra on ra.areaid=country.id  ");
			sql.append(" \r\n LEFT JOIN REGION r on r.regioncode=ra.regioncodeid  ");
			if(DbUtil.isDealer(users)) {
				sql.append(" \r\n LEFT JOIN RULE_USER_WH rw on tt.inwarehouseid = rw.warehouse_id  ");
				sql.append(" \r\n where rw.user_id = ?");param.put(UUID.randomUUID().toString(), users.getUserid());
			} else {
				sql.append(" \r\n where "+DbUtil.getWhereCondition(users, "o"));
			}
			//大区条件
			if(!StringUtil.isEmpty(queryForm.getRegion())){
				sql.append(" \r\n and r.regioncode = ? "); param.put(UUID.randomUUID().toString(), queryForm.getRegion());
			}
			sql.append(" \r\n  and p.USEFLAG = 1 ");
			sql.append(" \r\n union all");
			//调拨给其他PD的
			sql.append(" \r\n select DISTINCT tt.billNo billNo");
			sql.append(" \r\n ,r.sortname regionName");
			sql.append(" \r\n ,o.province provinceId");
			sql.append(" \r\n ,o.oecode oecode");
			sql.append(" \r\n ,country.areaname province");
			sql.append(" \r\n ,tt.oid organId ");
			sql.append(" \r\n ,o.organname organName");
			sql.append(" \r\n ,ttd.productid productId  ");
			sql.append(" \r\n ,pstr.sortname psName");
			sql.append(" \r\n ,p.productname productName");
			sql.append(" \r\n ,p.productnameen productNameen");
			sql.append(" \r\n ,p.mcode mCode");
			sql.append(" \r\n ,p.matericalchdes matericalChDes ");
			sql.append(" \r\n ,p.matericalendes matericalEnDes ");
			sql.append(" \r\n ,p.sunit unitId ");
			sql.append(" \r\n ,p.specmode packSizeName");
			sql.append(" \r\n ,p.packsizenameen packSizeNameEn ");
			sql.append(" \r\n ,swb.batch batch ");
			sql.append(" \r\n ,-swb.cycleoutquantity salesQuantity"); // 销售数量
			sql.append(" \r\n ,0 consumeQuantity"); //消耗数量
			sql.append(" \r\n ,sm.movedate makeDate,w.warehousename ");
//			sql.append(" \r\n ,pj.production_date produceDate ");
//			sql.append(" \r\n ,pj.expiry_date expiryDate ");
			sql.append(" \r\n from TAKE_TICKET tt INNER JOIN TAKE_TICKET_DETAIL ttd on tt.id = ttd.ttid  and tt.isaudit = 1 and tt.bsort = 2 ");
			if(!StringUtil.isEmpty(queryForm.getOrganId())){
				sql.append(" \r\n and tt.oid = ? ");param.put(UUID.randomUUID().toString(), queryForm.getOrganId()); //机构条件
			}
			if(!StringUtil.isEmpty(queryForm.getWarehouseId())){
				sql.append(" \r\n and tt.WAREHOUSEID = ? ");param.put(UUID.randomUUID().toString(), queryForm.getWarehouseId()); //仓库条件
			}
			if(!StringUtil.isEmpty(queryForm.getProductName()) && !StringUtil.isEmpty(queryForm.getPackSizeName())) { //产品条件
				sql.append(" \r\n and ttd.productid in (select id from product where productname = ? and specmode=?) ");param.put(UUID.randomUUID().toString(), queryForm.getProductName());param.put(UUID.randomUUID().toString(), queryForm.getPackSizeName());
			} else if(!StringUtil.isEmpty(queryForm.getProductName()) && StringUtil.isEmpty(queryForm.getPackSizeName())) { //产品条件
				sql.append(" \r\n and ttd.productid in (select id from product where productname = ?) ");param.put(UUID.randomUUID().toString(), queryForm.getProductName());
			}
			sql.append(" \r\n join STOCK_MOVE sm on sm.id=tt.billno");
			if(!StringUtil.isEmpty(queryForm.getHistoryDate())){
				sql.append(" \r\n and sm.SHIPMENTDATE >=to_date(?,'yyyy-MM-dd hh24:mi:ss') ");param.put(UUID.randomUUID().toString(), queryForm.getHistoryDate()); //开始时间条件
			}
			if(!StringUtil.isEmpty(queryForm.getEndDate())){
				sql.append(" \r\n and sm.SHIPMENTDATE < to_date(?,'yyyy-MM-dd hh24:mi:ss') ");param.put(UUID.randomUUID().toString(), queryForm.getEndDate()); //结束时间条件
			}
			sql.append(" \r\n join WAREHOUSE w on w.id = tt.warehouseid and w.USEFLAG = 1 ");
			sql.append(" \r\n INNER JOIN STOCK_WASTE_BOOK swb on swb.billcode = tt.id and ttd.productid = swb.productid ");
			sql.append(" \r\n INNER JOIN ORGAN o on tt.oid = o.id and o.organtype = 2 and o.organmodel = 1 and o.ISREPEAL = 0");
			sql.append(" \r\n LEFT JOIN F_UNIT f on ttd.productid = f.productid and ttd.unitid = f.funitid ");
			sql.append(" \r\n LEFT JOIN PRODUCT p on ttd.productid = p.id  ");
			sql.append(" \r\n LEFT JOIN PRODUCT_STRUCT pstr on p.PSID = pstr.STRUCTCODE  ");
			sql.append(" \r\n LEFT JOIN ORGAN ino on tt.inoid = ino.id and ino.organtype = 2 and ino.organmodel = 1");
			sql.append(" \r\n LEFT JOIN COUNTRY_AREA country on o.province=country.id  ");
			sql.append(" \r\n LEFT JOIN (SELECT max(REGIONCODEID) regioncodeid,max(AREAID) areaid  FROM REGION_AREA GROUP BY AREAID) ra on ra.areaid=country.id  ");
			sql.append(" \r\n LEFT JOIN REGION r on r.regioncode=ra.regioncodeid  ");
			if(DbUtil.isDealer(users)) {
				sql.append(" \r\n LEFT JOIN RULE_USER_WH rw on tt.warehouseid = rw.warehouse_id  ");
				sql.append(" \r\n where rw.user_id = ?");param.put(UUID.randomUUID().toString(), users.getUserid());
			} else {
				sql.append(" \r\n where "+DbUtil.getWhereCondition(users, "o"));
			}
			//大区条件
			if(!StringUtil.isEmpty(queryForm.getRegion())){
				sql.append(" \r\n and r.regioncode = ? "); param.put(UUID.randomUUID().toString(), queryForm.getRegion());
			}
			sql.append(" \r\n  and p.USEFLAG = 1 ");
			
			//#start modified by ryan.xi at 20150714
			//分包大包减销售
			sql.append(" \r\n union all");
			sql.append(" \r\n select DISTINCT tt.id billNo");
			sql.append(" \r\n ,r.sortname regionName");
			sql.append(" \r\n ,o.province provinceId");
			sql.append(" \r\n ,o.oecode oecode");
			sql.append(" \r\n ,country.areaname province");
			sql.append(" \r\n ,o.id organId ");
			sql.append(" \r\n ,o.organname organName");
			sql.append(" \r\n ,ttd.OUTPRODUCTID productId  ");
			sql.append(" \r\n ,pstr.sortname psName");
			sql.append(" \r\n ,p.productname productName");
			sql.append(" \r\n ,p.productnameen productNameen");
			sql.append(" \r\n ,p.mcode mCode");
			sql.append(" \r\n ,p.matericalchdes matericalChDes ");
			sql.append(" \r\n ,p.matericalendes matericalEnDes ");
			sql.append(" \r\n ,p.sunit unitId ");
			sql.append(" \r\n ,p.specmode packSizeName");
			sql.append(" \r\n ,p.packsizenameen packSizeNameEn ");
			sql.append(" \r\n ,ttd.outbatch batch ");
			sql.append(" \r\n ,-(ttd.outquantity-ttd.wastage) salesQuantity"); // 销售数量
			sql.append(" \r\n ,0 consumeQuantity"); //消耗数量
			sql.append(" \r\n ,tt.billdate makeDate,w.warehousename ");
//			sql.append(" \r\n ,pj.production_date produceDate ");
//			sql.append(" \r\n ,pj.expiry_date expiryDate ");
			sql.append(" \r\n from PACK_SEPARATE tt INNER JOIN PACK_SEPARATE_DETAIL ttd on tt.id = ttd.psid  and tt.isaudit = 1 ");
			if(!StringUtil.isEmpty(queryForm.getOrganId())){
				sql.append(" \r\n and tt.ORGANID = ? ");param.put(UUID.randomUUID().toString(), queryForm.getOrganId()); //机构条件
			}
			if(!StringUtil.isEmpty(queryForm.getWarehouseId())){
				sql.append(" \r\n and tt.WAREHOUSEID = ? ");param.put(UUID.randomUUID().toString(), queryForm.getWarehouseId()); //仓库条件
			}
			if(!StringUtil.isEmpty(queryForm.getProductName()) && !StringUtil.isEmpty(queryForm.getPackSizeName())) { //产品条件
				sql.append(" \r\n and ttd.OUTPRODUCTID in (select id from product where productname = ? and specmode= ?) ");param.put(UUID.randomUUID().toString(), queryForm.getProductName());param.put(UUID.randomUUID().toString(), queryForm.getPackSizeName());
			} else if(!StringUtil.isEmpty(queryForm.getProductName()) && StringUtil.isEmpty(queryForm.getPackSizeName())) { //产品条件
				sql.append(" \r\n and ttd.OUTPRODUCTID in (select id from product where productname = ?) ");param.put(UUID.randomUUID().toString(), queryForm.getProductName());
			}
			if(!StringUtil.isEmpty(queryForm.getHistoryDate())){
				sql.append(" \r\n and tt.auditdate >=to_date(?,'yyyy-MM-dd hh24:mi:ss') ");param.put(UUID.randomUUID().toString(), queryForm.getHistoryDate()); //开始时间条件
			}
			if(!StringUtil.isEmpty(queryForm.getEndDate())){
				sql.append(" \r\n and tt.auditdate < to_date(?,'yyyy-MM-dd hh24:mi:ss') ");param.put(UUID.randomUUID().toString(), queryForm.getEndDate()); //结束时间条件
			}
			sql.append(" \r\n join WAREHOUSE w on w.id = tt.warehouseid and w.USEFLAG = 1 ");
			sql.append(" \r\n INNER JOIN ORGAN o on tt.ORGANID = o.id and o.organtype = 2 and o.organmodel = 1 and o.ISREPEAL = 0");
			sql.append(" \r\n LEFT JOIN PRODUCT p on ttd.OUTPRODUCTID = p.id  ");
			sql.append(" \r\n LEFT JOIN PRODUCT_STRUCT pstr on p.PSID = pstr.STRUCTCODE  ");
			sql.append(" \r\n LEFT JOIN COUNTRY_AREA country on o.province=country.id  ");
			sql.append(" \r\n LEFT JOIN (SELECT max(REGIONCODEID) regioncodeid,max(AREAID) areaid  FROM REGION_AREA GROUP BY AREAID) ra on ra.areaid=country.id  ");
			sql.append(" \r\n LEFT JOIN REGION r on r.regioncode=ra.regioncodeid  ");
			if(DbUtil.isDealer(users)) {
				sql.append(" \r\n LEFT JOIN RULE_USER_WH rw on tt.warehouseid = rw.warehouse_id  ");
				sql.append(" \r\n where rw.user_id = ?");param.put(UUID.randomUUID().toString(), users.getUserid());
			} else {
				sql.append(" \r\n where "+DbUtil.getWhereCondition(users, "o"));
			}
			//大区条件
			if(!StringUtil.isEmpty(queryForm.getRegion())){
				sql.append(" \r\n and r.regioncode = ? "); param.put(UUID.randomUUID().toString(), queryForm.getRegion());
			}
			sql.append(" \r\n  and p.USEFLAG = 1 ");
			
			//分包小包加销售
			sql.append(" \r\n union all");
			sql.append(" \r\n select DISTINCT tt.id billNo");
			sql.append(" \r\n ,r.sortname regionName");
			sql.append(" \r\n ,o.province provinceId");
			sql.append(" \r\n ,o.oecode oecode");
			sql.append(" \r\n ,country.areaname province");
			sql.append(" \r\n ,o.id organId ");
			sql.append(" \r\n ,o.organname organName");
			sql.append(" \r\n ,ttd.INPRODUCTID productId  ");
			sql.append(" \r\n ,pstr.sortname psName");
			sql.append(" \r\n ,p.productname productName");
			sql.append(" \r\n ,p.productnameen productNameen");
			sql.append(" \r\n ,p.mcode mCode");
			sql.append(" \r\n ,p.matericalchdes matericalChDes ");
			sql.append(" \r\n ,p.matericalendes matericalEnDes ");
			sql.append(" \r\n ,p.sunit unitId ");
			sql.append(" \r\n ,p.specmode packSizeName");
			sql.append(" \r\n ,p.packsizenameen packSizeNameEn ");
			sql.append(" \r\n ,ttd.inbatch batch ");
			sql.append(" \r\n ,ttd.inquantity salesQuantity"); // 销售数量
			sql.append(" \r\n ,0 consumeQuantity"); //消耗数量
			sql.append(" \r\n ,tt.billdate makeDate,w.warehousename ");
//			sql.append(" \r\n ,pj.production_date produceDate ");
//			sql.append(" \r\n ,pj.expiry_date expiryDate ");
			sql.append(" \r\n from PACK_SEPARATE tt INNER JOIN PACK_SEPARATE_DETAIL ttd on tt.id = ttd.psid  and tt.isaudit = 1 ");
			if(!StringUtil.isEmpty(queryForm.getOrganId())){
				sql.append(" \r\n and tt.ORGANID = ? ");param.put(UUID.randomUUID().toString(), queryForm.getOrganId()); //机构条件
			}
			if(!StringUtil.isEmpty(queryForm.getWarehouseId())){
				sql.append(" \r\n and tt.WAREHOUSEID = ? ");param.put(UUID.randomUUID().toString(), queryForm.getWarehouseId()); //仓库条件
			}
			if(!StringUtil.isEmpty(queryForm.getProductName()) && !StringUtil.isEmpty(queryForm.getPackSizeName())) { //产品条件
				sql.append(" \r\n and ttd.INPRODUCTID in (select id from product where productname = ? and specmode= ?) ");param.put(UUID.randomUUID().toString(), queryForm.getProductName());param.put(UUID.randomUUID().toString(), queryForm.getPackSizeName());
			} else if(!StringUtil.isEmpty(queryForm.getProductName()) && StringUtil.isEmpty(queryForm.getPackSizeName())) { //产品条件
				sql.append(" \r\n and ttd.INPRODUCTID in (select id from product where productname = ?) ");param.put(UUID.randomUUID().toString(), queryForm.getProductName());
			}
			if(!StringUtil.isEmpty(queryForm.getHistoryDate())){
				sql.append(" \r\n and tt.auditdate >=to_date(?,'yyyy-MM-dd hh24:mi:ss') ");param.put(UUID.randomUUID().toString(), queryForm.getHistoryDate()); //开始时间条件
			}
			if(!StringUtil.isEmpty(queryForm.getEndDate())){
				sql.append(" \r\n and tt.auditdate < to_date(?,'yyyy-MM-dd hh24:mi:ss') ");param.put(UUID.randomUUID().toString(), queryForm.getEndDate()); //结束时间条件
			}
			sql.append(" \r\n join WAREHOUSE w on w.id = tt.warehouseid and w.USEFLAG = 1 ");
			sql.append(" \r\n INNER JOIN ORGAN o on tt.ORGANID = o.id and o.organtype = 2 and o.organmodel = 1 and o.ISREPEAL = 0");
			sql.append(" \r\n LEFT JOIN PRODUCT p on ttd.INPRODUCTID = p.id  ");
			sql.append(" \r\n LEFT JOIN PRODUCT_STRUCT pstr on p.PSID = pstr.STRUCTCODE  ");
			sql.append(" \r\n LEFT JOIN COUNTRY_AREA country on o.province=country.id  ");
			sql.append(" \r\n LEFT JOIN (SELECT max(REGIONCODEID) regioncodeid,max(AREAID) areaid  FROM REGION_AREA GROUP BY AREAID) ra on ra.areaid=country.id  ");
			sql.append(" \r\n LEFT JOIN REGION r on r.regioncode=ra.regioncodeid  ");
			if(DbUtil.isDealer(users)) {
				sql.append(" \r\n LEFT JOIN RULE_USER_WH rw on tt.warehouseid = rw.warehouse_id  ");
				sql.append(" \r\n where rw.user_id = ?");param.put(UUID.randomUUID().toString(), users.getUserid());
			} else {
				sql.append(" \r\n where "+DbUtil.getWhereCondition(users, "o"));
			}
			//大区条件
			if(!StringUtil.isEmpty(queryForm.getRegion())){
				sql.append(" \r\n and r.regioncode = ? "); param.put(UUID.randomUUID().toString(), queryForm.getRegion());
			}
			//#end modified by ryan.xi at 20150714
			sql.append(" \r\n  and p.USEFLAG = 1 ");
			
			//工厂退回--减销售
			sql.append(" \r\n union all");
			
			sql.append(" \r\n select DISTINCT tt.billNo billNo");
			sql.append(" \r\n ,r.sortname regionName");
			sql.append(" \r\n ,o.province provinceId");
			sql.append(" \r\n ,o.oecode oecode");
			sql.append(" \r\n ,country.areaname province");
			sql.append(" \r\n ,tt.oid organId ");
			sql.append(" \r\n ,o.organname organName");
			sql.append(" \r\n ,ttd.productid productId  ");
			sql.append(" \r\n ,pstr.sortname psName");
			sql.append(" \r\n ,p.productname productName");
			sql.append(" \r\n ,p.productnameen productNameen");
			sql.append(" \r\n ,p.mcode mCode");
			sql.append(" \r\n ,p.matericalchdes matericalChDes ");
			sql.append(" \r\n ,p.matericalendes matericalEnDes ");
			sql.append(" \r\n ,p.sunit unitId ");
			sql.append(" \r\n ,p.specmode packSizeName");
			sql.append(" \r\n ,p.packsizenameen packSizeNameEn ");
			sql.append(" \r\n ,swb.batch batch ");
			sql.append(" \r\n ,-swb.cycleoutquantity salesQuantity"); // 销售数量
			sql.append(" \r\n ,0 consumeQuantity"); //消耗数量
			sql.append(" \r\n ,tt.auditdate makeDate,w.warehousename");
			
			sql.append(" \r\n from TAKE_TICKET tt INNER JOIN TAKE_TICKET_DETAIL ttd on tt.id = ttd.ttid  and tt.isaudit = 1  and tt.bsort = 7 and tt.billno like 'PW%' ");
			if(!StringUtil.isEmpty(queryForm.getOrganId())){
				sql.append(" \r\n and tt.oid = ? ");param.put(UUID.randomUUID().toString(), queryForm.getOrganId()); //机构条件
			}
			if(!StringUtil.isEmpty(queryForm.getWarehouseId())){
				sql.append(" \r\n and tt.WAREHOUSEID = ? ");param.put(UUID.randomUUID().toString(), queryForm.getWarehouseId()); //仓库条件
			}
			if(!StringUtil.isEmpty(queryForm.getProductName()) && !StringUtil.isEmpty(queryForm.getPackSizeName())) { //产品条件
				sql.append(" \r\n and ttd.productid in (select id from product where productname = ? and specmode=?) ");param.put(UUID.randomUUID().toString(), queryForm.getProductName());param.put(UUID.randomUUID().toString(), queryForm.getPackSizeName());
			} else if(!StringUtil.isEmpty(queryForm.getProductName()) && StringUtil.isEmpty(queryForm.getPackSizeName())) { //产品条件
				sql.append(" \r\n and ttd.productid in (select id from product where productname = ?) ");param.put(UUID.randomUUID().toString(), queryForm.getProductName());
			}
			if(!StringUtil.isEmpty(queryForm.getHistoryDate())){
				sql.append(" \r\n and tt.auditdate >=to_date(?,'yyyy-MM-dd hh24:mi:ss') ");param.put(UUID.randomUUID().toString(), queryForm.getHistoryDate()); //开始时间条件
			}
			if(!StringUtil.isEmpty(queryForm.getEndDate())){
				sql.append(" \r\n and tt.auditdate < to_date(?,'yyyy-MM-dd hh24:mi:ss') ");param.put(UUID.randomUUID().toString(), queryForm.getEndDate()); //结束时间条件
			}
			/*
			sql.append(" \r\n  join ORGAN_WITHDRAW ow on tt.billno = ow.id");
			if(!StringUtil.isEmpty(queryForm.getHistoryDate())){
				sql.append(" \r\n and ow.makedate >=to_date('" + queryForm.getHistoryDate() + "','yyyy-MM-dd hh24:mi:ss') "); //开始时间条件
			}
			if(!StringUtil.isEmpty(queryForm.getEndDate())){
				sql.append(" \r\n and ow.makedate < to_date('" + queryForm.getEndDate() + "','yyyy-MM-dd hh24:mi:ss') "); //结束时间条件
			}*/
			sql.append(" \r\n join WAREHOUSE w on w.id = tt.warehouseid and w.USEFLAG = 1 ");
			sql.append(" \r\n INNER JOIN STOCK_WASTE_BOOK swb on swb.billcode = tt.id and ttd.productid = swb.productid   ");
			sql.append(" \r\n INNER JOIN ORGAN o on tt.oid = o.id and o.organtype = 2 and  organmodel = 1 and o.ISREPEAL = 0");
			sql.append(" \r\n LEFT JOIN F_UNIT f on ttd.productid = f.productid and ttd.unitid = f.funitid ");
			sql.append(" \r\n LEFT JOIN PRODUCT p on ttd.productid = p.id  ");
			sql.append(" \r\n LEFT JOIN PRODUCT_STRUCT pstr on p.PSID = pstr.STRUCTCODE  ");
			sql.append(" \r\n LEFT JOIN COUNTRY_AREA country on o.province=country.id  ");
			sql.append(" \r\n LEFT JOIN (SELECT max(REGIONCODEID) regioncodeid,max(AREAID) areaid  FROM REGION_AREA GROUP BY AREAID) ra on ra.areaid=country.id  ");
			sql.append(" \r\n LEFT JOIN REGION r on r.regioncode=ra.regioncodeid  ");
			if(DbUtil.isDealer(users)) {
				sql.append(" \r\n LEFT JOIN RULE_USER_WH rw on tt.warehouseid = rw.warehouse_id  ");
				sql.append(" \r\n where rw.user_id = ?");param.put(UUID.randomUUID().toString(), users.getUserid());
			} else {
				sql.append(" \r\n where "+DbUtil.getWhereCondition(users, "o"));
			}
			//大区条件
			if(!StringUtil.isEmpty(queryForm.getRegion())){
				sql.append(" \r\n and r.regioncode = ? "); param.put(UUID.randomUUID().toString(), queryForm.getRegion());
			}
			sql.append(" \r\n  and p.USEFLAG = 1 ");
			//#start modified by ryan.xi at 20150805
			//采购入库-加销售-20150805
			sql.append(" \r\n union all");
			
			sql.append(" \r\n select DISTINCT oia.id billNo ");
			sql.append(" \r\n ,r.sortname regionName  ");
			sql.append(" \r\n ,o.province provinceId  ");
			sql.append(" \r\n ,o.oecode oecode ");
			sql.append(" \r\n ,country.areaname province  ");
			sql.append(" \r\n ,o.id  organId");
			sql.append(" \r\n ,o.organname organName  ");
			sql.append(" \r\n ,oida.productid productId ");
			sql.append(" \r\n ,pstr.sortname psName ");
			sql.append(" \r\n ,p.productname productName ");
			sql.append(" \r\n  ,p.productnameen productNameen  ");
			sql.append(" \r\n ,p.mcode mCode ");
			sql.append(" \r\n ,p.matericalchdes matericalChDes  ");
			sql.append(" \r\n ,p.matericalendes matericalEnDes  ");
			sql.append(" \r\n ,p.sunit unitId  ");
			sql.append(" \r\n ,p.packsizename packSizeName ");
			sql.append(" \r\n  ,p.packsizenameen packSizeNameEn  ");
			sql.append(" \r\n ,oida.batch batch  ");
			sql.append(" \r\n ,(oida.quantity * f.xquantity) salesQuantity  ");
			sql.append(" \r\n ,0 consumeQuantity  ");
			sql.append(" \r\n ,oia.AUDITDATE makeDate,w.warehousename  ");
			sql.append(" \r\n from OTHER_INCOME_ALL oia INNER JOIN OTHER_INCOME_DETAIL_ALL oida on oia.id = oida.oiid and oia.isaudit = 1 and oia.incomesort = 6");
			if(!StringUtil.isEmpty(queryForm.getOrganId())){
				sql.append(" \r\n and oia.organid = ? ");param.put(UUID.randomUUID().toString(), queryForm.getOrganId()); //机构条件
			}
			if(!StringUtil.isEmpty(queryForm.getWarehouseId())){
				sql.append(" \r\n and oia.warehouseid = ? ");param.put(UUID.randomUUID().toString(), queryForm.getWarehouseId()); //仓库条件
			}
			if(!StringUtil.isEmpty(queryForm.getProductName()) && !StringUtil.isEmpty(queryForm.getPackSizeName())) { //产品条件
				sql.append(" \r\n and oida.productid in (select id from product where productname = ? and packsizename= ?) ");param.put(UUID.randomUUID().toString(), queryForm.getProductName());param.put(UUID.randomUUID().toString(), queryForm.getPackSizeName());
			} else if(!StringUtil.isEmpty(queryForm.getProductName()) && StringUtil.isEmpty(queryForm.getPackSizeName())) { //产品条件
				sql.append(" \r\n and oida.productid in (select id from product where productname = ?) ");param.put(UUID.randomUUID().toString(), queryForm.getProductName());
			}
			if(!StringUtil.isEmpty(queryForm.getHistoryDate())){
				sql.append(" \r\n and oia.auditdate >=to_date(?,'yyyy-MM-dd hh24:mi:ss') ");param.put(UUID.randomUUID().toString(), queryForm.getHistoryDate()); //开始时间条件
			}
			if(!StringUtil.isEmpty(queryForm.getEndDate())){
				sql.append(" \r\n and oia.auditdate < to_date(?,'yyyy-MM-dd hh24:mi:ss') ");param.put(UUID.randomUUID().toString(), queryForm.getEndDate()); //结束时间条件
			}
			sql.append(" \r\n join WAREHOUSE w on w.id = oia.warehouseid and w.USEFLAG = 1 ");
			sql.append(" \r\n INNER JOIN ORGAN o on oia.organid= o.id and o.organtype = 2 and  organmodel = 1 and o.ISREPEAL = 0");
			sql.append(" \r\n LEFT JOIN F_UNIT f on oida.productid = f.productid and oida.unitid = f.funitid ");
			sql.append(" \r\n LEFT JOIN PRODUCT p on oida.productid = p.id ");
			sql.append(" \r\n LEFT JOIN PRODUCT_STRUCT pstr on p.PSID = pstr.STRUCTCODE   ");
			sql.append(" \r\n LEFT JOIN COUNTRY_AREA country on o.province=country.id   ");
			sql.append(" \r\n LEFT JOIN (SELECT max(REGIONCODEID) regioncodeid,max(AREAID) areaid  FROM REGION_AREA GROUP BY AREAID) ra on ra.areaid=country.id   ");
			sql.append(" \r\n LEFT JOIN REGION r on r.regioncode=ra.regioncodeid   ");
			if(DbUtil.isDealer(users)) {
				sql.append(" \r\n INNER JOIN RULE_USER_WH rw on oia.warehouseid = rw.warehouse_id ");
				sql.append(" \r\n where rw.user_id = ?");param.put(UUID.randomUUID().toString(), users.getUserid());
			} else {
				sql.append(" \r\n where "+DbUtil.getWhereCondition(users, "o"));
			}
			if(!StringUtil.isEmpty(queryForm.getRegion())){
				sql.append(" \r\n and r.regioncode = ? "); param.put(UUID.randomUUID().toString(), queryForm.getRegion());
			}
			sql.append(" \r\n  and p.USEFLAG = 1 ");
			//#end modified by ryan.xi at 20150805
			sql.append(" \r\n union all");
			// 历史销售数据
			sql.append(" \r\n select sdh.billNo billNo");
			sql.append(" \r\n ,r.sortname regionName");
			sql.append(" \r\n ,o.province provinceId");
			sql.append(" \r\n ,o.oecode oecode");
			sql.append(" \r\n ,ca.areaname province");
			sql.append(" \r\n ,o.id organId ");
			sql.append(" \r\n ,o.organname organName");
			sql.append(" \r\n ,p.id productId  ");
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
			sql.append(" \r\n join organ o on o.id = sdh.organid and o.isrepeal=0 ");
			if(DbUtil.isDealer(users)) {
				sql.append(" \r\n INNER JOIN RULE_USER_WH rw on sdh.warehouseid = rw.warehouse_id ");
				sql.append(" \r\n and rw.user_id = ?");param.put(UUID.randomUUID().toString(), users.getUserid()); 
			} else {
				sql.append(" \r\n and "+DbUtil.getWhereCondition(users, "o"));
			}
			if(!StringUtil.isEmpty(queryForm.getOrganId())){
				sql.append(" \r\n and sdh.organid = ? ");param.put(UUID.randomUUID().toString(), queryForm.getOrganId()); //机构条件
			}
			if(!StringUtil.isEmpty(queryForm.getWarehouseId())){
				sql.append(" \r\n and sdh.warehouseid = ? ");param.put(UUID.randomUUID().toString(), queryForm.getWarehouseId()); //仓库条件
			}
			if(!StringUtil.isEmpty(queryForm.getBeginDate())){
				sql.append(" \r\n and sdh.makeDate >=to_date(?,'yyyy-MM-dd hh24:mi:ss') ");param.put(UUID.randomUUID().toString(), queryForm.getBeginDate()); //开始时间条件
			}
			if(!StringUtil.isEmpty(queryForm.getHistoryDate())){
				sql.append(" \r\n and sdh.makeDate < to_date(?,'yyyy-MM-dd hh24:mi:ss') ");param.put(UUID.randomUUID().toString(), queryForm.getHistoryDate()); //结束时间条件
			}
			sql.append(" \r\n join PRODUCT p on p.id = sdh.productid and p.useflag=1");
			if(!StringUtil.isEmpty(queryForm.getProductName()) && !StringUtil.isEmpty(queryForm.getPackSizeName())) { //产品条件
				sql.append(" \r\n and p.productname = ? and packsizename= ? ");param.put(UUID.randomUUID().toString(), queryForm.getProductName());param.put(UUID.randomUUID().toString(), queryForm.getPackSizeName());
			} else if(!StringUtil.isEmpty(queryForm.getProductName()) && StringUtil.isEmpty(queryForm.getPackSizeName())) { //产品条件
				sql.append(" \r\n and p.productname = ? ");param.put(UUID.randomUUID().toString(), queryForm.getProductName());
			}
			sql.append(" \r\n join WAREHOUSE w on w.id = sdh.warehouseid and w.useflag=1");
			sql.append(" \r\n join PRODUCT_STRUCT pstr on pstr.structcode=p.psid");
			sql.append(" \r\n LEFT JOIN COUNTRY_AREA ca on ca.id = o.province");
			sql.append(" \r\n LEFT JOIN REGION_AREA ra on RA.AREAID=o.province");
			sql.append(" \r\n LEFT JOIN REGION r on r.regioncode = ra.regioncodeid");
			//大区条件
			if(!StringUtil.isEmpty(queryForm.getRegion())){
				sql.append(" \r\n where r.regioncode = ? ");param.put(UUID.randomUUID().toString(), queryForm.getRegion()); 
			}
		}
//		System.out.println(sql.toString());
		logger.debug("querySalesDetailReportFromHistoryAndBill-sql:"+sql.toString());
		List<Map> list =  new ArrayList<Map>();
		if(request == null && pageSize == 0){
			list =  EntityManager.jdbcquery(sql.toString(), param);
		}else {
			list =  PageQuery.jdbcSqlserverQuery(request,"billNo", sql.toString(), pageSize, param);
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
				scForm.setReportDate(new Timestamp(System.currentTimeMillis()));
				//单据日期
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

	// 根据条件查询
	public List<SalesConsumeDetailReportForm> querySalesDetailReport2(HttpServletRequest request, int pageSize,SalesConsumeDetailReportForm queryForm,UsersBean users) throws Exception{
		Map<String, Object> param = new LinkedHashMap<String, Object>();
		//对于结束日期增加一天
		if(!StringUtil.isEmpty(queryForm.getEndDate())){
			String endDateStr = queryForm.getEndDate();
			Date endDate = Dateutil.StringToDate(endDateStr);
			endDate = Dateutil.addDay2Date(1, endDate);
			queryForm.setEndDate(Dateutil.formatDate(endDate));
		}
		
		List<SalesConsumeDetailReportForm> resultList = new ArrayList<SalesConsumeDetailReportForm>();
		StringBuffer sql = new StringBuffer();
		// 工厂给PD
		sql.append(" \r\n select DISTINCT tt.billNo billNo");
		sql.append(" \r\n ,r.sortname regionName");
		sql.append(" \r\n ,ino.province provinceId");
		sql.append(" \r\n ,ino.oecode oecode");
		sql.append(" \r\n ,country.areaname province");
		sql.append(" \r\n ,tt.inoid organId ");
		sql.append(" \r\n ,ino.organname organName");
		sql.append(" \r\n ,ttd.productid productId  ");
		sql.append(" \r\n ,pstr.sortname psName");
		sql.append(" \r\n ,p.productname productName");
		sql.append(" \r\n ,p.productnameen productNameen ");
		sql.append(" \r\n ,p.mcode mCode");
		sql.append(" \r\n ,p.matericalchdes matericalChDes ");
		sql.append(" \r\n ,p.matericalendes matericalEnDes ");
		sql.append(" \r\n ,p.sunit unitId ");
		sql.append(" \r\n ,p.specmode packSizeName");
		sql.append(" \r\n ,p.packsizenameen packSizeNameEn");
		sql.append(" \r\n ,swb.batch batch ");
		sql.append(" \r\n ,swb.cycleoutquantity salesQuantity"); // 销售数量
		sql.append(" \r\n ,0 consumeQuantity"); //消耗数量
		sql.append(" \r\n ,sam.movedate makeDate ,w.warehousename");
//		sql.append(" \r\n ,pj.production_date produceDate ");
//		sql.append(" \r\n ,pj.expiry_date expiryDate ");
		sql.append(" \r\n from TAKE_TICKET tt INNER JOIN TAKE_TICKET_DETAIL ttd on tt.id = ttd.ttid  and tt.isaudit = 1  and tt.bsort = 1 ");
		if(!StringUtil.isEmpty(queryForm.getOrganId())){
			sql.append(" \r\n and tt.inoid = ? ");param.put(UUID.randomUUID().toString(), queryForm.getOrganId()); //机构条件
		}
		if(!StringUtil.isEmpty(queryForm.getWarehouseId())){
			sql.append(" \r\n and tt.inwarehouseid = ? ");param.put(UUID.randomUUID().toString(), queryForm.getWarehouseId()); //仓库条件
		}
		if(!StringUtil.isEmpty(queryForm.getProductName()) && !StringUtil.isEmpty(queryForm.getPackSizeName())) { //产品条件
			sql.append(" \r\n and ttd.productid in (select id from product where productname = ? and specmode=?) ");param.put(UUID.randomUUID().toString(), queryForm.getProductName());param.put(UUID.randomUUID().toString(), queryForm.getPackSizeName());
		} else if(!StringUtil.isEmpty(queryForm.getProductName()) && StringUtil.isEmpty(queryForm.getPackSizeName())) { //产品条件
			sql.append(" \r\n and ttd.productid in (select id from product where productname = ?) ");param.put(UUID.randomUUID().toString(), queryForm.getProductName());
		}
		//过滤拒签的单据
		sql.append(" \r\n join STOCK_ALTER_MOVE sam on sam.id=tt.billno and sam.isblankout = 0");
		if(!StringUtil.isEmpty(queryForm.getBeginDate())){
			sql.append(" \r\n and sam.movedate >=to_date(?,'yyyy-MM-dd hh24:mi:ss') ");param.put(UUID.randomUUID().toString(), queryForm.getBeginDate()); //开始时间条件
		}
		if(!StringUtil.isEmpty(queryForm.getEndDate())){
			sql.append(" \r\n and sam.movedate < to_date(?,'yyyy-MM-dd hh24:mi:ss') ");param.put(UUID.randomUUID().toString(), queryForm.getEndDate()); //结束时间条件
		}
		sql.append(" \r\n join WAREHOUSE w on w.id = tt.inwarehouseid and w.USEFLAG = 1 ");
		sql.append(" \r\n INNER JOIN STOCK_WASTE_BOOK swb on swb.billcode = tt.id and ttd.productid = swb.productid ");
		sql.append(" \r\n INNER JOIN ORGAN o on tt.oid = o.id and o.organtype = 1  ");
		sql.append(" \r\n INNER JOIN ORGAN ino on tt.inoid = ino.id and ino.ISREPEAL = 0 ");
		//机构类型条件
		if(queryForm.getOrganType() == 1) {
			sql.append(" \r\n and ino.organtype = 1 ");//工厂
		} else if(queryForm.getOrganType() ==2){
			sql.append(" \r\n and ino.organtype = 2 and ino.organmodel = 1 ");//经销商
		} else {
			sql.append(" \r\n and ((ino.organtype = 1) or (ino.organtype = 2 and ino.organmodel = 1)) ");
		}
		sql.append(" \r\n LEFT JOIN F_UNIT f on ttd.productid = f.productid and ttd.unitid = f.funitid ");
		sql.append(" \r\n LEFT JOIN PRODUCT p on ttd.productid = p.id  ");
		sql.append(" \r\n LEFT JOIN PRODUCT_STRUCT pstr on p.PSID = pstr.STRUCTCODE  ");
		
		sql.append(" \r\n LEFT JOIN COUNTRY_AREA country on ino.province=country.id  ");
		
		sql.append(" \r\n LEFT JOIN (SELECT max(REGIONCODEID) regioncodeid,max(AREAID) areaid  FROM REGION_AREA GROUP BY AREAID) ra on ra.areaid=country.id  ");
		sql.append(" \r\n LEFT JOIN REGION r on r.regioncode=ra.regioncodeid  ");
		if(DbUtil.isDealer(users)) {
			sql.append(" \r\n LEFT JOIN RULE_USER_WH rw on tt.inwarehouseid = rw.warehouse_id  ");
			sql.append(" \r\n where rw.user_id = ?");param.put(UUID.randomUUID().toString(), users.getUserid());
		} else {
			sql.append(" \r\n where "+DbUtil.getWhereCondition(users, "ino"));
		}
		//大区条件
		if(!StringUtil.isEmpty(queryForm.getRegion())){
			sql.append(" \r\n and r.regioncode = ? "); param.put(UUID.randomUUID().toString(), queryForm.getRegion());
		}
		sql.append(" \r\n  and p.USEFLAG = 1 ");
		if(queryForm.getOrganType() != 1) {
			sql.append(" \r\n union all");
			//PD调拨回来的
			sql.append(" \r\n select DISTINCT tt.billNo billNo");
			sql.append(" \r\n ,r.sortname regionName");
			sql.append(" \r\n ,ino.province provinceId");
			sql.append(" \r\n ,ino.oecode oecode");
			sql.append(" \r\n ,country.areaname province");
			sql.append(" \r\n ,tt.inoid organId ");
			sql.append(" \r\n ,ino.organname organName");
			sql.append(" \r\n ,ttd.productid productId  ");
			sql.append(" \r\n ,pstr.sortname psName");
			sql.append(" \r\n ,p.productname productName");
			sql.append(" \r\n  ,p.productnameen productNameen ");
			sql.append(" \r\n ,p.mcode mCode");
			sql.append(" \r\n ,p.matericalchdes matericalChDes ");
			sql.append(" \r\n ,p.matericalendes matericalEnDes ");
			sql.append(" \r\n ,p.sunit unitId ");
			sql.append(" \r\n ,p.specmode packSizeName");
			sql.append(" \r\n  ,p.packsizenameen packSizeNameEn");
			sql.append(" \r\n ,swb.batch batch ");
			sql.append(" \r\n ,swb.cycleoutquantity salesQuantity"); // 销售数量
			sql.append(" \r\n ,0 consumeQuantity"); //消耗数量
			sql.append(" \r\n ,sm.movedate makeDate,w.warehousename ");
//			sql.append(" \r\n ,pj.production_date produceDate ");
//			sql.append(" \r\n ,pj.expiry_date expiryDate ");
			sql.append(" \r\n from TAKE_TICKET tt INNER JOIN TAKE_TICKET_DETAIL ttd on tt.id = ttd.ttid  and tt.isaudit = 1 and tt.bsort = 2 ");
			if(!StringUtil.isEmpty(queryForm.getOrganId())){
				sql.append(" \r\n and tt.inoid = ? ");param.put(UUID.randomUUID().toString(), queryForm.getOrganId()); //机构条件
			}
			if(!StringUtil.isEmpty(queryForm.getWarehouseId())){
				sql.append(" \r\n and tt.inwarehouseid = ? ");param.put(UUID.randomUUID().toString(), queryForm.getWarehouseId()); //仓库条件
			}
			if(!StringUtil.isEmpty(queryForm.getProductName()) && !StringUtil.isEmpty(queryForm.getPackSizeName())) { //产品条件
				sql.append(" \r\n and ttd.productid in (select id from product where productname = ? and specmode=?) ");param.put(UUID.randomUUID().toString(), queryForm.getProductName());param.put(UUID.randomUUID().toString(), queryForm.getPackSizeName());
			} else if(!StringUtil.isEmpty(queryForm.getProductName()) && StringUtil.isEmpty(queryForm.getPackSizeName())) { //产品条件
				sql.append(" \r\n and ttd.productid in (select id from product where productname = ?) ");param.put(UUID.randomUUID().toString(), queryForm.getProductName());
			}
			sql.append(" \r\n join STOCK_MOVE sm on sm.id=tt.billno");
			if(!StringUtil.isEmpty(queryForm.getBeginDate())){
				sql.append(" \r\n and sm.SHIPMENTDATE >=to_date(?,'yyyy-MM-dd hh24:mi:ss') ");param.put(UUID.randomUUID().toString(), queryForm.getBeginDate()); //开始时间条件
			}
			if(!StringUtil.isEmpty(queryForm.getEndDate())){
				sql.append(" \r\n and sm.SHIPMENTDATE < to_date(?,'yyyy-MM-dd hh24:mi:ss') ");param.put(UUID.randomUUID().toString(), queryForm.getEndDate()); //结束时间条件
			}
			sql.append(" \r\n join WAREHOUSE w on w.id = tt.inwarehouseid and w.USEFLAG = 1 ");
			sql.append(" \r\n INNER JOIN STOCK_WASTE_BOOK swb on swb.billcode = tt.id and ttd.productid = swb.productid ");
			sql.append(" \r\n INNER JOIN ORGAN o on tt.oid = o.id and o.organtype = 2 and o.organmodel = 1 ");
			sql.append(" \r\n LEFT JOIN F_UNIT f on ttd.productid = f.productid and ttd.unitid = f.funitid ");
			sql.append(" \r\n LEFT JOIN PRODUCT p on ttd.productid = p.id  ");
			sql.append(" \r\n LEFT JOIN PRODUCT_STRUCT pstr on p.PSID = pstr.STRUCTCODE  ");
			sql.append(" \r\n LEFT JOIN ORGAN ino on tt.inoid = ino.id and ino.organtype = 2 and ino.organmodel = 1 and ino.ISREPEAL = 0");
			sql.append(" \r\n LEFT JOIN COUNTRY_AREA country on ino.province=country.id  ");
			sql.append(" \r\n LEFT JOIN (SELECT max(REGIONCODEID) regioncodeid,max(AREAID) areaid  FROM REGION_AREA GROUP BY AREAID) ra on ra.areaid=country.id  ");
			sql.append(" \r\n LEFT JOIN REGION r on r.regioncode=ra.regioncodeid  ");
			if(DbUtil.isDealer(users)) {
				sql.append(" \r\n LEFT JOIN RULE_USER_WH rw on tt.inwarehouseid = rw.warehouse_id  ");
				sql.append(" \r\n where rw.user_id = ?");param.put(UUID.randomUUID().toString(), users.getUserid());
			} else {
				sql.append(" \r\n where "+DbUtil.getWhereCondition(users, "o"));
			}
			//大区条件
			if(!StringUtil.isEmpty(queryForm.getRegion())){
				sql.append(" \r\n and r.regioncode = ? "); param.put(UUID.randomUUID().toString(), queryForm.getRegion());
			}
			sql.append(" \r\n  and p.USEFLAG = 1 ");
			sql.append(" \r\n union all");
			//调拨给其他PD的
			sql.append(" \r\n select DISTINCT tt.billNo billNo");
			sql.append(" \r\n ,r.sortname regionName");
			sql.append(" \r\n ,o.province provinceId");
			sql.append(" \r\n ,o.oecode oecode");
			sql.append(" \r\n ,country.areaname province");
			sql.append(" \r\n ,tt.oid organId ");
			sql.append(" \r\n ,o.organname organName");
			sql.append(" \r\n ,ttd.productid productId  ");
			sql.append(" \r\n ,pstr.sortname psName");
			sql.append(" \r\n ,p.productname productName");
			sql.append(" \r\n ,p.productnameen productNameen");
			sql.append(" \r\n ,p.mcode mCode");
			sql.append(" \r\n ,p.matericalchdes matericalChDes ");
			sql.append(" \r\n ,p.matericalendes matericalEnDes ");
			sql.append(" \r\n ,p.sunit unitId ");
			sql.append(" \r\n ,p.specmode packSizeName");
			sql.append(" \r\n ,p.packsizenameen packSizeNameEn ");
			sql.append(" \r\n ,swb.batch batch ");
			sql.append(" \r\n ,-swb.cycleoutquantity salesQuantity"); // 销售数量
			sql.append(" \r\n ,0 consumeQuantity"); //消耗数量
			sql.append(" \r\n ,sm.movedate makeDate,w.warehousename ");
//			sql.append(" \r\n ,pj.production_date produceDate ");
//			sql.append(" \r\n ,pj.expiry_date expiryDate ");
			sql.append(" \r\n from TAKE_TICKET tt INNER JOIN TAKE_TICKET_DETAIL ttd on tt.id = ttd.ttid  and tt.isaudit = 1 and tt.bsort = 2 ");
			if(!StringUtil.isEmpty(queryForm.getOrganId())){
				sql.append(" \r\n and tt.oid = ? ");param.put(UUID.randomUUID().toString(), queryForm.getOrganId()); //机构条件
			}
			if(!StringUtil.isEmpty(queryForm.getWarehouseId())){
				sql.append(" \r\n and tt.WAREHOUSEID = ? ");param.put(UUID.randomUUID().toString(), queryForm.getWarehouseId()); //仓库条件
			}
			if(!StringUtil.isEmpty(queryForm.getProductName()) && !StringUtil.isEmpty(queryForm.getPackSizeName())) { //产品条件
				sql.append(" \r\n and ttd.productid in (select id from product where productname = ? and specmode=?) ");param.put(UUID.randomUUID().toString(), queryForm.getProductName());param.put(UUID.randomUUID().toString(), queryForm.getPackSizeName());
			} else if(!StringUtil.isEmpty(queryForm.getProductName()) && StringUtil.isEmpty(queryForm.getPackSizeName())) { //产品条件
				sql.append(" \r\n and ttd.productid in (select id from product where productname = ?) ");param.put(UUID.randomUUID().toString(), queryForm.getProductName());
			}
			sql.append(" \r\n join STOCK_MOVE sm on sm.id=tt.billno");
			if(!StringUtil.isEmpty(queryForm.getBeginDate())){
				sql.append(" \r\n and sm.SHIPMENTDATE >=to_date(?,'yyyy-MM-dd hh24:mi:ss') ");param.put(UUID.randomUUID().toString(), queryForm.getBeginDate()); //开始时间条件
			}
			if(!StringUtil.isEmpty(queryForm.getEndDate())){
				sql.append(" \r\n and sm.SHIPMENTDATE < to_date(?,'yyyy-MM-dd hh24:mi:ss') ");param.put(UUID.randomUUID().toString(), queryForm.getEndDate()); //结束时间条件
			}
			sql.append(" \r\n join WAREHOUSE w on w.id = tt.warehouseid and w.USEFLAG = 1 ");
			sql.append(" \r\n INNER JOIN STOCK_WASTE_BOOK swb on swb.billcode = tt.id and ttd.productid = swb.productid ");
			sql.append(" \r\n INNER JOIN ORGAN o on tt.oid = o.id and o.organtype = 2 and o.organmodel = 1 and o.ISREPEAL = 0");
			sql.append(" \r\n LEFT JOIN F_UNIT f on ttd.productid = f.productid and ttd.unitid = f.funitid ");
			sql.append(" \r\n LEFT JOIN PRODUCT p on ttd.productid = p.id  ");
			sql.append(" \r\n LEFT JOIN PRODUCT_STRUCT pstr on p.PSID = pstr.STRUCTCODE  ");
			sql.append(" \r\n LEFT JOIN ORGAN ino on tt.inoid = ino.id and ino.organtype = 2 and ino.organmodel = 1");
			sql.append(" \r\n LEFT JOIN COUNTRY_AREA country on o.province=country.id  ");
			sql.append(" \r\n LEFT JOIN (SELECT max(REGIONCODEID) regioncodeid,max(AREAID) areaid  FROM REGION_AREA GROUP BY AREAID) ra on ra.areaid=country.id  ");
			sql.append(" \r\n LEFT JOIN REGION r on r.regioncode=ra.regioncodeid  ");
			if(DbUtil.isDealer(users)) {
				sql.append(" \r\n LEFT JOIN RULE_USER_WH rw on tt.warehouseid = rw.warehouse_id  ");
				sql.append(" \r\n where rw.user_id = ?");param.put(UUID.randomUUID().toString(), users.getUserid());
			} else {
				sql.append(" \r\n where "+DbUtil.getWhereCondition(users, "o"));
			}
			//大区条件
			if(!StringUtil.isEmpty(queryForm.getRegion())){
				sql.append(" \r\n and r.regioncode = ? "); param.put(UUID.randomUUID().toString(), queryForm.getRegion());
			}
			sql.append(" \r\n  and p.USEFLAG = 1 ");
			
			//#start modified by ryan.xi at 20150714
			//分包大包减销售
			sql.append(" \r\n union all");
			sql.append(" \r\n select DISTINCT tt.id billNo");
			sql.append(" \r\n ,r.sortname regionName");
			sql.append(" \r\n ,o.province provinceId");
			sql.append(" \r\n ,o.oecode oecode");
			sql.append(" \r\n ,country.areaname province");
			sql.append(" \r\n ,o.id organId ");
			sql.append(" \r\n ,o.organname organName");
			sql.append(" \r\n ,ttd.OUTPRODUCTID productId  ");
			sql.append(" \r\n ,pstr.sortname psName");
			sql.append(" \r\n ,p.productname productName");
			sql.append(" \r\n ,p.productnameen productNameen");
			sql.append(" \r\n ,p.mcode mCode");
			sql.append(" \r\n ,p.matericalchdes matericalChDes ");
			sql.append(" \r\n ,p.matericalendes matericalEnDes ");
			sql.append(" \r\n ,p.sunit unitId ");
			sql.append(" \r\n ,p.specmode packSizeName");
			sql.append(" \r\n ,p.packsizenameen packSizeNameEn ");
			sql.append(" \r\n ,ttd.outbatch batch ");
			sql.append(" \r\n ,-(ttd.outquantity-ttd.wastage) salesQuantity"); // 销售数量
			sql.append(" \r\n ,0 consumeQuantity"); //消耗数量
			sql.append(" \r\n ,tt.billdate makeDate,w.warehousename ");
//			sql.append(" \r\n ,pj.production_date produceDate ");
//			sql.append(" \r\n ,pj.expiry_date expiryDate ");
			sql.append(" \r\n from PACK_SEPARATE tt INNER JOIN PACK_SEPARATE_DETAIL ttd on tt.id = ttd.psid  and tt.isaudit = 1 ");
			if(!StringUtil.isEmpty(queryForm.getOrganId())){
				sql.append(" \r\n and tt.ORGANID = ? ");param.put(UUID.randomUUID().toString(), queryForm.getOrganId()); //机构条件
			}
			if(!StringUtil.isEmpty(queryForm.getWarehouseId())){
				sql.append(" \r\n and tt.WAREHOUSEID = ? ");param.put(UUID.randomUUID().toString(), queryForm.getWarehouseId()); //仓库条件
			}
			if(!StringUtil.isEmpty(queryForm.getProductName()) && !StringUtil.isEmpty(queryForm.getPackSizeName())) { //产品条件
				sql.append(" \r\n and ttd.OUTPRODUCTID in (select id from product where productname = ? and specmode= ?) ");param.put(UUID.randomUUID().toString(), queryForm.getProductName());param.put(UUID.randomUUID().toString(), queryForm.getPackSizeName());
			} else if(!StringUtil.isEmpty(queryForm.getProductName()) && StringUtil.isEmpty(queryForm.getPackSizeName())) { //产品条件
				sql.append(" \r\n and ttd.OUTPRODUCTID in (select id from product where productname = ?) ");param.put(UUID.randomUUID().toString(), queryForm.getProductName());
			}
			if(!StringUtil.isEmpty(queryForm.getBeginDate())){
				sql.append(" \r\n and tt.auditdate >=to_date(?,'yyyy-MM-dd hh24:mi:ss') ");param.put(UUID.randomUUID().toString(), queryForm.getBeginDate()); //开始时间条件
			}
			if(!StringUtil.isEmpty(queryForm.getEndDate())){
				sql.append(" \r\n and tt.auditdate < to_date(?,'yyyy-MM-dd hh24:mi:ss') ");param.put(UUID.randomUUID().toString(), queryForm.getEndDate()); //结束时间条件
			}
			sql.append(" \r\n join WAREHOUSE w on w.id = tt.warehouseid and w.USEFLAG = 1 ");
			sql.append(" \r\n INNER JOIN ORGAN o on tt.ORGANID = o.id and o.organtype = 2 and o.organmodel = 1 and o.ISREPEAL = 0");
			sql.append(" \r\n LEFT JOIN PRODUCT p on ttd.OUTPRODUCTID = p.id  ");
			sql.append(" \r\n LEFT JOIN PRODUCT_STRUCT pstr on p.PSID = pstr.STRUCTCODE  ");
			sql.append(" \r\n LEFT JOIN COUNTRY_AREA country on o.province=country.id  ");
			sql.append(" \r\n LEFT JOIN (SELECT max(REGIONCODEID) regioncodeid,max(AREAID) areaid  FROM REGION_AREA GROUP BY AREAID) ra on ra.areaid=country.id  ");
			sql.append(" \r\n LEFT JOIN REGION r on r.regioncode=ra.regioncodeid  ");
			if(DbUtil.isDealer(users)) {
				sql.append(" \r\n LEFT JOIN RULE_USER_WH rw on tt.warehouseid = rw.warehouse_id  ");
				sql.append(" \r\n where rw.user_id = ?");param.put(UUID.randomUUID().toString(), users.getUserid());
			} else {
				sql.append(" \r\n where "+DbUtil.getWhereCondition(users, "o"));
			}
			//大区条件
			if(!StringUtil.isEmpty(queryForm.getRegion())){
				sql.append(" \r\n and r.regioncode = ? "); param.put(UUID.randomUUID().toString(), queryForm.getRegion());
			}
			sql.append(" \r\n  and p.USEFLAG = 1 ");
			
			//分包小包加销售
			sql.append(" \r\n union all");
			sql.append(" \r\n select DISTINCT tt.id billNo");
			sql.append(" \r\n ,r.sortname regionName");
			sql.append(" \r\n ,o.province provinceId");
			sql.append(" \r\n ,o.oecode oecode");
			sql.append(" \r\n ,country.areaname province");
			sql.append(" \r\n ,o.id organId ");
			sql.append(" \r\n ,o.organname organName");
			sql.append(" \r\n ,ttd.INPRODUCTID productId  ");
			sql.append(" \r\n ,pstr.sortname psName");
			sql.append(" \r\n ,p.productname productName");
			sql.append(" \r\n ,p.productnameen productNameen");
			sql.append(" \r\n ,p.mcode mCode");
			sql.append(" \r\n ,p.matericalchdes matericalChDes ");
			sql.append(" \r\n ,p.matericalendes matericalEnDes ");
			sql.append(" \r\n ,p.sunit unitId ");
			sql.append(" \r\n ,p.specmode packSizeName");
			sql.append(" \r\n ,p.packsizenameen packSizeNameEn ");
			sql.append(" \r\n ,ttd.inbatch batch ");
			sql.append(" \r\n ,ttd.inquantity salesQuantity"); // 销售数量
			sql.append(" \r\n ,0 consumeQuantity"); //消耗数量
			sql.append(" \r\n ,tt.billdate makeDate,w.warehousename ");
//			sql.append(" \r\n ,pj.production_date produceDate ");
//			sql.append(" \r\n ,pj.expiry_date expiryDate ");
			sql.append(" \r\n from PACK_SEPARATE tt INNER JOIN PACK_SEPARATE_DETAIL ttd on tt.id = ttd.psid  and tt.isaudit = 1 ");
			if(!StringUtil.isEmpty(queryForm.getOrganId())){
				sql.append(" \r\n and tt.ORGANID = ? ");param.put(UUID.randomUUID().toString(), queryForm.getOrganId()); //机构条件
			}
			if(!StringUtil.isEmpty(queryForm.getWarehouseId())){
				sql.append(" \r\n and tt.WAREHOUSEID = ? ");param.put(UUID.randomUUID().toString(), queryForm.getWarehouseId()); //仓库条件
			}
			if(!StringUtil.isEmpty(queryForm.getProductName()) && !StringUtil.isEmpty(queryForm.getPackSizeName())) { //产品条件
				sql.append(" \r\n and ttd.INPRODUCTID in (select id from product where productname = ? and specmode= ?) ");param.put(UUID.randomUUID().toString(), queryForm.getProductName());param.put(UUID.randomUUID().toString(), queryForm.getPackSizeName());
			} else if(!StringUtil.isEmpty(queryForm.getProductName()) && StringUtil.isEmpty(queryForm.getPackSizeName())) { //产品条件
				sql.append(" \r\n and ttd.INPRODUCTID in (select id from product where productname = ?) ");param.put(UUID.randomUUID().toString(), queryForm.getProductName());
			}
			if(!StringUtil.isEmpty(queryForm.getBeginDate())){
				sql.append(" \r\n and tt.auditdate >=to_date(?,'yyyy-MM-dd hh24:mi:ss') ");param.put(UUID.randomUUID().toString(), queryForm.getBeginDate()); //开始时间条件
			}
			if(!StringUtil.isEmpty(queryForm.getEndDate())){
				sql.append(" \r\n and tt.auditdate < to_date(?,'yyyy-MM-dd hh24:mi:ss') ");param.put(UUID.randomUUID().toString(), queryForm.getEndDate()); //结束时间条件
			}
			sql.append(" \r\n join WAREHOUSE w on w.id = tt.warehouseid and w.USEFLAG = 1 ");
			sql.append(" \r\n INNER JOIN ORGAN o on tt.ORGANID = o.id and o.organtype = 2 and o.organmodel = 1 and o.ISREPEAL = 0");
			sql.append(" \r\n LEFT JOIN PRODUCT p on ttd.INPRODUCTID = p.id  ");
			sql.append(" \r\n LEFT JOIN PRODUCT_STRUCT pstr on p.PSID = pstr.STRUCTCODE  ");
			sql.append(" \r\n LEFT JOIN COUNTRY_AREA country on o.province=country.id  ");
			sql.append(" \r\n LEFT JOIN (SELECT max(REGIONCODEID) regioncodeid,max(AREAID) areaid  FROM REGION_AREA GROUP BY AREAID) ra on ra.areaid=country.id  ");
			sql.append(" \r\n LEFT JOIN REGION r on r.regioncode=ra.regioncodeid  ");
			if(DbUtil.isDealer(users)) {
				sql.append(" \r\n LEFT JOIN RULE_USER_WH rw on tt.warehouseid = rw.warehouse_id  ");
				sql.append(" \r\n where rw.user_id = ?");param.put(UUID.randomUUID().toString(), users.getUserid());
			} else {
				sql.append(" \r\n where "+DbUtil.getWhereCondition(users, "o"));
			}
			//大区条件
			if(!StringUtil.isEmpty(queryForm.getRegion())){
				sql.append(" \r\n and r.regioncode = ? "); param.put(UUID.randomUUID().toString(), queryForm.getRegion());
			}
			//#end modified by ryan.xi at 20150714
			sql.append(" \r\n  and p.USEFLAG = 1 ");
			
			//工厂退回--减销售
			sql.append(" \r\n union all");
			
			sql.append(" \r\n select DISTINCT tt.billNo billNo");
			sql.append(" \r\n ,r.sortname regionName");
			sql.append(" \r\n ,o.province provinceId");
			sql.append(" \r\n ,o.oecode oecode");
			sql.append(" \r\n ,country.areaname province");
			sql.append(" \r\n ,tt.oid organId ");
			sql.append(" \r\n ,o.organname organName");
			sql.append(" \r\n ,ttd.productid productId  ");
			sql.append(" \r\n ,pstr.sortname psName");
			sql.append(" \r\n ,p.productname productName");
			sql.append(" \r\n ,p.productnameen productNameen");
			sql.append(" \r\n ,p.mcode mCode");
			sql.append(" \r\n ,p.matericalchdes matericalChDes ");
			sql.append(" \r\n ,p.matericalendes matericalEnDes ");
			sql.append(" \r\n ,p.sunit unitId ");
			sql.append(" \r\n ,p.specmode packSizeName");
			sql.append(" \r\n ,p.packsizenameen packSizeNameEn ");
			sql.append(" \r\n ,swb.batch batch ");
			sql.append(" \r\n ,-swb.cycleoutquantity salesQuantity"); // 销售数量
			sql.append(" \r\n ,0 consumeQuantity"); //消耗数量
			sql.append(" \r\n ,tt.auditdate makeDate,w.warehousename");
			
			sql.append(" \r\n from TAKE_TICKET tt INNER JOIN TAKE_TICKET_DETAIL ttd on tt.id = ttd.ttid  and tt.isaudit = 1  and tt.bsort = 7 and tt.billno like 'PW%' ");
			if(!StringUtil.isEmpty(queryForm.getOrganId())){
				sql.append(" \r\n and tt.oid = ? ");param.put(UUID.randomUUID().toString(), queryForm.getOrganId()); //机构条件
			}
			if(!StringUtil.isEmpty(queryForm.getWarehouseId())){
				sql.append(" \r\n and tt.WAREHOUSEID = ? ");param.put(UUID.randomUUID().toString(), queryForm.getWarehouseId()); //仓库条件
			}
			if(!StringUtil.isEmpty(queryForm.getProductName()) && !StringUtil.isEmpty(queryForm.getPackSizeName())) { //产品条件
				sql.append(" \r\n and ttd.productid in (select id from product where productname = ? and specmode=?) ");param.put(UUID.randomUUID().toString(), queryForm.getProductName());param.put(UUID.randomUUID().toString(), queryForm.getPackSizeName());
			} else if(!StringUtil.isEmpty(queryForm.getProductName()) && StringUtil.isEmpty(queryForm.getPackSizeName())) { //产品条件
				sql.append(" \r\n and ttd.productid in (select id from product where productname = ?) ");param.put(UUID.randomUUID().toString(), queryForm.getProductName());
			}
			if(!StringUtil.isEmpty(queryForm.getBeginDate())){
				sql.append(" \r\n and tt.auditdate >=to_date(?,'yyyy-MM-dd hh24:mi:ss') ");param.put(UUID.randomUUID().toString(), queryForm.getBeginDate()); //开始时间条件
			}
			if(!StringUtil.isEmpty(queryForm.getEndDate())){
				sql.append(" \r\n and tt.auditdate < to_date(?,'yyyy-MM-dd hh24:mi:ss') ");param.put(UUID.randomUUID().toString(), queryForm.getEndDate()); //结束时间条件
			}
			/*
			sql.append(" \r\n  join ORGAN_WITHDRAW ow on tt.billno = ow.id");
			if(!StringUtil.isEmpty(queryForm.getBeginDate())){
				sql.append(" \r\n and ow.makedate >=to_date('" + queryForm.getBeginDate() + "','yyyy-MM-dd hh24:mi:ss') "); //开始时间条件
			}
			if(!StringUtil.isEmpty(queryForm.getEndDate())){
				sql.append(" \r\n and ow.makedate < to_date('" + queryForm.getEndDate() + "','yyyy-MM-dd hh24:mi:ss') "); //结束时间条件
			}*/
			sql.append(" \r\n join WAREHOUSE w on w.id = tt.warehouseid and w.USEFLAG = 1 ");
			sql.append(" \r\n INNER JOIN STOCK_WASTE_BOOK swb on swb.billcode = tt.id and ttd.productid = swb.productid   ");
			sql.append(" \r\n INNER JOIN ORGAN o on tt.oid = o.id and o.organtype = 2 and  organmodel = 1 and o.ISREPEAL = 0");
			sql.append(" \r\n LEFT JOIN F_UNIT f on ttd.productid = f.productid and ttd.unitid = f.funitid ");
			sql.append(" \r\n LEFT JOIN PRODUCT p on ttd.productid = p.id  ");
			sql.append(" \r\n LEFT JOIN PRODUCT_STRUCT pstr on p.PSID = pstr.STRUCTCODE  ");
			sql.append(" \r\n LEFT JOIN COUNTRY_AREA country on o.province=country.id  ");
			sql.append(" \r\n LEFT JOIN (SELECT max(REGIONCODEID) regioncodeid,max(AREAID) areaid  FROM REGION_AREA GROUP BY AREAID) ra on ra.areaid=country.id  ");
			sql.append(" \r\n LEFT JOIN REGION r on r.regioncode=ra.regioncodeid  ");
			if(DbUtil.isDealer(users)) {
				sql.append(" \r\n LEFT JOIN RULE_USER_WH rw on tt.warehouseid = rw.warehouse_id  ");
				sql.append(" \r\n where rw.user_id = ?");param.put(UUID.randomUUID().toString(), users.getUserid());
			} else {
				sql.append(" \r\n where "+DbUtil.getWhereCondition(users, "o"));
			}
			//大区条件
			if(!StringUtil.isEmpty(queryForm.getRegion())){
				sql.append(" \r\n and r.regioncode = ? "); param.put(UUID.randomUUID().toString(), queryForm.getRegion());
			}
			sql.append(" \r\n  and p.USEFLAG = 1 ");
			//#start modified by ryan.xi at 20150805
			//采购入库-加销售-20150805
			sql.append(" \r\n union all");
			
			sql.append(" \r\n select DISTINCT oia.id billNo ");
			sql.append(" \r\n ,r.sortname regionName  ");
			sql.append(" \r\n ,o.province provinceId  ");
			sql.append(" \r\n ,o.oecode oecode ");
			sql.append(" \r\n ,country.areaname province  ");
			sql.append(" \r\n ,o.id  organId");
			sql.append(" \r\n ,o.organname organName  ");
			sql.append(" \r\n ,oida.productid productId ");
			sql.append(" \r\n ,pstr.sortname psName ");
			sql.append(" \r\n ,p.productname productName ");
			sql.append(" \r\n  ,p.productnameen productNameen  ");
			sql.append(" \r\n ,p.mcode mCode ");
			sql.append(" \r\n ,p.matericalchdes matericalChDes  ");
			sql.append(" \r\n ,p.matericalendes matericalEnDes  ");
			sql.append(" \r\n ,p.sunit unitId  ");
			sql.append(" \r\n ,p.packsizename packSizeName ");
			sql.append(" \r\n  ,p.packsizenameen packSizeNameEn  ");
			sql.append(" \r\n ,oida.batch batch  ");
			sql.append(" \r\n ,(oida.quantity * f.xquantity) salesQuantity  ");
			sql.append(" \r\n ,0 consumeQuantity  ");
			sql.append(" \r\n ,oia.AUDITDATE makeDate,w.warehousename  ");
			sql.append(" \r\n from OTHER_INCOME_ALL oia INNER JOIN OTHER_INCOME_DETAIL_ALL oida on oia.id = oida.oiid and oia.isaudit = 1 and oia.incomesort = 6");
			if(!StringUtil.isEmpty(queryForm.getOrganId())){
				sql.append(" \r\n and oia.organid = ? ");param.put(UUID.randomUUID().toString(), queryForm.getOrganId()); //机构条件
			}
			if(!StringUtil.isEmpty(queryForm.getWarehouseId())){
				sql.append(" \r\n and oia.warehouseid = ? ");param.put(UUID.randomUUID().toString(), queryForm.getWarehouseId()); //仓库条件
			}
			if(!StringUtil.isEmpty(queryForm.getProductName()) && !StringUtil.isEmpty(queryForm.getPackSizeName())) { //产品条件
				sql.append(" \r\n and oida.productid in (select id from product where productname = ? and packsizename= ?) ");param.put(UUID.randomUUID().toString(), queryForm.getProductName());param.put(UUID.randomUUID().toString(), queryForm.getPackSizeName());
			} else if(!StringUtil.isEmpty(queryForm.getProductName()) && StringUtil.isEmpty(queryForm.getPackSizeName())) { //产品条件
				sql.append(" \r\n and oida.productid in (select id from product where productname = ?) ");param.put(UUID.randomUUID().toString(), queryForm.getProductName());
			}
			if(!StringUtil.isEmpty(queryForm.getBeginDate())){
				sql.append(" \r\n and oia.auditdate >=to_date(?,'yyyy-MM-dd hh24:mi:ss') ");param.put(UUID.randomUUID().toString(), queryForm.getBeginDate()); //开始时间条件
			}
			if(!StringUtil.isEmpty(queryForm.getEndDate())){
				sql.append(" \r\n and oia.auditdate < to_date(?,'yyyy-MM-dd hh24:mi:ss') ");param.put(UUID.randomUUID().toString(), queryForm.getEndDate()); //结束时间条件
			}
			sql.append(" \r\n join WAREHOUSE w on w.id = oia.warehouseid and w.USEFLAG = 1 ");
			sql.append(" \r\n INNER JOIN ORGAN o on oia.organid= o.id and o.organtype = 2 and  organmodel = 1 and o.ISREPEAL = 0");
			sql.append(" \r\n LEFT JOIN F_UNIT f on oida.productid = f.productid and oida.unitid = f.funitid ");
			sql.append(" \r\n LEFT JOIN PRODUCT p on oida.productid = p.id ");
			sql.append(" \r\n LEFT JOIN PRODUCT_STRUCT pstr on p.PSID = pstr.STRUCTCODE   ");
			sql.append(" \r\n LEFT JOIN COUNTRY_AREA country on o.province=country.id   ");
			sql.append(" \r\n LEFT JOIN (SELECT max(REGIONCODEID) regioncodeid,max(AREAID) areaid  FROM REGION_AREA GROUP BY AREAID) ra on ra.areaid=country.id   ");
			sql.append(" \r\n LEFT JOIN REGION r on r.regioncode=ra.regioncodeid   ");
			if(DbUtil.isDealer(users)) {
				sql.append(" \r\n INNER JOIN RULE_USER_WH rw on oia.warehouseid = rw.warehouse_id ");
				sql.append(" \r\n where rw.user_id = ?");param.put(UUID.randomUUID().toString(), users.getUserid());
			} else {
				sql.append(" \r\n where "+DbUtil.getWhereCondition(users, "o"));
			}
			if(!StringUtil.isEmpty(queryForm.getRegion())){
				sql.append(" \r\n and r.regioncode = ? "); param.put(UUID.randomUUID().toString(), queryForm.getRegion());
			}
			sql.append(" \r\n  and p.USEFLAG = 1 ");
			//#end modified by ryan.xi at 20150805
		}
		System.out.println(sql.toString());
		List<Map> list =  new ArrayList<Map>();
		if(request == null && pageSize == 0){
			list =  EntityManager.jdbcquery(sql.toString(), param);
		}else {
			list =  PageQuery.jdbcSqlserverQuery(request,"billNo", sql.toString(), pageSize, param);
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
				scForm.setReportDate(new Timestamp(System.currentTimeMillis()));
				//单据日期
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
	
	public List<SalesConsumeDetailReportForm> queryConsumeDetailReport(HttpServletRequest request, int pageSize,SalesConsumeDetailReportForm queryForm,UsersBean users) throws Exception{
		//查看是否有统计过销售消耗历史数据
		BaseResource sacBr = appBr.getBaseResourceValue("ConsumeDetailDate", 1);
		if(sacBr == null) {//无历史数据按单据统计
			return queryConsumeDetailReport2(request, pageSize, queryForm, users);
		} else {
			Date sacDate = DateUtil.StringToDate(sacBr.getTagsubvalue());
			Date startDate = DateUtil.StringToDate(queryForm.getBeginDate());
			Date endDate = DateUtil.StringToDate(queryForm.getEndDate());
			if(sacDate.before(startDate)) {
				//历史数据在查询起始日期之前,则按单据实时统计
				return queryConsumeDetailReport2(request, pageSize, queryForm, users);
			} else if(sacDate.before(endDate)) {
				//历史数据在查询起始日期之后,结束日期之前,则按历史数据结合单据实时统计
				queryForm.setHistoryDate(sacBr.getTagsubvalue());
				return queryConsumeDetaiReportFromHistoryAndBill(request, pageSize, queryForm, users);
			} else {
				return queryConsumeDetaiReportFromHistory(request, pageSize, queryForm, users);
			}
		}
	}
	
	private List<SalesConsumeDetailReportForm> queryConsumeDetaiReportFromHistory(
			HttpServletRequest request, int pageSize,
			SalesConsumeDetailReportForm queryForm, UsersBean users) throws Exception {
		Map<String, Object> param = new LinkedHashMap<String, Object>();
		//对于结束日期增加一天
		if(!StringUtil.isEmpty(queryForm.getEndDate())){
			String endDateStr = queryForm.getEndDate();
			Date endDate = Dateutil.StringToDate(endDateStr);
			endDate = Dateutil.addDay2Date(1, endDate);
			queryForm.setEndDate(Dateutil.formatDate(endDate));
		}
		
		List<SalesConsumeDetailReportForm> resultList = new ArrayList<SalesConsumeDetailReportForm>();
		StringBuffer sql = new StringBuffer();
		
		//消耗数量
		sql.append(" \r\n select sdh.billNo billNo ");
		sql.append(" \r\n ,r.sortname regionName ");
		sql.append(" \r\n ,ino.province ");
		sql.append(" \r\n ,ino.city ");
		sql.append(" \r\n ,ino.areas ");
		sql.append(" \r\n ,ino.organModel ");
		sql.append(" \r\n ,ino.oecode oecode ");
//		sql.append(" \r\n ,ca.areaname province ");
		sql.append(" \r\n ,ino.id  organId ");
		sql.append(" \r\n ,ino.organname organName ");
		sql.append(" \r\n ,p.id productId  ");
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
		sql.append(" \r\n join organ o on o.id = sdh.outorganid and o.isrepeal=0");
		if(!StringUtil.isEmpty(queryForm.getOrganId())){
			sql.append(" \r\n and o.id = ? ");param.put(UUID.randomUUID().toString(), queryForm.getOrganId()); //机构条件
		}
		if(!StringUtil.isEmpty(queryForm.getWarehouseId())){
			sql.append(" \r\n and sdh.outwarehouseid = ? ");param.put(UUID.randomUUID().toString(), queryForm.getWarehouseId()); //仓库条件
		}
		if(!StringUtil.isEmpty(queryForm.getBeginDate())){
			sql.append(" \r\n and sdh.makeDate >=to_date(?,'yyyy-MM-dd hh24:mi:ss') ");param.put(UUID.randomUUID().toString(), queryForm.getBeginDate()); //开始时间条件
		}
		if(!StringUtil.isEmpty(queryForm.getEndDate())){
			sql.append(" \r\n and sdh.makedate < to_date(?,'yyyy-MM-dd hh24:mi:ss') ");param.put(UUID.randomUUID().toString(), queryForm.getEndDate()); //结束时间条件
		}
		if(DbUtil.isDealer(users)) {
			sql.append(" \r\n JOIN RULE_USER_WH rw on sdh.outwarehouseid = rw.warehouse_id  ");
			sql.append(" \r\n and rw.user_id = ?");param.put(UUID.randomUUID().toString(), users.getUserid());
		} else {
			sql.append(" \r\n and "+DbUtil.getWhereCondition(users, "o"));
		}
		sql.append(" \r\n join organ ino on ino.id = sdh.organid and ino.isrepeal=0");
		sql.append(" \r\n join PRODUCT p on p.id = sdh.productid and p.useflag=1");
		if(!StringUtil.isEmpty(queryForm.getProductName()) && !StringUtil.isEmpty(queryForm.getPackSizeName())) { //产品条件
			sql.append(" \r\n and p.productname = ? and specmode= ? ");param.put(UUID.randomUUID().toString(), queryForm.getProductName());param.put(UUID.randomUUID().toString(), queryForm.getPackSizeName());
		} else if(!StringUtil.isEmpty(queryForm.getProductName()) && StringUtil.isEmpty(queryForm.getPackSizeName())) { //产品条件
			sql.append(" \r\n and p.productname = ? ");param.put(UUID.randomUUID().toString(), queryForm.getProductName());
		}
		sql.append(" \r\n join WAREHOUSE w on w.id = sdh.outwarehouseid and w.useflag=1");
		sql.append(" \r\n join PRODUCT_STRUCT pstr on pstr.structcode=p.psid");
//		sql.append(" \r\n LEFT JOIN COUNTRY_AREA ca on ca.id = o.province");
		sql.append(" \r\n LEFT JOIN REGION_AREA ra on RA.AREAID=o.province");
		sql.append(" \r\n LEFT JOIN REGION r on r.regioncode = ra.regioncodeid");
		if(!StringUtil.isEmpty(queryForm.getRegion())){
			sql.append(" \r\n where r.regioncode = ? ");param.put(UUID.randomUUID().toString(), queryForm.getRegion()); 
		}
		System.out.println(sql.toString());
		List<Map> list =  new ArrayList<Map>();
		if(request == null && pageSize == 0){
			list =  EntityManager.jdbcquery(sql.toString(), param);
		}else {
			list =  PageQuery.jdbcSqlserverQuery(request,"billNo", sql.toString(), pageSize, param);
		}
		if(list != null && list.size()>0){
			Map<String,String> sapDeliveryNos = new HashMap<String, String>();
			Map<String,FUnit>  funitMap = appFUnit.getAllMap(); //获取所有的单位换算信息
			Map<Integer, String> countUnitMap = appBr.getBaseResourceMap("CountUnit");
			Map<String, String> areaNameMap =  appCountryArea.getAreaNameMap();
			for(Map map : list){
				SalesConsumeDetailReportForm scForm = new SalesConsumeDetailReportForm();
				//将Map中对应的值赋值给实例
				MapUtil.mapToObject(map, scForm);
				//设置省市区
				scForm.setProvinceId(scForm.getProvince());
				scForm.setCityId(scForm.getCity());
				scForm.setAreasId(scForm.getAreas());
				if(!StringUtil.isEmpty(scForm.getProvince())) {
					scForm.setProvince(areaNameMap.get(scForm.getProvince()));
				}
				if(!StringUtil.isEmpty(scForm.getCity())) {
					scForm.setCity(areaNameMap.get(scForm.getCity()));
				}
				if(!StringUtil.isEmpty(scForm.getAreas())) {
					scForm.setAreas(areaNameMap.get(scForm.getAreas()));
				}
				//报告日期主当前日期
				scForm.setReportDate(new Timestamp(System.currentTimeMillis()));
				//单据日期
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

	private List<SalesConsumeDetailReportForm> queryConsumeDetaiReportFromHistoryAndBill(
			HttpServletRequest request, int pageSize,
			SalesConsumeDetailReportForm queryForm, UsersBean users) throws Exception {
		Map<String, Object> param = new LinkedHashMap<String, Object>();
		//对于结束日期增加一天 
		if(!StringUtil.isEmpty(queryForm.getEndDate())){
			String endDateStr = queryForm.getEndDate();
			Date endDate = Dateutil.StringToDate(endDateStr);
			endDate = Dateutil.addDay2Date(1, endDate);
			queryForm.setEndDate(Dateutil.formatDate(endDate));
		}
		
		List<SalesConsumeDetailReportForm> resultList = new ArrayList<SalesConsumeDetailReportForm>();
		StringBuffer sql = new StringBuffer();
		
		//消耗数量
		sql.append(" \r\n --正常出库消耗 ");
		sql.append(" \r\n select DISTINCT tt.billNo billNo ");
		sql.append(" \r\n ,r.sortname regionName ");
		sql.append(" \r\n ,ino.province ");
		sql.append(" \r\n ,ino.city ");
		sql.append(" \r\n ,ino.areas ");
		sql.append(" \r\n ,ino.organModel ");
		sql.append(" \r\n ,ino.oecode oecode ");
//		sql.append(" \r\n ,country.areaname province ");
		sql.append(" \r\n ,tt.inoid  organId ");
		sql.append(" \r\n ,ino.organname organName ");
		sql.append(" \r\n ,ttd.productid productId  ");
		sql.append(" \r\n ,pstr.sortname psName ");
		sql.append(" \r\n ,p.productname productName ");
		sql.append(" \r\n ,p.productnameen productNameen ");
		sql.append(" \r\n ,p.mcode mCode ");
		sql.append(" \r\n ,p.matericalchdes matericalChDes ");
		sql.append(" \r\n ,p.matericalendes matericalEnDes ");
		sql.append(" \r\n ,p.sunit unitId ");
		sql.append(" \r\n ,p.specmode packSizeName");
		sql.append(" \r\n ,p.packsizenameen packSizeNameEn");
		sql.append(" \r\n ,swb.batch batch ");
		sql.append(" \r\n ,0 salesQuantity ");  // 销售数量
		sql.append(" \r\n ,swb.cycleoutquantity  consumeQuantity"); //消耗数量
		sql.append(" \r\n ,TT.AUDITDATE makeDate ");
//		sql.append(" \r\n ,pj.production_date produceDate");
//		sql.append(" \r\n ,pj.expiry_date expiryDate");
		sql.append(" \r\n ,o.organname outOrganName,w.warehousename ");
		sql.append(" \r\n from TAKE_TICKET tt INNER JOIN TAKE_TICKET_DETAIL ttd on tt.id = ttd.ttid  and tt.isaudit = 1  and tt.bsort = 1 and tt.oid <> tt.inoid ");
		if(!StringUtil.isEmpty(queryForm.getOrganId())){
			sql.append(" \r\n and tt.oid = ? ");param.put(UUID.randomUUID().toString(), queryForm.getOrganId()); //机构条件
		}
		if(!StringUtil.isEmpty(queryForm.getWarehouseId())){
			sql.append(" \r\n and tt.WAREHOUSEID = ? ");param.put(UUID.randomUUID().toString(), queryForm.getWarehouseId()); //仓库条件
		}
		if(!StringUtil.isEmpty(queryForm.getProductName()) && !StringUtil.isEmpty(queryForm.getPackSizeName())) { //产品条件
			sql.append(" \r\n and ttd.productid in (select id from product where productname = ? and specmode=?) ");param.put(UUID.randomUUID().toString(), queryForm.getProductName());param.put(UUID.randomUUID().toString(), queryForm.getPackSizeName());
		} else if(!StringUtil.isEmpty(queryForm.getProductName()) && StringUtil.isEmpty(queryForm.getPackSizeName())) { //产品条件
			sql.append(" \r\n and ttd.productid in (select id from product where productname = ?) ");param.put(UUID.randomUUID().toString(), queryForm.getProductName());
		}
		if(!StringUtil.isEmpty(queryForm.getHistoryDate())){
			sql.append(" \r\n and tt.auditdate >=to_date(?,'yyyy-MM-dd hh24:mi:ss') ");param.put(UUID.randomUUID().toString(), queryForm.getHistoryDate()); //开始时间条件
		}
		if(!StringUtil.isEmpty(queryForm.getEndDate())){
			sql.append(" \r\n and tt.auditdate < to_date(?,'yyyy-MM-dd hh24:mi:ss') ");param.put(UUID.randomUUID().toString(), queryForm.getEndDate()); //结束时间条件
		}
		
		sql.append(" \r\n join WAREHOUSE w on w.id = tt.warehouseid and w.USEFLAG = 1");
		
		sql.append(" \r\n INNER JOIN STOCK_WASTE_BOOK swb on swb.billcode = tt.id and ttd.productid = swb.productid ");
		sql.append(" \r\n INNER JOIN ORGAN o on tt.oid = o.id and o.organtype = 2 and  organmodel = 1 ");
		sql.append(" \r\n INNER JOIN ORGAN ino on tt.inoid = ino.id");
		sql.append(" \r\n LEFT JOIN F_UNIT f on ttd.productid = f.productid and ttd.unitid = f.funitid ");
		
		sql.append(" \r\n LEFT JOIN PRODUCT p on ttd.productid = p.id  ");
		sql.append(" \r\n LEFT JOIN PRODUCT_STRUCT pstr on p.PSID = pstr.STRUCTCODE  ");
//		sql.append(" \r\n LEFT JOIN COUNTRY_AREA country on ino.province=country.id  ");
		sql.append(" \r\n LEFT JOIN (SELECT max(REGIONCODEID) regioncodeid,max(AREAID) areaid  FROM REGION_AREA GROUP BY AREAID) ra on ra.areaid=ino.province ");
		sql.append(" \r\n LEFT JOIN REGION r on r.regioncode=ra.regioncodeid  ");
		if(DbUtil.isDealer(users)) {
			sql.append(" \r\n LEFT JOIN RULE_USER_WH rw on tt.warehouseid = rw.warehouse_id  ");
			sql.append(" \r\n where rw.user_id = ?");param.put(UUID.randomUUID().toString(), users.getUserid());
		} else {
			sql.append(" \r\n where "+DbUtil.getWhereCondition(users, "o"));
		}
		if(!StringUtil.isEmpty(queryForm.getRegion())){
			sql.append(" \r\n and r.regioncode = ? "); param.put(UUID.randomUUID().toString(), queryForm.getRegion());
		}
		sql.append(" \r\n  and p.USEFLAG = 1 ");
		sql.append(" \r\n union all");
		sql.append(" \r\n --工厂给PD收发差异记到消耗 ");
		//工厂给PD收发差异记到消耗
		sql.append(" \r\n select DISTINCT tt.billNo billNo ");
		sql.append(" \r\n ,r.sortname regionName  ");
		sql.append(" \r\n ,ino.province  ");
		sql.append(" \r\n ,ino.city ");
		sql.append(" \r\n ,ino.areas ");
		sql.append(" \r\n ,ino.organModel ");
		sql.append(" \r\n ,ino.oecode oecode ");
//		sql.append(" \r\n ,country.areaname province  ");
		sql.append(" \r\n ,ino.id  organId");
		sql.append(" \r\n ,ino.organname organName  ");
		sql.append(" \r\n ,ttd.productid productId ");
		sql.append(" \r\n ,pstr.sortname psName ");
		sql.append(" \r\n ,p.productname productName ");
		sql.append(" \r\n  ,p.productnameen productNameen  ");
		sql.append(" \r\n ,p.mcode mCode ");
		sql.append(" \r\n ,p.matericalchdes matericalChDes  ");
		sql.append(" \r\n ,p.matericalendes matericalEnDes  ");
		sql.append(" \r\n ,p.sunit unitId  ");
		sql.append(" \r\n ,p.specmode packSizeName ");
		sql.append(" \r\n  ,p.packsizenameen packSizeNameEn  ");
		sql.append(" \r\n ,'"+Constants.NO_BATCH+"' batch  ");
		sql.append(" \r\n ,0 salesQuantity  ");
		sql.append(" \r\n ,((samd.quantity-samd.receivequantity) * f.xquantity)  consumeQuantity ");
		sql.append(" \r\n ,sam.movedate makeDate  ");
		sql.append(" \r\n ,ino.organname outOrganName ,w.warehousename ");
		sql.append(" \r\n from TAKE_TICKET tt INNER JOIN TAKE_TICKET_DETAIL ttd on tt.id = ttd.ttid  and tt.isaudit = 1  and tt.bsort = 1 ");
		if(!StringUtil.isEmpty(queryForm.getOrganId())){
			sql.append(" \r\n and tt.inoid = ? ");param.put(UUID.randomUUID().toString(), queryForm.getOrganId()); //机构条件
		}
		if(!StringUtil.isEmpty(queryForm.getWarehouseId())){
			sql.append(" \r\n and tt.inwarehouseid = ? ");param.put(UUID.randomUUID().toString(), queryForm.getWarehouseId()); //仓库条件
		}
		if(!StringUtil.isEmpty(queryForm.getProductName()) && !StringUtil.isEmpty(queryForm.getPackSizeName())) { //产品条件
			sql.append(" \r\n and ttd.productid in (select id from product where productname = ? and specmode=?) ");param.put(UUID.randomUUID().toString(), queryForm.getProductName());param.put(UUID.randomUUID().toString(), queryForm.getPackSizeName());
		} else if(!StringUtil.isEmpty(queryForm.getProductName()) && StringUtil.isEmpty(queryForm.getPackSizeName())) { //产品条件
			sql.append(" \r\n and ttd.productid in (select id from product where productname = ?) ");param.put(UUID.randomUUID().toString(), queryForm.getProductName());
		}
		sql.append(" \r\n join STOCK_ALTER_MOVE sam on sam.id=tt.billno and sam.iscomplete =1");
		if(!StringUtil.isEmpty(queryForm.getHistoryDate())){
			sql.append(" \r\n and sam.movedate >=to_date(?,'yyyy-MM-dd hh24:mi:ss') ");param.put(UUID.randomUUID().toString(), queryForm.getHistoryDate()); //开始时间条件
		}
		if(!StringUtil.isEmpty(queryForm.getEndDate())){
			sql.append(" \r\n and sam.movedate < to_date(?,'yyyy-MM-dd hh24:mi:ss') ");param.put(UUID.randomUUID().toString(), queryForm.getEndDate()); //结束时间条件
		}
		sql.append(" \r\n join WAREHOUSE w on w.id = tt.inwarehouseid and w.USEFLAG = 1");
		sql.append(" \r\n join STOCK_ALTER_MOVE_DETAIL samd on samd.samid = sam.id and samd.productid=ttd.productid");
		sql.append(" \r\n INNER JOIN ORGAN o on tt.oid = o.id and o.organtype = 1   ");
		sql.append(" \r\n LEFT JOIN F_UNIT f on ttd.productid = f.productid and ttd.unitid = f.funitid  ");
		sql.append(" \r\n LEFT JOIN ORGAN ino on tt.inoid = ino.id  and ino.organtype = 2 and ino.organmodel = 1");
		sql.append(" \r\n LEFT JOIN PRODUCT p on ttd.productid = p.id   ");
		sql.append(" \r\n LEFT JOIN PRODUCT_STRUCT pstr on p.PSID = pstr.STRUCTCODE   ");
//		sql.append(" \r\n LEFT JOIN COUNTRY_AREA country on ino.province=country.id   ");
		sql.append(" \r\n LEFT JOIN (SELECT max(REGIONCODEID) regioncodeid,max(AREAID) areaid  FROM REGION_AREA GROUP BY AREAID) ra on ra.areaid=ino.province ");
		sql.append(" \r\n LEFT JOIN REGION r on r.regioncode=ra.regioncodeid   ");
		if(DbUtil.isDealer(users)) {
			sql.append(" \r\n LEFT JOIN RULE_USER_WH rw on tt.inwarehouseid = rw.warehouse_id   ");
			sql.append(" \r\n where rw.user_id = ?");param.put(UUID.randomUUID().toString(), users.getUserid());
		} else {
			sql.append(" \r\n where "+DbUtil.getWhereCondition(users, "ino"));
		}
		sql.append(" and (samd.quantity-samd.receivequantity) > 0 ");
		if(!StringUtil.isEmpty(queryForm.getRegion())){
			sql.append(" \r\n and r.regioncode = ? "); param.put(UUID.randomUUID().toString(), queryForm.getRegion());
		}
		sql.append(" \r\n  and p.USEFLAG = 1 ");
		
		
		//经销商退货-减消耗-20150429
		sql.append(" \r\n union all");
		sql.append(" \r\n --经销商退货-减消耗");
		sql.append(" \r\n select DISTINCT tt.billNo billNo ");
		sql.append(" \r\n ,r.sortname regionName  ");
		sql.append(" \r\n ,o.province ");
		sql.append(" \r\n ,o.city ");
		sql.append(" \r\n ,o.areas ");
		sql.append(" \r\n ,o.organModel ");
		sql.append(" \r\n ,o.oecode oecode ");
//		sql.append(" \r\n ,country.areaname province  ");
		sql.append(" \r\n ,o.id  organId");
		sql.append(" \r\n ,o.organname organName  ");
		sql.append(" \r\n ,ttd.productid productId ");
		sql.append(" \r\n ,pstr.sortname psName ");
		sql.append(" \r\n ,p.productname productName ");
		sql.append(" \r\n  ,p.productnameen productNameen  ");
		sql.append(" \r\n ,p.mcode mCode ");
		sql.append(" \r\n ,p.matericalchdes matericalChDes  ");
		sql.append(" \r\n ,p.matericalendes matericalEnDes  ");
		sql.append(" \r\n ,p.sunit unitId  ");
		sql.append(" \r\n ,p.specmode packSizeName ");
		sql.append(" \r\n  ,p.packsizenameen packSizeNameEn  ");
		sql.append(" \r\n ,swb.batch batch  ");
		sql.append(" \r\n ,0 salesQuantity  ");
		sql.append(" \r\n ,-swb.cycleoutquantity  consumeQuantity ");
		sql.append(" \r\n ,TT.AUDITDATE makeDate  ");
		sql.append(" \r\n ,ino.organname outOrganName,w.warehousename  ");
		sql.append(" \r\n from TAKE_TICKET tt INNER JOIN TAKE_TICKET_DETAIL ttd on tt.id = ttd.ttid  and tt.isaudit = 1  and tt.bsort = 7 and tt.billno like 'OW%' ");
		if(!StringUtil.isEmpty(queryForm.getOrganId())){
			sql.append(" \r\n and tt.inoid = ? ");param.put(UUID.randomUUID().toString(), queryForm.getOrganId()); //机构条件
		}
		if(!StringUtil.isEmpty(queryForm.getWarehouseId())){
			sql.append(" \r\n and tt.inwarehouseid = ? ");param.put(UUID.randomUUID().toString(), queryForm.getWarehouseId()); //仓库条件
		}
		if(!StringUtil.isEmpty(queryForm.getProductName()) && !StringUtil.isEmpty(queryForm.getPackSizeName())) { //产品条件
			sql.append(" \r\n and ttd.productid in (select id from product where productname = ? and specmode=?) ");param.put(UUID.randomUUID().toString(), queryForm.getProductName());param.put(UUID.randomUUID().toString(), queryForm.getPackSizeName());
		} else if(!StringUtil.isEmpty(queryForm.getProductName()) && StringUtil.isEmpty(queryForm.getPackSizeName())) { //产品条件
			sql.append(" \r\n and ttd.productid in (select id from product where productname = ?) ");param.put(UUID.randomUUID().toString(), queryForm.getProductName());
		}
		if(!StringUtil.isEmpty(queryForm.getHistoryDate())){
			sql.append(" \r\n and tt.auditdate >=to_date(?,'yyyy-MM-dd hh24:mi:ss') ");param.put(UUID.randomUUID().toString(), queryForm.getHistoryDate()); //开始时间条件
		}
		if(!StringUtil.isEmpty(queryForm.getEndDate())){
			sql.append(" \r\n and tt.auditdate < to_date(?,'yyyy-MM-dd hh24:mi:ss') ");param.put(UUID.randomUUID().toString(), queryForm.getEndDate()); //结束时间条件
		}
		sql.append(" \r\n join WAREHOUSE w on w.id = tt.inwarehouseid and w.USEFLAG = 1");
		sql.append(" \r\n INNER JOIN ORGAN ino on tt.inoid = ino.id and ino.organtype = 2 and  ino.organmodel = 1 ");
		sql.append(" \r\n INNER JOIN ORGAN o on tt.oid = o.id ");
		sql.append(" \r\n INNER JOIN STOCK_WASTE_BOOK swb on swb.billcode = tt.id and ttd.productid = swb.productid  ");
		sql.append(" \r\n LEFT JOIN F_UNIT f on ttd.productid = f.productid and ttd.unitid = f.funitid  ");
		sql.append(" \r\n LEFT JOIN PRODUCT p on ttd.productid = p.id   ");
		sql.append(" \r\n LEFT JOIN PRODUCT_STRUCT pstr on p.PSID = pstr.STRUCTCODE   ");
//		sql.append(" \r\n LEFT JOIN COUNTRY_AREA country on o.province=country.id   ");
		sql.append(" \r\n LEFT JOIN (SELECT max(REGIONCODEID) regioncodeid,max(AREAID) areaid  FROM REGION_AREA GROUP BY AREAID) ra on ra.areaid=o.province   ");
		sql.append(" \r\n LEFT JOIN REGION r on r.regioncode=ra.regioncodeid   ");
		if(DbUtil.isDealer(users)) {
			sql.append(" \r\n LEFT JOIN RULE_USER_WH rw on tt.inwarehouseid = rw.warehouse_id   ");
			sql.append(" \r\n where rw.user_id = ?");param.put(UUID.randomUUID().toString(), users.getUserid());
		} else {
			sql.append(" \r\n where "+DbUtil.getWhereCondition(users, "ino"));
		}
		if(!StringUtil.isEmpty(queryForm.getRegion())){
			sql.append(" \r\n and r.regioncode = ? "); param.put(UUID.randomUUID().toString(), queryForm.getRegion());
		}
		sql.append(" \r\n  and p.USEFLAG = 1 ");
		//#start modified by ryan.xi at 20150714
		//分包损耗记到消耗
		sql.append(" \r\n --分包损耗记到消耗");
		sql.append(" \r\n union all");
		sql.append(" \r\n select DISTINCT tt.id billNo ");
		sql.append(" \r\n ,r.sortname regionName ");
		sql.append(" \r\n ,o.province ");
		sql.append(" \r\n ,o.city ");
		sql.append(" \r\n ,o.areas ");
		sql.append(" \r\n ,o.organModel ");
		sql.append(" \r\n ,o.oecode oecode ");
//		sql.append(" \r\n ,country.areaname province ");
		sql.append(" \r\n ,o.id  organId ");
		sql.append(" \r\n ,o.organname organName ");
		sql.append(" \r\n ,ttd.OUTPRODUCTID productId  ");
		sql.append(" \r\n ,pstr.sortname psName ");
		sql.append(" \r\n ,p.productname productName ");
		sql.append(" \r\n ,p.productnameen productNameen ");
		sql.append(" \r\n ,p.mcode mCode ");
		sql.append(" \r\n ,p.matericalchdes matericalChDes ");
		sql.append(" \r\n ,p.matericalendes matericalEnDes ");
		sql.append(" \r\n ,p.sunit unitId ");
		sql.append(" \r\n ,p.specmode packSizeName");
		sql.append(" \r\n ,p.packsizenameen packSizeNameEn");
		sql.append(" \r\n ,ttd.outbatch batch ");
		sql.append(" \r\n ,0 salesQuantity ");  // 销售数量
		sql.append(" \r\n ,ttd.wastage consumeQuantity"); //消耗数量
		sql.append(" \r\n ,TT.AUDITDATE makeDate ");
		sql.append(" \r\n ,o.organname outOrganName,w.warehousename ");
		sql.append(" \r\n from PACK_SEPARATE tt INNER JOIN PACK_SEPARATE_DETAIL ttd on tt.id = ttd.psid  and tt.isaudit = 1 ");
		if(!StringUtil.isEmpty(queryForm.getOrganId())){
			sql.append(" \r\n and tt.ORGANID = ? ");param.put(UUID.randomUUID().toString(), queryForm.getOrganId()); //机构条件
		}
		if(!StringUtil.isEmpty(queryForm.getWarehouseId())){
			sql.append(" \r\n and tt.WAREHOUSEID = ? ");param.put(UUID.randomUUID().toString(), queryForm.getWarehouseId()); //仓库条件
		}
		if(!StringUtil.isEmpty(queryForm.getProductName()) && !StringUtil.isEmpty(queryForm.getPackSizeName())) { //产品条件
			sql.append(" \r\n and ttd.OUTPRODUCTID in (select id from product where productname = ? and specmode= ?) ");param.put(UUID.randomUUID().toString(), queryForm.getProductName());param.put(UUID.randomUUID().toString(), queryForm.getPackSizeName());
		} else if(!StringUtil.isEmpty(queryForm.getProductName()) && StringUtil.isEmpty(queryForm.getPackSizeName())) { //产品条件
			sql.append(" \r\n and ttd.OUTPRODUCTID in (select id from product where productname = ?) ");param.put(UUID.randomUUID().toString(), queryForm.getProductName());
		}
		if(!StringUtil.isEmpty(queryForm.getHistoryDate())){
			sql.append(" \r\n and tt.auditdate >=to_date(?,'yyyy-MM-dd hh24:mi:ss') ");param.put(UUID.randomUUID().toString(), queryForm.getHistoryDate()); //开始时间条件
		}
		if(!StringUtil.isEmpty(queryForm.getEndDate())){
			sql.append(" \r\n and tt.auditdate < to_date(?,'yyyy-MM-dd hh24:mi:ss') ");param.put(UUID.randomUUID().toString(), queryForm.getEndDate()); //结束时间条件
		}
		
		sql.append(" \r\n join WAREHOUSE w on w.id = tt.warehouseid and w.USEFLAG = 1 ");
		sql.append(" \r\n INNER JOIN ORGAN o on tt.ORGANID = o.id and o.organtype = 2 and  organmodel = 1 ");
		sql.append(" \r\n LEFT JOIN PRODUCT p on ttd.OUTPRODUCTID = p.id  ");
		sql.append(" \r\n LEFT JOIN PRODUCT_STRUCT pstr on p.PSID = pstr.STRUCTCODE  ");
//		sql.append(" \r\n LEFT JOIN COUNTRY_AREA country on o.province=country.id  ");
		sql.append(" \r\n LEFT JOIN (SELECT max(REGIONCODEID) regioncodeid,max(AREAID) areaid  FROM REGION_AREA GROUP BY AREAID) ra on ra.areaid=o.province  ");
		sql.append(" \r\n LEFT JOIN REGION r on r.regioncode=ra.regioncodeid  ");
		if(DbUtil.isDealer(users)) {
			sql.append(" \r\n LEFT JOIN RULE_USER_WH rw on tt.warehouseid = rw.warehouse_id  ");
			sql.append(" \r\n where rw.user_id = ?");param.put(UUID.randomUUID().toString(), users.getUserid());
		} else {
			sql.append(" \r\n where "+DbUtil.getWhereCondition(users, "o"));
		}
		sql.append(" \r\n and ttd.wastage <> 0 ");
		if(!StringUtil.isEmpty(queryForm.getRegion())){
			sql.append(" \r\n and r.regioncode = ? "); param.put(UUID.randomUUID().toString(), queryForm.getRegion());
		}
		sql.append(" \r\n  and p.USEFLAG = 1 ");
		//#end modified by ryan.xi at 20150714
		
		
		//#start modified by ryan.xi at 20150805
		//其他入库-减消耗-20150429
		sql.append(" \r\n union all");
		sql.append(" \r\n --其他入库-减消耗");
		sql.append(" \r\n select DISTINCT oia.id billNo ");
		sql.append(" \r\n ,r.sortname regionName  ");
		sql.append(" \r\n ,o.province ");
		sql.append(" \r\n ,o.city ");
		sql.append(" \r\n ,o.areas ");
		sql.append(" \r\n ,o.organModel ");
		sql.append(" \r\n ,o.oecode oecode ");
//		sql.append(" \r\n ,country.areaname province  ");
		sql.append(" \r\n ,o.id  organId");
		sql.append(" \r\n ,o.organname organName  ");
		sql.append(" \r\n ,oida.productid productId ");
		sql.append(" \r\n ,pstr.sortname psName ");
		sql.append(" \r\n ,p.productname productName ");
		sql.append(" \r\n  ,p.productnameen productNameen  ");
		sql.append(" \r\n ,p.mcode mCode ");
		sql.append(" \r\n ,p.matericalchdes matericalChDes  ");
		sql.append(" \r\n ,p.matericalendes matericalEnDes  ");
		sql.append(" \r\n ,p.sunit unitId  ");
		sql.append(" \r\n ,p.packsizename packSizeName ");
		sql.append(" \r\n  ,p.packsizenameen packSizeNameEn  ");
		sql.append(" \r\n ,oida.batch batch  ");
		sql.append(" \r\n ,0 salesQuantity  ");
		sql.append(" \r\n ,-(oida.quantity * f.xquantity) consumeQuantity  ");
		sql.append(" \r\n ,oia.AUDITDATE makeDate  ");
		sql.append(" \r\n ,o.organname outOrganName,w.warehousename  ");
		sql.append(" \r\n from OTHER_INCOME_ALL oia INNER JOIN OTHER_INCOME_DETAIL_ALL oida on oia.id = oida.oiid and oia.isaudit = 1 and oia.incomesort <> 6 ");
		if(!StringUtil.isEmpty(queryForm.getOrganId())){
			sql.append(" \r\n and oia.organid = ? ");param.put(UUID.randomUUID().toString(), queryForm.getOrganId()); //机构条件
		}
		if(!StringUtil.isEmpty(queryForm.getWarehouseId())){
			sql.append(" \r\n and oia.warehouseid = ? ");param.put(UUID.randomUUID().toString(), queryForm.getWarehouseId()); //仓库条件
		}
		if(!StringUtil.isEmpty(queryForm.getProductName()) && !StringUtil.isEmpty(queryForm.getPackSizeName())) { //产品条件
			sql.append(" \r\n and oida.productid in (select id from product where productname = ? and packsizename= ?) ");param.put(UUID.randomUUID().toString(), queryForm.getProductName());param.put(UUID.randomUUID().toString(), queryForm.getPackSizeName());
		} else if(!StringUtil.isEmpty(queryForm.getProductName()) && StringUtil.isEmpty(queryForm.getPackSizeName())) { //产品条件
			sql.append(" \r\n and oida.productid in (select id from product where productname = ?) ");param.put(UUID.randomUUID().toString(), queryForm.getProductName());
		}
		if(!StringUtil.isEmpty(queryForm.getHistoryDate())){
			sql.append(" \r\n and oia.auditdate >=to_date(?,'yyyy-MM-dd hh24:mi:ss') ");param.put(UUID.randomUUID().toString(), queryForm.getHistoryDate()); //开始时间条件
		}
		if(!StringUtil.isEmpty(queryForm.getEndDate())){
			sql.append(" \r\n and oia.auditdate < to_date(?,'yyyy-MM-dd hh24:mi:ss') ");param.put(UUID.randomUUID().toString(), queryForm.getEndDate()); //结束时间条件
		}
		sql.append(" \r\n join WAREHOUSE w on w.id = oia.warehouseid and w.USEFLAG = 1");
		sql.append(" \r\n INNER JOIN ORGAN o on oia.organid= o.id and o.organtype = 2 and  organmodel = 1 ");
		sql.append(" \r\n LEFT JOIN F_UNIT f on oida.productid = f.productid and oida.unitid = f.funitid ");
		sql.append(" \r\n LEFT JOIN PRODUCT p on oida.productid = p.id ");
		sql.append(" \r\n LEFT JOIN PRODUCT_STRUCT pstr on p.PSID = pstr.STRUCTCODE   ");
//		sql.append(" \r\n LEFT JOIN COUNTRY_AREA country on o.province=country.id   ");
		sql.append(" \r\n LEFT JOIN (SELECT max(REGIONCODEID) regioncodeid,max(AREAID) areaid  FROM REGION_AREA GROUP BY AREAID) ra on ra.areaid=o.province   ");
		sql.append(" \r\n LEFT JOIN REGION r on r.regioncode=ra.regioncodeid   ");
		if(DbUtil.isDealer(users)) {
			sql.append(" \r\n INNER JOIN RULE_USER_WH rw on oia.warehouseid = rw.warehouse_id ");
			sql.append(" \r\n where rw.user_id = ?");param.put(UUID.randomUUID().toString(), users.getUserid());
		} else {
			sql.append(" \r\n where "+DbUtil.getWhereCondition(users, "o"));
		}
		if(!StringUtil.isEmpty(queryForm.getRegion())){
			sql.append(" \r\n and r.regioncode = ? "); param.put(UUID.randomUUID().toString(), queryForm.getRegion());
		}
		sql.append(" \r\n  and p.USEFLAG = 1 ");
		
		//其他出库-加消耗-20150429
		sql.append(" \r\n union all");
		sql.append(" \r\n --其他出库-加消耗");
		sql.append(" \r\n select DISTINCT oia.id billNo ");
		sql.append(" \r\n ,r.sortname regionName  ");
		sql.append(" \r\n ,o.province ");
		sql.append(" \r\n ,o.city ");
		sql.append(" \r\n ,o.areas ");
		sql.append(" \r\n ,o.organModel ");
		sql.append(" \r\n ,o.oecode oecode ");
//		sql.append(" \r\n ,country.areaname province  ");
		sql.append(" \r\n ,o.id  organId");
		sql.append(" \r\n ,o.organname organName  ");
		sql.append(" \r\n ,oida.productid productId ");
		sql.append(" \r\n ,pstr.sortname psName ");
		sql.append(" \r\n ,p.productname productName ");
		sql.append(" \r\n  ,p.productnameen productNameen  ");
		sql.append(" \r\n ,p.mcode mCode ");
		sql.append(" \r\n ,p.matericalchdes matericalChDes  ");
		sql.append(" \r\n ,p.matericalendes matericalEnDes  ");
		sql.append(" \r\n ,p.sunit unitId  ");
		sql.append(" \r\n ,p.packsizename packSizeName ");
		sql.append(" \r\n  ,p.packsizenameen packSizeNameEn  ");
		sql.append(" \r\n ,oida.batch batch  ");
		sql.append(" \r\n ,0 salesQuantity  ");
		sql.append(" \r\n ,(oida.quantity * f.xquantity) consumeQuantity  ");
		sql.append(" \r\n ,oia.AUDITDATE makeDate  ");
		sql.append(" \r\n ,o.organname outOrganName,w.warehousename  ");
		sql.append(" \r\n from OTHER_SHIPMENT_BILL_ALL oia INNER JOIN OTHER_SHIPMENT_BILL_D_ALL oida on oia.id = oida.osid and oia.isaudit = 1 ");
		if(!StringUtil.isEmpty(queryForm.getOrganId())){
			sql.append(" \r\n and oia.organid = ? ");param.put(UUID.randomUUID().toString(), queryForm.getOrganId()); //机构条件
		}
		if(!StringUtil.isEmpty(queryForm.getWarehouseId())){
			sql.append(" \r\n and oia.warehouseid = ? ");param.put(UUID.randomUUID().toString(), queryForm.getWarehouseId()); //仓库条件
		}
		if(!StringUtil.isEmpty(queryForm.getProductName()) && !StringUtil.isEmpty(queryForm.getPackSizeName())) { //产品条件
			sql.append(" \r\n and oida.productid in (select id from product where productname = ? and packsizename= ?) ");param.put(UUID.randomUUID().toString(), queryForm.getProductName());param.put(UUID.randomUUID().toString(), queryForm.getPackSizeName());
		} else if(!StringUtil.isEmpty(queryForm.getProductName()) && StringUtil.isEmpty(queryForm.getPackSizeName())) { //产品条件
			sql.append(" \r\n and oida.productid in (select id from product where productname = ?) ");param.put(UUID.randomUUID().toString(), queryForm.getProductName());
		}
		if(!StringUtil.isEmpty(queryForm.getHistoryDate())){
			sql.append(" \r\n and oia.auditdate >=to_date(?,'yyyy-MM-dd hh24:mi:ss') ");param.put(UUID.randomUUID().toString(), queryForm.getHistoryDate()); //开始时间条件
		}
		if(!StringUtil.isEmpty(queryForm.getEndDate())){
			sql.append(" \r\n and oia.auditdate < to_date(?,'yyyy-MM-dd hh24:mi:ss') ");param.put(UUID.randomUUID().toString(), queryForm.getEndDate()); //结束时间条件
		}
		sql.append(" \r\n join WAREHOUSE w on w.id = oia.warehouseid and w.USEFLAG = 1");
		sql.append(" \r\n INNER JOIN ORGAN o on oia.organid= o.id and o.organtype = 2 and  organmodel = 1 ");
		sql.append(" \r\n LEFT JOIN F_UNIT f on oida.productid = f.productid and oida.unitid = f.funitid ");
		sql.append(" \r\n LEFT JOIN PRODUCT p on oida.productid = p.id ");
		sql.append(" \r\n LEFT JOIN PRODUCT_STRUCT pstr on p.PSID = pstr.STRUCTCODE   ");
//		sql.append(" \r\n LEFT JOIN COUNTRY_AREA country on o.province=country.id   ");
		sql.append(" \r\n LEFT JOIN (SELECT max(REGIONCODEID) regioncodeid,max(AREAID) areaid  FROM REGION_AREA GROUP BY AREAID) ra on ra.areaid=o.province   ");
		sql.append(" \r\n LEFT JOIN REGION r on r.regioncode=ra.regioncodeid   ");
		if(DbUtil.isDealer(users)) {
			sql.append(" \r\n INNER JOIN RULE_USER_WH rw on oia.warehouseid = rw.warehouse_id ");
			sql.append(" \r\n where rw.user_id = ?");param.put(UUID.randomUUID().toString(), users.getUserid());
		} else {
			sql.append(" \r\n where "+DbUtil.getWhereCondition(users, "o"));
		}
		if(!StringUtil.isEmpty(queryForm.getRegion())){
			sql.append(" \r\n and r.regioncode = ? "); param.put(UUID.randomUUID().toString(), queryForm.getRegion());
		}
		sql.append(" \r\n  and p.USEFLAG = 1 ");
		//#end modified by ryan.xi at 20150805
		
		//消耗数量
		sql.append(" \r\n union all");
		sql.append(" \r\n select sdh.billNo billNo ");
		sql.append(" \r\n ,r.sortname regionName ");
		sql.append(" \r\n ,ino.province ");
		sql.append(" \r\n ,ino.city ");
		sql.append(" \r\n ,ino.areas ");
		sql.append(" \r\n ,ino.organModel ");
		sql.append(" \r\n ,ino.oecode oecode ");
//		sql.append(" \r\n ,ca.areaname province ");
		sql.append(" \r\n ,ino.id  organId ");
		sql.append(" \r\n ,ino.organname organName ");
		sql.append(" \r\n ,p.id productId  ");
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
		sql.append(" \r\n join organ o on o.id = sdh.outorganid and o.isrepeal=0");
		if(!StringUtil.isEmpty(queryForm.getOrganId())){
			sql.append(" \r\n and o.id = ? ");param.put(UUID.randomUUID().toString(), queryForm.getOrganId()); //机构条件
		}
		if(!StringUtil.isEmpty(queryForm.getWarehouseId())){
			sql.append(" \r\n and sdh.outwarehouseid = ? ");param.put(UUID.randomUUID().toString(), queryForm.getWarehouseId()); //仓库条件
		}
		if(!StringUtil.isEmpty(queryForm.getBeginDate())){
			sql.append(" \r\n and sdh.makeDate >=to_date(?,'yyyy-MM-dd hh24:mi:ss') ");param.put(UUID.randomUUID().toString(), queryForm.getBeginDate()); //开始时间条件
		}
		if(!StringUtil.isEmpty(queryForm.getHistoryDate())){
			sql.append(" \r\n and sdh.makeDate < to_date(?,'yyyy-MM-dd hh24:mi:ss') ");param.put(UUID.randomUUID().toString(), queryForm.getHistoryDate()); //结束时间条件
		}
		if(DbUtil.isDealer(users)) {
			sql.append(" \r\n JOIN RULE_USER_WH rw on sdh.outwarehouseid = rw.warehouse_id  ");
			sql.append(" \r\n and rw.user_id = ?");param.put(UUID.randomUUID().toString(), users.getUserid());
		} else {
			sql.append(" \r\n and "+DbUtil.getWhereCondition(users, "o"));
		}
		sql.append(" \r\n join organ ino on ino.id = sdh.organid and ino.isrepeal=0");
		sql.append(" \r\n join PRODUCT p on p.id = sdh.productid and p.useflag=1");
		if(!StringUtil.isEmpty(queryForm.getProductName()) && !StringUtil.isEmpty(queryForm.getPackSizeName())) { //产品条件
			sql.append(" \r\n and p.productname = ? and specmode= ? ");param.put(UUID.randomUUID().toString(), queryForm.getProductName());param.put(UUID.randomUUID().toString(), queryForm.getPackSizeName());
		} else if(!StringUtil.isEmpty(queryForm.getProductName()) && StringUtil.isEmpty(queryForm.getPackSizeName())) { //产品条件
			sql.append(" \r\n and p.productname = ? ");param.put(UUID.randomUUID().toString(), queryForm.getProductName());
		}
		sql.append(" \r\n join WAREHOUSE w on w.id = sdh.outwarehouseid and w.useflag=1");
		sql.append(" \r\n join PRODUCT_STRUCT pstr on pstr.structcode=p.psid");
//		sql.append(" \r\n LEFT JOIN COUNTRY_AREA ca on ca.id = o.province");
		sql.append(" \r\n LEFT JOIN REGION_AREA ra on RA.AREAID=o.province");
		sql.append(" \r\n LEFT JOIN REGION r on r.regioncode = ra.regioncodeid");
		if(!StringUtil.isEmpty(queryForm.getRegion())){
			sql.append(" \r\n where r.regioncode = ? ");param.put(UUID.randomUUID().toString(), queryForm.getRegion()); 
		}
		System.out.println(sql.toString());
		List<Map> list =  new ArrayList<Map>();
		if(request == null && pageSize == 0){
			list =  EntityManager.jdbcquery(sql.toString(), param);
		}else {
			list =  PageQuery.jdbcSqlserverQuery(request,"billNo", sql.toString(), pageSize, param);
		}
		if(list != null && list.size()>0){
			Map<String,String> sapDeliveryNos = new HashMap<String, String>();
			Map<String,FUnit>  funitMap = appFUnit.getAllMap(); //获取所有的单位换算信息
			Map<Integer, String> countUnitMap = appBr.getBaseResourceMap("CountUnit");
			Map<String, String> areaNameMap =  appCountryArea.getAreaNameMap();
			for(Map map : list){
				SalesConsumeDetailReportForm scForm = new SalesConsumeDetailReportForm();
				//将Map中对应的值赋值给实例
				MapUtil.mapToObject(map, scForm);
				//设置省市区
				scForm.setProvinceId(scForm.getProvince());
				scForm.setCityId(scForm.getCity());
				scForm.setAreasId(scForm.getAreas());
				if(!StringUtil.isEmpty(scForm.getProvince())) {
					scForm.setProvince(areaNameMap.get(scForm.getProvince()));
				}
				if(!StringUtil.isEmpty(scForm.getCity())) {
					scForm.setCity(areaNameMap.get(scForm.getCity()));
				}
				if(!StringUtil.isEmpty(scForm.getAreas())) {
					scForm.setAreas(areaNameMap.get(scForm.getAreas()));
				}
				//报告日期主当前日期
				scForm.setReportDate(new Timestamp(System.currentTimeMillis()));
				//单据日期
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

	// 根据条件查询
	public List<SalesConsumeDetailReportForm> queryConsumeDetailReport2(HttpServletRequest request, int pageSize,SalesConsumeDetailReportForm queryForm,UsersBean users) throws Exception{
		Map<String, Object> param = new LinkedHashMap<String, Object>();
		//对于结束日期增加一天
		if(!StringUtil.isEmpty(queryForm.getEndDate())){
			String endDateStr = queryForm.getEndDate();
			Date endDate = Dateutil.StringToDate(endDateStr);
			endDate = Dateutil.addDay2Date(1, endDate);
			queryForm.setEndDate(Dateutil.formatDate(endDate));
		}
		
		List<SalesConsumeDetailReportForm> resultList = new ArrayList<SalesConsumeDetailReportForm>();
		StringBuffer sql = new StringBuffer();
		
		//消耗数量,ino.organType  
		sql.append(" \r\n --正常出库消耗 ");
		sql.append(" \r\n select DISTINCT tt.billNo billNo ");
		sql.append(" \r\n ,r.sortname regionName ");
		sql.append(" \r\n ,ino.province ");
		sql.append(" \r\n ,ino.city ");
		sql.append(" \r\n ,ino.areas ");
		sql.append(" \r\n ,ino.organModel ");
		sql.append(" \r\n ,ino.oecode oecode ");
//		sql.append(" \r\n ,country.areaname province ");
		sql.append(" \r\n ,tt.inoid  organId ");
		sql.append(" \r\n ,ino.organname organName ");
		sql.append(" \r\n ,ttd.productid productId  ");
		sql.append(" \r\n ,pstr.sortname psName ");
		sql.append(" \r\n ,p.productname productName ");
		sql.append(" \r\n ,p.productnameen productNameen ");
		sql.append(" \r\n ,p.mcode mCode ");
		sql.append(" \r\n ,p.matericalchdes matericalChDes ");
		sql.append(" \r\n ,p.matericalendes matericalEnDes ");
		sql.append(" \r\n ,p.sunit unitId ");
		sql.append(" \r\n ,p.specmode packSizeName");
		sql.append(" \r\n ,p.packsizenameen packSizeNameEn");
		sql.append(" \r\n ,swb.batch batch ");
		sql.append(" \r\n ,0 salesQuantity ");  // 销售数量
		sql.append(" \r\n ,swb.cycleoutquantity  consumeQuantity"); //消耗数量
		sql.append(" \r\n ,TT.AUDITDATE makeDate ");
//		sql.append(" \r\n ,pj.production_date produceDate");
//		sql.append(" \r\n ,pj.expiry_date expiryDate");
		sql.append(" \r\n ,o.organname outOrganName,w.warehousename ");
		sql.append(" \r\n ,case when swb.batch = '9999999999' then 0 else swb.cycleoutquantity end scanedQuantity ");  // 销售数量
		sql.append(" \r\n from TAKE_TICKET tt INNER JOIN TAKE_TICKET_DETAIL ttd on tt.id = ttd.ttid  and tt.isaudit = 1  and tt.bsort = 1 and tt.oid <> tt.inoid ");
		if(!StringUtil.isEmpty(queryForm.getOrganId())){
			sql.append(" \r\n and tt.oid = ? ");param.put(UUID.randomUUID().toString(), queryForm.getOrganId()); //机构条件
		}
		if(!StringUtil.isEmpty(queryForm.getWarehouseId())){
			sql.append(" \r\n and tt.WAREHOUSEID = ? ");param.put(UUID.randomUUID().toString(), queryForm.getWarehouseId()); //仓库条件
		}
		if(!StringUtil.isEmpty(queryForm.getProductName()) && !StringUtil.isEmpty(queryForm.getPackSizeName())) { //产品条件
			sql.append(" \r\n and ttd.productid in (select id from product where productname = ? and specmode=?) ");param.put(UUID.randomUUID().toString(), queryForm.getProductName());param.put(UUID.randomUUID().toString(), queryForm.getPackSizeName());
		} else if(!StringUtil.isEmpty(queryForm.getProductName()) && StringUtil.isEmpty(queryForm.getPackSizeName())) { //产品条件
			sql.append(" \r\n and ttd.productid in (select id from product where productname = ?) ");param.put(UUID.randomUUID().toString(), queryForm.getProductName());
		}
		if(!StringUtil.isEmpty(queryForm.getBeginDate())){
			sql.append(" \r\n and tt.auditdate >=to_date(?,'yyyy-MM-dd hh24:mi:ss') ");param.put(UUID.randomUUID().toString(), queryForm.getBeginDate()); //开始时间条件
		}
		if(!StringUtil.isEmpty(queryForm.getEndDate())){
			sql.append(" \r\n and tt.auditdate < to_date(?,'yyyy-MM-dd hh24:mi:ss') ");param.put(UUID.randomUUID().toString(), queryForm.getEndDate()); //结束时间条件
		}
		
		sql.append(" \r\n join WAREHOUSE w on w.id = tt.warehouseid and w.USEFLAG = 1");
		
		sql.append(" \r\n INNER JOIN STOCK_WASTE_BOOK swb on swb.billcode = tt.id and ttd.productid = swb.productid ");
		sql.append(" \r\n INNER JOIN ORGAN o on tt.oid = o.id and o.organtype = 2 and  organmodel = 1 ");
		sql.append(" \r\n INNER JOIN ORGAN ino on tt.inoid = ino.id");
		sql.append(" \r\n LEFT JOIN F_UNIT f on ttd.productid = f.productid and ttd.unitid = f.funitid ");
		
		sql.append(" \r\n LEFT JOIN PRODUCT p on ttd.productid = p.id  ");
		sql.append(" \r\n LEFT JOIN PRODUCT_STRUCT pstr on p.PSID = pstr.STRUCTCODE  ");
//		sql.append(" \r\n LEFT JOIN COUNTRY_AREA country on ino.province=country.id  ");
		sql.append(" \r\n LEFT JOIN (SELECT max(REGIONCODEID) regioncodeid,max(AREAID) areaid  FROM REGION_AREA GROUP BY AREAID) ra on ra.areaid=ino.province   ");
		sql.append(" \r\n LEFT JOIN REGION r on r.regioncode=ra.regioncodeid  ");
		if(DbUtil.isDealer(users)) {
			sql.append(" \r\n LEFT JOIN RULE_USER_WH rw on tt.warehouseid = rw.warehouse_id  ");
			sql.append(" \r\n where rw.user_id = ?");param.put(UUID.randomUUID().toString(), users.getUserid());
		} else {
			sql.append(" \r\n where "+DbUtil.getWhereCondition(users, "o"));
		}
		if(!StringUtil.isEmpty(queryForm.getRegion())){
			sql.append(" \r\n and r.regioncode = ? "); param.put(UUID.randomUUID().toString(), queryForm.getRegion());
		}
		sql.append(" \r\n  and p.USEFLAG = 1 ");
		sql.append(" \r\n union all");
		sql.append(" \r\n --工厂给PD收发差异记到消耗 ");
		//工厂给PD收发差异记到消耗
		sql.append(" \r\n select DISTINCT tt.billNo billNo ");
		sql.append(" \r\n ,r.sortname regionName  ");
		sql.append(" \r\n ,ino.province  ");
		sql.append(" \r\n ,ino.city ");
		sql.append(" \r\n ,ino.areas ");
		sql.append(" \r\n ,ino.organModel ");
		sql.append(" \r\n ,ino.oecode oecode ");
//		sql.append(" \r\n ,country.areaname province  ");
		sql.append(" \r\n ,ino.id  organId");
		sql.append(" \r\n ,ino.organname organName  ");
		sql.append(" \r\n ,ttd.productid productId ");
		sql.append(" \r\n ,pstr.sortname psName ");
		sql.append(" \r\n ,p.productname productName ");
		sql.append(" \r\n  ,p.productnameen productNameen  ");
		sql.append(" \r\n ,p.mcode mCode ");
		sql.append(" \r\n ,p.matericalchdes matericalChDes  ");
		sql.append(" \r\n ,p.matericalendes matericalEnDes  ");
		sql.append(" \r\n ,p.sunit unitId  ");
		sql.append(" \r\n ,p.specmode packSizeName ");
		sql.append(" \r\n  ,p.packsizenameen packSizeNameEn  ");
		sql.append(" \r\n ,'"+Constants.NO_BATCH+"' batch  ");
		sql.append(" \r\n ,0 salesQuantity  ");
		sql.append(" \r\n ,((samd.quantity-samd.receivequantity) * f.xquantity)  consumeQuantity ");
		sql.append(" \r\n ,sam.movedate makeDate  ");
		sql.append(" \r\n ,ino.organname outOrganName ,w.warehousename ");
		sql.append(" \r\n ,0 scanedQuantity ");  // 销售数量
		sql.append(" \r\n from TAKE_TICKET tt INNER JOIN TAKE_TICKET_DETAIL ttd on tt.id = ttd.ttid  and tt.isaudit = 1  and tt.bsort = 1 ");
		if(!StringUtil.isEmpty(queryForm.getOrganId())){
			sql.append(" \r\n and tt.inoid = ? ");param.put(UUID.randomUUID().toString(), queryForm.getOrganId()); //机构条件
		}
		if(!StringUtil.isEmpty(queryForm.getWarehouseId())){
			sql.append(" \r\n and tt.inwarehouseid = ? ");param.put(UUID.randomUUID().toString(), queryForm.getWarehouseId()); //仓库条件
		}
		if(!StringUtil.isEmpty(queryForm.getProductName()) && !StringUtil.isEmpty(queryForm.getPackSizeName())) { //产品条件
			sql.append(" \r\n and ttd.productid in (select id from product where productname = ? and specmode=?) ");param.put(UUID.randomUUID().toString(), queryForm.getProductName());param.put(UUID.randomUUID().toString(), queryForm.getPackSizeName());
		} else if(!StringUtil.isEmpty(queryForm.getProductName()) && StringUtil.isEmpty(queryForm.getPackSizeName())) { //产品条件
			sql.append(" \r\n and ttd.productid in (select id from product where productname = ?) ");param.put(UUID.randomUUID().toString(), queryForm.getProductName());
		}
		sql.append(" \r\n join STOCK_ALTER_MOVE sam on sam.id=tt.billno and sam.iscomplete =1");
		if(!StringUtil.isEmpty(queryForm.getBeginDate())){
			sql.append(" \r\n and sam.movedate >=to_date(?,'yyyy-MM-dd hh24:mi:ss') ");param.put(UUID.randomUUID().toString(), queryForm.getBeginDate()); //开始时间条件
		}
		if(!StringUtil.isEmpty(queryForm.getEndDate())){
			sql.append(" \r\n and sam.movedate < to_date(?,'yyyy-MM-dd hh24:mi:ss') ");param.put(UUID.randomUUID().toString(), queryForm.getEndDate()); //结束时间条件
			
		}
		sql.append(" \r\n join WAREHOUSE w on w.id = tt.inwarehouseid and w.USEFLAG = 1");
		sql.append(" \r\n join STOCK_ALTER_MOVE_DETAIL samd on samd.samid = sam.id and samd.productid=ttd.productid");
		sql.append(" \r\n INNER JOIN ORGAN o on tt.oid = o.id and o.organtype = 1   ");
		sql.append(" \r\n LEFT JOIN F_UNIT f on ttd.productid = f.productid and ttd.unitid = f.funitid  ");
		sql.append(" \r\n LEFT JOIN ORGAN ino on tt.inoid = ino.id  and ino.organtype = 2 and ino.organmodel = 1");
		sql.append(" \r\n LEFT JOIN PRODUCT p on ttd.productid = p.id   ");
		sql.append(" \r\n LEFT JOIN PRODUCT_STRUCT pstr on p.PSID = pstr.STRUCTCODE   ");
//		sql.append(" \r\n LEFT JOIN COUNTRY_AREA country on ino.province=country.id   ");
		sql.append(" \r\n LEFT JOIN (SELECT max(REGIONCODEID) regioncodeid,max(AREAID) areaid  FROM REGION_AREA GROUP BY AREAID) ra on ra.areaid=ino.province   ");
		sql.append(" \r\n LEFT JOIN REGION r on r.regioncode=ra.regioncodeid   ");
		if(DbUtil.isDealer(users)) {
			sql.append(" \r\n LEFT JOIN RULE_USER_WH rw on tt.inwarehouseid = rw.warehouse_id   ");
			sql.append(" \r\n where rw.user_id = ?");param.put(UUID.randomUUID().toString(), users.getUserid());
			
		} else {
			sql.append(" \r\n where "+DbUtil.getWhereCondition(users, "ino"));
		}
		sql.append(" and (samd.quantity-samd.receivequantity) > 0 ");
		if(!StringUtil.isEmpty(queryForm.getRegion())){
			sql.append(" \r\n and r.regioncode = ? "); param.put(UUID.randomUUID().toString(), queryForm.getRegion());
		}
		sql.append(" \r\n  and p.USEFLAG = 1 ");
		
		
		//经销商退货-减消耗-20150429
		sql.append(" \r\n union all");
		sql.append(" \r\n --经销商退货-减消耗");
		sql.append(" \r\n select DISTINCT tt.billNo billNo ");
		sql.append(" \r\n ,r.sortname regionName  ");
		sql.append(" \r\n ,o.province ");
		sql.append(" \r\n ,o.city ");
		sql.append(" \r\n ,o.areas ");
		sql.append(" \r\n ,o.organModel ");
		sql.append(" \r\n ,o.oecode oecode ");
//		sql.append(" \r\n ,country.areaname province  ");
		sql.append(" \r\n ,o.id  organId");
		sql.append(" \r\n ,o.organname organName  ");
		sql.append(" \r\n ,ttd.productid productId ");
		sql.append(" \r\n ,pstr.sortname psName ");
		sql.append(" \r\n ,p.productname productName ");
		sql.append(" \r\n  ,p.productnameen productNameen  ");
		sql.append(" \r\n ,p.mcode mCode ");
		sql.append(" \r\n ,p.matericalchdes matericalChDes  ");
		sql.append(" \r\n ,p.matericalendes matericalEnDes  ");
		sql.append(" \r\n ,p.sunit unitId  ");
		sql.append(" \r\n ,p.specmode packSizeName ");
		sql.append(" \r\n  ,p.packsizenameen packSizeNameEn  ");
		sql.append(" \r\n ,swb.batch batch  ");
		sql.append(" \r\n ,0 salesQuantity  ");
		sql.append(" \r\n ,-swb.cycleoutquantity  consumeQuantity ");
		sql.append(" \r\n ,TT.AUDITDATE makeDate  ");
		sql.append(" \r\n ,ino.organname outOrganName,w.warehousename  ");
		sql.append(" \r\n ,case when swb.batch = '9999999999' then 0 else -swb.cycleoutquantity end scanedQuantity ");  // 销售数量
		sql.append(" \r\n from TAKE_TICKET tt INNER JOIN TAKE_TICKET_DETAIL ttd on tt.id = ttd.ttid  and tt.isaudit = 1  and tt.bsort = 7 and tt.billno like 'OW%' ");
		if(!StringUtil.isEmpty(queryForm.getOrganId())){
			sql.append(" \r\n and tt.inoid = ? ");param.put(UUID.randomUUID().toString(), queryForm.getOrganId()); //机构条件
		}
		if(!StringUtil.isEmpty(queryForm.getWarehouseId())){
			sql.append(" \r\n and tt.inwarehouseid = ? ");param.put(UUID.randomUUID().toString(), queryForm.getWarehouseId()); //仓库条件
		}
		if(!StringUtil.isEmpty(queryForm.getProductName()) && !StringUtil.isEmpty(queryForm.getPackSizeName())) { //产品条件
			sql.append(" \r\n and ttd.productid in (select id from product where productname = ? and specmode=?) ");param.put(UUID.randomUUID().toString(), queryForm.getProductName());param.put(UUID.randomUUID().toString(), queryForm.getPackSizeName());
		} else if(!StringUtil.isEmpty(queryForm.getProductName()) && StringUtil.isEmpty(queryForm.getPackSizeName())) { //产品条件
			sql.append(" \r\n and ttd.productid in (select id from product where productname = ?) ");param.put(UUID.randomUUID().toString(), queryForm.getProductName());
		}
		if(!StringUtil.isEmpty(queryForm.getBeginDate())){
			sql.append(" \r\n and tt.auditdate >=to_date(?,'yyyy-MM-dd hh24:mi:ss') ");param.put(UUID.randomUUID().toString(), queryForm.getBeginDate()); //开始时间条件
		}
		if(!StringUtil.isEmpty(queryForm.getEndDate())){
			sql.append(" \r\n and tt.auditdate < to_date(?,'yyyy-MM-dd hh24:mi:ss') ");param.put(UUID.randomUUID().toString(), queryForm.getEndDate()); //结束时间条件
		}
		sql.append(" \r\n join WAREHOUSE w on w.id = tt.inwarehouseid and w.USEFLAG = 1");
		sql.append(" \r\n INNER JOIN ORGAN ino on tt.inoid = ino.id and ino.organtype = 2 and  ino.organmodel = 1 ");
		sql.append(" \r\n INNER JOIN ORGAN o on tt.oid = o.id ");
		sql.append(" \r\n INNER JOIN STOCK_WASTE_BOOK swb on swb.billcode = tt.id and ttd.productid = swb.productid  ");
		sql.append(" \r\n LEFT JOIN F_UNIT f on ttd.productid = f.productid and ttd.unitid = f.funitid  ");
		sql.append(" \r\n LEFT JOIN PRODUCT p on ttd.productid = p.id   ");
		sql.append(" \r\n LEFT JOIN PRODUCT_STRUCT pstr on p.PSID = pstr.STRUCTCODE   ");
//		sql.append(" \r\n LEFT JOIN COUNTRY_AREA country on o.province=country.id   ");
		sql.append(" \r\n LEFT JOIN (SELECT max(REGIONCODEID) regioncodeid,max(AREAID) areaid  FROM REGION_AREA GROUP BY AREAID) ra on ra.areaid=o.province   ");
		sql.append(" \r\n LEFT JOIN REGION r on r.regioncode=ra.regioncodeid   ");
		if(DbUtil.isDealer(users)) {
			sql.append(" \r\n LEFT JOIN RULE_USER_WH rw on tt.inwarehouseid = rw.warehouse_id   ");
			sql.append(" \r\n where rw.user_id = ?");param.put(UUID.randomUUID().toString(), users.getUserid());
		} else {
			sql.append(" \r\n where "+DbUtil.getWhereCondition(users, "ino"));
		}
		if(!StringUtil.isEmpty(queryForm.getRegion())){
			sql.append(" \r\n and r.regioncode = ? "); param.put(UUID.randomUUID().toString(), queryForm.getRegion());
		}
		sql.append(" \r\n  and p.USEFLAG = 1 ");
		//#start modified by ryan.xi at 20150714
		//分包损耗记到消耗
		sql.append(" \r\n --分包损耗记到消耗");
		sql.append(" \r\n union all");
		sql.append(" \r\n select DISTINCT tt.id billNo ");
		sql.append(" \r\n ,r.sortname regionName ");
		sql.append(" \r\n ,o.province ");
		sql.append(" \r\n ,o.city ");
		sql.append(" \r\n ,o.areas ");
		sql.append(" \r\n ,o.organModel ");
		sql.append(" \r\n ,o.oecode oecode ");
//		sql.append(" \r\n ,country.areaname province ");
		sql.append(" \r\n ,o.id  organId ");
		sql.append(" \r\n ,o.organname organName ");
		sql.append(" \r\n ,ttd.OUTPRODUCTID productId  ");
		sql.append(" \r\n ,pstr.sortname psName ");
		sql.append(" \r\n ,p.productname productName ");
		sql.append(" \r\n ,p.productnameen productNameen ");
		sql.append(" \r\n ,p.mcode mCode ");
		sql.append(" \r\n ,p.matericalchdes matericalChDes ");
		sql.append(" \r\n ,p.matericalendes matericalEnDes ");
		sql.append(" \r\n ,p.sunit unitId ");
		sql.append(" \r\n ,p.specmode packSizeName");
		sql.append(" \r\n ,p.packsizenameen packSizeNameEn");
		sql.append(" \r\n ,ttd.outbatch batch ");
		sql.append(" \r\n ,0 salesQuantity ");  // 销售数量
		sql.append(" \r\n ,ttd.wastage consumeQuantity"); //消耗数量
		sql.append(" \r\n ,TT.AUDITDATE makeDate ");
		sql.append(" \r\n ,o.organname outOrganName,w.warehousename ");
		sql.append(" \r\n ,0 scanedQuantity ");  // 销售数量
		sql.append(" \r\n from PACK_SEPARATE tt INNER JOIN PACK_SEPARATE_DETAIL ttd on tt.id = ttd.psid  and tt.isaudit = 1 ");
		if(!StringUtil.isEmpty(queryForm.getOrganId())){
			sql.append(" \r\n and tt.ORGANID = ? ");param.put(UUID.randomUUID().toString(), queryForm.getOrganId()); //机构条件
		}
		if(!StringUtil.isEmpty(queryForm.getWarehouseId())){
			sql.append(" \r\n and tt.WAREHOUSEID = ? ");param.put(UUID.randomUUID().toString(), queryForm.getWarehouseId()); //仓库条件
		}
		if(!StringUtil.isEmpty(queryForm.getProductName()) && !StringUtil.isEmpty(queryForm.getPackSizeName())) { //产品条件
			sql.append(" \r\n and ttd.OUTPRODUCTID in (select id from product where productname = ? and specmode= ?) ");param.put(UUID.randomUUID().toString(), queryForm.getProductName());param.put(UUID.randomUUID().toString(), queryForm.getPackSizeName());
		} else if(!StringUtil.isEmpty(queryForm.getProductName()) && StringUtil.isEmpty(queryForm.getPackSizeName())) { //产品条件
			sql.append(" \r\n and ttd.OUTPRODUCTID in (select id from product where productname = ?) ");param.put(UUID.randomUUID().toString(), queryForm.getProductName());
		}
		if(!StringUtil.isEmpty(queryForm.getBeginDate())){
			sql.append(" \r\n and tt.auditdate >=to_date(?,'yyyy-MM-dd hh24:mi:ss') ");param.put(UUID.randomUUID().toString(), queryForm.getBeginDate()); //开始时间条件
		}
		if(!StringUtil.isEmpty(queryForm.getEndDate())){
			sql.append(" \r\n and tt.auditdate < to_date(?,'yyyy-MM-dd hh24:mi:ss') ");param.put(UUID.randomUUID().toString(), queryForm.getEndDate()); //结束时间条件
			
		}
		
		sql.append(" \r\n join WAREHOUSE w on w.id = tt.warehouseid and w.USEFLAG = 1 ");
		sql.append(" \r\n INNER JOIN ORGAN o on tt.ORGANID = o.id and o.organtype = 2 and  organmodel = 1 ");
		sql.append(" \r\n LEFT JOIN PRODUCT p on ttd.OUTPRODUCTID = p.id  ");
		sql.append(" \r\n LEFT JOIN PRODUCT_STRUCT pstr on p.PSID = pstr.STRUCTCODE  ");
//		sql.append(" \r\n LEFT JOIN COUNTRY_AREA country on o.province=country.id  ");
		sql.append(" \r\n LEFT JOIN (SELECT max(REGIONCODEID) regioncodeid,max(AREAID) areaid  FROM REGION_AREA GROUP BY AREAID) ra on ra.areaid=o.province  ");
		sql.append(" \r\n LEFT JOIN REGION r on r.regioncode=ra.regioncodeid  ");
		if(DbUtil.isDealer(users)) {
			sql.append(" \r\n LEFT JOIN RULE_USER_WH rw on tt.warehouseid = rw.warehouse_id  ");
			sql.append(" \r\n where rw.user_id = ?");param.put(UUID.randomUUID().toString(), users.getUserid());
		} else {
			sql.append(" \r\n where "+DbUtil.getWhereCondition(users, "o"));
		}
		sql.append(" \r\n and ttd.wastage <> 0 ");
		if(!StringUtil.isEmpty(queryForm.getRegion())){
			sql.append(" \r\n and r.regioncode = ? "); param.put(UUID.randomUUID().toString(), queryForm.getRegion());
		}
		sql.append(" \r\n  and p.USEFLAG = 1 ");
		//#end modified by ryan.xi at 20150714
		
		
		//#start modified by ryan.xi at 20150805
		//其他入库-减消耗-20150429
		sql.append(" \r\n union all");
		sql.append(" \r\n --其他入库-减消耗");
		sql.append(" \r\n select DISTINCT oia.id billNo ");
		sql.append(" \r\n ,r.sortname regionName  ");
		sql.append(" \r\n ,o.province  ");
		sql.append(" \r\n ,o.city ");
		sql.append(" \r\n ,o.areas ");
		sql.append(" \r\n ,o.organModel ");
		sql.append(" \r\n ,o.oecode oecode ");
//		sql.append(" \r\n ,country.areaname province  ");
		sql.append(" \r\n ,o.id  organId");
		sql.append(" \r\n ,o.organname organName  ");
		sql.append(" \r\n ,oida.productid productId ");
		sql.append(" \r\n ,pstr.sortname psName ");
		sql.append(" \r\n ,p.productname productName ");
		sql.append(" \r\n  ,p.productnameen productNameen  ");
		sql.append(" \r\n ,p.mcode mCode ");
		sql.append(" \r\n ,p.matericalchdes matericalChDes  ");
		sql.append(" \r\n ,p.matericalendes matericalEnDes  ");
		sql.append(" \r\n ,p.sunit unitId  ");
		sql.append(" \r\n ,p.packsizename packSizeName ");
		sql.append(" \r\n  ,p.packsizenameen packSizeNameEn  ");
		sql.append(" \r\n ,oida.batch batch  ");
		sql.append(" \r\n ,0 salesQuantity  ");
		sql.append(" \r\n ,-(oida.quantity * f.xquantity) consumeQuantity  ");
		sql.append(" \r\n ,oia.AUDITDATE makeDate  ");
		sql.append(" \r\n ,o.organname outOrganName,w.warehousename  ");
		sql.append(" \r\n ,0 scanedQuantity ");
		sql.append(" \r\n from OTHER_INCOME_ALL oia INNER JOIN OTHER_INCOME_DETAIL_ALL oida on oia.id = oida.oiid and oia.isaudit = 1 and oia.incomesort <> 6 ");
		if(!StringUtil.isEmpty(queryForm.getOrganId())){
			sql.append(" \r\n and oia.organid = ? ");param.put(UUID.randomUUID().toString(), queryForm.getOrganId()); //机构条件
		}
		if(!StringUtil.isEmpty(queryForm.getWarehouseId())){
			sql.append(" \r\n and oia.warehouseid = ? ");param.put(UUID.randomUUID().toString(), queryForm.getWarehouseId()); //仓库条件
		}
		if(!StringUtil.isEmpty(queryForm.getProductName()) && !StringUtil.isEmpty(queryForm.getPackSizeName())) { //产品条件
			sql.append(" \r\n and oida.productid in (select id from product where productname = ? and packsizename= ?) ");param.put(UUID.randomUUID().toString(), queryForm.getProductName());param.put(UUID.randomUUID().toString(), queryForm.getPackSizeName());
		} else if(!StringUtil.isEmpty(queryForm.getProductName()) && StringUtil.isEmpty(queryForm.getPackSizeName())) { //产品条件
			sql.append(" \r\n and oida.productid in (select id from product where productname = ?) ");param.put(UUID.randomUUID().toString(), queryForm.getProductName());
		}
		if(!StringUtil.isEmpty(queryForm.getBeginDate())){
			sql.append(" \r\n and oia.auditdate >=to_date(?,'yyyy-MM-dd hh24:mi:ss') ");param.put(UUID.randomUUID().toString(), queryForm.getBeginDate()); //开始时间条件
		}
		if(!StringUtil.isEmpty(queryForm.getEndDate())){
			sql.append(" \r\n and oia.auditdate < to_date(?,'yyyy-MM-dd hh24:mi:ss') ");param.put(UUID.randomUUID().toString(), queryForm.getEndDate()); //结束时间条件
		}
		sql.append(" \r\n join WAREHOUSE w on w.id = oia.warehouseid and w.USEFLAG = 1");
		sql.append(" \r\n INNER JOIN ORGAN o on oia.organid= o.id and o.organtype = 2 and  organmodel = 1 ");
		sql.append(" \r\n LEFT JOIN F_UNIT f on oida.productid = f.productid and oida.unitid = f.funitid ");
		sql.append(" \r\n LEFT JOIN PRODUCT p on oida.productid = p.id ");
		sql.append(" \r\n LEFT JOIN PRODUCT_STRUCT pstr on p.PSID = pstr.STRUCTCODE   ");
//		sql.append(" \r\n LEFT JOIN COUNTRY_AREA country on o.province=country.id   ");
		sql.append(" \r\n LEFT JOIN (SELECT max(REGIONCODEID) regioncodeid,max(AREAID) areaid  FROM REGION_AREA GROUP BY AREAID) ra on ra.areaid=o.province   ");
		sql.append(" \r\n LEFT JOIN REGION r on r.regioncode=ra.regioncodeid   ");
		if(DbUtil.isDealer(users)) {
			sql.append(" \r\n INNER JOIN RULE_USER_WH rw on oia.warehouseid = rw.warehouse_id ");
			sql.append(" \r\n where rw.user_id = ?");param.put(UUID.randomUUID().toString(), users.getUserid());
		} else {
			sql.append(" \r\n where "+DbUtil.getWhereCondition(users, "o"));
		}
		if(!StringUtil.isEmpty(queryForm.getRegion())){
			sql.append(" \r\n and r.regioncode = ? "); param.put(UUID.randomUUID().toString(), queryForm.getRegion());
		}
		sql.append(" \r\n  and p.USEFLAG = 1 ");
		
		//其他出库-加消耗-20150429
		sql.append(" \r\n union all");
		sql.append(" \r\n --其他出库-加消耗");
		sql.append(" \r\n select DISTINCT oia.id billNo ");
		sql.append(" \r\n ,r.sortname regionName  ");
		sql.append(" \r\n ,o.province  ");
		sql.append(" \r\n ,o.city ");
		sql.append(" \r\n ,o.areas ");
		sql.append(" \r\n ,o.organModel ");
		sql.append(" \r\n ,o.oecode oecode ");
//		sql.append(" \r\n ,country.areaname province  ");
		sql.append(" \r\n ,o.id  organId");
		sql.append(" \r\n ,o.organname organName  ");
		sql.append(" \r\n ,oida.productid productId ");
		sql.append(" \r\n ,pstr.sortname psName ");
		sql.append(" \r\n ,p.productname productName ");
		sql.append(" \r\n  ,p.productnameen productNameen  ");
		sql.append(" \r\n ,p.mcode mCode ");
		sql.append(" \r\n ,p.matericalchdes matericalChDes  ");
		sql.append(" \r\n ,p.matericalendes matericalEnDes  ");
		sql.append(" \r\n ,p.sunit unitId  ");
		sql.append(" \r\n ,p.packsizename packSizeName ");
		sql.append(" \r\n  ,p.packsizenameen packSizeNameEn  ");
		sql.append(" \r\n ,oida.batch batch  ");
		sql.append(" \r\n ,0 salesQuantity  ");
		sql.append(" \r\n ,(oida.quantity * f.xquantity) consumeQuantity  ");
		sql.append(" \r\n ,oia.AUDITDATE makeDate  ");
		sql.append(" \r\n ,o.organname outOrganName,w.warehousename  ");
		sql.append(" \r\n ,0 scanedQuantity ");
		sql.append(" \r\n from OTHER_SHIPMENT_BILL_ALL oia INNER JOIN OTHER_SHIPMENT_BILL_D_ALL oida on oia.id = oida.osid and oia.isaudit = 1 ");
		if(!StringUtil.isEmpty(queryForm.getOrganId())){
			sql.append(" \r\n and oia.organid = ? ");param.put(UUID.randomUUID().toString(), queryForm.getOrganId()); //机构条件
		}
		if(!StringUtil.isEmpty(queryForm.getWarehouseId())){
			sql.append(" \r\n and oia.warehouseid = ? ");param.put(UUID.randomUUID().toString(), queryForm.getWarehouseId()); //仓库条件
		}
		if(!StringUtil.isEmpty(queryForm.getProductName()) && !StringUtil.isEmpty(queryForm.getPackSizeName())) { //产品条件
			sql.append(" \r\n and oida.productid in (select id from product where productname = ? and packsizename= ?) ");param.put(UUID.randomUUID().toString(), queryForm.getProductName());param.put(UUID.randomUUID().toString(), queryForm.getPackSizeName());
		} else if(!StringUtil.isEmpty(queryForm.getProductName()) && StringUtil.isEmpty(queryForm.getPackSizeName())) { //产品条件
			sql.append(" \r\n and oida.productid in (select id from product where productname = ?) ");param.put(UUID.randomUUID().toString(), queryForm.getProductName());
		}
		if(!StringUtil.isEmpty(queryForm.getBeginDate())){
			sql.append(" \r\n and oia.auditdate >=to_date(?,'yyyy-MM-dd hh24:mi:ss') ");param.put(UUID.randomUUID().toString(), queryForm.getBeginDate()); //开始时间条件
		}
		if(!StringUtil.isEmpty(queryForm.getEndDate())){
			sql.append(" \r\n and oia.auditdate < to_date(?,'yyyy-MM-dd hh24:mi:ss') ");param.put(UUID.randomUUID().toString(), queryForm.getEndDate()); //结束时间条件
		}
		sql.append(" \r\n join WAREHOUSE w on w.id = oia.warehouseid and w.USEFLAG = 1");
		sql.append(" \r\n INNER JOIN ORGAN o on oia.organid= o.id and o.organtype = 2 and  organmodel = 1 ");
		sql.append(" \r\n LEFT JOIN F_UNIT f on oida.productid = f.productid and oida.unitid = f.funitid ");
		sql.append(" \r\n LEFT JOIN PRODUCT p on oida.productid = p.id ");
		sql.append(" \r\n LEFT JOIN PRODUCT_STRUCT pstr on p.PSID = pstr.STRUCTCODE   ");
//		sql.append(" \r\n LEFT JOIN COUNTRY_AREA country on o.province=country.id   ");
		sql.append(" \r\n LEFT JOIN (SELECT max(REGIONCODEID) regioncodeid,max(AREAID) areaid  FROM REGION_AREA GROUP BY AREAID) ra on ra.areaid=o.province   ");
		sql.append(" \r\n LEFT JOIN REGION r on r.regioncode=ra.regioncodeid   ");
		if(DbUtil.isDealer(users)) {
			sql.append(" \r\n INNER JOIN RULE_USER_WH rw on oia.warehouseid = rw.warehouse_id ");
			sql.append(" \r\n where rw.user_id = ?");param.put(UUID.randomUUID().toString(), users.getUserid());
		} else {
			sql.append(" \r\n where "+DbUtil.getWhereCondition(users, "o"));
		}
		if(!StringUtil.isEmpty(queryForm.getRegion())){
			sql.append(" \r\n and r.regioncode = ? "); param.put(UUID.randomUUID().toString(), queryForm.getRegion());
			
		}
		sql.append(" \r\n  and p.USEFLAG = 1 ");
		//#end modified by ryan.xi at 20150805
		System.out.println(sql.toString());
		List<Map> list =  new ArrayList<Map>();
		if(request == null && pageSize == 0){
			list =  EntityManager.jdbcquery(sql.toString(), param);
		}else {
			list =  PageQuery.jdbcSqlserverQuery(request,"billNo", sql.toString(), pageSize, param);
		}
		if(list != null && list.size()>0){
			Map<String,String> sapDeliveryNos = new HashMap<String, String>();
			Map<String,FUnit>  funitMap = appFUnit.getAllMap(); //获取所有的单位换算信息
			Map<Integer, String> countUnitMap = appBr.getBaseResourceMap("CountUnit");
			Map<String, String> areaNameMap =  appCountryArea.getAreaNameMap();
			for(Map map : list){
				SalesConsumeDetailReportForm scForm = new SalesConsumeDetailReportForm();
				//将Map中对应的值赋值给实例 
				MapUtil.mapToObject(map, scForm);
				//设置省市区
				scForm.setProvinceId(scForm.getProvince());
				scForm.setCityId(scForm.getCity());
				scForm.setAreasId(scForm.getAreas());
				if(!StringUtil.isEmpty(scForm.getProvince())) {
					scForm.setProvince(areaNameMap.get(scForm.getProvince()));
				}
				if(!StringUtil.isEmpty(scForm.getCity())) {
					scForm.setCity(areaNameMap.get(scForm.getCity()));
				}
				if(!StringUtil.isEmpty(scForm.getAreas())) {
					scForm.setAreas(areaNameMap.get(scForm.getAreas()));
				}
				//报告日期主当前日期
				scForm.setReportDate(new Timestamp(System.currentTimeMillis()));
				//单据日期
				if(scForm.getMakeDate() != null){
					scForm.setMakeDate(new Timestamp(scForm.getMakeDate().getTime()));
				}
				//获取生产日期与过期日期
				PrintJob printJob = appPrintJob.getPrintJobByBatAMc(scForm.getmCode(), scForm.getBatch());
				if(printJob != null) {
					scForm.setProduceDate(printJob.getProductionDate());
					scForm.setExpiryDate(printJob.getExpiryDate());
				}
				//设置件数
				if(checkRate(scForm, funitMap,Constants.DEFAULT_UNIT_ID)){
					//将数量换算为件
					Double consumeCount = changeUnit(scForm.getProductId(),scForm.getUnitId(),scForm.getConsumeQuantity(),Constants.DEFAULT_UNIT_ID,funitMap);
					scForm.setConsumeCount(consumeCount);
					Double scannedCount = changeUnit(scForm.getProductId(),scForm.getUnitId(),scForm.getScanedQuantity(),Constants.DEFAULT_UNIT_ID,funitMap);
					scForm.setScanedCount(scannedCount);
					//单位默认显示为件
					//scForm.setUnitId(Constants.DEFAULT_UNIT_ID);
				}
				//设置计量单位数量
				Product product = appProduct.loadProductById(scForm.getProductId());
				if(product != null) {
					//检查单位是否可以正常转化,如不能则不转化
					if(scForm.getUnitId() != product.getSunit() && checkRate(scForm, funitMap,product.getSunit())){
						//将数量换算为小包装
						Double consumeQuantity = changeUnit(scForm.getProductId(),scForm.getUnitId(),scForm.getConsumeQuantity(),product.getSunit(),funitMap);
						scForm.setConsumeQuantity(consumeQuantity);
						Double scannedQuantity = changeUnit(scForm.getProductId(),scForm.getUnitId(),scForm.getScanedQuantity(),product.getSunit(),funitMap);
						scForm.setScanedQuantity(scannedQuantity);						
						scForm.setUnitId(product.getSunit());
					}
					if(product.getBoxquantity() != null && product.getBoxquantity() != 0d) {
						Double consumeQuantity = ArithDouble.mul(scForm.getConsumeQuantity(), product.getBoxquantity());
						scForm.setConsumeQuantity(consumeQuantity);
						Double scannedQuantity = ArithDouble.mul(scForm.getScanedQuantity(), product.getBoxquantity());
						scForm.setScanedQuantity(scannedQuantity);
						scForm.setUnitId(product.getCountunit());
					}
				}
				
				/*if(StringUtil.isEmpty(queryForm.getCountByUnit())) {
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
				}*/
				scForm.setUnitName(countUnitMap.get(scForm.getUnitId()));
				resultList.add(scForm);
			}
		}
		return resultList;
	}
	public static void main(String[] args) throws Exception {
		Map<String,Object> paraMap = new HashMap<>();
		paraMap.put("region", "107");
		paraMap.put("wname", "pd仓库001");
		paraMap.put("queryFlag", "1");
		paraMap.put("packSizeName", "5升");
		paraMap.put("countByUnit", "true");
		paraMap.put("warehouseId", "14849");
		paraMap.put("organName", "PD经销商20150109");
		paraMap.put("endDate", "2018-05-06");
		paraMap.put("ProductName", "保试达");
		paraMap.put("organId", "10001541");
		paraMap.put("beginDate", "2014-05-01");
		SalesConsumeDetailReportForm scrForm = new SalesConsumeDetailReportForm();
		MapUtil.mapToObject(MapUtil.changeKeyLow(paraMap), scrForm);
		SalesConsumeReportService serv = new SalesConsumeReportService();
		UsersBean users = new UsersBean();
		users.setUserid(10462);
		Object result = serv.queryConsumeDetailReport2(null, 0, scrForm, users);
		System.out.println();
	}
}
