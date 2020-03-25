package com.winsafe.drp.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.pager2.PageQuery;

public class AppCallCenterEvent {

	public List getCallCenterEvent(HttpServletRequest request, int pagesize, String pWhereClause)throws Exception{
		 String hql=" from CallCenterEvent  "
         +pWhereClause+" order by eventdate desc";
		 return PageQuery.hbmQuery(request, hql, pagesize);
	}
	
	public void addCallCenterEvent(CallCenterEvent ca) throws Exception{		
		EntityManager.save(ca);		
	}
	
	
	
	
	//-------------------------用户呼叫查询设置------------------------
	public void addUserCallEvent(UserCallEvent uce) throws Exception{		
		EntityManager.save(uce);		
	}
	
	public void addUserCallEventNoExist(UserCallEvent u) throws Exception{
		String sql="insert into user_call_event(id,userid) select "+u.getId()+","+u.getUserid()+" where not exists (select id from user_call_event where userid="+u.getUserid()+" )";
		EntityManager.updateOrdelete(sql);
	}
	
	public void DelUserCallEvent(int userid)throws Exception{		
		String sql="delete from user_call_event where userid ="+userid;
		EntityManager.updateOrdelete(sql);		
	}
	
	public void DelUserCallEventByMuid(int monitoruserid)throws Exception{		
		String sql="delete from user_call_event where monitoruserid="+monitoruserid;
		EntityManager.updateOrdelete(sql);		
	}
	
	public List getUserCallEventByMuid(int muid)throws Exception{		
		String sql="select userid from UserCallEvent where monitoruserid="+muid;
		return EntityManager.getAllByHql(sql);
	}
	
	public List getUserCallEvent(int monitoruserid){
		List als = new ArrayList();
		ResultSet rs = null;
		String sql = "select u.userid, u.realname,uce.id from users u left join  "+
		"(select uce.id, uce.userid from user_call_event uce where uce.monitoruserid="+monitoruserid+") uce "+
		"on u.userid=uce.userid where u.status=1 ";
		try {
			rs = EntityManager.query2(sql);
			while ( rs.next() ){
				UserCallEventForm of = new UserCallEventForm();
				of.setUserid(rs.getLong(1));
				of.setUseridname(rs.getString(2));				 
				of.setId(rs.getLong(3));
				als.add(of);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally{
			if ( rs != null ){
				try {
					rs.close();
				} catch (SQLException e) {					
					e.printStackTrace();
				}
			}
		}		
	 	
	 	return als;
	}
}
