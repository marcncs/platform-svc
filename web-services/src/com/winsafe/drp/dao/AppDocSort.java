package com.winsafe.drp.dao;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;

import com.winsafe.drp.entity.EntityManager;

public class AppDocSort {

	public List getDocSortVisit(Integer userid) throws Exception {
		//String sql = "select * from Doc_Sort as d where d.userid="+userid+ 
		//"or exists ( select id from doc_sort_visit as v where d.id=v.dsid and v.userid="+userid+")"; 
		String sql = "select * from Doc_Sort  d ";
		return EntityManager.jdbcquery(sql);
	}
	
	public List getDocSort(Integer userid) throws Exception {
		String sql = "select * from Doc_Sort  d where d.userid="+userid; 
		return EntityManager.jdbcquery(sql);
	}

	public DocSort getDocSortByID(Integer id) throws Exception {
		String sql = " from DocSort as pbs where pbs.id=" + id;
		return (DocSort) EntityManager.find(sql);
	}

	public boolean getDocSortByName(String sortname, Integer userid)
			throws Exception {
		DocSort pbs = null;
		String sql = " from DocSort as pbs where pbs.sortname= '" + sortname
				+ "' and userid = " + userid;
		pbs = (DocSort) EntityManager.find(sql);
		return pbs != null;
	}

	public void addDocSort(DocSort docsort) throws Exception {
		EntityManager.save(docsort);
	}

	public void updDocSort(Integer id, String sortname) throws Exception {
		String sql = "update doc_sort set SortName ='" + sortname
				+ "' where ID=" + id;
		EntityManager.updateOrdelete(sql);
	}

	public void delDocSort(Integer id) throws Exception {
		String sql = "delete from doc_sort where ID=" + id;
		EntityManager.updateOrdelete(sql);
	}
	
	public List<DocSortVisit> getDocSortVisitDSID(Integer dsid){
		String hql = "from DocSortVisit as dsv where dsv.dsid="+dsid;
		return EntityManager.getAllByHql(hql);
	}
	
	public void delDocSortVisitByDsid(Integer dsid) throws HibernateException, SQLException, Exception{
		String sql = "delete from doc_sort_visit where dsid=" + dsid;
		EntityManager.updateOrdelete(sql);
	}
	
	public void addDocSortVisit(DocSortVisit docSortVisit){
		EntityManager.save(docSortVisit);
	}
}
