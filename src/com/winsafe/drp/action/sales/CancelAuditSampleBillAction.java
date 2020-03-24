package com.winsafe.drp.action.sales;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppSampleBill;
import com.winsafe.drp.dao.AppTakeBill;
import com.winsafe.drp.dao.AppTakeService;
import com.winsafe.drp.dao.AppTakeTicket;
import com.winsafe.drp.dao.SampleBill;
import com.winsafe.drp.dao.TakeBill;
import com.winsafe.drp.dao.TakeTicket;
import com.winsafe.drp.server.TakeBillService;
import com.winsafe.drp.util.DBUserLog;

public class CancelAuditSampleBillAction extends BaseAction {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		initdata(request);

		try {
			String sbid = request.getParameter("SBID");
			AppSampleBill asb = new AppSampleBill();
			SampleBill sb = asb.findByID(sbid);
			//如果已回收,不能取消复核
			if (sb.getIsrecycle() == 1) {
				String result = "databases.record.noreceive";
				request.setAttribute("result", result);
				return new ActionForward("/sys/lockrecordclose.jsp");// ;mapping.findForward("lock");
			}


			sb.setAuditid(null);
			sb.setIsaudit(0);
			sb.setAuditdate(null);
			//更新样品单状态为未复核
			asb.update(sb);
			//删除对应的检货出库内容
			AppTakeBill api = new AppTakeBill(); 
			TakeBill tb = api.getTakeBillByID(sb.getId());	
			TakeBillService tbs = new TakeBillService(tb, users);
			tbs.cancelAuditDeal();
			//查出对应的检货出库单并删除
			AppTakeService appTakeService = new AppTakeService();
			List<TakeTicket> ticketlist = appTakeService.getTakeTicketByBillno(sb.getId());		
			//判断小票是否被复核,复核的小票不能取消
			if ( appTakeService.isAuditTakeTicket(ticketlist) ){
				request.setAttribute("result", "databases.record.already");
				return new ActionForward("/sys/lockrecordclose.jsp");
			}
			//删除小票
			appTakeService.deleteTake(ticketlist);
			
			request.setAttribute("result", "databases.cancel.success");
			DBUserLog.addUserLog(userid, "取消复核样品记录,单据编号:" + sbid);

			return mapping.findForward("noaudit");
		} catch (Exception e) {

			e.printStackTrace();
		} finally {

		}
		return new ActionForward(mapping.getInput());
	}

}
