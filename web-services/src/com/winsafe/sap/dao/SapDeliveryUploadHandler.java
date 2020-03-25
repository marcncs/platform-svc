package com.winsafe.sap.dao;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.digester.Digester;
import org.apache.log4j.Logger;

import com.winsafe.hbm.util.StringUtil;
import com.winsafe.drp.dao.AppProductStockpile;
import com.winsafe.drp.dao.AppStockAlterMove;
import com.winsafe.drp.dao.AppStockAlterMoveDetail;
import com.winsafe.drp.dao.AppStockAlterMoveIdcode;
import com.winsafe.drp.dao.AppTakeTicket;
import com.winsafe.drp.dao.AppTakeTicketDetail;
import com.winsafe.drp.dao.AppTakeTicketIdcode;
import com.winsafe.drp.dao.FUnit;
import com.winsafe.drp.dao.Idcode;
import com.winsafe.drp.dao.Organ;
import com.winsafe.drp.dao.StockAlterMove;
import com.winsafe.drp.dao.StockAlterMoveDetail;
import com.winsafe.drp.dao.StockAlterMoveIdcode;
import com.winsafe.drp.dao.TakeTicket;
import com.winsafe.drp.dao.TakeTicketDetail;
import com.winsafe.drp.dao.TakeTicketIdcode;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.dao.Warehouse;
import com.winsafe.drp.exception.NotExistException;
import com.winsafe.drp.server.StockAlterMoveService;
import com.winsafe.drp.util.Constants;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.drp.util.UploadErrorMsg;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.MakeCode;
import com.winsafe.sap.metadata.SapFileErrorType;
import com.winsafe.sap.metadata.SapFileType;
import com.winsafe.sap.metadata.YesOrNo;
import com.winsafe.sap.pojo.SapDeliveryDetail;
import com.winsafe.sap.pojo.SapDeliveryHeader;
/*******************************************************************************************  
 * SAP发货单文件处理类
 * @author: ryan.xi	  
 * @date：2014-12-17  
 * @version 1.0  
 *  
 *  
 * Version    Date       ModifiedBy                 Content  
 * -------- ---------    ----------         ------------------------  
 * 1.0      2014-12-17   ryan.xi  
 * 1.1      2015-01-26   ryan.xi            添加单据类型检查      
 * 1.2      2015-06-05   ryan.xi            详情中无箱码时不检查物料号,不检查码的状态,用ship To字段检查收货仓库
 * 1.3      2015-11-18   ryan.xi            针对种子客户做调整
 *******************************************************************************************  
 */ 
public class SapDeliveryUploadHandler extends SapUploadHandler{
	
	private static Logger logger = Logger.getLogger(SapDeliveryUploadHandler.class);
	
	private List<SapDeliveryHeader> dlvHeaders = new ArrayList<SapDeliveryHeader>();
	private AppStockAlterMove appStockAlterMove = new AppStockAlterMove();
	private AppStockAlterMoveDetail appStockAlterMoveDetail = new AppStockAlterMoveDetail();
	private AppStockAlterMoveIdcode appStockAlterMoveIdcode = new AppStockAlterMoveIdcode();
	private StockAlterMoveService stockAlterMoveService = new StockAlterMoveService();
	
	private AppTakeTicketDetail apid = new AppTakeTicketDetail();
	private AppTakeTicket apb = new AppTakeTicket();
	private AppTakeTicketIdcode apidcode = new AppTakeTicketIdcode();
	private AppProductStockpile apsp = new AppProductStockpile();
	//收货仓库是否存在
	private boolean hasInwarehouse = true;
	
	public SapDeliveryUploadHandler(SapFileType fileType) throws Exception {
		super(fileType);
	}

	/**
	 * 获取头文件信息
	 * @return
	 */
	public List<SapDeliveryHeader> getDlvHeaders() {
		return dlvHeaders;
	}

	public void addDeliveryHeader(SapDeliveryHeader dlvHeader) {
		doValidate(dlvHeader);
		if(!hasError) {
			dlvHeaders.add(dlvHeader);
		}
	}
	
	@Override
	public void handle() throws Exception  {
		logger.debug("start add sap uploaded delivery.");
		getRandomCode(dlvHeaders);
		for(SapDeliveryHeader dlvHeader : dlvHeaders) {
			//取得发货机构
			Organ outOrgan = organs.get(dlvHeader.getDistributionPlant());
			//取得发货机构的机构仓库
			Warehouse outWarehouse;
			if(outOrgan == null) {
				outWarehouse = existWarehouses.get(dlvHeader.getDistributionPlant());
				outOrgan = organService.getOrganByID(outWarehouse.getMakeorganid());
				if(outOrgan == null) {
					throw new NotExistException("编号为" + existWarehouses.get(dlvHeader.getDistributionPlant()).getMakeorganid() + "的机构不存在或已被撤销");
				}
			} else {
				outWarehouse = organWithWarehouse.get(outOrgan.getId());
			}
			
			//取得收货机构
			Organ inOrgan = organs.get(dlvHeader.getSoldToPartyCode());
			
			String outOrganId = outOrgan.getId();
			String outOrganName = outOrgan.getOrganname();
			String outWarehouseId = outWarehouse.getId();
				
			String inOrganId = inOrgan.getId();
			String inOrganName = inOrgan.getOrganname();
			String inWarehouseId =  null;
			//取得收货机构的机构仓库
			if(hasInwarehouse) {
				Warehouse inWarehouse = existWarehouses.get(dlvHeader.getShipToPartyCode());
				inWarehouseId = inWarehouse.getId();
			}
				
			addStockAlterMove(dlvHeader, outOrganId, outOrganName, outWarehouseId, inOrganId, inOrganName, inWarehouseId);
		}
	}
	
	private void getRandomCode(List<SapDeliveryHeader> dlvHeaders2) throws Exception {
		for(SapDeliveryHeader dlvHeader : dlvHeaders) {
			dlvHeader.setStockAlterMoveId(MakeCode.getExcIDByRandomTableName("stock_alter_move", 2, "OM"));
		}
	}

	/**
	 * 添加订购单
	 * Create Time 2014-10-16 下午05:22:37 
	 * @param dlvHeader      SAP发货单头信息
	 * @param outOrganId     出货机构ID
	 * @param outOrganName   出货机构名称
	 * @param outWarehouseId 出货仓库ID
	 * @param inOrganId      收货机构ID
	 * @param inOrganName    收货机构名称
	 * @param inWarehouseId  收货仓库ID
	 * @param isDirect       是否是转发单
	 * @throws Exception
	 */
	private void addStockAlterMove(SapDeliveryHeader dlvHeader, 
			String outOrganId, String outOrganName, String outWarehouseId, 
			String inOrganId, String inOrganName,  String inWarehouseId) throws Exception {
		logger.debug("添加发货单--开始, sap id = " + dlvHeader.getDlvDocNo());
		//取得制单机构
		Organ makeOrgan = organService.getOrganByID(sapUploadLog.getMakeOrganId());
		
		StockAlterMove stockAlterMove = new StockAlterMove();
		stockAlterMove.setId(dlvHeader.getStockAlterMoveId());
		//发货单内部编号(SAP编号)
		stockAlterMove.setNccode(dlvHeader.getDlvDocNo());
		
		//调拨日期
		if(dlvHeader.getTdlvDate() != null) {
			stockAlterMove.setMovedate(dlvHeader.getTdlvDate());
		} else {
			stockAlterMove.setMovedate(dlvHeader.getDlvDate());
		}
		
		//出库机构编号,名称以及出库机构仓库编号
		stockAlterMove.setOutOrganId(outOrganId);
		stockAlterMove.setOutOrganName(outOrganName);
		stockAlterMove.setOutwarehouseid(outWarehouseId);
		
		//收获机构编号，名称以及收货机构仓库编号
		stockAlterMove.setReceiveorganid(inOrganId);
		stockAlterMove.setReceiveorganidname(inOrganName);
		stockAlterMove.setInwarehouseid(inWarehouseId);
		
		//发货日期
		stockAlterMove.setShipmentdate(dlvHeader.getDlvDate());
		//是否复核, 复核日期， 复核人
		stockAlterMove.setIsaudit(YesOrNo.YES.getValue());
		stockAlterMove.setAuditdate(DateUtil.StringToDatetime(DateUtil.getCurrentDateTime()));
		stockAlterMove.setAuditid(sapUploadLog.getMakeId());
		//制单人,机构，部门，日期
		stockAlterMove.setMakeid(sapUploadLog.getMakeId());
		stockAlterMove.setMakeorganid(sapUploadLog.getMakeOrganId());
		stockAlterMove.setMakeorganidname(makeOrgan.getOrganname());
		stockAlterMove.setMakedeptid(sapUploadLog.getMakeDeptId());
		stockAlterMove.setMakedate(DateUtil.StringToDatetime(DateUtil.getCurrentDateTime()));
		if(hasInwarehouse) {
			//取得发货机构的机构仓库
			Warehouse inWarehouse = appWarehouse.getWarehouseByID(inWarehouseId);
			//收货地址
			stockAlterMove.setTransportaddr(inWarehouse.getWarehouseaddr());
			//联系人
			stockAlterMove.setOlinkman(inWarehouse.getUsername());
			stockAlterMove.setOtel(inWarehouse.getWarehousetel());
		}
		//是否出库
		stockAlterMove.setIsshipment(YesOrNo.YES.getValue());
		//是否记账
		stockAlterMove.setIstally(YesOrNo.YES.getValue());
		//是否作废
		stockAlterMove.setIsblankout(YesOrNo.NO.getValue());
		//是否完成
		stockAlterMove.setIscomplete(YesOrNo.NO.getValue());
		
		StringBuffer keyscontent = new StringBuffer();
		keyscontent.append(stockAlterMove.getId()).append(",").append(stockAlterMove.getOlinkman())
				.append(",").append(stockAlterMove.getOtel()).append(",").append(
						stockAlterMove.getMakeorganidname());
		
		if(!hasInwarehouse) {
			stockAlterMove.setIsmove(1);
		}
		
		Double quantity = 1.00 ; 
		
		List<StockAlterMoveDetail> samsList  = new ArrayList<StockAlterMoveDetail>();
		List<Idcode> idcodeList = new ArrayList<Idcode>();
		Map<SapDeliveryDetail,StockAlterMoveDetail> samsMap  = new HashMap<SapDeliveryDetail,StockAlterMoveDetail>();
		//保存已处理的箱码,用来检查重复的箱码
		Set<String> processedCartonCode = new HashSet<String>();
		for(SapDeliveryDetail dlvDetail : dlvHeader.getDlvDetails()) {
			//过滤掉如果箱码为空的记录
			if(StringUtil.isEmpty(dlvDetail.getCartonCode())) {
				continue;
			}
			//如果箱码头10位是"0000000000",那就检查托盘码，如果托盘码头10位是"0004052296",那就以托盘码替换箱码
			if(dlvDetail.getCartonCode().startsWith("0000000000")) {
				if(dlvDetail.getPalletCode().startsWith("0004052296")) {
					dlvDetail.setCartonCode(dlvDetail.getPalletCode());
				} else {
					continue;
				}
			}
			//检查重复的箱码信息
			if(processedCartonCode.contains(dlvDetail.getCartonCode().trim())) {
				continue;
			}
			//获取包装单位
			FUnit fUnit = materialWithFunit.get(existMaterialCodes.get(dlvDetail.getMaterialCode()).getId());
			//获取idcode，若不存在则添加到系统中
			Idcode idcode = appIdcode.getIdcodeById(dlvDetail.getCartonCode());
			if(idcode != null) {
				dlvDetail.setBatchNo(idcode.getBatch());
				dlvDetail.setProduceDate(idcode.getProducedate());
			} else {
				Idcode newIdcode = new Idcode();
				newIdcode.setIdcode(dlvDetail.getCartonCode());
				newIdcode.setProductid(existMaterialCodes.get(dlvDetail.getMaterialCode()).getId());
				newIdcode.setProductname(existMaterialCodes.get(dlvDetail.getMaterialCode()).getProductname());
				newIdcode.setBatch(!StringUtil.isEmpty(dlvDetail.getBatchNo()) ? dlvDetail.getBatchNo() : Constants.NO_BATCH);
				newIdcode.setProducedate(dlvDetail.getProduceDate());
				newIdcode.setUnitid(fUnit.getFunitid());
				newIdcode.setQuantity(quantity);
				newIdcode.setPackquantity(quantity * fUnit.getXquantity());
				newIdcode.setIsuse(0);
				newIdcode.setIsout(0);
				newIdcode.setIdbilltype(0);
				newIdcode.setMakeorganid(stockAlterMove.getMakeorganid());
				newIdcode.setWarehouseid(stockAlterMove.getOutwarehouseid());
				newIdcode.setMakedate(stockAlterMove.getMakedate());
				newIdcode.setWarehousebit(Constants.WAREHOUSE_BIT_DEFAULT);
				idcodeList.add(newIdcode);
			}
			if(StringUtil.isEmpty(dlvDetail.getBatchNo())){
				dlvDetail.setBatchNo(Constants.NO_BATCH);
			}
			
			StockAlterMoveDetail stockAlterMoveDetail = null;
			if(samsMap.containsKey(dlvDetail)) {
				stockAlterMoveDetail = samsMap.get(dlvDetail);
				stockAlterMoveDetail.setQuantity(stockAlterMoveDetail.getQuantity() + 1);
			} else {
				stockAlterMoveDetail = new StockAlterMoveDetail();
				stockAlterMoveDetail.setId(Integer.parseInt(dlvDetail.getDlvLineItemNo()));
				stockAlterMoveDetail.setSamid(stockAlterMove.getId());
				stockAlterMoveDetail.setProductname(existMaterialCodes.get(dlvDetail.getMaterialCode()).getProductname());
				stockAlterMoveDetail.setProductid(existMaterialCodes.get(dlvDetail.getMaterialCode()).getId());
				stockAlterMoveDetail.setNccode(existMaterialCodes.get(dlvDetail.getMaterialCode()).getmCode());
				stockAlterMoveDetail.setSpecmode(existMaterialCodes.get(dlvDetail.getMaterialCode()).getSpecmode());
				stockAlterMoveDetail.setUnitid(fUnit.getFunitid());
				stockAlterMoveDetail.setBatch(dlvDetail.getBatchNo());
				stockAlterMoveDetail.setQuantity(quantity);
				stockAlterMoveDetail.setTakequantity(0d);
				stockAlterMoveDetail.setMakedate(DateUtil.getCurrentDate());
				samsMap.put(dlvDetail, stockAlterMoveDetail);
				samsList.add(stockAlterMoveDetail);
			}
			
			StockAlterMoveIdcode stockAlterMoveIdcode = new StockAlterMoveIdcode();
//			stockAlterMoveIdcode.setId(dlvDetail.getStockAlterMoveIdcodeId());
			stockAlterMoveIdcode.setSamid(stockAlterMove.getId());
			stockAlterMoveIdcode.setProductid(existMaterialCodes.get(dlvDetail.getMaterialCode()).getId());
			stockAlterMoveIdcode.setIsidcode(YesOrNo.YES.getValue());
			stockAlterMoveIdcode.setWarehousebit(Constants.WAREHOUSE_BIT_DEFAULT);
			stockAlterMoveIdcode.setBatch(dlvDetail.getBatchNo());
			stockAlterMoveIdcode.setValidate("");
			stockAlterMoveIdcode.setUnitid(fUnit.getFunitid());
			stockAlterMoveIdcode.setQuantity(quantity);
			stockAlterMoveIdcode.setPackquantity(quantity * fUnit.getXquantity());
			stockAlterMoveIdcode.setMakedate(DateUtil.getCurrentDate());
			stockAlterMoveIdcode.setProducedate(dlvDetail.getProduceDate());
			//内部编号
			stockAlterMoveIdcode.setNccode(existMaterialCodes.get(dlvDetail.getMaterialCode()).getNccode());
			//设置箱码(条码)
			stockAlterMoveIdcode.setIdcode(dlvDetail.getCartonCode());
			appStockAlterMoveIdcode.addStockAlterMoveIdcode(stockAlterMoveIdcode);
			
			processedCartonCode.add(dlvDetail.getCartonCode().trim());
		}
		if(idcodeList.size() > 0) {
			appIdcode.addIdcodesFromSapDelivery(idcodeList);
		}
		for(StockAlterMoveDetail samd : samsList) {
			appStockAlterMoveDetail.addStockAlterMoveDetail(samd);
		}
		stockAlterMove.setTakestatus(YesOrNo.YES.getValue());
		stockAlterMove.setKeyscontent(keyscontent.toString());
		appStockAlterMove.addStockAlterMove(stockAlterMove);
		DBUserLog.addUserLog(sapUploadLog.getMakeId(), "发货单", "销售管理>>发货单>>新增 ,编号：" + stockAlterMove.getId());
		DBUserLog.addUserLog(sapUploadLog.getMakeId(), "发货单", "销售管理>>发货单>>确认单据 ,编号：" + stockAlterMove.getId());
		logger.debug("添加发货单--结束");
		addTakeTicket(dlvHeader, stockAlterMove, samsList);
		
	}
	
	
	
	/**
	 * 添加检货出库单
	 * Create Time 2014-10-16 下午05:22:37 
	 * @param dlvHeader
	 * @param stockAlterMove
	 * @param samsList
	 * @param isDirect
	 * @throws Exception 
	 */
	private void addTakeTicket(SapDeliveryHeader dlvHeader, StockAlterMove stockAlterMove, List<StockAlterMoveDetail> samsList) throws Exception {
		logger.debug("添加检货出库单---------------start-----------------");
		//添加检货出库单---------------start-----------------
		UsersBean users = new UsersBean();
		users.setUserid(sapUploadLog.getMakeId());
		users.setMakedeptid(sapUploadLog.getMakeDeptId());
		users.setMakeorganid(sapUploadLog.getMakeOrganId());
		TakeTicket takeTicket = stockAlterMoveService.addTakeTicket(stockAlterMove, samsList, users);
		apid.updRealQtyToQty(takeTicket.getId());
		List<TakeTicketIdcode> ttiList = new ArrayList<TakeTicketIdcode>();
		//保存已处理的箱码,用来检查重复的箱码
		Set<String> processedCartonCode = new HashSet<String>();
		for(SapDeliveryDetail dlvDetail : dlvHeader.getDlvDetails()) {
			//过滤掉如果箱码为空的记录
			if(StringUtil.isEmpty(dlvDetail.getCartonCode())) {
				continue;
			}
			//如果箱码头10位是"0000000000",那就检查托盘码，如果托盘码头10位是"0004052296",那就以托盘码替换箱码
			if(dlvDetail.getCartonCode().startsWith("0000000000")) {
				if(!dlvDetail.getPalletCode().startsWith("0004052296")) {
					continue;
				} 
			}
			//检查重复的箱码信息
			if(processedCartonCode.contains(dlvDetail.getCartonCode().trim())) {
				continue;
			}
			FUnit fUnit = materialWithFunit.get(existMaterialCodes.get(dlvDetail.getMaterialCode()).getId());
			TakeTicketIdcode pi = new TakeTicketIdcode();
//			pi.setId(dlvDetail.getTakeTicketIdcode());
			pi.setTtid(takeTicket.getId());
			pi.setProductid(existMaterialCodes.get(dlvDetail.getMaterialCode()).getId());
			pi.setIsidcode(1);
			pi.setWarehousebit(Constants.WAREHOUSE_BIT_DEFAULT);
			pi.setBatch(dlvDetail.getBatchNo());
			pi.setProducedate(dlvDetail.getProduceDate());
			pi.setUnitid(fUnit.getFunitid());
			pi.setQuantity(1.00);
			pi.setPackquantity(fUnit.getXquantity());
			pi.setMakedate(DateUtil.getCurrentDate());
			pi.setWarehousefromid(stockAlterMove.getOutwarehouseid());
			pi.setOrganfromid(stockAlterMove.getMakeorganid());
			//设置箱码
			pi.setIdcode(dlvDetail.getCartonCode());
			
			apidcode.addTakeTicketIdcode(pi);
			ttiList.add(pi);
			processedCartonCode.add(dlvDetail.getCartonCode().trim());
		}
			//得到TT明细
			List<TakeTicketDetail> ttds = apid.getTakeTicketDetailByTtid(takeTicket.getId());

			// 修改检货数量
			for(TakeTicketDetail ttd : ttds){
				apid.updTakeQuantity(takeTicket.getBsort(), takeTicket.getBillno(), ttd.getProductid(), ttd.getQuantity());
			}
			// 更新小票状态为已复核
			Date currentDate = DateUtil.getCurrentDate();
			takeTicket.setIsaudit(1);
			takeTicket.setAuditid(users.getUserid());
			takeTicket.setAuditdate(currentDate);
			//更新单据已检货
			apb.updTakeStatus(takeTicket.getBsort(), takeTicket.getBillno(), 1);
			//更新单据已出库
			apb.updShipment(takeTicket.getBsort(), takeTicket.getBillno(), 1,users.getUserid(),DateUtil.formatDateTime(currentDate));
			// 扣减出库仓库库存
			outProductStockpileTTD(ttds, ttiList, takeTicket.getWarehouseid());
			//更新条码为出库状态
			appIdcode.updIdcodeByttid(takeTicket.getId(), 0, 1);
			DBUserLog.addUserLog(sapUploadLog.getMakeId(), "检货出库", "检货出库>>复核检货小票,编号：" + takeTicket.getId());
			
			//签收
			if(hasInwarehouse) {
				Warehouse inWarehouse = appWarehouse.getWarehouseByID(takeTicket.getInwarehouseid());
				if(inWarehouse.getIsautoreceive() == 1){
					//更新单据已完成
					apb.updIsComplete(takeTicket.getBsort(), takeTicket.getBillno(), 1, sapUploadLog.getMakeId());
					inProductStockpileTTD(takeTicket.getBillno(),ttds, ttiList,takeTicket.getInwarehouseid(), Constants.TT_WASTE_BOOK_MEMO[takeTicket.getBsort()]);
					//更新条码为可用状态
					appIdcode.updIdcodeByttid(takeTicket.getId(), 1, 0);
					appIdcode.updIdcodeByttid(takeTicket.getId(), takeTicket.getInwarehouseid());
					// 如果为发货单时更新签收数量
					if(takeTicket != null && takeTicket.getBsort() == 1){
						updateStockAlterMoveReceive(takeTicket.getBillno(), ttds);
					}
					//记录签收日志
					addUserlog(takeTicket.getBsort(),takeTicket.getBillno(), users.getUserid());
				}
			}
		logger.debug("添加检货出库单--------------end------------------");
		//
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
	
	/**
	 * 扣减出货仓库库存
	 * @param ttdList
	 * @param idcodeBatchMap
	 * @param outWarehouseid
	 * @throws Exception
	 */
	private void outProductStockpileTTD(List<TakeTicketDetail> ttdList,Map<String, Double> idcodeBatchMap,String outWarehouseid) throws Exception {
		// 根据批次减出库仓库库存
		for (TakeTicketDetail ttd : ttdList) {
			//总数量
			Double quantity = appFUnit.getQuantity(ttd.getProductid(), ttd.getUnitid(), ttd.getQuantity());
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
			Double quantity = appFUnit.getQuantity(ttd.getProductid(), ttd.getUnitid(), ttd.getQuantity());
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
	/**
	 * 更新发货单的签收数量
	 * Create Time 2014-12-25 下午02:30:14 
	 */
	private void updateStockAlterMoveReceive(String billno,List<TakeTicketDetail> ttds) throws Exception{
		for(TakeTicketDetail ttd : ttds){
			appStockAlterMoveDetail.updReceiveQuantity(billno, ttd.getProductid(), ttd.getQuantity());
		}
	}

	
	/**
	 * 验证文件中信息的完整性
	 * @param dlvHeader
	 * @return
	 */
	protected boolean doValidate(SapDeliveryHeader dlvHeader) {
		//#start modified by ryan.xi at 20150724
		sapUploadLog.setBillNo(dlvHeader.getDlvDocNo());
		//#end modified by ryan.xi at 20150724
		if(StringUtil.isEmpty(dlvHeader.getDlvDocType())) {
			//#start modified by ryan.xi at 20150724
			sapUploadLog.setErrorType(SapFileErrorType.BILL_TYPE_EMPTY.getDbValue());
			//#end modified by ryan.xi at 20150724
			errMsg.append("单据类型为空").append("\r\n");
			clearAndSetError();
			return hasError;
		} else if(!"J".equals(dlvHeader.getDlvDocType().trim())) {
			//#start modified by ryan.xi at 20150724
			sapUploadLog.setErrorType(SapFileErrorType.RETURN_DATA.getDbValue());
			//#end modified by ryan.xi at 20150724
			errMsg.append("当前系统只处理SAP发货单，不处理其他单据").append("\r\n");
			clearAndSetError();
			return hasError;
		}
		Warehouse outWarehouse = null;
		Warehouse inWarehouse = null;
		if(StringUtil.isEmpty(dlvHeader.getSoldToPartyCode())) {
			if(StringUtil.isEmpty(dlvHeader.getShipToPartyCode())) {
				errMsg.append(UploadErrorMsg.getError(UploadErrorMsg.E00201, dlvHeader.getDistributionPlant())).append("\r\n");
				clearAndSetError();
			} else {
				dlvHeader.setSoldToPartyCode(dlvHeader.getShipToPartyCode());
			}
		}
		//检查发货机构或仓库是否存在
		if(!isOrganIdExists(dlvHeader.getDistributionPlant())) {
			if(!isWarehousExists(dlvHeader.getDistributionPlant())) {
				//去掉机构代码前面的0再检查
				if(dlvHeader.getDistributionPlant().startsWith("0")) {
					String organId = dlvHeader.getDistributionPlant().replaceFirst("^0*", "");
					if(!isOrganIdExists(organId)) {
						if(!isWarehousExists(organId)) {
							//#start modified by ryan.xi at 20150724
							sapUploadLog.setErrorType(SapFileErrorType.ORGAN_WAREHOUSE_NOT_EXISTS.getDbValue());
							//#end modified by ryan.xi at 20150724
							errMsg.append(UploadErrorMsg.getError(UploadErrorMsg.E00203, dlvHeader.getDistributionPlant() + "或 " + organId)).append("\r\n");
							clearAndSetError();
						} else {
							dlvHeader.setDistributionPlant(organId);
							outWarehouse = existWarehouses.get(dlvHeader.getDistributionPlant());
						}
					} else {
						dlvHeader.setDistributionPlant(organId);
					}
				} else {
					//#start modified by ryan.xi at 20150724
					sapUploadLog.setErrorType(SapFileErrorType.ORGAN_WAREHOUSE_NOT_EXISTS.getDbValue());
					//#end modified by ryan.xi at 20150724
					errMsg.append(UploadErrorMsg.getError(UploadErrorMsg.E00203, dlvHeader.getDistributionPlant())).append("\r\n");
					clearAndSetError();
				}
			} else {
				outWarehouse = existWarehouses.get(dlvHeader.getDistributionPlant());
			}
		} 
		//检查发货机构是否存在仓库
		if(isOrganIdExists(dlvHeader.getDistributionPlant())) {
			if(!isOrganWarehousExists(organs.get(dlvHeader.getDistributionPlant()).getId())) {
				//#start modified by ryan.xi at 20150724
				sapUploadLog.setErrorType(SapFileErrorType.ORGAN_WAREHOUSE_NOT_EXISTS.getDbValue());
				//#end modified by ryan.xi at 20150724
				errMsg.append(UploadErrorMsg.getError(UploadErrorMsg.E00202, dlvHeader.getDistributionPlant())).append("\r\n");
				clearAndSetError();
			} else {
				outWarehouse = organWithWarehouse.get(organs.get(dlvHeader.getDistributionPlant()).getId());
			}
		} 
		//检查收货机构是否存在 added by ryan.xi at 20150609
		if(!isOrganIdExists(dlvHeader.getSoldToPartyCode())) {
			if(dlvHeader.getSoldToPartyCode().startsWith("0")) {
				String organId = dlvHeader.getSoldToPartyCode().replaceFirst("^0*", "");
				if(!isOrganIdExists(organId)) {
					//#start modified by ryan.xi at 20150724
					sapUploadLog.setErrorType(SapFileErrorType.ORGAN_WAREHOUSE_NOT_EXISTS.getDbValue());
					//#end modified by ryan.xi at 20150724
					errMsg.append(UploadErrorMsg.getError(UploadErrorMsg.E00223, dlvHeader.getSoldToPartyCode() + "或 " + organId)).append("\r\n");
					clearAndSetError();
				} else {
					dlvHeader.setSoldToPartyCode(organId);
				}
			} else {
				//#start modified by ryan.xi at 20150724
				sapUploadLog.setErrorType(SapFileErrorType.ORGAN_WAREHOUSE_NOT_EXISTS.getDbValue());
				//#end modified by ryan.xi at 20150724
				errMsg.append(UploadErrorMsg.getError(UploadErrorMsg.E00223, dlvHeader.getSoldToPartyCode())).append("\r\n");
				clearAndSetError();
			}
		}
		//检查收货仓库是否存在 added by ryan.xi at 20150609
		if(!isWarehousExists(dlvHeader.getShipToPartyCode())) {
			if(dlvHeader.getShipToPartyCode().startsWith("0")) {
				String warehouseId = dlvHeader.getShipToPartyCode().replaceFirst("^0*", "");
				if(!isWarehousExists(warehouseId)) {
					hasInwarehouse = false;
					/* modified by ryan.xi at 20151116 考虑种衣剂客户需求
					//#start modified by ryan.xi at 20150724
					sapUploadLog.setErrorType(SapFileErrorType.ORGAN_WAREHOUSE_NOT_EXISTS.getDbValue());
					//#end modified by ryan.xi at 20150724
					errMsg.append(UploadErrorMsg.getError(UploadErrorMsg.E00224, dlvHeader.getSoldToPartyCode() + "或 " + warehouseId)).append("\r\n");
					clearAndSetError();*/
				} else {
					dlvHeader.setShipToPartyCode(warehouseId);
					inWarehouse = existWarehouses.get(dlvHeader.getShipToPartyCode());
					
					Organ inorgan = organs.get(dlvHeader.getSoldToPartyCode());
					if(inorgan != null && !inWarehouse.getMakeorganid().equals(inorgan.getId())) {
						hasInwarehouse = false;
					}
				}
			} else {
				hasInwarehouse = false;
				/* modified by ryan.xi at 20151116 考虑种衣剂客户需求
				//#start modified by ryan.xi at 20150724
				sapUploadLog.setErrorType(SapFileErrorType.ORGAN_WAREHOUSE_NOT_EXISTS.getDbValue());
				//#end modified by ryan.xi at 20150724
				errMsg.append(UploadErrorMsg.getError(UploadErrorMsg.E00224, dlvHeader.getSoldToPartyCode())).append("\r\n");
				clearAndSetError();*/
			}
		} else {
			inWarehouse = existWarehouses.get(dlvHeader.getShipToPartyCode());

			Organ inorgan = organs.get(dlvHeader.getSoldToPartyCode()); 
			if(inorgan != null && !inWarehouse.getMakeorganid().equals(inorgan.getId())) {
				hasInwarehouse = false;
			}
		}
		
		//检查收货机构是否存在仓库
		/*modified by ryan.xi at 20150609
		if(isOrganIdExists(dlvHeader.getSoldToPartyCode())) {
			if(!isOrganWarehousExists(organs.get(dlvHeader.getSoldToPartyCode()).getId())) {
				errMsg.append(UploadErrorMsg.getError(UploadErrorMsg.E00202, dlvHeader.getSoldToPartyCode())).append("\r\n");
				clearAndSetError();
			} else {
				inWarehouse = organWithWarehouse.get(organs.get(dlvHeader.getSoldToPartyCode()).getId());
			}
		}*/
		//检查发货单号是否已存在
		StockAlterMove stockAlterMove = null;
		try {
			stockAlterMove = appStockAlterMove.getStockAlterMoveByNCcode(dlvHeader.getDlvDocNo());
			if(stockAlterMove != null) {
				//#start modified by ryan.xi at 20150724
				sapUploadLog.setErrorType(SapFileErrorType.ORGAN_WAREHOUSE_NOT_EXISTS.getDbValue());
				//#end modified by ryan.xi at 20150724
				errMsg.append(UploadErrorMsg.getError(UploadErrorMsg.E00219, stockAlterMove.getNccode())).append("\r\n");
				clearAndSetError();
			} 
		} catch (Exception e) {
			logger.error("error occurred when get stockAlterMove using nccode "+ dlvHeader.getDlvDocNo(), e);
		}
		
		//检查物料号和包装比例关系是否存在
		for(SapDeliveryDetail dlvDetail : dlvHeader.getDlvDetails()) {
			preProcess(dlvDetail);
			if(StringUtil.isEmpty(dlvDetail.getCartonCode())) {
				continue;
			}
			if(!isMaterialCodeExists(dlvDetail.getMaterialCode())) {
				//#start modified by ryan.xi at 20150724
				sapUploadLog.setErrorType(SapFileErrorType.PRODUCT_NOT_EXIST.getDbValue());
				//#end modified by ryan.xi at 20150724
				errMsg.append(UploadErrorMsg.getError(UploadErrorMsg.E00204, dlvDetail.getMaterialCode())).append("\r\n");
				clearAndSetError();
			} else if(!isMaterialFUnitExists(existMaterialCodes.get(dlvDetail.getMaterialCode()).getId())) {
				//#start modified by ryan.xi at 20150724
				sapUploadLog.setErrorType(SapFileErrorType.PRODUCT_NOT_EXIST.getDbValue());
				//#end modified by ryan.xi at 20150724
				errMsg.append(UploadErrorMsg.getError(UploadErrorMsg.E00205, dlvDetail.getMaterialCode(), defaultUnitName)).append("\r\n");
				clearAndSetError();
			}
			//检查条码是否可用，是否已发过
			try {
				if(stockAlterMove == null) {
					//modified by ryan.xi at 20150608
					/*if(appIdcode.isInUseById(dlvDetail.getCartonCode())) {
						errMsg.append(UploadErrorMsg.getError(UploadErrorMsg.E00221, dlvDetail.getCartonCode())).append("\r\n");
						clearAndSetError();
					}*/
					if(outWarehouse != null && inWarehouse != null) {
						List<StockAlterMoveIdcode> idcodes = appStockAlterMoveIdcode.getStockAlterMoveIdcodesByidcode(dlvDetail.getCartonCode());
						for(StockAlterMoveIdcode idcode : idcodes) {
							StockAlterMove sam = appStockAlterMove.getStockAlterMoveByID(idcode.getSamid());
							if(sam != null && outWarehouse.getId().equals(sam.getOutwarehouseid()) && inWarehouse.getId().equals(sam.getInwarehouseid())) {
								//#start modified by ryan.xi at 20150724
								sapUploadLog.setErrorType(SapFileErrorType.CODE_DUPLICATE_SEND.getDbValue());
								//#end modified by ryan.xi at 20150724
								errMsg.append(UploadErrorMsg.getError(UploadErrorMsg.E00220, sam.getOutOrganName(), outWarehouse.getWarehousename(), sam.getReceiveorganidname(), inWarehouse.getWarehousename(), dlvDetail.getCartonCode(), sam.getId())).append("\r\n");
								clearAndSetError();
								break;
							}
						}
					}
				}
			} catch (Exception e) {
				logger.error("error occurred when check stockAlterMoveIdcode",e);
			}
		}
		return hasError;
	}
	
	/**
	 * 预处理解析后的数据
	 * Create Time 2014-10-20 上午11:23:26 
	 * @throws Exception
	 */
	private void preProcess(SapDeliveryDetail dlvDetail) {
		//如果物料码超过8位，取最后八位
		String materialCode = dlvDetail.getMaterialCode();
		if(!StringUtil.isEmpty(materialCode) && materialCode.length() > 8) {
			int length = materialCode.length();
			dlvDetail.setMaterialCode(materialCode.substring(length - 8, length));
		}
	}
	
	private void clearAndSetError() {
		if(!hasError) {
			dlvHeaders.clear();
			hasError = true;
		}
	}
	
	@Override
	public void checkResult() {
		if(dlvHeaders.size() == 0 && !hasError) {
			hasError = true;
			errMsg.append(UploadErrorMsg.getError(UploadErrorMsg.E00211));
		}
	}

	public void addRule(Digester digester) {
		digester.setValidating(false);  
		// 指明匹配模式和要创建的类    
		digester.addObjectCreate("ns1:DeliveryIDOC/ns0:DELIVERY_H", SapDeliveryHeader.class);  
		digester.addBeanPropertySetter("ns1:DeliveryIDOC/ns0:DELIVERY_H/VBELN", "dlvDocNo");  
		digester.addBeanPropertySetter("ns1:DeliveryIDOC/ns0:DELIVERY_H/VBTYP", "dlvDocType");  
		digester.addBeanPropertySetter("ns1:DeliveryIDOC/ns0:DELIVERY_H/ERDAT", "dlvDate");  
		digester.addBeanPropertySetter("ns1:DeliveryIDOC/ns0:DELIVERY_H/WADAT", "tdlvDate");  
		digester.addBeanPropertySetter("ns1:DeliveryIDOC/ns0:DELIVERY_H/WERKS", "distributionPlant");  
		digester.addBeanPropertySetter("ns1:DeliveryIDOC/ns0:DELIVERY_H/PARTN_SOLD", "soldToPartyCode");  
		digester.addBeanPropertySetter("ns1:DeliveryIDOC/ns0:DELIVERY_H/NAME1_SOLD", "soldToPartyName");  
		digester.addBeanPropertySetter("ns1:DeliveryIDOC/ns0:DELIVERY_H/REGIO_SOLD", "soldToPartyProvince");  
		digester.addBeanPropertySetter("ns1:DeliveryIDOC/ns0:DELIVERY_H/ORT01_SOLD", "soldToPartyCity");
		digester.addBeanPropertySetter("ns1:DeliveryIDOC/ns0:DELIVERY_H/LAND1_SOLD", "soldToPartyCountry");
		digester.addBeanPropertySetter("ns1:DeliveryIDOC/ns0:DELIVERY_H/PARTN_SHIP", "shipToPartyCode");
		digester.addBeanPropertySetter("ns1:DeliveryIDOC/ns0:DELIVERY_H/REGIO_SHIP", "shipToPartyProvince");
		digester.addBeanPropertySetter("ns1:DeliveryIDOC/ns0:DELIVERY_H/ORT01_SHIP", "shipToPartyCity");
		digester.addBeanPropertySetter("ns1:DeliveryIDOC/ns0:DELIVERY_H/LAND1_SHIP", "shipToPartyCountry");
		digester.addSetNext("ns1:DeliveryIDOC/ns0:DELIVERY_H", "addDeliveryHeader");
		digester.addObjectCreate("ns1:DeliveryIDOC/ns0:DELIVERY_H/DELIVERY_D", SapDeliveryDetail.class );
		digester.addBeanPropertySetter("ns1:DeliveryIDOC/ns0:DELIVERY_H/DELIVERY_D/POSNR", "dlvLineItemNo");
		digester.addBeanPropertySetter("ns1:DeliveryIDOC/ns0:DELIVERY_H/DELIVERY_D/MATNR", "materialCode");
		digester.addBeanPropertySetter("ns1:DeliveryIDOC/ns0:DELIVERY_H/DELIVERY_D/CHARG", "batchNo");
		digester.addBeanPropertySetter("ns1:DeliveryIDOC/ns0:DELIVERY_H/DELIVERY_D/LFIMG", "dlvQuantity");
		digester.addBeanPropertySetter("ns1:DeliveryIDOC/ns0:DELIVERY_H/DELIVERY_D/EXIDV_P", "palletCode");
		digester.addBeanPropertySetter("ns1:DeliveryIDOC/ns0:DELIVERY_H/DELIVERY_D/EXIDV_C", "cartonCode");
		digester.addSetNext("ns1:DeliveryIDOC/ns0:DELIVERY_H/DELIVERY_D", "addDeliveryDetails");
	}
	
}
