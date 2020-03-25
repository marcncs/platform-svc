package com.winsafe.drp.dao;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.pager.SimplePageInfo;
import com.winsafe.hbm.util.pager2.PageQuery;

public class AppPurchaseInquire {

	public List getPurchaseInquire(HttpServletRequest request, int pagesize, String pWhereClause) throws Exception {
		String sql = " from PurchaseInquire as pi "
				+ pWhereClause + " order by pi.makedate desc ";
		return PageQuery.hbmQuery(request, sql, pagesize);
	}

	public void addPurchaseInquire(Object pi) throws Exception {
		
		EntityManager.save(pi);
		
	}

	public PurchaseInquire getPurchaseInquireByID(Integer id) throws Exception {
		PurchaseInquire pi = new PurchaseInquire();
		String sql = " from PurchaseInquire as pi where pi.id=" + id;
		pi = (PurchaseInquire) EntityManager.find(sql);
		return pi;
	}

	public void updPurchaseInquire(PurchaseInquire p)
			throws Exception {
		EntityManager.update(p);
		
	}
	
	
	public void updIsComplete(String piid, Integer iscomplete) throws Exception {
		
		String sql = "update purchase_inquire set iscaseend=" + iscomplete
				+ " where id =" + piid+"";
		EntityManager.updateOrdelete(sql);
		
	}
	

	public void updIsAudit(Long piid, Long userid,Integer audit) throws Exception {
		
		String sql = "update purchase_inquire set isaudit="+audit+",auditid=" + userid
				+ ",auditdate='" + DateUtil.getCurrentDateString()
				+ "' where id=" + piid + " ";
		EntityManager.updateOrdelete(sql);
		
	}
	

	public int waitInputPurchaseInquireCount() throws Exception {
		int count = 0;
		String sql = "select count(*) from PurchaseInquire as pi where pi.isaudit=1 and pi.iscaseend=0 ";
		count = EntityManager.getRecordCount(sql);
		return count;
	}
	
	
	public List getPurchaseInquireToPurchase(int pagesize, String pWhereClause,
			SimplePageInfo pPgInfo) throws Exception {
		List pa = null;
		int targetPage = pPgInfo.getCurrentPageNo();
		String sql = "select pi.id,pi.ppid,pi.inquiretitle,pi.pid,pi.plinkman,pi.makeid,pi.makedate,pi.validdate from PurchaseInquire as pi "
				+ pWhereClause + " order by pi.makedate desc ";
		pa = EntityManager.getAllByHql(sql, targetPage, pagesize);
		return pa;
	}

	public List<PurchaseInquire> getPurchaseInquire(String whereSql) {
		String sql = " from PurchaseInquire as pi "
			+ whereSql + " order by pi.makedate desc ";
		return EntityManager.getAllByHql(sql);
	}
}
