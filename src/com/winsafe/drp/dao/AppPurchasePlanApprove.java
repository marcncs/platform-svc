package com.winsafe.drp.dao;

import java.util.List;

import com.winsafe.drp.entity.EntityManager;

public class AppPurchasePlanApprove{

 
 public List getPurchasePlanApprove(String ppid)throws Exception{
   List ls = null;
   String sql="select ppa.id,ppa.ppid,ppa.approveid,ppa.actid,ppa.approvecontent,ppa.approve,ppa.approvedate from PurchasePlanApprove as ppa where ppa.ppid='"+ppid+"'";
   ls = EntityManager.getAllByHql(sql);
   return ls;
 }

 
public void addApproveContent(PurchasePlan pp)throws Exception{
  
  EntityManager.update(pp);
  
}


public void cancelApprove(Integer approve,Integer actid,String ppid,Long userid)throws Exception{
 
 String sql = "update purchase_plan_approve set approve="+approve+" where ppid='"+ppid+"' and actid="+actid+" and approveid="+userid;
 EntityManager.updateOrdelete(sql);
 
}


 
 public void delPurchasePlanApproveByPPID(String ppid)throws Exception{
   
   String sql="delete from purchase_plan_approve where ppid='"+ppid+"'";
   EntityManager.updateOrdelete(sql);
   
 }

 
 public void addPurchasePlanApprove(Object approve)throws Exception{
   
   EntityManager.save(approve);
   
 }

}
