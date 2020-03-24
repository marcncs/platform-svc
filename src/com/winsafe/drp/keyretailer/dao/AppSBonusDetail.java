package com.winsafe.drp.keyretailer.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.HibernateException;

import com.winsafe.drp.dao.RegionItemInfo;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.keyretailer.pojo.SBonusDetail;
import com.winsafe.drp.keyretailer.pojo.SBonusSetting;
import com.winsafe.drp.metadata.OrganType;
import com.winsafe.hbm.util.StringUtil;
import com.winsafe.hbm.util.pager2.PageQuery;
public class AppSBonusDetail {
	
	public void addSBonusDetail(SBonusDetail d) throws Exception {		
		EntityManager.save(d);		
	}
	
	public void updSBonusSetting(SBonusSetting d) throws Exception {		
		EntityManager.update(d);		
	}
	
	
	/*public List getSBonusDetail(HttpServletRequest request, int pageSize, String whereSql) throws Exception{
		String hql="from SBonusDetail as p "+whereSql +" order by p.id desc";
		return PageQuery.hbmQuery(request, hql, pageSize);
	}*/
	
	public List getSBonusDetail(HttpServletRequest request, int pageSize, String whereSql) throws Exception{
		RegionItemInfo region = null;
		if(!StringUtil.isEmpty(request.getParameter("regionId"))) {
			AppSBonusArea arii = new AppSBonusArea();
			region = arii.getSBonusAreaById(request.getParameter("regionId"));
		}
		StringBuffer sql = new StringBuffer();
		sql.append("select sbd.*,o.organname,oppo.organname opporganname,sba3.name_loc BIGREGION,sba2.name_loc middleregion,sba1.name_loc smallregion from S_Bonus_Detail sbd join Organ o on o.id = sbd.organid join organ oppo on oppo.id = sbd.opporganid ");
		sql.append("LEFT JOIN SALES_AREA_COUNTRY sac on o.areas = sac.countryareaid and o.organmodel not in (1,2) ");
		sql.append("LEFT JOIN S_BONUS_AREA sba1 on sba1.id = sac.salesareaid ");
		sql.append("LEFT JOIN S_BONUS_AREA sba2 on sba2.id = sba1.parentid ");
		sql.append("LEFT JOIN S_BONUS_AREA sba3 on sba3.id = sba2.parentid ");
		if(!StringUtil.isEmpty(whereSql)) {
			sql.append(whereSql);
		} else {
			sql.append("where 1=1 ");
		}
		if(!StringUtil.isEmpty(request.getParameter("year"))) {
			sql.append(" and sbl.year="+request.getParameter("year"));
		}
		if(!StringUtil.isEmpty(request.getParameter("organModel"))) {
			sql.append(" and o.organModel="+request.getParameter("organModel"));
		}
		if(region != null) {
			if(region.getRank() == 2) {
				sql.append(" and sba1.id="+region.getId());
			} else if(region.getRank() == 1) {
				sql.append(" and sba2.id="+region.getId());
			} else if(region.getRank() == 0) {
				sql.append(" and sba3.id="+region.getId());
			}
		}
		if(pageSize == 0) {
			return EntityManager.jdbcquery(sql.toString());
		} else {
			return PageQuery.jdbcSqlserverQuery(request, "id desc", sql.toString(), pageSize);
		}
	}
	
	/**
	 * @param year
	 * @param month
	 * @param accountType
	 * @param productName
	 * @param spec
	 * @return
	 */
	public boolean isBonusDetailAlreadyExists(Integer year, Integer month,
			Integer accountType, String productName, String spec) {
		String sql = "select count(*) from S_BONUS_DETAIL sbd" +
				" where year >= "+year+" and month >= "+month+" and productName = '"+productName+"' and spec = '"+spec+"'" +
						" and exists (select id from organ where id = sbd.organId and organType = "+OrganType.Dealer.getValue()+" and organModel = "+accountType+")";
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

	public List<SBonusDetail> getSBonusDetail(String year, String month,
			String makeorganid, String organId) {
		String hql="from SBonusDetail where year >= " + year + " and month >= "+month+" and organId = '"+makeorganid+"' order by makeDate desc ";
		return EntityManager.getAllByHql(hql);
	}
	
	public List<Map<String,String>> getSBonusDetailsBySql(String year, String month,
			String makeorganid, String organId) throws HibernateException, SQLException {
		StringBuffer sql = new StringBuffer();
		sql.append("select SBD.makedate,o.organName,SBD.bonusType,SBD.productName,SBD.spec,SBD.bonusPoint,SBD.amount,p.countUnit from S_BONUS_DETAIL SBD ");
		sql.append("join organ o on SBD.OPPORGANID = o.id ");
		sql.append("join PRODUCT p on p.mcode = SBD.mcode ");
		//sql.append("where sbd.year >= " + year + " and sbd.month >= "+month+" and sbd.organId = '"+makeorganid+"' ");
		//modify changfu.chen 积分明细查询
		sql.append("where sbd.year = " + year + " and sbd.month = "+month+" and sbd.organId = '"+makeorganid+"' ");
		if(!StringUtil.isEmpty(organId)) {
			sql.append("and SBD.OPPORGANID = '"+organId+"'");
		}
		sql.append(" order by sbd.makedate desc ");
		return EntityManager.jdbcquery(sql.toString());
	}
	
	public SBonusDetail getSBonusDetailById(String id) {
		String hql="from SBonusDetail where id = " + id;
		return (SBonusDetail)EntityManager.find(hql);
	}

	public Map<String, String> getFinalBonusMap(Integer year) throws HibernateException, SQLException { 
		String sql = "select ORGANID,sum(BONUSPOINT) BONUSPOINT,PRODUCTNAME,SPEC from S_BONUS_DETAIL where year ="+year+" GROUP BY ORGANID,PRODUCTNAME,SPEC";
		List<Map<String, String>> list = EntityManager.jdbcquery(sql);
		Map<String,String> finalBonusMap = new HashMap<String, String>();
		for(Map<String, String> map : list) {
			finalBonusMap.put(map.get("organid")+map.get("productname")+map.get("spec"), map.get("bonuspoint"));
		}
		return finalBonusMap;
	} 
	
}

