package com.winsafe.drp.dao;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.pager2.PageQuery;

public class AppReceivableObject {

	public List getReceivableObject(HttpServletRequest request, int pagesize,
			String pWhereClause) throws Exception {

		String sql = " from ReceivableObject as ro " + pWhereClause
				+ " order by ro.makedate desc";
		return PageQuery.hbmQuery(request, sql, pagesize);

	}

	public List getReceivableObject(String pWhereClause) throws Exception {

		String sql = " from ReceivableObject as ro " + pWhereClause
				+ " order by ro.makedate desc";
		return EntityManager.getAllByHql(sql);

	}

	public void addReceivableObject(Object ro) throws Exception {
		EntityManager.save(ro);
	}

	public void setRecievableAwake(String id, String promisedate)
			throws Exception {

		String sql = "update receivable_object set promisedate ='"
				+ promisedate + "' where id='" + id + "'";
		EntityManager.updateOrdelete(sql);

	}

	public void addReceivableObjectIsNoExist(ReceivableObject ro)
			throws Exception {

		String sql = "insert into receivable_object(oid,objectsort,payer,makeorganid,makedeptid,makeid,makedate,keyscontent) select '"
				+ ro.getOid()
				+ "',"
				+ ro.getObjectsort()
				+ ",'"
				+ ro.getPayer()
				+ "','"
				+ ro.getMakeorganid()
				+ "',"
				+ ro.getMakedeptid()
				+ ","
				+ ro.getMakeid()
				+ ",'"
				+ DateUtil.getCurrentDateString()
				+ "','"
				+ ro.getKeyscontent()
				+ "' where not exists (select oid from receivable_object where objectsort="
				+ ro.getObjectsort()
				+ " and oid='"
				+ ro.getOid()
				+ "' and makeorganid='" + ro.getMakeorganid() + "')";
		EntityManager.updateOrdelete(sql);

	}

	public ReceivableObject getReceivableObjectByOSPID(Integer os, String pid)
			throws Exception {
		ReceivableObject ro = null;
		String sql = " from ReceivableObject as ro where ro.objectsort=" + os
				+ " and ro.id='" + pid + "'";
		ro = (ReceivableObject) EntityManager.find(sql);
		return ro;
	}

	public void delReceivableObject(String oid, String orgid) throws Exception {

		String sql = "delete from receivable_object where oid='" + oid
				+ "' and makeorganid='" + orgid + "'";
		EntityManager.updateOrdelete(sql);

	}

	public ReceivableObject getReceivableObjectByIDOrgID(String oid,
			String orgid) throws Exception {
		ReceivableObject w = null;
		String sql = "from ReceivableObject where oid='" + oid
				+ "' and makeorganid='" + orgid + "'";
		w = (ReceivableObject) EntityManager.find(sql);
		return w;
	}

	public void updReceivableObject(ReceivableObject r) throws Exception {

		String sql = "update receivable_object set objectsort="
				+ r.getObjectsort() + ",payer='" + r.getPayer()
				+ "',keyscontent='" + r.getKeyscontent() + "' where id='"
				+ r.getId() + "'";
		EntityManager.updateOrdelete(sql);

	}

	public void addReceivableSum(String roid, Double receivablesum)
			throws Exception {

		String sql = "update receivable_object set totalReceivablesum=totalReceivablesum + "
				+ receivablesum
				+ ",WaitReceivableSum=WaitReceivableSum+"
				+ receivablesum + " where id='" + roid + "'";
		EntityManager.updateOrdelete(sql);
		// System.out.println("sql---22="+sql);

	}

	public void removeReceivableSum(String roid, Double receivablesum)
			throws Exception {

		String sql = "update receivable_object set totalReceivablesum=totalReceivablesum - "
				+ receivablesum
				+ ",WaitReceivableSum=WaitReceivableSum-"
				+ receivablesum + " where id='" + roid + "'";
		EntityManager.updateOrdelete(sql);
		// System.out.println("sql---11="+sql);

	}

	public void addAlreadyReceivableSum(String roid, Double incomesum)
			throws Exception {

		String sql = "update receivable_object set alreadyreceivablesum=alreadyreceivablesum + "
				+ incomesum
				+ ",WaitReceivableSum=WaitReceivableSum-"
				+ incomesum + " where id='" + roid + "'";
		EntityManager.updateOrdelete(sql);

	}

	public void removeAlreadyReceivableSum(String roid, Double incomesum)
			throws Exception {

		String sql = "update receivable_object set alreadyreceivablesum=alreadyreceivablesum - "
				+ incomesum
				+ ",WaitReceivableSum=WaitReceivableSum+"
				+ incomesum + " where id='" + roid + "'";
		EntityManager.updateOrdelete(sql);
	}

	public List getViewRevenueWaste(HttpServletRequest request, int pagesize,
			String pWhereClause) throws Exception {
		String sql = " from ViewRevenueWaste as r " + pWhereClause
				+ " order by r.makedate desc";
		return PageQuery.hbmQuery(request, sql, pagesize);
	}

	public List getViewRevenueWaste(String pWhereClause) throws Exception {
		String sql = " from ViewRevenueWaste as r " + pWhereClause
				+ " order by r.makedate desc";
		return EntityManager.getAllByHql(sql);
	}

	public List getViewRevenueWasteToExc(String pWhereClause) throws Exception {
		String sql = "select r.id,r.roid,r.makeorganid,r.makedate,r.paymentmode,r.memo,r.receivablesum,r.incomesum from view_revenue_waste as r "
				+ pWhereClause + " order by r.makedate desc";
		return EntityManager.jdbcquery(sql);
	}

	public List getTotalSubum(String whereSql) throws Exception {
		String sql = "select sum(r.receivablesum), sum(r.incomesum) from ViewRevenueWaste as r "
				+ whereSql;
		return EntityManager.getAllByHql(sql);
	}

	public void updKeysContent(String cid, String keys) throws Exception {
		String sql = "update receivable_object set keyscontent='" + keys
				+ "' where oid='" + cid + "' ";
		EntityManager.updateOrdelete(sql);
	}

	public List getOutday() throws Exception {
		String sql = "select o.oid,o.objectsort, DATEDIFF(dd, r.awakedate, getdate()) as outday "
				+ "from Receivable as r, Receivable_Object as o "
				+ "where r.roid=o.oid and o.objectsort in (0,1)  "
				+ "and  DATEDIFF(dd, r.awakedate, getdate()) > 1 and r.isclose=0";
		return EntityManager.jdbcquery(sql);
	}

	public int getCountOutday(String makeorganid) throws Exception {
		String sql = "select count(*) as countnum "
				+ "from Receivable as r "
				+ "where r.makeorganid='"
				+ makeorganid
				+ "' "
				+ "and  DATEDIFF(dd, r.awakedate, getdate()) > 1 and r.isclose=0";
		int count = 0;
		try {
			List list = EntityManager.jdbcquery(sql);
			if (list != null && !list.isEmpty()) {
				Map o = (Map) list.get(0);
				if (o != null) {
					count = Integer.valueOf(o.get("countnum").toString());
				}
			}
		} catch (Exception e) {
			count = 0;
		}
		return count;
	}

}
