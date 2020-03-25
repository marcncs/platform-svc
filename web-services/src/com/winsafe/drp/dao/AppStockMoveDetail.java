package com.winsafe.drp.dao;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.pager.SimplePageInfo;
import com.winsafe.hbm.util.pager2.PageQuery;

public class AppStockMoveDetail {

    public void addStockMoveDetail(Object spb)throws Exception{
        EntityManager.save(spb);
    }
    
    public List<StockMoveDetail> getStockMoveDetailBySmIDNew(String smid)throws Exception{
  	  String sql=" from StockMoveDetail as smd where smd.smid='"+smid+"'";
  	  return EntityManager.getAllByHql(sql);
  	}
    
    public StockMoveDetail getStockMoveDetailByID(int id)throws Exception{
    	  String sql=" from StockMoveDetail where id="+id;
    	  return (StockMoveDetail)EntityManager.find(sql);
    	}
    
    public List getStockMoveDetailByPiidPid(String piid, String productid)
	throws Exception {
		String sql = " from StockMoveDetail where smid='" + piid
				+ "' and productid='" + productid + "'";
		return EntityManager.getAllByHql(sql);
	}
    

    public void delStockMoveDetailBySmID(String smid)throws Exception{
      
      String sql="delete from stock_move_detail where smid='"+smid+"'";
      EntityManager.updateOrdelete(sql);
      
      }
    
    public List getStockMoveDetail(String whereSql) throws Exception {
		String sql = " select sm, smd from StockMoveDetail as smd, StockMove as sm,Product as p "+whereSql+" order by sm.id";
		return EntityManager.getAllByHql(sql);
	}
    
    

	public void updTakeQuantity(String soid,String productid, String batch, double takequantity)throws Exception{
		//String sql="update sale_order_detail set takequantity=takequantity +"+takequantity+" where soid='"+soid+"' and batch='"+batch+"' and productid='"+productid+"' and warehouseid="+warehouseid;
		String sql="update Stock_Move_Detail set takequantity="+takequantity+" where smid='"+soid+"' and batch='"+batch+"' and productid='"+productid+"' ";
		EntityManager.updateOrdelete(sql);
	}
	

	public void updTakeQuantityRemove(String samid,String productid, String batch, double takequantity)throws Exception{
		//String sql="update sale_order_detail set takequantity=takequantity +"+takequantity+" where soid='"+soid+"' and batch='"+batch+"' and productid='"+productid+"'";
		String sql="update Stock_Move_Detail set takequantity="+takequantity+" where smid='"+samid+"' and batch='"+batch+"' and productid='"+productid+"' ";
		EntityManager.updateOrdelete(sql);
	}
	
	public List getStockMoveDetail(int pagesize, String pWhereClause,
			SimplePageInfo pPgInfo) throws Exception {
		int targetPage = pPgInfo.getCurrentPageNo();
		String sql = "select smd from StockMove as sm ,StockMoveDetail as smd "
				+ pWhereClause + " order by smd.productid, sm.makedate desc ";
		return EntityManager.getAllByHql(sql, targetPage, pagesize);
	}

	public List getDetailReport(HttpServletRequest request,int pagesize, String pWhereClause) throws Exception {
		String sql = "select pw.makeorganid,pw.makedate,pw.outwarehouseid,pw.inwarehouseid,pw.inorganid,pwd.smid,pwd.productid,pwd.productname,pwd.specmode,pwd.unitid,pwd.quantity from Stock_Move as pw ,Stock_Move_Detail as pwd "
				+ pWhereClause + "";
		return PageQuery.jdbcSqlserverQuery(request,"productid", sql, pagesize);
	}
	
	public List getDetailReportBC(HttpServletRequest request,int pagesize, String pWhereClause) throws Exception {
		String sql = "select pw.makeorganid,pw.makedate,pw.outwarehouseid,pw.inwarehouseid,pw.inorganid,pwd.smid,pwd.productid,pwd.productname,pwd.specmode,pwd.unitid,pwd.quantity,pwd.boxnum,pwd.scatternum from Stock_Move as pw ,Stock_Move_Detail as pwd "
				+ pWhereClause + "";
		return PageQuery.jdbcSqlserverQuery(request,"productid", sql, pagesize);
	}
	
	public List getDetailReport(String pWhereClause) throws Exception {
		String sql = "select pw.makeorganid,pw.makedate,pw.outwarehouseid,pw.inwarehouseid,pw.inorganid,pwd.smid,pwd.productid,pwd.productname,pwd.specmode,pwd.unitid,pwd.quantity,pwd.boxnum,pwd.scatternum from Stock_Move as pw ,Stock_Move_Detail as pwd "
			+ pWhereClause + "";
		return EntityManager.jdbcquery(sql);
	}
	
	public List getProductTotalReport(HttpServletRequest request, int pagesize, 
			String pWhereClause) throws Exception {
		String hql = "select pw.makeorganid,pwd.productid, pwd.productname, pwd.specmode, pwd.unitid, sum(pwd.quantity) as quantity, sum(pwd.takequantity) as takequantity ,sum(pwd.boxnum) as boxnum, sum(pwd.scatternum)  as scatternum "+
		"from Stock_Move_Detail pwd, Stock_Move pw "+ pWhereClause +
		" group by pw.makeorganid, pwd.productid,pwd.productname, pwd.specmode, pwd.unitid ";

		return PageQuery.jdbcSqlserverQuery(request,"makeorganid", hql, pagesize);
	}
	
	public List getProductTotalReport( 
			String pWhereClause) throws Exception {
		String hql = "select pw.makeorganid,pwd.productid, pwd.productname, pwd.specmode, pwd.unitid, sum(pwd.quantity) as quantity, sum(pwd.takequantity) as takequantity "+
		"from Stock_Move_Detail pwd, Stock_Move pw "+ pWhereClause +
		" group by pw.makeorganid,pwd.productid,pwd.productname, pwd.specmode, pwd.unitid";
		
		return EntityManager.jdbcquery(hql);
	}
	
	public Double getTotalSubum(String pWhereClause) throws Exception {
		String sql = "select sum(pwd.quantity)" +
			" from StockMove pw, StockMoveDetail pwd " +
			pWhereClause;
		return EntityManager.getdoubleSum(sql);
	}
	
	public int getTotalBoxnum(String pWhereClause) throws Exception {
		String sql = "select sum(pwd.boxnum)" +
			" from StockMove pw, StockMoveDetail pwd " +
			pWhereClause;
		return ((Double)EntityManager.getdoubleSum(sql)).intValue();
	}
	
	public Double getTotalScatternum(String pWhereClause) throws Exception {
		String sql = "select sum(pwd.scatternum)" +
			" from StockMove pw, StockMoveDetail pwd " +
			pWhereClause;
		return EntityManager.getdoubleSum(sql);
	}
	
	public void delStockMoveDetailByNCcode(String nccode) throws Exception {

		String sql = "delete from stock_move_detail where NCcode='" + nccode
				+ "'";
		EntityManager.updateOrdelete(sql);

	}
	public void updstockMove(StockMoveDetail sam) throws Exception {

		EntityManager.update(sam);

	}

	public StockMoveDetail getStockAlterMoveDetailBySmIDAndPid(String smid,
			String pid) {
		String sql = " from StockAlterMoveDetail as sm where sm.samid='" + smid + "' and sm.productid = '"+pid+"'";
		return (StockMoveDetail) EntityManager.find(sql);
	}
	
}
