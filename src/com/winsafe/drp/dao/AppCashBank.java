package com.winsafe.drp.dao;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.pager2.PageQuery;

public class AppCashBank {

	public List getCashBank(HttpServletRequest request, int pagesize,
			String pWhereClause)throws Exception{
		 String sql=" from CashBank as cb "
         +pWhereClause+" order by cb.id desc";
		 return PageQuery.hbmQuery(request, sql, pagesize);
	}
	
	public void addCashBank(CashBank bank) throws Exception{		
		EntityManager.save(bank);		
	}
	
	public void updCashBank(CashBank bank) throws Exception{		
		EntityManager.update(bank);		
	}
	
	public void updCashBankByID(CashBank cb) throws Exception{
		EntityManager.update(cb);
		
	}
	
	public void updTotalsumForAdd(Long id, double sum) throws Exception{		
		String sql = " update cash_bank set totalsum=totalsum + "+sum+" where id="+id;
		EntityManager.updateOrdelete(sql);		
	}
	

	public void updTotalsumForRemove(Long id, double sum) throws Exception{		
		String sql = " update cash_bank set totalsum=totalsum - "+sum+" where id="+id;
		EntityManager.updateOrdelete(sql);		
	}
	
	
	
	
	public void AdjustTotalSum(Integer cbid,double adjustsum)throws Exception{		
		String sql="update cash_bank set totalsum=totalsum+"+adjustsum+" where id ="+cbid;
		EntityManager.updateOrdelete(sql);		
	}
	
	 
	public void CancelAdjustTotalSum(Integer cbid,double adjustsum)throws Exception{		
		String sql="update cash_bank set totalsum=totalsum-"+adjustsum+" where id ="+cbid;
		EntityManager.updateOrdelete(sql);
		
	}
	
	public void delCashBank(Long id) throws Exception{
		
		String sql="delete from cash_bank where id="+id;
		EntityManager.updateOrdelete(sql);
		
	}
	
	public CashBank getCashBankById(int id) throws Exception{
		String sql="from CashBank where id="+id;
		return (CashBank)EntityManager.find(sql);
	}
	
	public String getCBName(int id) throws Exception{
		CashBank cb = getCashBankById(id);
		if ( cb != null ){
			return cb.getCbname();
		}
		return "";
	}
	
	public List getAllCashBank() throws Exception{
		String sql = "from CashBank order by id";
		return EntityManager.getAllByHql(sql);
	}
	
	public List getAllCashBankByOID(String oid) throws Exception{
		String sql = "from CashBank where makeorganid='"+oid+"' order by id";
		return EntityManager.getAllByHql(sql);
	}
	
	
}
