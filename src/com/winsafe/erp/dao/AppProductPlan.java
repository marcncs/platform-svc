package com.winsafe.erp.dao;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;

import com.winsafe.drp.entity.EntityManager;
import com.winsafe.erp.action.AddProductPlanAction;
import com.winsafe.erp.pojo.ProductPlan;
import com.winsafe.hbm.util.pager2.PageQuery;
@SuppressWarnings("unchecked")
public class AppProductPlan {
	
	private static Logger logger = Logger.getLogger(AppProductPlan.class);
	
	public void AddProductPlan(ProductPlan bic) throws Exception {		
		EntityManager.save(bic);		
	}
	
	public void updProductPlan(ProductPlan bic)throws Exception {		
		EntityManager.update(bic);		
	}
	
	public void deleteProductPlan(Integer id) throws HibernateException, SQLException, Exception  {
		String sql = "delete from ProductPlan  where  ID=" + id; 
		EntityManager.updateOrdelete(sql);
		
	}
	
	public List<ProductPlan> getProductPlans(HttpServletRequest request, int pageSize, String whereSql) throws Exception{
		String hql=" from ProductPlan as bic " + whereSql + " order by bic.id desc";
		return PageQuery.hbmQuery(request, hql, pageSize);
	}
	
	public ProductPlan getProductPlanByID(Integer id)throws Exception{
		return (ProductPlan)EntityManager.find("from ProductPlan where id=" + id + "");
	}
	
	public ProductPlan getProductPlanByIDString(String id)throws Exception{
		return (ProductPlan)EntityManager.find("from ProductPlan where id=" + id + "");
	}

	public ProductPlan getProductPlanByID(String organId, Integer templateNo, String fieldName)throws Exception{
		return (ProductPlan)EntityManager.find("from ProductPlan where organId='" + organId + "' and templateNo='" + templateNo + "' and fieldName='" + fieldName + "'");
	}
	
	public List<ProductPlan> getProductPlan()throws Exception{
		return EntityManager.getAllByHql(" from ProductPlan ");
	}
	
	public List<ProductPlan> getProductPlanByOrganId(String organId)throws Exception{
		return EntityManager.getAllByHql(" from ProductPlan where organId='" + organId + "'");
	}
	
	
	public ProductPlan getProductPlanByOidAndPid(String organId, String pid)throws Exception{
		return (ProductPlan)EntityManager.find("from ProductPlan where organId='" + organId + "' and productId='" +pid + "' ");
	}
	/**
	 * 更新是否处理
	 * @param l
	 * @param isdeal
	 * @throws Exception
	 */
	public void updIsDeal(long l, String status) throws Exception {
		String sql = "update ProductPlan set temp='"+status+"' where id=" + l + "";
		EntityManager.updateOrdelete(sql);
	}
	public void updIsDealSetPJId(long l, String status,Integer pjid) throws Exception {
		String sql = "update ProductPlan set temp='"+status+"',makeId ="+pjid+" where id=" + l + "";
		EntityManager.updateOrdelete(sql);
	}
	public void updIsDealMsg(long l, String status,String msg) throws Exception {
		String sql = "update ProductPlan set temp='"+status+"',msg='"+msg+"' where id=" + l + "";
		EntityManager.updateOrdelete(sql);
	}
	
	public List<ProductPlan> getProductPlanAll(String whereSql) throws Exception{
		String hql=" from ProductPlan as bic " + whereSql + " order by bic.id desc";
		return EntityManager.getAllByHql(hql);
	}
	
	public void updIsUpload(String id) throws HibernateException, SQLException, Exception  {
		String sql = "update  ProductPlan set isUpload = 1  where  ID in("+id+")"; 
		EntityManager.updateOrdelete(sql);
		
	}
	
	/**
	 * 批量下载码查询
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public List<ProductPlan> getProductPlanList(String id)throws Exception{
		String hql = "from ProductPlan where id in("+id+")";
		return EntityManager.getAllByHql(hql);
	} 

	public boolean isCodeExists(String codeFrom, String codeTo, Long planId) {
		StringBuffer sql = new StringBuffer();
		sql.append("select max(endcode-begincode) from ( ");
		sql.append("select case  when codefrom<'"+codeFrom+"' then '"+codeFrom+"' ELSE codefrom end as begincode ");
		sql.append(",case  when codeto>'"+codeTo+"' then '"+codeTo+"' ELSE codeto end as endcode ");
		sql.append("from PRODUCTPLAN where codefrom is not null and LENGTH(CODEFROM) = 11");
		if(planId != null) {
			sql.append("and id <> " + planId);
		}
		sql.append(")");
		Object o = EntityManager.getRecordCountObjctBySql(sql.toString());
		if(o == null) {
			return false;
		}
		try {
			long count = Long.parseLong(o.toString());
			return count >= 0;
		} catch (Exception e) {
			logger.error("",e);
			return true;
		}
	}

	public List<Map<String, String>> getCovertCodeById(String id) throws HibernateException, SQLException {
		String sql = "select PP.id,PP.PONO,pc.CARTON_CODE,pc.covert_code from PRODUCTPLAN pp " +
				"join PRINT_JOB pj on PP.PRINTJOBID = PJ.PRINT_JOB_ID and PP.id in ("+id+") " +
				"join PRIMARY_CODE pc on pc.print_job_id = pj.PRINT_JOB_ID " +
				"order by PP.id,pc.CARTON_CODE"; 
		return EntityManager.jdbcquery(sql);
	}
	
	public List<Map<String, String>> getCovertCodeById(HttpServletRequest request, int pageSize) throws Exception {
		StringBuffer sql = new StringBuffer(); 
		sql.append("select pc.CARTON_CODE,pc.covert_code from PRODUCTPLAN pp ");
		sql.append("join PRINT_JOB pj on PP.PRINTJOBID = PJ.PRINT_JOB_ID and PP.id = "+request.getParameter("ID")+" ");
		sql.append("join PRIMARY_CODE pc on pc.print_job_id = pj.PRINT_JOB_ID ");
		return PageQuery.jdbcSqlserverQuery(request, "carton_code", sql.toString(), pageSize);
//		sql.append("");
//		sql.append("");
//		sql.append("");
	}

	public boolean isCodeExistsInPirmayCode(String covertCodes, String cartonCode) {
		String sql = "select count(*) from PRIMARY_CODE where covert_code in ("+covertCodes+") and carton_Code not in ("+cartonCode+")";
		return EntityManager.getRecordCountBySql(sql) > 0;
	}

	public String getHasMaxCodeSeqPlanByPrefix(String codeSeq) {
		String hql = "select max(codeTo)from ProductPlan where codeTo like '"+codeSeq+"%' and LENGTH(codeTo) = 11";
		return (String)EntityManager.find(hql);
	}
//	public String getMaxCovertCodeByPrefix(String codeSeq) {
//		String hql = "select max(covertCode)from PrimaryCode where covertCode like '"+codeSeq+"%' and LENGTH(covertCode) = 11";
//		return (String)EntityManager.find(hql);
//	} 
	
	public String getMaxCovertCodeByPrefix(String codeSeq) {
		String sql = "select max(code) code from ( " +
				"select max(codeTo) code from ProductPlan where codeTo like '"+codeSeq+"%' and LENGTH(codeTo) = 11 " +
				"UNION ALL " +
				"select max(covert_Code) from Primary_Code where covert_Code like '"+codeSeq+"%' and LENGTH(covert_Code) = 11 " +
				") temp";
		return (String)EntityManager.queryResultByNativeSql(sql);
	}

	public Map<String, String> getMaxMinCovertCodeByProductPlaId(long id) throws HibernateException, SQLException {
		String sql = "select max(COVERT_CODE) max, min(COVERT_CODE) min from PRIMARY_CODE where PRIMARY_CODE in ( " +
				"select CODE from PREPARE_CODE where PRODUCTPLAN_ID = "+id+" )";
		List<Map<String, String>> list =  EntityManager.jdbcquery(sql);
		if(list.size() > 0) {
			return list.get(0);
		} else {
			return null;
		}
	}
	
}

