package com.winsafe.drp.action.sys;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppPaymentMode;
import com.winsafe.drp.dao.PaymentMode;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.util.DBUserLog;

public class UpdPaymentModeAction extends Action {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		try {
			Integer id = Integer.valueOf(request.getParameter("id"));
			AppPaymentMode air = new AppPaymentMode();
			PaymentMode w = air.getPaymentModeByID(id);
			PaymentMode oldw = (PaymentMode)BeanUtils.cloneBean(w);
			w.setId(id);
			w.setIrid(Integer.valueOf(request.getParameter("irid")));
			w.setPaymentname(request.getParameter("paymentname"));
			w.setIntegralrate(Double.valueOf(request.getParameter("integralrate")));
			air.updPaymentMode(w);

			request.setAttribute("result", "databases.upd.success");
			UsersBean users = UserManager.getUser(request);
			Integer userid = users.getUserid();
			DBUserLog.addUserLog(userid, 11,"基础设置>>修改收款方式,编号:"+w.getId(), oldw, w);

			return mapping.findForward("updResult");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		}
		return mapping.findForward("updResult");
	}
}
