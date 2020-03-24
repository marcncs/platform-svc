package com.winsafe.drp.dao;

import java.util.List;

import com.winsafe.drp.entity.EntityManager;

public class AppPurchaseBillApprove{

 
 public List getPurchaseBillApprove(String pbid)throws Exception{
   List ls = null;
   String sql="select pba.id,pba.pbid,pba.approveid,pba.actid,pba.approvecontent,pba.approve,pba.approvedate from PurchaseBillApprove as pba where pba.pbid='"+pbid+"'";
   ls = EntityManager.getAllByHql(sql);
   return ls;
 }
 
 	
 	public List getPurchaseBillApproveByPbid(String pbid)throws Exception{
	   List ls = null;
	   String sql="from PurchaseBillApprove where operate=1 and pbid='"+pbid+"'";
	   ls = EntityManager.getAllByHql(sql);
	   return ls;
	 }
 
// 	public PurchaseBillApprove getPurchaseBillApproveByid(Long id)throws Exception{
//	   String sql="from PurchaseBillApprove where id="+id+"";
//	   return (PurchaseBillApprove)EntityManager.find(sql);
//	 }

 
 public void delPurchaseBillApproveByBillID(String pbid)throws Exception{
   
   String sql="delete from purchase_bill_approve where pbid='"+pbid+"'";
   EntityManager.updateOrdelete(sql);
   
 }
 

 public void cancelApprove(Integer approve,Integer actid,String pbid,Long userid)throws Exception{
  
  String sql = "update purchase_bill_approve set approve="+approve+" where pbid='"+pbid+"' and actid="+actid+" and approveid="+userid;
  EntityManager.updateOrdelete(sql);
  
 }
//
// 
// public void addPurchaseBillApprove(PurchaseBillApprove approve)throws Exception{
//   
//   EntityManager.save(approve);
//   
// }
// 
// 
// public void updPurchaseBillApprove(PurchaseBillApprove pba)throws Exception{
//	   
//	   EntityManager.update(pba);
//	   
//	 }
 

 public void updOperateByNextOrder(String pbid, int nextorder)throws Exception{
	 
	 String sql = "update purchase_bill_approve set operate=1 where pbid='"+pbid+"' and approveorder="+nextorder;
	 EntityManager.updateOrdelete(sql);
	 
 }
 
 public void updApproveContent(String approvecontent,int approve,String approvedate,Long id)throws Exception{
	 
	 String sql = "update purchase_bill_approve set operate=0, approvecontent='"+approvecontent+"', approve="+approve+",approvedate='"+approvedate+"' where id="+id;
	 EntityManager.updateOrdelete(sql);
	 
	}

 
public void addApproveContent(String approvecontent,int approve,String approvedate,String pbid,Integer actid,Long userid)throws Exception{
 
 String sql = "update purchase_bill_approve set approvecontent='"+approvecontent+"', approve="+approve+",approvedate='"+approvedate+"' where pbid='"+pbid+"' and actid="+actid+" and approveid="+userid;
 EntityManager.updateOrdelete(sql);
 
}


}
