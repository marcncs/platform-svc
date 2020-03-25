package com.winsafe.drp.action.ditch;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppOrganInvoice;
import com.winsafe.drp.dao.AppOrganInvoiceDetail;
import com.winsafe.drp.dao.AppStockAlterMove;
import com.winsafe.drp.dao.OrganInvoice;
import com.winsafe.drp.dao.OrganInvoiceDetail;
import com.winsafe.drp.util.DBUserLog;

/**
 * @author : jerry
 * @version : 2009-8-24 下午05:29:32 www.winsafe.cn
 */
public class CancelAuditOrganInvoiceAction extends BaseAction {
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

			AppOrganInvoiceDetail appoid = new AppOrganInvoiceDetail();
			List<OrganInvoiceDetail> list = appoid
					.getOrganInvoiceDetailByPIID(id);
			AppStockAlterMove appsam = new AppStockAlterMove();
			for (OrganInvoiceDetail oid : list) {
				appsam.updIsMakeTicket(oid.getPoid(), 0);
			}

			alterma.setIsaudit(0);
			alterma.setAuditid(null);
			alterma.setAuditdate(null);
			appAMA.update(alterma);
			DBUserLog.addUserLog(userid, 4, "渠道管理>>取消复核渠道发票,编号：" + id);
			request.setAttribute("result", "databases.cancel.success");

			return mapping.findForward("success");

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return super.execute(mapping, form, request, response);
	}
}
