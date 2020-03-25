package com.winsafe.drp.dao;

import java.util.List;

import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.pager.SimplePageInfo;

public class AppAdjustOIntegral {

	
	public List getAdjustOIntegral(int pagesize, String pWhereClause,
			SimplePageInfo pPgInfo) throws Exception {
		List pls = null;
		int targetPage = pPgInfo.getCurrentPageNo();
		String sql = " from AdjustOIntegral  "
				+ pWhereClause + " order by id desc";
		pls = EntityManager.getAllByHql(sql, targetPage, pagesize);
		return pls;
	}
	

	public void addAdjustOIntegral(AdjustOIntegral r) throws Exception {		
		EntityManager.save(r);		
	}

	public AdjustOIntegral getAdjustOIntegralByID(String id) throws Exception {
		AdjustOIntegral p = null;
		String sql = " from AdjustOIntegral as p where p.id='" + id+"'";
		p = (AdjustOIntegral) EntityManager.find(sql);
		return p;
	}

	public void updAdjustOIntegral(AdjustOIntegral p) throws Exception {		
		EntityManager.update(p);
		
	}
	
	public void delAdjustOIntegral(String id)throws Exception{
		
		String sql="delete from adjust_o_integral where id='"+id+"'";
		EntityManager.updateOrdelete(sql);
		
	}
	
	public void updIsAudit(String id, Long userid,Integer audit) throws Exception {
			
			String sql = "update adjust_o_integral set isaudit="+audit+",auditid=" + userid
					+ ",auditdate='" + DateUtil.getCurrentDateTime()
					+ "' where id='" + id + "'";
			EntityManager.updateOrdelete(sql);			
	}
	

}
