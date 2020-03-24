package com.winsafe.drp.dao;

import java.util.List;

import com.winsafe.drp.entity.EntityManager;

public class AppManufacturerContact {

	public List getByManufacturerId(Integer manufacturerId){
		String hql = " from ManufacturerContact as p where p.manufacturerId = '" + manufacturerId + "'";
		return EntityManager.getAllByHql(hql); 
	}
}
