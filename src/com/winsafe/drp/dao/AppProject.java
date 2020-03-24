package com.winsafe.drp.dao;

import java.util.List;

import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.pager.SimplePageInfo;

public class AppProject {

	public List getProject(int pagesize, String pWhereClause,
			SimplePageInfo pPgInfo) throws Exception {
		List list = null;
		String sql = "select p.id,p.cid,p.pcontent,p.pstatus,p.amount,p.pbegin,p.pend,p.memo,p.makeid,p.makedate from Project as p "
				+ pWhereClause + " order by p.id desc";

		list = EntityManager.getAllByHql(sql, pPgInfo.getCurrentPageNo(),
				pagesize);
		return list;
	}
	
	public void addProject(Object r) throws Exception {
		
		EntityManager.save(r);
		
	}
	
	public void delProject(Long id)throws Exception{
		
		String sql="delete from Project where id="+id;
		EntityManager.updateOrdelete(sql);
		
	}
	
	public Project getProjectByID(Long id)throws Exception{
		Project h=new Project();
		String sql=" from Project as p where p.id="+id;
		h = (Project) EntityManager.find(sql);
		return h;
	}
	
	public void updProject(Project h,String pbegin,String pend)throws Exception{
		
		String sql = "update Project set cid='"+h.getCid()+"',pcontent="+h.getPcontent()+",pstatus="+h.getPstatus()+",amount="+h.getAmount()+",pbegin='"+pbegin+"',pend='"+pend+"',memo='"+h.getMemo()+"' where id="+h.getId();
		EntityManager.updateOrdelete(sql);
		
	}
}
