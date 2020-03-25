package com.winsafe.drp.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;

import com.winsafe.drp.entity.EntityManager;

public class AppTakeTicketDetailBatchBit {
	
	public void addTakeTicketDetailBatchBit(Object obj)throws Exception{
		EntityManager.save(obj);
	}
	
	public void updateTakeTicketDetailBatchBit(Object obj)throws Exception{
		EntityManager.update(obj);
	}
	
	public void deleteTakeTicketDetailBatchBit(Object obj)throws Exception{
		EntityManager.delete(obj);
	}
	
	public List<TakeTicketDetailBatchBit> getBatchBitByTTDID(Integer ttdid){
		String hql = "from TakeTicketDetailBatchBit where ttdid = "+ttdid;
		return EntityManager.getAllByHql(hql);
	}
	
	public List<TakeTicketDetailBatchBit> getBatchBitByTTIDPID(String ttid,String pid){
		String hql = "from TakeTicketDetailBatchBit where ttid = '"+ttid + "' and productid ='"+pid+"'";
		return EntityManager.getAllByHql(hql);
	}
	
	public List<TakeTicketDetailBatchBit> getmBatchBitByTTIDPID(String ttid,String pid,String mBatch){
		String hql = "from TakeTicketDetailBatchBit where ttid = '"+ttid + "' and productid ='"+pid+"' and SUBSTRING(batch,0,6)='" + mBatch +"'";
		return EntityManager.getAllByHql(hql);
	}
	
	public String getTotalBoxSumByttidAndpid(String ttid,String pid) throws HibernateException, SQLException {
		String hql = "select sum(realboxnum) as boxnum   from take_ticket_detail_batch_bit   where  ttid='" + ttid+
				 "' and productid='"+pid+"'";
		String quantity="";
		Map map = (Map) EntityManager.jdbcquery(hql).get(0);
		if (map.get("boxnum") != null) {
			quantity = (String)map.get("boxnum");
		}
		return quantity;
	}
	
	public String getTotalScatterSumByttidAndpid(String ttid,String pid) throws HibernateException, SQLException {
		String hql = "select sum(realscatternum) as scatternum   from take_ticket_detail_batch_bit   where  ttid='" + ttid+
				 "' and productid='"+pid+"'";
		String quantity="";
		Map map = (Map) EntityManager.jdbcquery(hql).get(0);
		if (map.get("scatternum") != null) {
			quantity = (String)map.get("scatternum");
		}
		return quantity;
	}
	
	public String getTotalrealquantityByttidAndpid(String ttid,String pid) throws HibernateException, SQLException {
		String hql = "select sum(realQuantity) as realQuantity   from take_ticket_detail_batch_bit   where  ttid='" + ttid+
				 "' and productid='"+pid+"'";
		String quantity="";
		Map map = (Map) EntityManager.jdbcquery(hql).get(0);
		if (map.get("realquantity") != null) {
			quantity = (String)map.get("realquantity");
		}
		return quantity;
	}
	
	public List<TakeTicketDetailBatchBit> getBatchBitByTTID(String ttid){
		String hql = "from TakeTicketDetailBatchBit where ttid = '"+ttid + "'";
		return EntityManager.getAllByHql(hql);
	}

	public List<TakeTicketDetailBatchBit> getBatchBitByTTIDQ(String ttid){
		String hql = "from TakeTicketDetailBatchBit where quantity>0 and ttid = '"+ttid + "'";
		return EntityManager.getAllByHql(hql);
	}

	public void returnProductStockPrepareOut(String ttid) throws HibernateException, SQLException, Exception{
		TakeTicket tt = new AppTakeTicket().getTakeTicketById(ttid);
		List<TakeTicketDetailBatchBit> batchBits = getBatchBitByTTID(ttid);
		for (TakeTicketDetailBatchBit takeTicketDetailBatchBit : batchBits) {
			Double prepareOutQuantity = takeTicketDetailBatchBit.getQuantity();
			//RichieYu 0311
			//String sql="update Product_Stockpile_All set stockpile=stockpile + "+prepareOutQuantity+", prepareout=prepareout - "+prepareOutQuantity+" where productid='"+takeTicketDetailBatchBit.getProductid()+"' and warehouseid='"+tt.getWarehouseid()+"'  and batch='"+takeTicketDetailBatchBit.getBatch()+"'";
			//EntityManager.updateOrdelete(sql);
			String sql2="update Product_Stockpile set stockpile=stockpile + "+prepareOutQuantity+", prepareout=prepareout - "+prepareOutQuantity+" where productid='"+takeTicketDetailBatchBit.getProductid()+"' and warehouseid='"+tt.getWarehouseid()+"'  and batch='"+takeTicketDetailBatchBit.getBatch()+"' and warehousebit='"+takeTicketDetailBatchBit.getWarehouseBit()+"'";
			EntityManager.updateOrdelete(sql2);	
		}
	}
	
	public void deleteBatchBitByTTDID(String ttid) throws HibernateException, SQLException, Exception{
		String hql = "delete from take_ticket_detail_batch_bit where ttid = '"+ttid+"'";
		EntityManager.updateOrdelete(hql);
	}
	
	public void deleteZeroQuantityBatchBitByTTDID(String ttid) throws HibernateException, SQLException, Exception{
		String hql = "delete from take_ticket_detail_batch_bit where ttid = '"+ttid+"' and quantity = 0 and realquantity = 0";
		EntityManager.updateOrdelete(hql);
	}
	
	public List getTotalTakeTicketDetailBatchBitReport(String begindate, String enddate, String productid){
		String hql = "select new map (tt.oname as oname, ttdb.batch as batch, ttdb.quantity as quantity, tt.auditdate as auditdate) "
			+"from TakeTicketDetailBatchBit as ttdb, TakeTicket as tt "
			+"where ttdb.ttid = tt.id and tt.isaudit = 1 and tt.auditdate >= '"+begindate+"' and tt.auditdate <= '"+enddate+"' and ttdb.productid = '"+productid+"' "
			+"order by tt.auditdate, ttdb.batch";
		return EntityManager.getAllByHql(hql);
	}
	
	public List getBatchBit(String pWhereClause) throws HibernateException, SQLException {
		String hql = "select ttid,productid,sum(realQuantity) as realQuantity,sum(realscatternum) as realscatternum,sum(realboxnum) as realboxnum " +
				"from take_ticket_detail_batch_bit " +
				"where ttid in (select tt.id from take_ticket as tt,take_ticket_detail as  ttd " + pWhereClause+" group by tt.id) group by ttid,productid ";

		return EntityManager.jdbcquery(hql);
	}
	
	public List getTotalkgquantityByWarehouseid(String warehouseid, String pWhereClause) throws HibernateException, SQLException {
		String sql = "select ttd.productid, sum(ttd.realQuantity) as realQuantity " +
				"from take_ticket tt, take_ticket_detail_batch_bit ttd "+pWhereClause+" " +
				"and tt.warehouseid = '"+warehouseid+"' " +
				"group by ttd.productid";
		return EntityManager.jdbcquery(sql);
	}
}
