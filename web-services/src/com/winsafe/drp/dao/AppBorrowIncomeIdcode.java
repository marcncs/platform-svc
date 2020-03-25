package com.winsafe.drp.dao;

import java.util.List;

import com.winsafe.drp.entity.EntityManager;

public class AppBorrowIncomeIdcode {

	public void addBorrowIncomeIdcode(BorrowIncomeIdcode pii) throws Exception{
		
		EntityManager.save(pii);
		
	}
	
	public void updBorrowIncomeIdcode(BorrowIncomeIdcode pii) throws Exception{
		
		EntityManager.update(pii);
		
	}
	
	public BorrowIncomeIdcode getBorrowIncomeIdcodeById(Long id) throws Exception{
		String sql = "from BorrowIncomeIdcode where id="+id;
		return (BorrowIncomeIdcode)EntityManager.find(sql);
		}
	
	public void delBorrowIncomeIdcodeByPidBatch(String productid, String batch) throws Exception{
		
		String sql="delete from borrow_income_idcode where productid='"+productid+"' and batch='"+batch+"'";
		EntityManager.updateOrdelete(sql);
		
	}
	
	public List getBorrowIncomeIdcodeByBiid(String biid) throws Exception{
		String sql = "from BorrowIncomeIdcode where  biid='"+biid+"'";
		return EntityManager.getAllByHql(sql);
	}
	
	public List getBorrowIncomeIdcodeByPidBatch(String productid, String biid, String batch) throws Exception{
		String sql = "from BorrowIncomeIdcode where productid='"+productid+"' and biid='"+biid+"' and batch='"+batch+"'";
		return EntityManager.getAllByHql(sql);
	}
}
