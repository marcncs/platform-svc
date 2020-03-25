package com.winsafe.drp.action.finance;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppPayable;
import com.winsafe.drp.dao.AppPaymentLog;
import com.winsafe.drp.dao.AppPaymentLogDetail;
import com.winsafe.drp.dao.PaymentLog;
import com.winsafe.drp.dao.PaymentLogDetail;
import com.winsafe.drp.util.DBUserLog;

public class DelPaymentLogAction extends BaseAction {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		super.initdata(request);
		try {
			String plid = request.getParameter("PLID");
			AppPaymentLog apl = new AppPaymentLog();
			AppPaymentLogDetail appld = new AppPaymentLogDetail();
			PaymentLog pl = apl.getPaymentLogByID(plid);

			if (pl.getIsaudit() == 1) {
				String result = "databases.record.lock";
				request.setAttribute("result", result);
				return new ActionForward("/sys/lockrecordclose.jsp");
			}
			
			List<PaymentLogDetail> ildlist = appld
					.getPaymentLogDetailByPLID(plid);
			AppPayable apr = new AppPayable();
			for (PaymentLogDetail ild : ildlist) {
				apr.backAlreadysumById(ild.getThispayablesum(), ild.getPid());
			}

			apl.delPaymentLog(plid);
			appld.delPaymentLogDetailByPLID(plid);

			request.setAttribute("result", "databases.del.success");
			DBUserLog.addUserLog(userid, 9, "应付款管理>>删除付款记录,编号：" + plid, pl);

			return mapping.findForward("del");
		} catch (Exception e) {

			e.printStackTrace();
		}
		return null;
	}

}
