package com.winsafe.drp.dao;

import java.util.List;

import com.winsafe.drp.entity.EntityManager;

public class AppPaymentLogApprove {

    
    public List getPaymentLogApprove(String plid)throws Exception{
      List ls = null;
      String sql="select ila.id,ila.plid,ila.approveid,ila.approvecontent,ila.approve,ila.approvedate,ila.actid from PaymentLogApprove as ila where ila.plid='"+plid+"'";
      ls = EntityManager.getAllByHql(sql);
      return ls;
    }

    
    public void delPaymentLogApproveByIlID(String plid)throws Exception{
      
      String sql="delete from payment_log_approve where plid='"+plid+"'";
      EntityManager.updateOrdelete(sql);
      
    }

    
    public void addPaymentLogApprove(Object approve)throws Exception{
    	
      EntityManager.save(approve);
      
    }

    
   public void addApproveContent(String approvecontent,int approve,String approvedate,String plid,Integer actid,Long userid)throws Exception{
    
    String sql = "update payment_log_approve set approvecontent='"+approvecontent+"', approve="+approve+",approvedate='"+approvedate+"' where plid='"+plid+"' and actid="+actid+" and approveid="+userid;
    EntityManager.updateOrdelete(sql);
    
}
   

	public void cancelApprove(Integer approve,Integer actid,String plid,Long userid)throws Exception{
	 
	 String sql = "update payment_log_approve set approve="+approve+" where plid='"+plid+"' and actid="+actid+" and approveid="+userid;
	 EntityManager.updateOrdelete(sql);
	 
	}
}
