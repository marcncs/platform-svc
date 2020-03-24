package com.winsafe.drp.dao;

import java.util.List;

import com.winsafe.drp.entity.EntityManager;

public class AppMoveApplyDetail {

	public void addMoveApplyDetail(MoveApplyDetail mad) throws Exception {

		EntityManager.save(mad);

	}


	public List<MoveApplyDetail> getMoveApplyDetailByAmID(String amid) throws Exception {
		String sql = " from MoveApplyDetail as sm where sm.maid= '" + amid+ "'";
		return EntityManager.getAllByHql(sql);
	}
	
	public List<MoveApplyDetail> getMoveApplyDetailByNoTransAmID(String amid) throws Exception {
		String sql = " from MoveApplyDetail as sm where sm.canquantity!=sm.alreadyquantity and sm.maid= '" + amid+ "'";
		return EntityManager.getAllByHql(sql);
	}

	public void delMoveApplyDetail(String id) throws Exception {

		String sql = "delete from Move_Apply_Detail where id='" + id
				+ "'";
		EntityManager.updateOrdelete(sql);

	}

	public void delMoveApplyDetailByAmid(String id) throws Exception {

		String sql = "delete from Move_Apply_Detail where maid='" + id
				+ "'";
		EntityManager.updateOrdelete(sql);

	}

	public MoveApplyDetail getMoveApplyDetailByID(String id)
			throws Exception {
		String sql = " from MoveApplyDetail as sm where sm.id='" + id
				+ "'";
		return (MoveApplyDetail) EntityManager.find(sql);
	}

	

	public void updAlterMoveApply(MoveApplyDetail sam) throws Exception {

		EntityManager.update(sam);

	}

	
	public void updAlreadyQuantity(Integer id, Double quantity) throws Exception {

		String sql = "update  Move_Apply_Detail set alreadyquantity=alreadyquantity+"
				+ quantity + " where id=" + id;
		EntityManager.updateOrdelete(sql);

	}

}
