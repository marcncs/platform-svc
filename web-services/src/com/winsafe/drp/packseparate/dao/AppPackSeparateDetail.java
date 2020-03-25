package com.winsafe.drp.packseparate.dao;


import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;

import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.packseparate.pojo.PackSeparateDetail;

public class AppPackSeparateDetail {
	
	/**
	 * 添加拆包单据
	 * @param packSeparate
	 * @return
	 * @author ryan.xi
	 */
	public void addPackSeparateDetail(PackSeparateDetail packSeparateDetail) throws Exception {
		EntityManager.save(packSeparateDetail);
		
	}
	
	/**
	 * 更新拆包单据
	 * @param packSeparate
	 * @return
	 * @author ryan.xi
	 * @throws Exception 
	 */
	public void updPackSeparateDetail(PackSeparateDetail packSeparateDetail) throws Exception {
		EntityManager.update(packSeparateDetail);
		
	}
	
	public List<PackSeparateDetail> getPackSeparateDetailsByPsid(String psid) throws Exception {
		String sql = " from PackSeparateDetail as psd where psd.psid = '"+psid+"' ";
		return EntityManager.getAllByHql(sql);
	}
	
	public PackSeparateDetail getPackSeparateDetailByPsid(String psid) throws Exception {
		String sql = " from PackSeparateDetail as psd where psd.psid = '"+psid+"' ";
		return (PackSeparateDetail)EntityManager.find(sql);
	}

	public void delPackSeparateDetailByPsid(String psid) throws Exception {
		String sql = "delete from PACK_SEPARATE_DETAIL where psid='" + psid + "'";
		EntityManager.updateOrdelete(sql);
	}

}
