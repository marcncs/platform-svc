package com.winsafe.drp.dao;

import com.winsafe.drp.entity.EntityManager;

public class AppMenu {

	public void addMenu(Object m)throws Exception{
		EntityManager.save(m);
	}
	
	public int getMenu(String u)throws Exception{
		int m=0;
		String sql="select count(*) from Menu where url='"+u+"'";
		m = EntityManager.getRecordCount(sql);
		return m;
	}
}
