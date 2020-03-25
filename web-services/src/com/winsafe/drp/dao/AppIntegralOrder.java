package com.winsafe.drp.dao;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.pager2.PageQuery;

public class AppIntegralOrder {

	public List searchIntegralOrder(HttpServletRequest request,int pagesize, String pWhereClause) throws Exception {
		String sql = " select distinct io from IntegralOrder as io "
				+ pWhereClause + " order by io.makedate desc";		
		return PageQuery.hbmQuery(request, sql, pagesize);
	}
	
	
	public List getHistoryChen(String cid,String productid)throws Exception {
		//String sql = "select sod.unitprice,so.makedate from IntegralOrder as so,IntegralOrderDetail as sod where so.cid='"+cid+"' and sod.productid='"+productid+"' and so.id=sod.soid order by so.makedate desc";
		String sql = "select sbd.unitprice,sb.makedate from ShipmentBill as sb,ShipmentBillDetail as sbd where sb.receiveid='"+cid+"' and sbd.productid='"+productid+"' and sb.id=sbd.sbid order by sb.makedate desc";
		return EntityManager.getAllByHql(sql,1,5);
	}
	
	
	public List getIntegralOrderTotal(String pWhereClause)throws Exception{
		String sql = "select so.id,so.cid,so.cname,so.makedate from IntegralOrder as so "
			+ pWhereClause + " order by so.makedate desc";
		return EntityManager.getAllByHql(sql);
	}

	public void addIntegralOrder(Object IntegralOrder) throws Exception {
		
		EntityManager.save(IntegralOrder);
		
	}

	public IntegralOrder getIntegralOrderByID(String id) throws Exception {
		String sql = " from IntegralOrder as so where so.id='" + id + "'";
		return (IntegralOrder) EntityManager.find(sql);
	}
	
	public void delIntegralOrder(String soid)throws Exception{
		
		String sql="delete from integral_order where id='"+soid+"'";
		EntityManager.updateOrdelete(sql);
		
	}
	
	public void updIntegralOrder(IntegralOrder so) throws Exception{
		
		EntityManager.update(so);
		
	}

//	public String updIntegralOrderByID(IntegralOrder so, String consignmentdate)
//			throws Exception {
//		
//		String sql = "update integral_order set CustomerBillID='"
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
		String sql = "update integral_order set takestatus="+takestatus+" where id='" + id + "'";
		EntityManager.updateOrdelete(sql);
	}

	
	public void updIsAudit(String soid, int userid,int audit) throws Exception {		
		String sql = "update integral_order set isaudit="+audit+",auditid=" + userid
				+ ",auditdate='" + DateUtil.getCurrentDateTime()
				+ "' where id='" + soid + "'";
		EntityManager.updateOrdelete(sql);
		
	}
	
	
	public void updIsEndcase(String soid, int userid,int endcase) throws Exception {		
		String sql = "update integral_order set isendcase="+endcase+",endcaseid=" + userid
				+ ",endcasedate='" + DateUtil.getCurrentDateTime()
				+ "' where id='" + soid + "'";
		EntityManager.updateOrdelete(sql);
		
	}
	
	
	
//	public List getCustomerIntegralOrderOutlay(String whereSql_s,String whereSql_o)throws Exception{
//		List ls = new ArrayList();
//		String sql="select so.cid,so.makedate,sum(sod.subsum),0 from integral_order as so,integral_order_detail as sod "+whereSql_s+"  group by so.cid,so.makedate union select o.customerid,o.makedate,0,o.totaloutlay from outlay as o "+whereSql_o+" group by o.customerid,o.makedate,o.totaloutlay order by makedate desc";
//		System.out.println("---"+sql);
//		ResultSet rs = EntityManager.query(sql);
//		if(rs.getRow()>0){
//			do {
//				CustomerIntegralOrderOutlay csoo = new CustomerIntegralOrderOutlay();
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
	
	
	public void BlankoutIntegralOrder(String id, int userid)throws Exception{
		String sql="update integral_order set isblankout=1,blankoutid="+userid
		+",blankoutdate='"+DateUtil.getCurrentDateString()+"' where id='"+id+"'";
		EntityManager.updateOrdelete(sql);
	}
	
	
	public void blankout(String id, Long userid, Integer blankout, String blankoutreason) throws Exception {
		
		String sql = "update integral_order set isblankout="+blankout+",blankoutid=" + userid
				+ ",blankoutdate='" + DateUtil.getCurrentDateTime()
				+ "',blankoutreason='"+blankoutreason+"' where id='" + id + "'";
		EntityManager.updateOrdelete(sql);
		
	}
	
	public List getIntegralOrderCustomer(String pWhereClause)throws Exception{
		String sql = " from IntegralOrder as so  "
			+ pWhereClause + " order by so.id desc";
		return EntityManager.getAllByHql(sql);
	}
	
	public int getIntegralOrderEndcase(String cid) throws Exception {
		String sql = "select count(*) from IntegralOrder as so where so.cid ='"+cid+"' and so.isendcase=1";
		return EntityManager.getRecordCount(sql);
	}

	public List<IntegralOrder> searchIntegralOrder(String whereSql) {
		String sql = " select distinct io from IntegralOrder as io "
			+ whereSql + " order by io.makedate desc";	
		return EntityManager.getAllByHql(sql);
	}
	

}
