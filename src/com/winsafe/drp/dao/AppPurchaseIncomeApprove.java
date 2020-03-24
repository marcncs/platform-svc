package com.winsafe.drp.dao;

import java.util.List;

import com.winsafe.drp.entity.EntityManager;


public class AppPurchaseIncomeApprove {

  public List getPurchaseIncomeApprove(String piid)throws Exception{
    List ls = null;
    String sql="select pia.id,pia.piid,pia.approveid,pia.approvecontent,pia.actid,pia.approve,pia.approvedate from PurchaseIncomeApprove as pia where piid='"+piid+"'";
    ls = EntityManager.getAllByHql(sql);
    return ls;
 }

 
public void addApproveContent(String approvecontent,int approve,String approvedate,String piid,Integer actid,Long userid)throws Exception{
 
 String sql = "update purchase_income_approve set approvecontent='"+approvecontent+"', approve="+approve+",approvedate='"+approvedate+"' where piid='"+piid+"' and actid="+actid+" and approveid="+userid;
 EntityManager.updateOrdelete(sql);
 
}


public void cancelApprove(Integer approve,Integer actid,String piid,Long userid)throws Exception{
 
 String sql = "update purchase_income_approve set approve="+approve+" where piid='"+piid+"' and actid="+actid+" and approveid="+userid;
 EntityManager.updateOrdelete(sql);
 
}



public void delPurchaseIncomeApproveByPiID(String piid)throws Exception{
  
  String sql="delete from purchase_income_approve where piid='"+piid+"'";
  EntityManager.updateOrdelete(sql);
  
}


public void addPurchaseIncomeApprove(Object approve)throws Exception{
  
  EntityManager.save(approve);
  
}

}
