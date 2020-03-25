package com.winsafe.drp.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.HibernateException;

import com.winsafe.common.util.StringUtil;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.MakeCode;
import com.winsafe.hbm.util.pager2.PageQuery;

public class AppStockWasteBook {

	public List getStockWastBook(HttpServletRequest request, int pagesize,
			String pWhereClause) throws Exception {
		String hql = "select new map(s.id as id, s.productid as productid,p.productname as productname,s.batch as batch,s.warehouseid as warehouseid,s.warehousebit as warehousebit,"
				+ "s.billcode as billcode,s.memo as memo,s.cyclefirstquantity as cyclefirstquantity,s.cycleinquantity as cycleinquantity,s.cycleoutquantity as cycleoutquantity,"
				+ "s.cyclebalancequantity as cyclebalancequantity,s.recorddate as recorddate, p.sunit as countunit,p.mCode as nccode,w.warehousename as warehouseidname,p.specmode as specmode,pstr.sortname as sortname,p.packSizeName as packSizeName) "
				+ "from StockWasteBook as s,Product as p,Warehouse w ,ProductStruct pstr "
				+ pWhereClause + " order by recorddate desc";
		return PageQuery.hbmQuery(request, hql, pagesize);
	}
	
	public List getStockWastBookList(HttpServletRequest request, int pagesize,
			String pWhereClause, UsersBean users) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT s.ID,s.productid,P.productname,s. BATCH,s.warehouseid,s.warehousebit,");
		sql.append("s.billcode,s.memo,s.cyclefirstquantity,s.cycleinquantity,s.cycleoutquantity,");
		sql.append("s.cyclebalancequantity,s.recorddate,P.sunit countunit,P.mCode nccode,");
		sql.append("w.warehousename warehouseidname,P.specmode,pstr.sortname,P.packSizeName ");
		sql.append("FROM Stock_Waste_Book s ");
		sql.append("join Product P on p.id=s.productid ");
		sql.append("join Warehouse w on w.id=s.warehouseid ");
		sql.append("join Product_Struct pstr on pstr.structcode = P.psid ");
		if(!DbUtil.isDealer(users)) {
			sql.append("join organ o on o.id=w.makeorganid ");
		}
		sql.append(pWhereClause);
		if(pagesize > 0) {
			return PageQuery.jdbcSqlserverQuery(request, "recorddate desc", sql.toString(), pagesize);
		} else {
			sql.append(" order by s.recorddate desc ");
			return EntityManager.jdbcquery(sql.toString());
		}
		
	}
	
	public List getStockWastBookList(HttpServletRequest request, int pagesize,
			String pWhereClause, UsersBean users, Map<String, Object> param) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT s.ID,s.productid,P.productname,s. BATCH,s.warehouseid,s.warehousebit,");
		sql.append("s.billcode,s.memo,s.cyclefirstquantity,s.cycleinquantity,s.cycleoutquantity,");
		sql.append("s.cyclebalancequantity,s.recorddate,P.sunit countunit,P.mCode nccode,");
		sql.append("w.warehousename warehouseidname,P.specmode,pstr.sortname,P.packSizeName ");
		sql.append("FROM Stock_Waste_Book s ");
		sql.append("join Product P on p.id=s.productid ");
		sql.append("join Warehouse w on w.id=s.warehouseid ");
		sql.append("join Product_Struct pstr on pstr.structcode = P.psid ");
		if(!DbUtil.isDealer(users)) {
			sql.append("join organ o on o.id=w.makeorganid ");
		}
		sql.append(pWhereClause);
		if(pagesize > 0) {
			return PageQuery.jdbcSqlserverQuery(request, "recorddate desc", sql.toString(), pagesize, param);
		} else {
			sql.append(" order by s.recorddate desc ");
			return EntityManager.jdbcquery(sql.toString(), param); 
		}
		
	}

	public List getStockWastBook(HttpServletRequest request, int pagesize,
			String pWhereClause, String orderby) throws Exception {
		String hql = "select new map(s.id as id, s.productid as productid,p.productname as productname,s.batch as batch,s.warehouseid as warehouseid,s.warehousebit as warehousebit,"
				+ "s.billcode as billcode,s.memo as memo,s.cyclefirstquantity as cyclefirstquantity,s.cycleinquantity as cycleinquantity,s.cycleoutquantity as cycleoutquantity,"
				+ "s.cyclebalancequantity as cyclebalancequantity,s.recorddate as recorddate, p.countunit as countunit,p.nccode as nccode,w.warehousename as warehousename,p.specmode as specmode,pstr.sortname as sortname) "
				+ "from StockWasteBook as s,Product as p,Warehouse w ,ProductStruct pstr "
				+ pWhereClause;
		if (!StringUtil.isEmpty(orderby)) {
			hql += " order by " + orderby;
		} else {
			hql += " order by recorddate desc";
		}
		return PageQuery.hbmQuery(request, hql, pagesize);
	}

	public List getStockWastBook(String pWhereClause) throws Exception {
		String hql = "select new map(s.id as id, s.productid as productid,p.productname as productname,s.batch as batch,s.warehouseid as warehouseid,s.warehousebit as warehousebit,"
				+ "s.billcode as billcode,s.memo as memo,s.cyclefirstquantity as cyclefirstquantity,s.cycleinquantity as cycleinquantity,s.cycleoutquantity as cycleoutquantity,"
				+ "s.cyclebalancequantity as cyclebalancequantity,s.recorddate as recorddate, p.sunit as countunit,p.mCode as nccode,w.warehousename as warehousename,p.specmode as specmode,pstr.sortname as sortname) "
				+ "from StockWasteBook as s,Product as p,Warehouse w ,ProductStruct pstr "
				+ pWhereClause + " order by recorddate desc";
		return EntityManager.getAllByHql(hql);
	}
	/**
	 * 增加条码盘点出库台账
	 */
	public void addOutBarcodeStockWasteBook(String productid, String batch,
			Double quantity, String warehouseid, String warehousebit,
			String billcode, String memo) throws Exception {

		Long id = Long.valueOf(MakeCode.getExcIDByRandomTableName("stock_waste_book", 0, ""));
		String cfqsql = "select sum(ps.stockpile+ps.prepareout) from ProductStockpile as ps where ps.productid='"
				+ productid
				+ "' and batch='"
				+ batch
				+ "' and warehouseid='"
				+ warehouseid + "' and warehousebit='" + warehousebit + "'";
		Double cyclebalancequantity = EntityManager.getdoubleSum(cfqsql);
		Double cyclefirstquantity = cyclebalancequantity + quantity;
		
		if (!isExist(productid, batch, warehouseid, warehousebit, billcode)) {
			String sql = "insert into stock_waste_book(id,productid,batch,warehouseid,warehousebit,billcode,memo,cyclefirstquantity,cycleinquantity,cycleoutquantity,cyclebalancequantity,recorddate) select "
					+ id
					+ ",'"
					+ productid
					+ "','"
					+ batch
					+ "','"
					+ warehouseid
					+ "','"
					+ warehousebit
					+ "','"
					+ billcode
					+ "','"
					+ memo
					+ "',"
					+ cyclefirstquantity
					+ ",0,"
					+ quantity
					+ ","
					+ cyclebalancequantity
					+ ",sysdate from dual";
			EntityManager.updateOrdelete(sql);
		} else {
			updOutBarcodeQuantity(productid, batch, warehouseid, warehousebit,
					billcode, quantity);
		}

	}
	/**
	 * 增加条码盘点入库台账
	 */
	public void addInBarcodeStockWasteBook(String productid, String batch,
			Double quantity, String warehouseid, String warehousebit,
			String billcode, String memo) throws Exception {

		Long id = Long.valueOf(MakeCode.getExcIDByRandomTableName(
				"stock_waste_book", 0, ""));
		String cfqsql = "select sum(ps.stockpile+ps.prepareout) from ProductStockpile as ps where ps.productid='"
				+ productid
				+ "' and batch='"
				+ batch
				+ "' and warehouseid='"
				+ warehouseid + "' and warehousebit='" + warehousebit + "'";
		Double cyclebalancequantity = EntityManager.getdoubleSum(cfqsql);
		Double cyclefirstquantity = cyclebalancequantity
				- quantity;
		
		if (!isExist(productid, batch, warehouseid, warehousebit, billcode)) {
			String sql = "insert into stock_waste_book(id,productid,batch,warehouseid,warehousebit,billcode,memo,cyclefirstquantity,cycleinquantity,cycleoutquantity,cyclebalancequantity,recorddate) select "
					+ id
					+ ",'"
					+ productid
					+ "','"
					+ batch
					+ "','"
					+ warehouseid
					+ "','"
					+ warehousebit
					+ "','"
					+ billcode
					+ "','"
					+ memo
					+ "',"
					+ cyclefirstquantity
					+ ","
					+ quantity
					+ ",0," + cyclebalancequantity + ",sysdate from dual";
			EntityManager.updateOrdelete(sql);
		} else {
			updInBarcodeQuantity(productid, batch, warehouseid, warehousebit,
					billcode, quantity);
		}

	}
	
	/**
	 * 增加出库台账
	 * Create Time 2014-10-16 上午11:50:19 
	 * @param productid
	 * @param batch
	 * @param quantity
	 * @param warehouseid
	 * @param warehousebit
	 * @param billcode
	 * @param memo
	 * @throws Exception
	 */
	public void addOutStockWasteBook(String productid, String batch,
			Double quantity, String warehouseid, String warehousebit,
			String billcode, String memo) throws Exception {

		Long id = Long.valueOf(MakeCode.getExcIDByRandomTableName("stock_waste_book", 0, ""));
		String cfqsql = "select sum(ps.stockpile+ps.prepareout) from ProductStockpile as ps where ps.productid='"
				+ productid
				+ "' and batch='"
				+ batch
				+ "' and warehouseid='"
				+ warehouseid + "' and warehousebit='" + warehousebit + "'";
		Double cyclefirstquantity = EntityManager.getdoubleSum(cfqsql)
				+ quantity;
		Double cyclebalancequantity = cyclefirstquantity - quantity;
		if (!isExist(productid, batch, warehouseid, warehousebit, billcode)) {
			String sql = "insert into stock_waste_book(id,productid,batch,warehouseid,warehousebit,billcode,memo,cyclefirstquantity,cycleinquantity,cycleoutquantity,cyclebalancequantity,recorddate) select "
					+ id
					+ ",'"
					+ productid
					+ "','"
					+ batch
					+ "','"
					+ warehouseid
					+ "','"
					+ warehousebit
					+ "','"
					+ billcode
					+ "','"
					+ memo
					+ "',"
					+ cyclefirstquantity
					+ ",0,"
					+ quantity
					+ ","
					+ cyclebalancequantity
					+ ",sysdate from dual";
			EntityManager.updateOrdelete(sql);
		} else {
			updOutQuantity(productid, batch, warehouseid, warehousebit,
					billcode, quantity);
		}

	}
	/**
	 * 增加入库台账
	 * Create Time 2014-10-16 上午11:50:19 
	 * @param productid
	 * @param batch
	 * @param quantity
	 * @param warehouseid
	 * @param warehousebit
	 * @param billcode
	 * @param memo
	 * @throws Exception
	 */
	public void addInStockWasteBook(String productid, String batch,
			Double quantity, String warehouseid, String warehousebit,
			String billcode, String memo) throws Exception {

		Long id = Long.valueOf(MakeCode.getExcIDByRandomTableName(
				"stock_waste_book", 0, ""));
		String cfqsql = "select sum(ps.stockpile+ps.prepareout) from ProductStockpile as ps where ps.productid='"
				+ productid
				+ "' and batch='"
				+ batch
				+ "' and warehouseid='"
				+ warehouseid + "' and warehousebit='" + warehousebit + "'";
		Double cyclefirstquantity = EntityManager.getdoubleSum(cfqsql)
				- quantity;
		Double cyclebalancequantity = cyclefirstquantity + quantity;
		if (!isExist(productid, batch, warehouseid, warehousebit, billcode)) {
			String sql = "insert into stock_waste_book(id,productid,batch,warehouseid,warehousebit,billcode,memo,cyclefirstquantity,cycleinquantity,cycleoutquantity,cyclebalancequantity,recorddate) select "
					+ id
					+ ",'"
					+ productid
					+ "','"
					+ batch
					+ "','"
					+ warehouseid
					+ "','"
					+ warehousebit
					+ "','"
					+ billcode
					+ "','"
					+ memo
					+ "',"
					+ cyclefirstquantity
					+ ","
					+ quantity
					+ ",0," + cyclebalancequantity + ",sysdate from dual";
			EntityManager.updateOrdelete(sql);
		} else {
			updInQuantity(productid, batch, warehouseid, warehousebit,
					billcode, quantity);
		}

	}

	public List getStockWasteBook(String whereSql) throws Exception {
		List sb = new ArrayList();

		String sql = "select swb.id,swb.productid,swb.batch,swb.warehouseid,swb.billcode,swb.memo,swb.cyclefirstquantity,swb.cycleinquantity,swb.cycleoutquantity,swb.cyclebalancequantity,substring(CONVERT(varchar, swb.recorddate, 120 ),1,10),substring(CONVERT(varchar, swb.recorddate, 120 ),1,7) from Stock_Waste_Book as swb "
				+ whereSql
				+ " union select 0,'0','',0,'','本月合计',0,sum(swb.cycleinquantity),sum(swb.cycleoutquantity),0,'',substring(CONVERT(varchar, swb.recorddate, 120 ),1,7) from Stock_Waste_Book as swb "
				+ whereSql
				+ " group by substring(CONVERT(varchar, swb.recorddate, 120 ),1,7) union select 0,'0's,'',0,'','本年累计',0,sum(swb.cycleinquantity),sum(swb.cycleoutquantity),0,'',substring(CONVERT(varchar, swb.recorddate, 120 ),1,4)+'-12' from Stock_Waste_Book as swb "
				+ whereSql
				+ " group by substring(CONVERT(varchar, swb.recorddate, 120 ),1,4)+'-12' order by substring(CONVERT(varchar, swb.recorddate, 120 ),1,7),swb.id ";
		ResultSet rs = EntityManager.query(sql);
		if (rs.getRow() > 0) {
			do {
				StockWasteBookObject swb = new StockWasteBookObject();
				swb.setId(rs.getLong(1));
				swb.setProductid(rs.getString(2));
				swb.setBatch(rs.getString(3));
				swb.setWarehouseid(rs.getLong(4));
				swb.setBillcode(rs.getString(5));
				swb.setMemo(rs.getString(6));
				swb.setCyclefirstquantity(rs.getDouble(7));
				swb.setCycleinquantity(rs.getDouble(8));
				swb.setCycleoutquantity(rs.getDouble(9));
				swb.setCyclebalancequantity(rs.getDouble(10));
				swb.setRecorddate(rs.getString(11));
				sb.add(swb);

			} while (rs.next());
		}
		rs.close();
		return sb;
	}

	public int getStockWasteBookByProductID(String pid) throws Exception {
		String sql = "select count(id) from StockWasteBook where productid='"
				+ pid + "'";
		return EntityManager.getRecordCount(sql);
	}

	public int getStockWasteBookByWarehousebit(String wid, String warehousebit)
			throws Exception {
		String sql = "select count(id) from StockWasteBook where warehouseid='"
				+ wid + "' and warehousebit='" + warehousebit + "'";
		return EntityManager.getRecordCount(sql);
	}

	public List getStockWasteBookTotal(HttpServletRequest request,
			int pagesize, String whereSql) throws Exception {
		String sql = "select s.warehouseid,s.productid,p.productcodedef as synid,p.productname,p.specmode,s.batch,p.countunit, p.nccode,sum(s.cycleinquantity) as inquantity, sum(s.cycleoutquantity) as outquantity,sum(s.cycleinquantity-s.cycleoutquantity) as quantity,pstr.sortname "
				+ " from Stock_Waste_Book as s, Product as p,product_struct pstr  "
				+ whereSql
				+ " and  pstr.StructCode=p.psid "
				+ " group by s.warehouseid,s.productid,p.ProductCodeDef,p.productname,p.specmode,s.batch,p.countunit,p.nccode,pstr.sortname  ";
		return PageQuery.jdbcSqlserverQuery(request, "warehouseid", sql,
				pagesize);
	}

	public List getStockWasteBookTotalNoBatch(HttpServletRequest request,
			int pagesize, String whereSql) throws Exception {
		String sql = "select s.warehouseid,s.productid,p.productcodedef as synid,p.productname,p.specmode,p.countunit, p.nccode,sum(s.cycleinquantity) as inquantity, sum(s.cycleoutquantity) as outquantity,sum(s.cycleinquantity-s.cycleoutquantity) as quantity,pstr.sortname "
				+ " from Stock_Waste_Book as s, Product as p ,product_struct pstr "
				+ whereSql
				+ " and  pstr.StructCode=p.psid "
				+ " group by s.warehouseid,s.productid,p.ProductCodeDef,p.productname,p.specmode,p.countunit,p.nccode,pstr.sortname ";
		return PageQuery.jdbcSqlserverQuery(request, "warehouseid", sql,
				pagesize);
	}

	public List getStockWasteBookTotal(HttpServletRequest request,
			int pagesize, String whereSql, String orderby) throws Exception {
		String sql = "select s.warehouseid,s.productid,p.productcodedef as synid,p.productname,p.specmode,s.batch,p.countunit, p.nccode,sum(s.cycleinquantity) as inquantity, sum(s.cycleoutquantity) as outquantity,sum(s.cycleinquantity-s.cycleoutquantity) as quantity,pstr.sortname "
				+ " from Stock_Waste_Book s, Product  p,product_struct pstr  "
				+ whereSql
				+ " and  pstr.StructCode=p.psid "
				+ " group by s.warehouseid,s.productid,p.ProductCodeDef,p.productname,p.specmode,s.batch,p.countunit,p.nccode,pstr.sortname  ";
		if (!StringUtil.isEmpty(orderby)) {
			return PageQuery
					.jdbcSqlserverQuery(request, orderby, sql, pagesize);
		} else {
			return PageQuery.jdbcSqlserverQuery(request, "warehouseid", sql,
					pagesize);
		}
	}

	public List getStockWasteBookTotalNoBatch(HttpServletRequest request,
			int pagesize, String whereSql, String orderby) throws Exception {
		String sql = "select s.warehouseid,s.productid,p.productcodedef as synid,p.productname,p.specmode,p.countunit, p.nccode,sum(s.cycleinquantity) as inquantity, sum(s.cycleoutquantity) as outquantity,sum(s.cycleinquantity-s.cycleoutquantity) as quantity,pstr.sortname "
				+ " from Stock_Waste_Book  s, Product  p ,product_struct pstr "
				+ whereSql
				+ " and  pstr.StructCode=p.psid "
				+ " group by s.warehouseid,s.productid,p.ProductCodeDef,p.productname,p.specmode,p.countunit,p.nccode,pstr.sortname ";
		if (!StringUtil.isEmpty(orderby)) {
			return PageQuery
					.jdbcSqlserverQuery(request, orderby, sql, pagesize);
		} else {
			return PageQuery.jdbcSqlserverQuery(request, "warehouseid", sql,
					pagesize);
		}
	}

	public List getStockWasteBookTotal2(String whereSql) throws Exception {
		String sql = "select s.warehouseid,s.productid,p.productcodedef as synid,p.productname,p.specmode,s.batch,p.sunit, p.nccode, sum(s.cycleinquantity) as inquantity, sum(s.cycleoutquantity) as outquantity,sum(s.cycleinquantity-s.cycleoutquantity) as quantity,pstr.sortname "
				+ " from Stock_Waste_Book as s, Product as p,product_struct pstr  "
				+ whereSql
				+ " and  pstr.StructCode=p.psid "
				+ " group by s.warehouseid,s.productid,p.ProductCodeDef,p.productname,p.specmode,s.batch,p.sunit, p.nccode,pstr.sortname  "
				+ " order by warehouseid";
		return EntityManager.jdbcquery(sql);
	}

	public List getStockWasteBookTotalNoBatch(String whereSql) throws Exception {
		String sql = "select s.warehouseid,s.productid,p.productcodedef as synid,p.productname,p.specmode,p.countunit, p.nccode,sum(s.cycleinquantity) as inquantity, sum(s.cycleoutquantity) as outquantity,sum(s.cycleinquantity-s.cycleoutquantity) as quantity,pstr.sortname "
				+ " from Stock_Waste_Book as s, Product as p ,product_struct pstr "
				+ whereSql
				+ " and  pstr.StructCode=p.psid "
				+ " group by s.warehouseid,s.productid,p.ProductCodeDef,p.productname,p.specmode,p.countunit,p.nccode,pstr.sortname "
				+ " order by warehouseid";
		return EntityManager.jdbcquery(sql);
	}

	public List getStockWasteBookTotal(String whereSql) throws Exception {
		String sql = "select sum(s.cycleinquantity) as inquantity, sum(s.cycleoutquantity) as outquantity,sum(s.cycleinquantity-s.cycleoutquantity) as quantity"
				+ " from Stock_Waste_Book as s, Product as p " + whereSql;
		return EntityManager.jdbcquery(sql);
	}

	private boolean isExist(String productid, String batch, String warehouseid,
			String warehousebit, String billcode) throws Exception {
		String sql = " select count(id) from StockWasteBook where productid='"
				+ productid + "' and batch='" + batch + "' and warehouseid='"
				+ warehouseid + "' and warehousebit='" + warehousebit
				+ "' and billcode='" + billcode + "' ";
		int count = EntityManager.getRecordCount(sql);
		if (count > 0) {
			return true;
		}
		return false;
	}
	
	private void updInBarcodeQuantity(String productid, String batch,
			String warehouseid, String warehousebit, String billcode,
			double quantity) throws Exception {
		String sql = " update Stock_Waste_Book set cycleinquantity=cycleinquantity+ "
				+ quantity
				+ ",cyclebalancequantity=cyclebalancequantity+ "
				+ quantity
				+ " where productid='"
				+ productid
				+ "' and batch='"
				+ batch
				+ "' and warehouseid='"
				+ warehouseid
				+ "' and warehousebit='"
				+ warehousebit
				+ "' and billcode='"
				+ billcode + "' ";
		EntityManager.updateOrdelete(sql);
	}

	private void updOutBarcodeQuantity(String productid, String batch,
			String warehouseid, String warehousebit, String billcode,
			double quantity) throws Exception {
		String sql = " update Stock_Waste_Book set cycleoutquantity=cycleoutquantity+ "
				+ quantity
				+ ",cyclebalancequantity=cyclebalancequantity- "
				+ quantity
				+ " where productid='"
				+ productid
				+ "' and batch='"
				+ batch
				+ "' and warehouseid='"
				+ warehouseid
				+ "' and warehousebit='"
				+ warehousebit
				+ "' and billcode='"
				+ billcode + "' ";
		EntityManager.updateOrdelete(sql);
	}
	
	
	
	
	
	

	private void updInQuantity(String productid, String batch,
			String warehouseid, String warehousebit, String billcode,
			double quantity) throws Exception {
		String sql = " update Stock_Waste_Book set cycleinquantity=cycleinquantity+ "
				+ quantity
				+ ",cyclebalancequantity=cyclebalancequantity+ "
				+ quantity
				+ " where productid='"
				+ productid
				+ "' and batch='"
				+ batch
				+ "' and warehouseid='"
				+ warehouseid
				+ "' and warehousebit='"
				+ warehousebit
				+ "' and billcode='"
				+ billcode + "' ";
		EntityManager.updateOrdelete(sql);
	}

	private void updOutQuantity(String productid, String batch,
			String warehouseid, String warehousebit, String billcode,
			double quantity) throws Exception {
		String sql = " update Stock_Waste_Book set cycleoutquantity=cycleoutquantity+ "
				+ quantity
				+ ",cyclebalancequantity=cyclebalancequantity- "
				+ quantity
				+ " where productid='"
				+ productid
				+ "' and batch='"
				+ batch
				+ "' and warehouseid='"
				+ warehouseid
				+ "' and warehousebit='"
				+ warehousebit
				+ "' and billcode='"
				+ billcode + "' ";
		EntityManager.updateOrdelete(sql);
	}

	public void delStockWasteBook(String productid, String batch,
			String warehouseid, String warehousebit, String billcode)
			throws Exception {
		String sql = " delete from Stock_Waste_Book " + " where productid='"
				+ productid + "' and batch='" + batch + "' and warehouseid='"
				+ warehouseid + "' and warehousebit='" + warehousebit
				+ "' and billcode='" + billcode + "' ";
		EntityManager.updateOrdelete(sql);
	}

	public List<Map> getStockWasteBookByStartDateAndEndDate(String startDate,
			String endDate) throws Exception {
		String sql = "select productid, warehouseid, batch, sum(cycleinquantity) cycleinquantity, sum(cycleoutquantity) cycleoutquantity from stock_waste_book where RECORDDATE>=to_date('"
				+ startDate
				+ "','yyyy-mm-dd hh24:mi:ss') and RECORDDATE<to_date('"
				+ endDate
				+ "','yyyy-mm-dd hh24:mi:ss')  group by productid, warehouseid, batch";
		return EntityManager.jdbcquery(sql);
	}

	public StockWasteBook getStockWastBookByBillCodeAndBatch(String billno,
			String productId, String noBatch) {
		String hql = "from StockWasteBook swb where swb.productid='"+productId+"' and swb.batch = '"+noBatch+"' and billCode=(select id from TakeTicket where billno = '"+billno+"')";
		return (StockWasteBook)EntityManager.find(hql);
	}

	/**
	 * 根据taketicket id 查询出库台账信息
	 * @param billno
	 * @return
	 */
	public StockWasteBook getStockWastBookByBillCode(String billno) {
		String hql = "from StockWasteBook swb where swb.billcode= '"+billno+"'";
		return (StockWasteBook)EntityManager.find(hql);
	}
	
	/**
	 * 更新出库台账
	 * @param swb
	 * @throws Exception
	 */
	public void updStockWasteBook(StockWasteBook swb) throws Exception {
		EntityManager.update(swb);
	}
	
	/**
	 * 删除出库台账信息
	 * 
	 * @param billcode
	 * @throws Exception
	 */
	public void deleteStockWasteBookByBillcode(String billcode) throws Exception{
		String sql = "DELETE FROM STOCK_WASTE_BOOK where billcode='"+billcode + "'";
		EntityManager.executeUpdate(sql);
	}
}
