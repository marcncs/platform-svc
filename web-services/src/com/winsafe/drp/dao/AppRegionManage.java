package com.winsafe.drp.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.entity.HibernateUtil;
import com.winsafe.hbm.util.StringUtil;
import com.winsafe.hbm.util.pager2.PageQuery;

public class AppRegionManage {
	public List<RegionItemInfo> getAllRegionItem() {
		String sql = "from RegionItemInfo r order by r.areaId";
		return (List<RegionItemInfo>) EntityManager.getAllByHql(sql);
	}
	
	public List<RegionItemInfo> search(String keyword) {
		String sql = "from RegionItemInfo r where r.areaId like '%"+keyword+"%' or r.name like '%"+keyword+"%' order by r.areaId";
		return (List<RegionItemInfo>) EntityManager.getAllByHql(sql);
	}
	
	/*public List<RegionItemInfo> getAllRegionDetails(HttpServletRequest request, RegionItemInfo info) throws Exception {
		StringBuilder sql = new StringBuilder();
		sql.append("select new RegionItemInfo(r1.areaId,r1.name,r2.name) ")
			.append("from RegionItemInfo r1 left join RegionItemInfo r2 on r1.pId=r2.id where 1=1 ");
		if (info.getAreaId() != null) {
			sql.append(" and r1.areaId=").append(String.valueOf(info.getAreaId()));
		}
		if (info.getName() != null) {
			sql.append(" and r1.name='").append(info.getName()).append("'");
		}
		if (info.getPname() != null) {
			sql.append(" and r2.pname='").append(info.getPname()).append("'");
		}
		sql.append("order by r1.areaId");
		return (List<RegionItemInfo>) PageQuery.hbmQuery(request, sql.toString(), 15);
	}*/
	
	public List<RegionItemInfo> getAllRegionDetail(HttpServletRequest request, RegionItemInfo info, String keyword, int pagesize) throws Exception {
		StringBuilder sql = new StringBuilder();
		sql.append("select new RegionItemInfo(r1.areaId,r1.name) ")
			.append("from RegionItemInfo r1 where 1=1 ");
		if (info.getpId() != null) {
			sql.append(" and r1.pId='").append(info.getpId()).append("'");
		}
		if (keyword != null) {
			sql.append(" and (r1.areaId like '%").append(keyword).append("%' or r1.name like '%").append(keyword).append("%')");
		}
		
		sql.append(" order by r1.areaId");
		return (List<RegionItemInfo>) PageQuery.hbmQuery(request, sql.toString(), pagesize);
	}
	
	public List<RegionItemInfo> query(RegionItemInfo info) {
		// String sql = "from RegionItemInfo r where 1=1";
		if (info == null) {
			return (List<RegionItemInfo>) EntityManager.getAllByHql("from RegionItemInfo r order by r.areaId");
		}
		StringBuilder sql = new StringBuilder();
		sql.append("from RegionItemInfo r where 1=1 ");
		if (info.getId() != null) {
			sql.append(" and r.id=").append(String.valueOf(info.getId()));
		}
		if (info.getAreaId() != null) {
			sql.append(" and r.areaId=").append(String.valueOf(info.getAreaId()));
		}
		if (info.getpId() != null) {
			sql.append(" and r.pId=").append(String.valueOf(info.getpId()));
		}
		if (info.getName() != null) {
			sql.append(" and r.name='").append(info.getName()).append("'");
		}
		if (info.getRank() != null) {
			sql.append(" and r.rank=").append(String.valueOf(info.getRank()));
		}
		sql.append(" order by r.areaId");
		return (List<RegionItemInfo>) EntityManager.getAllByHql(sql.toString());
	}
	
	public List<RegionItemInfo> queryByPage(HttpServletRequest request, RegionItemInfo info) throws Exception {
		StringBuilder sql = new StringBuilder();
		if (info == null) {
			sql.append("from RegionItemInfo r where 1=2 ");
			return (List<RegionItemInfo>) PageQuery.hbmQuery(request, sql.toString(), 15);
		} else {			
			sql.append("from RegionItemInfo r where 1=1 ");
		}
		if (info.getId() != null) {
			sql.append(" and r.id=").append(String.valueOf(info.getId()));
		}
		if (info.getAreaId() != null) {
			sql.append(" and r.areaId=").append(String.valueOf(info.getAreaId()));
		}
		if (info.getpId() != null) {
			sql.append(" and r.pId=").append(String.valueOf(info.getpId()));
		}
		if (info.getName() != null) {
			sql.append(" and r.name='").append(info.getName()).append("'");
		}
		if (info.getRank() != null) {
			sql.append(" and r.rank=").append(String.valueOf(info.getRank()));
		}
		sql.append(" order by r.areaId");
		return (List<RegionItemInfo>) PageQuery.hbmQuery(request, sql.toString(), 15);
	}
	
	public RegionItemInfo getRegionById(String id) {
		String sql = "from RegionItemInfo r where r.id=" + id;
		return (RegionItemInfo) EntityManager.find(sql);
	}
	
	public List<RegionItemInfo> getRegionByPid(int pid) {
		String sql = "from RegionItemInfo r where r.pId=" + pid + " order by r.areaId";
		return EntityManager.getAllByHql(sql);
	}
	
	public RegionItemInfo getRegionByRankName(RegionItemInfo info) {
		String sql = "from RegionItemInfo r where r.rank=" + info.getRank() + " and r.name='" + info.getName() + "'";
		return (RegionItemInfo) EntityManager.find(sql);
	}
	
	public RegionItemInfo getRegionByPidName(RegionItemInfo info) {
		String sql = "from RegionItemInfo r where r.pId=" + info.getpId() + " and r.name='" + info.getName() + "'" + " order by r.areaId";
		return (RegionItemInfo) EntityManager.find(sql);
	}
	
	public RegionItemInfo getRegionByPidAreaId(RegionItemInfo info) {
		String sql = "from RegionItemInfo r where r.pId=" + info.getpId() + " and r.areaId='" + info.getAreaId() + "'" + " order by r.areaId";
		return (RegionItemInfo) EntityManager.find(sql);
	}
	
	public void deleteRegion(RegionItemInfo info) {
		EntityManager.delete(info);
	}
	
	public void deleteRegionRec(RegionItemInfo info) {
		/*if (info.getRank() == 2) {
			EntityManager.delete(info);
		} else {*/
		List<RegionItemInfo> ret = new ArrayList<RegionItemInfo>();
		ret.add(info);
			List<RegionItemInfo> sub = this.getRegionByPid(info.getAreaId());
			List<RegionItemInfo> temp = new ArrayList<RegionItemInfo>();
			List<RegionItemInfo> t = null;
			while (sub != null && sub.size()>0) {
				for (RegionItemInfo i : sub) {
					t = this.getRegionByPid(i.getAreaId());
					if (t != null) {
						temp.addAll(t);
					}
				}
				ret.addAll(sub);
				sub.clear();
				sub.addAll(temp);
				temp.clear();
				if (sub.size() == 0) {
					sub = null;
				}
			}
			for (RegionItemInfo r : ret) {
				EntityManager.delete(r);
			}
		//}
	}
	
	public void updateRegion(RegionItemInfo info) throws Exception {
		EntityManager.update(info);
	}
	
	public void addRegion(RegionItemInfo info) {
		EntityManager.save(info);
		info.setAreaId(info.getId());
	}
	
	public void importFileData(List<RegionItemInfo> list) throws Exception {
		Session session = HibernateUtil.currentSession();
		Transaction tx = session.beginTransaction();
		try {
			session.createSQLQuery("delete from s_bonus_area").executeUpdate();
			for (RegionItemInfo r : list) {
				session.save(r);
			}
			tx.commit();
		} catch (HibernateException e) {
			tx.rollback();
			e.printStackTrace();
			throw e;
		}
	}
	
	public void addRegionBatch(List<RegionItemInfo> info) {
		for (RegionItemInfo i : info) {
			if (this.getRegionByPidAreaId(i) != null) {
				continue;
			}			
			EntityManager.save(i);
		}
		
	}
	
	public void deleteAll() {
		String sql = "delete from s_bonus_area";
		SQLQuery query = HibernateUtil.currentSession(true).createSQLQuery(sql);
		query.executeUpdate();
	}
	
	public List searchCountryArea(int rank, String keyword) {
		String sql = "from CountryArea c where c.rank="+rank+" and (c.id like '%" + 
			keyword+"%' or c.areaname like '%" + keyword + "%') order by c.id";
		return EntityManager.getAllByHql(sql);
	}
	
	public List getCountryAreaByPidRank(int rank, int pid) {
		String sql = "from CountryArea c where c.rank="+rank+" and c.parentid="+pid+" order by c.id";
		return EntityManager.getAllByHql(sql);
	}

	public List<Map<String, String>> getAllSBonusArea() throws Exception {
		String sql = "select big.NAME_LOC bigRegion,MIDDLE.NAME_LOC middleRegion,SMALL.NAME_LOC smallRegion from S_BONUS_AREA big " +
				"left join S_BONUS_AREA middle on MIDDLE.PARENTID = BIG.ID " +
				"left join S_BONUS_AREA small on SMALL.PARENTID=MIDDLE.ID " +
//				"left join SALES_AREA_COUNTRY sac on sac.salesareaid=small.id " +
//				"LEFT JOIN COUNTRY_AREA ca on CA.id = sac.countryareaid " +
				"where big.PARENTID = -1 " +
				"ORDER BY big.NAME_LOC,MIDDLE.NAME_LOC,SMALL.NAME_LOC ";
		return EntityManager.jdbcquery(sql);
	}
	
	public Map<String, Integer> getAllRegionMap() throws HibernateException, SQLException {
		String sql = "select sba.id,SBA.NAME_LOC name,psba.NAME_LOC parentName,psba2.NAME_LOC parentName2 from S_BONUS_AREA sba " +
				"left join S_BONUS_AREA psba on sba.PARENTID = psba.ID " +
				"left join S_BONUS_AREA psba2 on psba.PARENTID = psba2.ID";
		List<Map<String, String>> list = EntityManager.jdbcquery(sql);
		Map<String,Integer> regionMap = new HashMap<String, Integer>();
		for(Map<String, String> map : list) {
			regionMap.put(removeNull(map.get("parentname2"))+removeNull(map.get("parentname"))+map.get("name"), Integer.valueOf(map.get("id"))); 
		}
		return regionMap;
	}
	
	public static String removeNull(String obj){
		return obj == null || "null".equals(obj) || "全国".equals(obj) ? "" : obj.toString();
	}
	
	public Map<String, String> getAllAreasMap() throws HibernateException, SQLException {
		String sql = "select ca1.areaname province, ca2.areaname city,ca3.areaname area, ca3.id from COUNTRY_AREA ca1 " +
				"join COUNTRY_AREA ca2 on ca2.parentId = ca1.id " +
				"join COUNTRY_AREA ca3 on ca3.parentId = ca2.id " +
				"where ca1.rank = 0";
		List<Map<String, String>> list = EntityManager.jdbcquery(sql);
		Map<String,String> regionMap = new HashMap<String, String>();
		for(Map<String, String> map : list) {
			regionMap.put(map.get("province")+" "+map.get("city")+" "+map.get("area"), map.get("id")); 
		}
		return regionMap;
	}
	
	public Set<String> getAllSalesAreaCountrySet() throws HibernateException, SQLException { 
		String sql = "select salesareaid,countryareaid from SALES_AREA_COUNTRY ";
		List<Map<String, String>> list = EntityManager.jdbcquery(sql);
		Set<String> regionSet = new HashSet<String>();
		for(Map<String, String> map : list) {
			regionSet.add(map.get("salesareaid")+" "+map.get("countryareaid")); 
		}
		return regionSet;
	}
}
