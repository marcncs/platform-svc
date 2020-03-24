package com.winsafe.drp.dao;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.pager.SimplePageInfo;
import com.winsafe.hbm.util.pager2.PageQuery;

public class AppSaleOrder {

	public List getSaleOrderAll(HttpServletRequest request, int pagesize,
			String pWhereClause) throws Exception {
		List pls = null;

		String sql = " from SaleOrder as so " + pWhereClause
				+ " order by so.makedate desc";
		return PageQuery.hbmQuery(request, sql, pagesize);
	}

	public List searchSaleOrder(int pagesize, String pWhereClause,
			SimplePageInfo pPgInfo) throws Exception {
		int targetPage = pPgInfo.getCurrentPageNo();
		String sql = "  from SaleOrder as so " + pWhereClause
				+ " order by so.makedate desc";
		return EntityManager.getAllByHql(sql, targetPage, pagesize);
	}

	public List searchSaleOrder(HttpServletRequest request, int pagesize,
			String pWhereClause) throws Exception {
		String hql = " from SaleOrder as so " + pWhereClause
				+ " order by so.makedate desc";
		return PageQuery.hbmQuery(request, hql, pagesize);
	}

	
	public List getHistoryChen(String cid, String productid) throws Exception {
		List pls = null;
		// String sql =
		// "select sod.unitprice,so.makedate from SaleOrder as so,SaleOrderDetail as sod where so.cid='"+cid+"' and sod.productid='"+productid+"' and so.id=sod.soid order by so.makedate desc";
		String sql = "select sbd.unitprice,sb.makedate from ShipmentBill as sb,ShipmentBillDetail as sbd where sb.cid='"
				+ cid
				+ "' and sbd.productid='"
				+ productid
				+ "' and sb.id=sbd.sbid order by sb.makedate desc";
		pls = EntityManager.getAllByHql(sql, 1, 5);
		return pls;
	}


	public List getSaleOrderTotal(int pagesize, String pWhereClause,
			SimplePageInfo pPgInfo) throws Exception {
		List pls = null;
		int targetPage = pPgInfo.getCurrentPageNo();
		String sql = "select so.id,so.cid,so.cname,so.makedate from SaleOrder as so "
				+ pWhereClause + " order by so.makedate desc";
		pls = EntityManager.getAllByHql(sql, targetPage, pagesize);
		return pls;
	}

	public double getSaleOrderTotal(String pWhereClause) throws Exception {
		String sql = "select sum(so.totalsum) from SaleOrder as so "
				+ pWhereClause;
		System.out.println("---------" + sql);
		return EntityManager.getdoubleSum(sql);
	}

	public void addSaleOrder(Object saleorder) throws Exception {

		EntityManager.save(saleorder);

	}

	public SaleOrder getSaleOrderByID(String id) throws Exception {
		SaleOrder sl = null;
		String sql = " from SaleOrder as so where so.id='" + id + "'";
		sl = (SaleOrder) EntityManager.find(sql);
		return sl;
	}

	public SaleOrder getLastSaleOrderByMobile(String mobile) throws Exception {
		SaleOrder sl = null;
		String sql = " from SaleOrder as so where so.cmobile='" + mobile
				+ "' order by so.makedate desc";
		List list = EntityManager.getAllByHql(sql);
		if (list != null && !list.isEmpty()) {
			sl = (SaleOrder) list.get(0);
		}
		return sl;
	}

	public void delSaleOrder(String soid) throws Exception {

		String sql = "delete from sale_order where id='" + soid + "'";
		EntityManager.updateOrdelete(sql);

	}

	public void updSaleOrder(SaleOrder so) throws Exception {

		EntityManager.update(so);

	}

	// public String updSaleOrderByID(SaleOrder so, String consignmentdate)
	// throws Exception {
	//		
	// String sql = "update sale_order set CustomerBillID='"
	// + so.getCustomerbillid() + "',CID='" + so.getCid() + "',CName='"
	// + so.getCname() + "',SaleType=" + so.getSaletype()
	// + ",SaleDept=" + so.getSaledept() + ",ConsignmentDate='"
	// + consignmentdate + "',FundSrc=" + so.getFundsrc()
	// + ",FundAttach=" + so.getFundattach() + ",TotalSum="
	// + so.getTotalsum() + ",TransportMode=" + so.getTransportmode()
	// + ",TransportAddr='" + so.getTransportaddr() + "',ReceiveMan='"
	// + so.getReceiveman() + "',Tel='" + so.getTel() + "',UpdateID=" +
	// so.getUpdateid()
	// + ",LastUpdate='" + DateUtil.getCurrentDateTime()
	// + "',Remark='" + so.getRemark() + "' where ID='" + so.getId()
	// + "'";
	// // System.out.println("--sql=="+sql);
	// EntityManager.updateOrdelete(sql);
	//		
	// }

	
	public void updTakeStatus(String id, int takestatus) throws Exception {
		String sql = "update sale_order set takestatus=" + takestatus
				+ " where id='" + id + "'";
		EntityManager.updateOrdelete(sql);
	}

	
	public List waitApproveSaleOrder(int pagesize, String pWhereClause,
			SimplePageInfo pPgInfo) throws Exception {
		List wsl = null;
		int targetPage = pPgInfo.getCurrentPageNo();
		String sql = "select so.id,so.cname,so.makeid,so.totalsum,so.makedate,soa.actid,soa.approve,so.consignmentdate from SaleOrder as so,SaleOrderApprove as soa "
				+ pWhereClause + " order by so.makedate desc";
		wsl = EntityManager.getAllByHql(sql, targetPage, pagesize);
		return wsl;
	}

	

	public void updIsApprove(String id, int isapprove) throws Exception {
		String sql = "update sale_order set approvestatus=" + isapprove
				+ ",approvedate='" + DateUtil.getCurrentDateTime()
				+ "' where id ='" + id + "'";
		EntityManager.updateOrdelete(sql);
	}

	

	public double getTotalIncome(String pWhereClause) throws Exception {
		double totalincome = Double.parseDouble("0.00");
		String sql = "select sum(sl.totalsum) from SaleOrder as sl "
				+ pWhereClause + " sl.approvestatus =1";
		totalincome = EntityManager.getdoubleSum(sql);
		return totalincome;
	}

	public List getShouldIncome(int pagesize, String pWhereClause,
			SimplePageInfo pPgInfo) throws Exception {
		List pb = null;
		int targetPage = pPgInfo.getCurrentPageNo();
		String sql = "select sl.id,sl.cid,sl.totalsum,sl.consignmentdate,sl.orderstatus,sl.isreceiveall from SaleOrder as sl "
				+ pWhereClause + " order by sl.createdate desc ";
		pb = EntityManager.getAllByHql(sql, targetPage, pagesize);
		return pb;
	}

	
	public void updIsTransferShipment(Integer slid) throws Exception {

		String sql = "update sale_order set istransfershipment=1 where id="
				+ slid;
		EntityManager.updateOrdelete(sql);

	}

	
	public int waitShipmentCount() throws Exception {
		int count = 0;
		String sql = "select count(*) from SaleOrder as sl where sl.approvestatus=2 and sl.istransfershipment=0 and sl.isshipment=1";
		count = EntityManager.getRecordCount(sql);
		return count;
	}

	
	public int waitInputSaleShipmentCount() throws Exception {
		int count = 0;
		String sql = "select count(*) from SaleOrder as so where so.isaudit=1 and so.isendcase=0 and so.salesort=1";
		count = EntityManager.getRecordCount(sql);
		return count;
	}

	
	public void updIsShipment(String soid) throws Exception {

		String sql = "update sale_order set isshipment=1 where id='" + soid
				+ "'";
		// System.out.println("+++++++++++++"+sql);
		EntityManager.updateOrdelete(sql);

	}

	
	public void updIsTransferShipment(String soid) throws Exception {

		String sql = "update sale_order set istransfershipment=1 where id='"
				+ soid + "'";
		// System.out.println("+++++++++++++"+sql);
		EntityManager.updateOrdelete(sql);

	}

	
	public void updIsCompleteControl(String soid) throws Exception {

		String sql = "update sale_order set IsCompleteControl=1 where id='"
				+ soid + "'";
		// System.out.println("+++++++++++++"+sql);
		EntityManager.updateOrdelete(sql);

	}

	
	public void updOrderStatus(String soid, int orderstatus) throws Exception {

		String sql = "update sale_order set orderstatus=" + orderstatus
				+ " where id='" + soid + "'";
		EntityManager.updateOrdelete(sql);

	}

	
	public void updIsAudit(String soid, Integer userid, Integer audit)
			throws Exception {

		String sql = "update sale_order set isaudit=" + audit + ",auditid="
				+ userid + ",auditdate='" + DateUtil.getCurrentDateTime()
				+ "' where id='" + soid + "'";
		EntityManager.updateOrdelete(sql);

	}

	
	public void updIsEndcase(String soid, Integer userid, Integer endcase)
			throws Exception {

		String sql = "update sale_order set isendcase=" + endcase
				+ ",endcaseid=" + userid + ",endcasedate='"
				+ DateUtil.getCurrentDateTime() + "' where id='" + soid + "'";
		EntityManager.updateOrdelete(sql);

	}

	
	public void updIsAudittwo(String soid, Integer userid, Integer audit)
			throws Exception {

		String sql = "update sale_order set isaudittwo=" + audit
				+ ",audittwoid=" + userid + ",audittwodate='"
				+ DateUtil.getCurrentDateTime() + "' where id='" + soid + "'";
		EntityManager.updateOrdelete(sql);

	}

	
	public List getCustomerSaleOrderOutlay(String whereSql_s, String whereSql_o)
			throws Exception {
		List ls = new ArrayList();
		String sql = "select so.cid,so.makedate,sum(sod.subsum),0 from sale_order as so,sale_order_detail as sod "
				+ whereSql_s
				+ "  group by so.cid,so.makedate union select o.customerid,o.makedate,0,o.totaloutlay from outlay as o "
				+ whereSql_o
				+ " group by o.customerid,o.makedate,o.totaloutlay order by makedate desc";
		System.out.println("---" + sql);
		ResultSet rs = EntityManager.query(sql);
		if (rs.getRow() > 0) {
			do {
				CustomerSaleOrderOutlay csoo = new CustomerSaleOrderOutlay();
				csoo.setCid(rs.getString(1));
				csoo.setMakedate(rs.getString(2));
				csoo.setSubsum(rs.getDouble(3));
				csoo.setTotaloutlay(rs.getDouble(4));
				ls.add(csoo);
			} while (rs.next());
		}
		rs.close();
		return ls;
	}


	public void BlankoutSaleOrder(String id, Integer userid) throws Exception {
		String sql = "update sale_order set isblankout=1,blankoutid=" + userid
				+ ",blankoutdate='" + DateUtil.getCurrentDateString()
				+ "' where id='" + id + "'";
		EntityManager.updateOrdelete(sql);
	}


	public void blankout(String id, Integer userid, Integer blankout,
			String blankoutreason) throws Exception {

		String sql = "update sale_order set isblankout=" + blankout
				+ ",blankoutid=" + userid + ",blankoutdate='"
				+ DateUtil.getCurrentDateTime() + "',blankoutreason='"
				+ blankoutreason + "' where id='" + id + "'";
		EntityManager.updateOrdelete(sql);

	}

	public List getSaleOrderCustomer(String pWhereClause) throws Exception {
		List pls = null;
		String sql = " from SaleOrder as so  " + pWhereClause
				+ " order by so.id desc";
		pls = EntityManager.getAllByHql(sql);
		return pls;
	}

	public int getSaleOrderEndcase(String cid) throws Exception {
		int count = 0;
		String sql = "select count(*) from SaleOrder as so where so.cid ='"
				+ cid + "' and so.isendcase=1";
		count = EntityManager.getRecordCount(sql);
		return count;
	}

	
	public void updIsMakeTicket(String soid, int ismake) throws Exception {
		String sql = "update sale_order set ismaketicket=" + ismake
				+ " where id='" + soid + "'";
		EntityManager.updateOrdelete(sql);
	}

	
	public List getSaleTotal(HttpServletRequest request, int pagesize,
			String pWhereClause) throws Exception {
		String sql = "select makeorganid, productid as productid,productname as productname,specmode as specmode,unitid as unitid,sum(quantity) as quantity ,sum(subsum) as subsum "
				+ " from sale_order_detail as sod ,sale_order as so "
				+ pWhereClause
				+ " group by makeorganid, productid,productname,specmode,unitid ";
		return PageQuery.jdbcSqlserverQuery(request, "productid", sql, pagesize);
	}

	public List getSaleTotal(String pWhereClause) throws Exception {
		String sql = "select makeorganid,productid as productid,productname as productname,specmode as specmode,unitid as unitid,sum(quantity) as quantity ,sum(subsum) as subsum "
			+ " from Sale_Order_Detail as sod ,Sale_Order as so "
			+ pWhereClause
			+ " group by makeorganid, productid,productname,specmode,unitid ";

		return EntityManager.jdbcquery(sql);
	}

	
	public List getProductOrder(HttpServletRequest request, int pagesize,
			String pWhereClause, String ordertype) throws Exception {
		String ordersql = "order by sum(d.quantity) desc ";
		if (ordertype.equals("s")) {
			ordersql = "order by sum(d.subsum) desc ";
		}

		String sql = "select d.productid, d.productname, d.specmode, d.unitid, sum(d.quantity) as quantity, sum(d.subsum) as subsum "
				+ " from view_sale_product_total d "
				+ pWhereClause
				+ " group by d.productid,d.productname, d.specmode, d.unitid "
				+ ordersql;
		Object obj[] = DbUtil.setGroupByPager(request, sql, pagesize,
				"SaleOrderReport");
		SimplePageInfo tmpPgInfo = (SimplePageInfo) obj[0]; 
		int targetPage = tmpPgInfo.getCurrentPageNo();

		String hql = (String) obj[1];
		hql = hql.replace("view_sale_product_total", "ViewSaleProductTotal");
		return EntityManager.getAllByHql(hql, targetPage, pagesize);
	}

	public List getProductOrder(String pWhereClause, String ordertype)
			throws Exception {
		String ordersql = "order by sum(d.quantity) desc ";
		if (ordertype.equals("s")) {
			ordersql = "order by sum(d.subsum) desc ";
		}

		String hql = "select d.productid, d.productname, d.specmode, d.unitid, sum(d.quantity) as quantity, sum(d.subsum) as subsum "
				+ " from ViewSaleProductTotal d "
				+ pWhereClause
				+ " group by d.productid,d.productname, d.specmode, d.unitid "
				+ ordersql;
		return EntityManager.getAllByHql(hql);
	}

	public List getTotalSum(String pWhereClause) throws Exception {
		String sql = "select sum(sod.quantity) as allqt, sum(sod.subsum) as allsubsum, sum(sod.cost) as allcost, sum(sod.subsum-sod.cost) as allgain"
				+ " from sale_order_detail as sod ,sale_order as so "
				+ pWhereClause;
		return EntityManager.jdbcquery(sql);
	}

	public List getSaleCustomerTotal(String pWhereClause) throws Exception {
		String hql = "select cid as id,cname as name,cmobile as mobile,sum(totalsum) as totalsum from Sale_Order "
				+ pWhereClause + " group by cmobile,cid,cname";
		return EntityManager.jdbcquery(hql);
	}

	public List getSaleCustomerTotal(HttpServletRequest request, int pagesize,
			String pWhereClause) throws Exception {
		String hql = "select cid as id,cname as name,cmobile as mobile,sum(totalsum) as totalsum from Sale_Order "
				+ pWhereClause + " group by cmobile,cid,cname";
		return PageQuery.jdbcSqlserverQuery(request, hql, pagesize);
	}

	public List getSaleBillTotal(String pWhereClause) throws Exception {
		String hql = "select id,sosort,makedate,cid,cname,cmobile,makeorganid,equiporganid,totalsum from Sale_Order so "+pWhereClause;
		return EntityManager.jdbcquery(hql);
	}

	public List getSaleBillTotal(HttpServletRequest request, int pagesize, String pWhereClause) throws Exception {
		String hql = "select id,sosort,makedate,cid,cname,cmobile,makeorganid,equiporganid,totalsum from Sale_Order so "
				+ pWhereClause;

		return PageQuery.jdbcSqlserverQuery(request, hql, pagesize);
	}

	public double getSaleCustomerTotalSum(String pWhereClause) throws Exception {
		String sql = "select sum(so.totalsum) from SaleOrder so "
				+ pWhereClause;
		return EntityManager.getdoubleSum(sql);
	}

	public double getSaleOrderTotalSum(String yearmon, String conditon) {
		String[] date = yearmon.split("-");
		String sql = "select sum(sod.subsum) as subsum from SaleOrder so , " +
				"SaleOrderDetail sod where so.id=sod.soid and year(makedate) ='"+date[0]+"'  and month(makedate)='"+date[1]+"' " +conditon;
		return EntityManager.getdoubleSum(sql);
	}

	public double getSaleOrderCostSum(String yearmon, String conditon) {
		String[] date = yearmon.split("-");
		String sql = "select sum(sod.cost) as cost from SaleOrder so , " +
				"SaleOrderDetail sod where so.id=sod.soid and year(makedate) ='"+date[0]+"'  and month(makedate)='"+date[1]+"' " +conditon;
		return EntityManager.getdoubleSum(sql);
	}

	public double getSaleOrderProfitSum(String yearmon, String conditon) {
		String[] date = yearmon.split("-");
		String sql = "select sum(sod.subsum-sod.cost) as profitsum from SaleOrder so , " +
				"SaleOrderDetail sod where so.id=sod.soid and year(makedate) ='"+date[0]+"'  and month(makedate)='"+date[1]+"' " +conditon;
		return EntityManager.getdoubleSum(sql);
	}

	public List<SaleOrder> searchSaleOrder(String whereSql) {
		String sql = "  from SaleOrder as so " + whereSql
		+ " order by so.makedate desc";
		return EntityManager.getAllByHql(sql);
	}

}
