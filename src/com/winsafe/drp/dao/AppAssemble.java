package com.winsafe.drp.dao;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.pager2.PageQuery;

public class AppAssemble {

	public List getAssemble(HttpServletRequest request, int pagesize,
			String pWhereClause) throws Exception {
		String sql = " from Assemble as a "
			+ pWhereClause + " order by a.makedate desc ";
		return PageQuery.hbmQuery(request, sql, pagesize);

	}

	public void updAssemble(Assemble a)
			throws Exception {
		
		EntityManager.update(a);
		
	}

	//
	public void addAssemble(Assemble a) throws Exception {
		
		EntityManager.save(a);
		
	}

	public Assemble getAssembleByID(String id) throws Exception {
		Assemble pi = null;
		String sql = " from Assemble where id='" + id + "'";
		pi = (Assemble) EntityManager.find(sql);
		return pi;
	}
	
	public void delAssemble(String id)throws Exception{
		
		String sql="delete from Assemble where id='"+id+"'";
		EntityManager.updateOrdelete(sql);
		
	}




	
	public void updIsComplete(String id,Long userid, Integer complete) throws Exception {
		
		String sql = "update assemble set iscomplete="+complete+",completeid="+userid+",completedate='"+DateUtil.getCurrentDateTime()+"' where id ='" + id+"'";
		EntityManager.updateOrdelete(sql);
		
	}

	

	public void updIsAudit(String ppid, Integer userid,Integer audit) throws Exception {
		
		String sql = "update Assemble set isaudit="+audit+",auditid=" + userid
				+ ",auditdate='" + DateUtil.getCurrentDateString()
				+ "' where id='" + ppid + "'";
		EntityManager.updateOrdelete(sql);
		
	}

	public List<Assemble> getAssemble(String whereSql) {
		String sql = " from Assemble as a "
			+ whereSql + " order by a.makedate desc ";
		return EntityManager.getAllByHql(sql);
	}
	

}
