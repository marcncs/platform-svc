package com.winsafe.drp.action.ditch;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppOrganWithdraw;
import com.winsafe.drp.dao.OrganWithdraw;
import com.winsafe.drp.util.DBUserLog;

/**
 * @author : jerry
 * @version : 2009-8-24 下午06:23:34 www.winsafe.cn
 */
public class CancelRatifyOrganWithdrawAction extends BaseAction {

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		super.initdata(request);
		try {
			String id = request.getParameter("ID");
			AppOrganWithdraw appAMA = new AppOrganWithdraw();
			OrganWithdraw ow = appAMA.getOrganWithdrawByID(id);

			if (ow.getIsaffirm() == 1) {
				request.setAttribute("result", "退货单已确认!不能取消批准!请取消确认后!再进行此操作!");
				return new ActionForward("/sys/lockrecordclose2.jsp");
			}

			ow.setIsratify(0);
			ow.setRatifyid(null);
			ow.setRatifydate(null);
			appAMA.update(ow);

			request.setAttribute("result", "databases.cancel.success");
			DBUserLog.addUserLog(userid, 4, "渠道管理>>取消批准渠道退货,编号：" + id);

			return mapping.findForward("success");

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return super.execute(mapping, form, request, response);
	}
}
