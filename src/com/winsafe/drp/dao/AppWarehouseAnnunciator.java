package com.winsafe.drp.dao;

import java.util.List;

import com.winsafe.drp.entity.EntityManager;

public class AppWarehouseAnnunciator {

	public void addWarehouseAnnunciator(Object wa)throws Exception{
		
		EntityManager.save(wa);
		
	}
	
	
	public void delWareHouseAnnunciatorByWID(Long wid)throws Exception{
		
		String sql="delete from warehouse_annunciator where warehouseid="+wid+" and isawake=0";
		EntityManager.updateOrdelete(sql);
		
	}
	
	
	public List stockAnnumciator(Long userid)throws Exception{
		List ls =null;
		String sql="select wa.id,wa.warehouseid,userid,isawake from WarehouseAnnunciator as wa where wa.userid="+userid+" and wa.isawake=0 ";
		//System.out.println("-----"+sql);
		ls = EntityManager.getAllByHql(sql);
		return ls;
	}
	
	public WarehouseAnnunciator ShowAnnumciator(Long userid)throws Exception{
		WarehouseAnnunciator ls =null;
		String sql=" from WarehouseAnnunciator as wa where wa.userid="+userid+" and wa.isawake=0 order by wa.id desc";
		ls = (WarehouseAnnunciator)EntityManager.find(sql);
		return ls;
	}
	
	
	public void updWarehouseAnnumciator(Long warehouseid,Long userid)throws Exception{
		
		String sql="update warehouse_annunciator set isawake=1 where warehouseid="+warehouseid+" and userid="+userid;
		EntityManager.updateOrdelete(sql);
		
	}
}
