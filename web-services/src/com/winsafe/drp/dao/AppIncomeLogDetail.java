package com.winsafe.drp.dao;

import java.util.List;

import com.winsafe.drp.entity.EntityManager;

public class AppIncomeLogDetail {

    
    public List getIncomeLogDetail(String ilid)throws Exception{
      String sql="from IncomeLogDetail as ila where ila.ilid='"+ilid+"'";
      List ls = EntityManager.getAllByHql(sql);
      return ls;
    }

    
    public void delIncomeLogDetailByIlID(String ilid)throws Exception{
      String sql="delete from income_log_detail where ilid='"+ilid+"'";
      EntityManager.updateOrdelete(sql);
    }
    
    
    public void delIncomeLogDetailByBillNo(String billno)throws Exception{
      String sql="delete from income_log_detail where billno='"+billno+"'";
      EntityManager.updateOrdelete(sql);
    }

    
    public void addIncomeLogDetail(IncomeLogDetail approve)throws Exception{
      EntityManager.save(approve);
    }

}
