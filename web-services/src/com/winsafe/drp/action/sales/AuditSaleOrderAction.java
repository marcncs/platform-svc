package com.winsafe.drp.action.sales;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppFUnit;
import com.winsafe.drp.dao.AppObjIntegral;
import com.winsafe.drp.dao.AppOrgan;
import com.winsafe.drp.dao.AppProductStockpileAll;
import com.winsafe.drp.dao.AppReceivable;
import com.winsafe.drp.dao.AppReceivableObject;
import com.winsafe.drp.dao.AppSaleOrder;
import com.winsafe.drp.dao.AppSaleOrderDetail;
import com.winsafe.drp.dao.AppTakeService;
import com.winsafe.drp.dao.ObjIntegral;
import com.winsafe.drp.dao.Organ;
import com.winsafe.drp.dao.Receivable;
import com.winsafe.drp.dao.ReceivableObject;
import com.winsafe.drp.dao.SaleOrder;
import com.winsafe.drp.dao.SaleOrderDetail;
import com.winsafe.drp.dao.TakeBill;
import com.winsafe.drp.dao.TakeServiceBean;
import com.winsafe.drp.dao.TakeTicket;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.server.TakeTicketDetailService;
import com.winsafe.drp.util.Constants;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.MakeCode;

public class AuditSaleOrderAction extends BaseAction {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		super.initdata(request);

		try {
			String soid = request.getParameter("SOID");
			AppSaleOrder aso = new AppSaleOrder();
			SaleOrder so = aso.getSaleOrderByID(soid);

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

			AppSaleOrderDetail appsod = new AppSaleOrderDetail();
			List<SaleOrderDetail> plist = appsod.getSaleOrderDetailBySoidWise(soid, 0);
			List<SaleOrderDetail> flist = appsod.getSaleOrderDetailBySoidWise(soid, 1);
		    
			AppProductStockpileAll appps = new AppProductStockpileAll();
			AppFUnit appfu = new AppFUnit();
			for (SaleOrderDetail sod : plist ) {				
				
				double q = appfu.getQuantity(sod.getProductid(), sod.getUnitid(), sod.getQuantity());
				double stock = appps.getProductStockpileAllByPIDWID(sod.getProductid(), sod.getWarehouseid());
				if (q > stock ) {
					request.setAttribute("result", sod.getProductname()+"产品库存不足，不能复核!");
					return new ActionForward("/sys/lockrecordclose2.jsp");
				}
			}

			
			addTakeBill(so, plist, users);
			
			
			for ( SaleOrderDetail sod : flist ) {
				appsod.updTakeQuantity(sod.getSoid(), sod.getProductid(),
							sod.getBatch(), sod.getQuantity());
			}
			
			
			
			ObjIntegral ci = new ObjIntegral();
			ci.setOid(so.getCid());
			ci.setOsort(1);
			ci.setOname(so.getCname());
			ci.setOmobile(so.getCmobile());
			ci.setOrganid(so.getMakeorganid());
			ci.setKeyscontent(so.getCid()+","+so.getCname()+","+so.getCmobile());
			AppObjIntegral aoi = new AppObjIntegral();
			aoi.addObjIntegralIsNoExist(ci);
			
			AppOrgan ao = new AppOrgan();
			Organ o = ao.getOrganByID(so.getEquiporganid());
			ObjIntegral oi = new ObjIntegral();
			oi.setOid(so.getEquiporganid());
			oi.setOsort(0);
			oi.setOname(o.getOrganname());
			oi.setOmobile(o.getOmobile());
			oi.setOrganid(Constants.ORGANID);
			oi.setKeyscontent(oi.getOid()+","+oi.getOname()+","+oi.getOmobile());
			aoi.addObjIntegralIsNoExist(oi);
			
			IntegralService ids = new IntegralService();
			ids.SaleOrderIntegral(so, plist);
			
			aso.updIsAudit(soid, userid, 1);
			
			
			if ( plist.isEmpty() ){
				
				AppReceivableObject appro = new AppReceivableObject();
				ReceivableObject ro = new ReceivableObject();
				ro.setOid(so.getCid());
				ro.setObjectsort(1);
				ro.setPayer(so.getCname());
				ro.setMakeorganid(users.getMakeorganid());
				ro.setMakedeptid(users.getMakedeptid());
				ro.setMakeid(userid);
				ro.setMakedate(DateUtil.StringToDatetime(DateUtil
						.getCurrentDateTime()));
				ro.setKeyscontent(so.getCid()+","+so.getCname()+","+so.getCmobile());
				appro.addReceivableObjectIsNoExist(ro);

				
				AppReceivable ar = new AppReceivable();
				Receivable r = new Receivable();
				r.setId(MakeCode.getExcIDByRandomTableName("receivable",2, ""));
				r.setRoid(so.getCid());
				r.setReceivablesum(so.getTotalsum());
				r.setPaymentmode(so.getPaymentmode());
				r.setBillno(so.getId());
				r.setReceivabledescribe(so.getId() + "销售单费用生成应收款");
				r.setAlreadysum(0.00d);
				r.setIsclose(0);
				r.setMakeorganid(users.getMakeorganid());
				r.setMakedeptid(users.getMakedeptid());
				r.setMakeid(so.getMakeid());
				r.setMakedate(DateUtil.StringToDatetime(DateUtil
						.getCurrentDateTime()));
				ar.addReceivable(r);
				
				
				aso.updIsEndcase(soid, userid, 1);
			}

			request.setAttribute("result", "databases.audit.success");
			DBUserLog.addUserLog(userid, 6, "销售单>>复核销售单,编号:"+soid);

			return mapping.findForward("audit");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
	
	public void addTakeBill(SaleOrder so, List<SaleOrderDetail> pils, UsersBean users) throws Exception{
		TakeBill takebill = new TakeBill();
		takebill.setId(so.getId());
		takebill.setBsort(0);
		takebill.setOid(so.getCid());
		takebill.setOname(so.getCname());
		takebill.setRlinkman(so.getReceiveman());
		takebill.setTel(so.getReceivemobile());
		takebill.setInwarehouseid("");
		takebill.setSenddate(so.getConsignmentdate());
		takebill.setMakeorganid(so.getMakeorganid());
		takebill.setMakedeptid(so.getMakedeptid());
		takebill.setEquiporganid(so.getEquiporganid());
		takebill.setMakeid(users.getUserid());
		takebill.setMakedate(DateUtil.getCurrentDate());		
		takebill.setIsaudit(0);
		takebill.setIsblankout(0);

		TakeServiceBean tsb = new TakeServiceBean();
		tsb.setTakebill(takebill);
		
		TakeTicket tt = null;
		for (SaleOrderDetail pid : pils ) {
			
			if (tsb.getTtmap().get(pid.getWarehouseid()) == null) {
				tt = new TakeTicket();
				tt.setId(MakeCode.getExcIDByRandomTableName("take_ticket",2, "TT"));
				tt.setWarehouseid(pid.getWarehouseid());
				tt.setBillno(so.getId());
				tt.setBsort(0);
				tt.setOid(takebill.getOid());
				tt.setOname(takebill.getOname());
				tt.setRlinkman(takebill.getRlinkman());
				tt.setTel(takebill.getTel());
				tt.setRemark(so.getRemark());
				tt.setIsaudit(0);
				tt.setMakeorganid(users.getMakeorganid());
				tt.setMakedeptid(users.getMakedeptid());
				tt.setMakeid(users.getUserid());
				tt.setMakedate(DateUtil.getCurrentDate());
				tt.setPrinttimes(0);
				tt.setIsblankout(0);
				tsb.getTtmap().put(pid.getWarehouseid(), tt);
				tsb.getWarehouseids().add(pid.getWarehouseid());
			} else {
				tt = tsb.getTtmap().get(pid.getWarehouseid());
			}
			
			TakeTicketDetailService ttds = new TakeTicketDetailService(tt,tt.getWarehouseid(),pid.getProductid(),
					pid.getProductname(), pid.getSpecmode(), pid.getUnitid(), pid.getUnitprice());
			ttds.addBatchDetail(pid.getQuantity());
			
//			TakeTicketDetail ttd = new TakeTicketDetail();
//			ttd.setId(Integer.valueOf(MakeCode.getExcIDByRandomTableName(
//					"take_ticket_detail", 0, "")));
//			ttd.setProductid(pid.getProductid());
//			ttd.setProductname(pid.getProductname());
//			ttd.setSpecmode(pid.getSpecmode());
//			ttd.setUnitid(pid.getUnitid());
//			ttd.setBatch(pid.getBatch());
//			ttd.setUnitprice(pid.getTaxunitprice());
//			ttd.setQuantity(pid.getQuantity());
//			ttd.setTtid(tt.getId());			
//			tt.getTtdetails().add(ttd);
		}
		
		AppTakeService appts = new AppTakeService();
		appts.addTake(tsb, false);
	}

}
