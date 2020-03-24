package com.winsafe.drp.dao;

import java.util.List;

import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.pager.SimplePageInfo;

public class AppContactDitch {
	public  List searchContactDitch(int pagesize, String pWhereClause,
			SimplePageInfo pPgInfo) throws Exception {
		List list = null;
		String sql = "select c.id,c.did,c.contactdate,c.contactmode,c.contactproperty,c.contactcontent,c.feedback,c.linkman,c.nextcontact,c.userid from ContactDitch as c  "
				+ pWhereClause + " order by c.id desc";
		list = EntityManager.getAllByHql(sql, pPgInfo.getCurrentPageNo(),
				pagesize);
		return list;
	}

	public List initContactDitch(int pagesize, String pWhereClause)
			throws Exception {
		List list = null;
		String sql = "select c.id,c.did,c.contactdate,c.contactmode,c.contactproperty,c.contactcontent,"
				+ "c.feedback,c.linkmanid,c.nextcontact,c.userid from ContactDitch as c  "
				+ pWhereClause + " order by c.contactdate desc";
		list = EntityManager.getAllByHql(sql, 1, pagesize);
		return list;
	}

	public void addContactDitch(Object cl) throws Exception {
		
		EntityManager.save(cl);
		
	}
	
	public void delContactDitch(Long id)throws Exception{
		
		String sql="delete from Contact_Ditch where id="+id;
		EntityManager.updateOrdelete(sql);
		
	}

	public ContactDitch getContactDitch(Long id) throws Exception {
		ContactDitch cl = null;

		String hql = " from ContactDitch as c where c.id=" + id
				+ " ";
		cl = (ContactDitch) EntityManager.find(hql);
		return cl;

	}

	public void updateContactDitch(ContactDitch cl,String contactdate,String nextcontact) throws Exception {
		
		String sql="update contact_ditch set did='"+cl.getDid()+"',contactdate='"+contactdate+"',contactmode="+cl.getContactmode()+",contactproperty="+cl.getContactproperty()+",contactcontent='"+cl.getContactcontent()+"',feedback='"+cl.getFeedback()+"',linkman='"+cl.getLinkman()+"',nextcontact='"+nextcontact+"',nextgoal='"+cl.getNextgoal()+"' where id="+cl.getId()+" ";
		EntityManager.updateOrdelete(sql);
		
	}
}
