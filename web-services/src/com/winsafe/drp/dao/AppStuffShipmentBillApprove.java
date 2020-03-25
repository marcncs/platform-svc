package com.winsafe.drp.dao;

import java.util.List;

import com.winsafe.drp.entity.EntityManager;

public class AppStuffShipmentBillApprove{

 
 public List getStuffShipmentBillApprove(String ssid)throws Exception{
   List ls = null;
   String sql="select sba.id,sba.ssid,sba.approveid,sba.approvecontent,sba.approve,sba.actid,sba.approvedate from StuffShipmentBillApprove as sba where sba.ssid='"+ssid+"'";
   ls = EntityManager.getAllByHql(sql);
   return ls;
 }

 
 public void delStuffShipmentBillApproveBySBID(String ssid)throws Exception{
   
   String sql="delete from stuff_shipment_bill_approve where ssid='"+ssid+"'";
   EntityManager.updateOrdelete(sql);
   
 }

 
 public void addStuffShipmentBillApprove(Object approve)throws Exception{
	 
   EntityManager.save(approve);
   
 }

 
public void addApproveContent(String approvecontent,int approve,String approvedate,String ssid,Integer actid,Long userid)throws Exception{
 
 String sql = "update stuff_shipment_bill_approve set approvecontent='"+approvecontent+"', approve="+approve+",approvedate='"+approvedate+"' where ssid='"+ssid+"' and actid="+actid+" and approveid="+userid;
 EntityManager.updateOrdelete(sql);
 
}


public void cancelApprove(Integer approve,Integer actid,String ssid,Long userid)throws Exception{
 
 String sql = "update stuff_shipment_bill_approve set approve="+approve+" where ssid='"+ssid+"' and actid="+actid+" and approveid="+userid;
 EntityManager.updateOrdelete(sql);
 
}


}
