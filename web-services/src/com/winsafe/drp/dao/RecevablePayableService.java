package com.winsafe.drp.dao;

import com.winsafe.drp.entity.EntityManager;

public class RecevablePayableService {
	
	private String roid;
	private String orgid;
	private String startdate;
	private String enddate;
	

	public static final int ALL = -1;

	public static final int CASH = 0;

	public static final int MONTH = 1;	

	public static final int BUDGET = 2;
	
	
	public RecevablePayableService(String roid,String orgid,String startdate, String enddate){
		this.roid = roid;
		this.orgid= orgid;
		this.startdate = startdate;
		this.enddate = enddate;
	}
	
	
	public double getPrevioussumByRCV() throws Exception{
		
		String rsql= "select sum(r.receivablesum) from Receivable as r where r.makedate <'"+startdate+"' and r.roid='"+roid+"' and r.makeorganid='"+orgid+"' ";
		double rsum = EntityManager.getdoubleSum(rsql);
		
		String isql= "select sum(il.incomesum) from IncomeLog as il where il.makedate <'"+startdate+"' and il.roid='"+roid+"' and il.makeorganid='"+orgid+"' ";
		double isum = EntityManager.getdoubleSum(isql);
		return rsum - isum;
	}

	

	
/*

	public double getPreviousSum(int paymentmode) throws Exception{		
		StringBuffer sb = new StringBuffer();
		sb.append("select sum(rd.receivablesum) from Receivable as rd where rd.isclose=0 and rd.roid='")
		.append(roid).append("' and rd.makedate<'").append(startdate).append("' ");
		if ( -1 != paymentmode ){
			sb.append(" and rd.paymentmode=").append(paymentmode).append(" ");
		}	
		
		return EntityManager.getdoubleSum(sb.toString()) + getPreviousSumAfter(-1);		
	}
*/
	
	

	public double getPreviousSumAfter(int paymentmode) throws Exception{		
		StringBuffer sb = new StringBuffer();
		sb.append("select sum(rd.receivablesum) from Receivable as rd where rd.isclose=1 and rd.roid='")
		.append(roid).append("' and rd.makeorganid='"+orgid+"' and rd.makedate<'").append(startdate).append("' and rd.closedate>'").append(startdate).append("' ");
		if ( -1 != paymentmode ){
			sb.append(" and rd.paymentmode=").append(paymentmode).append(" ");
		}	
		
		return EntityManager.getdoubleSum(sb.toString());		
	}
	
	
	public double getCurrentSum(int paymentmode) throws Exception{
		StringBuffer sb = new StringBuffer();
		sb.append("select sum(rd.receivablesum) from Receivable as rd where rd.roid='").append(roid)
		.append("' and rd.makeorganid='"+orgid+"' and rd.makedate>='").append(startdate).append("' ")
		.append(" and rd.makedate<='").append(enddate).append(" 23:59:59' ");
		if ( -1 != paymentmode ){
			sb.append(" and rd.paymentmode=").append(paymentmode).append(" ");
		}	
		return EntityManager.getdoubleSum(sb.toString());		
	}
	
	
	public double getCurrentAlreadySum(int paymentmode) throws Exception{
		double sum=0.00;
		switch (paymentmode) {
			case -1:
				//sum = getGoodsFundSum(-1) + getPrepareReceiveSum();
				sum = getPrepareReceiveSum();
				break;
			case 0:
				sum = getGoodsFundSum(0);
				break;
			case 1:
				sum = getGoodsFundSum(1);
				break;
			case 2:
				sum = getPrepareReceiveSum();
				break;
			case 3:
				sum = getGoodsFundSum(3);
				break;
		}
		return sum;
	}
	
	
	
	
	private double getGoodsFundSum(int paymentmode) throws Exception{
		StringBuffer sb = new StringBuffer();
		sb.append("select sum(ild.goodsfund*ild.quantity) from IncomeLogDetail as ild where ild.destorymode=0 and ild.roid='").append(roid)
		.append("' and ild.makedate>='").append(startdate).append("' ")
		.append(" and ild.makedate<='").append(enddate).append(" 23:59:59' ");
		if ( -1 != paymentmode ){
			sb.append(" and ild.paymentmode=").append(paymentmode).append(" ");
		}	
		return EntityManager.getdoubleSum(sb.toString());		
	}
	
	
	private double getPrepareReceiveSum() throws Exception{
		StringBuffer sb = new StringBuffer();
		sb.append("select sum(il.incomesum) from IncomeLog as il where  il.roid='").append(roid)
		.append("' and il.makeorganid='"+orgid+"' and il.makedate>='").append(startdate).append("' ")
		.append(" and il.makedate<='").append(enddate).append(" 23:59:59' ");

		return EntityManager.getdoubleSum(sb.toString());//+getIncomeLogNoSpend()+getIncomeLogSpend();		
	}

/*
	

	private double getIncomeLogNoSpend() throws Exception{
		StringBuffer sb = new StringBuffer();
		sb.append("select sum(il.incomesum-il.alreadyspend) from IncomeLog as il where il.paymentmode=1 and il.roid='").append(roid)
		.append("' and il.makedate<'").append(startdate).append("' ");
System.out.println("------sql1111="+sb.toString());
		return EntityManager.getdoubleSum(sb.toString());		
	}
	
	
	private double getIncomeLogSpend() throws Exception{
		StringBuffer sb = new StringBuffer();
		sb.append("select sum(il.alreadyspend) from IncomeLog as il where il.paymentmode=1 and il.roid='").append(roid)
		.append("' and il.spenddate>='").append(startdate).append("' ")
		.append(" and il.spenddate<='").append(enddate).append("' ")
		.append(" and il.makedate<'").append(startdate).append("' ");
		
		System.out.println("------sql1222="+sb.toString());
		return EntityManager.getdoubleSum(sb.toString());		
	}
	
*/
	
	
	

	
	public double getPrevioussumByP()throws Exception{
		
		String psql="select sum(p.payablesum) from Payable as p where p.makedate <'"+startdate+"' and p.poid='"+roid+"' and p.makeorganid='"+orgid+"' ";
		double psum = EntityManager.getdoubleSum(psql);
		
		String lsql="select sum(pl.paysum) from PaymentLog as pl where pl.makedate <'"+startdate+"' and pl.poid='"+roid+"' and pl.makeorganid='"+orgid+"' ";
		double lsum= EntityManager.getdoubleSum(lsql);
		return psum - lsum;
	}
	
	
	
	
	
	public double getPayablePreviousSum() throws Exception{		
		StringBuffer sb = new StringBuffer();
		sb.append("select sum(rd.payablesum) from Payable as rd where rd.isclose=0 and rd.poid=' and rd.makeorganid='"+orgid+"' ")
		.append(roid).append("' and rd.makedate<'").append(startdate).append("' ");			
		return EntityManager.getdoubleSum(sb.toString())+getPayablePreviousSumAfter();		
	}
	
	
	public double getPayablePreviousSumAfter() throws Exception{		
		StringBuffer sb = new StringBuffer();
		sb.append("select sum(rd.payablesum) from Payable as rd where rd.isclose=1 and rd.poid='")
		.append(roid).append("' and rd.makeorganid='"+orgid+"' and rd.makedate<'").append(startdate).append("'and rd.closedate>'").append(startdate).append("' ");	
		return EntityManager.getdoubleSum(sb.toString());		
	}
	
	
	public double getPayableCurrentSum() throws Exception{
		StringBuffer sb = new StringBuffer();
		sb.append("select sum(rd.payablesum) from Payable as rd where rd.poid='").append(roid)
		.append("' and rd.makeorganid='"+orgid+"' and rd.makedate>='").append(startdate).append("' ")
		.append(" and rd.makedate<='").append(enddate).append(" 23:59:59' ");		
		return EntityManager.getdoubleSum(sb.toString());		
	}
	
	
	public double getPayableCurrentAlreadySum() throws Exception{
		return getPayableGoodsFundSum();// + getPayablePrepareReceiveSum();	
	}
	
	
	
	private double getPayableGoodsFundSum() throws Exception{
		StringBuffer sb = new StringBuffer();
		sb.append("select sum(pl.paysum) from PaymentLog as pl where pl.poid='").append(roid)
		.append("' and pl.makeorganid='"+orgid+"' and pl.makedate>='").append(startdate).append("' ")
		.append(" and pl.makedate<='").append(enddate).append(" 23:59:59' ");
		
		return EntityManager.getdoubleSum(sb.toString());//+getPaymentLogNoSpend()+getPaymentLogSpend();			
	}

/*
	
	private double getPaymentLogNoSpend() throws Exception{
		StringBuffer sb = new StringBuffer();
		sb.append("select sum(ild.paysum-ild.alreadyspend) from PaymentLog as ild where ild.paymode=1 and ild.poid='").append(roid)
		.append("' and ild.makedate<'").append(startdate).append("' ");
		
		return EntityManager.getdoubleSum(sb.toString());		
	}
	
	
	private double getPaymentLogSpend() throws Exception{
		StringBuffer sb = new StringBuffer();
		sb.append("select sum(ild.alreadyspend) from PaymentLog as ild where ild.paymode=1 and ild.poid='").append(roid)
		.append("' and ild.spenddate>='").append(startdate).append("' ")
		.append(" and ild.spenddate<='").append(enddate).append("' ")
		.append(" and ild.makedate<'").append(startdate).append("' ");
		return EntityManager.getdoubleSum(sb.toString());		
	}
	
	*/
	
	
	
	private double getPayablePrepareReceiveSum() throws Exception{
		StringBuffer sb = new StringBuffer();
		sb.append("select sum(pl.paysum) from PaymentLog as pl where  pl.rrpoid='").append(roid)
		.append("' and pl.makeorganid='"+orgid+"' and pl.makedate>='").append(startdate).append("' ")
		.append(" and pl.makedate<='").append(enddate).append(" 23:59:59' ");
		
		return EntityManager.getdoubleSum(sb.toString());		
	}
	
}
