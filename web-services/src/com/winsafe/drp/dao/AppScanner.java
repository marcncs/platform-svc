package com.winsafe.drp.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.exception.NotExistException;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.StringUtil;
import com.winsafe.hbm.util.pager2.PageQuery;

public class AppScanner {

	/**
	 * @author jason.huang 获取所有采集器信息
	 */
	public List getAllScanner() throws Exception {
		String sql = " from Scanner as s ";
		return EntityManager.getAllByHql(sql);
	}

	/**
	 * @author jason.huang
	 * @param s
	 *            whereSql 按照一定条件获取采集器信息
	 */
	public List selectScanner(HttpServletRequest request, int pageSize, String whereSql)
			throws Exception {
		String hql = " from Scanner f " + whereSql + " order by f.id asc";
		return PageQuery.hbmQuery(request, hql, pageSize);
	}

	/**
	 * @author jason.huang
	 * @param s
	 *            增加采集器
	 */

	public void addNewScanner(Object s) {
		EntityManager.save(s);
	}

	/**
	 * @author jason.huang
	 * @param id
	 * @return
	 * @throws Exception
	 *             按照采集器的id获取采集器信息
	 */

	public Scanner getScannerByID(Long id) throws Exception {
		String sql = " from Scanner as s where s.id='" + id + "'";
		return (Scanner) EntityManager.find(sql);
	}

	/**
	 * @author jason.huang
	 * @param s
	 * @throws Exception
	 *             修改指定采集器信息
	 */

	public void updateScanner(Scanner s) throws Exception {
		EntityManager.update(s);
	}

	/**
	 * 灵活修改采集器信息
	 * 
	 * @author jason.huang
	 * @param pid
	 * @param unitid
	 * @throws Exception
	 */
	public void updScannerByID(Long id, String model, String osVersion, Long status,
			String installDate, String scannerName, String scannerImeiN)
			throws Exception {
		String sql = "update Scanner set model = '" + model + "' , osVersion = '" + osVersion
				+ "' ,status = " + status + " ,installDate = to_date('" + installDate + " ','yyyy-mm-dd'),scannerName = '"
				+ scannerName + "' ,scannerImeiN = '" + scannerImeiN + "'  where id=" + id + " ";
		EntityManager.updateOrdelete(sql);
	}

	/**
	 * 
	 * @author jason.huang
	 * @param id
	 * @throws Exception
	 *             通过采集器的id删除采集器信息
	 */

	public void delScanner(String id) throws Exception {
		String sql = "delete from Scanner where id='" + id + "'";
		EntityManager.updateOrdelete(sql);
	}

	/**
	 * 通过whereSql语句来查询采集器
	 * 
	 * @author jason.huang
	 * @param whereSql
	 * @return
	 * @throws Exception
	 */

	public List<Scanner> findByWhereSql(String whereSql) throws Exception {
		String sql = " from Scanner as s " + whereSql + " order by s.id desc ";
		return EntityManager.findByWhereSql(sql);
	}

	/**
	 * 
	 * @author jason.huang
	 * @param nccode
	 * @return
	 * @throws Exception
	 */

	public Scanner findScannerById(String id) throws Exception {
		String sql = " from Scanner as s where s.id = '" + id + "'";
		return (Scanner) EntityManager.find(sql);
	}

	
	public Scanner findScannerByIMEI(String IMEI) throws Exception {
		String sql = " from Scanner as s where s.status=1 and s.scannerImeiN = '" + IMEI + "'";
		return (Scanner) EntityManager.find(sql);
	}

	/**
	 * 获取Scanner表中最大ID+1
	 * 
	 * @author jason.huang
	 * @return int
	 */
	public int getMaxScannerId() {
		String sql = "SELECT MAX(id) + 1 FROM Scanner";
		return EntityManager.getRecordCount(sql);
	}

	/**
	 * 
	 * @author jason.huang
	 * @param nccode
	 * @return
	 * @throws Exception
	 *             根据scannerImeiN码获得scanner
	 */

	public Scanner findScannerByImei(String scannerImeiN) throws Exception {
		String sql = " from Scanner as s where s.scannerImeiN = '" + scannerImeiN + "'";
		return (Scanner) EntityManager.find(sql);
	}

	/**
	 * 
	 * @author jason.huang
	 * @param nccode
	 * @return
	 * @throws Exception
	 *             根据id获得imein
	 */
	public String getImeiNById(String id) throws Exception {
		if (id == null || "".equals(id)) {
			return "";
		}
		Scanner o = getScannerByID(Long.parseLong(id));
		if (o != null) {
			return o.getScannerImeiN();
		}
		return "";
	}

}
