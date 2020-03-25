package com.winsafe.drp.dao;

import java.util.List;

import com.winsafe.drp.entity.EntityManager;

public class AppBorrowIncomeDetail {
	
	public void addBorrowIncomeDetail(BorrowIncomeDetail lod)throws Exception{
		
		EntityManager.save(lod);
		
	}
	
	public BorrowIncomeDetail getBorrowIncomeDetailByID(Long id)throws Exception{
		String sql=" from BorrowIncomeDetail as wd where wd.id="+id+"";
		return (BorrowIncomeDetail)EntityManager.find(sql);
	}
	
	public List getBorrowIncomeDetailByBiid(String biid)throws Exception{
		List ls = null;
		String sql=" from BorrowIncomeDetail as wd where wd.biid='"+biid+"'";
		ls=EntityManager.getAllByHql(sql);
		return ls;
	}

	public void delBorrowIncomeDetailByBiid(String biid)throws Exception{
		
		String sql="delete from borrow_income_detail where biid='"+biid+"'";
		EntityManager.updateOrdelete(sql);
		
	}
	

}
