package com.winsafe.drp.dao;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.pager2.PageQuery;

/**
 * @author : jerry
 * @version : 2009-8-8 上午10:06:48 www.winsafe.cn
 */
public class AppSampleBillDetail {

	@SuppressWarnings("unchecked")
	public List<SampleBillDetail> findAll(HttpServletRequest request,
			String whereStr, Integer pageSize) throws Exception {
		String hql = " from SampleBillDetail as s " + whereStr
				+ " order by s.makedate desc ";
		return PageQuery.hbmQuery(request, hql, pageSize);
	}

	public SampleBillDetail findByID(Integer id) throws Exception {
		String hql = " from SampleBillDetail as s where s.id = " + id;
		return (SampleBillDetail) EntityManager.find(hql);
	}

	public List<SampleBillDetail> findBySbid(String sbid) throws Exception {
		String hql = " from SampleBillDetail as s where s.sbid ='" + sbid +"'"
				+ " order by s.id desc ";
		return EntityManager.getAllByHql(hql);
	}

	public void save(SampleBillDetail sampleBillDetail) throws Exception {
		EntityManager.save(sampleBillDetail);
	}

	public void update(SampleBillDetail sampleBillDetail) throws Exception {
		EntityManager.update(sampleBillDetail);
	}

	public void delete(SampleBillDetail sampleBillDetail) throws Exception {
		EntityManager.delete(sampleBillDetail);
	}

	public void delete(Integer id) throws Exception {
		String hql = "delete from sample_bill_detail where id = " + id;
		EntityManager.updateOrdelete(hql);
	}

	public void deleteBySbid(String sbid) throws Exception {

		String hql = "delete from sample_bill_detail where sbid = '"
				+ sbid + "'";
		EntityManager.updateOrdelete(hql);
	}

	public void deleteAll() throws Exception {
		String hql = "delete from sample_bill_detail ";
		EntityManager.updateOrdelete(hql);
	}
}
