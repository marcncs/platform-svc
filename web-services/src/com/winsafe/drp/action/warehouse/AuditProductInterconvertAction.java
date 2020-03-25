package com.winsafe.drp.action.warehouse;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppFUnit;
import com.winsafe.drp.dao.AppProductInterconvert;
import com.winsafe.drp.dao.AppProductInterconvertDetail;
import com.winsafe.drp.dao.AppProductStockpileAll;
import com.winsafe.drp.dao.AppTakeService;
import com.winsafe.drp.dao.ProductInterconvert;
import com.winsafe.drp.dao.ProductInterconvertDetail;
import com.winsafe.drp.dao.TakeBill;
import com.winsafe.drp.dao.TakeServiceBean;
import com.winsafe.drp.dao.TakeTicket;
import com.winsafe.drp.dao.TakeTicketDetail;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.Users;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.server.UsersService;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.MakeCode;

public class AuditProductInterconvertAction extends BaseAction {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		UsersBean users = UserManager.getUser(request);
		int userid = users.getUserid();		
		super.initdata(request);try{
			String smid = request.getParameter("SMID");
			AppProductInterconvert asb = new AppProductInterconvert();			
			ProductInterconvert sb = asb.getProductInterconvertByID(smid);			
			if (sb.getIsaudit() == 1) {
				request.setAttribute("result", "databases.record.audit");
				return new ActionForward("/sys/lockrecordclose.jsp");
			}
			if (sb.getIsblankout() == 1) {
				request.setAttribute("result", "databases.record.blankoutnooperator");
				return new ActionForward("/sys/lockrecordclose.jsp");
			}
			
			
			AppProductInterconvertDetail aspb = new AppProductInterconvertDetail();			
			List<ProductInterconvertDetail> dls= aspb.getProductInterconvertDetailBySamID(smid);	
			AppProductStockpileAll appps = new AppProductStockpileAll();
			AppFUnit appfu = new AppFUnit();
			for (ProductInterconvertDetail sod : dls ) {				
				
				double q = appfu.getQuantity(sod.getProductid(), sod.getUnitid(), sod.getQuantity());
				double stock = appps.getProductStockpileAllByPIDWID(sod.getProductid(), sb.getOutwarehouseid(), sod.getBatch());
				if (q > stock ) {
					request.setAttribute("result", sod.getProductname()+"产品库存不足，不能复核!");
					return new ActionForward("/sys/lockrecordclose2.jsp");
				}
			}
			

			
			addTakeBill(sb, dls, users);
			
			asb.updIsAudit(smid, userid, 1);
			
			request.setAttribute("result", "databases.audit.success");
			DBUserLog.addUserLog(userid, 7, "库存处理>>复核商品互转单,编号:"+smid);			
			return mapping.findForward("audit");
		} catch (Exception e) {			
			e.printStackTrace();
		} 
		return new ActionForward(mapping.getInput());
	}
	
	public void addTakeBill(ProductInterconvert so, List<ProductInterconvertDetail> pils, UsersBean users) throws Exception{
		UsersService us = new UsersService();
		Users u = us.getUsersByid(so.getMakeid());
		TakeBill takebill = new TakeBill();
		takebill.setId(so.getId());
		takebill.setBsort(6);
		takebill.setOid(so.getMakeid().toString());
		takebill.setOname(u.getRealname());
		takebill.setRlinkman(u.getRealname());
		takebill.setTel(u.getMobile());
		takebill.setInwarehouseid(so.getInwarehouseid());
		takebill.setSenddate(so.getMovedate());
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
		for (ProductInterconvertDetail pid : pils) {
			
			if (tsb.getTtmap().get(so.getOutwarehouseid()) == null) {
				tt = new TakeTicket();
				tt.setId(MakeCode.getExcIDByRandomTableName("take_ticket",2, "TT"));
				tt.setWarehouseid(so.getOutwarehouseid());
				tt.setBillno(takebill.getId());
				tt.setBsort(6);
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
				tsb.getTtmap().put(so.getOutwarehouseid(), tt);
				tsb.getWarehouseids().add(so.getOutwarehouseid());
			} else {
				tt = tsb.getTtmap().get(so.getOutwarehouseid());
			}
			
			TakeTicketDetail ttd = new TakeTicketDetail();
			ttd.setId(Integer.valueOf(MakeCode.getExcIDByRandomTableName(
					"take_ticket_detail", 0, "")));
			ttd.setProductid(pid.getProductid());
			ttd.setProductname(pid.getProductname());
			ttd.setSpecmode(pid.getSpecmode());
			ttd.setUnitid(pid.getUnitid());
			ttd.setBatch(pid.getBatch());
			ttd.setUnitprice(pid.getUnitprice());
			ttd.setQuantity(pid.getQuantity());
			ttd.setTtid(tt.getId());			
			tt.getTtdetails().add(ttd);
			
		}
		
		AppTakeService appts = new AppTakeService();
		appts.addTake(tsb, true);
	}
}
