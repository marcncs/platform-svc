package com.winsafe.drp.dao;

import com.winsafe.drp.entity.EntityManager;

public class AppManufacturerNews {

	public ManufacturerNews getById(Integer id){
		String sql = " from ManufacturerNews as p where p.id='" + id + "'";
		return (ManufacturerNews) EntityManager.find(sql); 
	}
}
