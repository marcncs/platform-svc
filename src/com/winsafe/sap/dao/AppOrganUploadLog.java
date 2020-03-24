package com.winsafe.sap.dao;

import java.util.List; 

import javax.servlet.http.HttpServletRequest;



import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.pager2.PageQuery;
import com.winsafe.sap.metadata.SapUploadLogStatus;
import com.winsafe.sap.pojo.OrganUploadLog;
@SuppressWarnings("unchecked")
public class AppOrganUploadLog {
	
	public void addOrganUploadLog(OrganUploadLog organUploadLog) throws Exception {	
		EntityManager.save(organUploadLog);		
	}
	
	public void updOrganUploadLog(OrganUploadLog organUploadLog)throws Exception {		
		EntityManager.update(organUploadLog);		
	}
	
	public List<OrganUploadLog> getOrganUploadLog(HttpServletRequest request, int pageSize, String whereSql) throws Exception{
		String hql=" from OrganUploadLog as o "+whereSql+" order by o.id desc";
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
		String sql = "update Organ_Upload_Log set isdeal = "+SapUploadLogStatus.NOT_PROCESS.getDatabaseValue()+" where id = " + uploadId +" and isdeal="+SapUploadLogStatus.PROCESS_FAIL.getDatabaseValue();
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
		String sql = "select count(id) from Organ_Upload_Log where isdeal != " + SapUploadLogStatus.PROCESS_FAIL.getDatabaseValue() + " and filehashcode = '" + hashCode +"'";
		Integer count = EntityManager.getRecordCountBySql(sql);
		if(count > 0) {
			return true;
		} else {
			return false;
		}
	}
	
	public List getOrganUploadLogByWhere(String whereSql) {
		return EntityManager.getAllByHql(" from OrganUploadLog as u " + whereSql);
	}
	
	public int updOrganUploadLogBySql(String sql) throws Exception {
		return EntityManager.executeUpdate(sql);
	}
	
	public void updNum(int id, int isdeal, int errorCount, int totalCount, String logFilePath) throws Exception {
		String sql = "update Organ_Upload_Log set isdeal="+isdeal+",errorCount="+errorCount+"," +
				"totalCount="+totalCount+",logFilePath='"+logFilePath+"' where id=" + id + "";
		EntityManager.updateOrdelete(sql);
	}

	public OrganUploadLog getNotDealedLog() { 
		return (OrganUploadLog)EntityManager.find(" from OrganUploadLog where isDeal = 0 order by id");
	}

}

