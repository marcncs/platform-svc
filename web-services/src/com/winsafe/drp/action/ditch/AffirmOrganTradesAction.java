package com.winsafe.drp.action.ditch;

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
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.MakeCode;

/**
 * @author : jerry
 * @version : 2009-8-24 下午05:28:53 www.winsafe.cn
 */
public class AffirmOrganTradesAction extends BaseAction {

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		super.initdata(request);
		try {
			String id = request.getParameter("ID");
			AppOrganTrades appAMA = new AppOrganTrades();
			OrganTrades ow = appAMA.getOrganTradesByID(id);

			if (ow.getIsaudit() == 0) {
				request.setAttribute("result", "退货单未复核!不能进行此操作!");
				return new ActionForward("/sys/lockrecordclose2.jsp");
			}
			if (ow.getIsratify() == 0) {
				request.setAttribute("result", "退货单未批准!不能进行此操作!");
				return new ActionForward("/sys/lockrecordclose2.jsp");
			}

			if (ow.getIsblankout() == 1) {
				request.setAttribute("result",
						"databases.record.blankoutnooperator");
				return new ActionForward("/sys/lockrecordclose.jsp");
			}

			AppOrganTradesDetail appowd = new AppOrganTradesDetail();
			List<OrganTradesDetail> listowd = appowd
					.getOrganTradesDetailByotid(id);
			AppProductStockpileAll appps = new AppProductStockpileAll();
			AppFUnit appfu = new AppFUnit();
			for (OrganTradesDetail sod : listowd) {

				double q = appfu.getQuantity(sod.getProductid(), sod
						.getUnitid(), sod.getCanquantity());
				double stock = appps.getProductStockpileAllByPIDWID(sod
						.getProductid(), ow.getOutwarehouseid());
				if (q > stock) {
					request.setAttribute("result", sod.getProductname()
							+ "产品库存不足，不能确定!");
					return new ActionForward("/sys/lockrecordclose2.jsp");
				}
			}

			addTakeBill(ow, listowd, users);

			ow.setIsaffirm(1);
			ow.setAffirmid(userid);
			ow.setAffirmdate(DateUtil.getCurrentDate());
			appAMA.update(ow);

			request.setAttribute("result", "databases.isaffirm.success");
			DBUserLog.addUserLog(userid, 4, "渠道管理>>确认渠道换货,编号：" + id);

			return mapping.findForward("success");

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return super.execute(mapping, form, request, response);
	}

	public void addTakeBill(OrganTrades so, List<OrganTradesDetail> pils,
			UsersBean users) throws Exception {
		TakeBill takebill = new TakeBill();
		takebill.setId(so.getId());
		takebill.setBsort(8);
		takebill.setOid(so.getPorganid());
		takebill.setOname(so.getPorganname());
		takebill.setRlinkman(so.getPlinkman());
		takebill.setTel(so.getTel());
		takebill.setInwarehouseid(so.getInwarehouseid());
		takebill.setSenddate(DateUtil.getCurrentDate());
		takebill.setMakeorganid(so.getMakeorganid());
		takebill.setMakedeptid(so.getMakedeptid());
		takebill.setEquiporganid(users.getMakeorganid());
		takebill.setMakeid(users.getUserid());
		takebill.setMakedate(DateUtil.getCurrentDate());
		takebill.setIsaudit(0);
		takebill.setIsblankout(0);

		TakeServiceBean tsb = new TakeServiceBean();
		tsb.setTakebill(takebill);

		TakeTicket tt = null;
		for (int i = 0; i < pils.size(); i++) {
			OrganTradesDetail pid = (OrganTradesDetail) pils.get(i);

			if (tsb.getTtmap().get(so.getOutwarehouseid()) == null) {
				tt = new TakeTicket();
				tt.setId(MakeCode.getExcIDByRandomTableName("take_ticket", 2,
						"TT"));
				tt.setWarehouseid(so.getOutwarehouseid());
				tt.setBillno(takebill.getId());
				tt.setBsort(8);
				tt.setOid(takebill.getOid());
				tt.setOname(takebill.getOname());
				tt.setRlinkman(takebill.getRlinkman());
				tt.setTel(takebill.getTel());
				tt.setInwarehouseid(takebill.getInwarehouseid());
				tt.setRemark(so.getWithdrawcause());
				tt.setIsaudit(0);
				tt.setMakeorganid(users.getMakeorganid());
				tt.setMakedeptid(users.getMakedeptid());
				tt.setMakeid(users.getUserid());
				tt.setMakedate(DateUtil.getCurrentDate());
				tt.setPrinttimes(0);
				tt.setIsblankout(0);
				tsb.getTtmap().put(so.getOutwarehouseid().toString(), tt);
				tsb.getWarehouseids().add(so.getOutwarehouseid().toString());
			} else {
				tt = tsb.getTtmap().get(so.getOutwarehouseid().toString());
			}

			// TakeTicketDetailService ttds = new
			// TakeTicketDetailService(tt,tt.getWarehouseid(),pid.getProductid(),
			// pid.getProductname(), pid.getSpecmode(), pid.getUnitid(),
			// pid.getUnitprice());
			// ttds.addBatchDetail(pid.getQuantity());

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
		}

		AppTakeService appts = new AppTakeService();
		appts.addTake(tsb, true);
	}

}
