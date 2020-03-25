package com.winsafe.drp.server;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.winsafe.drp.dao.AppCountryArea;
import com.winsafe.drp.dao.CountryArea;
import com.winsafe.hbm.util.cache.CountryAreaCache;

public class CountryAreaService {
	private CountryAreaCache cache = new CountryAreaCache();
	private AppCountryArea appo = new AppCountryArea();
	
	
	public List getCity(Integer parentID, Long userid) throws Exception {
		return appo.getCity(parentID, userid);
	}
	
	public List getCountryAreaByParentid(int parentID) throws Exception {
		List list = cache.getCountryAreaByParentid(parentID);
		if ( list == null ){
			list = appo.getCountryAreaByParentid(parentID);
			modifyCache();
		}
		return list;
	}
	
	public List getAllCountryArea()throws Exception{
		List list = cache.getAllCountryArea();
		if ( list == null ){
			list = appo.getAllCountryArea();
			modifyCache();
		}
		return list;
	}
	
	public List getProvince() throws Exception {
		return appo.getProvince();
	}
	
	public List getProvinceObj() throws Exception {
		return getCountryAreaByParentid(0);
	}
	
	public CountryArea getAreaByID(Integer id) throws Exception {
		if ( id == 0 ){
			return null;
		}
		CountryArea o = cache.getCountryAreaById(id);
		if ( o == null ){
			o = appo.getAreaByID(id);
			modifyCache();
		}
		return o;
	}
	
	public List getAllCountryArea(HttpServletRequest request, int pagesize, String pWhereClause) throws Exception {
		return appo.getAllCountryArea(request, pagesize, pWhereClause);
	}
	
	public List getAreasByRank(Integer rank) {
		return appo.getAreasByRank(rank);
	}
	
	public void addCountryArea(Object ca) throws Exception {	
		appo.addCountryArea(ca);
		modifyCache();
	}
	
	
	public String getCountryAreaName(int id) throws Exception{
		if ( id == 0 ){
			return "";
		}
		CountryArea o = getAreaByID(id);
		if ( o != null ){
			return o.getAreaname();
		}
		return "";
	}

	
	 private void modifyCache() throws Exception {
		//cache.modifyCountryArea(appo.getAllCountryArea());
	}
	
	
}

