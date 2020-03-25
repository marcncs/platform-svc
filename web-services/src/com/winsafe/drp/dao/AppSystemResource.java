package com.winsafe.drp.dao;

import java.util.List;

import com.winsafe.common.util.StringUtil;
import com.winsafe.drp.entity.EntityManager;

public class AppSystemResource {

	public List getSystemResource(String tagName)throws Exception{
	    String sql = " from SystemResource as br where br.tagname='"+tagName+"' order by br.id ";
	    return EntityManager.getAllByHql(sql);
	  }
	
	public List getSystemResourceList(String tagName)throws Exception{
	    String sql = "select br.id,br.tagname,br.tagkey,br.tagvalue from SystemResource as br where br.tagname='"+tagName+"' ";
	    return EntityManager.getAllByHql(sql);
	}
	
	public SystemResource getSystemResourceByid(long id)throws Exception{
		String sql=" from SystemResource where id="+id;
		return (SystemResource)EntityManager.find(sql);
	}
	
	public SystemResource getSystemResourceValue(String tagName, String key)throws Exception{
		String sql=" from SystemResource as br where br.tagname='"+tagName+"' and br.tagkey='"+key+"' ";
		return (SystemResource)EntityManager.find(sql);
	}
	
	public List<SystemResource> querySystemResourceValue(String tagName, String key)throws Exception{
		String sql=" from SystemResource as br where br.tagname='"+tagName+"' and br.tagkey='"+key+"' order by id";
		return EntityManager.getAllByHql(sql);
	}
	
	public String getMaxSystemResourceByTagName(String tagname) throws Exception {
		String c = "0";
		String hql = "select max(br.tagkey) from SystemResource as br where br.tagname='"+tagname+"'";
		c = EntityManager.getString(hql);
		return c;
	}
	
	public void addSystemResource(Object br)throws Exception{		
		EntityManager.save(br);		
	}
	

	public void updSystemResource(SystemResource br)
	throws Exception {		

		EntityManager.update(br);
	}
	
	public List getAllSystemResource()throws Exception{
	    String sql = " from SystemResource order by id ";
	    return EntityManager.getAllByHql(sql);
	}
	
	public boolean isTrue(String tagName, String key) throws Exception{
		SystemResource sr=getSystemResourceValue(tagName, key);
		if(sr!=null){
			String str=sr.getTagvalue();
			if(StringUtil.isEmpty(str)){
				return false;
			}else{
				int i=0;
				try{
					i=Integer.valueOf(str);
				}catch(Exception e){
					return false;
				}
				if(i==1){
					return true;
				}else{
					return false;
				}
			}
		}else{
			return false;
		}
	}
}
