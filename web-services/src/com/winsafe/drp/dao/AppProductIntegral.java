package com.winsafe.drp.dao;

import java.util.List;

import com.winsafe.drp.entity.EntityManager;

public class AppProductIntegral {

	
	public ProductIntegral getProductIntegralByID(String id) throws Exception {

		String sql = " from ProductIntegral as p where p.productid='" + id
				+ "'";
		ProductIntegral p = (ProductIntegral) EntityManager.find(sql);
		return p;
	}
	
	public List getProductIntegralByPID(String pid) throws Exception {

		String sql = " from ProductIntegral as p where p.productid='" + pid
				+ "'";
		List p =  EntityManager.getAllByHql(sql);
		return p;
	}

	public ProductIntegral getProductIntegralByPIDUIDSID(String pid,
			Integer unitid, Integer ssid) throws Exception {

		String sql = " from ProductIntegral as p where p.productid='" + pid
				+ "' and p.unitid=" + unitid + " and p.salesort=" + ssid + " ";
		System.out.println("==========>"+sql);
		ProductIntegral p = (ProductIntegral) EntityManager.find(sql);
		return p;
	}

	
	public void updProductIntegral(ProductIntegral p) throws Exception {

		EntityManager.update(p);

	}

	public void addProductIntegral(ProductIntegral product) throws Exception {

		EntityManager.save(product);

	}

	public void delProductIntegralByPIDUIDSSID(String pid, Integer unitid,
			Integer ssid) throws Exception {

		String sql = "delete from Product_Integral where productid='" + pid
				+ "' and unitid=" + unitid + " and salesort=" + ssid + " ";
		EntityManager.updateOrdelete(sql);

	}

	public void delProductIntegralBPID(String pid) throws Exception {

		String sql = "delete from Product_Integral where productid='" + pid
				+ "'";
		EntityManager.updateOrdelete(sql);

	}

	public void delProductIntegral(String id) throws Exception {

		String sql = "delete from Product_Integral where id='" + id + "'";
		EntityManager.updateOrdelete(sql);

	}

}
