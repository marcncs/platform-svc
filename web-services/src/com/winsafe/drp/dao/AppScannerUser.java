package com.winsafe.drp.dao;

import java.util.List;


import com.winsafe.drp.entity.EntityManager;
@SuppressWarnings("unchecked")
public class AppScannerUser {

	public List<ScannerUser> getScannersByUserID(String uids) throws Exception {
		String hql = "from ScannerUser where userid in (" + uids + ")";
		return (List) EntityManager.getAllByHql(hql);
	}
	
	public List<ScannerUser> getScanners() throws Exception {
		String hql = "from ScannerUser ";
		return (List) EntityManager.getAllByHql(hql);
	}
	
	
	/**
	 * 新增采集器编号和用户的关系
	 */
	public boolean InsertScannerUser(ScannerUser scanneruser) throws Exception {
		try {
			EntityManager.save(scanneruser);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	/**
	 * 删除ScannerUser表某条记录
	 */
	public boolean delScannerUserById(int id) throws Exception {
		try {
			EntityManager.updateOrdelete("delete from scanner_user where id=" + id);
		} catch (Exception e) {
			return false;
		}
		return true;
	}
	
	/**
	 * 获取用户采集器
	 * Create Time 2014-6-11 上午09:42:45 
	 * @param scannerNo
	 * @return
	 * @throws Exception
	 * @author lipeng
	 */
	public ScannerUser getScanner(String scannerNo) throws Exception {
		String hql = "from ScannerUser where scanner='" + scannerNo + "'";
		return  (ScannerUser) EntityManager.find(hql);
	}

	/**
	 * 根据ID查询ScannerUser
	 */
	public ScannerUser getScannerUserById(int id) throws Exception {
		String hql = "from ScannerUser where id = " + id;
		return (ScannerUser) EntityManager.find(hql);
	}

	/**
	 * 根据ID更改ScannerUser
	 */
	public boolean updScannerUser(int id, String scanner, String uid) throws Exception {
		try {
			String sql = "update scanner_user set scanner = '" + scanner + "' , userid = '" + uid
					+ "' where id = " + id;
			EntityManager.updateOrdelete(sql);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

}
