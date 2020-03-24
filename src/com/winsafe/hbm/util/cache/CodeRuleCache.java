package com.winsafe.hbm.util.cache;

import java.util.ArrayList;
import java.util.List;

import com.winsafe.hbm.util.cache.CacheManager;
import com.winsafe.drp.dao.CodeRule;


public class CodeRuleCache {
	private CacheManager cm;
	private String CACHE_TAG = "coderulelist";
	
	public CodeRuleCache(){
		cm = CacheManager.getInstance("CodeRule");
	}
	
	public CodeRule getCodeRuleByUcodeCrp(String ucode, int crproperty){	
		List<CodeRule> list = getAllCodeRule();
		if ( list == null ){
			return null;
		}
		for ( CodeRule d : list ){
			if ( d.getUcode().equals(ucode) && d.getCrproperty().intValue() == crproperty){
				
				return d;
			}
		}
		return null;
	}
	
	public List getCodeRuleByUcode(String ucode){
		List<CodeRule> list = getAllCodeRule();
		if ( list == null ){
			return null;
		}
		List downOrgna = new ArrayList();
		for ( CodeRule d : list ){
			if ( d.getUcode().equalsIgnoreCase(ucode)  ){	
				downOrgna.add(d);
			}			
		}
		if ( downOrgna.isEmpty() ){
			return null;
		}
		return downOrgna;
	}
	
	
	
	public void putCodeRuleList(List list){
		cm.put(CACHE_TAG, list);
	}
	
	public void removeCodeRuleList(){
		cm.remove(CACHE_TAG);
	}
	
	public List getAllCodeRule(){
		
		return (List)cm.get(CACHE_TAG);
	}
	
	
	public void modifyCodeRule(List list){
		removeCodeRuleList();
		putCodeRuleList(list);
	}
}
