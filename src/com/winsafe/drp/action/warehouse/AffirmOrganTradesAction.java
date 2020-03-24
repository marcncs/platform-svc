package com.winsafe.drp.action.warehouse;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppFUnit;
import com.winsafe.drp.dao.AppOrganTrades;
import com.winsafe.drp.dao.AppOrganTradesDetail;
import com.winsafe.drp.dao.AppProductStockpileAll;
import com.winsafe.drp.dao.AppTakeService;
import com.winsafe.drp.dao.OrganTrades;
import com.winsafe.drp.dao.OrganTradesDetail;
import com.winsafe.drp.dao.TakeBill;
import com.winsafe.drp.dao.TakeServiceBean;
import com.winsafe.drp.dao.TakeTicket;
import com.winsafe.drp.dao.TakeTicketDetail;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.server.OrganService;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.MakeCode;

public class AffirmOrganTradesAction extends BaseAction {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
		UsersBean users = UserManager.getUser(request);
	    Integer userid = users.getUserid();

		super.initdata(request);try{
			String smid = request.getParameter("id");
			AppOrganTrades api = new AppOrganTrades(); 
			AppOrganTradesDetail asamd = new AppOrganTradesDetail();
			OrganTrades pi = api.getOrganTradesByID(smid);
			
			pi.setPoutwarehouseid(request.getParameter("poutwarehouseid"));
			
			List<OrganTradesDetail> dls = asamd.getOrganTradesDetailByotid(smid);	
			AppProductStockpileAll appps = new AppProductStockpileAll();
			AppFUnit appfu = new AppFUnit();
			for (OrganTradesDetail sod : dls ) {				
				
				double q = appfu.getQuantity(sod.getProductid(), sod.getUnitid(), sod.getCanquantity());
				double stock = appps.getProductStockpileAllByPIDWID(sod.getProductid(), pi.getPoutwarehouseid(), sod.getBatch());
				if (q > stock ) {
					request.setAttribute("result", sod.getProductname()+"产品库存不足，不能复核!");
					return new ActionForward("/sys/lockrecordclose2.jsp");
				}
			}
			
			addTakeBill(pi, dls, users);		
					
			pi.setPisaffirm(1);
			pi.setPaffirmid(userid);
			pi.setPaffirmdate(DateUtil.getCurrentDate());
			api.update(pi);
			
			request.setAttribute("result", "databases.audit.success");
			DBUserLog.addUserLog(userid, 4, "入库>>渠道换货供方签收>>确认渠道换货单,编号："+smid); 

			return mapping.findForward("success");
		}catch(Exception e){

			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
	
	public void addTakeBill(OrganTrades so, List<OrganTradesDetail> pils, UsersBean users) throws Exception{
		OrganService ao = new OrganService(); 
		TakeBill takebill = new TakeBill();
		takebill.setId(so.getIdii());
		takebill.setBsort(9);
		takebill.setOid(so.getMakeorganid());
		takebill.setOname(ao.getOrganName(so.getMakeorganid()));
		takebill.setRlinkman(so.getRlinkman());
		takebill.setTel(so.getRtel());
		takebill.setInwarehouseid(so.getInwarehouseid());
		takebill.setSenddate(DateUtil.getCurrentDate());
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
		for (OrganTradesDetail pid : pils) {
			
			if (tsb.getTtmap().get(so.getPoutwarehouseid()) == null) {
				tt = new TakeTicket();
				tt.setId(MakeCode.getExcIDByRandomTableName("take_ticket",2, "TT"));
				tt.setWarehouseid(so.getPoutwarehouseid());
				tt.setBillno(so.getIdii());
				tt.setBsort(9);
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
				tt.setInwarehouseid(takebill.getInwarehouseid());
				tt.setPrinttimes(0);
				tt.setIsblankout(0);
				tsb.getTtmap().put(so.getPoutwarehouseid(), tt);
				tsb.getWarehouseids().add(so.getPoutwarehouseid());
			} else {
				tt = tsb.getTtmap().get(so.getPoutwarehouseid());
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
			ttd.setQuantity(pid.getCanquantity());
			ttd.setTtid(tt.getId());			
			tt.getTtdetails().add(ttd);
			
//			TakeTicketDetailService ttds = new TakeTicketDetailService(tt,tt.getWarehouseid(),pid.getProductid(),
//					pid.getProductname(), pid.getSpecmode(), pid.getUnitid(), 0.00);
//			ttds.addBatchDetail(pid.getCanquantity());
			
		}
		
		AppTakeService appts = new AppTakeService();
		appts.addTake(tsb, true);
	}

}
