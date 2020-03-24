package com.winsafe.drp.dao;

import java.util.List;

import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.pager.SimplePageInfo;

public class AppApproveFlowLog{

	
	 public List getApproveFlowLog(String billno)throws Exception{
	   List ls = null;
	   String sql="from ApproveFlowLog where billno='"+billno+"' order by approveorder";
	   ls = EntityManager.getAllByHql(sql);
	   return ls;
	 }
 
 	
 	public List getApproveFlowLogByPbid(Integer userid, String afid)throws Exception{
	   List ls = null;
	   String sql="from ApproveFlowLog where operate=1 and approveid="+userid+" and afid='"+afid+"'";
	   ls = EntityManager.getAllByHql(sql);
	   return ls;
	 }
 
 	public ApproveFlowLog getApproveFlowLogByid(Integer id)throws Exception{
	   String sql="from ApproveFlowLog where id="+id+"";
	   return (ApproveFlowLog)EntityManager.find(sql);
	 }
 	
 	
 	public ApproveFlowLog getApproveFlowLogByUserid(Integer userid, String billno)throws Exception{
 	   String sql="from ApproveFlowLog where operate=1 and approve=0 and billno='"+billno+"' and approveid="+userid+"";
 	   return (ApproveFlowLog)EntityManager.find(sql);
 	 }

	 
	 public void delApproveFlowLogByBillID(String billno)throws Exception{
	   
	   String sql="delete from approve_flow_log where billno='"+billno+"'";
	   EntityManager.updateOrdelete(sql);
	   
	 }


	 
	 public void addApproveFlowLog(ApproveFlowLog approve)throws Exception{
	   
	   EntityManager.save(approve);
	   
	 }
 
	 
	 public void updApproveFlowLog(ApproveFlowLog pba)throws Exception{
		   
		   EntityManager.update(pba);
		   
		 }
 
	
	 public void updOperateByNextOrder(String billno, int nextorder)throws Exception{
		 
		 String sql = "update approve_flow_log set operate=1 where billno='"+billno+"' and approveorder="+nextorder;
		 EntityManager.updateOrdelete(sql);
	 
 }
 
 public void updApproveContent(ApproveFlowLog afl)throws Exception{
	 
	 String sql = "update approve_flow_log set operate=0, approvecontent='"+afl.getApprovecontent()+"', approve="+afl.getApprove()+",approvedate='"+afl.getApprovedate()+"' where id="+afl.getId();
	 EntityManager.updateOrdelete(sql);
	 
	}
 
 	
	 public static boolean judgeApprove(String billno)throws Exception{
		  boolean flag=false;
		  String sql="from ApproveFlowLog where approve!=1 and billno='"+billno+"'";
		  Object o=EntityManager.find(sql);
		  if(o==null){
			  flag=true;
		  }
		  return flag;
	 }
	 
	 public List waitApproveFlowLog(int pagesize, String pWhereClause,
				SimplePageInfo pPgInfo) throws Exception {
			List wpa = null;
			int targetPage = pPgInfo.getCurrentPageNo();
			String sql = " from ApproveFlowLog  "
					+ pWhereClause
					+ " order by id desc";
			wpa = EntityManager.getAllByHql(sql, targetPage, pagesize);
			return wpa;
		}




}
