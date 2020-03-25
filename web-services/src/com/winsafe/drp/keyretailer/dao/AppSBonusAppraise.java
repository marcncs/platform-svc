package com.winsafe.drp.keyretailer.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.HibernateException;

import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.keyretailer.pojo.SBonusAppraise;
import com.winsafe.drp.keyretailer.pojo.SBonusSetting;
import com.winsafe.drp.metadata.OrganType;
import com.winsafe.hbm.util.StringUtil;
import com.winsafe.hbm.util.pager2.PageQuery;
public class AppSBonusAppraise {
	
	public void addSBonusAppraise(SBonusAppraise d) throws Exception {		
		EntityManager.save(d);		
	}
	
	public void updSBonusAppraise(SBonusAppraise d) throws Exception {		
		EntityManager.update(d);		
	}
	
	
	/*public List getSBonusAppraise(HttpServletRequest request, int pageSize, String whereSql) throws Exception{
		String hql="from SBonusAppraise as p "+whereSql +" order by p.id desc";
		return PageQuery.hbmQuery(request, hql, pageSize);
	}*/
	
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


	public List<SBonusAppraise> getSBonusAppraiseByOrganId(String organId) {
		String hql="from SBonusAppraise where organId = " + organId;
		return EntityManager.getAllByHql(hql);
	}

	public void delSBonusSetting(SBonusSetting sbs) {
		EntityManager.delete(sbs);
	}

	public SBonusSetting getSBonusSetting(String productname, String packSizeName,
			int year, int month, Integer organModel) {
		String hql="from SBonusSetting where year = " + year + " and month >= "+month+" and activeFlag = 1 and productName = '"+productname+"' and spec = '"+packSizeName+"' and accountType = " + organModel + " order by month ";
		return (SBonusSetting)EntityManager.find(hql);
	}

	@SuppressWarnings("unchecked")
	public List<Map<String, String>> getSBonusAppraise(
			HttpServletRequest request, int pagesize, String whereSql) throws Exception {
		StringBuffer sql = new StringBuffer();
		
		sql.append(" select sba.*,cta.areaname from (");
		sql.append(" select sbap.id,sbap.accountid,sbac.mobile,sbac.name,sbac.companyname,sbac.address,sbac.organid,sbac.province,ROUND(sbap.bonuspoint,0) bonuspoint,ROUND(temp.rebate, 1) actualpoint,ROUND(temp.targetPoint,0) targetPoint,sbap.period,CASE WHEN bonuspoint <> 0 THEN ROUND(temp.rebate/sbap.bonuspoint, 2) ELSE 0 END appraise from s_bonus_appraise sbap ");
		sql.append(" join s_bonus_account sbac on sbap.accountid = sbac.accountid ");
		
		if(!StringUtil.isEmpty(request.getParameter("name"))) {
			sql.append(" and sbac.name = '"+request.getParameter("name")+"' ");
		}
		if(!StringUtil.isEmpty(request.getParameter("companyName"))) {
			sql.append(" and sbac.companyName = '"+request.getParameter("companyName")+"' ");
		}
		
		sql.append(" LEFT JOIN ( ");
		sql.append("select year,toorganid,sum(bonuspoint) targetPoint,sum(Rebate) rebate ");
		sql.append("from S_BONUS_TARGET GROUP BY year,toorganid ");
		sql.append(") temp on sbap.period=temp.year and sbap.organid=temp.toorganid ");
		
		sql.append(" join organ o on o.id = sbac.organid ");
		sql.append(whereSql);
		sql.append("  ) sba left join country_area cta ");
		sql.append(" on  sba.province = cta.id ");
		if(pagesize == 0) {
			return EntityManager.jdbcquery(sql.toString());
		}
		return PageQuery.jdbcSqlserverQuery(request, "mobile", sql.toString(), pagesize);
	}

	public SBonusAppraise getSBonusAppraiseByAccountId(String accountId) {
		String hql="from SBonusAppraise where accountId = " + accountId;
		return (SBonusAppraise)EntityManager.find(hql);
	}

	public SBonusAppraise getSBonusAppraiseByOrganIdAndPeriod(String organId, String period) {
		String hql="from SBonusAppraise where  organId = '" + organId + "' and period = " + period;
		return (SBonusAppraise)EntityManager.find(hql);
	}

	public SBonusAppraise getSBonusAppraiseById(String id) {
		String hql="from SBonusAppraise where id = " + id;
		return (SBonusAppraise)EntityManager.find(hql);
	}
	
	/**
	 * 获取修改所对应的属性
	 * @param accountId
	 * @return
	 * @throws HibernateException
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	public List getUpdSBonusAppraiseByAccountId(String accountId) throws HibernateException, SQLException{
		
		StringBuffer sql = new StringBuffer();
		sql.append(" select sbac.mobile,sbac.name,sbac.companyname,sbac.address,sbac.organid,sbap.accountid,sbap.bonuspoint,sbap.appraise,sbap.actualpoint ");
		sql.append(" from s_bonus_appraise sbap,s_bonus_account sbac");
		sql.append(" where sbap.accountid = sbac.accountid and sbac.accountid='"+accountId+"'");
		return EntityManager.jdbcquery(sql.toString());
	}
	
	/**
	 * 导出积分管理
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getExputSBonusAppraise(HttpServletRequest request) throws Exception {
		StringBuffer sql = new StringBuffer();
		
		sql.append(" select sba.*,cta.areaname from (");
		sql.append(" select sbap.id,sbap.accountid,sbac.mobile,sbac.name,sbac.companyname,sbac.address,sbac.organid,sbac.province,sbap.bonuspoint,sbap.appraise,sbap.actualpoint,sbap.period from s_bonus_appraise sbap ");
		sql.append(" join s_bonus_account sbac on sbap.accountid = sbac.accountid ");
		
		if(!StringUtil.isEmpty(request.getParameter("name"))) {
			sql.append(" and sbac.name = '"+request.getParameter("name")+"' ");
		}
		if(!StringUtil.isEmpty(request.getParameter("companyName"))) {
			sql.append(" and sbac.companyName = '"+request.getParameter("companyName")+"' ");
		}
		sql.append("  ) sba left join country_area cta ");
		sql.append(" on  sba.province = cta.id ");
		
		return EntityManager.jdbcquery(sql.toString());
	}

	public List<Map<String, String>> getCustomerSupportAction(
			String makeorganid, String organId, String year) throws HibernateException, SQLException { 
		StringBuffer sql = new StringBuffer();
		sql.append("select o.id organid,max(o.organname) organname,sum(temp2.curPoint) curPoint, max(temp.isSupported) isSupported,sum(temp.targetPoint) targetPoint from S_TRANSFER_RELATION str ");
		sql.append("join organ o on o.id = str.organizationid and str.opporganid = '"+makeorganid+"' ");
		if(!StringUtil.isEmpty(organId)) {
			sql.append(" and str.organizationid = '"+organId+"' ");
		}
		sql.append(" JOIN ( ");
		sql.append("select toorganid,productname,spec, sum(bonusPoint) targetPoint,MAX (isSupported) isSupported from S_BONUS_TARGET where year = "+year+" and fromorganid = '"+makeorganid+"' ");
		sql.append(" GROUP BY toorganid,productname,spec ");
		sql.append(") temp on temp.toorganid = str.organizationid ");
		sql.append("LEFT JOIN( ");
		sql.append("select organid,productname,spec,sum(bonuspoint) curPoint from S_BONUS_DETAIL ");
		sql.append("where year = "+year+" ");
		sql.append("GROUP BY organid,productname,spec ");
		sql.append(") temp2 on temp2.organid = str.organizationid and temp2.productname=temp.productname and temp2.spec=temp.spec");
		sql.append(" GROUP BY o.ID ");
		return EntityManager.jdbcquery(sql.toString());
	}

	public void updCustomerSupportAction(
			String makeorganid, String organId, String year, String isSupported) throws HibernateException, SQLException, Exception {
		String sql = "update S_BONUS_APPRAISE set issupported = "+isSupported+" where organid = '"+organId+"' and period = "+year+" and organid in " +
				"(select organizationid from S_TRANSFER_RELATION where opporganid = '"+makeorganid+"')";
		EntityManager.executeUpdate(sql);  
	}

	public List<Map<String, String>> getSBonusAppraise(String year, 
			String organId, String condition, String organModel, String areaId) throws HibernateException, SQLException { 
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT SUM (sbt.bonusPoint) targetPoint,ROUND(SUM (sbt.targetAmount),2) targetAmount,SUM (sbt.rebate) finalPoint,MAX (sba.bonusPoint) curPoint,ROUND(MAX (temp.curAmount),2) curAmount,MAX (sbt.ISCONFIRMED) ISCONFIRMED,MAX (sbt.ISSUPPORTED) ISSUPPORTED,");
		sql.append("CASE WHEN MAX (sba.bonusPoint) <> 0 THEN ROUND (SUM (sbt.rebate) / MAX (sba.bonusPoint),2) ELSE	0 END appraise, ");
		sql.append("MAX (o.ID) organId,MAX (o.organname) organname,"+year+" YEAR ");
		sql.append("FROM S_BONUS_TARGET sbt ");
		sql.append("JOIN S_BONUS_APPRAISE sba ON sba.organid = sbt.toorganid ");
		sql.append("JOIN ( select organid,sum(amount) curAmount from S_BONUS_DETAIL ");
		sql.append("where year ="+year);
		if(!StringUtil.isEmpty(organId)) {
			sql.append(" and organid='"+organId+"'");
		}
		sql.append(" GROUP BY organid ) temp on temp.organid = sba.organid ");
		sql.append("JOIN organ o ON o.ID = sba.organid ");
		if(!StringUtil.isEmpty(organModel)) {
			sql.append(" and o.organModel='"+organModel+"'");
		}
		if(!StringUtil.isEmpty(areaId)) {
			sql.append(" and o.AREAS="+areaId+" ");
		}
		sql.append("where sbt.year ="+year);
		if(!StringUtil.isEmpty(organId)) {
			sql.append(" and o.ID='"+organId+"'");
		} else {
			sql.append(" and "+ condition);
		}
		sql.append(" group by sbt.TOORGANID ");
		return EntityManager.jdbcquery(sql.toString());
	}
	
	public List<Map<String, String>> getRebate(String year,
			String organId, String condition, String isConfirmed, String organModel, String areaId) throws HibernateException, SQLException { 
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT SUM (sbt.bonusPoint) targetPoint,SUM (sbt.rebate) finalPoint,MAX (sba.bonusPoint) curPoint,MAX (sbt.ISCONFIRMED) ISCONFIRMED,MAX (sbt.ISSUPPORTED) ISSUPPORTED,");
		sql.append("CASE WHEN MAX (sba.bonusPoint) <> 0 THEN ROUND (SUM (sbt.rebate) / MAX (sba.bonusPoint),2) ELSE	0 END appraise, ");
		sql.append("MAX (o.ID) organId,MAX (o.organname) organname,"+year+" YEAR, MAX(o.organmodel) organType ");
		sql.append("FROM S_BONUS_TARGET sbt ");
		sql.append("JOIN S_BONUS_APPRAISE sba ON sba.organid = sbt.toorganid ");
		sql.append("JOIN organ o ON o.ID = sba.organid ");
		if(!StringUtil.isEmpty(areaId)) {
			sql.append("and o.AREAS = "+areaId+" ");
		} 
		if(!StringUtil.isEmpty(organId)) {
			sql.append(" and o.organModel="+organModel+" ");
		} 
		sql.append("where sbt.year ="+year);
		if(!StringUtil.isEmpty(organId)) {
			sql.append(" and o.ID='"+organId+"'");
		} else {
			sql.append(" and "+ condition);
		}
		if(!StringUtil.isEmpty(isConfirmed)) {
			if("0".equals(isConfirmed)) {
				sql.append(" and (sbt.ISCONFIRMED is null or sbt.ISCONFIRMED = 0 ) ");
			} else {
				sql.append(" and sbt.ISCONFIRMED = 1 ");
			}
		}
		sql.append(" group by sbt.TOORGANID ");
		return EntityManager.jdbcquery(sql.toString());
	}

	public List<Map<String, String>> getSBonusAppraiseDetail(String year,
			String organId, String fromOrganId, String productName) throws HibernateException, SQLException { 
		StringBuffer sql = new StringBuffer();
		/*sql.append("select sbt.productname,sbt.spec,max(sbt.BonusPoint) targetPoint,sum(sbd.BonusPoint) curPoint,max(o.id) organId,max(o.organname) organname, "+year+" year from S_BONUS_TARGET sbt ");
		sql.append("join S_BONUS_DETAIL sbd on sbt.productName = sbd.productName and sbt.spec = sbd.spec and sbd.organid=sbt.toorganid ");
		sql.append("and sbt.fromOrganId='"+fromOrganId+"' and sbt.toOrganId = '"+organId+"' ");
		sql.append("join organ o on o.id = sbt.toOrganId ");
		sql.append("where sbt.year ="+year+" ");
		sql.append("group by sbt.productname,sbt.spec ");*/
		
		sql.append("select "+year+" year,");
		sql.append("case when temp1.targetAmount is not null then temp1.targetAmount else 0 end targetAmount,");
		sql.append("case when temp1.targetPoint is not null then temp1.targetPoint else 0 end targetPoint,");
		sql.append("case when temp2.curPoint is not null then temp2.curPoint else 0 end curPoint,");
		sql.append("case when temp2.curAmount is not null then temp2.curAmount else 0 end curAmount,");
		sql.append("case WHEN temp1.PRODUCTNAME is NOT NULL then temp1.PRODUCTNAME else temp2.PRODUCTNAME END PRODUCTNAME,");
		sql.append("case WHEN temp1.SPEC is NOT NULL then temp1.SPEC else temp2.SPEC END SPEC,");
		sql.append("case WHEN temp1.organId is NOT NULL then temp1.organId else temp2.organId END organId,");
		sql.append("CASE WHEN temp1.targetAmount <> 0 THEN ROUND (temp2.curAmount / temp1.targetAmount,4) * 100 ELSE 0 END amountRate,");
		sql.append("CASE WHEN temp1.targetPoint <> 0 THEN ROUND (temp2.curPoint / temp1.targetPoint,4) * 100 ELSE	0 END pointRate ");
		sql.append("from ( ");
		sql.append("select TOORGANID organId,PRODUCTNAME,SPEC,SUM(targetAmount) targetAmount,SUM(bonusPoint)targetPoint from S_BONUS_TARGET ");
		sql.append("where TOORGANID = '"+organId+"' and year="+year+" and PRODUCTNAME= '"+productName+"' ");
		if(!StringUtil.isEmpty(fromOrganId)) {
			sql.append("  and FROMORGANID='"+fromOrganId+"' ");
		}
		sql.append(" group by TOORGANID,PRODUCTNAME,SPEC");
		sql.append(") temp1 full join (");
		sql.append("SELECT organid,PRODUCTNAME,SPEC,SUM (BONUSPOINT) curPoint,SUM (amount) curAmount	FROM S_BONUS_DETAIL	");
		sql.append("WHERE YEAR = "+year+" AND organid = '"+organId+"' and PRODUCTNAME= '"+productName+"' ");
		sql.append("GROUP BY organid,PRODUCTNAME,SPEC");
		sql.append(") temp2 on TEMP1.PRODUCTNAME = TEMP2.PRODUCTNAME and temp1.SPEC=temp2.SPEC");
		return EntityManager.jdbcquery(sql.toString());
	}

	public List<Map<String, String>> getSBonusAppraiseSetting(
			HttpServletRequest request, int pagesize, String whereSql) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select sbt.fromorganid,sbt.toorganid,sbt.year,max(o.organname) fromOrganName, max(toorgan.organname) toOrganName,sum(sbt.bonuspoint) targetBonus,max(sbt.issupported) issupported, ");
		sql.append("SUM (temp.curBonus) curBonus, ");
		sql.append("case when SUM (sbt.bonuspoint) <> 0 THEN ROUND(SUM (sbt.finalbonuspoint)/SUM (sbt.bonuspoint), 2) ELSE 0 END completerate,");
		sql.append("case when SUM (sbt.finalbonuspoint) <> 0 THEN ROUND(SUM (sbt.rebate)/SUM (sbt.finalbonuspoint), 2) ELSE 0 END rebaterate,");
		sql.append("SUM (sbt.rebate) REBATE,max(sbt.isconfirmed) isconfirmed,max(sbt.iscomplete) iscomplete ");
		sql.append("from S_BONUS_TARGET sbt ");
		sql.append("join ORGAN o on o.id=sbt.fromorganid ");
		
		if(!StringUtil.isEmpty(request.getParameter("year"))) {
			sql.append(" and sbt.year="+request.getParameter("year"));
		}
		if(!StringUtil.isEmpty(request.getParameter("outOrganId"))) {
			sql.append(" and o.id="+request.getParameter("outOrganId"));
		}
		
		sql.append(" join organ toorgan on toorgan.id = sbt.toorganid ");
		if(!StringUtil.isEmpty(request.getParameter("inOrganId"))) {
			sql.append(" and toorgan.id="+request.getParameter("inOrganId"));
		}
		sql.append(" LEFT JOIN ( ");
		sql.append("select organid,productname,spec,sum(bonusPoint) curBonus from S_BONUS_DETAIL ");
		sql.append("GROUP BY organid,productname,spec");
		sql.append(") temp on temp.organid = sbt.toorganid and temp.productname=sbt.productname and temp.spec=sbt.spec ");
		sql.append(whereSql);
		sql.append("GROUP BY sbt.fromorganid,sbt.toorganid,sbt.year");
		if(pagesize == 0) {
			return EntityManager.jdbcquery(sql.toString());
		} else {
			return PageQuery.jdbcSqlserverQuery(request, "year desc, toOrganName", sql.toString(), pagesize);
		}
		
	}

	public void updSBonusAppraiseSetting(String year,
			String fromOrganId, String toOrganId, String isSupported) throws Exception {
		String sql = "update S_BONUS_TARGET set issupported = "+isSupported+" where fromorganid='"+fromOrganId+"' and toorganid='"+toOrganId+"' and year="+year;
		EntityManager.executeUpdate(sql);
	}

	public List<Map<String, String>> getBonusList(String year, String organId,
			String condition) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT SUM (sbt.bonusPoint) targetPoint,SUM (sbt.targetAmount) targetAmount,SUM (sbt.rebate) finalPoint,SUM (temp.curPoint) curPoint,SUM (temp.curAmount) curAmount,MAX (sbt.ISCONFIRMED) ISCONFIRMED,MAX (sbt.ISSUPPORTED) ISSUPPORTED,");
		sql.append("CASE WHEN sum (temp.curPoint) <> 0 THEN ROUND (SUM (sbt.rebate) / sum (temp.curPoint),2) ELSE	0 END appraise, ");
		sql.append("MAX (o.ID) organId,MAX (o.organname) organname,MAX (fromo. ID) fromorganId,MAX (fromo.organname) fromorganname,"+year+" YEAR ");
		sql.append("FROM S_BONUS_TARGET sbt ");
		sql.append("JOIN ( select organid,PRODUCTNAME,SPEC,sum(BONUSPOINT) curPoint,sum(amount) curAmount from S_BONUS_DETAIL ");
		sql.append("where year ="+year);
		if(!StringUtil.isEmpty(organId)) {
			sql.append(" and organid='"+organId+"'");
		}
		sql.append(" GROUP BY organid,PRODUCTNAME,SPEC ) temp ON temp.organid = sbt.toorganid and temp.PRODUCTNAME = sbt.PRODUCTNAME and temp.spec = sbt.spec ");
		sql.append("JOIN organ o ON o. ID = sbt.toorganid ");
		sql.append("join organ fromo on fromo.id = sbt.fromorganId ");
		sql.append("where sbt.year ="+year);
		if(!StringUtil.isEmpty(organId)) {
			sql.append(" and o.ID='"+organId+"'");
		} else {
			sql.append(" and "+ condition);
		}
		sql.append(" GROUP BY sbt.FROMORGANID,sbt.TOORGANID ");
		return EntityManager.jdbcquery(sql.toString());
	}
	
	public List<Map<String, String>> getBonusProductList(String year, String organId,
			String condition, String fromOrganId) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select "+year+" year,");
		sql.append("case when temp1.targetAmount is not null then temp1.targetAmount else 0 end targetAmount,");
		sql.append("case when temp1.targetPoint is not null then temp1.targetPoint else 0 end targetPoint,");
		sql.append("case when temp2.curPoint is not null then temp2.curPoint else 0 end curPoint,");
		sql.append("case when temp2.curAmount is not null then temp2.curAmount else 0 end curAmount,");
		sql.append("case WHEN temp1.PRODUCTNAME is NOT NULL then temp1.PRODUCTNAME else temp2.PRODUCTNAME END PRODUCTNAME,");
		sql.append("case WHEN temp1.organId is NOT NULL then temp1.organId else temp2.organId END organId,");
		sql.append("CASE WHEN temp1.targetAmount <> 0 THEN ROUND (temp2.curAmount / temp1.targetAmount,4) * 100 ELSE 0 END amountRate,");
		sql.append("CASE WHEN temp1.targetPoint <> 0 THEN ROUND (temp2.curPoint / temp1.targetPoint,4) * 100 ELSE	0 END pointRate ");
		sql.append("from ( ");
		sql.append("select TOORGANID organId,PRODUCTNAME,SUM(targetAmount) targetAmount,SUM(bonusPoint)targetPoint from S_BONUS_TARGET ");
		sql.append("where TOORGANID = '"+organId+"' and year="+year);
		if(!StringUtil.isEmpty(fromOrganId)) {
			sql.append("  and FROMORGANID='"+fromOrganId+"' ");
		}
		sql.append(" group by TOORGANID,PRODUCTNAME");
		sql.append(") temp1 full join (");
		sql.append("SELECT organid,PRODUCTNAME,SUM (BONUSPOINT) curPoint,SUM (amount) curAmount	FROM S_BONUS_DETAIL	");
		sql.append("WHERE YEAR = "+year+" AND organid = '"+organId+"' ");
		sql.append("GROUP BY organid,PRODUCTNAME");
		sql.append(") temp2 on TEMP1.PRODUCTNAME = TEMP2.PRODUCTNAME");
		return EntityManager.jdbcquery(sql.toString());
	}

	public Map<String, String> getSBonusByOrganIdandYear(String organId,
			String year) throws Exception {
		String sql = "select max(sba.BONUSPOINT) BONUSPOINT,SBA.ACCOUNTID,sum(sbt.BONUSPOINT) bonusTarget,sum(CASE WHEN SBT.ISCOMPLETE = 1 THEN SBT.REBATE ELSE 0 end) actualPoint from S_BONUS_APPRAISE sba " +
				"join  S_BONUS_TARGET sbt on SBA.ORGANID = SBT.TOORGANID " +
				"where sba.ORGANID ='"+organId+"' and sba.PERIOD="+year+" GROUP BY SBA.ACCOUNTID";
		List<Map<String, String>> list =  EntityManager.jdbcquery(sql.toString());
		if(list.size() > 0) {
			return list.get(0);
		}
		return null;
	}
}

