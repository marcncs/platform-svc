package com.winsafe.drp.keyretailer.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.winsafe.drp.dao.BonusReportForm;
import com.winsafe.drp.dao.RegionItemInfo;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.keyretailer.dao.AppSBonusArea;
import com.winsafe.drp.keyretailer.dao.AppSBonusConfig;
import com.winsafe.drp.keyretailer.pojo.SBonusConfig;
import com.winsafe.drp.metadata.DealerType;
import com.winsafe.drp.metadata.OrganType;
import com.winsafe.drp.util.ArithDouble;
import com.winsafe.drp.util.MapUtil;
import com.winsafe.hbm.util.StringUtil;
import com.winsafe.hbm.util.pager2.PageQuery;

public class BonusReportService {
	private static Logger logger = Logger.getLogger(BonusReportService.class);
	private AppSBonusConfig appSBonusConfig = new AppSBonusConfig();
	
	// 根据条件查询 
	public List<BonusReportForm> queryReport(HttpServletRequest request, int pageSize,BonusReportForm scrForm,UsersBean users) throws Exception{
		
		List<BonusReportForm> resultList = new ArrayList<BonusReportForm>();
		String condition = "";
		if(OrganType.Dealer.getValue().equals(users.getOrganType())
				&& DealerType.PD.getValue().equals(users.getOrganModel())) { 
			condition = " o.id in (select distinct TOORGANID from S_BONUS_TARGET where FROMORGANID = '"+users.getMakeorganid()+"') ";
		} else {
			condition = SBonusService.getWhereCondition(users);
		}
		
		RegionItemInfo region = null;
		if(!StringUtil.isEmpty(scrForm.getRegionId())) {
			AppSBonusArea arii = new AppSBonusArea();
			region = arii.getSBonusAreaById(scrForm.getRegionId());
		}
		StringBuffer sql = new StringBuffer();
		
		/*sql.append(" \r\n select ");
		sql.append(" \r\n  TEMP1.YEAR");
		sql.append(" \r\n ,TEMP1.organid");
		sql.append(" \r\n ,o.ORGANNAME");
		sql.append(" \r\n ,sba3.name_loc BIGREGION ");
		sql.append(" \r\n ,sba2.name_loc middleREGION ");
		sql.append(" \r\n ,sba1.name_loc smallREGION");
		sql.append(" \r\n ,CA.areaname");
		sql.append(" \r\n ,TEMP1.CURPOINT ");
		sql.append(" \r\n ,temp2.targetpoint ");
		sql.append(" \r\n ,temp2.issupported ");
		sql.append(" \r\n ,temp2.confirmdate ");
		sql.append(" \r\n ,temp2.finalPoint");
		
		sql.append(" \r\n from (select sbd.YEAR,sbd.ORGANID,sum(sbd.BONUSPOINT) curPoint from S_BONUS_DETAIL sbd ");
		sql.append(" \r\n join S_BONUS_TARGET sbt on sbt.TOORGANID = sbd.organid ");
		sql.append(" \r\n and sbt.PRODUCTNAME = sbd.productname ");
		sql.append(" \r\n and sbt.spec = sbd.spec  ");
		//PD用户
		if(OrganType.Dealer.getValue().equals(users.getOrganType())) {
			sql.append(" and sbt.FROMORGANID='"+users.getMakeorganid()+"' ");
		}
		sql.append(" \r\n where 1=1 ");
		
		if(!StringUtil.isEmpty(scrForm.getOrganId())){
			sql.append(" and sbd.ORGANID = '" + scrForm.getOrganId()+"'");
		}
		if(!StringUtil.isEmpty(scrForm.getYear())){
			sql.append(" and sbd.year = " + scrForm.getYear());
		}
		if(!StringUtil.isEmpty(scrForm.getProductName())){
			sql.append(" and sbd.PRODUCTNAME = '" + scrForm.getProductName()+"'");
		}
		if(!StringUtil.isEmpty(scrForm.getSpec())){
			sql.append(" and sbd.spec = '" + scrForm.getSpec()+"'");
		}
		sql.append(" \r\n GROUP BY sbd.YEAR,sbd.ORGANID) temp1 ");
		sql.append(" \r\n join organ o on o.id = temp1.ORGANID  ");
		sql.append(" and " + condition);
		sql.append(" \r\n LEFT JOIN COUNTRY_AREA ca on ca.id = o.areas ");
		sql.append(" \r\n LEFT JOIN SALES_AREA_COUNTRY sac on o.areas = sac.countryareaid and o.organmodel not in (1,2)");
		sql.append(" \r\n LEFT JOIN S_BONUS_AREA sba1 on sba1.id = sac.salesareaid ");
		sql.append(" \r\n LEFT JOIN S_BONUS_AREA sba2 on sba2.id = sba1.parentid ");
		sql.append(" \r\n LEFT JOIN S_BONUS_AREA sba3 on sba3.id = sba2.parentid ");
		sql.append(" \r\n LEFT JOIN ( ");
		sql.append(" \r\n select toorganid organid,sum(bonuspoint) targetPoint,max(issupported) issupported,max(confirmdate) confirmdate,sum(rebate) finalPoint  from S_BONUS_TARGET ");
		sql.append(" \r\n where 1=1 ");
		//PD用户
		if(OrganType.Dealer.getValue().equals(users.getOrganType())) {
			sql.append(" and FROMORGANID='"+users.getMakeorganid()+"' ");
		}
		if(!StringUtil.isEmpty(scrForm.getOrganId())){
			sql.append(" and toorganid = '" + scrForm.getOrganId()+"'");
		}
		if(!StringUtil.isEmpty(scrForm.getYear())){
			sql.append(" and year = " + scrForm.getYear());
		}
		if(!StringUtil.isEmpty(scrForm.getProductName())){
			sql.append(" and PRODUCTNAME = '" + scrForm.getProductName()+"'");
		}
		if(!StringUtil.isEmpty(scrForm.getSpec())){
			sql.append(" and spec = '" + scrForm.getSpec()+"'");
		}
		sql.append(" \r\n GROUP BY toorganid ");
		sql.append(" \r\n ) temp2 on temp2.ORGANID = temp1.ORGANID "); // 出库仓库为工厂
		sql.append(" \r\n where 1=1 ");
		
		if(region != null) {
			if(region.getRank() == 2) {
				sql.append(" and sba1.id="+region.getId());
			} else if(region.getRank() == 1) {
				sql.append(" and sba2.id="+region.getId());
			} else if(region.getRank() == 0) {
				sql.append(" and sba3.id="+region.getId());
			}
		}
		
		if(!StringUtil.isEmpty(scrForm.getAreaId())) {
			sql.append(" and o.areas="+scrForm.getAreaId());
		}*/
		sql.append(" \r\n select ");
		sql.append(" \r\n  temp3.*");
		sql.append(" \r\n ,o.ORGANNAME");
		sql.append(" \r\n ,sba3.name_loc BIGREGION ");
		sql.append(" \r\n ,sba2.name_loc middleREGION ");
		sql.append(" \r\n ,sba1.name_loc smallREGION");
		sql.append(" \r\n ,CA.areaname");
		
		sql.append(" \r\n from (select ");
		sql.append(" \r\n  case when TEMP1.YEAR is not null then TEMP1.YEAR else TEMP2.YEAR END year ");
		sql.append(" \r\n ,case when TEMP1.organid is not null then TEMP1.organid else TEMP2.organid END organid ");
		sql.append(" \r\n ,TEMP1.CURPOINT ");
		
		sql.append(" \r\n ,temp2.targetpoint ");
		sql.append(" \r\n ,temp2.issupported ");
		sql.append(" \r\n ,temp2.confirmdate ");
		sql.append(" \r\n ,temp2.finalPoint ");
		
		sql.append(" \r\n from (select sbd.YEAR,sbd.ORGANID,sum(sbd.BONUSPOINT) curPoint from S_BONUS_DETAIL sbd ");
		sql.append(" \r\n join S_BONUS_TARGET sbt on sbt.TOORGANID = sbd.organid ");
		sql.append(" \r\n and sbt.PRODUCTNAME = sbd.productname ");
		sql.append(" \r\n and sbt.spec = sbd.spec  ");
		//PD用户
		if(OrganType.Dealer.getValue().equals(users.getOrganType())) {
			sql.append(" and sbt.FROMORGANID='"+users.getMakeorganid()+"' ");
		}
		sql.append(" \r\n where 1=1 ");
		
		if(!StringUtil.isEmpty(scrForm.getOrganId())){
			sql.append(" and sbd.ORGANID = '" + scrForm.getOrganId()+"'");
		}
		if(!StringUtil.isEmpty(scrForm.getYear())){
			sql.append(" and sbd.year = " + scrForm.getYear());
		}
		if(!StringUtil.isEmpty(scrForm.getProductName())){
			sql.append(" and sbd.PRODUCTNAME = '" + scrForm.getProductName()+"'");
		}
		if(!StringUtil.isEmpty(scrForm.getSpec())){
			sql.append(" and sbd.spec = '" + scrForm.getSpec()+"'");
		}
		sql.append(" \r\n GROUP BY sbd.YEAR,sbd.ORGANID) temp1 ");
		sql.append(" \r\n FULL JOIN ( ");
		sql.append(" \r\n select year,toorganid organid,sum(bonuspoint) targetPoint,max(issupported) issupported,max(confirmdate) confirmdate,sum(rebate) finalPoint  from S_BONUS_TARGET ");
		sql.append(" \r\n where 1=1 ");
		//PD用户
		if(OrganType.Dealer.getValue().equals(users.getOrganType())) {
			sql.append(" and FROMORGANID='"+users.getMakeorganid()+"' ");
		}
		if(!StringUtil.isEmpty(scrForm.getOrganId())){
			sql.append(" and toorganid = '" + scrForm.getOrganId()+"'");
		}
		if(!StringUtil.isEmpty(scrForm.getYear())){
			sql.append(" and year = " + scrForm.getYear());
		}
		if(!StringUtil.isEmpty(scrForm.getProductName())){
			sql.append(" and PRODUCTNAME = '" + scrForm.getProductName()+"'");
		}
		if(!StringUtil.isEmpty(scrForm.getSpec())){
			sql.append(" and spec = '" + scrForm.getSpec()+"'");
		}
		sql.append(" \r\n GROUP BY year,toorganid ");
		sql.append(" \r\n ) temp2 on temp2.ORGANID = temp1.ORGANID and temp2.year = temp1.year "); // 出库仓库为工厂
		sql.append(" \r\n ) temp3 ");
		sql.append(" \r\n join organ o on o.id = temp3.ORGANID  ");
		sql.append(" and " + condition);
		sql.append(" \r\n LEFT JOIN COUNTRY_AREA ca on ca.id = o.areas ");
		sql.append(" \r\n LEFT JOIN SALES_AREA_COUNTRY sac on o.areas = sac.countryareaid and o.organmodel not in (1,2)");
		sql.append(" \r\n LEFT JOIN S_BONUS_AREA sba1 on sba1.id = sac.salesareaid ");
		sql.append(" \r\n LEFT JOIN S_BONUS_AREA sba2 on sba2.id = sba1.parentid ");
		sql.append(" \r\n LEFT JOIN S_BONUS_AREA sba3 on sba3.id = sba2.parentid ");
		sql.append(" \r\n where 1=1 ");
		if(region != null) {
			if(region.getRank() == 2) {
				sql.append(" and sba1.id="+region.getId());
			} else if(region.getRank() == 1) {
				sql.append(" and sba2.id="+region.getId());
			} else if(region.getRank() == 0) {
				sql.append(" and sba3.id="+region.getId());
			}
		}
		if(!StringUtil.isEmpty(scrForm.getAreaId())) {
			sql.append(" and o.areas="+scrForm.getAreaId());
		}
		
		System.out.println(sql.toString());
		List<Map> list =  new ArrayList<Map>();
		if(request == null && pageSize == 0){
			list =  EntityManager.jdbcquery(sql.toString());
		}else {
			list =  PageQuery.jdbcSqlserverQuery(request, "year,organId", sql.toString(), pageSize);
		}
		if(list != null && list.size()>0){
			
			Map<Integer,SBonusConfig> reachRateMap = appSBonusConfig.getSBonusConfigYearMapByType(3);
			
			for(Map map : list){
				BonusReportForm scForm = new BonusReportForm();
				//将Map中对应的值赋值给实例
				MapUtil.mapToObject(map, scForm);
				
				if(!StringUtil.isEmpty(scForm.getTargetPoint())
						&& !"0".equals(scForm.getTargetPoint())
						&& !StringUtil.isEmpty(scForm.getCurPoint())) {
					double targetPoint = Double.valueOf(scForm.getTargetPoint());
					double curPoint = Double.valueOf(scForm.getCurPoint());
					Double completeRate = ArithDouble.mul(ArithDouble.div(curPoint, targetPoint, 4), 100);
					scForm.setCompleteRate(completeRate.toString());
					SBonusConfig reachRateConfig = reachRateMap.get(Integer.parseInt(scForm.getYear()));
					if(reachRateConfig != null && completeRate >= Double.valueOf(reachRateConfig.getValue())) {
						scForm.setIsReached("1");
					} else {
						scForm.setIsReached("0");
					}
				} else {
					scForm.setCompleteRate("0");
					scForm.setIsReached("0");
				}
				
				if(StringUtil.isEmpty(scForm.getIsSupported())) {
					scForm.setIsSupported("0");
				}
				resultList.add(scForm);
			}
		}
		return resultList;
	}
	
	public List<BonusReportForm> queryDetailReport(HttpServletRequest request, int pageSize,BonusReportForm scrForm,UsersBean users) throws Exception{
		
		List<BonusReportForm> resultList = new ArrayList<BonusReportForm>();
		String condition = "";
		if(OrganType.Dealer.getValue().equals(users.getOrganType())
				&& DealerType.PD.getValue().equals(users.getOrganModel())) { 
			condition = " o.id in (select distinct TOORGANID from S_BONUS_TARGET where FROMORGANID = '"+users.getMakeorganid()+"') ";
		} else {
			condition = SBonusService.getWhereCondition(users);
		}
		
		RegionItemInfo region = null;
		if(!StringUtil.isEmpty(scrForm.getRegionId())) {
			AppSBonusArea arii = new AppSBonusArea();
			region = arii.getSBonusAreaById(scrForm.getRegionId());
		}
		StringBuffer sql = new StringBuffer();
		
		/*sql.append(" \r\n select ");
		sql.append(" \r\n  TEMP1.YEAR");
		sql.append(" \r\n ,TEMP1.organid");
		sql.append(" \r\n ,o.ORGANNAME");
		sql.append(" \r\n ,sba3.name_loc BIGREGION ");
		sql.append(" \r\n ,sba2.name_loc middleREGION ");
		sql.append(" \r\n ,sba1.name_loc smallREGION");
		sql.append(" \r\n ,CA.areaname");
		sql.append(" \r\n ,TEMP1.CURPOINT ");
		sql.append(" \r\n ,temp2.targetpoint ");
		sql.append(" \r\n ,temp2.issupported ");
		sql.append(" \r\n ,temp2.confirmdate ");
		sql.append(" \r\n ,temp2.finalPoint");
		sql.append(" \r\n ,temp1.productname,temp1.spec ");
		
		sql.append(" \r\n from (select sbd.YEAR,sbd.ORGANID,sum(sbd.BONUSPOINT) curPoint,sbd.productname,sbd.spec from S_BONUS_DETAIL sbd ");
		sql.append(" \r\n join S_BONUS_TARGET sbt on sbt.TOORGANID = sbd.organid ");
		sql.append(" \r\n and sbt.PRODUCTNAME = sbd.productname ");
		sql.append(" \r\n and sbt.spec = sbd.spec  ");
		//PD用户
		if(OrganType.Dealer.getValue().equals(users.getOrganType())) {
			sql.append(" and sbt.FROMORGANID='"+users.getMakeorganid()+"' ");
		}
		sql.append(" \r\n where 1=1 ");
		if(!StringUtil.isEmpty(scrForm.getOrganId())){
			sql.append(" and ORGANID = '" + scrForm.getOrganId()+"'");
		}
		if(!StringUtil.isEmpty(scrForm.getYear())){
			sql.append(" and year = " + scrForm.getYear());
		}
		if(!StringUtil.isEmpty(scrForm.getProductName())){
			sql.append(" and PRODUCTNAME = '" + scrForm.getProductName()+"'");
		}
		if(!StringUtil.isEmpty(scrForm.getSpec())){
			sql.append(" and spec = '" + scrForm.getSpec()+"'");
		}
		sql.append(" \r\n GROUP BY sbd.YEAR,sbd.ORGANID,sbd.productname,sbd.spec) temp1 ");
		sql.append(" \r\n join organ o on o.id = temp1.ORGANID  ");
		sql.append(" and " + condition);
		sql.append(" \r\n LEFT JOIN COUNTRY_AREA ca on ca.id = o.areas ");
		sql.append(" \r\n LEFT JOIN SALES_AREA_COUNTRY sac on o.areas = sac.countryareaid and o.organmodel not in (1,2) ");
		sql.append(" \r\n LEFT JOIN S_BONUS_AREA sba1 on sba1.id = sac.salesareaid ");
		sql.append(" \r\n LEFT JOIN S_BONUS_AREA sba2 on sba2.id = sba1.parentid ");
		sql.append(" \r\n LEFT JOIN S_BONUS_AREA sba3 on sba3.id = sba2.parentid ");
		sql.append(" \r\n LEFT JOIN ( ");
		sql.append(" \r\n select toorganid organid,sum(bonuspoint) targetPoint,max(issupported) issupported,max(confirmdate) confirmdate,sum(rebate) finalPoint,productname,spec  from S_BONUS_TARGET ");
		sql.append(" \r\n where 1=1 ");
		//PD用户
		if(OrganType.Dealer.getValue().equals(users.getOrganType())) {
			sql.append(" and FROMORGANID='"+users.getMakeorganid()+"' ");
		}
		if(!StringUtil.isEmpty(scrForm.getOrganId())){
			sql.append(" and toorganid = '" + scrForm.getOrganId()+"'");
		}
		if(!StringUtil.isEmpty(scrForm.getYear())){
			sql.append(" and year = " + scrForm.getYear());
		}
		if(!StringUtil.isEmpty(scrForm.getProductName())){
			sql.append(" and PRODUCTNAME = '" + scrForm.getProductName()+"'");
		}
		if(!StringUtil.isEmpty(scrForm.getSpec())){
			sql.append(" and spec = '" + scrForm.getSpec()+"'");
		}
		sql.append(" \r\n GROUP BY toorganid,productname,spec ");
		sql.append(" \r\n ) temp2 on temp2.ORGANID = temp1.ORGANID and temp2.productname = temp1.productname and temp2.spec = temp1.spec "); // 出库仓库为工厂
		sql.append(" \r\n where 1=1 ");
		if(region != null) {
			if(region.getRank() == 2) {
				sql.append(" and sba1.id="+region.getId());
			} else if(region.getRank() == 1) {
				sql.append(" and sba2.id="+region.getId());
			} else if(region.getRank() == 0) {
				sql.append(" and sba3.id="+region.getId());
			}
		}
		
		if(!StringUtil.isEmpty(scrForm.getAreaId())) {
			sql.append(" and o.areas="+scrForm.getAreaId());
		}*/
		
		sql.append(" \r\n select ");
		sql.append(" \r\n  temp3.*");
		sql.append(" \r\n ,o.ORGANNAME");
		sql.append(" \r\n ,sba3.name_loc BIGREGION ");
		sql.append(" \r\n ,sba2.name_loc middleREGION ");
		sql.append(" \r\n ,sba1.name_loc smallREGION");
		sql.append(" \r\n ,CA.areaname");
		
		sql.append(" \r\n from (select ");
		sql.append(" \r\n  case when TEMP1.YEAR is not null then TEMP1.YEAR else TEMP2.YEAR END year ");
		sql.append(" \r\n ,case when TEMP1.organid is not null then TEMP1.organid else TEMP2.organid END organid ");
		sql.append(" \r\n ,TEMP1.CURPOINT ");
		
		sql.append(" \r\n ,temp2.targetpoint ");
		sql.append(" \r\n ,temp2.issupported ");
		sql.append(" \r\n ,temp2.confirmdate ");
		sql.append(" \r\n ,temp2.finalPoint ");
		
		sql.append(" \r\n ,case when TEMP1.productname is not null then TEMP1.productname else TEMP2.productname END productname");
		sql.append(" \r\n ,case when TEMP1.spec is not null then TEMP1.spec else TEMP2.spec END spec");
		
		sql.append(" \r\n from (select sbd.YEAR,sbd.ORGANID,sum(sbd.BONUSPOINT) curPoint,sbd.productname,sbd.spec from S_BONUS_DETAIL sbd ");
		sql.append(" \r\n join S_BONUS_TARGET sbt on sbt.TOORGANID = sbd.organid ");
		sql.append(" \r\n and sbt.PRODUCTNAME = sbd.productname ");
		sql.append(" \r\n and sbt.spec = sbd.spec  ");
		//PD用户
		if(OrganType.Dealer.getValue().equals(users.getOrganType())) {
			sql.append(" and sbt.FROMORGANID='"+users.getMakeorganid()+"' ");
		}
		sql.append(" \r\n where 1=1 ");
		
		if(!StringUtil.isEmpty(scrForm.getOrganId())){
			sql.append(" and sbd.ORGANID = '" + scrForm.getOrganId()+"'");
		}
		if(!StringUtil.isEmpty(scrForm.getYear())){
			sql.append(" and sbd.year = " + scrForm.getYear());
		}
		if(!StringUtil.isEmpty(scrForm.getProductName())){
			sql.append(" and sbd.PRODUCTNAME = '" + scrForm.getProductName()+"'");
		}
		if(!StringUtil.isEmpty(scrForm.getSpec())){
			sql.append(" and sbd.spec = '" + scrForm.getSpec()+"'");
		}
		sql.append(" \r\n GROUP BY sbd.YEAR,sbd.ORGANID,sbd.productname,sbd.spec) temp1 ");
		sql.append(" \r\n FULL JOIN ( ");
		sql.append(" \r\n select year,toorganid organid,sum(bonuspoint) targetPoint,max(issupported) issupported,max(confirmdate) confirmdate,sum(rebate) finalPoint,productname,spec  from S_BONUS_TARGET ");
		sql.append(" \r\n where 1=1 ");
		//PD用户
		if(OrganType.Dealer.getValue().equals(users.getOrganType())) {
			sql.append(" and FROMORGANID='"+users.getMakeorganid()+"' ");
		}
		if(!StringUtil.isEmpty(scrForm.getOrganId())){
			sql.append(" and toorganid = '" + scrForm.getOrganId()+"'");
		}
		if(!StringUtil.isEmpty(scrForm.getYear())){
			sql.append(" and year = " + scrForm.getYear());
		}
		if(!StringUtil.isEmpty(scrForm.getProductName())){
			sql.append(" and PRODUCTNAME = '" + scrForm.getProductName()+"'");
		}
		if(!StringUtil.isEmpty(scrForm.getSpec())){
			sql.append(" and spec = '" + scrForm.getSpec()+"'");
		}
		sql.append(" \r\n GROUP BY year,toorganid,productname,spec ");
		sql.append(" \r\n ) temp2 on temp2.ORGANID = temp1.ORGANID and temp2.year = temp1.year  and temp2.productname = temp1.productname and temp2.spec = temp1.spec ");
		sql.append(" \r\n ) temp3 ");
		sql.append(" \r\n join organ o on o.id = temp3.ORGANID  ");
		sql.append(" and " + condition);
		sql.append(" \r\n LEFT JOIN COUNTRY_AREA ca on ca.id = o.areas ");
		sql.append(" \r\n LEFT JOIN SALES_AREA_COUNTRY sac on o.areas = sac.countryareaid and o.organmodel not in (1,2)");
		sql.append(" \r\n LEFT JOIN S_BONUS_AREA sba1 on sba1.id = sac.salesareaid ");
		sql.append(" \r\n LEFT JOIN S_BONUS_AREA sba2 on sba2.id = sba1.parentid ");
		sql.append(" \r\n LEFT JOIN S_BONUS_AREA sba3 on sba3.id = sba2.parentid ");
		sql.append(" \r\n where 1=1 ");
		if(region != null) {
			if(region.getRank() == 2) {
				sql.append(" and sba1.id="+region.getId());
			} else if(region.getRank() == 1) {
				sql.append(" and sba2.id="+region.getId());
			} else if(region.getRank() == 0) {
				sql.append(" and sba3.id="+region.getId());
			}
		}
		if(!StringUtil.isEmpty(scrForm.getAreaId())) {
			sql.append(" and o.areas="+scrForm.getAreaId());
		}
		
		System.out.println(sql.toString());
		List<Map> list =  new ArrayList<Map>();
		if(request == null && pageSize == 0){
			list =  EntityManager.jdbcquery(sql.toString());
		}else {
			list =  PageQuery.jdbcSqlserverQuery(request, "year,organId", sql.toString(), pageSize);
		}
		if(list != null && list.size()>0){
			
			Map<Integer,SBonusConfig> reachRateMap = appSBonusConfig.getSBonusConfigYearMapByType(3);
			
			for(Map map : list){
				BonusReportForm scForm = new BonusReportForm();
				//将Map中对应的值赋值给实例
				MapUtil.mapToObject(map, scForm);
				
				if(!StringUtil.isEmpty(scForm.getTargetPoint())
						&& !"0".equals(scForm.getTargetPoint())
						&& !StringUtil.isEmpty(scForm.getCurPoint())) {
					double targetPoint = Double.valueOf(scForm.getTargetPoint());
					double curPoint = Double.valueOf(scForm.getCurPoint());
					Double completeRate = ArithDouble.mul(ArithDouble.div(curPoint, targetPoint, 4), 100);
					scForm.setCompleteRate(completeRate.toString());
					SBonusConfig reachRateConfig = reachRateMap.get(Integer.parseInt(scForm.getYear()));
					if(reachRateConfig != null && completeRate >= Double.valueOf(reachRateConfig.getValue())) {
						scForm.setIsReached("1");
					} else {
						scForm.setIsReached("0");
					}
				} else {
					scForm.setCompleteRate("0");
					scForm.setIsReached("0");
				}
				
				if(StringUtil.isEmpty(scForm.getIsSupported())) {
					scForm.setIsSupported("0");
				}
				resultList.add(scForm);
			}
		}
		return resultList;
	}
	
}
