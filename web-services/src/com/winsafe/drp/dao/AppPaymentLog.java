package com.winsafe.drp.dao;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.pager.SimplePageInfo;
import com.winsafe.hbm.util.pager2.PageQuery;

public class AppPaymentLog {

	
	public PaymentLog getPaymentLogByID(String id) throws Exception {
		String sql = " from PaymentLog where id='" + id + "'";
		PaymentLog pl = (PaymentLog) EntityManager.find(sql);
		return pl;
	}
	
	
	public void delPaymentLog(String plid)throws Exception{
		
		String sql="delete from payment_log where id='"+plid+"'";
		EntityManager.updateOrdelete(sql);
		
	}
	
	public double getTotalSum(String pWhereClause) throws Exception {
		String sql = "select sum(pl.paysum) from PaymentLog as pl "
				+ pWhereClause;
		return EntityManager.getdoubleSum(sql);
	}

	
	public List getPaymentLog(HttpServletRequest request, int pagesize,
			String pWhereClause) throws Exception {
		String sql = " from PaymentLog as pl "
				+ pWhereClause + " order by pl.makedate desc";
		return PageQuery.hbmQuery(request, sql, pagesize);
	}
	
	public List getPaymentLog(String pWhereClause) throws Exception {
		String sql = " from PaymentLog as pl "
				+ pWhereClause + " order by pl.makedate desc";
		List pl = EntityManager.getAllByHql(sql);
		return pl;
	}
	
	public List getPaymentLogByPoid(String poid) throws Exception {		
		String sql = " from PaymentLog where paymode=3 and ispay=1 and paysum>alreadyspend and poid='" + poid + "'";
		return EntityManager.getAllByHql(sql);		
	}
	
	public List getPaymentLogTotal(String pWhereClause) throws Exception {
		String sql = "select pl.id,pl.poid,pl.payee,pl.paymode,pl.fundsrc,pl.paysum,pl.makeorganid,pl.makeid,pl.makedate from Payment_Log as pl "
				+ pWhereClause + " order by pl.makedate desc";
		return EntityManager.jdbcquery(sql);
	}
	
	
	
	public List getListCompletePaymentLog(int pagesize, String pWhereClause,
			SimplePageInfo pPgInfo) throws Exception {

		int targetPage = pPgInfo.getCurrentPageNo();
		String sql = "select pl.id,pl.payattach,pl.paysrc,pl.paysum,pl.billdate,pl.billnum,pl.ispay from PaymentLog as pl "
				+ pWhereClause + " order by pl.ispay desc";
		List pl = EntityManager.getAllByHql(sql, targetPage, pagesize);
		return pl;
	}
	

	public Double getPaymentSumByPOID(String poid)throws Exception{
		Double s= 0.00;
		String sql = "select sum(pl.paysum) from PaymentLog as pl where pl.poid='"+poid+"'";
		s = EntityManager.getdoubleSum(sql);
		return s;
	}

	
	public double getOverPaySum(Long pbid) throws Exception {
		double ap = 0.00;
		String sql = "select sum(pl.paysum) from PaymentLog as pl where pl.pbid="
				+ pbid + " and paystatus =1";
		ap = EntityManager.getdoubleSum(sql);
		return ap;
	}

	
	public void addPaymentLog(Object paymentlog) throws Exception {
		
		EntityManager.save(paymentlog);
		
	}

	
	public void updPaymentLog(PaymentLog pl)
			throws Exception {
		
		//String sql = "update payment_log set poid='"+pl.getPoid()+"',payee='"+pl.getPayee()+"',paypurpose='"+pl.getPaypurpose()+"',paymode="+pl.getPaymode()+",paysum="+pl.getPaysum()+",owebalance="+pl.getOwebalance()+",billnum='"+pl.getBillnum()+"',remark='"+pl.getRemark()+"' where id='"+pl.getId()+"'";
		EntityManager.update(pl);
		
	}

	public void updIsRefer(String id) throws Exception {
		
		String sql = "update payment_log set isrefer=1 where id='" + id+"'";
		EntityManager.updateOrdelete(sql);
		
	}

	
	public List waitApprovePaymentLog(int pagesize, String pWhereClause,
			SimplePageInfo pPgInfo) throws Exception {
		
		int targetPage = pPgInfo.getCurrentPageNo();
		String sql = "select pl.id,pl.poid,pl.payee,pl.paysum,pl.makeid,pl.makedate,pla.approve,pla.actid  from PaymentLog as pl,PaymentLogApprove as pla "
				+ pWhereClause
				+ " order by pla.approve, pl.makedate desc";
		List wpa = EntityManager.getAllByHql(sql, targetPage, pagesize);
		return wpa;
	}



	public void completePayment(String id,Long userid) throws Exception {
		
		String sql = " update payment_log set ispay =1,cashier="+userid+",paydate='"+DateUtil.getCurrentDateString()+"' where id ='" + id+"'";
		EntityManager.updateOrdelete(sql);

	}

	//
	public double getAlreadyPayment(String paid) throws Exception {
		double totalincome = Double.parseDouble("0.00");
		String sql = "select sum(pl.paysum) from PaymentLog as pl where pl.pid='"
				+ paid + "' and ispay =1";
		totalincome = EntityManager.getdoubleSum(sql);
		return totalincome;
	}
	

	public void updIsAudit(String oid, Integer userid,Integer audit) throws Exception {
		
		String sql = "update payment_log set isaudit="+audit+",auditid=" + userid
				+ ",auditdate='" + DateUtil.getCurrentDateString()
				+ "' where id='" + oid + "'";
		EntityManager.updateOrdelete(sql);
		
	}
	
	
	public void updIsPay(String oid, Integer userid,Integer audit) throws Exception {
		
		String sql = "update payment_log set ispay="+audit+",payid=" + userid
				+ ",paydate='" + DateUtil.getCurrentDateString()
				+ "' where id='" + oid + "'";
		EntityManager.updateOrdelete(sql);
		
	}

}
