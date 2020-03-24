package com.winsafe.drp.dao;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.pager2.PageQuery;

public class AppWlmIdcodeLog {

	public List getWlmIdcodeLog(HttpServletRequest request, int pagesize,
			String pWhereClause) throws Exception {
		String sql = "from WlmIdcodeLog  " + pWhereClause
				+ " order by makedate desc";
		return PageQuery.hbmQuery(request, sql, pagesize);
	}
	
	public List getWlmIdcodeLog(String pWhereClause) throws Exception {
		String sql = "from WlmIdcodeLog  " + pWhereClause
				+ " order by makedate desc";
		return EntityManager.getAllByHql(sql);
	}

	public void addWlmIdcodeLog(Object dpd) throws Exception {
		EntityManager.save(dpd);
	}

	public void updWlmIdcodeLog(WlmIdcodeLog dpd) throws Exception {
		EntityManager.update(dpd);
	}

	public void delWlmIdcodeLog(int id) throws Exception {
		String sql = "delete from WlmIdcodeLog where id=" + id + "";
		EntityManager.updateOrdelete(sql);

	}

	public WlmIdcodeLog getWlmIdcodeLogByID(int id) throws Exception {
		String sql = " from WlmIdcodeLog where id=" + id + "";
		return (WlmIdcodeLog) EntityManager.find(sql);
	}
	
	public boolean isAready(WlmIdcodeLog wil) throws Exception{
		String sql = "select count(*) from WlmIdcodeLog where province="+wil.getProvince()+
		" and wlmidcode='"+wil.getWlmidcode()+"' and makeid="+wil.getMakeid()+
		" and CONVERT(varchar(100), makedate, 23)='"+DateUtil.formatDate(wil.getMakedate())+"'";
		int count = EntityManager.getRecordCount(sql);
		if ( count  > 1 ){
			return true;
		}
		return false;
	}
	
	public void insertWlmIdcodeLog(WlmIdcodeLog wil) throws Exception{
		String sql = "insert wlm_idcode_log(province,city,areas,wlmidcode,warehouseid, "+
		"cid,syncode,cname,productid,productname,specmode,organid,makeorganid,makedeptid,makeid,makedate) "+
		"select "+wil.getProvince()+","+wil.getCity()+","+wil.getAreas()+",'"+wil.getWlmidcode()+"',"+
		" warehouseid,cid,syncode,cname,productid,productname,specmode,organid,'"+wil.getMakeorganid()+"',"+
		wil.getMakedeptid()+","+wil.getMakeid()+",getdate() "+
		"from view_wlm_idcode where '"+wil.getWlmidcode()+"'  between startno and endno";
		EntityManager.updateOrdelete(sql);
	}
	
	/**
	 * 获取WlmIdcodeLog表中最大ID+1
	 * 
	 * @author jason.huang
	 * @return int
	 */
	public int getMaxWlmIdcodeLogId() {
		String sql = "SELECT MAX(id) + 1 from WlmIdcodeLog";
		return EntityManager.getRecordCount(sql);
	}
	
	

	
}
