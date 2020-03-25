package com.winsafe.drp.dao;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.pager.SimplePageInfo;
import com.winsafe.hbm.util.pager2.PageQuery;

public class AppObjIntegral {

	public List getObjIntegral(HttpServletRequest request, int pagesize,
			String pWhereClause) throws Exception {

		String sql = " from ObjIntegral as oi " + pWhereClause
				+ " order by oi.oid desc";
		return PageQuery.hbmQuery(request, sql, pagesize);

	}
	
	public List getObjIntegral(String pWhereClause) throws Exception {

		String sql = " from ObjIntegral as oi " + pWhereClause
				+ " order by oi.oid desc";
		return EntityManager.getAllByHql(sql);

	}

	public List searchObjIntegral(int pagesize, String pWhereClause,
			SimplePageInfo pPgInfo) throws Exception {
		String sql = " from ObjIntegral as oi " + pWhereClause
				+ " order by oi.oid desc";
		return EntityManager.getAllByHql(sql, pPgInfo.getCurrentPageNo(),
				pagesize);
	}

	public void addObjIntegral(Object ro) throws Exception {

		EntityManager.save(ro);

	}

	
	public void addObjIntegralIsNoExist(ObjIntegral oi) throws Exception {

		String sql = "insert into Obj_Integral(oid,osort,oname,omobile,organid,keyscontent) select '"
				+ oi.getOid()
				+ "',"
				+ oi.getOsort()
				+ ",'"
				+ oi.getOname()
				+ "','"
				+ oi.getOmobile()
				+ "','"
				+ oi.getOrganid()
				+ "','"
				+ oi.getKeyscontent()
				+ "' where not exists (select oid from obj_integral where osort="
				+ oi.getOsort()
				+ " and oid='"
				+ oi.getOid()
				+ "' and osort=" +oi.getOsort() + ")";
		EntityManager.updateOrdelete(sql);

	}

	
	public ObjIntegral getObjIntegralByOSID(Integer os, String id)
			throws Exception {
		String sql = " from ObjIntegral as oi where oi.osort=" + os
				+ " and oi.oid='" + id + "'";
		return (ObjIntegral) EntityManager.find(sql);

	}

	public void delObjIntegral(String id) throws Exception {

		String sql = "delete from Obj_Integral where oid='" + id
				+ "' ";
		EntityManager.updateOrdelete(sql);

	}

	public void updObjIntegral(ObjIntegral r) throws Exception {

		EntityManager.update(r);

	}
	
	
	public void updKeysContent(String id, String keys) throws Exception {
		String sql = "update Obj_Integral set keyscontent='" + keys
				+ "' where oid='" + id + "' ";
		EntityManager.updateOrdelete(sql);
	}
	
	//
	public void delIntegralIByBillNo(String billno)throws Exception{
		String sql="delete from integral_i where billno='"+billno+"'";
		EntityManager.updateOrdelete(sql);
	}
	
	public void delIntegralOByBillNo(String billno)throws Exception{
		String sql="delete from integral_o where billno='"+billno+"'";
		EntityManager.updateOrdelete(sql);
	}
	
	public void delIntegralDetailByBillNo(String billno)throws Exception{
		String sql="delete from integral_detail where billno='"+billno+"'";
		EntityManager.updateOrdelete(sql);
	}
	
	public double getBalance(String oid,int osort)throws Exception{
		String sqli="select sum(ii.aincome) from IntegralI as ii where ii.oid='"+oid+"' and osort="+osort+" ";
		String sqlo="select sum(io.aout) from IntegralO as io where io.oid='"+oid+"' and osort="+osort+"";
		double i = EntityManager.getdoubleSum(sqli);
		double o = EntityManager.getdoubleSum(sqlo);
		return i - o;
	}

}
