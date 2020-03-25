package com.winsafe.drp.dao;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.pager2.PageQuery;

public class AppPayableObject {
	public List getPayableObject(HttpServletRequest request, int pagesize,
			String pWhereClause) throws Exception {

		String sql = "from PayableObject as po " + pWhereClause
				+ " order by po.makedate desc";
		return PageQuery.hbmQuery(request, sql, pagesize);
	}

	public List getPayableObject(String pWhereClause) throws Exception {

		String sql = "from PayableObject as po " + pWhereClause
				+ " order by po.makedate desc";
		return EntityManager.getAllByHql(sql);
	}

	public void addPayableObject(Object po) throws Exception {
		EntityManager.save(po);
	}

	public void delPayableObject(String id) throws Exception {
		String sql = "delete from payable_object where id='" + id + "'";
		EntityManager.updateOrdelete(sql);
	}

	public void noExistsToAdd(PayableObject po) throws Exception {

		String sql = "insert into payable_object(oid,objectsort,payee,makeorganid,makedeptid,makeid,makedate,keyscontent) select '"
				+ po.getOid()
				+ "',"
				+ po.getObjectsort()
				+ ",'"
				+ po.getPayee()
				+ "','"
				+ po.getMakeorganid()
				+ "',"
				+ po.getMakedeptid()
				+ ","
				+ po.getMakeid()
				+ ",'"
				+ DateUtil.getCurrentDateString()
				+ "','"
				+ po.getKeyscontent()
				+ "' where not exists (select oid from payable_object where objectsort="
				+ po.getObjectsort()
				+ " and oid='"
				+ po.getOid()
				+ "' and makeorganid='" + po.getMakeorganid() + "')";
		// System.out.println("----"+sql);
		EntityManager.updateOrdelete(sql);

	}

	public Double getShouldPayment(Integer objectsort, String payeeid)
			throws Exception {
		Double sp = Double.valueOf(0.00);
		String sql = "select (po.totalpayablesum-po.alreadypayablesum) as shouldpayment from PayableObject as po where po.objectsort="
				+ objectsort + " and po.payeeid='" + payeeid + "'";
		sp = EntityManager.getdoubleSum(sql);
		return sp;
	}

	public PayableObject getPayableObjectByProvideID(String pid)
			throws Exception {
		PayableObject po = new PayableObject();
		String sql = "from PayableObject where objectsort=2 and payeeid='"
				+ pid + "'";
		po = (PayableObject) EntityManager.find(sql);
		return po;
	}

	public PayableObject getPayableObjectByOSPID(Integer os, String pid,
			String orgid) throws Exception {
		PayableObject ro = null;
		String sql = " from PayableObject as po where po.objectsort=" + os
				+ " and po.oid='" + pid + "' and makeorganid='" + orgid + "' ";
		ro = (PayableObject) EntityManager.find(sql);
		return ro;
	}

	public PayableObject getPayableObjectByOIDOrgID(String oid, String orgid)
			throws Exception {
		PayableObject w = null;
		String sql = " from PayableObject where oid='" + oid
				+ "' and makeorganid='" + orgid + "'";
		w = (PayableObject) EntityManager.find(sql);
		return w;
	}

	public void updPayableObject(PayableObject r) throws Exception {

		String sql = "update Payable_Object set objectsort="
				+ r.getObjectsort() + ",Payee='" + r.getPayee()
				+ "',keyscontent='" + r.getKeyscontent() + "' where id="
				+ r.getId();
		// System.out.println("------"+sql);
		EntityManager.updateOrdelete(sql);

	}

	public void addPayableSum(String poid, Double payablesum) throws Exception {

		String sql = "update Payable_Object set totalpayablesum=totalpayablesum + "
				+ payablesum
				+ ",WaitPayableSum=WaitPayableSum+"
				+ payablesum
				+ " where id='" + poid + "'";
		EntityManager.updateOrdelete(sql);

	}

	public void removePayableSum(String poid, Double payablesum)
			throws Exception {

		String sql = "update Payable_Object set totalpayablesum=totalpayablesum - "
				+ payablesum
				+ ",WaitPayableSum=WaitPayableSum-"
				+ payablesum
				+ " where id='" + poid + "'";
		EntityManager.updateOrdelete(sql);

	}

	public void paymentLog(Double paymentsum, String poid) throws Exception {

		String sql = "update Payable_Object set AlreadyPayableSum=AlreadyPayableSum +"
				+ paymentsum
				+ ",WaitPayableSum=WaitPayableSum-"
				+ paymentsum
				+ " where ID='" + poid + "' ";
		EntityManager.updateOrdelete(sql);
	}

	public void cancelPaymentLog(Double paymentsum, String poid)
			throws Exception {
		String sql = "update Payable_Object set AlreadyPayableSum=AlreadyPayableSum -"
				+ paymentsum
				+ ",WaitPayableSum=WaitPayableSum+"
				+ paymentsum
				+ " where ID='" + poid + "'";
		EntityManager.updateOrdelete(sql);

	}

	public List getViewPayoutWaste(HttpServletRequest request, int pagesize,
			String pWhereClause) throws Exception {
		String sql = " from ViewPayoutWaste p " + pWhereClause
				+ " order by p.makedate desc";
		return PageQuery.hbmQuery(request, sql, pagesize);
	}

	public List getViewPayoutWasteToExc(String pWhereClause) throws Exception {
		String sql = "select p.id,p.poid,p.makeorganid,p.makedate,p.paymode,p.memo,p.payablesum,p.paysum from view_payout_waste as p "
				+ pWhereClause + " order by p.makedate desc";
		return EntityManager.jdbcquery(sql);
	}

	public List getViewPayoutWaste(String pWhereClause) throws Exception {
		List ls = null;
		String sql = " from ViewPayoutWaste as p " + pWhereClause
				+ " order by p.makedate desc";
		ls = EntityManager.getAllByHql(sql);
		return ls;
	}

	public List getTotalSubum(String whereSql) throws Exception {
		String sql = "select sum(p.payablesum), sum(p.paysum) from ViewPayoutWaste as p "
				+ whereSql;
		return EntityManager.getAllByHql(sql);
	}

}
