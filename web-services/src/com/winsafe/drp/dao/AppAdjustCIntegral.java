package com.winsafe.drp.dao;

import java.util.List;

import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.pager.SimplePageInfo;

public class AppAdjustCIntegral {

	
	public List getAdjustCIntegral(int pagesize, String pWhereClause,
			SimplePageInfo pPgInfo) throws Exception {
		List pls = null;
		int targetPage = pPgInfo.getCurrentPageNo();
		String sql = " from AdjustCIntegral  "
				+ pWhereClause + " order by id desc";
		pls = EntityManager.getAllByHql(sql, targetPage, pagesize);
		return pls;
	}
	

	public void addAdjustCIntegral(AdjustCIntegral r) throws Exception {		
		EntityManager.save(r);		
	}

	public AdjustCIntegral getAdjustCIntegralByID(String id) throws Exception {
		AdjustCIntegral p = null;
		String sql = " from AdjustCIntegral as p where p.id='" + id+"'";
		p = (AdjustCIntegral) EntityManager.find(sql);
		return p;
	}

	public void updAdjustCIntegral(AdjustCIntegral p) throws Exception {		
		EntityManager.update(p);
		
	}
	
	public void delAdjustCIntegral(String id)throws Exception{
		
		String sql="delete from adjust_c_integral where id='"+id+"'";
		EntityManager.updateOrdelete(sql);
		
	}
	
	public void updIsAudit(String id, Long userid,Integer audit) throws Exception {
			
			String sql = "update adjust_c_integral set isaudit="+audit+",auditid=" + userid
					+ ",auditdate='" + DateUtil.getCurrentDateTime()
					+ "' where id='" + id + "'";
			EntityManager.updateOrdelete(sql);			
	}
	

}
