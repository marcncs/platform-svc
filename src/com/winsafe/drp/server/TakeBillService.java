package com.winsafe.drp.server;

import java.util.Date;

import com.winsafe.drp.dao.AppTakeBill;
import com.winsafe.drp.dao.TakeBill;
import com.winsafe.drp.dao.UsersBean;

/** 
 * @author jerry
 * @version 2009-8-25 下午06:05:36 
 * www.winsafe.cn 
 */
public class TakeBillService {
	private AppTakeBill apptb = new AppTakeBill();
	private BaseDealTakeBill baseDeal;
	private UsersBean users;
	
	private TakeBill tb;
	
	public TakeBillService(TakeBill tb, UsersBean users){
		this.tb= tb;
		this.users = users;
		newBaseDeal();
	}
	

	public void auditDeal() throws Exception{
		
		apptb.updTakeStatus(tb.getBsort(), tb.getId(), 1);
		
		
		apptb.updIsAudit(tb.getId(), users.getUserid(), 1);
		
		baseDeal.deal();
	}
	

	public void auditDealByTime(String datetime) throws Exception {
		
       apptb.updTakeStatus(tb.getBsort(), tb.getId(), 1);
		apptb.updIsAuditByTime(tb.getId(), users.getUserid(), 1,datetime);
		baseDeal.deal();
		
	}
	

	public void cancelAuditDeal() throws Exception{
		
		apptb.updTakeStatus(tb.getBsort(), tb.getId(), 0);
		
		
		apptb.updIsAudit(tb.getId(), users.getUserid(), 0);	
		
		
		baseDeal.cancelDeal();
	}
	

	private void newBaseDeal(){		
		switch ( tb.getBsort().intValue() ){

			case 0 : baseDeal = new SaleOrderBDImpl(users, tb.getId()); break;

			case 1 : baseDeal = new StockAlterMoveBDImpl(users, tb.getId()); break;

			case 2 : baseDeal = new StockMoveBDImpl(users, tb.getId()); break;

			case 3 : baseDeal = new SupplySaleMoveBDImpl(users, tb.getId()); break;

			case 4 : baseDeal = new DrawShipmentBillBDImpl(users, tb.getId()); break;

			case 5 : baseDeal = new HarmShipmentBillBDImpl(users ,tb.getId()); break;

			case 6 : baseDeal = new ProductInterconvertBDImpl(users ,tb.getId()); break;

			case 7 : baseDeal = new OrganWithdrawBDImpl(users ,tb.getId()); break;

			case 8 : baseDeal = new OrganTradesBDImpl(users ,tb.getId()); break;

			case 9 : baseDeal = new OrganTradesTBDImpl(users ,tb.getId()); break;
			
			case 10 : baseDeal = new PurchaseWithdrawBDImpl(users ,tb.getId()); break;
			
			case 11 : baseDeal = new SaleTradesBDImpl(users ,tb.getId()); break;
			
			case 12 : baseDeal = new IntegralOrderBDImpl(users ,tb.getId()); break;
			
			case 13 : baseDeal = new PurchaseTradesBDImpl(users ,tb.getId()); break;
			
			case 14 : baseDeal = new SampleBillBDImpl(users ,tb.getId()); break;
		}
		
	}	
}
