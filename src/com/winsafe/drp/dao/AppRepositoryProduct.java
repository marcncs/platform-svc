package com.winsafe.drp.dao;

import java.util.List;

import com.winsafe.drp.entity.EntityManager;

public class AppRepositoryProduct {


	
	public void addRepositoryProduct(RepositoryProduct r) throws Exception {		
		EntityManager.save(r);		
	}

	
	public RepositoryProduct getRepositoryProductByID(String id) throws Exception {
		String sql = " from RepositoryProduct as p where p.id='" + id+"'";
		return (RepositoryProduct) EntityManager.find(sql);
	}
	
	public String getRepositoryProductByPid(String pid)throws Exception{		
		String sql="select distinct rid from RepositoryProduct where productid='"+pid+"'";
		List list =  EntityManager.getAllByHql(sql);
		StringBuffer sb = new StringBuffer();
		for ( int i=0; i<list.size(); i++ ){
			String s = (String)list.get(i);
			if ( i == 0 ){
				sb.append("'"+s+"'");
			}else{
				sb.append(",").append("'"+s+"'");
			}
		}
		if ( sb.toString().equals("") ){
			return "''";
		}
		return sb.toString();
	}

	
	public void updRepositoryProduct(RepositoryProduct p) throws Exception {		
		EntityManager.update(p);
		
	}
	
	public void delRepositoryProduct(Long id)throws Exception{		
		String sql="delete from repository_product where id="+id+"";
		EntityManager.updateOrdelete(sql);		
	}
	
	public void delRepositoryProductByRid(String rid)throws Exception{		
		String sql="delete from repository_product where rid='"+rid+"'";
		EntityManager.updateOrdelete(sql);		
	}

	public List getRepositoryProductByRID(String rid)throws Exception{
		String sql="from RepositoryProduct  where rid='"+rid+"'";
		return EntityManager.getAllByHql(sql);
	}
	

}
