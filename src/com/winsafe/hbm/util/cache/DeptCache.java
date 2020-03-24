package com.winsafe.hbm.util.cache;

import java.util.ArrayList;
import java.util.List;

import com.winsafe.hbm.util.cache.CacheManager;
import com.winsafe.drp.dao.Dept;


public class DeptCache {
	private CacheManager cm;
	private String CACHE_TAG = "deptlist";
	
	public DeptCache(){
		cm = CacheManager.getInstance("Dept");
	}
	
	public Dept getDept(int id){	
		List<Dept> list = getAllDept();
		if ( list == null ){
			return null;
		}
		for ( Dept d : list ){
			if ( d.getId().intValue() == id ){
				return d;
			}
		}
		return null;
	}
	
	public List getDeptByOID(String oid){
		List<Dept> list = getAllDept();
		if ( list == null ){
			return null;
		}
		List downOrgna = new ArrayList();
		for ( Dept d : list ){
			if ( d.getOid().equalsIgnoreCase(oid)  ){	
				downOrgna.add(d);
			}			
		}
		if ( downOrgna.isEmpty() ){
			return null;
		}
		return downOrgna;
	}
	
	
	
	public void putDeptList(List list){
		cm.put(CACHE_TAG, list);
	}
	
	public void removeDeptList(){
		cm.remove(CACHE_TAG);
	}
	
	public List getAllDept(){
		return (List)cm.get(CACHE_TAG);
	}
	
	
	public void modifyDept(List list){
		removeDeptList();
		putDeptList(list);
	}
}
