package com.winsafe.drp.dao;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.pager2.PageQuery;

/**
 * @author : jerry
 * @version : 2009-8-26 下午03:49:02
 * www.winsafe.cn
 */
public class AppSupplySaleMove {

	
	public List<SupplySaleMove> getSupplySaleMoveAll(HttpServletRequest request,Integer pageSize, String whereSql)throws Exception{
		String hql =" from SupplySaleMove as ssa " +whereSql+"order by ssa.id desc";
		
		//System.out.println(hql);
		return PageQuery.hbmQuery(request, hql, pageSize);
	}
	
	public SupplySaleMove getSupplySaleMoveByID(String id)throws Exception{
		String hql = " from SupplySaleMove ssa  where ssa.id='" + id+"'";
		return (SupplySaleMove) EntityManager.find(hql);
	}
	public void save(SupplySaleMove supplySaleMove)throws Exception{
		EntityManager.save(supplySaleMove);
	}
	public void update(SupplySaleMove supplySaleMove)throws Exception{
		EntityManager.update(supplySaleMove);
	}
	public void delete(SupplySaleMove supplySaleMove)throws Exception{
		EntityManager.delete(supplySaleMove);
	}
	
	public void updSupplySaleMoveIsShipment(String id, int isshipment, int userid)throws Exception{
		String sql="update supply_sale_move set isshipment="+isshipment+",shipmentid="+userid+",shipmentdate='"+DateUtil.getCurrentDateString()+"' where id='"+id+"'";
		EntityManager.updateOrdelete(sql);
	}
	
	
	public void updSupplySaleMoveIsComplete(String id,int iscomplete,int userid)throws Exception{
		String sql="update supply_sale_move set iscomplete="+iscomplete+",receivedate='"+DateUtil.getCurrentDateString()+"',receiveid="+userid+" where id='"+id+"'";
		EntityManager.updateOrdelete(sql);
	}

	public List<SupplySaleMove> getSupplySaleMoveAll(String whereSql) {
		String hql =" from SupplySaleMove as ssa " +whereSql+"order by ssa.id desc";
		return EntityManager.getAllByHql(hql);
	}
	
	public void updIsMakeTicket(String id, int isticket) throws Exception {
		String sql = "update supply_sale_move set ismaketicket =" + isticket
				+ " where id='" + id + "'";
		EntityManager.updateOrdelete(sql);
	}
	
	public void updIsReceiveTicket(String id, int isticket) throws Exception {
		String sql = "update supply_sale_move set isreceiveticket =" + isticket
				+ " where id='" + id + "'";
		EntityManager.updateOrdelete(sql);
	}

	public List getSupplySaleMoveOrganTotal(HttpServletRequest request,
			int pagesize, String whereSql) throws Exception {
		String hql = "select makeorganid,supplyorganid,inorganid,sum(totalsum) as totalsum,sum(stotalsum) as stotalsum from supply_sale_move "
				+ whereSql
				+ " group by makeorganid,supplyorganid,inorganid";
		return PageQuery.jdbcSqlserverQuery(request,"makeorganid", hql,
				pagesize);
	}
	
	public List getSupplySaleMoveOrganTotal(String whereSql) throws Exception {
		String hql = "select makeorganid,supplyorganid,inorganid,sum(totalsum) as totalsum,sum(stotalsum) as stotalsum from supply_sale_move "
				+ whereSql
				+ " group by makeorganid,supplyorganid,inorganid";
		return EntityManager.jdbcquery(hql);
	}
	
	public List getSupplySaleMoveOrganTotalSum(String whereSql) throws Exception{
		String hql = "select sum(totalsum) as allp,sum(stotalsum) as alls from Supply_Sale_Move " + whereSql
				+ "";
		//System.out.println("---"+hql);
		return EntityManager.jdbcquery(hql);
	}
	
	public List getSupplySaleMoveProductTotal(HttpServletRequest request,
			int pagesize, String whereSql) throws Exception {
		String hql = "select sam.makeorganid,samd.productid,samd.productname,samd.specmode,samd.unitid,sum(samd.quantity) as quantity,sum(samd.psubsum )as psubsum,sum(samd.ssubsum )as ssubsum"
				+ " from supply_sale_move as sam , supply_sale_move_detail samd "
				+ whereSql +
				" group by makeorganid,productId,productname,specmode,unitid";
		return PageQuery.jdbcSqlserverQuery(request,"makeorganid", hql, pagesize);
	}
	public List getSupplySaleMoveProductTotal(String whereSql) throws Exception {
		String hql = "select sam.makeorganid,samd.productid,samd.productname,samd.specmode,samd.unitid,sum(samd.quantity) as quantity,sum(samd.psubsum )as psubsum,sum(samd.ssubsum )as ssubsum"
				+ " from supply_sale_move as sam , supply_sale_move_detail samd "
				+ whereSql +
				" group by makeorganid,productId,productname,specmode,unitid";
		return EntityManager.jdbcquery(hql);
	}
	
	public List getSupplySaleMoveProductTotalSum(String whereSql) throws Exception {
		String hql = "select sum(quantity) as quantity, sum(psubsum )as psubsum,sum(ssubsum) as ssubsum"
			+ " from supply_sale_move as sam , supply_sale_move_detail samd "
			+ whereSql ;
		return EntityManager.jdbcquery(hql);
	}
	
	public List getSupplySaleMoveBillTotal(HttpServletRequest request,
			int pagesize, String whereSql) throws Exception {
		String sql = "select id ,movedate,inorganid,makeorganid,supplyorganid,makedate,totalsum,stotalsum from supply_sale_move "
			+ whereSql ;
		return PageQuery.jdbcSqlserverQuery(request, sql, pagesize);
	}
	public List getSupplySaleMoveBillTotal(String whereSql) throws Exception {
		String sql = "select id ,movedate,inorganid,makeorganid,supplyorganid,makedate,totalsum,stotalsum from supply_sale_move "
			+ whereSql ;
		return EntityManager.jdbcquery(sql);
	}
	
	public List getSupplySaleMoveBillTotalSum(String whereSql)throws Exception {
		String hql = "select sum(totalsum) as totalsum,sum(stotalsum) as stotalsum from supply_sale_move " + whereSql
				+ "";
		return EntityManager.jdbcquery(hql);
	}
	
	public List getTotalSubum(String whereSql)throws Exception{		    
	    String sql="select sum(pwd.quantity) as qt, sum(pwd.psubsum) as pss, sum(pwd.ssubsum) as sss from Supply_Sale_Move as pw ,Supply_Sale_Move_Detail as pwd "+whereSql;
	    return EntityManager.jdbcquery(sql);		    
	}
	
	public List getDetailReport(HttpServletRequest request,int pagesize, String pWhereClause) throws Exception {
		String sql = "select pw.makeorganid,pw.inorganid,pw.supplyorganid,pw.makedate,pwd from SupplySaleMove as pw ,SupplySaleMoveDetail as pwd "
				+ pWhereClause + " order by pwd.productid, pw.makedate desc ";
		return PageQuery.hbmQuery(request, sql, pagesize);
	}
	public List getDetailReport(String pWhereClause) throws Exception {
		String sql = "select pw.makeorganid,pw.inorganid,pw.supplyorganid,pw.makedate,pwd from SupplySaleMove as pw ,SupplySaleMoveDetail as pwd "
				+ pWhereClause + " order by pwd.productid, pw.makedate desc ";
		return EntityManager.getAllByHql(sql);
	}
	
}
