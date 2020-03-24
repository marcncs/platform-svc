package com.winsafe.drp.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.HibernateException;

import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.pager2.PageQuery;

public class AppUserVisit {
	
	public void SaveUserVisit(Object d) throws Exception {		
		EntityManager.save(d);		
	}
	
	public void updUserVisit(Object d) throws Exception {		
		EntityManager.update(d);		
	}
	
	public List<UserVisit> getUserVisitByOrganList(String id)throws Exception{
		String hql = "from UserVisit as uv where uv.visitorgan like ('%,"+id+",%') or uv.visitorgan like ('"+id+",%') " +
				" or uv.visitorgan like ('"+id+"%') or uv.visitorgan like ('%,"+id+"') ";
		return EntityManager.getAllByHql(hql);
	}
	
	/**
	 * 主要功能：根据条件得到相应的机构
	 * @param whereSql 条件语句
	 * @return 机构列表
	 * @author RichieYu
	 */
	public List getAllUserVisitOrgan(String whereSql){
	    return EntityManager.getAllByHql("from Organ as o " + whereSql + " order by o.id");
	}
	
	public List getUserVisitOrgan(HttpServletRequest request, int pagesize, String whereSql) throws Exception{
		String hql=" select new map(o.id as organid, o.organname as organname)  from Organ as o "+whereSql +" order by o.id";
		return PageQuery.hbmQuery(request, hql, pagesize);
	}
	
	public List getUVOrgan(HttpServletRequest request, int pagesize, String whereSql) throws Exception{
		String hql=" select new map(o.id as organid, o.organname as organname,o.bigRegionName as bigRegionName,o.officeName as officeName)  from Organ as o "+whereSql +" order by o.id";
		return PageQuery.hbmQuery(request, hql, pagesize);
	}
	public List getUVOrgan( String whereSql) throws Exception{
		String hql=" select new map(o.id as organid, o.organname as organname,o.bigRegionName as bigRegionName,o.officeName as officeName)  from Organ as o "+whereSql +" order by o.id";
		return EntityManager.getAllByHql( hql);
	}
	
	public void UpdUserVisitByUserID(int uid, String vu)throws Exception {		
		String sql="update User_Visit set visitusers='"+vu+"' where userid="+uid;
		EntityManager.updateOrdelete(sql);		
	}
	
	public void delUserVisitByUserID(int uid)throws Exception {		
		String sql="delete from User_Visit where userid="+uid;
		EntityManager.updateOrdelete(sql);		
	}
	
	public void delUserVisit(String whereSql)throws Exception {		
		String sql="delete from User_Visit uv " + whereSql;
		EntityManager.updateOrdelete(sql);		
	}

	public void delUserVisitByUserid(String userid) throws Exception{
		String sql = "delete from USER_VISIT " +
				" where " +
				" userid= " +  userid + 
				" and visitorgan  not in " +
				"( " +
					"select makeorganid from WAREHOUSE" +
					" WHERE id in (select warehouse_id from RULE_USER_WH where user_id = " + userid + " )" +
				" )";
		EntityManager.updateOrdelete(sql);
	}
	
	public void delUserVisitByUserids(String userid) throws Exception{
		String sql = "delete from USER_VISIT " +
				" where " +
				" userid in (" +  userid + ")" +
				" and visitorgan  not in " +
				"( " +
					"select makeorganid from WAREHOUSE" +
					" WHERE id in (select warehouse_id from RULE_USER_WH where user_id in ( " + userid + " ))" +
				" )";
		EntityManager.updateOrdelete(sql);
	}
	
	public void UpdOrganVisitByUserID(int id, String vd)throws Exception {		
		String sql="update User_Visit set visitorgan='"+vd+"' where userid="+id;
		EntityManager.updateOrdelete(sql);		
	}
	
	public void UpdDeptVisitByUserID(int id, String vd)throws Exception {
		
		String sql="update User_Visit set visitdept='"+vd+"' where userid="+id;
		EntityManager.updateOrdelete(sql);
		
	}
	
	/**
	 * 批量添加用户管辖机构
	 * @param whereSql
	 * @param uid
	 * @throws Exception
	 */
	public void addAllUserVisit(String whereSql,String uid) throws  Exception{
		String sql = "insert into user_visit (id,userid,visitorgan)" +
				" select (select nvl(max(id),0) from user_visit)+row_number() over ( order by id ),'" + uid +  "',o.id from organ o " + whereSql 
				+ " and not exists (select u.id from user_visit  u where o.id=u.visitorgan and  u.userid= " + uid +")" ;
		EntityManager.updateOrdelete(sql); 
	}
	
	/**
	 * 根据仓库条件增加管辖机构
	 */
	public void addAllUserVisitByWid(String whereSql,String uid) throws  Exception{
		String sql = "";
		sql = "insert into user_visit (id,userid,visitorgan) select (select nvl(max(id),0) from user_visit)+row_number() over ( order by o.id )," + uid + ",o.id from organ o where o.id in (select w.makeorganid from warehouse w " + whereSql  + ")  " 
		+ " and not exists (select u.id from user_visit  u where o.id=u.visitorgan and  u.userid= " + uid +") " ;
		
		EntityManager.updateOrdelete(sql);
	}
	
	public void addAllUserVisitByOid(String whereSql,String oid) throws  Exception{
		String sql = "";
		sql = "insert into user_visit (id,userid,visitorgan) select (select nvl(max(id),0) from user_visit)+row_number() over ( order by u.userid ),u.userid,'"+oid+"' from Users u " + whereSql 
		+ " and not exists (select ruw.id from user_visit ruw where u.userid=ruw.userid and ruw.visitorgan = '" + oid +"')";
		
		EntityManager.updateOrdelete(sql);
	}
	
	public UserVisit getUserVisitByUserID(int userid)throws Exception{
		return (UserVisit)EntityManager.find(" from UserVisit as uv where uv.userid="+userid);
	}
	
	public List<UserVisit> queryUserVisitByUserID(int userid)throws Exception{
		return EntityManager.getAllByHql(" from UserVisit as uv where uv.userid="+userid);
	}
	
	public List<UserVisit> queryIncludeOrgan(String organid){
		return EntityManager.getAllByHql(" from UserVisit as uv  where instr("+organid+",uv.visitorgan)>=1 ");
	}
	
	@SuppressWarnings("unchecked")
	public List<UserVisit> queryIncludeOrgan(){
		List<UserVisit> uvList = new ArrayList<UserVisit>();
		List list = EntityManager.getAllByHql(" from UserVisit as uv,UserRole as ur where uv.userid=ur.userid and ur.roleid=10005 and ur.ispopedom=1");
		for(int i=0;i<list.size();i++){
			Object[] o = (Object[])list.get(i);
			uvList.add((UserVisit)o[0]);
		}
		return uvList;
	}
	
	/**
	 * @author jason.huang
	 * @param whereSql
	 * @return
	 * 根据userid获取机构号
	 */
	public List getAllUserVisitOrgan(int userid){
	    return EntityManager.getAllByHql("from UserVisit as u  where userid='" + userid + "' order by u.id");
	}
	/**
	 * 根据用户和机构获取管辖权限
	 * Create Time 2014-12-23 上午10:31:51 
	 * @param userid
	 * @param oid
	 * @return
	 */
	public UserVisit findVisitOrgan(int userid,String oid){
		String hql = "from UserVisit as u  where userid='" + userid + "' and visitorgan = '" + oid + "' order by u.id";
	    return (UserVisit) EntityManager.find(hql);
	}
	/**
	 * 
	 * 根据机构获取具有权限的用户
	 * Create Time 2014-12-23 上午10:59:25 
	 * @param userid
	 * @param oid
	 * @return
	 */
	public List<UserVisit> queryByOrganId(String oid){
		String hql = "from UserVisit as u  where visitorgan = '" + oid + "' order by u.id";
	    return EntityManager.getAllByHql(hql);
	}
	
	/**
	 * 检查管辖机构下的所有仓库是否全部管辖
	 */
	public Boolean checkAllRWAuth(String oid,String uid){
		Boolean isExist = true;
		String sql = "select count(w.id) from WAREHOUSE w " +
				" LEFT JOIN RULE_USER_WH rw on w.id = rw.WAREHOUSE_ID and rw.USER_ID =  " + uid 
				+ " where " 
				+ "  w.makeorganid= '" + oid + "'"   
				+ " and rw.id is null " ;
	    Integer obj = EntityManager.getRecordCountBySql(sql);
    	if(obj > 0){
    		isExist = false;
    	}
	    return isExist;
	}
	
	public Boolean checkRWAuth(String oid,String uid){
		Boolean isExist = true;
		String sql = "select count(w.id) from WAREHOUSE w " +
				" LEFT JOIN RULE_USER_WH rw on w.id = rw.WAREHOUSE_ID and rw.USER_ID =  " + uid 
				+ " where " 
				+ "  w.makeorganid= '" + oid + "'"   
				+ " and rw.id not null " ;
	    Integer obj = EntityManager.getRecordCountBySql(sql);
    	if(obj > 0){
    		isExist = false;
    	}
	    return isExist;
	}
	
	public void delNoUseUserVisit(String uid) throws  Exception{
		
		String sql = " delete from USER_VISIT uv where uv.userid= " + uid  
			+	" and uv.visitorgan in ( select w.makeorganid from WAREHOUSE w " 
			+ " LEFT JOIN RULE_USER_WH rw on w.id = rw.WAREHOUSE_ID and rw.USER_ID =  " + uid 
			+ " where " 
			+ " rw.id is null )" ;
		EntityManager.updateOrdelete(sql);
	}
	
	/**
	 * 根据用户和机构获取管辖权限
	 * Create Time 2014-12-23 上午10:31:51 
	 * @param userid
	 * @param oid
	 * @return
	 */
	public UserVisit findVisitOrganByUidAndOid(int userid,String oid){
		String hql = "from UserVisit as u  where userid='" + userid + "' and visitorgan like '%" + oid + "%' order by u.id";
	    return (UserVisit) EntityManager.find(hql);
	}
}
