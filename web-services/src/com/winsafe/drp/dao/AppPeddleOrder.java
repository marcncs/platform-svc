package com.winsafe.drp.dao;

import java.util.List;

import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.pager.SimplePageInfo;

public class AppPeddleOrder {

	
	public List getPeddleOrder(int pagesize, String pWhereClause,
			SimplePageInfo pPgInfo) throws Exception {
		List pls = null;
		int targetPage = pPgInfo.getCurrentPageNo();
		String sql = "select so.id,so.customerbillid,so.cid,so.makedate,so.consignmentdate,so.totalsum,so.isrefer,so.approvestatus,so.orderstatus,so.isaudit,so.isaudittwo,so.makeid from PeddleOrder as so "
				+ pWhereClause + " order by so.makedate desc";
		pls = EntityManager.getAllByHql(sql, targetPage, pagesize);
		return pls;
	}
	
	public List searchPeddleOrder(int pagesize, String pWhereClause,
			SimplePageInfo pPgInfo) throws Exception {
		List pls = null;
		int targetPage = pPgInfo.getCurrentPageNo();
		String sql = " from PeddleOrder as so "
				+ pWhereClause + " order by so.makedate desc";
		pls = EntityManager.getAllByHql(sql, targetPage, pagesize);
		return pls;
	}
	
	public List searchPeddleOrder(String pWhereClause) throws Exception {
		String sql = " from PeddleOrder as so "
				+ pWhereClause + " order by so.makedate desc";
		return EntityManager.getAllByHql(sql);
	}
	
	
	public List getHistoryChen(String cid,String productid)throws Exception {
		List pls = null;
		//String sql = "select sod.unitprice,so.makedate from PeddleOrder as so,PeddleOrderDetail as sod where so.cid='"+cid+"' and sod.productid='"+productid+"' and so.id=sod.soid order by so.makedate desc";
		String sql = "select sbd.unitprice,sb.makedate from ShipmentBill as sb,ShipmentBillDetail as sbd where sb.receiveid='"+cid+"' and sbd.productid='"+productid+"' and sb.id=sbd.sbid order by sb.makedate desc";
		pls = EntityManager.getAllByHql(sql,1,5);
		return pls;
	}
	
	
	public List getPeddleOrderTotal(int pagesize, String pWhereClause,
			SimplePageInfo pPgInfo)throws Exception{
		List pls = null;
		int targetPage = pPgInfo.getCurrentPageNo();
		String sql = "select so.id,so.cid,so.cname,so.makedate from PeddleOrder as so "
			+ pWhereClause + " order by so.makedate desc";
		pls = EntityManager.getAllByHql(sql, targetPage, pagesize);
		return pls;
	}
	
	public double getPeddleOrderTotal(String pWhereClause)throws Exception{		
		String sql = "select sum(so.totalsum) from PeddleOrder as so "
			+ pWhereClause;
		return EntityManager.getdoubleSum(sql);
	}

	public void addPeddleOrder(Object Peddleorder) throws Exception {
		
		EntityManager.save(Peddleorder);
		
	}

	public PeddleOrder getPeddleOrderByID(String id) throws Exception {
		PeddleOrder sl = null;
		String sql = " from PeddleOrder as so where so.id='" + id + "'";
		sl = (PeddleOrder) EntityManager.find(sql);
		return sl;
	}
	
	public void delPeddleOrder(String soid)throws Exception{
		
		String sql="delete from peddle_order where id='"+soid+"'";
		EntityManager.updateOrdelete(sql);
		
	}
	
	public void updPeddleOrder(PeddleOrder so) throws Exception{
		
		EntityManager.update(so);
		
	}

//	public String updPeddleOrderByID(PeddleOrder so, String consignmentdate)
//			throws Exception {
//		
//		String sql = "update Peddle_order set CustomerBillID='"
//				+ so.getCustomerbillid() + "',CID='" + so.getCid() + "',CName='"
//				+ so.getCname() + "',PeddleType=" + so.getPeddletype()
//				+ ",PeddleDept=" + so.getPeddledept() + ",ConsignmentDate='"
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
		
		String sql = "update Peddle_order set takestatus="+takestatus+" where id='" + id + "'";
		EntityManager.updateOrdelete(sql);
		
	}

	
	public List waitApprovePeddleOrder(int pagesize, String pWhereClause,
			SimplePageInfo pPgInfo) throws Exception {
		List wsl = null;
		int targetPage = pPgInfo.getCurrentPageNo();
		String sql = "select so.id,so.cname,so.makeid,so.totalsum,so.makedate,soa.actid,soa.approve,so.consignmentdate from PeddleOrder as so,PeddleOrderApprove as soa "
				+ pWhereClause + " order by so.makedate desc";
		wsl = EntityManager.getAllByHql(sql, targetPage, pagesize);
		return wsl;
	}

	

	public void updIsApprove(String id, int isapprove) throws Exception {
		String sql = "update Peddle_order set approvestatus=" + isapprove
				+ ",approvedate='" + DateUtil.getCurrentDateTime()
				+ "' where id ='" + id + "'";
		EntityManager.updateOrdelete(sql);
	}

	

	public double getTotalIncome(String pWhereClause) throws Exception {
		double totalincome = Double.parseDouble("0.00");
		String sql = "select sum(sl.totalsum) from PeddleOrder as sl "
				+ pWhereClause + " sl.approvestatus =1";
		totalincome = EntityManager.getdoubleSum(sql);
		return totalincome;
	}

	public List getShouldIncome(int pagesize, String pWhereClause,
			SimplePageInfo pPgInfo) throws Exception {
		List pb = null;
		int targetPage = pPgInfo.getCurrentPageNo();
		String sql = "select sl.id,sl.cid,sl.totalsum,sl.consignmentdate,sl.orderstatus,sl.isreceiveall from PeddleOrder as sl "
				+ pWhereClause + " order by sl.createdate desc ";
		pb = EntityManager.getAllByHql(sql, targetPage, pagesize);
		return pb;
	}

	
	public void updIsTransferShipment(Long slid) throws Exception {
		
		String sql = "update Peddle_order set istransfershipment=1 where id="
				+ slid;
		EntityManager.updateOrdelete(sql);
		
	}
	
	
	public void updIsDayBalance(String id) throws Exception {
		String sql = "update Peddle_order set isdaybalance=1 where id='"
				+ id +"'";
		EntityManager.updateOrdelete(sql);
		
	}

	
	public int waitShipmentCount() throws Exception {
		int count = 0;
		String sql = "select count(*) from PeddleOrder as sl where sl.approvestatus=2 and sl.istransfershipment=0 and sl.isshipment=1";
		count = EntityManager.getRecordCount(sql);
		return count;
	}
	
	
	public int waitInputPeddleShipmentCount() throws Exception {
		int count = 0;
		String sql = "select count(*) from PeddleOrder as so where so.isaudit=1 and so.isendcase=0 and so.Peddlesort=1";
		count = EntityManager.getRecordCount(sql);
		return count;
	}

	
	public void updIsShipment(String soid) throws Exception {
		
		String sql = "update Peddle_order set isshipment=1 where id='" + soid
				+ "'";
		// System.out.println("+++++++++++++"+sql);
		EntityManager.updateOrdelete(sql);
		
	}

	
	public void updIsTransferShipment(String soid) throws Exception {
		
		String sql = "update Peddle_order set istransfershipment=1 where id='"
				+ soid + "'";
		// System.out.println("+++++++++++++"+sql);
		EntityManager.updateOrdelete(sql);
		
	}

	
	public void updIsCompleteControl(String soid) throws Exception {
		
		String sql = "update Peddle_order set IsCompleteControl=1 where id='"
				+ soid + "'";
		// System.out.println("+++++++++++++"+sql);
		EntityManager.updateOrdelete(sql);
		
	}

	
	public void updOrderStatus(String soid, int orderstatus) throws Exception {
		
		String sql = "update Peddle_order set orderstatus=" + orderstatus
				+ " where id='" + soid + "'";
		EntityManager.updateOrdelete(sql);
		
	}

	
	public void updIsAudit(String soid, Long userid,Integer audit) throws Exception {
		
		String sql = "update Peddle_order set isaudit="+audit+",auditid=" + userid
				+ ",auditdate='" + DateUtil.getCurrentDateTime()
				+ "' where id='" + soid + "'";
		EntityManager.updateOrdelete(sql);
		
	}
	
	
	public void updIsEndcase(String soid, Long userid,Integer endcase) throws Exception {
		
		String sql = "update Peddle_order set isendcase="+endcase+",endcaseid=" + userid
				+ ",endcasedate='" + DateUtil.getCurrentDateTime()
				+ "' where id='" + soid + "'";
		EntityManager.updateOrdelete(sql);
		
	}
	
	
	public void updIsAudittwo(String soid, Long userid,Integer audit) throws Exception {
		
		String sql = "update peddle_order set isaudittwo="+audit+",audittwoid=" + userid
				+ ",audittwodate='" + DateUtil.getCurrentDateTime()
				+ "' where id='" + soid + "'";
		EntityManager.updateOrdelete(sql);
		
	}
	
	
//	public List getCustomerPeddleOrderOutlay(String whereSql_s,String whereSql_o)throws Exception{
//		List ls = new ArrayList();
//		String sql="select so.cid,so.makedate,sum(sod.subsum),0 from Peddle_order as so,Peddle_order_detail as sod "+whereSql_s+"  group by so.cid,so.makedate union select o.customerid,o.makedate,0,o.totaloutlay from outlay as o "+whereSql_o+" group by o.customerid,o.makedate,o.totaloutlay order by makedate desc";
//		System.out.println("---"+sql);
//		ResultSet rs = EntityManager.query(sql);
//		if(rs.getRow()>0){
//			do {
//				CustomerPeddleOrderOutlay csoo = new CustomerPeddleOrderOutlay();
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
	
	
	public void BlankoutPeddleOrder(String id, Long userid)throws Exception{
		
		String sql="update peddle_order set isblankout=1,blankoutid="+userid
		+",blankoutdate='"+DateUtil.getCurrentDateString()+"' where id='"+id+"'";
		EntityManager.updateOrdelete(sql);
		
	}
	
	
	public void blankout(String id, Long userid, Integer blankout, String blankoutreason) throws Exception {
		
		String sql = "update peddle_order set isblankout="+blankout+",blankoutid=" + userid
				+ ",blankoutdate='" + DateUtil.getCurrentDateTime()
				+ "',blankoutreason='"+blankoutreason+"' where id='" + id + "'";
		EntityManager.updateOrdelete(sql);
		
	}
	
	public List getPeddleOrderCustomer(String pWhereClause)throws Exception{
		List pls = null;
		String sql = "select so.id,so.cid,so.cname,so.totalsum,so.makedate,u.realname from PeddleOrder as so, Customer as c, Users as u  "
			+ pWhereClause + " order by so.id desc";
		pls = EntityManager.getAllByHql(sql);
		return pls;
	}
	
	public int getPeddleOrderByCid(String cid) throws Exception {
		int count = 0;
		String sql = "select count(*) from PeddleOrder as so where so.cid ='"+cid+"'";
		count = EntityManager.getRecordCount(sql);
		return count;
	}
	

}
