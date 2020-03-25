package com.winsafe.drp.dao;

import java.util.List;

import com.winsafe.drp.entity.EntityManager;

public class AppQualityPassDetail {

  
  public void addQualityPassDetail(Object pbd) throws Exception {
    
    EntityManager.save(pbd);
    
  }

  
  public List getQualityPassDetailByPbID(String pbid) throws Exception {
    List pbd = null;
    String sql = "select qpd.id,qpd.qpid,qpd.productid,qpd.productname,qpd.specmode,qpd.unitid,qpd.unitprice,qpd.quantity,qpd.subsum from QualityPassDetail as qpd where qpd.qpid='" +
        pbid+"'";
    pbd = EntityManager.getAllByHql(sql);
    return pbd;
  }


  public void delQualityPassDetailByPbID(String qpid) throws Exception {
    
    String sql = "delete from quality_pass_detail where qpid='" + qpid+"'";
    EntityManager.updateOrdelete(sql);
    
  }
  


}
