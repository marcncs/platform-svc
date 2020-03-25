package com.winsafe.drp.server;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.HibernateException;

import com.winsafe.drp.dao.AppOrgan;
import com.winsafe.drp.dao.Organ;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.exception.NotExistException;
import com.winsafe.drp.util.ESAPIUtil;
import com.winsafe.hbm.util.cache.OrganCache;
import com.winsafe.hbm.util.pager2.PageQuery;

public class OrganService {
	private OrganCache cache = new OrganCache();
	private AppOrgan appo = new AppOrgan();
	
	public void AddOrgan(Organ d) throws Exception {		
		appo.AddOrgan(d);		
		modifyCache();
	}
	
	public List getOrgan(HttpServletRequest request, int pageSize, String whereSql) throws Exception{
		return appo.getOrgan(request, pageSize, whereSql);
	}
	
	public List getOrganList(HttpServletRequest request, int pageSize, String whereSql) throws Exception{
		return appo.getOrganList(request, pageSize, whereSql);
	} 
	
	public List getOrganList(HttpServletRequest request, int pageSize, String whereSql, Map<String, Object> param) throws Exception{
		return appo.getOrganList(request, pageSize, whereSql, param);
	} 
	
	public List getOrgansb(HttpServletRequest request, int pageSize, String whereSql) throws Exception{
		return appo.getOrgansb(request, pageSize, whereSql);
	}
	
	public String getOcodeByParent(String strparentid)throws Exception{		
//		String code = cache.getOcodeByParent(strparentid);
//		if ( code == null ){
//			code = appo.getOcodeByParent(strparentid);
//			modifyCache();
//		}
		return appo.getOcodeByParent(strparentid);
	}

	public void updOrgan(Organ o)throws Exception {		
		appo.updOrgan(o);
		modifyCache();
	}
	
	public Organ getOrganByID(String id)throws Exception{
		if ( id==null || "".equals(id) || "null".equals(id) ){
			return null;
		}
		Organ o = cache.getOrgan(id);
		if ( o == null ){
			o = appo.getOrganByID(id);
			modifyCache();
		}
		return o;
	}
	
	
	public List getAllOrgan()throws Exception{
		List list = cache.getAllOrgan();
		if ( list == null ){
			list = appo.getAllOrgan();
			modifyCache();
		}
		return list;
	}
	
	public List getOrganToDown(String oid)throws Exception{
		List list = cache.getOrganToDown(oid);
		if ( list == null ){
			list = appo.getOrganToDown(oid);
			modifyCache();
		}
		return list;
	}
	
	public String getOrganName(String id) throws Exception{
		if ( id==null || "".equals(id) || "null".equals(id) ){
			return "";
		}
		Organ o = getOrganByID(id);
		if ( o != null ){
			return ESAPIUtil.decodeForHTML(o.getOrganname());
//			return o.getOrganname(); 
		}
		return "";
	}

	//树形
	 public List getTreeByCate(String parentid) throws Exception{
		List list = cache.getTreeByCate(parentid);
		if ( list == null ){
			list = appo.getTreeByCate(parentid);
			modifyCache();
		}
		return list;
	}
	
	 private void modifyCache() throws Exception {
		//cache.modifyOrgan(appo.getAllOrgan());
	}
	
	public List<Organ> getOrganIdAndNames() throws Exception{
		return appo.getOrganIdAndNames();
	}
	
	public Integer getOrganCountByOeCode(String oeCode){
		return appo.getOrganCountByOeCode(oeCode);
	}
	
	public Organ getNotRepealedOrganById(String id) throws NotExistException{
		try {
			return appo.getOrganByID_Isrepeal(id);
		} catch (Exception e) {
			throw new NotExistException("未取到编号为" + id + "的机构，机构不存在或系统发生了错误");
		}
	}

	public List<Map<String, String>> getExportOrganList(HttpServletRequest request, int i, String whereSql) throws HibernateException, SQLException {
		String sql="select o.*,cli.NAME iname,cli.LEG,cli.C_DATE,cli.RE_CODE,cli.STATUS from Organ o left join CUSTOMER_LOCAL_INFO cli on cli.ID=o.validate_local_id  "+whereSql;
//		sql+=" order by id desc ";
		return EntityManager.jdbcquery(sql);
	}
}

