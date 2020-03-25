package com.winsafe.drp.keyretailer.dao;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.HibernateException;

import com.winsafe.drp.dao.Organ;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.keyretailer.pojo.SBonusSetting;
import com.winsafe.drp.keyretailer.pojo.STransferRelation;
import com.winsafe.drp.metadata.DealerType;
import com.winsafe.drp.metadata.ValidateStatus;
import com.winsafe.hbm.util.pager2.PageQuery;
public class AppSTransferRelation { 
	
	public void addSTransferRelation(STransferRelation d) throws Exception {		
		EntityManager.save(d);		
	}
	
	public void updSTransferRelation(STransferRelation d) throws Exception {		
		EntityManager.update(d);		
	}
	
	public STransferRelation queryby2(String str1,String str2) {
		String hql="select p from STransferRelation as p where p.organizationId='"+str1 +"'  and p.oppOrganId='"+str2+"'  order by p.id desc";
		return (STransferRelation)EntityManager.find(hql);
	}
	
	
	public List getSTransferRelation(HttpServletRequest request, int pageSize, String whereSql) throws Exception{
		String hql="select p,o from STransferRelation as p ,Organ as o "+whereSql +" and o.id=p. oppOrganId order by p.id desc";
		return PageQuery.hbmQuery(request, hql, pageSize);
	}
	
	public List getSTransferRelation(String whereSql) throws Exception{
		String hql="select p,o from STransferRelation as p ,Organ as o "+whereSql +" and o.id=p. oppOrganId order by p.id desc";
		return EntityManager.getAllByHql(hql);
	}


	public STransferRelation getSTransferRelationById(String id) {
		String hql="from STransferRelation where id = " + id;
		return (STransferRelation)EntityManager.find(hql);
	}

	public void delSTransferRelation(STransferRelation sbs) {
		EntityManager.delete(sbs);
	}
	
	public void delSTransferRelation(String id) throws Exception {
		String sql = "delete S_TRANSFER_RELATION where id='" + id + "'";
		EntityManager.updateOrdelete(sql);
	}

	public STransferRelation getSTransferRelation(String organId, String oppOrganId) {
		String hql="from STransferRelation where organizationId = '" + organId + "' and oppOrganId = '"+oppOrganId+"'";
		return (STransferRelation)EntityManager.find(hql);
	}

	public List<Map<String, String>> getOrgansByOrganId(String organId) throws HibernateException, SQLException {
//		String sql = "select o.id organId, o.ORGANNAME ,o.omobile,o.province,o.city,o.areas from S_TRANSFER_RELATION str " +
//				"join organ o on str.OPPORGANID = o.id and o.isrepeal=0 " +
//				"and STR.ORGANIZATIONID = '"+organId+"' " +
//				"UNION " +
//				"select id organId, organname,omobile,province,city,areas from organ " +
//				"where id = (select PARENTID from organ where id = '"+organId+"') ";
		
		String sql = "select o.id organId, o.ORGANNAME ,o.omobile organMobile,o.province organProvince,o.city organCity,o.areas organAreas,o.oaddr organOaddr, temp.name organOlinkman,o.iskeyretailer from S_TRANSFER_RELATION str " +
		"join organ o on str.OPPORGANID = o.id and o.isrepeal=0 and (o.VALIDATE_STATUS="+ValidateStatus.PASSED.getValue()+"or o.organModel ="+DealerType.PD.getValue()+") " +
		"and STR.ORGANIZATIONID = '"+organId+"' " +
		"left join (  " +
		"select cid, max(name)name from OLINKMAN " +
		"GROUP BY cid ) temp on temp.cid = o.id " +
		"order by o.id desc" ;
		
		return EntityManager.jdbcquery(sql);
	}

	public List<Map<String, String>> getOrgansByOppOrganId(String organId, Integer userId, Integer isKeyRetailer) throws HibernateException, SQLException { 
//		String sql = "select o.id organId, o.ORGANNAME,o.omobile,o.province,o.city,o.areas from S_TRANSFER_RELATION str " + 
//				"join organ o on str.ORGANIZATIONID = o.id and o.isrepeal=0 " +
//				"and STR.OPPORGANID = '"+organId+"' " +
//				"UNION " +
//				"select id organId, ORGANNAME,omobile,province,city,areas from organ " +
//				"where isrepeal=0 " +
//				"and id in (select visitorgan from Organ_Visit where userid="+userId+") ";
		
		String sql = "select o.id organId, o.ORGANNAME,o.omobile organMobile,o.province organProvince,o.city organCity,o.areas organAreas,o.oaddr organOaddr, temp.name organOlinkman,o.iskeyretailer from S_TRANSFER_RELATION str " +
		"join organ o on str.ORGANIZATIONID = o.id and o.isrepeal=0 " + (isKeyRetailer!=null?" and o.iskeyretailer="+isKeyRetailer:"")+
		" and STR.OPPORGANID = '"+organId+"' and o.VALIDATE_STATUS="+ValidateStatus.PASSED.getValue() +" " +
		"left join (  " +
		"select cid, max(name)name from OLINKMAN " +
		"GROUP BY cid ) temp on temp.cid = o.id " +
		"order by o.id desc" ;
		return EntityManager.jdbcquery(sql);
	}

	public List<Map<String, String>> getAllOrgans(String organId,
			Integer userId) throws HibernateException, SQLException {  
//		String sql = "select o.id organId, o.ORGANNAME,o.omobile,o.province,o.city,o.areas from S_TRANSFER_RELATION str " +
//		"join organ o on str.OPPORGANID = o.id and o.isrepeal=0 " +
//		"and STR.ORGANIZATIONID = '"+organId+"' " +
//		"UNION " +
//		"select o.id organId, o.ORGANNAME,o.omobile,o.province,o.city,o.areas from S_TRANSFER_RELATION str " +
//		"join organ o on str.ORGANIZATIONID = o.id and o.isrepeal=0 " +
//		"and STR.OPPORGANID = '"+organId+"' " +
//		"UNION " +
//		"select id organId, organname,omobile,province,city,areas from organ " +
//		"where isrepeal=0 and id = (select PARENTID from organ where id = '"+organId+"') " +
//		"UNION " +
//		"select id organId, ORGANNAME,omobile,province,city,areas from organ " +
//		"where isrepeal=0 " +
//		"and id in (select visitorgan from Organ_Visit where userid="+userId+") ";
		
		String sql = "select temp.*, temp2.name organOlinkman from (select o.id organId, o.ORGANNAME,o.omobile organMobile,o.province organProvince,o.city organCity,o.areas organAreas,o.oaddr organOaddr,o.iskeyretailer,o.organType from S_TRANSFER_RELATION str " +
		"join organ o on str.OPPORGANID = o.id and o.isrepeal=0 and (o.VALIDATE_STATUS="+ValidateStatus.PASSED.getValue()+"or o.organModel ="+DealerType.PD.getValue()+") " +
		"and STR.ORGANIZATIONID = '"+organId+"' " +
		"UNION " +
		"select o.id organId, o.ORGANNAME,o.omobile,o.province,o.city,o.areas,o.oaddr,o.iskeyretailer,o.organType from S_TRANSFER_RELATION str " +
		"join organ o on str.ORGANIZATIONID = o.id and o.isrepeal=0 " +
		"and STR.OPPORGANID = '"+organId+"' and o.VALIDATE_STATUS="+ValidateStatus.PASSED.getValue()+" ) temp " +
		"left join (  " +
		"select cid, max(name)name from OLINKMAN " +
		"GROUP BY cid ) temp2 on temp2.cid = temp.organId " +
		"ORDER BY organId DESC ";
		
		return EntityManager.jdbcquery(sql);
	}
	
	public List<Map<String, String>> getOrgansByParentId(String organId) throws HibernateException, SQLException { 
		String sql = "select o.id organId, o.ORGANNAME,o.oaddr from S_TRANSFER_RELATION str " +
		"join organ o on str.ORGANIZATIONID = o.id and o.isrepeal=0 " +
		"and STR.OPPORGANID = '"+organId+"' and o.parentId='"+organId+"' order by o.id desc" ;
		return EntityManager.jdbcquery(sql);
	}

	/*public Organ getOrganByIdAndParentId(String organId,
			String parentOrganId) {
		String hql = " from Organ where id = '"+organId+"' and parentid='"+parentOrganId+"'";
		return (Organ)EntityManager.find(hql);
	}*/
	public List<Map<String,String>> getOrganByIdAndParentId(String organId,
			String parentOrganId) throws Exception {
		String sql = "select o.id organId,o.organname,o.OMOBILE mobile,o.PROVINCE,CA1.AREANAME provinceName,o.CITY,ca2.AREANAME cityName,o.AREAS,ca3.areaname Areasname,o.OADDR address,ORGANMODEL typeId from ORGAN o " +
				"LEFT JOIN COUNTRY_AREA ca1 on CA1.ID=o.PROVINCE " +
				"LEFT JOIN COUNTRY_AREA ca2 on CA2.id=o.city " +
				"LEFT JOIN COUNTRY_AREA ca3 on CA3.ID=o.areas " +
				"where o.id = '"+organId+"' and o.PARENTID = '"+parentOrganId+"' order by o.id desc";
		return EntityManager.jdbcquery(sql);
	}
	
	public List<Map<String, String>> getAllOrgans(String whereSql) throws HibernateException, SQLException {  
		String sql = "select o.id organId,o.ORGANNAME,o.OMOBILE organMobile,o.PROVINCE organProvince,o.CITY organCity,o.AREAS organAreas,o.OADDR organOaddr, temp.name organOlinkman,o.organModel organType from ORGAN o  " +
		"join (  " +
		"select cid, max(name)name from OLINKMAN " +
		"GROUP BY cid ) temp on temp.cid = o.id " +whereSql +" order by o.id desc";
		return EntityManager.jdbcquery(sql);
	}
	
	public List<Map<String, String>> getAllUserOrgans(String whereSql) throws HibernateException, SQLException {  
		String sql = "select o.id organId,o.ORGANNAME,o.OMOBILE organMobile,o.PROVINCE organProvince,o.CITY organCity,o.AREAS organAreas,o.OADDR organOaddr, o.organModel organType from ORGAN o  " +
		whereSql +" order by o.id desc"; 
		return EntityManager.jdbcquery(sql);
	}


	public List<Map<String, String>> getTransferOrgan(String organId,
			Integer userid) throws Exception {
		String sql = "select distinct o.id oppOrganid,o.organname opporganname,o.oaddr oppoaddr,str.id from S_USER_AREA sua " +
				"join S_BONUS_AREA sba on SBA.AREAID = SUA.AREAID " +
				"and sua.USERID = " + userid +
				"join S_BONUS_AREA sba2 on sba2.parentid = sba.parentid " +
				"join S_USER_AREA sua2 on sba2.areaid = sua2.areaid " +
				"join USER_CUSTOMER uc on uc.userid = sua2.userid " +
				"join organ o on o.id = uc.organid " +
				"LEFT JOIN S_TRANSFER_RELATION str on str.OPPORGANID = o.id and str.ORGANIZATIONID = '"+organId+"' ";
//				"where o.id not in ( " +
//				"select OPPORGANID from S_TRANSFER_RELATION where ORGANIZATIONID = '"+organId+"')";
		return EntityManager.jdbcquery(sql);
	}

	public Set<String> getAllRelationSet() throws Exception {
		String sql = "select ORGANIZATIONID toorganid,OPPORGANID fromorganid from S_TRANSFER_RELATION";
		List<Map<String,String>> list = EntityManager.jdbcquery(sql);
		Set<String> relation = new HashSet<String>();
		for(Map<String,String> map : list) {
			relation.add(map.get("fromorganid")+"_"+map.get("toorganid"));
		}
		return relation;
	}

	public boolean isAlreadyExists(String organId, String oppOrganId) {
		String sql = "select count(organizationid) from S_TRANSFER_RELATION where organizationid='"+organId+"' and opporganid='"+oppOrganId+"'";
		return EntityManager.getRecordCountBySql(sql) > 0;
	}

	public List<Map<String, String>> getTransferOrganByOrganId(String organId) throws Exception {
		String sql = "select o.id oppOrganId, o.organname oppOrganName, o.oaddr oppOaddr from S_TRANSFER_RELATION str " +
				"join organ o on str.opporganid = o.id and str.organizationid = '"+organId+"'";
		return EntityManager.jdbcquery(sql); 
	}

	public void delSTransferRelation(String organId, String oppOrganIds) throws Exception {
		String sql = "DELETE from S_TRANSFER_RELATION where ORGANIZATIONID='"+organId+"' and OPPORGANID not in ("+oppOrganIds+")";
		EntityManager.executeUpdate(sql);
	}
	
}

