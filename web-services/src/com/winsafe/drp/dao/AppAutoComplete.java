package com.winsafe.drp.dao;

import java.util.List;

import com.winsafe.drp.entity.EntityManager;

public class AppAutoComplete {
	
	public List getAutoComplete(int userid,String tblname,String keyvalue)throws Exception{
		List ls =null;
		String sql="";
		if(tblname.equals("Task")){
			sql="select t.tptitle from Task as t where t.userid="+userid+" and t.tptitle like '%"+keyvalue+"%'";
		}
		if(tblname.equals("Customer")){
			sql="select c.cname from Customer as c where c.specializeid like '"+userid+"%' and c.cname like '%"+keyvalue+"%'";
		}
		ls = EntityManager.getAllByHql(sql);
		return ls;
	}
	
	
	
}
