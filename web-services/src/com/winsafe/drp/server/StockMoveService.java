package com.winsafe.drp.server;

import java.util.ArrayList;
import java.util.List;

import com.winsafe.drp.dao.AppFUnit;
import com.winsafe.drp.dao.AppMoveApplyDetail;
import com.winsafe.drp.dao.AppOrgan;
import com.winsafe.drp.dao.AppProductStockpileAll;
import com.winsafe.drp.dao.AppStockMove;
import com.winsafe.drp.dao.AppStockMoveDetail;
import com.winsafe.drp.dao.AppTakeTicket;
import com.winsafe.drp.dao.AppTakeTicketDetail;
import com.winsafe.drp.dao.AppWarehouse;
import com.winsafe.drp.dao.MoveApply;
import com.winsafe.drp.dao.MoveApplyDetail;
import com.winsafe.drp.dao.Organ;
import com.winsafe.drp.dao.StockMove;
import com.winsafe.drp.dao.StockMoveDetail;
import com.winsafe.drp.dao.TakeTicket;
import com.winsafe.drp.dao.TakeTicketDetail;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.dao.Warehouse;
import com.winsafe.drp.util.Dateutil;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.MakeCode;



public class StockMoveService {
	
	private AppTakeTicketDetail attd =new AppTakeTicketDetail();
	private AppTakeTicket aptt = new AppTakeTicket();
	private AppOrgan appOrgan = new AppOrgan();
    private AppProductStockpileAll appps = new AppProductStockpileAll();
    private AppFUnit appfu = new AppFUnit();
    private AppWarehouse aw = new AppWarehouse();
    private AppStockMove asm = new AppStockMove();
    private AppStockMoveDetail asmd = new AppStockMoveDetail();
    private AppMoveApplyDetail amad = new AppMoveApplyDetail();
    
    /**
	 * 检查库存
	 * @throws Exception 
	 */
	public boolean checkStock(StockMove sm,List<StockMoveDetail> smdList) throws Exception{
		return checkStock(sm, smdList, null);
	}
    
	/**
	 * 检查库存
	 * @throws Exception 
	 */
	public boolean checkStock(StockMove sm,List<StockMoveDetail> smdList,StringBuffer errorMsg) throws Exception{
		// 如果仓库属性[是否负库存]为0,则检查库存
		Warehouse outWarehouse = aw.getWarehouseByID(sm.getOutwarehouseid());
		if(outWarehouse.getIsMinusStock() == null || outWarehouse.getIsMinusStock() == 0){
			for (StockMoveDetail sod : smdList)
			{
				double q = appfu.getQuantity(sod.getProductid(), sod.getUnitid(), sod.getQuantity());
				double stock = appps.getProductStockpileAllByPIDWID(sod.getProductid(), sm.getOutwarehouseid());
				// 库存大于实际数量
				if (q > stock)
				{
					//如果不须要错误信息则直接返回结果
					if(errorMsg == null){
						return false;
					}
					errorMsg.append("产品 [ " + sod.getProductid()+" "+ sod.getProductname() + " ] 库存不足<br/>");
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
	 * 复核单据
	 */
	public void addTakeTicket(StockMove so, List<StockMoveDetail> smds,	UsersBean users) throws Exception {
		
		
		Organ outOrgan = appOrgan.getOrganByID(so.getOutorganid());
		Organ inOrgan = appOrgan.getOrganByID(so.getInorganid());
		
		TakeTicket tt = new TakeTicket();
		tt.setId(MakeCode.getExcIDByRandomTableName("take_ticket", 2, "TT"));
		so.setTtid(tt.getId());
		tt.setWarehouseid(so.getOutwarehouseid());
		tt.setBillno(so.getId());
		tt.setBsort(2); //2 转仓
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
			ttd.setNccode(samd.getNccode());
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
		sm.setMakeorganid(ma.getMakeorganid());
		sm.setMakedeptid(users.getMakedeptid());
		sm.setMakeid(ma.getMakeid());
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
			asmd.addStockMoveDetail(smd);
			smdList.add(smd);
			amad.updAlterMoveApply(mad);
		}
		sm.setKeyscontent(keyscontent.toString());
		
		
		//增加检货出库单据
		addTakeTicket2(sm, smdList, users);
		//更新单据为复核状态
		sm.setIsaudit(1);
		sm.setAuditid(users.getUserid());
		sm.setAuditdate(DateUtil.getCurrentDate());
		asm.addStockMove(sm);
		
	}
	
	public void addTakeTicket2(StockMove so, List<StockMoveDetail> smds, UsersBean users) throws Exception {
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
		tt.setMakeorganid(so.getMakeorganid());
		tt.setMakedeptid(users.getMakedeptid());
		tt.setMakeid(so.getMakeid());
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
