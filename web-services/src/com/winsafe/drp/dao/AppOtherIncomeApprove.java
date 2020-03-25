package com.winsafe.drp.dao;

import java.util.List;

import com.winsafe.drp.entity.EntityManager;

public class AppOtherIncomeApprove {
	 
	 public List getOtherIncomeApprove(String oiid)throws Exception{
	   List ls = null;
	   String sql="select pia.id,pia.oiid,pia.approveid,pia.approvecontent,pia.actid,pia.approve,pia.approvedate from OtherIncomeApprove as pia where pia.oiid='"+oiid+"'";
	   ls = EntityManager.getAllByHql(sql);
	   return ls;
	 }
	 
	 
	 public void delOtherIncomeApproveByBillID(String oiid)throws Exception{
	   
	   String sql="delete from other_income_approve where oiid='"+oiid+"'";
	   EntityManager.updateOrdelete(sql);
	   
	 }
	 
	 
	 public void addOtherIncomeApprove(Object approve)throws Exception{
		 
	   EntityManager.save(approve);
	   
	 }
	 
	 
	 public void addApproveContent(String approvecontent,int approve,String approvedate,String oiid,Integer actid,Long userid)throws Exception{
	  
	  String sql = "update other_income_approve set approvecontent='"+approvecontent+"', approve="+approve+",approvedate='"+approvedate+"' where oiid='"+oiid+"' and actid="+actid+" and approveid="+userid;
	  EntityManager.updateOrdelete(sql);
	  
	 }
	 

	 public void cancelApprove(Integer approve,Integer actid,String oiid,Long userid)throws Exception{
	  
	  String sql = "update other_income_approve set approve="+approve+" where oiid='"+oiid+"' and actid="+actid+" and approveid="+userid;
	  EntityManager.updateOrdelete(sql);
	  
	 }
}
