package com.winsafe.sap.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.HibernateException;



import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.util.Constants;
import com.winsafe.drp.util.Dateutil;
import com.winsafe.hbm.util.StringUtil;
import com.winsafe.hbm.util.pager2.PageQuery;
@SuppressWarnings("unchecked")
public class AppCovertErrorLog {
	
	
	public void addCovertErrorLogs(List<String> batchSqls, Long covertUploadReportId) throws Exception {
		List<String> sqls = new ArrayList<String>();
		for(int i = 0; i < batchSqls.size(); i++) {
			String sql = batchSqls.get(i);
			sqls.add(sql.replace(":curId", covertUploadReportId.toString()));
			if(sqls.size() == Constants.DB_BULK_SIZE) {
				EntityManager.executeBatch(sqls);
				sqls.clear();
			}
		}
		if(sqls.size() > 0) {
			EntityManager.executeBatch(sqls);	
		}
	}
	
	public void updCovertErrorLogs(List<String> batchSqls) throws Exception {
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
	
	
	
	public List getCovertErrorLogs(HttpServletRequest request,
			int pagesize, String whereSql) throws Exception {
		String hql=" from CovertErrorLog as o "+whereSql +" order by o.id desc";
		return PageQuery.hbmQuery(request, hql, pagesize);
	}

	public List<Map> getCovertCodes(HttpServletRequest request, int pagesize,
			Map paraMap) throws Exception {
		//对于结束日期增加一天
		if(!StringUtil.isEmpty((String)paraMap.get("EndDate"))){
			String endDateStr = (String)paraMap.get("EndDate");
			endDateStr = endDateStr.replaceAll("-", "") + "235959";
			paraMap.put("EndDate", endDateStr);
		}
		//对于结束日期增加一天
		if(!StringUtil.isEmpty((String)paraMap.get("BeginDate"))){
			String beginDateStr = (String)paraMap.get("BeginDate");
			beginDateStr = beginDateStr.replaceAll("-", "") + "000000";
			paraMap.put("BeginDate", beginDateStr);
		}
		StringBuffer sql = new StringBuffer();
		sql.append("\r\n select CUR.product_id productId");
		sql.append("\r\n ,CUR.product_name productName");
		sql.append("\r\n ,CUR.BATCH batchNumber");
		sql.append("\r\n ,cel.COVERT_CODE covertCode");
		sql.append("\r\n ,cel.TDCODE tdCode");
		sql.append("\r\n ,cur.LINE_NO productionLine");
		sql.append("\r\n ,cel.PRINT_DATE printDate");
		sql.append("\r\n ,cel.ERROR_TYPE errorType");
		sql.append("\r\n ,pc.carton_code cartonCode");
		sql.append("\r\n ,cel.primary_code primaryCode");
		sql.append("\r\n ,cur.UPLOAD_PR_ID uploadPrId");
		sql.append("\r\n from COVERT_ERROR_LOG cel");
		sql.append("\r\n join COVERT_UPLOAD_REPORT cur on cel.UPLOAD_PR_ID=cur.id");
		if(!StringUtil.isEmpty((String)paraMap.get("productId"))) {
			sql.append(" and CUR.product_id = '"+paraMap.get("productId")+"'");
		}
		if(!StringUtil.isEmpty((String)paraMap.get("productName"))) {
			sql.append(" and CUR.product_name = '"+paraMap.get("productName")+"'");
		}
		
		if(!StringUtil.isEmpty((String)paraMap.get("batchNumber"))) {
			sql.append(" and CUR.BATCH = '"+paraMap.get("batchNumber")+"'");
		}
		if(!StringUtil.isEmpty((String)paraMap.get("covertCode"))) {
			sql.append(" and cel.COVERT_CODE = '"+paraMap.get("covertCode")+"'");
		}
		
		if(!StringUtil.isEmpty((String)paraMap.get("BeginDate"))) {
			sql.append(" and cel.PRINT_DATE >= '"+paraMap.get("BeginDate")+"'");
		}
		if(!StringUtil.isEmpty((String)paraMap.get("EndDate"))) {
			sql.append(" and cel.PRINT_DATE <= '"+paraMap.get("EndDate")+"'");
		}
		if(!StringUtil.isEmpty((String)paraMap.get("tdCode"))) {
			sql.append(" and cel.TDCODE = '"+paraMap.get("tdCode")+"'");
		}
		if(!StringUtil.isEmpty((String)paraMap.get("primaryCode"))) {
			sql.append(" and cel.primary_code = '"+paraMap.get("primaryCode")+"'");
		}
//		sql.append("\r\n JOIN PRIMARY_CODE pc on cel.primary_code = pc.primary_code");
		if(!StringUtil.isEmpty((String)paraMap.get("cartonCode"))) {
			//按外箱条码查询时用JOIN
			sql.append("\r\n JOIN PRIMARY_CODE pc on cel.primary_code = pc.primary_code");
		} else {
			sql.append("\r\n LEFT JOIN PRIMARY_CODE pc on cel.primary_code = pc.primary_code");
		}
		
		
		if(!StringUtil.isEmpty((String)paraMap.get("cartonCode"))) {
			sql.append(" and pc.carton_code = '"+paraMap.get("cartonCode")+"'");
		}

		return PageQuery.jdbcSqlserverQuery(request, "productid", sql.toString(), pagesize);
	}

	public ResultSet getForCovertCodeReport(String sql) throws Exception {
		return EntityManager.query2(sql);
	}
	
	
	public int getCountExcPutCovertErrorLog(String whereSql) throws Exception {
		int count = 0;
		String sql = "select count(*) from CovertErrorLog as o "+whereSql +" order by o.id desc";
		count = EntityManager.getRecordCount(sql);
		return count;
	}
	
	/**
	 * 点击导出功能
	 * @param request
	 * @param pagesize
	 * @param whereSql
	 * @return
	 * @throws Exception
	 */
	public List getExcPutCovertErrorLog(String whereSql) throws Exception {
		String hql=" from CovertErrorLog as o "+whereSql +" order by o.id desc";
		return EntityManager.getAllByHql(hql);
	}
}

