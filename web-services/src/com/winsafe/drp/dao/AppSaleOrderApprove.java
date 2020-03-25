package com.winsafe.drp.dao;

import java.util.List;

import com.winsafe.drp.entity.EntityManager;


public class AppSaleOrderApprove {

  public List getSaleOrderApprove(String saleid)throws Exception{
    List ls = null;
    String sql="select a.id,a.soid,a.approveid,a.actid,a.approvecontent,a.approve,a.approvedate from SaleOrderApprove as a where a.soid='"+saleid+"'";
    ls = EntityManager.getAllByHql(sql);
    return ls;
 }

 
public void addApproveContent(String approvecontent,int approve,String approvedate,String soid,Integer actid,Long userid)throws Exception{
 
 String sql = "update sale_order_approve set approvecontent='"+approvecontent+"', approve="+approve+",approvedate='"+approvedate+"' where soid='"+soid+"' and actid="+actid+" and approveid="+userid;
 EntityManager.updateOrdelete(sql);
 
}


public void cancelApprove(Integer approve,Integer actid,String soid,Long userid)throws Exception{
 
 String sql = "update sale_order_approve set approve="+approve+" where soid='"+soid+"' and actid="+actid+" and approveid="+userid;
 EntityManager.updateOrdelete(sql);
 
}



public void delSaleOrderApproveBySaleID(String saleid)throws Exception{
  
  String sql="delete from sale_order_approve where soid='"+saleid+"'";
  //System.out.println("-----------------"+sql);
  EntityManager.updateOrdelete(sql);
  
}


public void addSaleOrderApprove(Object approve)throws Exception{
  
  EntityManager.save(approve);
  
}

}
