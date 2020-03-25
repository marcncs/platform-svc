package com.winsafe.drp.dao;

import java.util.List;

import com.winsafe.drp.entity.EntityManager;


public class AppPhoneBookSort {

  
  public List getPhoneBookSort(Integer userid)throws Exception{
    List ps = null;
    String sql=" from PhoneBookSort where userid="+userid;
    ps = EntityManager.getAllByHql(sql);
    return ps;
  }

  
  public PhoneBookSort getPhoneBookSortByID(Integer id)throws Exception{
    PhoneBookSort pbs = null;
    String sql =" from PhoneBookSort as pbs where pbs.id="+id;
    pbs = (PhoneBookSort)EntityManager.find(sql);
    return pbs;
  }
  public boolean getPhoneBookSortBySortName(String sortname,Integer usesid)throws Exception{
	    PhoneBookSort pbs = null;
	    String sql =" from PhoneBookSort as pbs where pbs.userid="+usesid +" and pbs.sortname= '"+sortname+"'";;
	    pbs = (PhoneBookSort)EntityManager.find(sql);
	    return pbs!=null;
	  }

  
  public void addPhoneBookSort(Object phonebooksort)throws Exception {
    
    EntityManager.save(phonebooksort);
    
  }
  
  public void delPhoneBookSort(Integer id)throws Exception{
	  String sql = "delete from phone_book_sort where id = "+id;
	  EntityManager.updateOrdelete(sql);
  }

  
  public void updPhoneBookSort(Integer id,String sortname)throws Exception{
  
  String sql = "update phone_book_sort set SortName ='"+sortname+"' where ID="+id;
  EntityManager.updateOrdelete(sql);
  
  }

}
