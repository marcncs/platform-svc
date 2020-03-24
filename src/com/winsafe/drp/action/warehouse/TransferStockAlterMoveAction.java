package com.winsafe.drp.action.warehouse;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppFUnit;
import com.winsafe.drp.dao.AppIdcode;
import com.winsafe.drp.dao.AppProduct;
import com.winsafe.drp.dao.AppProductStockpile;
import com.winsafe.drp.dao.AppStockAlterMove;
import com.winsafe.drp.dao.AppStockAlterMoveDetail;
import com.winsafe.drp.dao.AppStockAlterMoveIdcode;
import com.winsafe.drp.dao.AppTakeTicket;
import com.winsafe.drp.dao.AppTakeTicketDetail;
import com.winsafe.drp.dao.AppTakeTicketIdcode;
import com.winsafe.drp.dao.AppWarehouse;
import com.winsafe.drp.dao.StockAlterMove;
import com.winsafe.drp.dao.StockAlterMoveDetail;
import com.winsafe.drp.dao.StockAlterMoveIdcode;
import com.winsafe.drp.dao.TakeTicket;
import com.winsafe.drp.dao.TakeTicketDetail;
import com.winsafe.drp.dao.TakeTicketIdcode;
import com.winsafe.drp.dao.Warehouse;
import com.winsafe.drp.server.OrganService;
import com.winsafe.drp.server.StockAlterMoveService;
import com.winsafe.drp.util.Constants;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.MakeCode;
import com.winsafe.hbm.util.StringUtil;
import com.winsafe.notification.dao.AppNotification;
import com.winsafe.sap.metadata.YesOrNo;

public class TransferStockAlterMoveAction extends BaseAction {
	private static Logger logger = Logger.getLogger(TransferStockAlterMoveAction.class);
	private AppStockAlterMove appStockAlterMove = new AppStockAlterMove();
	private AppStockAlterMoveDetail appStockAlterMoveDetail = new AppStockAlterMoveDetail();
	private AppStockAlterMoveIdcode appStockAlterMoveIdcode = new AppStockAlterMoveIdcode();
	private AppTakeTicketDetail apid = new AppTakeTicketDetail();
	private AppTakeTicket apb = new AppTakeTicket();
	
	private AppTakeTicketIdcode apidcode = new AppTakeTicketIdcode();
	private AppIdcode appidcode = new AppIdcode();
	
	private AppProductStockpile apsp = new AppProductStockpile();
	
	private StockAlterMoveService stockAlterMoveService = new StockAlterMoveService();
	private AppWarehouse appWarehouse = new AppWarehouse();
	private AppFUnit appFunit = new AppFUnit();
	private AppProduct appProduct = new AppProduct();
	
	//DAO层类
	OrganService ao = new OrganService();
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			{
		
		//初始化
		initdata(request);
		
		String samid = request.getParameter("samid");
		String oldWarehouseId = request.getParameter("oldinwarehouseid");
		
		try {
			
			StockAlterMove source = appStockAlterMove.getStockAlterMoveByID(samid);
			//得到TT明细
			TakeTicket sourceTt = apb.getTakeTicket(samid);
			List<TakeTicketDetail> sourceTtds = apid.getTakeTicketDetailByTtid(sourceTt.getId());
			//签收原订购单
			if(source.getIscomplete() == 0) {
				if (source.getIsshipment() == 0)
				{
					request.setAttribute("result", "databases.record.noshipment");
					return new ActionForward("/sys/lockrecordclose.jsp");
				}
				if (source.getIsblankout() == 1)
				{
					request.setAttribute("result", "datebases.record.isblankout");
					return new ActionForward("/sys/lockrecordclose.jsp");
				}
				//先更新种子单据入库仓库
				if(StringUtil.isEmpty(source.getInwarehouseid()) && !StringUtil.isEmpty(oldWarehouseId)) {
					source.setInwarehouseid(oldWarehouseId);
					sourceTt.setInwarehouseid(oldWarehouseId);
					apb.updTakeTicket(sourceTt);
				}
				//签收单据
				receipt(samid, sourceTt, sourceTtds,source);
				
				if(!StringUtil.isEmpty(source.getNccode())) {
					AppNotification appN = new AppNotification();
					appN.delNotificationLog(source.getNccode().replaceAll("^(0+)", ""));
				}
			}
			
			//转移到目标订购单
			if(source != null) {
				StockAlterMove target = new StockAlterMove();
				String smid = MakeCode.getExcIDByRandomTableName("stock_alter_move", 2, "OM");
				target.setId(smid);
				target.setMovedate(DateUtil.StringToDate(request.getParameter("movedate")));
				target.setOutwarehouseid(source.getInwarehouseid());
				target.setInwarehouseid(request.getParameter("inwarehouseid"));
				target.setTransportaddr(request.getParameter("transportaddr"));
				target.setOlinkman(request.getParameter("olinkman"));
				target.setOtel(request.getParameter("otel"));
				target.setIsmaketicket(YesOrNo.NO.getValue());
				target.setIsreceiveticket(YesOrNo.NO.getValue());
				//转移原因
				target.setMovecause(request.getParameter("movecause"));
				//是否复核, 复核日期， 复核人
				target.setIsaudit(YesOrNo.YES.getValue());
				target.setAuditdate(DateUtil.StringToDatetime(DateUtil.getCurrentDateTime()));
				target.setAuditid(userid);
				//制单人,机构，部门，日期
				target.setMakeorganid(users.getMakeorganid());
				target.setMakeorganidname(ao.getOrganName(users.getMakeorganid()));
				target.setMakedeptid(users.getMakedeptid());
				target.setMakeid(userid);
				target.setMakedate(DateUtil.StringToDatetime(DateUtil.getCurrentDateTime()));
				//是否出库
				target.setIsshipment(YesOrNo.YES.getValue());
				//是否记账
				target.setIstally(YesOrNo.YES.getValue());
				//是否作废
				target.setIsblankout(YesOrNo.NO.getValue());
				target.setReceiveorganid(request.getParameter("receiveorganid"));
				target.setReceiveorganidname(request.getParameter("oname"));
				//是否完成
				target.setIscomplete(YesOrNo.NO.getValue());
				//出库机构编号
				target.setOutOrganId(source.getReceiveorganid());
				//出库机构名称
				target.setOutOrganName(source.getReceiveorganidname());
				//是否是整单销售单
				target.setIsTransferred(YesOrNo.YES.getValue());
				StringBuffer keyscontent = new StringBuffer();
				keyscontent.append(target.getId()).append(",").append(target.getOlinkman())
						.append(",").append(target.getOtel()).append(",").append(
								target.getMakeorganidname());
				List<StockAlterMoveDetail> targetDetails  = new ArrayList<StockAlterMoveDetail>();
				List<StockAlterMoveDetail> sourceDetails = appStockAlterMoveDetail.getStockAlterMoveDetailBySamID(samid);
				
				for(StockAlterMoveDetail sourceDetail : sourceDetails) {
					StockAlterMoveDetail stockAlterMoveDetail = new StockAlterMoveDetail();
					stockAlterMoveDetail.setId(Integer.valueOf(MakeCode.getExcIDByRandomTableName("stock_alter_move_detail", 0, "")));
					stockAlterMoveDetail.setSamid(target.getId());
					stockAlterMoveDetail.setProductname(sourceDetail.getProductname());
					stockAlterMoveDetail.setProductid(sourceDetail.getProductid());
					stockAlterMoveDetail.setNccode(sourceDetail.getNccode());
					stockAlterMoveDetail.setSpecmode(sourceDetail.getSpecmode());
					stockAlterMoveDetail.setUnitid(sourceDetail.getUnitid());
					//订购不选择批次
					stockAlterMoveDetail.setBatch(sourceDetail.getBatch());
					stockAlterMoveDetail.setQuantity(sourceDetail.getQuantity());
					stockAlterMoveDetail.setTakequantity(0d);
					stockAlterMoveDetail.setMakedate(DateUtil.getCurrentDate());
					
					targetDetails.add(stockAlterMoveDetail);
					appStockAlterMoveDetail.addStockAlterMoveDetail(stockAlterMoveDetail);
				}
				
				List<StockAlterMoveIdcode> sourcesIdcodes = appStockAlterMoveIdcode.getStockAlterMoveIdcodeBysamid(samid);
				
				for(StockAlterMoveIdcode sourceIdcode : sourcesIdcodes) {
					StockAlterMoveIdcode targetIdcode = new StockAlterMoveIdcode();
//					targetIdcode.setId(Long.valueOf(MakeCode.getExcIDByRandomTableName("stock_alter_move_idcode", 0, "")));
					targetIdcode.setSamid(target.getId());
					targetIdcode.setProductid(sourceIdcode.getProductid());
					targetIdcode.setIsidcode(sourceIdcode.getIsidcode());
					targetIdcode.setWarehousebit(sourceIdcode.getWarehousebit());
					targetIdcode.setBatch(sourceIdcode.getBatch());
					targetIdcode.setValidate(sourceIdcode.getValidate());
					targetIdcode.setUnitid(sourceIdcode.getUnitid());
					targetIdcode.setQuantity(sourceIdcode.getQuantity());
					targetIdcode.setPackquantity(sourceIdcode.getPackquantity());
					targetIdcode.setMakedate(DateUtil.getCurrentDate());
					//内部编号
					targetIdcode.setNccode(sourceIdcode.getNccode());
					//设置箱码(条码)
					targetIdcode.setIdcode(sourceIdcode.getIdcode());
					targetIdcode.setProducedate(sourceIdcode.getProducedate());
					appStockAlterMoveIdcode.addStockAlterMoveIdcode(targetIdcode);
				}
				
				target.setTakestatus(YesOrNo.YES.getValue());
				target.setKeyscontent(keyscontent.toString());
				appStockAlterMove.addStockAlterMove(target);
				
				TakeTicket takeTicket = addTakeTicket(target);
				List<TakeTicketDetail> ttds = addTakeTicketDetails(sourceTtds, takeTicket);
				
				List<TakeTicketIdcode> ttiList = new ArrayList<TakeTicketIdcode>();
				List<TakeTicketIdcode> ttids = apidcode.getTakeTicketIdcodeByttid(source.getTtid());
				for(TakeTicketIdcode tt : ttids) {
					TakeTicketIdcode pi = new TakeTicketIdcode();
//					pi.setId(Long.valueOf(MakeCode.getExcIDByRandomTableName("take_ticket_idcode", 0, "")));
					pi.setTtid(takeTicket.getId());
					pi.setProductid(tt.getProductid());
					pi.setIsidcode(tt.getIsidcode());
					pi.setWarehousebit(tt.getWarehousebit());
					pi.setBatch(tt.getBatch());
					pi.setVad(tt.getVad());
					pi.setUnitid(tt.getUnitid());
					pi.setQuantity(tt.getQuantity());
					pi.setPackquantity(tt.getPackquantity());
					pi.setMakedate(DateUtil.getCurrentDate());
					//设置箱码
					pi.setIdcode(tt.getIdcode());
					pi.setProducedate(tt.getProducedate());
					apidcode.addTakeTicketIdcode(pi);
					ttiList.add(pi);
				} 
				
				// 修改检货数量
				for(TakeTicketDetail ttd : ttds){
					apid.updTakeQuantity(takeTicket.getBsort(), takeTicket.getBillno(), ttd.getProductid(), ttd.getRealQuantity());
				}
				// 更新小票状态为已复核
				Date currentDate = DateUtil.getCurrentDate();
				takeTicket.setIsaudit(1);
				takeTicket.setAuditid(users.getUserid());
				takeTicket.setAuditdate(currentDate);
				//更新单据已检货
				apb.updTakeStatus(takeTicket.getBsort(), takeTicket.getBillno(), 1);
				//更新单据已出库
				apb.updShipment(takeTicket.getBsort(), takeTicket.getBillno(), 1,users.getUserid(), DateUtil.formatDateTime(currentDate));
				// 扣减出库仓库库存
				outProductStockpileTTD(ttds, ttiList, takeTicket.getWarehouseid());
				//更新条码为出库状态
				appidcode.updIdcodeByttid(takeTicket.getId(), 0, 1);
				DBUserLog.addUserLog(users.getUserid(), "检货出库", "检货出库>>复核检货小票,编号：" + takeTicket.getId());
				
				//签收
				Warehouse inWarehouse = appWarehouse.getWarehouseByID(takeTicket.getInwarehouseid());
				if(inWarehouse.getIsautoreceive() == 1){
					//更新单据已完成
					apb.updIsComplete(takeTicket.getBsort(), takeTicket.getBillno(), 1, users.getUserid());
					inProductStockpileTTD(takeTicket.getBillno(),ttds, ttiList,takeTicket.getInwarehouseid(), Constants.TT_WASTE_BOOK_MEMO[takeTicket.getBsort()]);
					//更新条码为可用状态
					appidcode.updIdcodeByttid(takeTicket.getId(), 1, 0);
					appidcode.updIdcodeByttid(takeTicket.getId(), takeTicket.getInwarehouseid());
					// 如果为发货单时更新签收数量
					if(takeTicket != null && takeTicket.getBsort() == 1){
						updateStockAlterMoveReceive(takeTicket.getBillno(), ttds);
					}
					//记录签收日志
					addUserlog(takeTicket.getBsort(),takeTicket.getBillno(), users.getUserid());
				}
				
			}
			DBUserLog.addUserLog(request, "列表");
			request.setAttribute("result", "databases.transfer.success");
		} catch (Exception e) {
			logger.error(e);
			request.setAttribute("result", "databases.transfer.failed");
		}
		return mapping.findForward("result");
	}
	
	private List<TakeTicketDetail> addTakeTicketDetails(List<TakeTicketDetail> sourceTtds, TakeTicket targetTt) throws Exception {
		List<TakeTicketDetail> targetTtds = new ArrayList<TakeTicketDetail>();
		for (TakeTicketDetail samd : sourceTtds)
		{
			TakeTicketDetail ttd = new TakeTicketDetail();
			ttd.setId(Integer.valueOf(MakeCode.getExcIDByRandomTableName("take_ticket_detail", 0, "")));
			ttd.setProductid(samd.getProductid());
			ttd.setProductname(samd.getProductname());
			ttd.setSpecmode(samd.getSpecmode());
			ttd.setUnitid(samd.getUnitid());
			ttd.setNccode(samd.getNccode());
			ttd.setBatch(samd.getBatch());
			ttd.setUnitprice(samd.getUnitprice());
			//数量
			ttd.setQuantity(samd.getQuantity());
			ttd.setRealQuantity(samd.getRealQuantity());
			//箱数
			ttd.setBoxnum(samd.getBoxnum());
			//散数
			ttd.setScatternum(samd.getScatternum());
			ttd.setTtid(targetTt.getId());
			ttd.setIsPicked(0);
			apid.addTakeTicketDetail(ttd);
			targetTtds.add(ttd);
		}
		return targetTtds;
	}

	private TakeTicket addTakeTicket(StockAlterMove target) throws Exception {
		TakeTicket tt = new TakeTicket();
		tt.setId(MakeCode.getExcIDByRandomTableName("take_ticket", 2, "TT"));
		target.setTtid(tt.getId());
		tt.setWarehouseid(target.getOutwarehouseid());
		tt.setBillno(target.getId());
		tt.setBsort(1);
		tt.setOid(target.getOutOrganId());
		tt.setOname(target.getOutOrganName());
		tt.setRlinkman(target.getOlinkman());
		tt.setTel(target.getOtel());
		tt.setRemark(target.getRemark());
		tt.setIsaudit(0);
		tt.setIsmove(0);
		tt.setMakeorganid(users.getMakeorganid());
		tt.setMakedeptid(users.getMakedeptid());
		tt.setMakeid(users.getUserid());
		tt.setMakedate(DateUtil.getCurrentDate());
		tt.setInwarehouseid(target.getInwarehouseid());
		tt.setPrinttimes(0);
		tt.setIsblankout(0);
		//设置车号和路线
		tt.setBusNo(target.getBusNo());
		tt.setBusWay(target.getBusWay());
		//设置配送单号
		tt.setNccode(target.getNccode());
		tt.setNccode2(target.getNccode2());
		//设置未检货
		tt.setIsPicked(0);
		//设置未验货
		tt.setIsChecked(0);
		//入库机构编号
		tt.setInOid(target.getReceiveorganid());
		//入库机构名称
		tt.setInOname(target.getReceiveorganidname());
		apb.addTakeTicket(tt);
		return tt;
	}

	/**
	 * 更新发货单的签收数量
	 * Create Time 2014-12-25 下午02:30:14 
	 */
	private void updateStockAlterMoveReceive(String billno,List<TakeTicketDetail> ttds) throws Exception{
		for(TakeTicketDetail ttd : ttds){
			appStockAlterMoveDetail.updReceiveQuantity(billno, ttd.getProductid(), ttd.getRealQuantity());
		}
	}
	/**
	 * 根据检货详情及条码扣减库存
	 * @param ttdList
	 * @param ttiList
	 * @param outWarehouseid
	 * @throws Exception
	 */
	private void outProductStockpileTTD(List<TakeTicketDetail> ttdList,List<TakeTicketIdcode> ttiList,String outWarehouseid) throws Exception {
		Map<String, Double> idcodeBatchMap = collectIdcode(ttiList);
		outProductStockpileTTD(ttdList, idcodeBatchMap, outWarehouseid);
	}
	
	private void outProductStockpileTTD(List<TakeTicketDetail> ttdList,Map<String, Double> idcodeBatchMap,String outWarehouseid) throws Exception {
		// 根据批次减出库仓库库存
		for (TakeTicketDetail ttd : ttdList) {
			//总数量
			Double quantity = appFunit.getQuantity(ttd.getProductid(), ttd.getUnitid(), ttd.getRealQuantity());
			Integer unitid = appProduct.getProductByID(ttd.getProductid()).getSunit();
			// 先扣减所有批次的库存
			for(String key : idcodeBatchMap.keySet()){
				String[] objs = key.split("_");
				String pid = objs[0];
				String batch = objs[1];
				if(pid.equals(ttd.getProductid())){
					// 批次的数量
					Double batchQuantity = idcodeBatchMap.get(key);
					// 扣减对应批次库存
					apsp.outProductStockpile(pid, batch, batchQuantity, outWarehouseid, "000", ttd.getTtid(),
							"检货小票-出货", unitid,true);
					quantity = quantity - batchQuantity;
				}
			}
			// 如果quantity不为0,则对应扣减特殊批次的库存
			if(quantity != 0){
				apsp.outProductStockpile(ttd.getProductid(), Constants.NO_BATCH, quantity, outWarehouseid, "000", ttd.getTtid(),
						"检货小票-出货", unitid,true);
			}
			
		}
	}
	/**
	 * 根据条码汇总批次数量
	 * Create Time 2014-11-18 下午03:35:11 
	 * @param ttiList
	 * @return
	 */
	private Map<String, Double> collectIdcode(List<TakeTicketIdcode> ttiList){
		Map<String, Double> idcodeBatchMap = new HashMap<String, Double>();
		for(TakeTicketIdcode tti : ttiList){
			if(tti == null) {
				continue;
			}
			String pid = tti.getProductid();
			String batch = tti.getBatch();
			String key = pid + "_" + batch;
			if(StringUtil.isEmpty(batch)){
				continue;
			}
			Double quantity = idcodeBatchMap.get(key);
			if(quantity == null){
				idcodeBatchMap.put(key, tti.getPackquantity());
			}else {
				idcodeBatchMap.put(key, quantity + tti.getPackquantity());
			}
		}
		return idcodeBatchMap;
	}
	
	
	/**
	 * 记录签收入库日志
	 * Create Time 2014-10-16 下午05:22:37 
	 * @param bsort
	 * @param billno
	 * @throws Exception
	 */
	private void addUserlog(Integer bsort,String billno, Integer userid) throws Exception{
		// bsort 0|订购 
		if(bsort == 0){
			DBUserLog.addUserLog(userid, "出入库管理", "入库>>订购入库>>签收订购入库,编号：" + billno);
			
		}else {
			DBUserLog.addUserLog(userid, "出入库管理", "入库,编号：" + billno);
		}
	}
	
	/**
	 * 签收单据
	 * Create Time 2014-11-13 下午05:22:37 
	 * @param stockAlterMoveId
	 * @param sourceTtds 
	 * @param sourceTt 
	 * @param source 
	 * @param oldWarehouseId 
	 * @throws Exception
	 */
	private void receipt(String stockAlterMoveId, TakeTicket sourceTt, List<TakeTicketDetail> sourceTtds, StockAlterMove source) throws Exception{
		//得到TT明细
//		TakeTicket tt = apb.getTakeTicket(stockAlterMoveId);
//		List<TakeTicketDetail> ttds = apid.getTakeTicketDetailByTtid(tt.getId());
		List<TakeTicketIdcode> ttiList = apidcode.getTakeTicketIdcodeByttid(sourceTt.getId());
		//更新单据已完成
		source.setIscomplete(1);
		source.setReceivedate(DateUtil.getCurrentDate());
		source.setReceiveid(users.getUserid());
		appStockAlterMove.updstockAlterMove(source);
//		apb.updIsComplete(sourceTt.getBsort(), sourceTt.getBillno(), 1, users.getUserid());
		inProductStockpileTTD(sourceTt.getBillno(),sourceTtds, ttiList,sourceTt.getInwarehouseid(), Constants.TT_WASTE_BOOK_MEMO[sourceTt.getBsort()]);
		//更新条码为可用状态
		appidcode.updIdcodeByttid(sourceTt.getId(), 1, 0);
		appidcode.updIdcodeByttid(sourceTt.getId(), sourceTt.getInwarehouseid());
		// 如果为发货单时更新签收数量
		if(sourceTt != null && sourceTt.getBsort() == 1){
			updateStockAlterMoveReceive(sourceTt.getBillno(), sourceTtds);
		}
		//记录签收日志
		addUserlog(sourceTt.getBsort(),sourceTt.getBillno(), users.getUserid());
		
	}
	
	/**
	 * 根据条码中的批次增加库存
	 * @param ttdList
	 * @param ttiList
	 * @param inWarehouseid
	 * @param memo
	 * @throws Exception
	 */
	private void inProductStockpileTTD(String billNo,List<TakeTicketDetail> ttdList,List<TakeTicketIdcode> ttiList,String inWarehouseid,String memo) throws Exception {
		Map<String, Double> idcodeBatchMap = collectIdcode(ttiList);
		inProductStockpileTTD(billNo,ttdList, idcodeBatchMap, inWarehouseid, memo);
	}
	
	private void inProductStockpileTTD(String billNo,List<TakeTicketDetail> ttdList,Map<String, Double> idcodeBatchMap,String inWarehouseid,String memo) throws Exception {
		// 根据批次增加出库仓库库存
		for (TakeTicketDetail ttd : ttdList) {
			//总数量
			Double quantity = appFunit.getQuantity(ttd.getProductid(), ttd.getUnitid(), ttd.getRealQuantity());
			Integer unitid = appProduct.getProductByID(ttd.getProductid()).getSunit();
			// 先增加所有批次的库存
			for(String key : idcodeBatchMap.keySet()){
				String[] objs = key.split("_");
				String pid = objs[0];
				String batch = objs[1];
				if(pid.equals(ttd.getProductid())){
					// 批次的数量
					Double batchQuantity = idcodeBatchMap.get(key);
					// 增加对应批次库存
					apsp.inProductStockpile(pid, batch, batchQuantity, inWarehouseid, "000", billNo,memo,unitid,true);
					quantity = quantity - batchQuantity;
				}
			}
			// 如果quantity不为0,则对应增加特殊批次的库存
			if(quantity != 0){
				apsp.inProductStockpile(ttd.getProductid(), Constants.NO_BATCH, quantity, inWarehouseid, "000", billNo,memo,unitid,true);
			}
		}
	}
}
