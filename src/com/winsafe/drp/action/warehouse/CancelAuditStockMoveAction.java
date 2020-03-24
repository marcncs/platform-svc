package com.winsafe.drp.action.warehouse;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppStockMove;
import com.winsafe.drp.dao.AppTakeService;
import com.winsafe.drp.dao.AppTakeTicket;
import com.winsafe.drp.dao.AppTakeTicketDetail;
import com.winsafe.drp.dao.AppTakeTicketIdcode;
import com.winsafe.drp.dao.StockMove;
import com.winsafe.drp.dao.TakeTicket;
import com.winsafe.drp.dao.TakeTicketDetail;
import com.winsafe.drp.util.DBUserLog;

public class CancelAuditStockMoveAction extends BaseAction {
	private Logger logger = Logger.getLogger(CancelAuditStockMoveAction.class);
	private AppTakeService appts = new AppTakeService();
	private AppStockMove api = new AppStockMove();
	private AppTakeTicketIdcode appTti = new AppTakeTicketIdcode();
	private AppTakeTicket att = new AppTakeTicket();
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		initdata(request);
		
		try{
			String smid = request.getParameter("SMID");
			StockMove pi = api.getStockMoveByID(smid);
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
				request.setAttribute("result", "databases.record.blankoutnooperator");
				return new ActionForward("/sys/lockrecordclose.jsp");
			}
			// 单据是否检货出库
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
			
			appts.deleteTake(tt);			

			pi.setTtid("");
			pi.setIsaudit(0);
			pi.setAuditdate(null);
			pi.setAuditid(null);
			api.updstockMove(pi);

			request.setAttribute("result", "databases.cancel.success");
			DBUserLog.addUserLog(request, "取消复核编号:"+smid);

			return mapping.findForward("noaudit");
		} catch (Exception e) {
			logger.error("",e);
			throw e;
		}
	}

}
