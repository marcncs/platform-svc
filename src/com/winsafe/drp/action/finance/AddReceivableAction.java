package com.winsafe.drp.action.finance;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppReceivable;
import com.winsafe.drp.dao.Receivable;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.MakeCode;

public class AddReceivableAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		super.initdata(request);
		try {

			String roid = (String) request.getSession().getAttribute("roid");
			String orgid = (String) request.getSession().getAttribute("orgid");
			Receivable r = new Receivable();
			r.setId(MakeCode.getExcIDByRandomTableName("receivable", 2, ""));
			r.setRoid(roid);
			r.setReceivablesum(Double.valueOf(request
					.getParameter("receivablesum")));
			r.setBillno(request.getParameter("billno"));
			r.setReceivabledescribe(request.getParameter("receivabledescribe"));
			r.setPaymentmode(Integer.valueOf(request
					.getParameter("paymentmode")));
			r.setAlreadysum(0d);
			r.setIsclose(0);
			r.setMakeorganid(orgid);
			r.setMakedeptid(users.getMakedeptid());
			r.setMakeid(userid);
			r.setMakedate(DateUtil.getCurrentDate());

			AppReceivable ar = new AppReceivable();
			ar.addReceivable(r);

			request.setAttribute("result", "databases.add.success");

			DBUserLog.addUserLog(userid,9, "收款管理>>新增应收款记录,编号："+r.getId());

			return mapping.findForward("addresult");
		} catch (Exception e) {
			request.setAttribute("result", "databases.add.fail");
			e.printStackTrace();
		}

		return mapping.getInputForward();
	}
}
