package com.winsafe.drp.keyretailer.dao;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.HibernateException;

import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.keyretailer.pojo.SalesAreaCountry;
import com.winsafe.hbm.util.StringUtil;
import com.winsafe.hbm.util.pager2.PageQuery;
public class AppSalesAreaCountry {
	
	public void addSalesAreaCountry(SalesAreaCountry d) throws Exception {		
		EntityManager.save(d);		
	}
	
	public void updSalesAreaCountry(SalesAreaCountry d) throws Exception {		
		EntityManager.update(d);		
	}
	
	public List getSalesAreaCountry(HttpServletRequest request, int pageSize, String whereSql) throws Exception{
		StringBuffer sql = new StringBuffer();
		sql.append("select sba3.name_loc name1,sba2.name_loc name2,sba.name_loc name3,sac.id,CA.AREANAME province,CA2.AREANAME city,ca3.areaname area from SALES_AREA_COUNTRY sac  ");
		sql.append("join COUNTRY_AREA ca3 on CA3.ID = SAC.COUNTRYAREAID ");
		if(!StringUtil.isEmpty(request.getParameter("pid")) && !"-1".equals(request.getParameter("pid"))) {
			sql.append("and sac.salesareaid in ( ");
			sql.append("select AREAID from S_BONUS_AREA ");
			sql.append("START WITH AREAID ="+request.getParameter("pid"));
			sql.append(" CONNECT BY PRIOR AREAID=PARENTID) ");
			
		}
		sql.append("join COUNTRY_AREA ca2 on ca2.id = ca3.parentid ");
		sql.append("join COUNTRY_AREA ca on ca.id = ca2.parentid ");
		if(!StringUtil.isEmpty(request.getParameter("province"))) {
			sql.append("and ca.id='"+request.getParameter("province")+"' ");
		}
		if(!StringUtil.isEmpty(request.getParameter("city"))) {
			sql.append("and ca2.id='"+request.getParameter("city")+"' ");
		}
		sql.append("join S_BONUS_AREA sba on sba.id = sac.salesareaid ");
		sql.append("join S_BONUS_AREA sba2 on sba2.id = sba.parentid ");
		sql.append("join S_BONUS_AREA sba3 on sba3.id = sba2.parentid ");
		sql.append(whereSql);
		if(pageSize == 0) {
			sql.append(" order by sba3.name_loc ,sba2.name_loc ,sba.name_loc ");
			return EntityManager.jdbcquery(sql.toString());
		} else {
			return PageQuery.jdbcSqlserverQuery(request, "id desc", sql.toString(), pageSize);
		}
	}

	public List<Map<String, String>> getCountryAresToAdd(
			HttpServletRequest request, int pagesize, String blur) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select ca3.id,CA.AREANAME province,CA2.AREANAME city,ca3.areaname area from COUNTRY_AREA ca  ");
		sql.append("join COUNTRY_AREA ca2 on CA.ID=CA2.PARENTID and CA.RANK = 0 ");
		if(!StringUtil.isEmpty(request.getParameter("province"))) {
			sql.append("and ca.id='"+request.getParameter("province")+"' ");
		}
		if(!StringUtil.isEmpty(request.getParameter("city"))) {
			sql.append("and ca2.id='"+request.getParameter("city")+"' ");
		}
		sql.append("join COUNTRY_AREA ca3 on CA2.ID=CA3.PARENTID ");
		sql.append("where ca3.id not in ( ");
		sql.append("select COUNTRYAREAID from SALES_AREA_COUNTRY) ");
		sql.append(blur);
		return PageQuery.jdbcSqlserverQuery(request, "province asc", sql.toString(), pagesize);
	}

	public void addSalesAreaCountry(String countryAreaIds, String salesAreaId) throws HibernateException, SQLException, Exception {
		String sql = "INSERT INTO SALES_AREA_COUNTRY " +
				"select BCS_RI_COMMON_SEQ.nextval,"+salesAreaId+",id,sysdate from COUNTRY_AREA ca " +
				"where id in ("+countryAreaIds+") AND not EXISTS (select id from SALES_AREA_COUNTRY where countryareaid = ca.id) ";
		EntityManager.executeUpdate(sql);		
	}

	public void delSalesAreaCountryById(String id) throws HibernateException, SQLException, Exception {
		String sql ="delete from SALES_AREA_COUNTRY where id=" + id;
		EntityManager.executeUpdate(sql);
	}

	public void addAllSalesAreaCountry(HttpServletRequest request, String areaid, String blur) throws HibernateException, SQLException, Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("INSERT INTO SALES_AREA_COUNTRY ");
		sql.append("select BCS_RI_COMMON_SEQ.nextval,"+areaid+",ca3.id,sysdate from COUNTRY_AREA ca ");
		sql.append("join COUNTRY_AREA ca2 on CA.ID=CA2.PARENTID and CA.RANK = 0 ");
		if(!StringUtil.isEmpty(request.getParameter("province"))) {
			sql.append("and ca.id='"+request.getParameter("province")+"' ");
		}
		if(!StringUtil.isEmpty(request.getParameter("city"))) {
			sql.append("and ca2.id='"+request.getParameter("city")+"' ");
		}
		sql.append("join COUNTRY_AREA ca3 on CA2.ID=CA3.PARENTID ");
		sql.append("where not EXISTS (select id from SALES_AREA_COUNTRY where countryareaid = ca3.id) ");
		sql.append(blur);
		EntityManager.executeUpdate(sql.toString());
	}

	public Map<String, String[]> getCountrySalesAreaNamesMap() throws HibernateException, SQLException {
		String sql = "select countryareaid areaid,sba.name_loc name1,sba2.name_loc name2,sba3.name_loc name3 from Sales_Area_Country sac " +
				"join S_BONUS_AREA sba on sba.id=sac.salesareaid " +
				"join S_BONUS_AREA sba2 on sba2.id = sba.parentid " +
				"join S_BONUS_AREA sba3 on sba3.id = sba2.parentid";
		List<Map<String,String>> list = EntityManager.jdbcquery(sql);
		Map<String, String[]> areaNameMap = new HashMap<String, String[]>();
		for(Map<String,String> map : list) {
			areaNameMap.put(map.get("areaid"), new String[]{map.get("name1"),map.get("name2"),map.get("name3")});
		}
		return areaNameMap;
	}

	public List<Map<String, String>> getCountryAreasBySalesAreaId(
			String regionId) throws Exception {
		String sql = "select id,areaname name from COUNTRY_AREA where id in ( " +
				"select countryareaid from SALES_AREA_COUNTRY where salesareaid="+regionId+")";
		return EntityManager.jdbcquery(sql);
	}

}

