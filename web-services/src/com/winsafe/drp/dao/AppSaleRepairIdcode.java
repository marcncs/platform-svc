package com.winsafe.drp.dao;

import java.util.List;

import com.winsafe.drp.entity.EntityManager;

public class AppSaleRepairIdcode {

	public void addSaleRepairIdcode(SaleRepairIdcode pii) throws Exception{
		
		EntityManager.save(pii);
		
	}
	
	public void updSaleRepairIdcode(SaleRepairIdcode pii) throws Exception{
		
		EntityManager.update(pii);
		
	}
	
	public SaleRepairIdcode getSaleRepairIdcodeById(Long id) throws Exception{
		String sql = "from SaleRepairIdcode where id="+id;
		return (SaleRepairIdcode)EntityManager.find(sql);
		}
	
	public void delSaleRepairIdcodeByPidBatch(String productid, String batch) throws Exception{
		
		String sql="delete from sale_repair_idcode where productid='"+productid+"' and batch='"+batch+"'";
		EntityManager.updateOrdelete(sql);
		
	}
	
	public List getSaleRepairIdcodeByStid(String srid) throws Exception{
		String sql = "from SaleRepairIdcode where  srid='"+srid+"'";
		return EntityManager.getAllByHql(sql);
	}
	
	public List getSaleRepairIdcodeByPidBatch(String productid, String srid, String batch) throws Exception{
		String sql = "from SaleRepairIdcode where productid='"+productid+"' and srid='"+srid+"' and batch='"+batch+"'";
		return EntityManager.getAllByHql(sql);
	}
}
