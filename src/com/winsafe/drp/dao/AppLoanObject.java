package com.winsafe.drp.dao;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.pager2.PageQuery;

public class AppLoanObject {
	public List getLoanObject(HttpServletRequest request, int pagesize,
			String pWhereClause)throws Exception{
		 String sql=" from LoanObject as lo "
         +pWhereClause+" order by lo.id desc";
		 return PageQuery.hbmQuery(request, sql, pagesize);
	}
	
	public LoanObject getLoanObjectByID(Integer id)throws Exception{
		String sql=" from LoanObject where id="+id+"";
		return (LoanObject)EntityManager.find(sql);
	}
	
	public LoanObject getLoanObjectByUID(int uid)throws Exception{
		String sql=" from LoanObject where uid="+uid+"";
		return (LoanObject)EntityManager.find(sql);
	}
	
	public void addLoanObject(Object po)throws Exception{
		EntityManager.save(po);
	}
	
	public void updLoanObject(LoanObject po)throws Exception{
		EntityManager.save(po);
	}
	
	public void delLoanObject(int id)throws Exception{
		String sql="delete from loan_object where id="+id+"";
		EntityManager.updateOrdelete(sql);

	}
	
	
	public void noExistsToAdd(LoanObject po)throws Exception{
		String sql="insert into loan_object(id,uid,promisedate,makeorganid,makedeptid,makeid,makedate) select "+
		po.getId()+","+po.getUid()+",null,'"+po.getMakeorganid()+"',"+po.getMakedeptid()+","+po.getMakeid()+",'"+DateUtil.formatDateTime(po.getMakedate())+
		"' where not exists (select id from loan_object where uid="+po.getUid()+" and makeorganid='"+po.getMakeorganid()+"')";
		//System.out.println("----"+sql);
		 EntityManager.updateOrdelete(sql);

	}
	

	public void setLoanAwake(String id ,String promisedate)throws Exception{
		String sql="update loan_object set promisedate ='"+promisedate+"' where id="+id+"";
		 EntityManager.updateOrdelete(sql);

	}
	

	
}
