package com.winsafe.drp.dao;

import java.util.List;

import com.winsafe.drp.entity.EntityManager;

public class AppPurchaseOrderApprove{

 
 public List getPurchaseOrderApprove(String pbid)throws Exception{
   List ls = null;
   String sql="select pba.id,pba.pbid,pba.approveid,pba.actid,pba.approvecontent,pba.approve,pba.approvedate from PurchaseOrderApprove as pba where pba.poid='"+pbid+"'";
   ls = EntityManager.getAllByHql(sql);
   return ls;
 }
 
 
 	
 	public List getPurchaseOrderApproveByPoid(String poid)throws Exception{
	   List ls = null;
	   String sql="from PurchaseOrderApprove where poid='"+poid+"'";
	   ls = EntityManager.getAllByHql(sql);
	   return ls;
	 }
 
 	public PurchaseOrderApprove getPurchaseOrderApproveByid(Long id)throws Exception{
	   String sql="from PurchaseOrderApprove where id="+id+"";
	   return (PurchaseOrderApprove)EntityManager.find(sql);
	 }

 
 public void delPurchaseOrderApproveByPoID(String poid)throws Exception{
   
   String sql="delete from purchase_order_approve where poid='"+poid+"'";
   EntityManager.updateOrdelete(sql);
   
 }
 

 public void cancelApprove(Integer approve,Integer actid,String poid,Long userid)throws Exception{
  
  String sql = "update purchase_order_approve set approve="+approve+" where poid='"+poid+"' and actid="+actid+" and approveid="+userid;
  EntityManager.updateOrdelete(sql);
  
 }

 
 public void addPurchaseOrderApprove(PurchaseOrderApprove approve)throws Exception{
   
   EntityManager.save(approve);
   
 }
 
 
 public void updPurchaseOrderApprove(PurchaseOrderApprove pba)throws Exception{
	   
	   EntityManager.update(pba);
	   
	 }
 

 public void updOperateByNextOrder(String poid, int nextorder)throws Exception{
	 
	 String sql = "update purchase_order_approve set operate=1 where poid='"+poid+"' and approveorder="+nextorder;
	 EntityManager.updateOrdelete(sql);
	 
 }
 
 public void updApproveContent(String approvecontent,int approve,String approvedate,Long id)throws Exception{
	 
	 String sql = "update purchase_order_approve set operate=0, approvecontent='"+approvecontent+"', approve="+approve+",approvedate='"+approvedate+"' where id="+id;
	 EntityManager.updateOrdelete(sql);
	 
	}

 
public void addApproveContent(String approvecontent,int approve,String approvedate,String poid,Integer actid,Long userid)throws Exception{
 
 String sql = "update purchase_order_approve set approvecontent='"+approvecontent+"', approve="+approve+",approvedate='"+approvedate+"' where poid='"+poid+"' and actid="+actid+" and approveid="+userid;
 EntityManager.updateOrdelete(sql);
 
}


}
