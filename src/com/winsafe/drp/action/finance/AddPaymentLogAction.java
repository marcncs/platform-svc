package com.winsafe.drp.action.finance;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppPaymentLog;
import com.winsafe.drp.dao.PaymentLog;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.drp.util.DataValidate;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.MakeCode;

public class AddPaymentLogAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		super.initdata(request);
		try {
			String poid = request.getParameter("poid");
			String payee = request.getParameter("payee");
			String paypurpose = request.getParameter("paypurpose");
			//Integer paymode = Integer.valueOf(request.getParameter("PayMode"));
			Integer fundsrc = Integer.valueOf(request.getParameter("fundsrc"));
			String strpaysum = request.getParameter("paysum");

			String billnum = request.getParameter("billnum");
			String remark = request.getParameter("remark");

			PaymentLog pl = new PaymentLog();
			String plid = MakeCode.getExcIDByRandomTableName("payment_log", 2,"PL");
			pl.setId(plid);
			pl.setPoid(poid);
			pl.setPayee(payee);
			pl.setPaypurpose(paypurpose);
			pl.setFundsrc(fundsrc);
			pl.setPaymode(3);
			pl.setPaysum(DataValidate.IsDouble(strpaysum) ? Double
					.valueOf(strpaysum): 0d);
			pl.setAlreadyspend(0d);
			pl.setBillnum(billnum);
			pl.setVoucher(request.getParameter("voucher"));
			pl.setRemark(remark);
			pl.setMakeorganid(request.getParameter("orgid"));
			pl.setMakedeptid(users.getMakedeptid());
			pl.setMakeid(userid);
			pl.setIsaudit(0);
			pl.setIspay(0);
			pl.setMakedate(DateUtil.getCurrentDate());

			AppPaymentLog apl = new AppPaymentLog();
			apl.addPaymentLog(pl);

			request.setAttribute("result", "databases.add.success");

			DBUserLog.addUserLog(userid,9, "付款管理>>新增付款记录,编号："+plid);

			return mapping.findForward("addresult");
		} catch (Exception e) {
			request.setAttribute("result", "databases.add.fail");
			e.printStackTrace();
		}

		return new ActionForward(mapping.getInput());
	}
}
