package com.winsafe.drp.dao;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.pager2.PageQuery;
@SuppressWarnings("unchecked")
public class AppMoveCanuseOrgan {
	
	public void addMoveCanuseOrgan(Object d) throws Exception {		
		EntityManager.save(d);		
	}

	public void delMoveCanuseOrgan(Integer opid)throws Exception{
		String sql="delete from Move_Canuse_Organ where id="+opid;
		EntityManager.updateOrdelete(sql);
	}

	public List getMoveCanuseOrgan(HttpServletRequest request, int pagesize, String whereSql) throws Exception{
		String hql=" from MoveCanuseOrgan as mco "+whereSql +" order by mco.id ";
		return PageQuery.hbmQuery(request, hql, pagesize);
	}
	
	public List getCanuseOrgan(HttpServletRequest request, int pageSize, String whereSql) throws Exception{
		String hql="select o from Organ as o,MoveCanuseOrgan as mco "+whereSql +" order by o.id ";
		return PageQuery.hbmQuery(request, hql, pageSize);
	}

	public List getOrganNotInCanuseOrgan(HttpServletRequest request, int pagesize, String pWhereClause)throws Exception{
		String hql=" from Organ as o "+pWhereClause+" ";
		return PageQuery.hbmQuery(request, hql, pagesize);
	}

	public MoveCanuseOrgan getMoveCanuseOrganByID(Long id)throws Exception{
		return (MoveCanuseOrgan)EntityManager.find("from MoveCanuseOrgan as op where op.id="+id);
	}
	
	public MoveCanuseOrgan getMoveCanuseOrganByOID(String oid)throws Exception{
		return (MoveCanuseOrgan)EntityManager.find("from MoveCanuseOrgan as op where op.oid="+oid);
	}
	
	public List queryMoveCanUseOrgan(int userid) throws Exception{
		String hql="select o from Organ as o,MoveCanuseOrgan as mco where mco.uid='"+userid+"' order by o.id ";
		return EntityManager.getAllByHql(hql);
	}
	
	public List<MoveCanuseOrgan> getMoveCanuseOrganByUserid(Integer userId) {
		String hql = "from MoveCanuseOrgan where uid = " + userId +" and begindate<='"+DateUtil.getCurrentDateString()+"' and enddate>='"+DateUtil.getCurrentDateString()+"'";
		List<MoveCanuseOrgan> rules = EntityManager.getAllByHql(hql);
		return rules;
	}
	
}
