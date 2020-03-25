package com.winsafe.drp.dao;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.pager.SimplePageInfo;
import com.winsafe.hbm.util.pager2.PageQuery;

public class AppDoc {

    
    public List getDoc(int pagesize, String pWhereClause,
                           SimplePageInfo pPgInfo) throws Exception {
      List pls = null;
      int targetPage = pPgInfo.getCurrentPageNo();
      String sql = "select pb.id,pb.sortid,pb.realpathname,pb.describe,pb.makedate,pb.makeid from Doc as pb " +
          pWhereClause + " order by pb.id desc";
      pls = EntityManager.getAllByHql(sql,targetPage, pagesize);
      return pls;
  }
    
    public List searchDoc(HttpServletRequest request, int pagesize,
			String pWhereClause) throws Exception {
    	String hql = "select pb.id,pb.sortid,pb.realpathname,pb.describe,pb.makedate,pb.makeid from Doc as pb " +
        pWhereClause + " order by pb.id desc";
		return PageQuery.hbmQuery(request, hql, pagesize);
	}

  
  public void addDoc(Object Doc)throws Exception{
    
    EntityManager.save(Doc);
    
  }
  
  
  public void delDoc(Integer id)throws Exception{
	  
	  String sql="delete from Doc where id="+id;
	  EntityManager.updateOrdelete(sql);
	  
  }
  
  
  public void delDocBySort(Integer sortid) throws Exception{
	  String sql="delete from Doc where sortid="+sortid;
	  EntityManager.updateOrdelete(sql);
  }

  
  public Doc getDocByID(Integer id)throws Exception{
    Doc pb = null;
    String sql = " from Doc as pb where pb.id="+id;
    pb = (Doc)EntityManager.find(sql);
    return pb;
  }
  
  public int isCount(Integer sortid)throws Exception{
	  String hql = "select count(*) from Doc where sortid = "+sortid;
	  return EntityManager.getRecordCountQuery(hql);
  }

  
  public void updDoc(Integer id,String name,Integer sex,String phone,String mobile,String email,String qq,String msn,String birthday,String addr,String remark,Integer sortid,Integer userid)throws Exception{
    
    String sql = "update Doc set name='"+name+"',sex="+sex+",phone='"+phone+"',mobile='"+mobile+"',email='"+email+"',qq='"+qq+"',msn='"+msn+"',birthday='"+birthday+"',addr='"+addr+"',remark='"+remark+"',sortid="+sortid+" where id="+id+" and userid="+userid;
    EntityManager.updateOrdelete(sql);
    
  }

}
