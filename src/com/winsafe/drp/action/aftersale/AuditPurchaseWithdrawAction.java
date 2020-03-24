package com.winsafe.drp.action.aftersale;


import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppFUnit;
import com.winsafe.drp.dao.AppProductStockpileAll;
import com.winsafe.drp.dao.AppPurchaseWithdraw;
import com.winsafe.drp.dao.AppPurchaseWithdrawDetail;
import com.winsafe.drp.dao.AppTakeService;
import com.winsafe.drp.dao.PurchaseWithdraw;
import com.winsafe.drp.dao.PurchaseWithdrawDetail;
import com.winsafe.drp.dao.TakeBill;
import com.winsafe.drp.dao.TakeServiceBean;
import com.winsafe.drp.dao.TakeTicket;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.server.TakeTicketDetailService;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.MakeCode;

public class AuditPurchaseWithdrawAction extends Action {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
		UsersBean users = UserManager.getUser(request);
	    Integer userid = users.getUserid();

		try{
			String id = request.getParameter("id");
			AppPurchaseWithdraw apb = new AppPurchaseWithdraw(); 
			PurchaseWithdraw pb = apb.getPurchaseWithdrawByID(id);

			if(pb.getIsaudit()==1){
	               request.setAttribute("result", "databases.record.audit");
	               return new ActionForward("/sys/lockrecordclose.jsp");
	        }
			if (pb.getIsblankout() == 1) {
				request.setAttribute("result", "databases.record.blankoutnooperator");
				return new ActionForward("/sys/lockrecordclose.jsp");
			}
			
			AppPurchaseWithdrawDetail apid = new AppPurchaseWithdrawDetail();
			List<PurchaseWithdrawDetail> pils = apid.getPurchaseWithdrawDetailByPWID(id);	
			
			AppProductStockpileAll appps = new AppProductStockpileAll();
			AppFUnit appfu = new AppFUnit();
			for (PurchaseWithdrawDetail sod : pils ) {				
				
				double q = appfu.getQuantity(sod.getProductid(), sod.getUnitid(), sod.getQuantity());
				double stock = appps.getProductStockpileAllByPIDWID(sod.getProductid(), pb.getWarehouseid());
				if (q > stock ) {
					request.setAttribute("result", sod.getProductname()+"产品库存不足，不能复核!");
					return new ActionForward("/sys/lockrecordclose2.jsp");
				}
			}

		
			
			addTakeBill(pb, pils, users);
			
			pb.setAuditid(userid);
			pb.setIsaudit(1);
			pb.setAuditdate(DateUtil.getCurrentDate());
			apb.updPurchaseWithdraw(pb);

		      request.setAttribute("result", "databases.audit.success");
		      DBUserLog.addUserLog(userid, 2,"复核采购退货,编号："+id); 

			return mapping.findForward("audit");
		}catch(Exception e){

			e.printStackTrace();
		}finally {

	    }
		return new ActionForward(mapping.getInput());
	}
	
	public void addTakeBill(PurchaseWithdraw so, List<PurchaseWithdrawDetail> pils, UsersBean users) throws Exception{
		TakeBill takebill = new TakeBill();
		takebill.setId(so.getId());
		takebill.setBsort(10);
		takebill.setOid(so.getPid());
		takebill.setOname(so.getPname());
		takebill.setRlinkman(so.getPlinkman());
		takebill.setTel(so.getTel());
		takebill.setInwarehouseid("");
		takebill.setSenddate(so.getMakedate());
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
		for (PurchaseWithdrawDetail pid : pils ) {
			
			if (tsb.getTtmap().get(so.getWarehouseid()) == null) {
				tt = new TakeTicket();
				tt.setId(MakeCode.getExcIDByRandomTableName("take_ticket",2, "TT"));
				tt.setWarehouseid(so.getWarehouseid());
				tt.setBillno(so.getId());
				tt.setBsort(10);
				tt.setOid(takebill.getOid());
				tt.setOname(takebill.getOname());
				tt.setRlinkman(takebill.getRlinkman());
				tt.setTel(takebill.getTel());
				tt.setRemark(so.getWithdrawcause());
				tt.setIsaudit(0);
				tt.setMakeorganid(users.getMakeorganid());
				tt.setMakedeptid(users.getMakedeptid());
				tt.setMakeid(users.getUserid());
				tt.setMakedate(DateUtil.getCurrentDate());
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
