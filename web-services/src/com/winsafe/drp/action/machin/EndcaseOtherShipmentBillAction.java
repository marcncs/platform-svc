package com.winsafe.drp.action.machin;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppFeeWasteBook;
import com.winsafe.drp.dao.AppOtherShipmentBill;
import com.winsafe.drp.dao.AppTakeService;
import com.winsafe.drp.dao.FeeWasteBook;
import com.winsafe.drp.dao.OtherShipmentBill;
import com.winsafe.drp.dao.TakeTicket;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.MakeCode;

public class EndcaseOtherShipmentBillAction extends BaseAction {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		UsersBean users = UserManager.getUser(request);
//		Long userid = users.getUserid();
		////Connection conn = null;
		super.initdata(request);try{
			String id = request.getParameter("id");
			AppOtherShipmentBill apb = new AppOtherShipmentBill();
			OtherShipmentBill pb = new OtherShipmentBill();
			pb = apb.getOtherShipmentBillByID(id);

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

			AppTakeService appts = new AppTakeService();
			List<TakeTicket> ticketlist = appts.getTakeTicketByBillno(id);
			
			if (appts.isNotAuditTakeTicket(ticketlist)) {
				String result = "databases.record.taketicket.noaudit";
				request.setAttribute("result", result);
				return new ActionForward("/sys/lockrecordclose.jsp");
			}

			//Session 
			//
			
			
			
			FeeWasteBook fwb =new FeeWasteBook();
			fwb.setId(Long.valueOf(MakeCode.getExcIDByRandomTableName(
						"fee_waste_book", 0, "")));
			fwb.setCid(pb.getCid());
			fwb.setCname(pb.getCname());
//			fwb.setFeedept(pb.getShipmentdept());
//			fwb.setFeeid(pb.getUserid());
			fwb.setBillno(pb.getId());
			fwb.setMemo("其它出库生成费用台帐");
			fwb.setCycleinsum(pb.getTotalsum());
			fwb.setCycleoutsum(0d);
			fwb.setRecorddate(DateUtil.StringToDatetime(DateUtil.getCurrentDateTime()));
			AppFeeWasteBook apfwb = new AppFeeWasteBook();
			apfwb.addFeeWasteBook(fwb);

//			apb.updIsEndCase(id, userid, 1);
			
			request.setAttribute("result", "databases.audit.success");
//			DBUserLog.addUserLog(userid, "结案其它出库,编号：" + id);
			
			return mapping.findForward("audit");
		} catch (Exception e) {
			
			e.printStackTrace();
		} finally {
			//
		}
		return new ActionForward(mapping.getInput());
	}

}
