package com.winsafe.drp.keyretailer.dao;

import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.HibernateException;

import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.keyretailer.pojo.SBonusConfig;
import com.winsafe.hbm.util.StringUtil;
import com.winsafe.hbm.util.pager2.PageQuery;
public class AppSBonusConfig {
	
	public void addSBonusConfig(SBonusConfig d) throws Exception {		
		EntityManager.save(d);		
	}
	
	public void updSBonusConfig(SBonusConfig d) throws Exception {		
		EntityManager.update(d);		
	}
	
	
	public List getSBonusConfig(HttpServletRequest request, int pageSize, String whereSql) throws Exception{
		String hql="select p from SBonusConfig as p "+whereSql +" order by p.id desc";
		return PageQuery.hbmQuery(request, hql, pageSize);
	}


	public SBonusConfig getSBonusConfigById(String id) {
		String hql="from SBonusConfig where id = " + id;
		return (SBonusConfig)EntityManager.find(hql);
	}

	public void delSBonusConfig(SBonusConfig sbs) {
		EntityManager.delete(sbs);
	}

	public boolean isBonusConfigAlreadyExists(SBonusConfig sbs) {
		String sql = "select count(*) from S_BONUS_CONFIG " +
			" where year = "+sbs.getYear()+" and configType = "+sbs.getConfigType();
		return EntityManager.getRecordCountBySql(sql) > 0;
	}

	public List<SBonusConfig> getNotCountedConfig(String curDate) {
		String hql="from SBonusConfig where (isCounted = 0 or isCounted is null) and endDate < to_date('"+curDate+" 00:00:00','yyyy-MM-dd hh24:mi:ss')";
		return EntityManager.getAllByHql(hql);
	}

	public SBonusConfig getCurrentPeriodConfig(String curDate, int type) {
		String hql="from SBonusConfig where startDate <= to_date('"+curDate+" 23:59:59','yyyy-MM-dd hh24:mi:ss') and endDate >= to_date('"+curDate+" 00:00:00','yyyy-MM-dd hh24:mi:ss') and configType ="+type+" order by year";
		return (SBonusConfig)EntityManager.find(hql);
	}
	
	public SBonusConfig getPeriodConfig(String year, String curDate, int type) {
		String hql="from SBonusConfig where year="+year+" and startDate <= to_date('"+curDate+" 23:59:59','yyyy-MM-dd hh24:mi:ss') and endDate >= to_date('"+curDate+" 00:00:00','yyyy-MM-dd hh24:mi:ss') and configType ="+type;
		return (SBonusConfig)EntityManager.find(hql);
	}

	public SBonusConfig getSBonusConfigByYearAndType(Integer year, int type) {
		String hql="from SBonusConfig where year = " + year + " and configType="+type;
		return (SBonusConfig)EntityManager.find(hql);
	}

	public Map<Integer, SBonusConfig> getSBonusConfigYearMapByType(int type) {
		String hql="from SBonusConfig where configType="+type;
		List<SBonusConfig> list = EntityManager.getAllByHql(hql);
		Map<Integer,SBonusConfig> map = new HashMap<Integer, SBonusConfig>();
		for(SBonusConfig sbc : list) {
			map.put(sbc.getYear(), sbc);
		}
		return map;
	}

	public List<Map<String,String>> getYearList() throws HibernateException, SQLException {
//		String sql = "select distinct year from S_Bonus_Config where ISCOUNTED = 1 order by year";
		String sql = "select distinct year from S_Bonus_Config order by year";
		List<Map<String,String>> list = EntityManager.jdbcquery(sql);
		return list;
	}
	
}

