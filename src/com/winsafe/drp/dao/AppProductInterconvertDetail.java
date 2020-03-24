package com.winsafe.drp.dao;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.pager.SimplePageInfo;
import com.winsafe.hbm.util.pager2.PageQuery;

public class AppProductInterconvertDetail {

	public void addProductInterconvertDetail(Object stockAlterMove) throws Exception {
		
		EntityManager.save(stockAlterMove);
		
	}

	public List getProductInterconvertDetailBySamID(String samid) throws Exception {
		String sql = "from ProductInterconvertDetail as sm where sm.piid= '" + samid + "'" ;
		return EntityManager.getAllByHql(sql);
	}
	
	
	public void delProductInterconvertDetail(String id)throws Exception{		
		String sql="delete from product_interconvert_detail where id='"+id+"'";
		EntityManager.updateOrdelete(sql);		
	}
	
	public void delProductInterconvertDetailBySamid(String id)throws Exception{		
		String sql="delete from product_interconvert_detail where piid='"+id+"'";
		EntityManager.updateOrdelete(sql);		
	}


	public ProductInterconvertDetail getProductInterconvertDetailByID(int id) throws Exception {
		String sql = " from ProductInterconvertDetail as sm where sm.id=" + id;
		return (ProductInterconvertDetail) EntityManager.find(sql);
	}

	public void updstockAlterMove(ProductInterconvertDetail sam)throws Exception{		
		EntityManager.update(sam);		
	}
	
	public List getProductInterconvertDetailByPiidPid(String piid, String batch, String productid)
	throws Exception {
		String sql = " from ProductInterconvertDetail where piid='" + piid
				+ "' and batch='"+batch+"' and productid='" + productid + "'";
		return EntityManager.getAllByHql(sql);
	}
	

	public void updProductInterconvertDetailIsShipment(String id,Integer isshipment,Long userid)throws Exception{
		
		String sql="update product_interconvert set isshipment="+isshipment+",shipmentid="+userid+",shipmentdate='"+DateUtil.getCurrentDateString()+"' where id='"+id+"'";
		EntityManager.updateOrdelete(sql);		
	}
	
	
	public void updProductInterconvertDetailIsComplete(String id,Integer iscomplete,Long userid)throws Exception{
		
		String sql="update stock_move set iscomplete="+iscomplete+",receivedate='"+DateUtil.getCurrentDateString()+"',receiveid="+userid+" where id='"+id+"'";
		EntityManager.updateOrdelete(sql);		
	}
	
	
	public void updIsRefer(String id) throws Exception {		
		String sql = "update stock_move set isrefer=1 where id='" + id+"'";
		EntityManager.updateOrdelete(sql);		
	}
	
	public void updIsApprove(String id, int isapprove) throws Exception {
		String sql = "update stock_move set approvestatus=" + isapprove
				+ " where id ='" + id+"'";
		EntityManager.updateOrdelete(sql);
	}
	
 
	

	public void updIsAudit(String ppid, Long userid,Integer audit) throws Exception {		
		String sql = "update stock_move set isaudit="+audit+",auditid=" + userid
				+ ",auditdate='" + DateUtil.getCurrentDateString()
				+ "' where id='" + ppid + "'";
		EntityManager.updateOrdelete(sql);
		
	}
	
	public List getProductInterconvertDetail(String pWhereClause) throws Exception {
		String sql = "select pi, pid from ProductInterconvert as pi ,ProductInterconvertDetail as pid,Product as p "
				+ pWhereClause + " order by pid.productid, pi.makedate desc ";
		return EntityManager.getAllByHql(sql);
	}
	
	public List getProductInterconvertDetail2(int pagesize, String pWhereClause,
			SimplePageInfo pPgInfo) throws Exception {
		int targetPage = pPgInfo.getCurrentPageNo();
		String sql = "select smd from ProductInterconvert as sm ,ProductInterconvertDetail as smd "
				+ pWhereClause + " order by smd.productid, sm.makedate desc ";
		return EntityManager.getAllByHql(sql, targetPage, pagesize);
	}
	
	
	public List getDetailReport(HttpServletRequest request,int pagesize, String pWhereClause) throws Exception {
		String sql = "select pw.outwarehouseid,pw.inwarehouseid,pw.makedate,pwd from ProductInterconvert as pw ,ProductInterconvertDetail as pwd "
				+ pWhereClause + " order by pwd.productid, pw.makedate desc ";
		System.out.println(sql);
		return PageQuery.hbmQuery(request, sql, pagesize);
	}
	
	public List getDetailReport(String pWhereClause) throws Exception {
		String sql = "select pw.outwarehouseid,pw.inwarehouseid,pw.makedate,pwd from ProductInterconvert as pw ,ProductInterconvertDetail as pwd "
				+ pWhereClause + " order by pwd.productid, pw.makedate desc ";
		return EntityManager.getAllByHql(sql);
	}
	
	
	
	public List getTotalReport(HttpServletRequest request, int pagesize, 
			String pWhereClause) throws Exception {
		String hql = "select pwd.productid, pwd.productname, pwd.specmode, pwd.unitid, sum(pwd.quantity) as quantity "+
		"from Product_Interconvert_Detail pwd, Product_Interconvert pw "+ pWhereClause +
		" group by pwd.productid,pwd.productname, pwd.specmode, pwd.unitid";
		return PageQuery.jdbcSqlserverQuery(request,"productid",hql, pagesize);
	}
	
	public List getTotalReport(String pWhereClause) throws Exception {
		String hql = "select pwd.productid, pwd.productname, pwd.specmode, pwd.unitid, sum(pwd.quantity) as quantity "+
		"from ProductInterconvertDetail pwd, ProductInterconvert pw "+ pWhereClause +
		" group by pwd.productid,pwd.productname, pwd.specmode, pwd.unitid order by pwd.productid";
		return EntityManager.getAllByHql(hql);
	}
	
	public double getTotalSubum(String whereSql)throws Exception{		    
	    String sql="select sum(pwd.quantity) from ProductInterconvert as pw ,ProductInterconvertDetail as pwd "+whereSql;
	    return EntityManager.getdoubleSum(sql);
	}
	
}
