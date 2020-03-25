package com.winsafe.drp.dao;

import java.util.List;

import com.winsafe.drp.entity.EntityManager;

public class AppStockCheckApprove {
	 
	 public List getStockCheckApprove(String scid)throws Exception{
	   List ls = null;
	   String sql="select sca.id,sca.scid,sca.approveid,sca.approvecontent,sca.approve,sca.approvedate,sca.actid from StockCheckApprove as sca where sca.scid='"+scid+"'";
	   ls = EntityManager.getAllByHql(sql);
	   return ls;
	 }
	 
	 
	 public void delStockCheckApproveByBillID(String scid)throws Exception{
	   
	   String sql="delete from stock_check_approve where scid='"+scid+"'";
	   EntityManager.updateOrdelete(sql);
	   
	 }
	 
	 
	 public void addStockCheckApprove(Object approve)throws Exception{
		 
	   EntityManager.save(approve);
	   
	 }
	 
	 
	 public void addApproveContent(String approvecontent,int approve,String approvedate,String scid,Long userid)throws Exception{
	  
	  String sql = "update stock_check_approve set approvecontent='"+approvecontent+"', approve="+approve+",approvedate='"+approvedate+"' where scid='"+scid+"' and approveid="+userid;
	  EntityManager.updateOrdelete(sql);
	  
	 }
}
