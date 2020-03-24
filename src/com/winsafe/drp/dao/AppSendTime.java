package com.winsafe.drp.dao;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.pager.SimplePageInfo;
import com.winsafe.hbm.util.pager2.PageQuery;

public class AppSendTime {

	public List getSendTime(int pagesize, String pWhereClause,
			SimplePageInfo pPgInfo) throws Exception {
		List wr = null;
		int targetPage = pPgInfo.getCurrentPageNo();
		String sql = " from SendTime as st "
				+ pWhereClause + " order by st.id ";
		wr = EntityManager.getAllByHql(sql, targetPage, pagesize);
		return wr;
	}
	
	public List getSendTime(HttpServletRequest request, int pagesize, String pWhereClause) throws Exception {
		String hql = " from SendTime as st "+ pWhereClause + " order by st.id ";
		return PageQuery.hbmQuery(request, hql, pagesize);
	}

	public void addSendTime(Object pp) throws Exception {
		EntityManager.save(pp);
		
	}

	public List getAllSendTime() throws Exception {
		List aw = null;
		String sql = " from SendTime as pm ";
		aw = EntityManager.getAllByHql(sql);
		return aw;
	}
	

	public SendTime getSendTimeByID(int id) throws Exception {
		String sql = " from SendTime where id=" + id;
		SendTime w = (SendTime) EntityManager.find(sql);
		return w;
	}
	
	public SendTime getSendTimeBySETime(int t)throws Exception{
		String sql = " from SendTime as st where ("+t+">stime and "+t+"<etime) or (st.id=3 and "+t+">stime)";
		//System.out.println("--------->sql="+sql);
		return (SendTime) EntityManager.find(sql);
	}
	

	public void updSendTime(SendTime w) throws Exception {
		
		EntityManager.update(w);
		
	}

}
