package com.winsafe.drp.dao;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.pager2.PageQuery;
import com.winsafe.sap.pojo.UploadSAPLog;

/**
 * @author: jerry
 * @version:2009-10-13 下午05:33:25
 * @copyright:www.winsafe.cn
 */
public class RecordDao {

	public void save(UploadPrLog record) throws Exception {

		EntityManager.save(record);
	}

	public List<UploadPrLog> getAllRecord(HttpServletRequest request,
			String whereSql, int pageSize) throws Exception {
		String hql = " from UploadPrLog as r " + whereSql +" order by makedate desc";
		return PageQuery.hbmQuery(request, hql, pageSize);
	}

	public void del(UploadPrLog record) throws Exception {
		EntityManager.delete(record);
	}

	public UploadPrLog getRecordById(Integer id) throws Exception {
		String hql = " from UploadPrLog as r where id = " + id;
		return (UploadPrLog) EntityManager.find(hql);
	}

	public void updateDeal(Integer id, Integer isdeal) throws Exception {
		String hql = " update Upload_Pr_Log set isdeal = " + isdeal
				+ " where id = " + id;

		EntityManager.updateOrdelete(hql);

	}

	public void updateLog(Integer id, String logFilePath, int errorCount)
			throws Exception {
		String sql = "update Upload_Pr_Log set logFilePath = '" + logFilePath
				+ "', errorCount = nvl(errorCount,0) +" + errorCount + " where id = " + id;
		EntityManager.updateOrdelete(sql);

	}
	
	public List<UploadPrLog> getAllUnProcessedLog() throws Exception {
		List<UploadPrLog> list = new ArrayList<UploadPrLog>();
		String hql = " from UploadPrLog as r  where r.isdeal = 0 order by id";
		list.add((UploadPrLog)EntityManager.find(hql));
		return list;
	}
	
	public void updUploadPrlog(UploadPrLog uploadPrLog)throws Exception {		
		EntityManager.update(uploadPrLog);		
	}
	
	public int updUploadPrlogBySql(String sql) throws Exception {
		return EntityManager.executeUpdate(sql);
	}

	public boolean isDuplicateFileExists(String filename) {
		String hql = "select count(*) from UploadPrLog as r  where r.filename = '"+filename+"' and r.isdeal = 2";
		return EntityManager.getCount(hql) > 0;
	}
}
