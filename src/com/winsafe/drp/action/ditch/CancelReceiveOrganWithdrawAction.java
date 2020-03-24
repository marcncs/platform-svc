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
 * @version : 2009-8-24 下午05:28:53 www.winsafe.cn
 */
public class CancelReceiveOrganWithdrawAction extends BaseAction {

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		super.initdata(request);
		try {
			String id = request.getParameter("ID");
			AppOrganWithdraw appAMA = new AppOrganWithdraw();
			OrganWithdraw ow = appAMA.getOrganWithdrawByID(id);
			if (ow.getIsaffirm() == 0) {
				String result = "databases.operator.fail";
				request.setAttribute("result", result);
				return new ActionForward("/sys/lockrecordclose.jsp");
			}

			ow.setIscomplete(0);
			ow.setReceiveid(null);
			ow.setReceivedate(null);
			appAMA.update(ow);

			request.setAttribute("result", "databases.operator.success");
			DBUserLog.addUserLog(userid, 4, "渠道管理>>取消签收渠道退货,编号：" + id);

			return mapping.findForward("success");

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return super.execute(mapping, form, request, response);
	}
}
