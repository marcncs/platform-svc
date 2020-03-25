package com.winsafe.drp.dao;

import java.util.List;

import com.winsafe.drp.entity.EntityManager;

public class AppShipmentBillApprove{

 
 public List getShipmentBillApprove(String sbid)throws Exception{
   List ls = null;
   String sql="select sba.id,sba.sbid,sba.approveid,sba.actid,sba.approvecontent,sba.approve,sba.approvedate from ShipmentBillApprove as sba where sba.sbid='"+sbid+"'";
   ls = EntityManager.getAllByHql(sql);
   return ls;
 }

 
 public void delShipmentBillApproveBySBID(String sbid)throws Exception{
   
   String sql="delete from shipment_bill_approve where sbid='"+sbid+"'";
   EntityManager.updateOrdelete(sql);
   
 }

 
 public void addShipmentBillApprove(Object approve)throws Exception{
	 
   EntityManager.save(approve);
   
 }

 
public void addApproveContent(String approvecontent,int approve,String approvedate,String sbid,Long userid)throws Exception{
 
 String sql = "update shipment_bill_approve set approvecontent='"+approvecontent+"', approve="+approve+",approvedate='"+approvedate+"' where sbid='"+sbid+"' and approveid="+userid;
 EntityManager.updateOrdelete(sql);
 
}


}
