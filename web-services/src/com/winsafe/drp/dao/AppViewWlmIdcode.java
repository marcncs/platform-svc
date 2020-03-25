package com.winsafe.drp.dao;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.winsafe.common.util.StringUtil;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.pager2.PageQuery;

public class AppViewWlmIdcode {

	public List getViewWlmIdcode(HttpServletRequest request, int pagesize, String pWhereClause) throws Exception {
		String hql = " from ViewWlmIdcode  "+ pWhereClause + " order by makedate";
		//System.out.println("-----sql==="+hql);
		return PageQuery.hbmQuery(request, hql, pagesize);
	}
	
	public List getViewWlmIdcode(String pWhereClause) throws Exception {
		String hql = " from ViewWlmIdcode  "+ pWhereClause + " order by makedate";
		//System.out.println("-----sql==="+hql);
		return EntityManager.getAllByHql(hql);
	}
	
	public ViewWlmIdcode getViewWlmIdcodeByWhere(String pWhereClause) throws Exception {
		String hql = " from ViewWlmIdcode  "+ pWhereClause + " order by makedate desc";
		//System.out.println("-----sql==="+hql);
		return (ViewWlmIdcode)EntityManager.find(hql);
	}
	

	public ViewWlmIdcode getLastViewWlmIdcode(String code) throws Exception {
		String hql = "from ViewWlmIdcode  where '"+ code + "' between startno and endno and id not like 'TT%'  order by makedate desc";
//		System.out.println("-----sql==="+hql);
		List<ViewWlmIdcode> list = EntityManager.getAllByHql(hql);
		if(list!=null && !list.isEmpty()){
			return list.get(0);
		}else{
			return null;
		}
	}
	
	public ViewWlmIdcode getLastViewWlmIdcode(String boxcode,String sql) throws Exception {
		String hql = "from ViewWlmIdcode  where ("+ sql + "'"+boxcode+"' between startno and endno)   and id not like 'TT%'  order by makedate desc";
//		System.out.println("-----sql==="+hql);
		List<ViewWlmIdcode> list = EntityManager.getAllByHql(hql);
		if(list!=null && !list.isEmpty()){
			return list.get(0);
		}else{
			return null;
		}
	}

	public ViewWlmIdcode getViewWlmIdcodeByWhere(HttpServletRequest request, int pagesize, String pWhereClause) throws Exception {
		String hql = " from ViewWlmIdcode  "+ pWhereClause + " and cname<>'' order by makedate desc";
		List<ViewWlmIdcode> viewWlmIdcodeList = PageQuery.hbmQuery(request, hql, pagesize);
		if(viewWlmIdcodeList.size() > 0){
			return viewWlmIdcodeList.get(0);
		}
		return null;
	}
	
	//按ID汇总
	public List getViewWlmIdcodeByWhereGroup(HttpServletRequest request, int pagesize, String pWhereClause) throws Exception {
		String sql = " select   id, warehouseid, cid, cname, province, nccode, productname, specmode, billname, makedate, producedate, batch, packquantity " +
				     " from  view_wlm_idcode " + pWhereClause + 
				     " group by id, warehouseid, cid, cname, province, nccode, productname, specmode, billname, makedate, producedate, batch, packquantity ";
		return PageQuery.jdbcSqlserverQuery(request, " producedate desc ", sql, pagesize);
	}

	///按ID汇总
	public List getViewWlmIdcodeByWhereGroup(String pWhereClause) throws Exception {
		String sql = " select   id, warehouseid, cid, cname, province, nccode, productname, specmode, billname, makedate, producedate, batch, packquantity " +
	     " from  view_wlm_idcode " + pWhereClause + 
	     " group by id, warehouseid, cid, cname, province, nccode, productname, specmode, billname, makedate, producedate, batch, packquantity " +
	     " order by producedate desc ";
		return EntityManager.jdbcquery(sql);
	}
}
