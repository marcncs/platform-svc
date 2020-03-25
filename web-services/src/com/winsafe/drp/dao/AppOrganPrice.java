package com.winsafe.drp.dao;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.pager2.PageQuery;

public class AppOrganPrice {

	public void addOrganPrice(OrganPrice pp) throws Exception {

		EntityManager.save(pp);

	}

	public void updOrganPrice(OrganPrice pp) throws Exception {

		EntityManager.saveOrUpdate(pp);

	}

	public void delOrganPriceByOIDPID(String oid, String pid) throws Exception {

		String sql = "delete from Organ_Price where organid='" + oid
				+ "' and productid='" + pid + "'";
		EntityManager.updateOrdelete(sql);

	}

	public void delOrganPriceByOID(String oid) throws Exception {
		String sql = "delete from Organ_Price where organid='" + oid + "'";
		EntityManager.updateOrdelete(sql);
	}

	public void copyOrganPrice(String srcOid, String tagOid) throws Exception {
		String sql = "insert into organ_price(organid,productid,unitid,policyid,unitprice) "
				+ "select '"
				+ tagOid
				+ "',productid,unitid,policyid,unitprice from organ_price  "
				+ "where organid='" + srcOid + "'";
		EntityManager.updateOrdelete(sql);
	}

	public OrganPrice getOrganPriceByOidPidUidPlid(String oid, String pid,
			int uid, int plid) throws Exception {
		String sql = " from OrganPrice as op where op.organid='" + oid
				+ "' and op.productid='" + pid + "' and op.unitid=" + uid
				+ " and op.policyid=" + plid + " ";
		OrganPrice bank = (OrganPrice) EntityManager.find(sql);
		return bank;
	}

	public OrganPrice getOrganPriceById(Long id) throws Exception {
		String sql = "from OrganPrice where id=" + id;
		OrganPrice bank = (OrganPrice) EntityManager.find(sql);
		return bank;
	}

	public List getOrganPriceByProductID(String wheresql) throws Exception {
		List list = null;
		String sql = " from OrganPrice " + wheresql;
		list = EntityManager.getAllByHql(sql);
		return list;
	}

	public List getOrganPriceByOrganID(HttpServletRequest request,
			int pagesize, String whereSql) throws Exception {
		String hql = "select new map(op.id as id,op.productid as productid,p.productname as "
				+ "productname,p.specmode as specmode,op.unitid as unitid,op.policyid as policyid,"
				+ "op.unitprice as unitprice) from OrganPrice as op,Product as p "
				+ whereSql + " and p.id=op.productid order by op.id desc";
		return PageQuery.hbmQuery(request, hql, pagesize);
	}
}
