package com.winsafe.drp.dao;

import java.util.Date;
import java.util.List;


import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.winsafe.common.util.StringUtil;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.util.ArithDouble;
import com.winsafe.drp.util.Constants;
import com.winsafe.drp.util.Dateutil;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.MakeCode;
import com.winsafe.hbm.util.pager.SimplePageInfo;
import com.winsafe.hbm.util.pager2.PageQuery;
import com.winsafe.sap.task.SapUploadTask;
@SuppressWarnings("unchecked")
public class AppProductStockpile {

	//tommy 增加log
	private static Logger logger = Logger.getLogger(AppProductStockpile.class);
	
	private AppFUnit appFunit = new AppFUnit();

	private AppProductStockpileAll appall = new AppProductStockpileAll();

	public void updProductStockpile(ProductStockpile ps) throws Exception {
		EntityManager.update(ps);
	}

	public List getProductStockpile(int pagesize, String pWhereClause, SimplePageInfo pPgInfo) throws Exception {
		List pls = null;
		int targetPage = pPgInfo.getCurrentPageNo();
		String sql = "select ps.id,ps.productid,ps.psproductname,ps.psspecmode,ps.countunit,ps.batch,ps.barcode,ps.warehouseid,ps.stockpile,ps.prepareout,ps.islock from ProductStockpile as ps "
				+ pWhereClause + " order by ps.productid desc, ps.id ";
		pls = EntityManager.getAllByHql(sql, targetPage, pagesize);
		return pls;
	}

	public List getProductStockpilePresent(HttpServletRequest request, int pagesize, String pWhereClause) throws Exception {
		String sql = "select ps.productid,p.productname,p.specmode,ps.countunit,ps.warehouseid,ps.batch,ps.stockpile,ps.prepareout "
				+ "from ProductStockpile ps, Warehouse w, Product p  " + pWhereClause + " order by ps.productid desc";
		return PageQuery.hbmQuery(request, sql, pagesize);
	}

	public void updProductStockpileUnitidByProductID(String pid, Integer unitid) throws Exception {
		String sql = "update Product_Stockpile set CountUnit = " + unitid + " where productid='" + pid + "' ";
		EntityManager.updateOrdelete(sql);
	}

	public List getProductStockpileNew(int pagesize, String pWhereClause, SimplePageInfo pPgInfo) throws Exception {
		int targetPage = pPgInfo.getCurrentPageNo();
		String sql = " select ps from ProductStockpile as ps,Product as p " + pWhereClause
				+ " order by ps.productid desc, ps.id ";

		return EntityManager.getAllByHql(sql, targetPage, pagesize);
	}

	public List getProductStockpileNewTwo(HttpServletRequest request, int pagesize, String pWhereClause) throws Exception {
		String sql = " select p, ps from ProductStockpile as ps,Product as p " + pWhereClause
				+ " order by ps.productid desc, ps.id ";
		return PageQuery.hbmQuery(request, sql, pagesize);
	}

	public List getDistinctProductStockpile(HttpServletRequest request, int pagesize, String pWhereClause) throws Exception {
		String sql = " select distinct p.id,p.productname,p.specmode,p.countunit from Product_Stockpile as ps,Product as p "
				+ pWhereClause + " ";
		return PageQuery.jdbcSqlserverQuery(request, sql, pagesize);
	}

	public ProductStockpile getProductStockpileByID(Long id) throws Exception {
		String sql = " from ProductStockpile as ps where ps.id=" + id;
		return (ProductStockpile) EntityManager.find(sql);
	}
	public List<ProductStockpile> getProductStockpileByIDs(String ids) throws Exception {
		String sql = " from ProductStockpile as ps where ps.id in (" + ids + ")";
		return (List<ProductStockpile>)EntityManager.getAllByHql(sql);
	}
	

	public ProductStockpile getProductStockpileByProductIDUnitWid(String productid, int unitid, String wid, String warehousebit)
			throws Exception {
		String sql = " from ProductStockpile as ps where ps.productid='" + productid + "' and ps.countunit=" + unitid
				+ " and ps.warehouseid='" + wid + "' and ps.warehousebit='" + warehousebit + "'";
		return (ProductStockpile) EntityManager.find(sql);
	}

	public ProductStockpile getProductStockpileByPidWid(String productid, String wid, String warehousebit, String batch)
			throws Exception {
		String sql = " from ProductStockpile as ps where ps.productid='" + productid + "' and ps.batch='" + batch
				+ "' and ps.warehouseid='" + wid + "' and ps.warehousebit='" + warehousebit + "' and ps.verifyStatus=1";
		return (ProductStockpile) EntityManager.find(sql);
	}
	public ProductStockpile getMonthProductStockpileByPidWid(String productid, String wid, String warehousebit, String batch)
	throws Exception {
		String sql = " from ProductStockpile as ps where ps.productid='" + productid + "' and SUBSTRING(ps.batch,0,6)='" + batch
		+ "' and ps.warehouseid='" + wid + "' and ps.warehousebit='" + warehousebit + "' and ps.verifyStatus=1";
		return (ProductStockpile) EntityManager.find(sql);
	}
	
	

	public double getStockpileByPidWid(String productid, String wid, String warehousebit, String batch) throws Exception {
		String sql = " select ps.stockpile from ProductStockpile as ps where ps.productid='" + productid;
		if (!"".equals(batch)) {
			sql += "' and ps.batch='";
			sql += batch;
		}
		sql += "' and ps.warehouseid='" + wid + "' and ps.warehousebit='" + warehousebit + "'";
		return EntityManager.getdoubleSum2(sql);
	}

	public List searchProductStockpile(int pagesize, String pWhereClause, SimplePageInfo pPgInfo) throws Exception {
		int targetPage = pPgInfo.getCurrentPageNo();
		String sql = " select ps from ProductStockpile as ps,Product as p " + pWhereClause
				+ " order by ps.productid desc, ps.id ";
		return EntityManager.getAllByHql(sql, targetPage, pagesize);
	}

	public ProductStockpile getProductStockpileByPIDWID(String pWhereClause) throws Exception {
		String sql = " from ProductStockpile as ps " + pWhereClause + " ";
		return (ProductStockpile) EntityManager.find(sql);
	}


	public List getStockStat(HttpServletRequest request, int pagesize, String pWhereClause) throws Exception {
		String hql = "select ps, p from ProductStockpile as ps,WarehouseVisit as wv, Product as p " + pWhereClause
				+ " order by ps.productid desc, ps.id desc ";
		return PageQuery.hbmQuery(request, hql, pagesize);
	}

	public List getStockStat(String pWhereClause) throws Exception {
		String hql = "select ps, p from ProductStockpile as ps,WarehouseVisit as wv, Product as p " + pWhereClause
				+ " order by ps.productid desc, ps.id desc ";
		return EntityManager.getAllByHql(hql);
	}

	public List getProductCheckStockpile(int pagesize, String pWhereClause, SimplePageInfo pPgInfo) throws Exception {
		int targetPage = pPgInfo.getCurrentPageNo();
		String sql = "select ps.id,ps.productid,ps.psproductname,ps.psspecmode,ps.countunit,ps.batch,ps.barcode,ps.warehouseid,ps.stockpile + ps.prepareout,ps.prepareout,ps.islock from ProductStockpile as ps "
				+ pWhereClause + " order by ps.productid desc, ps.id ";
		return EntityManager.getAllByHql(sql, targetPage, pagesize);
	}

	public List getProductCheckStockpile2(int pagesize, String pWhereClause, SimplePageInfo pPgInfo) throws Exception {
		int targetPage = pPgInfo.getCurrentPageNo();
		String sql = "select ps.id,p.id,p.productname,p.specmode,p.countunit,ps.batch,ps.barcode,ps.warehouseid,ps.stockpile + ps.prepareout,ps.prepareout,ps.islock from ProductStockpile as ps,Product as p "
				+ pWhereClause + " order by p.id desc, ps.id ";
		return EntityManager.getAllByHql(sql, targetPage, pagesize);
	}

	public List getCheckStockpileEmpty(String pWhereClause) throws Exception {
		String sql = "select ps, p from ProductStockpile as ps,Product as p " + pWhereClause
				+ " order by ps.productid desc, ps.id ";
		return EntityManager.getAllByHql(sql);
	}

	public double getProductStockpileByProductID(String productid, Long wid) throws Exception {
		double stock = 0.00;
		String sql = "select sum(ps.stockpile) from ProductStockpile as ps where ps.productid='" + productid
				+ "' and ps.warehouseid=" + wid + " ";
		stock = EntityManager.getdoubleSum(sql);
		return stock;
	}

	public double getProductStockpileByProductIDWIDBatch(String productid, String wid, String batch) throws Exception {
		String sql = "select sum(ps.stockpile) from ProductStockpile as ps where ps.productid='" + productid
				+ "' and ps.warehouseid=" + wid + " and ps.batch='" + batch + "'";
		return EntityManager.getdoubleSum(sql);
	}

	public List getProductStockpileByProductIDWID(String productid, Long wid) throws Exception {
		String sql = "select ps.id,ps.productid,ps.psproductname,ps.psspecmode,ps.countunit,ps.batch,ps.warehouseid,ps.stockpile,ps.prepareout,ps.islock from ProductStockpile as ps where ps.productid='"
				+ productid + "' and ps.warehouseid=" + wid + " and ps.stockpile>0 ";
		return EntityManager.getAllByHql(sql);
	}

	public void addProductStockpile(Object productstockpile) throws Exception {

		EntityManager.save(productstockpile);

	}

	public void addProductByPurchaseIncome(ProductStockpile ps) throws Exception {
		String sql = "insert into product_stockpile(id,productid,countunit,batch,producedate,vad,cost,warehouseid,warehousebit,verifyStatus,stockpile,prepareout,islock,makedate)  select "
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
				+ ps.getWarehousebit()
				+ "','"
				+ ps.getVerifyStatus()
				+ "',"
				+ "0,0,0,sysdate from dual where not exists(select id from product_stockpile where productid='"
				+ ps.getProductid()
				+ "' and batch='"
				+ ps.getBatch()
				+ "' and warehouseid='"
				+ ps.getWarehouseid()
				+ "' and warehousebit='"
				+ ps.getWarehousebit() + "')";
		EntityManager.updateOrdelete(sql);
		// 对ProductStockpileAll作相同的操作
		appall.addProductByPurchaseIncome(ps);
	}

	/**
	 * 默认verifyStatus检验状态为合格
	 * 
	 * @param ps
	 * @throws Exception
	 */
	public void addProductByPurchaseIncome2(ProductStockpile ps) throws Exception {
		String sql = "insert into product_stockpile(id,productid,countunit,batch,producedate,vad,cost,warehouseid,warehousebit,stockpile,prepareout,islock,makedate,verifyStatus)  select "
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
				+ ps.getWarehousebit()
				+ "',"
				+ "0,0,0,sysdate,1 from dual where not exists(select id from product_stockpile where productid='"
				+ ps.getProductid()
				+ "' and batch='"
				+ ps.getBatch()
				+ "' and warehouseid='"
				+ ps.getWarehouseid()
				+ "' and warehousebit='"
				+ ps.getWarehousebit() + "')";
		EntityManager.updateOrdelete(sql);
		// 对ProductStockpileAll作相同的操作
		appall.addProductByPurchaseIncome2(ps);
	}

	public void prepareOut(String productid, int unitid, String warehouseid, String warehousebit, String batch, Double quantity)
			throws Exception {
//		quantity = appFunit.getQuantity(productid, unitid, quantity);
		this.prepareOut(productid, warehouseid, warehousebit, batch, quantity);
	}

	public void prepareOut(String productid, String warehouseid, String warehousebit, String batch, Double quantity)
			throws Exception {
		// quantity = appFunit.getQuantity(productid, 2, quantity);
		String sql = "update Product_Stockpile set stockpile=stockpile - " + quantity + ", prepareout=prepareout + " + quantity
				+ " where productid='" + productid + "' and warehouseid='" + warehouseid + "' and warehousebit='" + warehousebit
				+ "' and batch='" + batch + "'";
		EntityManager.updateOrdelete(sql);

	}

	public void updateCost(String productid, String warehouseid, String warehousebit, String batch, Double cost) throws Exception {

		String sql = "update product_stockpile set cost=" + cost + " where productid='" + productid + "' and warehouseid='"
				+ warehouseid + "' and batch='" + batch + "' and warehousebit='" + warehousebit + "'";
		EntityManager.updateOrdelete(sql);

	}

	public void freeStockpile(String productid, int unitid, String warehouseid, String warehousebit, String batch, Double quantity)
			throws Exception {
//		quantity = appFunit.getQuantity(productid, unitid, quantity);
		this.freeStockpile(productid, warehouseid, warehousebit, batch, quantity);
	}

	public void freeStockpile(String productid, String warehouseid, String warehousebit, String batch, Double quantity)
			throws Exception {
		// quantity = appFunit.getQuantity(productid, 2, quantity);
		String sql = "update product_stockpile set stockpile=stockpile + " + quantity + ", prepareout=prepareout - " + quantity
				+ " where productid='" + productid + "' and warehouseid='" + warehouseid + "' and batch='" + batch
				+ "' and warehousebit='" + warehousebit + "'";
		EntityManager.updateOrdelete(sql);

	}

	AppStockWasteBook aswb = new AppStockWasteBook();

	/**
	 * 主要功能:减少更新库存表(数量为实际单位数量)
	 * 
	 * @param productid
	 * @param unitid
	 * @param batch
	 * @param quantity
	 * @param warehouseid
	 * @param warehousebit
	 * @param billcode
	 * @param memo
	 * @throws Exception
	 */
	public void outProductStockpile(String productid, int unitid, String batch, Double quantity, String warehouseid,
			String warehousebit, String billcode, String memo) throws Exception {
//		quantity = appFunit.getQuantity(productid, unitid, quantity);
		this.outProductStockpile(productid, batch, quantity, warehouseid, warehousebit, billcode, memo, unitid,true);
	}
	
	public void outProductStockpile(String productid, String batch, Double quantity, String warehouseid, String warehousebit,
			String billcode, String memo, int unitid,boolean isAccount) throws Exception {
		//转化为最小单位数量
		//logger.debug("保存库存的 跟踪信息   productid:" +productid);
		//logger.debug("保存库存的跟踪信息   unitid:" +unitid);
		//logger.debug("保存库存的跟踪信息   unitid:" +quantity);
		//
		quantity = appFunit.getQuantity(productid, unitid, quantity);
		if(StringUtil.isEmpty(batch)){
			batch = Constants.NO_BATCH;
		}
		//判断库存是否存在
		isExist(productid, batch, warehouseid, warehousebit, unitid);
		//更新库位库存表
		String sql = "update product_stockpile set stockpile=stockpile - " + quantity + " where productid='" + productid
				+ "' and batch='" + batch + "' and warehouseid='" + warehouseid + "' and warehousebit='" + warehousebit + "'";
		EntityManager.updateOrdelete(sql);
		//更新总库存表
		appall.outProductStockpileAll(productid, batch, quantity, warehouseid, billcode, memo);
		//增加出库台账记录
		if(isAccount){
			aswb.addOutStockWasteBook(productid, batch, quantity, warehouseid, warehousebit, billcode, memo);// 台账
		}
	}

	
	/**
	 * 条码盘点调整库存
	 * @param productid
	 * @param unitid
	 * @param batch
	 * @param quantity
	 * @param warehouseid
	 * @param warehousebit
	 * @param billcode
	 * @param memo
	 * @throws Exception
	 */
	public void inbarcodeInventoryStockpile(String productid, int unitid, String batch, Double quantity, String warehouseid,
			String warehousebit, String billcode,Double total, String memo) throws Exception {
//		quantity = appFunit.getQuantity(productid, unitid, quantity);
		this.inbarcodeInventoryStockpile(productid, batch, quantity, warehouseid, warehousebit, billcode,total, memo, unitid,true);
	}
	
	public void inbarcodeInventoryStockpile(String productid, String batch, Double quantity, String warehouseid, String warehousebit,
			String billcode,Double total, String memo, int unitid,boolean isAccount) throws Exception {
		//转化为最小单位数量
		quantity = appFunit.getQuantity(productid, unitid, quantity);
		if(StringUtil.isEmpty(batch)){
			batch = Constants.NO_BATCH;
		}
		//判断库存是否存在
		isExist(productid, batch, warehouseid, warehousebit, unitid);
		//更新库位库存表
		String sql = "update product_stockpile set stockpile=" + quantity + " where productid='" + productid
				+ "' and batch='" + batch + "' and warehouseid='" + warehouseid + "' and warehousebit='" + warehousebit + "'";
		EntityManager.updateOrdelete(sql);
		//更新总库存表
		appall.inBarcodeProductStockpileAll(productid, batch, quantity, warehouseid, billcode, memo);
		//增加出库台账记录
		if(isAccount){
			aswb.addInBarcodeStockWasteBook(productid, batch, quantity-total, warehouseid, warehousebit, billcode, memo);// 台账
		}
	}
	

	
	
	
	public void outbarcodeInventoryStockpile(String productid, int unitid, String batch, Double quantity, String warehouseid,
			String warehousebit, String billcode,Double total, String memo) throws Exception {
//		quantity = appFunit.getQuantity(productid, unitid, quantity);
		this.outbarcodeInventoryStockpile(productid, batch, quantity, warehouseid, warehousebit, billcode,total, memo, unitid,true);
	}
	
	public void outbarcodeInventoryStockpile(String productid, String batch, Double quantity, String warehouseid, String warehousebit,
			String billcode,Double total, String memo, int unitid,boolean isAccount) throws Exception {
		//转化为最小单位数量
		quantity = appFunit.getQuantity(productid, unitid, quantity);
		if(StringUtil.isEmpty(batch)){
			batch = Constants.NO_BATCH;
		}
		//判断库存是否存在
		isExist(productid, batch, warehouseid, warehousebit, unitid);
		//更新库位库存表
		String sql = "update product_stockpile set stockpile=" + quantity + " where productid='" + productid
				+ "' and batch='" + batch + "' and warehouseid='" + warehouseid + "' and warehousebit='" + warehousebit + "'";
		EntityManager.updateOrdelete(sql);
		//更新总库存表
		appall.outBarcodeProductStockpileAll(productid, batch, quantity, warehouseid, billcode, memo);
		//增加出库台账记录
		if(isAccount){
			aswb.addOutBarcodeStockWasteBook(productid, batch, total-quantity, warehouseid, warehousebit, billcode, memo);// 台账
		}
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
	public void outPrepareout(String warehouseid, String warehousebit, String productid, Integer unitid, String batch,
			Double quantity, String billno, String memo, boolean isaccount) throws Exception {

//		quantity = appFunit.getQuantity(productid, unitid, quantity);
		outPrepareout(warehouseid, warehousebit, productid, batch, quantity, billno, memo, isaccount);
	}

	public void outPrepareout(String warehouseid, String warehousebit, String productid, String batch, Double quantity,
			String billno, String memo, boolean isaccount) throws Exception {

		String sql = "update Product_Stockpile set prepareout = prepareout - " + quantity + " where productid='" + productid
				+ "' and batch='" + batch + "' and warehouseid='" + warehouseid + "' and warehousebit='" + warehousebit + "'";
		EntityManager.updateOrdelete(sql);
		if (isaccount) {
			aswb.addOutStockWasteBook(productid, batch, quantity, warehouseid, warehousebit, billno, memo);// 台账
		}

	}

	public void returnPrepareout(String warehouseid, String warehousebit, String productid, Integer unitid, String batch,
			Double quantity, String billno, String memo) throws Exception {

		quantity = appFunit.getQuantity(productid, unitid, quantity);
		returnPrepareout(warehouseid, warehousebit, productid, batch, quantity, billno, memo);
	}

	public void returnPrepareout(String warehouseid, String warehousebit, String productid, String batch, Double quantity,
			String billno, String memo) throws Exception {

		String sql = "update Product_Stockpile set prepareout = prepareout + " + quantity + " where productid='" + productid
				+ "' and batch='" + batch + "' and warehouseid='" + warehouseid + "' and warehousebit='" + warehousebit + "'";
		EntityManager.updateOrdelete(sql);

		aswb.delStockWasteBook(productid, batch, warehouseid, warehousebit, billno);
	}

	public void returnOutProductStockpile(String productid, int unitid, String batch, Double quantity, String warehouseid,
			String warehousebit, String billcode, String memo) throws Exception {
		quantity = appFunit.getQuantity(productid, unitid, quantity);
		this.returnOutProductStockpile(productid, batch, quantity, warehouseid, warehousebit, billcode, memo);
	}

	public void returnOutProductStockpile(String productid, String batch, Double quantity, String warehouseid,
			String warehousebit, String billcode, String memo) throws Exception {
		String sql = "update product_stockpile set stockpile=stockpile + " + quantity + " where productid='" + productid
				+ "' and batch='" + batch + "' and warehouseid='" + warehouseid + "' and warehousebit='" + warehousebit + "'";
		EntityManager.updateOrdelete(sql);
		appall.returnOutProductStockpileAll(productid, batch, quantity, warehouseid, billcode, memo);
		// aswb.addOutStockWasteBook(productid, batch, quantity, warehouseid,
		// warehousebit,billcode,memo);
		aswb.delStockWasteBook(productid, batch, warehouseid, warehousebit, billcode);
	}

	/**
	 * 主要功能:更新库存表(数量为实际单位数量)
	 * 
	 * @param productid
	 * @param unitid
	 * @param batch
	 * @param quantity
	 * @param warehouseid
	 * @param warehousebit
	 * @param billcode
	 * @param memo
	 * @throws Exception
	 */
	public void inProductStockpile(String productid, int unitid, String batch, Double quantity, String warehouseid,
			String warehousebit, String billcode, String memo) throws Exception {

//		quantity = appFunit.getQuantity(productid, unitid, quantity);
		this.inProductStockpile(productid, batch, quantity, warehouseid, warehousebit, billcode, memo,unitid, true);
	}

	public void inProductStockpileWithOutAccount(String productid, int unitid, String batch, Double quantity, String warehouseid,
			String warehousebit, String billcode, String memo) throws Exception {

//		quantity = appFunit.getQuantity(productid, unitid, quantity);
		this.inProductStockpile(productid, batch, quantity, warehouseid, warehousebit, billcode, memo,unitid, false);
	}
	
	public void outProductStockpileWithOutAccount(String productid, int unitid, String batch, Double quantity, String warehouseid,
			String warehousebit, String billcode, String memo) throws Exception {

//		quantity = appFunit.getQuantity(productid, unitid, quantity);
		this.outProductStockpile(productid, batch, quantity, warehouseid, warehousebit, billcode, memo,unitid, false);
	}

	/**
	 * 主要功能:更新库存表(数量为实际总数量)
	 * 
	 * @param productid
	 * @param unitid
	 * @param batch
	 * @param quantity
	 * @param warehouseid
	 * @param warehousebit
	 * @param billcode
	 * @param memo
	 * @throws Exception
	 */
	public void inProductStockpileTotalQuantity(String productid, int unitid, String batch, Double quantity, String warehouseid,
			String warehousebit, String billcode, String memo) throws Exception {
		quantity = appFunit.getQuantity(productid, unitid, quantity);
		this.inProductStockpile(productid, batch, quantity, warehouseid, warehousebit, billcode, memo,unitid, true);
	}
	/**
	 * 判断库存是否存在,不存在则新增0库存
	 * @param productid
	 * @param batch
	 * @param quantity
	 * @param warehouseid
	 * @param warehousebit
	 * @param billcode
	 * @param memo
	 * @param unitid
	 * @param isaccount
	 * @throws Exception
	 */
	private void isExist(String productid, String batch, String warehouseid, String warehousebit,int unitid) throws Exception{
		
		String sqlsel = "from ProductStockpileAll as p where p.productid='" + productid + "' and p.batch='" + batch
		+ "' and p.warehouseid='" + warehouseid + "'";
		List productStockpile = EntityManager.getAllByHql(sqlsel);
		// 判断原本是否有库存
		if (productStockpile == null || productStockpile.size() == 0) {
			// 新增
			// 库位库存表
			ProductStockpile psk = new ProductStockpile();
			psk.setId(Long.valueOf(MakeCode.getExcIDByRandomTableName("product_stockpile", 0, "")));
			psk.setBatch(batch);
			psk.setProductid(productid);
			psk.setWarehouseid(warehouseid);
			psk.setWarehousebit("000");
			psk.setStockpile(0.00);
			psk.setCountunit(unitid);
			psk.setPrepareout(0.00);
			psk.setIslock(0);
			psk.setMakedate(Dateutil.getCurrentDate());
			EntityManager.save(psk);
			//总库存表
			ProductStockpileAll psa = new ProductStockpileAll();
			psa.setId(Long.valueOf(MakeCode.getExcIDByRandomTableName("product_stockpile", 0, "")));
			psa.setBatch(batch);
			psa.setProductid(productid);
			psa.setWarehouseid(warehouseid);
			psa.setStockpile(0.00);
			psa.setCountunit(unitid);
			psa.setPrepareout(0.00);
			psa.setIslock(0);
			psa.setMakedate(Dateutil.getCurrentDate());
			EntityManager.save(psa);
		}
	}
	
	
	public void inProductStockpile(String productid, String batch, Double quantity, String warehouseid, String warehousebit,
			String billcode, String memo,int unitid,boolean isaccount) throws Exception {
		//转化为最小单位数量
		quantity = appFunit.getQuantity(productid, unitid, quantity);
		if(StringUtil.isEmpty(batch)){
			batch = Constants.NO_BATCH;
		}
		//判断库存是否存在,不存在则新增0库存
		isExist(productid, batch, warehouseid, warehousebit, unitid);
		//更新为库位库存表
		String sql = "update product_stockpile set stockpile=stockpile + " + quantity + " where productid='" + productid
				+ "' and batch='" + batch + "' and warehouseid='" + warehouseid + "' and warehousebit='" + warehousebit + "'";
		EntityManager.updateOrdelete(sql);
		appall.inProductStockpileAll(productid, batch, quantity, warehouseid, billcode, memo);
		if (isaccount) {
			aswb.addInStockWasteBook(productid, batch, quantity, warehouseid, warehousebit, billcode, memo);
		}
	}

	public void returninProductStockpile(String productid, int unitid, String batch, Double quantity, String warehouseid,
			String warehousebit, String billcode, String memo) throws Exception {

		quantity = appFunit.getQuantity(productid, unitid, quantity);
		this.returninProductStockpile(productid, batch, quantity, warehouseid, warehousebit, billcode, memo);
	}

	
	public void returnoutProductStockpile(String productid, int unitid, String batch, Double quantity, String warehouseid,
			String warehousebit, String billcode, String memo) throws Exception {

		quantity = appFunit.getQuantity(productid, unitid, quantity);
		this.returnoutProductStockpile(productid, batch, quantity, warehouseid, warehousebit, billcode, memo);
	}
	/**
	 * 主要功能:还原库存数量(实际总数量)
	 * 
	 * @author RichieYu 20100505
	 * @param productid
	 * @param unitid
	 * @param batch
	 * @param quantity
	 * @param warehouseid
	 * @param warehousebit
	 * @param billcode
	 * @param memo
	 * @throws Exception
	 */
	public void returninProductStockpileTotalQuantity(String productid, int unitid, String batch, Double quantity,
			String warehouseid, String warehousebit, String billcode, String memo) throws Exception {
		quantity = appFunit.getQuantity(productid, unitid, quantity);
		this.returninProductStockpile(productid, batch, quantity, warehouseid, warehousebit, billcode, memo);
	}

	public void returninProductStockpile(String productid, String batch, Double quantity, String warehouseid,
			String warehousebit, String billcode, String memo) throws Exception {

		String sql = "update product_stockpile set stockpile=stockpile - " + quantity + " where productid='" + productid
				+ "' and batch='" + batch + "' and warehouseid='"+warehouseid+"'";
		EntityManager.updateOrdelete(sql);
		appall.returninProductStockpileAll(productid, batch, quantity, warehouseid, billcode, memo);
		// aswb.addInStockWasteBook(productid, batch, quantity,
		// warehouseid,warehousebit, billcode,memo);
		aswb.delStockWasteBook(productid, batch, warehouseid, warehousebit, billcode);
	}
	
	public void returnoutProductStockpile(String productid, String batch, Double quantity, String warehouseid,
			String warehousebit, String billcode, String memo) throws Exception {

		String sql = "update product_stockpile set stockpile=stockpile + "+quantity+" where productid='"+productid
				+"' and batch='"+batch+"' and warehouseid='"+warehouseid+"'";
		EntityManager.updateOrdelete(sql);
		appall.returnOutProductStockpileAll(productid, batch, quantity, warehouseid, billcode, memo);
		// aswb.addInStockWasteBook(productid, batch, quantity,
		// warehouseid,warehousebit, billcode,memo);
		aswb.delStockWasteBook(productid, batch, warehouseid, warehousebit, billcode);
	}

	//	
	// public String outProductStockpileSimple(String productid,String batch,
	// Double quantity,
	// Long warehouseid,String billcode,String memo) throws Exception {
	// String out = "f";
	// String sql =
	// "update product_stockpile set stockpile=stockpile-"+quantity+" where productid='"
	// + productid
	// + "' and batch='"+batch+"' and warehouseid=" + warehouseid;
	// //System.out.println("-----"+sql);
	// out = EntityManager.updateOrdelete(sql);
	// aswb.addInStockWasteBook(productid, batch, quantity, warehouseid,
	// billcode,memo);//台账
	// return out;
	// }

	public void adjustProductStockpile(String productid, String sign, int quantity, String warehouseid) throws Exception {

		String sql = "update product_stockpile set stockpile=stockpile " + sign + "" + quantity + " where productid='"
				+ productid + "' and warehouseid=" + warehouseid;
		EntityManager.updateOrdelete(sql);

	}

	public double getProductStockpileByPIDWID(String productid, String warehouseid) throws Exception {
		String sql = "select sum(ps.stockpile) from ProductStockpile as ps where ps.productid='" + productid
				+ "' and ps.warehouseid=" + warehouseid;
		return EntityManager.getdoubleSum(sql);
	}

	public List getProductStockpileByPID(String pid) throws Exception {
		String sql = "select p.stockpile,p.warehouseid from ProductStockpile as p where p.productid ='" + pid + "'";
		return EntityManager.getAllByHql(sql);
	}

	public Double getProductStockpileByPIDUIdWID(String pid, Integer userid, String wid) throws Exception {
		String sql = "select sum(ps.stockpile) from ProductStockpile as ps,WarehouseVisit as wv where ps.productid ='" + pid
				+ "' and ps.warehouseid=wv.wid and  wv.userid=" + userid + " and ps.warehouseid= '" + wid + "' ";
		// System.out.println("------"+sql);
		return EntityManager.getdoubleSum(sql);
	}

	public List getProductStockpileByPIDUId(String pid, Integer userid) throws Exception {
		String sql = "select sum(p.stockpile),p.warehouseid from ProductStockpile as p,WarehouseVisit as wv where p.productid ='"
				+ pid + "' and p.warehouseid=wv.wid and wv.userid=" + userid + " group by p.warehouseid ";
		// System.out.println("------"+sql);
		return EntityManager.getAllByHql(sql);
	}

	public void IsLock(String productid, String batch, String warehouseid, String warehousebit, int islock) throws Exception {
		String sql = "update product_stockpile set islock=" + islock + " where productid='" + productid + "' and batch='" + batch
				+ "' and warehouseid='" + warehouseid + "' and warehousebit='" + warehousebit + "'";
		EntityManager.updateOrdelete(sql);

	}

	public void coerceClearPrepare() throws Exception {

		String sql = "update product_stockpile set prepareout=0 where prepareout<0";
		EntityManager.updateOrdelete(sql);

	}

	public void setPrepareByID(Long id, Double stockpile, Double prepareout) throws Exception {

		String sql = "update product_stockpile set stockpile=stockpile+" + stockpile + ",prepareout=prepareout+" + prepareout
				+ " where id=" + id;
		EntityManager.updateOrdelete(sql);

	}

	// 鎸変骇鍝両D鏌ュ彲鐢ㄦ暟閲?
	public double getSumByProductID(String id) throws Exception {
		double cp = 0;
		String sql = "select sum(stockpile) from ProductStockpile where productid='" + id + "'";
		cp = EntityManager.getdoubleSum(sql);
		return cp;
	}

	// 鎸変骇鍝両D鏌ラ瀹氭暟閲?
	public double getPoSumByProductID(String id) throws Exception {
		String sql = "select sum(prepareout) from ProductStockpile where productid='" + id + "'";
		return EntityManager.getdoubleSum(sql);
	}

	public List getStockPileByPIDWID(String productId, String warehouseId) {
		String sql = " from ProductStockpile as ps where ps.productid ='" + productId + "' and ps.warehouseid='" + warehouseId
				+ "' and ps.verifyStatus=1  order by ps.batch asc";
		return EntityManager.getAllByHql(sql);
	}
	
	public List getMonthPSByPIDWIDBatch(String productId, String warehouseId,String mBatch) {
		String sql = " from ProductStockpile as ps where ps.productid ='" + productId + "' and ps.warehouseid='" + warehouseId
				+ "' and ps.verifyStatus=1 and SUBSTRING(ps.batch,0,6)='" + mBatch + "'  order by ps.batch asc";
		return EntityManager.getAllByHql(sql);
	}
	
	
	
	public List getMonthStockPileByPIDWID(String productId, String warehouseId) {
		String sql = "select new  com.winsafe.drp.dao.ProductStockpile(max(ps.id),max(ps.productid)" +
				",max(ps.countunit),SUBSTRING(max(ps.batch),0,6),max(ps.producedate)" +
				",max(ps.vad),max(ps.cost),max(ps.warehouseid),max(ps.warehousebit),max(ps.stockpile)" +
				",max(ps.prepareout),max(ps.islock),max(ps.makedate)) " +
				" from ProductStockpile as ps where ps.productid ='" + productId + "' and ps.warehouseid='" + warehouseId
				+ "' and ps.verifyStatus=1 group by SUBSTRING(ps.batch,0,6) order by SUBSTRING(ps.batch,0,6) asc ";
		return EntityManager.getAllByHql(sql);
	}

	public void cleanEmptyStock() throws Exception {
		String hql = "delete from Product_Stockpile where stockpile + prepareout = 0";
		EntityManager.updateOrdelete(hql);
	}

	/**
	 * 根据库位库存出库. 处理后的可用库存数=当前库存数 +预出库数量-实际出库数 预出库直接转成可用库存.
	 * 
	 * @param productid
	 * @param batch
	 * @param quantity
	 * @param warehouseid
	 * @param bit
	 * @throws Exception
	 */
	public void outRealProductStockpile(String productid, Integer unitid, String batch, Double quantity, Double realQuantity,
			String warehouseid, String bit, String ttid) throws Exception {
		// ProductStockpile
//		quantity = appFunit.getQuantity(productid, unitid, quantity);
//		realQuantity = appFunit.getQuantity(productid, unitid, realQuantity);
		String sql = "update Product_Stockpile set stockpile = stockpile + " + (ArithDouble.sub(quantity, realQuantity))
				+ " , prepareout = prepareout - " + quantity + " where productid='" + productid + "' and batch='" + batch
				+ "' and warehouseid='" + warehouseid + "' and warehousebit = '" + bit + "'";
		System.out.println(sql);
		EntityManager.updateOrdelete(sql);
		aswb.addOutStockWasteBook(productid, batch, realQuantity, warehouseid, bit, ttid, "检货小票-出货");// 台账
	}

	public void outRealProductStockpileAll(String productid, Integer unitid, String batch, Double quantity, Double realQuantity,
			String warehouseid, String bit, String ttid) throws Exception {
		// ProductStockpileAll
		quantity = appFunit.getQuantity(productid, unitid, quantity);
		realQuantity = appFunit.getQuantity(productid, unitid, realQuantity);
		String sql = "update Product_Stockpile_All set stockpile = stockpile + " + (ArithDouble.sub(quantity, realQuantity))
				+ " , prepareout = prepareout - " + quantity + " where productid='" + productid + "' and batch='" + batch
				+ "' and warehouseid='" + warehouseid + "'";
		EntityManager.updateOrdelete(sql);
	}

	public String inProductStockpileTotalQuantityImport(String productid, int unitid, String batch, Double quantity,
			Double prepareout, String warehouseid, String warehousebit, String billcode, String memo) throws Exception {
		quantity = appFunit.getQuantity2(productid, unitid, quantity);
		if (quantity == 0.0) {
			return "单位错误";
		}
		prepareout = appFunit.getQuantity2(productid, unitid, prepareout);
		this.inProductStockpileImport(productid, batch, quantity, prepareout, warehouseid, warehousebit, billcode, memo);
		return null;
	}

	public void inProductStockpileImport(String productid, String batch, Double quantity, Double prepareout, String warehouseid,
			String warehousebit, String billcode, String memo) throws Exception {

		String sql = "update product_stockpile set stockpile=stockpile + " + quantity + ",prepareout=prepareout+" + prepareout
				+ " where productid='" + productid + "' and batch='" + batch + "' and warehouseid='" + warehouseid
				+ "' and warehousebit='" + warehousebit + "'";
		EntityManager.updateOrdelete(sql);
		appall.inProductStockpileAllImport(productid, batch, quantity, prepareout, warehouseid, billcode, memo);
		// aswb.addOutStockWasteBook(productid, batch, quantity, warehouseid,
		// warehousebit, billcode, memo);
	}
	
	public List<ProductStockpile> getProductStockpileAllListByWID(HttpServletRequest request, 
			int pagesize, String pWhereClause, String orderSql) throws Exception {
		String sql = " select ps from ProductStockpile as ps, Product as p " + pWhereClause +" ";
		if(!StringUtil.isEmpty(orderSql)){
			sql+= " order by "+ orderSql;
		}
		return PageQuery.hbmQuery(request, sql, pagesize);
	}
	
	public List<ProductStockpile> getProductStockpileAllListByWIDNoPage(String pWhereClause) throws Exception {
		String sql = " select ps from ProductStockpile as ps, Product as p " + pWhereClause +" ";
		return EntityManager.getAllByHql(sql);
	}
	
	public void insProductStockPileAllByNone(String productid,String batch,String warehouseid, Integer sunit) throws Exception{
		String sql = " insert into product_stockpile (id,productid,countunit,batch,producedate,vad,cost,warehouseid,warehousebit,stockpile,prepareout,islock,makedate,verifystatus,remark,verifydate) "
			+  " values ((select max(id)+1 from product_stockpile_all),'"
			+ productid
			+ "','"
			+ sunit
			+ "','"
			+ batch
			+ "','','','','"
			+ warehouseid
			+ "','000',0,0,0,to_date('"+DateUtil.formatDateTime(new Date())+"','yyyy-MM-dd hh24:mi:ss'),1,'','' )";
		
		EntityManager.updateOrdelete(sql);
		
	}
}
