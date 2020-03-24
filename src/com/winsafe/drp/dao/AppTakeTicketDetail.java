package com.winsafe.drp.dao;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.util.Constants;
import com.winsafe.hbm.entity.HibernateUtil;

public class AppTakeTicketDetail {
	
	public void addTakeTicketDetail(TakeTicketDetail t)throws Exception{		
		EntityManager.save(t);		
	}
	
	public void updTakeTicketDetail(TakeTicketDetail t)throws Exception{		
		EntityManager.update(t);		
	}
	
	public void updQuantityById(Long id, Double quantity)throws Exception{		
		String sql = "update take_ticket_detail set quantity="+quantity+" where id="+id;
		EntityManager.updateOrdelete(sql);	
	}
	
	public void updTakeReadStatusById(String id) throws Exception{
		String sql="update take_ticket set isRead=1 where ID='"+id+"'";
		EntityManager.updateOrdelete(sql);
	}
	
	public TakeTicketDetail getTakeTicketDetailByID(int id)throws Exception{
		String sql=" from TakeTicketDetail as wd where wd.id="+id+"";
		return (TakeTicketDetail)EntityManager.find(sql);
	}
	
	public List<TakeTicketDetail> getTakeTicketDetailByTtid(String ttid)throws Exception{
		String sql=" from TakeTicketDetail as wd where wd.ttid='"+ttid+"' order by productid";
		return EntityManager.getAllByHql(sql);
	}
	
	public List getTakeTicketDetailByTtidPid(String ttid, String productid)throws Exception{
		String sql=" from TakeTicketDetail as wd where wd.ttid='"+ttid+"' and wd.productid='"+productid+"'";
		return EntityManager.getAllByHql(sql);
	}
	
	public List getTakeTicketDetailByBillno(String billno)throws Exception{
		String sql=" select wd from TakeTicketDetail as wd, TakeTicket as t where wd.ttid=t.id and t.billno='"+billno+"'";
		return EntityManager.getAllByHql(sql);
	}
	

	public void delTakeTicketDetailByTtid(String ttid)throws Exception{		
		String sql="delete from take_ticket_detail where ttid='"+ttid+"'";
		EntityManager.updateOrdelete(sql);		
	}
	
	public List getTakeTicketDetail(String whereSql)throws Exception{
		whereSql = whereSql.replace("oname", "tb.oname").replace("isaudit", "tb.isaudit").replace("isblankout", "tb.isblankout")
		.replace("makeorganid", "tb.makeorganid").replace("makedeptid", "tb.makedeptid").replace("makedate", "tb.makedate")
		.replace("makeid", "tb.makeid").replace("bsort", "tb.bsort");
		String sql=" select t, ttd from TakeTicketDetail as ttd, TakeTicket as t, TakeBill as tb, Product as p "+ whereSql+" order by t.id";
		return EntityManager.getAllByHql(sql);
	}
	
	public void updTakeQuantity(int bsort,String billid,String productid, double takequantity)throws Exception{
		String tablename = Constants.TT_DETAIL_TABLE[bsort];
		String column = Constants.TT_MAIN_COLUMN[bsort];
		String sql="update "+tablename+" set takequantity= takequantity+"+takequantity+" where "+column+"='"+billid+"'  and productid='"+productid+"' ";
		EntityManager.updateOrdelete(sql);
	}
	
	public void updSaleOrderTakeQuantity(String soid,String productid, double takequantity, String warehouseid)throws Exception{
		String sql="update sale_order_detail set takequantity= takequantity+"+takequantity+" where soid='"+soid+"' and productid='"+productid+"' and warehouseid='"+warehouseid+"'";
		EntityManager.updateOrdelete(sql);
	}
	
	/**
	 * 得到检货小票的产品总数量
	 * @param ttid
	 * @param productid
	 * @return
	 */
	public double getTakeTicketProductTotalQuantity(String ttid, String productid) {
		double quantity = 0;
		String sql = "select sum(quantity) from take_ticket_detail where ttid=:ttid and productid=:productid";
		Session session = null;
		try {
			session = HibernateUtil.currentSession();
			Query query = session.createSQLQuery(sql);
			query.setParameter("ttid", ttid);
			query.setParameter("productid", productid);
			quantity = ((java.math.BigDecimal)query.uniqueResult()).doubleValue();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			HibernateUtil.closeSession();
		}
		return quantity;
	}
	
	public int getTakeTicketProductTotalBoxQuantity(String ttid, String productid) {
		int quantity = 0;
		String sql = "select sum(boxnum) from take_ticket_detail where ttid=:ttid and productid=:productid";
		Session session = null;
		try {
			session = HibernateUtil.currentSession();
			Query query = session.createSQLQuery(sql);
			query.setParameter("ttid", ttid);
			query.setParameter("productid", productid);
			if (query.uniqueResult()!=null) {
				quantity =((java.math.BigDecimal)query.uniqueResult()).intValue();
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			HibernateUtil.closeSession();
		}
		return quantity;
	}
	
	public double getTakeTicketProductTotalScatterQuantity(String ttid, String productid) {
		double quantity = 0;
		String sql = "select sum(scatternum) from take_ticket_detail where ttid=:ttid and productid=:productid";
		Session session = null;
		try {
			session = HibernateUtil.currentSession();
			Query query = session.createSQLQuery(sql);
			query.setParameter("ttid", ttid);
			query.setParameter("productid", productid);
			if (query.uniqueResult()!=null) {
				quantity = ((java.math.BigDecimal)query.uniqueResult()).doubleValue();
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			HibernateUtil.closeSession();
		}
		return quantity;
	}
	
	public List<TakeTicketDetail> getTakeTicketDetailsByTtids(String ttids)throws Exception{
		String sql=" from TakeTicketDetail as wd where wd.ttid in("+ttids+")";
		return EntityManager.getAllByHql(sql);
	}

	public void updRealQtyToQty(String id) throws Exception {
		String sql=" update take_ticket_detail set realQuantity = quantity where ttid = '"+id+"'";
		EntityManager.executeUpdate(sql);
	}

	public void addTakeTicketDetails(List<TakeTicketDetail> ttdList) {
		EntityManager.batchSave(ttdList);		
	}
	
	public int getRecordCountByTtid(String ttids) {
		String hql = "select count(*) from TakeTicketDetail where ttid in ('"+ttids+"')";
		return EntityManager.getCount(hql);
	}
	
	public List<TakeTicketDetail> getTakeTicketDetailByTtid(String ttid, String orderBy)throws Exception{
		String sql=" from TakeTicketDetail as wd where wd.ttid='"+ttid+"' " + orderBy;
		return EntityManager.getAllByHql(sql);
	}
	
	public List<TakeTicketDetail> getDetailByReadOnly(String ttid)throws Exception{
		String sql=" from TakeTicketDetail as wd where wd.ttid='"+ttid+"' order by productid";
		return EntityManager.getAllByHqlReadOnly(sql);
	}
	
	public int getCountByTtidAndPid(String ttid,String pid)throws Exception{
		String hql=" select count(*) from TakeTicketDetail where ttid = '"+ttid+"' and productid='"+pid+"' ";
		return EntityManager.getCount(hql);
	}
	
	public TakeTicketDetail getDetailByTtidAndPid(String ttid,String pid)throws Exception{
		String hql=" from TakeTicketDetail where ttid = '"+ttid+"' and productid='"+pid+"' ";
		return (TakeTicketDetail)EntityManager.find(hql);
	}

	public void delTakeTicketDetailById(Integer id) throws Exception {
		String sql = "DELETE FROM TAKE_TICKET_DETAIL where ID="+id;
		EntityManager.executeUpdate(sql);
	}
}
