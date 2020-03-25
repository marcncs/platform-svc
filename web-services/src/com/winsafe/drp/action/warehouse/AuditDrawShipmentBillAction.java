package com.winsafe.drp.action.warehouse;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppDrawShipmentBill;
import com.winsafe.drp.dao.AppDrawShipmentBillDetail;
import com.winsafe.drp.dao.AppFUnit;
import com.winsafe.drp.dao.AppProductStockpileAll;
import com.winsafe.drp.dao.AppTakeService;
import com.winsafe.drp.dao.DrawShipmentBill;
import com.winsafe.drp.dao.DrawShipmentBillDetail;
import com.winsafe.drp.dao.TakeBill;
import com.winsafe.drp.dao.TakeServiceBean;
import com.winsafe.drp.dao.TakeTicket;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.Users;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.server.TakeTicketDetailService;
import com.winsafe.drp.server.UsersService;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.MakeCode;

public class AuditDrawShipmentBillAction extends BaseAction {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		UsersBean users = UserManager.getUser(request);
		int userid = users.getUserid();
		super.initdata(request);try{
			String osid = request.getParameter("OSID");
			AppDrawShipmentBill asb = new AppDrawShipmentBill();			
			DrawShipmentBill sb = asb.getDrawShipmentBillByID(osid);

			if (sb.getIsaudit() == 1) {
				request.setAttribute("result", "databases.record.audit");
				return new ActionForward("/sys/lockrecordclose.jsp");
			}
			if (sb.getIsblankout() == 1) {
				request.setAttribute("result", "databases.record.blankoutnooperator");
				return new ActionForward("/sys/lockrecordclose.jsp");
			}

			
			AppDrawShipmentBillDetail aspb = new AppDrawShipmentBillDetail();			
			List<DrawShipmentBillDetail> dls= aspb.getDrawShipmentBillDetailByDsid(osid);	
			AppProductStockpileAll appps = new AppProductStockpileAll();
			AppFUnit appfu = new AppFUnit();
			for (DrawShipmentBillDetail sod : dls ) {				
				
				double q = appfu.getQuantity(sod.getProductid(), sod.getUnitid(), sod.getQuantity());
				double stock = appps.getProductStockpileAllByPIDWID(sod.getProductid(), sb.getWarehouseid());
				if (q > stock ) {
					request.setAttribute("result", sod.getProductname()+"产品库存不足，不能复核!");
					return new ActionForward("/sys/lockrecordclose2.jsp");
				}
			}
			
			addTakeBill(sb, dls, users);
			asb.updIsAudit(osid, userid, 1);
			
			request.setAttribute("result", "databases.audit.success");
			DBUserLog.addUserLog(userid, 7, "库存处理>>复核领用出库单,编号:"+osid);			
			return mapping.findForward("audit");
		} catch (Exception e) {			
			e.printStackTrace();
		} 
		return new ActionForward(mapping.getInput());
	}
	
	public void addTakeBill(DrawShipmentBill so, List<DrawShipmentBillDetail> pils, UsersBean users) throws Exception{
		UsersService us = new UsersService();
		Users u = us.getUsersByid(so.getMakeid());
		TakeBill takebill = new TakeBill();
		takebill.setId(so.getId());
		takebill.setBsort(4);
		takebill.setOid(so.getMakeid().toString());
		takebill.setOname(u.getRealname());
		takebill.setRlinkman(u.getRealname());
		takebill.setTel(u.getMobile());
		takebill.setInwarehouseid(so.getWarehouseid());
		takebill.setSenddate(so.getDrawdate());
		takebill.setMakeorganid(so.getMakeorganid());
		takebill.setMakedeptid(so.getMakedeptid());
		takebill.setEquiporganid(so.getMakeorganid());
		takebill.setMakeid(users.getUserid());
		takebill.setMakedate(DateUtil.getCurrentDate());			
		takebill.setIsaudit(0);
		takebill.setIsblankout(0);

		TakeServiceBean tsb = new TakeServiceBean();
		tsb.setTakebill(takebill);
		
		TakeTicket tt = null;
		for (DrawShipmentBillDetail pid : pils) {
			
			if (tsb.getTtmap().get(so.getWarehouseid()) == null) {
				tt = new TakeTicket();
				tt.setId(MakeCode.getExcIDByRandomTableName("take_ticket",2, "TT"));
				tt.setWarehouseid(so.getWarehouseid());
				tt.setBillno(so.getId());
				tt.setBsort(4);
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
				tt.setInwarehouseid(takebill.getInwarehouseid());
				tt.setPrinttimes(0);
				tt.setIsblankout(0);
				tsb.getTtmap().put(so.getWarehouseid(), tt);
				tsb.getWarehouseids().add(so.getWarehouseid());
			} else {
				tt = tsb.getTtmap().get(so.getWarehouseid());
			}
			
			TakeTicketDetailService ttds = new TakeTicketDetailService(tt,tt.getWarehouseid(),pid.getProductid(),
					pid.getProductname(), pid.getSpecmode(), pid.getUnitid(), pid.getUnitprice());
			ttds.addBatchDetail(pid.getQuantity());
			
		}
		
		AppTakeService appts = new AppTakeService();
		appts.addTake(tsb, false);
	}

}
