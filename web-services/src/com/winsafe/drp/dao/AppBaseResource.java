package com.winsafe.drp.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.winsafe.drp.entity.EntityManager;

public class AppBaseResource {

	public List getBaseResource(String tagName)throws Exception{
	    String sql = " from BaseResource as br where br.tagname='"+tagName+"' order by br.tagsubkey ";
	    return EntityManager.getAllByHql(sql);
	  }
	
	public Map<Integer, String> getBaseResourceMap(String tagName) throws Exception{
		Map<Integer, String> resultMap = new HashMap<Integer, String>();
		List<BaseResource> list = getBaseResource(tagName);
		for(BaseResource br : list){
			if(br == null) continue;
			resultMap.put(br.getTagsubkey(), br.getTagsubvalue());
		}
		return resultMap;
	}
	
	public List getBaseResourceList(String tagName)throws Exception{
	    String sql = "select br.id,br.tagname,br.tagsubkey,br.tagsubvalue from BaseResource as br where br.tagname='"+tagName+"' ";
	    return EntityManager.getAllByHql(sql);
	}
	
	public BaseResource getBaseResourceByid(long id)throws Exception{
		String sql=" from BaseResource where id="+id;
		return (BaseResource)EntityManager.find(sql);
	}
	
	public BaseResource getBaseResourceValue(String tagName, int key)throws Exception{
		String sql=" from BaseResource as br where br.tagname='"+tagName+"' and br.tagsubkey="+key+" ";
		return (BaseResource)EntityManager.find(sql);
	}
	
	public String getMaxBaseResourceByTagName(String tagname) throws Exception {
		String c = "0";
		String hql = "select max(br.tagsubkey) from BaseResource as br where br.tagname='"+tagname+"'";
		c = EntityManager.getString(hql);
		return c;
	}
	
	public void addBaseResource(Object br)throws Exception{		
		EntityManager.save(br);		
	}
	

	public void updBaseResource(BaseResource br)
	throws Exception {		
//		String sql = "update Base_Resource set TagSubValue='"+br.getTagsubvalue()+ "' where TagName='" + br.getTagname() + "' and TagSubKey="+br.getTagsubkey();
//		EntityManager.updateOrdelete(sql);		
		EntityManager.update(br);
	}
	
	public List getIsUseBaseResource(String tagName)throws Exception{
	    String sql = " from BaseResource where isuse=1 and tagname='"+tagName+"' order by tagsubkey ";
	    return EntityManager.getAllByHql(sql);
	}
	
	public List getAllBaseResource()throws Exception{
	    String sql = " from BaseResource order by tagname,tagsubkey ";
	    return EntityManager.getAllByHql(sql);
	}
	
	
	public BaseResource getBaseResourceKey(String tagName, String value)throws Exception{
		String sql=" from BaseResource as br where br.tagname='"+tagName+"' and br.tagsubvalue='"+value+"' ";
		return (BaseResource)EntityManager.find(sql);
	}
	
	public BaseResource getBaseResourceValueWithLock(String tagName, int key)throws Exception{
		String sql=" from BaseResource as br where br.tagname='"+tagName+"' and br.tagsubkey="+key+" ";
		return (BaseResource)EntityManager.findWithLock(sql, "br");
	}
	
}
