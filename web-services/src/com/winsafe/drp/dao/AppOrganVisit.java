package com.winsafe.drp.dao;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.HibernateException;

import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.pager2.PageQuery;

public class AppOrganVisit {
	
	/**
	 * 保存
	 * Create Time 2014-10-13 下午04:10:13 
	 * @param d
	 * @throws Exception
	 */
	public void SaveOrganVisit(Object d) throws Exception {		
		EntityManager.save(d);		
	}
	
	/**
	 * 查询业务往来机构
	 * @param request
	 * @param pagesize
	 * @param whereSql
	 * @return
	 * @throws Exception
	 */
	public List getOrganVisit(HttpServletRequest request, int pagesize, String whereSql) throws Exception{
		String hql=" select new map(o.id as organid, o.organname as organname,o.bigRegionName as bigRegionName,o.officeName as officeName)  from Organ as o "+whereSql +" order by o.id";
		return PageQuery.hbmQuery(request, hql, pagesize);
	}
	/**
	 * 删除操作
	 * @param whereSql
	 * @throws Exception
	 */
	public void delOrganVisit(String whereSql)throws Exception {		
		String sql="delete from organ_visit ov " + whereSql;
		EntityManager.updateOrdelete(sql);		
	}
	
	/**
	 * 批量添加业务往来机构
	 * @param whereSql
	 * @param uid
	 * @throws Exception
	 */
	public void addAllOrganVisit(String whereSql,String uid) throws  Exception{
		String sql = "insert into organ_visit (id,userid,visitorgan)" +
				" select (select nvl(max(id),0) from organ_visit)+row_number() over ( order by id ),'" + uid +  "',o.id from organ o " + whereSql 
				+ " and not exists (select u.id from organ_visit  u where o.id=u.visitorgan and  u.userid= " + uid +")" ;
		EntityManager.updateOrdelete(sql); 
	}
	
	public OrganVisit findOrganVisit(int userid,String oid) throws Exception{
		String sql = " from OrganVisit where  userid='"+ userid + "' and visitorgan='" + oid + "' ";
		return (OrganVisit) EntityManager.find(sql);
	}
	
	public List<OrganVisit> queryByOrganId(String oid) throws Exception{
		String sql = " from OrganVisit where  visitorgan='" + oid + "' order by userid ";
		return EntityManager.getAllByHql(sql);
	}

	public void addOrganVisit(String oid, String parentid) throws Exception {
		String sql = "INSERT INTO ORGAN_VISIT (id,userid,visitorgan) " +
				"select (select nvl(max(id),0) from ORGAN_VISIT)+row_number() over ( order by id ),userid,'"+oid+"' FROM USER_VISIT uv " +
				"where visitorgan = '"+parentid+"' and not exists (select id from ORGAN_VISIT where userid = uv.userid and visitorgan = '"+oid+"') ";
		EntityManager.executeUpdate(sql);
	}
	
	public void addOrganVisitForTR(String oid, String userid) throws Exception {
		String sql = "INSERT INTO ORGAN_VISIT (id,userid,visitorgan) " +
				"select (select nvl(max(id),0) from ORGAN_VISIT)+row_number() over ( order by id ),"+userid+",organizationid FROM S_TRANSFER_RELATION str " +
				"where opporganid = '"+oid+"' and not exists (select id from ORGAN_VISIT where userid = "+userid+" and visitorgan = str.organizationid)";
		EntityManager.executeUpdate(sql);
	}
	
	public void delOrganVisitForTR(String oid, int userid) throws Exception {
		String sql = "DELETE from ORGAN_VISIT where userid = "+userid+" and visitorgan in (" +
				"select organizationid from S_TRANSFER_RELATION where opporganid = '"+oid+"')";
		EntityManager.executeUpdate(sql);
	}

	public void delOrganVisit(String oid, String parentid) throws Exception {
		String sql = "delete from ORGAN_VISIT " +
				"where visitorgan = '"+oid+"' and USERID in ( " +
				"select USERID FROM USER_VISIT where visitorgan = '"+parentid+"') ";
		EntityManager.executeUpdate(sql);
	}
	
	public void addOrganVisitByParentOrganId(String userid, String parentOId) throws Exception {
		String sql = "INSERT INTO ORGAN_VISIT (id,userid,visitorgan) " +
				"select (select nvl(max(id),0) from ORGAN_VISIT)+row_number() over ( order by id ),"+userid+",o.id FROM Organ o " +
				"where o.parentId = '"+parentOId+"' and not exists (select id from ORGAN_VISIT where userid = "+userid+" and visitorgan = o.id) ";
		EntityManager.executeUpdate(sql);
	}
	
}
