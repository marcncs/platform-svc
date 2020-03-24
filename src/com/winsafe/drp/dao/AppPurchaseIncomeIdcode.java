package com.winsafe.drp.dao;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.pager2.PageQuery;

public class AppPurchaseIncomeIdcode {

	public List searchPurchaseIncomeIdcode(HttpServletRequest request, int pagesize, String pWhereClause)
	throws Exception {
		String hql = " from PurchaseIncomeIdcode  " + pWhereClause + " order by id desc";
		return PageQuery.hbmQuery(request, hql, pagesize);
	}
	public void addPurchaseIncomeIdcode(PurchaseIncomeIdcode pii) throws Exception{		
		EntityManager.save(pii);		
	}
	
	public void updPurchaseIncomeIdcode(PurchaseIncomeIdcode pii) throws Exception{		
		EntityManager.update(pii);		
	}
	
	public void updPurchaseIncomeIdcodeBillNo(String billno,String truebillno) throws Exception{		
		String sql="update purchase_income_idcode set PIID='"+truebillno+"' where PIID='"+billno+"'";
		EntityManager.updateOrdelete(sql);			
	}
	
	public PurchaseIncomeIdcode getPurchaseIncomeIdcodeById(Long id) throws Exception{
		String sql = "from PurchaseIncomeIdcode where id="+id;
		return (PurchaseIncomeIdcode)EntityManager.find(sql);
		}
	
	public void delPurchaseIncomeIdcodeById(long id) throws Exception{		
		String sql="delete from purchase_income_idcode where id="+id;
		EntityManager.updateOrdelete(sql);		
	}
	
	public void delPurchaseIncomeIdcodeByPiid(String piid) throws Exception{		
		String sql="delete from purchase_income_idcode where piid='"+piid+"'";
		EntityManager.updateOrdelete(sql);		
	}
	
	public void delPurchaseIncomeIdcodeByPid(String productid, String piid) throws Exception{		
		String sql="delete from purchase_income_idcode where productid='"+productid+"' and piid='"+piid+"'";
		EntityManager.updateOrdelete(sql);
		
	}
	
	public List getPurchaseIncomeIdcodeByPiid(String piid) throws Exception{
		String sql = "from PurchaseIncomeIdcode where  piid='"+piid+"'";
		return EntityManager.getAllByHql(sql);
	}
	
	public List getPurchaseIncomeIdcodeByPiid(String piid, int isidcode) throws Exception{
		String sql = "from PurchaseIncomeIdcode where  piid='"+piid+"' and isidcode="+isidcode;
		return EntityManager.getAllByHql(sql);
	}
	
	public List getPurchaseIncomeIdcodeByPidBatch(String productid, String piid) throws Exception{
		String sql = "from PurchaseIncomeIdcode where productid='"+productid+"' and piid='"+piid+"'";
		return EntityManager.getAllByHql(sql);
	}
	
	public List getPurchaseIncomeIdcodeByPidBatch(String productid, String piid, int isidcode) throws Exception{
		String sql = "from PurchaseIncomeIdcode where productid='"+productid+"' and piid='"+piid+"' and isidcode="+isidcode;
		return EntityManager.getAllByHql(sql);
	}
	
	public PurchaseIncomeIdcode getPurchaseIncomeIdcodeByidcode(String productid, String piid, String idcode) throws Exception{
		//String sql = " from PurchaseIncomeIdcode where productid='"+productid+"' and piid='"+piid+"' and idcode='"+idcode+"'";
		String sql = " from PurchaseIncomeIdcode where productid='"+productid+"' and idcode='"+idcode+"'";
		return (PurchaseIncomeIdcode)EntityManager.find(sql);
	}
	
	
	public double getQuantitySumByPiidProductid(String productid, String piid) throws Exception{
		String sql = "select sum(pii.quantity*f.xquantity) from PurchaseIncomeIdcode as pii,FUnit as f "+
		"where pii.productid=f.productid and pii.unitid=f.funitid and pii.productid='"+productid+"' and pii.piid='"+piid+"'";
		return EntityManager.getdoubleSum(sql);
	}
	
	public void delPurchaseIncomeIDcodeByNCcode(String nccode) throws Exception {
		String sql = "delete from purchase_income_idcode where Nccode='"
				+ nccode + "'";
		EntityManager.updateOrdelete(sql);
	}
	
	
}
