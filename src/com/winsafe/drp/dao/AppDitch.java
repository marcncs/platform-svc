package com.winsafe.drp.dao;

import java.util.List;

import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.pager.SimplePageInfo;

public class AppDitch {

	public List getDitch(int pagesize, String pWhereClause,
			SimplePageInfo pPgInfo) throws Exception {
		List list = null;
		String sql = "select d.id,d.dname,d.telone,d.teltwo,d.fax,d.province,d.city,d.areas,d.detailaddr,d.postcode,d.email,d.homepage,d.ditchrank,d.prestige,d.scale,d.vocation,d.taxcode,d.bankname,d.doorname,d.bankaccount,d.memo,d.userid,d.makeid,d.makedate from Ditch as d "
				+ pWhereClause + " order by d.makedate desc";

		list = EntityManager.getAllByHql(sql, pPgInfo.getCurrentPageNo(),
				pagesize);
		return list;
	}
	
	
	public void addDitch(Object ditch) throws Exception {
		
		EntityManager.save(ditch);
		
	}
	
	public Ditch getDitchByID(Long id) throws Exception {
		Ditch d = null;
		String hql = " from Ditch as d where  d.id=" + id ;
		d = (Ditch) EntityManager.find(hql);
		return d;
	}
	
	public void updDitch(Ditch d)throws Exception{
		
		String sql="update ditch set dname='"+d.getDname()+"',telone='"+d.getTelone()+"',teltwo='"+d.getTeltwo()+"',fax='"+d.getFax()+"',province="+d.getProvince()+",city="+d.getCity()+",areas="+d.getAreas()+",detailaddr='"+d.getDetailaddr()+"',postcode='"+d.getPostcode()+"',email='"+d.getEmail()+"',homepage='"+d.getHomepage()+"',ditchrank="+d.getDitchrank()+",prestige="+d.getPrestige()+",scale='"+d.getScale()+"',vocation="+d.getVocation()+",taxcode='"+d.getTaxcode()+"',bankname='"+d.getBankname()+"',doorname='"+d.getDoorname()+"',bankaccount='"+d.getBankaccount()+"',memo='"+d.getMemo()+"' where id="+d.getId();
		//System.out.println("==---"+sql);
		EntityManager.updateOrdelete(sql);
		
	}
	
	
	public void appointsDitch(String userid,Long id)throws Exception{

		String sql="update ditch set userid='"+userid+"' where id = "+id;
		//System.out.println("-----"+sql);
		EntityManager.updateOrdelete(sql);

	}
	
}
