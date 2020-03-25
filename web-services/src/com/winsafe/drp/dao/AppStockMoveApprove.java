package com.winsafe.drp.dao;

import java.util.List;

import com.winsafe.drp.entity.EntityManager;

public class AppStockMoveApprove {
	 
	 public List getStockMoveApprove(String smid)throws Exception{
	   List ls = null;
	   String sql="select pia.id,pia.smid,pia.approveid,pia.approvecontent,pia.approve,pia.approvedate,pia.actid from StockMoveApprove as pia where pia.smid='"+smid+"'";
	   ls = EntityManager.getAllByHql(sql);
	   return ls;
	 }
	 
	 
	 public void delStockMoveApproveByBillID(String smid)throws Exception{
	   
	   String sql="delete from stock_move_approve where smid='"+smid+"'";
	   EntityManager.updateOrdelete(sql);
	   
	 }
	 
	 
	 public void addStockMoveApprove(Object approve)throws Exception{
		 
	   EntityManager.save(approve);
	   
	 }
	 
	 
	 public void addApproveContent(String approvecontent,int approve,String approvedate,String smid,Integer actid,Long userid)throws Exception{
	  
	  String sql = "update stock_move_approve set approvecontent='"+approvecontent+"', approve="+approve+",approvedate='"+approvedate+"' where smid='"+smid+"' and actid="+actid+" and approveid="+userid;
	  EntityManager.updateOrdelete(sql);
	  
	 }
	 

	 public void cancelApprove(Integer approve,Integer actid,String smid,Long userid)throws Exception{
	  
	  String sql = "update stock_move_approve set approve="+approve+" where smid='"+smid+"' and actid="+actid+" and approveid="+userid;
	  EntityManager.updateOrdelete(sql);
	  
	 }
}
