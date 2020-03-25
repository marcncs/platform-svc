package com.winsafe.drp.server;

import com.winsafe.drp.entity.EntityManager;

public class ProductCostService
{

	public static double getViewProductCost(String warehouseid, String productid, String batch) throws Exception
	{
		String hql = " select sum(mixcost*mixquantity)/sum(mixquantity) from ViewProductCost  where warehouseid='" + warehouseid + "' and productid='"
				+ productid + "' and batch='" + batch + "'";
		//System.out.println("-----sql==="+hql);
		return EntityManager.getdoubleSum(hql);
	}

	private static void inProductStockpileAll(String warehouseid, String productid, String batch, double cost) throws Exception
	{
		String sql = "update Product_Stockpile_All set cost=" + cost + " where productid='" + productid + "' and batch='" + batch + "' and warehouseid='"
				+ warehouseid + "' ";
		EntityManager.updateOrdelete(sql);
	}

	public static void updateCost(String warehouseid, String productid, String batch) throws Exception
	{
		double cost = getViewProductCost(warehouseid, productid, batch);
		//System.out.println("-----cost==="+cost);
		inProductStockpileAll(warehouseid, productid, batch, cost);
	}

	public static double getCost(String warehouseid, String productid, String batch) throws Exception
	{
		String hql = " select cost from ProductStockpileAll  where warehouseid='" + warehouseid + "' and productid='" + productid + "' and batch='" + batch
				+ "'";
		return EntityManager.getdoubleSum(hql);
	}

}
