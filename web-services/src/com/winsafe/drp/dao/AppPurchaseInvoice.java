package com.winsafe.drp.dao;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.pager2.PageQuery;

public class AppPurchaseInvoice {

	
	public List<PurchaseInvoice> searchPurchaseInvoice(HttpServletRequest request, int pagesize, String pWhereClause) throws Exception {
		String hql = " from PurchaseInvoice as pi "
			+ pWhereClause + " order by pi.makedate desc ";
		return PageQuery.hbmQuery(request, hql, pagesize);
	}
	
	public List<PurchaseInvoice> searchPurchaseInvoice(String pWhereClause) throws Exception {
		String hql = " from PurchaseInvoice as pi "
			+ pWhereClause + " order by pi.makedate desc ";
		return EntityManager.getAllByHql(hql);
	}

	public void addPurchaseInvoice(Object pi) throws Exception {
		
		EntityManager.save(pi);
		
	}

	public PurchaseInvoice getPurchaseInvoiceByID(Integer id) throws Exception {
		PurchaseInvoice pi = new PurchaseInvoice();
		String sql = " from PurchaseInvoice as pi where pi.id=" + id;
		pi = (PurchaseInvoice) EntityManager.find(sql);
		return pi;
	}

	public void updPurchaseInvoiceByID(PurchaseInvoice pi) throws Exception {

		EntityManager.update(pi);
		
	}
	
	public List selectPurchaseInvoice(String whereSql)throws Exception{
		List ls = null;
		String sql="select pid.id,pi.invoicedate,pi.provideid,pid.productid,pid.productname,pid.specmode,pid.unitid,pid.quantity,pid.unitprice,pid.subsum from PurchaseInvoice as pi,PurchaseInvoiceDetail as pid where pi.id=pid.piid and pid.issettlement=0 "+whereSql;
		ls = EntityManager.getAllByHql(sql);
		return ls;
		
	}
	
	
	public void updPurchaseInvoiceSettlement(Long id)throws Exception{

		String sql="update purchase_invoice set issettlement=1 where id="+id;
		EntityManager.updateOrdelete(sql);

	}
	

	public void updIsAudit(Integer piid, Integer userid,Integer audit) throws Exception {
		
		String sql = "update purchase_invoice set isaudit="+audit+",auditid=" + userid
				+ ",auditdate='" + DateUtil.getCurrentDateString()
				+ "' where id=" + piid + " ";
		EntityManager.updateOrdelete(sql);
		
	}
	

	public void delPurchaseInvoice(Integer id)throws Exception{
		
		String sql="delete from purchase_invoice where id="+id+"";
		EntityManager.updateOrdelete(sql);
		
	}
}
