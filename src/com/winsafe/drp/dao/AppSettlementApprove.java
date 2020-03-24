package com.winsafe.drp.dao;

import java.util.List;

import com.winsafe.drp.entity.EntityManager;

public class AppSettlementApprove{

 
 public List getSettlementApprove(Long sid)throws Exception{
   List ls = null;
   String sql="select sa.id,sa.sid,sa.approveid,sa.approvecontent,sa.approve,sa.approvedate,sa.actid from SettlementApprove as sa where sa.sid="+sid;
   ls = EntityManager.getAllByHql(sql);
   return ls;
 }

 
 public void delSettlementApproveBySID(Long sid)throws Exception{
   
   String sql="delete from settlement_approve where sid="+sid;
   EntityManager.updateOrdelete(sql);
   
 }

 
 public void addSettlementApprove(Object approve)throws Exception{
	 
   EntityManager.save(approve);
   
 }

 
public void addApproveContent(String approvecontent,int approve,String approvedate,Long sid,Integer actid,Long userid)throws Exception{
 
 String sql = "update settlement_approve set approvecontent='"+approvecontent+"', approve="+approve+",approvedate='"+approvedate+"' where sid="+sid+" and actid="+actid+" and approveid="+userid;
 EntityManager.updateOrdelete(sql);
 
}


public void cancelApprove(Integer approve,Integer actid,String sid,Long userid)throws Exception{
 
 String sql = "update settlement_approve set approve="+approve+" where sid='"+sid+"' and actid="+actid+" and approveid="+userid;
 EntityManager.updateOrdelete(sql);
 
}


}
