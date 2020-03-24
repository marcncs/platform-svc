package com.winsafe.drp.dao;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.pager2.PageQuery;

public class AppRegion {

	public List getChild(String parentid) {
		String sql = " from Region as a where a.regioncode like '" + parentid
				+ "%' order by a.regioncode";
		return EntityManager.getAllByHql(sql);
	}

	public Region getRegionById(String psid) throws Exception {
		String hql = " from Region as r where r.regioncode = '" + psid + "'";
		return (Region) EntityManager.find(hql);
	}

	public String getAcodeByParent(String strparentid) throws Exception {

		String stracode = null;
		int length = strparentid.length() + 2;
		String sql = "from Region as a where a.regioncode like'" + strparentid
				+ "%' and length(regioncode)=" + length
				+ " order by a.regioncode desc ";
		Region as = (Region) EntityManager.find(sql);
		long intacode = 0;
		if (as == null) {
			intacode = Long.valueOf(strparentid + "01");
		} else {
			intacode = Long.valueOf(as.getRegioncode()).intValue() + 1;
		}
		stracode = String.valueOf(intacode);
		return stracode;
	}

	public void del(String dcode) throws Exception {
		String sql = "delete from region where regioncode='" + dcode + "'";
		EntityManager.updateOrdelete(sql);

	}

	public List hashChile(String dcode) {
		String sql = " from Region as a where a.parentid  = '" + dcode
				+ "' order by a.regioncode";
		return EntityManager.getAllByHql(sql);
	}

	public void saveRegion(Region region) {
		// TODO Auto-generated method stub
		EntityManager.save(region);
	}

	public Region getRegionBySortName(String regioncode) {
		String hql = " from Region as r where r.sortname = '" + regioncode
				+ "'";
		return (Region) EntityManager.find(hql);
	}

	public Region getRegionBySortNameAndParent(String regioncode,
			String parentid) {
		String hql = " from Region as r where r.sortname = '" + regioncode
				+ "' and parentid='" + parentid + "'";
		return (Region) EntityManager.find(hql);
	}

	public Region getRegionByUserid(String usersid) {
		String hql = " from Region as r where r.userid = '" + usersid + "'";
		return (Region) EntityManager.find(hql);
	}

	public Region getRegionByKeyId(Long key) {
		String hql = " from Region as r where r.id = " + key;
		return (Region) EntityManager.find(hql);
	}

	public Region getRegionBySortNameAndParentName(String officename,
			String bigregionname) {
		String hql = " from Region as r where r.sortname = '" + officename
				+ "' and r.parentname='" + bigregionname + "'";
		return (Region) EntityManager.find(hql);
	}

	public Region getRegionBySortNameBigRegion(String regioncode) {
		String hql = " from Region as r where r.sortname like  '" + regioncode+ "'";
		return (Region) EntityManager.find(hql);
	}

	public List getRegionByType(String typeid) {
		String sql = " from Region as a where a.typeid  = '" + typeid
				+ "' order by a.regioncode";
		return EntityManager.getAllByHql(sql);
	}

	public Region getRegionByCode(String regioncode) {
		String hql = " from Region as r where r.regioncode = '" + regioncode+ "'";
		return (Region) EntityManager.find(hql);
	}

	public List<Region> getAllRegion() {
		String sql = " from Region as a where a.id <> 1 order by a.typeid";
		return EntityManager.getAllByHql(sql);
	}


	public List getRegions(HttpServletRequest request, int pageSize,
			String whereSql) throws Exception {
		String hql = " from Region as r " + whereSql +" order  by  len(r.regioncode)";
		return PageQuery.hbmQuery(request, hql, pageSize);
	}

	public Region getRegionByCodeAndTypeId (String regioncode,String condition) {
		String hql = " from Region as r where r.regioncode = '" + regioncode+ "'" + condition;
		return (Region) EntityManager.find(hql);
	}
	
	public List getRegionSortNameByAreaId(String areaId) {
		String hql = " select sortname from Region where regioncode in (select regioncodeid from RegionArea where areaid = '"+areaId+"')";
		return EntityManager.getAllByHql(hql);
	}

	
	public String getSortNameByCode(String regioncode) {
		String hql = " from Region as r where r.regioncode = '" + regioncode+ "'";
		Region region = (Region) EntityManager.find(hql);
		if (region != null) {
			return region.getSortname();
		}
		return "";
	}
}
