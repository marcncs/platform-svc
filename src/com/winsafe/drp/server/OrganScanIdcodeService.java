package com.winsafe.drp.server;

import java.util.List;

import org.apache.commons.beanutils.BeanUtils;

import com.winsafe.drp.dao.AppOrganScan;
import com.winsafe.drp.dao.AppOrganTradesPIdcode;
import com.winsafe.drp.dao.AppOrganTradesTIdcode;
import com.winsafe.drp.dao.AppOrganWithdrawIdcode;
import com.winsafe.drp.dao.AppProductInterconvertIdcode;
import com.winsafe.drp.dao.AppStockAlterMoveIdcode;
import com.winsafe.drp.dao.AppStockMoveIdcode;
import com.winsafe.drp.dao.AppSupplySaleMoveIdcode;
import com.winsafe.drp.dao.OrganScan;
import com.winsafe.drp.dao.OrganTradesPIdcode;
import com.winsafe.drp.dao.OrganTradesTIdcode;
import com.winsafe.drp.dao.OrganWithdrawIdcode;
import com.winsafe.drp.dao.ProductInterconvertIdcode;
import com.winsafe.drp.dao.StockAlterMoveIdcode;
import com.winsafe.drp.dao.StockMoveIdcode;
import com.winsafe.drp.dao.SupplySaleMoveIdcode;
import com.winsafe.drp.dao.TakeTicket;
import com.winsafe.drp.dao.TakeTicketIdcode;
import com.winsafe.hbm.util.MakeCode;

/** 
 * 入库是否扫机身号
 * @author: jelli 
 * @version:2009-9-5 下午06:51:22 
 * @copyright:www.winsafe.cn
 */
public class OrganScanIdcodeService {

	
	
	public void scan(TakeTicket tt, List<TakeTicketIdcode> idlist) throws Exception{
		String scb = "";
		switch ( tt.getBsort().intValue() ){
			case 1 : scb = "OM"; break;
			case 2 : scb = "SM"; break;
			case 3 : scb = "DM"; break;
			case 6 : scb = "PC"; break;
			case 7 : scb = "OW"; break;
			case 8 : scb = "OT"; break;
			case 9 : scb = "OT"; break;
		}			
		if ( !scb.equals("") ){
			AppOrganScan appos = new AppOrganScan();
			
			OrganScan os = appos.getOrganScanByOidScb( tt.getBsort()==6?tt.getMakeorganid():tt.getOid(), scb);
			if ( os == null ){
				return;
			}
			if ( os.getIsscan().intValue() == 0 ){
				if ( !isNoOtherBit(tt.getInwarehouseid()) ){
					return;
				}
				switch ( tt.getBsort().intValue() ){
					
					case 1 : addStockAlterMoveIdcode(idlist, tt.getBillno()); break;
					
					case 2 : addStockMoveIdcode(idlist, tt.getBillno()); break;
					
					case 3 : addSupplySaleMoveIdcode(idlist, tt.getBillno()); break;
					
					case 6 : addProductInterconvertIdcode(idlist, tt.getBillno()); break;
					
					case 7 : addOrganWithdrawIdcode(idlist, tt.getBillno()); break;
					
					case 8 : addOrganTradesPIdcode(idlist, tt.getBillno()); break;
					
					case 9 : addOrganTradesTIdcode(idlist, tt.getBillno()); break;
				}					
			}
		}
	}
	
	private boolean isNoOtherBit(String warehouseid){
		WarehouseService ws = new WarehouseService();
		try{
			List list = ws.getWarehouseBitByWid(warehouseid);
			if ( !list.isEmpty() && list.size()>1 ){
				return false;
			}
		}catch(Exception e ){
			e.printStackTrace();
		}
		return true;
	}
	
	
	private void addStockAlterMoveIdcode(List<TakeTicketIdcode> idlist, String billno) throws Exception {
		AppStockAlterMoveIdcode app = new AppStockAlterMoveIdcode();
		for ( TakeTicketIdcode tti : idlist ){
			StockAlterMoveIdcode idcode = new StockAlterMoveIdcode();
			BeanUtils.copyProperties(idcode, tti);
//			idcode.setId(Long.valueOf(MakeCode.getExcIDByRandomTableName(
//					"stock_alter_move_idcode", 0, "")));
			idcode.setSamid(billno);
			idcode.setWarehousebit("000");
			app.addStockAlterMoveIdcode(idcode);
		}
	}
	
	private void addStockMoveIdcode(List<TakeTicketIdcode> idlist, String billno) throws Exception {
		AppStockMoveIdcode app = new AppStockMoveIdcode();
		for ( TakeTicketIdcode tti : idlist ){
			StockMoveIdcode idcode = new StockMoveIdcode();
			BeanUtils.copyProperties(idcode, tti);
			idcode.setId(Long.valueOf(MakeCode.getExcIDByRandomTableName(
					"stock_move_idcode", 0, "")));
			idcode.setSmid(billno);
			idcode.setWarehousebit("000");
			app.addStockMoveIdcode(idcode);
		}
	}
	
	private void addSupplySaleMoveIdcode(List<TakeTicketIdcode> idlist, String billno) throws Exception {
		AppSupplySaleMoveIdcode app = new AppSupplySaleMoveIdcode();
		for ( TakeTicketIdcode tti : idlist ){
			SupplySaleMoveIdcode idcode = new SupplySaleMoveIdcode();
			BeanUtils.copyProperties(idcode, tti);
			idcode.setId(Long.valueOf(MakeCode.getExcIDByRandomTableName(
					"supply_sale_move_idcode", 0, "")));
			idcode.setSsmid(billno);
			idcode.setWarehousebit("000");
			app.addSupplySaleMoveIdcode(idcode);
		}
	}
	
	
	private void addProductInterconvertIdcode(List<TakeTicketIdcode> idlist, String billno) throws Exception {
		AppProductInterconvertIdcode app = new AppProductInterconvertIdcode();
		for ( TakeTicketIdcode tti : idlist ){
			ProductInterconvertIdcode idcode = new ProductInterconvertIdcode();
			BeanUtils.copyProperties(idcode, tti);
			idcode.setId(Long.valueOf(MakeCode.getExcIDByRandomTableName(
					"product_interconvert_idcode", 0, "")));
			idcode.setPiid(billno);
			idcode.setWarehousebit("000");
			app.addProductInterconvertIdcode(idcode);
		}
	}
	
	private void addOrganWithdrawIdcode(List<TakeTicketIdcode> idlist, String billno) throws Exception {
		AppOrganWithdrawIdcode app = new AppOrganWithdrawIdcode();
		for ( TakeTicketIdcode tti : idlist ){
			OrganWithdrawIdcode idcode = new OrganWithdrawIdcode();
			BeanUtils.copyProperties(idcode, tti);
			idcode.setId(Long.valueOf(MakeCode.getExcIDByRandomTableName(
					"organ_withdraw_idcode", 0, "")));
			idcode.setOwid(billno);
			idcode.setWarehousebit("000");
			app.addOrganWithdrawIdcode(idcode);
		}
	}
	
	private void addOrganTradesPIdcode(List<TakeTicketIdcode> idlist, String billno) throws Exception {
		AppOrganTradesPIdcode app = new AppOrganTradesPIdcode();
		for ( TakeTicketIdcode tti : idlist ){
			OrganTradesPIdcode idcode = new OrganTradesPIdcode();
			BeanUtils.copyProperties(idcode, tti);
			idcode.setId(Long.valueOf(MakeCode.getExcIDByRandomTableName(
					"organ_trades_p_idcode", 0, "")));
			idcode.setOtid(billno);
			idcode.setWarehousebit("000");
			app.addOrganTradesPIdcode(idcode);
		}
	}
	
	private void addOrganTradesTIdcode(List<TakeTicketIdcode> idlist, String billno) throws Exception {
		AppOrganTradesTIdcode app = new AppOrganTradesTIdcode();
		for ( TakeTicketIdcode tti : idlist ){
			OrganTradesTIdcode idcode = new OrganTradesTIdcode();
			BeanUtils.copyProperties(idcode, tti);
			idcode.setId(Long.valueOf(MakeCode.getExcIDByRandomTableName(
					"organ_trades_t_idcode", 0, "")));
			idcode.setOtid(billno.replace("OD", "OT"));
			idcode.setWarehousebit("000");
			app.addOrganTradesTIdcode(idcode);
		}
	}
}
