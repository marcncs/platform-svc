package com.winsafe.drp.action.ditch;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppOrganInvoice;
import com.winsafe.drp.dao.OrganInvoice;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DateUtil;

/**
 * @author : jerry
 * @version : 2009-8-24 下午05:28:53 www.winsafe.cn
 */
public class AuditOrganInvoiceAction extends BaseAction {

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		super.initdata(request);
		try {
			String id = request.getParameter("ID");
			AppOrganInvoice appAMA = new AppOrganInvoice();
			OrganInvoice alterma = appAMA.getOrganInvoiceByID(Integer
					.valueOf(id));

			alterma.setIsaudit(1);
			alterma.setAuditid(userid);
			alterma.setAuditdate(DateUtil.getCurrentDate());
			appAMA.update(alterma);

			request.setAttribute("result", "databases.audit.success");
			DBUserLog.addUserLog(userid, 4, "渠道管理>>复核渠道发票,编号：" + id);

			return mapping.findForward("success");

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return super.execute(mapping, form, request, response);
	}
}
