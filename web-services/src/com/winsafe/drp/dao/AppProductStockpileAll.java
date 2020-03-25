package com.winsafe.drp.dao;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;


import com.winsafe.common.util.StringUtil;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.pager2.PageQuery;

public class AppProductStockpileAll {

	private AppFUnit appFunit = new AppFUnit();

	public List getProductStockpilePresent(HttpServletRequest request, int pagesize,
			String pWhereClause) throws Exception {
		String sql = "select ps.productid,p.productname,p.specmode,ps.countunit,p.sunit,ps.warehouseid,ps.batch,ps.stockpile,ps.prepareout "
				+ "from ProductStockpileAll ps, Warehouse w, Product p  "
				+ pWhereClause
				+ " order by ps.productid desc";
		return PageQuery.hbmQuery(request, sql, pagesize);
	}

	public void updProductStockpileAllUnitidByProductID(String pid, Integer unitid)
			throws Exception {
		String sql = "update Product_Stockpile_All set CountUnit = " + unitid
				+ " where productid='" + pid + "' ";
		EntityManager.updateOrdelete(sql);
	}

	public List getProductStockpileGroup(HttpServletRequest request, int pagesize,
			String pWhereClause) throws Exception {
		String sql = " select p.id,p.productname,p.specmode,p.sunit, sum(ps.stockpile) as stockpile from product_stockpile_all as ps, product as p "
				+ pWhereClause + " group by p.id,p.productname,p.specmode,p.sunit  ";
		return PageQuery.jdbcSqlserverQuery(request, sql, pagesize);
	}

	public List getProductStockpileGroupNoBatch(HttpServletRequest request, int pagesize,
			String pWhereClause) throws Exception {
		String sql = " select max(p.id)  productid,max(p.productname) productname,max(p.specmode) specmode,max(p.sunit) unitid,max(p.mCode)  mCode, sum(ps.stockpile)  stockpile from product_stockpile_all  ps, product  p "
				+ pWhereClause + " group by p.productname  having  sum(ps.stockpile) > 0";
		return PageQuery.jdbcSqlserverQuery(request, "productid", sql, pagesize);
	}

	public List getProductStockpileOrderbyPy(HttpServletRequest request, int pagesize,
			String pWhereClause) throws Exception {
		String sql = " select max(p.id)  productid,max(p.productname) productname,max(p.specmode) specmode,max(p.sunit) unitid,max(p.mCode)  mCode, sum(ps.stockpile)  stockpile,max(p.packSizeName) packSizeName from product_stockpile_all  ps, product  p "
				+ pWhereClause
				+ " group by p.id  having sum(ps.stockpile)>0 order by NLSSORT(productname,'NLS_SORT = SCHINESE_PINYIN_M')";
		return PageQuery.jdbcSqlserverQuery(request,
				"NLSSORT(productname,'NLS_SORT = SCHINESE_PINYIN_M')", sql, pagesize);
	}

	public List getProductStockpileAllNewTwo(HttpServletRequest request, int pagesize,
			String pWhereClause) throws Exception {
		String hql = " select p, ps from ProductStockpileAll as ps,Product as p " + pWhereClause
				+ " order by ps.productid desc, ps.id ";
		return PageQuery.hbmQuery(request, hql, pagesize);
	}

	public ProductStockpileAll getProductStockpileAllByID(Long id) throws Exception {
		String sql = " from ProductStockpileAll as ps where ps.id=" + id;
		return (ProductStockpileAll) EntityManager.find(sql);
	}

	public List<ProductStockpileAll> getProductStockpileAllByIDs(String ids) throws Exception {
		String sql = " from ProductStockpileAll as ps where ps.id in (" + ids + ")";
		return (List<ProductStockpileAll>) EntityManager.getAllByHql(sql);
	}

	public ProductStockpileAll getProductStockpileAllByProductIDUnitWid(String productid,
			int unitid, String wid) throws Exception {
		String sql = " from ProductStockpileAll as ps where ps.productid='" + productid
				+ "' and ps.countunit=" + unitid + " and ps.warehouseid='" + wid + "' ";
		return (ProductStockpileAll) EntityManager.find(sql);
	}

	public ProductStockpileAll getBatchQuantityByPidWid(String productid, String wid)
			throws Exception {
		String sql = "  from ProductStockpileAll where productid='" + productid + "' "
				+ " and warehouseid='" + wid + "' and stockpile>0 order by batch ";
		return (ProductStockpileAll) EntityManager.find(sql);
	}

	public ProductStockpileAll getProductStockpileAllByPidWid(String productid, String wid,
			String batch) throws Exception {
		String sql = " from ProductStockpileAll as ps where ps.productid='" + productid
				+ "' and ps.batch='" + batch + "' and ps.warehouseid='" + wid + "' ";
		return (ProductStockpileAll) EntityManager.find(sql);
	}

	public double getStockpileByPidWid(String productid, String wid, String batch) throws Exception {
		String sql = " select ps.stockpile from ProductStockpileAll as ps where ps.productid='"
				+ productid + "' and ps.batch='" + batch + "' and ps.warehouseid='" + wid + "' ";
		return EntityManager.getdoubleSum(sql);
	}

	public ProductStockpileAll getProductStockpileAllByPIDWID(String pWhereClause) throws Exception {
		String sql = " from ProductStockpileAll as ps " + pWhereClause + " ";
		return (ProductStockpileAll) EntityManager.find(sql);
	}

	public List getStockStat(HttpServletRequest request, int pagesize, String pWhereClause)
			throws Exception {
		String hql = "select ps, p,pstr.sortname from ProductStockpileAll as ps,WarehouseVisit as wv, Product as p,ProductStruct pstr "
				+ pWhereClause + " order by ps.productid desc";
		return PageQuery.hbmQuery(request, hql, pagesize);
	}

	public List getStockStat(HttpServletRequest request, int pagesize, String pWhereClause,
			String orderby) throws Exception {
		String hql = "select ps, p,pstr.sortname from ProductStockpileAll as ps, Product as p,ProductStruct pstr  "
				+ pWhereClause;

		if (!StringUtil.isEmpty(orderby)) {
			hql += " order by " + orderby;
		}
		return PageQuery.hbmQuery(request, hql, pagesize);
	}

	public List getProductStockpile(HttpServletRequest request, String pWhereClause)
			throws Exception {
		String sql = " select p.productname,PS.WAREHOUSEID ID,ps.warehouseid,ps.productid,p.mcode,p.specmode,p.boxquantity,sum(ps.stockpile+ps.prepareout) as stockpile, ps.batch  from product_stockpile_all  ps, product  p, product_struct  pstr "
				+ pWhereClause
				+ " group by p.productname,ps.warehouseid,ps.productid,p.mcode,p.specmode,p.boxquantity, ps.batch " +
						" order by NLSSORT(p.productname,'NLS_SORT = SCHINESE_PINYIN_M'),p.mcode,ps.batch ";
		return EntityManager.jdbcquery(sql);
//		return PageQuery.jdbcSqlserverQuery(request, sql);
	}

	// 按仓库，产品汇总
	public List getStockStatNoBatch(HttpServletRequest request, int pagesize, String pWhereClause)
			throws Exception {
		String sql = "select p.nccode,p.productname,p.specmode,p.countunit,sum(ps.prepareout) as preoutnum,ps.warehouseid, w.WarehouseName,ps.productid,pstr.sortname  "
				+ " from Product_Stockpile_All as ps,Warehouse_Visit as wv, Product as p,warehouse as w,product_struct as pstr "
				+ pWhereClause
				+ " and w.id=ps.warehouseid and pstr.StructCode=p.PSID   "
				+ "group by ps.productid,ps.warehouseid,p.productname,p.specmode,p.countunit,p.nccode, w.WarehouseName,pstr.sortname  "
				+ "having sum(ps.prepareout) >0   ";
		// return PageQuery.hbmQuery(request, hql, pagesize);
		return PageQuery.jdbcSqlserverQuery(request, "warehouseid", sql, pagesize);
	}

	public List getStockStat(String pWhereClause) throws Exception {
		// String hql =
		// "select ps, p,pstr.sortname from ProductStockpileAll as ps,WarehouseVisit as wv, Product as p,ProductStruct pstr  "
		// + pWhereClause
		// + " order by ps.productid desc, ps.id desc ";

		String hql = "select ps, p,pstr.sortname from ProductStockpileAll as ps, Product as p,ProductStruct pstr  "
				+ pWhereClause;
		return EntityManager.getAllByHql(hql);
	}

	public List getCheckStockpileEmpty(String pWhereClause) throws Exception {
		String sql = "select ps.id,ps.productid,p.productname,p.specmode,ps.countunit,ps.batch,ps.barcode,ps.warehouseid,ps.stockpile + ps.prepareout,ps.prepareout,ps.islock from ProductStockpileAll as ps,Product as p "
				+ pWhereClause + " order by ps.productid desc, ps.id ";
		return EntityManager.getAllByHql(sql);
	}

	public double getProductStockpileAllByProductID(String productid, Long wid) throws Exception {
		String sql = "select sum(ps.stockpile) from ProductStockpileAll as ps where ps.productid='"
				+ productid + "' and ps.warehouseid='" + wid + "' ";
		return EntityManager.getdoubleSum(sql);
	}

	public double getProductStockpileAllByProductIDWIDBatch(String productid, String wid,
			String batch) throws Exception {
		String sql = "select sum(ps.stockpile) from ProductStockpileAll as ps where ps.productid='"
				+ productid + "' and ps.warehouseid=" + wid + " and ps.batch='" + batch + "'";
		return EntityManager.getdoubleSum(sql);
	}

	public List getProductStockpileAllByProductIDWID(String productid, Long wid) throws Exception {
		String sql = "select ps.id,ps.productid,ps.psproductname,ps.psspecmode,ps.countunit,ps.batch,ps.warehouseid,ps.stockpile,ps.prepareout,ps.islock from ProductStockpileAll as ps where ps.productid='"
				+ productid + "' and ps.warehouseid=" + wid + " and ps.stockpile>0 ";
		return EntityManager.getAllByHql(sql);
	}

	public void addProductStockpileAll(Object ProductStockpileAll) throws Exception {
		EntityManager.save(ProductStockpileAll);
	}

	public void addProductByPurchaseIncome(ProductStockpile ps) throws Exception {
		String sql = "insert into Product_Stockpile_All(id,productid,countunit,batch,producedate,vad,cost,warehouseid,verifyStatus,stockpile,prepareout,islock,makedate)  select "
				+ ps.getId()
				+ ",'"
				+ ps.getProductid()
				+ "',"
				+ ps.getCountunit()
				+ ",'"
				+ ps.getBatch()
				+ "','"
				+ ps.getProducedate()
				+ "','"
				+ ps.getVad()
				+ "',"
				+ "0,'"
				+ ps.getWarehouseid()
				+ "','"
				+ ps.getVerifyStatus()
				+ "',"
				+ "0,0,0,sysdate from dual where not exists(select id from Product_Stockpile_All where productid='"
				+ ps.getProductid()
				+ "' and batch='"
				+ ps.getBatch()
				+ "' and warehouseid='"
				+ ps.getWarehouseid() + "')";
		EntityManager.updateOrdelete(sql);

	}

	/**
	 * 默认verifyStatus检验状态为合格
	 * 
	 * @param ps
	 * @throws Exception
	 */
	public void addProductByPurchaseIncome2(ProductStockpile ps) throws Exception {
		String sql = "insert into Product_Stockpile_All(id,productid,countunit,batch,producedate,vad,cost,warehouseid,stockpile,prepareout,islock,makedate,verifyStatus)  select "
				+ ps.getId()
				+ ",'"
				+ ps.getProductid()
				+ "',"
				+ ps.getCountunit()
				+ ",'"
				+ ps.getBatch()
				+ "','"
				+ ps.getProducedate()
				+ "','"
				+ ps.getVad()
				+ "',"
				+ "0,'"
				+ ps.getWarehouseid()
				+ "',"
				+ "0,0,0,sysdate,1 from dual  where not exists(select id from Product_Stockpile_All where productid='"
				+ ps.getProductid()
				+ "' and batch='"
				+ ps.getBatch()
				+ "' and warehouseid='"
				+ ps.getWarehouseid() + "')";
		EntityManager.updateOrdelete(sql);

	}

	public void prepareOut(String productid, int unitid, String warehouseid, String batch,
			Double quantity) throws Exception {
		// quantity = appFunit.getQuantity(productid, unitid, quantity);
		this.prepareOut(productid, warehouseid, batch, quantity);
	}

	public void prepareOut(String productid, String warehouseid, String batch, Double quantity)
			throws Exception {
		// quantity = appFunit.getQuantity(productid, 2, quantity);
		String sql = "update Product_Stockpile_All set stockpile=stockpile - " + quantity
				+ ", prepareout=prepareout + " + quantity + " where productid='" + productid
				+ "' and warehouseid='" + warehouseid + "'  and batch='" + batch + "'";
		EntityManager.updateOrdelete(sql);
	}

	public void updateCost(String productid, String warehouseid, String batch, Double cost)
			throws Exception {
		String sql = "update Product_Stockpile_All set cost=" + cost + " where productid='"
				+ productid + "' and warehouseid='" + warehouseid + "' and batch='" + batch + "'";
		EntityManager.updateOrdelete(sql);
	}

	public void freeStockpile(String productid, int unitid, String warehouseid, String batch,
			Double quantity) throws Exception {

		// quantity = appFunit.getQuantity(productid, unitid, quantity);
		this.freeStockpile(productid, warehouseid, batch, quantity);
	}

	public void freeStockpile(String productid, String warehouseid, String batch, Double quantity)
			throws Exception {
		// quantity = appFunit.getQuantity(productid, 2, quantity);
		String sql = "update Product_Stockpile_All set stockpile=stockpile + " + quantity
				+ ", prepareout=prepareout - " + quantity + " where productid='" + productid
				+ "' and warehouseid='" + warehouseid + "' and batch='" + batch + "'";
		EntityManager.updateOrdelete(sql);

	}

	public void outProductStockpileAll(String productid, String batch, Double quantity,
			String warehouseid, String billcode, String memo) throws Exception {
		String sql = "update Product_Stockpile_All set stockpile=stockpile - " + quantity
				+ " where productid='" + productid + "' and batch='" + batch
				+ "' and warehouseid='" + warehouseid + "' ";
		EntityManager.updateOrdelete(sql);
	}

	public void inBarcodeProductStockpileAll(String productid, String batch, Double quantity,
			String warehouseid, String billcode, String memo) throws Exception {
		String sql = "update Product_Stockpile_All set stockpile=" + quantity
				+ " where productid='" + productid + "' and batch='" + batch
				+ "' and warehouseid='" + warehouseid + "' ";
		EntityManager.updateOrdelete(sql);
	}

	public void outBarcodeProductStockpileAll(String productid, String batch, Double quantity,
			String warehouseid, String billcode, String memo) throws Exception {
		String sql = "update Product_Stockpile_All set stockpile=" + quantity
				+ " where productid='" + productid + "' and batch='" + batch
				+ "' and warehouseid='" + warehouseid + "' ";
		EntityManager.updateOrdelete(sql);
	}

	/**
	 * 扣减预出库
	 * 
	 * @param warehouseid
	 * @param productid
	 * @param unitid
	 * @param batch
	 * @param quantity
	 * @throws Exception
	 */
	public void outPrepareout(String warehouseid, String productid, Integer unitid, String batch,
			Double quantity) throws Exception {

		quantity = appFunit.getQuantity(productid, unitid, quantity);
		outPrepareout(warehouseid, productid, batch, quantity);
	}

	public void outPrepareout(String warehouseid, String productid, String batch, Double quantity)
			throws Exception {

		String sql = "update Product_Stockpile_All set prepareout = prepareout - " + quantity
				+ " where productid='" + productid + "' and batch='" + batch
				+ "' and warehouseid='" + warehouseid + "'";
		EntityManager.updateOrdelete(sql);
	}

	public void returnPrepareout(String warehouseid, String productid, Integer unitid,
			String batch, Double quantity) throws Exception {

		quantity = appFunit.getQuantity(productid, unitid, quantity);
		returnPrepareout(warehouseid, productid, batch, quantity);
	}

	public void returnPrepareout(String warehouseid, String productid, String batch, Double quantity)
			throws Exception {

		String sql = "update Product_Stockpile_All set prepareout = prepareout + " + quantity
				+ " where productid='" + productid + "' and batch='" + batch
				+ "' and warehouseid='" + warehouseid + "'";
		EntityManager.updateOrdelete(sql);

	}

	public void returnOutProductStockpileAll(String productid, String batch, Double quantity,
			String warehouseid, String billcode, String memo) throws Exception {
		String sql = "update Product_Stockpile_All set stockpile=stockpile + " + quantity
				+ " where productid='" + productid + "' and batch='" + batch
				+ "' and warehouseid='" + warehouseid + "'";
		EntityManager.updateOrdelete(sql);
		// aswb.addOutStockWasteBook(productid, batch, quantity, warehouseid,
		// warehousebit,billcode,memo);
	}

	public void inProductStockpileAll(String productid, String batch, Double quantity,
			String warehouseid, String billcode, String memo) throws Exception {
		String sql = "update Product_Stockpile_All set stockpile=stockpile + " + quantity
				+ " where productid='" + productid + "' and batch='" + batch
				+ "' and warehouseid='" + warehouseid + "' ";
		EntityManager.updateOrdelete(sql);
	}

	public void returninProductStockpileAll(String productid, String batch, Double quantity,
			String warehouseid, String billcode, String memo) throws Exception {

		String sql = "update Product_Stockpile_All set stockpile=stockpile - " + quantity
				+ " where productid='" + productid + "' and batch='" + batch
				+ "' and warehouseid='" + warehouseid + "'";
		EntityManager.updateOrdelete(sql);
		// aswb.addInStockWasteBook(productid, batch, quantity,
		// warehouseid,warehousebit, billcode,memo);

	}

	public void adjustProductStockpileAll(String productid, String sign, int quantity,
			String warehouseid) throws Exception {

		String sql = "update Product_Stockpile_All set stockpile=stockpile " + sign + "" + quantity
				+ " where productid='" + productid + "' and warehouseid=" + warehouseid;
		EntityManager.updateOrdelete(sql);

	}

	public double getProductStockpileAllByPIDWID(String productid, String warehouseid)
			throws Exception {
		String sql = "select sum(ps.stockpile) from ProductStockpileAll as ps where ps.productid='"
				+ productid + "' and ps.warehouseid='" + warehouseid + "' ";
		return EntityManager.getdoubleSum(sql);
	}

	public double getProductStockpileAllByPIDWID(String productid, String warehouseid, String batch)
			throws Exception {
		String sql = "select sum(ps.stockpile) from ProductStockpileAll as ps where ps.productid='"
				+ productid + "' and ps.warehouseid='" + warehouseid + "' and ps.batch='" + batch
				+ "'";
		return EntityManager.getdoubleSum(sql);
	}

	public List getProductStockpileAllByPID(String pid) throws Exception {
		String sql = "select p.stockpile,p.warehouseid from ProductStockpileAll as p where p.productid ='"
				+ pid + "'";
		return EntityManager.getAllByHql(sql);
	}

	public Double getProductStockpileAllByPIDUIdWID(String pid, Integer userid, String wid)
			throws Exception {
		String sql = "select sum(ps.stockpile) from ProductStockpileAll as ps,WarehouseVisit as wv where ps.productid ='"
				+ pid
				+ "' and ps.warehouseid=wv.wid and  wv.userid="
				+ userid
				+ " and ps.warehouseid= '" + wid + "' ";
		return EntityManager.getdoubleSum(sql);
	}

	public List getProductStockpileAllByPIDUId(String pid, int userid) throws Exception {
		String sql = "select sum(p.stockpile),p.warehouseid,p.batch from ProductStockpileAll as p,RuleUserWh as rw where p.productid ='"
				+ pid
				+ "' and p.warehouseid=rw.warehouseId and rw.userId="
				+ userid
				+ " group by p.warehouseid,p.batch order by p.warehouseid";
		return EntityManager.getAllByHql(sql);
	}

	public List getProductStockpileWithWbByPIDUId(String pid, int userid) throws Exception {
		String sql = "select distinct(psp.warehouseid),bw.bitName  from ProductStockpile as psp,WarehouseVisit as wv,WarehouseBit  as wb  where p.productid ='"
				+ pid
				+ "' and psp.warehouseid=wv.wid and wv.userid="
				+ userid
				+ " and wb.wid = psp.warehouseid   and  wb.wbid = psp.warehousebit ";
		return EntityManager.getAllByHql(sql);
	}

	public void IsLock(String productid, String batch, Long warehouseid) throws Exception {

		String sql = "update Product_Stockpile_All set islock=1 where productid='" + productid
				+ "' and batch='" + batch + "' and warehouseid=" + warehouseid;
		EntityManager.updateOrdelete(sql);

	}

	public void UnIsLock(String productid, String batch, Long warehouseid) throws Exception {

		String sql = "update Product_Stockpile_All set islock=0 where productid='" + productid
				+ "' and batch='" + batch + "' and warehouseid=" + warehouseid;
		EntityManager.updateOrdelete(sql);

	}

	public void coerceClearPrepare() throws Exception {

		String sql = "update Product_Stockpile_All set prepareout=0 where prepareout<0";
		EntityManager.updateOrdelete(sql);

	}

	public void setPrepareByID(Long id, Double stockpile, Double prepareout) throws Exception {

		String sql = "update Product_Stockpile_All set stockpile=stockpile+" + stockpile
				+ ",prepareout=prepareout+" + prepareout + " where id=" + id;
		EntityManager.updateOrdelete(sql);

	}

	// 
	public double getSumByProductID(String id) throws Exception {
		double cp = 0;
		String sql = "select sum(stockpile) from ProductStockpileAll where productid='" + id + "'";
		cp = EntityManager.getdoubleSum(sql);
		return cp;
	}

	// 
	public double getPoSumByProductID(String id) throws Exception {
		String sql = "select sum(prepareout) from ProductStockpileAll where productid='" + id + "'";
		return EntityManager.getdoubleSum(sql);
	}

	public int geProductStockpileAllByWid(String wid) throws Exception {
		String sql = "select count(*) from ProductStockpileAll where warehouseid='" + wid + "'";
		return EntityManager.getRecordCount(sql);
	}

	public double getCost(String warehouseid, String productid, String batch) throws Exception {
		String hql = " select cost from ProductStockpileAll  where warehouseid='" + warehouseid
				+ "' and productid='" + productid + "' and batch='" + batch + "'";
		return EntityManager.getdoubleSum(hql);
	}

	public void cleanEmptyStockAll() throws Exception {
		String hql = "delete from Product_Stockpile_All where stockpile + prepareout = 0";
		EntityManager.updateOrdelete(hql);
	}

	public void outRealProductStockpileAll(String productid, String batch, Double quantity,
			String warehouseid) throws Exception {
		String sql = "update Product_Stockpile_All set stockpile=stockpile-" + quantity
				+ " where productid='" + productid + "' and batch='" + batch
				+ "' and warehouseid='" + warehouseid + "' ";
		EntityManager.updateOrdelete(sql);
	}

	public void inProductStockpileAllImport(String productid, String batch, Double quantity,
			Double prepareout, String warehouseid, String billcode, String memo) throws Exception {

		String sql = "update Product_Stockpile_All set stockpile=stockpile + " + quantity
				+ ",prepareout=prepareout+" + prepareout + " where productid='" + productid
				+ "' and batch='" + batch + "' and warehouseid='" + warehouseid + "' ";
		EntityManager.updateOrdelete(sql);
	}

	public void updProductStockpileAll(ProductStockpileAll psa) throws Exception {
		EntityManager.update(psa);
	}

	public double getProductStockpileAllByWID(String warehouseid) throws Exception {
		String sql = "select sum(ps.stockpile) + sum(prepareout) from ProductStockpileAll as ps where ps.warehouseid='"
				+ warehouseid + "' ";
		return EntityManager.getdoubleSum(sql);
	}

	public List getProductStockpileAllGroup(HttpServletRequest request, int pagesize,
			String whereSql, String orderby) throws Exception {
		String sql = "SELECT PS.SORTNAME, S.WAREHOUSEID, S.PRODUCTID, P.PRODUCTNAME, P.SPECMODE, P.COUNTUNIT,P.NCCODE"
				+ ", SUM(S.STOCKPILE) STOCKPILE, SUM(S.PREPAREOUT) PREPAREOUT, SUM((S.STOCKPILE+S.PREPAREOUT) ) QUANTITY"
				+ "  FROM PRODUCT_STOCKPILE_ALL S ,PRODUCT P ,PRODUCT_STRUCT PS "
				+ whereSql
				+ "  GROUP BY PS.SORTNAME ,S.WAREHOUSEID ,S.PRODUCTID ,P.PRODUCTNAME ,P.SPECMODE ,P.COUNTUNIT ,P.NCCODE ";
		if (!StringUtil.isEmpty(orderby)) {
			return PageQuery.jdbcSqlserverQuery(request, orderby, sql, pagesize);
		} else {
			return PageQuery.jdbcSqlserverQuery(request, "warehouseid", sql, pagesize);
		}

	}

	/**
	 * 获取某一仓库中的某批次某产品数量
	 * 
	 * @param ids
	 * @return
	 * @throws Exception
	 */
	public Double getStockpileAllByWidBatchProductid(String warehouseid, String batch,
			String productid) throws Exception {
		// String sql =
		// " from ProductStockpileAll as ps where productid='"+productid+"'  and  WAREHOUSEID='"+warehouseid+"'  and  BATCH='"+batch+"'  ";
		String cfqsql = "select sum(ps.stockpile+ps.prepareout) from ProductStockpileAll as ps where ps.productid='"
				+ productid + "' and batch='" + batch + "' and warehouseid='" + warehouseid + "' ";

		return EntityManager.getdoubleSum(cfqsql);
	}

	/**
	 * 获取某一仓库中的某批次某产品数量
	 * 
	 * @param ids
	 * @return
	 * @throws Exception
	 */
	public Double getStockpileByWidBatchProductid(String warehouseid, String batch,
			String productid, String warehousebit) throws Exception {
		// String sql =
		// " from ProductStockpileAll as ps where productid='"+productid+"'  and  WAREHOUSEID='"+warehouseid+"'  and  BATCH='"+batch+"'  ";
		String cfqsql = "select sum(ps.stockpile+ps.prepareout) from ProductStockpileAll as ps where ps.productid='"
				+ productid
				+ "' and batch='"
				+ batch
				+ "' and warehouseid='"
				+ warehouseid + "'";
//				+ "' and warehousebit='" + warehousebit + "'";

		return EntityManager.getdoubleSum(cfqsql);
	}

	public List getProductStockpileAllReport(HttpServletRequest request, int pagesize,
			String whereSql,String havingString) throws Exception {
		StringBuffer sbBuffer = new StringBuffer();
		sbBuffer.append(" select w.makeorganid organid,w.id warehouseid,psa.productid productid,sum(psa.stockpile) stockpile ");
		sbBuffer.append(" from	product_stockpile_all psa inner join warehouse w on w.id = psa.warehouseid ");
//		sbBuffer.append(whereSql);
		sbBuffer.append(" group by w.makeorganid,w.id,psa.productid  ");
		String sql = sbBuffer.toString();
		return PageQuery.jdbcSqlserverQuery(request, "organid", sql, pagesize);
	}
	
	
	public List getValuedateReport(HttpServletRequest request, int pagesize,
			String whereSql,String havingString) throws Exception {
		StringBuffer sbBuffer = new StringBuffer();
		sbBuffer.append(" select w.makeorganid organid,psa.productid productid,psa.batch batch,sum(psa.stockpile) stockpile ");
		sbBuffer.append(" from	product_stockpile_all psa inner join warehouse w on w.id = psa.warehouseid ");
		sbBuffer.append(" group by w.makeorganid,psa.productid,psa.batch ");
		String sql = sbBuffer.toString();
		return PageQuery.jdbcSqlserverQuery(request, "organid", sql, pagesize);
	}
	
	public List getInventoryReport(HttpServletRequest request, int pagesize,
			String whereSql) throws Exception {
		StringBuffer sbBuffer = new StringBuffer();
		sbBuffer.append(" SELECT w.makeorganid organid,aid.osid,aid.PRODUCTID productid,sum(unitprice) unitprice,sum(quantity) quantity,sum(takequantity) takequantity ");
		sbBuffer.append(" from (WAREHOUSE w INNER JOIN AMOUNT_INVENTORY ai on w.id=ai.WAREHOUSEID) INNER JOIN AMOUNT_INVENTORY_DETAIL aid ON ai.id=aid.osid ");
		sbBuffer.append(" group by w.makeorganid,aid.osid,aid.PRODUCTID ");
		sbBuffer.append(" UNION ");
		sbBuffer.append(" SELECT w.makeorganid organid,bida.osid,bida.PRODUCTID productid,sum(UNTIPRICE),sum(quantity),sum(takequantity) ");
		sbBuffer.append(" from (WAREHOUSE w INNER JOIN BARCODE_INVENTORY bi on w.id=bi.WAREHOUSEID) INNER JOIN BARCODE_INVENTORY_D_ALL bida ON bi.id=bida.osid ");
		sbBuffer.append(" group by w.makeorganid,bida.osid,bida.PRODUCTID ");
		String sql = sbBuffer.toString();
		
		return PageQuery.jdbcSqlserverQuery(request, "organid", sql, pagesize);
	}

	public void delProductStockpileAll(ProductStockpileAll psa) throws Exception {
		String sql = "delete from PRODUCT_STOCKPILE_ALL where warehouseid = '"+psa.getWarehouseid()+"' and productid = '"+psa.getProductid()+"' and batch = '"+psa.getBatch()+"'";
		String sql2 = "delete from PRODUCT_STOCKPILE where warehouseid = '"+psa.getWarehouseid()+"' and productid = '"+psa.getProductid()+"' and batch = '"+psa.getBatch()+"'";
		EntityManager.updateOrdelete(sql);
		EntityManager.updateOrdelete(sql2);
	}

	public List<ProductStockpileAll> getNotInProductStockpilePidsAndWid(
			String productIds, String warehouseid) {
		String sql = "from ProductStockpileAll as ps where ps.productid not in ("+productIds+") and ps.warehouseid='" + warehouseid+"'";
		return EntityManager.getAllByHql(sql);
	}

	public List getStockStatList(HttpServletRequest request, int pagesize,
			String whereSql, UsersBean users) throws Exception { 
		StringBuffer sql = new StringBuffer();
		sql.append("select p.PRODUCTNAME psproductname,PS.MAKEDATE,PStr.sortname,p.SPECMODE psspecmode,p.PACKSIZENAME,w.warehousename warehourseidname,p.MCODE NCCODE ");
		sql.append(",PS.ID,PS.PRODUCTID,PS.BATCH,PS.PREPAREOUT,PS.STOCKPILE,PS.WAREHOUSEID,PS.REMARK,PS.VERIFYDATE from PRODUCT_STOCKPILE_ALL PS ");
		sql.append("join product p on p.id = PS.productId ");
		sql.append("join WAREHOUSE w on w.id=PS.warehouseId ");
		sql.append("join PRODUCT_STRUCT pstr on pstr.structcode=p.psid ");
		if(!DbUtil.isDealer(users)) {
			sql.append("join organ o on o.id=w.makeorganId ");
		}
		sql.append(whereSql);
		if(pagesize > 0) {
			return PageQuery.jdbcSqlserverQuery(request, "warehourseidname", sql.toString(), pagesize);
		} else {
			sql.append(" order by w.warehousename ");
			return EntityManager.jdbcquery(sql.toString());
		}
		
	} 
	
	public List getStockStatList(HttpServletRequest request, int pagesize,
			String whereSql, UsersBean users, Map<String, Object> param) throws Exception { 
		StringBuffer sql = new StringBuffer();
		sql.append("select p.PRODUCTNAME psproductname,PS.MAKEDATE,PStr.sortname,p.SPECMODE psspecmode,p.PACKSIZENAME,w.warehousename warehourseidname,p.MCODE NCCODE ");
		sql.append(",PS.ID,PS.PRODUCTID,PS.BATCH,PS.PREPAREOUT,PS.STOCKPILE,PS.WAREHOUSEID,PS.REMARK,PS.VERIFYDATE from PRODUCT_STOCKPILE_ALL PS ");
		sql.append("join product p on p.id = PS.productId ");
		sql.append("join WAREHOUSE w on w.id=PS.warehouseId ");
		sql.append("join PRODUCT_STRUCT pstr on pstr.structcode=p.psid ");
		if(!DbUtil.isDealer(users)) {
			sql.append("join organ o on o.id=w.makeorganId ");
		}
		sql.append(whereSql);
		if(pagesize > 0) {
			return PageQuery.jdbcSqlserverQuery(request, "warehourseidname", sql.toString(), pagesize, param);
		} else {
			sql.append(" order by w.warehousename ");
			return EntityManager.jdbcquery(sql.toString(), param);
		}
		
	} 
}
