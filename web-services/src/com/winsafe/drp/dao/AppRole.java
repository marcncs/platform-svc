/**
 * 
 */
package com.winsafe.drp.dao;



import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.HibernateException;

import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.pager.SimplePageInfo;
import com.winsafe.hbm.util.pager2.PageQuery;

/**
 * @author alex
 *
 */
public class AppRole {

	public  boolean hasPower(int adminId,String menuUrl){
		//System.out.println("run the haspower");
		String sql="select m.id,m.url,m.menuname from Menu as m,RoleMenu as rm,AdminRole as ar where "
			+ "ar.adminid=" + adminId + " and m.url='" + menuUrl +  "' and ar.roleid=rm.roleid  and rm.menuid=m.id and " +
					"ar.ispopedom=1 and rm.ispopedom=1 ";
		
		try {
			Object oo=EntityManager.find(sql);
            
            if(oo !=null){
                return true;
            }else{
            	return false;
            }
          }
          catch (HibernateException ex) {
        	
            ex.printStackTrace();
            return false;
          }
//		List list=EntityManager.getAllByHql(sql);
//		System.out.println("the pwer size is:" + list.size());
//		for(int i=0;i<list.size();i++){
//			Object [] o=(Object[])list.get(i);
//			System.out.println("the mneu is:" + o[1].toString());
//			if(menuUrl.equals(o[1].toString()) || menuUrl==o[1].toString()){
//				return true;
//			}
//		}
//		return false;
	}

	public boolean isFiltePath(String path){
		String sql="from Menu as m where m.url='" + path + "'";
		List list=EntityManager.getAllByHql(sql);
		if(list.size()==0 || list==null){
			return false;
		}else{
			return true;
		}
	}

	public Menu getMenuById(int id) throws Exception{
		String sql="from Menu where id="+id;
		return (Menu)EntityManager.find(sql);
	}

	public RoleMenu getRoleMenuById(int id) throws Exception{
		String sql="from RoleMenu where id="+id;
		return (RoleMenu)EntityManager.find(sql);
	}
	
	public RoleMenu getRoleMenuByOIdRid(int oid, int rid) throws Exception{
		String sql="from RoleMenu where operateid="+oid+" and roleid="+rid;
		return (RoleMenu)EntityManager.find(sql);
	}
	

	public Menu getMenuByRmid(int id) throws Exception {
		String sql="from Menu where id=(select rm.menuid from RoleMenu as rm where rm.id="
			+id + ")";
		return (Menu)EntityManager.find(sql);
	}
	
	public void delRoleById(int id) throws Exception{
		
		String sql="delete from Role where id=" + id;
		EntityManager.updateOrdelete(sql);
		
	}

	public void delUserRoleByUserid(int userid)throws Exception{		
		String sql="delete from user_role where userid="+ userid;
		EntityManager.updateOrdelete(sql);
		
	}

	public void delWareHouseByUserid(int userid)throws Exception{		
		String sql="delete from warehouse_visit where userid="+ userid;
		EntityManager.updateOrdelete(sql);
		
	}
	
	public void delRMbyRid(int id)throws Exception{
		
		String sql="delete from Role_Menu where roleid=" + id;
		EntityManager.updateOrdelete(sql);
		
	}
	
	public void delRMbyRidOid(int rid, int oid)throws Exception{			
			String sql="delete from Role_Menu where roleid=" + rid+" and operateid="+oid;
			EntityManager.updateOrdelete(sql);			
	}
	
	public void delRoleLeftMenuByRidLmid(int rid, int lmid)throws Exception{			
		String sql="delete from Role_left_Menu where roleid=" + rid+" and lmid="+lmid;
		EntityManager.updateOrdelete(sql);			
}
	

	public void delMenuByMid(int id) throws Exception{		
		String sql="delete from Menu where id=" + id ;
		EntityManager.updateOrdelete(sql);
		
	}

	public void delRMByMid(int id) throws Exception{		
		String sql="delete from Role_Menu where Menuid=" + id ;
		EntityManager.updateOrdelete(sql);
		
	}
		

	public void delMenuByRmid(int id) throws Exception{
		
		String sql="delete from Menu where id=" +
				"(select menuid from Role_Menu where id=" + id + ")";
		EntityManager.updateOrdelete(sql);
		
	}

	public void delRMByRmid(int id) throws Exception{
		
		String sql="delete a, b from Role_Menu a, Role_Menu b " + 
  " where a.MenuID = b.MenuID and a.id="+id;
		System.out.println("the sql is:" + sql);
		EntityManager.updateOrdelete(sql);
		
	}

	public void delRMByMenuid(int menuid) throws Exception{
		
		String sql="delete from Role_Menu where menuid=" + menuid;
				
		EntityManager.updateOrdelete(sql);
		
	}

	public Role getRoleById(int id) throws Exception{
		String sql="from Role where id="+id;
		return (Role)EntityManager.find(sql);
	}

	public Users getUserById(int id) throws Exception{
		String sql="from Users where id="+id;
		return (Users)EntityManager.find(sql);
	}

	public void changeARispopedom(int arid)throws Exception{

		String sql="update admin_role set ispopedom=(1-ispopedom) where id="+arid;
		EntityManager.updateOrdelete(sql);

	}

	public void changeRMispopedom(int operateid,int roleid)throws Exception{

		String sql="update role_menu set ispopedom=(1-ispopedom) " +
				"where roleid=" + roleid + " and  menuid in (select id from menu where operateid=" + operateid + ")";
		//System.out.println("the sql is:" + sql);
		EntityManager.updateOrdelete(sql);

	}

	public void updateAR(int arid)throws Exception{

		String sql="update user_role set ispopedom=1 where id="+arid;
		EntityManager.updateOrdelete(sql);

	}

	public void updateRM(int rmid)throws Exception{

		String sql="update role_menu set ispopedom=1 where id="+rmid;
		EntityManager.updateOrdelete(sql);

	}

	public void resetARByUserid(int userid) throws Exception{
		String sql="update user_role set ispopedom=0 where  userid="+userid;
		if ( userid == 1){
			sql="update user_role set ispopedom=0 where roleid!=1 and userid="+userid;
		}
		EntityManager.updateOrdelete(sql);

	}
	public void resetARByRoleid(int roleid) throws Exception{
		String sql="update user_role set ispopedom=0 where roleid="+roleid;
		if ( roleid == 1 ){
			sql="update user_role set ispopedom=0 where userid!=1 and roleid="+roleid;
		}		
		EntityManager.updateOrdelete(sql);
	}
	

	public void resetRM(int roleid) throws Exception{
		String sql="update role_menu set ispopedom=0 where roleid="+roleid;
		EntityManager.updateOrdelete(sql);
	}

	public List getRolePageList(int pagesize, String pWhereClause,
            SimplePageInfo pPgInfo){
	 	int targetPage = pPgInfo.getCurrentPageNo();
	 	String sql =
		 		"from Role " +
   	pWhereClause + "";
	 	return EntityManager.getAllByHql(sql, targetPage, pagesize);
	}
	
	public List getAdminPageList(int pagesize, String pWhereClause,
            SimplePageInfo pPgInfo){
	 	int targetPage = pPgInfo.getCurrentPageNo();
	 	String sql =
		 		"from Admin " +
   	pWhereClause + "";
	 	return EntityManager.getAllByHql(sql, targetPage, pagesize);
	}
	
	public List getRoleByAdminid(int pagesize,String pWhereClause,SimplePageInfo pPgInfo){
	 	int targetPage = pPgInfo.getCurrentPageNo();
	 	String sql =
		 		"select r.id,r.rolename,ar.ispopedom,ar.id from Role as r,AdminRole as ar " +
   	pWhereClause + "";
	 	return EntityManager.getAllByHql(sql, targetPage, pagesize);
	}
	

	public List getRoleByUserid(int userid){
	 	String sql =
		 		"select r.id,r.rolename,ur.ispopedom,ur.id,r.describes from Role as r,UserRole as ur" +
		 		" where r.id=ur.roleid and ur.userid=" + userid+" ";
	 	return EntityManager.getAllByHql(sql);
	}
	
	public List<Role> getRolesByUserid(int userid){
	 	String sql =
		 		"select r from Role as r,UserRole as ur" +
		 		" where r.id=ur.roleid and ur.userid=" + userid+" and ur.ispopedom=1 ";
	 	return EntityManager.getAllByHql(sql);
	}
	
	public List getRoleByParent(int userid,int parentUserId){
	 	String sql =
		 		"select r.id,r.rolename,ur.ispopedom,ur.id,r.describes from Role as r,UserRole as ur" +
		 		" where r.id=ur.roleid and ur.userid=" + userid+ " ";
		if(parentUserId != 1){
			sql += " and ur.roleid in (select roleid from UserRole where ispopedom=1 and userid=" + parentUserId + " ) ";
		}
	 	return EntityManager.getAllByHql(sql);
	}
	
	public List getUserRoleByRoleid(int roleid){
	 	String sql =
		 		"select r.userid,r.realname,ur.ispopedom,ur.id from Users as r,UserRole as ur " +
		 		"where r.userid=ur.userid and ur.roleid="+roleid+" order by r.userid " ;
	 	return EntityManager.getAllByHql(sql);
	}
	

	public List getMenus(int pagesize,String pWhereClause,SimplePageInfo pPgInfo){
	 	int targetPage = pPgInfo.getCurrentPageNo();
	 	String sql =
		 		"from Menu as m " +
   	pWhereClause + " ";
	 	//System.out.println("the sql is:" + sql);
	 	return EntityManager.getAllByHql(sql, targetPage, pagesize);
	}

	public List getMenuByRoleid(int pagesize,String pWhereClause,SimplePageInfo pPgInfo){
		List als = null;
	 	int targetPage = pPgInfo.getCurrentPageNo();
	 	String sql =
		 		"select m.id,m.menuname,rm.ispopedom,rm.id,m.moduleID,m.url from Menu as m,RoleMenu as rm " +
   	pWhereClause + " ";
	 	//System.out.println("the sql is:" + sql);
	 	als = EntityManager.getAllByHql(sql, targetPage, pagesize);
	 	return als;
	}
	
	public List getMenuByRidMid(int roleid,Integer moduleid){
		List als = new ArrayList();
		ResultSet rs = null;
//	 	String sql =
//		 		"select rm.ispopedom,o.operatename,o.id  from Operate as o,Menu as m,RoleMenu as rm " +
//		 		" where m.id=rm.menuid and m.operateid=o.id and  rm.roleid="+roleid + " and o.moduleid=" + moduleid + " group by o.id,rm.ispopedom,o.operatename  " ;
	 	//System.out.println("----the sql module is:" + sql);
		String sql = "select c.roleid,o.operatename,o.id from operate o left join "+
		" (select r.roleid, r.operateid from role_menu r where r.roleid="+roleid+") c on o.id=c.operateid "+
		" where o.moduleid="+moduleid;
		try {
			rs = EntityManager.query2(sql);
			while ( rs.next() ){
				OperateForm of = new OperateForm();
				long rid = rs.getLong(1);
				if ( rid > 0){
					of.setIspd(1);
				}else{
					of.setIspd(0);
				}				
				of.setOperatename(rs.getString(2));				 
				of.setId(rs.getLong(3));
				als.add(of);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally{
			if ( rs != null ){
				try {
					rs.close();
				} catch (SQLException e) {					
					e.printStackTrace();
				}
			}
		}		
	 	
	 	return als;
	}
	

	
	
	public List getModuleId(int roleid){
	 	String sql =
		 		"select m.id,m.menuname,rm.ispopedom,rm.id,m.moduleid,m.url from Menu as m,RoleMenu as rm " +
		 		" where m.id=rm.menuid and rm.roleid="+roleid + " group by m.moduleid" ;
	 	return EntityManager.getAllByHql(sql);	
	}

	public List getMenuByRoleid(int roleid){
	 	String sql ="select m.id,m.menuname,rm.ispopedom,rm.id,m.moduleid,m.url from Menu as m,RoleMenu as rm where m.id=rm.menuid and rm.roleid="+roleid ;
	 	return EntityManager.getAllByHql(sql);
	}

	public void modifyMenu(Object menu) throws Exception {
		EntityManager.update(menu);
	}
	
	public void addMenu(Object menu) throws Exception{		
		EntityManager.save(menu);		
	}
	
	public void addRole(Object role) throws Exception{		
		EntityManager.save(role);		
	}
	 
	public void updRole(Object role) throws Exception {
		EntityManager.update(role);
	}
	
	public List getRoleList() throws Exception{
		String hql="from Role";
		return EntityManager.getAllByHql(hql);
	}
	
	public List getRole(HttpServletRequest request, int pagesize,String pWhereClause) throws Exception{
		String hql=" from Role as r "+pWhereClause;
		return PageQuery.hbmQuery(request, hql, pagesize);
	}
	

	public List getMenuList() throws Exception{
		String hql="from Menu";
		return EntityManager.getAllByHql(hql);
	}
	
	public List getUserList() throws Exception{
		String hql="from Users";
		return EntityManager.getAllByHql(hql);
	}
	
	public void addUserRole(Object ar) throws Exception {		
		EntityManager.save(ar);		
	}
	
	public void addRoleMenu(Object rm) throws Exception {		
		EntityManager.save(rm);		
	}
	
	public UserRole getRoleIDByID(int id) throws Exception{
		String hql="from UserRole where id="+id;
		return (UserRole)EntityManager.find(hql);
	}
	
	
	
	
	public void DelRoleMenu(int roleid)throws Exception{		
		String sql = "delete role_menu where roleid="+roleid;
		EntityManager.updateOrdelete(sql);		
	}
	
	public void DelRoleLeftMenu(int roleid)throws Exception{		
		String sql = "delete role_left_menu where roleid="+roleid;
		EntityManager.updateOrdelete(sql);		
	}
	
	
	public void DelUserRole(int roleid)throws Exception{		
		String sql="delete user_role where roleid="+roleid;
		EntityManager.updateOrdelete(sql);		
	}
	
	
	public void DelRole(int roleid)throws Exception{		
		String sql="delete from role where id ="+roleid;
		EntityManager.updateOrdelete(sql);		
	}
	
	public boolean isDispatchUser(int roleid){
		String sql="from UserRole where roleid=" + roleid + " and ispopedom=1";
		List list=EntityManager.getAllByHql(sql);
		if(list.size()==0 || list==null){
			return false;
		}else{
			return true;
		}
	}
	
	public void addRoleMenuNoExists(RoleMenu rm) throws Exception{
		String sql="insert into role_menu(roleid,operateid) select "+rm.getRoleid()+","+rm.getOperateid()+" from dual where not exists (select id from role_menu where roleid="+rm.getRoleid()+" and operateid="+rm.getOperateid()+")";
		EntityManager.updateOrdelete(sql);
	}
	
	public void addRoleLeftMenuNoExists(RoleLeftMenu rm) throws Exception{
		String sql="insert into role_left_menu(roleid,lmid) select "+rm.getRoleid()+","+rm.getLmid()+" from dual where not exists (select id from role_left_menu where roleid="+rm.getRoleid()+" and lmid="+rm.getLmid()+")";
		EntityManager.updateOrdelete(sql);
	}
	
	public List getLeftMenuByUserid(int userid) throws Exception{
		String hql="select distinct lm "+
			"from UserRole ur, RoleLeftMenu rlm, LeftMenu lm "+
			"where ur.roleid=rlm.roleid and rlm.lmid=lm.id "+
			"and ur.ispopedom=1 and ur.userid="+userid+" order by lm.sort";
		return EntityManager.getAllByHql(hql);
	}
	
	
	
	public void copyUserRole(int srcRoleid, int tagRoleid) throws Exception {		
		String sql = "insert into user_role(userid,roleid,ispopedom) "+
		 "select ur.userid,"+tagRoleid+",ur.ispopedom from user_role ur "+
		 "where ur.roleid="+srcRoleid+"";
		EntityManager.updateOrdelete(sql);		
	}
	
	public void copyRoleMenu(int srcRoleid, int tagRoleid) throws Exception {		
		String sql = "insert into role_menu(roleid,operateid) "+
		 "select "+tagRoleid+",rm.operateid from role_menu rm "+
		 "where rm.roleid="+srcRoleid+"";
		EntityManager.updateOrdelete(sql);		
	}
	
	public void copyRoleLeftMenu(int srcRoleid, int tagRoleid) throws Exception {		
		String sql = "insert into role_left_menu(roleid,lmid) "+
		 "select "+tagRoleid+",rm.lmid from role_left_menu rm "+
		 "where rm.roleid="+srcRoleid+"";
		EntityManager.updateOrdelete(sql);		
	}
	
	public List getUserByRoleInAdmin(){
	 	String sql ="select ur.userid from UserRole as ur where ur.roleid=10005 and ur.ispopedom=1" ;
	 	return EntityManager.getAllByHql(sql);
	}
	public List getUserRoleByUserid(Integer userid) {
		String sql =" from UserRole as ur where ur.userid="+userid  +" and ur.ispopedom=1 " ;
		return EntityManager.getAllByHql(sql);
		
	}

	public List getRoleByUserId(int userid) {
		String sql =
	 		"select r.id,r.rolename,ur.ispopedom,ur.id,r.describes from Role as r,UserRole as ur" +
	 		" where r.id=ur.roleid and ur.userid=" + userid+ " ";
		return EntityManager.getAllByHql(sql);
	}
	
	public Map<Integer,String> getAllRoleNameMap() throws Exception {
		List<Role> roleList = getRoleList();
		Map<Integer,String> map = new HashMap<Integer, String>();
		for(Role role : roleList) {
			map.put(role.getId(), role.getRolename());
		}
		return map;
	}

	public boolean hasRole(int userId, String roleName) {
		String sql = "select count(*)from USER_ROLE ur join ROLE r on r.ID = UR.ROLEID and UR.ISPOPEDOM=1 and UR.USERID="+userId+" and r.ROLENAME='"+roleName+"'";
		return EntityManager.getRecordCountBySql(sql) > 0;
	}

	public List<String> getProductRoleNames(Integer userId, String names) {
		String hql ="select r.rolename from UserRole ur ,Role r where r.id = ur.roleid and ur.ispopedom=1 and ur.userid="+userId+" and r.rolename in ("+names+")";
		return EntityManager.getAllByHql(hql); 
	}
	
	public List<String> getRoleNames(Integer userId) {
		String hql ="select r.rolename from UserRole ur ,Role r where r.id = ur.roleid and ur.ispopedom=1 and ur.userid="+userId;
		return EntityManager.getAllByHql(hql); 
	}
}
