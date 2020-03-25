package com.winsafe.drp.dao;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.pager.SimplePageInfo;
import com.winsafe.hbm.util.pager2.PageQuery;

public class AppPhoneBook {

    
    public List getPhoneBook(int pagesize, String pWhereClause,
                           SimplePageInfo pPgInfo) throws Exception {
      List pls = null;
      int targetPage = pPgInfo.getCurrentPageNo();
      String sql = " from PhoneBook as pb " +
          pWhereClause + " order by pb.id desc";
      pls = EntityManager.getAllByHql(sql,targetPage, pagesize);
      return pls;
  }
    
    public List searchPhoneBook(HttpServletRequest request, int pagesize,
			String pWhereClause) throws Exception {
    	String hql = " from PhoneBook as pb " +
        pWhereClause + " order by pb.id desc";
		return PageQuery.hbmQuery(request, hql, pagesize);
	}

  
  public void addPhoneBook(PhoneBook phonebook)throws Exception{
    
    EntityManager.save(phonebook);
    
  }
  
  
  public void delPhoneBook(Integer id)throws Exception{
	  
	  String sql="delete from Phone_Book where id="+id;
	  EntityManager.updateOrdelete(sql);
	  
  }
  
  
  public void delPhoneBookBySortid(Integer sortid)throws Exception{
	  
	  String sql="delete from Phone_Book where sortid="+sortid;
	  EntityManager.updateOrdelete(sql);
	  
  }
  
  
  public int  isCount(Integer sortid)throws Exception{
	  
	  String hql = "select count(*) from Phone_Book where sortid="+sortid;
	  
	  return EntityManager.getRecordCountQuery(hql);
  }

  
  public PhoneBook getPhoneBookByID(Integer id)throws Exception{
    PhoneBook pb = null;
    String sql = " from PhoneBook as pb where pb.id="+id;
    pb = (PhoneBook)EntityManager.find(sql);
    return pb;
  }

  
  public void updPhoneBook(Integer id,String name,Integer sex,String phone,String mobile,String email,String qq,String msn,String birthday,String addr,String remark,Integer sortid,Integer userid)throws Exception{
    
    String sql = "update phone_book set name='"+name+"',sex="+sex+",phone='"+phone+"',mobile='"+mobile+"',email='"+email+"',qq='"+qq+"',msn='"+msn+"',birthday='"+birthday+"',addr='"+addr+"',remark='"+remark+"',sortid="+sortid+" where id="+id+" and userid="+userid;
    EntityManager.updateOrdelete(sql);
    
  }

}
