package com.winsafe.drp.dao;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.pager2.PageQuery;

public class AppReckoning {
	
	public List getReckoning(HttpServletRequest request, int pagesize,
			String pWhereClause)throws Exception{
		
		 String sql=" from Reckoning as r "
         +pWhereClause+" order by r.makedate desc";
		 return PageQuery.hbmQuery(request, sql, pagesize);
	}
	

	
	public Double getReckoningSumByUID(Integer uid)throws Exception{
		String sql = "select sum(r.backsum) from Reckoning as r where r.uid="+uid+" and r.isaudit=1";
		return EntityManager.getdoubleSum(sql);
	}
	
	
	public void addReckoning(Object payable)throws Exception{
		
		EntityManager.save(payable);
		
	}
	
	
	public void delReckoning(String lid)throws Exception{
		
		String sql="delete from Reckoning where id='"+lid+"' and isaudit=0";
		EntityManager.updateOrdelete(sql);
		
	}
	
	public Reckoning getReckoningByID(String id)throws Exception{
		String sql="from Reckoning where id='"+id+"'";
		Reckoning w=(Reckoning)EntityManager.find(sql);
		return w;
	}

	public void updReckoning(Reckoning r)throws Exception {
		
		//String sql="update payable set payablesum="+r.getPayablesum()+",payabledescribe='"+r.getPayabledescribe()+"' where id='"+r.getId()+"'";
		EntityManager.update(r);
		
	}
	
	public void updIsAudit(String oid, Integer userid,Integer audit) throws Exception {
		
		String sql = "update Reckoning set isaudit="+audit+",auditid=" + userid
				+ ",auditdate='" + DateUtil.getCurrentDateString()
				+ "' where id='" + oid + "'";
		EntityManager.updateOrdelete(sql);
		
	}

}
