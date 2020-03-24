package com.winsafe.drp.dao;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.pager2.PageQuery;

public class AppMsg {
	
	public List searchMsg(HttpServletRequest request, int pagesize, String pWhereClause) throws Exception {
		String hql = "from Msg  "
				+ pWhereClause + " order by makedate desc";
		return PageQuery.hbmQuery(request, hql, pagesize);
	}

	public void addMsg(Msg msg) throws Exception{		
		EntityManager.save(msg);		
	}
	
	public void updMsg(Msg msg) throws Exception{		
		EntityManager.saveOrUpdate(msg);		
	}
	
	
	public void delMsg(int id) throws Exception{		
		String sql="delete from Msg where id="+id;
		EntityManager.updateOrdelete(sql);		
	}
	
	public Msg getMsgById(int id) throws Exception{
		String sql="from Msg where id="+id;
		return (Msg)EntityManager.find(sql);
	}
	
	
	public List getReceiveMsgNoDeal()throws Exception{
		String sql=" from Msg as m where m.msgsort=0 and m.isdeal=0 ";
		return  EntityManager.getAllByHql(sql);
	}
	
	public List getSendMsgNoDeal()throws Exception{
		String sql=" from Msg as m where m.msgsort=1 and m.isaudit=1 and m.isdeal=0 ";
		return EntityManager.getAllByHql(sql);
	}
	
	
	public void updMsgDeal(int id)throws Exception{
		String sql=" update msg set isdeal=1 where id="+id;
		EntityManager.updateOrdelete(sql);
	}
	
	public void updIsAudit(int id, int userid,int audit) throws Exception {		
		String sql = "update msg set isaudit="+audit+",auditid=" + userid
				+ ",auditdate='" + DateUtil.getCurrentDateTime()
				+ "' where id=" + id + "";
		EntityManager.updateOrdelete(sql);			
	}
	
	
	public String filternums(String num)throws Exception{
		if ( num == null || "".equals(num) ){
			return "";
		}
		String[] ss = num.split(",");
		StringBuffer sb = new StringBuffer();
		for( int i=0; i<ss.length; i++ ){
			if ( i == 0 ){
				sb.append("'").append(ss[i]).append("'");
			}else{
				sb.append(",'").append(ss[i]).append("'");
			}
		}
		String sql=" select mobile from Customer where mobile in ("+sb.toString()+") and isreceivemsg=0";
		List mls = EntityManager.getAllByHql(sql);
		for ( int i=0; i<mls.size(); i++){
			String mobile = (String)mls.get(i);
			if ( num.indexOf(mobile) >= 0 ){
				num = num.replace(mobile+",", "");	
				num = num.replace(","+mobile, "");	
				num = num.replace(mobile, "");	
			}
		}
		return num;
	}
}
