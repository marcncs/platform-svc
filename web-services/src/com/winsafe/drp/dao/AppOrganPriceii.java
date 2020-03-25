package com.winsafe.drp.dao;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.pager2.PageQuery;

public class AppOrganPriceii {

	public void addOrganPriceii(OrganPriceii pp) throws Exception {		
		EntityManager.save(pp);		
	}

	public void updOrganPriceii(OrganPriceii pp) throws Exception {		
		EntityManager.update(pp);		
	}
	
	public void updOrganPriceiiPrice(int id, double price,double frate) throws Exception {		
		String sql = "update Organ_Priceii set unitprice="+price+",frate="+frate+" where id="+id;
		
		EntityManager.updateOrdelete(sql);		
	}

	public void delOrganPriceiiByOIDPID(String oid, String pid)
			throws Exception {		
		String sql = "delete from Organ_Priceii where organid='" + oid
				+ "' and productid='" + pid + "'";
		EntityManager.updateOrdelete(sql);
		
	}

	public void delOrganPriceiiByOID(String oid) throws Exception {		
		String sql = "delete from Organ_Priceii where organid='" + oid + "'";
		EntityManager.updateOrdelete(sql);		
	}
	
	public void copyOrganPriceii(String srcOid, String tagOid) throws Exception {		
		String sql = "insert organ_priceii(organid,productid,unitid,policyid,unitprice) "+
		 "select '"+tagOid+"',op.productid,op.unitid,op.policyid,op.unitprice from Organ_Priceii  op "+
		 "where op.organid='"+srcOid+"'";
		EntityManager.updateOrdelete(sql);		
	}
	
	public void copyProductPriceii(String oid, String productid, int grade) throws Exception {		
		String sql = "insert into organ_priceii(organid,productid,unitid,unitprice,frate) "+
		 "select '"+oid+"',op.productid,op.unitid,op.unitprice,op.frate from product_priceii op, organ_grade og "+
		 "where op.productid='"+productid+"' and op.policyid=og.policyid and og.id="+grade;

		EntityManager.updateOrdelete(sql);		
	}

	public OrganPriceii getOrganPriceiiByOidPidUidPlid(String oid, String pid,int uid,int plid )
			throws Exception {
		String sql = " from OrganPriceii as op where op.organid='" + oid
				+ "' and op.productid='" + pid + "' and op.unitid="+uid+" and op.policyid="+plid+" ";
		return (OrganPriceii) EntityManager.find(sql);
	}
	
	public OrganPriceii getOrganPriceiiByOidPidUid(String oid, String pid,int uid )
	throws Exception {
		String sql = " from OrganPriceii as op where op.organid='" + oid
		+ "' and op.productid='" + pid + "' and op.unitid="+uid+" ";
		return (OrganPriceii) EntityManager.find(sql);
	}

	public OrganPriceii getOrganPriceiiById(int id) throws Exception {
		String sql = "from OrganPriceii where id=" + id;
		return (OrganPriceii) EntityManager.find(sql);
	}

	public List getOrganPriceiiByProductID(String wheresql) throws Exception {
		String sql = " from OrganPriceii " + wheresql;
		return EntityManager.getAllByHql(sql);
	}
	
	
	public List findOrganPriceii(HttpServletRequest request,int pagesize, String pWhereClause) throws Exception {
		String sql = "select new map(p.id as id,op.organid as organid,p.productname as productname,p.specmode as specmode,op.unitid as unitid,op.unitprice as unitprice,op.frate as frate) from Product as p,OrganPriceii as op,Organ as o  "
				+ pWhereClause + " order by op.organid desc";
		return PageQuery.hbmQuery(request, sql, pagesize);
	}
	
	public List findOrganPriceii(String pWhereClause) throws Exception {
		String sql = "select new map(p.id as productid,op.organid as organid,p.productname as productname,p.specmode as specmode,op.id as id,op.unitid as unitid,op.unitprice as unitprice,op.frate as frate) from Product as p,OrganPriceii as op,Organ as o  "
			+ pWhereClause + " order by op.organid desc";	

		return EntityManager.getAllByHql(sql);
	}
}
