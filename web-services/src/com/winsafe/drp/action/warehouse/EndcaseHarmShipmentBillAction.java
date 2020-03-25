package com.winsafe.drp.action.warehouse;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppFUnit;
import com.winsafe.drp.dao.AppHarmShipmentBill;
import com.winsafe.drp.dao.AppHarmShipmentBillDetail;
import com.winsafe.drp.dao.AppProductStockpile;
import com.winsafe.drp.dao.HarmShipmentBill;
import com.winsafe.drp.dao.HarmShipmentBillDetail;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;

public class EndcaseHarmShipmentBillAction extends BaseAction {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		UsersBean users = UserManager.getUser(request);
//		Long userid = users.getUserid();

		super.initdata(request);try{
			String id = request.getParameter("id");
			AppHarmShipmentBill apb = new AppHarmShipmentBill();
			HarmShipmentBill pb = apb.getHarmShipmentBillByID(id);

			if (pb.getIsblankout() == 1) {
				String result = "databases.record.blankoutnooperator";
				request.setAttribute("result", result);
				return new ActionForward("/sys/lockrecordclose.jsp");
			}
			if (pb.getIsaudit() == 0) {
				String result = "databases.record.nooperator";
				request.setAttribute("result", result);
				return new ActionForward("/sys/lockrecordclose.jsp");
			}
			

			AppHarmShipmentBillDetail aspb = new AppHarmShipmentBillDetail();
			List<HarmShipmentBillDetail> spbls = aspb.getHarmShipmentBillDetailBySbID(id); 
			AppProductStockpile aps = new AppProductStockpile();
			AppFUnit apfu = new AppFUnit();
			for (HarmShipmentBillDetail pid : spbls) {
				

//				aps.returninProductStockpile(pid.getProductid(), pid.getBatch(), 
//						apfu.getQuantity(pid.getProductid(), pid.getUnitid().intValue(), pid.getQuantity()), 
//						pb.getWarehouseid(),id,"报损-出库");

//				double stock = aps.getProductStockpileByProductIDWIDBatch(pid
//						.getProductid(), sb.getWarehouseid(), pid.getBatch());
//				if (pid.getQuantity() - stock > 0) {
//					String result = "databases.makeshipment.nostockpile";
//					request.setAttribute("result", result);
//					return new ActionForward("/sys/lockrecord.jsp");
//				}
			}

			/*
			AppTakeService appts = new AppTakeService();
			List<TakeTicket> ticketlist = appts.getTakeTicketByBillno(id);
			
			if (appts.isNotAuditTakeTicket(ticketlist)) {
				String result = "databases.record.taketicket.noaudit";
				request.setAttribute("result", result);
				return new ActionForward("/sys/lockrecordclose.jsp");
			}
			
			
			FeeWasteBook fwb =new FeeWasteBook();
			fwb.setId(Long.valueOf(MakeCode.getExcIDByRandomTableName(
						"fee_waste_book", 0, "")));
			fwb.setCid("");
			fwb.setCname(users.getRealname());
			fwb.setFeedept(pb.getShipmentdept());
			fwb.setFeeid(pb.getMakeid());
			fwb.setBillno(pb.getId());
			fwb.setMemo("报损出库生成费用台帐");
			fwb.setCycleinsum(pb.getTotalsum());
			fwb.setCycleoutsum(0d);
			fwb.setRecorddate(DateUtil.StringToDatetime(DateUtil.getCurrentDateTime()));
			AppFeeWasteBook apfwb = new AppFeeWasteBook();
			apfwb.addFeeWasteBook(fwb);
			
			*/

//			apb.updIsEndCase(id, userid, 1);
			
			request.setAttribute("result", "databases.audit.success");
//			DBUserLog.addUserLog(userid, "结案报损出库,编号：" + id);

			return mapping.findForward("audit");
		} catch (Exception e) {

			e.printStackTrace();
		} finally {

		}
		return new ActionForward(mapping.getInput());
	}

}
