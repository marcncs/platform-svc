package com.winsafe.drp.dao;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.metadata.EAuditStatus;
import com.winsafe.drp.metadata.EListedStatus;
import com.winsafe.hbm.util.pager2.PageQuery;

public class AppPopularProduct {
	
	public List getPopularProduct(HttpServletRequest request , int pagesize , int currentPage, Integer manufacturerId, String keyword, 
			Integer group1Id, Integer group2Id, EListedStatus[] listedStatus, EAuditStatus[] auditStatusArray, String orderBy) throws Exception{
		String sql = "from PopularProduct p";

//		StringBuffer sb = new StringBuffer("FROM PopularProduct p WHERE 1=1");
//		if (manufacturerId != null) {
//			sb.append(" AND p.manufacturerId = '" + manufacturerId + "'");
//		}
//		if (!StringUtils.isEmpty(keyword)) {
//			sb.append(" AND (p.name like '%" + keyword + "%' OR p.component like '%" + keyword + "%' OR p.certification like '%" + keyword + "%')");
//		}
//		if (group1Id != null) {
//			sb.append(" AND p.group1Id = '" +group1Id+ "'");
//		}
//		if (group2Id != null) {
//			sb.append(" AND p.group2Id = '" +group2Id+ "'");
//		}
//		if (listedStatus != null && listedStatus.length > 0) {
//			sb.append(" AND (");
//			int i=0;
//			for (EListedStatus status : listedStatus) {
//				if (i > 0) {
//					sb.append(" OR ");
//				}
//				sb.append(" p.listedStatus = '" +status.getValue()+ "'").append(++i);
//			}
//			sb.append(")");
//		}
//		if (auditStatusArray != null && auditStatusArray.length > 0) {
//			sb.append(" AND (");
//			int i=0;
//			for (EAuditStatus status : auditStatusArray) {
//				if (i > 0) {
//					sb.append(" OR ");
//				}
//				sb.append(" p.auditStatus = '" +status.getValue()+ "'").append(++i);
//			}
//			sb.append(")");
//		}
//		if (StringUtils.isEmpty(orderBy)) {
//			sb.append(" ORDER BY p.id DESC");
//		} else {
//			sb.append(orderBy);
//		}
//		return queryForPage(sb.toString(), paramMap, currentPage, pagesize);
		return PageQuery.hbmQuery(request, sql, pagesize);
	
	}
	
	public ScannerUser getScannerUserScanner(String scanid){
		return (ScannerUser)EntityManager.find("from ScannerUser where scanner='"+scanid+"'");
	}
	
	public void addPopularProduct(PopularProduct product) throws Exception{
		EntityManager.save(product);
	}
	
	public List<PopularProduct> getPopularProductAll() throws Exception{
		return EntityManager.getAllByHql("from PopularProduct");
	}
	
	public PopularProduct getPopularProductByID(String id) throws Exception{
		String sql = "from PopularProduct as pp where pp.id = '" + id + "'";
		return (PopularProduct) EntityManager.find(sql);
	}
	
	public PopularProduct getPopularProductByName(String name) {
		String sql = "from PopularProduct as pp where pp.name = '" + name + "'";
		return (PopularProduct) EntityManager.find(sql);
	}
	
	public PopularProduct getPopularProductByManufacturerID(Integer id){
		String sql = "from PopularProduct as pp where pp.manufacturerId = '" + id + "' and rownum = 1 order by pp.lastModifyTime desc";
		return (PopularProduct) EntityManager.find(sql);
	}
	
	public List<PopularProduct> getPopularProductListByManufacturerID(Integer id){
		String sql = "from PopularProduct as pp where pp.manufacturerId = '" + id + "' and rownum <= 10 order by pp.lastModifyTime desc";
		return (List<PopularProduct>) EntityManager.getAllByHql(sql);
	}
	
	public void updPopularProduct(PopularProduct p) throws Exception{
		EntityManager.update(p);
	}

	public void delProductById(String productId) throws Exception {
		String sql = "delete from Popular_Product where id=?";
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		map.put("productId", productId);
		EntityManager.executeUpdateBySql(sql, map);
	}

	public void auditProductById(String productId, Integer isAudit) throws Exception {
		String sql = "update Popular_Product set audit_status=? where id=?";
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		map.put("isAudit", isAudit);
		map.put("productId", productId);
		EntityManager.executeUpdateBySql(sql, map);
		
	}

	public void updPopularProduct(String productId, String slogan, String component, String certification) throws Exception {
		String sql = "update Popular_Product set slogan=?,component=?,certification=? where id=?";
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		map.put("slogan", slogan);
		map.put("component", component);
		map.put("certification", certification);
		map.put("productId", productId);
		EntityManager.executeUpdateBySql(sql, map);
	}

	public List getProduct(HttpServletRequest request, int pagesize, String whereSql) throws Exception {
		String hql = " from PopularProduct " + whereSql + " order by createTime desc";
		return PageQuery.hbmQuery(request, hql, pagesize);
	}
}
