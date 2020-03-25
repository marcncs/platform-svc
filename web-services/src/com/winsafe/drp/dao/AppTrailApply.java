package com.winsafe.drp.dao;

import com.winsafe.drp.entity.EntityManager;

public class AppTrailApply {

	public void addTrailApply(Object ta)throws Exception{
		
		EntityManager.save(ta);
		
	}
}
