package com.winsafe.drp.dao;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.pager2.PageQuery;

public class AppBarcodeInventoryDAll {
	
	
	/**
	 * 根据id获得条码盘点细节
	 * @author jason.huang
	 * @param osid
	 * @return
	 * @throws Exception
	 */

	public List getBarcodeInventoryDAllByID(String osid) throws Exception {
		String sql = "  from BarcodeInventoryDAll  bid where bid.osid='"
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

	public BarcodeInventoryDAll findBarcodeInventoryDAllByID(String osid) throws Exception {
		String sql = "  from BarcodeInventoryDAll  bid where bid.osid='"
				+ osid + "'";
		return  (BarcodeInventoryDAll) EntityManager.find(sql);
	}
	
	/**
	 * 统计各类产品的数量
	 * @author jason.huang
	 * @param osid
	 * @return
	 * @throws Exception
	 */

	public List groupBarcodeInventoryDAllByID(HttpServletRequest request,String osid) throws Exception {
		String sql = " select bid.productid ID,bid.productid,bid.productname,bid.specmode,bid.unitid,bid.\"BATCH\",sum(quantity) quantity from barcode_inventory_detail  bid where bid.osid='"
				+ osid + "'" + " group by productid,productname,specmode,unitid,\"BATCH\"  ";
		return PageQuery.jdbcSqlserverQuery(request, sql);
	}

	/**
	 * 新增BarcodeInventoryDAll
	 * 
	 * @author jason.huang
	 * @param spb
	 * @throws Exception
	 */

	public void addBarcodeInventoryDAll(Object spb) throws Exception {
		EntityManager.save(spb);
	}
	
	
	/**
	 * 获取货物数量
	 * @author jason.huang
	 */

	public List getStockpile(HttpServletRequest request, String warehouseid,String productid,String batch) throws Exception{
		String sql = " select ps.id,stockpile from Product_Stockpile_All  ps, Product  p,Product_Struct pstr where  p.id=ps.productid and pstr.structcode=p.psid and productid = '"+productid+"' and   batch='"+batch+"' and warehouseid = '"+warehouseid+"' "; 
		return  PageQuery.jdbcSqlserverQuery(request, sql);
	}
	
	/**
	 * 根据warehouseid获取所有产品情况
	 * @author jason.huang
	 */
	public List getAllBarcodeDetailByW(HttpServletRequest request, String pWhereClause) throws Exception {
		String hql = "select ps, p,pstr.sortname from ProductStockpileAll as ps, Product as p,ProductStruct pstr  " + pWhereClause;
		int pagesize =100;
		return PageQuery.hbmQuery(request, hql,pagesize);
	}
	
	/**
	 * 根据warehouseid获取产品数量
	 * @author jason.huang
	 * 
	 */
	public List getNumByProductid(HttpServletRequest request,String productid,String osid,String batch) throws Exception {
		String sql = " select bid.productid ID,sum(quantity) quantity from barcode_inventory_detail  bid where bid.productid='"+productid+"' and osid='"+osid+"' and batch='"+batch+"'  group by productid ";
		return PageQuery.jdbcSqlserverQuery(request, sql);
	}
	
	/**
	 * 根据单号获得各个产品的信息
	 * @author jason.huang
	 * 
	 */
	public List getQuantityById(HttpServletRequest request,String osid) throws Exception {
		String sql = " select bid.productid ID,productid,batch,productname,specmode,unitid,sum(quantity) quantity from barcode_inventory_detail  bid where  osid='"+osid+"'   group by productid,batch,productname,specmode,unitid ";
		return PageQuery.jdbcSqlserverQuery(request, sql);
	}
	
	
	/**
	 * 遍历BarcodeInventoryDAll，找出product_stock_pile中不存在的产品，并将其加入到其中
	 * 
	 * @author jason.huang
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
	
	
	public BarcodeInventoryDAll getBarcodeInventoryDAllByID(int id)throws Exception{
		String sql=" from BarcodeInventoryDAll  bid where bid.id="+id+"";
		return (BarcodeInventoryDAll)EntityManager.find(sql);
	}
	
	
	/**
	 * 根据id和batch获取detail
	 * @author jason.huang
	 * @param osid
	 * @return
	 * @throws Exception
	 */

	public BarcodeInventoryDAll getDetailByIdAndBatch(String osid,String pid,String batch) throws Exception {
		String sql = "  from BarcodeInventoryDAll  bid where bid.osid='"
				+ osid + "' and bid.batch='"+batch+"' and bid.productid='"+pid+"'  ";
		return  (BarcodeInventoryDAll) EntityManager.find(sql);
	}

	public List<ProductStockpileAll> getNotUploadedInventory(BarcodeInventory bi) {
		String hql = "from ProductStockpileAll ps where ps.warehouseid = '"+bi.getWarehouseid()+"' and not EXISTS (select id from BarcodeInventoryDetail where osid = '"+bi.getId()+"' and productid = ps.productid and batch = ps.batch) ";
		return EntityManager.getAllByHql(hql);
	}
	
}
