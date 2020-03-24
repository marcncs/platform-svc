package com.winsafe.drp.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.HibernateException;

import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.StringFilters;
import com.winsafe.hbm.util.StringUtil;
import com.winsafe.hbm.util.pager2.PageQuery;

public class AppRuleUserWH {
	
	
	public List queryRuleWHByUid(HttpServletRequest request, int pagesize, String whereSql) throws Exception{
		String hql=" select new map(w.id as wid,w.warehousename as warehousename,w.makeorganid  as makeorganid )  from Warehouse  w "+whereSql +" order by w.id";
		return PageQuery.hbmQuery(request, hql, pagesize);
	}
	
	public List queryRuleWHByWid(HttpServletRequest request, int pagesize, String whereSql) throws Exception{
		String hql=" select new map(u.id as uid,u.loginname as loginname,u.makeorganid  as makeorganid,u.realname as realname,o.organname as organname)  from Users u,Organ o "+whereSql +" order by u.userid";
		return PageQuery.hbmQuery(request, hql, pagesize);
	}
	
	/**
	 * 主要功能：检查当前用户的仓库权限
	 * 
	 * @param warehouses
	 *            拥有的所有仓库
	 */
	public List<RuleUserWh> chkUserWarehouse(List<Warehouse> warehouses, Integer userId) {
		// 处理后返回的所有仓库权限
		List<RuleUserWh> rules = new ArrayList<RuleUserWh>();
		AppWarehouse aw = new AppWarehouse();
		AppOrgan ao = new AppOrgan();
		for (Warehouse warehouse : warehouses) {
			// 判断当前数据库是否有历史权限记录
			// 根据条件得到对应的仓库权限对象
			RuleUserWh ruleFlag = getRuleByWH(warehouse, userId);
			// 如果没有则新增权限记录(默认仓库权限为可见)
			if (ruleFlag == null) {
				// 设置默认为true
				RuleUserWh ruleUserWh = new RuleUserWh();
				ruleUserWh.setUserId(userId);
				ruleUserWh.setWarehouseId(warehouse.getId());
				// 插入数据库
				addRuleUserWh(ruleUserWh);
				// 设置仓库名
				ruleUserWh.setWarehouseName(warehouse.getWarehousename());
				// //设置机构id 名称
				// try {
				// ruleUserWh.setOrganId(aw.getWarehouseByID(warehouse.getId()).getMakeorganid());
				// ruleUserWh.setOrganName(ao.getOrganByID(ruleUserWh.getOrganId()).getOrganname());
				// } catch (Exception e) {
				// e.printStackTrace();
				// }
				// 插入返回权限列表
				rules.add(ruleUserWh);

			} else {
				// 直接添加入权限列表
				rules.add(ruleFlag);
			}
		}
		return rules;
	}

	/**
	 * 主要功能：根据仓库ID查看是否有权限记录
	 * 
	 * @param warehouse
	 *            仓库
	 * @return 权限记录(null:没有记录)
	 */
	public RuleUserWh getRuleByWH(Warehouse warehouse, Integer userId) {
		List<RuleUserWh> rules = EntityManager.getAllByHql("from RuleUserWh where warehouseId = '" + warehouse.getId()
				+ "' and userId = " + userId);
		if (rules.size() > 0) {
			return rules.get(0);
		}
		return null;
	}

	/**
	 * 主要功能：得到指定用户的所有可见仓库权限
	 * 
	 * @param userId
	 *            指定用户ID
	 * @return 可见仓库权限列表(null:没有仓库数据)
	 */
	public List<RuleUserWh> getRuleByUserId(Integer userId) {
		List<RuleUserWh> rules = EntityManager.getAllByHql("from RuleUserWh where userId = " + userId);
		if (rules.size() > 0) {
			return rules;
		}
		return null;
	}

	public List<RuleUserWh> getRuleByUserId(HttpServletRequest request, int pagesize, Integer userId, String KeyWord)
			throws Exception {

		KeyWord = StringFilters.filterSql(KeyWord);

		StringBuilder hql = new StringBuilder();
		hql.append("select ru from RuleUserWh as ru,Warehouse as w,Organ as o");
		hql.append(" where ru.warehouseId=w.id");
		hql.append(" and o.id=w.makeorganid");
		hql.append(" and ru.userId = " + userId);
		if (!StringUtil.isEmpty(KeyWord)) {
			hql.append(" and (o.id like '" + KeyWord + "%'");
			hql.append(" or o.organname like '%" + KeyWord + "%'");
			hql.append(" or w.id like '" + KeyWord + "%'");
			hql.append(" or w.warehousename like '%" + KeyWord + "%')");
		}
		hql.append(" order by ru.activeFlag desc,w.makeorganid,w.id");
		return PageQuery.hbmQuery(request, hql.toString(), pagesize);
	}

	/**
	 * 主要功能：新增仓库权限
	 * 
	 * @param ruleUserWh
	 *            仓库权限对象
	 */
	public void addRuleUserWh(RuleUserWh ruleUserWh) {
		EntityManager.save(ruleUserWh);
	}

	public void addRuleUserWh(String wid, Integer userid) throws Exception {
		String sql = "insert into Rule_User_Wh(warehouse_id,user_id,activeFlag) select '" + wid + "','" + userid
				+ "',1 from dual where not exists (select id from Rule_User_Wh where warehouse_id='" + wid + "' and user_id='" + userid
				+ "')";
		EntityManager.updateOrdelete(sql);
	}

	/**
	 * 主要功能：根据仓库名过滤仓库
	 * 
	 * @param rules
	 *            所有权限仓库列表
	 * @param warehouseName
	 *            查询条件(仓库名)
	 * @return 过滤后列表
	 */
	public List<RuleUserWh> searchRulesByWHName(List<RuleUserWh> rules, String warehouseName) {
		List<RuleUserWh> resultList = new ArrayList<RuleUserWh>();
		for (RuleUserWh ruleUserWh : rules) {
			// 符合条件的仓库
			if (ruleUserWh.getWarehouseName().indexOf(warehouseName) > -1) {
				resultList.add(ruleUserWh);
			}
		}
		return resultList;
	}

	/**
	 * 
	 * 主要功能：更新仓库权限
	 * 
	 * @param rules
	 *            仓库权限列表
	 * @param ids
	 *            可见的id列表
	 */
	public void updateRuleUserWH(List<RuleUserWh> rules, String[] ids) {
		for (RuleUserWh ruleUserWh : rules) {
			try {
				// 得到仓库权限ID
				Integer ruleId = ruleUserWh.getId();
				// 判断是否设置为可见
				if (existIds(ruleId.toString(), ids)) {
					// 更新仓库权限
					updateRuleUserWH(ruleUserWh, true);
				} else {
					// 更新仓库权限
					updateRuleUserWH(ruleUserWh, false);
				}
			} catch (HibernateException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public void updateActiveFlag(Integer userid, boolean value, String KeyWord) throws Exception {

		KeyWord = StringFilters.filterSql(KeyWord);

		StringBuilder hql = new StringBuilder();
		hql.append("update rule_user_wh ");
		hql.append(" set activeFlag=" + (value ? 1 : 0));
		hql.append("where user_id = " + userid );
		hql.append(" and exists (");
		hql.append("select * from rule_user_wh ru,warehouse w,organ o ");
		hql.append(" where ru.warehouse_id=w.id");
		hql.append(" and w.makeorganid=o.id");
		if (!StringUtil.isEmpty(KeyWord)) {
			hql.append(" and (o.id like '" + KeyWord + "%'");
			hql.append(" or o.organname like '%" + KeyWord + "%'");
			hql.append(" or w.id like '" + KeyWord + "%'");
			hql.append(" or w.warehousename like '%" + KeyWord + "%')");
		}
		hql.append(")");
		EntityManager.updateOrdelete(hql.toString());
	}

	public void updateRuleUserWH(String[] ids, String[] values) throws Exception {
		String id;
		String value;
		RuleUserWh ru;
		for (int i = 0; i < ids.length; i++) {
			id = ids[i];
			value = values[i];
			ru = getRuleUserWHByID(id);
			if (ru == null) {
				continue;
			} else {
				ru.setActiveFlag(Boolean.parseBoolean(value));
				EntityManager.update(ru);
			}
		}
	}

	public RuleUserWh getRuleUserWHByID(String id) {
		String sql = " from RuleUserWh as p where p.id='" + id + "'";
		return (RuleUserWh) EntityManager.find(sql);
	}

	/**
	 * 主要功能：判断Id是否存在
	 * 
	 * @param id
	 *            要判断的ID
	 * @param ids
	 *            ID集合
	 * @return true:存在 false:不存在
	 */
	private boolean existIds(String id, String[] ids) {
		for (int i = 0; i < ids.length; i++) {
			if (id.equals(ids[i])) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 主要功能：更新仓库权限
	 * 
	 * @param ruleUserWh
	 *            仓库权限
	 * @throws HibernateException
	 * @throws SQLException
	 */
	public void updateRuleUserWH(RuleUserWh ruleUserWh) throws HibernateException, SQLException {
		EntityManager.update(ruleUserWh);
	}

	/**
	 * 主要功能：更新仓库权限(带activeFlag)
	 * 
	 * @param ruleUserWh
	 *            仓库权限
	 * @param activeFlag
	 *            true:可见;false:不可见
	 * @throws HibernateException
	 * @throws SQLException
	 */
	public void updateRuleUserWH(RuleUserWh ruleUserWh, Boolean activeFlag) throws HibernateException, SQLException {
		ruleUserWh.setActiveFlag(activeFlag);
		EntityManager.update(ruleUserWh);
	}

	/**
	 * 主要功能:删除多余的仓库权限
	 * 
	 * @param userid
	 *            选中的用户id
	 * @param rules
	 *            用户仓库权限
	 * @param allWarehouses
	 *            仓库
	 */
	public void delOldUserWarehouse(String userid, List<RuleUserWh> rules, List<Warehouse> allWarehouses) {
		for (RuleUserWh ruleUserWh : rules) {
			// 判断当前用户的仓库权限是否有对应的仓库存在
			// 如果不存在,删除多余的仓库权限数据
			if (checkExistInWarehouse(ruleUserWh.getWarehouseId(), allWarehouses) == false) {
				delRuleUserWHByWHID(ruleUserWh.getWarehouseId(), userid);
			}
		}
	}

	/**
	 * 主要功能:检查仓库是否存在
	 * 
	 * @param WHID
	 *            仓库id
	 * @param allWarehouses
	 *            所有有权限的仓库
	 * @return true:存在;false:不存在
	 */
	private boolean checkExistInWarehouse(String WHID, List<Warehouse> allWarehouses) {
		for (Warehouse warehouse : allWarehouses) {
			// ID判断存在则返回true;
			if (WHID.equals(warehouse.getId())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 主要功能:删除仓库权限表
	 * 
	 * @param WHID
	 *            仓库id
	 * @param visitUserId
	 *            选中人Id
	 */
	public void delRuleUserWHByWHID(String WHID, String visitUserId) {
		String delSql = "from RuleUserWh where warehouseId ='" + WHID + "' and userId = " + visitUserId;
		EntityManager.deleteByHql(delSql);
	}

	public List<Warehouse> queryRuleUserWh(int userid) throws Exception {
		String sql = "select w from Warehouse as w,RuleUserWh as wv where w.useflag=1  and w.id=wv.warehouseId and wv.userId="
				+ userid + " order by w.id,wv.id";
		return EntityManager.getAllByHql(sql);
	}
	
	public List<Warehouse> queryWhByScanner(String scannerNo) throws Exception {
		String sql = "select w from Warehouse w,ScannerWarehouse sw,Scanner s " +
				" where w.useflag=1 and w.id=sw.warehouseid " +
				" and sw.scannerid =s.id " +
				" and s.status=1 and s.scannerImeiN='" + scannerNo +"' ) " +
				" order by w.id,sw.id,s.id";
		return EntityManager.getAllByHql(sql);
	}

	public List<Warehouse> queryRuleUserWh(String userid, int warehouseProperty) throws Exception {
		String sql = "select w from Warehouse as w,RuleUserWh as wv where w.useflag=1 and wv.activeFlag=1 and w.id=wv.warehouseId and wv.userId="
				+ userid + " and w.warehouseproperty=" + warehouseProperty + " order by w.id";
		return EntityManager.getAllByHql(sql);
	}

	public List<Warehouse> queryRuleUserWh(int userid, String organid) throws Exception {
		String sql = "select w from Warehouse as w,RuleUserWh as wv where w.useflag=1 "
				+ "and w.id=wv.warehouseId and wv.userId=" + userid + "  and w.makeorganid='" + organid + "'"
				+ " order by w.id";
		return EntityManager.getAllByHql(sql);
	}

	public List<Warehouse> queryRuleUserWh(int userid, String organid, int warehouseProperty) throws Exception {
		String sql = "select w from Warehouse as w,RuleUserWh as wv where w.useflag=1 "
				+ "and w.id=wv.warehouseId and wv.userId=" + userid + " and wv.activeFlag=1 and w.makeorganid='" + organid
				+ "' and w.warehouseproperty=" + warehouseProperty + " order by w.id";
		return EntityManager.getAllByHql(sql);
	}

	public List queryUserIncludeOrgan(String organid) {
		String sql = "select ruw.userid from RuleUserWh ruw,Warehouse w where ruw.warehouseId=w.id and w.makeorganid=" + organid
				+ " and ruw.activeFlag=1 group by ruw.userid";
		return EntityManager.getAllByHql(sql);
	}

	public RuleUserWh getRuleByWH(String wid, Integer userId) {
		List<RuleUserWh> rules = EntityManager.getAllByHql("from RuleUserWh where activeFlag = 1 and warehouseId = '" + wid + "' and userId = "
				+ userId);
		if (rules.size() > 0) {
			return rules.get(0);
		}
		return null;
	}

	public List<RuleUserWh> getRuleByUserid(Integer userId) {
		List<RuleUserWh> rules = EntityManager.getAllByHql("from RuleUserWh where activeFlag=1  and userId = " + userId);
		return rules;
	}
	
	public List getNoRuleWarehouse(HttpServletRequest request, int pagesize, String whereSql) throws Exception{
		String hql=" select new map(w.id as wid,w.warehousename as warehousename,w.makeorganid as makeorganid)  from Warehouse  w "+whereSql +" order by w.id";
		return PageQuery.hbmQuery(request, hql, pagesize);
	}
	
	public List getNoRuleUsers(HttpServletRequest request, int pagesize, String whereSql) throws Exception{
		String hql=" select new map(u.userid as uid,u.loginname as loginname,u.makeorganid as makeorganid,u.realname as realname,o.organname as organname)  from Users u,Organ o "+whereSql +" order by u.userid";
		return PageQuery.hbmQuery(request, hql, pagesize);
	}
	
	public List getWarehouseIdByUserid(Integer userId) {
		List rules = EntityManager.getAllByHql("select warehouseId from RuleUserWh where activeFlag=1  and userId = " + userId);
		return rules;
	}
	
	public void delRuleWhByOid(String oid,String uid,boolean isSingle) throws Exception{
		String sql = "";
		sql = "delete from rule_user_wh ruw where ruw.user_id=" + uid + " and warehouse_id in ( select id from warehouse where makeorganid in ( " + oid + " ) )";
		EntityManager.updateOrdelete(sql);
	}
	
	public void delRuleWhByWid(String oid,String uid,boolean isSingle) throws Exception{
		String sql = "";
		sql = "delete from rule_user_wh ruw where ruw.user_id=" + uid + " and warehouse_id in ( " + oid + ")";
		EntityManager.updateOrdelete(sql);
	}
	
	public void delRuleWhByUid(String uid,String wid,boolean isSingle) throws Exception{
		String sql = "";
		sql = "delete from rule_user_wh ruw where ruw.user_id in(" + uid + ") and warehouse_id ='"+wid+"'";
		EntityManager.updateOrdelete(sql);
	}
	
	public void addRuleWhByOid(String oid,String uid) throws Exception{
		String sql = "";
		sql = "insert into rule_user_wh (id,user_id,warehouse_id) select (select nvl(max(id),0) from rule_user_wh)+row_number() over ( order by id )," + uid + ",id from warehouse  where makeorganid='" + oid +"'" ;
		EntityManager.updateOrdelete(sql);
	}
	
	public void addRuleWhByOrganWhere(String whereSql,String uid) throws Exception{
		String sql = "";
		sql = "insert into rule_user_wh (id,user_id,warehouse_id) select (select nvl(max(id),0) from rule_user_wh)+row_number() over ( order by w.id )," + uid + ",w.id from warehouse w,organ o where w.makeorganid=o.id  " + whereSql 
		+ " and not exists (select u.id from rule_user_wh  u where w.id=u.warehouse_id and  u.user_id= " + uid +")";
		
		EntityManager.updateOrdelete(sql);
	}
	
	public void addRuleWhByWarehouseWhere(String whereSql,String uid) throws Exception{
		String sql = "";
		sql = "insert into rule_user_wh (id,user_id,warehouse_id) select (select nvl(max(id),0) from rule_user_wh)+row_number() over ( order by w.id )," + uid + ",w.id from warehouse w " + whereSql 
		+ " and not exists (select u.id from rule_user_wh  u where w.id=u.warehouse_id and  u.user_id= " + uid +")";
		EntityManager.updateOrdelete(sql);
	}
	
	public void addRuleWhByUsersWhere(String whereSql,String wid) throws Exception{
		String sql = "";
		sql = "insert into rule_user_wh (id,user_id,warehouse_id) select (select nvl(max(id),0) from rule_user_wh)+row_number() over ( order by u.userid ),u.userid,"+wid+" from Users u " + whereSql 
		+ " and not exists (select ruw.id from rule_user_wh ruw where u.userid=ruw.user_id and ruw.warehouse_id = " + wid +")";
		EntityManager.updateOrdelete(sql);
	}
	
}
