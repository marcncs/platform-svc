package com.winsafe.drp.server;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.winsafe.drp.dao.AppFUnit;
import com.winsafe.drp.dao.AppIdcode;
import com.winsafe.drp.dao.AppOrgan;
import com.winsafe.drp.dao.AppOrganWithdraw;
import com.winsafe.drp.dao.AppOrganWithdrawDetail;
import com.winsafe.drp.dao.AppOrganWithdrawIdcode;
import com.winsafe.drp.dao.AppProduct;
import com.winsafe.drp.dao.AppProductStockpile;
import com.winsafe.drp.dao.AppProductStockpileAll;
import com.winsafe.drp.dao.AppStockMove;
import com.winsafe.drp.dao.AppStockMoveIdcode;
import com.winsafe.drp.dao.AppTakeTicket;
import com.winsafe.drp.dao.AppTakeTicketDetail;
import com.winsafe.drp.dao.AppTakeTicketIdcode;
import com.winsafe.drp.dao.AppWarehouse;
import com.winsafe.drp.dao.Organ;
import com.winsafe.drp.dao.OrganWithdraw;
import com.winsafe.drp.dao.OrganWithdrawDetail;
import com.winsafe.drp.dao.OrganWithdrawIdcode;
import com.winsafe.drp.dao.ProductStockpile;
import com.winsafe.drp.dao.StockAlterMoveIdcode;
import com.winsafe.drp.dao.StockMove;
import com.winsafe.drp.dao.StockMoveDetail;
import com.winsafe.drp.dao.StockMoveIdcode;
import com.winsafe.drp.dao.TakeTicket;
import com.winsafe.drp.dao.TakeTicketDetail;
import com.winsafe.drp.dao.TakeTicketIdcode;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.dao.Warehouse;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.MakeCode;
import com.winsafe.hbm.util.StringUtil;

/**
 * 渠道退货流程
 * 
 * @author Andy.liu CreateTime 2014-04-24 13:27:09
 * 
 */
public class OrganWithdrawService {
	private AppTakeTicketDetail attd = new AppTakeTicketDetail();
	private AppTakeTicketIdcode atti = new AppTakeTicketIdcode();
	private AppIdcode ai = new AppIdcode();
	private AppProductStockpile apsp = new AppProductStockpile();
	private AppTakeTicket att = new AppTakeTicket();
	private AppWarehouse aw = new AppWarehouse();
	private AppOrganWithdraw aow = new AppOrganWithdraw();
	private AppOrganWithdrawIdcode aowi = new AppOrganWithdrawIdcode();
	private AppOrganWithdrawDetail aowd = new AppOrganWithdrawDetail();
	private AppFUnit appfu = new AppFUnit();
	private AppProductStockpileAll appps = new AppProductStockpileAll();
	
	
	/**
	 * tti码的类型 小盒，中盒，外箱<br>
	 * key 为unitid<br>
	 * value 为CodeColumn
	 * 
	 */
	private static Map<Integer, String> map = new HashMap<Integer, String>();

	static {
		map.put(1, "idcode"); // unitid 为1 则是小盒码
		map.put(2, "cartonCode"); // unitid为2 则是外箱码
		map.put(4, "boxCode"); // unitid 为4 则是中盒码

	}
	
	 /**
	 * 检查库存
	 * @throws Exception 
	 */
	public boolean checkStock(OrganWithdraw ow,List<OrganWithdrawDetail> owdList) throws Exception{
		return checkStock(ow, owdList, null);
	}
	/**
	 * 检查库存
	 * @throws Exception 
	 */
	public boolean checkStock(OrganWithdraw ow,List<OrganWithdrawDetail> owdList,StringBuffer errorMsg) throws Exception{
		// 如果仓库属性[是否负库存]为0,则检查库存
		Warehouse outWarehouse = aw.getWarehouseByID(ow.getWarehouseid());
		if(outWarehouse.getIsMinusStock() == null || outWarehouse.getIsMinusStock() == 0){
			for (OrganWithdrawDetail owd : owdList)
			{
				double q = appfu.getQuantity(owd.getProductid(), owd.getUnitid(), owd.getQuantity());
				double stock = appps.getProductStockpileAllByPIDWID(owd.getProductid(), ow.getWarehouseid());
				// 库存大于实际数量
				if (q > stock)
				{
					//如果不须要错误信息则直接返回结果
					if(errorMsg == null){
						return false;
					}
					errorMsg.append("产品 [ " +owd.getProductid()+" "+ owd.getProductname() + " ] 库存不足<br/>");
				}
			}
		}
		// 判断错误信息是否为空
		if(errorMsg != null && errorMsg.length() >0 ){
			return false;
		}
		
		return true;
	}
	/**
	 * 生成检货出库相关单据
	 */
	public void addTakeTicket(OrganWithdraw so, List<OrganWithdrawDetail> pils,
			UsersBean users) throws Exception {
		AppTakeTicketDetail attd =new AppTakeTicketDetail();
		AppTakeTicket aptt = new AppTakeTicket();
		AppOrgan appOrgan = new AppOrgan();
		// 小票 TakeTicket
		TakeTicket tt = new TakeTicket();
		tt.setId(MakeCode.getExcIDByRandomTableName("take_ticket", 2,"TT"));
		so.setTtid(tt.getId());
		tt.setWarehouseid(so.getWarehouseid());
		tt.setBillno(so.getId());
		tt.setBsort(7);
		tt.setOid(so.getPorganid());
		tt.setOname(so.getPorganname());
		tt.setRlinkman(so.getPlinkman());
		tt.setTel(so.getTel());
		tt.setInwarehouseid(so.getInwarehouseid());
		tt.setRemark(so.getWithdrawcause());
		tt.setIsaudit(0);
		tt.setMakeorganid(users.getMakeorganid());
		tt.setMakedeptid(users.getMakedeptid());
		tt.setMakeid(users.getUserid());
		tt.setMakedate(DateUtil.getCurrentDate());
		tt.setPrinttimes(0);
		tt.setIsblankout(0);
		//入库机构编号
		tt.setInOid(so.getReceiveorganid());
		//入库机构名称
		Organ inOrgan = appOrgan.getOrganByID(tt.getInOid());
		if(inOrgan != null){
			tt.setInOname(inOrgan.getOrganname());
		}
		
		// 有明细TakeTicketDetail
		for (int i = 0; i < pils.size(); i++) {
			OrganWithdrawDetail pid = (OrganWithdrawDetail) pils.get(i);
			TakeTicketDetail ttd = new TakeTicketDetail();
			ttd.setId(Integer.valueOf(MakeCode.getExcIDByRandomTableName("take_ticket_detail", 0, "")));
			ttd.setProductid(pid.getProductid());
			ttd.setProductname(pid.getProductname());
			ttd.setSpecmode(pid.getSpecmode());
			ttd.setUnitid(pid.getUnitid());
			ttd.setBatch(pid.getBatch());
			ttd.setUnitprice(pid.getUnitprice());
			ttd.setQuantity(pid.getQuantity());
			ttd.setBoxnum(pid.getBoxnum());
			ttd.setScatternum(pid.getScatternum());
			ttd.setTtid(tt.getId());
			attd.addTakeTicketDetail(ttd);
		}
		aptt.addTakeTicket(tt);
	}
	
	
	
	/**
	 * 复核退货单流程
	 * 
	 * @param tt
	 * @throws Exception
	 */
	public void auditOrganWithdraw(TakeTicket tt, int userid) throws Exception {
		String returnStr = "";
		/*
		 * 复核退货单 1，更新条码表为未使用及已出库 isuse = 0 and isout = 1 2，更新小票状态为已复核 3，更新库存表
		 * 4，新增码到退货单码表中 5，修改idcode码的状态：退还码仓库，isuse为1 isout为0 6，修改退货单状态为已完成
		 */

		// 出库单详情
		List<TakeTicketDetail> ttdList = attd.getTakeTicketDetailByTtid(tt
				.getId());
		// 出库单码 (此处为退货单码，可能为小盒码，中盒码，外箱码，)
		List<TakeTicketIdcode> ttiList = atti.getTakeTicketIdcodeByttid(tt
				.getId());

		// 根据出库单码找IDCODE表中的条码，更新条码标为未使用和已出库 isuse = 0 and isout = 1
		this.updIdcodeByTTI(ttiList);

		// 更新小票状态为已复核
		att.updIsAudit(tt.getId(), userid, 1);
		// 单据状态改为1
		aow.updOrganWithdrawTakestatus(tt.getBillno(), 1);
		// 更新库存表 减 出库仓库库存
		// this.outProductStockpile(ttiList, tt.getWarehouseid());
		this.outProductStockpileTTD(ttdList, tt.getWarehouseid());

		// 新增码到退货单码表中
		// 自动签收
		// 找到入库仓库判断是否是自动签收
		Warehouse whs = aw.getWarehouseByID(tt.getInwarehouseid());

		if (whs.getIsautoreceive() == 1) { // 自动签收
			OrganWithdraw ow = aow.getOrganWithdrawByID(tt.getBillno());
			// 新增退货详情和退货码表
			this.addOrganWithdrawDetailAndIdcode(ow.getId(), ttdList, ttiList);
			// 修改idcode码的状态：退还码仓库，isuse为1 isout为0
			addIdcodeOW(ow, ttiList);
			// 修改退货单状态为已完成
			aow.updOrganWithdrawIsReceive(ow.getId(), 1, userid);
			// 更新库存表 加 入库仓库库存
			// this.inProductStockpile(ttiList, tt.getInwarehouseid());
			this.addProductStockpileOWByDetail(ttdList, tt.getInwarehouseid());
			DBUserLog.addUserLog(userid, 7, "入库>>渠道退货>>退货入库,编号：" + ow.getId());
		}

	}

	private void outProductStockpileTTD(List<TakeTicketDetail> ttdList,
			String outWarehouseid) throws Exception {
		for (TakeTicketDetail ttd : ttdList) {
			// 减出库仓库库存
			apsp.outProductStockpile(ttd.getProductid(), ttd.getBatch(), ttd
					.getQuantity(), outWarehouseid, "000", ttd.getTtid(),
					"检货小票-出货", ttd.getUnitid(),true);
		}
	}

	private void addProductStockpileOWByDetail(List<TakeTicketDetail> ttdList,
			String inWarehouseid) throws Exception {
		AppProductStockpile aps = new AppProductStockpile();
		AppProduct ap = new AppProduct();
		ProductStockpile ps = null;
		for (TakeTicketDetail ttd : ttdList) {
			ps = aps.getProductStockpileByProductIDUnitWid(ttd.getProductid(),
					ap.getProductByID(ttd.getProductid()).getCountunit(),
					inWarehouseid, "000");
			if (ps == null) {
				ps = new ProductStockpile();

				ps.setId(Long.valueOf(MakeCode.getExcIDByRandomTableName(
						"product_stockpile", 0, "")));
				ps.setProductid(ttd.getProductid());

				ps.setCountunit(ap.getProductByID(ps.getProductid())
						.getCountunit());
				ps.setWarehouseid(inWarehouseid);
				ps.setWarehousebit("000");
				ps.setMakedate(DateUtil.StringToDatetime(DateUtil
						.getCurrentDateTime()));
				// 默认为合格状态
				ps.setVerifyStatus(1);
				aps.addProductByPurchaseIncome(ps);
			}
			aps.inProductStockpile(ps.getProductid(), ttd.getBatch(), ttd
					.getQuantity(), ps.getWarehouseid(), ps.getWarehousebit(),
					ttd.getTtid(), "退货入库-入库",ttd.getUnitid(), true);
		}
	}

	/**
	 * 根据出库单码找IDCODE表中的条码，<br>
	 * 更新条码标为未使用和已出库 isuse = 0 and isout = 1
	 * 
	 * @param list
	 * @throws Exception
	 */
	private void updIdcodeByTTI(List<TakeTicketIdcode> list) throws Exception {
		int isuse = 0, isout = 1;
		for (TakeTicketIdcode tti : list) {
			Integer unitid = tti.getUnitid();
			// 根据unitid获取码的类型
			String codeColumn = map.get(unitid);
			if (StringUtil.isEmpty(codeColumn)) {
				return;
			}
			// 修改码状态
			ai.updIdcodeUseByCodeColumn(codeColumn, isuse, isout, tti
					.getIdcode());
		}
	}

	/**
	 * 更新库存
	 * 
	 * @param idlist
	 *            taketicket的idcode
	 * @param warehouseid
	 *            出库仓库号
	 * @throws Exception
	 */
	private void outProductStockpile(List<TakeTicketIdcode> idlist,
			String outWarehouseid) throws Exception {
		/*
		 * 退货仓库无需判断是否存在库存，入库时已经新增过库存，且判断过库存是否足够
		 */
		for (TakeTicketIdcode idcode : idlist) {
			// 减 出库仓库库存
			apsp.outProductStockpile(idcode.getProductid(), idcode.getUnitid(),
					idcode.getBatch(), idcode.getQuantity(), outWarehouseid,
					idcode.getWarehousebit(), idcode.getTtid(), "检货小票-出货");
		}
	}

	/**
	 * 更新库存
	 * 
	 * @param idlist
	 *            taketicket的idcode
	 * @param warehouseid
	 *            出库仓库号
	 * @throws Exception
	 */
	private void inProductStockpile(List<TakeTicketIdcode> idlist,
			String inWarehouseid) throws Exception {
		/*
		 * , 退货仓库无需判断是否存在库存，入库时已经新增过库存，且判断过库存是否足够
		 */
		for (TakeTicketIdcode idcode : idlist) {
			// 加 入库仓库库存
			apsp.inProductStockpileTotalQuantity(idcode.getProductid(), idcode
					.getUnitid(), idcode.getBatch(), idcode.getQuantity(),
					inWarehouseid, idcode.getWarehousebit(), idcode.getTtid(),
					"检货小票-入库");
		}
	}

	/**
	 * 新增退货单详情和码
	 * 
	 * @param ttdList
	 *            出库单详情列表
	 * @param ttiList
	 *            出库单码列表
	 * @throws Exception
	 */
	private void addOrganWithdrawDetailAndIdcode(String owid,
			List<TakeTicketDetail> ttdList, List<TakeTicketIdcode> ttiList)
			throws Exception {
		OrganWithdrawDetail owd = null;
		OrganWithdrawIdcode owi = null;
		// 新增码表
		for (TakeTicketIdcode tti : ttiList) {
			OrganWithdrawIdcode owdi = aowi.getOrganWithdrawIdcodeByidcode(tti
					.getProductid(), owid, tti.getIdcode());
			// 当条码不存在时,则新增
			if (owdi == null) {
				Integer id = Integer.valueOf(MakeCode
						.getExcIDByRandomTableName("organ_withdraw_idcode", 0,
								""));
				owi = new OrganWithdrawIdcode();
				owi.setId(id.longValue());
				owi.setBatch(tti.getBatch());
				owi.setIdcode(tti.getIdcode());
				owi.setIsidcode(tti.getIsidcode());
				owi.setMakedate(DateUtil.getCurrentDate());
				owi.setOwid(owid);
				owi.setPackquantity(tti.getPackquantity());
				owi.setProducedate(tti.getProducedate());
				owi.setProductid(tti.getProductid());
				owi.setQuantity(tti.getQuantity());
				owi.setLcode(tti.getLcode());
				owi.setUnitid(tti.getUnitid());
				owi.setWarehousebit(tti.getWarehousebit());
				aowi.addOrganWithdrawIdcode(owi);
			}
		}
		// 新增详情
		for (TakeTicketDetail ttd : ttdList) {
			// 当详情存在时,不新增
			OrganWithdrawDetail owdd = aowd.getOrganWithdrawDetailByOwidPid(
					owid, ttd.getProductid());
			if (owdd == null) {
				Integer id = Integer.valueOf(MakeCode
						.getExcIDByRandomTableName("organ_withdraw_detail", 0,
								""));
				owd = new OrganWithdrawDetail();
				owd.setId(id);
				owd.setBatch(ttd.getBatch());
				owd.setOwid(owid);
				owd.setProductid(ttd.getProductid());
				owd.setProductname(ttd.getProductname());
				owd.setQuantity(ttd.getQuantity());
				owd.setRatifyquantity(ttd.getQuantity());
				owd.setUnitid(ttd.getUnitid());
				owd.setUnitprice(ttd.getUnitprice());
				owd.setSpecmode(ttd.getSpecmode());
				owd.setScatternum(ttd.getScatternum());
				owd.setSubsum(owd.getQuantity() * owd.getUnitprice());
				owd.setTakequantity(owd.getQuantity());
				owd.setBoxnum(0);
				aowd.save(owd);
			}
		}

	}

	/**
	 * 修改idcode的状态 退还码仓库，isuse为1 isout为0
	 * 
	 * @param ow
	 * @param owList
	 * @throws Exception
	 */
	public void addIdcodeOW(OrganWithdraw ow, List<TakeTicketIdcode> owList)
			throws Exception {
		int isuse = 1, isout = 0;
		for (TakeTicketIdcode tti : owList) {
			String codeColumn = map.get(tti.getUnitid());
			ai.updIdcodeByCodeColumn(tti.getIdcode(), ow.getReceiveorganid(),
					ow.getInwarehouseid(), "000", codeColumn, isuse, isout);
		}
	}
	
	
	

}
