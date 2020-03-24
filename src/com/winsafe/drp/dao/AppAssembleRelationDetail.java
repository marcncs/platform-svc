package com.winsafe.drp.dao;

import java.util.List;

import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.pager.SimplePageInfo;

public class AppAssembleRelationDetail {
	public List searchAssembleRelationDetail(int pagesize, String pWhereClause,
			SimplePageInfo pPgInfo) throws Exception {
		int targetPage = pPgInfo.getCurrentPageNo();
		String sql = "from AssembleRelation as ar "
				+ pWhereClause + " order by ar.makedate desc";
		List pls = EntityManager.getAllByHql(sql, targetPage, pagesize);
		return pls;
	}

	public void addAssembleRelationDetail(Object[] si) throws Exception {
		EntityManager.save(si);
	}

	public void delAssembleRelationDetail(String id) throws Exception {
		String sql = "delete from assemble_relation_detail where arid='" + id+"'";
		EntityManager.updateOrdelete(sql);
	
	}

	public AssembleRelationDetail getAssembleRelationDetailByID(String id) throws Exception {
		String sql = " from AssembleRelationDetail as ard where ard.id='" + id+"'";
		AssembleRelationDetail p = (AssembleRelationDetail) EntityManager.find(sql);
		return p;
	}
	
	public List getAssembleRelationDetailBySIID(String arid) throws Exception {
		List list = null;
		String sql = " from AssembleRelationDetail as ard where ard.arid='" + arid+"'";
		list = EntityManager.getAllByHql(sql);
		return list;
	}
	
	public List getSaleIndentDetailBySIIDNoTrans(String siid) throws Exception {
		String sql = " from SaleIndentDetail as p where quantity != saleorderquantity and p.siid='" + siid+"'";
		return EntityManager.getAllByHql(sql);
	}
	
	public void updSaleIndentDetail(SaleIndentDetail si) throws Exception {
		EntityManager.update(si);
	}
	
	 
	  public void updSaleOrderQuantity(Long id, Double quantity) throws Exception{
		    String sql = "update  sale_indent_detail set saleorderquantity=saleorderquantity+"+quantity+" where id="+id;
		   EntityManager.updateOrdelete(sql);

	  }
}
