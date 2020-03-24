package com.winsafe.drp.action.finance;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppPayable;
import com.winsafe.drp.dao.Payable;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.MakeCode;

public class AddPayableAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		super.initdata(request);
		try {
			Payable pa = new Payable();
			String poid = (String) request.getSession().getAttribute("poid");
			String orgid = (String) request.getSession().getAttribute("orgid");
			String id = MakeCode.getExcIDByRandomTableName("payable", 2, "");
			pa.setId(id);
			pa.setPoid(poid);
			pa
					.setPayablesum(Double.valueOf(request
							.getParameter("payablesum")));
			pa.setBillno(request.getParameter("billno"));
			pa.setPayabledescribe(request.getParameter("payabledescribe"));
			pa.setPaymode(Integer.valueOf(request.getParameter("paymode")));
			pa.setAwakedate(DateUtil.getCurrentDate());
			pa.setAlreadysum(0d);
			pa.setIsclose(0);
			pa.setMakeorganid(orgid);
			pa.setMakedeptid(users.getMakedeptid());
			pa.setMakeid(userid);
			pa.setMakedate(DateUtil.getCurrentDate());

			AppPayable ar = new AppPayable();
			ar.addPayable(pa);

			request.setAttribute("result", "databases.add.success");

			DBUserLog.addUserLog(userid, 9, "应付款结算单>>新增应付款记录,编号：" + id);
			return mapping.findForward("addresult");
		} catch (Exception e) {
			request.setAttribute("result", "databases.add.fail");
			e.printStackTrace();
		}
		return mapping.getInputForward();
	}
}
