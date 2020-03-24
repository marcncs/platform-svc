package com.winsafe.drp.dao;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.pager2.PageQuery;

public class AppOrganTradesPIdcode {

	public List searchOrganTradesPIdcode(HttpServletRequest request, int pagesize, String pWhereClause)
	throws Exception {
		String hql = " from OrganTradesPIdcode  " + pWhereClause + " order by id desc";
		return PageQuery.hbmQuery(request, hql, pagesize);
	}
	public void addOrganTradesPIdcode(OrganTradesPIdcode pii) throws Exception{		
		EntityManager.save(pii);		
	}
	
	public void updOrganTradesPIdcode(OrganTradesPIdcode pii) throws Exception{		
		EntityManager.update(pii);		
	}
	
	public void updOrganTradesPIdcodeBillNo(String billno,String truebillno) throws Exception{		
		String sql="update organ_trades_p_idcode set otid='"+truebillno+"' where otid='"+billno+"'";
		EntityManager.updateOrdelete(sql);			
	}
	
	public OrganTradesPIdcode getOrganTradesPIdcodeById(Long id) throws Exception{
		String sql = "from OrganTradesPIdcode where id="+id;
		return (OrganTradesPIdcode)EntityManager.find(sql);
		}
	
	public void delOrganTradesPIdcodeById(long id) throws Exception{		
		String sql="delete from organ_trades_p_idcode where id="+id;
		EntityManager.updateOrdelete(sql);		
	}
	
	public void delOrganTradesPIdcodeByPiid(String otid) throws Exception{		
		String sql="delete from organ_trades_p_idcode where otid='"+otid+"'";
		EntityManager.updateOrdelete(sql);		
	}
	
	public void delOrganTradesPIdcodeByPid(String productid, String otid, String batch) throws Exception{		
		String sql="delete from organ_trades_p_idcode where productid='"+productid+"' and otid='"+otid+"' and batch='"+batch+"'";
		EntityManager.updateOrdelete(sql);
		
	}
	
	public List getOrganTradesPIdcodeByotid(String otid) throws Exception{
		String sql = "from OrganTradesPIdcode where  otid='"+otid+"'";
		return EntityManager.getAllByHql(sql);
	}
	
	public List getOrganTradesPIdcodeByotid(String otid, int isidcode) throws Exception{
		String sql = "from OrganTradesPIdcode where  otid='"+otid+"' and isidcode="+isidcode;
		return EntityManager.getAllByHql(sql);
	}
	
	public List getOrganTradesPIdcodeByPidBatch(String productid, String otid) throws Exception{
		String sql = "from OrganTradesPIdcode where productid='"+productid+"' and otid='"+otid+"'";
		return EntityManager.getAllByHql(sql);
	}
	
	public List getOrganTradesPIdcodeByPidBatch(String productid, String otid, int isidcode, String batch) throws Exception{
		String sql = "from OrganTradesPIdcode where productid='"+productid+"' and otid='"+otid+"' and isidcode="+isidcode+" and batch='"+batch+"'";
		return EntityManager.getAllByHql(sql);
	}
	
	public OrganTradesPIdcode getOrganTradesPIdcodeByidcode(String productid, String otid, String idcode) throws Exception{
		//String sql = " from OrganTradesPIdcode where productid='"+productid+"' and otid='"+otid+"' and idcode='"+idcode+"'";
		String sql = " from OrganTradesPIdcode where productid='"+productid+"' and idcode='"+idcode+"'";

		return (OrganTradesPIdcode)EntityManager.find(sql);
	}
	
	
	public double getQuantitySumByotidProductid(String productid, String otid, String batch) throws Exception{
		String sql = "select sum(pii.quantity*f.xquantity) from OrganTradesPIdcode as pii,FUnit as f "+
		"where pii.productid=f.productid and pii.unitid=f.funitid and pii.productid='"+productid+"' and pii.otid='"+otid+"' and pii.batch='"+batch+"'";
		return EntityManager.getdoubleSum(sql);
	}
}
