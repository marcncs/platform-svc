package com.winsafe.drp.action.warehouse;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppStockAlterMove;
import com.winsafe.drp.dao.AppTakeService;
import com.winsafe.drp.dao.AppTakeTicket;
import com.winsafe.drp.dao.AppTakeTicketIdcode;
import com.winsafe.drp.dao.StockAlterMove;
import com.winsafe.drp.dao.TakeTicket;
import com.winsafe.drp.util.DBUserLog;

public class CancelAuditStockAlterMoveAction extends BaseAction {
	private static Logger logger = Logger.getLogger(CancelAuditStockAlterMoveAction.class);
	private AppStockAlterMove api = new AppStockAlterMove();
	private AppTakeTicket att = new AppTakeTicket();
	private AppTakeService appts = new AppTakeService();
	private AppTakeTicketIdcode appTti = new AppTakeTicketIdcode();
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// 初始化
		initdata(request);

		try {
			String smid = request.getParameter("SMID");
			StockAlterMove pi = api.getStockAlterMoveByID(smid);
			// 单据是否存在
			if(pi == null){
				request.setAttribute("result", "databases.record.delete");
				return new ActionForward("/sys/lockrecordclose.jsp");
			}
			// 单据是否复核
			if (pi.getIsaudit() == 0) {
				request.setAttribute("result", "databases.record.already");
				return new ActionForward("/sys/lockrecordclose.jsp");
			}
			// 单据是否作废
			if (pi.getIsblankout() == 1) {
				request.setAttribute("result",
						"databases.record.blankoutnooperator");
				return new ActionForward("/sys/lockrecordclose.jsp");
			}
			// 单据是否复核出库
			if (pi.getIsshipment() == 1) {
				request.setAttribute("result", "databases.record.already");
				return new ActionForward("/sys/lockrecordclose.jsp");
			}

			TakeTicket tt = att.getTakeTicket(smid);
			if (tt != null && tt.getIsaudit() == 1) {
				request.setAttribute("result", "databases.record.already");
				return new ActionForward("/sys/lockrecordclose.jsp");
			}
			// 判断单据是否存在条码
			if(tt != null ){
				List list = appTti.getTakeTicketIdcodeByttid(tt.getId());
				if(list != null && list.size()>0){
					request.setAttribute("result", "此单据已上传条码,不能操作");
					return new ActionForward("/sys/lockrecordclose2.jsp");
				}
			}
			// 删除检货出库单据
			appts.deleteTake(tt);
			// 订购单还原为未复核
			pi.setTtid("");
			pi.setAuditdate(null);
			pi.setAuditid(null);
			pi.setIsaudit(0);
			api.updstockAlterMove(pi);

			request.setAttribute("result", "databases.cancel.success");
			DBUserLog.addUserLog(request, "取消复核订购单" + smid);

			return mapping.findForward("success");
		} catch (Exception e) {
			logger.error("", e);
			throw e;
		}
	}

}
