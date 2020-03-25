package com.winsafe.drp.dao;

import java.sql.ResultSet;
import java.util.List;

import com.winsafe.drp.entity.EntityManager;

public class AppIntegralExchange {

	public void addIntegralExchange(Object pp) throws Exception{
		
		EntityManager.save(pp);
		
	}
	
	public void updIntegralExchange(IntegralExchange pp) throws Exception{
		
		EntityManager.saveOrUpdate(pp);
		
	}
	
	public List getAllIntegralExchange()throws Exception{
		String sql=" from IntegralExchange";
		List ls = EntityManager.getAllByHql(sql);
		return ls;
	}
	
	public void delIntegralExchangeByProductID(String pid) throws Exception{
		
		String sql="delete from Integral_Exchange where productid='"+pid+"'";
		EntityManager.updateOrdelete(sql);
		
	}
	
	public IntegralExchange getIntegralExchangeById(Long id) throws Exception{
		String sql="from IntegralExchange where id="+id;
		IntegralExchange bank=(IntegralExchange)EntityManager.find(sql);
		return bank;
	}
	
	public List getIntegralExchangeByProductID(String pid) throws Exception{
		List list = null;
		String sql = " from IntegralExchange where productid='"+pid+"'";
		list = EntityManager.getAllByHql(sql);
		return list;
	}
	
	
	public IntegralExchange getIntegralExchangeByPIDUnitID(String pid,int unitid)throws Exception{
		String sql=" from IntegralExchange as ie where ie.productid='"+pid+"' and ie.unitid="+unitid+" ";
		IntegralExchange pp= (IntegralExchange)EntityManager.find(sql);
		return pp;
	}
	
	public double getUnitintegralByPidUnitId(String pid,int unitid)throws Exception{
		double p =0.00;
		String sql="select op.unitintegral from Integral_Exchange as op where op.productid='"+pid+"' and op.unitid="+unitid;
		ResultSet rs = EntityManager.query(sql);
		if(rs.getRow()>0){
			do {
				p = rs.getDouble(1);
			} while (rs.next());
			rs.close();
		}
		return p;
	}

}
