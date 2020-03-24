package com.winsafe.drp.dao;

import java.util.List;

import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.pager.SimplePageInfo;

public class AppIdcodeReset {

	public List getIdcodeReset(int pagesize, String pWhereClause,
            SimplePageInfo pPgInfo)throws Exception{
		 String sql=" from IdcodeReset "+pWhereClause+" order by id desc";
		 return EntityManager.getAllByHql(sql, pPgInfo.getCurrentPageNo(),pagesize);
	}
	
	public void addIdcodeReset(IdcodeReset ir) throws Exception{		
		EntityManager.save(ir);
		
	}
	
	public void updIdcodeReset(IdcodeReset ir) throws Exception{		
		EntityManager.update(ir);
		
	}
	
	public void updIsAudit(String id, Long userid,Integer audit) throws Exception {
		String sql = "update idcode_reset set isaudit="+audit+",auditid=" + userid
				+ ",auditdate='" + DateUtil.getCurrentDateString()
				+ "' where id='" + id + "'";
		EntityManager.updateOrdelete(sql);

	}
	
	public void delIdcodeReset(String id) throws Exception{		
		String sql = " delete from idcode_reset where id='"+id+"'";
		EntityManager.updateOrdelete(sql);
		
	}	
	
	public IdcodeReset getIdcodeResetById(String id) throws Exception{
		String sql="from IdcodeReset where id='"+id+"'";
		return (IdcodeReset)EntityManager.find(sql);
	}
	
}
