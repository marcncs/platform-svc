package com.winsafe.drp.dao;

import java.util.List;

import com.winsafe.drp.entity.EntityManager;

public class AppServiceDetail {

  public void addServiceDetail(Object sod)throws Exception{
    
    EntityManager.save(sod);
    
  }

  public List getServiceDetailBySAID(Integer said)throws Exception{
    List sl = null;
    String sql="select d.id,d.said,d.productid,d.productname,d.specmode,d.unitid,d.unitprice,d.quantity,d.subsum from ServiceDetail as d where d.said="+said+" ";
    sl = EntityManager.getAllByHql(sql);
    return sl;
  }
  
  public List getServiceDetailObjectBySAID(String sbid)throws Exception{
	    List sl = null;
	    String sql=" from ServiceDetail as d where d.sbid='"+sbid+"'";
	    sl = EntityManager.getAllByHql(sql);
	    return sl;
	  }
  

  public void delServiceBySAID(Integer said)throws Exception{
    
    String sql="delete from service_detail where said="+said+" ";
    EntityManager.updateOrdelete(sql);
    
  }
  
	

}
