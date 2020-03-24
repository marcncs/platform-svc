package com.winsafe.drp.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;


import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.util.Dateutil;
import com.winsafe.drp.util.JProperties;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.StringUtil;
import com.winsafe.hbm.util.pager2.PageQuery;

public class AppFIFOAlertReport {

	public void addFIFOAlertReport(ArrayList<FIFOAlertReport> reports) throws Exception {
		EntityManager.batchSave(reports);
	}

	public Date getMaxEndDate() throws Exception {
		String sql = "select max(endDate) from FIFOAlertReport";
		return (Date)EntityManager.find(sql);
	}
	
	public List getFIFOAlertReportByTime(Date beginDate, Date endDate) throws Exception {
		Properties sysPro = JProperties.loadProperties("system.properties", JProperties.BY_CLASSLOADER);
		if(beginDate == null) {
			beginDate = getBeginDate(beginDate, sysPro);
		} 
		StringBuffer sql = new StringBuffer();
		sql.append("\r\n select organid,productid,stockbatch,max(stockproductiondate) as stockproductiondate,sum(shipquantity) as shipquantity, shipbatch,max(shipproductiondate) as shipproductiondate,billno,warehouseid, billdate  from (");
		//发货
		sql.append("\r\n select DISTINCT temp_1.organid,temp_1.productid,temp_2.dateAndbatch as stockbatch,temp_2.producedate as stockproductiondate,temp_1.shipquantity, temp_1.batch as shipbatch,temp_1.producedate as shipproductiondate,temp_1.id as billno,temp_1.warehouseid, temp_1.billdate  from ( ");
		sql.append("\r\n select max(sm.outorganid) as organid, sm.id, smd.productid, smi.batch,smi.producedate,sm.outwarehouseid as warehouseid , sum(smi.quantity * f.xquantity)  as shipquantity, max(sm.auditdate) as billdate  from STOCK_ALTER_MOVE sm ");
		sql.append(" \r\n join organ o on o.id = sm.outorganid "); 
		if(sysPro != null &&"1".equals(sysPro.getProperty("genReportOnlyForPD"))){ //是否只统计PD
			sql.append(" \r\n and o.organtype = 2 and o.organmodel = 1"); 
		}
		
		sql.append("\r\n join STOCK_ALTER_MOVE_DETAIL smd on sm.id = smd.samid and sm.ISSHIPMENT = 1 and sm.isblankout = 0 and (sm.istransferred = 0 or sm.istransferred is NULL) ");
		if(beginDate != null) {
			sql.append("\r\n and sm.shipmentdate >=to_date('" + DateUtil.formatDate(beginDate, "yyyy-MM-dd HH:mm:ss") + "','yyyy-MM-dd hh24:mi:ss')");
		}
		if(endDate != null) {
			sql.append("\r\n and sm.shipmentdate <=to_date('" + DateUtil.formatDate(endDate, "yyyy-MM-dd HH:mm:ss") + "','yyyy-MM-dd hh24:mi:ss')");
		}
		
		sql.append("\r\n join STOCK_ALTER_MOVE_IDCODE smi on smd.productid = smi.productid and smd.samid = smi.samid ");
		sql.append("\r\n LEFT JOIN F_UNIT f on smd.productid = f.productid and smd.unitid = f.funitid  ");
		sql.append("\r\n group by sm.id, smd.productid, smi.batch, smi.producedate,sm.outwarehouseid) temp_1");
		sql.append("\r\n  join  (");
		sql.append("\r\n select ps.productid , ps.warehouseid, min(pj2.production_date) as producedate,min(pj2.production_date || '_' || pj2.batch_number) as dateAndbatch from PRODUCT_STOCKPILE_ALL ps ");
		sql.append("\r\n join PRINT_JOB pj2 on pj2.product_id = ps.productid and pj2.batch_number = ps.batch and ps.stockpile > 0");
		sql.append("\r\n group by ps.productid , ps.warehouseid ) temp_2");
		
		sql.append("\r\n on temp_1.productid = temp_2.productid and temp_1.warehouseid = temp_2.warehouseid ");
		sql.append("\r\n where temp_1.producedate > temp_2.producedate ");
		
		sql.append("\r\n UNION ALL");
		//转仓单
		sql.append("\r\n select DISTINCT temp_1.organid,temp_1.productid,temp_2.dateAndbatch as stockbatch,temp_2.producedate as stockproductiondate,temp_1.shipquantity, temp_1.batch as shipbatch,temp_1.producedate as shipproductiondate,temp_1.id as billno,temp_1.warehouseid, temp_1.billdate  from ( ");
		sql.append("\r\n select max(sm.outorganid) as organid, sm.id, smd.productid, smi.batch,smi.producedate,sm.outwarehouseid as warehouseid , sum(smi.quantity * f.xquantity)  as shipquantity, max(sm.auditdate) as billdate  from STOCK_MOVE sm ");
		sql.append(" \r\n join organ o on o.id = sm.outorganid "); 
		if(sysPro != null &&"1".equals(sysPro.getProperty("genReportOnlyForPD"))){ //是否只统计PD
			sql.append(" \r\n and o.organtype = 2 and o.organmodel = 1"); 
		}
		
		sql.append("\r\n join STOCK_MOVE_DETAIL smd on sm.id = smd.smid and sm.ISSHIPMENT = 1 and sm.isblankout = 0 ");
		if(beginDate != null) {
			sql.append("\r\n and sm.shipmentdate >=to_date('" + DateUtil.formatDate(beginDate, "yyyy-MM-dd HH:mm:ss") + "','yyyy-MM-dd hh24:mi:ss')");
		}
		if(endDate != null) {
			sql.append("\r\n and sm.shipmentdate <=to_date('" + DateUtil.formatDate(endDate, "yyyy-MM-dd HH:mm:ss") + "','yyyy-MM-dd hh24:mi:ss')");
		}
		
		sql.append("\r\n join STOCK_MOVE_IDCODE smi on smd.productid = smi.productid and smd.smid = smi.smid ");
		sql.append("\r\n LEFT JOIN F_UNIT f on smd.productid = f.productid and smd.unitid = f.funitid  ");
		sql.append("\r\n group by sm.id, smd.productid, smi.batch, smi.producedate,sm.outwarehouseid) temp_1");
		sql.append("\r\n  join  (");
		sql.append("\r\n select ps.productid , ps.warehouseid, min(pj2.production_date) as producedate,min(pj2.production_date || '_' || pj2.batch_number) as dateAndbatch from PRODUCT_STOCKPILE_ALL ps ");
		sql.append("\r\n join PRINT_JOB pj2 on pj2.product_id = ps.productid and pj2.batch_number = ps.batch  and ps.stockpile > 0");
		sql.append("\r\n group by ps.productid , ps.warehouseid ) temp_2");
		
		sql.append("\r\n on temp_1.productid = temp_2.productid and temp_1.warehouseid = temp_2.warehouseid ");
		sql.append("\r\n where temp_1.producedate > temp_2.producedate ");
		
		
		sql.append("\r\n ) GROUP BY organid,productid,stockbatch, shipbatch,billno,warehouseid,billdate");
		sql.append("\r\n order by organid");
		
		return EntityManager.jdbcquery(sql.toString());
	}

	private Date getBeginDate(Date beginDate, Properties sysPro) {
		try {
			String beginDateStr = sysPro.getProperty("fifoBeginDate");
			if(!StringUtil.isEmpty(beginDateStr)) {
				beginDate = DateUtil.StringToDate(beginDateStr, "yyyy-MM-dd HH:mm:ss");
			} else {
				beginDate = DateUtil.StringToDate("2015-10-31 23:59:59", "yyyy-MM-dd HH:mm:ss");
			}
		} catch (Exception e) {
			beginDate = DateUtil.StringToDate("2015-10-31 23:59:59", "yyyy-MM-dd HH:mm:ss");
		}
		return beginDate;
	}

	public List getFIFOAlertReport(HttpServletRequest request, int pageSize,
			Map paraMap, UsersBean users) throws Exception {
		//对于结束日期增加一天
		if(!StringUtil.isEmpty((String)paraMap.get("endDate"))){
			String endDateStr = (String)paraMap.get("endDate");
			Date endDate = Dateutil.StringToDate(endDateStr);
			endDate = Dateutil.addDay2Date(1, endDate);
			paraMap.put("endDate", Dateutil.formatDate(endDate));
		}
		if(!StringUtil.isEmpty((String)paraMap.get("expiryDate"))) {
			Date expiryDate = Dateutil.getCurrentDate();
			Integer days = Integer.parseInt((String)paraMap.get("expiryDate")); 
			expiryDate = Dateutil.addDay2Date(days, expiryDate);
			paraMap.put("expiryDate", Dateutil.formatDate(expiryDate, "yyyyMMdd"));
		}
//		String hql="select p from FIFOAlertReport as p "+whereSql +" order by p.billNo desc";
		StringBuffer sql = new StringBuffer();
		sql.append("\r\n select ca.areaname as PROVINCE");
		sql.append("\r\n ,far.ORGANID");
		sql.append("\r\n ,o.ORGANNAME");
		sql.append("\r\n ,o.OECODE");
		sql.append("\r\n ,ps.SORTNAME");
		sql.append("\r\n ,far.PRODUCTID");
		sql.append("\r\n ,p.MCODE");
		sql.append("\r\n ,p.PRODUCTNAME");
		sql.append("\r\n ,p.specmode");
		sql.append("\r\n ,p.countunit as unitId");
		sql.append("\r\n ,far.VIEWDATE as viewDateStr");
		sql.append("\r\n ,far.STOCKBATCH");
		sql.append("\r\n ,far.STOCKPILE");
		sql.append("\r\n ,far.STOCKPRODUCTIONDATE");
		sql.append("\r\n ,far.STOCKEXPIRYDATE");
		sql.append("\r\n ,far.SHIPQUANTITY");
		sql.append("\r\n ,far.SHIPBATCH");
		sql.append("\r\n ,far.SHIPPRODUCTIONDATE");
		sql.append("\r\n ,far.SHIPEXPIRYDATE");
		sql.append("\r\n ,far.QUANTITY");
		sql.append("\r\n ,far.BILLNO");
		sql.append("\r\n ,far.BEGINDATE");
		sql.append("\r\n ,far.ENDDATE");
		sql.append("\r\n ,r.SORTNAME as REGIONNAME");
		sql.append("\r\n ,far.WAREHOUSEID");
		sql.append("\r\n ,wa.warehousename from FIFO_ALERT_REPORT far");
		sql.append("\r\n join organ o on o.id = far.organid");
		sql.append("\r\n and far.WAREHOUSEID in (select wv.warehouse_Id from RULE_USER_WH wv where wv.user_Id="+users.getUserid()+")");
		if(!StringUtil.isEmpty((String)paraMap.get("beginDate"))){
			sql.append(" \r\n and far.billdate >=to_date('" + paraMap.get("beginDate") + "','yyyy-MM-dd hh24:mi:ss') "); //开始时间条件
		}
		if(!StringUtil.isEmpty((String)paraMap.get("endDate"))){
			sql.append(" \r\n and far.billdate < to_date('" + paraMap.get("endDate") + "','yyyy-MM-dd hh24:mi:ss') "); //结束时间条件
		}
		if(!StringUtil.isEmpty((String)paraMap.get("organId"))){
			sql.append(" \r\n and far.organid = '" + paraMap.get("organId") + "' "); //机构条件
		}
		if(!StringUtil.isEmpty((String)paraMap.get("warehouseId"))){
			sql.append(" \r\n and far.warehouseId = '" + paraMap.get("warehouseId") + "' "); //仓库条件
		}
		if(!StringUtil.isEmpty((String)paraMap.get("ProductName")) && !StringUtil.isEmpty((String)paraMap.get("packSizeName"))) { //产品条件
			sql.append(" \r\n and far.productId in (select id from product where productname = '"+(String)paraMap.get("ProductName")+"' and specmode= '"+(String)paraMap.get("packSizeName")+"') ");
		} else if(!StringUtil.isEmpty((String)paraMap.get("ProductName")) && StringUtil.isEmpty((String)paraMap.get("packSizeName"))) { //产品条件
			sql.append(" \r\n and far.productId in (select id from product where productname = '"+(String)paraMap.get("ProductName")+"') ");
		}
//		if(!StringUtil.isEmpty((String)paraMap.get("productId"))){
//			sql.append(" \r\n and far.productId = '" + paraMap.get("productId") + "' "); //产品条件
//		}
		if(!StringUtil.isEmpty((String)paraMap.get("batch"))){
			sql.append(" \r\n and (far.STOCKBATCH = '" + ((String)paraMap.get("batch")).trim() + "' or far.SHIPBATCH = '" + ((String)paraMap.get("batch")).trim() + "')"); //批次条件
		}
		if(!StringUtil.isEmpty((String)paraMap.get("expiryDate"))){
			sql.append(" \r\n and far.STOCKEXPIRYDATE <= '" + ((String)paraMap.get("expiryDate")).trim() + "' "); //有效期
		}
		sql.append("\r\n join product p on p.id = far.productid");              
		sql.append("\r\n join PRODUCT_STRUCT ps on ps.structcode = p.psid");
		if(!StringUtil.isEmpty((String)paraMap.get("productSort"))){
			sql.append(" \r\n and p.psid = '" + paraMap.get("productSort") + "' "); //产品类型条件
		}
		sql.append("\r\n join warehouse wa on wa.id = far.warehouseid");        
		if(!StringUtil.isEmpty((String)paraMap.get("region"))){
			sql.append("\r\n join country_area ca on ca.id = o.province");     
			sql.append("\r\n join region_area ra on ra.areaid = ca.id");
			sql.append(" \r\n and ra.regioncodeid = '" + paraMap.get("region") + "' "); //区域
		} else {
			sql.append("\r\n left join country_area ca on ca.id = o.province");     
			sql.append("\r\n left join region_area ra on ra.areaid = ca.id");
		}
		sql.append("\r\n left join region r on r.regioncode = ra.regioncodeid");
		List<Map> list = new ArrayList<Map>();
		if(request == null && pageSize == 0){
			list =  EntityManager.jdbcquery(sql.toString());
		}else {
			list = PageQuery.jdbcSqlserverQuery(request, "ORGANID,productid", sql.toString(), pageSize);
		}
		return list;
	}
	public List getFIFOAlertReportNew(HttpServletRequest request, int pageSize, UsersBean users) throws Exception {
		StringBuffer whereSql = new StringBuffer();
		Map paraMap = new HashMap(request.getParameterMap());
		
		//区域
		if(!StringUtil.isEmpty((String)paraMap.get("region"))){
			whereSql.append(" \r\n and r.regioncode = '").append(((String)paraMap.get("region")).trim()).append("' "); 
		}
		//机构条件
		if(!StringUtil.isEmpty((String)paraMap.get("organId"))){
			whereSql.append(" \r\n and o.id = '").append(((String)paraMap.get("organId")).trim()).append("' "); 
		}
		//仓库条件
		if(!StringUtil.isEmpty((String)paraMap.get("warehouseId"))){
			whereSql.append(" \r\n and far.warehouseid = '").append(((String)paraMap.get("warehouseId")).trim()).append("' "); 
		}
		//产品条件
		if(!StringUtil.isEmpty((String)paraMap.get("ProductName")) && !StringUtil.isEmpty((String)paraMap.get("packSizeName"))) {
			whereSql.append("\r\n and p.productName = '").append(((String)paraMap.get("ProductName")).trim()).append("' and p.specmode= '").append(((String)paraMap.get("packSizeName")).trim()).append("' ");
		} else if(!StringUtil.isEmpty((String)paraMap.get("ProductName")) && StringUtil.isEmpty((String)paraMap.get("packSizeName"))) {
			whereSql.append("\r\n and p.productName = '").append(((String)paraMap.get("ProductName")).trim()).append("' ");
		}
		//批次
		if(!StringUtil.isEmpty((String)paraMap.get("batch"))){
			whereSql.append("\r\n and far.stockbatch = '").append(((String)paraMap.get("batch")).trim()).append("' ");
		}
		//箱码
//		if(!StringUtil.isEmpty((String)paraMap.get("idcode"))){
//			whereSql.append("\r\n and temp.idcode = '").append(((String)paraMap.get("idcode")).trim()).append("' ");
//		}
		//管辖权限
		if(DbUtil.isDealer(users)) {
			whereSql.append("\r\n and far.warehouseId in (select wv.warehouse_Id from RULE_USER_WH wv where wv.user_Id="+users.getUserid()+")");
		}
		
		//开始日期
		if(!StringUtil.isEmpty((String)paraMap.get("beginDate"))){
			whereSql.append("\r\n and far.billdate >=to_date('").append(((String)paraMap.get("beginDate")).trim()).append("','yyyy-MM-dd hh24:mi:ss') "); //开始时间条件
		}
		//对于结束日期增加一天
		if(!StringUtil.isEmpty((String)paraMap.get("endDate"))){
			String endDateStr = (String)paraMap.get("endDate");
			Date endDate = Dateutil.StringToDate(endDateStr);
			endDate = Dateutil.addDay2Date(1, endDate);
			paraMap.put("endDate", Dateutil.formatDate(endDate));
			whereSql.append("\r\n and far.billdate < to_date('").append(((String)paraMap.get("endDate")).trim()).append("','yyyy-MM-dd hh24:mi:ss') "); //开始时间条件
		}
		
		StringBuffer sql = new StringBuffer();
		sql.append("\r\n select r.regioncode, r.sortname as regionname, oProvince.areaname as province, o.id as organid, o.organname, o.oecode, w.id as warehouseid, w.warehousename, p.id as productid, p.mcode, p.countunit, p.specmode, p.packSizeNameEn, p.matericalchdes, p.matericalendes, p.productname, p.productnameen, far.stockbatch, far.stockpile, far.stockproductiondate, far.stockexpirydate, far.billno, far.billdate, far.shipquantity, far.shipbatch, far.shipproductiondate, far.shipexpirydate,to_date(far.shipproductiondate, 'yyyy-mm-dd')-to_date(far.stockproductiondate, 'yyyy-mm-dd') as differentDays ");
		sql.append("\r\n from FIFO_ALERT_REPORT far ");
		sql.append("\r\n join ( ");
		sql.append("\r\n select billno,min(shipproductiondate|| '_' ||shipbatch) as  shipproductiondate from FIFO_ALERT_REPORT GROUP BY billno ");
		sql.append("\r\n 	) far_temp on far.billno = far_temp.billno and far.shipproductiondate|| '_' ||far.shipbatch = far_temp.shipproductiondate  ");
		sql.append("\r\n join organ o on far.organid=o.id  ");
		if(!DbUtil.isDealer(users)) {
			sql.append(" \r\n and "+DbUtil.getWhereCondition(users, "o"));
		}
		sql.append("\r\n join country_area oProvince on oProvince.id=o.province ");
		sql.append("\r\n left join region_area ra on ra.areaid = oProvince.id ");
		sql.append("\r\n left join region r on r.regioncode = ra.regioncodeid ");
		sql.append("\r\n join warehouse w on far.warehouseid=w.id ");
		sql.append("\r\n join product p on far.productid=p.id ");
		sql.append("\r\n where 1=1 and productid not in (select id from product where USEFLAG = 0) and w.id not in (select id from WAREHOUSE where USEFLAG = 0)").append(whereSql);
		
		List<Map> list = new ArrayList<Map>();
		if(pageSize <= 0){
			list =  EntityManager.jdbcquery(sql.toString());
		}else {
			list = PageQuery.jdbcSqlserverQuery(request, "regionname, province, organid, billno", sql.toString(), pageSize);
		}
		return list;
	}
}
