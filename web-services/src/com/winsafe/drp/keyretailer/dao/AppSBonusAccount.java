package com.winsafe.drp.keyretailer.dao;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.keyretailer.pojo.SBonusAccount;
import com.winsafe.drp.keyretailer.pojo.SBonusSetting;
import com.winsafe.hbm.util.pager2.PageQuery;
public class AppSBonusAccount {
	
	public void addSBonusAccount(SBonusAccount d) throws Exception {		
		EntityManager.save(d);		
	}
	
	public void updSBonusAccount(SBonusAccount d) throws Exception {		
		EntityManager.update(d);		
	}
	
	public SBonusAccount getByOrganId(String organId) {
		String sql = "from SBonusAccount s where s.organId=" + organId;
		return (SBonusAccount) EntityManager.find(sql);
	}
	
	public List getSBonusAccount(HttpServletRequest request, int pageSize, String whereSql) throws Exception{
		String hql="from SBonusAccount "+whereSql;
		return PageQuery.hbmQuery(request, hql, pageSize);
	}
	
	/**
	 * @param year
	 * @param month
	 * @param accountType
	 * @param productName
	 * @param spec
	 * @return
	 */
	public boolean isBonusAlreadyExists(Integer year, Integer month,
			Integer accountType, String productName, String spec) {
		String sql = "select count(*) from S_BONUS_SETTING " +
				" where year = "+year+" and month >= "+month+" and accountType = "+accountType+" and productName = '"+productName+"' and spec = '"+spec+"'";
		return EntityManager.getRecordCountBySql(sql) > 0;
	}


	public SBonusSetting getSBonusSettingById(String id) {
		String hql="from SBonusSetting where id = " + id;
		return (SBonusSetting)EntityManager.find(hql);
	}

	public void delSBonusSetting(SBonusSetting sbs) {
		EntityManager.delete(sbs);
	}

	public SBonusAccount getSBonusAccountByOrganId(String organId) {
		String hql="from SBonusAccount where organId = " + organId;
		return (SBonusAccount)EntityManager.find(hql);
	}

	public SBonusAccount getSBonusAccountByAccountId(String accountId) {
		String hql="from SBonusAccount where accountId = " + accountId;
		return (SBonusAccount)EntityManager.find(hql);
	}
	
}

