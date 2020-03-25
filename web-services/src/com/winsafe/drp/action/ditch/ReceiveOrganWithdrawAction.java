package com.winsafe.drp.action.ditch;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppOrganWithdraw;
import com.winsafe.drp.dao.OrganWithdraw;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DateUtil;

/**
 * @author : jerry
 * @version : 2009-8-24 下午05:28:53 www.winsafe.cn
 */
public class ReceiveOrganWithdrawAction extends BaseAction {

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		super.initdata(request);
		try {
			UsersBean users = UserManager.getUser(request);
			Integer userid = users.getUserid();
			String id = request.getParameter("ID");
			AppOrganWithdraw appAMA = new AppOrganWithdraw();
			OrganWithdraw ow = appAMA.getOrganWithdrawByID(id);

			if (ow.getIsaffirm() == 0) {
				String result = "退货单未确认!不能进行此操作!";
				request.setAttribute("result", result);
				return new ActionForward("/sys/lockrecordclose2.jsp");
			}

			ow.setIscomplete(1);
			ow.setReceiveid(userid);
			ow.setReceivedate(DateUtil.getCurrentDate());
			appAMA.update(ow);

			request.setAttribute("result", "databases.operator.success");
			DBUserLog.addUserLog(userid, 4, "渠道管理>>签收渠道退货,编号：" + id);

			return mapping.findForward("success");

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return super.execute(mapping, form, request, response);
	}
}
