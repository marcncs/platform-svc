package com.winsafe.drp.dao;

import java.util.List;

import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.pager.SimplePageInfo;

public class AppCheckImport {

	public List getCheckImport(int pagesize, String pWhereClause,
			SimplePageInfo pPgInfo) throws Exception {
		List list = null;
		String sql = "select p.id,p.cid,p.pcontent,p.pstatus,p.amount,p.pbegin,p.pend,p.memo,p.makeid,p.makedate from Project as p "
				+ pWhereClause + " order by p.id desc";

		list = EntityManager.getAllByHql(sql, pPgInfo.getCurrentPageNo(),
				pagesize);
		return list;
	}
	
	
	public List getCheckImportByIsImport(Long makeid) throws Exception{
		List list=null;
		String sql=" from CheckImport as ci where ci.isimport=0 and ci.makeid= "+makeid;
		list=EntityManager.getAllByHql(sql);
		return list;
	}
	
	public void addCheckImport(CheckImport r) throws Exception {
		
		EntityManager.save(r);
		
	}
	
	public void delCheckImport(Long id)throws Exception{
		
		String sql="delete from CheckImport where id="+id;
		EntityManager.updateOrdelete(sql);
		
	}
	
	public CheckImport getCheckImportByID(Long id)throws Exception{
		String sql=" from CheckImport as ci where ci.id="+id;
		CheckImport h = (CheckImport) EntityManager.find(sql);
		return h;
	}
	
	

	public void CompleteCheckImport(Long id, int isimport)throws Exception {
		String sql="update Check_Import set isimport="+isimport+" where id="+id;
		EntityManager.updateOrdelete(sql);
	}
	

}
