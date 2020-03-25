package com.winsafe.hbm.util.cache;

import java.util.ArrayList;
import java.util.List;

import com.winsafe.hbm.util.cache.CacheManager;
import com.winsafe.drp.dao.Users;
import com.winsafe.drp.dao.UsersBean;


public class UsersCache {
	private CacheManager cm;
	private String CACHE_TAG = "userslist";
	
	public UsersCache(){
		cm = CacheManager.getInstance("users");
	}
	
	public Users getUsers(int id){	
		List<Users> list = getAllUsers();
		if ( list == null ){

			return null;
		}

		for ( Users d : list ){
			if ( d.getUserid().intValue() == id ){

				return d;
			}
		}
		return null;
	}
	
	public UsersBean getUsersByID(int id){	
		Users u = getUsers(id);
		if ( u != null ){
			UsersBean ub = new UsersBean();
			ub.setUserid(u.getUserid());
			ub.setLoginname(u.getLoginname());
			ub.setRealname(u.getRealname());
			return ub;
		}
		return null;
	}
	
	public Users getUsersByLoginname(String loginname){	
		List<Users> list = getAllUsers();
		if ( list == null ){
			return null;
		}
		for ( Users d : list ){
			if ( d.getLoginname().equals(loginname) ){

				return d;
			}
		}
		return null;
	}
	
	public List getUsersByDept(int deptid){
		List<Users> list = getAllUsers();
		if ( list == null ){
			return null;
		}
		List downOrgna = new ArrayList();
		for ( Users d : list ){
			if ( d.getMakedeptid().intValue() == deptid){	
				downOrgna.add(d);
			}			
		}
		if ( downOrgna.isEmpty() ){
			return null;
		}
		return downOrgna;
	}
	
	public List getUsersByOrgan(String organid){
		List<Users> list = getAllUsers();
		if ( list == null ){
			return null;
		}
		List downOrgna = new ArrayList();
		for ( Users d : list ){
			if ( d.getMakeorganid().equals(organid) ){	
				downOrgna.add(d);
			}			
		}
		if ( downOrgna.isEmpty() ){
			return null;
		}
		return downOrgna;
	}
	
	public List getUsersByOID(String oid){
		List<Users> list = getAllUsers();
		if ( list == null ){
			return null;
		}
		List downOrgna = new ArrayList();
		for ( Users d : list ){
			if ( d.getMakeorganid().equalsIgnoreCase(oid)  ){	
				downOrgna.add(d);
			}			
		}
		if ( downOrgna.isEmpty() ){
			return null;
		}
		return downOrgna;
	}
	
	public List getIDAndLoginNameByOID2(String oid){
		List<Users> list = getAllUsers();
		if ( list == null ){
			return null;
		}
		List downOrgna = new ArrayList();
		for ( Users u : list ){
			if ( u.getMakeorganid().equalsIgnoreCase(oid) && u.getStatus() == 1 ){	
				UsersBean ub = new UsersBean();
				ub.setUserid(u.getUserid());
				ub.setLoginname(u.getLoginname());
				ub.setRealname(u.getRealname());
				downOrgna.add(ub);
			}			
		}
		if ( downOrgna.isEmpty() ){
			return null;
		}
		return downOrgna;
	}
	
	
	
	public void putUsersList(List list){
		cm.put(CACHE_TAG, list);
	}
	
	public void removeUsersList(){
		cm.remove(CACHE_TAG);
	}
	
	public List getAllUsers(){
		return (List)cm.get(CACHE_TAG);
	}
	
	
	public void modifyUsers(List list){
		removeUsersList();
		putUsersList(list);
	}
}
