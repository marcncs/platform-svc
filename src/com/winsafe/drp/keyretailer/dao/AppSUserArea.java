package com.winsafe.drp.keyretailer.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.HibernateException;

import com.winsafe.drp.dao.RegionItemInfo;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.keyretailer.pojo.SUserArea;
import com.winsafe.drp.metadata.UserType;
import com.winsafe.drp.util.Constants;
import com.winsafe.hbm.util.pager2.PageQuery;
public class AppSUserArea {
	
	public void addSUserArea(SUserArea d) throws Exception {		
		EntityManager.save(d);		
	}
	
	public void updSUserArea(SUserArea d) throws Exception {		
		EntityManager.update(d);		
	}
	
	public List getSUserArea(HttpServletRequest request, int pageSize, String whereSql) throws Exception{
		StringBuffer sql = new StringBuffer();
		sql.append("select sua.id,u.userid,u.LOGINNAME,u.REALNAME,SUA.AREAID,sba.name_loc areaName,u.userType,o.organname from S_USER_AREA sua ");
		sql.append("join users u on u.USERID = SUA.USERID ");
		sql.append("join organ o on u.makeorganid = o.ID ");
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
		}
		return PageQuery.jdbcSqlserverQuery(request, "id desc", sql.toString(), pageSize);
	}

	public List<Map<String, String>> getUsersToAddToArea(
			HttpServletRequest request, int pagesize, String whereSql) throws Exception {
		String sql = "select USERID,LOGINNAME,REALNAME,USERTYPE from users where USERTYPE in ("+UserType.SS.getValue()+","+UserType.SR.getValue()+","+UserType.ASM.getValue()+","+UserType.RM.getValue()+") and USERID not in (" +
				"select USERID from S_USER_AREA) " + whereSql;
		return PageQuery.jdbcSqlserverQuery(request, "LOGINNAME", sql.toString(), pagesize);
	}

	public void addSUserAreas(String userIds, String areaid) throws HibernateException, SQLException, Exception {
		String sql = "INSERT INTO S_USER_AREA " +
				"select S_USER_AREA_SEQ.nextval,userid,"+areaid+",null,SYSDATE,0 from USERS u " +
				"where u.userid in ("+userIds+") and not EXISTS (select id from S_USER_AREA where userid = u.userid) ";
		EntityManager.executeUpdate(sql);
	}
	
	public void delSUserAreaById(String id) throws HibernateException, SQLException, Exception {
		String sql ="delete from S_USER_AREA where id=" + id;
		EntityManager.executeUpdate(sql);
	}

	public void addAllSUserAreas(String areaid) throws HibernateException, SQLException, Exception {
		String sql = "INSERT INTO S_USER_AREA " +
		"select S_USER_AREA_SEQ.nextval,userid,"+areaid+",null,SYSDATE,0 from USERS u " +
		"where not EXISTS (select id from S_USER_AREA where userid = u.userid) ";
		EntityManager.executeUpdate(sql);
	}

	public Set<String> getUserIdSet(String userIds) throws HibernateException, SQLException {
		String sql = "select userId from Users where userId in ("+userIds+")";
		List<Map<String,String>> idList = EntityManager.jdbcquery(sql);
		Set<String> idSet = new HashSet<String>();
		for(Map<String,String> map : idList) {
			idSet.add(map.get("userid"));
		}
		return idSet;
	}

	public void updSUserAreaByBatch(List<String> batchSqls) {
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

	public List<RegionItemInfo> getAreaByUserId(Integer userid) {
		String hql = "select rii from RegionItemInfo rii, SUserArea sua where rii.id = sua.areaId and sua.userId="+userid;
		return EntityManager.getAllByHql(hql);
	}

	public boolean isAll(Integer userid) { 
		String sql = "select count(id) from S_USER_AREA where AREAID = -1 and USERID = "+userid;
		return EntityManager.getRecordCountBySql(sql) > 0;
	} 
}

