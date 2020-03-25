package com.winsafe.drp.dao;

import java.util.List;

import com.winsafe.drp.entity.EntityManager;

public class AppWithdrawApprove {

	

	public List getWithdrawApprove(String wid) throws Exception {
		List ls = null;
		String sql = "select wa.id,wa.wid,wa.approveid,wa.actid,wa.approvecontent,wa.approve,wa.approvedate from WithdrawApprove as wa where wa.wid='"
				+ wid+"'";
		ls = EntityManager.getAllByHql(sql);
		return ls;
	}

	public void delWithdrawApproveByWID(String wid) throws Exception {
		
		String sql = "delete from withdraw_approve where wid='" + wid+"'";
		//System.out.println("---------------+++"+sql);
		EntityManager.updateOrdelete(sql);
		
	}

	public void addWithdrawApprove(Object o) throws Exception {
		

		EntityManager.save(o);
		
	}

	public void addApproveContent(String approvecontent, int approve,
			String approvedate, String wid,Integer actid, Long userid) throws Exception {
		
		String sql = "update withdraw_approve set approvecontent='"
				+ approvecontent + "', approve=" + approve + ",approvedate='"
				+ approvedate + "' where wid='" + wid
				+ "' and actid="+actid+" and approveid=" + userid;
		EntityManager.updateOrdelete(sql);
		
	}
	

	public void cancelApprove(Integer approve,Integer actid,String wid,Long userid)throws Exception{
	 
	 String sql = "update withdraw_approve set approve="+approve+" where wid='"+wid+"' and actid="+actid+" and approveid="+userid;
	 EntityManager.updateOrdelete(sql);
	 
	}
}
