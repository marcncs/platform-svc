package com.winsafe.drp.dao;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.pager.SimplePageInfo;
import com.winsafe.hbm.util.pager2.PageQuery;

public class AppSaleInvoice {

	
	public List getSaleInvoice(int pagesize, String pWhereClause,
			SimplePageInfo pPgInfo) throws Exception {
		List pls = null;
		int targetPage = pPgInfo.getCurrentPageNo();
		String sql = "select si.id,si.invoicecode,si.invoicetype,si.makeinvoicedate,si.invoicedate,si.invoicesum,si.isaudit,si.cid from SaleInvoice as si "
				+ pWhereClause + " order by si.makedate desc";
		pls = EntityManager.getAllByHql(sql, targetPage, pagesize);
		return pls;
	}
	
	public List getSaleInvoiceAll(HttpServletRequest request,int pagesize, String pWhereClause) throws Exception {

		String sql = " from SaleInvoice as si "
				+ pWhereClause + " order by si.makedate desc";
		return PageQuery.hbmQuery(request, sql, pagesize);
	}
	public List<SaleInvoice> getSaleInvoiceAll(String pWhereClause) throws Exception {

		String sql = " from SaleInvoice as si "
				+ pWhereClause + " order by si.makedate desc";
		return EntityManager.getAllByHql(sql);
	}

	public void addSaleInvoice(Object si) throws Exception {
		
		EntityManager.save(si);
		
	}

	public void delSaleInvoice(Integer id)throws Exception{
		
		String sql="delete from Sale_Invoice where id="+id;
		EntityManager.updateOrdelete(sql);
		
	}
	
	public SaleInvoice getSaleInvoiceByID(Integer id) throws Exception {
		String sql = " from SaleInvoice as si where si.id=" + id;
		return(SaleInvoice) EntityManager.find(sql);
	}

	public void updSaleInvoiceByID(SaleInvoice si) throws Exception {
		

		EntityManager.update(si);
		
	}


	public void updIsAudit(Integer siid, Integer userid,Integer audit) throws Exception {
		
		String sql = "update sale_invoice set isaudit="+audit+",auditid=" + userid
				+ ",auditdate='" + DateUtil.getCurrentDateString()
				+ "' where id=" + siid ;
		EntityManager.updateOrdelete(sql);
		
	}
}
