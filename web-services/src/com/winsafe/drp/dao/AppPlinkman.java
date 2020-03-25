package com.winsafe.drp.dao;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.exception.NotExistException;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.pager2.PageQuery;

public class AppPlinkman {

	
	public List getPlinkManByPId(HttpServletRequest request, int pagesize, String pWhereClause) throws Exception {

		String sql = " from Plinkman as pl " + pWhereClause
				+ " order by pl.id desc ";
		return PageQuery.hbmQuery(request, sql, pagesize);
	}

	
	public List getLinkmanByPid(String pid) throws Exception {
         
		String sql = "from Plinkman as pl where pl.pid= '" + pid
				+ "' order by pl.id desc ";
		// System.out.println("----"+sql);
		return EntityManager.getAllByHql(sql);
	}

	
	public void addPlinkman(Object providelinkman) throws Exception {
		EntityManager.save(providelinkman);
	}

	
	public Plinkman getProvideLinkmanByID(Integer id) throws Exception {
		String sql = " from Plinkman as pl where pl.id=" + id;
		return (Plinkman) EntityManager.find(sql);
	}
	
	public Plinkman getPLinkmanByPID(String pid) throws Exception {
		String sql = " from Plinkman  where pid='" + pid+"' order by ismain desc ";
		return (Plinkman) EntityManager.find(sql);
	}

	
	public void updPlinkman(Plinkman pl) throws Exception {
		EntityManager.update(pl);
	}

	public void delPlinkman(Integer plid) throws Exception {
		String sql = "delete from Plinkman where id=" + plid;
		EntityManager.updateOrdelete(sql);
	}

	public void delPlinkmanByPid(String plid) throws Exception {
		String sql = "delete from Plinkman where pid='" + plid + "'";
		EntityManager.updateOrdelete(sql);
	}

	public List getPlinkmanByPid(String pid) throws Exception {
		String hql = "from Plinkman where pid='" + pid + "'";
		return EntityManager.getAllByHql(hql);
	}

	public Plinkman getProvideLinkmanByNCcode(Integer nccode) throws Exception {
		String sql = " from Plinkman as pl where pl.nccode=" + nccode;
		return (Plinkman) EntityManager.find(sql);
	}
	
	public Plinkman getPLinkmanByNCcodde(String nccode,int i) throws Exception {
		String sql = " from Plinkman  where nccode='" + nccode+"' order by ismain desc ";
		Plinkman temp= new Plinkman();
		temp=(Plinkman) EntityManager.find(sql);
		if(temp==null){
			try {
				DBUserLog.addUserLog(i, 13,"基础数据错误，找不到相对应的供应商联系人" + nccode);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		throw new NotExistException("基础数据错误，找不到相对应的供应商联系人");
		} 
	return temp;
	}

	
}
