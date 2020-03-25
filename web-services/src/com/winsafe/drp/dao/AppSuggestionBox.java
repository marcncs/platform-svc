package com.winsafe.drp.dao;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.pager2.PageQuery;

public class AppSuggestionBox {
	/**
	 * @author jason.huang 获取所有投诉信息
	 */
	public List getAllSuggestionBox() throws Exception {
		String sql = " from SuggestionBox as s ";
		return EntityManager.getAllByHql(sql);
	}

	/**
	 * @author jason.huang
	 * @param s
	 *            whereSql 按照一定条件获取投诉信息
	 */
	public List selectSuggestionBox(HttpServletRequest request, int pageSize, String whereSql)
			throws Exception {
		String hql = " from SuggestionBox f " + whereSql + " order by f.makeDate desc";
		return PageQuery.hbmQuery(request, hql, pageSize);
	}

	/**
	 * @author jason.huang
	 * @param s
	 *            增加投诉
	 */

	public void addSuggestionBox(Object s) {
		EntityManager.save(s);
	}

	/**
	 * @author jason.huang
	 * @param id
	 * @return
	 * @throws Exception
	 *             按照投诉的id获取投诉信息
	 */

	public SuggestionBox getSuggestionBoxByID(Long id) throws Exception {
		String sql = " from SuggestionBox as s where s.id='" + id + "'";
		return (SuggestionBox) EntityManager.find(sql);
	}

	/**
	 * @author jason.huang
	 * @param s
	 * @throws Exception
	 *             修改指定投诉信息
	 */

	public void updateSuggestionBox(SuggestionBox s) throws Exception {
		EntityManager.update(s);
	}

	/**
	 * 灵活修改投诉信息
	 * 
	 * @author jason.huang
	 * @param pid
	 * @param unitid
	 * @throws Exception
	 */
	public void updSuggestionBoxByID(Long id, String model, String osVersion, Long status,
			String installDate, String SuggestionBoxName, String SuggestionBoxImeiN)
			throws Exception {
		String sql = "update SuggestionBox set model = '" + model + "' , osVersion = '" + osVersion
				+ "' ,status = " + status + " ,installDate = to_date('" + installDate + " ','yyyy-mm-dd'),SuggestionBoxName = '"
				+ SuggestionBoxName + "' ,SuggestionBoxImeiN = '" + SuggestionBoxImeiN + "'  where id=" + id + " ";
		EntityManager.updateOrdelete(sql);
	}

	/**
	 * 
	 * @author jason.huang
	 * @param id
	 * @throws Exception
	 *             通过投诉的id删除投诉信息
	 */

	public void delSuggestionBox(String id) throws Exception {
		String sql = "delete from SuggestionBox where id='" + id + "'";
		EntityManager.updateOrdelete(sql);
	}

	/**
	 * 通过whereSql语句来查询投诉
	 * 
	 * @author jason.huang
	 * @param whereSql
	 * @return
	 * @throws Exception
	 */

	public List<SuggestionBox> findByWhereSql(String whereSql) throws Exception {
		String sql = " from SuggestionBox as s " + whereSql + " order by s.id desc ";
		return EntityManager.findByWhereSql(sql);
	}

	/**
	 * 
	 * @author jason.huang
	 * @param nccode
	 * @return
	 * @throws Exception
	 */

	public SuggestionBox findSuggestionBoxById(String id) throws Exception {
		String sql = " from SuggestionBox as s where s.id = '" + id + "'";
		return (SuggestionBox) EntityManager.find(sql);
	}

	
	public SuggestionBox findSuggestionBoxByIMEI(String IMEI) throws Exception {
		String sql = " from SuggestionBox as s where s.status=1 and s.SuggestionBoxImeiN = '" + IMEI + "'";
		return (SuggestionBox) EntityManager.find(sql);
	}

	/**
	 * 获取SuggestionBox表中最大ID+1
	 * 
	 * @author jason.huang
	 * @return int
	 */
	public int getMaxSuggestionBoxId() {
		String sql = "SELECT MAX(id) + 1 FROM SuggestionBox";
		return EntityManager.getRecordCount(sql);
	}

	/**
	 * 
	 * @author jason.huang
	 * @param nccode
	 * @return
	 * @throws Exception
	 *             根据SuggestionBoxImeiN码获得SuggestionBox
	 */

	public SuggestionBox findSuggestionBoxByImei(String SuggestionBoxImeiN) throws Exception {
		String sql = " from SuggestionBox as s where s.SuggestionBoxImeiN = '" + SuggestionBoxImeiN + "'";
		return (SuggestionBox) EntityManager.find(sql);
	}

}
