package com.winsafe.drp.dao;

import java.util.List;

import com.winsafe.drp.entity.EntityManager;

public class AppInvoiceConf {

	public void addInvoiceConf(InvoiceConf bank) throws Exception{
		
		EntityManager.save(bank);
		
	}
	
	public void updInvoiceConf(InvoiceConf bank) throws Exception{		
		EntityManager.saveOrUpdate(bank);		
	}

	public List getAllInvoiceConf() throws Exception{
		String sql=" from InvoiceConf";
		List bank=EntityManager.getAllByHql(sql);
		return bank;
	}
	
	
	public InvoiceConf getInvoiceConfById(int id) throws Exception{
		String sql="from InvoiceConf where id="+id;
		return (InvoiceConf)EntityManager.find(sql);
	}
	
	public String getIvnameById(int id) throws Exception{
		InvoiceConf ic = getInvoiceConfById(id);
		if ( ic != null ){
			return ic.getIvname();
		}
		return "";
	}

}
