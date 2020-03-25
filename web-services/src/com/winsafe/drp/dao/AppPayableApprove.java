package com.winsafe.drp.dao;

import java.util.List;

import com.winsafe.drp.entity.EntityManager;

public class AppPayableApprove {

	

	public List getPayableApprove(String paid) throws Exception {
		List ls = null;
		String sql = "select wa.id,wa.pid,wa.approveid,wa.approvecontent,wa.approve,wa.approvedate,wa.actid from PayableApprove as wa where wa.pid='"
				+ paid+"'";
		ls = EntityManager.getAllByHql(sql);
		return ls;
	}

	public void delPayableApproveByWID(String paid) throws Exception {
		
		String sql = "delete from payable_approve where pid='" + paid+"'";
		EntityManager.updateOrdelete(sql);
		
	}

	public void addPayableApprove(Object o) throws Exception {
		
		EntityManager.save(o);
		
	}

	public void addApproveContent(String approvecontent, int approve,
			String approvedate, String paid,Integer actid, Long userid) throws Exception {
		
		String sql = "update payable_approve set approvecontent='"
				+ approvecontent + "', approve=" + approve + ",approvedate='"
				+ approvedate + "' where pid='" + paid
				+ "' and actid="+actid+" and approveid=" + userid;
		EntityManager.updateOrdelete(sql);
		
	}
	

	public void cancelApprove(Integer approve,Integer actid,String pid,Long userid)throws Exception{
	 
	 String sql = "update payable_approve set approve="+approve+" where pid='"+pid+"' and actid="+actid+" and approveid="+userid;
	 EntityManager.updateOrdelete(sql);
	 
	}
}
