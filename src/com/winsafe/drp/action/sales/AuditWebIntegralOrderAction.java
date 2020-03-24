package com.winsafe.drp.action.sales;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppCIntegral;
import com.winsafe.drp.dao.AppCIntegralDeal;
import com.winsafe.drp.dao.AppIntegralOrder;
import com.winsafe.drp.dao.AppIntegralOrderDetail;
import com.winsafe.drp.dao.AppOIntegral;
import com.winsafe.drp.dao.AppOIntegralDeal;
import com.winsafe.drp.dao.AppProduct;
import com.winsafe.drp.dao.CIntegral;
import com.winsafe.drp.dao.CIntegralDeal;
import com.winsafe.drp.dao.IntegralOrder;
import com.winsafe.drp.dao.OIntegral;
import com.winsafe.drp.dao.OIntegralDeal;
import com.winsafe.hbm.util.DateUtil;

public class AuditWebIntegralOrderAction extends BaseAction {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
//		Long userid = users.getUserid();

		try {
			String soid = request.getParameter("SOID");
			AppIntegralOrder aso = new AppIntegralOrder();
			IntegralOrder so = aso.getIntegralOrderByID(soid);

			if (so.getIsaudit() == 1) {
				String result = "databases.record.audit";
				request.setAttribute("result", result);
				return new ActionForward("/sys/lockrecordclose.jsp");
			}
			
			if (!so.getEquiporganid().equals(users.getMakeorganid())) {
				String result = "databases.record.nopurview";
				request.setAttribute("result", result);
				return new ActionForward("/sys/lockrecordclose.jsp");
			}
			
			if (so.getIsblankout() == 1) {
				String result = "databases.record.blankoutnooperator";
				request.setAttribute("result", result);
				return new ActionForward("/sys/lockrecordclose.jsp");
			}

			AppIntegralOrderDetail appsod = new AppIntegralOrderDetail();
//			List pils = appsod.getIntegralOrderDetailObjectByioid(soid);
			//AppProductStockpile aps = new AppProductStockpile();
			AppProduct ap = new AppProduct();
			
			
			String[] wdid = request.getParameterValues("wdid");
			String[] wid = request.getParameterValues("warehouseid");
			for ( int i=0; i<wdid.length; i ++ ){
				if ( wid[i].equals("") ){
					request.setAttribute("result", "databases.approve.fail");
					return new ActionForward("/sys/lockrecordclose.jsp");
				}
			}
			so.setEquiporganid(request.getParameter("equiporganid"));
			aso.updIntegralOrder(so);
			for ( int i=0; i<wdid.length; i ++ ){
				appsod.updWarehourseId(Long.valueOf(wdid[i]), Long.valueOf(wid[i]));
			}

		    
//		    if ( so.getPaymentmode().intValue() == 2 ){    	
//		    	RecevablePayableService aprd = new RecevablePayableService(so.getCid(), DateUtil.getMonthFirstDay(), DateUtil.getMonthLastDay());	    	
//		    	double sum = aprd.getPreviousSum(-1)+aprd.getCurrentSum(-1)-aprd.getCurrentAlreadySum(-1);  
//		    	//System.out.println("========================================sum="+sum);
//				if ( Math.abs(sum) < so.getTotalsum() ){
//					String result = "databases.record.noreceivable";
//			    	request.setAttribute("result", result);
//			    	return new ActionForward("/sys/lockrecord.jsp");
//				}
//		 	
//		    }
		    
//		    if ( so.getPaymentmode().intValue() == 3 ){
//		    	AppReceivableDetail aprd = new AppReceivableDetail();
//		    	AppCustomer ac = new AppCustomer();
//		    	GregorianCalendar calendar=new GregorianCalendar(); 
//		    	int today=calendar.get(Calendar.DAY_OF_MONTH); 
//		    	
//		    	double rsum = aprd.getReceivableSumByDate(so.getCid(), DateUtil.getCurrentDateString());    	
//		    	
//		    	double cursum = rsum+so.getTotalsum() - ac.getCustomer(so.getCid()).getCreditlock().doubleValue();
//		    	//System.out.println("++++++++++++++++++++++++++++++++++++++++++++应付余款="+rsum);
//		    	//System.out.println("++++++++++++++++++++++++++++++++++++++++++++应付余款+单金额-信用="+cursum);
//				if ( cursum > 0 ){
//					//System.out.println("++++++++++++++++++++++++++++++++++++++++++++1号到10");
//					String result = "databases.record.largecredit";
//			    	request.setAttribute("result", result);
//			    	return new ActionForward("/sys/lockrecord.jsp");
//				}
//
//		    	if ( today > 10 && today <= 31 ){  
//		    		AppIncomeLogDetail apil = new AppIncomeLogDetail();
//		    		String enddate  = DateUtil.formatDate(DateUtil.calculatedays(DateUtil.StringToDate(DateUtil.getMonthFirstDay()), 9));
//		    		double ildsum = apil.getReceivableSumByDate(so.getCid(), DateUtil.getMonthFirstDay(), enddate);
//		    		System.out.println("++++++++++++++++++++++++++++++++++++++++++++1-10收款="+ildsum);
//		    		if ( rsum - ildsum  > 0 ){
//		    			//System.out.println("++++++++++++++++++++++++++++++++++++++++++++10号到31减去1-10");
//		    			String result = "databases.record.largecredit";
//		    	    	request.setAttribute("result", result);
//		    	    	return new ActionForward("/sys/lockrecord.jsp");
//		    		}
//		    	}
//		    }
		    

//			for (int i = 0; i < pils.size(); i++) {
//				IntegralOrderDetail pid = (IntegralOrderDetail) pils.get(i);
//				int wise = ap.getProductByID(pid.getProductid()).getWise();
//				if (0 == wise) {
//					
////					if (pid.getWarehouseid() == null
////							|| pid.getWarehouseid() == 0) {
////						String result = "databases.record.needwarehouse";
////						request.setAttribute("result", result);
////						return new ActionForward("/sys/lockrecord.jsp");
////					}
//					
////					double stock = aps.getProductStockpileByProductIDWIDBatch(
////							pid.getProductid(), pid.getWarehouseid(), pid
////									.getBatch());
////					if (pid.getQuantity() - stock > 0) {
////						String result = "databases.makeshipment.nostockpile";
////						request.setAttribute("result", result);
////						return new ActionForward("/sys/lockrecord.jsp");
////					}
//				}
//			}
//			// --------------------add take service----
//			TakeBill takebill = new TakeBill();
//			takebill.setId(so.getId());
//			takebill.setOid(so.getCid());
//			takebill.setOname(so.getCname());
//			takebill.setRlinkman(so.getReceiveman());
//			takebill.setTel(so.getReceivetel());
//			takebill.setMakeorganid(so.getMakeorganid());
//			takebill.setMakedeptid(so.getMakedeptid());
//			takebill.setEquiporganid(so.getEquiporganid());
////			takebill.setMakeid(userid);
//			takebill.setMakedate(DateUtil.StringToDatetime(DateUtil.getCurrentDateTime()));
//			
//			takebill.setIsaudit(0);
//			takebill.setIsblankout(0);
//
//			TakeServiceBean tsb = new TakeServiceBean();
//			tsb.setTakebill(takebill);
////			for (int i = 0; i < pils.size(); i++) {
////				IntegralOrderDetail pid = (IntegralOrderDetail) pils.get(i);
////				
////				int wise = ap.getProductByID(pid.getProductid()).getWise();
////				TakeTicket tt = null;
////				if (tsb.getTtmap().get(pid.getWarehouseid().toString()) == null) {
////					tt = new TakeTicket();
////					tt.setId(MakeCode.getExcIDByRandomTableName("take_ticket",
////							2, "TT"));
////					tt.setWarehouseid(pid.getWarehouseid());
////					tt.setBillno(so.getId());
////					tt.setRemark(so.getRemark());
////					tt.setIsaudit(0);
////					tt.setMakeorganid(users.getMakeorganid());
////					tt.setMakedeptid(users.getMakedeptid());
////					tt.setMakeid(userid);
////					tt.setMakedate(DateUtil.StringToDatetime(DateUtil.getCurrentDateTime()));
////					tt.setPrinttimes(0);
////					tt.setIsblankout(0);
////					tsb.getTtmap().put(pid.getWarehouseid().toString(), tt);
////					tsb.getWarehouseids().add(pid.getWarehouseid().toString());
////				} else {
////					tt = tsb.getTtmap().get(pid.getWarehouseid().toString());
////				}
////				TakeTicketDetail ttd = new TakeTicketDetail();
////				ttd.setId(Long.valueOf(MakeCode.getExcIDByRandomTableName(
////						"take_ticket_detail", 0, "")));
////				ttd.setProductid(pid.getProductid());
////				ttd.setProductname(pid.getProductname());
////				ttd.setSpecmode(pid.getSpecmode());
////				Integer unitid = Integer.parseInt(pid.getUnitid().toString());
////				ttd.setUnitid(unitid);
////				ttd.setBatch("");
////				ttd.setUnitprice(pid.getIntegralprice());
////				ttd.setQuantity(Double.valueOf(pid.getQuantity().toString()));
////				ttd.setTtid(tt.getId());
////				if (wise != 0) {
////					tt.getSpedetails().add(ttd);
////				} else {
////					tt.getTtdetails().add(ttd);
////				}
////			}
//			// ---------------------------------------
//
//			// System.out.println("==================="+tsb.getWarehouseids());
//			// for ( int i=0; i<tsb.getWarehouseids().size(); i++ ){
//			// TakeTicket tt = tsb.getTtmap().get(tsb.getWarehouseids().get(i));
//			// System.out.println(
//			// "================================>spedetails.size="
//			// +tt.getSpedetails().size());
//			// }
//
//			
//			AppTakeService appts = new AppTakeService();
////			appts.addTake(tsb);
//			
//			for (String arehouseid : tsb.getWarehouseids()) {
//				TakeTicket tt = tsb.getTtmap().get(arehouseid);
////				for (TakeTicketDetail ttd : tt.getSpedetails()) {
////					appsod.updTakeQuantity(tt.getBillno(), ttd.getProductid(),
////							ttd.getBatch(), ttd.getQuantity());
////				}
//			}
			
//			aso.updIsAudit(soid, userid, 1);
			
			//---------------------积分预处理表写入积分表-------------------------
			
			AppCIntegralDeal acid = new AppCIntegralDeal();
			AppCIntegral aci = new AppCIntegral();
			CIntegral ci = new CIntegral();
			CIntegralDeal o=(CIntegralDeal)acid.getCIntegralDealByBillNo(soid, 9);
			ci.setOrganid(o.getOrganid());
			ci.setCid(o.getCid());
			ci.setIsort(o.getIsort());
			ci.setCintegral(o.getDealintegral());
			ci.setIyear(DateUtil.getCurrentYear());
			aci.addCIntegralIsNoExist(ci);
			aci.updCIntegralByIntegral(ci);
			
			acid.updCIntegralDealByID(o.getId(), ci.getCintegral());
			
			
			AppOIntegralDeal aoid = new AppOIntegralDeal();
			AppOIntegral aoi = new AppOIntegral();
			OIntegral oi = new OIntegral();
			OIntegralDeal obj = (OIntegralDeal)aoid.getOIntegralDealByBillNo(soid, 9);
			oi.setPowerorganid("1");
			oi.setEquiporganid(obj.getOid());
			oi.setIsort(obj.getIsort());
			oi.setOintegral(obj.getDealintegral());
			oi.setIyear(DateUtil.getCurrentYear());
			aoi.addOIntegralIsNoExist(oi);
			aoi.updOIntegralByIntegral(oi);			
			
			aoid.updOIntegralDealByID(obj.getId(), oi.getOintegral());
			//-------------------------------------------------------------------

			request.setAttribute("result", "databases.audit.success");
//			DBUserLog.addUserLog(userid, "复核积分换购单");

			return mapping.findForward("audit");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}

}
