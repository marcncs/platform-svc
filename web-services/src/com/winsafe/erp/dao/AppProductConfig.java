package com.winsafe.erp.dao;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.HibernateException;

import com.winsafe.drp.entity.EntityManager;
import com.winsafe.erp.pojo.ProductConfig;
import com.winsafe.hbm.util.pager2.PageQuery;
@SuppressWarnings("unchecked")
public class AppProductConfig {
	
	public void AddProductConfig(ProductConfig bic) throws Exception {		
		EntityManager.save(bic);		
	}
	
	public void updProductConfig(ProductConfig bic)throws Exception {		
		EntityManager.update(bic);		
	}
	
	public void deleteProductConfig(Integer id) throws HibernateException, SQLException, Exception  {
		String sql = "delete from Product_Config  where  ID=" + id; 
		EntityManager.updateOrdelete(sql);
		
	}
	
	public List<ProductConfig> getProductConfigs(HttpServletRequest request, int pageSize, String whereSql) throws Exception{
		String hql=" from ProductConfig as bic " + whereSql + " order by bic.id desc";
		return PageQuery.hbmQuery(request, hql, pageSize);
	}
	
	public ProductConfig getProductConfigByID(Integer id)throws Exception{
		return (ProductConfig)EntityManager.find("from ProductConfig where id=" + id + "");
	}

	public ProductConfig getProductConfigByID(String organId, Integer templateNo, String fieldName)throws Exception{
		return (ProductConfig)EntityManager.find("from ProductConfig where organId='" + organId + "' and templateNo='" + templateNo + "' and fieldName='" + fieldName + "'");
	}
	
	public List<ProductConfig> getProductConfig()throws Exception{
		return EntityManager.getAllByHql(" from ProductConfig ");
	}
	
	public List<ProductConfig> getProductConfigByOrganId(String organId)throws Exception{
		return EntityManager.getAllByHql(" from ProductConfig where organId='" + organId + "'");
	}
	
	
	public ProductConfig getProductConfigByOidAndMid(String organId, String mid)throws Exception{
		return (ProductConfig)EntityManager.find("from ProductConfig where organId='" + organId + "' and mCode='" + mid + "' ");
	}
	
	
}

