package com.winsafe.drp.dao;

import java.util.List;

import com.winsafe.drp.entity.EntityManager;

public class AppOtherShipmentBillApprove{

 
 public List getOtherShipmentBillApprove(String osid)throws Exception{
   List ls = null;
   String sql="select sba.id,sba.osid,sba.approveid,sba.approvecontent,sba.actid,sba.approve,sba.approvedate from OtherShipmentBillApprove as sba where sba.osid='"+osid+"'";
   ls = EntityManager.getAllByHql(sql);
   return ls;
 }

 
 public void delOtherShipmentBillApproveBySBID(String osid)throws Exception{
   
   String sql="delete from other_shipment_bill_approve where osid='"+osid+"'";
   EntityManager.updateOrdelete(sql);
   
 }

 
 public void addOtherShipmentBillApprove(Object approve)throws Exception{
	 
   EntityManager.save(approve);
   
 }

 
public void addApproveContent(String approvecontent,int approve,String approvedate,String osid,Integer actid,Long userid)throws Exception{
 
 String sql = "update other_shipment_bill_approve set approvecontent='"+approvecontent+"', approve="+approve+",approvedate='"+approvedate+"' where osid='"+osid+"' and actid="+actid+" and approveid="+userid;
 EntityManager.updateOrdelete(sql);
 
}


public void cancelApprove(Integer approve,Integer actid,String osid,Long userid)throws Exception{
 
 String sql = "update other_shipment_bill_approve set approve="+approve+" where osid='"+osid+"' and actid="+actid+" and approveid="+userid;
 EntityManager.updateOrdelete(sql);
 
}


}
