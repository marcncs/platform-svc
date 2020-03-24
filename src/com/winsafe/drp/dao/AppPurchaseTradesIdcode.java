package com.winsafe.drp.dao;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.pager2.PageQuery;

public class AppPurchaseTradesIdcode {

	public List searchPurchaseTradesIdcode(HttpServletRequest request, int pagesize, String pWhereClause)
	throws Exception {
		String hql = " from PurchaseTradesIdcode  " + pWhereClause + " order by id desc";
		return PageQuery.hbmQuery(request, hql, pagesize);
	}
	public void addPurchaseTradesIdcode(PurchaseTradesIdcode pii) throws Exception{		
		EntityManager.save(pii);		
	}
	
	public void updPurchaseTradesIdcode(PurchaseTradesIdcode pii) throws Exception{		
		EntityManager.update(pii);		
	}
	
	public void updPurchaseTradesIdcodeBillNo(String billno,String truebillno) throws Exception{		
		String sql="update purchase_trades_idcode set ptid='"+truebillno+"' where ptid='"+billno+"'";
		EntityManager.updateOrdelete(sql);			
	}
	
	public PurchaseTradesIdcode getPurchaseTradesIdcodeById(Long id) throws Exception{
		String sql = "from PurchaseTradesIdcode where id="+id;
		return (PurchaseTradesIdcode)EntityManager.find(sql);
		}
	
	public void delPurchaseTradesIdcodeById(long id) throws Exception{		
		String sql="delete from purchase_trades_idcode where id="+id;
		EntityManager.updateOrdelete(sql);		
	}
	
	public void delPurchaseTradesIdcodeByPtid(String ptid) throws Exception{		
		String sql="delete from purchase_trades_idcode where ptid='"+ptid+"'";
		EntityManager.updateOrdelete(sql);		
	}
	
	public void delPurchaseTradesIdcodeByPid(String productid, String ptid) throws Exception{		
		String sql="delete from purchase_trades_idcode where productid='"+productid+"' and ptid='"+ptid+"'";
		EntityManager.updateOrdelete(sql);
		
	}
	
	public List getPurchaseTradesIdcodeByptid(String ptid) throws Exception{
		String sql = "from PurchaseTradesIdcode where  ptid='"+ptid+"'";
		return EntityManager.getAllByHql(sql);
	}
	
	public List getPurchaseTradesIdcodeByptid(String ptid, int isidcode) throws Exception{
		String sql = "from PurchaseTradesIdcode where  ptid='"+ptid+"' and isidcode="+isidcode;
		return EntityManager.getAllByHql(sql);
	}
	
	public List getPurchaseTradesIdcodeByPidBatch(String productid, String ptid) throws Exception{
		String sql = "from PurchaseTradesIdcode where productid='"+productid+"' and ptid='"+ptid+"'";
		return EntityManager.getAllByHql(sql);
	}
	
	public List getPurchaseTradesIdcodeByPidBatch(String productid, String ptid, int isidcode) throws Exception{
		String sql = "from PurchaseTradesIdcode where productid='"+productid+"' and ptid='"+ptid+"' and isidcode="+isidcode;
		return EntityManager.getAllByHql(sql);
	}
	
	public PurchaseTradesIdcode getPurchaseTradesIdcodeByidcode(String productid, String ptid, String idcode) throws Exception{
		//String sql = " from PurchaseTradesIdcode where productid='"+productid+"' and ptid='"+ptid+"' and idcode='"+idcode+"'";
		String sql = " from PurchaseTradesIdcode where productid='"+productid+"' and idcode='"+idcode+"'";
    	return (PurchaseTradesIdcode)EntityManager.find(sql);
	}
	
	
	public double getQuantitySumByptidProductid(String productid, String ptid) throws Exception{
		String sql = "select sum(pii.quantity*f.xquantity) from PurchaseTradesIdcode as pii,FUnit as f "+
		"where pii.productid=f.productid and pii.unitid=f.funitid and pii.productid='"+productid+"' and pii.ptid='"+ptid+"'";
		return EntityManager.getdoubleSum(sql);
	}
}
