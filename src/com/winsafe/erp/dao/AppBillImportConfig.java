package com.winsafe.erp.dao;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.HibernateException;

import com.winsafe.drp.entity.EntityManager;
import com.winsafe.erp.pojo.BillImportConfig;
import com.winsafe.hbm.util.pager2.PageQuery;
@SuppressWarnings("unchecked")
public class AppBillImportConfig {
	
	public void AddBillImportConfig(BillImportConfig bic) throws Exception {		
		EntityManager.save(bic);		
	}
	
	public void updBillImportConfig(BillImportConfig bic)throws Exception {		
		EntityManager.update(bic);		
	}
	
	public void deleteBillImportConfig(Integer id) throws HibernateException, SQLException, Exception  {
		String sql = "delete from BILL_IMPORT_CONFIG  where  ID=" + id; 
		EntityManager.updateOrdelete(sql);
		
	}
	
	public List<BillImportConfig> getOrgan(HttpServletRequest request, int pageSize, String whereSql) throws Exception{
		String hql=" from BillImportConfig as bic " + whereSql + " order by bic.id desc";
		return PageQuery.hbmQuery(request, hql, pageSize);
	}
	
	public BillImportConfig getBillImportConfigByID(Integer id)throws Exception{
		return (BillImportConfig)EntityManager.find("from BillImportConfig where id=" + id + "");
	}

	public BillImportConfig getBillImportConfigByID(String organId, Integer templateNo, String fieldName)throws Exception{
		return (BillImportConfig)EntityManager.find("from BillImportConfig where organId='" + organId + "' and templateNo='" + templateNo + "' and fieldName='" + fieldName + "'");
	}
	
	public List<BillImportConfig> getAllBillImportConfig()throws Exception{
		return EntityManager.getAllByHql(" from BillImportConfig ");
	}
	
	public List<BillImportConfig> getAllBillImportConfig(String organId, String templateNo)throws Exception{
		return EntityManager.getAllByHql(" from BillImportConfig where organId='" + organId + "' and templateNo='" + templateNo + "'");
	}
	

	public List getBillImportConfig(HttpServletRequest request, int pagesize,
			String whereSql) throws Exception {
		String hql="from BillImportConfig "+whereSql +" order by id desc";
		return PageQuery.hbmQuery(request, hql, pagesize);
	}

	public List<String> getTemplateNoByOrganId(String organId) {
		return EntityManager.getAllByHql("select distinct templateNo from BillImportConfig where organId='" + organId + "'");
	}
	
	public BillImportConfig getBillImportConfig(String organId, String templateNo, String fieldName)throws Exception{
		return (BillImportConfig) EntityManager.find(" from BillImportConfig where organId='" + organId + "' and templateNo='" + templateNo + "' and fieldName='"+fieldName+"'");
	}
}

