package com.winsafe.drp.dao;

import java.util.List;

import com.winsafe.drp.entity.EntityManager;

public class AppWorkReportExecute {

  
  public void addWorkReportExecute(Object execute)throws Exception{
      EntityManager.save(execute);
  }

  
  public List getWorkReportExecute(Integer id)throws Exception{
    List wr = null;
    String sql = "select wre.approve,wre.approveid,wre.approvecontent from WorkReportExecute as wre where wre.reportid="+id;
    wr = EntityManager.getAllByHql(sql);
    return wr;
  }

  
  public void addApproveContent(String approvecontent,int approve,String approvedate,Integer reportid,Integer userid)throws Exception{
    
    String sql = "update work_report_execute set approvecontent='"+approvecontent+"', approve="+approve+",approvedate='"+approvedate+"' where reportid="+reportid+" and approveid="+userid;
    EntityManager.updateOrdelete(sql);
    
  }

  
  public void delWorkReportExecuteByReportID(Integer reportid)throws Exception{
    
    String sql="delete from work_report_execute where reportid="+reportid;
    EntityManager.updateOrdelete(sql);
    
  }



}
