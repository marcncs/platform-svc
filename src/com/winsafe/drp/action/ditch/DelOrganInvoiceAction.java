package com.winsafe.drp.action.ditch;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppOrganInvoice;
import com.winsafe.drp.dao.AppOrganInvoiceDetail;
import com.winsafe.drp.dao.OrganInvoice;
import com.winsafe.drp.util.DBUserLog;

/**
 * @author : jerry
 * @version : 2009-8-24 下午03:40:40 www.winsafe.cn
 */
public class DelOrganInvoiceAction extends BaseAction {

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		super.initdata(request);
		try {
			String id = request.getParameter("ID");
			AppOrganInvoice appAMA = new AppOrganInvoice();
			AppOrganInvoiceDetail appAMAD = new AppOrganInvoiceDetail();
			OrganInvoice alterma = appAMA.getOrganInvoiceByID(Integer
					.valueOf(id));
			if (alterma.getIsaudit() == 1) {
				String result = "databases.record.nodel";
				request.setAttribute("result", result);
				return new ActionForward("/sys/lockrecordclose.jsp");
			}

			appAMAD.deleteByPIID(Integer.valueOf(id));
			appAMA.delete(alterma);

			DBUserLog.addUserLog(userid, 4, "渠道管理>>删除订购申请,编号：" + id, alterma);
			request.setAttribute("result", "databases.del.success");

		} catch (Exception ex) {
			ex.printStackTrace();
			request.setAttribute("result", "databases.del.fail");
		}
		return mapping.findForward("success");
	}
}
