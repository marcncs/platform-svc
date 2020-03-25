package com.winsafe.drp.dao;

import java.util.List;

import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.pager.SimplePageInfo;

public class AppProductRedeploy {
	
	public List getProductRedeploy(int pagesize, String pWhereClause,
			SimplePageInfo pPgInfo) throws Exception {
		List pls = null;
		int targetPage = pPgInfo.getCurrentPageNo();
		String sql = " from ProductRedeploy as p "
				+ pWhereClause + " order by p.id desc";
		pls = EntityManager.getAllByHql(sql, targetPage, pagesize);
		return pls;
	}
	
	
	public List getSingleProductRedeploy(int pagesize, String pWhereClause,
			SimplePageInfo pPgInfo) throws Exception {
		List pls = null;
		int targetPage = pPgInfo.getCurrentPageNo();
		String sql = "select distinct(p.id),p.productname,p.specmode,p.psid,p.countunit,p.productcode,p.barcode,p.useflag from ProductRedeploy as p,ProductRedeployProperty as pp "
				+ pWhereClause + " order by p.id desc";
		//System.out.println("sql===="+sql);
		pls = EntityManager.getAllByHql(sql, targetPage, pagesize);
		return pls;
	}

	
	public void addProductRedeploy(Object product) throws Exception {
		
		EntityManager.save(product);
		
	}
	
	public void saveProductRedeploy(Object product) throws Exception {
		
		EntityManager.save(product);
		
	}

	
	public ProductRedeploy getProductRedeployByID(String id) throws Exception {
		ProductRedeploy p = null;
		String sql = " from ProductRedeploy as p where p.id='" + id+"'";
		p = (ProductRedeploy) EntityManager.find(sql);
		return p;
	}

	
//	public String updProductRedeploy(ProductRedeploy p) throws Exception {
//		
//		String sql = " update product set productname='" + p.getProductRedeployname()
//				+ "',psid='" + p.getPsid() + "',countunit=" + p.getCountunit()
//				+ ",";
//		sql += " productcode='" + p.getProductRedeploycode() + "',specmode='"
//				+ p.getSpecmode() + "',barcode='" + p.getBarcode()
//				+ "',standardpurchase=" + p.getStandardpurchase() + ",";
//		sql += " standardsale=" + p.getStandardsale() + ",leastsale="
//				+ p.getLeastsale() + ",leaststock=" + p.getLeaststock() + ",";
//		sql += " standardstock=" + p.getStandardstock() + ",tiptopstock="
//				+ p.getTiptopstock() + ",abcsort=" + p.getAbcsort() + ",memo='"
//				+ p.getMemo() + "',useflag=" + p.getUseflag();
//		if (p.getProductRedeploypicture() != null && !p.getProductRedeploypicture().equals("")) {
//			sql += ", productpicture = '" + p.getProductRedeploypicture() + "'";
//		}
//		sql += " where id = '" + p.getId()+"'";
//
//		EntityManager.updateOrdelete(sql);
//		
//	}
	
	public void updateProductRedeploy(ProductRedeploy p) throws Exception {
		
		EntityManager.update(p);
		
	}

	
	public List getStockConstrue(int pagesize, String pWhereClause,
			SimplePageInfo pPgInfo) throws Exception {
		List pls = null;
		int targetPage = pPgInfo.getCurrentPageNo();
		String sql = "select p.id,p.productname,p.countunit,p.leaststock,p.standardstock,p.tiptopstock,sum(ps.stockpile) from ProductRedeploy as p,ProductRedeployStockpile as ps "
				+ pWhereClause + " group by p.id  order by p.id desc";
		pls = EntityManager.getAllByHql(sql, targetPage, pagesize);
		return pls;
	}

	
	public List getMakePurchaseApply(String pWhereClause) throws Exception {
		List pls = null;

		String sql = "select p.id,p.productname,p.countunit,p.standardpurchase,p.leaststock,p.standardstock,p.tiptopstock,sum(ps.stockpile) from ProductRedeploy as p,ProductRedeployStockpile as ps "
				+ pWhereClause + " group by p.id  order by p.id desc";
		pls = EntityManager.getAllByHql(sql);
		return pls;
	}

	
	public List getSelectProductRedeploy(int pagesize, String pWhereClause,
			SimplePageInfo pPgInfo) throws Exception {
		List pls = null;
		int targetPage = pPgInfo.getCurrentPageNo();
		String sql = "select p.id,p.productname,p.specmode,p.countunit,p.standardpurchase,p.standardsale from ProductRedeploy as p ,ProductRedeployProperty as pp "
				+ pWhereClause + " order by p.id desc";
		//System.out.println("--------"+sql);
		pls = EntityManager.getAllByHql(sql, targetPage, pagesize);
		return pls;
	}
	
	public List getSelectProductRedeployRate(int pagesize, String pWhereClause,
			SimplePageInfo pPgInfo) throws Exception {
		List pls = null;
		int targetPage = pPgInfo.getCurrentPageNo();
		String sql = "select p.id,p.productname,p.specmode,p.countunit,p.standardpurchase,p.standardsale,p.pricei,p.priceii,p.priceiii,p.pricewholesale,p.priceivs,p.priceuni from ProductRedeploy as p ,ProductRedeployProperty as pp "
				+ pWhereClause + " order by p.id desc";
		//System.out.println("--------"+sql);
		pls = EntityManager.getAllByHql(sql, targetPage, pagesize);
		return pls;
	}
	

	
	public List getSelectSaleOrderProductRedeploy(int pagesize, String pWhereClause,
			SimplePageInfo pPgInfo) throws Exception {
		List pls = null;
		int targetPage = pPgInfo.getCurrentPageNo();
		String sql = "select p.id,p.productname,p.specmode,p.countunit, " +
				"p.standardsale,p.pricei,p.priceii,p.priceiii,p.pricewholesale,p.priceivs,p.priceuni " +				
				"from ProductRedeploy as p, CpProductRedeploy as cpp, ProductRedeployProperty as pp  "
				+ pWhereClause + " order by p.id desc";
		//System.out.println("--------"+sql);
		pls = EntityManager.getAllByHql(sql, targetPage, pagesize);
		return pls;
	}
	
	
	public List getSelectAllProductRedeploy(int pagesize, String pWhereClause,
			SimplePageInfo pPgInfo) throws Exception {
		List pls = null;
		int targetPage = pPgInfo.getCurrentPageNo();
		String sql = "select p.id,p.productname,p.countunit,p.specmode,p.standardpurchase from ProductRedeploy as p "
				+ pWhereClause + " order by p.id desc";
		//System.out.println("--------"+sql);
		pls = EntityManager.getAllByHql(sql, targetPage, pagesize);
		return pls;
	}
	
	
	public List getSelectFinishProductRedeploy(int pagesize, String pWhereClause,
			SimplePageInfo pPgInfo) throws Exception {
		List pls = null;
		int targetPage = pPgInfo.getCurrentPageNo();
		String sql = "select distinct(p.id),p.productname,p.specmode,p.countunit,p.standardpurchase,p.standardsale from ProductRedeploy as p ,ProductRedeployProperty as pp "
				+ pWhereClause + " order by p.id desc";
		//System.out.println("--------"+sql);
		pls = EntityManager.getAllByHql(sql, targetPage, pagesize);
		return pls;
	}

	
	public List getAllProductRedeploy() throws Exception {
		List ap = null;
		String sql = "select p.id,p.countunit,p.productcode,p.barcode from ProductRedeploy as p";
		ap = EntityManager.getAllByHql(sql);
		return ap;
	}
	
	
	public int getCountProductRedeployByPSID(String dcode)throws Exception{
		int c =0;
		String sql="select count(p.id) from ProductRedeploy as p where p.psid='"+dcode+"'";
		c = EntityManager.getRecordCount(sql);
		return c;
	}
	

	public int getProductRedeployID(String id) throws Exception {
		int c = 0;
		String sql = "select count(*) from ProductRedeploy where id='" + id
				+ "'";
		c = EntityManager.getRecordCountQuery(sql);
		return c;
	}

}
