package com.winsafe.drp.dao;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.pager2.PageQuery;

public class AppOtherIncomeIdcode {

	public List searchOtherIncomeIdcode(HttpServletRequest request, int pagesize, String pWhereClause)
	throws Exception {
		String hql = " from OtherIncomeIdcode  " + pWhereClause + " order by id desc";
		return PageQuery.hbmQuery(request, hql, pagesize);
	}
	public void addOtherIncomeIdcode(OtherIncomeIdcode pii) throws Exception{		
		EntityManager.save(pii);		
	}
	
	public void updOtherIncomeIdcode(OtherIncomeIdcode pii) throws Exception{		
		EntityManager.update(pii);		
	}
	
	public void updOtherIncomeIdcodeBillNo(String billno,String truebillno) throws Exception{		
		String sql="update other_income_idcode set oiid='"+truebillno+"' where oiid='"+billno+"'";
		EntityManager.updateOrdelete(sql);			
	}
	
	public OtherIncomeIdcode getOtherIncomeIdcodeById(Long id) throws Exception{
		String sql = "from OtherIncomeIdcode where id="+id;
		return (OtherIncomeIdcode)EntityManager.find(sql);
		}
	
	public void delOtherIncomeIdcodeById(long id) throws Exception{		
		String sql="delete from other_income_idcode where id="+id;
		EntityManager.updateOrdelete(sql);		
	}
	
	public void delOtherIncomeIdcodeByPiid(String oiid) throws Exception{		
		String sql="delete from other_income_idcode where oiid='"+oiid+"'";
		EntityManager.updateOrdelete(sql);		
	}
	
	public void delOtherIncomeIdcodeByPid(String productid, String oiid, String batch) throws Exception{		
		String sql="delete from other_income_idcode where productid='"+productid+"' and oiid='"+oiid+"' and batch='"+batch+"'";
		EntityManager.updateOrdelete(sql);
		
	}
	
	public List getOtherIncomeIdcodeByoiid(String oiid) throws Exception{
		String sql = "from OtherIncomeIdcode where  oiid='"+oiid+"'";
		return EntityManager.getAllByHql(sql);
	}
	
	public List getOtherIncomeIdcodeByoiid(String oiid, int isidcode) throws Exception{
		String sql = "from OtherIncomeIdcode where  oiid='"+oiid+"' and isidcode="+isidcode;
		return EntityManager.getAllByHql(sql);
	}
	
	public List getOtherIncomeIdcodeByPidBatch(String productid, String oiid) throws Exception{
		String sql = "from OtherIncomeIdcode where productid='"+productid+"' and oiid='"+oiid+"'";
		return EntityManager.getAllByHql(sql);
	}
	
	public List getOtherIncomeIdcodeByPidBatch(String productid, String oiid, int isidcode, String batch) throws Exception{
		String sql = "from OtherIncomeIdcode where productid='"+productid+"' and oiid='"+oiid+"' and isidcode="+isidcode+" and batch='"+batch+"'";
		return EntityManager.getAllByHql(sql);
	}
	
	public OtherIncomeIdcode getOtherIncomeIdcodeByidcode(String productid, String oiid, String idcode) throws Exception{
		//String sql = " from OtherIncomeIdcode where productid='"+productid+"' and oiid='"+oiid+"' and idcode='"+idcode+"'";
		String sql = " from OtherIncomeIdcode where productid='"+productid+"' and idcode='"+idcode+"'";
		return (OtherIncomeIdcode)EntityManager.find(sql);
	}
	
	
	public double getQuantitySumByoiidProductid(String productid, String oiid, String batch) throws Exception{
		String sql = "select sum(pii.quantity*f.xquantity) from OtherIncomeIdcode as pii,FUnit as f "+
		"where pii.productid=f.productid and pii.unitid=f.funitid and pii.productid='"+productid+"' and pii.oiid='"+oiid+"' and pii.batch='"+batch+"'";
		return EntityManager.getdoubleSum(sql);
	}
	
	public void delOtherIncomeIDcodeByNCcode(String nccode)throws Exception{    
	    String sql="delete from other_income_idcode where Nccode='"+nccode+"'";
	    EntityManager.updateOrdelete(sql);    
	   }
}
