package com.winsafe.drp.action.ditch;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppFUnit;
import com.winsafe.drp.dao.AppProductStockpileAll;
import com.winsafe.drp.dao.AppSupplySaleMove;
import com.winsafe.drp.dao.AppSupplySaleMoveDetail;
import com.winsafe.drp.dao.AppTakeService;
import com.winsafe.drp.dao.SupplySaleMove;
import com.winsafe.drp.dao.SupplySaleMoveDetail;
import com.winsafe.drp.dao.TakeBill;
import com.winsafe.drp.dao.TakeServiceBean;
import com.winsafe.drp.dao.TakeTicket;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.server.MsgService;
import com.winsafe.drp.server.OrganService;
import com.winsafe.drp.server.TakeTicketDetailService;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.MakeCode;

/**
 * @author : jerry
 * @version : 2009-9-02 上午10:28:53 www.winsafe.cn
 */
public class AuditSupplySaleMoveAction extends BaseAction {

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		super.initdata(request);
		try {
			String id = request.getParameter("ID");
			AppSupplySaleMove appAMA = new AppSupplySaleMove();
			SupplySaleMove ssm = appAMA.getSupplySaleMoveByID(id);

			if (ssm.getIsaudit() == 1) {
				request.setAttribute("result", "databases.record.audit");
				return new ActionForward("/sys/lockrecordclose.jsp");
			}

			if (ssm.getIsblankout() == 1) {
				request.setAttribute("result",
						"databases.record.blankoutnooperator");
				return new ActionForward("/sys/lockrecordclose.jsp");
			}

			if (!ssm.getMakeorganid().equals(users.getMakeorganid())) {
				request.setAttribute("result", "databases.record.nopurview");
				return new ActionForward("/sys/lockrecordclose.jsp");
			}

			AppSupplySaleMoveDetail appSsmd = new AppSupplySaleMoveDetail();
			List<SupplySaleMoveDetail> listowd = appSsmd
					.getSupplySaleMoveBySSMID(id);
			AppProductStockpileAll appps = new AppProductStockpileAll();
			AppFUnit appfu = new AppFUnit();
			for (SupplySaleMoveDetail owd : listowd) {

				if (owd.getQuantity().toString().equals("0.0")) {
					request.setAttribute("result", "产品：" + owd.getProductname()
							+ " 数量为0! 请更改单据! ");
					return new ActionForward("/sys/lockrecordclose2.jsp");
				}

				double q = appfu.getQuantity(owd.getProductid(), owd
						.getUnitid(), owd.getQuantity());
				double stock = appps.getProductStockpileAllByPIDWID(owd
						.getProductid(), ssm.getOutwarehouseid());
				if (q > stock) {
					request.setAttribute("result", "产品：" + owd.getProductname()
							+ " 库存量不足! 复核不通过!请更改单据! ");
					return new ActionForward("/sys/lockrecordclose2.jsp");
				}
			}

			addTakeBill(ssm, listowd, users);

			ssm.setIsaudit(1);
			ssm.setAuditid(userid);
			ssm.setAuditdate(DateUtil.getCurrentDate());
			appAMA.update(ssm);

			String[] param = new String[] { "name", "applytime", "billno" };
			String[] values = new String[] { ssm.getOlinkman(),
					DateUtil.formatDate(ssm.getMovedate()), ssm.getId() };
			MsgService ms = new MsgService(param, values, users, 9);
			ms.addmag(1, ssm.getOtel());

			request.setAttribute("result", "databases.audit.success");
			DBUserLog.addUserLog(userid, 4, "渠道管理>>复核渠道代销,编号：" + id);

			return mapping.findForward("success");

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return super.execute(mapping, form, request, response);
	}

	public void addTakeBill(SupplySaleMove so, List<SupplySaleMoveDetail> pils,
			UsersBean users) throws Exception {
		OrganService organS = new OrganService();
		TakeBill takebill = new TakeBill();
		takebill.setId(so.getId());
		takebill.setBsort(3);
		takebill.setOid(so.getInorganid());
		takebill.setOname(organS.getOrganName(so.getInorganid()));
		takebill.setRlinkman(so.getOlinkman());
		takebill.setTel(so.getOtel());
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
		for (int i = 0; i < pils.size(); i++) {
			SupplySaleMoveDetail pid = (SupplySaleMoveDetail) pils.get(i);

			if (tsb.getTtmap().get(so.getOutwarehouseid()) == null) {
				tt = new TakeTicket();
				tt.setId(MakeCode.getExcIDByRandomTableName("take_ticket", 2,
						"TT"));
				tt.setWarehouseid(so.getOutwarehouseid());
				tt.setBillno(takebill.getId());
				tt.setBsort(3);
				tt.setOid(takebill.getOid());
				tt.setOname(takebill.getOname());
				tt.setRlinkman(takebill.getRlinkman());
				tt.setTel(takebill.getTel());
				tt.setInwarehouseid(takebill.getInwarehouseid());
				tt.setRemark(so.getMovecause());
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

			TakeTicketDetailService ttds = new TakeTicketDetailService(tt, tt
					.getWarehouseid(), pid.getProductid(),
					pid.getProductname(), pid.getSpecmode(), pid.getUnitid(),
					pid.getSunitprice());
			ttds.addBatchDetail(pid.getQuantity());

			// TakeTicketDetail ttd = new TakeTicketDetail();
			// ttd.setId(Integer.valueOf(MakeCode.getExcIDByRandomTableName(
			// "take_ticket_detail", 0, "")));
			// ttd.setProductid(pid.getProductid());
			// ttd.setProductname(pid.getProductname());
			// ttd.setSpecmode(pid.getSpecmode());
			// ttd.setUnitid(pid.getUnitid());
			// ttd.setBatch(pid.getBatch());
			// ttd.setUnitprice(pid.getUnitprice());
			// ttd.setQuantity(pid.getQuantity());
			// ttd.setTtid(tt.getId());
			// tt.getTtdetails().add(ttd);
		}

		AppTakeService appts = new AppTakeService();
		appts.addTake(tsb, false);
	}

}
