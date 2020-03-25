package com.winsafe.erp.dao;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.winsafe.drp.entity.EntityManager;
import com.winsafe.erp.pojo.UploadCartonSeqLog;
import com.winsafe.hbm.util.pager2.PageQuery;
import com.winsafe.sap.metadata.SapUploadLogStatus;

public class AppCartonSeqDao {
	
	public void addUploadCartonSeqLog(UploadCartonSeqLog uploadcsLog) throws Exception {		
		EntityManager.save(uploadcsLog);		
	}
	
	public void updUploadCartonSeqLog(UploadCartonSeqLog uploadcsLog)throws Exception {		
		EntityManager.update(uploadcsLog);		
	}
	
	public void delUploadCartonSeqLogById(UploadCartonSeqLog uploadcsLog)throws Exception {		
		EntityManager.delete(uploadcsLog);
	}

	public UploadCartonSeqLog getCartonSeqLogByMD5(String md5) {
		String hql = "from UploadCartonSeqLog where fileHaseCode = '"+md5+"'"; 
		return (UploadCartonSeqLog)EntityManager.find(hql);
	}
	
	public List<UploadCartonSeqLog> getCartonSeqLogListByCondition(String whereSQL) {
		String hql = "from UploadCartonSeqLog " + whereSQL; 
		return (List)EntityManager.find(hql);
	}
	
	public List getCartonSeqLogListByCondition(HttpServletRequest request, int pageSize, String whereSql) throws Exception{
		String sql="select * from UPLOAD_CARTON_SEQ_LOG " + whereSql;
		return PageQuery.jdbcSqlserverQuery(request, " MAKEDATE desc ", sql, pageSize);
	}

	public List<UploadCartonSeqLog> getAllUnProcessedLog() {
		String hql = "from UploadCartonSeqLog where isDeal = "+SapUploadLogStatus.NOT_PROCESS.getDatabaseValue(); 
		return EntityManager.getAllByHql(hql);
	} 
}

