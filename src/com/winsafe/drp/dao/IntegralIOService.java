package com.winsafe.drp.dao;

import com.winsafe.drp.entity.EntityManager;

public class IntegralIOService {
	
	private String oid;
	private String orgid;
	private String startdate;
	private String enddate;

	public IntegralIOService(String oid,String orgid,String startdate, String enddate){
		this.oid = oid;
		this.orgid= orgid;
		this.startdate = startdate;
		this.enddate = enddate;
		
	}
	
	
	
	
	public double getRvIncome(String sqlw)throws Exception{
		String sql="select sum(ii.rincome) from IntegralI as ii where "+sqlw+" ii.oid='"+oid+"' ";
		return EntityManager.getdoubleSum(sql);
	}
	
	
	public double getAlIncome(String sqlw)throws Exception{
		String sql="select sum(ii.aincome) from IntegralI as ii where "+sqlw+" ii.oid='"+oid+"' ";
		return EntityManager.getdoubleSum(sql);
	}
	
	
	public double getRvOut(String sqlw)throws Exception{
		String sql="select sum(io.rout) from IntegralO as io where "+sqlw+" io.oid='"+oid+"' ";
		return EntityManager.getdoubleSum(sql);
	}
	
	
	public double getAlOut(String sqlw)throws Exception{
		String sql="select sum(io.aout) from IntegralO as io where "+sqlw+" io.oid='"+oid+"' ";
		return EntityManager.getdoubleSum(sql);
	}
	
	
	public double getBalance()throws Exception{
		String sqli="select sum(ii.aincome) from IntegralI as ii where ii.oid='"+oid+"' ";
		String sqlo="select sum(io.aout) from IntegralO as io where io.oid='"+oid+"'";
		double i = EntityManager.getdoubleSum(sqli);
		double o = EntityManager.getdoubleSum(sqlo);
		return i - o;
	}

}
