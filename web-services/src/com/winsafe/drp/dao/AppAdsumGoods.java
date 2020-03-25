package com.winsafe.drp.dao;

import java.util.List;

import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.pager.SimplePageInfo;

public class AppAdsumGoods {


	public List getAdsumGoods(int pagesize, String pWhereClause,
			SimplePageInfo pPgInfo) throws Exception {
		List pb = null;
		int targetPage = pPgInfo.getCurrentPageNo();
		String sql = "select ag.id,ag.poid,ag.pid,ag.totalsum,ag.receivedate,ag.isaudit from AdsumGoods as ag "
				+ pWhereClause + " order by ag.makedate desc ";
		pb = EntityManager.getAllByHql(sql, targetPage, pagesize);
		return pb;
	}
	
	
	public List getAdsumGoodsToQuality(int pagesize, String pWhereClause,
			SimplePageInfo pPgInfo) throws Exception {
		List pb = null;
		int targetPage = pPgInfo.getCurrentPageNo();
		String sql = "select ag.id,ag.pid,ag.obtaincode,ag.receivedate,ag.totalsum,ag.purchasesort from AdsumGoods as ag "
				+ pWhereClause + " order by ag.makedate desc ";
		pb = EntityManager.getAllByHql(sql, targetPage, pagesize);
		return pb;
	}

	
	public void addAdsumGoods(Object ag) throws Exception {
		
		EntityManager.save(ag);
		
	}
	
	public void delAdsumGoods(String id)throws Exception{
		
		String sql="delete from adsum_goods where id='"+id+"'";
		EntityManager.updateOrdelete(sql);
		
	}
	

	
	public AdsumGoods getAdsumGoodsByID(String id) throws Exception {
		AdsumGoods ag = null;
		String sql = " from AdsumGoods as ag where ag.id='" + id + "'";
		ag = (AdsumGoods) EntityManager.find(sql);
		return ag;
	}

	
	public void updAdsumGoodsByID(AdsumGoods pb, String receivedate)
			throws Exception {
		
		String sql = "update adsum_goods set PID='" + pb.getPid() + "',PLinkman='"
				+ pb.getPlinkman() + "',Obtaincode='"+pb.getObtaincode()+"',PurchaseDept=" + pb.getPurchasedept()
				+ ",PurchaseID=" + pb.getPurchaseid() + ",Totalsum=" + pb.getTotalsum()
				+ ",ReceiveDate='" + receivedate + "',Remark='" + pb.getRemark()
				+ "' where ID='" + pb.getId() + "'";
		EntityManager.updateOrdelete(sql);
		
	}

	
	public void updIsRefer(String id) throws Exception {
		
		String sql = "update adsum_goods set isrefer=1 where id='" + id+"'";
		EntityManager.updateOrdelete(sql);
		
	}
	
	public int waitInputAdsumGoodsCount() throws Exception {
		int count = 0;
		String sql = "select count(*) from AdsumGoods as ag where ag.isaudit=1 and ag.istransferincome=0";
		count = EntityManager.getRecordCount(sql);
		return count;
	}

	
	public List waitApproveAdsumGoods(int pagesize, String pWhereClause,
			SimplePageInfo pPgInfo) throws Exception {
		List wpa = null;
		int targetPage = pPgInfo.getCurrentPageNo();
		String sql = "select ag.id,ag.pid,ag.makeid,ag.makedate,ag.totalsum,ag.receivedate,aga.actid,aga.approve from AdsumGoods as ag,AdsumGoodsApprove as aga "
				+ pWhereClause
				+ " order by ag.approvestatus, ag.makedate desc";
		wpa = EntityManager.getAllByHql(sql, targetPage, pagesize);
		return wpa;
	}
	
	public void updIsComplete(String agid, Integer iscomplete) throws Exception {
		
		String sql = "update adsum_goods set istransferincome=" + iscomplete
				+ " where id = '" + agid+"'";
		EntityManager.updateOrdelete(sql);
		
	}

	
	public void updIsApprove(String id, int isapprove) throws Exception {
		String sql = "update adsum_goods set approvestatus=" + isapprove
				+ ",approvedate='"+DateUtil.getCurrentDateString()+"' where id ='" + id+"'";
		EntityManager.updateOrdelete(sql);
	}

	
	
	
	public void updIsAudit(String ppid, Long userid,Integer audit) throws Exception {
		
		String sql = "update adsum_goods set isaudit="+audit+",auditid=" + userid
				+ ",auditdate='" + DateUtil.getCurrentDateString()
				+ "' where id='" + ppid + "'";
		EntityManager.updateOrdelete(sql);
		
	}
	


}
