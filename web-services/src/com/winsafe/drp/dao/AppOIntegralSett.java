package com.winsafe.drp.dao;

import java.util.List;

import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.pager.SimplePageInfo;

public class AppOIntegralSett {

	
	public List getOIntegralSett(int pagesize, String pWhereClause,
			SimplePageInfo pPgInfo) throws Exception {
		int targetPage = pPgInfo.getCurrentPageNo();
		String sql = " from OIntegralSett  "
				+ pWhereClause + " order by makedate desc";
		return EntityManager.getAllByHql(sql, targetPage, pagesize);
	}
	

	public void addOIntegralSett(OIntegralSett r) throws Exception {		
		EntityManager.save(r);		
	}

	public OIntegralSett getOIntegralSettByID(String id) throws Exception {
		OIntegralSett p = null;
		String sql = " from OIntegralSett as p where p.id='" + id+"'";
		p = (OIntegralSett) EntityManager.find(sql);
		return p;
	}

	public void updOIntegralSett(OIntegralSett p) throws Exception {		
		EntityManager.update(p);
		
	}
	
	public void delOIntegralSett(String id)throws Exception{
		
		String sql="delete from o_integral_sett where id='"+id+"'";
		EntityManager.updateOrdelete(sql);
		
	}
	
	public void updIsAudit(String id, Long userid,Integer audit) throws Exception {
			
			String sql = "update o_integral_sett set isaudit="+audit+",auditid=" + userid
					+ ",auditdate='" + DateUtil.getCurrentDateTime()
					+ "' where id='" + id + "'";
			EntityManager.updateOrdelete(sql);			
	}
	

}
