package com.winsafe.drp.action.sales;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppProduct;
import com.winsafe.drp.dao.AppTakeService;
import com.winsafe.drp.dao.AppWebIndent;
import com.winsafe.drp.dao.AppWebIndentDetail;
import com.winsafe.drp.dao.TakeBill;
import com.winsafe.drp.dao.TakeServiceBean;
import com.winsafe.drp.dao.TakeTicket;
import com.winsafe.drp.dao.TakeTicketDetail;
import com.winsafe.drp.dao.WebIndent;
import com.winsafe.drp.dao.WebIndentDetail;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.MakeCode;

public class AuditWebIndentAction extends BaseAction {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
//		Long userid = users.getUserid();

		try {
			String soid = request.getParameter("SOID");
			AppWebIndent aso = new AppWebIndent();
			WebIndent so = aso.getWebIndentByID(soid);
			AppWebIndentDetail appsod = new AppWebIndentDetail();
			
//			String[] wdid = request.getParameterValues("wdid");
//			String[] wid = request.getParameterValues("warehouseid");
//			for ( int i=0; i<wdid.length; i ++ ){
//				if ( wid[i].equals("") ){
//					request.setAttribute("result", "databases.approve.fail");
//					return new ActionForward("/sys/lockrecordclose.jsp");
//				}
//			}
//			so.setEquiporganid(request.getParameter("equiporganid"));
//			aso.updWebIndent(so);
//			for ( int i=0; i<wdid.length; i ++ ){
//				appsod.updWarehourseId(Long.valueOf(wdid[i]), Long.valueOf(wid[i]));
//			}
			

			if (so.getIsaudit() == 1) {
				request.setAttribute("result", "databases.record.audit");
				return new ActionForward("/sys/lockrecordclose.jsp");
			}
			
			if (!so.getEquiporganid().equals(users.getMakeorganid())) {
				request.setAttribute("result", "databases.record.nopurview");
				return new ActionForward("/sys/lockrecordclose.jsp");
			}
			
			if (so.getIsblankout() == 1) {
				request.setAttribute("result", "databases.record.blankoutnooperator");
				return new ActionForward("/sys/lockrecordclose.jsp");
			}

			List pils = appsod.getWebIndentDetailObjectBySOID(soid);
			//AppProductStockpile aps = new AppProductStockpile();
			AppProduct ap = new AppProduct();

		    
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
		    

			for (int i = 0; i < pils.size(); i++) {
				WebIndentDetail pid = (WebIndentDetail) pils.get(i);
				int wise = ap.getProductByID(pid.getProductid()).getWise();
				if (0 == wise) {
										
					if (pid.getWarehouseid() == null
							|| pid.getWarehouseid() == 0) {
						request.setAttribute("result", "databases.record.needwarehouse");
						return new ActionForward("/sys/lockrecord.jsp");
					}
					
//					double stock = aps.getProductStockpileByProductIDWIDBatch(
//							pid.getProductid(), pid.getWarehouseid(), pid
//									.getBatch());
//					if (pid.getQuantity() - stock > 0) {
//						String result = "databases.makeshipment.nostockpile";
//						request.setAttribute("result", result);
//						return new ActionForward("/sys/lockrecord.jsp");
//					}
				}
			}
			// --------------------add take service----
			TakeBill takebill = new TakeBill();
			takebill.setId(so.getId());
			takebill.setOid(so.getCid());
			takebill.setOname(so.getCname());
			takebill.setRlinkman(so.getReceiveman());
			takebill.setTel(so.getReceivemobile());
			takebill.setMakeorganid(so.getEquiporganid());
			takebill.setMakedeptid(users.getMakedeptid());
			takebill.setEquiporganid(so.getEquiporganid());
//			takebill.setMakeid(userid);
			takebill.setMakedate(DateUtil.StringToDatetime(DateUtil.getCurrentDateTime()));
			
			takebill.setIsaudit(0);
			takebill.setIsblankout(0);

			TakeServiceBean tsb = new TakeServiceBean();
			tsb.setTakebill(takebill);
			for (int i = 0; i < pils.size(); i++) {
				WebIndentDetail pid = (WebIndentDetail) pils.get(i);
				
				int wise = ap.getProductByID(pid.getProductid()).getWise();
				TakeTicket tt = null;
				if (tsb.getTtmap().get(pid.getWarehouseid().toString()) == null) {
					tt = new TakeTicket();
					tt.setId(MakeCode.getExcIDByRandomTableName("take_ticket",
							2, "TT"));
//					tt.setWarehouseid(pid.getWarehouseid());
					tt.setBillno(so.getId());
					tt.setRemark(so.getRemark());
					tt.setIsaudit(0);
					tt.setMakeorganid(users.getMakeorganid());
					tt.setMakedeptid(users.getMakedeptid());
//					tt.setMakeid(userid);
					tt.setMakedate(DateUtil.StringToDatetime(DateUtil.getCurrentDateTime()));
					tt.setPrinttimes(0);
					tt.setIsblankout(0);
					tsb.getTtmap().put(pid.getWarehouseid().toString(), tt);
					tsb.getWarehouseids().add(pid.getWarehouseid().toString());
				} else {
					tt = tsb.getTtmap().get(pid.getWarehouseid().toString());
				}
				TakeTicketDetail ttd = new TakeTicketDetail();
//				ttd.setId(Long.valueOf(MakeCode.getExcIDByRandomTableName(
//						"take_ticket_detail", 0, "")));
				ttd.setProductid(pid.getProductid());
				ttd.setProductname(pid.getProductname());
				ttd.setSpecmode(pid.getSpecmode());
				Integer unitid = Integer.parseInt(pid.getUnitid().toString());
				ttd.setUnitid(unitid);
				ttd.setBatch(pid.getBatch());
				ttd.setUnitprice(pid.getTaxunitprice());
				ttd.setQuantity(pid.getQuantity());
				ttd.setTtid(tt.getId());
				if (wise != 0) {
//					tt.getSpedetails().add(ttd);
				} else {
					tt.getTtdetails().add(ttd);
				}
			}
			// ---------------------------------------

			// System.out.println("==================="+tsb.getWarehouseids());
			// for ( int i=0; i<tsb.getWarehouseids().size(); i++ ){
			// TakeTicket tt = tsb.getTtmap().get(tsb.getWarehouseids().get(i));
			// System.out.println(
			// +tt.getTtdetails().size());
			// System.out.println(
			// +tt.getSpedetails().size());
			// }

			
			AppTakeService appts = new AppTakeService();
//			appts.addTake(tsb);
			
//			for (String arehouseid : tsb.getWarehouseids()) {
//				TakeTicket tt = tsb.getTtmap().get(arehouseid);
//				for (TakeTicketDetail ttd : tt.getSpedetails()) {
//					appsod.updTakeQuantity(tt.getBillno(), ttd.getProductid(),
//							ttd.getBatch(), ttd.getQuantity());
//				}
//			}
			
			
			IntegralService ids = new IntegralService();
//			ids.WebIndentDealIntegral(so, pils);
//
//			aso.updIsAudit(soid, userid, 1);

			request.setAttribute("result", "databases.audit.success");
//			DBUserLog.addUserLog(userid, "复核网站订单");

			return mapping.findForward("audit");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}

}
