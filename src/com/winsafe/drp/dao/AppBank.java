package com.winsafe.drp.dao;

import java.util.List;

import com.winsafe.drp.entity.EntityManager;

public class AppBank {

	public void addBank(Bank bank) throws Exception{
		
		EntityManager.save(bank);
		
	}
	
	public void updBank(Bank bank) throws Exception{
		
		EntityManager.saveOrUpdate(bank);
		
	}
	
	public void updBankBySql(Bank bank) throws Exception{
		
		String sql = " update Bank set BankName='"+bank.getBankname()+"',DoorName='"+
		bank.getDoorname()+"',BankAccount='"+bank.getBankaccount()+"' where ID="+bank.getId();
		EntityManager.updateOrdelete(sql);
		
	}
	
	public void delBank(Long id) throws Exception{
		
		String sql="delete from Bank where id="+id;
		EntityManager.updateOrdelete(sql);
		
	}
	
	public Bank getBankById(Long id) throws Exception{
		Bank bank=null;
		String sql="from Bank where id="+id;
		bank=(Bank)EntityManager.find(sql);
		return bank;
	}
	
	public List getBankByCid(String cid) throws Exception{
		List list = null;
		String sql = "from Bank where cid='"+cid+"'";
		list = EntityManager.getAllByHql(sql);
		if ( list.size() == 0 ){
			return null;
		}
		return list;
	}
}
