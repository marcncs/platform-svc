package com.winsafe.drp.dao;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.StringUtil;
import com.winsafe.hbm.util.pager2.PageQuery;

public class AppMoveApply {

	public void addMoveApply(MoveApply ma) throws Exception {
		EntityManager.save(ma);
	}

	public List getMoveApply(HttpServletRequest request,int pageSize, String whereSql) throws Exception {
		String hql = " from MoveApply as ma "
				+ whereSql + " order by ma.makedate desc ";
		return PageQuery.hbmQuery(request, hql, pageSize);
	}
	
	public List getMoveApplyList(HttpServletRequest request,int pageSize, String whereSql) throws Exception {
		String sql = "select ma.*,outo.organname outoname,ino.organname inoname from MOVE_APPLY ma " +
				"join organ outo on ma.OUTORGANID = OUTO.ID " +
				"join organ ino on ino.id=ma.INORGANID "+ whereSql;
		if(pageSize > 0) {
			return PageQuery.jdbcSqlserverQuery(request, "makedate desc", sql, pageSize);
		} else {
			sql+=" order by ma.makedate desc ";
			return EntityManager.jdbcquery(sql);
		}
		
	}
	
	public List getMoveApplyList(HttpServletRequest request,int pageSize, String whereSql, Map<String, Object> param) throws Exception {
		String sql = "select ma.*,outo.organname outoname,ino.organname inoname from MOVE_APPLY ma " +
				"join organ outo on ma.OUTORGANID = OUTO.ID " +
				"join organ ino on ino.id=ma.INORGANID "+ whereSql;
		if(pageSize > 0) {
			return PageQuery.jdbcSqlserverQuery(request, "makedate desc", sql, pageSize, param);
		} else {
			sql+=" order by ma.makedate desc ";
			return EntityManager.jdbcquery(sql, param);
		}
		
	}

	public void updIsTrans(String id, int trans) throws Exception {
		String sql = "update Move_Apply set istrans="+trans+" where id='" + id + "'";
		EntityManager.updateOrdelete(sql);
	}
	
	public void delMoveApply(String id)throws Exception{
		String sql="delete from Move_Apply where id='"+id+"'";
		EntityManager.updateOrdelete(sql);
	}

	public MoveApply getMoveApplyByID(String id) throws Exception {
		MoveApply sm = null;
		String sql = " from MoveApply as ama where ama.id='" + id+"'";
		sm = (MoveApply) EntityManager.find(sql);
		return sm;
	}
	
	
	public int waitOutPortMoveApply(String organid) throws Exception {
		int count = 0;
		String sql = "select count(*) from MoveApply as am where am.isratify=1 and am.istrans=0 and am.outorganid='"+organid+"'";
		count = EntityManager.getRecordCount(sql);
		return count;
	}

	
	public void updMoveApply(MoveApply sam)throws Exception{
		EntityManager.update(sam);
	}
	

	public void ratifyMoveApply(String id,Integer isratify,Long userid)throws Exception{
		String sql="update Move_Apply set isratify="+isratify+",ratifyid="+userid+",ratifydate='"+DateUtil.getCurrentDateString()+"' where id='"+id+"'";
		EntityManager.updateOrdelete(sql);
	}
	
	
	public void updStockAlterMoveIsComplete(String id,Integer iscomplete,Long userid)throws Exception{
		String sql="update Move_Apply set iscomplete="+iscomplete+",receivedate='"+DateUtil.getCurrentDateString()+"',receiveid="+userid+" where id='"+id+"'";
		EntityManager.updateOrdelete(sql);
	}
	
	
	
	public void updIsApprove(String id, int isapprove) throws Exception {
		String sql = "update Move_Apply set approvestatus=" + isapprove
				+ " where id ='" + id+"'";
		EntityManager.updateOrdelete(sql);
	}


	public void updIsAudit(String ppid, Long userid,Integer audit) throws Exception {
		String sql = "update Move_Apply set isaudit="+audit+",auditid=" + userid
				+ ",auditdate='" + DateUtil.getCurrentDateString()
				+ "' where id='" + ppid + "'";
		EntityManager.updateOrdelete(sql);
	}
	

	public void updIsTally(String ppid, Long userid,Integer tally) throws Exception {
		String sql = "update Move_Apply set istally="+tally+",tallyid=" + userid
				+ ",tallydate='" + DateUtil.getCurrentDateString()
				+ "' where id='" + ppid + "'";
		EntityManager.updateOrdelete(sql);
	}
	

	public void blankout(String id, Long userid, Integer blankout, String blankoutreason) throws Exception {
		
		String sql = "update Move_Apply set isblankout="+blankout+",blankoutid=" + userid
				+ ",blankoutdate='" + DateUtil.getCurrentDateTime()
				+ "',blankoutreason='"+blankoutreason+"' where id='" + id + "'";
		EntityManager.updateOrdelete(sql);
		
	}
	
	public void IsTrans(String id,Integer istrans )throws Exception{
		String sql = "update Move_Apply set istrans="+istrans+" where id='" + id + "'";
		EntityManager.updateOrdelete(sql);
		
	}

	public List<MoveApply> getMoveApply(String whereSql) {
		String hql = " from MoveApply as ma "
			+ whereSql + " order by ma.makedate desc ";
		return EntityManager.getAllByHql(hql);
	}

	public int getMoveQuantityByMaidBatchAndPid(String moveApplyId,
			String batch, String pid) throws Exception {
		if(StringUtil.isEmpty(batch)) {
			String sql1 = "select sum(CANQUANTITY) as canquantity from MOVE_APPLY_DETAIL where maid = '"+moveApplyId+"' and PRODUCTID = '"+pid+"'";
			String sql2 = "select sum(quantity) as quantity from STOCK_MOVE_IDCODE where smid = '"+moveApplyId+"' and PRODUCTID = '"+pid+"' and batch is not null";
			return EntityManager.getRecordCountBySql(sql1) - EntityManager.getRecordCountBySql(sql2);
		} else {
			String sql = "select sum(quantity) as quantity from STOCK_MOVE_IDCODE where smid = '"+moveApplyId+"' and PRODUCTID = '"+pid+"' and batch = '"+batch+"'";
			return  EntityManager.getRecordCountBySql(sql);
		}
	}

	public List<Object[]> getApproversByOrganId(String organId) {
		String hql = "select us.realname,us.loginname from Users us, UserCustomer uc where us.userid = uc.userId and  uc.organId='"+organId+"'";
		return EntityManager.getAllByHql(hql);
	}

	public List<Object[]> getApproversByRoleName(String roleName) {
		String hql = "select us.realname,us.loginname from Users us, UserRole uc, Role r where us.userid = uc.userid and uc.ispopedom = 1 and uc.roleid = r.id and  r.rolename='"+roleName+"'";
		return EntityManager.getAllByHql(hql);
	} 
	
}
