package com.winsafe.drp.action.sys;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppReceivableObject;
import com.winsafe.drp.dao.TakeTicket;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.server.SelfAwakeService;
import com.winsafe.drp.util.MapUtil;

public class ListSelfAwakeAction extends Action {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		UsersBean usersBean = UserManager.getUser(request);
		Integer userid = usersBean.getUserid();
		String tableName;
		String Condition;
		String organid = usersBean.getMakeorganid();
		try {
			SelfAwakeService sas = new SelfAwakeService();
			Map<String, String> title =sas.setDisplay(userid);			
			request.setAttribute("title", title);
			//公告
			if ( title.get("self").equals("block") ){ 
				request.setAttribute("self", sas.setSelfDisplay(userid));
				
				
//				String afficheCondition = " where a.id=ab.afficheid and ab.userid="
//						+ userid + " and ab.isbrowse=0 ";
//				String afficheTable = "Affiche as a,AfficheBrowse as ab ";
//				int affiche = getCount(afficheCondition, afficheTable);
				
//				request.setAttribute("affiche", affiche);
			}
	//仓库	
			/*
			if ( title.get("warehouse").equals("block") ){
				
				//自己的显示仓库
				request.setAttribute("warehouse", sas.getWarehouseDisplay(userid));
				
                //产成品
//				Condition = " where pi.makeorganid='" + organid+"' and pi.warehouseid in " +
//				"(select wv.wid from  WarehouseVisit as wv where wv.userid="+userid+")"
//						+ " and pi.isaudit=0 ";
//				tableName = " ProductIncome as pi ";
//				int productincomecount = getCount(Condition, tableName);
				
				//检货
				
				List<Map> list =  new ArrayList<Map>();
				StringBuffer sql = new StringBuffer();
				sql.append(" select * from take_ticket tt where 1=1 ");
				sql.append(" \n and tt.bsort = 1  "); //1代表渠道订购单
				sql.append(" \n " + " and  isaudit='0' " );
				sql.append(" \n and tt.warehouseid in ( select ruw.warehouse_id from rule_user_wh ruw where ruw.user_id=" + userid + ") ");
				sql.append(" \n and tt.inwarehouseid in ( select wid from warehouse_visit wv where  wv.userid=" + userid + ") ");
				
				sql.append(" \n union ");
				
				sql.append(" \n select * from take_ticket tt  where 1=1 ");
				sql.append(" \n and tt.bsort = 2  "); //2代表转仓
				sql.append(" \n " + " and  isaudit='0' " );
				sql.append(" \n and tt.warehouseid in ( select ruw.warehouse_id from rule_user_wh ruw where ruw.user_id=" + userid + ") ");
				
				sql.append(" \n union ");
				
				sql.append(" \n select * from take_ticket tt  where 1=1 ");
				sql.append(" \n and tt.bsort = 7 and tt.billno like 'OW%' "); //7代表渠道退货单
				sql.append(" \n " + " and  isaudit='0' " );
				sql.append(" \n and tt.inwarehouseid in ( select ruw.warehouse_id from rule_user_wh ruw where ruw.user_id=" + userid + ") ");
				sql.append(" \n and tt.warehouseid in ( select wid from warehouse_visit wv where  wv.userid=" + userid + ") ");
				
				sql.append(" \n union ");
				
				sql.append(" \n select * from take_ticket tt  where 1=1 ");
				sql.append(" \n and tt.bsort = 7 and tt.billno like 'PW%'  "); //渠道工厂退回
				sql.append(" \n " + " and  isaudit='0' " );
				sql.append(" \n and tt.warehouseid in ( select ruw.warehouse_id from rule_user_wh ruw where ruw.user_id=" + userid + ") ");
				//sql.append(" \n and tt.warehouseid in ( select wid from warehouse_visit wv where  wv.userid=" + userid + ") ");
				sql.append(" \n and  tt.inwarehouseid in (select id from Warehouse  w where w.makeorganid in (select id from Organ o where  o.isrepeal=0 and  o.organType = 1 )) ");
				list = EntityManager.jdbcquery(sql.toString());
				int takecount = list.size();
				request.setAttribute("takecount", takecount);
			    //订购
				String SAMCondition = " where sam.receiveorganid='"
					+ organid+"' and sam.inwarehouseid in (select wv.wid from  WarehouseVisit as wv where wv.userid="+userid+")"+
					// " or sam.outwarehouseid in (select wv.wid from  WarehouseVisit as wv where wv.userid="+userid+"))"
						 " and sam.isshipment=1 and sam.isblankout=0 and sam.iscomplete=0 ";
				String SAMtableName = " StockAlterMove as sam ";
				int stockaltermovecount = getCount(SAMCondition, SAMtableName);
				request.setAttribute("stockaltermovecount", stockaltermovecount);
				
			    //转仓
				Condition = " where sm.inorganid='" + organid+"' and sm.inwarehouseid in (select wv.wid from  WarehouseVisit as wv where wv.userid="+userid+")"+
					 //" or sm.outwarehouseid in (select wv.wid from  WarehouseVisit as wv where wv.userid="+userid+"))"
						" and sm.isshipment=1 and sm.iscomplete=0 "; // 
				tableName = " StockMove as sm ";
				int stockmovecount = getCount(Condition, tableName);
				request.setAttribute("stockmovecount", stockmovecount);

				//机构退货
				Condition = " where p.porganid='"
						+ organid
						+ "' and p.isaffirm=1 and p.isblankout=0 and p.iscomplete=0 " +
								"and p.inwarehouseid in (select wv.wid from  WarehouseVisit as wv where wv.userid="+userid+")";
						// " or p.warehouseid in (select wv.wid from  WarehouseVisit as wv where wv.userid="+userid+"))";
				tableName = " OrganWithdraw as p ";
				int owcount = getCount(Condition, tableName);
				request.setAttribute("owcount", owcount);
				
//				request.setAttribute("productincomecount", productincomecount);
			}
			
			if ( title.get("sale").equals("block") ){
				request.setAttribute("sale", sas.getSaleDisplay(userid));
				
				Condition = " where p.makeorganid='" + organid
						+ "' and p.isaudit=0 and p.isblankout=0 ";
				tableName = " SaleOrder as p ";
				int socount = getCount(Condition, tableName);
				request.setAttribute("socount", socount);
				
				Condition = " where p.makeorganid='" + organid
						+ "' and p.isaudit=0 ";
				tableName = " SaleIndent as p ";
				int sicount = getCount(Condition, tableName);
				request.setAttribute("sicount", sicount);
				
				Condition = " where p.makeorganid='" + organid
						+ "' and p.isaudit=0 ";
				tableName = " SaleInvoice as p ";
				int sicount2 = getCount(Condition, tableName);
				request.setAttribute("sicount2", sicount2);
				
				Condition = " where p.makeorganid='" + organid
						+ "' and p.isaudit=0 ";
				tableName = " Withdraw as p ";
				int swcount = getCount(Condition, tableName);
				request.setAttribute("swcount", swcount);
				
				Condition = " where p.makeorganid='" + organid
						+ "' and p.isaudit=0 ";
				tableName = " SaleTrades as p ";
				int stcount = getCount(Condition, tableName);
				request.setAttribute("stcount", stcount);
				
				Condition = " where p.makeorganid='" + organid
						+ "' and p.isaudit=1 and p.isendcase=0";
				tableName = " SaleTrades as p ";
				int stcount2 = getCount(Condition, tableName);
				request.setAttribute("stcount2", stcount2);
				
				Condition = " where p.makeorganid='" + organid
						+ "' and p.isaudit=0 and p.isblankout=0";
				tableName = " IntegralOrder as p ";
				int iocount = getCount(Condition, tableName);
				request.setAttribute("iocount", iocount);
			}
			*/
			
			if ( title.get("ditch").equals("block") ){
				request.setAttribute("ditch", sas.getDitchDisplay(userid));
				
				//待复核发货单
				Condition = " where isaudit='0'" +
					" and  sm.outwarehouseid in (select wv.warehouseId from  RuleUserWh as wv where  wv.userId="+userid+")" ;
//					" and  sm.inwarehouseid in (select wv.wid from  WarehouseVisit as wv where wv.userid="+userid+")" ;
				tableName = " StockAlterMove as sm ";
				int samcount = getCount(Condition, tableName);
				request.setAttribute("samcount", samcount);
				
				//待出库发货单
				Condition = " where tt.bsort='1' and tt.isaudit='0'" + 
					" and tt.warehouseid in ( select ruw.warehouseId from RuleUserWh ruw where ruw.userId="+userid+")" ; 
//					" and tt.inwarehouseid in ( select wid from WarehouseVisit wv where  wv.userid="+userid+")";  
				tableName = " TakeTicket as tt";
				int samttcount = getCount(Condition, tableName);
				request.setAttribute("samttcount", samttcount);
				
				//待签收发货单
				Condition = " where sm.iscomplete='0' and  sm.isshipment=1 and sm.isblankout=0" + 
					" and sm.inwarehouseid in (select wv.warehouseId from  RuleUserWh as wv where wv.userId="+userid+")";
				tableName = " StockAlterMove as sm";
				int samreceivecount = getCount(Condition, tableName);
				request.setAttribute("samreceivecount", samreceivecount);
				
				
				
				
				//待复核转仓单
				Condition = " where sm.isaudit='0'" + 
					" and sm.outwarehouseid in (select wv.warehouseId from RuleUserWh as wv where wv.userId="+userid+")";
				tableName = " StockMove as sm";
				int smcount = getCount(Condition, tableName);
				request.setAttribute("smcount", smcount);
				
				//待批准的机构间转仓单
				Condition = " where ma.isratify='0'" + 
					" and "+usersBean.getMakeorganid()+" in (select parentid from Organ where id = ma.outorganid)" +
					" and ma.outorganid in (select visitorgan from UserVisit as uv where  uv.userid="+userid+")";
				tableName = " MoveApply as ma";
				int macount = getCount(Condition, tableName);
				request.setAttribute("macount", macount);
				
				//待出库转仓单
				Condition = " where tt.bsort='2' and tt.isaudit='0'" + 
					" and tt.warehouseid in ( select ruw.warehouseId from RuleUserWh ruw where ruw.userId="+userid+")";
//					" and tt.inwarehouseid in ( select wid from WarehouseVisit wv where  wv.userid="+userid+")";  
				tableName = " TakeTicket as tt";
				int smttcount = getCount(Condition, tableName);
				request.setAttribute("smttcount", smttcount);
				
				//待签收转仓单
				Condition = " where iscomplete='0' and  sm.isshipment=1 and sm.isblankout=0" + 
					" and sm.inwarehouseid in (select wv.warehouseId from  RuleUserWh as wv where wv.userId="+userid+")";
				tableName = " StockMove as sm";
				int smreceivecount = getCount(Condition, tableName);
				request.setAttribute("smreceivecount", smreceivecount);
				
				
				//待复核渠道退货
				Condition = " where ow.isaudit='0' and ow.id like 'OW%'" + 
					" and ow.inwarehouseid in (select wv.warehouseId from  RuleUserWh as wv where  wv.userId="+userid+")";
//					" and ow.warehouseid in (select wv.wid from  WarehouseVisit as wv where wv.userid="+userid+")";
				tableName = " OrganWithdraw as ow";
				int owcount = getCount(Condition, tableName);
				request.setAttribute("owcount", owcount);
				
				//待出库渠道退货
				Condition = " where tt.bsort='7' and tt.isaudit='0' and tt.billno like 'OW%' " + 
					" and tt.inwarehouseid in ( select ruw.warehouseId from RuleUserWh ruw where ruw.userId="+userid+")";
//					" and tt.warehouseid in ( select wid from WarehouseVisit wv where  wv.userid="+userid+")";  
				tableName = " TakeTicket as tt";
				int owttcount = getCount(Condition, tableName);
				request.setAttribute("owttcount", owttcount);
				
				//待签收渠道退货
				Condition = " where ow.iscomplete='0' and ow.id like 'OW%' and ow.takestatus=1 and ow.isblankout=0" + 
					" and ow.inwarehouseid  in (select wv.warehouseId from  RuleUserWh as wv where wv.userId="+userid+")";
				tableName = " OrganWithdraw as ow";
				int owreceivecount = getCount(Condition, tableName);
				request.setAttribute("owreceivecount", owreceivecount);
				
				//待复核工厂退货
				Condition = " where ow.isaudit='0' and ow.id like 'PW%'" + 
					" and ow.warehouseid in (select wv.warehouseId from  RuleUserWh as wv where  wv.userId="+userid+")";
//					" and ow.warehouseid in (select wv.wid from  WarehouseVisit as wv where wv.userid="+userid+")";
				tableName = " OrganWithdraw as ow";
				int powcount = getCount(Condition, tableName);
				request.setAttribute("powcount", powcount);
				
				//待出库工厂退货
				Condition = " where tt.bsort='7' and tt.isaudit='0' and tt.billno like 'PW%' " + 
					" and tt.warehouseid in ( select ruw.warehouseId from RuleUserWh ruw where ruw.userId="+userid+")";
//					" and tt.inwarehouseid in ( select wid from WarehouseVisit wv where  wv.userid="+userid+")";  
				tableName = " TakeTicket as tt";
				int powttcount = getCount(Condition, tableName);
				request.setAttribute("powttcount", powttcount);
				
				//待签收工厂退货
				Condition = " where ow.iscomplete='0' and ow.id like 'PW%' and ow.takestatus=1 and ow.isblankout=0" + 
					" and ow.inwarehouseid  in (select wv.warehouseId from  RuleUserWh as wv where wv.userId="+userid+")";
				tableName = " OrganWithdraw as ow";
				int powreceivecount = getCount(Condition, tableName);
				request.setAttribute("powreceivecount", powreceivecount);
				
				
				
				
//				Condition = " where p.makeorganid='" + organid
//						+ "' and p.isaudit=0 and p.isblankout=0";
//				tableName = " StockAlterMove as p ";
////				int samcount = getCount(Condition, tableName);
//				request.setAttribute("samcount", samcount);
//				
//				Condition = " where p.makeorganid='" + organid
//						+ "' and p.isaudit=0 and p.isblankout=0";
//				tableName = " MoveApply as p ";
//				int macount = getCount(Condition, tableName);
//				request.setAttribute("macount", macount);
//				
//				Condition = " where p.outorganid='" + organid
//						+ "' and p.isaudit=1 and isratify= 0 and p.isblankout=0";
//				tableName = " MoveApply as p ";
//				int rmaacount = getCount(Condition, tableName);
//				request.setAttribute("rmaacount", rmaacount);
//				
//				Condition = " where p.makeorganid='" + organid
//						+ "' and p.isaudit=0 and p.isblankout=0";
//				tableName = " StockMove as p ";
//				int smcount = getCount(Condition, tableName);
//				request.setAttribute("smcount", smcount);
//				
//				
//				Condition = " where p.makeorganid='" + organid
//						+ "' and p.isaudit=0 and p.isblankout=0";
//				tableName = " OrganWithdraw as p ";
//				int owcount2 = getCount(Condition, tableName);
//				request.setAttribute("owcount2", owcount2);
//				
//				Condition = " where p.porganid='" + organid
//						+ "' and p.isaudit=1 and isratify= 0 and p.isblankout=0";
//				tableName = " OrganWithdraw as p ";
//				int rowcount = getCount(Condition, tableName);
//				request.setAttribute("rowcount", rowcount);
			}
			
			/*
			if ( title.get("equip").equals("block") ){
				request.setAttribute("equip", sas.getEquipDisplay(userid));
				
				Condition = " where p.makeorganid='" + organid
						+ "' and p.isaudit=1 and p.istrans = 0 and p.isblankout=0";
				tableName = " ShipmentBill as p ";
				int sbcount = getCount(Condition, tableName);
				request.setAttribute("sbcount", sbcount);
				
				Condition = " where p.makeorganid='" + organid
						+ "' and p.isaudit=0 and p.isblankout=0";
				tableName = " ShipmentBill as p ";
				int asbcount = getCount(Condition, tableName);
				request.setAttribute("asbcount", asbcount);
			}
			
			if ( title.get("purchase").equals("block") ){
				request.setAttribute("purchase", sas.getPurchaseDisplay(userid));
				
				Condition = " where pp.makeorganid='" + organid
						+ "' and isaudit =0";
				tableName = " PurchasePlan pp ";
				int plancount = getCount(Condition, tableName);
				request.setAttribute("plancount", plancount);

				
				String planCondition = " where pp.makeorganid='" + organid
						+ "' and  pp.isratify=0 and isaudit =1";
				String planTable = " PurchasePlan pp ";
				int purchaseplanratify = getCount(planCondition, planTable);

				
				Condition = " where pb.makeorganid='" + organid
						+ "' and pb.isaudit =1 and  pb.isratify=0";
				String billTable = "PurchaseBill pb ";
				int purchasebillratify = getCount(Condition, billTable);

				
				Condition = " where pb.makeorganid='" + organid
						+ "' and pb.isaudit =0";
				tableName = " PurchaseBill pb ";
				int pbcount = getCount(Condition, tableName);
				request.setAttribute("pbcount", pbcount);

				request.setAttribute("purchaseplanratify", purchaseplanratify);
				request.setAttribute("purchasebillratify", purchasebillratify);
				
				Condition = " where pb.makeorganid='" + organid
						+ "' and pb.isaudit =0";
				tableName = " PurchaseInvoice pb ";
				int pinvoicecount = getCount(Condition, tableName);
				request.setAttribute("pinvoicecount", pinvoicecount);
				
				Condition = " where pb.makeorganid='" + organid
						+ "' and pb.isaudit =0 and pb.isblankout=0";
				tableName = " PurchaseWithdraw pb ";
				int pwcount = getCount(Condition, tableName);
				request.setAttribute("pwcount", pwcount);
				
				Condition = " where pb.makeorganid='" + organid
						+ "' and pb.isaudit =0 and pb.isblankout=0";
				tableName = " PurchaseTrades pb ";
				int ptcount = getCount(Condition, tableName);
				request.setAttribute("ptcount", ptcount);
				
				
				Condition = " where pb.makeorganid='" + organid
				+ "' and pb.isaudit =1 and pb.isreceive=0 and pb.isblankout=0";
			tableName = " PurchaseTrades pb ";
			int rptcount = getCount(Condition, tableName);
			request.setAttribute("rptcount", rptcount);
			}
			
			if ( title.get("machin").equals("block") ){
				request.setAttribute("machin", sas.getMachinDisplay(userid));
				
				Condition = " where pb.makeorganid='" + organid
						+ "' and pb.isaudit =0";
				tableName = " AssembleRelation pb ";
				int arcount = getCount(Condition, tableName);
				request.setAttribute("arcount", arcount);
			}
			
			
			if ( title.get("finance").equals("block") ){
				request.setAttribute("finance", sas.getFinanceDisplay(userid));
				
				Condition = " where pb.makeorganid='" + organid
						+ "' and pb.isaudit =0";
				tableName = " IncomeLog pb ";
				int ilcount = getCount(Condition, tableName);
				request.setAttribute("ilcount", ilcount);
				
				Condition = " where pb.makeorganid='" + organid
						+ "' and pb.isaudit =0";
				tableName = " PaymentLog pb ";
				int plcount = getCount(Condition, tableName);
				request.setAttribute("plcount", plcount);
				
				Condition = " where pb.makeorganid='" + organid
						+ "' and pb.isaudit =0";
				tableName = " Outlay pb ";
				int ocount = getCount(Condition, tableName);
				request.setAttribute("ocount", ocount);
				
				Condition = " where pb.makeorganid='" + organid
						+ "' and pb.isaudit =1 and pb.isendcase=0";
				tableName = " Outlay pb ";
				int oecount = getCount(Condition, tableName);
				request.setAttribute("oecount", oecount);
				
				
				AppReceivableObject appro = new AppReceivableObject();			
				request.setAttribute("clcount", appro.getCountOutday(organid));
			}
			*/
			return mapping.findForward("mainall");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private int getCount(String wherSql, String tableName) {
		String sql = "select count(*) from " + tableName + wherSql;
		return EntityManager.getRecordCount(sql);
	}
}
