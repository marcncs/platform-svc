package com.winsafe.drp.dao;

import java.util.List;

import com.winsafe.drp.entity.EntityManager;

public class AppIncomeLogApprove {

    
    public List getIncomeLogApprove(String ilid)throws Exception{
      List ls = null;
      String sql="select ila.id,ila.ilid,ila.approveid,ila.approvecontent,ila.approve,ila.approvedate,ila.actid from IncomeLogApprove as ila where ila.ilid='"+ilid+"'";
      ls = EntityManager.getAllByHql(sql);
      return ls;
    }

    
    public void delIncomeLogApproveByIlID(String ilid)throws Exception{
      
      String sql="delete from income_log_approve where ilid='"+ilid+"'";
      EntityManager.updateOrdelete(sql);
      
    }

    
    public void addIncomeLogApprove(Object approve)throws Exception{
    	
      EntityManager.save(approve);
      
    }

    
   public void addApproveContent(String approvecontent,int approve,String approvedate,String ilid,Integer actid,Long userid)throws Exception{
    
    String sql = "update income_log_approve set approvecontent='"+approvecontent+"', approve="+approve+",approvedate='"+approvedate+"' where ilid='"+ilid+"' and actid="+actid+" and approveid="+userid;
    EntityManager.updateOrdelete(sql);
    
}
   

	public void cancelApprove(Integer approve,Integer actid,String ilid,Long userid)throws Exception{
	 
	 String sql = "update income_log_approve set approve="+approve+" where ilid='"+ilid+"' and actid="+actid+" and approveid="+userid;
	 EntityManager.updateOrdelete(sql);
	 
	}
}
