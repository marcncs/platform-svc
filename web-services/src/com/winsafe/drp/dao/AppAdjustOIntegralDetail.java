package com.winsafe.drp.dao;

import java.util.List;

import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.pager.SimplePageInfo;

public class AppAdjustOIntegralDetail {

	
	public List getAdjustOIntegralDetail(int pagesize, String pWhereClause,
			SimplePageInfo pPgInfo) throws Exception {
		List pls = null;
		int targetPage = pPgInfo.getCurrentPageNo();
		String sql = " from AdjustOIntegralDetail  "
				+ pWhereClause + " order by id desc";
		pls = EntityManager.getAllByHql(sql, targetPage, pagesize);
		return pls;
	}
	

	public void addAdjustOIntegralDetail(AdjustOIntegralDetail r) throws Exception {		
		EntityManager.save(r);		
	}

	public AdjustOIntegralDetail getAdjustOIntegralDetailByID(String id) throws Exception {
		AdjustOIntegralDetail p = null;
		String sql = " from AdjustOIntegralDetail as p where p.id=" + id+"";
		p = (AdjustOIntegralDetail) EntityManager.find(sql);
		return p;
	}
	
	public List getDetailByAocid(String accid) throws Exception {		
		String sql = " from AdjustOIntegralDetail  where aoiid='" + accid+"'";
		return EntityManager.getAllByHql(sql);
	}

	public void updAdjustOIntegralDetail(AdjustOIntegralDetail p) throws Exception {		
		EntityManager.update(p);
		
	}
	
	public void delDetailByAocid(String accid)throws Exception{		
		String sql="delete from adjust_o_integral_detail where aoiid='"+accid+"'";
		EntityManager.updateOrdelete(sql);
		
	}
	
	public void delAdjustOIntegralDetail(Long id)throws Exception{		
		String sql="delete from adjust_o_integral_detail where id="+id;
		EntityManager.updateOrdelete(sql);
		
	}
	

}
