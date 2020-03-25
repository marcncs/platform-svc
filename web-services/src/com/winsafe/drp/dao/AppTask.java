package com.winsafe.drp.dao;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.pager.SimplePageInfo;
import com.winsafe.hbm.util.pager2.PageQuery;

public class AppTask {

	
	public List getTask(int pagesize, String pWhereClause,
			SimplePageInfo pPgInfo) throws Exception {
		List tpls = null;
		int targetPage = pPgInfo.getCurrentPageNo();
		String sql = "select t.id,t.tptitle,t.begindate,t.enddate,t.status,t.userid,t.isallot from Task as t "
				+ pWhereClause + " order by t.makedate desc ";
		tpls = EntityManager.getAllByHql(sql, targetPage, pagesize);
		return tpls;
	}

	public List searchTask(HttpServletRequest request, int pagesize,
			String pWhereClause) throws Exception {
		String hql = "select t.id,t.objsort,t.cname,t.tptitle,t.conclusiondate,t.priority,t.status,t.overstatus,t.makeorganid,t.makeid,te.isaffirm from Task as t ,TaskExecute as te "
				+ pWhereClause + " order by t.makedate desc ";
		return PageQuery.hbmQuery(request, hql, pagesize);
	}

	public List getTask(String pWhereClause) throws Exception {
		String sql = " from Task as t " + pWhereClause
				+ " order by t.makedate desc ";
		return EntityManager.getAllByHql(sql);
	}

	
	public void addTask(Task task) throws Exception {
		EntityManager.save(task);

	}

	public void delTask(Integer id) throws Exception {

		String sql = "delete from task where id=" + id;
		EntityManager.updateOrdelete(sql);

	}

	
	public Task getTaskByID(Integer id) throws Exception {

		String hql = "from Task as t where t.id=" + id;
		return (Task) EntityManager.getAllByHql(hql).get(0);

	}

	public Task getTaskFromByID(Integer id) throws Exception {
		Task tp = null;
		String sql = " from Task where id=" + id;
		tp = (Task) EntityManager.find(sql);
		return tp;
	}

	
	public void updIsAllot(Integer id) throws Exception {

		String sql = "update task set isallot = 1 where id=" + id;
		EntityManager.updateOrdelete(sql);

	}

	
	public void updIsOverStatus(Integer id, Integer status, String overdate)
			throws Exception {
		String sql = "update task set overstatus = " + status + ",overdate='"
				+ overdate + "' where id=" + id;
		EntityManager.updateOrdelete(sql);
	}

	
	public List listReceiptTask(int pagesize, String pWhereClause,
			SimplePageInfo pPgInfo) throws Exception {
		List rt = null;
		int targetPage = pPgInfo.getCurrentPageNo();
		String sql = "select t.id,t.tptitle,t.begindate,t.enddate,t.status,t.tpcontent,t.userid,t.isallot from Task as t,TaskExecute as te "
				+ pWhereClause + " order by te.isaffirm,t.makedate desc";
		rt = EntityManager.getAllByHql(sql, targetPage, pagesize);
		return rt;
	}

	public void updUpdTaskPlan(Integer id, String tptitle, String begindate,
			String enddate, Integer status, String tpcontent) throws Exception {

		String sql = "update task set tptitle='" + tptitle + "',begindate='"
				+ begindate + "',enddate='" + enddate + "',status=" + status
				+ ",tpcontent='" + tpcontent + "' where id=" + id;
		EntityManager.updateOrdelete(sql);

	}

	public void updTask(Task task) throws Exception {
		EntityManager.saveOrUpdate(task);
	}

}
