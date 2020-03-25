package com.winsafe.drp.dao;

import com.winsafe.drp.entity.EntityManager;

public class AppManufacturer {

	
	public Manufacturer getManufacturerById(Integer manufacturerById) throws Exception{
		String sql = " from Manufacturer p where p.id=" + manufacturerById;
		return (Manufacturer) EntityManager.find(sql); 
	}
}
