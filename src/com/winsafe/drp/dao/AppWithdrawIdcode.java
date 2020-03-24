package com.winsafe.drp.dao;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.pager2.PageQuery;

public class AppWithdrawIdcode {

	public List searchWithdrawIdcode(HttpServletRequest request, int pagesize, String pWhereClause)
	throws Exception {
		String hql = " from WithdrawIdcode  " + pWhereClause + " order by id desc";
		return PageQuery.hbmQuery(request, hql, pagesize);
	}
	public void addWithdrawIdcode(WithdrawIdcode pii) throws Exception{		
		EntityManager.save(pii);		
	}
	
	public void updWithdrawIdcode(WithdrawIdcode pii) throws Exception{		
		EntityManager.update(pii);		
	}
	
	public void updWithdrawIdcodeBillNo(String billno,String truebillno) throws Exception{		
		String sql="update withdraw_idcode set wid='"+truebillno+"' where wid='"+billno+"'";
		EntityManager.updateOrdelete(sql);			
	}
	
	public WithdrawIdcode getWithdrawIdcodeById(Long id) throws Exception{
		String sql = "from WithdrawIdcode where id="+id;
		return (WithdrawIdcode)EntityManager.find(sql);
		}
	
	public void delWithdrawIdcodeById(long id) throws Exception{		
		String sql="delete from withdraw_idcode where id="+id;
		EntityManager.updateOrdelete(sql);		
	}
	
	public void delWithdrawIdcodeByWid(String wid) throws Exception{		
		String sql="delete from withdraw_idcode where wid='"+wid+"'";
		EntityManager.updateOrdelete(sql);		
	}
	
	public void delWithdrawIdcodeByPid(String productid, String wid) throws Exception{		
		String sql="delete from withdraw_idcode where productid='"+productid+"' and wid='"+wid+"'";
		EntityManager.updateOrdelete(sql);
		
	}
	
	public List getWithdrawIdcodeBywid(String wid) throws Exception{
		String sql = "from WithdrawIdcode where  wid='"+wid+"'";
		return EntityManager.getAllByHql(sql);
	}
	
	public List getWithdrawIdcodeBywid(String wid, int isidcode) throws Exception{
		String sql = "from WithdrawIdcode where  wid='"+wid+"' and isidcode="+isidcode;
		return EntityManager.getAllByHql(sql);
	}
	
	public List getWithdrawIdcodeByPidBatch(String productid, String wid) throws Exception{
		String sql = "from WithdrawIdcode where productid='"+productid+"' and wid='"+wid+"'";
		return EntityManager.getAllByHql(sql);
	}
	
	public List getWithdrawIdcodeByPidBatch(String productid, String wid, int isidcode) throws Exception{
		String sql = "from WithdrawIdcode where productid='"+productid+"' and wid='"+wid+"' and isidcode="+isidcode;
		return EntityManager.getAllByHql(sql);
	}
	
	public WithdrawIdcode getWithdrawIdcodeByidcode(String productid, String wid, String idcode) throws Exception{
		//String sql = " from WithdrawIdcode where productid='"+productid+"' and wid='"+wid+"' and idcode='"+idcode+"'";
		String sql = " from WithdrawIdcode where productid='"+productid+"' and idcode='"+idcode+"'";
		return (WithdrawIdcode)EntityManager.find(sql);
	}
	
	
	public double getQuantitySumBywidProductid(String productid, String wid) throws Exception{
		String sql = "select sum(pii.quantity*f.xquantity) from WithdrawIdcode as pii,FUnit as f "+
		"where pii.productid=f.productid and pii.unitid=f.funitid and pii.productid='"+productid+"' and pii.wid='"+wid+"'";
		return EntityManager.getdoubleSum(sql);
	}
}
