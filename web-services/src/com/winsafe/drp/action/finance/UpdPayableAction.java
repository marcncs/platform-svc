package com.winsafe.drp.action.finance;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppPayable;
import com.winsafe.drp.dao.Payable;
import com.winsafe.drp.util.DBUserLog;

public class UpdPayableAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		super.initdata(request);
		try {
			String id = request.getParameter("id");
			AppPayable apa = new AppPayable();
			Payable pa = apa.getPayableByID(id);
			Payable oldW = (Payable)BeanUtils.cloneBean(pa);
			pa
					.setPayablesum(Double.valueOf(request
							.getParameter("payablesum")));
			pa.setBillno(request.getParameter("billno"));
			pa.setPayabledescribe(request.getParameter("payabledescribe"));
			pa.setPaymode(Integer.valueOf(request.getParameter("paymode")));

			apa.updPayable(pa);

			request.setAttribute("result", "databases.upd.success");

			DBUserLog.addUserLog(userid,9, "应付款管理>>修改应付款,编号："+id,oldW, pa);

			return mapping.findForward("updresult");
		} catch (Exception e) {

			e.printStackTrace();
		}

		return new ActionForward(mapping.getInput());
	}
}
