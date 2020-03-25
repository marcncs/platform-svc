package com.winsafe.drp.dao;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.exception.NotExistException;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.pager.SimplePageInfo;
import com.winsafe.hbm.util.pager2.PageQuery;

public class AppProvider {

	
	
	public List getProvider(HttpServletRequest request, int pagesize, String pWhereClause) throws Exception {
		String sql = " from Provider as p "
				+ pWhereClause + " order by p.makedate desc ";
		//System.out.println("----"+sql);
		return PageQuery.hbmQuery(request, sql, pagesize);
	}
	
	public List getProviderAll(int pagesize, String pWhereClause,
			SimplePageInfo pPgInfo) throws Exception {
		int targetPage = pPgInfo.getCurrentPageNo();
		String sql = " from Provider as p "
				+ pWhereClause + " order by p.makedate desc ";
		return EntityManager.getAllByHql(sql, targetPage, pagesize);
	}
	
	public List<Provider> finAll(String pWhereClause)throws Exception{
		String sql = " from Provider as p "
			+ pWhereClause + " order by p.makedate desc ";
		return EntityManager.getAllByHql(sql);
	}
	
	
	public void addProvider(Object provide) throws Exception {		
		EntityManager.save(provide);		
	}

	
	public Provider getProviderByID(String pid) throws Exception {
		String sql = " from Provider as p where pid='" + pid+"'";
		return (Provider) EntityManager.find(sql);
	}
	
	public String getPName(String pid)throws Exception{
		Provider p = getProviderByID(pid);
		if ( p == null ){
			return "";
		}
		return p.getPname();
	}

	
	public void updProvider(Provider p) throws Exception {
		EntityManager.update(p);		
	}
	

	public void updProviderToDel(String pid) throws Exception {		
		String sql = "update provider set isdel=1 where pid='" + pid+"'";
		EntityManager.updateOrdelete(sql);		
	}
	
	
	public Integer getProviderPrompt(String pid) throws Exception{
		String sql = " from Provider as p where pid='" + pid+"'";
		Provider p = (Provider) EntityManager.find(sql);
		return p.getPrompt();
	}
	

	public int getProviderID(String id) throws Exception {
		String sql = "select count(*) from Provider where pid='" + id
				+ "'";
		return EntityManager.getRecordCountQuery(sql);
	}
	
	
	public int getProviderCount(String pWhereClause) throws Exception {
		String hql = "select count(p.pid) from Provider as p " + pWhereClause;
		return EntityManager.getRecordCount(hql);
	}
	
	public List getProviderBySql(String wheresql) throws Exception {
		String hql = "from Provider as c " + wheresql;
		return EntityManager.getAllByHql(hql);	
	}

	
	public Provider getProviderByNCcode(String nccode,int i) throws Exception {
		String sql = " from Provider as p where nccode='" + nccode+"'";
		Provider temp=new Provider();
		temp=(Provider) EntityManager.find(sql);
		if(temp==null){
			try {
				DBUserLog.addUserLog(i, 13,"基础数据错误，找不到相对应的供应商" + nccode);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		throw new NotExistException("基础数据错误，找不到相对应的供应商");
		} 
		return temp;
	}
}
