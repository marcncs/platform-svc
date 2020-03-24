package com.winsafe.drp.dao;

import java.util.List;

import com.winsafe.drp.entity.EntityManager;

public class AppSaleInvoiceDetail {

  public void addSaleInvoiceDetail(SaleInvoiceDetail sid)throws Exception{
    
    EntityManager.save(sid);
    
  }
  
  public List getSaleInvoiceDetailObjectBySIID(Integer siid)throws Exception{
	    String sql=" from SaleInvoiceDetail as d where d.siid="+siid;
	    return EntityManager.getAllByHql(sql);
	  }

  public void delSaleInvoiceBySIID(Integer siid)throws Exception{
    
    String sql="delete from sale_invoice_detail where siid='"+siid+"'";
    EntityManager.updateOrdelete(sql);
    
  }


}
