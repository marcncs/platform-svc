package com.winsafe.drp.dao;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.pager.SimplePageInfo;
import com.winsafe.hbm.util.pager2.PageQuery;

public class AppIncomeLog {


	public List getIncomeLog(HttpServletRequest request, int pagesize,
			String pWhereClause) throws Exception {
		String sql = "from IncomeLog as il "
				+ pWhereClause + " order by il.makedate desc ";
		return PageQuery.hbmQuery(request, sql, pagesize);
	}
	
	public List getIncomeLog(String pWhereClause) throws Exception {
		String sql = "from IncomeLog as il "
				+ pWhereClause + " order by il.makedate desc ";
		List pl = EntityManager.getAllByHql(sql);
		return pl;
	}
	
	public double getTotalSum(String pWhereClause) throws Exception {
		String sql = "select sum(il.incomesum) from IncomeLog as il "
				+ pWhereClause;
		return EntityManager.getdoubleSum(sql);
	}
	
	public List getIncomeLogByRoid(String roid) throws Exception {
		String sql = "from IncomeLog as il where il.roid='"+roid+"' and il.paymentmode=3 and il.isreceive=1  and il.incomesum>il.alreadyspend order by il.makedate desc ";
		List pl = EntityManager.getAllByHql(sql);
		return pl;
	}
	
	
	public List getIncomeLogTotal(String pWhereClause) throws Exception {
		String sql = "select il.id,il.roid,il.drawee,il.paymentmode,il.incomesum,il.fundattach,il.makeorganid,il.makedate from Income_Log as il "
				+ pWhereClause + " order by il.makedate desc ";
		return EntityManager.jdbcquery(sql);
	}
	

	public Double getIncomeLogSumByROID(String roid)throws Exception{
		Double s= 0.00;
		String sql = "select sum(il.incomesum) from IncomeLog as il where il.roid='"+roid+"'";
		s = EntityManager.getdoubleSum(sql);
		return s;
	}
	
	public List getListCompleteIncomeLog(int pagesize, String pWhereClause,
			SimplePageInfo pPgInfo) throws Exception {
		int targetPage = pPgInfo.getCurrentPageNo();
		String sql = "select il.id,il.rid,il.fundsrc,il.fundattach,il.incomesum,il.incomedate,il.billnum,il.incomestatus from IncomeLog as il "
				+ pWhereClause + " order by il.incomestatus ";
		List pl = EntityManager.getAllByHql(sql, targetPage, pagesize);
		return pl;
	}
	

	public void delIncomeLog(String ilid)throws Exception{
		String sql="delete from Income_Log where id='"+ilid+"'";
		EntityManager.updateOrdelete(sql);
	}
	
	public void delIncomeByROIDBILLNO(String roid,String billno)throws Exception{
		
		String sql="delete Income_Log where roid='"+roid+"' and billnum='"+billno+"'";
		EntityManager.updateOrdelete(sql);
		
	}
	

	public IncomeLog getIncomeLogByID(String id) throws Exception {
		String sql = " from IncomeLog where id='" + id + "'";
		IncomeLog il = (IncomeLog) EntityManager.find(sql);
		return il;
	}

	
	public IncomeLog getIncomeLogByIDOrgID(String oid,String orgid) throws Exception {
		String sql = " from IncomeLog where oid='" + oid + "' and makeorganid='"+orgid+"'";
		IncomeLog il = (IncomeLog) EntityManager.find(sql);
		return il;
	}
	
	public IncomeLog getIncomeLogByBillnum(String billnum) throws Exception {
		String sql = " from IncomeLog where billnum='" + billnum + "' ";
		return (IncomeLog) EntityManager.find(sql);
	}

	
	public double getAlreadyIncomeSum(String rid) throws Exception {
		double ap = 0.00;
		String sql = "select sum(il.incomesum) from IncomeLog as il where il.rid='"
				+ rid + "' and il.approvestatus !=3";
		ap = EntityManager.getdoubleSum(sql);
		return ap;
	}

	
	public double getCompleteIncomeSum(String rid) throws Exception {
		double s = 0.00;
		String sql = "select sum(il.incomesum) from IncomeLog as il where il.rid="
				+ rid + " and il.incomestatus=1";
		s = EntityManager.getdoubleSum(sql);
		return s;
	}

	public void addIncomeLog(Object incomelog) throws Exception {
		 EntityManager.save(incomelog);
	}

	public void updIsRefer(String id) throws Exception {
		String sql = "update income_log set isrefer=1 where id='" + id + "'";
		EntityManager.updateOrdelete(sql);
	}

	
	public void updIncomeLog(IncomeLog il)
			throws Exception {
		 EntityManager.update(il);

	}

	public List waitApproveIncomeLog(int pagesize, String pWhereClause,
			SimplePageInfo pPgInfo) throws Exception {
		int targetPage = pPgInfo.getCurrentPageNo();
		String sql = "select il.id,il.roid,il.drawee,il.incomesum,il.fundsrc,il.fundattach,il.makeid,ila.approve,ila.actid from IncomeLog as il,IncomeLogApprove as ila "
				+ pWhereClause
				+ " order by ila.approve, il.makedate desc";
		List wpa = EntityManager.getAllByHql(sql, targetPage, pagesize);
		return wpa;
	}

	public void updIsApprove(String id, int isapprove) throws Exception {
		String sql = "update income_log set approvestatus=" + isapprove
				+ " where id ='" + id+"'";
		EntityManager.updateOrdelete(sql);
	}

	public void completeIncome(String id,Long userid) throws Exception {
		String sql = " update income_log set incomestatus =1,cashier="+userid+",cashierdate='"+DateUtil.getCurrentDateString()+"' where id ='" + id+"'";
		EntityManager.updateOrdelete(sql);
	}

	//
	public double getAlreadyIncome(String pWhereClause) throws Exception {
		double totalincome = Double.parseDouble("0.00");
		String sql = "select sum(il.incomesum) from IncomeLog as il "
				+ pWhereClause + " il.approvestatus =2 and incomestatus=1";
		totalincome = EntityManager.getdoubleSum(sql);
		return totalincome;
	}

	
	public double getOverIncomeSum(Long slid) throws Exception {
		double ap = 0.00;
		String sql = "select sum(il.incomesum) from IncomeLog as il where il.slid="
				+ slid + " and incomestatus =1";
		ap = EntityManager.getdoubleSum(sql);
		return ap;
	}
	

	public void updIsAudit(String oid, Integer userid,Integer audit) throws Exception {
		String sql = "update income_log set isaudit="+audit+",auditid=" + userid
				+ ",auditdate='" + DateUtil.getCurrentDateString()
				+ "' where id='" + oid + "'";
		EntityManager.updateOrdelete(sql);

	}
	
	public void updIsReceive(String oid, Integer userid,Integer audit) throws Exception {
		String sql = "update income_log set isreceive="+audit+",receiveid=" + userid
				+ ",receivedate='" + DateUtil.getCurrentDateString()
				+ "' where id='" + oid + "'";
		EntityManager.updateOrdelete(sql);

	}


}
