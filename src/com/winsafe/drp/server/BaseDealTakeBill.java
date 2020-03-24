package com.winsafe.drp.server;

import com.winsafe.drp.dao.AppShipmentBill;
import com.winsafe.drp.dao.AppShipmentBillDetail;
import com.winsafe.drp.dao.UsersBean;

/** 
 * @author jerry
 * @version 2009-8-25 下午06:05:36 
 * www.winsafe.cn 
 * 相关单据处理业务抽象类
 */
public abstract class BaseDealTakeBill {
	protected AppShipmentBill appsb = new AppShipmentBill();
	protected AppShipmentBillDetail appsbd = new AppShipmentBillDetail();
	protected UsersBean users;
	protected String billno;
		
	public BaseDealTakeBill(UsersBean users, String billno){
		this.users = users;
		this.billno = billno;
	}
	

	protected abstract void deal() throws Exception;
	

	protected abstract void cancelDeal() throws Exception;
	
	
}
