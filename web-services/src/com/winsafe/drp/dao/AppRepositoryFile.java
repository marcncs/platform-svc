package com.winsafe.drp.dao;

import java.util.List;

import com.winsafe.drp.entity.EntityManager;

public class AppRepositoryFile {


	
	public void addRepositoryFile(RepositoryFile r) throws Exception {		
		EntityManager.save(r);		
	}

	
	public RepositoryFile getRepositoryFileByID(String id) throws Exception {
		String sql = " from RepositoryFile as p where p.id='" + id+"'";
		return (RepositoryFile) EntityManager.find(sql);
	}

	
	public void updRepositoryFile(RepositoryFile p) throws Exception {		
		EntityManager.update(p);
		
	}
	
	public void delRepositoryFile(Long id)throws Exception{		
		String sql="delete from Repository_File where id="+id+"";
		EntityManager.updateOrdelete(sql);		
	}
	
	public void delRepositoryFileByRid(String rid)throws Exception{		
		String sql="delete from Repository_File where rid='"+rid+"'";
		EntityManager.updateOrdelete(sql);		
	}

	public List getRepositoryFileByRID(String rid)throws Exception{
		String sql="from RepositoryFile  where rid='"+rid+"'";
		return EntityManager.getAllByHql(sql);
	}
	

}
