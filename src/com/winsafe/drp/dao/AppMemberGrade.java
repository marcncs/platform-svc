package com.winsafe.drp.dao;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.pager2.PageQuery;

public class AppMemberGrade {

	public List getMemberGrade(HttpServletRequest request, String pWhereClause) throws Exception {
		String hql = " from MemberGrade as mg "+ pWhereClause + " order by mg.id ";
		return PageQuery.hbmQuery(request, hql);
	}

	public void addMemberGrade(Object pp) throws Exception {		
		EntityManager.save(pp);		
	}

	public List getAllMemberGrade() throws Exception {
		String sql = " from MemberGrade as mg ";
		return EntityManager.getAllByHql(sql);
	}
	
	

	public List getEnableMemberGradeByVisit(Integer userid) throws Exception {
		String sql = "select mg.id,mg.gradename,mg.integralrate from MemberGrade as mg,UserGrade as ug where mg.id=ug.gradeid and ug.userid="+userid;
		return EntityManager.getAllByHql(sql);
	}

	public MemberGrade getMemberGradeByID(Integer id) throws Exception {
		String sql = " from MemberGrade where id=" + id;
		return (MemberGrade) EntityManager.find(sql);
	}
	
	public String getMemberGradeName(int id) throws Exception {
		MemberGrade mg = getMemberGradeByID(id);
		if ( mg != null ){
			return mg.getGradename();
		}
		return "";
	}
	
	public void updMemberGrade(MemberGrade w) throws Exception {		
		EntityManager.update(w);		
	}
	
	public void delMemberGrade(int id) throws Exception {
		String sql = " delete from Member_Grade where id="+id;
		EntityManager.updateOrdelete(sql);
	}
	
	public boolean isAready(int id) throws Exception {	
		String sql = " select count(*) from Customer where rate=" + id;
		int i  =EntityManager.getRecordCount(sql);
		if ( i>0 ){
			return true;
		}
		return false;
	}

}
