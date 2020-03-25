package com.winsafe.drp.dao;

import java.util.List;

import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.pager.SimplePageInfo;

public class AppWebIndent {

	
	public List getWebIndent(int pagesize, String pWhereClause,
			SimplePageInfo pPgInfo) throws Exception {
		List pls = null;
		int targetPage = pPgInfo.getCurrentPageNo();
		String sql = "select so.id,so.customerbillid,so.cid,so.makedate,so.consignmentdate,so.totalsum,so.isrefer,so.approvestatus,so.orderstatus,so.isaudit,so.isaudittwo,so.makeid from WebIndent as so "
				+ pWhereClause + " order by so.makedate desc";
		pls = EntityManager.getAllByHql(sql, targetPage, pagesize);
		return pls;
	}
	
	public List searchWebIndent(int pagesize, String pWhereClause,
			SimplePageInfo pPgInfo) throws Exception {
		List pls = null;
		int targetPage = pPgInfo.getCurrentPageNo();
		String sql = " select distinct so from WebIndent as so "
				+ pWhereClause + " order by so.makedate desc";		
		pls = EntityManager.getAllByHql(sql, targetPage, pagesize);
		return pls;
	}
	
	
	public List getHistoryChen(String cid,String productid)throws Exception {
		List pls = null;
		//String sql = "select sod.unitprice,so.makedate from WebIndent as so,WebIndentDetail as sod where so.cid='"+cid+"' and sod.productid='"+productid+"' and so.id=sod.soid order by so.makedate desc";
		String sql = "select sbd.unitprice,sb.makedate from ShipmentBill as sb,ShipmentBillDetail as sbd where sb.receiveid='"+cid+"' and sbd.productid='"+productid+"' and sb.id=sbd.sbid order by sb.makedate desc";
		pls = EntityManager.getAllByHql(sql,1,5);
		return pls;
	}
	
	
	public List getWebIndentTotal(int pagesize, String pWhereClause,
			SimplePageInfo pPgInfo)throws Exception{
		List pls = null;
		int targetPage = pPgInfo.getCurrentPageNo();
		String sql = "select so.id,so.cid,so.cname,so.makedate from WebIndent as so "
			+ pWhereClause + " order by so.makedate desc";
		pls = EntityManager.getAllByHql(sql, targetPage, pagesize);
		return pls;
	}
	
	public double getWebIndentTotal(String pWhereClause)throws Exception{		
		String sql = "select sum(so.totalsum) from WebIndent as so "
			+ pWhereClause;
		return EntityManager.getdoubleSum(sql);
	}

	public void addWebIndent(WebIndent w) throws Exception {
		
		EntityManager.save(w);
		
	}

	public WebIndent getWebIndentByID(String id) throws Exception {
		WebIndent sl = null;
		String sql = " from WebIndent as so where so.id='" + id + "'";
		sl = (WebIndent) EntityManager.find(sql);
		return sl;
	}
	
	public void delWebIndent(String soid)throws Exception{
		
		String sql="delete from sale_order where id='"+soid+"'";
		EntityManager.updateOrdelete(sql);
		
	}
	
	public void updWebIndent(WebIndent so) throws Exception{
		
		EntityManager.update(so);
		
	}

//	public String updWebIndentByID(WebIndent so, String consignmentdate)
//			throws Exception {
//		
//		String sql = "update sale_order set CustomerBillID='"
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
		String sql = "update web_indent set takestatus="+takestatus+" where id='" + id + "'";
		EntityManager.updateOrdelete(sql);
	}

	
	public List waitApproveWebIndent(int pagesize, String pWhereClause,
			SimplePageInfo pPgInfo) throws Exception {
		List wsl = null;
		int targetPage = pPgInfo.getCurrentPageNo();
		String sql = "select so.id,so.cname,so.makeid,so.totalsum,so.makedate,soa.actid,soa.approve,so.consignmentdate from WebIndent as so,WebIndentApprove as soa "
				+ pWhereClause + " order by so.makedate desc";
		wsl = EntityManager.getAllByHql(sql, targetPage, pagesize);
		return wsl;
	}

	

	public void updIsApprove(String id, int isapprove) throws Exception {
		String sql = "update web_indent set approvestatus=" + isapprove
				+ ",approvedate='" + DateUtil.getCurrentDateTime()
				+ "' where id ='" + id + "'";
		EntityManager.updateOrdelete(sql);
	}

	

	public double getTotalIncome(String pWhereClause) throws Exception {
		double totalincome = Double.parseDouble("0.00");
		String sql = "select sum(sl.totalsum) from WebIndent as sl "
				+ pWhereClause + " sl.approvestatus =1";
		totalincome = EntityManager.getdoubleSum(sql);
		return totalincome;
	}

	public List getShouldIncome(int pagesize, String pWhereClause,
			SimplePageInfo pPgInfo) throws Exception {
		List pb = null;
		int targetPage = pPgInfo.getCurrentPageNo();
		String sql = "select sl.id,sl.cid,sl.totalsum,sl.consignmentdate,sl.orderstatus,sl.isreceiveall from WebIndent as sl "
				+ pWhereClause + " order by sl.createdate desc ";
		pb = EntityManager.getAllByHql(sql, targetPage, pagesize);
		return pb;
	}

	
	public void updIsTransferShipment(Long slid) throws Exception {
		
		String sql = "update web_indent set istransfershipment=1 where id="
				+ slid;
		EntityManager.updateOrdelete(sql);
		
	}

	
	public int waitShipmentCount() throws Exception {
		int count = 0;
		String sql = "select count(*) from WebIndent as sl where sl.approvestatus=2 and sl.istransfershipment=0 and sl.isshipment=1";
		count = EntityManager.getRecordCount(sql);
		return count;
	}
	
	
	public int waitInputSaleShipmentCount() throws Exception {
		int count = 0;
		String sql = "select count(*) from WebIndent as so where so.isaudit=1 and so.isendcase=0 and so.salesort=1";
		count = EntityManager.getRecordCount(sql);
		return count;
	}

	
	public void updIsShipment(String soid) throws Exception {
		
		String sql = "update web_indent set isshipment=1 where id='" + soid
				+ "'";
		// System.out.println("+++++++++++++"+sql);
		EntityManager.updateOrdelete(sql);
		
	}

	
	public void updIsTransferShipment(String soid) throws Exception {
		
		String sql = "update web_indent set istransfershipment=1 where id='"
				+ soid + "'";
		// System.out.println("+++++++++++++"+sql);
		EntityManager.updateOrdelete(sql);
		
	}

	
	public void updIsCompleteControl(String soid) throws Exception {
		
		String sql = "update web_indent set IsCompleteControl=1 where id='"
				+ soid + "'";
		// System.out.println("+++++++++++++"+sql);
		EntityManager.updateOrdelete(sql);
		
	}

	
	public void updOrderStatus(String soid, int orderstatus) throws Exception {
		
		String sql = "update web_indent set orderstatus=" + orderstatus
				+ " where id='" + soid + "'";
		EntityManager.updateOrdelete(sql);
		
	}

	
	public void updIsAudit(String soid, Long userid,Integer audit) throws Exception {
		
		String sql = "update web_indent set isaudit="+audit+",auditid=" + userid
				+ ",auditdate='" + DateUtil.getCurrentDateTime()
				+ "' where id='" + soid + "'";
		EntityManager.updateOrdelete(sql);
		
	}
	
	
	public void updIsEndcase(String soid, Long userid,Integer endcase) throws Exception {
		
		String sql = "update web_indent set isendcase="+endcase+",endcaseid=" + userid
				+ ",endcasedate='" + DateUtil.getCurrentDateTime()
				+ "' where id='" + soid + "'";
		EntityManager.updateOrdelete(sql);
		
	}
	
	
	public void updIsAudittwo(String soid, Long userid,Integer audit) throws Exception {
		
		String sql = "update web_indent set isaudittwo="+audit+",audittwoid=" + userid
				+ ",audittwodate='" + DateUtil.getCurrentDateTime()
				+ "' where id='" + soid + "'";
		EntityManager.updateOrdelete(sql);
		
	}
	
	
	
	public void BlankoutWebIndent(String id, Long userid)throws Exception{
		String sql="update web_indent set isblankout=1,blankoutid="+userid
		+",blankoutdate='"+DateUtil.getCurrentDateString()+"' where id='"+id+"'";
		EntityManager.updateOrdelete(sql);
	}
	
	
	public void blankout(String id, Long userid, Integer blankout, String blankoutreason) throws Exception {
		
		String sql = "update web_indent set isblankout="+blankout+",blankoutid=" + userid
				+ ",blankoutdate='" + DateUtil.getCurrentDateTime()
				+ "',blankoutreason='"+blankoutreason+"' where id='" + id + "'";
		EntityManager.updateOrdelete(sql);
		
	}
	
	public List getWebIndentCustomer(String pWhereClause)throws Exception{
		List pls = null;
		String sql = " from WebIndent as so  "
			+ pWhereClause + " order by so.id desc";
		pls = EntityManager.getAllByHql(sql);
		return pls;
	}
	
	public int getWebIndentEndcase(String cid) throws Exception {
		int count = 0;
		String sql = "select count(*) from WebIndent as so where so.cid ='"+cid+"' and so.isendcase=1";
		count = EntityManager.getRecordCount(sql);
		return count;
	}
	

}
