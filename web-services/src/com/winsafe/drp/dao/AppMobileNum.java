package com.winsafe.drp.dao;

import com.winsafe.drp.entity.EntityManager;

public class AppMobileNum {
	
	public MobileArea findMobileArea(String mobilenum)throws Exception
	{
		MobileArea ma=null;
		String sql="from MobileArea as m where m.mobilenum='"+mobilenum+"'";
		ma=(MobileArea)EntityManager.find(sql);
		return ma;
	}

}
