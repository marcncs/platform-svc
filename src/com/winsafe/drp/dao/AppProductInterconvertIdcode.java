package com.winsafe.drp.dao;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.pager2.PageQuery;

public class AppProductInterconvertIdcode {

	public List searchProductInterconvertIdcode(HttpServletRequest request, int pagesize, String pWhereClause)
	throws Exception {
		String hql = " from ProductInterconvertIdcode  " + pWhereClause + " order by id desc";
		return PageQuery.hbmQuery(request, hql, pagesize);
	}
	public void addProductInterconvertIdcode(ProductInterconvertIdcode pii) throws Exception{		
		EntityManager.save(pii);		
	}
	
	public void updSupplySaleMoveIdcodeBillNo(String billno,String truebillno) throws Exception{		
		String sql="update product_interconvert_idcode set piid='"+truebillno+"' where piid='"+billno+"'";
		EntityManager.updateOrdelete(sql);			
	}
	
	public void updProductInterconvertIdcode(ProductInterconvertIdcode pii) throws Exception{		
		EntityManager.update(pii);		
	}
	
	public ProductInterconvertIdcode getProductInterconvertIdcodeById(Long id) throws Exception{
		String sql = "from ProductInterconvertIdcode where id="+id;
		return (ProductInterconvertIdcode)EntityManager.find(sql);
		}
	
	public void delProductInterconvertIdcodeById(long id) throws Exception{		
		String sql="delete from product_interconvert_idcode where id="+id;
		EntityManager.updateOrdelete(sql);		
	}
	
	public void delProductInterconvertIdcodeByPiid(String piid) throws Exception{		
		String sql="delete from product_interconvert_idcode where piid='"+piid+"'";
		EntityManager.updateOrdelete(sql);		
	}
	
	public void delProductInterconvertIdcodeByPid(String productid, String piid, String batch) throws Exception{		
		String sql="delete from product_interconvert_idcode where productid='"+productid+"' and piid='"+piid+"' and batch='"+batch+"'";
		EntityManager.updateOrdelete(sql);
		
	}
	
	public List getProductInterconvertIdcodeByPiid(String piid) throws Exception{
		String sql = "from ProductInterconvertIdcode where  piid='"+piid+"'";
		return EntityManager.getAllByHql(sql);
	}
	
	public List getProductInterconvertIdcodeByPiid(String piid, int isidcode) throws Exception{
		String sql = "from ProductInterconvertIdcode where  piid='"+piid+"' and isidcode="+isidcode;
		return EntityManager.getAllByHql(sql);
	}
	
	public List getProductInterconvertIdcodeByPidBatch(String productid, String piid) throws Exception{
		String sql = "from ProductInterconvertIdcode where productid='"+productid+"' and piid='"+piid+"'";
		return EntityManager.getAllByHql(sql);
	}
	
	public List getProductInterconvertIdcodeByPidBatch(String productid, String piid, int isidcode) throws Exception{
		String sql = "from ProductInterconvertIdcode where productid='"+productid+"' and piid='"+piid+"' and isidcode="+isidcode;
		return EntityManager.getAllByHql(sql);
	}
	
	public List getProductInterconvertIdcodeByPidBatch(String productid, String piid, int isidcode, String batch) throws Exception{
		String sql = "from ProductInterconvertIdcode where productid='"+productid+"' and piid='"+piid+"' and isidcode="+isidcode+" and batch='"+batch+"'";
		return EntityManager.getAllByHql(sql);
	}
	
	public ProductInterconvertIdcode getProductInterconvertIdcodeByidcode(String productid, String piid, String idcode) throws Exception{
		//String sql = " from ProductInterconvertIdcode where productid='"+productid+"' and piid='"+piid+"' and idcode='"+idcode+"'";
		String sql = " from ProductInterconvertIdcode where productid='"+productid+"' and idcode='"+idcode+"'";
		return (ProductInterconvertIdcode)EntityManager.find(sql);
	}
	
	
	public double getQuantitySumByPiidProductid(String productid, String piid, String batch) throws Exception{
		String sql = "select sum(pii.quantity*f.xquantity) from ProductInterconvertIdcode as pii,FUnit as f "+
		"where pii.productid=f.productid and pii.unitid=f.funitid and pii.productid='"+productid+"' and pii.piid='"+piid+"' and pii.batch='"+batch+"'";
		return EntityManager.getdoubleSum(sql);
	}
}
