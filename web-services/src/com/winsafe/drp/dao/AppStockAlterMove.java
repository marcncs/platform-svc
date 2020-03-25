package com.winsafe.drp.dao;

import java.sql.SQLException;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;

import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.server.MsgService;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.drp.util.WfLogger;
import com.winsafe.drp.util.fileListener.UFIDA.ReplyInfo;
import com.winsafe.hbm.entity.HibernateUtil;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.MakeCode;
import com.winsafe.hbm.util.StringUtil;
import com.winsafe.hbm.util.pager2.PageQuery;

public class AppStockAlterMove {

	private static final Logger log = Logger.getLogger(AppStockAlterMove.class);

	public void addStockAlterMove(Object stockAlterMove) throws Exception {
		EntityManager.save(stockAlterMove);
	}

	public List<StockAlterMove> getStockAlterMove(HttpServletRequest request,
			int pagesize, String whereSql) throws Exception {
		String hql = "select sm from StockAlterMove as sm " + whereSql
				+ " order by sm.id desc,sm.isblankout asc ";
		return PageQuery.hbmQuery(request, hql, pagesize);
	}
	
	public List<Map<String,String>> getStockAlterMoveList(HttpServletRequest request,
			int pagesize, String whereSql) throws Exception {
		String sql = "select sm.* from STOCK_ALTER_MOVE sm " 
				+ "join organ outo on OUTO.id = SM.OUTORGANID " +
				"join organ ino on ino.id = SM.receiveorganid "+ whereSql;
		if(pagesize > 0) {
			return PageQuery.jdbcSqlserverQuery(request, "id desc,isblankout asc", sql, pagesize);
		} else {
			sql+=" order by sm.id desc";
			return EntityManager.jdbcquery(sql);
		}
		 
	}
	
	public List<Map<String,String>> getStockAlterMoveList(HttpServletRequest request,
			int pagesize, String whereSql, Map<String, Object> param) throws Exception {
		String sql = "select sm.* from STOCK_ALTER_MOVE sm " 
				+ "join organ outo on OUTO.id = SM.OUTORGANID " +
				"join organ ino on ino.id = SM.receiveorganid "+ whereSql;
		if(pagesize > 0) {
			return PageQuery.jdbcSqlserverQuery(request, "id desc,isblankout asc", sql, pagesize, param);
		} else {
			sql+=" order by sm.id desc";
			return EntityManager.jdbcquery(sql, param); 
		}
		 
	}
	
	public List<StockAlterMove> getStockAlterMoveBySql(HttpServletRequest request,
			int pagesize, String whereSql, int userId, String organId) throws Exception {
		StringBuffer sb = new StringBuffer();
		sb.append("\r\n select * from (");
		sb.append("\r\n select * from STOCK_ALTER_MOVE sm ");
		sb.append("\r\n where  sm.isshipment=1 and sm.isblankout=0");
		sb.append("\r\n and sm.inwarehouseid in (");
		sb.append("\r\n select wv.warehouse_Id from  RULE_USER_WH wv where wv.user_Id="+userId).append(") ");
		sb.append("\r\n "+ whereSql);
		sb.append("\r\n  UNION");
		sb.append("\r\n select * from STOCK_ALTER_MOVE sm ");
		sb.append("\r\n where  sm.isshipment=1 and sm.isblankout=0");
		sb.append("\r\n and ");
		sb.append("\r\n sm.inwarehouseid is null and receiveorganid in (");
		
		sb.append("\r\n select visitorgan from User_Visit where userid="+userId+")");
		sb.append("\r\n  UNION");
		sb.append("\r\n select * from STOCK_ALTER_MOVE sm ");
		sb.append("\r\n where  sm.isshipment=1 and sm.isblankout=0");
		sb.append("\r\n and ");
		sb.append("\r\n sm.receiveorganid in (");
		
		sb.append("\r\n select oppOrganId from S_Transfer_Relation where  organizationId='" + organId + "'");
		sb.append("\r\n ) ");
		sb.append("\r\n" +whereSql);
		sb.append("\r\n )");
		
		return PageQuery.jdbcSqlserverQuery(request, "id desc", sb.toString(), pagesize);
//		return PageQuery.hbmQuery(request, hql, pagesize);
	}
	
	public List<Map<String,String>> getStockAlterMoveBySql(HttpServletRequest request,
			int pagesize, String whereSql, UsersBean users) throws Exception { 
		StringBuffer sb = new StringBuffer();
		sb.append("\r\n select * from (");
		sb.append("\r\n select sm.* from STOCK_ALTER_MOVE sm ");
		if(!DbUtil.isDealer(users)) {
			sb.append("\r\n join organ outo on OUTO.id = SM.OUTORGANID");
			sb.append("\r\n join organ ino on ino.id = SM.receiveorganid");
		}
		sb.append("\r\n where  sm.isshipment=1 and sm.isblankout=0");
		if(DbUtil.isDealer(users)) {
			sb.append("\r\n and sm.inwarehouseid in (");
			sb.append("\r\n select wv.warehouse_Id from  RULE_USER_WH wv where wv.user_Id="+users.getUserid()).append(") ");
		} else {
			sb.append(" and ("+DbUtil.getWhereCondition(users, "outo") + " or "+DbUtil.getWhereCondition(users, "ino")+") ");
		}
		sb.append("\r\n "+ whereSql);
		if(DbUtil.isDealer(users)) {
			sb.append("\r\n  UNION");
			sb.append("\r\n select * from STOCK_ALTER_MOVE sm ");
			sb.append("\r\n where  sm.isshipment=1 and sm.isblankout=0");
			sb.append("\r\n and ");
			sb.append("\r\n sm.inwarehouseid is null and receiveorganid in (");
			
			sb.append("\r\n select visitorgan from User_Visit where userid="+users.getUserid());
			sb.append("\r\n ) ");
			sb.append("\r\n" +whereSql);
		}
		sb.append("\r\n ) ");
		if(pagesize > 0) {
			return PageQuery.jdbcSqlserverQuery(request, "id desc", sb.toString(), pagesize);
		} else {
			return EntityManager.jdbcquery(sb.toString());
		}
		
//		return PageQuery.hbmQuery(request, hql, pagesize);
	}
	
	public List<Map<String,String>> getStockAlterMoveBySql(HttpServletRequest request,
			int pagesize, String whereSql, UsersBean users, Map<String, Object> param) throws Exception { 
		Map<String, Object> params = new LinkedHashMap<String, Object>();
		StringBuffer sb = new StringBuffer();
		sb.append("\r\n select * from (");
		sb.append("\r\n select sm.* from STOCK_ALTER_MOVE sm ");
		if(!DbUtil.isDealer(users)) {
			sb.append("\r\n join organ outo on OUTO.id = SM.OUTORGANID");
			sb.append("\r\n join organ ino on ino.id = SM.receiveorganid");
		}
		sb.append("\r\n where  sm.isshipment=1 and sm.isblankout=0");
		if(DbUtil.isDealer(users)) {
			sb.append("\r\n and sm.inwarehouseid in (");
			sb.append("\r\n select wv.warehouse_Id from  RULE_USER_WH wv where wv.user_Id="+users.getUserid()).append(") ");
		} else {
			sb.append(" and ("+DbUtil.getWhereCondition(users, "outo") + " or "+DbUtil.getWhereCondition(users, "ino")+") ");
		}
		sb.append("\r\n "+ whereSql);
		setParamater(params, param);
		if(DbUtil.isDealer(users)) {
			sb.append("\r\n  UNION");
			sb.append("\r\n select * from STOCK_ALTER_MOVE sm ");
			sb.append("\r\n where  sm.isshipment=1 and sm.isblankout=0");
			sb.append("\r\n and ");
			sb.append("\r\n sm.inwarehouseid is null and receiveorganid in (");
			
			sb.append("\r\n select visitorgan from User_Visit where userid="+users.getUserid());
			sb.append("\r\n ) ");
			sb.append("\r\n" +whereSql);
			setParamater(params, param);
		}
		sb.append("\r\n ) ");
		if(pagesize > 0) {
			return PageQuery.jdbcSqlserverQuery(request, "id desc", sb.toString(), pagesize, params);
		} else {
			return EntityManager.jdbcquery(sb.toString(), params);
		}
	}

	private void setParamater(Map<String, Object> params, Map<String, Object> param) {
		Iterator iter = param.entrySet().iterator(); 
		while (iter.hasNext()) { 
			Map.Entry entry = (Map.Entry) iter.next(); 
			params.put(UUID.randomUUID().toString(), entry.getValue());
		} 
		
	}

	public double getTotalSum(String pWhereClause) throws Exception {
		String sql = "select sum(sm.totalsum) from StockAlterMove as sm "
				+ pWhereClause;
		return EntityManager.getdoubleSum(sql);
	}

	public void updTakeStatus(String id, int takestatus) throws Exception {
		String sql = "update stock_alter_move set takestatus=" + takestatus
				+ " where id='" + id + "'";
		EntityManager.updateOrdelete(sql);
	}

	public void delStockAlterMove(String id) throws Exception {
		String sql = "delete from stock_alter_move where id='" + id + "'";
		EntityManager.updateOrdelete(sql);
	}

	public void delStockAlterMoveByNCcode(String nccode) throws Exception {
		String sql = "delete from stock_alter_move where Nccode='" + nccode
				+ "'";
		EntityManager.updateOrdelete(sql);
	}

	public StockAlterMove getStockAlterMoveByID(String id) throws Exception {
		StockAlterMove sm = null;
		String sql = " from StockAlterMove as sm where sm.id='" + id + "'";
		sm = (StockAlterMove) EntityManager.find(sql);
		return sm;
	}

	public StockAlterMove getStockAlterMoveByNCcode(String nccode)
			throws Exception {
		StockAlterMove sm = null;
		String sql = " from StockAlterMove as sm where sm.nccode='" + nccode
				+ "'";
		sm = (StockAlterMove) EntityManager.find(sql);
		return sm;
	}

	public void updStockAlterMoveByID(StockAlterMove sm, String movedate)
			throws Exception {
		String sql = "update stock_alter_move set movedate='" + movedate
				+ "',outwarehouseid=" + sm.getOutwarehouseid()
				+ ",inwarehouseid=" + sm.getInwarehouseid() + ",totalsum="
				+ sm.getTotalsum() + ",movecause='" + sm.getMovecause()
				+ "',remark='" + sm.getRemark() + "' where id='" + sm.getId()
				+ "'";
		EntityManager.updateOrdelete(sql);
	}

	public void updstockAlterMove(StockAlterMove sam) throws Exception {

		EntityManager.update(sam);

	}

	public void updStockAlterMoveIsShipment(String id, int isshipment,
			int userid) throws Exception {
		String sql = "update stock_alter_move set isshipment=" + isshipment
				+ ",shipmentid=" + userid + ",shipmentdate=sysdate where id='"
				+ id + "'";
		EntityManager.updateOrdelete(sql);
	}

	public void updStockAlterMoveIsComplete(String id, int iscomplete,
			int userid) throws Exception {
		String sql = "update stock_alter_move set iscomplete=" + iscomplete
				+ ",receivedate=sysdate,receiveid=" + userid + " where id='"
				+ id + "'";
		EntityManager.updateOrdelete(sql);
	}

	public void updIsMakeTicket(String id, int isticket) throws Exception {
		String sql = "update stock_alter_move set ismaketicket =" + isticket
				+ " where id='" + id + "'";
		EntityManager.updateOrdelete(sql);
	}

	public void updIsReceiveTicket(String id, int isticket) throws Exception {
		String sql = "update stock_alter_move set isreceiveticket =" + isticket
				+ " where id='" + id + "'";
		EntityManager.updateOrdelete(sql);
	}

	public void updIsRefer(String id) throws Exception {
		String sql = "update stock_alter_move set isrefer=1 where id='" + id
				+ "'";
		EntityManager.updateOrdelete(sql);
	}

	public void updIsApprove(String id, int isapprove) throws Exception {
		String sql = "update stock_alter_move set approvestatus=" + isapprove
				+ " where id ='" + id + "'";
		EntityManager.updateOrdelete(sql);
	}

	public void updIsAudit(String ppid, Long userid, Integer audit)
			throws Exception {
		String sql = "update stock_alter_move set isaudit=" + audit
				+ ",auditid=" + userid + ",auditdate='"
				+ DateUtil.getCurrentDateString() + "' where id='" + ppid + "'";
		EntityManager.updateOrdelete(sql);
	}

	public void updIsTally(String ppid, Long userid, Integer tally)
			throws Exception {
		String sql = "update stock_alter_move set istally=" + tally
				+ ",tallyid=" + userid + ",tallydate='"
				+ DateUtil.getCurrentDateTime() + "' where id='" + ppid + "'";
		EntityManager.updateOrdelete(sql);
	}

	public void blankout(String id, Long userid, Integer blankout,
			String blankoutreason) throws Exception {

		String sql = "update stock_alter_move set isblankout=" + blankout
				+ ",blankoutid=" + userid + ",blankoutdate='"
				+ DateUtil.getCurrentDateTime() + "',blankoutreason='"
				+ blankoutreason + "' where id='" + id + "'";
		EntityManager.updateOrdelete(sql);

	}

	public List getStockAlterOrganTotal(HttpServletRequest request,
			int pagesize, String whereSql) throws Exception {
		String hql = "select receiveorganid,receiveorganidname,olinkman,otel,sum(totalsum) as totalsum, sum(boxnum) as boxnum ,sum(scatternum) as scatternum from stock_alter_move sm, stock_alter_move_detail smd "
				+ whereSql
				+ " group by receiveorganid,receiveorganidname,olinkman,otel";
		return PageQuery.jdbcSqlserverQuery(request, "receiveorganid", hql,
				pagesize);
	}

	public List getStockAlterOrganTotal(String whereSql) throws Exception {
		String hql = "select receiveorganid,receiveorganidname,olinkman,otel,sum(totalsum) as totalsum from stock_alter_move "
				+ whereSql
				+ " group by receiveorganid,receiveorganidname,olinkman,otel";
		return EntityManager.jdbcquery(hql);
	}

	public double getStockAlterOrganTotalSum(String whereSql) {
		String hql = "select sum(totalsum) from StockAlterMove " + whereSql
				+ "";
		return EntityManager.getdoubleSum(hql);
	}

	public int getStockAlterTotalBoxSum(String whereSql)
			throws HibernateException, SQLException {
		String hql = "select sum(boxnum) as boxnum   from Stock_Alter_Move_Detail as smd,Stock_Alter_Move as sm "
				+ whereSql + "";
		int quantity = 0;
		Map map = (Map) EntityManager.jdbcquery(hql).get(0);
		if (map.get("boxnum") != null) {
			quantity = Integer.parseInt((map.get("boxnum").toString()));
		}
		return quantity;

	}

	public double getStockAlterTotalScatterSum(String whereSql)
			throws HibernateException, SQLException {
		double quantity = 0d;
		String hql = "select sum(scatternum) as scatternum  from Stock_Alter_Move_Detail as smd,Stock_Alter_Move as sm  "
				+ whereSql + "";

		Map map = (Map) EntityManager.jdbcquery(hql).get(0);
		if (map.get("scatternum") != null) {
			quantity = Double.valueOf(map.get("scatternum").toString());
		}
		return quantity;
	}

	public List getStockAlterProductTotal(HttpServletRequest request,
			int pagesize, String whereSql) throws Exception {
		String hql = "select  makeorganid,productid,productname,specmode,unitid,unitprice,sum(quantity) as quantity,sum(subsum )as subsum,sum(boxnum) as boxnum,sum(scatternum) as scatternum"
				+ " from stock_alter_move as sam , stock_alter_move_detail samd "
				+ whereSql
				+ " group by makeorganid,productId,productName,specMode,unitid,unitprice";
		return PageQuery.jdbcSqlserverQuery(request, "makeorganid", hql,
				pagesize);
	}

	public List getStockAlterProductTotalSum(String whereSql) throws Exception {
		String hql = "select sum(quantity) as quantity, sum(subsum )as subsum,sum(boxnum) as boxnum,sum(scatternum) as scatternum"
				+ " from stock_alter_move as sam , stock_alter_move_detail samd "
				+ whereSql;
		return EntityManager.jdbcquery(hql);
	}

	public List getStockAlterProductTotal(String whereSql) throws Exception {
		String hql = "select  productId,productName,specMode,unitid,unitprice,sum(quantity) as quantity,sum(subsum )as subsum"
				+ " from stock_alter_move as sam , stock_alter_move_detail samd "
				+ whereSql
				+ " group by productId,productName,specMode,unitid,unitprice";
		return EntityManager.jdbcquery(hql);
	}

	public List getStockAlterMoveBillTotal(HttpServletRequest request,
			int pagesize, String whereSql) throws Exception {
		String sql = "select sm.id ,movedate,receiveorganidname,makeorganid,sm.makedate,sum(quantity) as quantity,sum(boxnum) as boxnum,sum(scatternum) as scatternum  from stock_alter_move as sm,stock_alter_move_detail as smd "
				+ whereSql
				+ " group by sm.id,movedate,receiveorganidname,makeorganid,sm.makedate";
		return PageQuery.jdbcSqlserverQuery(request, sql, pagesize);
	}

	public List getStockAlterMoveBillTotal(String whereSql)
			throws HibernateException, SQLException {
		String hql = "select id ,movedate,receiveorganidname,makeorganid,makedate,totalsum from stock_alter_move "
				+ whereSql;
		return EntityManager.jdbcquery(hql);
	}

	public List<StockAlterMove> getStockAlterMove(String whereSql) {
		String hql = " from StockAlterMove as sm " + whereSql
				+ " order by sm.id desc,sm.isblankout asc ";
		return EntityManager.getAllByHql(hql);
	}
	
	public List<StockAlterMove> getStockAlterMove(String whereSql, Map<String,Object> param) throws Exception {
		String hql = " from StockAlterMove as sm " + whereSql
				+ " order by sm.id desc,sm.isblankout asc ";
		return EntityManager.getAllByHql(hql, param);
	}
	
	public List<Map<String,String>> getStockAlterMoveList(String whereSql) throws Exception {
		String sql = "select sm.* from STOCK_ALTER_MOVE sm " 
				+ "join organ outo on OUTO.id = SM.OUTORGANID " +
				"join organ ino on ino.id = SM.receiveorganid "+ whereSql+" order by sm.id desc,sm.isblankout asc ";
		return EntityManager.jdbcquery(sql);
	}

	public List getSiidByTtid(String ttid) throws Exception {
		String sql = "select siid from suggestinspect where mergeid in ( select sts.id from stock_alter_move sam , suggestinspect sts where sam.NCCODE = sts.SIID and sam.id = '"
				+ ttid
				+ "' )"
				+ " union "
				+ "select siid from suggestinspect where typeid <> '2000001' and id in ( select sts.id from stock_alter_move sam , suggestinspect sts where sam.NCCODE = sts.SIID and sam.id = '"
				+ ttid + "' ) ";
		return EntityManager.jdbcquery(sql);
	}

	/**
	 * 检验该单据是否已经复核过
	 * 
	 * @param nccode用友给出的编号
	 * @return result1为已经复核过0为未复核
	 */
	public boolean checkIsAudit(String nccode) {
		boolean result = false;
		String sql = " from StockAlterMove where NCcode='" + nccode + "'";
		StockAlterMove temp = new StockAlterMove();
		temp = (StockAlterMove) EntityManager.find(sql);
		if (temp != null) {
			if (temp.getIsaudit() == 0) {
				result = true;
			}
		} else {
			result = true;
		}
		return result;

	}

	/**
	 * 通过nccode找id
	 * 
	 * @param nccode
	 * @return id
	 */

	public String getStockAlterMoveNccodeByID(String id) throws Exception {
		String result = "";
		StockAlterMove stockAlterMove = new StockAlterMove();
		stockAlterMove = this.getStockAlterMoveByID(id);
		result = stockAlterMove.getNccode();
		return result;
	}

	/**
	 * 入库StockAlterMove保存的方式
	 * 
	 * @param StockAlterMove需要保存的实体对象
	 * @return replyInfo是否该次保存顺利执行及相关信息
	 */
	public static ReplyInfo saveStockAlterMove(StockAlterMove stockAlterMove) {
		ReplyInfo replyInfo = new ReplyInfo();
		boolean flag = false;
		String info = "";
		HibernateUtil.currentTransaction();
		AppStockAlterMove appStockAlterMove = new AppStockAlterMove();
		try {

			appStockAlterMove.addStockAlterMove(stockAlterMove);

			flag = true;
			log.info("------保存成功------" + flag);
		} catch (Exception e) {

			e.printStackTrace();
			info = e.toString();
			log.error("------StockAlterMove保存------" + e.toString());
		}
		replyInfo.setSaveFlag(flag);
		replyInfo.setErrorInfo(info);
		HibernateUtil.commitTransaction();
		return replyInfo;

	}

	/**
	 * 入库StockAlterMoveDetail保存的方式
	 * 
	 * @param stockAlterMoveDetail需要保存的实体对象
	 * @return replyInfo是否该次保存顺利执行及相关信息
	 */

	public static ReplyInfo saveStockAlterMoveDetail(
			StockAlterMoveDetail stockAlterMoveDetail) {

		ReplyInfo replyInfo = new ReplyInfo();
		boolean flag = false;
		String info = "";
		HibernateUtil.currentTransaction();
		AppStockAlterMoveDetail appStockAlterMoveDetail = new AppStockAlterMoveDetail();
		try {

			appStockAlterMoveDetail
					.addStockAlterMoveDetail(stockAlterMoveDetail);
			flag = true;
			log.info("------StockAlterMoveDetail保存-----成功");
		} catch (Exception e) {

			e.printStackTrace();
			info = e.toString();
			log.error("------StockAlterMoveDetail保存------" + e.toString());
		}
		replyInfo.setSaveFlag(flag);
		replyInfo.setErrorInfo(info);
		HibernateUtil.commitTransaction();
		return replyInfo;

	}

	/**
	 * 检货检货出库
	 * 
	 * @param smid产成品ID
	 * @param userId根据psname得到的制单人信息
	 * @return result检货结果
	 */
	public static String auditStockAlterMove(String smid, String userId) {
		String result = "success";
		Users users = new Users();
		UsersBean usersBean = new UsersBean();
		AppUsers appUsers = new AppUsers();
		AppFUnit appFUnit = new AppFUnit();
		AppStockAlterMove api = new AppStockAlterMove();
		AppStockAlterMoveDetail asamd = new AppStockAlterMoveDetail();
		AppProductStockpileAll appps = new AppProductStockpileAll();
		StockAlterMove stockAlterMove = new StockAlterMove();
		List<StockAlterMoveDetail> dls;
		try {
			dls = asamd.getStockAlterMoveDetailBySamID(smid);
		} catch (Exception e1) {
			result = "false";
			e1.printStackTrace();
			return result;
		}
		try {
			stockAlterMove = api.getStockAlterMoveByID(smid);
			users = appUsers.getUsersByid(Integer.valueOf(userId));
			usersBean = UserManager.getBeanFromUsers(users);
			// 是否复核过
			if (stockAlterMove.getIsaudit() == 1) {
				result = ResourceBundle
						.getBundle(
								"com.winsafe.drp.util.fileListener.UFIDA.ImportDataFormat")
						.getString("have_auditted");
				return result;

			}
			// 是否作废 1为作废
			if (stockAlterMove.getIsblankout() == 1) {
				result = ResourceBundle
						.getBundle(
								"com.winsafe.drp.util.fileListener.UFIDA.ImportDataFormat")
						.getString("blank_out");
				return result;

			}

			// 不是同一制单机构
			if (!stockAlterMove.getMakeorganid().equals(users.getMakeorganid())) {
				result = ResourceBundle
						.getBundle(
								"com.winsafe.drp.util.fileListener.UFIDA.ImportDataFormat")
						.getString("different_makeorganid");
				return result;
			}
			for (StockAlterMoveDetail sod : dls) {

				double q = appFUnit.getQuantity(sod.getProductid(), sod
						.getUnitid(), sod.getQuantity());
				double stock = appps.getProductStockpileAllByPIDWID(sod
						.getProductid(), stockAlterMove.getOutwarehouseid());
				if (q > stock) {
					result = ResourceBundle
							.getBundle(
									"com.winsafe.drp.util.fileListener.UFIDA.ImportDataFormat")
							.getString("out_of_quantity");
					return result;
				}
			}
			addTakeBill(stockAlterMove, dls, usersBean);

			stockAlterMove.setAuditdate(DateUtil.getCurrentDate());
			stockAlterMove.setAuditid(Integer.valueOf(userId));
			stockAlterMove.setIsaudit(1);

			HibernateUtil.currentTransaction();
			api.updstockAlterMove(stockAlterMove);
			HibernateUtil.commitTransaction();

			String[] param = new String[] { "name", "applytime", "billno" };
			String[] values = new String[] { stockAlterMove.getOlinkman(),
					DateUtil.formatDate(stockAlterMove.getMovedate()),
					stockAlterMove.getId() };
			MsgService ms = new MsgService(param, values, usersBean, 7);
			ms.addmag(1, stockAlterMove.getOtel());

			DBUserLog.addUserLog(Integer.valueOf(userId), 4, "订购>>复核订购单,编号:"
					+ smid);
		} catch (Exception e) {
			result = "fail";
			e.printStackTrace();
			return result;
		}
		return result;
	}

	public static void addTakeBill(StockAlterMove so,
			List<StockAlterMoveDetail> pils, UsersBean users) throws Exception {
		TakeBill takebill = new TakeBill();
		takebill.setId(so.getId());
		takebill.setBsort(1);
		takebill.setOid(so.getReceiveorganid());
		takebill.setOname(so.getReceiveorganidname());
		takebill.setRlinkman(so.getOlinkman());
		takebill.setTel(so.getOtel());
		takebill.setInwarehouseid(so.getInwarehouseid());
		takebill.setSenddate(so.getMovedate());
		takebill.setMakeorganid(so.getMakeorganid());
		takebill.setMakedeptid(so.getMakedeptid());
		takebill.setEquiporganid(so.getMakeorganid());
		takebill.setMakeid(users.getUserid());
		takebill.setMakedate(DateUtil.getCurrentDate());
		takebill.setIsaudit(0);
		takebill.setIsblankout(0);

		TakeServiceBean tsb = new TakeServiceBean();
		tsb.setTakebill(takebill);

		TakeTicket tt = null;
		for (StockAlterMoveDetail pid : pils) {

			if (tsb.getTtmap().get(so.getOutwarehouseid()) == null) {
				tt = new TakeTicket();
				tt.setId(MakeCode.getExcIDByRandomTableName("take_ticket", 2,
						"TT"));
				tt.setWarehouseid(so.getOutwarehouseid());
				tt.setBillno(takebill.getId());
				tt.setBsort(1);
				tt.setOid(takebill.getOid());
				tt.setOname(takebill.getOname());
				tt.setRlinkman(takebill.getRlinkman());
				tt.setTel(takebill.getTel());
				tt.setRemark(so.getRemark());
				tt.setIsaudit(0);
				tt.setMakeorganid(users.getMakeorganid());
				tt.setMakedeptid(users.getMakedeptid());
				tt.setMakeid(users.getUserid());
				tt.setMakedate(DateUtil.getCurrentDate());
				tt.setInwarehouseid(takebill.getInwarehouseid());
				tt.setPrinttimes(0);
				tt.setIsblankout(0);
				tsb.getTtmap().put(so.getOutwarehouseid(), tt);
				tsb.getWarehouseids().add(so.getOutwarehouseid());
			} else {
				tt = tsb.getTtmap().get(so.getOutwarehouseid());
			}
			// 不控制批次
			// TakeTicketDetailService ttds = new
			// TakeTicketDetailService(tt,tt.getWarehouseid(),pid.getProductid(),
			// pid.getProductname(), pid.getSpecmode(), pid.getUnitid(),
			// pid.getUnitprice());
			// ttds.addBatchDetail(pid.getQuantity());

			TakeTicketDetail ttd = new TakeTicketDetail();
			ttd.setId(Integer.valueOf(MakeCode.getExcIDByRandomTableName(
					"take_ticket_detail", 0, "")));
			ttd.setProductid(pid.getProductid());
			ttd.setProductname(pid.getProductname());
			ttd.setSpecmode(pid.getSpecmode());
			ttd.setUnitid(pid.getUnitid());
			ttd.setBatch("");
			ttd.setUnitprice(pid.getUnitprice());
			ttd.setQuantity(pid.getQuantity());
			ttd.setTtid(tt.getId());
			tt.getTtdetails().add(ttd);

		}

		AppTakeService appts = new AppTakeService();
		HibernateUtil.currentTransaction();
		appts.addTake(tsb, false);
		HibernateUtil.commitTransaction();
	}

	/**
	 * 根据nccode使单据作废(出库)
	 * 
	 * @param nccode用友给出的id
	 * @param psnname批单人
	 */
	public ReplyInfo updateblankoutByNCcode(String nccode, String psnname)
			throws Exception {
		ReplyInfo replyInfo = new ReplyInfo();
		boolean flag = false;
		String info = "";
		StockAlterMove temp = new StockAlterMove();
		temp = this.getStockAlterMoveByNCcode(nccode);
		// 如果查不到该条记录则返回报错xml
		if (temp == null) {
			flag = false;
			info = ResourceBundle.getBundle(
					"com.winsafe.drp.util.fileListener.UFIDA.ImportDataFormat")
					.getString("none_record");

		} else {
			// 判断该单据是否已记帐
			// 如果该条记录已经复核过则取消复核
			if (1 == temp.getIsshipment()) {
				/*
				 * AppOtherIncomeIdcode aoidcode = new AppOtherIncomeIdcode();
				 * AppOtherIncomeDetail aoid=new AppOtherIncomeDetail();
				 * AppIdcode appidcode = new AppIdcode(); AppProductStockpile
				 * aps = new AppProductStockpile(); OtherIncome otherIncome =
				 * new OtherIncome(); otherIncome =
				 * this.getOtherIncomeByNCcode(nccode); // String
				 * batch=purchaseIncome.get this.updIsAudit(nccode,
				 * Integer.valueOf(psnname), 0);
				 * this.delOtherIncomeByNCcode(nccode); List<OtherIncomeIdcode>
				 * idcodelist = aoidcode
				 * .getOtherIncomeIdcodeByoiid(temp.getId(), 1); for
				 * (OtherIncomeIdcode ic : idcodelist) {
				 * appidcode.delIdcodeByid(ic.getIdcode()); }
				 * List<OtherIncomeIdcode> piilist = aoidcode
				 * .getOtherIncomeIdcodeByoiid(temp.getId()); for
				 * (OtherIncomeIdcode pii : piilist) { // RichieYu -----20100505
				 * // 退还实际总数量 aps.returninProductStockpileTotalQuantity(pii
				 * .getProductid(), pii.getUnitid(), pii.getBatch(),
				 * pii.getPackquantity() * pii.getQuantity(), temp
				 * .getWarehouseid(), pii.getWarehousebit(), temp.getId(),
				 * "取消其他入库-出货"); }
				 * 
				 * aoidcode.delOtherIncomeIDcodeByNCcode(nccode);
				 * aoid.delOtherIncomeDetailByNCcode(nccode);
				 */
				flag = false;
				info = ResourceBundle
						.getBundle(
								"com.winsafe.drp.util.fileListener.UFIDA.ImportDataFormat")
						.getString("can_not_cancel_audit");
			} else {
				HibernateUtil.currentTransaction();
				this.delStockAlterMoveByNCcode(nccode);
				AppStockAlterMoveIdcode aoidcode = new AppStockAlterMoveIdcode();
				AppStockAlterMoveDetail aoid = new AppStockAlterMoveDetail();
				aoidcode.delStockAlterMoveIDcodeByNCcode(nccode);
				aoid.delStockAlterMoveDetailByNCcode(nccode);
				HibernateUtil.commitTransaction();
			}
			flag = true;
			info = ResourceBundle.getBundle(
					"com.winsafe.drp.util.fileListener.UFIDA.ImportDataFormat")
					.getString("success");

		}
		replyInfo.setSaveFlag(flag);
		replyInfo.setErrorInfo(info);
		return replyInfo;
	}

	public StockMove getStockMoveByNCcode(String nccode) throws Exception {
		String sql = " from StockMove where NCcode='" + nccode + "'";
		return (StockMove) EntityManager.find(sql);
	}

	public Integer getStockAlterMoveCountByNCcode(String ncCode) {
		String sql = "select count(id) from stock_alter_move where nccode = '"+ncCode+"'";
		return EntityManager.getRecordCountBySql(sql);
		
	}
	
	public StockAlterMove getStockAlterMoveByOidAndNCcode(String oid, String nccode)
		throws Exception {
		StockAlterMove sm = null;
		String sql = " from StockAlterMove as sm where sm.outOrganId='"+oid+"' and sm.nccode='" + nccode
				+ "'";
		sm = (StockAlterMove) EntityManager.find(sql);
		return sm;
	}

	public List<Map<String, String>> getStockAlterMoveReceive(
			HttpServletRequest request, Users loginUsers) throws HibernateException, SQLException {
		StringBuilder hql = new StringBuilder();
		hql.append("select max(SAM.OUTORGANNAME) fromOrgName,max(SAM.RECEIVEORGANIDNAME) toOrgName,max(SAM.MAKEDATE) billDate,SAM.ID billNo,SAMD.PRODUCTID,max(SAMD.PRODUCTNAME) PRODUCTNAME,max(SAMD.SPECMODE) SPECMODE,max(SAMD.TAKEQUANTITY) TAKEQUANTITY,max(SAMD.NCCODE) MCODE,max(SAMD.UNITID) unitid,sum(sami.quantity) sjtotal from STOCK_ALTER_MOVE sam ");
		hql.append("join STOCK_ALTER_MOVE_DETAIL samd on sam.ID = SAMD.SAMID ");
		hql.append("and SAM.ISSHIPMENT = 1 and SAM.ISBLANKOUT = 0 and SAM.ISCOMPLETE = 0 ");
		hql.append("and SAM.INWAREHOUSEID in (select warehouse_Id from  Rule_User_Wh where user_Id="+loginUsers.getUserid()+") ");
		if(!StringUtil.isEmpty(request.getParameter("fromOrganId"))) {
			hql.append("and SAM.OUTORGANID = '"+request.getParameter("fromOrganId")+"' ");
		}
		hql.append("LEFT JOIN STOCK_ALTER_MOVE_IDCODE sami on  SAMD.SAMID = SAMI.SAMID and sami.productid=samd.productid ");
		hql.append("group by SAM. ID,SAMD.PRODUCTID ");
		hql.append("order by SAM.ID desc ");
		return EntityManager.jdbcquery(hql.toString());
	}

	public List<StockAlterMove> getShippedStockAlterMoveToBonus(String startDate, String endDate) { 
//		String sql = "select sam from StockAlterMove sam,Organ o where sam.outOrganId = o.id and o.organType = 2 and o.organModel = 2 and o.isKeyRetailer = 1 and sam.iscomplete = 1 and sam.bonusStatus = 0 and sam.makedate >= to_date('"+startDate+"','yyyy-MM-dd hh24:mi:ss') and sam.makedate <= to_date('"+endDate+" 23:59:59','yyyy-MM-dd hh24:mi:ss')";
		String sql = "select sam from StockAlterMove sam,Organ o,Organ ino where sam.outOrganId = o.id and sam.receiveorganid=ino.id  and o.organType = 2 and ((o.organModel = 2 and o.isKeyRetailer = 1) or (o.organModel = 1 and ino.organType = 2 and ino.organModel not in (1,2) and ino.isKeyRetailer = 1)) and sam.iscomplete = 1 and sam.bonusStatus = 0 and sam.makedate >= to_date('"+startDate+"','yyyy-MM-dd hh24:mi:ss') and sam.makedate <= to_date('"+endDate+" 23:59:59','yyyy-MM-dd hh24:mi:ss')";
		return EntityManager.getAllByHql(sql);
	}
	
	public List<StockAlterMove> getReceivedStockAlterMoveToBonus() {
		String sql = "select sam from StockAlterMove sam,Organ o1,Organ o2 where sam.outOrganId = o1.id and sam.receiveorganid = o2.id and o1.organType = 2 and o1.organModel = 2 and o1.isKeyRetailer = 1 and o2.organType = 2 and o2.organModel not in (1,2) and o2.isKeyRetailer = 1 and sam.iscomplete = 1 and sam.bonusStatus = 1 ";
		return EntityManager.getAllByHql(sql);
	}
	
	public Integer countStockAlterMoveReceive(HttpServletRequest request, Users loginUsers) throws HibernateException, SQLException {
		StringBuilder hql = new StringBuilder();
		hql.append("select count(SAM.ID) from STOCK_ALTER_MOVE SAM ");
		hql.append(" where SAM.ISSHIPMENT = 1 and SAM.ISBLANKOUT = 0 and SAM.ISCOMPLETE = 0 ");
		hql.append(" and SAM.INWAREHOUSEID in (select warehouse_Id from  Rule_User_Wh where user_Id="+loginUsers.getUserid()+") ");
		if(!StringUtil.isEmpty(request.getParameter("fromOrganId"))) {
			hql.append("and SAM.OUTORGANID = '"+request.getParameter("fromOrganId")+"' ");
		}
		hql.append("order by SAM.ID desc ");
		return EntityManager.getRecordCountBySql(hql.toString());
	}

}
