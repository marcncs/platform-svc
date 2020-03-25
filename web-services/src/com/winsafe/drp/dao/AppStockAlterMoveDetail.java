package com.winsafe.drp.dao;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.pager.SimplePageInfo;
import com.winsafe.hbm.util.pager2.PageQuery;

public class AppStockAlterMoveDetail {

	public void addStockAlterMoveDetail(Object stockAlterMove) throws Exception {
		EntityManager.save(stockAlterMove);
	}

	public List<StockAlterMoveDetail> getStockAlterMoveDetailBySamID(
			String samid) throws Exception {
		String sql = " from StockAlterMoveDetail as sm where sm.samid= '"
				+ samid + "'";
		return EntityManager.getAllByHql(sql); 
	}
	
	public List<StockAlterMoveDetail> getUsefulStockAlterMoveDetailBySamID(
			String samid) throws Exception {
		String sql = " from StockAlterMoveDetail as sm where sm.samid= '"
				+ samid + "' and productid not in (select id from Product where useflag = 0)";
		return EntityManager.getAllByHql(sql);
	}

	public List getStockAlterMoveDetail(String whereSql) throws Exception {
		String sql = " select sm, smd from StockAlterMoveDetail as smd, StockAlterMove as sm,Product as p "
				+ whereSql + " order by sm.id";
		return EntityManager.getAllByHql(sql);
	}

	public List getStockAlterMoveDetailByPiidPid(String piid, String productid)
			throws Exception {
		String sql = " from StockAlterMoveDetail where samid='" + piid
				+ "' and productid='" + productid + "'";
		return EntityManager.getAllByHql(sql);
	}

	public void updTakeQuantity(String soid, String productid, String batch,
			double takequantity) throws Exception {
		// String sql="update sale_order_detail set takequantity=takequantity
		// +"+takequantity+" where soid='"+soid+"' and batch='"+batch+"' and
		// productid='"+productid+"' and warehouseid="+warehouseid;
		String sql = "update Stock_Alter_Move_Detail set takequantity="
				+ takequantity + " where samid='" + soid + "' and batch='"
				+ batch + "' and productid='" + productid + "' ";
		EntityManager.updateOrdelete(sql);
	}

	public void updTakeQuantityRemove(String samid, String productid,
			String batch, double takequantity) throws Exception {
		// String sql="update sale_order_detail set takequantity=takequantity
		// +"+takequantity+" where soid='"+soid+"' and batch='"+batch+"' and
		// productid='"+productid+"'";
		String sql = "update Stock_Alter_Move_Detail set takequantity="
				+ takequantity + " where samid='" + samid + "' and batch='"
				+ batch + "' and productid='" + productid + "' ";
		EntityManager.updateOrdelete(sql);
	}
	
	public void updReceiveQuantity(String samid, String productid, double receiveQuantity) throws Exception {
		String sql = "update Stock_Alter_Move_Detail set receiveQuantity="
				+ receiveQuantity + " where samid='" + samid + "' and productid='" + productid + "' ";
		EntityManager.updateOrdelete(sql);
	}

	public void delStockAlterMoveDetail(String id) throws Exception {

		String sql = "delete from stock_alter_move_detail where id='" + id
				+ "'";
		EntityManager.updateOrdelete(sql);

	}

	public void delStockAlterMoveDetailBySamid(String id) throws Exception {

		String sql = "delete from stock_alter_move_detail where samid='" + id
				+ "'";
		EntityManager.updateOrdelete(sql);

	}

	public void delStockAlterMoveDetailByNCcode(String nccode) throws Exception {

		String sql = "delete from stock_alter_move_detail where Nccode='" + nccode
				+ "'";
		EntityManager.updateOrdelete(sql);

	}

	public StockAlterMoveDetail getStockAlterMoveDetailByID(int id)
			throws Exception {
		String sql = " from StockAlterMoveDetail as sm where sm.id=" + id + "";
		return (StockAlterMoveDetail) EntityManager.find(sql);
	}

	public void updstockAlterMove(StockAlterMoveDetail sam) throws Exception {

		EntityManager.update(sam);

	}
	
	public void updstockAlterMoveDetailBySql(String sql) throws Exception {
		EntityManager.updByJDBC(sql);
	}
	
	public void insertstockAlterMoveDetailBySql(String sql) throws Exception {

		EntityManager.updateOrdelete(sql);

	}

	public List getStockAlterMoveDetail2(int pagesize, String pWhereClause,
			SimplePageInfo pPgInfo) throws Exception {
		int targetPage = pPgInfo.getCurrentPageNo();
		String sql = "select smd from StockAlterMove as sm ,StockAlterMoveDetail as smd "
				+ pWhereClause + " order by smd.productid, sm.makedate desc ";
		return EntityManager.getAllByHql(sql, targetPage, pagesize);
	}

	public List getDetailReport(HttpServletRequest request, int pagesize,
			String pWhereClause) throws Exception {
		String sql = "select pw.makeorganid,pw.makeorganidname,pw.receiveorganid,pw.receiveorganidname,pw.makedate,pwd from StockAlterMove as pw ,StockAlterMoveDetail as pwd "
				+ pWhereClause + " order by pwd.productid, pw.makedate desc ";
		return PageQuery.hbmQuery(request, sql, pagesize);
	}

	public List getDetailReport(String pWhereClause) throws Exception {
		String sql = "select pw.makeorganid,pw.makeorganidname,pw.receiveorganid,pw.receiveorganidname,pw.makedate,pwd from StockAlterMove as pw ,StockAlterMoveDetail as pwd "
				+ pWhereClause + " order by pwd.productid, pw.makedate desc ";
		return EntityManager.getAllByHql(sql);
	}

	public List getTotalReport(HttpServletRequest request, int pagesize,
			String pWhereClause) throws Exception {
		String hql = "select pwd.productid, pwd.productname, pwd.specmode, pwd.unitid, sum(pwd.quantity) as quantity, sum(pwd.subsum) as subsum "
				+ "from StockAlterMoveDetail pwd, StockAlterMove pw "
				+ pWhereClause
				+ " group by pwd.productid,pwd.productname, pwd.specmode, pwd.unitid order by pwd.productid";
		String sql = hql.replace("StockAlterMoveDetail",
				"stock_alter_move_detail");
		sql = sql.replace("StockAlterMove", "stock_alter_move");

		Object obj[] = DbUtil.setGroupByPager(request, sql, pagesize,
				"StockAlterMoveTotalReport");
		SimplePageInfo tmpPgInfo = (SimplePageInfo) obj[0];
		int targetPage = tmpPgInfo.getCurrentPageNo();
		return EntityManager.getAllByHql(hql, targetPage, pagesize);
	}

	public List getTotalReport(String pWhereClause) throws Exception {
		String hql = "select pwd.productid, pwd.productname, pwd.specmode, pwd.unitid, sum(pwd.quantity) as quantity, sum(pwd.subsum) as subsum "
				+ "from StockAlterMoveDetail pwd, StockAlterMove pw "
				+ pWhereClause
				+ " group by pwd.productid,pwd.productname, pwd.specmode, pwd.unitid order by pwd.productid";

		return EntityManager.getAllByHql(hql);
	}

	public List getTotalSubum(String whereSql) throws Exception {
		String sql = "select sum(pwd.quantity), sum(pwd.subsum) from StockAlterMove as pw ,StockAlterMoveDetail as pwd "
				+ whereSql;
		return EntityManager.getAllByHql(sql);
	}
	public List getTotalBoxnumAndScatternum(String whereSql) throws Exception {
		String sql = "select sum(pwd.boxnum), sum(pwd.scatternum) from StockAlterMove as pw ,StockAlterMoveDetail as pwd "
				+ whereSql;
		return EntityManager.getAllByHql(sql);
	}
	
	public void updAutoReceiveQuantity(String soid) throws Exception {
		String sql = "update Stock_Alter_Move_Detail set RECEIVEQUANTITY = TAKEQUANTITY"
				+ " where samid='" + soid + "'";
		EntityManager.updateOrdelete(sql);
	}
	
	public void addStockAlterMoveDetails(Object stockAlterMove) throws Exception {
		EntityManager.batchSave(stockAlterMove);
	}

	public StockAlterMoveDetail getStockAlterMoveDetailBySamIDAndPid(String samid,
			String pid) {
		String sql = " from StockAlterMoveDetail as sm where sm.samid='" + samid + "' and sm.productid = '"+pid+"'";
		return (StockAlterMoveDetail) EntityManager.find(sql);
	}
	public StockMoveDetail getStockMoveDetailBySamIDAndPid(String samid,
			String pid) {
		String sql = " from StockMoveDetail as sm where sm.smid='" + samid + "' and sm.productid = '"+pid+"'";
		return (StockMoveDetail) EntityManager.find(sql);
	}
	public OrganWithdrawDetail getOrganWithdrawDetailBySamIDAndPid(String samid,
			String pid) {
		String sql = " from OrganWithdrawDetail as sm where sm.owid='" + samid + "' and sm.productid = '"+pid+"'";
		return (OrganWithdrawDetail) EntityManager.find(sql);
	}
	
	public StockAlterMoveDetail getStockAlterMoveDetailBySamIDAndNccode(String samid,
			String nccode) {
		String sql = " from StockAlterMoveDetail as sm where sm.samid='" + samid + "' and sm.nccode = '"+nccode+"'";
		return (StockAlterMoveDetail) EntityManager.find(sql);
	}
	
	
}
