package com.winsafe.drp.dao;

import java.util.List;

import com.winsafe.drp.server.WarehouseBitDafService;
import com.winsafe.drp.util.ArithDouble;
import com.winsafe.hbm.entity.HibernateUtil;


public class AppTakeService {

	private AppTakeBill aptb;
	private AppTakeTicket aptt;
	private AppTakeTicketDetail apttd;
	private AppProductStockpileAll aps;
	private AppTakeTicketIdcode aptti;
	
	public AppTakeService(){
		aptb = new AppTakeBill();
		aptt = new AppTakeTicket();
		apttd = new AppTakeTicketDetail();
		aps = new AppProductStockpileAll();
		aptti = new AppTakeTicketIdcode();
	}
	
	/**
	 * 新增检货
	 * @param tsb TakeServiceBean
	 * @param isPrepareOut 是否占用库存
	 * @throws Exception
	 */
	public String addTake(TakeServiceBean tsb, boolean isPrepareOut) throws Exception {
		
		if ( tsb == null ){
			return "f";
		}
		for ( String arehouseid : tsb.getWarehouseids() ){
			TakeTicket tt = tsb.getTtmap().get(arehouseid);
			aptt.addTakeTicket(tt);
			aptb.addTakeBill(tsb.getTakebill());
			WarehouseBitDafService wbds = new WarehouseBitDafService("take_ticket_idcode","ttid",tt.getWarehouseid());
			for ( TakeTicketDetail ttd : tt.getTtdetails() ){
				ttd.setCost(aps.getCost(tt.getWarehouseid(), ttd.getProductid(), ttd.getBatch()));
				
				//依据产品id和ttid得到TT明细
				List isttd = apttd.getTakeTicketDetailByTtidPid(tt.getId(), ttd.getProductid());
				//存在
				if(isttd.size()>0){
					//累加数量
					TakeTicketDetail attd = (TakeTicketDetail)isttd.get(0);
					attd.setQuantity(ArithDouble.add(attd.getQuantity(),ttd.getQuantity()));
					//attd.setBoxnum(attd.getBoxnum()+ttd.getBoxnum());
					attd.setScatternum(ArithDouble.add(attd.getScatternum(),ttd.getScatternum()));
					attd.setBatch("");
					apttd.updTakeTicketDetail(attd);
					
				}else{
					//不存在新增
					apttd.addTakeTicketDetail(ttd);
				}
				
				wbds.add(tt.getId(), ttd.getProductid(), ttd.getUnitid(), ttd.getQuantity(), ttd.getBatch());
			}
		}
		if ( isPrepareOut ){
			
			this.prepareOut(tsb);
		}
		return "s";
	}
	
	/**
	 * 占用库存
	 * @param tsb TakeServiceBean
	 * @return 
	 * @throws Exception
	 */
	private String prepareOut(TakeServiceBean tsb) throws Exception {
		if ( tsb == null ){
			return "f";
		}	
		for ( String arehouseid : tsb.getWarehouseids() ){
			TakeTicket tt = tsb.getTtmap().get(arehouseid);
			for ( TakeTicketDetail ttd : tt.getTtdetails() ){				
				aps.prepareOut(ttd.getProductid(), ttd.getUnitid(), tt.getWarehouseid(), ttd.getBatch(), ttd.getQuantity());
			}			
		}
		return "s";
	}
	
	/**
	 * 用单据编号获取检货小票
	 * @param billno 单据编号
	 * @return 检货小票列表及明细
	 * @throws Exception
	 */
	public List<TakeTicket> getTakeTicketByBillno(String billno) throws Exception{
		List<TakeTicket> ttlist = aptt.getTakeTicketByBillno(billno);
		for ( TakeTicket tt: ttlist ){
			List<TakeTicketDetail> dl = apttd.getTakeTicketDetailByTtid(tt.getId());
			tt.setTtdetails(dl);
		}
		return ttlist;
	}
	
	/**
	 * 检货小票复核
	 * @param ttlist
	 * @return true 复核/false 未复核
	 * @throws Exception
	 */
	public boolean isAuditTakeTicket(List<TakeTicket> ttlist) throws Exception{
		if ( null == ttlist ){
			return false;
		}
		for ( TakeTicket tt : ttlist ){
			if ( 1 == tt.getIsaudit() ){
				return true;
			}
		}
		return false;
	}
	

	/**
	 * 检货小票否复核
	 * @param ttlist
	 * @return true 复核/false 未复核
	 * @throws Exception
	 */
	public boolean isNotAuditTakeTicket(List<TakeTicket> ttlist) throws Exception{
		if ( null == ttlist ){
			return false;
		}
		for ( TakeTicket tt : ttlist ){
			if ( 0 == tt.getIsaudit() ){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 删除检货
	 * @param ttlist
	 * @return
	 * @throws Exception
	 */
	public String deleteTake(List<TakeTicket> ttlist) throws Exception{
		for ( TakeTicket tt:ttlist ){
			apttd.delTakeTicketDetailByTtid(tt.getId());
			aptti.delTakeTicketIdcodeByTiid(tt.getId());
			aptt.delTakeTicketByid(tt.getId());
		}
		return "s";
	}
	
	public void deleteTake(TakeTicket tt) throws Exception{
		aptti.delTakeTicketIdcodeByTiid(tt.getId());
		apttd.delTakeTicketDetailByTtid(tt.getId());
		aptt.delTakeTicketByid(tt.getId());
	}
	
	/**
	 * 释放库存
	 * @param ttlist 检货小票列表及明细
	 * @return 
	 * @throws Exception
	 */
	private String freeStockpile(List<TakeTicket> ttlist) throws Exception{
		if ( null == ttlist ){
			return "f";
		}
		for ( TakeTicket tt:ttlist ){
			for ( TakeTicketDetail ttd : tt.getTtdetails() ){
				aps.freeStockpile(ttd.getProductid(), ttd.getUnitid(),tt.getWarehouseid(),ttd.getBatch(), ttd.getQuantity());
			}
		}
		return "s";
	}
}
