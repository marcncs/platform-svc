package com.winsafe.drp.dao;

import java.util.List;

import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.pager.SimplePageInfo;

public class AppTeardown {

	public List getTeardown(int pagesize, String pWhereClause,
			SimplePageInfo pPgInfo) throws Exception {
		List sb = null;
		int targetPage = pPgInfo.getCurrentPageNo();
		String sql = " from Teardown as a "
			+ pWhereClause + " order by a.makedate desc ";
		sb = EntityManager.getAllByHql(sql, targetPage, pagesize);
		return sb;
	}

	public void updTeardown(Teardown a)
			throws Exception {
		
		EntityManager.update(a);
		
	}

	//
	public void addTeardown(Teardown a) throws Exception {
		
		EntityManager.save(a);
		
	}

	public Teardown getTeardownByID(String id) throws Exception {
		Teardown pi = null;
		String sql = " from Teardown where id='" + id + "'";
		pi = (Teardown) EntityManager.find(sql);
		return pi;
	}
	
	public void delTeardown(String id)throws Exception{
		
		String sql="delete from Teardown where id='"+id+"'";
		EntityManager.updateOrdelete(sql);
		
	}

	
	public void updIsComplete(String id,Long userid, Integer complete) throws Exception {
		
		String sql = "update teardown set iscomplete="+complete+",completeid="+userid+",completedate='"+DateUtil.getCurrentDateTime()+"' where id ='" + id+"'";
		EntityManager.updateOrdelete(sql);
		
	}

	

	public void updIsAudit(String ppid, Long userid,Integer audit) throws Exception {
		
		String sql = "update teardown set isaudit="+audit+",auditid=" + userid
				+ ",auditdate='" + DateUtil.getCurrentDateString()
				+ "' where id='" + ppid + "'";
		EntityManager.updateOrdelete(sql);
		
	}
	

}
