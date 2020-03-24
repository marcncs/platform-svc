package com.winsafe.drp.dao;

import java.util.List;

import com.winsafe.drp.entity.EntityManager;

public class AppAfficheBrowse {
	public AfficheBrowse getAfficheBrowse(Integer affiecheid,Integer userid)throws Exception{
		AfficheBrowse ab = null;
		String sql=" from AfficheBrowse as ab where ab.afficheid="+affiecheid+" and ab.userid="+userid;
		ab = (AfficheBrowse) EntityManager.find(sql);
		return ab;
	}
	
	public void addAfficheBrowse(AfficheBrowse affb)throws Exception{
		EntityManager.save(affb);
	}
	
	
	public void delAfficheBrowse(Integer afid )throws Exception{
		
		String sql="delete from Affiche_Browse where afficheid="+afid;
		EntityManager.updateOrdelete(sql);
		
	}
	
	
	public List getAfficheBrowse(String whereSql)throws Exception{
		List ls=null;
		String sql="select ab.id,ab.afficheid,ab.userid,ab.isbrowse from AfficheBrowse as ab "+whereSql+" ";
		ls =EntityManager.getAllByHql(sql);
		//System.out.println("---"+sql);
		return ls;
	}
	
	
}
