package com.winsafe.drp.dao;

import java.util.List;

import com.winsafe.drp.entity.EntityManager;

public class AppAlterMoveApplyDetail {

	public void addAlterMoveApplyDetail(Object stockAlterMove) throws Exception {

		EntityManager.save(stockAlterMove);

	}


	public List<AlterMoveApplyDetail> getAlterMoveApplyDetailByAmID(String amid) throws Exception {
		String sql = " from AlterMoveApplyDetail as sm where sm.amid= '" + amid+ "'";
		return EntityManager.getAllByHql(sql);
	}
	
	public List<AlterMoveApplyDetail> getAlterMoveApplyDetailNoTransByAmID(String amid) throws Exception {
		String sql = " from AlterMoveApplyDetail as sm where sm.canquantity!=sm.alreadyquantity and sm.amid= '" + amid+ "'";
		return EntityManager.getAllByHql(sql);
	}

	public void delAlterMoveApplyDetail(String id) throws Exception {

		String sql = "delete from Alter_Move_Apply_Detail where id='" + id
				+ "'";
		EntityManager.updateOrdelete(sql);

	}

	public void delAlterMoveApplyDetailByAmid(String id) throws Exception {

		String sql = "delete from Alter_Move_Apply_detail where amid='" + id
				+ "'";
		EntityManager.updateOrdelete(sql);

	}

	public AlterMoveApplyDetail getAlterMoveApplyDetailByID(String id)
			throws Exception {
		AlterMoveApplyDetail sm = null;
		String sql = " from AlterMoveApplyDetail as sm where sm.id='" + id
				+ "'";
		sm = (AlterMoveApplyDetail) EntityManager.find(sql);
		return sm;
	}

	

	public void updAlterMoveApplyDetail(AlterMoveApplyDetail sam) throws Exception {

		EntityManager.update(sam);

	}

	
	public void updAlreadyQuantity(Long id, Double quantity) throws Exception {

		String sql = "update  Alter_Move_Apply_Detail set alreadyquantity=alreadyquantity+"
				+ quantity + " where id=" + id;
		EntityManager.updateOrdelete(sql);

	}
	
	public void updCanQuantity(String amid) throws Exception {

		String sql = "update  Alter_Move_Apply_Detail set canquantity=0  where amid='" + amid+"'";
		EntityManager.updateOrdelete(sql);

	}

}
