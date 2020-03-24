package com.winsafe.drp.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;

import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.server.MsgService;
import com.winsafe.drp.server.OrganService;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.drp.util.fileListener.UFIDA.ReplyInfo;
import com.winsafe.hbm.entity.HibernateUtil;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.MakeCode;
import com.winsafe.hbm.util.pager.SimplePageInfo;
import com.winsafe.hbm.util.pager2.PageQuery;

public class AppStockMove {

	private static final Logger log = Logger.getLogger(AppStockMove.class);

	public void addStockMove(Object stockmove) throws Exception {

		EntityManager.save(stockmove);

	}

	public List getStockMove(HttpServletRequest request, int pageSize,
			String whereSql) throws Exception {
		String hql = "from StockMove as sm " + whereSql
				+ " order by sm.id desc ";
		return PageQuery.hbmQuery(request, hql, pageSize);
	}
	
	public List getStockMoveList(HttpServletRequest request, int pagesize,
			String whereSql) throws Exception { 
		String sql = "select sm.*,outo.organname outoname,ino.organname inoname from STOCK_MOVE sm " 
			+ "join organ outo on OUTO.id = SM.OUTORGANID " +
			"join organ ino on ino.id = SM.inorganid "+ whereSql;
		if(pagesize > 0) {
			return PageQuery.jdbcSqlserverQuery(request, "id desc,isblankout asc", sql, pagesize);
		} else {
			sql+=" order by sm.id desc,sm.isblankout asc";
			return EntityManager.jdbcquery(sql);
		}
	
	} 
	
	public List getStockMoveList(HttpServletRequest request, int pagesize,
			String whereSql, Map<String, Object> param) throws Exception { 
		String sql = "select sm.*,outo.organname outoname,ino.organname inoname from STOCK_MOVE sm " 
			+ "join organ outo on OUTO.id = SM.OUTORGANID " +
			"join organ ino on ino.id = SM.inorganid "+ whereSql;
		if(pagesize > 0) {
			return PageQuery.jdbcSqlserverQuery(request, "id desc,isblankout asc", sql, pagesize, param);
		} else {
			sql+=" order by sm.id desc,sm.isblankout asc";
			return EntityManager.jdbcquery(sql, param);
		}
	
	} 

	public List getTotalSum(String pWhereClause) throws Exception {
		String sql = "select sum(smd.quantity), sum(smd.takequantity), sum(smd.cost)"
				+ " from StockMove sm, StockMoveDetail smd "
				+ pWhereClause
				+ " and sm.id = smd.smid ";
		return EntityManager.getAllByHql(sql);
	}

	public List getStockMoveByVisit(int pagesize, String whereSql,
			SimplePageInfo tmpPgInfo) throws Exception {
		int targetPage = tmpPgInfo.getCurrentPageNo();
		String sql = "select sm from StockMove as sm,WarehouseVisit as wv "
				+ whereSql + " order by sm.id desc ";
		// System.out.println("----"+sql);
		return EntityManager.getAllByHql(sql, targetPage, pagesize);
	}

	public void updTakeStatus(String id, int takestatus) throws Exception {
		String sql = "update stock_move set takestatus=" + takestatus
				+ " where id='" + id + "'";
		EntityManager.updateOrdelete(sql);
	}
	
	public String completeStockMoveRec(String nccode, String nccode_rec) throws Exception {
		String result="success";
		HibernateUtil.currentTransaction();
		StockMove temp= new StockMove();
		temp=getStockMoveByNCcode(nccode);
		if(temp==null){
			result="fail";
			return result;
		}
		String sql = "update stock_move set NCcode_receive='" + nccode_rec
				+ "' where nccode='" + nccode + "'";
		EntityManager.updateOrdelete(sql);
		HibernateUtil.commitTransaction();
		
		return result;
	}
	

	public void delStockMove(String id) throws Exception {

		String sql = "delete from stock_move where id='" + id + "'";
		EntityManager.updateOrdelete(sql);

	}

	public void updstockMove(StockMove sm) throws Exception {
		EntityManager.update(sm);
	}

	public StockMove getStockMoveByID(String id) throws Exception {
		String sql = " from StockMove as sm where sm.id='" + id + "'";
		return (StockMove) EntityManager.find(sql);
	}

	public void updStockMoveByID(StockMove sm, String movedate)
			throws Exception {

		String sql = "update stock_move set movedate='" + movedate
				+ "',outwarehouseid=" + sm.getOutwarehouseid()
				+ ",inwarehouseid=" + sm.getInwarehouseid() + ",totalsum="
				+ sm.getTotalsum() + ",movecause='" + sm.getMovecause()
				+ "',remark='" + sm.getRemark() + "' where id='" + sm.getId()
				+ "'";
		EntityManager.updateOrdelete(sql);

	}

	public void updStockMoveIsShipment(String id, int isshipment, int userid)
			throws Exception {
		String sql = "update stock_move set isshipment=" + isshipment
				+ ",shipmentid=" + userid + ",shipmentdate='"
				+ DateUtil.getCurrentDateTime() + "' where id='" + id + "'";
		EntityManager.updateOrdelete(sql);
	}

	public void updStockMoveIsComplete(String id, Integer iscomplete, int userid)
			throws Exception {

		String sql = "update stock_move set iscomplete=" + iscomplete
				+ ",receivedate=sysdate,receiveid=" + userid + " where id='" + id + "'";
		EntityManager.updateOrdelete(sql);
	}

	public void updIsRefer(String id) throws Exception {

		String sql = "update stock_move set isrefer=1 where id='" + id + "'";
		EntityManager.updateOrdelete(sql);

	}

	public void updIsApprove(String id, int isapprove) throws Exception {
		String sql = "update stock_move set approvestatus=" + isapprove
				+ " where id ='" + id + "'";
		EntityManager.updateOrdelete(sql);
	}

	public List waitApproveStockMove(int pagesize, String pWhereClause,
			SimplePageInfo pPgInfo) throws Exception {
		int targetPage = pPgInfo.getCurrentPageNo();
		String sql = "select sm.id,sm.movedate,sm.outwarehouseid,sm.inwarehouseid,sm.makeid,sma.approve,sma.actid from StockMove as sm,StockMoveApprove as sma "
				+ pWhereClause + " order by sma.approve, sm.movedate desc";
		return EntityManager.getAllByHql(sql, targetPage, pagesize);
	}

	public void updIsAudit(String ppid, Integer userid, Integer audit)
			throws Exception {

		String sql = "update stock_move set isaudit=" + audit + ",auditid="
				+ userid + ",auditdate=sysdate where id='" + ppid + "'";
		EntityManager.updateOrdelete(sql);

	}

	public void blankout(String id, Long userid, Integer blankout,
			String blankoutreason) throws Exception {
		String sql = "update stock_move set isblankout=" + blankout
				+ ",blankoutid=" + userid + ",blankoutdate='"
				+ DateUtil.getCurrentDateTime() + "',blankoutreason='"
				+ blankoutreason + "' where id='" + id + "'";
		EntityManager.updateOrdelete(sql);
	}

	public List getStockMoveBillTotal(HttpServletRequest request, int pagesize,
			String whereSql) throws Exception {
		String sql = "select sm.id ,movedate,inorganid,makeorganid,sm.makedate,sum(quantity) as quantity,sum(boxnum) as boxnum,sum(scatternum) as scatternum  from Stock_Move sm , Stock_Move_detail smd "
				+ whereSql
				+ " group by sm.id,movedate,inorganid,makeorganid,sm.makedate";
		return PageQuery.jdbcSqlserverQuery(request, sql, pagesize);
	}

	public List getStockMoveBillTotal(String whereSql) throws Exception {
		String sql = "select sm.id ,movedate,inorganid,makeorganid,sm.makedate,sum(quantity) as quantity from Stock_Move sm , Stock_Move_detail smd "
				+ whereSql
				+ " group by sm.id,movedate,inorganid,makeorganid,sm.makedate";
		return EntityManager.jdbcquery(sql);
	}

	public Double getStockTotalSum(String whereSql) throws Exception {
		double quantity = 0d;
		String hql = "select sum(quantity) as quantity from Stock_Move_Detail smd, Stock_Move sm "
				+ whereSql;
		Map map = (Map) EntityManager.jdbcquery(hql).get(0);
		if (map.get("quantity") != null) {
			quantity = Double.valueOf(map.get("quantity").toString());
		}
		return quantity;
	}
	
	public int getStockMoveTotalBoxSum(String whereSql) throws HibernateException, SQLException {
		String hql = "select sum(boxnum) as boxnum   from Stock_Move_Detail as smd,Stock_Move as sm " + whereSql
				+ "";
		int quantity=0;
		Map map = (Map) EntityManager.jdbcquery(hql).get(0);
		if (map.get("boxnum") != null) {
			quantity = Integer.parseInt((map.get("boxnum").toString()));
		}
		return quantity;
		
	}
	
	public double getStockMoveTotalScatterSum(String whereSql) throws HibernateException, SQLException {
		double quantity=0d;
		String hql = "select sum(scatternum) as scatternum  from Stock_Move_Detail as smd,Stock_Move as sm  " + whereSql
				+ "";
		
		Map map = (Map) EntityManager.jdbcquery(hql).get(0);
		if (map.get("scatternum") != null) {
			quantity = Double.valueOf(map.get("scatternum").toString());
		}
		return quantity;
	}

	public List getStockMoveWarehouseTotal(HttpServletRequest request,
			int pagesize, String whereSql) throws Exception {
		String hql = "select sm.makeorganid,sm.outwarehouseid,sm.inorganid,sm.inwarehouseid,smd.unitid,smd.productid,smd.productname ,sum(smd.quantity) as quantity,sum(boxnum) as boxnum,sum(scatternum) as scatternum "
				+ " from stock_move sm , stock_move_detail smd "
				+ whereSql
				+ " group by sm.makeorganid,sm.outwarehouseid,sm.inorganid,sm.inwarehouseid,smd.productid,smd.unitid,smd.productname";
		return PageQuery.jdbcSqlserverQuery(request, "makeorganid", hql,
				pagesize);
	}

	public List getStockMoveWarehouseTotal(String whereSql) throws Exception {
		String hql = "select sm.makeorganid,sm.outwarehouseid,sm.inorganid,sm.inwarehouseid,smd.productid,smd.productname ,sum(smd.quantity) as quantity "
				+ " from stock_move sm , stock_move_detail smd "
				+ whereSql
				+ " group by sm.makeorganid,sm.outwarehouseid,sm.inorganid,sm.inwarehouseid,smd.productid,smd.productname";
		return EntityManager.jdbcquery(hql);
	}

	public Double getStockWarehouseTotalSum(String whereSql) {
		return null;
	}

	public List<StockMove> getStockMove(String whereSql) {
		String hql = "from StockMove as sm " + whereSql
				+ " order by sm.makedate desc ";
		return EntityManager.getAllByHql(hql);
	}

	public void delStockMoveByNCcode(String nccode) throws Exception {

		String sql = "delete from stock_move where NCcode='" + nccode + "'";
		EntityManager.updateOrdelete(sql);

	}


	/**
	 * 检验该单据是否已经复核过
	 * 
	 * @param nccode用友给出的编号
	 * @return result1为已经复核过0为未复核
	 */
	public boolean checkIsAudit(String nccode) {
		boolean result = false;
		String sql = " from StockMove where NCcode='" + nccode + "'";
		StockMove temp = new StockMove();
		temp = (StockMove) EntityManager.find(sql);
		if (temp.getIsaudit() == 0) {
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
		StockMove stockMove = new StockMove();
		stockMove = this.getStockMoveByID(id);
		result = stockMove.getNccode();
		return result;
	}

	/**
	 * 入库StockMove保存的方式
	 * 
	 * @param StockMove需要保存的实体对象
	 * @return replyInfo是否该次保存顺利执行及相关信息
	 */
	public static ReplyInfo saveStockMove(StockMove stockMove) {
		ReplyInfo replyInfo = new ReplyInfo();
		boolean flag = false;
		String info = "";
		AppStockMove appStockMove = new AppStockMove();
		try {
			HibernateUtil.currentTransaction();
			appStockMove.addStockMove(stockMove);
			HibernateUtil.commitTransaction();
			flag = true;
			log.info("------保存成功------" + flag);
		} catch (Exception e) {

			e.printStackTrace();
			info = e.toString();
			log.error("------StockAlterMove保存------" + e.toString());
		}
		replyInfo.setSaveFlag(flag);
		replyInfo.setErrorInfo(info);
		return replyInfo;

	}

	/**
	 * 入库StockMoveDetail保存的方式
	 * 
	 * @param stockMoveDetail需要保存的实体对象
	 * @return replyInfo是否该次保存顺利执行及相关信息
	 */

	public static ReplyInfo saveStockMoveDetail(StockMoveDetail stockMoveDetail) {

		ReplyInfo replyInfo = new ReplyInfo();
		boolean flag = false;
		String info = "";
		AppStockMoveDetail appStockMoveDetail = new AppStockMoveDetail();
		try {
			HibernateUtil.currentTransaction();
			appStockMoveDetail.addStockMoveDetail(stockMoveDetail);
			HibernateUtil.commitTransaction();
			flag=true;
			log.info("------StockAlterMoveDetail保存-----成功");
		} catch (Exception e) {

			e.printStackTrace();
			info = e.toString();
			log.error("------StockAlterMoveDetail保存------" + e.toString());
		}
		replyInfo.setSaveFlag(flag);
		replyInfo.setErrorInfo(info);
		return replyInfo;

	}

	/**
	 * 根据nccode使单据作废(入库)
	 * 
	 * @param nccode用友给出的id
	 * @param psnname批单人
	 */
	public ReplyInfo updateblankoutByNCcode(String nccode, String psnname)
			throws Exception {
		ReplyInfo replyInfo = new ReplyInfo();
		boolean flag = false;
		String info = "";
		StockMove temp = new StockMove();
		temp = this.getStockMoveByNCcode(nccode);
		// 如果查不到该条记录则返回报错xml
		if (temp == null) {
			flag = false;
			info = ResourceBundle.getBundle(
					"com.winsafe.drp.util.fileListener.UFIDA.ImportDataFormat")
					.getString("none_record");

		} else {
			// 判断该单据是否已记帐
			// 如果该条记录已经复核过则取消复核
			if (this.checkIsAudit(nccode)) {
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
				 * appidcode.delIdcodeByid(ic.getIdcode()); } List<OtherIncomeIdcode>
				 * piilist = aoidcode .getOtherIncomeIdcodeByoiid(temp.getId());
				 * for (OtherIncomeIdcode pii : piilist) { // RichieYu
				 * -----20100505 // 退还实际总数量
				 * aps.returninProductStockpileTotalQuantity(pii
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
				this.delStockMoveByNCcode(nccode);
				AppStockMoveIdcode aoidcode = new AppStockMoveIdcode();
				AppStockMoveDetail aoid = new AppStockMoveDetail();
				aoidcode.delStockMoveIDcodeByNCcode(nccode);
				aoid.delStockMoveDetailByNCcode(nccode);
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

	public static StockMove getStockMoveByNCcode(String nccode) throws Exception {
		String sql = " from StockMove where nccode='" + nccode + "'";
		return (StockMove) EntityManager.find(sql);
	}
	
	
	/**
	 * 对StockMove进行复核
	 */
	public static String auditStockMove(String smid, String userId) {
		String result="success";
		Users users = new Users();
		UsersBean usersBean = new UsersBean();
		AppUsers appUsers = new AppUsers();
		AppStockMove api = new AppStockMove(); 
		AppStockMoveDetail asamd = new AppStockMoveDetail();
		AppProductStockpileAll appps = new AppProductStockpileAll();
		AppFUnit appfu = new AppFUnit();
		
		try{
		StockMove pi = api.getStockMoveByID(smid);
		users = appUsers.getUsersByid(Integer.valueOf(userId));
		usersBean = UserManager.getBeanFromUsers(users);
		List<StockMoveDetail> dls = asamd.getStockMoveDetailBySmIDNew(smid);
		
		//是否复核过
		if (pi.getIsaudit() == 1) {
			result = ResourceBundle
			.getBundle(
					"com.winsafe.drp.util.fileListener.UFIDA.ImportDataFormat")
			.getString("have_auditted");
			return result;
		}
		//是否作废 1为作废
		if (pi.getIsblankout() == 1) {
			result = ResourceBundle
			.getBundle(
					"com.winsafe.drp.util.fileListener.UFIDA.ImportDataFormat")
			.getString("blank_out");
	return result;
		}
		//不是同一制单机构
		if (!pi.getMakeorganid().equals(users.getMakeorganid())) {
			result = ResourceBundle
			.getBundle(
					"com.winsafe.drp.util.fileListener.UFIDA.ImportDataFormat")
			.getString("different_makeorganid");
	return result;
		}
		
	
		for (StockMoveDetail sod : dls ) {				
			
			double q = appfu.getQuantity(sod.getProductid(), sod.getUnitid(), sod.getQuantity());
			double stock = appps.getProductStockpileAllByPIDWID(sod.getProductid(), pi.getOutwarehouseid());
			if (q > stock ) {
				result = ResourceBundle
				.getBundle(
						"com.winsafe.drp.util.fileListener.UFIDA.ImportDataFormat")
				.getString("out_of_quantity");
		return result;
			}
		}
		
		addTakeBill(pi, dls, usersBean);		
		
		api.updIsAudit(smid, Integer.valueOf(userId), 1);
		
		
		String[] param = new String[]{"name","applytime","billno"};
		String[] values = new String[]{pi.getOlinkman(), DateUtil.formatDate(pi.getMovedate()), pi.getId()};
		MsgService ms = new MsgService(param, values, usersBean, 11);
		ms.addmag(1,pi.getOtel());	
		
	
		DBUserLog.addUserLog(Integer.valueOf(userId), 4, "转仓>>复核转仓单,编号："+smid); 
		return result;
		} catch(Exception e){
			log.equals(e.toString());
			return "error";
		}
	}
	
	public static void addTakeBill(StockMove so, List<StockMoveDetail> pils, UsersBean users) throws Exception{
		OrganService ao = new OrganService(); 
		TakeBill takebill = new TakeBill();
		takebill.setId(so.getId());
		takebill.setBsort(2);
		takebill.setOid(so.getInorganid());
		takebill.setOname(ao.getOrganName(so.getInorganid()));
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
		for (StockMoveDetail pid : pils) {
			
			if (tsb.getTtmap().get(so.getOutwarehouseid()) == null) {
				tt = new TakeTicket();
				tt.setId(MakeCode.getExcIDByRandomTableName("take_ticket",2, "TT"));
				tt.setWarehouseid(so.getOutwarehouseid());
				tt.setBillno(takebill.getId());
				tt.setBsort(2);
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
//不控制批次			
//			TakeTicketDetailService ttds = new TakeTicketDetailService(tt,tt.getWarehouseid(),pid.getProductid(),
//					pid.getProductname(), pid.getSpecmode(), pid.getUnitid(), 0.00);
//			ttds.addBatchDetail(pid.getQuantity());
			
			TakeTicketDetail ttd = new TakeTicketDetail();
			ttd.setId(Integer.valueOf(MakeCode.getExcIDByRandomTableName(
					"take_ticket_detail", 0, "")));
			ttd.setProductid(pid.getProductid());
			ttd.setProductname(pid.getProductname());
			ttd.setSpecmode(pid.getSpecmode());
			ttd.setUnitid(pid.getUnitid());
			ttd.setBatch("");
			ttd.setUnitprice(0.00);
			ttd.setQuantity(pid.getQuantity());
			ttd.setTtid(tt.getId());			
			tt.getTtdetails().add(ttd);	
			
		}
		
		AppTakeService appts = new AppTakeService();
		appts.addTake(tsb, false);
	}
	
}
