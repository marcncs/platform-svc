package com.winsafe.drp.action.sales;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppFUnit;
import com.winsafe.drp.dao.AppIntegralOrder;
import com.winsafe.drp.dao.AppIntegralOrderDetail;
import com.winsafe.drp.dao.AppProductStockpileAll;
import com.winsafe.drp.dao.AppTakeService;
import com.winsafe.drp.dao.IntegralOrder;
import com.winsafe.drp.dao.IntegralOrderDetail;
import com.winsafe.drp.dao.TakeBill;
import com.winsafe.drp.dao.TakeServiceBean;
import com.winsafe.drp.dao.TakeTicket;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.server.TakeTicketDetailService;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.MakeCode;

public class AuditIntegralOrderAction extends BaseAction {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		initdata(request);

		try {
			String soid = request.getParameter("SOID");
			AppIntegralOrder aso = new AppIntegralOrder();
			IntegralOrder so = aso.getIntegralOrderByID(soid);

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

			AppIntegralOrderDetail appsod = new AppIntegralOrderDetail();
			List<IntegralOrderDetail> pils = appsod.getIntegralOrderDetailByIoid(soid);
			
			AppProductStockpileAll appps = new AppProductStockpileAll();
			AppFUnit appfu = new AppFUnit();
			for (IntegralOrderDetail sod : pils ) {				
				
				double q = appfu.getQuantity(sod.getProductid(), sod.getUnitid(), sod.getQuantity());
				double stock = appps.getProductStockpileAllByPIDWID(sod.getProductid(), sod.getWarehouseid());
				if (q > stock ) {
					request.setAttribute("result", sod.getProductname()+"产品库存不足，不能复核!");
					return new ActionForward("/sys/lockrecordclose2.jsp");
				}
			}
			
			
			addTakeBill(so, pils, users);
			
			aso.updIsAudit(soid, userid, 1);
			
			//---------------------积分预处理表写入积分表-------------------------
			
//			AppCIntegralDeal acid = new AppCIntegralDeal();
//			AppCIntegral aci = new AppCIntegral();
//			CIntegral ci = new CIntegral();
//			CIntegralDeal o=(CIntegralDeal)acid.getCIntegralDealByBillNo(soid, 9);
//			ci.setOrganid(o.getOrganid());
//			ci.setCid(o.getCid());
//			ci.setIsort(o.getIsort());
//			ci.setCintegral(o.getDealintegral());
//			ci.setIyear(DateUtil.getCurrentYear());
//			aci.addCIntegralIsNoExist(ci);
//			aci.updCIntegralByIntegral(ci);
//			
//			acid.updCIntegralDealByID(o.getId(), ci.getCintegral());
//			
//			
//			AppOIntegralDeal aoid = new AppOIntegralDeal();
//			AppOIntegral aoi = new AppOIntegral();
//			OIntegral oi = new OIntegral();
//			OIntegralDeal obj = (OIntegralDeal)aoid.getOIntegralDealByBillNo(soid, 9);
//			oi.setPowerorganid("1");
//			oi.setEquiporganid(obj.getOid());
//			oi.setIsort(obj.getIsort());
//			oi.setOintegral(obj.getDealintegral());
//			oi.setIyear(DateUtil.getCurrentYear());
//			aoi.addOIntegralIsNoExist(oi);
//			aoi.updOIntegralByIntegral(oi);			
//			
//			aoid.updOIntegralDealByID(obj.getId(), oi.getOintegral());
			//-------------------------------------------------------------------

			request.setAttribute("result", "databases.audit.success");
			DBUserLog.addUserLog(userid, 6, "积分换购>>复核积分换购单,编号:"+soid);

			return mapping.findForward("audit");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
	
	public void addTakeBill(IntegralOrder so, List<IntegralOrderDetail> pils, UsersBean users) throws Exception{
		TakeBill takebill = new TakeBill();
		takebill.setId(so.getId());
		takebill.setBsort(12);
		takebill.setOid(so.getCid());
		takebill.setOname(so.getCname());
		takebill.setRlinkman(so.getDecideman());
		takebill.setTel(so.getCmobile());
		takebill.setInwarehouseid("");
		takebill.setSenddate(so.getConsignmentdate());
		takebill.setMakeorganid(users.getMakeorganid());
		takebill.setMakedeptid(users.getMakedeptid());
		takebill.setEquiporganid(users.getMakeorganid());
		takebill.setMakeid(users.getUserid());
		takebill.setMakedate(DateUtil.getCurrentDate());		
		takebill.setIsaudit(0);
		takebill.setIsblankout(0);

		TakeServiceBean tsb = new TakeServiceBean();
		tsb.setTakebill(takebill);
		
		TakeTicket tt = null;
		for (IntegralOrderDetail pid : pils ) {
			
			if (tsb.getTtmap().get(pid.getWarehouseid()) == null) {
				tt = new TakeTicket();
				tt.setId(MakeCode.getExcIDByRandomTableName("take_ticket",2, "TT"));
				tt.setWarehouseid(pid.getWarehouseid());
				tt.setBillno(so.getId());
				tt.setBsort(10);
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
					pid.getProductname(), pid.getSpecmode(), pid.getUnitid(), pid.getIntegralprice());
			ttds.addBatchDetail(pid.getQuantity());
		}
		
		AppTakeService appts = new AppTakeService();
		appts.addTake(tsb, false);
	}

}
