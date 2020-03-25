package com.winsafe.drp.dao;

import java.util.List;

import com.winsafe.drp.entity.EntityManager;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class AppPaymentApprove {

    
    public List getPaymentApprove(Long plid)throws Exception{
      List ls = null;
      String sql="select pa.id,pa.plid,pa.approveid,pa.approvecontent,pa.approve,pa.approvedate from PaymentApprove as pa where pa.plid="+plid;
      ls = EntityManager.getAllByHql(sql);
      return ls;
    }

    
    public void delPaymentApproveByPlID(Long plid)throws Exception{
      
      String sql="delete from payment_approve where plid="+plid;
      EntityManager.updateOrdelete(sql);
      
    }

    
    public void addPaymentApprove(Object approve)throws Exception{
      EntityManager.save(approve);
    }

    
   public void addApproveContent(String approvecontent,int approve,String approvedate,Long plid,Long userid)throws Exception{
    
    String sql = "update payment_approve set approvecontent='"+approvecontent+"', approve="+approve+",approvedate='"+approvedate+"' where plid="+plid+" and approveid="+userid;
    EntityManager.updateOrdelete(sql);
    
}
}
