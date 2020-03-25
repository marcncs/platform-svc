package com.winsafe.drp.dao;

import java.util.List;

import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.pager.SimplePageInfo;

public class AppConsignMachinDetail {
	public List searchConsignMachinDetail(int pagesize, String pWhereClause,
			SimplePageInfo pPgInfo) throws Exception {
		int targetPage = pPgInfo.getCurrentPageNo();
		String sql = "from ConsignMachinDetail as cmd "
				+ pWhereClause + " order by cmd.id desc";
		List pls = EntityManager.getAllByHql(sql, targetPage, pagesize);
		return pls;
	}

	public void addConsignMachinDetail(Object[] si) throws Exception {
		EntityManager.save(si);
	}

	public void delConsignMachinDetail(String id) throws Exception {
		String sql = "delete from consign_machin_detail where cmid='" + id+"'";
		EntityManager.updateOrdelete(sql);
	}

	public ConsignMachinDetail getConsignMachinDetailByID(String id) throws Exception {
		String sql = " from ConsignMachinDetail as cmd where cmd.id='" + id+"'";
		ConsignMachinDetail p = (ConsignMachinDetail) EntityManager.find(sql);
		return p;
	}
	
	public List getConsignMachinDetailBySIID(String cmid) throws Exception {
		String sql = " from ConsignMachinDetail as cmd where cmd.cmid='" + cmid+"'";
		List list = EntityManager.getAllByHql(sql);
		return list;
	}
	
	public List getConsignMachinDetailBySIIDNotAlready(String cmid) throws Exception {
		String sql = " from ConsignMachinDetail as cmd where cmd.quantity!=cmd.alreadyquantity and cmd.cmid='" + cmid+"'";
		List list = EntityManager.getAllByHql(sql);
		return list;
	}
	

	  public void updAlreadyQuantity(Long id, Double quantity) throws Exception{
		    String sql = "update  consign_machin_detail set alreadyquantity=alreadyquantity+ "+quantity+" where id="+id;
		    EntityManager.updateOrdelete(sql);
	  }
	
	
	
}
