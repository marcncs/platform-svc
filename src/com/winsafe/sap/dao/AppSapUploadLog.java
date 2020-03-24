package com.winsafe.sap.dao;

import java.util.List;

import javax.servlet.http.HttpServletRequest;



import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.pager2.PageQuery;
import com.winsafe.sap.metadata.SapUploadLogStatus;
import com.winsafe.sap.pojo.UploadSAPLog;
@SuppressWarnings("unchecked")
public class AppSapUploadLog {
	
	public void addUploadSAPlog(UploadSAPLog uploadSapLog) throws Exception {	
		EntityManager.save(uploadSapLog);		
	}
	
	public void updUploadSAPlog(UploadSAPLog uploadSapLog)throws Exception {		
		EntityManager.update(uploadSapLog);		
	}
	
	public List<UploadSAPLog> getSapUploadLog(HttpServletRequest request, int pageSize, String whereSql) throws Exception{
		String hql=" from UploadSAPLog as o "+whereSql+" order by o.id desc";
		return PageQuery.hbmQuery(request, hql, pageSize);
	}

	/**
	 * 更新状态使其重新处理
	 * Create Time: Oct 8, 2014 4:42:02 PM 
	 * @param uploadId
	 * @author ryan.xi
	 * @throws Exception 
	 */
	public void reProcessFile(Integer uploadId) throws Exception {
		String sql = "update UPLOAD_SAP_LOG set isdeal = "+SapUploadLogStatus.NOT_PROCESS.getDatabaseValue()+" where id = " + uploadId +" and isdeal="+SapUploadLogStatus.PROCESS_FAIL.getDatabaseValue();
		EntityManager.updateOrdelete(sql);
	}
	/**
	 * 查询是否上传过相同的且已经成功处理完的文件
	 * Create Time: Oct 8, 2014 4:42:02 PM 
	 * @param uploadId
	 * @return
	 * @author ryan.xi
	 */
	public boolean isFileAlreadyExists(String hashCode) {
		String sql = "select count(id) from UPLOAD_SAP_LOG where isdeal != " + SapUploadLogStatus.PROCESS_FAIL.getDatabaseValue() + " and filehashcode = '" + hashCode +"'";
		Integer count = EntityManager.getRecordCountBySql(sql);
		if(count > 0) {
			return true;
		} else {
			return false;
		}
	}
	
	public List getSapUploadLogByWhere(String whereSql) {
		return EntityManager.getAllByHql(" from UploadSAPLog as u " + whereSql);
	}
	
	public UploadSAPLog findSapUploadLogByWhere(String whereSql) {
		return (UploadSAPLog)EntityManager.find(" from UploadSAPLog as u " + whereSql);
	}
	
	public int updSapUploadLogBySql(String sql) throws Exception {
		return EntityManager.executeUpdate(sql);
	}
	
	public void updNum(int id, int isdeal, int errorCount, int totalCount, String logFilePath,  int errorType) throws Exception {
		String sql = "update UPLOAD_SAP_LOG set isdeal="+isdeal+",errorCount="+errorCount+"," +
				"totalCount="+totalCount+",logFilePath='"+logFilePath+",errorType='"+errorType+"' where id=" + id + "";
		EntityManager.updateOrdelete(sql);
	}

}

