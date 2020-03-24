package com.winsafe.drp.dao;

import java.util.List;

import com.winsafe.drp.entity.EntityManager;

public class AppLoanOutIdcode {

	public void addLoanOutIdcode(LoanOutIdcode pii) throws Exception{
		
		EntityManager.save(pii);
		
	}
	
	public void updLoanOutIdcode(LoanOutIdcode pii) throws Exception{
		
		EntityManager.update(pii);
		
	}
	
	public LoanOutIdcode getLoanOutIdcodeById(Long id) throws Exception{
		String sql = "from LoanOutIdcode where id="+id;
		return (LoanOutIdcode)EntityManager.find(sql);
		}
	
	public void delLoanOutIdcodeByPidBatch(String productid, String batch) throws Exception{
		
		String sql="delete from loan_out_idcode where productid='"+productid+"' and batch='"+batch+"'";
		EntityManager.updateOrdelete(sql);
		
	}
	
	public List getLoanOutIdcodeByLoid(String loid) throws Exception{
		String sql = "from LoanOutIdcode where  loid='"+loid+"'";
		return EntityManager.getAllByHql(sql);
	}
	
	public List getLoanOutIdcodeByPidBatch(String productid, String loid, String batch) throws Exception{
		String sql = "from LoanOutIdcode where productid='"+productid+"' and loid='"+loid+"' and batch='"+batch+"'";
		return EntityManager.getAllByHql(sql);
	}
}
