package com.winsafe.drp.dao;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.pager2.PageQuery;

public class AppOrganTradesTIdcode {

	public List searchOrganTradesTIdcode(HttpServletRequest request, int pagesize, String pWhereClause)
	throws Exception {
		String hql = " from OrganTradesTIdcode  " + pWhereClause + " order by id desc";
		return PageQuery.hbmQuery(request, hql, pagesize);
	}
	public void addOrganTradesTIdcode(OrganTradesTIdcode pii) throws Exception{		
		EntityManager.save(pii);		
	}
	
	public void updOrganTradesTIdcode(OrganTradesTIdcode pii) throws Exception{		
		EntityManager.update(pii);		
	}
	
	public void updOrganTradesTIdcodeBillNo(String billno,String truebillno) throws Exception{		
		String sql="update organ_trades_t_idcode set otid='"+truebillno+"' where otid='"+billno+"'";
		EntityManager.updateOrdelete(sql);			
	}
	
	public OrganTradesTIdcode getOrganTradesTIdcodeById(Long id) throws Exception{
		String sql = "from OrganTradesTIdcode where id="+id;
		return (OrganTradesTIdcode)EntityManager.find(sql);
		}
	
	public void delOrganTradesTIdcodeById(long id) throws Exception{		
		String sql="delete from organ_trades_t_idcode where id="+id;
		EntityManager.updateOrdelete(sql);		
	}
	
	public void delOrganTradesTIdcodeByPiid(String otid) throws Exception{		
		String sql="delete from organ_trades_t_idcode where otid='"+otid+"'";
		EntityManager.updateOrdelete(sql);		
	}
	
	public void delOrganTradesTIdcodeByPid(String productid, String otid, String batch) throws Exception{		
		String sql="delete from organ_trades_t_idcode where productid='"+productid+"' and otid='"+otid+"' and batch='"+batch+"'";
		EntityManager.updateOrdelete(sql);
		
	}
	
	public List getOrganTradesTIdcodeByotid(String otid) throws Exception{
		String sql = "from OrganTradesTIdcode where  otid='"+otid+"'";
		return EntityManager.getAllByHql(sql);
	}
	
	public List getOrganTradesTIdcodeByotid(String otid, int isidcode) throws Exception{
		String sql = "from OrganTradesTIdcode where  otid='"+otid+"' and isidcode="+isidcode;
		return EntityManager.getAllByHql(sql);
	}
	
	public List getOrganTradesTIdcodeByPidBatch(String productid, String otid) throws Exception{
		String sql = "from OrganTradesTIdcode where productid='"+productid+"' and otid='"+otid+"'";
		return EntityManager.getAllByHql(sql);
	}
	
	public List getOrganTradesTIdcodeByPidBatch(String productid, String otid, int isidcode, String batch) throws Exception{
		String sql = "from OrganTradesTIdcode where productid='"+productid+"' and otid='"+otid+"' and isidcode="+isidcode+" and batch='"+batch+"'";
		return EntityManager.getAllByHql(sql);
	}
	
	public OrganTradesTIdcode getOrganTradesTIdcodeByidcode(String productid, String otid, String idcode) throws Exception{
		//String sql = " from OrganTradesTIdcode where productid='"+productid+"' and otid='"+otid+"' and idcode='"+idcode+"'";
		String sql = " from OrganTradesTIdcode where productid='"+productid+"' and idcode='"+idcode+"'";

		return (OrganTradesTIdcode)EntityManager.find(sql);
	}
	
	
	public double getQuantitySumByotidProductid(String productid, String otid, String batch) throws Exception{
		String sql = "select sum(pii.quantity*f.xquantity) from OrganTradesTIdcode as pii,FUnit as f "+
		"where pii.productid=f.productid and pii.unitid=f.funitid and pii.productid='"+productid+"' and pii.otid='"+otid+"' and pii.batch='"+batch+"'";
		return EntityManager.getdoubleSum(sql);
	}
}
