package com.winsafe.drp.dao;

import java.util.List;

import com.winsafe.drp.entity.EntityManager;

public class AppTaskExecute {

	
	public List getExecuteIDByTaskPlanID(Integer tpid) throws Exception {

		String sql = " select te.userid from TaskExecute as te where te.tpid="
				+ tpid;
		return EntityManager.getAllByHql(sql);

	}

	public List getIsUsers(String Makeorganid, Integer tpid) throws Exception {

		String sql = "select a.userid,a.realname,b.userid hasexe from users a left join "
				+ "(select userid from task_execute where tpid="
				+ tpid
				+ ") b on a.userid=b.userid where a.makeorganid='"
				+ Makeorganid + "'";
		return EntityManager.jdbcquery(sql);

	}

	public List getExecuteIDByTaskPlanID(String whereSql) throws Exception {
		List els = null;
		String sql = " select te.id,te.tpid,te.userid,te.isaffirm from TaskExecute as te "
				+ whereSql;
		els = EntityManager.getAllByHql(sql);
		return els;
	}

	
	public void addExecute(TaskExecute taskExecute) throws Exception {

		EntityManager.save(taskExecute);

	}
	public void addExecute(TaskExecute[] taskExecute) throws Exception {

		EntityManager.save(taskExecute);

	}
	
	public void updTaskExecute(TaskExecute taskExecute) throws Exception{
		
		EntityManager.update(taskExecute);
	}

	
	public void updIsAffirmTaskExecute(Integer tpid, Integer userid) throws Exception {
		String sql = "update task_execute set isaffirm=1 where tpid=" + tpid
				+ " and userid=" + userid;
		EntityManager.updateOrdelete(sql);

	}
	
	
	public int getIsOverUser(Integer tpid)throws Exception{
		String sql="select count(*) from TaskExecute where tpid="+tpid+" and isover =0";
		int i = EntityManager.getRecordCount(sql);
		return i;
	}

	public TaskExecute getTaskExecute(Integer tpid, Integer userid) throws Exception {
		TaskExecute te = new TaskExecute();
		String sql = " from TaskExecute where tpid=" + tpid + " and userid="
				+ userid;
		te = (TaskExecute) EntityManager.find(sql);
		return te;
	}

	
	public void delTaskPlanExecuteByTPID(Integer tpid) throws Exception {

		String sql = "delete from task_execute where tpid=" + tpid;
		EntityManager.updateOrdelete(sql);
	}

}
