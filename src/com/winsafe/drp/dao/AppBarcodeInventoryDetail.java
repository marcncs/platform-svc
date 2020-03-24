package com.winsafe.drp.dao;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.util.Constants;
import com.winsafe.hbm.util.DateUtil;

public class AppBarcodeInventoryDetail {
	
	
	/**
	 * 根据id获得条码盘点细节
	 * @author jason.huang
	 * @param osid
	 * @return
	 * @throws Exception
	 */

	public List getBarcodeInventoryDetailByID(String osid) throws Exception {
		String sql = "  from BarcodeInventoryDetail  bid where bid.osid='"
				+ osid + "'";
		return  EntityManager.getAllByHql(sql);
	}
	
	/**
	 * 根据id获得条码盘点细节
	 * @author jason.huang
	 * @param osid
	 * @return
	 * @throws Exception
	 */

	public BarcodeInventoryDetail findBarcodeInventoryDetailByID(String osid) throws Exception {
		String sql = "  from BarcodeInventoryDetail  bid where bid.osid='"
				+ osid + "'";
		return  (BarcodeInventoryDetail) EntityManager.find(sql);
	}
	
	/**
	 * 统计各类产品的数量
	 * @author jason.huang
	 * @param osid
	 * @return
	 * @throws Exception
	 */

	public List groupBarcodeInventoryDetailByID(HttpServletRequest request,String osid) throws Exception {
		String sql = " select bid.productid ID,bid.productid,bid.productname,bid.specmode,bid.unitid,bid.\"BATCH\",sum(quantity) quantity from barcode_inventory_detail  bid where bid.osid='"
				+ osid + "'" + " group by productid,productname,specmode,unitid,\"BATCH\"  ";
		return EntityManager.jdbcquery(sql);
	}

	/**
	 * 新增barcodeinventorydetail
	 * 
	 * @author jason.huang
	 * @param spb
	 * @throws Exception
	 */

	public void addBarcodeInventoryDetail(Object spb) throws Exception {
		EntityManager.save(spb);
	}
	
	
	/**
	 * 获取货物数量
	 * @author jason.huang
	 */

	public List getStockpile(HttpServletRequest request, String warehouseid,String productid,String batch) throws Exception{
		String sql = " select ps.id,stockpile from Product_Stockpile_All  ps, Product  p,Product_Struct pstr where  p.id=ps.productid and pstr.structcode=p.psid and productid = '"+productid+"' and   batch='"+batch+"' and warehouseid = '"+warehouseid+"' "; 
		return  EntityManager.jdbcquery(sql);
	}
	
	/**
	 * 根据warehouseid获取所有产品情况
	 * @author jason.huang
	 * @param string 
	 */
	public List getAllBarcodeDetailByW(HttpServletRequest request, String warehouseId, String billId) throws Exception {
		String hql = " select productid,batch,sum(stockpile) as stockpile, sum(quantity) as quantity from " +
				" ( " +
				" select productid,batch,0 as stockpile,quantity from barcode_inventory_detail where osid = '"+billId+"' " +
				" UNION ALL " +
				" select ps.productid,ps.batch,ps.stockpile / fu.xquantity, 0 as quantity from PRODUCT_STOCKPILE_ALL ps " +
				" join F_UNIT fu on fu.productid = ps.productid and fu.funitid = " + Constants.DEFAULT_UNIT_ID + 
				" where ps.warehouseid = '"+warehouseId+"' " +
				" ) " +
				" GROUP BY productid,batch ";
		return EntityManager.jdbcquery(hql);
	}
	
	/**
	 * 根据warehouseid获取所有产品情况
	 * @author jason.huang
	 */
	public List getAllBarcodeDetailByW(HttpServletRequest request, String pWhereClause) throws Exception {
		String hql = "select ps, p,pstr.sortname from ProductStockpileAll as ps, Product as p,ProductStruct pstr  " + pWhereClause;
		return EntityManager.getAllByHql(hql);
	}
	
	/**
	 * 根据warehouseid获取产品数量
	 * @author jason.huang
	 * 
	 */
	public List getNumByProductid(HttpServletRequest request,String productid,String osid,String batch) throws Exception {
		String sql = " select bid.productid ID,sum(quantity) quantity from barcode_inventory_detail  bid where bid.productid='"+productid+"' and osid='"+osid+"' and batch='"+batch+"'  group by productid ";
		return EntityManager.jdbcquery(sql);
	}
	
	/**
	 * 根据单号获得各个产品的信息 
	 * @author jason.huang
	 * 
	 */
	public List getQuantityById(String osid) throws Exception {
		String sql = " select bid.productid ID,productid,batch,productname,specmode,unitid,sum(quantity) quantity from barcode_inventory_detail  bid where  osid='"+osid+"'   group by productid,batch,productname,specmode,unitid ";
		return EntityManager.jdbcquery(sql);
	}
	
	
	/**
	 * 遍历barcodeinventorydetail，找出product_stock_pile中不存在的产品，并将其加入到其中
	 * 
	 * @author jason.huang
	 * @param sunit 
	 * @param ppid
	 * @throws Exception
	 */

	public void insProductStockPileByNone(String productid, String batch,
			String warehouseid, Integer sunit) throws Exception {
		if(isExistProduct(productid, batch, warehouseid)){
			
		}else {
			String sql = " insert into product_stockpile_all (id,productid,countunit,batch,producedate,vad,cost,warehouseid,stockpile,prepareout,islock,makedate,verifystatus,remark,verifydate) "
				+  " values ((select max(id)+1 from product_stockpile_all),'"
				+ productid
				+ "','"
				+ sunit
				+ "','"
				+ batch
				+ "','','','','"
				+ warehouseid
				+ "',0,0,0,to_date('"+DateUtil.formatDateTime(new Date())+"','yyyy-MM-dd hh24:mi:ss'),1,'','' )";
			EntityManager.updateOrdelete(sql);
			//TODO:在product_stockpile中增加一条相同的记录
			AppProductStockpile aps = new AppProductStockpile();
			aps.insProductStockPileAllByNone(productid, batch, warehouseid, sunit);
			
		}
	}
	


	/**
	 * 是否存在该产品
	 * 
	 * @param productid
	 * @param batch
	 * @param warehouseid
	 * @param warehousebit
	 * @param billcode
	 * @return
	 * @throws Exception
	 */
	private boolean isExistProduct(String productid, String batch, String warehouseid)
			throws Exception {
		String sql = " select count(id) from ProductStockpileAll where productid='" + productid
				+ "' and batch='" + batch + "' and warehouseid='" + warehouseid + "'  ";
		int count = EntityManager.getRecordCount(sql);
		if (count > 0) {
			return true;
		}
		return false;
	}
	
	
	public BarcodeInventoryDetail getBarcodeInventoryDetailByID(int id)throws Exception{
		String sql=" from BarcodeInventoryDetail  bid where bid.id="+id+"";
		return (BarcodeInventoryDetail)EntityManager.find(sql);
	}
	
	
	/**
	 * 根据id和batch获取detail
	 * @author jason.huang
	 * @param osid
	 * @return
	 * @throws Exception
	 */

	public BarcodeInventoryDetail getDetailByIdAndBatch(String osid,String pid,String batch) throws Exception {
		String sql = "  from BarcodeInventoryDetail  bid where bid.osid='"
				+ osid + "' and bid.batch='"+batch+"' and bid.productid='"+pid+"'  ";
		return  (BarcodeInventoryDetail) EntityManager.find(sql);
	}
	
	public Object[] getDetailByIdPidAndBatch(String osid,String pid,String batch) throws Exception {
		String sql = "select max(productid),max(productname),max(batch),sum(quantity),max(unitid),max(specmode) from BarcodeInventoryDetail bid where bid.osid='"
				+ osid + "' and bid.batch='"+batch+"' and bid.productid='"+pid+"' group by bid.osid,bid.batch,bid.productid ";
		return  (Object[])EntityManager.find(sql);
	}
	
}
