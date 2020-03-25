package com.winsafe.drp.dao;

import java.util.List;

import com.winsafe.drp.entity.EntityManager;

public class AppReceivableApprove {

	

	public List getReceivableApprove(String rid) throws Exception {
		List ls = null;
		String sql = "select wa.id,wa.rid,wa.approveid,wa.approvecontent,wa.approve,wa.approvedate,wa.actid from ReceivableApprove as wa where wa.rid='"
				+ rid+"'";
		ls = EntityManager.getAllByHql(sql);
		return ls;
	}

	public void delReceivableApproveByWID(String wid) throws Exception {
		
		String sql = "delete from receivable_approve where rid='" + wid+"'";
		EntityManager.updateOrdelete(sql);
		
	}

	public void addReceivableApprove(Object o) throws Exception {
		
		EntityManager.save(o);
		
	}

	public void addApproveContent(String approvecontent, int approve,
			String approvedate, String rid,Integer actid, Long userid) throws Exception {
		
		String sql = "update receivable_approve set approvecontent='"
				+ approvecontent + "', approve=" + approve + ",approvedate='"
				+ approvedate + "' where rid='" + rid
				+ "' and actid="+actid+" and approveid=" + userid;
		EntityManager.updateOrdelete(sql);
		
	}
	

	public void cancelApprove(Integer approve,Integer actid,String rid,Long userid)throws Exception{
	 
	 String sql = "update receivable_approve set approve="+approve+" where rid='"+rid+"' and actid="+actid+" and approveid="+userid;
	 EntityManager.updateOrdelete(sql);
	 
	}
}
