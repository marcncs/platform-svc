package com.winsafe.drp.dao;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.pager2.PageQuery;

public class AppAlterMoveApply {

	public void addAlterMoveApply(AlterMoveApply stockAlterMove) throws Exception {
		EntityManager.save(stockAlterMove);
	}

	public List<AlterMoveApply> getAlterMoveApply(HttpServletRequest request,int pagesize, String whereSql) throws Exception {
		String hql = " from AlterMoveApply as ama "
				+ whereSql + " order by ama.makedate desc ";
		return PageQuery.hbmQuery(request, hql,pagesize);
	}
	

	public void updIsTrans(String id, int trans) throws Exception {
		String sql = "update Alter_Move_Apply set istrans="+trans+" where id='" + id + "'";
		EntityManager.updateOrdelete(sql);
	}
	
	public void delAlterMoveApply(AlterMoveApply alterma)throws Exception{
		EntityManager.delete(alterma);
	}

	public AlterMoveApply getAlterMoveApplyByID(String id) throws Exception {
		AlterMoveApply sm = null;
		String sql = " from AlterMoveApply as ama where ama.id='" + id+"'";
		sm = (AlterMoveApply) EntityManager.find(sql);
		return sm;
	}
	
	
	public int waitOutPortAlterMoveApply(String organid) throws Exception {
		int count = 0;
		String sql = "select count(*) from AlterMoveApply as am where am.isratify=1 and am.istrans=0 and am.isblankout=0 and am.outorganid='"+organid+"'";
		count = EntityManager.getRecordCount(sql);
		return count;
	}

	
	public void updAlterMoveApply(AlterMoveApply sam)throws Exception{
		EntityManager.update(sam);
	}
	

	public void ratifyAlterMoveApply(String id,Integer isratify,Long userid)throws Exception{
		String sql="update Alter_Move_Apply set isratify="+isratify+",ratifyid="+userid+",ratifydate='"+DateUtil.getCurrentDateString()+"' where id='"+id+"'";
		EntityManager.updateOrdelete(sql);
	}
	
	
	public void updStockAlterMoveIsComplete(String id,Integer iscomplete,Long userid)throws Exception{
		String sql="update Alter_Move_Apply set iscomplete="+iscomplete+",receivedate='"+DateUtil.getCurrentDateString()+"',receiveid="+userid+" where id='"+id+"'";
		EntityManager.updateOrdelete(sql);
	}
	
	
	
	public void updIsApprove(String id, int isapprove) throws Exception {
		String sql = "update Alter_Move_Apply set approvestatus=" + isapprove
				+ " where id ='" + id+"'";
		EntityManager.updateOrdelete(sql);
	}


	public void updIsAudit(String ppid, Long userid,Integer audit) throws Exception {
		String sql = "update Alter_Move_Apply set isaudit="+audit+",auditid=" + userid
				+ ",auditdate='" + DateUtil.getCurrentDateString()
				+ "' where id='" + ppid + "'";
		EntityManager.updateOrdelete(sql);
	}
	

	public void updIsTally(String ppid, Long userid,Integer tally) throws Exception {
		String sql = "update Alter_Move_Apply set istally="+tally+",tallyid=" + userid
				+ ",tallydate='" + DateUtil.getCurrentDateString()
				+ "' where id='" + ppid + "'";
		EntityManager.updateOrdelete(sql);
	}
	

	public void blankout(String id, Long userid, Integer blankout, String blankoutreason) throws Exception {
		
		String sql = "update Alter_Move_Apply set isblankout="+blankout+",blankoutid=" + userid
				+ ",blankoutdate='" + DateUtil.getCurrentDateTime()
				+ "',blankoutreason='"+blankoutreason+"' where id='" + id + "'";
		EntityManager.updateOrdelete(sql);
		
	}
	
	public void IsTrans(String id,Integer istrans )throws Exception{
		String sql = "update Alter_Move_Apply set istrans="+istrans+" where id='" + id + "'";
		EntityManager.updateOrdelete(sql);
		
	}

	public List<AlterMoveApply> getAlterMoveApply(String whereSql) {
		String hql = " from AlterMoveApply as ama "
			+ whereSql + " order by ama.makedate desc ";
		return EntityManager.getAllByHql(hql);
	}
	
}
