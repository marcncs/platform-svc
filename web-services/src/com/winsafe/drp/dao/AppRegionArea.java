package com.winsafe.drp.dao;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.pager2.PageQuery;
import com.winsafe.sap.pojo.PrintJob;


public class AppRegionArea {

	public List getRegionList(HttpServletRequest request, int pagesize,
			String pWhereClause) throws Exception {
		String hql = "select p.id,p.areaid,ca.areaname,p.regioncodeid from RegionArea as p,CountryArea as ca "
				+ pWhereClause + "  order by p.id desc";
		return PageQuery.hbmQuery(request, hql, pagesize);
	}
	
	public int getCountByRegioncode(String dcode) throws Exception {
		String sql = "select count(p.id) from RegionArea as p where p.regioncodeid='"
				+ dcode + "'";
		return EntityManager.getRecordCount(sql);
	}

	public RegionArea getRegionArea(String areaid,String regioncode) throws Exception {
		String sql = " from RegionArea as ca where ca.areaid='" + areaid+"' and ca.regioncodeid='"+regioncode+"'";
		return (RegionArea) EntityManager.find(sql);
	}

	public void del(String id) throws Exception {
		String sql = "delete from Region_Area where id='" + id + "'";
		EntityManager.updateOrdelete(sql);
	}
	public void delAll(String id) throws Exception {
		String sql = "delete from Region_Area where id in (" + id + ")";
		EntityManager.updateOrdelete(sql);
	}

	public void addRegionAreas(String regionCodeId, String[] countryAreaIds) {
		for(String countryAreaId : countryAreaIds) {
			RegionArea ra = new RegionArea();
			ra.setAreaid(countryAreaId);
			ra.setRegioncodeid(regionCodeId);
			EntityManager.save(ra);
		}
	}
	
	public void delByRegionCodeId(String regionCodeId) throws Exception {
		String sql = "delete from Region_Area where REGIONCODEID='" + regionCodeId + "'";
		EntityManager.updateOrdelete(sql);
	}

	public List getAlreadyExistRegionArea(String regioncodeid, String ids) {
		String hql = "select distinct ca.areaname from CountryArea as ca where ca.id in (select areaid from RegionArea where areaid in ("+ids+"))"; 
		return EntityManager.getAllByHql(hql);
	}

}

