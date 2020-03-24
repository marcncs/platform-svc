package com.winsafe.drp.dao;

import java.util.List;

import com.winsafe.drp.entity.EntityManager;

public class AppUserGrade {
	public List getUserGradeMGID(int wid) throws Exception {
		String sql = " from UserGrade as ug where ug.gradeid="
				+ wid;
		return EntityManager.getAllByHql(sql);
	}
	
	public void addUserGradeVisit(Object s) throws Exception {
		EntityManager.save(s);
		
	}
	
	public void delUserGradeByUGID(int mgid) throws Exception {		
		String sql = "delete from User_Grade where gradeid=" + mgid;
		EntityManager.updateOrdelete(sql);		
	}
	
	
	public int findUserGradeByUseridPid(int pid,int uid)throws Exception{
		String sql="select count(ug.id) from UserGrade as ug where ug.gradeid="+pid+" and userid="+uid;
		return EntityManager.getRecordCount(sql);
	}
}
