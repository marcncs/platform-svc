package com.winsafe.drp.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.HibernateException;

import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.keyretailer.metadata.AfficheType;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.pager.SimplePageInfo;
import com.winsafe.hbm.util.pager2.PageQuery;

public class AppAffiche {
	
	public List getListAffiche(int pagesize,String whereSql,SimplePageInfo tmpPgInfo)throws Exception{
		
		List list=null;
		int targetPage = tmpPgInfo.getCurrentPageNo();
		String sql="from Affiche as a,AfficheBrowse as ab "+whereSql+" order by ab.isbrowse,a.publishdate desc";
		list=EntityManager.getAllByHql(sql,targetPage,pagesize);
		return list;
	}
	
	
	public List getAfficheByPublish(int pagesize,String whereSql,SimplePageInfo tmpPgInfo)throws Exception{
		List ls=null;
		int targetPage = tmpPgInfo.getCurrentPageNo();
		String sql="from Affiche as a "+whereSql+" order by a.publishdate desc";
		ls =EntityManager.getAllByHql(sql,targetPage,pagesize);
		return ls;
	}
	
	public List searchAffiche(HttpServletRequest request, int pagesize, String pWhereClause) throws Exception {
		String hql = " select a.id,a.affichetitle,a.makeid,a.affichecontent,a.affichetype,a.makedate,ab.isbrowse from Affiche  a,AfficheBrowse  ab "
				+ pWhereClause + " order by ab.isbrowse,a.makedate desc";		
		return PageQuery.hbmQuery(request, hql, pagesize);
	}
	
	public void addAffiche(Affiche affiche)throws Exception{
		
		EntityManager.save(affiche);
		
	}
	
	public void delAffiche(Integer id)throws Exception{
		
		String sql="delete from Affiche where id="+id;
		EntityManager.updateOrdelete(sql);
		
	}
	/**
	 * 添加
	 * @param af
	 * @throws Exception
	 */
	public void addAffiches(Affiche af)throws Exception{
		
		EntityManager.save(af);
	}
	/**
	 * 更新
	 * @param af
	 * @throws Exception
	 */
	public void updAffiche(Affiche af) throws Exception{
		
		EntityManager.update(af);
		
	}
	public Affiche getAfficheByID(Integer id) throws Exception{
		String sql="from Affiche as af where af.id="+id;
		
		return (Affiche)EntityManager.find(sql);
	}
	
	public void updAfficheBrowse(Integer afficheid,Integer userid)throws Exception{
		
		String sql="update Affiche_browse set isbrowse=1 where afficheid="+afficheid+" and userid="+userid;
		EntityManager.updateOrdelete(sql);
	}
	
	public List getAffiche(HttpServletRequest request, int pagesize, String pWhereClause) throws Exception {
		String hql = " from Affiche  a "+pWhereClause+" order by a.makedate desc ";
		return PageQuery.hbmQuery(request, hql, pagesize);
	}

	
	public List<Affiche> getIsPublishAffiche() throws Exception {
		return EntityManager.getAllByHql(" from Affiche where isPublish = 1 order by makedate desc ");
	}
	
	
	/**
	 * @return
	 * @throws Exception
	 */
	public List<Affiche> getIsPublishAffiche(String afficheType) throws Exception {
		return EntityManager.getAllByHql(" from Affiche where afficheType = '"+afficheType+"' and isPublish = 1 order by makedate desc ");
	}


	public List<Affiche> getPublishedSysAffiche() {
		String hql = "from Affiche where afficheType = 3 and isPublish = 1 and endDate >= to_date('"+DateUtil.getCurrentDateString()+"','yyyy-MM-dd') order by makedate desc ";
		return EntityManager.getAllByHql(hql);
	}
	
	public List<Affiche> getIsPublishAfficheByOrganIdAndType(String organId, String afficheType) throws Exception {
		return EntityManager.getAllByHql(" from Affiche where afficheorganid = '"+organId+"' and  affichetype in ("+afficheType+") and isPublish = 1 order by makedate desc ");
	}
	
	public Integer getIsPublishAfficheCountByOrganId(String organId) {
		String sql = "select count(id) from Affiche where afficheorganid = '"+organId+"' and isPublish = 1 and (ISREAD=0 or ISREAD IS NULL)  order by makedate desc ";
		return EntityManager.getRecordCountBySql(sql);
	}
	
	public void updAffiche(String afficheid)throws Exception{
		String sql="update Affiche set isread=1 where id in(" + afficheid + ")";
		EntityManager.updateOrdelete(sql);
	}

	public void setAfficheToRead(Integer userId, String afficheType) throws HibernateException, SQLException, Exception {
		String sql="insert into AFFICHE_BROWSE(ID,AFFICHEID,USERID) " +
				" select SEQ_AFFICHE_BROWSE.nextval, id,"+userId+" from AFFICHE af where AFFICHETYPE = " + afficheType +
				" and isPublish = 1 and endDate >= to_date('"+DateUtil.getCurrentDateString()+"','yyyy-MM-dd') and not EXISTS (select id from AFFICHE_BROWSE where AFFICHEID = AF.ID and USERID = "+userId+") ";
		EntityManager.updateOrdelete(sql); 
	
	}


	public List<Map<String,String>> getIsPublishAfficheByType(String afficheType, Integer userId, String organId) throws HibernateException, SQLException { 
		String sql =
//				"select * from ( " +
				"select a.id,a.affichetitle title,a.affichecontent content,a.makedate publishDate, a.AFFICHETYPE type,a.endDate ,case when ab.id is not null then 1 else 0 END isread from AFFICHE a " +
				" LEFT JOIN AFFICHE_BROWSE ab on a.id = Ab.afficheid and Ab.userid = " + userId +
				" where a.AFFICHETYPE =" + afficheType + " and a.isPublish = 1 and endDate >= to_date('"+DateUtil.getCurrentDateString()+"','yyyy-MM-dd') " +
//				"UNION ALL " +
//				"select id,affichetitle,affichecontent,makedate,AFFICHETYPE,endDate," + 
//				"CASE WHEN isread IS NOT NULL THEN	TO_NUMBER(isread) ELSE 0 END isread from AFFICHE " +
//				"where AFFICHETYPE in ("+AfficheType.AUDIT.getValue()+","+AfficheType.BONUS_CONFIRM.getValue()+") and AFFICHEORGANID = '"+organId+"') temp " +
				"order by a.makedate desc";
		return EntityManager.jdbcquery(sql);
	}


	public int getNotReadAfficheCountByTypeAndUserId(Integer type,
			Integer userid, String organId) { 
		/*String sql = "select count(ID) from AFFICHE where isPublish = 1 and ((affichetype = "+type+" and endDate >= to_date('"+DateUtil.getCurrentDateString()+"','yyyy-MM-dd') and id not in (" +
				"select AFFICHEID from AFFICHE_BROWSE where USERID = "+userid+")) " +
						" or (AFFICHETYPE in ("+AfficheType.AUDIT.getValue()+","+AfficheType.BONUS_CONFIRM.getValue()+") and AFFICHEORGANID = '"+organId+"' and (ISREAD=0 or ISREAD IS NULL))) ";
		*/
		String sql = "select count(ID) from AFFICHE where affichetype = "+type+" and isPublish = 1 and endDate >= to_date('"+DateUtil.getCurrentDateString()+"','yyyy-MM-dd') and id not in (" +
				"select AFFICHEID from AFFICHE_BROWSE where USERID = "+userid+") ";

		return EntityManager.getRecordCountBySql(sql);
	}


	public void setAfficheToReadById(Integer userid, String afficheId) throws HibernateException, SQLException, Exception {
		String sql="insert into AFFICHE_BROWSE(ID,AFFICHEID,USERID) " +
		" select SEQ_AFFICHE_BROWSE.nextval, "+afficheId+","+userid+" from dual where not EXISTS (select id from AFFICHE_BROWSE where AFFICHEID = "+afficheId+" and USERID = "+userid+") ";
		EntityManager.updateOrdelete(sql); 
	}


	public void addBKRAfficheUsers(Integer afficheId, Integer countryAreaId) throws Exception, Exception {
		String sql = "INSERT INTO AFFICHE_USER " +
				"select AFFICHE_USER_SEQ.nextval,userid,"+afficheId+" from S_USER_AREA " +
				"where areaid in ( " +
				"select salesareaid from SALES_AREA_COUNTRY where countryareaid =" +countryAreaId+")";
		EntityManager.executeUpdate(sql);
	}


	public void addBKDAfficheUsers(Integer afficheId, Integer areas) throws Exception {
		/*String sql = "INSERT INTO AFFICHE_USER " +
				"select AFFICHE_USER_SEQ.nextval,userid,"+afficheId+" from USER_CUSTOMER " +
				"where organid = '"+organId+"'";*/
		String sql = "INSERT INTO AFFICHE_USER " +
			"select AFFICHE_USER_SEQ.nextval,sua.userid,"+afficheId+" from SALES_AREA_COUNTRY sac " +
			"join S_BONUS_AREA sba on SAC.SALESAREAID=SBA.ID AND SAC.COUNTRYAREAID = "+areas +" " +
					"join S_BONUS_AREA sba2 on sba2.id = SBA.parentid " +
					"join S_USER_AREA sua on sua.areaid = sba2.id ";
		EntityManager.executeUpdate(sql);
	}


	public List<Map<String,String>> getIsPublishAfficheForSaleman(int userId) throws Exception {
		String sql = "select af.id,AF.AFFICHECONTENT content,AF.AFFICHETITLE title,AF.MAKEDATE publishDate,AF.MAKEORGANID organId,AFFICHETYPE type,ab.id ISREAD from AFFICHE af " +
				"LEFT JOIN AFFICHE_USER au on AF.id=AU.AFFICHEID " +
				"LEFT JOIN AFFICHE_BROWSE ab on ab.afficheid = AF.id and ab.userid = " +userId+
				" where (AF.AFFICHETYPE = "+AfficheType.KEYBKDBKR.getValue()+" or (AF.AFFICHETYPE = "+AfficheType.AUDIT.getValue()+" and AU.USERID = "+userId+" )) " +
				"and  AF.isPublish = 1 order by af.makedate desc ";
		return EntityManager.jdbcquery(sql);
	}


	public int getNotReadAfficheCountForSaleman(Integer userId) {
		String sql = "select count(af.id) from AFFICHE af " +
				"LEFT JOIN AFFICHE_USER au on AF.id=AU.AFFICHEID " +
				"LEFT JOIN AFFICHE_BROWSE ab on ab.afficheid = AF.id and ab.userid = " +userId+
				" where ab.id is null and (AF.AFFICHETYPE = "+AfficheType.AUDIT.getValue()+" and AU.USERID = "+userId+" ) " +
				"and  AF.isPublish = 1 order by af.makedate desc ";
		return EntityManager.getRecordCountBySql(sql);
	}


	public int getNotReadBKDBKRAfficheCountForSaleman(Integer userid) {
		String sql = "select count(af.id) from AFFICHE af " +
		"LEFT JOIN AFFICHE_USER au on AF.id=AU.AFFICHEID " +
		"LEFT JOIN AFFICHE_BROWSE ab on ab.afficheid = AF.id and ab.userid = " +userid+
		" where ab.id is null and (AF.AFFICHETYPE = "+AfficheType.KEYBKDBKR.getValue()+") " +
		"and  AF.isPublish = 1 order by af.makedate desc ";
		return EntityManager.getRecordCountBySql(sql);
	}


	public boolean isSSSRExists(Integer countryAreaId) { 
		String sql = "select count(*) from S_USER_AREA " +
			"where areaid in ( " +
			"select salesareaid from SALES_AREA_COUNTRY where countryareaid =" +countryAreaId+")";
		return EntityManager.getRecordCountBySql(sql) > 0;
	}


	public void addBKRAfficheUsersByPD(Integer afficheId, String organId) throws Exception { 
		String sql = "INSERT INTO AFFICHE_USER " +
			"select AFFICHE_USER_SEQ.nextval,userid,"+afficheId+" from USER_CUSTOMER " +
			"where organid = '"+organId+"'";
		EntityManager.executeUpdate(sql);
	}


	public boolean isASMExists(Integer areas) {
		String sql = "select count(sua.id) from SALES_AREA_COUNTRY sac " +
		"join S_BONUS_AREA sba on SAC.SALESAREAID=SBA.ID AND SAC.COUNTRYAREAID = "+areas +" " +
				"join S_BONUS_AREA sba2 on sba2.id = SBA.parentid " +
				"join S_USER_AREA sua on sua.areaid = sba2.id ";
		return EntityManager.getRecordCountBySql(sql) > 0;
	}
}
