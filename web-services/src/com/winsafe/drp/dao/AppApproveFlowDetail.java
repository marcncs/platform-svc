package com.winsafe.drp.dao;

import java.util.List;

import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.pager.SimplePageInfo;

public class AppApproveFlowDetail {

	public List getApproveFlowDetail(int pagesize, String pWhereClause,
			SimplePageInfo pPgInfo) throws Exception {
		int targetPage = pPgInfo.getCurrentPageNo();
		String sql = "select aff.id,aff.afid,aff.approveid,aff.actid from ApproveFlowDetail as aff "
				+ pWhereClause + " order by aff.id ";
		return EntityManager.getAllByHql(sql, targetPage, pagesize);
	}
	
	public List searchApproveFlowDetail(int pagesize, String pWhereClause,
			SimplePageInfo pPgInfo) throws Exception {
		int targetPage = pPgInfo.getCurrentPageNo();
		String sql = " from ApproveFlowDetail as aff "
				+ pWhereClause + " order by aff.approveorder,aff.id ";
		return EntityManager.getAllByHql(sql, targetPage, pagesize);
	}

	public void addApproveFlowDetail(Object o) throws Exception {		
		EntityManager.save(o);		
	}
	
	public List getApproveFlowDetailByAFID(Long afid)throws Exception{
		String sql="select aff.id,aff.approveid,aff.actid from ApproveFlowDetail as aff where aff.afid="+afid+"  order by aff.id";
		//System.out.println("---+++++++"+sql);
		return EntityManager.getAllByHql(sql);
	}
	
	public List getApproveFlowDetailByAFID(String afid)throws Exception{
		String sql="from ApproveFlowDetail where afid='"+afid+"' order by approveorder,id";
		return EntityManager.getAllByHql(sql);
	}


	public ApproveFlowDetail getApproveFlowDetailByID(Long id) throws Exception {
		String sql = " from ApproveFlowDetail where id=" + id;
		return (ApproveFlowDetail) EntityManager.find(sql);
	}

	public void updApproveFlowDetail(ApproveFlowDetail aff) throws Exception {		
		EntityManager.update(aff);
		
	}
	
	public void delApproveFlowDetail(Long id)throws Exception{		
		String sql="delete from approve_flow_detail where id="+id;
		EntityManager.updateOrdelete(sql);
		
	}

}
