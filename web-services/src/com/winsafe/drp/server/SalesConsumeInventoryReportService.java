package com.winsafe.drp.server;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.apache.log4j.Logger;
import org.hibernate.HibernateException;

import com.winsafe.drp.dao.AppBaseResource;
import com.winsafe.drp.dao.BaseResource;
import com.winsafe.drp.dao.ProductRollingPrice;
import com.winsafe.drp.dao.UsersBean; 
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.DbUtil; 
import com.winsafe.hbm.util.StringUtil;

public class SalesConsumeInventoryReportService extends ReportServices{
	private static Logger logger = Logger.getLogger(SalesConsumeInventoryReportService.class);
	
	
	
	/**
	 * 获取产品单价(计量单位)
	 * @param year
	 * @param month
	 * @param productId
	 * @return
	 * @throws SQLException 
	 * @throws HibernateException 
	 */
//	public double getPrductRollingPrice(int year, int month, String productId, String organId) throws HibernateException, SQLException {
//		//获取12月之前(包括当前月)的年份与月份
//		Calendar calendar = Calendar.getInstance();
//		calendar.set(year, month - 1, 1);
//		String thisYear = DateUtil.formatDate(calendar.getTime(), "yyyyMM");
//		calendar.add(Calendar.MONTH, -11);
//		String lastYear = DateUtil.formatDate(calendar.getTime(), "yyyyMM");
//		
//		StringBuffer sql = new StringBuffer();
//		sql.append("\r\n select sum(ach.sales_Value) as salesValue, sum(ach.sales_Volume * pro.boxquantity) as salesVolume from Sales_Consum_History ach");
//		sql.append("\r\n join Product pro  on ach.productId = pro.id  ");
//		sql.append("\r\n where ach.productId = '"+productId+"' and ach.organId = '"+organId+"'");
//		sql.append("\r\n and (year || case when month < 10 THEN '0'||month ELSE to_char(month) END) >= '"+lastYear+"'");
//		sql.append("\r\n and (year || case when month < 10 THEN '0'||month ELSE to_char(month) END) <= '"+thisYear+"'");
//		sql.append("\r\n and ach.hasInvoice = 1 ");
//		
//		List<Map<String,String>> list =  EntityManager.jdbcquery(sql.toString());
//		Map<String,String> map = list.get(0);
//		Double salesValue =  getDouble(map.get("salesvalue"));
//		Double salesVolume =  getDouble(map.get("salesvolume"));
//		if(salesValue == 0d || salesVolume == 0d) {
//			return 0d;
//		}
//		return ArithDouble.div(salesValue, salesVolume,2);
//		
//	}
	
	
	/**
	 * 获取产品单价(计量单位)
	 * @param year
	 * @param month
	 * @param productId
	 * @return
	 * @throws SQLException 
	 * @throws HibernateException 
	 */
	public double getPrductRollingPrice(int year, int month, String productId, String organId) throws HibernateException, SQLException {
		StringBuffer sql = new StringBuffer();
//		sql.append("\r\n select price/p.boxquantity from SalesConsumHistory sch, Product p where sch.productId = p.id and sch.productId = '"+productId+"' and sch.organId = '"+organId+"' and sch.year = " + year +" and sch.month = "+month);
		sql.append("\r\n select price from SalesConsumHistory sch where sch.productId = '"+productId+"' and sch.organId = '"+organId+"' and sch.year = " + year +" and sch.month = "+month);
		Double price = (Double) EntityManager.find(sql.toString());
		if(price == null) {
			return 0d;
		}
		return price;
		
	}
	
	
	private double getDouble(String value) {
		if(!StringUtil.isEmpty(value)) {
			return Double.parseDouble(value);
		} 
		return 0d;
	}
	
	
	
	public List<Map<String,String>> getInitalSalesConsumHistoryData(String dateTime) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("\r\n select oid as organId, wid as warehouseId, pid as productId, sum(salesvolume) salesVolume, mydate, sum(case when salesvalue is null then 0 else salesvalue end) salesValue, sum(consumvolume) as consumVolume, sum(case when invQty is null then 0 else invQty end) as invQty, sum(othervolume) as otherConsumVolume from (  ");
		//工厂给PD
		sql.append("\r\n --工厂给PD");
		sql.append("\r\n select sam.receiveorganid oid,sam.inwarehouseid wid, samd.productid pid,samd.quantity * f.xquantity as salesvolume, 0 as consumvolume, to_char(movedate, 'yyyymm') as mydate, iv.net_val as salesvalue, iv.invQty as invQty, 0 as othervolume from STOCK_ALTER_MOVE sam  ");
		sql.append("\r\n INNER JOIN STOCK_ALTER_MOVE_DETAIL samd on sam.id = samd.samid ");
		
		sql.append("\r\n and sam.inwarehouseid is not null ");
		 
		sql.append("\r\n and sam.isaudit = 1 and sam.isshipment = 1 and sam.isblankout = 0 ");
		sql.append("\r\n and sam.shipmentdate < to_date('"+dateTime+"','yyyy-MM-dd hh24:mi:ss') ");
		sql.append("\r\n INNER JOIN ORGAN o on sam.outorganid = o.id and o.organtype = 1 ");
		sql.append("\r\n INNER JOIN ORGAN ino on sam.receiveorganid = ino.id and ino.organtype = 2 and ino.organmodel = 1 ");
		sql.append("\r\n JOIN F_UNIT f on samd.productid = f.productid and samd.unitid = f.funitid  ");
		sql.append("\r\n LEFT JOIN ");
		sql.append("\r\n (select DELIVERY_NUMBER, MATERIAL_CODE,sum(TO_NUMBER(invoice_qty)) as invQty, sum(TO_NUMBER(NET_VAL)) as net_val from INVOICE  ");
		sql.append("\r\n group by DELIVERY_NUMBER, MATERIAL_CODE ) iv on sam.nccode = iv.DELIVERY_NUMBER and samd.nccode = iv.material_code");
	
		//盘库前单据拒签-统计价格-20151216
		sql.append(" \r\n UNION ALL ");
		sql.append("\r\n --盘库前单据拒签-统计价格");
		sql.append(" \r\n select sam.receiveorganid organId,sam.inwarehouseid warehouseId, samd.productid productId,0,0, to_char(shipmentdate, 'yyyymm'), 0,0,0 from STOCK_ALTER_MOVE sam ");
		sql.append(" \r\n INNER JOIN STOCK_ALTER_MOVE_DETAIL samd on sam.id = samd.samid and SAM.ISBLANKOUT = 1 ");
		sql.append(" \r\n JOIN INVOICE iv on sam.nccode = iv.DELIVERY_NUMBER and samd.nccode = iv.material_code and iv.invoice_type ='M' ");
		
		//采购入库-加销售-20151209
		sql.append("\r\n --采购入库-加销售");
		sql.append(" \r\n UNION ALL ");
		sql.append(" \r\n select oia.organid, oia.WAREHOUSEID, oida.productid pid,(oida.quantity * f.xquantity),0,to_char(oia.auditdate, 'yyyymm'), 0,0, 0 ");
		sql.append(" \r\n from OTHER_INCOME_ALL oia INNER JOIN OTHER_INCOME_DETAIL_ALL oida on oia.id = oida.oiid and oia.isaudit = 1 and oia.incomesort = 6 ");
		sql.append(" \r\n and oia.auditdate < to_date('" + dateTime + "','yyyy-MM-dd hh24:mi:ss') "); //结束时间条件
		sql.append(" \r\n INNER JOIN ORGAN o on oia.organid= o.id and o.organtype = 2 and  organmodel = 1 ");
		sql.append(" \r\n JOIN F_UNIT f on oida.productid = f.productid and oida.unitid = f.funitid  "); 
		
		//PD调拨回来的
		sql.append("\r\n --PD调拨回来的");
		sql.append("\r\n UNION ALL");
		
		sql.append("\r\n select tt.inoid ,tt.inwarehouseid, ttd.productid ,ttd.realquantity * f.xquantity , 0, to_char(tt.auditdate, 'yyyymm') ,0 ,0, 0 ");
		sql.append("\r\n from TAKE_TICKET tt INNER JOIN TAKE_TICKET_DETAIL ttd on tt.id = ttd.ttid  and tt.isaudit = 1  and tt.bsort = 2 ");
		sql.append("\r\n and tt.auditdate < to_date('"+dateTime+"','yyyy-MM-dd hh24:mi:ss') ");
		sql.append("\r\n INNER JOIN ORGAN o on tt.oid = o.id and o.organtype = 2 and o.organmodel = 1 ");
		sql.append("\r\n LEFT JOIN F_UNIT f on ttd.productid = f.productid and ttd.unitid = f.funitid ");
		
		//调拨给其他PD
		sql.append("\r\n --调拨给其他PD");
		sql.append("\r\n UNION ALL");
		
		sql.append("\r\n select tt.oid,tt.warehouseid,ttd.productid pid ,-ttd.realquantity * f.xquantity quantity , 0, to_char(tt.auditdate, 'yyyymm') ,0 ,0, 0  ");
		sql.append("\r\n from TAKE_TICKET tt INNER JOIN TAKE_TICKET_DETAIL ttd on tt.id = ttd.ttid  and tt.isaudit = 1  and tt.bsort = 2   ");
		sql.append("\r\n and tt.auditdate < to_date('"+dateTime+"','yyyy-MM-dd hh24:mi:ss') ");
		sql.append("\r\n INNER JOIN ORGAN o on tt.inoid = o.id and o.organtype = 2 and  o.organmodel = 1 ");
		sql.append("\r\n LEFT JOIN F_UNIT f on ttd.productid = f.productid and ttd.unitid = f.funitid ");
		
		//扣减分包时大包的销售数量
		sql.append("\r\n --扣减分包时大包的销售数量");
		sql.append("\r\n UNION ALL");
		
		sql.append("\r\n select tt.ORGANID oid,tt.WAREHOUSEID, ttd.OUTPRODUCTID pid ,-(OUTQUANTITY-ttd.wastage) quantity , 0, to_char(tt.auditdate, 'yyyymm'), 0,0, 0 ");
		sql.append("\r\n from PACK_SEPARATE tt INNER JOIN PACK_SEPARATE_DETAIL ttd on tt.id = ttd.psid  and tt.isaudit = 1  ");
		sql.append("\r\n and tt.auditdate < to_date('"+dateTime+"','yyyy-MM-dd hh24:mi:ss') ");
		
		//增加分包时小包的销售数量
		sql.append("\r\n --增加分包时小包的销售数量");
		sql.append("\r\n UNION ALL");
		sql.append("\r\n select tt.ORGANID oid,tt.WAREHOUSEID,ttd.INPRODUCTID pid ,INQUANTITY quantity, 0, to_char(tt.auditdate, 'yyyymm'), 0,0, 0 ");
		sql.append("\r\n from PACK_SEPARATE tt INNER JOIN PACK_SEPARATE_DETAIL ttd on tt.id = ttd.psid  and tt.isaudit = 1  ");
		sql.append("\r\n and tt.auditdate < to_date('"+dateTime+"','yyyy-MM-dd hh24:mi:ss') ");
		
		//退回工厂
		sql.append("\r\n --退回工厂");
		sql.append("\r\n UNION ALL");
		
		sql.append("\r\n select tt.oid oid,tt.warehouseid, ttd.productid pid ,-ttd.realquantity * f.xquantity quantity , 0, to_char(tt.auditdate, 'yyyymm'),0 ,0, 0 ");
		sql.append("\r\n from TAKE_TICKET tt INNER JOIN TAKE_TICKET_DETAIL ttd on tt.id = ttd.ttid  and tt.isaudit = 1  and tt.bsort = 7 and tt.billno like 'PW%'  ");
		sql.append("\r\n and tt.auditdate < to_date('"+dateTime+"','yyyy-MM-dd hh24:mi:ss') ");
		sql.append("\r\n INNER JOIN ORGAN o on tt.oid = o.id and o.organtype = 2 and  organmodel = 1  ");
		sql.append("\r\n LEFT JOIN F_UNIT f on ttd.productid = f.productid and ttd.unitid = f.funitid  ");
		
		
		//消耗
		sql.append("\r\n --消耗");
		sql.append("\r\n UNION ALL");
		
		sql.append("\r\n select tt.outorganid oid, tt.outwarehouseid wid, ttd.productid pid, 0, (ttd.takequantity * f.xquantity) consumvolume,to_char(tt.shipmentdate, 'yyyymm'), 0, 0, 0 ");
		sql.append("\r\n from STOCK_ALTER_MOVE tt INNER JOIN STOCK_ALTER_MOVE_DETAIL ttd on tt.id = ttd.samid  and tt.isaudit = 1 and tt.isshipment = 1 and tt.isblankout = 0 ");
		sql.append("\r\n and tt.shipmentdate < to_date('"+dateTime+"','yyyy-MM-dd hh24:mi:ss') ");
		sql.append("\r\n INNER JOIN ORGAN o on tt.outorganid = o.id and o.organtype = 2 and  organmodel = 1   ");
		sql.append("\r\n LEFT JOIN F_UNIT f on ttd.productid = f.productid and ttd.unitid = f.funitid ");
		sql.append("\r\n where (ttd.takequantity  * f.xquantity) <> 0  ");
		
		
		//分包时的损耗记到消耗
		sql.append("\r\n --分包时的损耗记到消耗");
		sql.append("\r\n UNION ALL");
		
		sql.append("\r\n select tt.ORGANID, tt.WAREHOUSEID, ttd.OUTPRODUCTID pid,0, ttd.wastage quantity ,to_char(tt.auditdate, 'yyyymm'), 0, 0, 0");
		sql.append("\r\n from PACK_SEPARATE tt INNER JOIN PACK_SEPARATE_DETAIL ttd on tt.id = ttd.psid  and tt.isaudit = 1  ");
		sql.append("\r\n where ttd.wastage <> 0 ");
		sql.append("\r\n and tt.auditdate < to_date('"+dateTime+"','yyyy-MM-dd hh24:mi:ss') ");
		
		//工厂发货给PD, 收货差异计入PD消耗
		sql.append("\r\n --工厂发货给PD, 收货差异计入PD消耗");
		sql.append("\r\n UNION ALL");
		
		sql.append("\r\n select tt.inoid,tt.inwarehouseid, ttd.productid pid,0, ((samd.quantity-samd.receivequantity) * f.xquantity) quantity ,to_char(sam.movedate, 'yyyymm'), 0, 0, 0");
		sql.append("\r\n from TAKE_TICKET tt INNER JOIN TAKE_TICKET_DETAIL ttd on tt.id = ttd.ttid  and tt.isaudit = 1  and tt.bsort = 1   ");
		sql.append("\r\n join STOCK_ALTER_MOVE sam on sam.id=tt.billno and sam.iscomplete =1  ");
//		sql.append("\r\n and sam.receivedate < to_date('"+dateTime+"','yyyy-MM-dd hh24:mi:ss') ");
		sql.append("\r\n and sam.movedate < to_date('"+dateTime+"','yyyy-MM-dd hh24:mi:ss') ");
		sql.append("\r\n join STOCK_ALTER_MOVE_DETAIL samd on samd.samid = sam.id and samd.productid=ttd.productid  ");
		sql.append("\r\n INNER JOIN ORGAN o on tt.oid = o.id and o.organtype = 1  ");
		sql.append("\r\n LEFT JOIN F_UNIT f on ttd.productid = f.productid and ttd.unitid = f.funitid  ");
		sql.append("\r\n LEFT JOIN ORGAN ino on tt.inoid = ino.id  and ino.organtype = 2 and ino.organmodel = 1  ");
		sql.append("\r\n where (samd.quantity-samd.receivequantity) > 0  ");
		
		
		//经销商退货
		sql.append("\r\n --经销商退货");
		sql.append("\r\n UNION ALL");
		
		sql.append("\r\n select tt.inoid oid,tt.inwarehouseid,ttd.productid pid,0,-ttd.realquantity * f.xquantity quantity ,to_char(tt.auditdate, 'yyyymm'), 0, 0, 0");
		sql.append("\r\n from TAKE_TICKET tt INNER JOIN TAKE_TICKET_DETAIL ttd on tt.id = ttd.ttid  and tt.isaudit = 1  and tt.bsort = 7 and tt.billno like 'OW%' ");
		sql.append("\r\n and tt.auditdate < to_date('"+dateTime+"','yyyy-MM-dd hh24:mi:ss') ");
		sql.append("\r\n INNER JOIN ORGAN o on tt.inoid = o.id and o.organtype = 2 and  organmodel = 1  ");
		sql.append("\r\n JOIN F_UNIT f on ttd.productid = f.productid and ttd.unitid = f.funitid  ");
		
		
		
		
		//其他入库-减消耗-20150429
		sql.append("\r\n --其他入库-减消耗");
		sql.append(" \r\n UNION ALL ");
		sql.append(" \r\n select oia.organid, oia.WAREHOUSEID, oida.productid pid,0,0,to_char(oia.auditdate, 'yyyymm'), 0,0, -(oida.quantity * f.xquantity) ");
		sql.append(" \r\n from OTHER_INCOME_ALL oia INNER JOIN OTHER_INCOME_DETAIL_ALL oida on oia.id = oida.oiid and oia.isaudit = 1 and oia.incomesort <> 6");
		sql.append(" \r\n and oia.auditdate < to_date('" + dateTime + "','yyyy-MM-dd hh24:mi:ss') "); //结束时间条件
		sql.append(" \r\n INNER JOIN ORGAN o on oia.organid= o.id and o.organtype = 2 and  organmodel = 1 ");
		sql.append(" \r\n JOIN F_UNIT f on oida.productid = f.productid and oida.unitid = f.funitid  "); 
		
		//其他出库-加消耗-20150429
		sql.append("\r\n --其他出库-加消耗");
		sql.append(" \r\n UNION ALL ");
		sql.append(" \r\n select oia.organid,oia.WAREHOUSEID, oida.productid pid,0,0,to_char(oia.auditdate, 'yyyymm'), 0,0, (oida.quantity * f.xquantity) ");
		sql.append(" \r\n from OTHER_SHIPMENT_BILL_ALL oia INNER JOIN OTHER_SHIPMENT_BILL_D_ALL oida on oia.id = oida.osid and oia.isaudit = 1 ");
		sql.append(" \r\n and oia.auditdate < to_date('" + dateTime + "','yyyy-MM-dd hh24:mi:ss') "); //结束时间条件
		sql.append(" \r\n INNER JOIN ORGAN o on oia.organid= o.id and o.organtype = 2 and  organmodel = 1 ");
		sql.append(" \r\n JOIN F_UNIT f on oida.productid = f.productid and oida.unitid = f.funitid  "); 
		
		sql.append("\r\n  ) where pid not in (select id from product where useflag = 0) ");
		sql.append(" \r\n GROUP BY oid, wid, pid , mydate ORDER BY oid,wid,pid asc ");
		
		return EntityManager.jdbcquery(sql.toString());
	}
	
	
	public List<Map<String,String>> getAllSalesConsumHistoryDataByDate(String startDate, String endDate) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("\r\n select oid as organId, wid as warehouseId, pid as productId, sum(salesvolume) salesVolume, mydate, sum(salesvalue) salesValue, sum(salesvolume) salesVolume,sum(consumvolume) as consumVolume, hasinvoice, sum(othervolume) as otherConsumVolume from ( ");
		//工厂给PD
//		sql.append("\r\n --工厂给PD");
		sql.append("\r\n select sam.receiveorganid oid,sam.inwarehouseid wid, samd.productid pid,samd.quantity * f.xquantity as salesvolume, 0 as consumvolume, to_char(movedate, 'yyyymm') as mydate, iv.net_val as salesvalue, 1 as hasinvoice,0 as othervolume from STOCK_ALTER_MOVE sam ");
		sql.append("\r\n INNER JOIN STOCK_ALTER_MOVE_DETAIL samd on sam.id = samd.samid ");
		sql.append("\r\n and sam.isaudit = 1 and sam.isshipment = 1 and sam.isblankout = 0 ");
		sql.append("\r\n and sam.inwarehouseid is not null ");
		sql.append("\r\n and sam.shipmentdate >= to_date('"+startDate+"','yyyy-MM-dd hh24:mi:ss') ");
		sql.append("\r\n and sam.shipmentdate < to_date('"+endDate+"','yyyy-MM-dd hh24:mi:ss') ");
		sql.append("\r\n INNER JOIN ORGAN o on sam.outorganid = o.id and o.organtype = 1 ");
		sql.append("\r\n INNER JOIN ORGAN ino on sam.receiveorganid = ino.id and ino.organtype = 2 and ino.organmodel = 1 ");
		sql.append("\r\n INNER JOIN F_UNIT f on samd.productid = f.productid and samd.unitid = f.funitid  ");
		sql.append("\r\n LEFT JOIN ");
		sql.append("\r\n (select DELIVERY_NUMBER, MATERIAL_CODE, sum(TO_NUMBER(NET_VAL)) as net_val from INVOICE ");
		sql.append("\r\n group by DELIVERY_NUMBER, MATERIAL_CODE ) iv on sam.nccode = iv.DELIVERY_NUMBER and samd.nccode = iv.material_code");
		
		sql.append("\r\n UNION ALL");
		//种子客户单据,签收之后再统计
//		sql.append("\r\n --种子客户单据");
		sql.append("\r\n select sam.receiveorganid oid,sam.inwarehouseid wid, samd.productid pid,samd.quantity * f.xquantity as salesvolume, 0 as consumvolume, to_char(movedate, 'yyyymm') as mydate, iv.net_val as salesvalue, 1 as hasinvoice,0 as othervolume from STOCK_ALTER_MOVE sam ");
		sql.append("\r\n INNER JOIN STOCK_ALTER_MOVE_DETAIL samd on sam.id = samd.samid ");
		sql.append("\r\n and sam.ISCOMPLETE = 1 and sam.isblankout = 0 ");
		sql.append("\r\n and sam.ismove = 1 ");
		sql.append("\r\n and sam.RECEIVEDATE >= to_date('"+startDate+"','yyyy-MM-dd hh24:mi:ss') ");
		sql.append("\r\n and sam.RECEIVEDATE < to_date('"+endDate+"','yyyy-MM-dd hh24:mi:ss') ");
		sql.append("\r\n INNER JOIN ORGAN o on sam.outorganid = o.id and o.organtype = 1 ");
		sql.append("\r\n INNER JOIN ORGAN ino on sam.receiveorganid = ino.id and ino.organtype = 2 and ino.organmodel = 1 ");
		sql.append("\r\n INNER JOIN F_UNIT f on samd.productid = f.productid and samd.unitid = f.funitid  ");
		sql.append("\r\n LEFT JOIN ");
		sql.append("\r\n (select DELIVERY_NUMBER, MATERIAL_CODE, sum(TO_NUMBER(NET_VAL)) as net_val from INVOICE ");
		sql.append("\r\n group by DELIVERY_NUMBER, MATERIAL_CODE ) iv on sam.nccode = iv.DELIVERY_NUMBER and samd.nccode = iv.material_code");
		
		//采购入库-加销售-20150429
		sql.append("\r\n --其他入库-减消耗");
		sql.append(" \r\n UNION ALL ");
		sql.append(" \r\n select oia.organid, oia.WAREHOUSEID, oida.productid pid,0,0,to_char(oia.auditdate, 'yyyymm'), 0,1, -(oida.quantity * f.xquantity) ");
		sql.append(" \r\n from OTHER_INCOME_ALL oia INNER JOIN OTHER_INCOME_DETAIL_ALL oida on oia.id = oida.oiid and oia.isaudit = 1 and INCOMESORT = 6");
		sql.append(" \r\n and oia.auditdate >= to_date('" + startDate + "','yyyy-MM-dd hh24:mi:ss') "); 
		sql.append(" \r\n and oia.auditdate < to_date('" + endDate + "','yyyy-MM-dd hh24:mi:ss') "); 
		
		sql.append(" \r\n INNER JOIN ORGAN o on oia.organid= o.id and o.organtype = 2 and  organmodel = 1 ");
		sql.append(" \r\n JOIN F_UNIT f on oida.productid = f.productid and oida.unitid = f.funitid  "); 
		
		//工厂给PD,作废的单据
		sql.append("\r\n UNION ALL");
		sql.append("\r\n --工厂给PD,作废的单据");
		sql.append("\r\n select sam.receiveorganid oid,sam.inwarehouseid wid, samd.productid pid,-(samd.quantity * f.xquantity) as salesvolume, 0 as consumvolume, to_char(movedate, 'yyyymm') as mydate, -TO_NUMBER(iv.net_val) as salesvalue, 1, 0 from STOCK_ALTER_MOVE sam ");
		sql.append("\r\n INNER JOIN STOCK_ALTER_MOVE_DETAIL samd on sam.id = samd.samid ");
		sql.append("\r\n and sam.isblankout = 1 ");
		sql.append("\r\n and sam.inwarehouseid is not null ");
		sql.append("\r\n and sam.shipmentdate < to_date('"+startDate+"','yyyy-MM-dd hh24:mi:ss') ");
		sql.append("\r\n and sam.blankoutdate >= to_date('"+startDate+"','yyyy-MM-dd hh24:mi:ss') ");
		sql.append("\r\n and sam.blankoutdate < to_date('"+endDate+"','yyyy-MM-dd hh24:mi:ss') ");
		sql.append("\r\n INNER JOIN ORGAN o on sam.outorganid = o.id and o.organtype = 1 ");
		sql.append("\r\n INNER JOIN ORGAN ino on sam.receiveorganid = ino.id and ino.organtype = 2 and ino.organmodel = 1 ");
		sql.append("\r\n INNER JOIN F_UNIT f on samd.productid = f.productid and samd.unitid = f.funitid  ");
		sql.append("\r\n LEFT JOIN INVOICE iv on sam.nccode = iv.DELIVERY_NUMBER and ino.oecode = iv.partn_sold and samd.nccode = iv.material_code and iv.net_val is not NULL ");
		
		
		//PD调拨回来的
		sql.append("\r\n --PD调拨回来的");
		sql.append("\r\n UNION ALL");
		
		sql.append("\r\n select tt.inoid ,tt.inwarehouseid, ttd.productid ,ttd.realquantity * f.xquantity , 0, to_char(tt.auditdate, 'yyyymm') ,0 ,0,0 ");
		sql.append("\r\n from TAKE_TICKET tt INNER JOIN TAKE_TICKET_DETAIL ttd on tt.id = ttd.ttid  and tt.isaudit = 1  and tt.bsort = 2 ");
		sql.append("\r\n and tt.auditdate >= to_date('"+startDate+"','yyyy-MM-dd hh24:mi:ss') ");
		sql.append("\r\n and tt.auditdate < to_date('"+endDate+"','yyyy-MM-dd hh24:mi:ss') ");
		sql.append("\r\n INNER JOIN ORGAN o on tt.oid = o.id and o.organtype = 2 and o.organmodel = 1 ");
		sql.append("\r\n LEFT JOIN F_UNIT f on ttd.productid = f.productid and ttd.unitid = f.funitid ");
		
		//调拨给其他PD
		sql.append("\r\n --调拨给其他PD");
		sql.append("\r\n UNION ALL");
		
		sql.append("\r\n select tt.oid,tt.warehouseid,ttd.productid pid ,-ttd.realquantity * f.xquantity quantity , 0, to_char(tt.auditdate, 'yyyymm') ,0 ,0,0  ");
		sql.append("\r\n from TAKE_TICKET tt INNER JOIN TAKE_TICKET_DETAIL ttd on tt.id = ttd.ttid  and tt.isaudit = 1  and tt.bsort = 2 ");
		sql.append("\r\n and tt.auditdate >= to_date('"+startDate+"','yyyy-MM-dd hh24:mi:ss') ");
		sql.append("\r\n and tt.auditdate < to_date('"+endDate+"','yyyy-MM-dd hh24:mi:ss') ");
		sql.append("\r\n INNER JOIN ORGAN o on tt.inoid = o.id and o.organtype = 2 and  o.organmodel = 1 ");
		sql.append("\r\n LEFT JOIN F_UNIT f on ttd.productid = f.productid and ttd.unitid = f.funitid ");
		
		//扣减分包时大包的销售数量(不包含损耗)
		sql.append("\r\n --扣减分包时大包的销售数量");
		sql.append("\r\n UNION ALL");
		
		sql.append("\r\n select tt.ORGANID oid,tt.WAREHOUSEID, ttd.OUTPRODUCTID pid ,-(OUTQUANTITY-ttd.wastage) quantity , 0, to_char(tt.auditdate, 'yyyymm'), 0,0,0 ");
		sql.append("\r\n from PACK_SEPARATE tt INNER JOIN PACK_SEPARATE_DETAIL ttd on tt.id = ttd.psid  and tt.isaudit = 1  ");
		sql.append("\r\n and tt.auditdate >= to_date('"+startDate+"','yyyy-MM-dd hh24:mi:ss') ");
		sql.append("\r\n and tt.auditdate < to_date('"+endDate+"','yyyy-MM-dd hh24:mi:ss') ");
		
		//增加分包时小包的销售数量
		sql.append("\r\n --增加分包时小包的销售数量");
		sql.append("\r\n UNION ALL");
		sql.append("\r\n select tt.ORGANID oid,tt.WAREHOUSEID,ttd.INPRODUCTID pid ,INQUANTITY quantity, 0, to_char(tt.auditdate, 'yyyymm'), 0,0,0 ");
		sql.append("\r\n from PACK_SEPARATE tt INNER JOIN PACK_SEPARATE_DETAIL ttd on tt.id = ttd.psid  and tt.isaudit = 1  ");
		sql.append("\r\n and tt.auditdate >= to_date('"+startDate+"','yyyy-MM-dd hh24:mi:ss') ");
		sql.append("\r\n and tt.auditdate < to_date('"+endDate+"','yyyy-MM-dd hh24:mi:ss') ");
		
		//退回工厂
		sql.append("\r\n --退回工厂");
		sql.append("\r\n UNION ALL");
		
		sql.append("\r\n select tt.oid oid,tt.warehouseid, ttd.productid pid ,-ttd.realquantity * f.xquantity quantity , 0, to_char(tt.auditdate, 'yyyymm'),0 ,0,0 ");
		sql.append("\r\n from TAKE_TICKET tt INNER JOIN TAKE_TICKET_DETAIL ttd on tt.id = ttd.ttid  and tt.isaudit = 1  and tt.bsort = 7 and tt.billno like 'PW%'  ");
		sql.append("\r\n and tt.auditdate >= to_date('"+startDate+"','yyyy-MM-dd hh24:mi:ss') ");
		sql.append("\r\n and tt.auditdate < to_date('"+endDate+"','yyyy-MM-dd hh24:mi:ss') ");
		sql.append("\r\n INNER JOIN ORGAN o on tt.oid = o.id and o.organtype = 2 and  organmodel = 1  ");
		sql.append("\r\n LEFT JOIN F_UNIT f on ttd.productid = f.productid and ttd.unitid = f.funitid  ");
		
		
		//消耗
		sql.append("\r\n --消耗");
		sql.append("\r\n UNION ALL");
		
		sql.append("\r\n select tt.outorganid oid, tt.outwarehouseid wid, ttd.productid pid, 0, (ttd.takequantity * f.xquantity) consumvolume,to_char(tt.shipmentdate, 'yyyymm'), 0, 1,0 ");
		sql.append("\r\n from STOCK_ALTER_MOVE tt INNER JOIN STOCK_ALTER_MOVE_DETAIL ttd on tt.id = ttd.samid  and tt.isaudit = 1 and tt.isshipment = 1 and tt.isblankout = 0 ");
		sql.append("\r\n and tt.shipmentdate >= to_date('"+startDate+"','yyyy-MM-dd hh24:mi:ss') ");
		sql.append("\r\n and tt.shipmentdate < to_date('"+endDate+"','yyyy-MM-dd hh24:mi:ss') ");
		sql.append("\r\n INNER JOIN ORGAN o on tt.outorganid = o.id and o.organtype = 2 and  organmodel = 1   ");
		sql.append("\r\n LEFT JOIN F_UNIT f on ttd.productid = f.productid and ttd.unitid = f.funitid ");
		sql.append("\r\n where (ttd.takequantity  * f.xquantity) <> 0  ");
		
		
		//分包时的损耗记到消耗
		sql.append("\r\n --分包时的损耗记到消耗");
		sql.append("\r\n UNION ALL");
		
		sql.append("\r\n select tt.ORGANID, tt.WAREHOUSEID, ttd.OUTPRODUCTID pid,0, ttd.wastage quantity ,to_char(tt.auditdate, 'yyyymm'), 0, 1,0");
		sql.append("\r\n from PACK_SEPARATE tt INNER JOIN PACK_SEPARATE_DETAIL ttd on tt.id = ttd.psid  and tt.isaudit = 1  ");
		sql.append("\r\n where ttd.wastage <> 0 ");
		sql.append("\r\n and tt.auditdate >= to_date('"+startDate+"','yyyy-MM-dd hh24:mi:ss') ");
		sql.append("\r\n and tt.auditdate < to_date('"+endDate+"','yyyy-MM-dd hh24:mi:ss') ");
		
		//工厂发货给PD, 收货差异计入PD消耗
		sql.append("\r\n --工厂发货给PD, 收货差异计入PD消耗");
		sql.append("\r\n UNION ALL");
		
		sql.append("\r\n select tt.inoid,tt.inwarehouseid, ttd.productid pid,0, ((samd.quantity-samd.receivequantity) * f.xquantity) quantity ,to_char(sam.movedate, 'yyyymm'), 0,1,0");
		sql.append("\r\n from TAKE_TICKET tt INNER JOIN TAKE_TICKET_DETAIL ttd on tt.id = ttd.ttid  and tt.isaudit = 1  and tt.bsort = 1   ");
		sql.append("\r\n join STOCK_ALTER_MOVE sam on sam.id=tt.billno and sam.iscomplete =1  ");
		sql.append("\r\n and sam.movedate >= to_date('"+startDate+"','yyyy-MM-dd hh24:mi:ss') ");
		sql.append("\r\n and sam.movedate < to_date('"+endDate+"','yyyy-MM-dd hh24:mi:ss') ");
		sql.append("\r\n join STOCK_ALTER_MOVE_DETAIL samd on samd.samid = sam.id and samd.productid=ttd.productid  ");
		sql.append("\r\n INNER JOIN ORGAN o on tt.oid = o.id and o.organtype = 1  ");
		sql.append("\r\n LEFT JOIN F_UNIT f on ttd.productid = f.productid and ttd.unitid = f.funitid  ");
		sql.append("\r\n LEFT JOIN ORGAN ino on tt.inoid = ino.id  and ino.organtype = 2 and ino.organmodel = 1  ");
		sql.append("\r\n where (samd.quantity-samd.receivequantity) > 0  ");
		
		
		//经销商退货
		sql.append("\r\n --经销商退货");
		sql.append("\r\n UNION ALL");
		
		sql.append("\r\n select tt.inoid oid,tt.inwarehouseid,ttd.productid pid,0,-ttd.realquantity * f.xquantity quantity ,to_char(tt.auditdate, 'yyyymm'), 0, 1,0");
		sql.append("\r\n from TAKE_TICKET tt INNER JOIN TAKE_TICKET_DETAIL ttd on tt.id = ttd.ttid  and tt.isaudit = 1  and tt.bsort = 7 and tt.billno like 'OW%' ");
		sql.append("\r\n and tt.auditdate >= to_date('"+startDate+"','yyyy-MM-dd hh24:mi:ss') ");
		sql.append("\r\n and tt.auditdate < to_date('"+endDate+"','yyyy-MM-dd hh24:mi:ss') ");
		sql.append("\r\n INNER JOIN ORGAN o on tt.inoid = o.id and o.organtype = 2 and  organmodel = 1  ");
		sql.append("\r\n LEFT JOIN F_UNIT f on ttd.productid = f.productid and ttd.unitid = f.funitid  ");
		
		//其他入库-减消耗-20150429
		sql.append("\r\n --其他入库-减消耗");
		sql.append(" \r\n UNION ALL ");
		sql.append(" \r\n select oia.organid, oia.WAREHOUSEID, oida.productid pid,0,0,to_char(oia.auditdate, 'yyyymm'), 0,1, -(oida.quantity * f.xquantity) ");
		sql.append(" \r\n from OTHER_INCOME_ALL oia INNER JOIN OTHER_INCOME_DETAIL_ALL oida on oia.id = oida.oiid and oia.isaudit = 1 and INCOMESORT <> 6");
		sql.append(" \r\n and oia.auditdate >= to_date('" + startDate + "','yyyy-MM-dd hh24:mi:ss') "); 
		sql.append(" \r\n and oia.auditdate < to_date('" + endDate + "','yyyy-MM-dd hh24:mi:ss') "); 
		
		sql.append(" \r\n INNER JOIN ORGAN o on oia.organid= o.id and o.organtype = 2 and  organmodel = 1 ");
		sql.append(" \r\n JOIN F_UNIT f on oida.productid = f.productid and oida.unitid = f.funitid  "); 
		
		//其他出库-加消耗-20150429
		sql.append("\r\n --其他出库-加消耗");
		sql.append(" \r\n UNION ALL ");
		sql.append(" \r\n select oia.organid,oia.WAREHOUSEID, oida.productid pid,0,0,to_char(oia.auditdate, 'yyyymm'), 0,1, (oida.quantity * f.xquantity) ");
		sql.append(" \r\n from OTHER_SHIPMENT_BILL_ALL oia INNER JOIN OTHER_SHIPMENT_BILL_D_ALL oida on oia.id = oida.osid and oia.isaudit = 1 ");
		sql.append(" \r\n and oia.auditdate >= to_date('" + startDate + "','yyyy-MM-dd hh24:mi:ss') "); 
		sql.append(" \r\n and oia.auditdate < to_date('" + endDate + "','yyyy-MM-dd hh24:mi:ss') ");
		sql.append(" \r\n INNER JOIN ORGAN o on oia.organid= o.id and o.organtype = 2 and  organmodel = 1 ");
		sql.append(" \r\n JOIN F_UNIT f on oida.productid = f.productid and oida.unitid = f.funitid  "); 
		
		sql.append("\r\n ) where pid not in (select id from product where useflag = 0) GROUP BY oid, wid, pid , mydate, hasinvoice ORDER BY hasinvoice desc ");
//		System.out.println(sql.toString());
		return EntityManager.jdbcquery(sql.toString());
	}
	
//	public double getInventoryByDate(String pid, String wid, Integer year, Integer month) throws HibernateException, SQLException {
//		String endDate = getInventoryEndDate(year, month);
//		StringBuffer sql = new StringBuffer();
//		
//		sql.append(" \r\n select sum(ps.stockpile * pro.boxquantity) as stockpile from (");
//		//历史库存
//		sql.append(" \r\n select (ps.stockpile * f.xquantity) as stockpile from PRODUCT_STOCKPILE_ALL ps ");
//		sql.append(" \r\n JOIN F_UNIT f on ps.productid = f.productid and ps.countunit = f.funitid");
//		sql.append(" \r\n where ps.PRODUCTID = '"+pid+"' and ps.WAREHOUSEID = '"+wid+"'");
//		
//
//		sql.append(" \r\n UNION ALL");
//		//--发货单(入库)
//		sql.append(" \r\n select -(samd.receivequantity * f.xquantity) as stockpile from STOCK_ALTER_MOVE sam ");
//		sql.append(" \r\n join STOCK_ALTER_MOVE_DETAIL samd on sam.id = samd.samid ");
//		sql.append(" \r\n and sam.iscomplete = 1");
//		sql.append(" \r\n and sam.receivedate > to_date('" + endDate + "','yyyy-MM-dd hh24:mi:ss')");
//		sql.append(" \r\n and sam.movedate > to_date('" + endDate + "','yyyy-MM-dd hh24:mi:ss')");
//		sql.append(" \r\n and sam.INWAREHOUSEID = '" + wid + "' "); //仓库条件
//		sql.append(" \r\n and samd.productid = '"+pid+"'");
//		sql.append(" \r\n  LEFT JOIN F_UNIT f on samd.productid = f.productid and samd.unitid = f.funitid  ");
//		
//		sql.append(" \r\n UNION ALL");
//		//--发货单(出库)
//		sql.append(" \r\n select (samd.takequantity * f.xquantity) from STOCK_ALTER_MOVE sam ");
//		sql.append(" \r\n join STOCK_ALTER_MOVE_DETAIL samd on sam.id = samd.samid ");
//		sql.append(" \r\n and sam.isshipment = 1");
//		sql.append(" \r\n and sam.shipmentdate > to_date('" + endDate + "','yyyy-MM-dd hh24:mi:ss')");
//		sql.append(" \r\n and sam.movedate > to_date('" + endDate + "','yyyy-MM-dd hh24:mi:ss')");
//		sql.append(" \r\n and sam.OUTWAREHOUSEID = '" + wid + "' "); //仓库条件
//		sql.append(" \r\n and samd.productid = '"+pid+"' ");
//		sql.append(" \r\n  LEFT JOIN F_UNIT f on samd.productid = f.productid and samd.unitid = f.funitid  ");
//		
//		sql.append(" \r\n UNION ALL");
//		
//		//--发货单(待发货)
//		sql.append(" \r\n select (samd.takequantity * f.xquantity) from STOCK_ALTER_MOVE sam ");
//		sql.append(" \r\n join STOCK_ALTER_MOVE_DETAIL samd on sam.id = samd.samid ");
//		sql.append(" \r\n and sam.isshipment = 1");
//		sql.append(" \r\n and sam.shipmentdate > to_date('" + endDate + "','yyyy-MM-dd hh24:mi:ss')");
//		sql.append(" \r\n and sam.movedate <= to_date('" + endDate + "','yyyy-MM-dd hh24:mi:ss')");
//		sql.append(" \r\n and sam.OUTWAREHOUSEID = '" + wid + "' "); //仓库条件
//		sql.append(" \r\n and samd.productid = '"+pid+"' ");
//		sql.append(" \r\n  LEFT JOIN F_UNIT f on samd.productid = f.productid and samd.unitid = f.funitid  ");
//		
//		
//		sql.append(" \r\n UNION ALL");
//		
//		// --转仓单(入库)
//		sql.append(" \r\n select -(smd.takequantity * f.xquantity) from STOCK_MOVE sm ");
//		sql.append(" \r\n join STOCK_MOVE_DETAIL smd on sm.id = smd.smid  ");
//		sql.append(" \r\n and sm.iscomplete = 1");
//		sql.append(" \r\n and sm.receivedate > to_date('" + endDate + "','yyyy-MM-dd hh24:mi:ss')");
//		sql.append(" \r\n and sm.shipmentdate > to_date('" + endDate + "','yyyy-MM-dd hh24:mi:ss')");
//		sql.append(" \r\n and sm.INWAREHOUSEID = '" + wid + "' "); //仓库条件
//		sql.append(" \r\n and smd.productid = '"+pid+"' ");
//		sql.append(" \r\n  LEFT JOIN F_UNIT f on smd.productid = f.productid and smd.unitid = f.funitid  ");
//		
//		sql.append(" \r\n UNION ALL");
//		//--转仓单(出库)
//		sql.append(" \r\n select (smd.takequantity * f.xquantity)  from STOCK_MOVE sm ");
//		sql.append(" \r\n join STOCK_MOVE_DETAIL smd on sm.id = smd.smid  ");
//		sql.append(" \r\n and sm.isshipment = 1");
//		sql.append(" \r\n and sm.shipmentdate > to_date('" + endDate + "','yyyy-MM-dd hh24:mi:ss')");
//		sql.append(" \r\n and sm.OUTWAREHOUSEID = '" + wid + "' "); //仓库条件
//		sql.append(" \r\n and smd.productid = '"+pid+"' ");
//		sql.append(" \r\n  LEFT JOIN F_UNIT f on smd.productid = f.productid and smd.unitid = f.funitid  ");
//		
//		sql.append(" \r\n UNION ALL");
//		//--退货单(入库)
//		sql.append(" \r\n select -(owd.takequantity * f.xquantity) from ORGAN_WITHDRAW ow  ");
//		sql.append(" \r\n join TAKE_TICKET tt on tt.billno = ow.id ");
//		sql.append(" \r\n and ow.iscomplete = 1");
//		sql.append(" \r\n and tt.auditdate > to_date('" + endDate + "','yyyy-MM-dd hh24:mi:ss')");
//		sql.append(" \r\n and ow.INWAREHOUSEID = '" + wid + "' "); //仓库条件
//		sql.append(" \r\n join ORGAN_WITHDRAW_DETAIL owd on ow.id = owd.owid  ");
//		sql.append(" \r\n and owd.productid = '"+pid+"' ");
//		sql.append(" \r\n  LEFT JOIN F_UNIT f on owd.productid = f.productid and owd.unitid = f.funitid   ");
//		
//		sql.append(" \r\n UNION ALL");
//		//--退货单(出库)
//		sql.append(" \r\n select (owd.takequantity * f.xquantity) from ORGAN_WITHDRAW ow  ");
//		sql.append(" \r\n join TAKE_TICKET tt on tt.billno = ow.id ");
//		sql.append(" \r\n and tt.isaudit = 1");
//		sql.append(" \r\n and tt.auditdate > to_date('" + endDate + "','yyyy-MM-dd hh24:mi:ss')");
//		sql.append(" \r\n and ow.WAREHOUSEID = '" + wid + "' "); //仓库条件
//		sql.append(" \r\n join ORGAN_WITHDRAW_DETAIL owd on ow.id = owd.owid  ");
//		sql.append(" \r\n and owd.productid = '"+pid+"' ");
//		sql.append(" \r\n  LEFT JOIN F_UNIT f on owd.productid = f.productid and owd.unitid = f.funitid   ");
//		
//		sql.append(" \r\n UNION ALL");
//		
//		//-- 未签收的单据（在途库存）
//		// --发货单
//		sql.append(" \r\n select (samd.takequantity * f.xquantity) from STOCK_ALTER_MOVE sam ");
//		sql.append(" \r\n join STOCK_ALTER_MOVE_DETAIL samd on sam.id = samd.samid ");
//		sql.append(" \r\n and sam.isshipment = 1 and sam.isblankout = 0 and sam.iscomplete = 0 ");
//		sql.append(" \r\n and sam.movedate < to_date('" + endDate + "','yyyy-MM-dd hh24:mi:ss')");
//		sql.append(" \r\n and sam.INWAREHOUSEID = '" + wid + "' "); //仓库条件
//		sql.append(" \r\n and samd.productid = '"+pid+"' ");
//		sql.append(" \r\n LEFT JOIN F_UNIT f on samd.productid = f.productid and samd.unitid = f.funitid  ");
//		sql.append(" \r\n UNION ALL");
//		//--转仓单
//		sql.append(" \r\n select (smd.takequantity * f.xquantity) from STOCK_MOVE sm ");
//		sql.append(" \r\n join STOCK_MOVE_DETAIL smd on sm.id = smd.smid  ");
//		sql.append(" \r\n and sm.isshipment = 1 and sm.iscomplete = 0 and sm.isblankout = 0 ");
//		sql.append(" \r\n and sm.shipmentdate < to_date('" + endDate + "','yyyy-MM-dd hh24:mi:ss')");
//		sql.append(" \r\n and sm.INWAREHOUSEID = '" + wid + "' "); //仓库条件
//		sql.append(" \r\n and smd.productid = '"+pid+"' ");
//		sql.append(" \r\n  LEFT JOIN F_UNIT f on smd.productid = f.productid and smd.unitid = f.funitid  ");
//		
//		sql.append(" \r\n UNION ALL");
//		// --退货单
//		sql.append(" \r\n select (owd.takequantity * f.xquantity) from ORGAN_WITHDRAW ow  ");
//		sql.append(" \r\n join TAKE_TICKET tt on tt.billno = ow.id ");
//		sql.append(" \r\n and ow.takestatus = 1 and ow.iscomplete = 0 and ow.isblankout = 0 ");
//		sql.append(" \r\n and tt.auditdate < to_date('" + endDate + "','yyyy-MM-dd hh24:mi:ss')");
//		sql.append(" \r\n and ow.INWAREHOUSEID = '" + wid + "' "); //仓库条件
//		sql.append(" \r\n join ORGAN_WITHDRAW_DETAIL owd on ow.id = owd.owid  ");
//		sql.append(" \r\n and owd.productid = '"+pid+"' ");
//		sql.append(" \r\n  LEFT JOIN F_UNIT f on owd.productid = f.productid and owd.unitid = f.funitid   ");
//		
//		//#start modified by ryan.xi at 20150714
//		//分包入库
//		sql.append(" \r\n UNION ALL");
//		sql.append(" \r\n select -(samd.inquantity) from PACK_SEPARATE sam   ");
//		sql.append(" \r\n join PACK_SEPARATE_DETAIL samd on sam.id = samd.psid ");
//		sql.append(" \r\n and sam.ISAUDIT = 1 ");
//		sql.append(" \r\n and sam.AUDITDATE > to_date('" + endDate + "','yyyy-MM-dd hh24:mi:ss')");
//		sql.append(" \r\n and sam.WAREHOUSEID = '" + wid + "' "); //仓库条件
//		sql.append(" \r\n and samd.inproductid = '"+pid+"' ");
//		
//		//分包出库
//		sql.append(" \r\n UNION ALL");
//		sql.append(" \r\n select samd.outquantity from PACK_SEPARATE sam ");
//		sql.append(" \r\n join PACK_SEPARATE_DETAIL samd on sam.id = samd.psid ");
//		sql.append(" \r\n and sam.ISAUDIT = 1 ");
//		sql.append(" \r\n and sam.AUDITDATE > to_date('" + endDate + "','yyyy-MM-dd hh24:mi:ss')");
//		sql.append(" \r\n and sam.WAREHOUSEID = '" + wid + "' "); //仓库条件
//		sql.append(" \r\n and samd.outproductid = '"+pid+"' ");
//		
//		
//		//分包损耗
//		sql.append(" \r\n UNION ALL");
//		sql.append(" \r\n select samd.wastage from PACK_SEPARATE sam ");
//		sql.append(" \r\n join PACK_SEPARATE_DETAIL samd on sam.id = samd.psid ");
//		sql.append(" \r\n and sam.ISAUDIT = 1 ");
//		sql.append(" \r\n and sam.AUDITDATE > to_date('" + endDate + "','yyyy-MM-dd hh24:mi:ss')");
//		sql.append(" \r\n and sam.WAREHOUSEID = '" + wid + "' "); //仓库条件
//		sql.append(" \r\n and samd.outproductid = '"+pid+"' ");
//				
//		//#start modified by ryan.xi at 20150714
//		
//		//#start modified by ryan.xi at 20150805
//
//		//其他入库-减库存-20150429
//		sql.append(" \r\n UNION ALL ");
//		sql.append(" \r\n select -(oida.quantity * f.xquantity) ");
//		sql.append(" \r\n from OTHER_INCOME_ALL oia INNER JOIN OTHER_INCOME_DETAIL_ALL oida on oia.id = oida.oiid and oia.isaudit = 1 ");
//		sql.append(" \r\n and oia.auditdate > to_date('" + endDate + "','yyyy-MM-dd hh24:mi:ss')");
//		sql.append(" \r\n and oia.warehouseid = '" + wid + "' "); //仓库条件
//		sql.append(" \r\n and oida.productid = '"+pid+"' ");
//		sql.append(" \r\n JOIN F_UNIT f on oida.productid = f.productid and oida.unitid = f.funitid  "); 
//		
//		
//		//其他出库-加库存-20150429
//		sql.append(" \r\n UNION ALL ");
//		sql.append(" \r\n select (oida.quantity * f.xquantity) ");
//		sql.append(" \r\n from OTHER_SHIPMENT_BILL_ALL oia INNER JOIN OTHER_SHIPMENT_BILL_D_ALL oida on oia.id = oida.osid and oia.isaudit = 1 and oia.shipmentsort = 0");
//		sql.append(" \r\n and oia.auditdate > to_date('" + endDate + "','yyyy-MM-dd hh24:mi:ss')");
//		sql.append(" \r\n and oia.warehouseid = '" + wid + "' "); //仓库条件
//		sql.append(" \r\n and oida.productid = '"+pid+"' ");
//		sql.append(" \r\n JOIN F_UNIT f on oida.productid = f.productid and oida.unitid = f.funitid  ) ps"); 
//		sql.append(" \r\n JOIN Product pro on pro.id = '"+pid+"' "); 
//		
//		List<Map<String,String>> stockpileList =  EntityManager.jdbcquery(sql.toString());
//		if(stockpileList != null && stockpileList.size() > 0) {
//			Map<String,String> stockpileMap = stockpileList.get(0);
//			if(stockpileMap != null && !StringUtil.isEmpty(stockpileMap.get("stockpile"))) {
//				return Double.parseDouble(stockpileMap.get("stockpile"));
//			} else {
//				return 0d;
//			}
//		} else {
//			return 0d;
//		}
//		
//	}
	
	/**
	 * 获取当月月末库存
	 * @param productId
	 * @param organId
	 * @param warehouseId
	 * @param year
	 * @param month
	 * @return
	 * @throws HibernateException
	 * @throws SQLException
	 */
//	public double getInventoryByDate(String productId,String organId, String warehouseId, Integer year, Integer month) throws HibernateException, SQLException {
//		String endDate = getInventoryEndDate(year, month);
//		StringBuffer sql = new StringBuffer();
//		
//		sql.append(" \r\n select sum(ps.stockpile * pro.boxquantity) as stockpile from (");
//		//历史库存
//		sql.append(" \r\n select (ps.stockpile * f.xquantity) as stockpile from PRODUCT_STOCKPILE_ALL ps ");
//		sql.append(" \r\n JOIN F_UNIT f on ps.productid = f.productid and ps.countunit = f.funitid");
//		sql.append(" \r\n where ps.PRODUCTID = '"+productId+"' ");
//		if(!StringUtil.isEmpty(warehouseId)) {
//			sql.append(" \r\n  and ps.WAREHOUSEID = '"+warehouseId+"'");
//		} else {
//			sql.append(" \r\n  and ps.WAREHOUSEID in (select id from warehouse where makeorganid = '"+organId+"')");
//		}
//		
//
//		sql.append(" \r\n UNION ALL");
//		//--发货单(入库)
//		sql.append(" \r\n select -(samd.receivequantity * f.xquantity) as stockpile from STOCK_ALTER_MOVE sam ");
//		sql.append(" \r\n join STOCK_ALTER_MOVE_DETAIL samd on sam.id = samd.samid ");
//		sql.append(" \r\n and sam.iscomplete = 1");
//		sql.append(" \r\n and sam.receivedate > to_date('" + endDate + "','yyyy-MM-dd hh24:mi:ss')");
//		sql.append(" \r\n and sam.movedate > to_date('" + endDate + "','yyyy-MM-dd hh24:mi:ss')");
//		if(!StringUtil.isEmpty(warehouseId)) {
//			sql.append(" \r\n  and sam.INWAREHOUSEID = '"+warehouseId+"'");
//		} else {
//			sql.append(" \r\n  and sam.INWAREHOUSEID in (select id from warehouse where makeorganid = '"+organId+"')");
//		}
//		sql.append(" \r\n and samd.productid = '"+productId+"'");
//		sql.append(" \r\n  LEFT JOIN F_UNIT f on samd.productid = f.productid and samd.unitid = f.funitid  ");
//		
//		sql.append(" \r\n UNION ALL");
//		//--发货单(出库)
//		sql.append(" \r\n select (samd.takequantity * f.xquantity) from STOCK_ALTER_MOVE sam ");
//		sql.append(" \r\n join STOCK_ALTER_MOVE_DETAIL samd on sam.id = samd.samid ");
//		sql.append(" \r\n and sam.isshipment = 1");
//		sql.append(" \r\n and sam.shipmentdate > to_date('" + endDate + "','yyyy-MM-dd hh24:mi:ss')");
//		sql.append(" \r\n and sam.movedate > to_date('" + endDate + "','yyyy-MM-dd hh24:mi:ss')");
//		if(!StringUtil.isEmpty(warehouseId)) {
//			sql.append(" \r\n  and sam.OUTWAREHOUSEID = '"+warehouseId+"'");
//		} else {
//			sql.append(" \r\n  and sam.OUTWAREHOUSEID in (select id from warehouse where makeorganid = '"+organId+"')");
//		}
//		sql.append(" \r\n and samd.productid = '"+productId+"' ");
//		sql.append(" \r\n  LEFT JOIN F_UNIT f on samd.productid = f.productid and samd.unitid = f.funitid  ");
//		
//		sql.append(" \r\n UNION ALL");
//		
//		//--发货单(待发货)
//		sql.append(" \r\n select (samd.takequantity * f.xquantity) from STOCK_ALTER_MOVE sam ");
//		sql.append(" \r\n join STOCK_ALTER_MOVE_DETAIL samd on sam.id = samd.samid ");
//		sql.append(" \r\n and sam.isshipment = 1");
//		sql.append(" \r\n and sam.shipmentdate > to_date('" + endDate + "','yyyy-MM-dd hh24:mi:ss')");
//		sql.append(" \r\n and sam.movedate <= to_date('" + endDate + "','yyyy-MM-dd hh24:mi:ss')");
//		if(!StringUtil.isEmpty(warehouseId)) {
//			sql.append(" \r\n  and sam.OUTWAREHOUSEID = '"+warehouseId+"'");
//		} else {
//			sql.append(" \r\n  and sam.OUTWAREHOUSEID in (select id from warehouse where makeorganid = '"+organId+"')");
//		}
//		sql.append(" \r\n and samd.productid = '"+productId+"' ");
//		sql.append(" \r\n  LEFT JOIN F_UNIT f on samd.productid = f.productid and samd.unitid = f.funitid  ");
//		
//		
//		sql.append(" \r\n UNION ALL");
//		
//		// --转仓单(入库)
//		sql.append(" \r\n select -(smd.takequantity * f.xquantity) from STOCK_MOVE sm ");
//		sql.append(" \r\n join STOCK_MOVE_DETAIL smd on sm.id = smd.smid  ");
//		sql.append(" \r\n and sm.iscomplete = 1");
//		sql.append(" \r\n and sm.receivedate > to_date('" + endDate + "','yyyy-MM-dd hh24:mi:ss')");
//		sql.append(" \r\n and sm.shipmentdate > to_date('" + endDate + "','yyyy-MM-dd hh24:mi:ss')");
//		if(!StringUtil.isEmpty(warehouseId)) {
//			sql.append(" \r\n  and sm.INWAREHOUSEID = '"+warehouseId+"'");
//		} else {
//			sql.append(" \r\n  and sm.INWAREHOUSEID in (select id from warehouse where makeorganid = '"+organId+"')");
//		}
//		sql.append(" \r\n and smd.productid = '"+productId+"' ");
//		sql.append(" \r\n  LEFT JOIN F_UNIT f on smd.productid = f.productid and smd.unitid = f.funitid  ");
//		
//		sql.append(" \r\n UNION ALL");
//		//--转仓单(出库)
//		sql.append(" \r\n select (smd.takequantity * f.xquantity)  from STOCK_MOVE sm ");
//		sql.append(" \r\n join STOCK_MOVE_DETAIL smd on sm.id = smd.smid  ");
//		sql.append(" \r\n and sm.isshipment = 1");
//		sql.append(" \r\n and sm.shipmentdate > to_date('" + endDate + "','yyyy-MM-dd hh24:mi:ss')");
//		if(!StringUtil.isEmpty(warehouseId)) {
//			sql.append(" \r\n  and sm.OUTWAREHOUSEID = '"+warehouseId+"'");
//		} else {
//			sql.append(" \r\n  and sm.OUTWAREHOUSEID in (select id from warehouse where makeorganid = '"+organId+"')");
//		}
//		sql.append(" \r\n and smd.productid = '"+productId+"' ");
//		sql.append(" \r\n  LEFT JOIN F_UNIT f on smd.productid = f.productid and smd.unitid = f.funitid  ");
//		
//		sql.append(" \r\n UNION ALL");
//		//--退货单(入库)
//		sql.append(" \r\n select -(owd.takequantity * f.xquantity) from ORGAN_WITHDRAW ow  ");
//		sql.append(" \r\n join TAKE_TICKET tt on tt.billno = ow.id ");
//		sql.append(" \r\n and ow.iscomplete = 1");
//		sql.append(" \r\n and tt.auditdate > to_date('" + endDate + "','yyyy-MM-dd hh24:mi:ss')");
//		if(!StringUtil.isEmpty(warehouseId)) {
//			sql.append(" \r\n  and ow.INWAREHOUSEID = '"+warehouseId+"'");
//		} else {
//			sql.append(" \r\n  and ow.INWAREHOUSEID in (select id from warehouse where makeorganid = '"+organId+"')");
//		}
//		sql.append(" \r\n join ORGAN_WITHDRAW_DETAIL owd on ow.id = owd.owid  ");
//		sql.append(" \r\n and owd.productid = '"+productId+"' ");
//		sql.append(" \r\n  LEFT JOIN F_UNIT f on owd.productid = f.productid and owd.unitid = f.funitid   ");
//		
//		sql.append(" \r\n UNION ALL");
//		//--退货单(出库)
//		sql.append(" \r\n select (owd.takequantity * f.xquantity) from ORGAN_WITHDRAW ow  ");
//		sql.append(" \r\n join TAKE_TICKET tt on tt.billno = ow.id ");
//		sql.append(" \r\n and tt.isaudit = 1");
//		sql.append(" \r\n and tt.auditdate > to_date('" + endDate + "','yyyy-MM-dd hh24:mi:ss')");
//		if(!StringUtil.isEmpty(warehouseId)) {
//			sql.append(" \r\n  and ow.WAREHOUSEID = '"+warehouseId+"'");
//		} else {
//			sql.append(" \r\n  and ow.WAREHOUSEID in (select id from warehouse where makeorganid = '"+organId+"')");
//		}
//		sql.append(" \r\n join ORGAN_WITHDRAW_DETAIL owd on ow.id = owd.owid  ");
//		sql.append(" \r\n and owd.productid = '"+productId+"' ");
//		sql.append(" \r\n  LEFT JOIN F_UNIT f on owd.productid = f.productid and owd.unitid = f.funitid   ");
//		
//		sql.append(" \r\n UNION ALL");
//		
//		//-- 未签收的单据（在途库存）
//		// --发货单
//		sql.append(" \r\n select (samd.takequantity * f.xquantity) from STOCK_ALTER_MOVE sam ");
//		sql.append(" \r\n join STOCK_ALTER_MOVE_DETAIL samd on sam.id = samd.samid ");
//		sql.append(" \r\n and sam.isshipment = 1 and sam.isblankout = 0 and sam.iscomplete = 0 ");
//		sql.append(" \r\n and sam.movedate < to_date('" + endDate + "','yyyy-MM-dd hh24:mi:ss')");
//		if(!StringUtil.isEmpty(warehouseId)) {
//			sql.append(" \r\n  and sam.INWAREHOUSEID = '"+warehouseId+"'");
//		} else {
//			sql.append(" \r\n  and sam.INWAREHOUSEID in (select id from warehouse where makeorganid = '"+organId+"')");
//		}
//		sql.append(" \r\n and samd.productid = '"+productId+"' ");
//		sql.append(" \r\n LEFT JOIN F_UNIT f on samd.productid = f.productid and samd.unitid = f.funitid  ");
//		sql.append(" \r\n UNION ALL");
//		//--转仓单
//		sql.append(" \r\n select (smd.takequantity * f.xquantity) from STOCK_MOVE sm ");
//		sql.append(" \r\n join STOCK_MOVE_DETAIL smd on sm.id = smd.smid  ");
//		sql.append(" \r\n and sm.isshipment = 1 and sm.iscomplete = 0 and sm.isblankout = 0 ");
//		sql.append(" \r\n and sm.shipmentdate < to_date('" + endDate + "','yyyy-MM-dd hh24:mi:ss')");
//		if(!StringUtil.isEmpty(warehouseId)) {
//			sql.append(" \r\n  and sm.INWAREHOUSEID = '"+warehouseId+"'");
//		} else {
//			sql.append(" \r\n  and sm.INWAREHOUSEID in (select id from warehouse where makeorganid = '"+organId+"')");
//		}
//		sql.append(" \r\n and smd.productid = '"+productId+"' ");
//		sql.append(" \r\n  LEFT JOIN F_UNIT f on smd.productid = f.productid and smd.unitid = f.funitid  ");
//		
//		sql.append(" \r\n UNION ALL");
//		// --退货单
//		sql.append(" \r\n select (owd.takequantity * f.xquantity) from ORGAN_WITHDRAW ow  ");
//		sql.append(" \r\n join TAKE_TICKET tt on tt.billno = ow.id ");
//		sql.append(" \r\n and ow.takestatus = 1 and ow.iscomplete = 0 and ow.isblankout = 0 ");
//		sql.append(" \r\n and tt.auditdate < to_date('" + endDate + "','yyyy-MM-dd hh24:mi:ss')");
//		if(!StringUtil.isEmpty(warehouseId)) {
//			sql.append(" \r\n  and ow.INWAREHOUSEID = '"+warehouseId+"'");
//		} else {
//			sql.append(" \r\n  and ow.INWAREHOUSEID in (select id from warehouse where makeorganid = '"+organId+"')");
//		}
//		sql.append(" \r\n join ORGAN_WITHDRAW_DETAIL owd on ow.id = owd.owid  ");
//		sql.append(" \r\n and owd.productid = '"+productId+"' ");
//		sql.append(" \r\n  LEFT JOIN F_UNIT f on owd.productid = f.productid and owd.unitid = f.funitid   ");
//		
//		//#start modified by ryan.xi at 20150714
//		//分包入库
//		sql.append(" \r\n UNION ALL");
//		sql.append(" \r\n select -(samd.inquantity) from PACK_SEPARATE sam   ");
//		sql.append(" \r\n join PACK_SEPARATE_DETAIL samd on sam.id = samd.psid ");
//		sql.append(" \r\n and sam.ISAUDIT = 1 ");
//		sql.append(" \r\n and sam.AUDITDATE > to_date('" + endDate + "','yyyy-MM-dd hh24:mi:ss')");
//		if(!StringUtil.isEmpty(warehouseId)) {
//			sql.append(" \r\n  and sam.WAREHOUSEID = '"+warehouseId+"'");
//		} else {
//			sql.append(" \r\n  and sam.WAREHOUSEID in (select id from warehouse where makeorganid = '"+organId+"')");
//		}
//		sql.append(" \r\n and samd.inproductid = '"+productId+"' ");
//		
//		//分包出库
//		sql.append(" \r\n UNION ALL");
//		sql.append(" \r\n select samd.outquantity from PACK_SEPARATE sam ");
//		sql.append(" \r\n join PACK_SEPARATE_DETAIL samd on sam.id = samd.psid ");
//		sql.append(" \r\n and sam.ISAUDIT = 1 ");
//		sql.append(" \r\n and sam.AUDITDATE > to_date('" + endDate + "','yyyy-MM-dd hh24:mi:ss')");
//		if(!StringUtil.isEmpty(warehouseId)) {
//			sql.append(" \r\n  and sam.WAREHOUSEID = '"+warehouseId+"'");
//		} else {
//			sql.append(" \r\n  and sam.WAREHOUSEID in (select id from warehouse where makeorganid = '"+organId+"')");
//		}
//		sql.append(" \r\n and samd.outproductid = '"+productId+"' ");
//		
//		
//		//分包损耗
//		sql.append(" \r\n UNION ALL");
//		sql.append(" \r\n select samd.wastage from PACK_SEPARATE sam ");
//		sql.append(" \r\n join PACK_SEPARATE_DETAIL samd on sam.id = samd.psid ");
//		sql.append(" \r\n and sam.ISAUDIT = 1 ");
//		sql.append(" \r\n and sam.AUDITDATE > to_date('" + endDate + "','yyyy-MM-dd hh24:mi:ss')");
//		if(!StringUtil.isEmpty(warehouseId)) {
//			sql.append(" \r\n  and sam.WAREHOUSEID = '"+warehouseId+"'");
//		} else {
//			sql.append(" \r\n  and sam.WAREHOUSEID in (select id from warehouse where makeorganid = '"+organId+"')");
//		}
//		sql.append(" \r\n and samd.outproductid = '"+productId+"' ");
//				
//		//#start modified by ryan.xi at 20150714
//		
//		//#start modified by ryan.xi at 20150805
//
//		//其他入库-减库存-20150429
//		sql.append(" \r\n UNION ALL ");
//		sql.append(" \r\n select -(oida.quantity * f.xquantity) ");
//		sql.append(" \r\n from OTHER_INCOME_ALL oia INNER JOIN OTHER_INCOME_DETAIL_ALL oida on oia.id = oida.oiid and oia.isaudit = 1 ");
//		sql.append(" \r\n and oia.auditdate > to_date('" + endDate + "','yyyy-MM-dd hh24:mi:ss')");
//		if(!StringUtil.isEmpty(warehouseId)) {
//			sql.append(" \r\n  and oia.warehouseid = '"+warehouseId+"'");
//		} else {
//			sql.append(" \r\n  and oia.warehouseid in (select id from warehouse where makeorganid = '"+organId+"')");
//		}
//		sql.append(" \r\n and oida.productid = '"+productId+"' ");
//		sql.append(" \r\n JOIN F_UNIT f on oida.productid = f.productid and oida.unitid = f.funitid  "); 
//		
//		
//		//其他出库-加库存-20150429
//		sql.append(" \r\n UNION ALL ");
//		sql.append(" \r\n select (oida.quantity * f.xquantity) ");
//		sql.append(" \r\n from OTHER_SHIPMENT_BILL_ALL oia INNER JOIN OTHER_SHIPMENT_BILL_D_ALL oida on oia.id = oida.osid and oia.isaudit = 1 and oia.shipmentsort = 0");
//		sql.append(" \r\n and oia.auditdate > to_date('" + endDate + "','yyyy-MM-dd hh24:mi:ss')");
//		if(!StringUtil.isEmpty(warehouseId)) {
//			sql.append(" \r\n  and oia.warehouseid = '"+warehouseId+"'");
//		} else {
//			sql.append(" \r\n  and oia.warehouseid in (select id from warehouse where makeorganid = '"+organId+"')");
//		}
//		sql.append(" \r\n and oida.productid = '"+productId+"' ");
//		sql.append(" \r\n JOIN F_UNIT f on oida.productid = f.productid and oida.unitid = f.funitid  ) ps"); 
//		sql.append(" \r\n JOIN Product pro on pro.id = '"+productId+"' "); 
//		
//		List<Map<String,String>> stockpileList =  EntityManager.jdbcquery(sql.toString());
//		if(stockpileList != null && stockpileList.size() > 0) {
//			Map<String,String> stockpileMap = stockpileList.get(0);
//			if(stockpileMap != null && !StringUtil.isEmpty(stockpileMap.get("stockpile"))) {
//				return Double.parseDouble(stockpileMap.get("stockpile"));
//			} else {
//				return 0d;
//			}
//		} else {
//			return 0d;
//		}
//		
//	}
	
	public Map<String, Double> getMonthBeginInventoryMap(String productName, String packSizeName, String region, String organId, String warehouseId, Calendar inventoryDate, String fromDate, String toDate,UsersBean users) throws HibernateException, SQLException {
		inventoryDate.set(Calendar.DAY_OF_MONTH, 0);
		String endDate = DateUtil.formatDate(inventoryDate.getTime()) + " 23:59:59";
		return getInventoryMapByDate(productName, packSizeName, region, organId, warehouseId, endDate, fromDate, toDate, users);
	}
	
	public Map<String, Double> getMonthEndInventoryMap(String productName, String packSizeName, String region, String organId, String warehouseId, Calendar inventoryDate, String fromDate, String toDate, UsersBean users) throws HibernateException, SQLException {
		inventoryDate.set(Calendar.DAY_OF_MONTH, inventoryDate.getActualMaximum(Calendar.DAY_OF_MONTH));
		String endDate = DateUtil.formatDate(inventoryDate.getTime()) + " 23:59:59";
		return getInventoryMapByDate(productName, packSizeName, region, organId, warehouseId, endDate, fromDate, toDate, users);
	}

	/**
	 * 获取当月月末库存MAP(计量单位)
	 * @param productId
	 * @param organId
	 * @param warehouseId
	 * @param year
	 * @param month
	 * @return
	 * @throws HibernateException
	 * @throws SQLException
	 */
	public Map<String, Double> getInventoryMapByDate(String productName, String packSizeName, String region, String organId, String warehouseId, String endDate, String fromDate, String toDate, UsersBean users) throws HibernateException, SQLException {
		StringBuffer sql = new StringBuffer();
		sql.append(" \r\n select ps.productId, ps.organId, ps.warehouseId, sum(ps.stockpile * pro.boxquantity) as stockpile from (");
		sql.append(getInventorySql(productName, packSizeName, region, organId, warehouseId, endDate, users));
//		StringBuffer tempSql = new StringBuffer();
//		
//		tempSql.append("\r\n select organid, productid,warehouseid from SALES_CONSUM_HISTORY where 1=1 ");
//		tempSql.append("\r\n and (year || case when month < 10 THEN '0'||month ELSE to_char(month) END) >= '"+fromDate+"'");
//		tempSql.append("\r\n and (year || case when month < 10 THEN '0'||month ELSE to_char(month) END) <= '"+toDate+"'");
//		
//		if(!StringUtil.isEmpty(productName) && !StringUtil.isEmpty(packSizeName)) { //产品条件
//			tempSql.append(" \r\n and productid in (select id from product where productname = '"+productName+"' and specmode= '"+packSizeName+"') ");
//		} else if(!StringUtil.isEmpty(productName) && StringUtil.isEmpty(packSizeName)) { //产品条件
//			tempSql.append(" \r\n and productid in (select id from product where productname = '"+productName+"') ");
//		}
//		if(!StringUtil.isEmpty(organId)) {
//			tempSql.append(" \r\n  and organId = '"+organId+"'");
//		}
//		if(!StringUtil.isEmpty(warehouseId)) {
//			tempSql.append(" \r\n  and WAREHOUSEID = '"+warehouseId+"'");
//		}
//		if(!StringUtil.isEmpty(region)) {
//			tempSql.append(" \r\n join ORGAN o on o.id = organid");
//			tempSql.append(" \r\n and o.province is not null and o.province in (select areaid from region_area where regioncodeid =  '" + region + "')"); //大区条件 
//		}
//		tempSql.append(" \r\n GROUP BY organid,productid,warehouseid ");
//		
//		
//		sql.append(" \r\n select ps.productId, ps.organId, ps.warehouseId, sum(ps.stockpile * pro.boxquantity) as stockpile from (");
//		//历史库存
//		sql.append(" \r\n select sch.productId, sch.organId, sch.warehouseId, ps.stockpile as stockpile from PRODUCT_STOCKPILE_ALL ps ");
//		sql.append("\r\n JOIN (");
//		sql.append(tempSql);
//		sql.append(" \r\n ) sch on ps.productid = sch.productid and ps.warehouseid = sch.warehouseid ");
//		
//		
//		
//
//		sql.append(" \r\n UNION ALL");
//		//--发货单(入库)
//		sql.append(" \r\n --发货单(入库) ");
//		sql.append(" \r\n select sch.productId, sch.organId, sch.warehouseId,-(samd.receivequantity * f.xquantity) as stockpile from STOCK_ALTER_MOVE sam ");
//		sql.append(" \r\n join STOCK_ALTER_MOVE_DETAIL samd on sam.id = samd.samid ");
//		sql.append(" \r\n and sam.iscomplete = 1");
//		sql.append(" \r\n and sam.receivedate > to_date('" + endDate + "','yyyy-MM-dd hh24:mi:ss')");
//		sql.append(" \r\n and sam.movedate > to_date('" + endDate + "','yyyy-MM-dd hh24:mi:ss')");
//		sql.append("\r\n JOIN (");
//		sql.append(tempSql);
//		sql.append(" \r\n ) sch on samd.productid = sch.productid and sam.INWAREHOUSEID = sch.warehouseid ");
//		sql.append(" \r\n  LEFT JOIN F_UNIT f on samd.productid = f.productid and samd.unitid = f.funitid  ");
//		
//		sql.append(" \r\n UNION ALL");
//		//--发货单(出库)
//		sql.append(" \r\n --发货单(出库) ");
//		sql.append(" \r\n select sch.productId, sch.organId, sch.warehouseId,(samd.takequantity * f.xquantity) from STOCK_ALTER_MOVE sam ");
//		sql.append(" \r\n join STOCK_ALTER_MOVE_DETAIL samd on sam.id = samd.samid ");
//		sql.append(" \r\n and sam.isshipment = 1");
//		sql.append(" \r\n and sam.shipmentdate > to_date('" + endDate + "','yyyy-MM-dd hh24:mi:ss')");
//		sql.append(" \r\n and sam.movedate > to_date('" + endDate + "','yyyy-MM-dd hh24:mi:ss')");
//		sql.append("\r\n JOIN (");
//		sql.append(tempSql);
//		sql.append(" \r\n ) sch on samd.productid = sch.productid and sam.OUTWAREHOUSEID = sch.warehouseid ");
//		sql.append(" \r\n  LEFT JOIN F_UNIT f on samd.productid = f.productid and samd.unitid = f.funitid  ");
//		
//		sql.append(" \r\n UNION ALL");
//		
//		//--发货单(待发货)
//		sql.append(" \r\n --发货单(待发货) ");
//		sql.append(" \r\n select sch.productId, sch.organId, sch.warehouseId,(samd.takequantity * f.xquantity) from STOCK_ALTER_MOVE sam ");
//		sql.append(" \r\n join STOCK_ALTER_MOVE_DETAIL samd on sam.id = samd.samid ");
//		sql.append(" \r\n and sam.isshipment = 1");
//		sql.append(" \r\n and sam.shipmentdate > to_date('" + endDate + "','yyyy-MM-dd hh24:mi:ss')");
//		sql.append(" \r\n and sam.movedate <= to_date('" + endDate + "','yyyy-MM-dd hh24:mi:ss')");
//		sql.append("\r\n JOIN (");
//		sql.append(tempSql);
//		sql.append(" \r\n ) sch on samd.productid = sch.productid and sam.OUTWAREHOUSEID = sch.warehouseid ");
//		sql.append(" \r\n  LEFT JOIN F_UNIT f on samd.productid = f.productid and samd.unitid = f.funitid  ");
//		
//		sql.append(" \r\n UNION ALL");
//		
//		// --转仓单(入库)
//		sql.append(" \r\n --转仓单(入库)");
//		sql.append(" \r\n select sch.productId, sch.organId, sch.warehouseId,-(smd.takequantity * f.xquantity) from STOCK_MOVE sm ");
//		sql.append(" \r\n join STOCK_MOVE_DETAIL smd on sm.id = smd.smid  and sm.inwarehouseid <> outwarehouseid");
//		sql.append(" \r\n and sm.iscomplete = 1");
//		sql.append(" \r\n and sm.receivedate > to_date('" + endDate + "','yyyy-MM-dd hh24:mi:ss')");
//		sql.append(" \r\n and sm.shipmentdate > to_date('" + endDate + "','yyyy-MM-dd hh24:mi:ss')");
//		sql.append("\r\n JOIN (");
//		sql.append(tempSql);
//		sql.append(" \r\n ) sch on smd.productid = sch.productid and sm.INWAREHOUSEID = sch.warehouseid ");
//		sql.append(" \r\n  LEFT JOIN F_UNIT f on smd.productid = f.productid and smd.unitid = f.funitid  ");
//		
//		sql.append(" \r\n UNION ALL");
//		//--转仓单(出库)
//		sql.append(" \r\n --转仓单(出库)");
//		sql.append(" \r\n select sch.productId, sch.organId, sch.warehouseId,(smd.takequantity * f.xquantity)  from STOCK_MOVE sm ");
//		sql.append(" \r\n join STOCK_MOVE_DETAIL smd on sm.id = smd.smid  and sm.inwarehouseid <> outwarehouseid");
//		sql.append(" \r\n and sm.isshipment = 1");
//		sql.append(" \r\n and sm.shipmentdate > to_date('" + endDate + "','yyyy-MM-dd hh24:mi:ss')");
//		sql.append("\r\n JOIN (");
//		sql.append(tempSql);
//		sql.append(" \r\n ) sch on smd.productid = sch.productid and sm.OUTWAREHOUSEID = sch.warehouseid ");
//		sql.append(" \r\n  LEFT JOIN F_UNIT f on smd.productid = f.productid and smd.unitid = f.funitid  ");
//		
//		sql.append(" \r\n UNION ALL");
//		//--退货单(入库)
//		sql.append(" \r\n --退货单(入库)");
//		sql.append(" \r\n select sch.productId, sch.organId, sch.warehouseId,-(owd.takequantity * f.xquantity) from ORGAN_WITHDRAW ow  ");
//		sql.append(" \r\n join TAKE_TICKET tt on tt.billno = ow.id ");
//		sql.append(" \r\n and ow.iscomplete = 1");
//		sql.append(" \r\n and tt.auditdate > to_date('" + endDate + "','yyyy-MM-dd hh24:mi:ss')");
//		if(!StringUtil.isEmpty(warehouseId)) {
//			sql.append(" \r\n  and ow.INWAREHOUSEID = '"+warehouseId+"'");
//		} 
//		sql.append(" \r\n join ORGAN_WITHDRAW_DETAIL owd on ow.id = owd.owid  ");
//		sql.append("\r\n JOIN (");
//		sql.append(tempSql);
//		sql.append(" \r\n ) sch on owd.productid = sch.productid and ow.INWAREHOUSEID = sch.warehouseid ");
//		sql.append(" \r\n  LEFT JOIN F_UNIT f on owd.productid = f.productid and owd.unitid = f.funitid   ");
//		
//		sql.append(" \r\n UNION ALL");
//		//--退货单(出库)
//		sql.append(" \r\n --退货单(出库)");
//		sql.append(" \r\n select sch.productId, sch.organId, sch.warehouseId,(owd.takequantity * f.xquantity) from ORGAN_WITHDRAW ow  ");
//		sql.append(" \r\n join TAKE_TICKET tt on tt.billno = ow.id ");
//		sql.append(" \r\n and tt.isaudit = 1");
//		sql.append(" \r\n and tt.auditdate > to_date('" + endDate + "','yyyy-MM-dd hh24:mi:ss')");
//		if(!StringUtil.isEmpty(warehouseId)) {
//			sql.append(" \r\n  and ow.WAREHOUSEID = '"+warehouseId+"'");
//		} 
//		sql.append(" \r\n join ORGAN_WITHDRAW_DETAIL owd on ow.id = owd.owid  ");
//		sql.append("\r\n JOIN (");
//		sql.append(tempSql);
//		sql.append(" \r\n ) sch on owd.productid = sch.productid and ow.WAREHOUSEID = sch.warehouseid ");
//		sql.append(" \r\n  LEFT JOIN F_UNIT f on owd.productid = f.productid and owd.unitid = f.funitid   ");
//		
//		sql.append(" \r\n UNION ALL");
//		
//		//-- 未签收的单据（在途库存）
//		// --发货单
//		sql.append(" \r\n --未签收的单据（在途库存）-发货单");
//		sql.append(" \r\n select sch.productId, sch.organId, sch.warehouseId,(samd.takequantity * f.xquantity) from STOCK_ALTER_MOVE sam ");
//		sql.append(" \r\n join STOCK_ALTER_MOVE_DETAIL samd on sam.id = samd.samid ");
//		sql.append(" \r\n and sam.isshipment = 1 and sam.isblankout = 0 and sam.iscomplete = 0 ");
//		sql.append(" \r\n and sam.movedate < to_date('" + endDate + "','yyyy-MM-dd hh24:mi:ss')");
//		sql.append("\r\n JOIN (");
//		sql.append(tempSql);
//		sql.append(" \r\n ) sch on samd.productid = sch.productid and sam.INWAREHOUSEID = sch.warehouseid ");
//		sql.append(" \r\n LEFT JOIN F_UNIT f on samd.productid = f.productid and samd.unitid = f.funitid  ");
//		
//		sql.append(" \r\n UNION ALL");
//		//--转仓单
//		sql.append(" \r\n --未签收的单据（在途库存）--转仓单");
//		sql.append(" \r\n select sch.productId, sch.organId, sch.warehouseId,(smd.takequantity * f.xquantity) from STOCK_MOVE sm ");
//		sql.append(" \r\n join STOCK_MOVE_DETAIL smd on sm.id = smd.smid  and sm.inwarehouseid <> outwarehouseid");
//		sql.append(" \r\n and sm.isshipment = 1 and sm.iscomplete = 0 and sm.isblankout = 0 ");
//		sql.append(" \r\n and sm.shipmentdate < to_date('" + endDate + "','yyyy-MM-dd hh24:mi:ss')");
//		sql.append("\r\n JOIN (");
//		sql.append(tempSql);
//		sql.append(" \r\n ) sch on smd.productid = sch.productid and sm.INWAREHOUSEID = sch.warehouseid ");
//		sql.append(" \r\n  LEFT JOIN F_UNIT f on smd.productid = f.productid and smd.unitid = f.funitid  ");
//		
//		sql.append(" \r\n UNION ALL");
//		// --退货单
//		sql.append(" \r\n --未签收的单据（在途库存）--退货单");
//		sql.append(" \r\n select sch.productId, sch.organId, sch.warehouseId,(owd.takequantity * f.xquantity) from ORGAN_WITHDRAW ow  ");
//		sql.append(" \r\n join TAKE_TICKET tt on tt.billno = ow.id ");
//		sql.append(" \r\n and ow.takestatus = 1 and ow.iscomplete = 0 and ow.isblankout = 0 ");
//		sql.append(" \r\n and tt.auditdate < to_date('" + endDate + "','yyyy-MM-dd hh24:mi:ss')");
//		sql.append(" \r\n join ORGAN_WITHDRAW_DETAIL owd on ow.id = owd.owid  ");
//		sql.append("\r\n JOIN (");
//		sql.append(tempSql);
//		sql.append(" \r\n ) sch on owd.productid = sch.productid and ow.INWAREHOUSEID = sch.warehouseid ");
//		sql.append(" \r\n  LEFT JOIN F_UNIT f on owd.productid = f.productid and owd.unitid = f.funitid   ");
//		
//		//#start modified by ryan.xi at 20150714
//		//分包入库
//		sql.append(" \r\n UNION ALL");
//		sql.append(" \r\n --分包入库");
//		sql.append(" \r\n select sch.productId, sch.organId, sch.warehouseId,-(samd.inquantity) from PACK_SEPARATE sam   ");
//		sql.append(" \r\n join PACK_SEPARATE_DETAIL samd on sam.id = samd.psid ");
//		sql.append(" \r\n and sam.ISAUDIT = 1 ");
//		sql.append(" \r\n and sam.AUDITDATE > to_date('" + endDate + "','yyyy-MM-dd hh24:mi:ss')");
//		sql.append("\r\n JOIN (");
//		sql.append(tempSql);
//		sql.append(" \r\n ) sch on samd.inproductid = sch.productid and sam.WAREHOUSEID = sch.warehouseid ");
//		
//		//分包出库
//		sql.append(" \r\n UNION ALL");
//		sql.append(" \r\n --分包出库");
//		sql.append(" \r\n select sch.productId, sch.organId, sch.warehouseId,samd.outquantity from PACK_SEPARATE sam ");
//		sql.append(" \r\n join PACK_SEPARATE_DETAIL samd on sam.id = samd.psid ");
//		sql.append(" \r\n and sam.ISAUDIT = 1 ");
//		sql.append(" \r\n and sam.AUDITDATE > to_date('" + endDate + "','yyyy-MM-dd hh24:mi:ss')");
//		sql.append("\r\n JOIN (");
//		sql.append(tempSql);
//		sql.append(" \r\n ) sch on samd.outproductid = sch.productid and sam.WAREHOUSEID = sch.warehouseid ");
//		
//		/*
//		//分包损耗
//		sql.append(" \r\n UNION ALL");
//		sql.append(" \r\n select sch.productId, sch.organId, sch.warehouseId,samd.wastage from PACK_SEPARATE sam ");
//		sql.append(" \r\n join PACK_SEPARATE_DETAIL samd on sam.id = samd.psid ");
//		sql.append(" \r\n and sam.ISAUDIT = 1 ");
//		sql.append(" \r\n and sam.AUDITDATE > to_date('" + endDate + "','yyyy-MM-dd hh24:mi:ss')");
//		if(!StringUtil.isEmpty(warehouseId)) {
//			sql.append(" \r\n  and sam.WAREHOUSEID = '"+warehouseId+"'");
//		} 
//		sql.append(" \r\n JOIN SALES_CONSUM_HISTORY sch on samd.outproductid = sch.productid and sam.WAREHOUSEID = sch.warehouseid");
//		sql.append("\r\n and (year || case when month < 10 THEN '0'||month ELSE to_char(month) END) >= '"+fromDate+"'");
//		sql.append("\r\n and (year || case when month < 10 THEN '0'||month ELSE to_char(month) END) <= '"+toDate+"'");
//		if(!StringUtil.isEmpty(productName) && !StringUtil.isEmpty(packSizeName)) { //产品条件
//			sql.append(" \r\n and samd.outproductid in (select id from product where productname = '"+productName+"' and specmode= '"+packSizeName+"') ");
//		} else if(!StringUtil.isEmpty(productName) && StringUtil.isEmpty(packSizeName)) { //产品条件
//			sql.append(" \r\n and samd.outproductid in (select id from product where productname = '"+productName+"') ");
//		}
//		if(!StringUtil.isEmpty(organId)) {//机构条件
//			sql.append(" \r\n  and sch.organId = '"+organId+"'");
//		}
//		if(!StringUtil.isEmpty(region)) {//大区条件
//			sql.append(" \r\n join ORGAN o on o.id = sch.organid");
//			sql.append(" \r\n and o.province is not null and o.province in (select areaid from region_area where regioncodeid =  '" + region + "')"); //大区条件 
//		}
//				
//		//#start modified by ryan.xi at 20150714
//		*/
//		//#start modified by ryan.xi at 20150805
//
//		//其他入库-减库存-20150429
//		sql.append(" \r\n UNION ALL ");
//		sql.append(" \r\n --其他入库-减库存-20150429");
//		sql.append(" \r\n select sch.productId, sch.organId, sch.warehouseId,-(oida.quantity * f.xquantity) ");
//		sql.append(" \r\n from OTHER_INCOME_ALL oia INNER JOIN OTHER_INCOME_DETAIL_ALL oida on oia.id = oida.oiid and oia.isaudit = 1 ");
//		sql.append(" \r\n and oia.auditdate > to_date('" + endDate + "','yyyy-MM-dd hh24:mi:ss')");
//		sql.append("\r\n JOIN (");
//		sql.append(tempSql);
//		sql.append(" \r\n ) sch on oida.productid = sch.productid and oia.warehouseid = sch.warehouseid ");
//		sql.append(" \r\n JOIN F_UNIT f on oida.productid = f.productid and oida.unitid = f.funitid  "); 
//		
//		
//		//其他出库-加库存-20150429
//		sql.append(" \r\n UNION ALL ");
//		sql.append(" \r\n --其他出库-加库存-20150429");
//		sql.append(" \r\n select sch.productId, sch.organId, sch.warehouseId,(oida.quantity * f.xquantity) ");
//		sql.append(" \r\n from OTHER_SHIPMENT_BILL_ALL oia INNER JOIN OTHER_SHIPMENT_BILL_D_ALL oida on oia.id = oida.osid and oia.isaudit = 1");
//		sql.append(" \r\n and oia.auditdate > to_date('" + endDate + "','yyyy-MM-dd hh24:mi:ss')");
//		sql.append("\r\n JOIN (");
//		sql.append(tempSql);
//		sql.append(" \r\n ) sch on oida.productid = sch.productid and oia.warehouseid = sch.warehouseid ");
//		sql.append(" \r\n JOIN F_UNIT f on oida.productid = f.productid and oida.unitid = f.funitid  ) ps"); 
//		
//		sql.append(" \r\n JOIN Product pro on pro.id = ps.productId "); 
//		sql.append(" \r\n group by ps.productId, ps.organId, ps.warehouseId "); 
//		
//		System.out.println(sql.toString());
		List<Map<String,String>> stockpileList =  EntityManager.jdbcquery(sql.toString());
		Map<String,Double> stockpileMap = new HashMap<String, Double>();
		
		if(stockpileList != null && stockpileList.size() > 0) {
			for(Map<String,String> map : stockpileList) {
				if(!StringUtil.isEmpty(map.get("stockpile"))) {
					stockpileMap.put(map.get("productid")+"_"+map.get("organid")+"_"+map.get("warehouseid"), Double.parseDouble(map.get("stockpile")));
				} else {
					stockpileMap.put(map.get("productid")+"_"+map.get("organid")+"_"+map.get("warehouseid"),0d);
				}
			}
		} 
		return stockpileMap;
		
	}
	
	public String getInventorySql2(String productName, String packSizeName, String region, String organId, String warehouseId, String endDate, UsersBean users) throws Exception {
		//查看是否有统计过历史数据
		BaseResource inventoryBr = appBr.getBaseResourceValue("InventoryDetailDate", 1);
		if(inventoryBr == null) {
			return getInventorySql(productName, packSizeName, region, organId, warehouseId, endDate, users);
		} else {
			Date historyDate = DateUtil.StringToDate(inventoryBr.getTagsubvalue());
			Date queryDate = DateUtil.StringToDate(endDate);
			if(queryDate.before(historyDate)) {
				return getInventorySqlFromHistory(productName, packSizeName, region, organId, warehouseId, endDate, users);
			} else {
				return getInventorySqlFromHistoryAndSalesConsume(productName, packSizeName, region, organId, warehouseId, endDate, users);
			}
		}
	}


	private String getInventorySqlFromHistoryAndSalesConsume(
			String productName, String packSizeName, String region,
			String organId, String warehouseId, String endDate, UsersBean users) {
		StringBuffer sql = new StringBuffer();
		return sql.toString();
		/*sql.append("select organid,productid,stockpile,stockpileToShip, stockpileInTransit,WAREHOUSEID from INVENTORY_DETAIL_HISTORY ");
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
		sql.append(" \r\n --工厂给PD的数量");
		sql.append(" \r\n select tt.inoid oid,ttd.productid pid ,(ttd.quantity * f.xquantity) quantity,0,0,w.id wid  ");
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
		sql.append(" \r\n INNER JOIN STOCK_ALTER_MOVE sam on sam.id = tt.billno and sam.isblankout = 0");
		if(!StringUtil.isEmpty(historyDateStr)){
			sql.append(" \r\n and sam.movedate >=to_date('" + historyDateStr + "','yyyy-MM-dd hh24:mi:ss') "); //开始时间条件
		}
		if(!StringUtil.isEmpty(endDate)){
			sql.append(" \r\n and sam.movedate < to_date('" + endDate + "','yyyy-MM-dd hh24:mi:ss') "); //结束时间条件
		}
		sql.append(" \r\n INNER JOIN ORGAN o on tt.oid = o.id and o.organtype = 1  "); // 出库仓库为工厂
		sql.append(" \r\n INNER JOIN ORGAN ino on tt.inoid = ino.id and ino.organtype = 2 and ino.organmodel = 1"); // 出库机构为PD
		sql.append(" \r\n join WAREHOUSE w on tt.inwarehouseid = w.id and w.USEFLAG = 1  ");
		sql.append(" \r\n LEFT JOIN F_UNIT f on ttd.productid = f.productid and ttd.unitid = f.funitid ");
		if(DbUtil.isDealer(users)) {
			sql.append(" \r\n LEFT JOIN RULE_USER_WH rw on tt.inwarehouseid = rw.warehouse_id  ");
			sql.append(" \r\n where rw.user_id = " + users.getUserid());
		} else {
			sql.append(" \r\n where "+DbUtil.getWhereCondition(users, "ino"));
		}
		
		sql.append(" \r\n UNION ALL ");
		
		sql.append(" \r\n --PD调拨回来的");
		sql.append(" \r\n select tt.inoid oid,ttd.productid pid ,(ttd.realquantity * f.xquantity) quantity,0,0,w.id wid  ");
		sql.append(" \r\n from TAKE_TICKET tt INNER JOIN TAKE_TICKET_DETAIL ttd on tt.id = ttd.ttid  and tt.isaudit = 1  and tt.bsort = 2 ");
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
		sql.append(" \r\n INNER JOIN STOCK_MOVE sm on sm.id = tt.billno");
		if(!StringUtil.isEmpty(historyDateStr)){
			sql.append(" \r\n and tt.auditdate >=to_date('" + historyDateStr + "','yyyy-MM-dd hh24:mi:ss') "); //开始时间条件
		}
		if(!StringUtil.isEmpty(endDate)){
			sql.append(" \r\n and tt.auditdate < to_date('" + endDate + "','yyyy-MM-dd hh24:mi:ss') "); //结束时间条件
		}
		sql.append(" \r\n INNER JOIN ORGAN o on tt.oid = o.id and o.organtype = 2 and o.organmodel = 1"); // 出库机构为PD
		sql.append(" \r\n join WAREHOUSE w on tt.inwarehouseid = w.id and w.USEFLAG = 1  ");
		sql.append(" \r\n LEFT JOIN F_UNIT f on ttd.productid = f.productid and ttd.unitid = f.funitid ");
		if(DbUtil.isDealer(users)) {
			sql.append(" \r\n LEFT JOIN RULE_USER_WH rw on tt.inwarehouseid = rw.warehouse_id  ");
			sql.append(" \r\n where rw.user_id = " + users.getUserid());
		} else {
			sql.append(" \r\n where "+DbUtil.getWhereCondition(users, "o"));
		}
		
		sql.append(" \r\n UNION ALL ");
		//调拨给其他PD的
		sql.append(" \r\n --调拨给其他PD的");
		sql.append(" \r\n select tt.oid,ttd.productid pid ,-(ttd.realquantity * f.xquantity) quantity,0,0,w.id wid    ");
		sql.append(" \r\n from TAKE_TICKET tt INNER JOIN TAKE_TICKET_DETAIL ttd on tt.id = ttd.ttid  and tt.isaudit = 1  and tt.bsort = 2 ");
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
		sql.append(" \r\n INNER JOIN STOCK_MOVE sm on sm.id = tt.billno");
		if(!StringUtil.isEmpty(historyDateStr)){
			sql.append(" \r\n and tt.auditdate >=to_date('" + historyDateStr + "','yyyy-MM-dd hh24:mi:ss') "); //开始时间条件
		}
		if(!StringUtil.isEmpty(endDate)){
			sql.append(" \r\n and tt.auditdate < to_date('" + endDate + "','yyyy-MM-dd hh24:mi:ss') "); //结束时间条件
		}
		sql.append(" \r\n INNER JOIN ORGAN o on tt.inoid = o.id and o.organtype = 2 and  o.organmodel = 1 "); // 收货机构为PD
		sql.append(" \r\n join WAREHOUSE w on tt.warehouseid = w.id and w.USEFLAG = 1  ");
		sql.append(" \r\n LEFT JOIN F_UNIT f on ttd.productid = f.productid and ttd.unitid = f.funitid ");
		if(DbUtil.isDealer(users)) {
			sql.append(" \r\n LEFT JOIN RULE_USER_WH rw on tt.warehouseid = rw.warehouse_id  ");
			sql.append(" \r\n where rw.user_id = " + users.getUserid());
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
		sql.append(" \r\n join WAREHOUSE w on tt.warehouseid = w.id and w.USEFLAG = 1  ");
		if(DbUtil.isDealer(users)) {
			sql.append(" \r\n JOIN RULE_USER_WH rw on tt.WAREHOUSEID = rw.warehouse_id ");
			sql.append(" \r\n where rw.user_id = " + users.getUserid());
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
		sql.append(" \r\n join WAREHOUSE w on tt.warehouseid = w.id and w.USEFLAG = 1  ");
		if(DbUtil.isDealer(users)) {
			sql.append(" \r\n JOIN RULE_USER_WH rw on tt.WAREHOUSEID = rw.warehouse_id ");
			sql.append(" \r\n where rw.user_id = " + users.getUserid());
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
		sql.append(" \r\n INNER JOIN ORGAN o on oia.organid= o.id and o.organtype = 2 and  organmodel = 1 ");
		sql.append(" \r\n join WAREHOUSE w on oia.warehouseid = w.id and w.USEFLAG = 1  ");
		sql.append(" \r\n LEFT JOIN F_UNIT f on oida.productid = f.productid and oida.unitid = f.funitid  "); 
		if(DbUtil.isDealer(users)) {
			sql.append(" \r\n INNER JOIN RULE_USER_WH rw on oia.warehouseid = rw.warehouse_id ");
			sql.append(" \r\n where rw.user_id = " + users.getUserid()); 
		} else {
			sql.append(" \r\n where "+DbUtil.getWhereCondition(users, "o"));
		}
		
		sql.append(" \r\n UNION ALL ");
		sql.append(" \r\n --退回工厂");
		
		sql.append(" \r\n select tt.oid oid,ttd.productid pid ,-(ttd.realquantity * f.xquantity) quantity,0,0,w.id wid  ");
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
		sql.append(" \r\n INNER JOIN ORGAN o on tt.oid = o.id and o.organtype = 2 and  organmodel = 1 "); //出库仓库为PD
		sql.append(" \r\n join WAREHOUSE w on tt.warehouseid = w.id and w.USEFLAG = 1  ");
		sql.append(" \r\n LEFT JOIN F_UNIT f on ttd.productid = f.productid and ttd.unitid = f.funitid ");
		if(DbUtil.isDealer(users)) {
			sql.append(" \r\n LEFT JOIN RULE_USER_WH rw on tt.warehouseid = rw.warehouse_id  ");
			sql.append(" \r\n where rw.user_id = " + users.getUserid());
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
		//过滤拒签的单据
		sql.append(" \r\n INNER JOIN STOCK_ALTER_MOVE sam on sam.id = tt.billno and sam.isblankout = 0");
		sql.append(" \r\n INNER JOIN ORGAN o on tt.oid = o.id and o.organtype = 2 and  organmodel = 1 "); //出库仓库为PD
		sql.append(" \r\n join WAREHOUSE w on tt.warehouseid = w.id and w.USEFLAG = 1  ");
		sql.append(" \r\n LEFT JOIN F_UNIT f on ttd.productid = f.productid and ttd.unitid = f.funitid ");
		if(DbUtil.isDealer(users)) {
			sql.append(" \r\n JOIN RULE_USER_WH rw on tt.warehouseid = rw.warehouse_id  ");
			//#start modified by ryan.xi 20150518 过滤消耗为0的单据
			sql.append(" \r\n where rw.user_id = " + users.getUserid());
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
		sql.append(" \r\n join WAREHOUSE w on tt.warehouseid = w.id and w.USEFLAG = 1  ");
		if(DbUtil.isDealer(users)) {
			sql.append(" \r\n JOIN RULE_USER_WH rw on tt.WAREHOUSEID = rw.warehouse_id ");
			sql.append(" \r\n where rw.user_id = " + users.getUserid());
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
		sql.append(" \r\n join STOCK_ALTER_MOVE_DETAIL samd on samd.samid = sam.id and samd.productid=ttd.productid");
		sql.append(" \r\n INNER JOIN ORGAN o on tt.oid = o.id and o.organtype = 1 ");
		sql.append(" \r\n join WAREHOUSE w on tt.inwarehouseid = w.id and w.USEFLAG = 1  ");
		sql.append(" \r\n LEFT JOIN F_UNIT f on ttd.productid = f.productid and ttd.unitid = f.funitid ");
		sql.append(" \r\n LEFT JOIN ORGAN ino on tt.inoid = ino.id  and ino.organtype = 2 and ino.organmodel = 1 ");
		if(DbUtil.isDealer(users)) {
			sql.append(" \r\n LEFT JOIN RULE_USER_WH rw on tt.inwarehouseid = rw.warehouse_id ");
			sql.append(" \r\n where rw.user_id = " + users.getUserid());
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
		sql.append(" \r\n INNER JOIN ORGAN o on oia.organid= o.id and o.organtype = 2 and  organmodel = 1 ");
		sql.append(" \r\n join WAREHOUSE w on oia.warehouseid = w.id and w.USEFLAG = 1  ");
		sql.append(" \r\n LEFT JOIN F_UNIT f on oida.productid = f.productid and oida.unitid = f.funitid  "); 
		if(DbUtil.isDealer(users)) {
			sql.append(" \r\n INNER JOIN RULE_USER_WH rw on oia.warehouseid = rw.warehouse_id ");
			sql.append(" \r\n where rw.user_id = " + users.getUserid()); 
		} else {
			sql.append(" \r\n where "+DbUtil.getWhereCondition(users, "o"));
		}
		
		
		//其他出库-加消耗-20150429
		sql.append(" \r\n UNION ALL ");
		sql.append(" \r\n --其他出库-加消耗");
		sql.append(" \r\n select oia.organid, oida.productid pid, -(oida.quantity * f.xquantity) quantity,0,0,w.id wid   ");
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
		sql.append(" \r\n INNER JOIN ORGAN o on oia.organid= o.id and o.organtype = 2 and  organmodel = 1 ");
		sql.append(" \r\n join WAREHOUSE w on oia.warehouseid = w.id and w.USEFLAG = 1  ");
		sql.append(" \r\n LEFT JOIN F_UNIT f on oida.productid = f.productid and oida.unitid = f.funitid  "); 
		if(DbUtil.isDealer(users)) {
			sql.append(" \r\n INNER JOIN RULE_USER_WH rw on oia.warehouseid = rw.warehouse_id ");
			sql.append(" \r\n where rw.user_id = " + users.getUserid()); 
		} else { 
			sql.append(" \r\n where "+DbUtil.getWhereCondition(users, "o"));
		}
		sql.append(" \r\n UNION ALL ");
		sql.append(" \r\n --退货");
		sql.append(" \r\n select tt.inoid oid,ttd.productid pid,(ttd.realquantity * f.xquantity) quantity,0,0,w.id wid   ");
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
		sql.append(" \r\n INNER JOIN ORGAN o on tt.inoid = o.id and o.organtype = 2 and  organmodel = 1 "); // 入库仓库为PD
		sql.append(" \r\n join WAREHOUSE w on tt.inwarehouseid = w.id and w.USEFLAG = 1  ");
		sql.append(" \r\n LEFT JOIN F_UNIT f on ttd.productid = f.productid and ttd.unitid = f.funitid ");
		
		if(DbUtil.isDealer(users)) {
			sql.append(" \r\n LEFT JOIN RULE_USER_WH rw on tt.inwarehouseid = rw.warehouse_id ");
			sql.append(" \r\n where rw.user_id = " + users.getUserid());
		} else {
			sql.append(" \r\n where "+DbUtil.getWhereCondition(users, "o"));
		}*/
	}


	private String getInventorySqlFromHistory(String productName,
			String packSizeName, String region, String organId,
			String warehouseId, String endDate, UsersBean users) throws Exception {
		Calendar yearMonth = Calendar.getInstance();
		yearMonth.setTime(DateUtil.StringToDate(endDate));
		
		StringBuffer sql = new StringBuffer();
		sql.append("select idh.productid,idh.organId,idh.warehouseId,(stockpile+stockpileintransit+STOCKPILEtoship) stockpile from INVENTORY_DETAIL_HISTORY idh ");
		sql.append("join organ o on o.id = idh.organid and o.ISREPEAL = 0 ");
		sql.append("and year ="+yearMonth.get(Calendar.YEAR)+" and month="+(yearMonth.get(Calendar.MONTH)+1)+" ");
		if(!StringUtil.isEmpty(organId)) {
			sql.append(" \r\n and idh.organid = '"+organId+"'");
		}
		if(!StringUtil.isEmpty(warehouseId)) {
			sql.append(" \r\n  and idh.warehouseId = '"+warehouseId+"'");
		}
		if(!StringUtil.isEmpty(region)) {
			sql.append(" \r\n and o.province is not null and o.province in (select areaid from region_area where regioncodeid =  '" + region + "')"); //大区条件 
		}
		if(!StringUtil.isEmpty(productName) && !StringUtil.isEmpty(packSizeName)) { //产品条件
			sql.append(" \r\n and idh.productid in (select id from product where productname = '"+productName+"' and specmode= '"+packSizeName+"') ");
		} else if(!StringUtil.isEmpty(productName) && StringUtil.isEmpty(packSizeName)) { //产品条件
			sql.append(" \r\n and idh.productid in (select id from product where productname = '"+productName+"') ");
		} 
		if(!DbUtil.isDealer(users)) {
			sql.append(" and "+DbUtil.getWhereCondition(users, "o"));
		} else {
			sql.append(" and warehouseid in (select wv.warehouse_Id from RULE_USER_WH wv where wv.user_Id="+users.getUserid()+")");
		}
		sql.append(" ) ps"); 
		sql.append(" \r\n JOIN Product pro on pro.id = ps.productId and pro.useflag = 1"); 
		sql.append(" \r\n group by ps.productId, ps.organId, ps.warehouseId "); 
		return sql.toString();
	}


	public String getInventorySql(String productName, String packSizeName, String region, String organId, String warehouseId, String endDate, UsersBean users) {
		StringBuffer sql = new StringBuffer();
		
		StringBuffer tempSql = new StringBuffer();
		
		tempSql.append("\r\n select o.id as organId,w.id as warehouseId from ORGAN o ");
		tempSql.append("\r\n join WAREHOUSE w on w.makeorganid = o.id  and w.useflag = 1");
		tempSql.append("\r\n join RULE_USER_WH wv on w.id = wv.warehouse_Id and wv.user_Id = " + users.getUserid());
		tempSql.append("\r\n where organType =2 and organModel =1");
		
//		if(!StringUtil.isEmpty(productName) && !StringUtil.isEmpty(packSizeName)) { //产品条件
//			tempSql.append(" \r\n and productid in (select id from product where productname = '"+productName+"' and specmode= '"+packSizeName+"') ");
//		} else if(!StringUtil.isEmpty(productName) && StringUtil.isEmpty(packSizeName)) { //产品条件
//			tempSql.append(" \r\n and productid in (select id from product where productname = '"+productName+"') ");
//		}
		if(!StringUtil.isEmpty(organId)) {
			tempSql.append(" \r\n  and o.id = '"+organId+"'");
		}
		if(!StringUtil.isEmpty(warehouseId)) {
			tempSql.append(" \r\n  and w.id = '"+warehouseId+"'");
		}
		if(!StringUtil.isEmpty(region)) {
			tempSql.append(" \r\n and o.province is not null and o.province in (select areaid from region_area where regioncodeid =  '" + region + "')"); //大区条件 
		}
		tempSql.append(" \r\n GROUP BY o.id,w.id ");
		
		
//		sql.append(" \r\n select ps.productId, ps.organId, ps.warehouseId, sum(ps.stockpile * pro.boxquantity) as stockpile from (");
		//历史库存
		sql.append(" \r\n select ps.productid, sch.organId, sch.warehouseId, ps.stockpile as stockpile from PRODUCT_STOCKPILE_ALL ps ");
		sql.append("\r\n JOIN (");
		sql.append(tempSql);
		sql.append(" \r\n ) sch on ps.warehouseid = sch.warehouseid ");
		sql.append(getProductCondition(productName,packSizeName,"ps.productid"));

		sql.append(" \r\n UNION ALL");
		//--发货单(入库)
		sql.append(" \r\n --发货单(入库) ");
		sql.append(" \r\n select samd.productid, sch.organId, sch.warehouseId,-(samd.receivequantity * f.xquantity) as stockpile from STOCK_ALTER_MOVE sam ");
		sql.append(" \r\n join STOCK_ALTER_MOVE_DETAIL samd on sam.id = samd.samid ");
		sql.append(" \r\n and sam.iscomplete = 1");
		sql.append(" \r\n and sam.receivedate > to_date('" + endDate + "','yyyy-MM-dd hh24:mi:ss')");
		sql.append(" \r\n and sam.movedate > to_date('" + endDate + "','yyyy-MM-dd hh24:mi:ss')");
		sql.append("\r\n JOIN (");
		sql.append(tempSql);
		sql.append(" \r\n ) sch on sam.INWAREHOUSEID = sch.warehouseid ");
		sql.append(getProductCondition(productName,packSizeName,"samd.productid"));
		sql.append(" \r\n  LEFT JOIN F_UNIT f on samd.productid = f.productid and samd.unitid = f.funitid  ");
		
		sql.append(" \r\n UNION ALL");
		//--发货单(出库)
		sql.append(" \r\n --发货单(出库) ");
		sql.append(" \r\n select samd.productid, sch.organId, sch.warehouseId,(samd.takequantity * f.xquantity) from STOCK_ALTER_MOVE sam ");
		sql.append(" \r\n join STOCK_ALTER_MOVE_DETAIL samd on sam.id = samd.samid ");
		sql.append(" \r\n and sam.isshipment = 1");
		sql.append(" \r\n and sam.shipmentdate > to_date('" + endDate + "','yyyy-MM-dd hh24:mi:ss')");
		sql.append(" \r\n and sam.movedate > to_date('" + endDate + "','yyyy-MM-dd hh24:mi:ss')");
		sql.append("\r\n JOIN (");
		sql.append(tempSql);
		sql.append(" \r\n ) sch on  sam.OUTWAREHOUSEID = sch.warehouseid ");
		sql.append(getProductCondition(productName,packSizeName,"samd.productid"));
		sql.append(" \r\n  LEFT JOIN F_UNIT f on samd.productid = f.productid and samd.unitid = f.funitid  ");
		
		sql.append(" \r\n UNION ALL");
		
		//--发货单(待发货)
		sql.append(" \r\n --发货单(待发货) ");
		sql.append(" \r\n select samd.productid, sch.organId, sch.warehouseId,(samd.takequantity * f.xquantity) from STOCK_ALTER_MOVE sam ");
		sql.append(" \r\n join STOCK_ALTER_MOVE_DETAIL samd on sam.id = samd.samid ");
		sql.append(" \r\n and sam.isshipment = 1");
		sql.append(" \r\n and sam.shipmentdate > to_date('" + endDate + "','yyyy-MM-dd hh24:mi:ss')");
		sql.append(" \r\n and sam.movedate <= to_date('" + endDate + "','yyyy-MM-dd hh24:mi:ss')");
		sql.append("\r\n JOIN (");
		sql.append(tempSql);
		sql.append(" \r\n ) sch on sam.OUTWAREHOUSEID = sch.warehouseid ");
		sql.append(getProductCondition(productName,packSizeName,"samd.productid"));
		sql.append(" \r\n  LEFT JOIN F_UNIT f on samd.productid = f.productid and samd.unitid = f.funitid  ");
		
		sql.append(" \r\n UNION ALL");
		
		// --转仓单(入库)
		sql.append(" \r\n --转仓单(入库)");
		sql.append(" \r\n select smd.productid, sch.organId, sch.warehouseId,-(smd.takequantity * f.xquantity) from STOCK_MOVE sm ");
		sql.append(" \r\n join STOCK_MOVE_DETAIL smd on sm.id = smd.smid  and sm.inwarehouseid <> outwarehouseid");
		sql.append(" \r\n and sm.iscomplete = 1");
		sql.append(" \r\n and sm.receivedate > to_date('" + endDate + "','yyyy-MM-dd hh24:mi:ss')");
		sql.append(" \r\n and sm.shipmentdate > to_date('" + endDate + "','yyyy-MM-dd hh24:mi:ss')");
		sql.append("\r\n JOIN (");
		sql.append(tempSql);
		sql.append(" \r\n ) sch on sm.INWAREHOUSEID = sch.warehouseid ");
		sql.append(getProductCondition(productName,packSizeName,"smd.productid"));
		sql.append(" \r\n  LEFT JOIN F_UNIT f on smd.productid = f.productid and smd.unitid = f.funitid  ");
		
		sql.append(" \r\n UNION ALL");
		//--转仓单(出库)
		sql.append(" \r\n --转仓单(出库)");
		sql.append(" \r\n select smd.productid, sch.organId, sch.warehouseId,(smd.takequantity * f.xquantity)  from STOCK_MOVE sm ");
		sql.append(" \r\n join STOCK_MOVE_DETAIL smd on sm.id = smd.smid  and sm.inwarehouseid <> outwarehouseid");
		sql.append(" \r\n and sm.isshipment = 1");
		sql.append(" \r\n and sm.shipmentdate > to_date('" + endDate + "','yyyy-MM-dd hh24:mi:ss')");
		sql.append("\r\n JOIN (");
		sql.append(tempSql);
		sql.append(" \r\n ) sch on sm.OUTWAREHOUSEID = sch.warehouseid ");
		sql.append(getProductCondition(productName,packSizeName,"smd.productid"));
		sql.append(" \r\n  LEFT JOIN F_UNIT f on smd.productid = f.productid and smd.unitid = f.funitid  ");
		
		sql.append(" \r\n UNION ALL");
		//--退货单(入库)
		sql.append(" \r\n --退货单(入库)");
		sql.append(" \r\n select owd.productid, sch.organId, sch.warehouseId,-(owd.takequantity * f.xquantity) from ORGAN_WITHDRAW ow  ");
		sql.append(" \r\n join TAKE_TICKET tt on tt.billno = ow.id ");
		sql.append(" \r\n and ow.iscomplete = 1");
		sql.append(" \r\n and tt.auditdate > to_date('" + endDate + "','yyyy-MM-dd hh24:mi:ss')");
		if(!StringUtil.isEmpty(warehouseId)) {
			sql.append(" \r\n  and ow.INWAREHOUSEID = '"+warehouseId+"'");
		} 
		sql.append(" \r\n join ORGAN_WITHDRAW_DETAIL owd on ow.id = owd.owid  ");
		sql.append("\r\n JOIN (");
		sql.append(tempSql);
		sql.append(" \r\n ) sch on ow.INWAREHOUSEID = sch.warehouseid ");
		sql.append(getProductCondition(productName,packSizeName,"owd.productid"));
		sql.append(" \r\n  LEFT JOIN F_UNIT f on owd.productid = f.productid and owd.unitid = f.funitid   ");
		
		sql.append(" \r\n UNION ALL");
		//--退货单(出库)
		sql.append(" \r\n --退货单(出库)");
		sql.append(" \r\n select owd.productid, sch.organId, sch.warehouseId,(owd.takequantity * f.xquantity) from ORGAN_WITHDRAW ow  ");
		sql.append(" \r\n join TAKE_TICKET tt on tt.billno = ow.id ");
		sql.append(" \r\n and tt.isaudit = 1");
		sql.append(" \r\n and tt.auditdate > to_date('" + endDate + "','yyyy-MM-dd hh24:mi:ss')");
		if(!StringUtil.isEmpty(warehouseId)) {
			sql.append(" \r\n  and ow.WAREHOUSEID = '"+warehouseId+"'");
		} 
		sql.append(" \r\n join ORGAN_WITHDRAW_DETAIL owd on ow.id = owd.owid  ");
		sql.append("\r\n JOIN (");
		sql.append(tempSql);
		sql.append(" \r\n ) sch on ow.WAREHOUSEID = sch.warehouseid ");
		sql.append(getProductCondition(productName,packSizeName,"owd.productid"));
		sql.append(" \r\n  LEFT JOIN F_UNIT f on owd.productid = f.productid and owd.unitid = f.funitid   ");
		
		sql.append(" \r\n UNION ALL");
		
		//-- 未签收的单据（在途库存）
		// --发货单
		sql.append(" \r\n --未签收的单据（在途库存）-发货单");
		sql.append(" \r\n select samd.productid, sch.organId, sch.warehouseId,(samd.takequantity * f.xquantity) from STOCK_ALTER_MOVE sam ");
		sql.append(" \r\n join STOCK_ALTER_MOVE_DETAIL samd on sam.id = samd.samid ");
		sql.append(" \r\n and sam.isshipment = 1 and sam.isblankout = 0 and sam.iscomplete = 0 ");
		sql.append(" \r\n and sam.movedate < to_date('" + endDate + "','yyyy-MM-dd hh24:mi:ss')");
		sql.append("\r\n JOIN (");
		sql.append(tempSql);
		sql.append(" \r\n ) sch on sam.INWAREHOUSEID = sch.warehouseid ");
		sql.append(getProductCondition(productName,packSizeName,"samd.productid"));
		sql.append(" \r\n LEFT JOIN F_UNIT f on samd.productid = f.productid and samd.unitid = f.funitid  ");
		
		sql.append(" \r\n UNION ALL");
		//--转仓单
		sql.append(" \r\n --未签收的单据（在途库存）--转仓单");
		sql.append(" \r\n select smd.productid, sch.organId, sch.warehouseId,(smd.takequantity * f.xquantity) from STOCK_MOVE sm ");
		sql.append(" \r\n join STOCK_MOVE_DETAIL smd on sm.id = smd.smid  and sm.inwarehouseid <> outwarehouseid");
		sql.append(" \r\n and sm.isshipment = 1 and sm.iscomplete = 0 and sm.isblankout = 0 ");
		sql.append(" \r\n and sm.shipmentdate < to_date('" + endDate + "','yyyy-MM-dd hh24:mi:ss')");
		sql.append("\r\n JOIN (");
		sql.append(tempSql);
		sql.append(" \r\n ) sch on sm.INWAREHOUSEID = sch.warehouseid ");
		sql.append(getProductCondition(productName,packSizeName,"smd.productid"));
		sql.append(" \r\n  LEFT JOIN F_UNIT f on smd.productid = f.productid and smd.unitid = f.funitid  ");
		
		sql.append(" \r\n UNION ALL");
		// --退货单
		sql.append(" \r\n --未签收的单据（在途库存）--退货单");
		sql.append(" \r\n select owd.productid, sch.organId, sch.warehouseId,(owd.takequantity * f.xquantity) from ORGAN_WITHDRAW ow  ");
		sql.append(" \r\n join TAKE_TICKET tt on tt.billno = ow.id ");
		sql.append(" \r\n and ow.takestatus = 1 and ow.iscomplete = 0 and ow.isblankout = 0 ");
		sql.append(" \r\n and tt.auditdate < to_date('" + endDate + "','yyyy-MM-dd hh24:mi:ss')");
		sql.append(" \r\n join ORGAN_WITHDRAW_DETAIL owd on ow.id = owd.owid  ");
		sql.append("\r\n JOIN (");
		sql.append(tempSql);
		sql.append(" \r\n ) sch on ow.INWAREHOUSEID = sch.warehouseid ");
		sql.append(getProductCondition(productName,packSizeName,"owd.productid"));
		sql.append(" \r\n  LEFT JOIN F_UNIT f on owd.productid = f.productid and owd.unitid = f.funitid   ");
		
		//#start modified by ryan.xi at 20150714
		//分包入库
		sql.append(" \r\n UNION ALL");
		sql.append(" \r\n --分包入库");
		sql.append(" \r\n select samd.inproductid, sch.organId, sch.warehouseId,-(samd.inquantity) from PACK_SEPARATE sam   ");
		sql.append(" \r\n join PACK_SEPARATE_DETAIL samd on sam.id = samd.psid ");
		sql.append(" \r\n and sam.ISAUDIT = 1 ");
		sql.append(" \r\n and sam.AUDITDATE > to_date('" + endDate + "','yyyy-MM-dd hh24:mi:ss')");
		sql.append("\r\n JOIN (");
		sql.append(tempSql);
		sql.append(" \r\n ) sch on sam.WAREHOUSEID = sch.warehouseid ");
		sql.append(getProductCondition(productName,packSizeName,"samd.inproductid"));
		
		//分包出库
		sql.append(" \r\n UNION ALL");
		sql.append(" \r\n --分包出库");
		sql.append(" \r\n select samd.outproductid, sch.organId, sch.warehouseId,samd.outquantity from PACK_SEPARATE sam ");
		sql.append(" \r\n join PACK_SEPARATE_DETAIL samd on sam.id = samd.psid ");
		sql.append(" \r\n and sam.ISAUDIT = 1 ");
		sql.append(" \r\n and sam.AUDITDATE > to_date('" + endDate + "','yyyy-MM-dd hh24:mi:ss')");
		sql.append("\r\n JOIN (");
		sql.append(tempSql);
		sql.append(" \r\n ) sch on sam.WAREHOUSEID = sch.warehouseid ");
		sql.append(getProductCondition(productName,packSizeName,"samd.outproductid"));
		
		//其他入库-减库存-20150429
		sql.append(" \r\n UNION ALL ");
		sql.append(" \r\n --其他入库-减库存-20150429");
		sql.append(" \r\n select oida.productid, sch.organId, sch.warehouseId,-(oida.quantity * f.xquantity) ");
		sql.append(" \r\n from OTHER_INCOME_ALL oia INNER JOIN OTHER_INCOME_DETAIL_ALL oida on oia.id = oida.oiid and oia.isaudit = 1 ");
		sql.append(" \r\n and oia.auditdate > to_date('" + endDate + "','yyyy-MM-dd hh24:mi:ss')");
		sql.append("\r\n JOIN (");
		sql.append(tempSql);
		sql.append(" \r\n ) sch on oia.warehouseid = sch.warehouseid ");
		sql.append(getProductCondition(productName,packSizeName,"oida.productid"));
		sql.append(" \r\n JOIN F_UNIT f on oida.productid = f.productid and oida.unitid = f.funitid  "); 
		
		
		//其他出库-加库存-20150429
		sql.append(" \r\n UNION ALL ");
		sql.append(" \r\n --其他出库-加库存-20150429");
		sql.append(" \r\n select oida.productid, sch.organId, sch.warehouseId,(oida.quantity * f.xquantity) ");
		sql.append(" \r\n from OTHER_SHIPMENT_BILL_ALL oia INNER JOIN OTHER_SHIPMENT_BILL_D_ALL oida on oia.id = oida.osid and oia.isaudit = 1");
		sql.append(" \r\n and oia.auditdate > to_date('" + endDate + "','yyyy-MM-dd hh24:mi:ss')");
		sql.append("\r\n JOIN (");
		sql.append(tempSql);
		sql.append(" \r\n ) sch on oia.warehouseid = sch.warehouseid ");
		sql.append(getProductCondition(productName,packSizeName,"oida.productid"));
		sql.append(" \r\n JOIN F_UNIT f on oida.productid = f.productid and oida.unitid = f.funitid  ) ps"); 
		
		sql.append(" \r\n JOIN Product pro on pro.id = ps.productId and pro.useflag = 1"); 
		sql.append(" \r\n group by ps.productId, ps.organId, ps.warehouseId "); 
		
		System.out.println(sql.toString());
		return sql.toString();
		
	}
	
//	private String getInventoryBeginDate(Integer year, Integer month) {
//		Calendar calendar = Calendar.getInstance();
//		calendar.set(year, month-1, 1);
//		calendar.add(calendar.DAY_OF_MONTH, -1);
//		return DateUtil.formatDate(calendar.getTime()) + " 23:59:59";
//	}
	
	private String getProductCondition(String productName, String packSizeName,
			String fieldName) {
		if(!StringUtil.isEmpty(productName) && !StringUtil.isEmpty(packSizeName)) { //产品条件
			return " \r\n and "+fieldName+" in (select id from product where productname = '"+productName+"' and specmode= '"+packSizeName+"') ";
		} else if(!StringUtil.isEmpty(productName) && StringUtil.isEmpty(packSizeName)) { //产品条件
			return " \r\n and "+fieldName+" in (select id from product where productname = '"+productName+"') ";
		} else {
			return "";
		}
	}


	private String getInventoryEndDate(Integer year, Integer month) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(year, month-1, 1);
		calendar.add(Calendar.MONTH, 1);
		calendar.set(Calendar.DAY_OF_MONTH, 0);
		return DateUtil.formatDate(calendar.getTime()) + " 23:59:59";
	}
	
	
	/**
	 * 获取产品单价(计量单位)
	 * @param year
	 * @param month
	 * @param productId
	 * @return
	 * @throws SQLException 
	 * @throws HibernateException 
	 */
	/**
	 * @param yearMonth
	 * @param productId
	 * @param organId
	 * @param warehouseId 
	 * @return
	 * @throws HibernateException
	 * @throws SQLException
	 */
	public ProductRollingPrice getPrductRollingPrices(String yearMonth, String productId, String organId, String warehouseId) throws HibernateException, SQLException {
		Calendar today = Calendar.getInstance();
		Calendar earlyDate = Calendar.getInstance();
		earlyDate.setTime(DateUtil.StringToDate(yearMonth, "yyyyMM"));
		ProductRollingPrice prp = new ProductRollingPrice();
		prp.setOrganId(organId);
		prp.setProductId(productId);
		prp.setWarehouseId(warehouseId);
		Map<String, Double> priceMap = prp.getPriceMap();
		do {
			Double price = getPrice(earlyDate, productId, organId);
			priceMap.put(DateUtil.formatDate(earlyDate.getTime(), "yyyyMM"), price);
			earlyDate.add(Calendar.MONTH, 1);
		} while (earlyDate.compareTo(today) < 0);
		return prp;
	}
	
	public Double getPrice(Calendar earlyDate, String productId, String organId) throws HibernateException, SQLException {
		//获取12月之前(包括当前月)的年份与月份
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(earlyDate.getTime());
		calendar.add(Calendar.MONTH, 1);
		String thisYear = DateUtil.formatDate(calendar.getTime(), "yyyy-MM-dd") ;
		calendar.add(Calendar.MONTH, -12);
		String lastYear = DateUtil.formatDate(calendar.getTime(), "yyyy-MM-dd")+ " 23:59:59";
		
		StringBuffer sql = new StringBuffer();
//		sql.append("\r\n select sum(TO_NUMBER(NET_VAL)) / sum(TO_NUMBER(invoice_qty) * f.xquantity) as price from invoice inv ");
		sql.append("\r\n select sum(TO_NUMBER(NET_VAL)) / sum(TO_NUMBER(invoice_qty)) as price from invoice inv ");
		sql.append("\r\n join STOCK_ALTER_MOVE sam on inv.DELIVERY_NUMBER = sam.nccode and sam.RECEIVEORGANID = '"+organId+"'  ");
		sql.append("\r\n join PRODUCT p on p.mcode = inv.MATERIAL_CODE  and p.id = '"+productId+"' ");
//		sql.append("\r\n JOIN F_UNIT f on p.id = f.productid and f.funitid = 2 ");
		sql.append("\r\n where inv.invoice_type ='M' ");
		sql.append("\r\n and sam.shipmentdate >= to_date('" + lastYear + "','yyyy-MM-dd hh24:mi:ss') and sam.shipmentdate < to_date('" + thisYear + "','yyyy-MM-dd hh24:mi:ss')");
		List<Map<String,String>> list =  EntityManager.jdbcquery(sql.toString());
		Map<String,String> map = list.get(0);
		return getDouble(map.get("price"));
	}

	public static void main(String[] args) throws HibernateException, SQLException {
		Calendar earlyDate = Calendar.getInstance();
		earlyDate.setTime(DateUtil.StringToDate("201511", "yyyyMM"));
		earlyDate.add(Calendar.MONTH, 1);
		String thisYear = DateUtil.formatDate(earlyDate.getTime(), "yyyyMMdd");
		earlyDate.add(Calendar.MONTH, -12);
		String lastYear = DateUtil.formatDate(earlyDate.getTime(), "yyyyMMdd");
		String productId = "014023";
		String organId = "14196";
		String warehouseId = "";
		String dateTime = "2016-01-01 00:00:00";
		String startDate = "2015-10-23 09:00:00";
		String endDate = "2015-10-23 10:00:00";
		//获取12月之前(包括当前月)的年份与月份
		Calendar calendar = Calendar.getInstance();
		Date date = calendar.getTime();
		calendar.set(2015, 11-1, 1);
//		String thisYear = DateUtil.formatDate(calendar.getTime(), "yyyyMM");
//		calendar.add(Calendar.MONTH, -11);
//		String lastYear = DateUtil.formatDate(calendar.getTime(), "yyyyMM");
		
		UsersBean users = new UsersBean(); 
		users.setUserid(1);
		System.out.println(new SalesConsumeInventoryReportService().getInventorySql("产品", "GUIGE", "DAQU", "213", "12321", "2015-01-05", users));

		
		
	}
	
}
