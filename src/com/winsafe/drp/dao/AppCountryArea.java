package com.winsafe.drp.dao;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.HibernateException;

import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.StringUtil;
import com.winsafe.hbm.util.pager2.PageQuery;
@SuppressWarnings("unchecked")
public class AppCountryArea {


//	public List getCountryArea(Long userid) throws Exception {
//		String sql = "  select ca.id,ca.areaname,ca.parentid,ca.rank from CountryArea as ca ,UserArea as ua where ca.id=ua.areaid and ua.userid="
//				+ userid + " order by ca.id ";
//		return EntityManager.getAllByHql(sql);
//	}
//
//	public List getProvince(Long userid) throws Exception {
//		String sql = "  select ca.id,ca.areaname,ca.parentid,ca.rank from CountryArea as ca ,UserArea as ua where ca.id=ua.areaid and ua.userid="
//				+ userid + "  and ca.parentid = 0 order by ca.id ";
//		;
//		return EntityManager.getAllByHql(sql);
//	}
//

	public List getCity(Integer parentID, Long userid) throws Exception {
		String sql = "select ca.id,ca.areaname,ca.parentid,ca.rank from CountryArea as ca ,UserArea as ua where ca.id=ua.areaid and ua.userid="
				+ userid
				+ " and ca.parentid = "
				+ parentID
				+ " order by ca.id ";

		return EntityManager.getAllByHql(sql);
	}
	
	public List getCountryAreaByParentid(int parentID) throws Exception {
		String sql = "from CountryArea  where parentid=" + parentID;
		return EntityManager.getAllByHql(sql);
	}

	public List getAllCountryArea() throws Exception {
		String sql = " from CountryArea order by id ";
		return EntityManager.getAllByHql(sql);
	}


	
	public List getProvince() throws Exception {
		String sql = " select ca.id,ca.areaname,ca.parentid,ca.rank from CountryArea as ca where ca.parentid = 0";
		return EntityManager.getAllByHql(sql);
	}
	

	public CountryArea getAreaByID(Integer id) throws Exception {
		String sql = " from CountryArea as ca where ca.id=" + id;
		return (CountryArea) EntityManager.find(sql);
	}

	
	public List getAllCountryArea(HttpServletRequest request, int pagesize, String pWhereClause) throws Exception {
		String hql = "select c.id,c.areaname,c.parentid from CountryArea as c "
				+ pWhereClause + " order by c.id ";
		return PageQuery.hbmQuery(request, hql, pagesize);
	}
	
	
	public String getCountryAreaByAreaName(Integer id){
		String hql = " from CountryArea as c where id="+id;
		
		List list = EntityManager.getAllByHql(hql);
		if(list.size() <=0)
			return "";
		
		CountryArea ca = (CountryArea) list.get(0);
		if(ca  == null)
			return "";
		return ca.getAreaname();
	}
	public List getAreasByRank(Integer rank) {
		String hql = "from CountryArea as ca where ca.rank=" + rank;
		return EntityManager.getAllByHql(hql);
	}
	/**
	 * 根据rank查找countryArea
	 * Create Time 2013-11-11 下午05:32:23 
	 * @param rank
	 * @return
	 * @author lipeng
	 */
    public Map<String,Integer> getCountryAreaMap(Integer rank){
    	Map<String,Integer> resultMap = new HashMap<String,Integer>();
    	List<CountryArea> resultList =  getAreasByRank(rank);
    	if(resultList != null && resultList.size()>0){
    		for(CountryArea countryArea : resultList){
    			if(countryArea == null) continue;
    			resultMap.put(countryArea.getAreaname(), countryArea.getId());
    		}
    	}
    	return resultMap;
    }
    
	public void addCountryArea(Object ca) throws Exception {		
		EntityManager.save(ca);		
	}
	
    /**
     * 根据地区的名字获取地区Id
     * Create Time 2011-10-8 下午12:12:55 
     * @param name
     *        地区
     * @return
     * @throws Exception
     * @author zhangfuming
     */
    public CountryArea getCountryAreaByName(String name) throws Exception {
		String sql = "from CountryArea  where areaname like '" + name + "%'";
		List list = EntityManager.getAllByHql(sql);
		return (CountryArea) (list.isEmpty() ? null : list.get(0));
    }
    /**
     *  根据地区的名字和rank获取地区Id
     * Create Time 2013-11-12 上午08:49:24 
     * @param name
     * @param rank
     * @return
     * @throws Exception
     * @author lipeng
     */
    public Integer getIdByName(String name,Integer rank) throws Exception {
		Integer id = null;
    	String sql = "from CountryArea  where rank=" + rank +" and areaname like '" + name + "%'";
		List list = EntityManager.getAllByHql(sql);
		if(list != null && list.size()>0){
			CountryArea countryArea = (CountryArea) list.get(0);
			if(countryArea != null){
				id = countryArea.getId();
			}
		}
		return id;
    }

	public List getCountryAreas(String parentids) throws Exception {
		String sql = "from CountryArea  where parentid in(" + parentids + ")";
		return EntityManager.getAllByHql(sql);
	}
	
	public CountryArea getCountryAreaByAreaNameAndParentId(String name, Integer parentId){
		String hql = "from CountryArea  where areaname like '" + name + "%' and parentid = " + parentId;
		List list = EntityManager.getAllByHql(hql);
		return (CountryArea) (list.isEmpty() ? null : list.get(0));
	}

	public CountryArea getProvinceByName(String organProvince) {
		String sql = "from CountryArea  where parentid = 0 and areaname like '" + organProvince + "%'";
		List list = EntityManager.getAllByHql(sql);
		return (CountryArea) (list.isEmpty() ? null : list.get(0));
	}
	
	public List getCountryAreas(HttpServletRequest request, int pagesize, String pWhereClause) throws Exception {
		String hql = " from CountryArea as c "
				+ pWhereClause + " order by c.id ";
		return PageQuery.hbmQuery(request, hql, pagesize);
	}

	public Map<Integer, String> getProvinceMap() {
		Map<Integer, String> resultMap = new HashMap<Integer, String>();
		String hql = "from CountryArea  where rank = 0";
		List<CountryArea> list = EntityManager.getAllByHql(hql);
		for(CountryArea ca : list){
			if(ca == null) continue;
			resultMap.put(ca.getId(), ca.getAreaname());
		}
		return resultMap;
	}
	
	public String getAreaNameById(String areaId) {
		String hql = " from CountryArea  ca where ca.id = '" + areaId+ "'";
		CountryArea countryArea = (CountryArea) EntityManager.find(hql);
		if (countryArea != null) {
			return countryArea.getAreaname();
		}
		return "";
	}
	
	public Map<String, String> getAreaNameMap() throws Exception {
		Map<String, String> resultMap = new HashMap<String, String>();
		List<CountryArea> list = getAllCountryArea();
		for(CountryArea ca : list){
			if(ca == null) continue;
			resultMap.put(ca.getId().toString(), ca.getAreaname());
		} 
		return resultMap;
	}

	public List<Map<String, String>> getUserAreas(Integer userid) throws Exception {
		String sql = "SELECT id areaid,AREANAME FROM COUNTRY_AREA WHERE	ID IN ( " +
				"SELECT o.AREAS FROM	USER_CUSTOMER uc " +
				"join organ o on o.id=UC.ORGANID " +
				"WHERE	userid = " +userid+
				" UNION " +
				"SELECT COUNTRYAREAID FROM	SALES_AREA_COUNTRY " +
				"WHERE salesareaid IN ( " +
				"SELECT ID FROM S_BONUS_AREA START WITH ID IN ( " +
				"SELECT areaid FROM S_USER_AREA " +
				"WHERE	userid = "+userid+") CONNECT BY PRIOR ID = PARENTID))";
		return EntityManager.jdbcquery(sql);
	}

	public Map<String,String> getAreaByIDs(String... fields) throws Exception {
		Map<String,String> data = new HashMap<>();
		StringBuffer sb = new StringBuffer();
		sb.append("select id,areaname from COUNTRY_AREA where id in (");
		for(String id : fields) {
			if(StringUtil.isEmpty(id)) {
				continue;
			}
			sb.append(id).append(",");
		}
		List<Map<String, String>> mapList = EntityManager.jdbcquery(sb.substring(0, sb.length()-1) +")");
		for(Map<String, String> map : mapList) {
			data.put(map.get("id"), map.get("areaname"));
		}
		return data;
		
	}
	
	public Map<String,String> getAreaMapByIDs(String... fields) throws Exception {
		Map<String,String> data = new HashMap<>();
		StringBuffer sb = new StringBuffer();
		sb.append("select code,encode from COUNTRY_AREA_MAP where CODE in ('");
		for(String id : fields) {
			sb.append(id).append("','");
		}
		List<Map<String, String>> mapList = EntityManager.jdbcquery(sb.substring(0, sb.length()-2) +")");
		for(Map<String, String> map : mapList) {
			data.put(map.get("code"), map.get("encode"));
		}
		return data;
		
	}

}
