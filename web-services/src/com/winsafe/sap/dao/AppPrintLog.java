package com.winsafe.sap.dao;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;


import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.pager2.PageQuery;
import com.winsafe.sap.pojo.PrintLog;
public class AppPrintLog {
	
	
	
	public List getPrintLog(HttpServletRequest request, int pageSize, String whereSql) throws Exception{
		String hql=" from PrintLog as o "+whereSql +" order by o.printLogId desc";
		return PageQuery.hbmQuery(request, hql, pageSize);
	}

	public List<PrintLog> getPrintLogByJobID(String printJobId) {
		return EntityManager.getAllByHql("from PrintLog where printJobId='"+printJobId+"'");
	}
	
	public Date getMinPrintDateByJobID(String printJobId) {
		return (Date) EntityManager.find("select min(printDate) from PrintLog where printJobId='"+printJobId+"'");
	}
	

}

