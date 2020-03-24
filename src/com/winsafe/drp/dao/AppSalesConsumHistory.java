package com.winsafe.drp.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.HibernateException;

import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.report.pojo.SalesConsumHistory;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.StringUtil;
import com.winsafe.hbm.util.pager2.PageQuery;

public class AppSalesConsumHistory {

	public void addSalesConsumHistory(SalesConsumHistory sch) {
		EntityManager.save(sch);
	}

	public SalesConsumHistory getSalesConsumHistory(String year, String month,
			String organId, String warehouseId, String productId) {
		String hql = "from SalesConsumHistory where year = "+year+" and month = "+Integer.parseInt(month)+" and organId = '"+organId+"' and warehouseId = '"+warehouseId+"' and productId = '"+productId+"'";
		return (SalesConsumHistory)EntityManager.find(hql);
	}
	
	public SalesConsumHistory getSalesConsumHistory(Integer year, Integer month,
			String organId, String productId, String hasInvoice) {
		String hql = "from SalesConsumHistory where year = "+year+" and month = "+month+" and organId = '"+organId+"' and productId = '"+productId+"' and hasInvoice = "+hasInvoice;
		return (SalesConsumHistory)EntityManager.find(hql);
	}

	public void updSalesConsumHistory(SalesConsumHistory sch) throws HibernateException, SQLException {
		EntityManager.update(sch);
	}

	public List<Map<String,String>> getByBillNoAndInvoiceNo(String billNo, String invoiceNumber) throws HibernateException, SQLException {
		StringBuffer sql = new StringBuffer();
		sql.append("\r\n select sam.receiveorganid organId,sam.inwarehouseid warehouseId, samd.productid productId, to_char(shipmentdate, 'yyyy') as year, to_char(shipmentdate, 'mm') as month, sum(TO_NUMBER(iv.net_val)) as salesvalue,to_char(movedate, 'yyyy') moveYear, to_char(movedate, 'MM') moveMonth from STOCK_ALTER_MOVE sam ");
		sql.append("\r\n INNER JOIN STOCK_ALTER_MOVE_DETAIL samd on sam.id = samd.samid ");
		sql.append("\r\n and sam.id = '"+billNo+"'");
		sql.append("\r\n JOIN INVOICE iv on sam.nccode = iv.DELIVERY_NUMBER and samd.nccode = iv.material_code and iv.INVOICE_NUMBER = '"+invoiceNumber+"' and iv.invoice_type ='M'");
		sql.append("\r\n GROUP BY sam.receiveorganid, sam.inwarehouseid, samd.productid , to_char(shipmentdate, 'yyyy'), to_char(shipmentdate, 'mm'),to_char(movedate, 'yyyy') , to_char(movedate, 'MM') ");
		return EntityManager.jdbcquery(sql.toString());
	}

	public List<Map<String,String>> getAllSalesConsumHistory(HttpServletRequest request,
			int pageSize, Map map, UsersBean users) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("\r\n select (year || case when month < 10 THEN '0'||month ELSE to_char(month) END) as yearmonth");
		sql.append("\r\n ,sch.organId as oid");
		sql.append("\r\n ,max(o.oecode) as oecode");
		sql.append("\r\n ,max(o.ORGANNAME) as oname");
		sql.append("\r\n ,sch.PRODUCTID as pid");
		sql.append("\r\n ,max(pro.sunit) as pSunit");
		sql.append("\r\n ,max(pro.PRODUCTNAME) as pName");
		sql.append("\r\n ,max(pro.PRODUCTNAMEEN) as pNameEn");
		sql.append("\r\n ,max(pro.MCODE) as mcode");
		sql.append("\r\n ,max(pro.MATERICALCHDES) as mChName");
		sql.append("\r\n ,max(pro.MATERICALENDES) as mEnName");
		sql.append("\r\n ,max(pro.PACKSIZENAME) as packSizeName");
		sql.append("\r\n ,max(pro.boxquantity) as boxquantity");
		sql.append("\r\n ,max(pro.countunit) as countunit");
		sql.append("\r\n ,max(pro.PACKSIZENAMEEN) as packSizeNameEn");
		sql.append("\r\n ,sum(sch.SALES_VOLUME) as salesVolume");
		sql.append("\r\n ,sum(sch.SALES_VALUE) as salesValue");
		sql.append("\r\n ,sum(sch.COMSUMPTION_VOLUME) as consumVolume");
		sql.append("\r\n ,sum(sch.OTHER_CONSUM_VOLUME) as otherConsum ");
		sql.append("\r\n ,max(sch.price) as price ");
		sql.append("\r\n from SALES_CONSUM_HISTORY sch");
		sql.append("\r\n join PRODUCT pro on sch.PRODUCTID = pro.id");
		sql.append("\r\n and pro.USEFLAG = 1 ");//过滤不可用产品
		//产品条件
		if(!StringUtil.isEmpty((String)map.get("ProductName")) && !StringUtil.isEmpty((String)map.get("packSizeName"))) { //产品条件
			sql.append(" \r\n and sch.PRODUCTID in (select id from product where productname = '"+(String)map.get("ProductName")+"' and specmode= '"+(String)map.get("packSizeName")+"') ");
		} else if(!StringUtil.isEmpty((String)map.get("ProductName")) && StringUtil.isEmpty((String)map.get("packSizeName"))) { //产品条件
			sql.append(" \r\n and sch.PRODUCTID in (select id from product where productname = '"+(String)map.get("ProductName")+"') ");
		}
		//日期条件
		if(!StringUtil.isEmpty((String)map.get("beginDate"))) {
			sql.append("\r\n and (year || case when month < 10 THEN '0'||month ELSE to_char(month) END) >= '"+((String)map.get("beginDate")).substring(0,7).replace("-", "")+"'");
		}
		if(!StringUtil.isEmpty((String)map.get("endDate"))) {
			sql.append("\r\n and (year || case when month < 10 THEN '0'||month ELSE to_char(month) END) <= '"+((String)map.get("endDate")).substring(0,7).replace("-", "")+"'");
		}
		//机构条件
		sql.append("\r\n join organ o on o.id = sch.organId ");
		if(DbUtil.isDealer(users)) {
			//管辖条件
			sql.append("\r\n and sch.warehouseId in (select wv.warehouse_Id from RULE_USER_WH wv where wv.user_Id="+users.getUserid()+")");
		} else {
			sql.append(" \r\n and "+DbUtil.getWhereCondition(users, "o"));
		}
		if(!StringUtil.isEmpty((String)map.get("organId"))) {
			sql.append("\r\n and sch.organid = '"+map.get("organId")+"'");
		}
		//仓库条件
		if(!StringUtil.isEmpty((String)map.get("warehouseId"))) {
			sql.append("\r\n and sch.warehouseId = '"+map.get("warehouseId")+"'");
		}
		//sql.append("\r\n and (sch.SALES_VOLUME <> 0 or sch.COMSUMPTION_VOLUME <> 0 or sch.OTHER_CONSUM_VOLUME <> 0 or sch.price <> 0) ");
		sql.append("\r\n GROUP BY (year || case when month < 10 THEN '0'||month ELSE to_char(month) END), sch.PRODUCTID,sch.organId");
		sql.append("\r\n ORDER BY NLSSORT(oname,'NLS_SORT = SCHINESE_PINYIN_M'),NLSSORT(pNameEn,'NLS_SORT = SCHINESE_PINYIN_M')");
		return PageQuery.jdbcSqlserverQuery(request, "NLSSORT(oname,'NLS_SORT = SCHINESE_PINYIN_M'),NLSSORT(pNameEn,'NLS_SORT = SCHINESE_PINYIN_M')", sql.toString(), pageSize);
	} 
	
	public static void main(String[] args) {
		String date = "2015-04-03";
		System.out.println(date.substring(0,7).replace("-", ""));
	}

	public List<SalesConsumHistory> getNewerSalesConsumHistory(String year,
			String month, String organId, String warehouseId, String productId) {
		String hql = "from SalesConsumHistory where year >= "+year+" and month > "+Integer.parseInt(month)+" and organId = '"+organId+"' and warehouseId = '"+warehouseId+"' and productId = '"+productId+"'";
		return EntityManager.getAllByHql(hql);
	}

	public List<Map<String, String>> getNotExistsHistoryDataByDate(int year,
			int month) throws HibernateException, SQLException {
		String sql = " select organId, warehouseId,productId from SALES_CONSUM_HISTORY sch " +
				" where not exists (select id from SALES_CONSUM_HISTORY where organId = sch.organId and warehouseId = sch.warehouseId and productId = sch.productId and year = "+year+" and month = "+month+" ) " +
						" and WAREHOUSEID is not null " +
				" GROUP BY organId, warehouseId,productId ";
		return EntityManager.jdbcquery(sql);
	}
	
}
