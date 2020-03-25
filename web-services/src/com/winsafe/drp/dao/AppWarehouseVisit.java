package com.winsafe.drp.dao;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.HibernateException;

import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.MakeCode;
import com.winsafe.hbm.util.StringFilters;
import com.winsafe.hbm.util.StringUtil;
import com.winsafe.hbm.util.pager2.PageQuery;

public class AppWarehouseVisit {
	public List getWarehouseVisitWID(String wid) throws Exception {
		String sql = " select wv.id,wv.wid,wv.userid from WarehouseVisit as wv where wv.wid='"
				+ wid + "'";
		return EntityManager.getAllByHql(sql);
	}

	// 根据用户找有权限的仓库
	public List getWarehouseVisitUID(String userid) throws Exception {
		String sql = " from WarehouseVisit as wv where wv.userid='" + userid
				+ "'";
		return EntityManager.getAllByHql(sql);
	}
	
	// 根据仓库id找有权限的用户
	public List getWarehouseVisitWID2(String wid) throws Exception {
		String sql = "  from WarehouseVisit as wv where wv.wid='" + wid + "'";
		return EntityManager.getAllByHql(sql);
	}

	public void addWarehouseVisit(Object s) throws Exception {

		EntityManager.save(s);

	}

	public void delWarehouseVisitByWID(String wid) throws Exception {

		String sql = "delete from Warehouse_Visit where wid='" + wid + "'";
		EntityManager.updateOrdelete(sql);

	}

	public void delWarehouseVisitByUID(String userid) throws Exception {

		String sql = "delete from Warehouse_Visit where userid='" + userid
				+ "'";
		EntityManager.updateOrdelete(sql);

	}

	public int findWarehouseByUseridWid(String wid, int uid) throws Exception {
		int w = 0;
		String sql = "select count(wv.id) from WarehouseVisit as wv where wv.wid='"
				+ wid + "' and userid=" + uid;
		w = EntityManager.getRecordCount(sql);
		return w;
	}
	/**
	 * 根据机构增加业务往来仓库
	 * @param oid
	 * @param uid
	 * @throws Exception
	 */
	public void addVWByOid(String oid,String uid) throws Exception{
		String sql = "";
		sql = "insert into warehouse_visit (id,userid,wid) select (select nvl(max(id),0) from warehouse_visit)+row_number() over ( order by id )," + uid + ",id from warehouse  where makeorganid='" + oid +"'" ;
		EntityManager.updateOrdelete(sql);
	}
	
	/**
	 * 根据上级机构增加业务往来仓库
	 * @param oid
	 * @param uid
	 * @throws Exception
	 */
	public void addVWByPOid(String parentOid,String uid) throws Exception{
		String sql = "";
		sql = "insert into warehouse_visit (id,userid,wid) select (select nvl(max(id),0) from warehouse_visit)+row_number() over ( order by id )," + uid + ",id from warehouse  where makeorganid in (select id from organ where parentid='" + parentOid +"')" ;
		EntityManager.updateOrdelete(sql);
	}
	
	/**
	 * 
	 * 根据机构条件增加业务往来仓库
	 * @param whereSql
	 * @param uid
	 * @throws Exception
	 */
	public void addRuleWhByOrganWhere(String whereSql,String uid) throws Exception{
		String sql = "";
		sql = "insert into warehouse_visit (id,userid,wid) select (select nvl(max(id),0) from warehouse_visit)+row_number() over ( order by w.id )," + uid + ",w.id from warehouse w,organ o where w.makeorganid=o.id  " + whereSql 
		+ " and not exists (select u.id from warehouse_visit  u where w.id=u.wid and  u.userid= " + uid +")";
		
		EntityManager.updateOrdelete(sql);
	}
	/**
	 * 
	 * 根据机构删除业务往来仓库
	 * @param oid
	 * @param uid
	 * @param isSingle
	 * @throws Exception
	 */
	public void delByOid(String oid,String uid,boolean isSingle) throws Exception{
		String sql = "";
		sql = "delete from warehouse_visit wv where wv.userid=" + uid + " and wv.wid in ( select id from warehouse where makeorganid in ( " + oid + " ) )";
		EntityManager.updateOrdelete(sql);
	}

	public List getWarehouseVisitByUserId(HttpServletRequest request,
			int pagesize, Integer userId, String KeyWord, String sysid,
			String parentOrganid, String organid) throws Exception {
		KeyWord = StringFilters.filterSql(KeyWord);
		sysid = StringFilters.filterSql(sysid);
		parentOrganid = StringFilters.filterSql(parentOrganid);
		organid = StringFilters.filterSql(organid);

		String bigRegionName = request.getParameter("bigRegionName");
		String officeName = request.getParameter("officeName");

		StringBuilder sql = new StringBuilder();
		sql
				.append("select o.id as oid,o.sysid,o.OrganName,w.ID as wid,w.WarehouseName,wv.id ,o.oecode,o.bigRegionName,o.officeName");
		sql.append(" from Organ o");
		sql.append(" inner join Warehouse w");
		sql.append(" on o.id=w.makeorganid");
		sql.append(" left join warehouse_visit wv");
		sql.append(" on wv.WID=w.id");
		sql.append(" and wv.UserID = " + userId);
		sql.append(" where 1=1");
		if (!StringUtil.isEmpty(sysid)) {
			sql.append(" and o.sysid like '" + sysid + "%'");
		}
		if (!StringUtil.isEmpty(bigRegionName)) {
			sql.append(" and o.bigRegionName like '" + bigRegionName.trim()
					+ "%'");
		}
		if (!StringUtil.isEmpty(officeName)) {
			sql.append(" and o.officeName like '" + officeName.trim() + "%'");
		}
		if (!StringUtil.isEmpty(parentOrganid)) {
			sql.append(" and o.ParentID = '" + parentOrganid + "'");
		}
		if (!StringUtil.isEmpty(organid)) {
			sql.append(" and o.id = '" + organid + "'");
		}
		if (!StringUtil.isEmpty(KeyWord)) {
			sql.append(" and (o.id like '" + KeyWord + "%'");
			sql.append(" or o.organname like '%" + KeyWord + "%'");
			sql.append(" or w.id like '" + KeyWord + "%'");
			sql.append(" or w.warehousename like '%" + KeyWord + "%'");
			sql.append(" or o.oecode like '%" + KeyWord + "%')");
		}
		sql.append(" order by wv.id desc,sysid,wid");
		return PageQuery.sqlQuery(request, sql.toString(), pagesize);
	}

	public List getWarehouseVisitByUserId(Integer userId, String KeyWord,
			String sysid, String parentOrganid, String organid)
			throws Exception {
		KeyWord = StringFilters.filterSql(KeyWord);
		sysid = StringFilters.filterSql(sysid);
		parentOrganid = StringFilters.filterSql(parentOrganid);
		organid = StringFilters.filterSql(organid);

		StringBuilder sql = new StringBuilder();
		sql
				.append("select o.id as oid,o.sysid,o.OrganName,w.ID as wid,w.WarehouseName,wv.id");
		sql.append(" from Organ as o");
		sql.append(" inner join Warehouse as w");
		sql.append(" on o.id=w.makeorganid");
		sql.append(" left join warehouse_visit as wv");
		sql.append(" on wv.WID=w.id");
		sql.append(" and wv.UserID = " + userId);
		sql.append(" where 1=1");
		if (!StringUtil.isEmpty(sysid)) {
			sql.append(" and o.sysid like '" + sysid + "%'");
		}
		if (!StringUtil.isEmpty(parentOrganid)) {
			sql.append(" and o.ParentID = '" + parentOrganid + "'");
		}
		if (!StringUtil.isEmpty(organid)) {
			sql.append(" and o.id = '" + organid + "'");
		}
		if (!StringUtil.isEmpty(KeyWord)) {
			sql.append(" and (o.id like '" + KeyWord + "%'");
			sql.append(" or o.organname like '%" + KeyWord + "%'");
			sql.append(" or w.id like '" + KeyWord + "%'");
			sql.append(" or w.warehousename like '%" + KeyWord + "%')");
		}
		sql.append(" order by wv.id desc,sysid,wid");
		return PageQuery.getSqlList(sql.toString());
	}

	public List getWarehouseVisitByUserId(Integer userId, String KeyWord,
			String bigRegionName, String officeName, String sysid,
			String parentOrganid, String organid) throws Exception {
		KeyWord = StringFilters.filterSql(KeyWord);
		sysid = StringFilters.filterSql(sysid);
		parentOrganid = StringFilters.filterSql(parentOrganid);
		organid = StringFilters.filterSql(organid);

		StringBuilder sql = new StringBuilder();
		sql
				.append("select o.id as oid,o.sysid,o.OrganName,w.ID as wid,w.WarehouseName,wv.id");
		sql.append(" from Organ o");
		sql.append(" inner join Warehouse w");
		sql.append(" on o.id=w.makeorganid");
		sql.append(" left join warehouse_visit wv");
		sql.append(" on wv.WID=w.id");
		sql.append(" and wv.UserID = " + userId);
		sql.append(" where 1=1");
		if (!StringUtil.isEmpty(sysid)) {
			sql.append(" and o.sysid like '" + sysid + "%'");
		}
		if (!StringUtil.isEmpty(parentOrganid)) {
			sql.append(" and o.ParentID = '" + parentOrganid + "'");
		}
		if (!StringUtil.isEmpty(organid)) {
			sql.append(" and o.id = '" + organid + "'");
		}
		if (!StringUtil.isEmpty(bigRegionName)) {
			sql.append(" and o.bigRegionName like '" + bigRegionName.trim()
					+ "%'");
		}
		if (!StringUtil.isEmpty(officeName)) {
			sql.append(" and o.officeName like '" + officeName.trim() + "%'");
		}
		if (!StringUtil.isEmpty(KeyWord)) {
			sql.append(" and (o.id like '" + KeyWord + "%'");
			sql.append(" or o.organname like '%" + KeyWord + "%'");
			sql.append(" or w.id like '" + KeyWord + "%'");
			sql.append(" or w.warehousename like '%" + KeyWord + "%')");
		}
		sql.append(" order by wv.id desc,sysid,wid");
		return PageQuery.getSqlList(sql.toString());
	}

	public void update(Integer userid, String[] wids, String[] values)
			throws Exception {
		String wid;
		String value;
		WarehouseVisit wv;
		for (int i = 0; i < wids.length; i++) {
			wid = wids[i];
			value = values[i];

			delWarehouseVisit(userid, wid);
			if (Boolean.parseBoolean(value)) {
				wv = new WarehouseVisit();
				wv.setId(Integer.valueOf(MakeCode.getExcIDByRandomTableName(
						"warehouse_visit", 0, "")));
				wv.setUserid(userid);
				wv.setWid(wid);

				addWarehouseVisit(wv);
			}
		}
	}

	public void update(Integer userid, boolean value, String KeyWord,
			String sysid, String parentOrganid, String organid)
			throws Exception {

		List list = getWarehouseVisitByUserId(userid, KeyWord, sysid,
				parentOrganid, organid);

		List<WarehouseVisit> wvlist = new ArrayList();
		Object[] objs;
		WarehouseVisit wv;
		WarehouseVisit temp;
		for (Object obj : list) {
			objs = (Object[]) obj;
			temp = new WarehouseVisit();
			temp.setOrganId((String) objs[0]);
			temp.setSysId((String) objs[1]);
			temp.setOrganName((String) objs[2]);
			temp.setWid((String) objs[3]);
			temp.setWarehouseName((String) objs[4]);
			temp.setId(((BigDecimal) objs[5]) == null ? 0
					: ((BigDecimal) objs[5]).intValue());

			delWarehouseVisit(userid, temp.getWid());
			if (value) {
				wv = new WarehouseVisit();
				wv.setId(Integer.valueOf(MakeCode.getExcIDByRandomTableName(
						"warehouse_visit", 0, "")));
				wv.setUserid(userid);
				wv.setWid(temp.getWid());

				addWarehouseVisit(wv);
			}
		}
	}

	public void update(Integer userid, boolean value, String KeyWord,
			String bigRegionName, String officeName, String sysid,
			String parentOrganid, String organid) throws Exception {

		List list = getWarehouseVisitByUserId(userid, KeyWord, bigRegionName,
				officeName, sysid, parentOrganid, organid);

		List<WarehouseVisit> wvlist = new ArrayList();
		Object[] objs;
		WarehouseVisit wv;
		WarehouseVisit temp;
		for (Object obj : list) {
			objs = (Object[]) obj;
			temp = new WarehouseVisit();
			temp.setOrganId((String) objs[0]);
			temp.setSysId((String) objs[1]);
			temp.setOrganName((String) objs[2]);
			temp.setWid((String) objs[3]);
			temp.setWarehouseName((String) objs[4]);
			temp.setId(((BigDecimal) objs[5]) == null ? null
					: ((BigDecimal) objs[5]).intValue());

			delWarehouseVisit(userid, temp.getWid());
			if (value) {
				wv = new WarehouseVisit();
				wv.setId(Integer.valueOf(MakeCode.getExcIDByRandomTableName(
						"warehouse_visit", 0, "")));
				wv.setUserid(userid);
				wv.setWid(temp.getWid());

				addWarehouseVisit(wv);
			}
		}
	}

	public void delWarehouseVisit(Integer userid, String wid) throws Exception {

		String sql = "delete from Warehouse_Visit where wid='" + wid
				+ "' and UserID=" + userid;
		EntityManager.updateOrdelete(sql);

	}

	public List queryUserIncludeOrgan(String organid) {
		String sql = "select wv.UserID from Warehouse_Visit wv,Warehouse w where wv.wid=w.id and w.makeorganid="
				+ organid + " group by wv.UserID";
		return EntityManager.getAllByHql(sql);
	}

	public WarehouseVisit getWarehouseVisit(Integer userid, String wid)
			throws Exception {

		String sql = "from WarehouseVisit where wid='" + wid + "' and userid="
				+ userid;
		return (WarehouseVisit) EntityManager.find(sql);

	}

	// 根据用户找有权限的仓库
	public List<WarehouseVisit> getWarehouseVisitByUID(Integer userid) {
		String sql = " from WarehouseVisit as wv where wv.userid="+ userid;
		return EntityManager.getAllByHql(sql);
	}
	
	/**
	 * 获取WarehouseVisit表中最大ID+1
	 * 
	 * @author jason.huang
	 * @return int
	 */
	public int getMaxWarehouseVisitId() {
		String sql = "SELECT MAX(id) + 1 FROM WarehouseVisit";
		return EntityManager.getRecordCount(sql);
	}
	
	/**
	 * 根据机构增加业务往来仓库
	 * @param oid
	 * @param uid
	 * @throws Exception
	 */
	public void addWareHouseVisit(int id,String oid,int uid) throws Exception{
		String sql = "";
		sql = "insert into warehouse_visit (id,userid,wid) values('"+id+"','"+uid+"','"+oid+"') ";
		EntityManager.updateOrdelete(sql);
	}
	
	/**
	 * 机构仓库和用户管辖关联
	 * @param oid
	 * @param uid
	 * @throws Exception
	 */
	public void addRuleUserWh(int uid,String whid) throws Exception{
		String sql = "";
		sql = "insert into rule_user_wh (user_id,warehouse_id,id,activeflag) values('"+uid+"','"+whid+"',(SELECT MAX(id) + 1 FROM rule_user_wh),'1') ";
		EntityManager.updateOrdelete(sql);
	}

	public void addWarehousVisit(String oid, String parentid) throws Exception {
		String sql = "INSERT INTO WAREHOUSE_VISIT (id,wid,userid) " +
				"select (select nvl(max(id),0) from WAREHOUSE_VISIT)+row_number() over ( order by uv.id ),w.id,uv.userid FROM USER_VISIT uv, WAREHOUSE w " +
				"where visitorgan = '"+parentid+"' and w.makeorganid = '"+oid+"' and not exists (select id from WAREHOUSE_VISIT where wid = w.id and userid=uv.userid)";
		EntityManager.executeUpdate(sql);
	}

	public void addWarehousVisitForTR(String organId, String userId) throws Exception {
		String sql = "INSERT INTO WAREHOUSE_VISIT (id,wid,userid) " +
				"select (select nvl(max(id),0) from WAREHOUSE_VISIT)+row_number() over ( order by str.id ),w.id,"+userId+" FROM S_TRANSFER_RELATION str " +
				"join WAREHOUSE w on w.makeorganid = str.organizationid " +
				"where str.opporganid = '"+organId+"' and not exists (select id from WAREHOUSE_VISIT where wid = w.id and userid="+userId+")";
		EntityManager.executeUpdate(sql);
	}
	
	public void delWarehousVisitForTR(String organId, int userId) throws Exception {
		String sql = "DELETE from WAREHOUSE_VISIT where userid = "+userId+" and wid in (" +
				"select id from WAREHOUSE where makeorganid in (" +
				"select organizationid from S_TRANSFER_RELATION where opporganid = '"+organId+"'))";
		EntityManager.executeUpdate(sql);
	}

	public void delWarehousVisit(String oid, String parentid) throws Exception {
		String sql = "delete from WAREHOUSE_VISIT " +
				"where wid in ( " +
				"select id from WAREHOUSE where makeorganid = '"+oid+"' " +
				") and USERID in ( " +
				"select USERID FROM USER_VISIT where visitorgan = '"+parentid+"')";
		EntityManager.executeUpdate(sql);
	}
	
	
	
	
}
