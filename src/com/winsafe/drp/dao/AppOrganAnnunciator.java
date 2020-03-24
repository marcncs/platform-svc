package com.winsafe.drp.dao;

import java.util.List;

import com.winsafe.drp.entity.EntityManager;

public class AppOrganAnnunciator {

	public void addOrganAnnunciator(Object wa)throws Exception{
		
		EntityManager.save(wa);
		
	}
	
	
	public void delWareHouseAnnunciatorByWID(Long wid)throws Exception{
		
		String sql="delete from organ_annunciator where organid="+wid+" and isawake=0";
		EntityManager.updateOrdelete(sql);
		
	}
	
	
	public List stockAnnumciator(int userid)throws Exception{
		String sql="select wa.id,wa.organid,userid,isawake from OrganAnnunciator as wa where wa.userid="+userid+" and wa.isawake=0 ";
		//System.out.println("-----"+sql);
		return EntityManager.getAllByHql(sql);
	}
	
	public OrganAnnunciator ShowAnnumciator(int userid)throws Exception{
		OrganAnnunciator ls =null;
		String sql=" from OrganAnnunciator as wa where wa.userid="+userid+" and wa.isawake=0 order by wa.id desc";
		ls = (OrganAnnunciator)EntityManager.find(sql);
		return ls;
	}
	
	
	public void updOrganAnnumciator(String organid,int userid)throws Exception{
		
		String sql="update organ_annunciator set isawake=1 where organid='"+organid+"' and userid="+userid;
		EntityManager.updateOrdelete(sql);
		
	}
}
