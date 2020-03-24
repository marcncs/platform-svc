package com.winsafe.drp.server;

import java.sql.SQLException;
import java.util.Calendar;

import org.apache.log4j.Logger;  
import org.hibernate.HibernateException;

import com.winsafe.drp.dao.AppBaseResource;
import com.winsafe.drp.dao.BaseResource;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.report.pojo.ReportForm;
import com.winsafe.drp.util.Constants;
import com.winsafe.hbm.entity.HibernateUtil;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.MakeCode;
import com.winsafe.hbm.util.StringUtil;

public class ReportHistoryService extends ReportServices{
	private static Logger logger = Logger.getLogger(ReportHistoryService.class);
	private AppBaseResource appBr = new AppBaseResource();
	
	private void addSalesDetailReportHistory(int year, int month,
			String startDate, String endDate, ReportForm scrForm) throws Exception { 
		StringBuffer sql = new StringBuffer();
		sql.append("INSERT INTO SALES_DETAIL_HISTORY(YEAR,MONTH,BILLNO,OECODE,ORGANID,PRODUCTID,BATCH,SALESQUANTITY,MAKEDATE,WAREHOUSEID,iscomplete) ");
		sql.append("select "+year+","+month+",BILLNO,OECODE,ORGANID,PRODUCTID,BATCH,SALESQUANTITY,MAKEDATE,WAREHOUSEID,iscomplete from (");
		// 工厂给PD
		sql.append(" \r\n select DISTINCT tt.billNo billNo");
//		sql.append(" \r\n ,r.sortname regionName");
//		sql.append(" \r\n ,ino.province provinceId");
		sql.append(" \r\n ,ino.oecode oecode");
//		sql.append(" \r\n ,country.areaname province");
		sql.append(" \r\n ,tt.inoid organId ");
//		sql.append(" \r\n ,ino.organname organName");
		sql.append(" \r\n ,ttd.productid productId  ");
//		sql.append(" \r\n ,pstr.sortname psName");
//		sql.append(" \r\n ,p.productname productName");
//		sql.append(" \r\n ,p.productnameen productNameen ");
//		sql.append(" \r\n ,p.mcode mCode");
//		sql.append(" \r\n ,p.matericalchdes matericalChDes ");
//		sql.append(" \r\n ,p.matericalendes matericalEnDes ");
//		sql.append(" \r\n ,p.sunit unitId ");
//		sql.append(" \r\n ,p.specmode packSizeName");
//		sql.append(" \r\n ,p.packsizenameen packSizeNameEn");
		sql.append(" \r\n ,swb.batch batch ");
		sql.append(" \r\n ,swb.cycleoutquantity salesQuantity"); // 销售数量
		sql.append(" \r\n ,0 consumeQuantity"); //消耗数量
		sql.append(" \r\n ,sam.movedate makeDate ,w.id warehouseid");
		sql.append(" \r\n ,case when sam.iscomplete = 0 or sam.RECEIVEDATE >= to_date('" + endDate + "','yyyy-MM-dd hh24:mi:ss') then 0 else 1 end iscomplete ");
		sql.append(" \r\n from TAKE_TICKET tt INNER JOIN TAKE_TICKET_DETAIL ttd on tt.id = ttd.ttid  and tt.isaudit = 1  and tt.bsort = 1 ");
		//过滤拒签的单据
		sql.append(" \r\n join STOCK_ALTER_MOVE sam on sam.id=tt.billno and sam.isblankout = 0");
		if(!StringUtil.isEmpty(startDate)){
			sql.append(" \r\n and sam.movedate >=to_date('" + startDate + "','yyyy-MM-dd hh24:mi:ss') "); //开始时间条件
		}
		if(!StringUtil.isEmpty(endDate)){
			sql.append(" \r\n and sam.movedate < to_date('" + endDate + "','yyyy-MM-dd hh24:mi:ss') "); //结束时间条件
		}
		sql.append(" \r\n join WAREHOUSE w on w.id = tt.inwarehouseid and w.USEFLAG = 1 ");
		sql.append(" \r\n INNER JOIN STOCK_WASTE_BOOK swb on swb.billcode = tt.id and ttd.productid = swb.productid ");
		sql.append(" \r\n INNER JOIN ORGAN o on tt.oid = o.id and o.organtype = 1  ");
		sql.append(" \r\n INNER JOIN ORGAN ino on tt.inoid = ino.id and ino.ISREPEAL = 0 ");
		if(!StringUtil.isEmpty(scrForm.getOrganId())) {
			sql.append(" and ino.id = '"+scrForm.getOrganId()+"' "); 
		}
		sql.append(" \r\n and ino.organtype = 2 and ino.organmodel = 1 ");//经销商
		sql.append(" \r\n LEFT JOIN F_UNIT f on ttd.productid = f.productid and ttd.unitid = f.funitid ");
		sql.append(" \r\n LEFT JOIN PRODUCT p on ttd.productid = p.id  ");
//		sql.append(" \r\n LEFT JOIN PRODUCT_STRUCT pstr on p.PSID = pstr.STRUCTCODE  ");
		
//		sql.append(" \r\n LEFT JOIN COUNTRY_AREA country on ino.province=country.id  ");
		
//		sql.append(" \r\n LEFT JOIN (SELECT max(REGIONCODEID) regioncodeid,max(AREAID) areaid  FROM REGION_AREA GROUP BY AREAID) ra on ra.areaid=country.id  ");
//		sql.append(" \r\n LEFT JOIN REGION r on r.regioncode=ra.regioncodeid  ");
		
		sql.append(" \r\n where p.USEFLAG = 1 ");
		
		sql.append(" \r\n union all");
		//PD调拨回来的
		sql.append(" \r\n select DISTINCT tt.billNo billNo");
//		sql.append(" \r\n ,r.sortname regionName");
//		sql.append(" \r\n ,ino.province provinceId");
		sql.append(" \r\n ,ino.oecode oecode");
//		sql.append(" \r\n ,country.areaname province");
		sql.append(" \r\n ,tt.inoid organId ");
//		sql.append(" \r\n ,ino.organname organName");
		sql.append(" \r\n ,ttd.productid productId  ");
//		sql.append(" \r\n ,pstr.sortname psName");
//		sql.append(" \r\n ,p.productname productName");
//		sql.append(" \r\n  ,p.productnameen productNameen ");
//		sql.append(" \r\n ,p.mcode mCode");
//		sql.append(" \r\n ,p.matericalchdes matericalChDes ");
//		sql.append(" \r\n ,p.matericalendes matericalEnDes ");
//		sql.append(" \r\n ,p.sunit unitId ");
//		sql.append(" \r\n ,p.specmode packSizeName");
//		sql.append(" \r\n  ,p.packsizenameen packSizeNameEn");
		sql.append(" \r\n ,swb.batch batch ");
		sql.append(" \r\n ,swb.cycleoutquantity salesQuantity"); // 销售数量
		sql.append(" \r\n ,0 consumeQuantity"); //消耗数量
		sql.append(" \r\n ,sm.SHIPMENTDATE makeDate,w.id ");
		sql.append(" \r\n ,case when sm.iscomplete = 0 or sm.RECEIVEDATE >= to_date('" + endDate + "','yyyy-MM-dd hh24:mi:ss') then 0 else 1 end iscomplete ");
		sql.append(" \r\n from TAKE_TICKET tt INNER JOIN TAKE_TICKET_DETAIL ttd on tt.id = ttd.ttid  and tt.isaudit = 1 and tt.bsort = 2 ");
		sql.append(" \r\n join STOCK_MOVE sm on sm.id=tt.billno");
		if(!StringUtil.isEmpty(startDate)){
			sql.append(" \r\n and sm.SHIPMENTDATE >=to_date('" + startDate + "','yyyy-MM-dd hh24:mi:ss') "); //开始时间条件
		}
		if(!StringUtil.isEmpty(endDate)){
			sql.append(" \r\n and sm.SHIPMENTDATE < to_date('" + endDate + "','yyyy-MM-dd hh24:mi:ss') "); //结束时间条件
		}
		sql.append(" \r\n join WAREHOUSE w on w.id = tt.inwarehouseid and w.USEFLAG = 1 ");
		sql.append(" \r\n INNER JOIN STOCK_WASTE_BOOK swb on swb.billcode = tt.id and ttd.productid = swb.productid ");
		sql.append(" \r\n INNER JOIN ORGAN o on tt.oid = o.id and o.organtype = 2 and o.organmodel = 1 ");
		sql.append(" \r\n LEFT JOIN F_UNIT f on ttd.productid = f.productid and ttd.unitid = f.funitid ");
		sql.append(" \r\n LEFT JOIN PRODUCT p on ttd.productid = p.id  ");
//		sql.append(" \r\n LEFT JOIN PRODUCT_STRUCT pstr on p.PSID = pstr.STRUCTCODE  ");
		sql.append(" \r\n LEFT JOIN ORGAN ino on tt.inoid = ino.id and ino.organtype = 2 and ino.organmodel = 1 and ino.ISREPEAL = 0");
		if(!StringUtil.isEmpty(scrForm.getOrganId())) {
			sql.append(" and ino.id = '"+scrForm.getOrganId()+"' "); 
		}
//		sql.append(" \r\n LEFT JOIN COUNTRY_AREA country on ino.province=country.id  ");
//		sql.append(" \r\n LEFT JOIN (SELECT max(REGIONCODEID) regioncodeid,max(AREAID) areaid  FROM REGION_AREA GROUP BY AREAID) ra on ra.areaid=country.id  ");
//		sql.append(" \r\n LEFT JOIN REGION r on r.regioncode=ra.regioncodeid  ");
		sql.append(" \r\n where p.USEFLAG = 1 ");
		sql.append(" \r\n union all");
		//调拨给其他PD的
		sql.append(" \r\n select DISTINCT tt.billNo billNo");
//		sql.append(" \r\n ,r.sortname regionName");
//		sql.append(" \r\n ,o.province provinceId");
		sql.append(" \r\n ,o.oecode oecode");
//		sql.append(" \r\n ,country.areaname province");
		sql.append(" \r\n ,tt.oid organId ");
//		sql.append(" \r\n ,o.organname organName");
		sql.append(" \r\n ,ttd.productid productId  ");
//		sql.append(" \r\n ,pstr.sortname psName");
//		sql.append(" \r\n ,p.productname productName");
//		sql.append(" \r\n ,p.productnameen productNameen");
//		sql.append(" \r\n ,p.mcode mCode");
//		sql.append(" \r\n ,p.matericalchdes matericalChDes ");
//		sql.append(" \r\n ,p.matericalendes matericalEnDes ");
//		sql.append(" \r\n ,p.sunit unitId ");
//		sql.append(" \r\n ,p.specmode packSizeName");
//		sql.append(" \r\n ,p.packsizenameen packSizeNameEn ");
		sql.append(" \r\n ,swb.batch batch ");
		sql.append(" \r\n ,-swb.cycleoutquantity salesQuantity"); // 销售数量
		sql.append(" \r\n ,0 consumeQuantity"); //消耗数量
		sql.append(" \r\n ,sm.SHIPMENTDATE makeDate,w.id ");
		sql.append(" \r\n ,1 ");
		sql.append(" \r\n from TAKE_TICKET tt INNER JOIN TAKE_TICKET_DETAIL ttd on tt.id = ttd.ttid  and tt.isaudit = 1 and tt.bsort = 2 ");
		sql.append(" \r\n join STOCK_MOVE sm on sm.id=tt.billno");
		if(!StringUtil.isEmpty(startDate)){
			sql.append(" \r\n and sm.SHIPMENTDATE >=to_date('" + startDate + "','yyyy-MM-dd hh24:mi:ss') "); //开始时间条件
		}
		if(!StringUtil.isEmpty(endDate)){
			sql.append(" \r\n and sm.SHIPMENTDATE < to_date('" + endDate + "','yyyy-MM-dd hh24:mi:ss') "); //结束时间条件
		}
		sql.append(" \r\n join WAREHOUSE w on w.id = tt.warehouseid and w.USEFLAG = 1 ");
		sql.append(" \r\n INNER JOIN STOCK_WASTE_BOOK swb on swb.billcode = tt.id and ttd.productid = swb.productid ");
		sql.append(" \r\n INNER JOIN ORGAN o on tt.oid = o.id and o.organtype = 2 and o.organmodel = 1 and o.ISREPEAL = 0");
		if(!StringUtil.isEmpty(scrForm.getOrganId())) {
			sql.append(" and o.id = '"+scrForm.getOrganId()+"' "); 
		}
		sql.append(" \r\n LEFT JOIN F_UNIT f on ttd.productid = f.productid and ttd.unitid = f.funitid ");
		sql.append(" \r\n LEFT JOIN PRODUCT p on ttd.productid = p.id  ");
//		sql.append(" \r\n LEFT JOIN PRODUCT_STRUCT pstr on p.PSID = pstr.STRUCTCODE  ");
		sql.append(" \r\n LEFT JOIN ORGAN ino on tt.inoid = ino.id and ino.organtype = 2 and ino.organmodel = 1");
//		sql.append(" \r\n LEFT JOIN COUNTRY_AREA country on o.province=country.id  ");
//		sql.append(" \r\n LEFT JOIN (SELECT max(REGIONCODEID) regioncodeid,max(AREAID) areaid  FROM REGION_AREA GROUP BY AREAID) ra on ra.areaid=country.id  ");
//		sql.append(" \r\n LEFT JOIN REGION r on r.regioncode=ra.regioncodeid  ");
		
		sql.append(" \r\n where p.USEFLAG = 1 ");
		//#start modified by ryan.xi at 20150714
		//分包大包减销售
		sql.append(" \r\n union all");
		sql.append(" \r\n select DISTINCT tt.id billNo");
//		sql.append(" \r\n ,r.sortname regionName");
//		sql.append(" \r\n ,o.province provinceId");
		sql.append(" \r\n ,o.oecode oecode");
//		sql.append(" \r\n ,country.areaname province");
		sql.append(" \r\n ,o.id organId ");
//		sql.append(" \r\n ,o.organname organName");
		sql.append(" \r\n ,ttd.OUTPRODUCTID productId  ");
//		sql.append(" \r\n ,pstr.sortname psName");
//		sql.append(" \r\n ,p.productname productName");
//		sql.append(" \r\n ,p.productnameen productNameen");
//		sql.append(" \r\n ,p.mcode mCode");
//		sql.append(" \r\n ,p.matericalchdes matericalChDes ");
//		sql.append(" \r\n ,p.matericalendes matericalEnDes ");
//		sql.append(" \r\n ,p.sunit unitId ");
//		sql.append(" \r\n ,p.specmode packSizeName");
//		sql.append(" \r\n ,p.packsizenameen packSizeNameEn ");
		sql.append(" \r\n ,ttd.outbatch batch ");
		sql.append(" \r\n ,-(ttd.outquantity-ttd.wastage) salesQuantity"); // 销售数量
		sql.append(" \r\n ,0 consumeQuantity"); //消耗数量
		sql.append(" \r\n ,tt.auditdate makeDate,w.id ");
		sql.append(" \r\n ,1 ");
		sql.append(" \r\n from PACK_SEPARATE tt INNER JOIN PACK_SEPARATE_DETAIL ttd on tt.id = ttd.psid  and tt.isaudit = 1 ");
		if(!StringUtil.isEmpty(startDate)){
			sql.append(" \r\n and tt.auditdate >=to_date('" + startDate + "','yyyy-MM-dd hh24:mi:ss') "); //开始时间条件
		}
		if(!StringUtil.isEmpty(endDate)){
			sql.append(" \r\n and tt.auditdate < to_date('" + endDate + "','yyyy-MM-dd hh24:mi:ss') "); //结束时间条件
		}
		sql.append(" \r\n join WAREHOUSE w on w.id = tt.warehouseid and w.USEFLAG = 1 ");
		sql.append(" \r\n INNER JOIN ORGAN o on tt.ORGANID = o.id and o.organtype = 2 and o.organmodel = 1 and o.ISREPEAL = 0");
		if(!StringUtil.isEmpty(scrForm.getOrganId())) {
			sql.append(" and o.id = '"+scrForm.getOrganId()+"' "); 
		}
		sql.append(" \r\n LEFT JOIN PRODUCT p on ttd.OUTPRODUCTID = p.id  ");
//		sql.append(" \r\n LEFT JOIN PRODUCT_STRUCT pstr on p.PSID = pstr.STRUCTCODE  ");
//		sql.append(" \r\n LEFT JOIN COUNTRY_AREA country on o.province=country.id  ");
//		sql.append(" \r\n LEFT JOIN (SELECT max(REGIONCODEID) regioncodeid,max(AREAID) areaid  FROM REGION_AREA GROUP BY AREAID) ra on ra.areaid=country.id  ");
//		sql.append(" \r\n LEFT JOIN REGION r on r.regioncode=ra.regioncodeid  ");
		sql.append(" \r\n where p.USEFLAG = 1 ");
		//分包小包加销售
		sql.append(" \r\n union all");
		sql.append(" \r\n select DISTINCT tt.id billNo");
//		sql.append(" \r\n ,r.sortname regionName");
//		sql.append(" \r\n ,o.province provinceId");
		sql.append(" \r\n ,o.oecode oecode");
//		sql.append(" \r\n ,country.areaname province");
		sql.append(" \r\n ,o.id organId ");
//		sql.append(" \r\n ,o.organname organName");
		sql.append(" \r\n ,ttd.INPRODUCTID productId  ");
//		sql.append(" \r\n ,pstr.sortname psName");
//		sql.append(" \r\n ,p.productname productName");
//		sql.append(" \r\n ,p.productnameen productNameen");
//		sql.append(" \r\n ,p.mcode mCode");
//		sql.append(" \r\n ,p.matericalchdes matericalChDes ");
//		sql.append(" \r\n ,p.matericalendes matericalEnDes ");
//		sql.append(" \r\n ,p.sunit unitId ");
//		sql.append(" \r\n ,p.specmode packSizeName");
//		sql.append(" \r\n ,p.packsizenameen packSizeNameEn ");
		sql.append(" \r\n ,ttd.inbatch batch ");
		sql.append(" \r\n ,ttd.inquantity salesQuantity"); // 销售数量
		sql.append(" \r\n ,0 consumeQuantity"); //消耗数量
		sql.append(" \r\n ,tt.auditdate makeDate,w.id ");
		sql.append(" \r\n ,1 ");
		sql.append(" \r\n from PACK_SEPARATE tt INNER JOIN PACK_SEPARATE_DETAIL ttd on tt.id = ttd.psid  and tt.isaudit = 1 ");
		if(!StringUtil.isEmpty(startDate)){
			sql.append(" \r\n and tt.auditdate >=to_date('" + startDate + "','yyyy-MM-dd hh24:mi:ss') "); //开始时间条件
		}
		if(!StringUtil.isEmpty(endDate)){
			sql.append(" \r\n and tt.auditdate < to_date('" + endDate + "','yyyy-MM-dd hh24:mi:ss') "); //结束时间条件
		}
		sql.append(" \r\n join WAREHOUSE w on w.id = tt.warehouseid and w.USEFLAG = 1 ");
		sql.append(" \r\n INNER JOIN ORGAN o on tt.ORGANID = o.id and o.organtype = 2 and o.organmodel = 1 and o.ISREPEAL = 0");
		if(!StringUtil.isEmpty(scrForm.getOrganId())) {
			sql.append(" and o.id = '"+scrForm.getOrganId()+"' "); 
		}
		sql.append(" \r\n LEFT JOIN PRODUCT p on ttd.INPRODUCTID = p.id  ");
//		sql.append(" \r\n LEFT JOIN PRODUCT_STRUCT pstr on p.PSID = pstr.STRUCTCODE  ");
//		sql.append(" \r\n LEFT JOIN COUNTRY_AREA country on o.province=country.id  ");
//		sql.append(" \r\n LEFT JOIN (SELECT max(REGIONCODEID) regioncodeid,max(AREAID) areaid  FROM REGION_AREA GROUP BY AREAID) ra on ra.areaid=country.id  ");
//		sql.append(" \r\n LEFT JOIN REGION r on r.regioncode=ra.regioncodeid  ");
		sql.append(" \r\n where p.USEFLAG = 1 ");
		//工厂退回--减销售
		sql.append(" \r\n union all");
		
		sql.append(" \r\n select DISTINCT tt.billNo billNo");
//		sql.append(" \r\n ,r.sortname regionName");
//		sql.append(" \r\n ,o.province provinceId");
		sql.append(" \r\n ,o.oecode oecode");
//		sql.append(" \r\n ,country.areaname province");
		sql.append(" \r\n ,tt.oid organId ");
//		sql.append(" \r\n ,o.organname organName");
		sql.append(" \r\n ,ttd.productid productId  ");
//		sql.append(" \r\n ,pstr.sortname psName");
//		sql.append(" \r\n ,p.productname productName");
//		sql.append(" \r\n ,p.productnameen productNameen");
//		sql.append(" \r\n ,p.mcode mCode");
//		sql.append(" \r\n ,p.matericalchdes matericalChDes ");
//		sql.append(" \r\n ,p.matericalendes matericalEnDes ");
//		sql.append(" \r\n ,p.sunit unitId ");
//		sql.append(" \r\n ,p.specmode packSizeName");
//		sql.append(" \r\n ,p.packsizenameen packSizeNameEn ");
		sql.append(" \r\n ,swb.batch batch ");
		sql.append(" \r\n ,-swb.cycleoutquantity salesQuantity"); // 销售数量
		sql.append(" \r\n ,0 consumeQuantity"); //消耗数量
		sql.append(" \r\n ,tt.auditdate makeDate,w.id ");
		sql.append(" \r\n ,1 ");
		sql.append(" \r\n from TAKE_TICKET tt INNER JOIN TAKE_TICKET_DETAIL ttd on tt.id = ttd.ttid  and tt.isaudit = 1  and tt.bsort = 7 and tt.billno like 'PW%' ");
		if(!StringUtil.isEmpty(startDate)){
			sql.append(" \r\n and tt.auditdate >=to_date('" + startDate + "','yyyy-MM-dd hh24:mi:ss') "); //开始时间条件
		}
		if(!StringUtil.isEmpty(endDate)){
			sql.append(" \r\n and tt.auditdate < to_date('" + endDate + "','yyyy-MM-dd hh24:mi:ss') "); //结束时间条件
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
		if(!StringUtil.isEmpty(scrForm.getOrganId())) {
			sql.append(" and o.id = '"+scrForm.getOrganId()+"' "); 
		}
		sql.append(" \r\n LEFT JOIN F_UNIT f on ttd.productid = f.productid and ttd.unitid = f.funitid ");
		sql.append(" \r\n LEFT JOIN PRODUCT p on ttd.productid = p.id  ");
//		sql.append(" \r\n LEFT JOIN PRODUCT_STRUCT pstr on p.PSID = pstr.STRUCTCODE  ");
//		sql.append(" \r\n LEFT JOIN COUNTRY_AREA country on o.province=country.id  ");
//		sql.append(" \r\n LEFT JOIN (SELECT max(REGIONCODEID) regioncodeid,max(AREAID) areaid  FROM REGION_AREA GROUP BY AREAID) ra on ra.areaid=country.id  ");
//		sql.append(" \r\n LEFT JOIN REGION r on r.regioncode=ra.regioncodeid  ");
		sql.append(" \r\n where p.USEFLAG = 1 ");
		//#start modified by ryan.xi at 20150805
		//采购入库-加销售-20150805
		sql.append(" \r\n union all");
		
		sql.append(" \r\n select DISTINCT oia.id billNo ");
//		sql.append(" \r\n ,r.sortname regionName  ");
//		sql.append(" \r\n ,o.province provinceId  ");
		sql.append(" \r\n ,o.oecode oecode ");
//		sql.append(" \r\n ,country.areaname province  ");
		sql.append(" \r\n ,o.id  organId");
//		sql.append(" \r\n ,o.organname organName  ");
		sql.append(" \r\n ,oida.productid productId ");
//		sql.append(" \r\n ,pstr.sortname psName ");
//		sql.append(" \r\n ,p.productname productName ");
//		sql.append(" \r\n  ,p.productnameen productNameen  ");
//		sql.append(" \r\n ,p.mcode mCode ");
//		sql.append(" \r\n ,p.matericalchdes matericalChDes  ");
//		sql.append(" \r\n ,p.matericalendes matericalEnDes  ");
//		sql.append(" \r\n ,p.sunit unitId  ");
//		sql.append(" \r\n ,p.packsizename packSizeName ");
//		sql.append(" \r\n  ,p.packsizenameen packSizeNameEn  ");
		sql.append(" \r\n ,oida.batch batch  ");
		sql.append(" \r\n ,(oida.quantity * f.xquantity) salesQuantity  ");
		sql.append(" \r\n ,0 consumeQuantity  ");
		sql.append(" \r\n ,oia.AUDITDATE makeDate,w.id  ");
		sql.append(" \r\n ,1 ");
		sql.append(" \r\n from OTHER_INCOME_ALL oia INNER JOIN OTHER_INCOME_DETAIL_ALL oida on oia.id = oida.oiid and oia.isaudit = 1 and oia.incomesort = 6");
		if(!StringUtil.isEmpty(startDate)){
			sql.append(" \r\n and oia.auditdate >=to_date('" + startDate + "','yyyy-MM-dd hh24:mi:ss') "); //开始时间条件
		}
		if(!StringUtil.isEmpty(endDate)){
			sql.append(" \r\n and oia.auditdate < to_date('" + endDate + "','yyyy-MM-dd hh24:mi:ss') "); //结束时间条件
		}
		sql.append(" \r\n join WAREHOUSE w on w.id = oia.warehouseid and w.USEFLAG = 1 ");
		sql.append(" \r\n INNER JOIN ORGAN o on oia.organid= o.id and o.organtype = 2 and  organmodel = 1 and o.ISREPEAL = 0");
		if(!StringUtil.isEmpty(scrForm.getOrganId())) {
			sql.append(" and o.id = '"+scrForm.getOrganId()+"' "); 
		}
		sql.append(" \r\n LEFT JOIN F_UNIT f on oida.productid = f.productid and oida.unitid = f.funitid ");
		sql.append(" \r\n LEFT JOIN PRODUCT p on oida.productid = p.id ");
//		sql.append(" \r\n LEFT JOIN PRODUCT_STRUCT pstr on p.PSID = pstr.STRUCTCODE   ");
//		sql.append(" \r\n LEFT JOIN COUNTRY_AREA country on o.province=country.id   ");
//		sql.append(" \r\n LEFT JOIN (SELECT max(REGIONCODEID) regioncodeid,max(AREAID) areaid  FROM REGION_AREA GROUP BY AREAID) ra on ra.areaid=country.id   ");
//		sql.append(" \r\n LEFT JOIN REGION r on r.regioncode=ra.regioncodeid   ");
		sql.append(" \r\n where p.USEFLAG = 1 ");
		sql.append(" \r\n ) temp ");
		//#end modified by ryan.xi at 20150805
		System.out.println(sql.toString());
		EntityManager.executeUpdate(sql.toString());
		
	}
	
	// 根据条件查询
	public void genSalesDetailReportHistory() throws Exception {
		try {
			//获取当前月份 
			Calendar thisMonth = getThisMonth();
			//检查是否已统计过
			BaseResource br = appBr.getBaseResourceValue("SalesDetailDate", 1);
			Calendar lastDate = Calendar.getInstance();
			if(br == null) {
				//若还未统计过,从2015-01开始统计
				lastDate = getInitDate();
			} else {
				//从上一次的统计时间
				lastDate.setTime(DateUtil.StringToDate(br.getTagsubvalue()));
			}
			while(lastDate.before(thisMonth)) {
				String startDate = DateUtil.formatDate(lastDate.getTime());
				int year=lastDate.get(Calendar.YEAR);
				int month=lastDate.get(Calendar.MONTH) + 1;
				lastDate.add(Calendar.MONTH, 1);
				String endDate = DateUtil.formatDate(lastDate.getTime());
				addSalesDetailReportHistory(year, month, startDate, endDate, new ReportForm());
			}
			if(br == null) {
				addBaseResource("SalesDetailDate", DateUtil.formatDate(thisMonth.getTime()));
			} else {
				br.setTagsubvalue(DateUtil.formatDate(thisMonth.getTime()));
				appBr.updBaseResource(br);
			}
			HibernateUtil.commitTransaction();
		} catch (Exception e) {
			logger.error("生成销售明细历史数据失败", e);
			HibernateUtil.rollbackTransaction();
			throw e;
		}
	}

	// 根据条件查询
	public void genConsumeDetailReportHistory() throws Exception {
		try {
			//获取当前月份
			Calendar thisMonth = getThisMonth();
			//检查是否已统计过
			BaseResource br = appBr.getBaseResourceValue("ConsumeDetailDate", 1);
			Calendar lastDate = Calendar.getInstance();
			//若是第一次统计,统计到目前为止所有的数据
			if(br == null) {
				lastDate = getInitDate();
			} else {
				lastDate = Calendar.getInstance();
				lastDate.setTime(DateUtil.StringToDate(br.getTagsubvalue()));
			}
			while(lastDate.before(thisMonth)) {
				String startDate = DateUtil.formatDate(lastDate.getTime());
				int year=lastDate.get(Calendar.YEAR);
				int month=lastDate.get(Calendar.MONTH) + 1;
				lastDate.add(Calendar.MONTH, 1);
				String endDate = DateUtil.formatDate(lastDate.getTime());
				addConsumeDetailReportHistory(year, month, startDate, endDate, new ReportForm());
			}
			if(br == null) {
				addBaseResource("ConsumeDetailDate", DateUtil.formatDate(thisMonth.getTime()));
			} else {
				br.setTagsubvalue(DateUtil.formatDate(thisMonth.getTime()));
				appBr.updBaseResource(br);
			}
			HibernateUtil.commitTransaction();
		} catch (Exception e) {
			logger.error("生成消耗明细历史数据失败", e);
			HibernateUtil.rollbackTransaction();
			throw e;
		}
	}
	
	private Calendar getThisMonth() {
		//获取当前月份
		Calendar thisMonth = Calendar.getInstance();
		thisMonth.set(Calendar.DAY_OF_MONTH, 1);
		thisMonth.set(Calendar.HOUR_OF_DAY, 0);
		thisMonth.set(Calendar.MINUTE, 0);
		thisMonth.set(Calendar.SECOND, 0);
		thisMonth.set(Calendar.MILLISECOND, 0);
		return thisMonth;
	}

	public void genInventoryDetailReportHistory() throws Exception { 
		try {
			//获取当前月份
			Calendar thisMonth = getThisMonth();
			//检查是否已统计过
			BaseResource br = appBr.getBaseResourceValue("InventoryDetailDate", 1);
			//若是第一次统计,先统计
			if(br == null) {
				//2017-04之前的库存按老的方式统计好每月的历史库存
				Calendar oldDay = getOldDay();
				Calendar initDate = getInitDate();
				while(initDate.before(thisMonth)) {
//					String startDate = DateUtil.formatDate(initDate.getTime());
					int year=initDate.get(Calendar.YEAR);
					int month=initDate.get(Calendar.MONTH) + 1;
					if(initDate.before(oldDay)) {
						initDate.add(Calendar.MONTH, 1);
						String endDate = DateUtil.formatDate(initDate.getTime());
						addInventoryDetailReportHistory(year, month, endDate, new ReportForm());
					} else {
						initDate.add(Calendar.MONTH, -1);
						int iYear=initDate.get(Calendar.YEAR);
						int iMonth=initDate.get(Calendar.MONTH) + 1;
						initDate.add(Calendar.MONTH, 2);
						addInventoryDetailReportHistoryBySalesAndConsume(year, month, iYear,iMonth,new ReportForm());
					}
				}
			} else {
				Calendar lastDate = Calendar.getInstance();
				lastDate.setTime(DateUtil.StringToDate(br.getTagsubvalue()));
				while(lastDate.before(thisMonth)) {
//					String startDate = DateUtil.formatDate(lastDate.getTime());
					int year=lastDate.get(Calendar.YEAR);
					int month=lastDate.get(Calendar.MONTH) + 1;
					lastDate.add(Calendar.MONTH, -1);
					int iYear=lastDate.get(Calendar.YEAR);
					int iMonth=lastDate.get(Calendar.MONTH) + 1;
					lastDate.add(Calendar.MONTH, 2);
					addInventoryDetailReportHistoryBySalesAndConsume(year, month, iYear,iMonth, new ReportForm());
				}
			}
			if(br == null) {
				addBaseResource("InventoryDetailDate", DateUtil.formatDate(thisMonth.getTime()));
			} else {
				br.setTagsubvalue(DateUtil.formatDate(thisMonth.getTime()));
				appBr.updBaseResource(br);
			}
			HibernateUtil.commitTransaction();
		} catch (Exception e) {
			logger.error("生成库存历史数据失败", e);
			HibernateUtil.rollbackTransaction();
			throw e;
		}
	} 

	private void addInventoryDetailReportHistoryBySalesAndConsume(int year,
			int month, int iYear, int iMonth, ReportForm scrForm) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("INSERT INTO INVENTORY_DETAIL_HISTORY(YEAR,MONTH,ORGANID,OECODE,PRODUCTID,STOCKPILE,STOCKPILEINTRANSIT,STOCKPILETOSHIP,WAREHOUSEID,BATCH) ");
		sql.append("select "+year+","+month+",ORGANID,max(OECODE),PRODUCTID,sum(STOCKPILE),sum(STOCKPILEINTRANSIT),sum(STOCKPILETOSHIP),WAREHOUSEID,BATCH from (");
		sql.append("select ORGANID,OECODE,PRODUCTID,STOCKPILE,STOCKPILEINTRANSIT,STOCKPILETOSHIP,WAREHOUSEID,BATCH FROM INVENTORY_DETAIL_HISTORY ");
		sql.append("where year="+iYear+" and month="+iMonth+" ");
		if(!StringUtil.isEmpty(scrForm.getOrganId())) {
			sql.append("and organId ='"+scrForm.getOrganId()+"' ");
		}
		sql.append("UNION ALL ");
		sql.append("select ORGANID,OECODE,PRODUCTID,case when iscomplete <> 0 then SALESQUANTITY else 0 end,case when iscomplete = 0 then SALESQUANTITY else 0 end,0,WAREHOUSEID,BATCH from SALES_DETAIL_HISTORY ");
		sql.append("where year="+year+" and month="+month+" ");
		if(!StringUtil.isEmpty(scrForm.getOrganId())) {
			sql.append("and organId ='"+scrForm.getOrganId()+"' ");
		}
		sql.append("UNION ALL ");
		sql.append("select OUTORGANID,OECODE,PRODUCTID,-CONSUMEQUANTITY,0,0,OUTWAREHOUSEID,BATCH from CONSUME_DETAIL_HISTORY ");
		sql.append("where year="+year+" and month="+month);
		if(!StringUtil.isEmpty(scrForm.getOrganId())) {
			sql.append("and OUTORGANID ='"+scrForm.getOrganId()+"' ");
		}
		sql.append(") temp ");
		sql.append("GROUP BY ORGANID,PRODUCTID,BATCH,WAREHOUSEID");
		System.out.println(sql.toString());
		EntityManager.executeUpdate(sql.toString());
	}

	private void addBaseResource(String tagName, String tagValue) throws Exception {
		BaseResource br = new BaseResource();
	    Long brid = Long.valueOf(MakeCode.getExcIDByRandomTableName("base_resource",0,""));
	    br.setId(brid);
	    br.setTagname(tagName);
	    br.setTagsubkey(1);
	    br.setTagsubvalue(tagValue);
	    br.setIsuse(1);
	    appBr.addBaseResource(br);
	}

	/**
	 * 2015-01-01作为历史报表数据的初始日期
	 * @return
	 */
	private Calendar getInitDate() {
		Calendar initDate = Calendar.getInstance();
		initDate.set(Calendar.YEAR, 2015);
		initDate.set(Calendar.MONTH, 0);
		initDate.set(Calendar.DAY_OF_MONTH, 1);
		initDate.set(Calendar.HOUR_OF_DAY, 0);
		initDate.set(Calendar.MINUTE, 0);
		initDate.set(Calendar.SECOND, 0);
		initDate.set(Calendar.MILLISECOND, 0);
		return initDate;
	}

	private void addConsumeDetailReportHistory(int year, int month, String startDate, String endDate, ReportForm scrForm) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("INSERT INTO CONSUME_DETAIL_HISTORY(YEAR,MONTH,BILLNO,OECODE,ORGANID,PRODUCTID,BATCH,CONSUMEQUANTITY,MAKEDATE,OUTORGANID,OUTWAREHOUSEID) ");
		sql.append("select "+year+","+month+",BILLNO,OECODE,ORGANID,PRODUCTID,BATCH,CONSUMEQUANTITY,MAKEDATE,OUTORGANID,OUTWAREHOUSEID from (");
		//消耗数量
		sql.append(" \r\n --正常出库消耗 ");
		sql.append(" \r\n select DISTINCT tt.billNo billNo ");
//		sql.append(" \r\n ,r.sortname regionName ");
//		sql.append(" \r\n ,ino.province provinceId ");
		sql.append(" \r\n ,ino.oecode oecode ");
//		sql.append(" \r\n ,country.areaname province ");
		sql.append(" \r\n ,tt.inoid  organId ");
//		sql.append(" \r\n ,ino.organname organName ");
		sql.append(" \r\n ,ttd.productid productId  ");
//		sql.append(" \r\n ,pstr.sortname psName ");
//		sql.append(" \r\n ,p.productname productName ");
//		sql.append(" \r\n ,p.productnameen productNameen ");
//		sql.append(" \r\n ,p.mcode mCode ");
//		sql.append(" \r\n ,p.matericalchdes matericalChDes ");
//		sql.append(" \r\n ,p.matericalendes matericalEnDes ");
//		sql.append(" \r\n ,p.sunit unitId ");
//		sql.append(" \r\n ,p.specmode packSizeName");
//		sql.append(" \r\n ,p.packsizenameen packSizeNameEn");
		sql.append(" \r\n ,swb.batch batch ");
		sql.append(" \r\n ,0 salesQuantity ");  // 销售数量
		sql.append(" \r\n ,swb.cycleoutquantity  consumeQuantity"); //消耗数量
		sql.append(" \r\n ,TT.AUDITDATE makeDate ");
		sql.append(" \r\n ,o.id outOrganId,w.id OUTWAREHOUSEID ");
		sql.append(" \r\n from TAKE_TICKET tt INNER JOIN TAKE_TICKET_DETAIL ttd on tt.id = ttd.ttid  and tt.isaudit = 1  and tt.bsort = 1 and tt.oid <> tt.inoid ");
		if(!StringUtil.isEmpty(startDate)) {
			sql.append(" \r\n and tt.auditdate >=to_date('" + startDate + "','yyyy-MM-dd hh24:mi:ss') "); //开始时间条件
		}
		if(!StringUtil.isEmpty(endDate)) {
			sql.append(" \r\n and tt.auditdate < to_date('" + endDate + "','yyyy-MM-dd hh24:mi:ss') "); //结束时间条件
		}
		
		sql.append(" \r\n join WAREHOUSE w on w.id = tt.warehouseid and w.USEFLAG = 1");
		
		sql.append(" \r\n INNER JOIN STOCK_WASTE_BOOK swb on swb.billcode = tt.id and ttd.productid = swb.productid ");
		sql.append(" \r\n INNER JOIN ORGAN o on tt.oid = o.id and o.organtype = 2 and  organmodel = 1 ");
		if(!StringUtil.isEmpty(scrForm.getOrganId())) {
			sql.append(" and o.id = '"+scrForm.getOrganId()+"' "); 
		}
		sql.append(" \r\n INNER JOIN ORGAN ino on tt.inoid = ino.id");
		sql.append(" \r\n LEFT JOIN F_UNIT f on ttd.productid = f.productid and ttd.unitid = f.funitid ");
		sql.append(" \r\n LEFT JOIN PRODUCT p on ttd.productid = p.id  ");
//		sql.append(" \r\n LEFT JOIN PRODUCT_STRUCT pstr on p.PSID = pstr.STRUCTCODE  ");
//		sql.append(" \r\n LEFT JOIN COUNTRY_AREA country on ino.province=country.id  ");
//		sql.append(" \r\n LEFT JOIN (SELECT max(REGIONCODEID) regioncodeid,max(AREAID) areaid  FROM REGION_AREA GROUP BY AREAID) ra on ra.areaid=country.id  ");
//		sql.append(" \r\n LEFT JOIN REGION r on r.regioncode=ra.regioncodeid  ");
		sql.append(" \r\n where p.USEFLAG = 1 ");
		sql.append(" \r\n union all");
		sql.append(" \r\n --工厂给PD收发差异记到消耗 ");
		//工厂给PD收发差异记到消耗
		sql.append(" \r\n select DISTINCT tt.billNo billNo ");
//		sql.append(" \r\n ,r.sortname regionName  ");
//		sql.append(" \r\n ,ino.province provinceId  ");
		sql.append(" \r\n ,ino.oecode oecode ");
//		sql.append(" \r\n ,country.areaname province  ");
		sql.append(" \r\n ,ino.id  organId");
//		sql.append(" \r\n ,ino.organname organName  ");
		sql.append(" \r\n ,ttd.productid productId ");
//		sql.append(" \r\n ,pstr.sortname psName ");
//		sql.append(" \r\n ,p.productname productName ");
//		sql.append(" \r\n  ,p.productnameen productNameen  ");
//		sql.append(" \r\n ,p.mcode mCode ");
//		sql.append(" \r\n ,p.matericalchdes matericalChDes  ");
//		sql.append(" \r\n ,p.matericalendes matericalEnDes  ");
//		sql.append(" \r\n ,p.sunit unitId  ");
//		sql.append(" \r\n ,p.specmode packSizeName ");
//		sql.append(" \r\n  ,p.packsizenameen packSizeNameEn  ");
		sql.append(" \r\n ,'"+Constants.NO_BATCH+"' batch  ");
		sql.append(" \r\n ,0 salesQuantity  ");
		sql.append(" \r\n ,((samd.quantity-samd.receivequantity) * f.xquantity)  consumeQuantity ");
		sql.append(" \r\n ,sam.movedate makeDate  ");
		sql.append(" \r\n ,ino.id outOrganId ,w.id OUTWAREHOUSEID ");
		sql.append(" \r\n from TAKE_TICKET tt INNER JOIN TAKE_TICKET_DETAIL ttd on tt.id = ttd.ttid  and tt.isaudit = 1  and tt.bsort = 1 ");
		sql.append(" \r\n join STOCK_ALTER_MOVE sam on sam.id=tt.billno and sam.iscomplete =1");
		if(!StringUtil.isEmpty(startDate)){
			sql.append(" \r\n and sam.movedate >=to_date('" + startDate + "','yyyy-MM-dd hh24:mi:ss') "); //开始时间条件
		}
		if(!StringUtil.isEmpty(endDate)){
			sql.append(" \r\n and sam.movedate < to_date('" + endDate + "','yyyy-MM-dd hh24:mi:ss') "); //结束时间条件
		}
		sql.append(" \r\n join WAREHOUSE w on w.id = tt.inwarehouseid and w.USEFLAG = 1");
		sql.append(" \r\n join STOCK_ALTER_MOVE_DETAIL samd on samd.samid = sam.id and samd.productid=ttd.productid");
		sql.append(" \r\n INNER JOIN ORGAN o on tt.oid = o.id and o.organtype = 1   ");
		sql.append(" \r\n LEFT JOIN F_UNIT f on ttd.productid = f.productid and ttd.unitid = f.funitid  ");
		sql.append(" \r\n LEFT JOIN ORGAN ino on tt.inoid = ino.id  and ino.organtype = 2 and ino.organmodel = 1");
		if(!StringUtil.isEmpty(scrForm.getOrganId())) {
			sql.append(" and ino.id = '"+scrForm.getOrganId()+"' "); 
		}
		sql.append(" \r\n LEFT JOIN PRODUCT p on ttd.productid = p.id   ");
//		sql.append(" \r\n LEFT JOIN PRODUCT_STRUCT pstr on p.PSID = pstr.STRUCTCODE   ");
//		sql.append(" \r\n LEFT JOIN COUNTRY_AREA country on ino.province=country.id   ");
//		sql.append(" \r\n LEFT JOIN (SELECT max(REGIONCODEID) regioncodeid,max(AREAID) areaid  FROM REGION_AREA GROUP BY AREAID) ra on ra.areaid=country.id   ");
//		sql.append(" \r\n LEFT JOIN REGION r on r.regioncode=ra.regioncodeid   ");
		sql.append(" \r\n where (samd.quantity-samd.receivequantity) > 0");
		sql.append(" \r\n  and p.USEFLAG = 1 ");
		
		
		//经销商退货-减消耗-20150429
		sql.append(" \r\n union all");
		sql.append(" \r\n --经销商退货-减消耗");
		sql.append(" \r\n select DISTINCT tt.billNo billNo ");
//		sql.append(" \r\n ,r.sortname regionName  ");
//		sql.append(" \r\n ,o.province provinceId  ");
		sql.append(" \r\n ,o.oecode oecode ");
//		sql.append(" \r\n ,country.areaname province  ");
		sql.append(" \r\n ,o.id  organId");
//		sql.append(" \r\n ,o.organname organName  ");
		sql.append(" \r\n ,ttd.productid productId ");
//		sql.append(" \r\n ,pstr.sortname psName ");
//		sql.append(" \r\n ,p.productname productName ");
//		sql.append(" \r\n  ,p.productnameen productNameen  ");
//		sql.append(" \r\n ,p.mcode mCode ");
//		sql.append(" \r\n ,p.matericalchdes matericalChDes  ");
//		sql.append(" \r\n ,p.matericalendes matericalEnDes  ");
//		sql.append(" \r\n ,p.sunit unitId  ");
//		sql.append(" \r\n ,p.specmode packSizeName ");
//		sql.append(" \r\n  ,p.packsizenameen packSizeNameEn  ");
		sql.append(" \r\n ,swb.batch batch  ");
		sql.append(" \r\n ,0 salesQuantity  ");
		sql.append(" \r\n ,-swb.cycleoutquantity  consumeQuantity ");
		sql.append(" \r\n ,TT.AUDITDATE makeDate  ");
		sql.append(" \r\n ,ino.id outOrganId,w.id  ");
		sql.append(" \r\n from TAKE_TICKET tt INNER JOIN TAKE_TICKET_DETAIL ttd on tt.id = ttd.ttid  and tt.isaudit = 1  and tt.bsort = 7 and tt.billno like 'OW%' ");
		if(!StringUtil.isEmpty(startDate)){
			sql.append(" \r\n and tt.auditdate >=to_date('" + startDate + "','yyyy-MM-dd hh24:mi:ss') "); //开始时间条件
		}
		if(!StringUtil.isEmpty(endDate)){
			sql.append(" \r\n and tt.auditdate < to_date('" + endDate + "','yyyy-MM-dd hh24:mi:ss') "); //结束时间条件
		}
		sql.append(" \r\n join WAREHOUSE w on w.id = tt.inwarehouseid and w.USEFLAG = 1");
		sql.append(" \r\n INNER JOIN ORGAN ino on tt.inoid = ino.id and ino.organtype = 2 and  ino.organmodel = 1 ");
		if(!StringUtil.isEmpty(scrForm.getOrganId())) {
			sql.append(" and ino.id = '"+scrForm.getOrganId()+"' "); 
		}
		sql.append(" \r\n INNER JOIN ORGAN o on tt.oid = o.id ");
		sql.append(" \r\n INNER JOIN STOCK_WASTE_BOOK swb on swb.billcode = tt.id and ttd.productid = swb.productid  ");
		sql.append(" \r\n LEFT JOIN F_UNIT f on ttd.productid = f.productid and ttd.unitid = f.funitid  ");
		sql.append(" \r\n LEFT JOIN PRODUCT p on ttd.productid = p.id   ");
//		sql.append(" \r\n LEFT JOIN PRODUCT_STRUCT pstr on p.PSID = pstr.STRUCTCODE   ");
//		sql.append(" \r\n LEFT JOIN COUNTRY_AREA country on o.province=country.id   ");
//		sql.append(" \r\n LEFT JOIN (SELECT max(REGIONCODEID) regioncodeid,max(AREAID) areaid  FROM REGION_AREA GROUP BY AREAID) ra on ra.areaid=country.id   ");
//		sql.append(" \r\n LEFT JOIN REGION r on r.regioncode=ra.regioncodeid   ");
		sql.append(" \r\n where p.USEFLAG = 1 ");
		//#start modified by ryan.xi at 20150714
		//分包损耗记到消耗
		sql.append(" \r\n --分包损耗记到消耗");
		sql.append(" \r\n union all");
		sql.append(" \r\n select DISTINCT tt.id billNo ");
//		sql.append(" \r\n ,r.sortname regionName ");
//		sql.append(" \r\n ,o.province provinceId ");
		sql.append(" \r\n ,o.oecode oecode ");
//		sql.append(" \r\n ,country.areaname province ");
		sql.append(" \r\n ,o.id  organId ");
//		sql.append(" \r\n ,o.organname organName ");
		sql.append(" \r\n ,ttd.OUTPRODUCTID productId  ");
//		sql.append(" \r\n ,pstr.sortname psName ");
//		sql.append(" \r\n ,p.productname productName ");
//		sql.append(" \r\n ,p.productnameen productNameen ");
//		sql.append(" \r\n ,p.mcode mCode ");
//		sql.append(" \r\n ,p.matericalchdes matericalChDes ");
//		sql.append(" \r\n ,p.matericalendes matericalEnDes ");
//		sql.append(" \r\n ,p.sunit unitId ");
//		sql.append(" \r\n ,p.specmode packSizeName");
//		sql.append(" \r\n ,p.packsizenameen packSizeNameEn");
		sql.append(" \r\n ,ttd.outbatch batch ");
		sql.append(" \r\n ,0 salesQuantity ");  // 销售数量
		sql.append(" \r\n ,ttd.wastage consumeQuantity"); //消耗数量
		sql.append(" \r\n ,TT.AUDITDATE makeDate ");
		sql.append(" \r\n ,o.id outOrganId,w.id ");
		sql.append(" \r\n from PACK_SEPARATE tt INNER JOIN PACK_SEPARATE_DETAIL ttd on tt.id = ttd.psid  and tt.isaudit = 1 ");
		if(!StringUtil.isEmpty(startDate)){
			sql.append(" \r\n and tt.auditdate >=to_date('" + startDate + "','yyyy-MM-dd hh24:mi:ss') "); //开始时间条件
		}
		if(!StringUtil.isEmpty(endDate)){
			sql.append(" \r\n and tt.auditdate < to_date('" + endDate + "','yyyy-MM-dd hh24:mi:ss') "); //结束时间条件
		}
		
		sql.append(" \r\n join WAREHOUSE w on w.id = tt.warehouseid and w.USEFLAG = 1 ");
		sql.append(" \r\n INNER JOIN ORGAN o on tt.ORGANID = o.id and o.organtype = 2 and  organmodel = 1 ");
		if(!StringUtil.isEmpty(scrForm.getOrganId())) {
			sql.append(" and o.id = '"+scrForm.getOrganId()+"' "); 
		}
		sql.append(" \r\n LEFT JOIN PRODUCT p on ttd.OUTPRODUCTID = p.id  ");
//		sql.append(" \r\n LEFT JOIN PRODUCT_STRUCT pstr on p.PSID = pstr.STRUCTCODE  ");
//		sql.append(" \r\n LEFT JOIN COUNTRY_AREA country on o.province=country.id  ");
//		sql.append(" \r\n LEFT JOIN (SELECT max(REGIONCODEID) regioncodeid,max(AREAID) areaid  FROM REGION_AREA GROUP BY AREAID) ra on ra.areaid=country.id  ");
//		sql.append(" \r\n LEFT JOIN REGION r on r.regioncode=ra.regioncodeid  ");
		sql.append(" \r\n where ttd.wastage <> 0 ");
		sql.append(" \r\n  and p.USEFLAG = 1 ");
		//#end modified by ryan.xi at 20150714
		
		
		//#start modified by ryan.xi at 20150805
		//其他入库-减消耗-20150429
		sql.append(" \r\n union all");
		sql.append(" \r\n --其他入库-减消耗");
		sql.append(" \r\n select DISTINCT oia.id billNo ");
//		sql.append(" \r\n ,r.sortname regionName  ");
//		sql.append(" \r\n ,o.province provinceId  ");
		sql.append(" \r\n ,o.oecode oecode ");
//		sql.append(" \r\n ,country.areaname province  ");
		sql.append(" \r\n ,o.id  organId");
//		sql.append(" \r\n ,o.organname organName  ");
		sql.append(" \r\n ,oida.productid productId ");
//		sql.append(" \r\n ,pstr.sortname psName ");
//		sql.append(" \r\n ,p.productname productName ");
//		sql.append(" \r\n  ,p.productnameen productNameen  ");
//		sql.append(" \r\n ,p.mcode mCode ");
//		sql.append(" \r\n ,p.matericalchdes matericalChDes  ");
//		sql.append(" \r\n ,p.matericalendes matericalEnDes  ");
//		sql.append(" \r\n ,p.sunit unitId  ");
//		sql.append(" \r\n ,p.packsizename packSizeName ");
//		sql.append(" \r\n  ,p.packsizenameen packSizeNameEn  ");
		sql.append(" \r\n ,oida.batch batch  ");
		sql.append(" \r\n ,0 salesQuantity  ");
		sql.append(" \r\n ,-(oida.quantity * f.xquantity) consumeQuantity  ");
		sql.append(" \r\n ,oia.AUDITDATE makeDate  ");
		sql.append(" \r\n ,o.id outOrganId,w.id  ");
		sql.append(" \r\n from OTHER_INCOME_ALL oia INNER JOIN OTHER_INCOME_DETAIL_ALL oida on oia.id = oida.oiid and oia.isaudit = 1 and oia.incomesort <> 6 ");
		
		if(!StringUtil.isEmpty(startDate)){
			sql.append(" \r\n and oia.auditdate >=to_date('" + startDate + "','yyyy-MM-dd hh24:mi:ss') "); //开始时间条件
		}
		if(!StringUtil.isEmpty(endDate)){
			sql.append(" \r\n and oia.auditdate < to_date('" + endDate + "','yyyy-MM-dd hh24:mi:ss') "); //结束时间条件
		}
		sql.append(" \r\n join WAREHOUSE w on w.id = oia.warehouseid and w.USEFLAG = 1");
		sql.append(" \r\n INNER JOIN ORGAN o on oia.organid= o.id and o.organtype = 2 and  organmodel = 1 ");
		if(!StringUtil.isEmpty(scrForm.getOrganId())) {
			sql.append(" and o.id = '"+scrForm.getOrganId()+"' "); 
		}
		sql.append(" \r\n LEFT JOIN F_UNIT f on oida.productid = f.productid and oida.unitid = f.funitid ");
		sql.append(" \r\n LEFT JOIN PRODUCT p on oida.productid = p.id ");
//		sql.append(" \r\n LEFT JOIN PRODUCT_STRUCT pstr on p.PSID = pstr.STRUCTCODE   ");
//		sql.append(" \r\n LEFT JOIN COUNTRY_AREA country on o.province=country.id   ");
//		sql.append(" \r\n LEFT JOIN (SELECT max(REGIONCODEID) regioncodeid,max(AREAID) areaid  FROM REGION_AREA GROUP BY AREAID) ra on ra.areaid=country.id   ");
//		sql.append(" \r\n LEFT JOIN REGION r on r.regioncode=ra.regioncodeid   ");
		sql.append(" \r\n where p.USEFLAG = 1 ");
		
		//其他出库-加消耗-20150429
		sql.append(" \r\n union all");
		sql.append(" \r\n --其他出库-加消耗");
		sql.append(" \r\n select DISTINCT oia.id billNo ");
//		sql.append(" \r\n ,r.sortname regionName  ");
//		sql.append(" \r\n ,o.province provinceId  ");
		sql.append(" \r\n ,o.oecode oecode ");
//		sql.append(" \r\n ,country.areaname province  ");
		sql.append(" \r\n ,o.id  organId");
//		sql.append(" \r\n ,o.organname organName  ");
		sql.append(" \r\n ,oida.productid productId ");
//		sql.append(" \r\n ,pstr.sortname psName ");
//		sql.append(" \r\n ,p.productname productName ");
//		sql.append(" \r\n  ,p.productnameen productNameen  ");
//		sql.append(" \r\n ,p.mcode mCode ");
//		sql.append(" \r\n ,p.matericalchdes matericalChDes  ");
//		sql.append(" \r\n ,p.matericalendes matericalEnDes  ");
//		sql.append(" \r\n ,p.sunit unitId  ");
//		sql.append(" \r\n ,p.packsizename packSizeName ");
//		sql.append(" \r\n  ,p.packsizenameen packSizeNameEn  ");
		sql.append(" \r\n ,oida.batch batch  ");
		sql.append(" \r\n ,0 salesQuantity  ");
		sql.append(" \r\n ,(oida.quantity * f.xquantity) consumeQuantity  ");
		sql.append(" \r\n ,oia.AUDITDATE makeDate  ");
		sql.append(" \r\n ,o.id outOrganId,w.id  ");
		sql.append(" \r\n from OTHER_SHIPMENT_BILL_ALL oia INNER JOIN OTHER_SHIPMENT_BILL_D_ALL oida on oia.id = oida.osid and oia.isaudit = 1 ");
		
		if(!StringUtil.isEmpty(startDate)){
			sql.append(" \r\n and oia.auditdate >=to_date('" + startDate + "','yyyy-MM-dd hh24:mi:ss') "); //开始时间条件
		}
		if(!StringUtil.isEmpty(endDate)){
			sql.append(" \r\n and oia.auditdate < to_date('" + endDate + "','yyyy-MM-dd hh24:mi:ss') "); //结束时间条件
		}
		sql.append(" \r\n join WAREHOUSE w on w.id = oia.warehouseid and w.USEFLAG = 1");
		sql.append(" \r\n INNER JOIN ORGAN o on oia.organid= o.id and o.organtype = 2 and  organmodel = 1 ");
		if(!StringUtil.isEmpty(scrForm.getOrganId())) {
			sql.append(" and o.id = '"+scrForm.getOrganId()+"' "); 
		}
		sql.append(" \r\n LEFT JOIN F_UNIT f on oida.productid = f.productid and oida.unitid = f.funitid ");
		sql.append(" \r\n LEFT JOIN PRODUCT p on oida.productid = p.id ");
//		sql.append(" \r\n LEFT JOIN PRODUCT_STRUCT pstr on p.PSID = pstr.STRUCTCODE   ");
//		sql.append(" \r\n LEFT JOIN COUNTRY_AREA country on o.province=country.id   ");
//		sql.append(" \r\n LEFT JOIN (SELECT max(REGIONCODEID) regioncodeid,max(AREAID) areaid  FROM REGION_AREA GROUP BY AREAID) ra on ra.areaid=country.id   ");
//		sql.append(" \r\n LEFT JOIN REGION r on r.regioncode=ra.regioncodeid   ");
		sql.append(" \r\n where p.USEFLAG = 1 ");
		sql.append(" \r\n ) temp ");
		System.out.println(sql.toString());
		EntityManager.executeUpdate(sql.toString());
	} 


	private void addInventoryDetailReportHistory(int year, int month,
			String endDate, ReportForm scrForm) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" INSERT INTO INVENTORY_DETAIL_HISTORY(YEAR,MONTH,ORGANID,OECODE,PRODUCTID,STOCKPILE,STOCKPILEINTRANSIT,STOCKPILETOSHIP,WAREHOUSEID,BATCH) ");
		sql.append(" \r\n select "+year+","+month+",res.organid,res.oecode,res.PRODUCTID,res.stockpile,res.stockintransit STOCKPILEINTRANSIT,res.STOCKPILETOSHIP,res.warehouseid,res.batch  from ( ");
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
		if(!StringUtil.isEmpty(scrForm.getOrganId())) {
			sql.append(" and o.id = '"+scrForm.getOrganId()+"' "); 
		}
		sql.append(" \r\n and o.organtype = 2 and o.organmodel = 1"); 
		sql.append(" \r\n UNION ALL ");

		//更早的库存明细
		//--发货单(入库)
		sql.append(" \r\n --发货单(入库)");
		sql.append(" \r\n select o.id as organid,o.organname,o.oecode,o.province,swb.productid,-swb.cycleinquantity as stockpile,0 as stockintransit,sam.INWAREHOUSEID as warehouseid ,swb.batch, 0 as stockpiletoship  from STOCK_ALTER_MOVE sam ");
		sql.append(" \r\n join ORGAN o on o.id = sam.receiveorganid and sam.iscomplete = 1");
		if(!StringUtil.isEmpty(scrForm.getOrganId())) {
			sql.append(" and o.id = '"+scrForm.getOrganId()+"' "); 
		}
		sql.append(" \r\n and sam.receivedate > to_date('" + endDate + "','yyyy-MM-dd hh24:mi:ss')");
		sql.append(" \r\n and sam.movedate > to_date('" + endDate + "','yyyy-MM-dd hh24:mi:ss')");
		sql.append(" \r\n and o.organtype = 2 and o.organmodel = 1"); 
		sql.append(" \r\n join STOCK_WASTE_BOOK swb on sam.id = swb.billcode ");
		
		//--盘库前发货单签收-减去
		sql.append(" \r\n UNION ALL");
		sql.append(" \r\n --盘库前发货单签收-减去");
		sql.append(" \r\n select o.id as organid,o.organname,o.oecode,o.province,swb.productid,-swb.cycleoutquantity as stockpile,0 as stockintransit,sam.INWAREHOUSEID as warehouseid ,swb.batch, 0 as stockpiletoship  from STOCK_ALTER_MOVE sam ");
		sql.append(" \r\n join ORGAN o on o.id = sam.receiveorganid and sam.iscomplete = 1 and not exists (select 1 from STOCK_WASTE_BOOK where billcode=sam.id)");
		if(!StringUtil.isEmpty(scrForm.getOrganId())) {
			sql.append(" and o.id = '"+scrForm.getOrganId()+"' "); 
		}
		sql.append(" \r\n and sam.movedate > to_date('" + endDate + "','yyyy-MM-dd hh24:mi:ss')");
		sql.append(" \r\n and o.organtype = 2 and o.organmodel = 1"); 
		sql.append(" \r\n join take_ticket tt on sam.id = tt.billno ");
		sql.append(" \r\n join STOCK_WASTE_BOOK swb on tt.id = swb.billcode ");
		sql.append(" \r\n UNION ALL");
		//--发货单(出库)
		sql.append(" \r\n --发货单(出库)");
		sql.append(" \r\n select o.id as organid,o.organname,o.oecode,o.province,swb.productid,swb.cycleoutquantity as stockpile,0 as stockintransit,sam.OUTWAREHOUSEID as warehouseid,swb.batch, 0 as stockpiletoship  from STOCK_ALTER_MOVE sam ");
		sql.append(" \r\n join ORGAN o on o.id = sam.outorganid and sam.isshipment = 1");
		if(!StringUtil.isEmpty(scrForm.getOrganId())) {
			sql.append(" and o.id = '"+scrForm.getOrganId()+"' "); 
		}
		sql.append(" \r\n and sam.shipmentdate > to_date('" + endDate + "','yyyy-MM-dd hh24:mi:ss')");
		sql.append(" \r\n and sam.movedate > to_date('" + endDate + "','yyyy-MM-dd hh24:mi:ss')");
		sql.append(" \r\n and o.organtype = 2 and o.organmodel = 1"); 
		sql.append(" \r\n join TAKE_TICKET tt on tt.billno = sam.id ");
		sql.append(" \r\n join STOCK_WASTE_BOOK swb on tt.id = swb.billcode");
		
		sql.append(" \r\n UNION ALL");
		
		//--发货单(待发货)
		sql.append(" \r\n --发货单(待发货)");
		sql.append(" \r\n select o.id as organid,o.organname,o.oecode,o.province,swb.productid,0 as stockpile,0 as stockintransit,sam.OUTWAREHOUSEID as warehouseid,swb.batch, swb.cycleoutquantity as stockpiletoship  from STOCK_ALTER_MOVE sam ");
		sql.append(" \r\n join ORGAN o on o.id = sam.outorganid and sam.isshipment = 1");
		if(!StringUtil.isEmpty(scrForm.getOrganId())) {
			sql.append(" and o.id = '"+scrForm.getOrganId()+"' "); 
		}
		sql.append(" \r\n and sam.shipmentdate > to_date('" + endDate + "','yyyy-MM-dd hh24:mi:ss')");
		sql.append(" \r\n and sam.movedate < to_date('" + endDate + "','yyyy-MM-dd hh24:mi:ss')");
		sql.append(" \r\n and o.organtype = 2 and o.organmodel = 1"); 
		sql.append(" \r\n join TAKE_TICKET tt on tt.billno = sam.id ");
		sql.append(" \r\n join STOCK_WASTE_BOOK swb on tt.id = swb.billcode");
		sql.append(" \r\n UNION ALL");
		
		// --转仓单(入库)
		sql.append(" \r\n --转仓单(入库)");
		sql.append(" \r\n select o.id as organid,o.organname,o.oecode,o.province,swb.productid,-swb.cycleinquantity as stockpile,0 as stockintransit,sm.INWAREHOUSEID as warehouseid,swb.batch, 0 as stockpiletoship from STOCK_MOVE sm ");
		sql.append(" \r\n join ORGAN o on o.id = sm.inorganid and sm.iscomplete = 1 and sm.inwarehouseid <> outwarehouseid");
		if(!StringUtil.isEmpty(scrForm.getOrganId())) {
			sql.append(" and o.id = '"+scrForm.getOrganId()+"' "); 
		}
		sql.append(" \r\n and sm.receivedate > to_date('" + endDate + "','yyyy-MM-dd hh24:mi:ss')");
		sql.append(" \r\n and sm.SHIPMENTDATE > to_date('" + endDate + "','yyyy-MM-dd hh24:mi:ss')");
		sql.append(" \r\n and o.organtype = 2 and o.organmodel = 1"); 
		sql.append(" \r\n join STOCK_WASTE_BOOK swb on sm.id = swb.billcode ");
		
		sql.append(" \r\n UNION ALL");
		//--转仓单(出库)
		sql.append(" \r\n --转仓单(出库)");
		sql.append(" \r\n select o.id as organid,o.organname,o.oecode,o.province,swb.productid,swb.cycleoutquantity as stockpile,0 as stockintransit,sm.OUTWAREHOUSEID as warehouseid,swb.batch, 0 as stockpiletoship from STOCK_MOVE sm ");
		sql.append(" \r\n join ORGAN o on o.id = sm.outorganid and sm.isshipment = 1 and sm.inwarehouseid <> outwarehouseid");
		if(!StringUtil.isEmpty(scrForm.getOrganId())) {
			sql.append(" and o.id = '"+scrForm.getOrganId()+"' "); 
		}
		sql.append(" \r\n and sm.shipmentdate > to_date('" + endDate + "','yyyy-MM-dd hh24:mi:ss')");
		sql.append(" \r\n and o.organtype = 2 and o.organmodel = 1"); 
		sql.append(" \r\n join TAKE_TICKET tt on tt.billno = sm.id ");
		sql.append(" \r\n join STOCK_WASTE_BOOK swb on tt.id = swb.billcode ");
		
		sql.append(" \r\n UNION ALL");
		//--退货单(入库)
		sql.append(" \r\n --退货单(入库)");
		sql.append(" \r\n select o.id as organid,o.organname,o.oecode,o.province,swb.productid,-swb.cycleinquantity as stockpile,0 as stockintransit,ow.INWAREHOUSEID as warehouseid,swb.batch, 0 as stockpiletoship from ORGAN_WITHDRAW ow ");
		sql.append(" \r\n join take_ticket tt on tt.billno = ow.id and ow.iscomplete = 1");
		sql.append(" \r\n and tt.auditdate > to_date('" + endDate + "','yyyy-MM-dd hh24:mi:ss')");
		sql.append(" \r\n join ORGAN o on o.id = ow.receiveorganid");
		if(!StringUtil.isEmpty(scrForm.getOrganId())) {
			sql.append(" and o.id = '"+scrForm.getOrganId()+"' "); 
		}
		sql.append(" \r\n and o.organtype = 2 and o.organmodel = 1"); 
		sql.append(" \r\n join STOCK_WASTE_BOOK swb on ow.id = swb.billcode ");
		
		sql.append(" \r\n UNION ALL");
		//--退货单(出库)
		sql.append(" \r\n --退货单(出库)");
		sql.append(" \r\n select o.id as organid,o.organname,o.oecode,o.province,swb.productid,swb.cycleoutquantity as stockpile,0 as stockintransit,ow.WAREHOUSEID as warehouseid,swb.batch, 0 as stockpiletoship from ORGAN_WITHDRAW ow ");
		sql.append(" \r\n join ORGAN o on o.id = ow.porganid and ow.takestatus = 1");
		if(!StringUtil.isEmpty(scrForm.getOrganId())) {
			sql.append(" and o.id = '"+scrForm.getOrganId()+"' "); 
		}
		sql.append(" \r\n and o.organtype = 2 and o.organmodel = 1"); 
		sql.append(" \r\n join TAKE_TICKET tt on tt.billno = ow.id ");
		sql.append(" \r\n and tt.auditdate >  to_date('" + endDate + "','yyyy-MM-dd hh24:mi:ss')");
		sql.append(" \r\n join STOCK_WASTE_BOOK swb on tt.id = swb.billcode ");
		sql.append(" \r\n UNION ALL");
		
		//-- 未签收的单据（在途库存）
		// --发货单
		sql.append(" \r\n --未签收的单据（在途库存）--发货单");
		sql.append(" \r\n select o.id as organid,o.organname,o.oecode,o.province,swb.productid,0 as stockpile,swb.cycleoutquantity as stockintransit,sam.INWAREHOUSEID as warehouseid,swb.batch, 0 as stockpiletoship from STOCK_ALTER_MOVE sam ");
		sql.append(" \r\n join ORGAN o on o.id = sam.receiveorganid and sam.isshipment = 1 and sam.isblankout = 0 and sam.iscomplete = 0 ");
		if(!StringUtil.isEmpty(scrForm.getOrganId())) {
			sql.append(" and o.id = '"+scrForm.getOrganId()+"' "); 
		}
		sql.append(" \r\n and sam.movedate < to_date('" + endDate + "','yyyy-MM-dd hh24:mi:ss')");
		sql.append(" \r\n and o.organtype = 2 and o.organmodel = 1"); 
		sql.append(" \r\n join TAKE_TICKET tt on tt.billno = sam.id ");
		sql.append(" \r\n join STOCK_WASTE_BOOK swb on tt.id = swb.billcode ");
		sql.append(" \r\n UNION ALL");
		//--转仓单
		sql.append(" \r\n --未签收的单据（在途库存）--转仓单");
		sql.append(" \r\n select o.id as organid,o.organname,o.oecode,o.province,swb.productid,0 as stockpile,swb.cycleoutquantity as stockintransit,sm.INWAREHOUSEID as warehouseid,swb.batch, 0 as stockpiletoship from STOCK_MOVE sm ");
		sql.append(" \r\n join ORGAN o on o.id = sm.inorganid and sm.isshipment = 1 and sm.iscomplete = 0 and sm.isblankout = 0 and sm.inwarehouseid <> outwarehouseid");
		if(!StringUtil.isEmpty(scrForm.getOrganId())) {
			sql.append(" and o.id = '"+scrForm.getOrganId()+"' "); 
		}
		sql.append(" \r\n and sm.SHIPMENTDATE < to_date('" + endDate + "','yyyy-MM-dd hh24:mi:ss')");
		sql.append(" \r\n and o.organtype = 2 and o.organmodel = 1"); 
		sql.append(" \r\n join TAKE_TICKET tt on tt.billno = sm.id ");
		sql.append(" \r\n join STOCK_WASTE_BOOK swb on tt.id = swb.billcode ");
		
		sql.append(" \r\n UNION ALL");
		// --退货单
		sql.append(" \r\n --未签收的单据（在途库存）--退货单");
		sql.append(" \r\n select o.id as organid,o.organname,o.oecode,o.province,swb.productid,0 as stockpile,swb.cycleoutquantity  as stockintransit,ow.INWAREHOUSEID as warehouseid,swb.batch, 0 as stockpiletoship from ORGAN_WITHDRAW ow ");
		sql.append(" \r\n join ORGAN o on o.id = ow.receiveorganid and ow.takestatus = 1 and ow.iscomplete = 0 and ow.isblankout = 0 ");
		if(!StringUtil.isEmpty(scrForm.getOrganId())) {
			sql.append(" and o.id = '"+scrForm.getOrganId()+"' "); 
		}
		sql.append(" \r\n and o.organtype = 2 and o.organmodel = 1"); 
		sql.append(" \r\n join TAKE_TICKET tt on tt.billno = ow.id ");
		sql.append(" \r\n and tt.auditdate < to_date('" + endDate + "','yyyy-MM-dd hh24:mi:ss')");
		sql.append(" \r\n join STOCK_WASTE_BOOK swb on tt.id = swb.billcode ");
		
		//#start modified by ryan.xi at 20150714
		//--分包单(入库)
		sql.append(" \r\n --分包单(入库)");
		sql.append(" \r\n UNION ALL");
		sql.append(" \r\n select o.id as organid,o.organname,o.oecode,o.province,swb.inproductid,-swb.inquantity as stockpile,0 as stockintransit,sam.WAREHOUSEID as warehouseid ,swb.inbatch, 0 as stockpiletoship  from PACK_SEPARATE sam ");
		sql.append(" \r\n join ORGAN o on o.id = sam.ORGANID and sam.ISAUDIT = 1 ");
		if(!StringUtil.isEmpty(scrForm.getOrganId())) {
			sql.append(" and o.id = '"+scrForm.getOrganId()+"' "); 
		}
		sql.append(" \r\n and sam.AUDITDATE > to_date('" + endDate + "','yyyy-MM-dd hh24:mi:ss')");
		sql.append(" \r\n and o.organtype = 2 and o.organmodel = 1"); 
		sql.append(" \r\n join PACK_SEPARATE_DETAIL swb on sam.id = swb.psid ");
		
		//--分包单(出库)
		sql.append(" \r\n UNION ALL");
		sql.append(" \r\n --分包单(出库)");
		sql.append(" \r\n select o.id as organid,o.organname,o.oecode,o.province,swb.outproductid,swb.outquantity as stockpile,0 as stockintransit,sam.WAREHOUSEID as warehouseid ,swb.outbatch, 0 as stockpiletoship  from PACK_SEPARATE sam ");
		sql.append(" \r\n join ORGAN o on o.id = sam.ORGANID and sam.ISAUDIT = 1 ");
		if(!StringUtil.isEmpty(scrForm.getOrganId())) {
			sql.append(" and o.id = '"+scrForm.getOrganId()+"' "); 
		}
		sql.append(" \r\n and sam.AUDITDATE > to_date('" + endDate + "','yyyy-MM-dd hh24:mi:ss')");
		sql.append(" \r\n and o.organtype = 2 and o.organmodel = 1"); 
		sql.append(" \r\n join PACK_SEPARATE_DETAIL swb on sam.id = swb.psid ");

		//其他入库-减库存-20150429
		sql.append(" \r\n UNION ALL ");
		sql.append(" \r\n --其他入库-减库存-20150429");
		sql.append(" \r\n select o.id as organid,o.organname,o.oecode,o.province,oida.productid,-(oida.quantity * f.xquantity) as stockpile,0 as stockintransit,oia.warehouseid as warehouseid,oida.batch, 0 as stockpiletoship ");
		sql.append(" \r\n from OTHER_INCOME_ALL oia INNER JOIN OTHER_INCOME_DETAIL_ALL oida on oia.id = oida.oiid and oia.isaudit = 1 ");
		sql.append(" \r\n and oia.auditdate >=to_date('" + endDate + "','yyyy-MM-dd hh24:mi:ss') "); //开始时间条件
		sql.append(" \r\n INNER JOIN ORGAN o on oia.organid= o.id");
		if(!StringUtil.isEmpty(scrForm.getOrganId())) {
			sql.append(" and o.id = '"+scrForm.getOrganId()+"' "); 
		}
		sql.append(" \r\n and o.organtype = 2 and o.organmodel = 1"); 
		sql.append(" \r\n LEFT JOIN F_UNIT f on oida.productid = f.productid and oida.unitid = f.funitid  "); 
		
		//其他出库-加库存-20150429
		sql.append(" \r\n UNION ALL ");
		sql.append(" \r\n --其他出库-加库存-20150429");
		sql.append(" \r\n select o.id as organid,o.organname,o.oecode,o.province,oida.productid,(oida.quantity * f.xquantity) as stockpile,0 as stockintransit,oia.warehouseid as warehouseid,oida.batch, 0 as stockpiletoship ");
		sql.append(" \r\n from OTHER_SHIPMENT_BILL_ALL oia INNER JOIN OTHER_SHIPMENT_BILL_D_ALL oida on oia.id = oida.osid and oia.isaudit = 1");
		sql.append(" \r\n and oia.auditdate >=to_date('" + endDate + "','yyyy-MM-dd hh24:mi:ss') "); //开始时间条件
		sql.append(" \r\n INNER JOIN ORGAN o on oia.organid= o.id");
		if(!StringUtil.isEmpty(scrForm.getOrganId())) {
			sql.append(" and o.id = '"+scrForm.getOrganId()+"' "); 
		}
		sql.append(" \r\n and o.organtype = 2 and o.organmodel = 1"); 
		sql.append(" \r\n LEFT JOIN F_UNIT f on oida.productid = f.productid and oida.unitid = f.funitid  "); 
		
		sql.append(" \r\n ) GROUP BY organid,productid,warehouseid, batch ");
		sql.append(" \r\n ) res  ");
		sql.append(" \r\n join WAREHOUSE wa on res.WAREHOUSEID = wa.id and wa.USEFLAG = 1");
		sql.append(" \r\n LEFT JOIN COUNTRY_AREA ca on ca.id = res.province ");
		
		sql.append(" \r\n LEFT JOIN REGION_AREA ra on ra.areaid = res.province ");
		sql.append(" \r\n LEFT JOIN REGION r on r.regioncode = ra.regioncodeid ");
		sql.append(" \r\n where  (stockpile <> 0 or stockintransit <> 0 or stockpiletoship <> 0)");
		sql.append(" \r\n and  res.productid not in (select id from product where USEFLAG = 0) ");
		System.out.println(sql.toString());
		EntityManager.executeUpdate(sql.toString());
	}
	
	public static void main(String[] args) {
//		Calendar initDate = Calendar.getInstance();
//		initDate.set(Calendar.YEAR, 2015);
//		initDate.set(Calendar.MONTH, 0);
//		initDate.set(Calendar.DAY_OF_MONTH, 1);
//		System.out.println(initDate.getTime());
//		System.out.println(DateUtil.formatDate(initDate.getTime()));
//		
//		Calendar oldDay = Calendar.getInstance();
//		oldDay.set(Calendar.YEAR, 2017);
//		oldDay.set(Calendar.MONTH, 3);
//		oldDay.set(Calendar.DAY_OF_MONTH, 1);
//		System.out.println(DateUtil.formatDate(oldDay.getTime()));
		
		Calendar thisMonth = Calendar.getInstance();
		thisMonth.set(Calendar.DAY_OF_MONTH, 1);
		String endDate = DateUtil.formatDate(thisMonth.getTime());
		
		
		Calendar lastMonth = Calendar.getInstance();
		lastMonth.add(Calendar.MONTH, -1);
		lastMonth.set(Calendar.DAY_OF_MONTH, 1);
		String startDate = DateUtil.formatDate(lastMonth.getTime());
		
		int endYear = lastMonth.get(Calendar.YEAR);
		int endMonth = lastMonth.get(Calendar.MONTH) + 1;
		
		Calendar initDate = Calendar.getInstance();
		initDate.set(Calendar.YEAR, 2015);
		initDate.set(Calendar.MONTH, 0);
		initDate.set(Calendar.DAY_OF_MONTH, 1);
		
		while(initDate.before(thisMonth)) {
			startDate = DateUtil.formatDate(initDate.getTime());
			int year=initDate.get(Calendar.YEAR);
			int month=initDate.get(Calendar.MONTH)+ 1;
			initDate.add(Calendar.MONTH, 1);
			endDate = DateUtil.formatDate(initDate.getTime());
			System.out.println(year+" "+month+" "+startDate+" "+endDate);
//			addConsumeDetailReportHistory(year, month, startDate, endDate);
		}
	}

	private void delInventoryHistory(ReportForm scrForm) throws Exception {
		StringBuffer logSql = new StringBuffer();
		logSql.append("insert into INVENTORY_DETAIL_HISTORY_LOG select idh.*,SYSDATE from INVENTORY_DETAIL_HISTORY idh where 1=1 ");
		if(!StringUtil.isEmpty(scrForm.getOrganId())) {
			logSql.append(" and organId = '"+scrForm.getOrganId()+"' ");
		}
		if(!StringUtil.isEmpty(scrForm.getStartDate())) {
			logSql.append("\r\n and (year || case when month < 10 THEN '0'||month ELSE to_char(month) END) >= '"+scrForm.getStartDate().substring(0,7).replace("-", "")+"'");
		}
		EntityManager.executeUpdate(logSql.toString());
		StringBuffer sql = new StringBuffer();
		sql.append("delete from INVENTORY_DETAIL_HISTORY where 1=1 ");
		if(!StringUtil.isEmpty(scrForm.getOrganId())) {
			sql.append(" and organId = '"+scrForm.getOrganId()+"' ");
		}
		if(!StringUtil.isEmpty(scrForm.getStartDate())) {
			sql.append("\r\n and (year || case when month < 10 THEN '0'||month ELSE to_char(month) END) >= '"+scrForm.getStartDate().substring(0,7).replace("-", "")+"'");
		}
		System.out.println(sql.toString());
		EntityManager.executeUpdate(sql.toString());
	}

	private Calendar getThisMonth(String dateStr) {
		//获取当前月份
		Calendar thisMonth = Calendar.getInstance();
		thisMonth.setTime(DateUtil.StringToDate(dateStr));
		thisMonth.set(Calendar.DAY_OF_MONTH, 1);
		thisMonth.set(Calendar.HOUR_OF_DAY, 0);
		thisMonth.set(Calendar.MINUTE, 0);
		thisMonth.set(Calendar.SECOND, 0);
		thisMonth.set(Calendar.MILLISECOND, 0);
		return thisMonth;
	}

	public void updInventoryDetailHistory(ReportForm scrForm) throws Exception {
		//先删除历史记录
		delInventoryHistory(scrForm);
		Calendar fromMonth = getThisMonth(scrForm.getStartDate());
		updInventoryDetailReportHistory(fromMonth, scrForm);
	}
	
	public void updInventoryDetailReportHistory(Calendar initDate, ReportForm scrForm) throws Exception { 
		try {
			//检查是否已统计过
			BaseResource br = appBr.getBaseResourceValue("InventoryDetailDate", 1);
			//判断是否有统计过
			if(br == null) {
				throw new Exception("还未生成过历史记录,无法更新");
			} else {
				//获取最新历史数据日期
				Calendar thisMonth = getThisMonth(br.getTagsubvalue());
				//2017-04之前的库存按老的方式统计好每月的历史库存
				Calendar oldDay = getOldDay();
				while(initDate.before(thisMonth)) {
					int year=initDate.get(Calendar.YEAR);
					int month=initDate.get(Calendar.MONTH) + 1;
					if(initDate.before(oldDay)) {
						initDate.add(Calendar.MONTH, 1);
						String endDate = DateUtil.formatDate(initDate.getTime());
						addInventoryDetailReportHistory(year, month, endDate, scrForm);
					} else {
						initDate.add(Calendar.MONTH, -1);
						int iYear=initDate.get(Calendar.YEAR);
						int iMonth=initDate.get(Calendar.MONTH) + 1;
						initDate.add(Calendar.MONTH, 2);
						addInventoryDetailReportHistoryBySalesAndConsume(year, month, iYear,iMonth, scrForm);
					}
				}
			}
		} catch (Exception e) {
			logger.error("生成库存历史数据失败", e);
			throw e;
		}
	}

	private Calendar getOldDay() {
		Calendar oldDay = Calendar.getInstance();
		oldDay.set(Calendar.YEAR, 2017);
		oldDay.set(Calendar.MONTH, 3);
		oldDay.set(Calendar.DAY_OF_MONTH, 1);
		oldDay.set(Calendar.HOUR_OF_DAY, 0);
		oldDay.set(Calendar.MINUTE, 0);
		oldDay.set(Calendar.SECOND, 0);
		oldDay.set(Calendar.MILLISECOND, 0);
		return oldDay;
	}

	public void updSalesDetailHistory(ReportForm scrForm) throws Exception {
		//先删除历史记录
		delSalesDetailHistory(scrForm);
		Calendar fromMonth = getThisMonth(scrForm.getStartDate());
		//更新销售
		updSalesDetailReportHistory(fromMonth, scrForm);
		//更新库存
//		updInventoryDetailHistory(scrForm);
		
	}

	private void updSalesDetailReportHistory(Calendar lastDate,
			ReportForm scrForm) throws Exception { 
		try {
			//检查是否已统计过
			BaseResource br = appBr.getBaseResourceValue("SalesDetailDate", 1);
			if(br == null) {
				throw new Exception("还未生成过历史记录,无法更新");
			} else {
				//获取最新历史数据日期
				Calendar thisMonth = getThisMonth(br.getTagsubvalue());
				while(lastDate.before(thisMonth)) {
					String startDate = DateUtil.formatDate(lastDate.getTime());
					int year=lastDate.get(Calendar.YEAR);
					int month=lastDate.get(Calendar.MONTH) + 1;
					lastDate.add(Calendar.MONTH, 1);
					String endDate = DateUtil.formatDate(lastDate.getTime());
					addSalesDetailReportHistory(year, month, startDate, endDate, scrForm);
				}
			}
		} catch (Exception e) {
			logger.error("生成销售明细历史数据失败", e);
			throw e;
		}
		
	}

	private void delSalesDetailHistory(ReportForm scrForm) throws Exception {
		StringBuffer logSql = new StringBuffer();
		logSql.append("insert into SALES_DETAIL_HISTORY_LOG select sdh.*,SYSDATE from SALES_DETAIL_HISTORY sdh where 1=1 ");
		if(!StringUtil.isEmpty(scrForm.getOrganId())) {
			logSql.append(" and organId = '"+scrForm.getOrganId()+"' ");
		}
		if(!StringUtil.isEmpty(scrForm.getStartDate())) {
			logSql.append("\r\n and (year || case when month < 10 THEN '0'||month ELSE to_char(month) END) >= '"+scrForm.getStartDate().substring(0,7).replace("-", "")+"'");
		}
		EntityManager.executeUpdate(logSql.toString());
		StringBuffer sql = new StringBuffer();
		sql.append("delete from SALES_DETAIL_HISTORY where 1=1 ");
		if(!StringUtil.isEmpty(scrForm.getOrganId())) {
			sql.append(" and organId = '"+scrForm.getOrganId()+"' ");
		}
		if(!StringUtil.isEmpty(scrForm.getStartDate())) {
			sql.append("\r\n and (year || case when month < 10 THEN '0'||month ELSE to_char(month) END) >= '"+scrForm.getStartDate().substring(0,7).replace("-", "")+"'");
		}
		System.out.println(sql.toString());
		EntityManager.executeUpdate(sql.toString());
		
	}

	public void updConsumeDetailHistory(ReportForm scrForm) throws Exception {
		//先删除历史记录
		delConsumeDetailHistory(scrForm);
		Calendar fromMonth = getThisMonth(scrForm.getStartDate());
		//更新销售
		updConsumeDetailReportHistory(fromMonth, scrForm);
		//更新库存
//		updInventoryDetailHistory(scrForm);
		
	}

	private void updConsumeDetailReportHistory(Calendar lastDate,
			ReportForm scrForm) throws Exception { 
		try {
			//检查是否已统计过
			BaseResource br = appBr.getBaseResourceValue("ConsumeDetailDate", 1);
			//若是第一次统计,统计到目前为止所有的数据
			if(br == null) {
				throw new Exception("还未生成过历史记录,无法更新");
			} else {
				//获取最新历史数据日期
				Calendar thisMonth = getThisMonth(br.getTagsubvalue());
				while(lastDate.before(thisMonth)) {
					String startDate = DateUtil.formatDate(lastDate.getTime());
					int year=lastDate.get(Calendar.YEAR);
					int month=lastDate.get(Calendar.MONTH) + 1;
					lastDate.add(Calendar.MONTH, 1);
					String endDate = DateUtil.formatDate(lastDate.getTime());
					addConsumeDetailReportHistory(year, month, startDate, endDate, scrForm);
				}
			}
		} catch (Exception e) {
			logger.error("生成消耗明细历史数据失败", e);
			throw e;
		}
	}

	private void delConsumeDetailHistory(ReportForm scrForm) throws Exception {
		StringBuffer logSql = new StringBuffer();
		logSql.append("insert into CONSUME_DETAIL_HISTORY_LOG select cdh.*,SYSDATE from CONSUME_DETAIL_HISTORY cdh where 1=1 ");
		if(!StringUtil.isEmpty(scrForm.getOrganId())) {
			logSql.append(" and outOrganId = '"+scrForm.getOrganId()+"' ");
		}
		if(!StringUtil.isEmpty(scrForm.getStartDate())) {
			logSql.append("\r\n and (year || case when month < 10 THEN '0'||month ELSE to_char(month) END) >= '"+scrForm.getStartDate().substring(0,7).replace("-", "")+"'");
		}
		EntityManager.executeUpdate(logSql.toString());
		StringBuffer sql = new StringBuffer();
		sql.append("delete from CONSUME_DETAIL_HISTORY where 1=1 ");
		if(!StringUtil.isEmpty(scrForm.getOrganId())) {
			sql.append(" and outOrganId = '"+scrForm.getOrganId()+"' ");
		}
		if(!StringUtil.isEmpty(scrForm.getStartDate())) {
			sql.append("\r\n and (year || case when month < 10 THEN '0'||month ELSE to_char(month) END) >= '"+scrForm.getStartDate().substring(0,7).replace("-", "")+"'");
		}
		System.out.println(sql.toString());
		EntityManager.executeUpdate(sql.toString());
		
	} 
}
