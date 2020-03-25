package com.winsafe.hbm.util.cache;

import java.util.ArrayList;
import java.util.List;

import com.winsafe.hbm.util.cache.CacheManager;
import com.winsafe.drp.dao.Organ;


public class OrganCache {
	private CacheManager cm;
	private String CACHE_TAG = "organlist";
	
	public OrganCache(){
		cm = CacheManager.getInstance("Organ");
	}
	
	public Organ getOrgan(String id){	
		List<Organ> list = getAllOrgan();
		if ( list == null ){
			return null;
		}
		for ( Organ d : list ){
			if ( d.getId().equalsIgnoreCase(id) ){
				return d;
			}
		}
		return null;
	}
	
	public String getOcodeByParent(String strparentid){
		List<Organ> list = getAllOrgan();
		if ( list == null ){
			return null;
		}
		int length=strparentid.length()+4;
		long intacode=0;
		for ( Organ d : list ){
			if ( d.getSysid().startsWith(strparentid) && length == d.getSysid().length() ){				
				intacode=Long.valueOf(d.getSysid()).intValue()+1;
			}			
		}
		if ( intacode == 0 ){
			return null;
		}
		return String.valueOf(intacode);
	}
	
	public List getOrganToDown(String oid){
		List<Organ> list = getAllOrgan();
		if ( list == null ){
			return null;
		}
		List downOrgna = new ArrayList();
		for ( Organ d : list ){
			if ( d.getSysid().startsWith(oid)  ){	
				downOrgna.add(d);
			}			
		}
		if ( downOrgna.isEmpty() ){
			return null;
		}
		return downOrgna;
	}
	
	public List getTreeByCate(String parentid) {
		List<Organ> list = getAllOrgan();
		if ( list == null ){
			return null;
		}
		List treeOrgna = new ArrayList();
		for ( Organ d : list ){
			if ( d.getParentid().equalsIgnoreCase(parentid) ){	
				treeOrgna.add(d);
			}			
		}
		if ( treeOrgna.isEmpty() ){
			return null;
		}
		return treeOrgna;
	}
	
	public void putOrganList(List list){
		cm.put(CACHE_TAG, list);
	}
	
	public void removeOrganList(){
		cm.remove(CACHE_TAG);
	}
	
	public List getAllOrgan(){
		return (List)cm.get(CACHE_TAG);
	}
	
	
	public void modifyOrgan(List list){
		removeOrganList();
		putOrganList(list);
	}
}
