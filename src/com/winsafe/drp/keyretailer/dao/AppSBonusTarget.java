package com.winsafe.drp.keyretailer.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.HibernateException;

import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.keyretailer.pojo.SBonusSetting;
import com.winsafe.drp.keyretailer.pojo.SBonusTarget;
import com.winsafe.hbm.util.StringUtil;
import com.winsafe.hbm.util.pager2.PageQuery;
public class AppSBonusTarget {
	
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
	
	/*public List getSBonusTarget(HttpServletRequest request, int pageSize, String whereSql) throws Exception{
		String hql="select p from SBonusTarget as p "+whereSql +" order by p.modifiedDate desc";
		return PageQuery.hbmQuery(request, hql, pageSize);
	}*/
	
	public List getSBonusTarget(HttpServletRequest request, int pageSize, String whereSql) throws Exception{
		String sql="select sbt.id,sbt.year,sbt.productname,sbt.spec,sbt.countunit,ROUND(sbt.targetamount,2) targetamount,ROUND(sbt.bonuspoint,0) bonuspoint,sbt.modifieddate,fromo.ORGANNAME FromOrganName,o.organname ToOrganName from S_BONUS_TARGET sbt " +
				"join ORGAN fromo on fromo.id = sbt.FROMORGANID " +
				"join organ o on o.id = sbt.toorganid "+whereSql;
		if(pageSize == 0) {
			sql += " order by sbt.id desc";
			return EntityManager.jdbcquery(sql);
		}
		return PageQuery.jdbcSqlserverQuery(request, "id desc", sql, pageSize);
	}
	
	public List getSBonusTarget(String whereSql) throws Exception{
		String hql="select p from SBonusTarget as p "+whereSql +" order by p.modifiedDate desc";
		return EntityManager.getAllByHql(hql);
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

	public SBonusSetting getSBonusSetting(String productname, String packSizeName,
			int year, int month, Integer organModel) {
		String hql="from SBonusSetting where year = " + year + " and month >= "+month+" and activeFlag = 1 and productName = '"+productname+"' and spec = '"+packSizeName+"' and accountType = " + organModel + " order by month ";
		return (SBonusSetting)EntityManager.find(hql);
	}

	public SBonusTarget getSBonusTarget(Integer accountId, String productname,
			String packSizeName, int year) { 
		String hql="from SBonusTarget where year = " + year + " and accountId = "+accountId+" and productName = '"+productname+"' and spec = '"+packSizeName+"' ";
		return (SBonusTarget)EntityManager.find(hql);
	}

	public List<Map<String, String>> getSBonusTarget(String year,
			String makeorganid, String organId) throws HibernateException, SQLException { 
		StringBuffer sql = new StringBuffer();
		sql.append("select sbt.year,sbt.fromorganid organid,max(o.ORGANNAME) organname,sum(round(bonuspoint,0))  bonuspoint from S_BONUS_TARGET sbt  ");
		sql.append("join organ o on sbt.fromorganid = o.id ");
		sql.append("and sbt.toorganid = '"+makeorganid+"' ");
		if(!StringUtil.isEmpty(organId)) {
			sql.append("and sbt.fromorganid= '"+organId+"' ");
		}
		sql.append("and sbt.year= "+year);
		sql.append(" GROUP BY sbt.year,sbt.fromorganid");
//		System.out.println(sql.toString());
		return EntityManager.jdbcquery(sql.toString());
	}
	
	public List<Map<String, String>> getMySBonusTarget(String year,
			String makeorganid, String organId) throws HibernateException, SQLException { 
		StringBuffer sql = new StringBuffer();
		sql.append("select sbt.year,sbt.toorganid organid,max(o.ORGANNAME) organname,sum(round(sbt.bonuspoint,0))  bonuspoint,round(max(SBA.BONUSPOINT), 0) curBonusPoint from S_BONUS_TARGET sbt  ");
		sql.append("join organ o on sbt.toorganid = o.id ");
		sql.append("and sbt.fromorganid= '"+makeorganid+"' ");
		if(!StringUtil.isEmpty(organId)) {
			sql.append("and sbt.toorganid = '"+organId+"' ");
		}
		sql.append("and sbt.year= "+year);
		sql.append("LEFT JOIN S_BONUS_APPRAISE sba on SBT.ACCOUNTID = SBA.ACCOUNTID ");
		sql.append(" GROUP BY sbt.year,sbt.toorganid");
//		System.out.println(sql.toString());
		return EntityManager.jdbcquery(sql.toString());
	}

	public List<Map<String, String>> getSBonusTargetDetail(String year,
			String makeorganid, String organId) throws HibernateException, SQLException {
		StringBuffer sql = new StringBuffer();
		sql.append("select sbt.year,sbt.fromorganid organid,o.ORGANNAME,sbt.targetamount amount,sbt.productname,sbt.countunit,round(sbt.bonusPoint,0) bonusPoint,sbt.spec,round(temp.curBonusPoint,0) proCurBonusPoint,temp.curAmount from S_BONUS_TARGET sbt ");
		sql.append("join organ o on sbt.fromorganid = o.id ");
		sql.append("and sbt.toorganid = '"+makeorganid+"' ");
		if(!StringUtil.isEmpty(organId)) {
			sql.append("and sbt.fromorganid= '"+organId+"' ");
		}
		sql.append("and sbt.year= "+year);
		
		sql.append(" left join (select SBT.FROMORGANID oid, sum(sbd.bonuspoint) curBonusPoint,sum(sbd.amount) curAmount from S_BONUS_DETAIL sbd ");
		sql.append("join S_BONUS_TARGET sbt on sbd.organid = SBT.TOORGANID and sbd.opporganid = SBT.FROMORGANID ");
		sql.append("and sbd.organid = '"+makeorganid+"' and sbd.year ="+year);
		if(!StringUtil.isEmpty(organId)) {
			sql.append(" and sbt.fromorganid= '"+organId+"' ");
		}
		sql.append("group by SBT.FROMORGANID) temp on sbt.fromorganid = temp.oid");
		
//		System.out.println(sql.toString());
		return EntityManager.jdbcquery(sql.toString());
	}
	
	public List<Map<String, String>> getMySBonusTargetDetail(String year,
			String makeorganid, String organId) throws HibernateException, SQLException {
		StringBuffer sql = new StringBuffer();
		sql.append("select sbt.year,sbt.toorganid organid,o.ORGANNAME,sbt.targetamount amount,sbt.productname,sbt.countunit,round(sbt.bonusPoint,0) bonusPoint,sbt.spec,round(SBA.BONUSPOINT, 0) curBonusPoint,round(temp.curBonusPoint,0) proCurBonusPoint,temp.curAmount  from S_BONUS_TARGET sbt ");
		sql.append("join organ o on sbt.toorganid= o.id ");
		sql.append("and sbt.fromorganid= '"+makeorganid+"' ");
		if(!StringUtil.isEmpty(organId)) {
			sql.append("and sbt.toorganid = '"+organId+"' ");
		}
		sql.append("and sbt.year= "+year); 
		
		sql.append(" left join (select SBT.TOORGANID oid, sum(sbd.bonuspoint) curBonusPoint,sum(sbd.amount) curAmount from S_BONUS_DETAIL sbd ");
		sql.append("join S_BONUS_TARGET sbt on sbd.organid = SBT.TOORGANID and sbd.opporganid = SBT.FROMORGANID ");
		sql.append("and sbd.opporganid = '"+makeorganid+"' and sbd.year ="+year);
		if(!StringUtil.isEmpty(organId)) {
			sql.append(" and sbt.toorganid= '"+organId+"' ");
		}
		sql.append("group by sbt.toorganid) temp on sbt.toorganid = temp.oid ");
		
		sql.append("LEFT JOIN S_BONUS_APPRAISE sba on SBT.ACCOUNTID = SBA.ACCOUNTID ");
//		System.out.println(sql.toString());
		return EntityManager.jdbcquery(sql.toString());
	}
	
	public SBonusTarget getSBonusTargetById(String id) {
		String hql="from SBonusTarget where id = " + id;
		return (SBonusTarget)EntityManager.find(hql);
	}

	public void delSBonusTargetByid(String id) throws HibernateException, SQLException, Exception {
		String sql ="delete from S_Bonus_Target where  id='" + id+"'";
		EntityManager.updateOrdelete(sql);
	}
	
	
	/*public boolean isSBonusTargetAlreadyExists(SBonusTarget sbt) {
		String sql = "select count(*) from S_BONUS_TARGET " +
				" where year = "+sbt.getYear()+" and fromorganid='"+sbt.getFromOrganId()+"' and toorganid='"+sbt.getToOrganId()+"' and productname = '"+sbt.getProductName()+"' and spec = '"+sbt.getSpec()+"' and countunit=" +sbt.getCountUnit();
		return EntityManager.getRecordCountBySql(sql) > 0;
	}*/
	
	public void addSBonusTarget(SBonusTarget d) throws Exception {		
		EntityManager.save(d);		
	}
	
	public void updSBonusTarget(SBonusTarget d) throws Exception {		
		EntityManager.update(d);		
	}
	
	public SBonusTarget getquerySBonusTarget(SBonusTarget sbt) {
		String sql = "from SBonusTarget where " +
				" year = "+sbt.getYear()+" and fromOrganId='"+sbt.getFromOrganId()+"' and toOrganId='"+sbt.getToOrganId()+"' and productName = '"+sbt.getProductName()+"' and spec = '"+sbt.getSpec()+"' and countUnit=" +sbt.getCountUnit();
		return (SBonusTarget)EntityManager.find(sql);
	}
	
	public double getSBonusTargetTotalById(String year,String toorganid)throws Exception{
		double ci = 0d;
		String sql="select sum(bonusPoint) from SBonusTarget where toOrganId='"+toorganid +"' and year=" +year;
		ci = EntityManager.getdoubleSum(sql);
		return ci;
	}

	public SBonusTarget getSBonusTarget(String fromOrganId, String toOrganId,
			String productName, String spec, String year) {
		String sql = "from SBonusTarget " +
			" where year = "+year+" and fromOrganId='"+fromOrganId+"' and toOrganId='"+toOrganId+"' and productName = '"+productName+"' and spec = '"+spec+"'";
		return (SBonusTarget)EntityManager.find(sql);
	}
	
	public SBonusTarget getSBonusTarget(String toOrganId,
			String productName, String spec, String year) {
		String sql = "from SBonusTarget " +
			" where year = "+year+" and toOrganId='"+toOrganId+"' and productName = '"+productName+"' and spec = '"+spec+"'";
		return (SBonusTarget)EntityManager.find(sql);
	}
	
	public SBonusTarget getSBonusTarget(String toOrganId, String productName,
			String spec, String year, Integer id) {
		String sql = "from SBonusTarget " +
			" where id <> "+id+" and year = "+year+" and toOrganId='"+toOrganId+"' and productName = '"+productName+"' and spec = '"+spec+"'";
		return (SBonusTarget)EntityManager.find(sql);
	}

	public List<SBonusTarget> getSBonusTargetByYear(Integer year) {
		String sql = "from SBonusTarget where year = "+year;
		return EntityManager.getAllByHql(sql);
	}

	public Map<String, Double> getTargetBonusAndRebate(String year,
			String makeorganid) {
		String hql = "select new map(sum(bonusPoint) as bonusPoint,sum(rebate) as rebate) from SBonusTarget where year="+year+" and toOrganId='"+makeorganid+"' and isComplete=1 ";
		List<Map<String, Double>> list = EntityManager.getAllByHql(hql);
		if(list != null && list.size() > 0) {
			return list.get(0);
		} else {
			return null;
		}
	}

	public void updCustomerSupportAction(String makeorganid, String organId,
			String year, String isSupported) throws Exception {
		String sql = "update S_BONUS_TARGET set issupported = "+isSupported+",supportdate=sysdate where toorganid = '"+organId+"' and fromOrganId='"+makeorganid+"' and year = "+year+" and isComplete <> 1 ";
//				"and toorganid in " +
//			"(select organizationid from S_TRANSFER_RELATION where opporganid = '"+makeorganid+"')";
		EntityManager.executeUpdate(sql); 
	}

	public List<SBonusTarget> getSBonusTargetForAppraise(String year,
			String fromOrganId, String toOrganId) {
		String hql = "from SBonusTarget where year = "+year+" and fromOrganId='"+fromOrganId+"' and toOrganId='"+toOrganId+"'";
		return EntityManager.getAllByHql(hql);
	}

	public List<Map<String, String>> getProductList(String year,String fromOrganId, String toOrganId) throws HibernateException, SQLException { 
		String sql = "select * from (select PRODUCTNAME productName,SPECMODE SPEC,countunit, 0 amount from PRODUCT p where useflag=1 and not EXISTS " +
				"(select id from S_BONUS_TARGET where year = "+year+" and TOORGANID = '"+toOrganId+"' and PRODUCTNAME=p.PRODUCTNAME and spec=p.specmode) " +
				"UNION ALL " +
				"select productName,spec,countunit,targetamount from S_BONUS_TARGET where year="+year+" and fromorganid='"+fromOrganId+"' and toorganid = '"+toOrganId+"' ) temp order by productName,spec";
		return EntityManager.jdbcquery(sql);
	}

	public List<Map<String, String>> getBonusConfirm(String organId,
			String year) throws HibernateException, SQLException {  
		String sql = "select max(FROMO.organname) organname,FROMORGANID organid,sum(SBT.BONUSPOINT) targetPoint,sum(FINALBONUSPOINT) curPoint,sum(REBATE) rebate, " +
				"case when SUM (sbt.finalbonuspoint) <> 0 THEN ROUND(SUM (sbt.rebate)/SUM (sbt.finalbonuspoint), 2) ELSE 0 END rebaterate, max(ISCONFIRMED) ISCONFIRMED,MAX (ISSUPPORTED) ISSUPPORTED " +
				"from S_BONUS_TARGET sbt " +
				"join organ fromo on FROMO.ID=SBT.FROMORGANID and SBT.ISCOMPLETE=1 " +
				"where SBT.TOORGANID = '"+organId+"' and YEAR="+year+
				" GROUP BY FROMORGANID,TOORGANID";
		return EntityManager.jdbcquery(sql);
	}

	public void updBonusConfirm(String toOrganId, String year,
			String fromOrganId, String isConfirmed) throws Exception {
		String sql = "update S_BONUS_TARGET set ISCONFIRMED = "+isConfirmed+",confirmdate=sysdate where FROMORGANID ='"+fromOrganId+"' and TOORGANID='"+toOrganId+"' and YEAR ="+year;
		EntityManager.executeUpdate(sql);
	}

	public boolean isConfirmed(String year) { 
		String sql = "select count(*) from S_BONUS_TARGET where YEAR = "+year+" and ISCOMPLETE <> 1";
		return EntityManager.getRecordCountBySql(sql) == 0;
	}

	public void confirmAll(String year) throws Exception {
		String sql = "update S_BONUS_TARGET set ISCOMPLETE = 1, COMPLETEDATE = sysdate where YEAR = "+year;
		EntityManager.executeUpdate(sql);
	}

	public List<SBonusTarget> getNotCompletedTargetByYear(String year) {
		String sql = "from SBonusTarget where year = "+year+" and isComplete=0";
		return EntityManager.getAllByHql(sql);
	}

	public List<Map<String, String>> getSBonusTargetByYearToAccount(Integer year) throws Exception {
		String sql = "select FROMORGANID,TOORGANID,sum(bonusPoint) targetPoint, sum(finalBonusPoint) finalPoint, max(issupported) issupported from S_BONUS_TARGET " +
				"where year = " +year+
				" GROUP BY FROMORGANID,TOORGANID ";
		return EntityManager.jdbcquery(sql);
	}

	public void updSBonusTargetRebate(String fromOrganId, String toOrganId,
			double rebateRate, Integer year) throws Exception {
		String sql = "update S_BONUS_TARGET set rebate = "+rebateRate+" * finalBonusPoint " +
				"where FROMORGANID = '"+fromOrganId+"' and TOORGANID = '"+toOrganId+"' and year = "+year;
		EntityManager.executeUpdate(sql);
	}
}

