package com.winsafe.erp.dao;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.HibernateException;

import com.winsafe.drp.entity.EntityManager;
import com.winsafe.erp.pojo.UnitInfo;
import com.winsafe.hbm.util.pager2.PageQuery;
@SuppressWarnings("unchecked")
public class AppUnitInfo {
	
	public void AddUnitInfo(UnitInfo bic) throws Exception {		
		EntityManager.save(bic);		
	}
	
	public void updUnitInfo(UnitInfo bic)throws Exception {		
		EntityManager.update(bic);		
	}
	
	public void deleteUnitInfo(Integer id) throws HibernateException, SQLException, Exception  {
		String sql = "delete from UnitInfo  where  ID=" + id; 
		EntityManager.updateOrdelete(sql);
		
	}
	
	public List<UnitInfo> getUnitInfos(HttpServletRequest request, int pageSize, String whereSql) throws Exception{
		String hql=" from UnitInfo as bic " + whereSql + " order by bic.id desc";
		return PageQuery.hbmQuery(request, hql, pageSize);
	}
	
	public UnitInfo getUnitInfoByID(Integer id)throws Exception{
		return (UnitInfo)EntityManager.find("from UnitInfo where id=" + id + "");
	}

	public UnitInfo getUnitInfoByID(String organId, Integer templateNo, String fieldName)throws Exception{
		return (UnitInfo)EntityManager.find("from UnitInfo where organId='" + organId + "' and templateNo='" + templateNo + "' and fieldName='" + fieldName + "'");
	}
	
	public List<UnitInfo> getUnitInfo()throws Exception{
		return EntityManager.getAllByHql(" from UnitInfo ");
	}
	
	public List<UnitInfo> getUnitInfoByOrganId(String organId)throws Exception{
		return EntityManager.getAllByHql(" from UnitInfo where organId='" + organId + "'");
	}
	
	
	public UnitInfo getUnitInfoByOidAndPid(String organId, String pid)throws Exception{
		return (UnitInfo)EntityManager.find("from UnitInfo where organId='" + organId + "' and productId='" +pid + "' and isactive = 1");
	}

	public Map<String, String> getNeedRepakageMap() throws HibernateException, SQLException {
		Map<String,String> needRepakageMap = new HashMap<String, String>();
		String sql = "select organid||'_'||productid oidPid,NEEDREPACKAGE from UNITINFO";
		List<Map<String,String>> list = EntityManager.jdbcquery(sql);
		for(Map<String,String> map : list) {
			needRepakageMap.put(map.get("oidpid"), map.get("needrepackage"));
		}
		return needRepakageMap;
	} 

	public boolean isProCodeAlreadyExists(String proCode) {
		String sql = "select count(*) from UnitInfo where codeSeq like '"+proCode+"%'";
		return EntityManager.getRecordCountBySql(sql) > 0;
	}
	
	
}

