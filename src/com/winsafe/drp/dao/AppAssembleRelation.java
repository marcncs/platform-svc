package com.winsafe.drp.dao;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.pager2.PageQuery;

public class AppAssembleRelation {


	public List getAssembleRelation(HttpServletRequest request, int pagesize,
			String pWhereClause) throws Exception {

		String sql = " from AssembleRelation as ar "
				+ pWhereClause + " order by ar.id desc ";
		return PageQuery.hbmQuery(request, sql, pagesize);
	}
	

	
	public void addAssembleRelation(Object ar) throws Exception {
		EntityManager.save(ar);

	}
	
	public void delAssembleRelation(String id)throws Exception{
		String sql="delete from Assemble_Relation where id='"+id+"'";
		EntityManager.updateOrdelete(sql);
	}
	

	
	public AssembleRelation getAssembleRelationByID(String id) throws Exception {
		String sql = " from AssembleRelation as ag where ag.id='" + id + "'";
		AssembleRelation ar = (AssembleRelation) EntityManager.find(sql);
		return ar;
	}

	
	public void updAssembleRelationByID(AssembleRelation ar)
			throws Exception {
		EntityManager.update(ar);
	}

	
	public void updIsAudit(String id, int userid,Integer audit) throws Exception {
		String sql = "update Assemble_Relation set isaudit="+audit+",auditid=" + userid
				+ ",auditdate='" + DateUtil.getCurrentDateString()
				+ "' where id='" + id + "'";
		EntityManager.updateOrdelete(sql);
	}


	public List<AssembleRelation> getAssembleRelation(String whereSql) {
		String sql = " from AssembleRelation as ar "
			+ whereSql + " order by ar.id desc ";
		return EntityManager.getAllByHql(sql);
	}
	


}
