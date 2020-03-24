package com.winsafe.drp.dao;

import java.util.List;

import com.winsafe.drp.entity.EntityManager;

public class AppRepositoryType {


	public RepositoryType getRepositoryTypeById(String psid) throws Exception {
		String hql = " from RepositoryType as ps where ps.structcode = '"
				+ psid + "'";
		return (RepositoryType) EntityManager.find(hql);
	}

	
	public List getRepositoryTypeCanUse() throws Exception {
		List uls = null;
		String sql = " from RepositoryType as ps  order by ps.structcode";
		uls = EntityManager.getAllByHql(sql);
		return uls;
	}

	
	public void addNewRepositoryType(RepositoryType r) throws Exception {

		EntityManager.save(r);

	}

	public List getTreeByCate(String strparentid) {
		List list = null;
		String sql = "";
		int length = strparentid.length() + 2;
		if (strparentid != null && strparentid.equals("-1"))
			sql = "select a.id,a.structcode,a.sortname from RepositoryType as a where length(a.structcode)=1";
		else
			sql = "select a.id,a.structcode,a.sortname from RepositoryType as a where a.structcode like '"
					+ strparentid + "%' and length(structcode)=" + length;
		list = EntityManager.getAllByHql(sql);
		return list;
	}

	public String getAcodeByParent(String strparentid) throws Exception {

		String stracode = null;
		int length = strparentid.length() + 2;
		String sql = "from RepositoryType as a where a.structcode like'"
				+ strparentid + "%' and length(structcode)=" + length
				+ " order by a.structcode desc ";
		RepositoryType as = (RepositoryType) EntityManager.find(sql);
		long intacode = 0;
		if (as == null) {
			intacode = Long.valueOf(strparentid + "01");
		} else {
			intacode = Long.valueOf(as.getStructcode()).intValue() + 1;
		}
		stracode = String.valueOf(intacode);
		return stracode;
	}

	public void upd(String acode, String areaname) throws Exception {

		String sql = "update repository_type set SortName='" + areaname
				+ "'  where StructCode='" + acode + "'";
		EntityManager.updateOrdelete(sql);

	}

	public void del(String dcode, String dname) throws Exception {

		String sql = "delete from repository_type where structcode='" + dcode
				+ "'";
		EntityManager.updateOrdelete(sql);

	}

	public int checkHasSubStructByCode(String structcode) throws Exception {
		int c = 0;
		int psidlength = structcode.length() + 3;
		String sql = "select count(p.id) from RepositoryType as p where p.structcode like '"
				+ structcode + "%' and length(p.structcode)=" + psidlength;
		c = EntityManager.getRecordCount(sql);
		return c;
	}

}
