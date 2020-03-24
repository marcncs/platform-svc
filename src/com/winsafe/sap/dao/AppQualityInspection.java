package com.winsafe.sap.dao;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.pager2.PageQuery;
import com.winsafe.sap.pojo.QualityInspection;

public class AppQualityInspection {
	
	public List<QualityInspection> getQualityInspection(HttpServletRequest request, int pageSize, String whereSql) throws Exception{
		String hql=" from QualityInspection as o "+whereSql+" order by o.id desc";
		return PageQuery.hbmQuery(request, hql, pageSize);
	}
	
	public void addQualityInspection(QualityInspection qualityInspection) throws Exception {	
		EntityManager.save(qualityInspection);		
	}
	
	public void updQualityInspection(QualityInspection qualityInspection)throws Exception {		
		EntityManager.update(qualityInspection);		
	}
	
	public List getQualityInspectionByWhere(String whereSql) {
		return EntityManager.getAllByHql(" from QualityInspection as u " + whereSql);
	}

	public boolean isQualityInspectionExists(String mCode, String batch, Integer id) {
		String sql = "select count(*) from Quality_Inspection where mcode='"+mCode+"' and batch='"+batch+"'";
		if(id != null) {
			sql+=" and id <>"+id;
		}
		return EntityManager.getRecordCountBySql(sql) > 0;
	}

	public void delQualityInspection(Integer id) throws Exception {
		String sql = "delete from Quality_Inspection where id="+id;
		EntityManager.executeUpdate(sql);
		
	}

	public QualityInspection getQualityInspectionById(Integer id) {
		return (QualityInspection)EntityManager.find(" from QualityInspection where id=" + id);
	}
}

