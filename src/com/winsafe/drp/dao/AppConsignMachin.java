package com.winsafe.drp.dao;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.pager2.PageQuery;

public class AppConsignMachin {

	public List getConsignMachin(HttpServletRequest request, int pagesize,
			String pWhereClause) throws Exception {

		String sql = " from ConsignMachin as cm "
			+ pWhereClause + " order by cm.makedate desc ";
		return PageQuery.hbmQuery(request, sql, pagesize);

	}

	public void updConsignMachin(ConsignMachin a)
			throws Exception {
		EntityManager.update(a);
	}

	//
	public void addConsignMachin(ConsignMachin a) throws Exception {
		 EntityManager.save(a);
	}

	public ConsignMachin getConsignMachinByID(String id) throws Exception {
		String sql = " from ConsignMachin where id='" + id + "'";
		return (ConsignMachin) EntityManager.find(sql);
	}
	
	public void delConsignMachin(String id)throws Exception{
		String sql="delete from Consign_Machin where id='"+id+"'";
		EntityManager.updateOrdelete(sql);

	}
	
	
	public void updCompleteQuantity(String id, Double complete) throws Exception {
		String sql = "update consign_machin set completequantity=completequantity + "+complete+" where id ='" + id+"'";
		EntityManager.updateOrdelete(sql);
	}

	
	public void updIsComplete(String id) throws Exception {
		String sql = "update consign_machin set isendcase=1 where cquantity=completequantity and id ='" + id+"'";
		EntityManager.updateOrdelete(sql);
	}

	

	public void updIsAudit(String ppid, Integer userid,Integer audit) throws Exception {
		String sql = "update consign_machin set isaudit="+audit+",auditid=" + userid
				+ ",auditdate='" + DateUtil.getCurrentDateString()
				+ "' where id='" + ppid + "'";
		EntityManager.updateOrdelete(sql);
	}
	

	public void updIsEndcase(String ppid, Integer audit) throws Exception {
		String sql = "update consign_machin set isendcase="+audit+" where id='" + ppid + "'";
		EntityManager.updateOrdelete(sql);
	}
	
	
	
	public int waitIncomeConsignMachin() throws Exception {
		int count = 0;
		String sql = "select count(*) from ConsignMachin as cm where cm.isaudit=1 and cm.isendcase=0";
		count = EntityManager.getRecordCount(sql);
		return count;
	}

	public List<ConsignMachin> getConsignMachin(String whereSql) {
		String sql = " from ConsignMachin as cm "
			+ whereSql + " order by cm.makedate desc ";
		return EntityManager.getAllByHql(sql);
	}
	

}
