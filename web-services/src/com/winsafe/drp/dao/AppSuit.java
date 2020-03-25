package com.winsafe.drp.dao;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.pager.SimplePageInfo;
import com.winsafe.hbm.util.pager2.PageQuery;

public class AppSuit {

	public List getSuit(int pagesize, String pWhereClause,
			SimplePageInfo pPgInfo) throws Exception {
		String sql = " from Suit as s  "
				+ pWhereClause + " order by s.makedate desc";

		return EntityManager.getAllByHql(sql, pPgInfo.getCurrentPageNo(),pagesize);
	}
	
	
	public List searchSuit(HttpServletRequest request,int pagesize, String pWhereClause) throws Exception {
		String hql = " from Suit as s  "
			+ pWhereClause + " order by s.makedate desc";
		return PageQuery.hbmQuery(request, hql, pagesize);
	}
	
	public void addSuit(Suit s) throws Exception {
		EntityManager.save(s);	
	}
	
	public void updSuit(Suit s) throws Exception {
		EntityManager.update(s);	
	}
	
	public void delSuit(String id)throws Exception{
		
		String sql="delete from Suit where id='"+id+"'";
		EntityManager.updateOrdelete(sql);
		
	}
	
	public Suit getSuitByID(String id)throws Exception{
		String sql=" from Suit as s where s.id='"+id+"'";
		return (Suit) EntityManager.find(sql);
	}
	
	public void updSuit(Suit s,String suitdate)throws Exception{
		
		String sql = "update Suit set cid='"+s.getCid()+"',suitcontent="+s.getSuitcontent()+",suitway="+s.getSuitway()+",suittools='"+s.getSuittools()+"',suitstatus='"+s.getSuitstatus()+"',suitdate='"+suitdate+"',memo='"+s.getMemo()+"' where id="+s.getId();
		EntityManager.updateOrdelete(sql);
		
	}
	
	 
	public void updIsApprove(String id, int isapprove) throws Exception {
		String sql = "update suit set approvestatus=" + isapprove+" where id ='" + id+"'";
		EntityManager.updateOrdelete(sql);
	}
	
	//处理投诉
	public void dealSuit(Suit s,String dealdate,Integer isdeal)throws Exception{
	
	String sql = "update Suit set IsDeal="+isdeal+",DealWay="+s.getDealway()+",DealUser="+s.getDealuser()+",DealDate='"+dealdate+"',DealContent='"+s.getDealcontent()+"',DealFinal='"+s.getDealfinal()+"' where id="+s.getId();
	EntityManager.updateOrdelete(sql);
	
}
}
