package com.winsafe.sap.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.HibernateException;

import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.metadata.LinkMode;
import com.winsafe.drp.util.Constants;
import com.winsafe.hbm.util.StringUtil;
import com.winsafe.hbm.util.pager2.PageQuery;
import com.winsafe.sap.pojo.CartonCode;
import com.winsafe.sap.pojo.CartonSeq;
import com.winsafe.sap.pojo.PrimaryCode;
import com.winsafe.sap.pojo.PrintJob; 

public class AppPrimaryCode {
	
	/**
	 *添加小包装码 
	 * @param uploadId
	 * @return
	 * @author ryan.xi
	 */
	public void addPrimaryCode(PrimaryCode primaryCode) {
		EntityManager.save(primaryCode);
		
	}
	/**
	 *更新小码
	 * @param uploadId
	 * @return
	 * @author ryan.xi
	 */
	public void updPrimaryCode(PrimaryCode pcObj)throws Exception {		
		EntityManager.update(pcObj);		
	}
	
	/**
	 *批量添加小包装码 
	 * @param uploadId
	 * @return
	 * @author ryan.xi
	 */
	public void addPrimaryCodes(List<PrimaryCode> list) throws Exception {

		EntityManager.batchSave(list);
		
	}
	/**
	 *根据printJobId分配小包装码 
	 * @param uploadId
	 * @return
	 * @author ryan.xi
	 * @param codeLength 
	 */
	public void allocatePrimaryCodeByPrintJobId(PrintJob printJob, List<CartonCode> cartonCodes, Integer packSize, Integer codeLength) throws Exception {
		List<String> batchSqls = new ArrayList<String>();
		for(CartonCode cc :cartonCodes) {
			if(LinkMode.After.getValue().equals(printJob.getLinkMode())) {
				StringBuffer sb = new StringBuffer();
				sb.append("update primary_code ");
				sb.append(" set print_job_id = ");
				sb.append(printJob.getPrintJobId());
				sb.append(" ,is_used = 1 where is_used = 0");
				sb.append(" and code_length = ");
				sb.append(codeLength);
				sb.append(" and ROWNUM > 0 and ROWNUM < ");
				sb.append(packSize + 1);
				batchSqls.add(sb.toString());
			} else {
				StringBuffer sb = new StringBuffer();
				sb.append("update primary_code set carton_code = '");
				sb.append(cc.getCartonCode());
				sb.append("' , pallet_code = '");
				sb.append(cc.getPalletCode());
				sb.append("' , print_job_id = ");
				sb.append(printJob.getPrintJobId());
				sb.append(" ,is_used = 1 where is_used = 0");
				sb.append(" and code_length = ");
				sb.append(codeLength);
				sb.append(" and ROWNUM > 0 and ROWNUM < ");
				sb.append(packSize + 1);
				batchSqls.add(sb.toString());
			}
			
			
			
			if(packSize * batchSqls.size() > Constants.DB_BULK_COUNT) {
				EntityManager.executeBatch(batchSqls);
				batchSqls.clear();
			} else if(batchSqls.size() == Constants.DB_BULK_SIZE){
				EntityManager.executeBatch(batchSqls);
				batchSqls.clear();
			}
		}
		EntityManager.executeBatch(batchSqls);
	}
	
	/**
	 * 根据printJobId生成通用码
	 * @param uploadId
	 * @return
	 * @author ryan.xi
	 * @param codeLength 
	 * @param commonCodeLogId 
	 */
	public int createCommonCodeByPrintJobId(Integer printJobId, String commonCode, Integer count, Integer codeLength, Long commonCodeLogId) throws Exception {
		String sql = "update primary_code set carton_code = '"
			+ commonCode
			+ "' , print_job_id = "
			+ printJobId
			+ " , COMMON_CODE_LOG_ID = "
			+ commonCodeLogId
			+ " ,is_used = 1 where is_used = 0 and code_length = "
			+ codeLength
			+ " and ROWNUM > 0 and ROWNUM < "
			+ (count + 1)
			+ " and (select count(id) from primary_code where is_used = 0 and code_length = "
			+ codeLength + " ) > " + count;
		return EntityManager.executeUpdate(sql);
	}
	
	
	
	/**
	 * 更新上传的暗码到数据库中
	 * @param batchSqls
	 * @return
	 * @author ryan.xi
	 */
	public int[] updateConvertCodeWithResult(List<String> batchSqls) throws Exception {
		return EntityManager.executeBatchWithResult(batchSqls);
	}
	
	/**
	 *获取可分配小包装码的数量 
	 * @return
	 * @author ryan.xi
	 * @param codeLength 
	 */
	
	public Integer getAvailablePrimaryCodeCount(Integer codeLength) {
		String sql = "select count(id) from primary_code where is_used = 0 and code_length = "+codeLength;
		return EntityManager.getRecordCountBySql(sql);
	}
	
	public PrimaryCode getPrimaryCodeByPCode(String primaryCode) {
		Map<String,Object> param = new HashMap<>();
		param.put("primaryCode", primaryCode);
		String hql = "from PrimaryCode p where p.primaryCode =:primaryCode";
		PrimaryCode temp = (PrimaryCode)EntityManager.find(hql,param);
//		if(temp==null){
//			throw new NotExistException("找不到相对应的小包装码");
//		}
		return temp;
	}
	
	
	
	/**
	 * @author jason.huang
	 * @param primaryCode
	 * @return
	 * 
	 */
	public PrimaryCode getPrimaryCodeByCovertCode(String covertCode) {
		Map<String,Object> param = new HashMap<>();
	    param.put("covertCode", covertCode);
		String hql = "from PrimaryCode p where p.covertCode = :covertCode";
		return (PrimaryCode)EntityManager.find(hql, param);
	}
	
	
	public ResultSet getForGenPrimaryCodeFile(Integer printJobId, String commonCode) throws Exception {
		String sql = "select PRIMARY_CODE, CARTON_CODE from PRIMARY_CODE where PRINT_JOB_ID = " + printJobId +" and CARTON_CODE != '" + commonCode +"' order by PALLET_CODE ,CARTON_CODE";
		return EntityManager.query2(sql);
	}

	
	/**
	 * 更新小码第一次查询日期
	 * @param uploadId
	 * @return
	 * @author jason.huang
	 * @param codeLength 
	 */
	public void updFirstSearch(String fisttime,String primarycode) throws Exception {
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		map.put("fisttime", fisttime);
		map.put("primarycode", primarycode);
		String sql = "update primary_code set firsttime = to_date(?,'yyyy-MM-dd:hh24:mi:ss') where primary_code = ?";
		EntityManager.executeUpdateBySql(sql, map);
	}
	
	public void updPrimaryCodes(List<String> batchSqls) throws Exception {
		List<String> sqls = new ArrayList<String>();
		for(int i = 0; i < batchSqls.size(); i++) {
			String sql = batchSqls.get(i);
			sqls.add(sql);
			if(sqls.size() == Constants.DB_BULK_SIZE) {
				EntityManager.executeBatch(sqls);
				sqls.clear();
			}
		}
		if(sqls.size() > 0) {
			EntityManager.executeBatch(sqls);	
		}
	}
	
	public void batchUpdPrimaryCodes(List<String> batchSqls) throws Exception {
		EntityManager.executeBatch(batchSqls);
	}
	
	public List<PrimaryCode> getPrimaryCode(HttpServletRequest request,
			int pagesize, String whereSql) throws Exception {
		String hql=" from PrimaryCode "+whereSql +" order by cartonCode ";
		return PageQuery.hbmQuery(request, hql, pagesize);
	}
	public boolean isCovertCodeAlreadyExists(String codeFrom, String codeTo) {
		String sql = "select count(*) from PRIMARY_CODE where COVERT_CODE > '"+codeFrom+"' and COVERT_CODE < '"+codeTo+"'";
		return EntityManager.getRecordCountBySql(sql) > 0;
	}
	public List<Map<String,String>> getCodeRelation(HttpServletRequest request, int pagesize,
			Map<String,String> paraMap, Integer userid) throws Exception {
		Map<String, Object> param = new LinkedHashMap<String, Object>();
		StringBuffer sql = new StringBuffer();
		sql.append("select PC.PRIMARY_CODE primaryCode, PC.CARTON_CODE cartonCode, PC.COVERT_CODE covertCode from PRIMARY_CODE pc  ");
		sql.append("join PRINT_JOB pj on pc.PRINT_JOB_ID = pj.PRINT_JOB_ID ");
		if(!StringUtil.isEmpty(paraMap.get("primaryCode"))) {
			sql.append("and pc.PRIMARY_CODE = ? ");
			param.put("primaryCode", paraMap.get("primaryCode"));
		}
		if(!StringUtil.isEmpty(paraMap.get("cartonCode"))) {
			sql.append("and pc.CARTON_CODE = ? ");
			param.put("cartonCode", paraMap.get("cartonCode"));
		}
		if(!StringUtil.isEmpty(paraMap.get("covertCode"))) {
			sql.append("and pc.COVERT_CODE = ? ");
			param.put("covertCode", paraMap.get("covertCode"));
		}
		sql.append("and pj.plant_code in ( ");
		sql.append("select oecode from organ where id in ( ");
		sql.append("select visitorgan from USER_VISIT where userid = ? )) ");
		param.put("userid", userid);
		return PageQuery.jdbcSqlserverQuery(request, "primaryCode", sql.toString(), pagesize, param);
	}
	
	public List<Map<String, String>> getListBySql(String sql) throws Exception {
		return EntityManager.jdbcquery(sql); 
	}
	
	/**
	 * 根据21位验证码查询
	 * @author jason.huang
	 * @param primaryCode
	 * @return
	 * @throws SQLException 
	 * @throws HibernateException 
	 * 
	 */
	public List<Map<String, String>> getCartonCodeByVCode(String vCode) throws Exception {
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		map.put("vCode", vCode);
		String sql = "select PRIMARY_CODE primaryCode,CARTON_CODE cartonCode,is_used isUsed,PRINT_JOB_ID printJobId,UPLOAD_PR_ID uploadPrId,COVERT_CODE covertCode  from PRIMARY_CODE where SUBSTR(PRIMARY_CODE, 12, 21) =?";
		return EntityManager.jdbcquery(sql, map); 
	}
	
	public static void main(String[] args) throws Exception {
		AppPrimaryCode apc = new AppPrimaryCode();
		Object result = apc.getPrimaryCodeByCovertCode("00800020001");
//		apc.updFirstSearch("2018-04-24 19:00:01","0194127703767");
//		HibernateUtil.commitTransaction();
		System.out.println("--------result:"+result);
	}
}
 
