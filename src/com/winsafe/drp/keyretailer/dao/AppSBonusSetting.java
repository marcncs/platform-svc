package com.winsafe.drp.keyretailer.dao;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.keyretailer.pojo.SBonusSetting;
import com.winsafe.hbm.util.StringUtil;
import com.winsafe.hbm.util.pager2.PageQuery;
public class AppSBonusSetting {
	
	public void addSBonusSetting(SBonusSetting d) throws Exception {		
		EntityManager.save(d);		
	}
	
	public void updSBonusSetting(SBonusSetting d) throws Exception {		
		EntityManager.update(d);		
	}
	
	
	public List getSBonusSetting(HttpServletRequest request, int pageSize, String whereSql) throws Exception{
		String hql="select p from SBonusSetting as p "+whereSql +" order by p.id desc";
		return PageQuery.hbmQuery(request, hql, pageSize);
	}
	
	public List getSBonusSetting(String whereSql) throws Exception{
		String hql="select p from SBonusSetting as p "+whereSql +" order by p.id desc";
		return EntityManager.getAllByHql(hql);
	}
	
	/**
	 * @param year
	 * @param month
	 * @param accountType
	 * @param productName
	 * @param spec
	 * @param id 
	 * @return
	 */
	public boolean isBonusAlreadyExists(Integer year, Integer month,
			Integer accountType, String productName, String spec, String id) {
		String sql = "select count(*) from S_BONUS_SETTING " +
				" where year = "+year+" and month >= "+month+" and accountType = "+accountType+" and productName = '"+productName+"' and spec = '"+spec+"'";
		if(!StringUtil.isEmpty(id)) {
			sql += " and id <> " + id;
		}
		return EntityManager.getRecordCountBySql(sql) > 0;
	}


	public SBonusSetting getSBonusSettingById(String id) {
		String hql="from SBonusSetting where id = " + id;
		return (SBonusSetting)EntityManager.find(hql);
	}

	public void delSBonusSetting(SBonusSetting sbs) {
		EntityManager.delete(sbs);
	}

	public SBonusSetting getSBonusSetting(String productname, String packSizeName,
			int year, int month, Integer organModel) {
		String hql="from SBonusSetting where year = " + year + " and month >= "+month+" and activeFlag = 1 and productName = '"+productname+"' and spec = '"+packSizeName+"' and accountType = " + organModel + " order by month ";
		return (SBonusSetting)EntityManager.find(hql);
	}
	
	public SBonusSetting getSBonusSetting(String productname, String spec,Integer organModel) {
		String hql="from SBonusSetting where   activeFlag = 1 and productName = '"+productname+"' and spec = '"+spec+"' and accountType = " + organModel + " order by month ";
		return (SBonusSetting)EntityManager.find(hql);
	}
	
}

