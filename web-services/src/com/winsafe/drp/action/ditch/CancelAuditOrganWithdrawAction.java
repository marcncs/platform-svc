package com.winsafe.drp.action.ditch;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppOrganWithdraw;
import com.winsafe.drp.dao.AppTakeService;
import com.winsafe.drp.dao.AppTakeTicket;
import com.winsafe.drp.dao.AppTakeTicketIdcode;
import com.winsafe.drp.dao.OrganWithdraw;
import com.winsafe.drp.dao.TakeTicket;
import com.winsafe.drp.util.DBUserLog;

/**
 * @author : jerry
 * @version : 2009-8-24 下午05:29:32 www.winsafe.cn
 */
public class CancelAuditOrganWithdrawAction extends BaseAction {
	private static Logger logger = Logger.getLogger(CancelAuditOrganWithdrawAction.class);
	private AppTakeService appts = new AppTakeService();
	private AppTakeTicketIdcode appTti = new AppTakeTicketIdcode();
	private AppTakeTicket att = new AppTakeTicket();
	
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		initdata(request);
		try {
			String id = request.getParameter("ID");
			AppOrganWithdraw appAMA = new AppOrganWithdraw();
			OrganWithdraw organWithdraw = appAMA.getOrganWithdrawByID(id);
			// 单据是否存在
			if(organWithdraw == null){
				request.setAttribute("result", "databases.record.delete");
				return new ActionForward("/sys/lockrecordclose.jsp");
			}
			// 单据是否作废
			if (organWithdraw.getIsblankout() == 1) {
				String result = "databases.record.blankoutnooperator";
				request.setAttribute("result", result);
				return new ActionForward("/sys/lockrecordclose.jsp");
			}
			// 单据是否检货出库
			TakeTicket tt = att.getTakeTicket(id);
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
			
			//取消复核删除相关单据
			appts.deleteTake(tt);			

			organWithdraw.setTtid("");
			organWithdraw.setIsaudit(0);
			organWithdraw.setAuditid(null);
			organWithdraw.setAuditdate(null);
			appAMA.update(organWithdraw);
			DBUserLog.addUserLog(userid, 4, "渠道管理>>取消复核渠道退货,编号：" + id);
			request.setAttribute("result", "databases.cancel.success");

			return mapping.findForward("success");

		} catch (Exception ex) {
			logger.error("",ex);
			throw ex;
		}
	}
}
