package com.winsafe.drp.action.warehouse;

import java.util.ArrayList;
import java.util.List;

import com.winsafe.drp.dao.AppMoveApplyDetail;
import com.winsafe.drp.dao.AppOrgan;
import com.winsafe.drp.dao.AppStockMove;
import com.winsafe.drp.dao.AppStockMoveDetail;
import com.winsafe.drp.dao.AppTakeTicket;
import com.winsafe.drp.dao.AppTakeTicketDetail;
import com.winsafe.drp.dao.MoveApply;
import com.winsafe.drp.dao.MoveApplyDetail;
import com.winsafe.drp.dao.Organ;
import com.winsafe.drp.dao.StockMove;
import com.winsafe.drp.dao.StockMoveDetail;
import com.winsafe.drp.dao.TakeTicket;
import com.winsafe.drp.dao.TakeTicketDetail;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.server.StockMoveService;
import com.winsafe.drp.util.Dateutil;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.MakeCode;

public class OrganMoveService {
	private AppStockMove appStockMove = new AppStockMove();
	private AppStockMoveDetail appStockMoveDetail = new AppStockMoveDetail();
	private AppMoveApplyDetail appMoveApplyDetail = new AppMoveApplyDetail();
	private StockMoveService smService = new StockMoveService();
	
	
	/**
	 * 新增机构间转仓单据
	 * Create Time 2014-10-15 下午02:26:51 
	 * @param ma
	 * @param mads
	 * @param users
	 * @throws Exception
	 */
	public void addStockMove(MoveApply ma, List<MoveApplyDetail> mads,
			UsersBean users) throws Exception {
		StockMove sm = new StockMove();
//		String smid = MakeCode.getExcIDByRandomTableName("stock_move", 2, "SM");
		sm.setId(ma.getId());
		sm.setMovedate(ma.getMovedate());
		sm.setOutwarehouseid(ma.getOutwarehouseid());
		sm.setInorganid(ma.getInorganid());
		sm.setOlinkman(ma.getOlinkman());
		sm.setOtel(ma.getOtel());
		sm.setInwarehouseid(ma.getInwarehouseid());
		sm.setTransportmode(ma.getTransportmode());
		sm.setTransportaddr(ma.getTransportaddr());
		sm.setMovecause(ma.getMovecause());
		sm.setRemark(ma.getRemark());
		sm.setIsaudit(0);
		sm.setMakeorganid(users.getMakeorganid());
		sm.setMakedeptid(users.getMakedeptid());
		sm.setMakeid(users.getUserid());
		sm.setMakedate(DateUtil.StringToDatetime(DateUtil.getCurrentDateTime()));
		//是否出库
		sm.setIsshipment(0);
		//是否完成
		sm.setIscomplete(0);
		//是否作废
		sm.setIsblankout(0);
		//转出机构
		sm.setOutorganid(ma.getOutorganid());
		
		StringBuffer keyscontent = new StringBuffer();
		keyscontent.append(sm.getId()).append(",");
		
		List<StockMoveDetail> smdList = new ArrayList<StockMoveDetail>();
		
		for (MoveApplyDetail mad : mads) { 
			StockMoveDetail smd = new StockMoveDetail();
			smd.setId(Integer.valueOf(MakeCode.getExcIDByRandomTableName("stock_move_detail", 0, "")));
			smd.setSmid(ma.getId());
			smd.setProductid(mad.getProductid());
			smd.setProductname(mad.getProductname());
			smd.setSpecmode(mad.getSpecmode());
			//单位
			smd.setUnitid(mad.getUnitid());
			smd.setQuantity(mad.getCanquantity());
			//检货数量
			smd.setTakequantity(0d);
			smd.setMakedate(Dateutil.getCurrentDate());
			appStockMoveDetail.addStockMoveDetail(smd);
			smdList.add(smd);
			appMoveApplyDetail.updAlterMoveApply(mad);
		}
		sm.setKeyscontent(keyscontent.toString());
		
		// 默认复核单据,如果单据中的产品库存不足,不复核
		if(smService.checkStock(sm, smdList)){
			//增加检货出库单据
			addTakeTicket(sm, smdList, users);
			//更新单据为复核状态
			sm.setIsaudit(1);
			sm.setAuditid(users.getUserid());
			sm.setAuditdate(DateUtil.getCurrentDate());
		}
		
		appStockMove.addStockMove(sm);
		
	}
	
	
	
	public void addTakeTicket(StockMove so, List<StockMoveDetail> smds,	UsersBean users) throws Exception {
		AppTakeTicketDetail attd =new AppTakeTicketDetail();
		AppTakeTicket aptt = new AppTakeTicket();
		AppOrgan appOrgan = new AppOrgan();
		
		Organ outOrgan = appOrgan.getOrganByID(so.getOutorganid());
		Organ inOrgan = appOrgan.getOrganByID(so.getInorganid());
		
		TakeTicket tt = new TakeTicket();
		tt.setId(MakeCode.getExcIDByRandomTableName("take_ticket", 2, "TT"));
		so.setTtid(tt.getId());
		tt.setWarehouseid(so.getOutwarehouseid());
		tt.setBillno(so.getId());
		tt.setBsort(2); //2转仓单
		tt.setOid(so.getOutorganid());
		tt.setOname(outOrgan.getOrganname());
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
		tt.setInOid(so.getInorganid());
		//入库机构名称
		tt.setInOname(inOrgan.getOrganname());
		
		for (StockMoveDetail samd : smds)
		{
			TakeTicketDetail ttd = new TakeTicketDetail();
			ttd.setId(Integer.valueOf(MakeCode.getExcIDByRandomTableName("take_ticket_detail", 0, "")));
			ttd.setProductid(samd.getProductid());
			ttd.setProductname(samd.getProductname());
			ttd.setSpecmode(samd.getSpecmode());
			ttd.setUnitid(samd.getUnitid());
			ttd.setBatch(samd.getBatch());
			ttd.setUnitprice(0d);
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
		
	}

}
