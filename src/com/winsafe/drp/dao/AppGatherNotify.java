package com.winsafe.drp.dao;

import java.util.List;

import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.pager.SimplePageInfo;

public class AppGatherNotify {

	public List getGatherNotify(int pagesize, String pWhereClause,
			SimplePageInfo pPgInfo) throws Exception {
		List pls = null;
		int targetPage = pPgInfo.getCurrentPageNo();
		String sql = "from GatherNotify as dp " + pWhereClause
				+ " order by dp.makedate desc";
		pls = EntityManager.getAllByHql(sql, targetPage, pagesize);
		return pls;
	}

	public void addGatherNotify(GatherNotify dpd) throws Exception {
		
		EntityManager.save(dpd);
		
	}

	public void updGatherNotify(GatherNotify dpd) throws Exception {
		
		EntityManager.update(dpd);
		
	}

	public void delGatherNotify(Long id) throws Exception {
		
		String sql = "delete from gather_notify where id=" + id + "";
		EntityManager.updateOrdelete(sql);
		
	}

	public GatherNotify getGatherNotifyByID(Long id) throws Exception {
		GatherNotify dp = null;
		String sql = " from GatherNotify where id=" + id+"";
		dp = (GatherNotify) EntityManager.find(sql);
		return dp;
	}

	
	public void updIsEndcase(Long id, Long userid, Integer audit)
			throws Exception {
		
		String sql = "update gather_notify set isendcase=" + audit + ",endcaseid="
				+ userid + ",endcasedate='" + DateUtil.getCurrentDateString()
				+ "' where id=" + id + "";
		EntityManager.updateOrdelete(sql);
		
	}
	

}
