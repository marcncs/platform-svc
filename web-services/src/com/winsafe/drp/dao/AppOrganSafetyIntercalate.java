package com.winsafe.drp.dao;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.pager.SimplePageInfo;
import com.winsafe.hbm.util.pager2.PageQuery;

public class AppOrganSafetyIntercalate {
	public List getOrganSafetyIntercalate(int pagesize, String pWhereClause,
			SimplePageInfo pPgInfo) throws Exception {
		List pls = null;
		int targetPage = pPgInfo.getCurrentPageNo();
		String sql = "select ws.id,ws.organid,ws.productid,ws.safetyh,ws.safetyl from OrganSafetyIntercalate as ws "
				+ pWhereClause + " order by ws.id desc ";
		pls = EntityManager.getAllByHql(sql, targetPage, pagesize);
		return pls;
	}

	
	public List getOrganSafetyIntercalateList(HttpServletRequest request,int pagesize,
			String pWhereClause) throws Exception {
		String hql = "select new map(ws.id as id,ws.productid as productid,p.productname as productname,p.specmode as specmode,ws.safetyl as safetyl,ws.safetyh as safetyh,ws.organid as organid) from Product as p, OrganSafetyIntercalate as ws "
				+ pWhereClause + " order by ws.id desc ";
		return PageQuery.hbmQuery(request, hql, pagesize);
	}
	
	public OrganSafetyIntercalate getOrganSafetyIntercalateByProductidOID(String productid,String oid)throws Exception{
		String sql=" from OrganSafetyIntercalate as osi where osi.productid='"+productid+"' and osi.organid='"+oid+"'";
		OrganSafetyIntercalate oi = (OrganSafetyIntercalate)EntityManager.find(sql);
		return oi;
	}

	
	public List getOrganSafetyByOID(Long wid) throws Exception {
		List sls = null;
		String sql = "select ws.id,ws.productid,ws.safetyh,ws.safetyl from OrganSafetyIntercalate as ws where ws.organid="
				+ wid;
		sls = EntityManager.getAllByHql(sql);
		return sls;
	}

	public void addOrganSafetyIntercalate(Object ws) throws Exception {

		EntityManager.save(ws);

	}

	public OrganSafetyIntercalate getOrganSafetyIntercalateByID(int id)
			throws Exception {
		String sql = " from OrganSafetyIntercalate as ws where ws.id =" + id;
		return (OrganSafetyIntercalate) EntityManager.find(sql);
 	}

	public void updOrganSafetyIntercalate(OrganSafetyIntercalate ws)
			throws Exception {	
		EntityManager.update(ws);
	}

	public void delOrganSafetyIntercalate(int id) throws Exception {

		String sql = "delete from organ_safety_intercalate where id=" + id;
		EntityManager.updateOrdelete(sql);

	}

	
	public int getSafetyProductCount(String oid) throws Exception {
		int s = 0;
		String sql = "select count(*) from ProductStockpile as ps,OrganSafetyIntercalate as osi,Warehouse as w where (ps.stockpile<osi.safetyl or ps.stockpile>osi.safetyh) and ps.productid=osi.productid and w.makeorganid='"
				+ oid + "' and w.makeorganid=osi.organid group by ps.productid";
		//System.out.println("----sql===" + sql);
		s = EntityManager.getRecordCount(sql);
		//System.out.println("----aaa==" + s);
		return s;
	}

	
	public List getCurrentStock(String oid) throws Exception {
		String sql = "select ps.productid,sum(ps.stockpile) from ProductStockpileAll as ps,OrganSafetyIntercalate as osi,Warehouse as w "
				+ " where (ps.stockpile<osi.safetyl or ps.stockpile>osi.safetyh) and "
				+ " ps.productid=osi.productid and ps.warehouseid=w.id and w.makeorganid='"+oid+"' and w.makeorganid=osi.organid group by ps.productid ";
		List cs = EntityManager.getAllByHql(sql);
		return cs;
	}
	
	public List getCurrentStock2(HttpServletRequest request,int pagesize,String oid) throws Exception {
		String sql = "select mp.productid,mp.stockpile " +
				"from Organ_Safety_Intercalate as osi, "+
				"(select w.makeorganid,ps.productid,sum(ps.stockpile) as stockpile "+
				"from Product_Stockpile_all as ps,Warehouse as w  "+
				"where  ps.warehouseid=w.id and w.makeorganid='"+oid+"'   " +
				"group by w.makeorganid,ps.productid ) as mp "+
				"where osi.organid=mp.makeorganid and mp.productid=osi.productid and "+
				"(mp.stockpile<osi.safetyl or mp.stockpile>osi.safetyh) ";
		return PageQuery.jdbcSqlserverQuery(request, "productid", sql, pagesize);
	}

}
