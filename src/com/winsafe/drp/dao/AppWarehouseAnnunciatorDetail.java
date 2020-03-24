package com.winsafe.drp.dao;

import java.util.List;

import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.pager.SimplePageInfo;

public class AppWarehouseAnnunciatorDetail {
	public void addWarehouseAnnunciatorDetail(Object wad)throws Exception{
		EntityManager.save(wad);
	}
	
	public List getWarehouseAnnunciatorDetailByWAID(int pagesize, String pWhereClause,
			SimplePageInfo pPgInfo)throws Exception{
		List ls = null;
		int targetPage = pPgInfo.getCurrentPageNo();
		String sql ="select wad.id,wad.waid,wad.productid,wad.quantity from WarehouseAnnunciatorDetail as wad "+pWhereClause+" ";
		//System.out.println("-----"+sql);
		ls = EntityManager.getAllByHql(sql, targetPage, pagesize);
		return ls;
	}
}
