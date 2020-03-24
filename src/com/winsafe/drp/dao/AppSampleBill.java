package com.winsafe.drp.dao;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.HibernateException;

import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.pager2.PageQuery;

/**
 * @author : jerry
 * @version : 2009-8-8 上午10:06:48 www.winsafe.cn
 */
public class AppSampleBill {
 
	public List<SampleBill> findAll(HttpServletRequest request, String whereStr,
			Integer pageSize) throws Exception {
		String hql = " from SampleBill as s " + whereStr
				+ " order by s.makedate desc ";
		return PageQuery.hbmQuery(request, hql, pageSize);
	}

	public SampleBill findByID(String id) throws Exception {
		String hql = " from SampleBill as s where s.id = '" + id + "'";
		return (SampleBill) EntityManager.find(hql);
	}

	public void save(SampleBill sampleBill) throws Exception {
		EntityManager.save(sampleBill);
	}

	public void update(SampleBill sampleBill) throws Exception {
		EntityManager.update(sampleBill);
	}

	public void delete(SampleBill sampleBill) throws Exception {
		EntityManager.delete(sampleBill);
	}

	public void delete(Integer id) throws Exception {
		String hql = "delete from sample_Bill as s where s.id = " + id;
		EntityManager.updateOrdelete(hql);
	}

	public void deleteAll() throws Exception {
		String hql = "delete from sample_Bill as s ";
		EntityManager.updateOrdelete(hql);
	}
	
	public void updSampleBillIsShipment(String id, int isshipment, int userid) throws HibernateException, SQLException, Exception{
	    
	    String sql = "update sample_bill set isshipment=" + isshipment
		+ ",shipmentid=" + userid + ",shipmentdate='"
		+ DateUtil.getCurrentDateTime() + "' where id='" + id + "'";
	    EntityManager.updateOrdelete(sql);
	}
}
