package com.winsafe.drp.dao;

import java.util.List;

import com.winsafe.drp.entity.EntityManager;

public class AppSettlementDetail {

	
	public List getSettlementDetailBySID(Long sid) throws Exception {
		List pl = null;
		String sql = "select sd.id,sd.sid,sd.shipmentid,sd.invoiceid,sd.productid,sd.settlementquantity,sd.settlementprice,sd.settlementsum from SettlementDetail as sd where sd.sid="+sid+" order by sd.id ";
		pl = EntityManager.getAllByHql(sql);
		return pl;
	}
	
	public void addSettlementDetail(Object settlement)throws Exception{
		
		EntityManager.save(settlement);
		
	}
	
	
	public Double getSumSettlement(Long sid)throws Exception{
		Double s=Double.valueOf(0.00);
		String sql="select sum(sd.settlementsum) from SettlementDetail as sd where sd.sid="+sid;
		s = EntityManager.getdoubleSum(sql);
		return s;
	}
	

}
