package com.winsafe.drp.dao;

import java.util.List;

import com.winsafe.drp.entity.EntityManager;

public class AppStockAdjustDetail {

    public void addStockAdjustDetail(Object spb)throws Exception{
        
        EntityManager.save(spb);
        
    }
    
    
    public List getStockAdjustDetailBySmID(String said)throws Exception{
    	  List spb = null;
    	  String sql="select sad.id,sad.said,sad.productid,sad.productname,sad.specmode,sad.unitid,sad.batch,sad.unitprice,sad.operatesign,sad.quantity,sad.subsum from StockAdjustDetail as sad where sad.said='"+said+"'";
    	  spb = EntityManager.getAllByHql(sql);
    	  return spb;
    	}
    

    public void delStockAdjustDetailBySmID(String said)throws Exception{
      
      String sql="delete from stock_adjust_detail where said='"+said+"'";
      EntityManager.updateOrdelete(sql);
      
      }
}
