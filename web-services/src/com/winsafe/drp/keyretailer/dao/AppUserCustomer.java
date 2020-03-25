package com.winsafe.drp.keyretailer.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.HibernateException;

import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.keyretailer.pojo.UserCustomer;
import com.winsafe.drp.metadata.UserType;
import com.winsafe.hbm.util.pager2.PageQuery;
public class AppUserCustomer {
	
	public void addUserCustomer(UserCustomer d) throws Exception {		
		EntityManager.save(d);		
	}
	
	public void updUserCustomer(UserCustomer d) throws Exception {		
		EntityManager.update(d);		
	}
	
	public List getUserCustomer(HttpServletRequest request, int pageSize, String whereSql) throws Exception{
		StringBuffer sql = new StringBuffer();
		sql.append("select uc.id, o.id oid,o.OECODE,o.ORGANNAME,o.ORGANTYPE,o.ORGANMODEL from USER_CUSTOMER uc  ");
		sql.append("join organ o on o.id = UC.ORGANID ");
		sql.append("and UC.USERID = "+request.getParameter("userid")+" ");
		sql.append(whereSql);
		if(pageSize == 0) {
			sql.append(" order by uc.id desc");
			return EntityManager.jdbcquery(sql.toString());
		} else {
			return PageQuery.jdbcSqlserverQuery(request, "id desc", sql.toString(), pageSize);
		}
	}

	public List<Map<String, String>> getOrgansToAdd(
			HttpServletRequest request, int pagesize, String blur) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select o.id oid,o.OECODE,o.ORGANNAME,o.ORGANTYPE,o.ORGANMODEL from ORGAN o where o.ORGANTYPE = 2 and o.ORGANMODEL in (1,2) ");
		sql.append("and o.id not in (select organid from USER_CUSTOMER where USERID = "+request.getParameter("userid")+") ");
		sql.append(blur);
		return PageQuery.jdbcSqlserverQuery(request, "oid desc", sql.toString(), pagesize);
	}

	public void addUserCustomer(String organIds, String userId) throws HibernateException, SQLException, Exception {
		String sql = "INSERT INTO USER_CUSTOMER " +
				"select BCS_RI_COMMON_SEQ.nextval,"+userId+",id,sysdate from Organ o " +
				"where o.id in ("+organIds+") AND not EXISTS (select id from USER_CUSTOMER where userid = "+userId+" and organid = o.id) ";
		EntityManager.executeUpdate(sql);		
	}

	public void delUserCustomerById(String id) throws HibernateException, SQLException, Exception {
		String sql ="delete from USER_CUSTOMER where id=" + id;
		EntityManager.executeUpdate(sql);
	}

	public void addAllUserCustomer(HttpServletRequest request, String userId, String blur) throws HibernateException, SQLException, Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("INSERT INTO USER_CUSTOMER ");
		sql.append("select BCS_RI_COMMON_SEQ.nextval,"+userId+",o.id,sysdate from ORGAN o ");
		sql.append("where o.ORGANTYPE = 2 and o.ORGANMODEL in (1,2) ");
		sql.append("and o.id not in (select organid from USER_CUSTOMER where USERID = "+userId+") ");
		sql.append(blur);
		EntityManager.executeUpdate(sql.toString());
	}
	
	public List getOrganSalesman(HttpServletRequest request, int pageSize, String whereSql) throws Exception{
		StringBuffer sql = new StringBuffer();
		sql.append("select u.userid,u.loginname,u.realname,u.usertype from USER_CUSTOMER uc ");
		sql.append("join USERS u on u.userid = uc.userid ");
		sql.append("and uc.organid = '"+request.getParameter("OID")+"' ");
		sql.append(whereSql);
		if(pageSize == 0) {
			sql.append(" order by uc.id desc");
			return EntityManager.jdbcquery(sql.toString());
		} else {
			return PageQuery.jdbcSqlserverQuery(request, "userid desc", sql.toString(), pageSize);
		}
	}

	public List<Map<String,String>> getSSAndSR(int userId, String organId) throws Exception{
		StringBuffer sql = new StringBuffer();
		sql.append("select distinct u.userid,u.loginname username,u.realname,u.usertype typeId,u.mobile from S_USER_AREA sua ");
		sql.append("join S_BONUS_AREA sba on sua.areaid = sba.parentid ");
		sql.append("and sua.userid ="+userId);
		sql.append(" join S_USER_AREA sua2 on SUA2.AREAID = sba.id ");
		
		sql.append("join USERS u on u.userid = sua2.userid ");
		sql.append("and u.usertype in ("+UserType.SS.getValue()+","+UserType.SR.getValue()+") ");
		sql.append("and not exists ( ");
		sql.append("select * from USER_CUSTOMER where userid = u.userid and organid = '"+organId+"')");
		return EntityManager.jdbcquery(sql.toString());
	}

	public UserCustomer getUserCustomer(String userId, String organId) {
		String sql = " from UserCustomer where userId = "+userId+" and organId='"+organId+"'";
		return (UserCustomer)EntityManager.find(sql);
	}

	public UserCustomer getUserCustomer(String organId) {
		String sql = " from UserCustomer where organId='"+organId+"'";
		return (UserCustomer)EntityManager.find(sql);
	}

	public void delUserCustomerByOrganId(String organId) throws Exception {
		String sql ="delete from USER_CUSTOMER where organid='"+organId+"'";
		EntityManager.executeUpdate(sql);
	}
	public List<UserCustomer> getUserCustomerByUserid(String userid) throws Exception{
		String sql="select u from UserCustomer u where userId= '"+userid+"'";
		return EntityManager.getAllByHql(sql);
	}
}

