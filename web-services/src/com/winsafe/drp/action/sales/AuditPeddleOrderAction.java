package com.winsafe.drp.action.sales;
//package com.winsafe.drp.action.sales;
//
//import java.util.List;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.apache.struts.action.Action;
//import org.apache.struts.action.ActionForm;
//import org.apache.struts.action.ActionForward;
//import org.apache.struts.action.ActionMapping;
//
//import com.winsafe.drp.dao.AppIncomeLog;
//import com.winsafe.drp.dao.AppIncomeLogDetail;
//import com.winsafe.drp.dao.AppPeddleOrder;
//import com.winsafe.drp.dao.AppPeddleOrderDetail;
//import com.winsafe.drp.dao.AppPeddleOrderIdcode;
//import com.winsafe.drp.dao.AppProduct;
//import com.winsafe.drp.dao.AppProductStockpile;
//import com.winsafe.drp.dao.AppReceivable;
//import com.winsafe.drp.dao.AppReceivableDetail;
//import com.winsafe.drp.dao.AppReceivableObject;
//import com.winsafe.drp.dao.IncomeLog;
//import com.winsafe.drp.dao.IncomeLogDetail;
//import com.winsafe.drp.dao.PeddleOrder;
//import com.winsafe.drp.dao.PeddleOrderDetail;
//import com.winsafe.drp.dao.Product;
//import com.winsafe.drp.dao.Receivable;
//import com.winsafe.drp.dao.ReceivableDetail;
//import com.winsafe.drp.dao.ReceivableObject;
//import com.winsafe.drp.dao.UserManager;
//import com.winsafe.drp.dao.UsersBean;
//import com.winsafe.drp.util.DateUtil;
//import com.winsafe.drp.util.DbUtil;
//import com.winsafe.drp.util.MakeCode;
//
//public class AuditPeddleOrderAction extends BaseAction {
//	public ActionForward execute(ActionMapping mapping, ActionForm form,
//			HttpServletRequest request, HttpServletResponse response)
//			throws Exception {
//		
//		Long userid = users.getUserid();
//
//		try {
//			String soid = request.getParameter("SOID");
//			AppPeddleOrder aso = new AppPeddleOrder();
//			PeddleOrder so = aso.getPeddleOrderByID(soid);
//
//			if (so.getIsaudit() == 1) {
//				String result = "databases.record.audit";
//				request.setAttribute("result", result);
//				return new ActionForward("/sys/lockrecordclose.jsp");
//			}
//			if (so.getIsblankout() == 1) {
//				String result = "databases.record.blankoutnooperator";
//				request.setAttribute("result", result);
//				return new ActionForward("/sys/lockrecordclose.jsp");
//			}
//
//			AppPeddleOrderDetail appsod = new AppPeddleOrderDetail();
//			AppPeddleOrderIdcode appwi = new AppPeddleOrderIdcode();
//			List pils = appsod.getPeddleOrderDetailObjectByPOID(soid);
//			AppProductStockpile aps = new AppProductStockpile();
//			AppProduct ap = new AppProduct();
//
//			for (int i = 0; i < pils.size(); i++) {
//				PeddleOrderDetail pid = (PeddleOrderDetail) pils.get(i);
//				
////				判断机身号是否存在
//				Product p = ap.getProductByID(pid.getProductid());
//				if (p.getIsidcode() == 1) {
//					List idcodelist = appwi.getPeddleOrderIdcodeByPidBatch(pid.getProductid(), soid, pid.getBatch());
//					if ( pid.getQuantity() > idcodelist.size() ){
//						String result = "databases.record.needidcode";
//						request.setAttribute("result", result);
//						return new ActionForward("/sys/lockrecord.jsp");
//					}
//				}
//				
//				int wise = ap.getProductByID(pid.getProductid()).getWise();
//				if (0 == wise) {
//					
//					if (pid.getWarehouseid() == null
//							|| pid.getWarehouseid() == 0) {
//						String result = "databases.record.needwarehouse";
//						request.setAttribute("result", result);
//						return new ActionForward("/sys/lockrecord.jsp");
//					}
//					
//					double stock = aps.getProductStockpileByProductIDWIDBatch(
//							pid.getProductid(), pid.getWarehouseid(), pid
//									.getBatch());
//					if (pid.getQuantity() - stock > 0) {
//						String result = "databases.makeshipment.nostockpile";
//						request.setAttribute("result", result);
//						return new ActionForward("/sys/lockrecord.jsp");
//					}
//				}
//				
//
//				aps.returninProductStockpile(pid.getProductid(), pid.getBatch(), pid.getQuantity(), pid.getWarehouseid(),soid,"零售单-出货");
//			}
//
//
//			AppReceivableObject appro = new AppReceivableObject();
//			ReceivableObject ro = new ReceivableObject();
//			ro.setId(so.getCid());
//			ro.setObjectsort(1);
//			ro.setPayer(so.getCname());
//			ro.setMakeorganid(users.getMakeorganid());
//			ro.setMakedeptid(users.getMakedeptid());
//			ro.setMakeid(userid);
//			ro.setMakedate(DateUtil.StringToDatetime(DateUtil.getCurrentDateTime()));
//			appro.addReceivableObjectIsNoExist(ro);
//			
//			AppReceivable appr = new AppReceivable();
//			Receivable rv = new Receivable();
//			String rid = MakeCode.getExcIDByRandomTableName("receivable",2,"");
//			rv.setId(rid);
//			rv.setRoid(so.getCid());
//			//rv.setReceivablesum(so.getTotalsum());
//			rv.setBillno(so.getId());
//			rv.setReceivabledescribe("零售单生成应收款结算单");
//			rv.setMakeorganid(users.getMakeorganid());
//			rv.setMakedeptid(users.getMakedeptid());
//			rv.setMakeid(so.getMakeid());
//			rv.setMakedate(DateUtil.StringToDatetime(DateUtil.getCurrentDateTime()));	
//
//			AppReceivableDetail apprvd = new AppReceivableDetail();
//			//List pils = apsbd.getShipmentBillDetailBySbID(sb.getId());
//			//ShipmentBillDetail pid = null;
//			Double receivablesum = 0.00;
//			
//			for (int i = 0; i < pils.size(); i++) {
//				PeddleOrderDetail pid = (PeddleOrderDetail) pils.get(i);		
//				单明细
//				ReceivableDetail rvd = new ReceivableDetail();
//				rvd.setId(Long.valueOf(MakeCode.getExcIDByRandomTableName("receivable_detail",0,"")));
//				rvd.setRid(rid);
//				rvd.setProductid(pid.getProductid());
//				rvd.setProductname(pid.getProductname());
//				rvd.setSpecmode(pid.getSpecmode());
//				rvd.setBatch(pid.getBatch());
//				rvd.setBillno(rv.getBillno());
//				rvd.setPaymentmode(so.getPaymentmode());
//				rvd.setUnitid(pid.getUnitid());
//				rvd.setQuantity(pid.getQuantity());
//				rvd.setGoodsfund(pid.getUnitprice());
//				rvd.setScot(0d);
//				rvd.setAlreadyquantity(pid.getQuantity());
//				rvd.setAlreadysum(pid.getUnitprice()*pid.getQuantity());
//				rvd.setIsclose(1);
//				rvd.setRoid(rv.getRoid());
//				rvd.setMakedate(rv.getMakedate());
//				receivablesum = receivablesum +(pid.getQuantity()*pid.getUnitprice());
//				apprvd.addReceivableDetail(rvd);
//			}
//			rv.setReceivablesum(receivablesum);
//			appr.addReceivable(rv);	
//			
//			
//			IncomeLog il = new IncomeLog();
//			String ilid = MakeCode.getExcIDByRandomTableName("income_log", 2,
//					"");
//			il.setId(ilid);
//			il.setRoid(so.getCid());
//			il.setDrawee(so.getCname());
//			il.setFundattach(1);			
//			il.setPoundage(0d);
//			il.setAccidentsum(0d);
//			il.setPreparereceive(0d);
//			il.setBillnum(rid);
//			il.setIncomedate(DateUtil.StringToDatetime(DateUtil.getCurrentDateTime()));
//			il.setRemark("零售单收款");
//			il.setMakeorganid(users.getMakeorganid());
//			il.setMakedeptid(users.getMakedeptid());
//			il.setMakeid(userid);
//			il.setMakedate(DateUtil.StringToDatetime(DateUtil.getCurrentDateTime()));
//			il.setIsaudit(0);
//			il.setIsblankout(0);
//			il.setIsclose(0);
//			
//			double incomesum =0.00;
//			IncomeLogDetail ild = null;
//			AppIncomeLogDetail appild = new AppIncomeLogDetail();
//			AppReceivableDetail apprd = new AppReceivableDetail();
//			for (int j = 0; j < pils.size(); j++) {
//				PeddleOrderDetail pod = (PeddleOrderDetail) pils.get(j);	
//				ild = new IncomeLogDetail();
//				ild.setId(Long.valueOf(MakeCode.getExcIDByRandomTableName("income_log_detail",0,"")));
//				ild.setIlid(ilid);
//				ild.setRoid(so.getCid());
//				ild.setProductid(pod.getProductid());
//				ild.setProductname(pod.getProductname());
//				ild.setSpecmode(pod.getSpecmode());
//				ild.setBatch(pod.getBatch());
//				ild.setBillno(rv.getBillno());
//				ild.setUnitid(pod.getUnitid());
//				ild.setPaymentmode(so.getPaymentmode());
//				ild.setQuantity(pod.getQuantity());
//				ild.setGoodsfund(pod.getUnitprice());
//				ild.setScot(0d);
//				ild.setGatherquantity(0d);
//				ild.setGathersum(0d);
//				ild.setMakedate(DateUtil.StringToDatetime(DateUtil.getCurrentDateTime()));
//				ild.setDestorymode(0);
//				incomesum =incomesum+(pod.getQuantity()*pod.getUnitprice());
//				appild.addIncomeLogDetail(ild);
//			}
//			il.setIncomesum(incomesum);
//			AppIncomeLog ail = new AppIncomeLog();
//			ail.addIncomeLog(il);
//			
//			String upd = aso.updIsAudit(soid, userid, 1);
//
//			request.setAttribute("result", "databases.audit.success");
//			DBUserLog.addUserLog(userid, "复核零售单");
//
//			return mapping.findForward("audit");
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return new ActionForward(mapping.getInput());
//	}
//
//}
