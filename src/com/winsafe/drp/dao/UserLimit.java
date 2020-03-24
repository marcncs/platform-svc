package com.winsafe.drp.dao;

import com.winsafe.drp.entity.EntityManager;

public class UserLimit {
	public int userLimit()throws Exception{
		int ul = 0;
		int factcount=0;
		int userlimit = 90;
		String sql="select count(u.userid) from Users as u where u.status = 1";
		factcount=EntityManager.getRecordCount(sql);
		if(factcount >= userlimit){
			ul=1;
		}
		return ul;
	}
}
