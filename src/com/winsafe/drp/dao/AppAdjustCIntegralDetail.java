package com.winsafe.drp.dao;

import java.util.List;

import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.pager.SimplePageInfo;

public class AppAdjustCIntegralDetail {

	
	public List getAdjustCIntegralDetail(int pagesize, String pWhereClause,
			SimplePageInfo pPgInfo) throws Exception {
		List pls = null;
		int targetPage = pPgInfo.getCurrentPageNo();
		String sql = " from AdjustCIntegralDetail  "
				+ pWhereClause + " order by id desc";
		pls = EntityManager.getAllByHql(sql, targetPage, pagesize);
		return pls;
	}
	

	public void addAdjustCIntegralDetail(AdjustCIntegralDetail r) throws Exception {		
		EntityManager.save(r);		
	}

	public AdjustCIntegralDetail getAdjustCIntegralDetailByID(String id) throws Exception {
		AdjustCIntegralDetail p = null;
		String sql = " from AdjustCIntegralDetail as p where p.id=" + id+"";
		p = (AdjustCIntegralDetail) EntityManager.find(sql);
		return p;
	}
	
	public List getDetailByAccid(String accid) throws Exception {		
		String sql = " from AdjustCIntegralDetail  where aciid='" + accid+"'";
		return EntityManager.getAllByHql(sql);
	}

	public void updAdjustCIntegralDetail(AdjustCIntegralDetail p) throws Exception {		
		EntityManager.update(p);
		
	}
	
	public void delDetailByAccid(String accid)throws Exception{		
		String sql="delete from adjust_c_integral_detail where aciid='"+accid+"'";
		EntityManager.updateOrdelete(sql);
		
	}
	
	public void delAdjustCIntegralDetail(Long id)throws Exception{		
		String sql="delete from adjust_c_integral_detail where id="+id;
		EntityManager.updateOrdelete(sql);
		
	}
	

}
