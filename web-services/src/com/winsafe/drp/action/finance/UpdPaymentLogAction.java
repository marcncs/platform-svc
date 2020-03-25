package com.winsafe.drp.action.finance;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppPaymentLog;
import com.winsafe.drp.dao.PaymentLog;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.RequestTool;

public class UpdPaymentLogAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		super.initdata(request);
		try {
			String id = request.getParameter("id");

			AppPaymentLog apl = new AppPaymentLog();
			PaymentLog pl = apl.getPaymentLogByID(id);
			PaymentLog oldW = (PaymentLog)BeanUtils.cloneBean(pl);

			String poid = request.getParameter("poid");
			String payee = request.getParameter("payee");
			String paypurpose = request.getParameter("paypurpose");
			//Integer paymode = Integer.valueOf(request.getParameter("PayMode"));
			int fundsrc = RequestTool.getInt(request, "fundsrc");
			double paysum = RequestTool.getDouble(request,"paysum");			
			String billnum = request.getParameter("billnum");
			String remark = request.getParameter("remark");
			
			pl.setPoid(poid);
			pl.setPayee(payee);
			pl.setPaypurpose(paypurpose);			
			pl.setPaysum(paysum);
			pl.setFundsrc(fundsrc);			
			pl.setBillnum(billnum);
			pl.setVoucher(request.getParameter("voucher"));
			pl.setRemark(remark);

			AppPaymentLog ail = new AppPaymentLog();
			ail.updPaymentLog(pl);

			request.setAttribute("result", "databases.upd.success");

			DBUserLog.addUserLog(userid,9,"付款管理>>修改付款记录,编号："+id,oldW,pl); 
			
			return mapping.findForward("updresult");
		} catch (Exception e) {
			
			e.printStackTrace();
		}

		return new ActionForward(mapping.getInput());
	}
}
