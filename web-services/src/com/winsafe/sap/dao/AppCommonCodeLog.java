package com.winsafe.sap.dao;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.HibernateException;

import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.pager2.PageQuery;
import com.winsafe.sap.metadata.CommonCodeStatus;
import com.winsafe.sap.metadata.SyncStatus;
import com.winsafe.sap.pojo.CommonCodeLog;

public class AppCommonCodeLog {
	
	/**
	 *添加通用码 生成记录
	 * @param commonCodeLog
	 * @return
	 * @author ryan.xi
	 */
	public void addCommonCodeLog(CommonCodeLog commonCodeLog) {
		EntityManager.save(commonCodeLog);
		
	}
	
	/**
	 *获取需要生成通用码 的记录
	 * @param commonCodeLog
	 * @return
	 * @author ryan.xi
	 */
	public List<CommonCodeLog> getCommonCodeLogByWhere(String whereSql) {
		String hql = "from CommonCodeLog " + whereSql;
		return EntityManager.getAllByHql(hql);
		
	}

	public void updCommonCodeLog(CommonCodeLog commonCodeLog) throws HibernateException, SQLException {
		EntityManager.update(commonCodeLog);
	}

	public List<CommonCodeLog> getByPrintJobId(Integer printJobId) {
		String hql = "from CommonCodeLog where printJobId =" + printJobId;
		return EntityManager.getAllByHql(hql);
	}
	
	public List getCommonCodeLog(HttpServletRequest request, int pageSize, String whereSql) throws Exception{
		String hql=" from CommonCodeLog as o "+whereSql +" order by o.id desc";
		return PageQuery.hbmQuery(request, hql, pageSize);
	}

	public int updCommonCodeLogBySql(String sql) throws Exception {
		return EntityManager.executeUpdate(sql);
	}
	
	public int getSeqForCommonCodeFile(Integer printJobId, Long Id) throws Exception {
		String sql = "select seq from (select rownum as seq ,id  from (select id from COMMON_CODE_LOG where PRINTJOBID = "+printJobId+" order by id )) where id = " + Id;
		return EntityManager.getRecordCountBySql(sql);
	}

	public List<CommonCodeLog> getNotUploadedCommonCodeLog() {
		String hql=" from CommonCodeLog where status = "+CommonCodeStatus.GENERATED.getDatabaseValue()+" and syncStatus in ("+SyncStatus.NOT_UPLOADED.getValue()+","+SyncStatus.UPLOAD_ERROR.getValue()+")";
		return EntityManager.getAllByHql(hql);
	}
}

