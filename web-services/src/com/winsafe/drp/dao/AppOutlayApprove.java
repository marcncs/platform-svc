package com.winsafe.drp.dao;

import java.util.List;

import com.winsafe.drp.entity.EntityManager;


public class AppOutlayApprove {

  public List getOutlayApprove(String oid)throws Exception{
    List ls = null;
    String sql="select a.id,a.oid,a.approveid,a.actid,a.approvecontent,a.approve,a.approvedate from OutlayApprove as a where a.oid='"+oid+"'";
    ls = EntityManager.getAllByHql(sql);
    return ls;
 }

 
public void addApproveContent(String approvecontent,int approve,String approvedate,String oid,Integer actid,Long userid)throws Exception{
 
 String sql = "update outlay_approve set approvecontent='"+approvecontent+"', approve="+approve+",approvedate='"+approvedate+"' where oid='"+oid+"' and actid="+actid+" and approveid="+userid;
 EntityManager.updateOrdelete(sql);
 
}



public void delOutlayApproveByOID(String oid)throws Exception{
  
  String sql="delete from outlay_approve where oid='"+oid+"'";
  EntityManager.updateOrdelete(sql);
  
}


public void addOutlayApprove(Object approve)throws Exception{
  
  EntityManager.save(approve);
  
}


public void cancelApprove(Integer approve,Integer actid,String oid,Long userid)throws Exception{
 
 String sql = "update outlay_approve set approve="+approve+" where oid='"+oid+"' and actid="+actid+" and approveid="+userid;
 EntityManager.updateOrdelete(sql);
 
}

}
