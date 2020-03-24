package com.winsafe.drp.dao;

import java.util.List;

import com.winsafe.drp.entity.EntityManager;


public class AppDemandPriceDetail {

  public void addDemandPriceDetail(Object dpd)throws Exception{
    
    EntityManager.save(dpd);
    
  }

  public List getDemandPriceDetailByDpID(Long dpid)throws Exception{
    List dpd = null;
    String sql = "select d.id,d.dpid,d.productid,d.productname,d.specmode,d.unitid,d.unitprice,d.quantity,d.subsum,d.discount,d.taxrate from DemandPriceDetail as d where d.dpid="+dpid;
    dpd = EntityManager.getAllByHql(sql);
    return dpd;
  }

  public void delDemandPriceDetailByDpID(Long dpid)throws Exception{
    
    String sql="delete from demand_price_detail where dpid="+dpid;
    EntityManager.updateOrdelete(sql);
    
  }


}
