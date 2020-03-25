package com.winsafe.drp.dao;

import java.util.List;

import com.winsafe.drp.entity.EntityManager;

public class AppSaleRepairDetail {
	
	public void addSaleRepairDetail(Object SaleRepairDetail)throws Exception{
		
		EntityManager.save(SaleRepairDetail);
		
	}
	
	public SaleRepairDetail getSaleRepairDetailByID(Long id)throws Exception{
		String sql=" from SaleRepairDetail as wd where wd.id="+id+"";
		return (SaleRepairDetail)EntityManager.find(sql);
	}
	
	public List getSaleRepairDetailBySrid(String srid)throws Exception{
		List ls = null;
		String sql=" from SaleRepairDetail as wd where wd.srid='"+srid+"'";
		ls=EntityManager.getAllByHql(sql);
		return ls;
	}

	public void delSaleRepairDetailBySrid(String srid)throws Exception{
		
		String sql="delete from sale_repair_detail where srid='"+srid+"'";
		EntityManager.updateOrdelete(sql);
		
	}
}
