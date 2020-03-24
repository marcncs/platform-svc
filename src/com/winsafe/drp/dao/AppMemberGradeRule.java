package com.winsafe.drp.dao;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.pager2.PageQuery;

public class AppMemberGradeRule {

	public List getMemberGradeRule(HttpServletRequest request, String pWhereClause) throws Exception {
		String hql = " from MemberGradeRule as m "
				+ pWhereClause + " order by m.id ";
		return PageQuery.hbmQuery(request, hql);
	}

	public void addMemberGradeRule(MemberGradeRule m) throws Exception {
		
		EntityManager.save(m);
		
	}

	public List getAllMemberGradeRule() throws Exception {
		String sql = " from MemberGradeRule as m ";
		return EntityManager.getAllByHql(sql);
	}
	
	public List getMemberGradeRuleByMgid(int mgid) throws Exception {
		String sql = " from MemberGradeRule where mgid="+mgid;
		return EntityManager.getAllByHql(sql);
	}
	


	public MemberGradeRule getMemberGradeRuleByID(int id) throws Exception {
		String sql = " from MemberGradeRule where id=" + id;
		return (MemberGradeRule) EntityManager.find(sql);
	}
	

	public void updMemberGradeRule(MemberGradeRule m) throws Exception {
		
		EntityManager.update(m);
		
	}

}
