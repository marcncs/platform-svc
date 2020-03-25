package com.winsafe.drp.dao;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.pager2.PageQuery;

public class AppSaleTradesIdcode {

	public List searchSaleTradesIdcode(HttpServletRequest request, int pagesize, String pWhereClause)
	throws Exception {
		String hql = " from SaleTradesIdcode  " + pWhereClause + " order by id desc";
		return PageQuery.hbmQuery(request, hql, pagesize);
	}
	public void addSaleTradesIdcode(SaleTradesIdcode pii) throws Exception{		
		EntityManager.save(pii);		
	}
	
	public void updSaleTradesIdcode(SaleTradesIdcode pii) throws Exception{		
		EntityManager.update(pii);		
	}
	
	public void updSaleTradesIdcodeBillNo(String billno,String truebillno) throws Exception{		
		String sql="update sale_trades_idcode set stid='"+truebillno+"' where stid='"+billno+"'";
		EntityManager.updateOrdelete(sql);			
	}
	
	public SaleTradesIdcode getSaleTradesIdcodeById(Long id) throws Exception{
		String sql = "from SaleTradesIdcode where id="+id;
		return (SaleTradesIdcode)EntityManager.find(sql);
		}
	
	public void delSaleTradesIdcodeById(long id) throws Exception{		
		String sql="delete from sale_trades_idcode where id="+id;
		EntityManager.updateOrdelete(sql);		
	}
	
	public void delSaleTradesIdcodeByStid(String stid) throws Exception{		
		String sql="delete from sale_trades_idcode where stid='"+stid+"'";
		EntityManager.updateOrdelete(sql);		
	}
	
	public void delSaleTradesIdcodeByPid(String productid, String stid) throws Exception{		
		String sql="delete from sale_trades_idcode where productid='"+productid+"' and stid='"+stid+"'";
		EntityManager.updateOrdelete(sql);
		
	}
	
	public List getSaleTradesIdcodeBystid(String stid) throws Exception{
		String sql = "from SaleTradesIdcode where  stid='"+stid+"'";
		return EntityManager.getAllByHql(sql);
	}
	
	public List getSaleTradesIdcodeBystid(String stid, int isidcode) throws Exception{
		String sql = "from SaleTradesIdcode where  stid='"+stid+"' and isidcode="+isidcode;
		return EntityManager.getAllByHql(sql);
	}
	
	public List getSaleTradesIdcodeByPidBatch(String productid, String stid) throws Exception{
		String sql = "from SaleTradesIdcode where productid='"+productid+"' and stid='"+stid+"'";
		return EntityManager.getAllByHql(sql);
	}
	
	public List getSaleTradesIdcodeByPidBatch(String productid, String stid, int isidcode) throws Exception{
		String sql = "from SaleTradesIdcode where productid='"+productid+"' and stid='"+stid+"' and isidcode="+isidcode;
		return EntityManager.getAllByHql(sql);
	}
	
	public SaleTradesIdcode getSaleTradesIdcodeByidcode(String productid, String stid, String idcode) throws Exception{
		//String sql = " from SaleTradesIdcode where productid='"+productid+"' and stid='"+stid+"' and idcode='"+idcode+"'";
		String sql = " from SaleTradesIdcode where productid='"+productid+"' and idcode='"+idcode+"'";
	    return (SaleTradesIdcode)EntityManager.find(sql);
	}
	
	
	public double getQuantitySumBystidProductid(String productid, String stid) throws Exception{
		String sql = "select sum(pii.quantity*f.xquantity) from SaleTradesIdcode as pii,FUnit as f "+
		"where pii.productid=f.productid and pii.unitid=f.funitid and pii.productid='"+productid+"' and pii.stid='"+stid+"'";
		return EntityManager.getdoubleSum(sql);
	}
}
