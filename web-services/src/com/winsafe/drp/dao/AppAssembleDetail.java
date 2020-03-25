package com.winsafe.drp.dao;

import java.util.List;

import com.winsafe.drp.entity.EntityManager;

public class AppAssembleDetail {

	public void addAssembleDetail(AssembleDetail ild)throws Exception{
		
		EntityManager.save(ild);
		
	}
	
	public AssembleDetail getAssembleDetailByID(Long id)throws Exception{
		String sql=" from AssembleDetail as wd where wd.id="+id+"";
		return (AssembleDetail)EntityManager.find(sql);
	}
	
	public List getAssembleDetailByAid(String aid)throws Exception{
		String sql=" from AssembleDetail as wd where wd.aid='"+aid+"'";
		return EntityManager.getAllByHql(sql);
	}
	

	public void delAssembleDetailByAid(String id)throws Exception{
		
		String sql="delete from assemble_detail where aid='"+id+"'";
		EntityManager.updateOrdelete(sql);
		
	}
}
