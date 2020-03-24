package com.winsafe.drp.action.warehouse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.hibernate.StaleObjectStateException;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppBarcodeInventory;
import com.winsafe.drp.dao.BarcodeInventory;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.server.AuditBarcodeInventoryService;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.entity.HibernateUtil;

public class AuditBarcodeInventoryAction extends BaseAction {
	private AuditBarcodeInventoryService abis = new AuditBarcodeInventoryService();
	private Logger logger = Logger.getLogger(AuditBarcodeInventoryAction.class);

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		UsersBean users = UserManager.getUser(request);
		super.initdata(request);
		String id = request.getParameter("ID");
		try {
			AppBarcodeInventory abi = new AppBarcodeInventory();
			BarcodeInventory bi = abi.getBarcodeInventoryByID(id);

			// 如果已经作废
			if (bi.getIsblankout() == 1) {
				request.setAttribute("result", "databases.record.blankoutnooperator");
				return new ActionForward("/sys/lockrecordclose.jsp");
			}
			// 如果已经复核
			if (bi.getIsaudit() == 1) {
				request.setAttribute("result", "databases.record.audit");
				return new ActionForward("/sys/lockrecordclose.jsp");
			}
			
			abis.auditBarcodeInventory(bi, users);
			
			request.setAttribute("result", "databases.audit.success");
			DBUserLog.addUserLog(request, "编号：" + id);
			
			HibernateUtil.commitTransaction();
			return mapping.findForward("audit");
		} catch (Exception e) {
			logger.error("", e);
			HibernateUtil.rollbackTransaction();
			if(e instanceof StaleObjectStateException) {
				request.setAttribute("result", "单据已复核");
				return new ActionForward("/sys/lockrecordclose2.jsp");
			}
		}
		return new ActionForward(mapping.getInput());
	}

}