package com.winsafe.drp.dao;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.pager.SimplePageInfo;

public class AppVocationOrder {

	
	public List getVocationOrder(int pagesize, String pWhereClause,
			SimplePageInfo pPgInfo) throws Exception {
		List pls = null;
		int targetPage = pPgInfo.getCurrentPageNo();
		String sql = "select so.id,so.customerbillid,so.cid,so.makedate,so.consignmentdate,so.totalsum,so.isrefer,so.approvestatus,so.orderstatus,so.isaudit,so.isaudittwo,so.makeid from VocationOrder as so "
				+ pWhereClause + " order by so.makedate desc";
		pls = EntityManager.getAllByHql(sql, targetPage, pagesize);
		return pls;
	}
	
	public List getVocationOrderAll(int pagesize, String pWhereClause,
			SimplePageInfo pPgInfo) throws Exception {
		List pls = null;
		int targetPage = pPgInfo.getCurrentPageNo();
		String sql = " from VocationOrder as so "
				+ pWhereClause + " order by so.makedate desc";
		pls = EntityManager.getAllByHql(sql, targetPage, pagesize);
		return pls;
	}
	
	public List searchVocationOrder(int pagesize, String pWhereClause,
			SimplePageInfo pPgInfo) throws Exception {
		List pls = null;
		int targetPage = pPgInfo.getCurrentPageNo();
		String sql = " select distinct so from VocationOrder as so "
				+ pWhereClause + " order by so.makedate desc";		
		pls = EntityManager.getAllByHql(sql, targetPage, pagesize);
		return pls;
	}
	
	
	public List getHistoryChen(String cid,String productid)throws Exception {
		List pls = null;
		//String sql = "select sod.unitprice,so.makedate from VocationOrder as so,VocationOrderDetail as sod where so.cid='"+cid+"' and sod.productid='"+productid+"' and so.id=sod.soid order by so.makedate desc";
		String sql = "select sbd.unitprice,sb.makedate from ShipmentBill as sb,ShipmentBillDetail as sbd where sb.cid='"+cid+"' and sbd.productid='"+productid+"' and sb.id=sbd.sbid order by sb.makedate desc";
		pls = EntityManager.getAllByHql(sql,1,5);
		return pls;
	}
	
	
	public List getVocationOrderTotal(int pagesize, String pWhereClause,
			SimplePageInfo pPgInfo)throws Exception{
		List pls = null;
		int targetPage = pPgInfo.getCurrentPageNo();
		String sql = "select so.id,so.cid,so.cname,so.makedate from VocationOrder as so "
			+ pWhereClause + " order by so.makedate desc";
		pls = EntityManager.getAllByHql(sql, targetPage, pagesize);
		return pls;
	}
	
	public double getVocationOrderTotal(String pWhereClause)throws Exception{		
		String sql = "select sum(so.totalsum) from VocationOrder as so "
			+ pWhereClause;
		System.out.println("---------"+sql);
		return EntityManager.getdoubleSum(sql);
	}

	public void addVocationOrder(Object VocationOrder) throws Exception {
		
		EntityManager.save(VocationOrder);
		
	}

	public VocationOrder getVocationOrderByID(String id) throws Exception {
		VocationOrder sl = null;
		String sql = " from VocationOrder as so where so.id='" + id + "'";
		sl = (VocationOrder) EntityManager.find(sql);
		return sl;
	}
	
	public void delVocationOrder(String soid)throws Exception{
		
		String sql="delete from vocation_order where id='"+soid+"'";
		EntityManager.updateOrdelete(sql);
		
	}
	
	public void updVocationOrder(VocationOrder so) throws Exception{
		
		EntityManager.update(so);
		
	}

//	public String updVocationOrderByID(VocationOrder so, String consignmentdate)
//			throws Exception {
//		
//		String sql = "update vocation_order set CustomerBillID='"
//				+ so.getCustomerbillid() + "',CID='" + so.getCid() + "',CName='"
//				+ so.getCname() + "',SaleType=" + so.getSaletype()
//				+ ",SaleDept=" + so.getSaledept() + ",ConsignmentDate='"
//				+ consignmentdate + "',FundSrc=" + so.getFundsrc()
//				+ ",FundAttach=" + so.getFundattach() + ",TotalSum="
//				+ so.getTotalsum() + ",TransportMode=" + so.getTransportmode()
//				+ ",TransportAddr='" + so.getTransportaddr() + "',ReceiveMan='"
//				+ so.getReceiveman() + "',Tel='" + so.getTel() + "',UpdateID=" + so.getUpdateid()
//				+ ",LastUpdate='" + DateUtil.getCurrentDateTime()
//				+ "',Remark='" + so.getRemark() + "' where ID='" + so.getId()
//				+ "'";
//		// System.out.println("--sql=="+sql);
//		EntityManager.updateOrdelete(sql);
//		
//	}

	
	public void updTakeStatus(String id, int takestatus) throws Exception {
		String sql = "update vocation_order set takestatus="+takestatus+" where id='" + id + "'";
		EntityManager.updateOrdelete(sql);
	}

	
	public List waitApproveVocationOrder(int pagesize, String pWhereClause,
			SimplePageInfo pPgInfo) throws Exception {
		List wsl = null;
		int targetPage = pPgInfo.getCurrentPageNo();
		String sql = "select so.id,so.cname,so.makeid,so.totalsum,so.makedate,soa.actid,soa.approve,so.consignmentdate from VocationOrder as so,VocationOrderApprove as soa "
				+ pWhereClause + " order by so.makedate desc";
		wsl = EntityManager.getAllByHql(sql, targetPage, pagesize);
		return wsl;
	}

	

	public void updIsApprove(String id, int isapprove) throws Exception {
		String sql = "update vocation_order set approvestatus=" + isapprove
				+ ",approvedate='" + DateUtil.getCurrentDateTime()
				+ "' where id ='" + id + "'";
		EntityManager.updateOrdelete(sql);
	}

	

	public double getTotalIncome(String pWhereClause) throws Exception {
		double totalincome = Double.parseDouble("0.00");
		String sql = "select sum(sl.totalsum) from VocationOrder as sl "
				+ pWhereClause + " sl.approvestatus =1";
		totalincome = EntityManager.getdoubleSum(sql);
		return totalincome;
	}

	public List getShouldIncome(int pagesize, String pWhereClause,
			SimplePageInfo pPgInfo) throws Exception {
		List pb = null;
		int targetPage = pPgInfo.getCurrentPageNo();
		String sql = "select sl.id,sl.cid,sl.totalsum,sl.consignmentdate,sl.orderstatus,sl.isreceiveall from VocationOrder as sl "
				+ pWhereClause + " order by sl.createdate desc ";
		pb = EntityManager.getAllByHql(sql, targetPage, pagesize);
		return pb;
	}

	
	public void updIsTransferShipment(Long slid) throws Exception {
		
		String sql = "update vocation_order set istransfershipment=1 where id="
				+ slid;
		EntityManager.updateOrdelete(sql);
		
	}

	
	public int waitShipmentCount() throws Exception {
		int count = 0;
		String sql = "select count(*) from VocationOrder as sl where sl.approvestatus=2 and sl.istransfershipment=0 and sl.isshipment=1";
		count = EntityManager.getRecordCount(sql);
		return count;
	}
	
	
	public int waitInputSaleShipmentCount() throws Exception {
		int count = 0;
		String sql = "select count(*) from VocationOrder as so where so.isaudit=1 and so.isendcase=0 and so.salesort=1";
		count = EntityManager.getRecordCount(sql);
		return count;
	}

	
	public void updIsShipment(String soid) throws Exception {
		
		String sql = "update vocation_order set isshipment=1 where id='" + soid
				+ "'";
		// System.out.println("+++++++++++++"+sql);
		EntityManager.updateOrdelete(sql);
		
	}

	
	public void updIsTransferShipment(String soid) throws Exception {
		
		String sql = "update vocation_order set istransfershipment=1 where id='"
				+ soid + "'";
		// System.out.println("+++++++++++++"+sql);
		EntityManager.updateOrdelete(sql);
		
	}

	
	public void updIsCompleteControl(String soid) throws Exception {
		
		String sql = "update vocation_order set IsCompleteControl=1 where id='"
				+ soid + "'";
		// System.out.println("+++++++++++++"+sql);
		EntityManager.updateOrdelete(sql);
		
	}

	
	public void updOrderStatus(String soid, int orderstatus) throws Exception {
		
		String sql = "update vocation_order set orderstatus=" + orderstatus
				+ " where id='" + soid + "'";
		EntityManager.updateOrdelete(sql);
		
	}

	
	public void updIsAudit(String soid, Long userid,Integer audit) throws Exception {
		
		String sql = "update vocation_order set isaudit="+audit+",auditid=" + userid
				+ ",auditdate='" + DateUtil.getCurrentDateTime()
				+ "' where id='" + soid + "'";
		EntityManager.updateOrdelete(sql);
		
	}
	
	
	public void updIsEndcase(String soid, Long userid,Integer endcase) throws Exception {
		
		String sql = "update vocation_order set isendcase="+endcase+",endcaseid=" + userid
				+ ",endcasedate='" + DateUtil.getCurrentDateTime()
				+ "' where id='" + soid + "'";
		EntityManager.updateOrdelete(sql);
		
	}
	
	
	public void updIsAudittwo(String soid, Long userid,Integer audit) throws Exception {
		
		String sql = "update vocation_order set isaudittwo="+audit+",audittwoid=" + userid
				+ ",audittwodate='" + DateUtil.getCurrentDateTime()
				+ "' where id='" + soid + "'";
		EntityManager.updateOrdelete(sql);
		
	}
	
	
//	public List getCustomerVocationOrderOutlay(String whereSql_s,String whereSql_o)throws Exception{
//		List ls = new ArrayList();
//		String sql="select so.cid,so.makedate,sum(sod.subsum),0 from vocation_order as so,vocation_order_detail as sod "+whereSql_s+"  group by so.cid,so.makedate union select o.customerid,o.makedate,0,o.totaloutlay from outlay as o "+whereSql_o+" group by o.customerid,o.makedate,o.totaloutlay order by makedate desc";
//		System.out.println("---"+sql);
//		ResultSet rs = EntityManager.query(sql);
//		if(rs.getRow()>0){
//			do {
//				CustomerVocationOrderOutlay csoo = new CustomerVocationOrderOutlay();
//				csoo.setCid(rs.getString(1));
//				csoo.setMakedate(rs.getString(2));
//				csoo.setSubsum(rs.getDouble(3));
//				csoo.setTotaloutlay(rs.getDouble(4));
//				ls.add(csoo);
//			} while (rs.next());
//		}
//		rs.close();
//		return ls;
//	}
	
	
	public void BlankoutVocationOrder(String id, Long userid)throws Exception{
		String sql="update vocation_order set isblankout=1,blankoutid="+userid
		+",blankoutdate='"+DateUtil.getCurrentDateString()+"' where id='"+id+"'";
		EntityManager.updateOrdelete(sql);
	}
	
	
	public void blankout(String id, Long userid, Integer blankout, String blankoutreason) throws Exception {
		
		String sql = "update vocation_order set isblankout="+blankout+",blankoutid=" + userid
				+ ",blankoutdate='" + DateUtil.getCurrentDateTime()
				+ "',blankoutreason='"+blankoutreason+"' where id='" + id + "'";
		EntityManager.updateOrdelete(sql);
		
	}
	
	public List getVocationOrderCustomer(String pWhereClause)throws Exception{
		List pls = null;
		String sql = " from VocationOrder as so  "
			+ pWhereClause + " order by so.id desc";
		pls = EntityManager.getAllByHql(sql);
		return pls;
	}
	
	public int getVocationOrderEndcase(String cid) throws Exception {
		int count = 0;
		String sql = "select count(*) from VocationOrder as so where so.cid ='"+cid+"' and so.isendcase=1";
		count = EntityManager.getRecordCount(sql);
		return count;
	}
	
	
	public void updIsMakeTicket(String soid,int ismake)throws Exception{
		String sql="update vocation_order set ismaketicket="+ismake+" where id='"+soid+"'";
		EntityManager.updateOrdelete(sql);
	}
	
	
	
	public List getSaleTotal(HttpServletRequest request, int pagesize, 
			String pWhereClause)throws Exception{
		String hql = "select d.productid, d.productname, d.specmode, d.unitid, sum(d.quantity) as quantity, sum(d.subsum) as subsum, sum(d.subcost) as subcost, sum(d.gain) as gain " +
		 " from ViewSaleProductTotal d " + pWhereClause + 
		" group by d.productid,d.productname, d.specmode, d.unitid ";
		String sql = hql.replace("ViewSaleProductTotal", "view_sale_product_total");
		Object obj[] = DbUtil.setGroupByPager(request, sql, pagesize, "VocationOrderReport");
		SimplePageInfo tmpPgInfo = (SimplePageInfo) obj[0]; 
		int targetPage = tmpPgInfo.getCurrentPageNo();
		
		return EntityManager.getAllByHql(hql, targetPage, pagesize);
	}
	
	public List getSaleTotal(String pWhereClause)throws Exception{
		String hql = "select d.productid, d.productname, d.specmode, d.unitid, sum(d.quantity) as quantity, sum(d.subsum) as subsum, sum(d.subcost) as subcost, sum(d.gain) as gain " +
		 " from ViewSaleProductTotal d " + pWhereClause + 
		" group by d.productid,d.productname, d.specmode, d.unitid ";
		
		return EntityManager.getAllByHql(hql);
	}
	
	
	public List getProductOrder(HttpServletRequest request, int pagesize, 
			String pWhereClause, String ordertype)throws Exception{
		String ordersql = "order by sum(d.quantity) desc ";
		if ( ordertype.equals("s") ){
			ordersql = "order by sum(d.subsum) desc ";
		}
		
		String hql = "select d.productid, d.productname, d.specmode, d.unitid, sum(d.quantity) as quantity, sum(d.subsum) as subsum " +
		 " from ViewSaleProductTotal d " + pWhereClause + 
		" group by d.productid,d.productname, d.specmode, d.unitid "+ ordersql;
		String sql = hql.replace("ViewSaleProductTotal", "view_sale_product_total");
		Object obj[] = DbUtil.setGroupByPager(request, sql, pagesize, "VocationOrderReport");
		SimplePageInfo tmpPgInfo = (SimplePageInfo) obj[0]; 
		int targetPage = tmpPgInfo.getCurrentPageNo();
		
		return EntityManager.getAllByHql(hql, targetPage, pagesize);
	}
	
	public List getProductOrder(String pWhereClause, String ordertype)throws Exception{
		String ordersql = "order by sum(d.quantity) desc ";
		if ( ordertype.equals("s") ){
			ordersql = "order by sum(d.subsum) desc ";
		}
		
		String hql = "select d.productid, d.productname, d.specmode, d.unitid, sum(d.quantity) as quantity, sum(d.subsum) as subsum " +
		 " from ViewSaleProductTotal d " + pWhereClause + 
		" group by d.productid,d.productname, d.specmode, d.unitid "+ ordersql;
		return EntityManager.getAllByHql(hql);
	}
	
	
	
	public List getTotalSum(String pWhereClause) throws Exception {
		String sql = "select sum(d.quantity), sum(d.subsum), sum(d.subcost), sum(d.gain)" +
			" from ViewSaleProductTotal d " + pWhereClause ;
		return EntityManager.getAllByHql(sql);
	}
	
	public List getSaleCustomerTotal(String pWhereClause)throws Exception{
		String hql = "select d.cid, d.cname, d.cmobile, sum(d.totalsum) as totalsum " +
		 " from ViewSaleCustomerTotal d " + pWhereClause + 
		" group by d.cid,d.cname, d.cmobile order by d.cid ";		
		return EntityManager.getAllByHql(hql);
	}
	
	public List getSaleCustomerTotal(HttpServletRequest request, int pagesize, 
			String pWhereClause)throws Exception{
		String hql = "select d.cid, d.cname, d.cmobile, sum(d.totalsum) as totalsum " +
		 " from ViewSaleCustomerTotal d " + pWhereClause + 
		" group by d.cid,d.cname, d.cmobile order by d.cid ";
		String sql = hql.replace("ViewSaleCustomerTotal", "view_sale_customer_total");
		Object obj[] = DbUtil.setGroupByPager(request, sql, pagesize, "SaleCustomerReport");
		SimplePageInfo tmpPgInfo = (SimplePageInfo) obj[0]; 
		int targetPage = tmpPgInfo.getCurrentPageNo();
		
		return EntityManager.getAllByHql(hql, targetPage, pagesize);
	}
	
	public List getSaleBillTotal(String pWhereClause)throws Exception{
		String hql = "select d.id, d.billtype, d.makedate, d.totalsum, d.cname,d.makeorganid " +
		 " from ViewSaleCustomerTotal d " + pWhereClause + 
		" order by d.id ";		
		return EntityManager.getAllByHql(hql);
	}
	
	public List getSaleBillTotal(HttpServletRequest request, int pagesize, 
			String pWhereClause)throws Exception{
		String hql = "select d.id, d.billtype, d.makedate, d.totalsum, d.cname,d.makeorganid " +
		 " from ViewSaleCustomerTotal d " + pWhereClause + 
		" order by d.id ";
		
		Object obj[] = DbUtil.setDynamicPager(request, "ViewSaleCustomerTotal as d ", 
				pWhereClause, pagesize, "SaleBillReport");
		SimplePageInfo tmpPgInfo = (SimplePageInfo) obj[0]; 
		int targetPage = tmpPgInfo.getCurrentPageNo();
		
		return EntityManager.getAllByHql(hql, targetPage, pagesize);
	}
	
	public double getSaleCustomerTotalSum(String pWhereClause) throws Exception {
		String sql = "select sum(d.totalsum) " +
			" from ViewSaleCustomerTotal d " + pWhereClause ;
		return EntityManager.getdoubleSum(sql);
	}
	
	

}
