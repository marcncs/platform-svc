package com.winsafe.drp.dao;

import java.util.List;

import com.winsafe.drp.entity.EntityManager;

public class AppProductIncomeApprove {
	 
	 public List getProductIncomeApprove(String piid)throws Exception{
	   List ls = null;
	   String sql="select pia.id,pia.piid,pia.approveid,pia.approvecontent,pia.actid,pia.approve,pia.approvedate from ProductIncomeApprove as pia where pia.piid='"+piid+"'";
	   ls = EntityManager.getAllByHql(sql);
	   return ls;
	 }
	 
	 
	 public void delProductIncomeApproveByBillID(String piid)throws Exception{
	   
	   String sql="delete from product_income_approve where piid='"+piid+"'";
	   EntityManager.updateOrdelete(sql);
	   
	 }
	 
	 
	 public void addProductIncomeApprove(Object approve)throws Exception{
		 
	   EntityManager.save(approve);
	   
	 }
	 
	 
	 public void addApproveContent(String approvecontent,int approve,String approvedate,String piid,Integer actid,Long userid)throws Exception{
	  
	  String sql = "update product_income_approve set approvecontent='"+approvecontent+"', approve="+approve+",approvedate='"+approvedate+"' where piid='"+piid+"' and actid="+actid+" and approveid="+userid;
	  EntityManager.updateOrdelete(sql);
	  
	 }
	 

	 public void cancelApprove(Integer approve,Integer actid,String piid,Long userid)throws Exception{
	  
	  String sql = "update product_income_approve set approve="+approve+" where piid='"+piid+"' and actid="+actid+" and approveid="+userid;
	  EntityManager.updateOrdelete(sql);
	  
	 }
}
