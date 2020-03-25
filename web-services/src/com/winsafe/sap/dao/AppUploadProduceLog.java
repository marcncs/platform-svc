package com.winsafe.sap.dao;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.pager2.PageQuery;
import com.winsafe.sap.metadata.SapUploadLogStatus;
import com.winsafe.sap.pojo.UploadProduceLog;

public class AppUploadProduceLog {  
	
	public List<UploadProduceLog> getUploadProduceLog(HttpServletRequest request, int pageSize, String whereSql) throws Exception{
		String hql=" from UploadProduceLog as o "+whereSql+" order by o.id desc";
		return PageQuery.hbmQuery(request, hql, pageSize);
	}
	
	public List getUploadProduceLogBySql(HttpServletRequest request, int pageSize, String whereSql) throws Exception{
		String sql="select upl.*,o.organname from UPLOAD_PRODUCE_LOG upl " +
				"join ORGAN o on upl.makeorganid = o.id " + whereSql +
				" order by o.id desc";
		return PageQuery.jdbcSqlserverQuery(request, " id desc ", sql, pageSize);
	} 
	
	public void addUploadProduceLog(UploadProduceLog uploadSapLog) throws Exception {	
		EntityManager.save(uploadSapLog);		
	}
	
	public void updUploadProduceLog(UploadProduceLog uploadSapLog)throws Exception {		
		EntityManager.update(uploadSapLog);		
	}
	
	public List getUploadProduceLogByWhere(String whereSql) {
		return EntityManager.getAllByHql(" from UploadProduceLog as u " + whereSql);
	}

	public List<UploadProduceLog> getAllUnProcessedLog(Integer fileType) {
		return EntityManager.getAllByHql(" from UploadProduceLog where fileType = "+fileType+" and isDeal = "+SapUploadLogStatus.NOT_PROCESS.getDatabaseValue());
	}

	/**
	 * 更新状态使其重新处理
	 * Create Time: Oct 8, 2014 4:42:02 PM 
	 * @param uploadId
	 * @author ryan.xi
	 * @throws Exception 
	 */
	public void reProcessFile(Integer uploadId) throws Exception {
		String sql = "update UPLOAD_PRODUCE_LOG set isdeal = "+SapUploadLogStatus.NOT_PROCESS.getDatabaseValue()+" where id = " + uploadId +" and isdeal="+SapUploadLogStatus.PROCESS_FAIL.getDatabaseValue();
		EntityManager.updateOrdelete(sql);
	}
	
	/**
	 * 查询是否上传过相同的且已经成功处理完的文件
	 * Create Time: Oct 8, 2014 4:42:02 PM 
	 * @param uploadId
	 * @return
	 * @author ryan.xi
	 */
	public boolean isFileAlreadyExists(String hashCode, int id) {
		String sql = "select count(id) from UPLOAD_PRODUCE_LOG where id<>"+id+" and isdeal != " + SapUploadLogStatus.PROCESS_FAIL.getDatabaseValue() + " and filehashcode = '" + hashCode +"'";
		Integer count = EntityManager.getRecordCountBySql(sql);
		if(count > 0) {
			return true;
		} else {
			return false;
		}
	}

	public UploadProduceLog getUploadProduceLogByPrintJobId(Integer printJobId) {
		return (UploadProduceLog)EntityManager.find(" from UploadProduceLog where printJobId = " + printJobId +" order by id desc ");
	}
}

