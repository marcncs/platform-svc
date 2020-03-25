package com.winsafe.drp.dao;

import java.util.List;

import com.winsafe.drp.entity.EntityManager;

public class AppIdcodeResetDetail {

	
	public void addIdcodeResetDetail(IdcodeResetDetail ird) throws Exception{		
		EntityManager.save(ird);
		
	}
	
	public void updIdcodeResetDetail(IdcodeResetDetail ird) throws Exception{		
		EntityManager.update(ird);
		
	}
	
	
	public void delIdcodeResetDetailByIrid(String irid) throws Exception{		
		String sql = " delete from idcode_reset_detail where irid='"+irid+"'";
		EntityManager.updateOrdelete(sql);
		
	}	
	
	public IdcodeResetDetail getIdcodeResetDetailById(Long id) throws Exception{
		String sql="from IdcodeResetDetail where id="+id+"";
		return (IdcodeResetDetail)EntityManager.find(sql);
	}
	
	public List getIdcodeResetDetailByIrid(String irid) throws Exception{
		String sql="from IdcodeResetDetail where irid='"+irid+"'";
		return EntityManager.getAllByHql(sql);
	}
	
}
