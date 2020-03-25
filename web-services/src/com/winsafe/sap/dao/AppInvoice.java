package com.winsafe.sap.dao;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.HibernateException;

import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.pager2.PageQuery;
import com.winsafe.sap.pojo.Invoice;
public class AppInvoice {
	
	public void addInvoices(List<Invoice> invoices) throws Exception {	
		EntityManager.batchSave(invoices);
	}

	public List<Invoice> getInvoices(HttpServletRequest request, int pagesize,
			String whereSql) throws Exception {
		String hql="select i from Invoice as i "+whereSql +" order by i.invoiceDate desc";
		return PageQuery.hbmQuery(request, hql, pagesize);
	}
	
	public List<Invoice> getInvoices(String whereSql) throws Exception {
		String hql="select i from Invoice as i "+whereSql +" order by i.invoiceDate desc";
		return EntityManager.getAllByHql(hql);
	}

	public List getInvoicesDataForRollingPrice() throws HibernateException, SQLException {
		String sql="select MATERIAL_CODE as mcode, PARTN_SOLD as oecode, SUBSTR(INVOICE_DATE, 0, 6) as idate, sum(INVOICE_QTY) as qty, sum(TO_NUMBER(NET_VAL)) as totalPrice " +
				"from INVOICE " +
				"where NET_VAL is not NULL " +
				"GROUP BY MATERIAL_CODE, PARTN_SOLD, SUBSTR(INVOICE_DATE, 0, 6) ";
		return EntityManager.jdbcquery(sql);
	}

	public List getInvoices(String mcode, String oecode, String date, String nextMonth) throws HibernateException, SQLException {
		String sql="select sum(INVOICE_QTY) as qty, sum(TO_NUMBER(NET_VAL)) as totalPrice from INVOICE  " +
			"where MATERIAL_CODE = '"+mcode+"' and PARTN_SOLD = '"+oecode+"' and INVOICE_DATE >= '"+date+"' and INVOICE_DATE < '"+nextMonth+"'";
		return EntityManager.jdbcquery(sql);
	}

	public Double getInvoice(String nccode, String mCode) {
		String hql="select sum(cast(netVal as double)) from Invoice where materialCode = '"+mCode+"' and deliveryNumber = '"+nccode+"'";
		return (Double)EntityManager.getdoubleSum(hql);
	}
}

