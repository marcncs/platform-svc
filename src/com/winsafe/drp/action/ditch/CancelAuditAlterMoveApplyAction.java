package com.winsafe.drp.action.ditch;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AlterMoveApply;
import com.winsafe.drp.dao.AppAlterMoveApply;
import com.winsafe.drp.util.DBUserLog;

/**
 * @author : jerry
 * @version : 2009-8-24 下午05:29:32 www.winsafe.cn
 */
public class CancelAuditAlterMoveApplyAction extends BaseAction {
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		super.initdata(request);
		try {
			String id = request.getParameter("ID");
			AppAlterMoveApply appAMA = new AppAlterMoveApply();
			AlterMoveApply alterma = appAMA.getAlterMoveApplyByID(id);
			if (alterma.getIsratify() == 1) {
				String result = "对不起!该单据已经批准!不能取消复核!";
				request.setAttribute("result", result);
				return new ActionForward("/sys/lockrecordclose2.jsp");
			}

			if (alterma.getIsblankout() == 1) {
				String result = "databases.record.blankoutnooperator";
				request.setAttribute("result", result);
				return new ActionForward("/sys/lockrecordclose.jsp");
			}

			alterma.setIsaudit(0);
			alterma.setAuditid(null);
			alterma.setAuditdate(null);
			appAMA.updAlterMoveApply(alterma);
			DBUserLog.addUserLog(userid, 4, "渠道管理>>取消复核订购申请,编号：" + id);
			request.setAttribute("result", "databases.cancel.success");

			return mapping.findForward("success");

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return super.execute(mapping, form, request, response);
	}
}
