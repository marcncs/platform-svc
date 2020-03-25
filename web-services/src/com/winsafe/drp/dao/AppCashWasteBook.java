package com.winsafe.drp.dao;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.pager2.PageQuery;

public class AppCashWasteBook {
	public void addCashWasteBook(CashWasteBook ag) throws Exception {
		
		EntityManager.save(ag);
		
	}
	
	public void delCashWasteBook(Long id)throws Exception{
		
		String sql="delete from cash_waste_book where id="+id+"";
		EntityManager.updateOrdelete(sql);
		
	}
	
	public void delCashWasteBookByBillno(String billno)throws Exception{
		
		String sql="delete from cash_waste_book where billno='"+billno+"'";
		EntityManager.updateOrdelete(sql);
		
	}
	
	@SuppressWarnings("unchecked")
	public List<CashWasteBook> searchCashWasteBook(HttpServletRequest request, int pagesize,
			String pWhereClause) throws Exception {
		String sql = "from CashWasteBook as cwb "
				+ pWhereClause + " order by cwb.recorddate desc ";
		return PageQuery.hbmQuery(request, sql, pagesize);
	}
	@SuppressWarnings("unchecked")
	public List<CashWasteBook> searchCashWasteBook(String pWhereClause) throws Exception {
		String sql = "from CashWasteBook as cwb "
				+ pWhereClause + " order by cwb.recorddate desc ";
		return EntityManager.getAllByHql(sql);
	}
}
