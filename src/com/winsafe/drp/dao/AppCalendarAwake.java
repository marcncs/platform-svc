package com.winsafe.drp.dao;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.pager.SimplePageInfo;
import com.winsafe.hbm.util.pager2.PageQuery;


public class AppCalendarAwake {

  
  public List getCurDayAwake(String curDate,Integer userid)throws Exception{
    List cls = null;
    String sql = " from CalendarAwake where awakedatetime>='"+curDate+" 00:00:00' and awakedatetime<='"+curDate+" 23:59:59' and userid="+userid+" and isdel=0 ";
    cls = EntityManager.getAllByHql(sql);
    return cls;
  }
  
  public List getAllAwake(Integer userid)throws Exception{
	    List cls = null;
	    String sql = " from CalendarAwake where userid="+userid+" and isdel=0 ";
	    cls = EntityManager.getAllByHql(sql);
	    return cls;
	  }

	  public List getAwake(int pagesize, String pWhereClause,
	          SimplePageInfo pPgInfo)throws Exception{
	List tpls = null;
	int targetPage = pPgInfo.getCurrentPageNo();
	String sql = "select ca.id,ca.awakecontent,ca.awakedatetime,ca.awakemodel,ca.isawake,ca.isdel,ca.userid from CalendarAwake as ca " +
	pWhereClause+" order by ca.awakedatetime desc ";

	tpls = EntityManager.getAllByHql(sql,targetPage,pagesize);
	return tpls;
	}
	  
	  public List searchAwake(HttpServletRequest request, int pagesize, String pWhereClause) throws Exception {
		  String sql = "select ca.id,ca.awakecontent,ca.awakedatetime,ca.awakemodel,ca.isawake,ca.isdel,ca.userid from CalendarAwake as ca " +
			pWhereClause+" order by ca.awakedatetime desc ";
			return PageQuery.hbmQuery(request, sql, pagesize);
		}
  
  
  public CalendarAwake getCalendar(Integer id,Integer userid)throws Exception{
    CalendarAwake ca = null;
    String sql = " from CalendarAwake as ca where ca.id="+id+" and ca.userid="+userid;
    ca = (CalendarAwake)EntityManager.find(sql);
    return ca;
  }

  
  public void addNewAwake(CalendarAwake calendarawake) throws Exception{
    
      EntityManager.save(calendarawake);

  }

  
  public void updAwake(CalendarAwake ca)throws Exception{

      EntityManager.update(ca);

  }

  
  public void delAwake(String id,Integer userid)throws Exception{
    
    String sql="delete from calendar_awake where id="+id+" and userid="+userid;
    EntityManager.updateOrdelete(sql);

  }

  
  public List getAwake(String curDate,Integer userid) throws Exception {
    List cls = null;
    String sql = "select ca.id,ca.awakecontent,ca.userid from CalendarAwake as ca where ca.awakedatetime<'"+curDate+"' and ca.isawake=0 and ca.isdel=0 and ca.userid="+userid;
    cls = EntityManager.getAllByHql(sql);
    return cls;
  }

  
  public CalendarAwake getAwakeByID(String id) throws Exception{
    CalendarAwake ca = null;
    String sql = " from CalendarAwake where id="+id;
    ca = (CalendarAwake)EntityManager.find(sql);
    return ca;
  }

  
  public void affrieGetAwake(Integer id) throws Exception{
    String sql =" update calendar_awake set isawake = 1 where id ="+id; // and userid=+userid;
    EntityManager.updateOrdelete(sql);
  }

}
