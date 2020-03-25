package com.winsafe.drp.dao;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.pager2.PageQuery;

public class AppApproveFlow {


	
	public List getApproveFlow(HttpServletRequest request, int pagesize, String pWhereClause) throws Exception {
		String hql = "select af.id,af.flowname,af.memo from ApproveFlow as af "
				+ pWhereClause + " order by af.id desc ";
		return PageQuery.hbmQuery(request, hql, pagesize);
	}

	public void addApproveFlow(Object approveflow) throws Exception {
		
		EntityManager.save(approveflow);
		
	}
	
	public List getApproveFlow()throws Exception{
		String sql="select af.id,af.flowname from ApproveFlow as af order by af.id desc ";
		return EntityManager.getAllByHql(sql);
	}


	public ApproveFlow getApproveFlowByID(Long id) throws Exception {
		String sql = " from ApproveFlow where id=" + id;
		return(ApproveFlow) EntityManager.find(sql);
	}

	public void updApproveFlow(ApproveFlow af) throws Exception {
		
		String sql = "update approve_flow set flowname='"
				+ af.getFlowname() + "',memo='" + af.getMemo() + "' where id="
				+ af.getId();
		EntityManager.updateOrdelete(sql);
		
	}

}
