package com.winsafe.drp.action.warehouse;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.hibernate.HibernateException;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppIdcode;
import com.winsafe.drp.dao.AppOrganScan;
import com.winsafe.drp.dao.AppProduct;
import com.winsafe.drp.dao.AppProductStockpile;
import com.winsafe.drp.dao.AppProductStockpileAll;
import com.winsafe.drp.dao.AppStockAlterMove;
import com.winsafe.drp.dao.AppStockAlterMoveIdcode;
import com.winsafe.drp.dao.AppTakeBill;
import com.winsafe.drp.dao.AppTakeTicket;
import com.winsafe.drp.dao.AppTakeTicketDetail;
import com.winsafe.drp.dao.AppTakeTicketDetailBatchBit;
import com.winsafe.drp.dao.AppTakeTicketIdcode;
import com.winsafe.drp.dao.AppWarehouse;
import com.winsafe.drp.dao.OrganScan;
import com.winsafe.drp.dao.Product;
import com.winsafe.drp.dao.ProductStockpile;
import com.winsafe.drp.dao.StockAlterMove;
import com.winsafe.drp.dao.StockAlterMoveIdcode;
import com.winsafe.drp.dao.StockMove;
import com.winsafe.drp.dao.TakeBill;
import com.winsafe.drp.dao.TakeTicket;
import com.winsafe.drp.dao.TakeTicketDetail;
import com.winsafe.drp.dao.TakeTicketDetailBatchBit;
import com.winsafe.drp.dao.TakeTicketIdcode;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.dao.Warehouse;
import com.winsafe.drp.server.OrganScanIdcodeService;
import com.winsafe.drp.server.ProductCostService;
import com.winsafe.drp.server.TakeBillService;
import com.winsafe.drp.util.ArithDouble;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.MakeCode;

public class AuditTakeTicketRealQuantity extends BaseAction {
	private AppIdcode appidcode = new AppIdcode();
//	private AppProduct ap = new AppProduct();
	private AppProductStockpile appps = new AppProductStockpile();
//	private AppFUnit af = new AppFUnit();
//	private AppWarehouse aw = new AppWarehouse();
	AppTakeTicketIdcode apidcode = new AppTakeTicketIdcode();
	AppTakeTicket apb = new AppTakeTicket();
	AppTakeTicketDetail apid = new AppTakeTicketDetail();
	AuditTakeTicketAction auditTakeTicketAction = new AuditTakeTicketAction();
//	private AppProductStockpileAll appall = new AppProductStockpileAll();

	@SuppressWarnings("unchecked")
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		UsersBean users = UserManager.getUser(request);
		Integer userid = users.getUserid();
		String ttid = request.getParameter("ttid");
		AppTakeTicketDetailBatchBit appTakeTicketDetailBatchBit = new AppTakeTicketDetailBatchBit(); 
//		List<TakeTicketDetailBatchBit> batchBitList = appTakeTicketDetailBatchBit.getBatchBitByTTID(ttid);
		List<TakeTicketDetailBatchBit> batchBitList = new ArrayList<TakeTicketDetailBatchBit>();
		TakeTicket tt = new AppTakeTicket().getTakeTicketById(ttid);
		// TT已作废的情况
		if (tt.getIsblankout() == 1) {
			request.setAttribute("result",
					"databases.record.blankoutnooperator");
			return new ActionForward("/sys/lockrecordclose.jsp");
		}
		// TT已复核的情况
		if (tt.getIsaudit() == 1) {
			request.setAttribute("result", "databases.record.audit");
			return new ActionForward("/sys/lockrecordclose.jsp");
		}
		/*
		String[] batchQuantity = (String[]) request.getParameterValues("batchQuantity");
		List<String[]> bqs = new ArrayList<String[]>();
		for (String item : batchQuantity) {
			String[] bq = item.split(",");
			bqs.add(bq);
		}
		*/
		/**
		 * 先判断batchbit表中的检货情况
		 * 再判断productStockpile表中的情况
		 * 主要是先整理batchBit表中的real数量
		 */
		AppProduct ap =new AppProduct();
		Product p;
		String[] pids = (String[])request.getParameterValues("pid");
		String[] realmBatch = (String[])request.getParameterValues("mBatch");
		String[] realboxnum = (String[])request.getParameterValues("realboxnum");
		String[] realscatternum = (String[])request.getParameterValues("realscatternum");
		
		for(int i=0 ; i<realmBatch.length ; i++){
			String rMbatch = realmBatch[i]; //月批次
			String pid = pids[i]; //产品
			Integer rBoxnum = Integer.parseInt(realboxnum[i]); //箱数量
			Double rScaternum = Double.parseDouble(realscatternum[i]); //散数量
			Double bXquantity = 0d; //箱到最小单位转化率
			Double sXquantity = 1d; //散到最小单位转化率
			Double rQuantity = ArithDouble.add(rBoxnum*bXquantity,rScaternum*sXquantity);
			
			//查找批次表
			List<TakeTicketDetailBatchBit> mBatchBitList = appTakeTicketDetailBatchBit.getmBatchBitByTTIDPID(ttid,pid,rMbatch);
			for(TakeTicketDetailBatchBit mTtdbb : mBatchBitList){
				if(mTtdbb.getRealQuantity() == null){
					mTtdbb.setRealQuantity(0d);
				}
				if(mTtdbb.getRealboxnum() == null){
					mTtdbb.setRealboxnum(0);
				}
				if(mTtdbb.getRealscatternum() == null){
					mTtdbb.setRealscatternum(0d);
				}
				
				if(rQuantity > 0){
					if(rQuantity >= mTtdbb.getQuantity()){
						mTtdbb.setRealQuantity(mTtdbb.getQuantity());
//						mTtdbb.setRealboxnum(mTtdbb.getQuantity()/bXquantity);
						mTtdbb.setRealboxnum(0);
						mTtdbb.setRealscatternum(mTtdbb.getQuantity() - mTtdbb.getRealboxnum() * bXquantity);
						rQuantity = rQuantity - mTtdbb.getQuantity();
					}else {
						mTtdbb.setRealQuantity(rQuantity);
//						mTtdbb.setRealboxnum(mTtdbb.getQuantity()/bXquantity);
						mTtdbb.setRealboxnum(0);
						mTtdbb.setRealscatternum(rQuantity - mTtdbb.getRealboxnum() * bXquantity);
						rQuantity = rQuantity - mTtdbb.getQuantity();
					}
				}
			}
			List<TakeTicketDetailBatchBit> newTTDBBs = new ArrayList<TakeTicketDetailBatchBit>();
			//真实数大于0(真实数分配完ttbb表中的检货还有多的数,则从库存中再次检批次)
			if(rQuantity > 0){
				AppTakeTicketDetail appTakeTicketDetail = new AppTakeTicketDetail();
				List<TakeTicketDetail> ttds = appTakeTicketDetail.getTakeTicketDetailByTtidPid(tt.getId(), pid);
				newTTDBBs = initBatchBit(tt, ttds.get(0).getId(), pid, rMbatch, 0, rQuantity);
				if(newTTDBBs != null && newTTDBBs.size() >0){
					//将新检出的批次进行预出库
//					AppProductStockpileAll appProductStockpileAll = new AppProductStockpileAll();
//					AppProductStockpile appProductStockpile = new AppProductStockpile();
//					for(TakeTicketDetailBatchBit takeTicketDetailBatchBit : newTTDBBs){
//						appProductStockpileAll.prepareOut(takeTicketDetailBatchBit.getProductid(),takeTicketDetailBatchBit.getUnitid() ,tt.getWarehouseid(), takeTicketDetailBatchBit.getBatch(), takeTicketDetailBatchBit.getQuantity());
//						appProductStockpile.prepareOut(takeTicketDetailBatchBit.getProductid(), takeTicketDetailBatchBit.getUnitid() ,tt.getWarehouseid(), takeTicketDetailBatchBit.getWarehouseBit(), takeTicketDetailBatchBit.getBatch(), takeTicketDetailBatchBit.getQuantity());
//					}
					//重复的检货批次
					List<TakeTicketDetailBatchBit> repeateList = new ArrayList<TakeTicketDetailBatchBit>();
					//再将新检批次与原有的检出的产品批次合并(如有相同的产品批次则合并)
					for(TakeTicketDetailBatchBit newttdbb : newTTDBBs){
						for(TakeTicketDetailBatchBit oldttdbb: mBatchBitList){
							
							if(newttdbb.getBatch().equals(oldttdbb.getBatch()) && newttdbb.getProductid().equals(oldttdbb.getProductid())){
								//批次相同则合并
								oldttdbb.setRealboxnum(oldttdbb.getRealboxnum() + newttdbb.getRealboxnum());
								oldttdbb.setRealscatternum(oldttdbb.getRealscatternum() + newttdbb.getRealscatternum());
								oldttdbb.setRealQuantity(oldttdbb.getRealQuantity() + newttdbb.getRealQuantity());
								//增加到重复的检货批次的集合中
								repeateList.add(newttdbb);
							}
						}
					}
					//最后将新检出的批次去除重复的检货批次
					newTTDBBs.removeAll(repeateList);
					//更新原有的检货批次
					for(TakeTicketDetailBatchBit oldttdbb: mBatchBitList){
						appTakeTicketDetailBatchBit.updateTakeTicketDetailBatchBit(oldttdbb);
					}
					//新增新检出的检货批次
					for(TakeTicketDetailBatchBit newttdbb : newTTDBBs){
						appTakeTicketDetailBatchBit.addTakeTicketDetailBatchBit(newttdbb);
					}
				}
			}
			
			batchBitList.addAll(mBatchBitList);
			batchBitList.addAll(newTTDBBs);
		}
		List<TakeTicketIdcode> idcodelist = apidcode.getTakeTicketIdcodeByttid(ttid, 1);
		List<TakeTicketIdcode> ttilist = apidcode.getTakeTicketIdcodeByttid(ttid);
		// 有条码的情况
		if (idcodelist.size() > 0) {
			for(TakeTicketIdcode tti: idcodelist){//更新Idcode
				/**
				 *理新条码逻辑
				 *
				 * 先判断条码
				 *     散  查idcode表中的散列
				 *           判断位数是否为16位
				 *           更新散码
				 * 	        盒 查idcode表中的盒列
				 * 			 更新盒中所有的散码
				 * 	        箱 查idcode表中的箱列
				 *           更新箱中所有的散码
				 */
				if(tti.getUnitid() == 1){
					//散的情况
					appidcode.updIsUseOut(tti.getIdcode(), 0, 1);
				}else if(tti.getUnitid() == 4) {
					//盒的情况
					appidcode.updBoxIsUseOut(tti.getIdcode(), 0, 1);
				}else if(tti.getUnitid() == 2) {
					//箱的情况
					appidcode.updCartonIsUseOut(tti.getIdcode(), 0, 1);
				}
			}
			
		}
		// 更新小票状态为已复核
	   apb.updIsAudit(ttid, userid, 1);
	  // apb.updIsAuditByTime(ttid, userid, 1,datetime);
		// 更新库存表
		this.outRealProductStockpile(batchBitList, tt.getWarehouseid());
		//清除0库存
		AppProductStockpile appProductStockpile = new AppProductStockpile();
		appProductStockpile.cleanEmptyStock();
		AppProductStockpileAll appProductStockpileAll = new AppProductStockpileAll();
		appProductStockpileAll.cleanEmptyStockAll();
		//条码复制
		OrganScanIdcodeService oScan = new OrganScanIdcodeService();
		oScan.scan(tt, ttilist);

		int count = apb.getNoAuditCountByBillno(tt.getBillno());
		if (count == 0) {
			AppTakeBill api = new AppTakeBill();
			TakeBill tb = api.getTakeBillByID(tt.getBillno());
			TakeBillService tbs = new TakeBillService(tb, users);
			tbs.auditDeal();
		//	tbs.auditDealByTime(datetime);
		}

		//richieyu---出库后删除多余的数量为0的库位列表
		cleanZeroQuantityBatchBit(ttid);
		DBUserLog.addUserLog(userid, 7, "检货出库>>复核检货小票,编号：" + ttid);

		// ----------------------------------------------------------------------
		//首先清空dealerreceive字段
		//appidcode.updDealerreceiveNullByTtid(ttid);
		
		AppWarehouse awhs = new AppWarehouse();
		Warehouse whs = awhs.getWarehouseByID(tt.getInwarehouseid());
		
		AppOrganScan appos = new AppOrganScan();
		
		String smid = tt.getBillno();
		// 自动签收
//		// 转仓
//		if (tt.getBillno().startsWith("SM")) {
//			AppStockMove asm = new AppStockMove();
//			OrganScan os = appos.getOrganScanByOidScb(tt.getOid(), "SM");
//			if (whs.getIsautoreceive() == 1) { // 自动签收
//				// AppStockMoveDetail asmd = new AppStockMoveDetail();
//				AppStockMoveIdcode appmi = new AppStockMoveIdcode();
////				List<StockMoveIdcode> smlist = appmi.getStockMoveIdcodeBysmid(smid);
//				asm.updStockMoveIsComplete(smid, 1, userid);
//				//update by wenping ---当收货机构不需要扫描时才增加库存
//				if(os!=null && os.getIsscan()==0){
//					StockMove sm = asm.getStockMoveByID(smid);
//					List<StockMoveIdcode> smidcodelist = appmi.getStockMoveIdcodeBysmid(smid, 1);
//					auditTakeTicketAction.addIdcodeSM(sm, smidcodelist);
////					auditTakeTicketAction.addProductStockpileSM(smlist, sm.getInwarehouseid());
//					addProductStockpileSM(batchBitList, sm);
//					
//					//更改条码dealerreceive状态
//					appidcode.updIdcodeDealerreceiveValByttid(tt.getInwarehouseid(), ttid);
//				}
//
//				request.setAttribute("result", "databases.add.success");
//				DBUserLog.addUserLog(userid, 7, "入库>>转仓签收>>签收转仓单,编号：" + smid);
//			}else{
//				//当收货机构需要扫描，则将单据设为已签收，不增加库存
//				if(os!=null && os.getIsscan()==1){
//					asm.updStockMoveIsComplete(smid, 1, userid);
//				}
//			}
//		}
		
		// 订购
		if (tt.getBillno().startsWith("OM")) {
			AppStockAlterMove asm = new AppStockAlterMove();
			OrganScan os = appos.getOrganScanByOidScb(tt.getOid(), "OM");
			if (whs.getIsautoreceive() == 1) { // 自动签收
				// return new
				// ActionForward("/completeStockAlterMoveReceiveAction.do?OMID="+tt.getBillno());
				AppStockAlterMoveIdcode appmi = new AppStockAlterMoveIdcode();
				asm.updStockAlterMoveIsComplete(smid, 1, userid);
//				List<StockAlterMoveIdcode> omlist = appmi.getStockAlterMoveIdcodeBysamid(smid);
				//update by wenping ---当收货机构不需要扫描时才增加库存
				if(os!=null && os.getIsscan()==0){
					StockAlterMove sm = asm.getStockAlterMoveByID(smid);
					List<StockAlterMoveIdcode> omidcodelist = appmi.getStockAlterMoveIdcodeBysamid(smid, 1);
					//auditTakeTicketAction.addIdcodeOM(sm, omidcodelist);
					//auditTakeTicketAction.addPayable(sm, users);
					this.addProductStockpileOMReal(batchBitList, sm);
					//更改条码dealerreceive状态
					//appidcode.updIdcodeDealerreceiveValByttid(tt.getInwarehouseid(), ttid);
				}

				request.setAttribute("result", "databases.audit.success");
				DBUserLog.addUserLog(userid, 7, "入库>>订购入库>>签收订购入库,编号：" + smid);
			}else{
				//当收货机构需要扫描，则将单据设为已签收，不增加库存
				if(os!=null && os.getIsscan()==1){
					asm.updStockAlterMoveIsComplete(smid, 1, userid);
				}
			}
		}
		return null;
	}

	protected void outRealProductStockpile(List<TakeTicketDetailBatchBit> batchBits, String warehouseid) throws Exception 
	{
		//更新库位库存表
		for (TakeTicketDetailBatchBit batchBit : batchBits) {
			appps.outRealProductStockpile(batchBit.getProductid(), batchBit.getUnitid(),batchBit
					.getBatch(),batchBit.getQuantity(), batchBit.getRealQuantity(), warehouseid,
					batchBit.getWarehouseBit(),batchBit.getTtid());
			
			appps.outRealProductStockpileAll(batchBit.getProductid(),batchBit.getUnitid(), batchBit
					.getBatch(),batchBit.getQuantity(), batchBit.getRealQuantity(), warehouseid,
					batchBit.getWarehouseBit(),batchBit.getTtid());
			
			//更新检货数量
			TakeTicket tt = apb.getTakeTicketById(batchBit.getTtid());
//			AppFUnit appFunit= new AppFUnit();
//			double quantity = appFunit.getQuantity(batchBit.getProductid(), 2, batchBit.getQuantity());	
			if (tt.getBsort().intValue() == 0) {
				apid.updSaleOrderTakeQuantity(tt.getBillno(), batchBit.getProductid(), batchBit.getQuantity(), tt.getWarehouseid());
			} else {
				 
				apid.updTakeQuantity(tt.getBsort(), tt.getBillno(), batchBit.getProductid(), batchBit.getQuantity());
			}
							
		}
		//合并库存出库清单(用户库存出库:非库位库存)
//		List<TakeTicketDetailBatchBit> allList = this.amountAllStockPileList(batchBits);
		//更新所有库存表
		/*
		for (TakeTicketDetailBatchBit takeTicketDetailBatchBit : allList) {
			appall.outRealProductStockpileAll(takeTicketDetailBatchBit.getProductid(), takeTicketDetailBatchBit.getBatch(), takeTicketDetailBatchBit.getRealQuantity(), warehouseid);
		}
		*/
		
	}

	/*private List<TakeTicketDetailBatchBit> amountAllStockPileList(
			List<TakeTicketDetailBatchBit> batchBits) {
		List<TakeTicketDetailBatchBit> returnList = new ArrayList<TakeTicketDetailBatchBit>();

		for (TakeTicketDetailBatchBit takeTicketDetailBatchBit : batchBits) {
			//第一条数据添加
			if (returnList.size() == 0) {
				TakeTicketDetailBatchBit newBatchBit = new TakeTicketDetailBatchBit();
				copyPro(newBatchBit,takeTicketDetailBatchBit);
				returnList.add(newBatchBit);
			} else {
				// 存在的话增加数量
				TakeTicketDetailBatchBit existBatchBit = existNotSameBit(returnList, takeTicketDetailBatchBit);
				if(existBatchBit != null){
					existBatchBit.setRealQuantity(existBatchBit.getRealQuantity() + takeTicketDetailBatchBit.getRealQuantity());
				// 不存在则直接添加入list
				}else{
					TakeTicketDetailBatchBit newBatchBit = new TakeTicketDetailBatchBit();
					copyPro(newBatchBit,takeTicketDetailBatchBit);
					returnList.add(newBatchBit);
				}
			}
		}
		return returnList;
	}*/

	/*private TakeTicketDetailBatchBit existNotSameBit(List<TakeTicketDetailBatchBit> list,
			TakeTicketDetailBatchBit batchBit) {
		for (TakeTicketDetailBatchBit batchBit2 : list) {
			//产品,批次相同,仓位不相同的找出
			if (batchBit.getProductid().equals(batchBit2.getProductid())
					&& batchBit.getBatch().equals(batchBit2.getBatch())
					&& !batchBit.getWarehouseBit().equals(batchBit2)) {
				return batchBit2;
			}
		}
		return null;
	}*/

	protected void addProductStockpileOMReal(List<TakeTicketDetailBatchBit> batchBits, StockAlterMove sm)
			throws Exception {
		AppProductStockpile aps = new AppProductStockpile();
		AppProduct ap = new AppProduct();
		ProductStockpile ps = null;
		for (TakeTicketDetailBatchBit batchBit : batchBits) {
			ps = new ProductStockpile();
			ps.setId(Long.valueOf(MakeCode.getExcIDByRandomTableName(	"product_stockpile", 0, "")));
			ps.setProductid(batchBit.getProductid());
			ps	.setCountunit(ap.getProductByID(ps.getProductid()).getCountunit());
			ps.setBatch(batchBit.getBatch());
			
			ps.setProducedate("");
			 ps.setVad("");
			ps.setWarehouseid(sm.getInwarehouseid());
			// 下级默认仓位
			ps.setWarehousebit("000");
			ps.setMakedate(DateUtil.StringToDatetime(DateUtil.getCurrentDateTime()));
			ps.setVerifyStatus(1);
			aps.addProductByPurchaseIncome(ps);
			aps.inProductStockpile(ps.getProductid(), batchBit.getUnitid(), ps.getBatch(), batchBit.getRealQuantity(), ps.getWarehouseid(), ps
					.getWarehousebit(), sm.getId(), "订购入库-入库");
//			ProductCostService.updateCost(sm.getInwarehouseid(), ps.getProductid(), ps.getBatch());
		}
	}
	
	public void addProductStockpileSM(List<TakeTicketDetailBatchBit> batchBits, StockMove sm) throws Exception
	{
		AppProductStockpile aps = new AppProductStockpile();
		AppProduct ap = new AppProduct();
		ProductStockpile ps = null;
		for (TakeTicketDetailBatchBit batchBit : batchBits)
		{
			ps = new ProductStockpile();

			ps.setId(Long.valueOf(MakeCode.getExcIDByRandomTableName("product_stockpile", 0, "")));
			ps.setProductid(batchBit.getProductid());

			ps.setCountunit(ap.getProductByID(ps.getProductid()).getCountunit());

			//如果批次长度为12位  就截取前9位作为批次保存
			if(batchBit.getBatch()!=null && !batchBit.getBatch().equals("") &&batchBit.getBatch().length()==12 ){
				
				String batch =batchBit.getBatch().substring(0, 9);
				ps.setBatch(batch);
			}else{
				//否则按原批次存储
				ps.setBatch(batchBit.getBatch());
			}
			
			ps.setProducedate("");
			ps.setVad("");
			ps.setWarehouseid(sm.getInwarehouseid());
			ps.setWarehousebit(batchBit.getWarehouseBit());
			ps.setMakedate(DateUtil.StringToDatetime(DateUtil.getCurrentDateTime()));
			ps.setVerifyStatus(1);
			aps.addProductByPurchaseIncome(ps);

			aps.inProductStockpile(ps.getProductid(), batchBit.getUnitid(), ps.getBatch(), batchBit.getRealQuantity(), ps.getWarehouseid(), ps.getWarehousebit(),
									sm.getId(), "转仓签收-入库");
			ProductCostService.updateCost(sm.getInwarehouseid(), ps.getProductid(), ps.getBatch());
		}
	}
	/**
	 * 删除数量为0的项目
	 * @param ttid 检货小票号
	 * @throws HibernateException
	 * @throws SQLException
	 * @throws Exception
	 */
	private void cleanZeroQuantityBatchBit(String ttid) throws HibernateException, SQLException, Exception{
		AppTakeTicketDetailBatchBit appTakeTicketDetailBatchBit = new AppTakeTicketDetailBatchBit();
		appTakeTicketDetailBatchBit.deleteZeroQuantityBatchBitByTTDID(ttid);
	}
	
	
	/*private void copyPro(TakeTicketDetailBatchBit newBatchBit,TakeTicketDetailBatchBit oldBatchBit){
		
		newBatchBit.setProductid(oldBatchBit.getProductid());
		newBatchBit.setBatch(oldBatchBit.getBatch());
		newBatchBit.setQuantity(oldBatchBit.getQuantity());
		newBatchBit.setRealQuantity(oldBatchBit.getRealQuantity());
	}*/
	
	/**
	 * 根据月份初始化库位库存.并进行预出库
	 */
	@SuppressWarnings("unchecked")
	protected static List<TakeTicketDetailBatchBit> initBatchBit(TakeTicket tt,Integer ttdid,String pid,String monthBatch,Integer checkBoxNum,Double checkscatterNum) throws Exception {
		AppProductStockpile appProductStockpile = new AppProductStockpile();
//		AppFUnit af = new AppFUnit();
		// 页面显示用的库位库存列表
		List<TakeTicketDetailBatchBit> returnList = new ArrayList<TakeTicketDetailBatchBit>();
		AppProduct ap =new AppProduct();
		Product p ;
			p =ap.getProductByID(pid);
			//箱到最小单位转换率
			double boxTransfrom = 0d;
			//散到最小单位转换率
			double scatterTransfrom = 1d;
			//箱转化为最小单位
			double xtsQuantity = ArithDouble.mul(checkBoxNum,boxTransfrom) ;
			//散转化为最小单位
			double stsQuantity = ArithDouble.mul(checkscatterNum,scatterTransfrom);
			//订购的数量(最小单位数)
			double needQuantity =  ArithDouble.add(xtsQuantity, stsQuantity);
			
			double needQuantityTemp = needQuantity;
			
			// 找到对应的批次库位库存  按照批次升序排列
			List<ProductStockpile> stockpiles = appProductStockpile.getMonthPSByPIDWIDBatch(pid, tt.getWarehouseid(),monthBatch);
			//将库存转化为份库存
			for (ProductStockpile productStockpile : stockpiles) {
				// 检查库存是否为0,0的不进行初始化库位
				if (productStockpile.getStockpile() <= 0) {
					continue;
				}
				
				TakeTicketDetailBatchBit batchBit = new TakeTicketDetailBatchBit();
				batchBit.setTtid(tt.getId());
				batchBit.setTtdid(ttdid);
				batchBit.setProductid(pid);
				batchBit.setProductname(p.getProductname());
				batchBit.setBatch(productStockpile.getBatch());
				batchBit.setWarehouseBit(productStockpile.getWarehousebit());
				batchBit.setStockQuantity(productStockpile.getStockpile());
				
				//箱到最小单位转化率
//				Double boxq = af.getXQuantity(takeTicketDetail.getProductid(), 2);
				Double boxq = boxTransfrom;
				//散到最小单位转化率
				Double scaq = scatterTransfrom;
				//得到库存整箱数
				int q = 0;
				batchBit.setStockboxnum(q);
				//得到库存散数
				double tqu = productStockpile.getStockpile();
				batchBit.setStockscatternum(tqu);

				batchBit.setUnitid(p.getScatterunitid());
				batchBit.setXtsQuantity(boxq);
				batchBit.setStsQuantity(scaq);
	
				//当库存数量大于订购数量时
				if (productStockpile.getStockpile() >= needQuantityTemp) {
					if(needQuantity==needQuantityTemp){
						batchBit.setBoxnum(checkBoxNum);
						batchBit.setScatternum(checkscatterNum);
						batchBit.setQuantity(checkscatterNum);
						batchBit.setRealboxnum(checkBoxNum);
						batchBit.setRealscatternum(checkscatterNum);
						batchBit.setRealQuantity(checkscatterNum);
					}else{
						q = 0;
						batchBit.setRealboxnum(q);
						
						tqu = ArithDouble.sub(needQuantityTemp, ArithDouble.mul(boxq, (double)q));
						batchBit.setRealscatternum(ArithDouble.div(tqu, scaq));
						batchBit.setRealQuantity(ArithDouble.div(tqu, scaq));
						batchBit.setBoxnum(checkBoxNum);
						batchBit.setScatternum(checkscatterNum);
						batchBit.setQuantity(checkscatterNum);
					}
					
					needQuantityTemp = 0;
				} else {// 如果库存不足需求量,设置最大库存，并取下一个库位库存,需求量扣减
					batchBit.setRealboxnum(0);
					batchBit.setRealscatternum(ArithDouble.div( ArithDouble.sub(productStockpile.getStockpile(), ArithDouble.mul(boxq, (double)q)), scaq));
					batchBit.setRealQuantity(ArithDouble.div( ArithDouble.sub(productStockpile.getStockpile(), ArithDouble.mul(boxq, (double)q)), scaq));
					batchBit.setBoxnum(checkBoxNum);
					batchBit.setScatternum(checkscatterNum);
					batchBit.setQuantity(checkscatterNum);
					
					needQuantityTemp = ArithDouble.sub(needQuantityTemp, productStockpile.getStockpile()) ;
				}

				returnList.add(batchBit);

			}
			//判断是否全部完全分配
			if(needQuantityTemp > 0){//若还有需要分配数量,即数量未分配完
				//删除不符合的对象
				for (int i = 0; i < returnList.size(); i++) {
					TakeTicketDetailBatchBit batchBit = returnList.get(i);
					if(batchBit.getTtdid().equals(ttdid)){
						returnList.remove(batchBit);
						i--;
					}
				} 
			}
		return returnList;
	}
}
