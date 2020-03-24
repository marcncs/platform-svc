package com.winsafe.drp.dao;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.pager.SimplePageInfo;
import com.winsafe.hbm.util.pager2.PageQuery;

public class AppWorkReport {

  
  public List getWorkReport(int pagesize, String pWhereClause,
                             SimplePageInfo pPgInfo)throws Exception{
    List wr = null;
    int targetPage = pPgInfo.getCurrentPageNo();
    String sql = "select wr.id,wr.reportcontent,wr.reportsort,wr.referdate,wr.isrefer,wr.approvestatus,wr.remark from WorkReport as wr "+pWhereClause+" order by wr.id desc ";
    wr = EntityManager.getAllByHql(sql,targetPage,pagesize);
    return wr;
  }
  
  public List searchWorkReport(HttpServletRequest request, int pagesize,
			String pWhereClause) throws Exception {
		String hql = "select wr.id,wr.reportsort,wr.reportcontent,wr.isrefer,wr.approvestatus,wr.makeorganid,wr.makeid,wr.makedate,wra.approve from WorkReport as wr ,WorkReportApprove as wra "
				+ pWhereClause + " order by wr.id desc ";
		return PageQuery.hbmQuery(request, hql, pagesize);
	}


 
 public void addNewWorkReport(WorkReport workreport)throws Exception{

   EntityManager.save(workreport);

 }
 
 public void updWorkReport(WorkReport wrp)throws Exception{

	    EntityManager.update(wrp);

	 }
 
 
 public void delWorkReport(Integer id)throws Exception{
	 
	 String sql="delete from Work_Report where id="+id;
	 EntityManager.updateOrdelete(sql);
	 
 }

 
 public WorkReport getWorkReportByID(Integer id)throws Exception{

   String sql = " from WorkReport as wr where wr.id="+id;
   return (WorkReport)EntityManager.find(sql);
 }

 
 public void updWorkReportByID(WorkReport wr,Integer userid)throws Exception{

   String sql = " update work_report set ReportSort="+wr.getReportsort()+", ReportContent='"+wr.getReportcontent()+"' where id ="+wr.getId()+" and CreateID="+userid;
    EntityManager.updateOrdelete(sql);

 }

 
 public void updIsRefer(Integer id) throws Exception {

   String sql = "update work_report set isrefer = 1 where id=" + id;
   EntityManager.updateOrdelete(sql);

 }

 
 public List waitApproveWorkReport(int pagesize, String pWhereClause,
                             SimplePageInfo pPgInfo)throws Exception{
   List wa = null;
   int targetPage = pPgInfo.getCurrentPageNo();
   String sql = "select wr.id,wr.createid,wr.reportcontent,wr.reportsort,wr.referdate,wr.isrefer,wra.approve,wr.remark,wra.approvedate from WorkReport as wr,WorkReportApprove as wra "+pWhereClause+" order by wra.approve,wr.referdate desc ";
   wa = EntityManager.getAllByHql(sql,targetPage,pagesize);
   return wa;
 }

 
 public void updIsApprove(String id,int isapprove)throws Exception{
   String sql = "update work_report set approvestatus="+isapprove+" where id ='"+id+"'";
   EntityManager.updateOrdelete(sql);
 }


}
