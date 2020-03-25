package com.winsafe.drp.server;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.hibernate.HibernateException;

import com.winsafe.drp.entity.EntityManager;

/** 
 * @author: jelli 
 * @version:2009-10-16 下午03:19:01 
 * @copyright:www.winsafe.cn
 */
public class SelfAwakeService {
	private static Map<String, String> title = new HashMap<String, String>();
	private static Map<String, String> self = new HashMap<String, String>();
	private static Map<String, String> warehouse = new HashMap<String, String>();
	private static Map<String, String> sale = new HashMap<String, String>();
	private static Map<String, String> ditch = new HashMap<String, String>();
	private static Map<String, String> equip = new HashMap<String, String>();
	private static Map<String, String> purchase = new HashMap<String, String>();
	private static Map<String, String> machin = new HashMap<String, String>();
	private static Map<String, String> finance = new HashMap<String, String>();
	
	static{
		title.put("self", "2");
		title.put("warehouse", "15");
		title.put("sale", "13");
		title.put("ditch", "9");
		title.put("equip", "17");
		title.put("purchase", "5");
		title.put("machin", "7");
		title.put("finance", "19");
		
		self.put("se1", "/self/listAffichePublisthAction.do");
		self.put("se2", "/sys/listTaskAction.do");
		self.put("se3", "/self/listServiceAgreementAllAction.do");
		self.put("se4", "/sys/listWorkReportAction.do");
		
		warehouse.put("wa1", "/warehouse/listTakeBillAction.do");
		warehouse.put("wa2", "/warehouse/auditPurchaseIncomeAction.do");
		warehouse.put("wa3", "/warehouse/auditProductIncomeAction.do");
		warehouse.put("wa4", "/warehouse/completeStockAlterMoveReceiveAction.do");
		warehouse.put("wa5", "/warehouse/completeStockMoveReceiveAction.do");
		warehouse.put("wa6", "/warehouse/completeSupplySaleMoveReceiveAction.do");
		warehouse.put("wa7", "/warehouse/completeProductInterconvertReceiveAction.do");
		warehouse.put("wa8", "/warehouse/completeOrganWithdrawReceiveAction.do");
		warehouse.put("wa9", "/warehouse/completeOrganTradePReceiveAction.do");
		warehouse.put("wa10", "/warehouse/completeOrganTradeTReceiveAction.do");
		warehouse.put("wa11", "/warehouse/auditStockCheckAction.do");
		warehouse.put("wa12", "/warehouse/auditOtherShipmentBillAction.do");
		warehouse.put("wa13", "/warehouse/auditOtherIncomeAction.do");
		warehouse.put("wa14", "/warehouse/auditDrawShipmentBillAction.do");
		warehouse.put("wa15", "/warehouse/auditHarmShipmentBillAction.do");
		warehouse.put("wa16", "/warehouse/auditProductInterconvertAction.do");
		
		sale.put("sa1", "/sales/auditSaleOrderAction.do");
		sale.put("sa2", "/sales/auditSaleIndentAction.do");
		sale.put("sa3", "/sales/auditSaleInvoiceAction.do");
		sale.put("sa4", "/aftersale/auditWithdrawAction.do");
		sale.put("sa5", "/aftersale/auditSaleTradesAction.do");
		sale.put("sa6", "/aftersale/endcaseSaleTradesAction.do");
		sale.put("sa7", "/sales/auditIntegralOrderAction.do");
		
		ditch.put("di1", "/ditch/auditAlterMoveApplyAction.do");
		ditch.put("di2", "/ditch/toRatifyAlterMoveApply.do");
		ditch.put("di3", "/warehouse/auditStockAlterMoveAction.do");
		ditch.put("di4", "/ditch/auditSupplySaleApplyAction.do");
		ditch.put("di5", "/ditch/toRatifySupplySaleApply.do");
		ditch.put("di6", "/ditch/auditSupplySaleMoveAction.do");
		ditch.put("di7", "/warehouse/auditMoveApplyAction.do");
		ditch.put("di8", "/warehouse/toRatifyMoveApplyAction.do");
		ditch.put("di9", "/warehouse/auditStockMoveAction.do");
		ditch.put("di10", "/ditch/auditOrganInvoiceAction.do");
		ditch.put("di11", "/ditch/auditOrganWithdrawAction.do");
		ditch.put("di12", "/ditch/toRatifyOrganWithdrawAction.do");
		ditch.put("di13", "/ditch/auditOrganTradesAction.do");
		ditch.put("di14", "/ditch/toRatifyOrganTradesAction.do");
		
		ditch.put("di15", "/warehouse/toAuditTakeTicketAction.do");
		ditch.put("di16", "/warehouse/toCompleteStockAlterMoveReceiveAction.do");
		ditch.put("di17", "/warehouse/completeStockMoveReceiveAction.do");
		ditch.put("di18", "/warehouse/completeOrganWithdrawReceiveAction.do");
		ditch.put("di19", "/ditch/auditPlantWithdrawAction.do");
		ditch.put("di20", "/warehouse/completePlantWithdrawReceiveAction.do");
		
		
		
		equip.put("eq1", "/warehouse/toAddEquipAction.do");
		equip.put("eq2", "/warehouse/auditShipmentBillAction.do");
		
		purchase.put("pu1", "/purchase/auditPurchasePlanAction.do");
		purchase.put("pu2", "/purchase/ratifyPurchasePlanAction.do");
		purchase.put("pu3", "/purchase/auditPurchaseBillAction.do");
		purchase.put("pu4", "/purchase/ratifyPurchaseBillAction.do");
		purchase.put("pu5", "/purchase/auditPurchaseInvoiceAction.do");
		purchase.put("pu6", "/aftersale/auditPurchaseWithdrawAction.do");
		purchase.put("pu7", "/aftersale/auditPurchaseTradesAction.do");
		purchase.put("pu8", "/aftersale/receivePurchaseTradesAction.do");
		
		machin.put("ma1", "/machin/auditAssembleRelationAction.do");
		machin.put("ma2", "/machin/auditAssembleAction.do");
		machin.put("ma3", "/machin/auditConsignMachinAction.do");

		finance.put("fi1", "/finance/auditIncomeLogAction.do");
		finance.put("fi2", "/finance/auditPaymentLogAction.do");
		finance.put("fi3", "/finance/auditOutlayAction.do");
		finance.put("fi4", "/finance/toEndCaseOutlayAction.do");
		finance.put("fi5", "/finance/listReceivableAllAction.do");
	}
	
	public Map setDisplay(int userid) throws Exception{
		Map<String, String> map = new HashMap<String, String>();
		Set tmpKeySet = title.keySet();
		for (Iterator tmpIt = tmpKeySet.iterator(); tmpIt.hasNext();) {
			String tmpKey = tmpIt.next().toString();
			String tmpVal = title.get(tmpKey).toString();
			if ( isAlready(userid, tmpVal) ){
				map.put(tmpKey, "block");
			}else{
				map.put(tmpKey, "none");
			}
		}
		return map;
	}
	
	public Map setSelfDisplay(int userid) throws Exception{
		return setSmallDisplay(userid, self);
	}
	
	public Map getWarehouseDisplay(int userid) throws Exception{
		return setSmallDisplay(userid, warehouse);
	}
	
	public Map getSaleDisplay(int userid) throws Exception{
		return setSmallDisplay(userid, sale);
	}
	
	public Map getDitchDisplay(int userid) throws Exception{
		return setSmallDisplay(userid, ditch);
	}
	
	public Map getEquipDisplay(int userid) throws Exception{
		return setSmallDisplay(userid, equip);
	}
	
	public Map getPurchaseDisplay(int userid) throws Exception{
		return setSmallDisplay(userid, purchase);
	}
	
	public Map getMachinDisplay(int userid) throws Exception{
		return setSmallDisplay(userid, machin);
	}
	
	public Map getFinanceDisplay(int userid) throws Exception{
		return setSmallDisplay(userid, finance);
	}
	
	
	private Map setSmallDisplay(int userid, Map<String, String> srcmap) throws Exception{
		Map<String, String> map = new HashMap<String, String>();
		Set tmpKeySet = srcmap.keySet();
		for (Iterator tmpIt = tmpKeySet.iterator(); tmpIt.hasNext();) {
			String tmpKey = tmpIt.next().toString();
			String tmpVal = srcmap.get(tmpKey).toString();
			
			if ( isPermit(userid, tmpVal) ){
				map.put(tmpKey, "block");
			}else{
				map.put(tmpKey, "none");
			}
		}
		return map;
	}
	
	private Map setNoneDisplay(Map<String, String> srcmap) throws Exception{
		Map<String, String> map = new HashMap<String, String>();
		Set tmpKeySet = srcmap.keySet();
		for (Iterator tmpIt = tmpKeySet.iterator(); tmpIt.hasNext();) {
			String tmpKey = tmpIt.next().toString();
			map.put(tmpKey, "none");
		}
		return map;
	}
	
	public static void main(String[] args) throws Exception{
		//setDisplay(2);
	}
	
	private boolean isAlready(int userid, String lmid) throws Exception{
		String hql="select count(rlm.lmid)"+
			"from UserRole ur, RoleLeftMenu rlm "+
			"where ur.roleid=rlm.roleid "+
			"and ur.ispopedom=1 and ur.userid="+userid+" and  lmid="+lmid+" ";
		int count =  EntityManager.getRecordCount(hql);
		if ( count > 0 ){
			return true;
		}
		return false;
	}
	
	private static boolean isPermit(int userid, String tmpPath) {
		boolean ispermit = false;
		String hql="select r.id from RoleMenu r, Menu m, UserRole u where r.operateid = m.operateid and r.roleid=u.roleid "+
		 " and u.userid="+userid+"  and m.url='"+ tmpPath+"' and u.ispopedom=1";
		
		Object aa = new Object();
		try {
			aa = (Object) EntityManager.find(hql);
			if (aa != null) {
				ispermit = true;
			}
		} catch (HibernateException ex) {
			ex.printStackTrace();
		}

		return ispermit;
	}
	
	private boolean UrlAlready(int userid, String lmid) throws Exception{
		String hql="select count(rlm.lmid)"+
			"from UserRole ur, RoleLeftMenu rlm "+
			"where ur.roleid=rlm.roleid "+
			"and ur.ispopedom=1 and ur.userid="+userid+" and  lmid="+lmid+" ";
		int count =  EntityManager.getRecordCount(hql);
		if ( count > 0 ){
			return true;
		}
		return false;
	}
}
