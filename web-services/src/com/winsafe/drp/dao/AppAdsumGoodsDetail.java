package com.winsafe.drp.dao;

import java.util.List;

import com.winsafe.drp.entity.EntityManager;

public class AppAdsumGoodsDetail {

  
  public void addAdsumGoodsDetail(Object agd) throws Exception {
    
    EntityManager.save(agd);
    
  }

  
  public List getAdsumGoodsDetailByPbID(String pbid) throws Exception {
    List pbd = null;
    String sql = "select agd.id,agd.agid,agd.productid,agd.productname,agd.specmode,agd.unitid,agd.unitprice,agd.quantity,agd.subsum from AdsumGoodsDetail as agd where agd.agid='" +
        pbid+"'";
    pbd = EntityManager.getAllByHql(sql);
    return pbd;
  }


  public void delAdsumGoodsDetailByPbID(String agid) throws Exception {
    
    String sql = "delete from adsum_goods_detail where agid='" + agid+"'";
    EntityManager.updateOrdelete(sql);
    
  }
  

  public void updAdsumGoodsDetailChangeQuantity(String agid,String productid,Double changequantity)throws Exception{
	  
	  String sql="update adsum_goods_detail set changequantity=changequantity+"+changequantity+" where agid='"+agid+"' and productid='"+productid+"'";
	  EntityManager.updateOrdelete(sql);
	  
  }

}
