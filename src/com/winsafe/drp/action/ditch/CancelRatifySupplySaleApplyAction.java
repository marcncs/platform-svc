package com.winsafe.drp.action.ditch;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppSupplySaleApply;
import com.winsafe.drp.dao.SupplySaleApply;
import com.winsafe.drp.util.DBUserLog;

/**
 * @author : jerry
 * @version : 2009-8-24 下午06:23:34 www.winsafe.cn
 */
public class CancelRatifySupplySaleApplyAction extends BaseAction {

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		super.initdata(request);
		try {
			String id = request.getParameter("ID");
			AppSupplySaleApply appAMA = new AppSupplySaleApply();
			SupplySaleApply alterma = appAMA.getSupplySaleApplyByID(id);

			alterma.setIsratify(0);
			alterma.setRatifyid(null);
			alterma.setRatifydate(null);
			appAMA.update(alterma);

			request.setAttribute("result", "databases.cancel.success");
			DBUserLog.addUserLog(userid, 4, "渠道管理>>取消批准代销申请,编号：" + id);

			return mapping.findForward("success");

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return super.execute(mapping, form, request, response);
	}
}
