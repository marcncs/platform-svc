package com.winsafe.drp.dao;

import java.util.List;

import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.pager.SimplePageInfo;

public class AppFeeWasteBook {
	
	public void addFeeWasteBook(FeeWasteBook f)throws Exception{
		
		EntityManager.save(f);
		
	}
	
	public FeeWasteBook getFeeWasteBookByID(Long id)throws Exception{
		String sql=" from FeeWasteBook as wd where wd.id="+id+"";
		return (FeeWasteBook)EntityManager.find(sql);
	}
	
	public List getFeeWasteBookBySrid(String billno)throws Exception{
		List ls = null;
		String sql=" from FeeWasteBook as wd where wd.billno='"+billno+"'";
		ls=EntityManager.getAllByHql(sql);
		return ls;
	}

	public void delFeeWasteBookByBillno(String billno)throws Exception{
		
		String sql="delete from fee_waste_book where billno='"+billno+"'";
		EntityManager.updateOrdelete(sql);
		
	}
	
	public List searchFeeWasteBook(int pagesize, String pWhereClause,
			SimplePageInfo pPgInfo) throws Exception {
		List pl = null;
		int targetPage = pPgInfo.getCurrentPageNo();
		String sql = "from FeeWasteBook as fwb "
				+ pWhereClause + " order by fwb.recorddate desc ";
		pl = EntityManager.getAllByHql(sql, targetPage, pagesize);
		return pl;
	}
}
