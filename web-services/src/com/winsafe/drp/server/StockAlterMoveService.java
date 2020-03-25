package com.winsafe.drp.server;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.winsafe.common.util.StringUtil;
import com.winsafe.drp.dao.AppOrgan;
import com.winsafe.drp.dao.AppProduct;
import com.winsafe.drp.dao.AppStockAlterMove;
import com.winsafe.drp.dao.AppStockAlterMoveDetail;
import com.winsafe.drp.dao.AppSuggestInspect;
import com.winsafe.drp.dao.AppSuggestInspectDetail;
import com.winsafe.drp.dao.AppTakeService;
import com.winsafe.drp.dao.AppTakeTicket;
import com.winsafe.drp.dao.AppTakeTicketDetail;
import com.winsafe.drp.dao.AppWarehouse;
import com.winsafe.drp.dao.Organ;
import com.winsafe.drp.dao.Product;
import com.winsafe.drp.dao.StockAlterMove;
import com.winsafe.drp.dao.StockAlterMoveDetail;
import com.winsafe.drp.dao.SuggestInspect;
import com.winsafe.drp.dao.SuggestInspectDetail;
import com.winsafe.drp.dao.TakeBill;
import com.winsafe.drp.dao.TakeServiceBean;
import com.winsafe.drp.dao.TakeTicket;
import com.winsafe.drp.dao.TakeTicketDetail;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.dao.Warehouse;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.MakeCode;


/**
 * 订购单服务层，用于生成订购单和复核订购
 * Create Time 2014-04-17 下午14:15
 * @author Andy.liu
 *
 */
public class StockAlterMoveService {
	AppWarehouse aw = new AppWarehouse();
	AppOrgan ao = new AppOrgan();
	AppSuggestInspectDetail asid = new AppSuggestInspectDetail();
	AppStockAlterMove asam = new AppStockAlterMove();
	AppProduct ap = new AppProduct();
	AppStockAlterMoveDetail asamd =new AppStockAlterMoveDetail();
	AppSuggestInspect asi = new AppSuggestInspect();
	
	
	/**
	 * 判断是否存在促销品
	 * @param siid
	 * @return
	 * @throws Exception
	 */
	public boolean checkHiveGift(String siid) throws Exception {
		String whereSql = " where sid.siid ='" + siid +"' and sid.isGift = 1";
		int count = asid.getCountByWhereSql(whereSql);
		if(count > 0){
			return true;
		}else{
			return false;
		}
		
	}
	
	/**
	 * 获取拣货单详情，用以生成订购单详情
	 * @param siid
	 * @param isGift
	 * @return
	 * @throws Exception
	 */
	public List<SuggestInspectDetail> getSidBySiidAndGiftNo(String siid, int isGift) throws Exception{
		String whereSql = " where sid.siid ='" + siid +"' and sid.isGift = "+isGift;
		List<SuggestInspectDetail> list = asid.getSuggestInspectDetailByWhereSql(whereSql);
		return list;
	}
	
	/**
	 * 生成拣货单详情生成对应订购单的详情
	 * @param list
	 * @param samid
	 * @throws Exception
	 */
	public String addSamdBySidList(List<SuggestInspectDetail> list, String samid) throws Exception{
		//List<StockAlterMoveDetail> samdList = new ArrayList<StockAlterMoveDetail>();
		StockAlterMoveDetail samd = null;
		for (SuggestInspectDetail sid : list) {
			
			Product p = ap.getProductByID(sid.getProductId());
			if(p==null){
				return "databases.product.notfound";
			}
			samd = new StockAlterMoveDetail(); 
			samd.setSamid(samid);
			samd.setProductid(sid.getProductId());
			samd.setUnitid(1);
			samd.setQuantity(sid.getQuantity().doubleValue());
			samd.setProductname(sid.getProductName());
			samd.setNccode(p.getNccode());
			samd.setSpecmode(p.getSpecmode());
			samd.setUnitprice(0d);
			samd.setSubsum(0d);
			samd.setScatternum(sid.getQuantity().doubleValue());
			samd.setBoxnum(0);
			asamd.addStockAlterMoveDetail(samd);
		}
		return null;
	}
	
	
	/**
	 * 生成订购单
	 * @param request
	 * @param si
	 * @return
	 */
	public String produceStockAlterMove(HttpServletRequest request, SuggestInspect si){
		
		try {
			UsersBean users = UserManager.getUser(request);
			Integer userid = users.getUserid();
			Warehouse inWh = aw.getWhBySiName(si.getDisWareHouseName());
			if(inWh==null){
				//入库仓库不存在
				return "databases.warehouse.notfound";
			}
			Warehouse outWh = aw.getWarehouseByID("11133");
			Organ makeOrgan = ao.getOrganByID(users.getMakeorganid());
			Organ inOrgan = ao.getOrganByID(inWh.getMakeorganid());
			//生成IS订购单，并生成订购单详情，产品为产成品
			String smid = MakeCode.getExcIDByRandomTableName(
					"stock_alter_move", 2, "OM");
			//获取管家婆订购单编号 siid
			String samsiid = "";
			SuggestInspect sugg = asi.getSiBySiid(si.getSiid());
			if(!"2000001".equals(sugg.getTypeId())){
				samsiid = sugg.getSiid();
			}else{
				String whereSql = " where si.mergeId = "+si.getId();
				List<SuggestInspect> sulist = asi.getSiByIds(whereSql);
				for (SuggestInspect suggestInspect : sulist) {
					samsiid +=suggestInspect.getSiid()+",";
				}
				samsiid = samsiid.substring(0, samsiid.length()-1);
			}
//			samsiid = sugg.getSiid();
			//默认从广州丸美仓库出货
			StockAlterMove sam = new StockAlterMove();
			sam.setOutwarehouseid(outWh.getId());
			sam.setInwarehouseid(inWh.getId());
			sam.setMakeid(userid);
			sam.setNccode(si.getSiid());
			sam.setNccode2(samsiid);
			sam.setMakedate(DateUtil.getCurrentDate());
			sam.setIsaudit(0);
			sam.setIsshipment(0);
			sam.setIstally(0);
			sam.setIsblankout(0);
			sam.setIscomplete(0);
			sam.setPaymentmode(0);
			sam.setInvmsg(0);
			sam.setTransportmode(0);
			sam.setOlinkman(outWh.getUsername());
			sam.setOtel(outWh.getWarehousetel());
			sam.setMakeorganid(makeOrgan.getId());
			sam.setMakeorganidname(makeOrgan.getOrganname());
			sam.setMakedeptid(0);
			sam.setReceiveorganid(inOrgan.getId());
			sam.setReceiveorganidname(inOrgan.getOrganname());
			sam.setReceivedeptid(0);
			sam.setTransportaddr("默认仓库");
			sam.setTotalsum(0d);
			sam.setMovedate(DateUtil.getCurrentDate());
			//生成产成品订购单详情
			List<SuggestInspectDetail> productList = getSidBySiidAndGiftNo(si.getSiid(), 0);
			//根据拣货单详情判断是否需要生成促销品单
			if(checkHiveGift(si.getSiid())){
				StockAlterMove samGift = sam.clone();
				sam.setId(smid+"_1/2");
				if(productList.size() > 0){
					samGift.setId(smid+"_2/2");
				}else{
					samGift.setId(smid);
				}
				//生成促销品订购详情单
				List<SuggestInspectDetail> giftList = getSidBySiidAndGiftNo(si.getSiid(), 1);
				String retDetail = addSamdBySidList(giftList, samGift.getId());
				if(retDetail!=null){
					return retDetail;
				}
				//将促销品订购单入库
				asam.addStockAlterMove(samGift);
			}else{
				//若无促销品订单，则生成一张
				sam.setId(smid);
			}
			//若无产成品,则不生成订购单
			if(productList.size() > 0){
				String retDetail = addSamdBySidList(productList, sam.getId());
				if(retDetail!=null){
					return retDetail;
				}
				sam.setKeyscontent(sam.getId()+","+sam.getMakeorganidname());
				asam.addStockAlterMove(sam);
			}
			//生成完后，修改拣货单及拣货单详情状态及时间修改人
			String currentDate = DateUtil.getCurrentDateTime();
			asi.updateSiIsOut(si.getId(), userid, currentDate);
			asid.updateSidIsOut(si.getSiid());
		
		} catch(Exception e){
			e.printStackTrace();
			return "databases.samout.fail";
		}
		
		return null;
		
	}
	/**
	 * 新增出库单据
	 * Create Time 2014-10-15 下午02:26:51 
	 * @param so
	 * @param samds
	 * @param users
	 * @throws Exception
	 */
	public TakeTicket addTakeTicket(StockAlterMove so, List<StockAlterMoveDetail> samds, UsersBean users) throws Exception{

		AppTakeTicketDetail attd =new AppTakeTicketDetail();
		AppTakeTicket aptt = new AppTakeTicket();
		
		TakeTicket tt = new TakeTicket();
		tt.setId(MakeCode.getExcIDByRandomTableName("take_ticket", 2, "TT"));
		so.setTtid(tt.getId());
		tt.setWarehouseid(so.getOutwarehouseid());
		tt.setBillno(so.getId());
		tt.setBsort(1);
		tt.setOid(so.getOutOrganId());
		tt.setOname(so.getOutOrganName());
		tt.setRlinkman(so.getOlinkman());
		tt.setTel(so.getOtel());
		tt.setRemark(so.getRemark());
		tt.setIsaudit(0);
		tt.setIsmove(0);
		tt.setMakeorganid(users.getMakeorganid());
		tt.setMakedeptid(users.getMakedeptid());
		tt.setMakeid(users.getUserid());
		tt.setMakedate(DateUtil.getCurrentDate());
		tt.setInwarehouseid(so.getInwarehouseid());
		tt.setPrinttimes(0);
		tt.setIsblankout(0);
		//设置车号和路线
		tt.setBusNo(so.getBusNo());
		tt.setBusWay(so.getBusWay());
		//设置配送单号
		tt.setNccode(so.getNccode());
		tt.setNccode2(so.getNccode2());
		//设置未检货
		tt.setIsPicked(0);
		//设置未验货
		tt.setIsChecked(0);
		//入库机构编号
		tt.setInOid(so.getReceiveorganid());
		//入库机构名称
		tt.setInOname(so.getReceiveorganidname());
		
		for (StockAlterMoveDetail samd : samds)
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
			//箱数
			ttd.setBoxnum(samd.getBoxnum());
			//散数
			ttd.setScatternum(samd.getScatternum());
			ttd.setTtid(tt.getId());
			ttd.setIsPicked(0);
			attd.addTakeTicketDetail(ttd);
		}
		
		aptt.addTakeTicket(tt);
		
		return tt;
	}

		
	
	/**
	 * 复核订单前的添加tt和tb
	 * @param so
	 * @param pils
	 * @param users
	 * @throws Exception
	 */
	public void addTakeBill(StockAlterMove so, List<StockAlterMoveDetail> pils, UsersBean users) throws Exception{
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
		takebill.setTbBsort(so.getBsort());

		TakeServiceBean tsb = new TakeServiceBean();
		tsb.setTakebill(takebill);

		TakeTicket tt = null;
		AppTakeTicketDetail attd =new AppTakeTicketDetail();
		for (StockAlterMoveDetail pid : pils)
		{

			if (tsb.getTtmap().get(so.getOutwarehouseid()) == null)
			{
				tt = new TakeTicket();
				if(StringUtil.isEmpty(so.getTtid())){
					tt.setId(MakeCode.getExcIDByRandomTableName("take_ticket", 2, "TT"));
					so.setTtid(tt.getId());
				}else{
					tt.setId(so.getTtid());
				}
				tt.setWarehouseid(so.getOutwarehouseid());
				tt.setBillno(takebill.getId());
				tt.setBsort(1);
				tt.setOid(takebill.getOid());
				tt.setOname(takebill.getOname());
				tt.setRlinkman(takebill.getRlinkman());
				tt.setTel(takebill.getTel());
				tt.setRemark(so.getRemark());
				tt.setIsaudit(0);
				tt.setIsmove(0);
				tt.setMakeorganid(users.getMakeorganid());
				tt.setMakedeptid(users.getMakedeptid());
				tt.setMakeid(users.getUserid());
				tt.setMakedate(DateUtil.getCurrentDate());
				tt.setInwarehouseid(takebill.getInwarehouseid());
				tt.setPrinttimes(0);
				tt.setIsblankout(0);
				//设置车号和路线
				tt.setBusNo(so.getBusNo());
				tt.setBusWay(so.getBusWay());
				//设置配送单号
				tt.setNccode(so.getNccode());
				tt.setNccode2(so.getNccode2());
				//设置未检货
				tt.setIsPicked(0);
				//设置未验货
				tt.setIsChecked(0);
				tsb.getTtmap().put(so.getOutwarehouseid(), tt);
				tsb.getWarehouseids().add(so.getOutwarehouseid());
			}
			else
			{
				tt = tsb.getTtmap().get(so.getOutwarehouseid());
			}
			//不控制批次			
//			TakeTicketDetailService ttds = new TakeTicketDetailService(tt, tt.getWarehouseid(), pid.getProductid(), pid.getProductname(), pid.getSpecmode(), pid.getUnitid(), pid.getUnitprice());
//			ttds.addBatchDetail(pid.getQuantity());

			TakeTicketDetail ttd = new TakeTicketDetail();
			ttd.setId(Integer.valueOf(MakeCode.getExcIDByRandomTableName("take_ticket_detail", 0, "")));
			ttd.setProductid(pid.getProductid());
			ttd.setProductname(pid.getProductname());
			ttd.setSpecmode(pid.getSpecmode());
			ttd.setUnitid(pid.getUnitid());
			ttd.setBatch(pid.getBatch());
			ttd.setUnitprice(pid.getUnitprice());
			//数量
			ttd.setQuantity(pid.getQuantity());
			//箱数
			ttd.setBoxnum(pid.getBoxnum());
			//散数
			ttd.setScatternum(pid.getScatternum());
			ttd.setTtid(tt.getId());
			ttd.setIsPicked(0);
			tt.getTtdetails().add(ttd);
		}
		AppTakeService appts = new AppTakeService();
		appts.addTake(tsb, false);
	}
	
	
}
