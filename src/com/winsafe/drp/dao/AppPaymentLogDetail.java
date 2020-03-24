package com.winsafe.drp.dao;

import java.util.List;

import com.winsafe.drp.entity.EntityManager;


public class AppPaymentLogDetail {

  public void addPaymentLogDetail(Object pid)throws Exception{
    
    EntityManager.save(pid);
    
  }

  public List getPaymentLogDetailByPLID(String plid)throws Exception{
    String sql="from PaymentLogDetail as d where d.plid='"+plid+"'";
    List pid = EntityManager.getAllByHql(sql);
    return pid;
  }
  

  public void delPaymentLogDetailByPLID(String plid)throws Exception{
    
    String sql="delete from payment_log_detail where plid='"+plid+"'";
    EntityManager.updateOrdelete(sql);
    
    }
  



}
