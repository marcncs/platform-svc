package com.winsafe.drp.keyretailer.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.HibernateException;

import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.keyretailer.pojo.SCustomerArea;
import com.winsafe.drp.util.Constants;
import com.winsafe.hbm.util.StringUtil;
import com.winsafe.hbm.util.pager2.PageQuery;
public class AppSCustomerArea {
	
	public void addSCustomerArea(SCustomerArea d) throws Exception {		
		EntityManager.save(d);		
	}
	
	public void updSCustomerArea(SCustomerArea d) throws Exception {		
		EntityManager.update(d);		
	}
	
	public List getSCustomerArea(HttpServletRequest request, int pageSize, String whereSql) throws Exception{
		StringBuffer sql = new StringBuffer();
		sql.append("select sua.id,o.id OID,o.ORGANNAME ONAME,o.ORGANMODEL,o.ISKEYRETAILER,SUA.AREAID,sba.name_loc areaName,po.organname poname from S_CUSTOMER_AREA sua  ");
		sql.append("join organ o on o.ID = SUA.ORGANID  ");
		if(!StringUtil.isEmpty(request.getParameter("organModel"))) {
			sql.append("and o.organModel ="+request.getParameter("organModel"));
		}
		sql.append(" join organ po on o.parentid = po.id  ");
		if(!"-1".equals(request.getParameter("pid"))) {
			sql.append("and SUA.AREAID in ( ");
			sql.append("select AREAID from S_BONUS_AREA ");
			sql.append("START WITH AREAID ="+request.getParameter("pid"));
			sql.append(" CONNECT BY PRIOR AREAID=PARENTID) ");
		}
		sql.append("join S_BONUS_AREA sba on sba.areaid = sua.areaid ");
		sql.append(whereSql);
		if(pageSize == 0) {
			sql.append(" order by sua.id desc");
			return EntityManager.jdbcquery(sql.toString());
		} else {
			return PageQuery.jdbcSqlserverQuery(request, "id desc", sql.toString(), pageSize);
		}
	}

	public List<Map<String, String>> getOrgansToAddToArea(
			HttpServletRequest request, int pagesize, String blur) throws Exception {
		String sql = "select o.id,o.ORGANNAME oname,o.ORGANMODEL,PO.ORGANNAME poname from organ o " +
				"join organ po on po.id = o.id " +
				"where o.ORGANTYPE = 2 and o.ISKEYRETAILER = 1 and o.id not in ( " +
				"select ORGANID from S_CUSTOMER_AREA) " + blur;
		return PageQuery.jdbcSqlserverQuery(request, "id desc", sql.toString(), pagesize);
	}

	public void addSCustomerAreas(String organIds, String areaid) throws HibernateException, SQLException, Exception {
		String sql = "INSERT INTO S_CUSTOMER_AREA " +
				"select S_CUSTOMER_AREA_SEQ.nextval,o.id,"+areaid+",null,sysdate,0 from organ o " +
				"where o.id in ("+organIds+") AND not EXISTS (select id from S_CUSTOMER_AREA where organid = o.id) ";
		EntityManager.executeUpdate(sql);		
	}

	public void delSCustomerAreaById(String id) throws HibernateException, SQLException, Exception {
		String sql ="delete from S_CUSTOMER_AREA where id=" + id;
		EntityManager.executeUpdate(sql);
	}

	public void addAllSCustomerAreas(String areaid) throws HibernateException, SQLException, Exception {
		String sql = "INSERT INTO S_CUSTOMER_AREA " +
		"select S_CUSTOMER_AREA_SEQ.nextval,o.id,"+areaid+",null,sysdate,0 from organ o " +
		"where not EXISTS (select id from S_CUSTOMER_AREA where organid = o.id) ";
		EntityManager.executeUpdate(sql);
	}

	public Set<String> getOrganIdSet(String organIds) throws HibernateException, SQLException { 
		String sql = "select id from Organ where id in ("+organIds+")";
		List<Map<String,String>> idList = EntityManager.jdbcquery(sql);
		Set<String> idSet = new HashSet<String>();
		for(Map<String,String> map : idList) {
			idSet.add(map.get("id"));
		}
		return idSet;
	}

	public void updSCustomerAreaByBatch(List<String> batchSqls) {
		if(batchSqls.size() <= Constants.DB_BULK_SIZE) {
			EntityManager.executeBatch(batchSqls);	
		} else {
			List<String> sqls = new ArrayList<String>();
			for(int i = 0; i < batchSqls.size(); i++) {
				String sql = batchSqls.get(i);
				sqls.add(sql);
				if(sqls.size() == Constants.DB_BULK_SIZE) {
					EntityManager.executeBatch(sqls);
					sqls.clear();
				}
			}
			if(sqls.size() > 0) {
				EntityManager.executeBatch(sqls);	
			}
		}
	} 
}

