package com.winsafe.drp.action.finance;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppCashBank;
import com.winsafe.drp.dao.AppPayable;
import com.winsafe.drp.dao.AppPaymentLog;
import com.winsafe.drp.dao.Payable;
import com.winsafe.drp.dao.PayableForm;
import com.winsafe.drp.dao.PaymentLog;
import com.winsafe.drp.dao.PaymentLogForm;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.Internation;

public class ToDestoryPaymentLogAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String rids = request.getParameter("rids");

		super.initdata(request);super.initdata(request);try{
			String poid = (String) request.getSession().getAttribute("poid");
			AppPaymentLog apil = new AppPaymentLog();
			List<PaymentLog> illist = apil.getPaymentLogByPoid(poid);
			AppCashBank appcb = new AppCashBank();
			List loglist = new ArrayList();
			for (PaymentLog il : illist) {
				PaymentLogForm ilf = new PaymentLogForm();
				ilf.setId(il.getId());
				ilf.setPoid(il.getPoid());
				ilf.setPayee(il.getPayee());
				ilf.setFundsrcname(appcb.getCashBankById(il.getFundsrc())
						.getCbname());
				ilf.setPaymodename(Internation.getStringByKeyPosition(
						"PayMode", request, il.getPaymode(),
						"global.sys.SystemResource"));
				ilf.setPaysum(il.getPaysum());
				ilf.setAlreadyspend(il.getAlreadyspend());
				ilf.setBillnum(il.getBillnum());
				ilf.setMakedate(DateUtil.formatDate(il.getMakedate()));
				loglist.add(ilf);
			}

			rids = rids.substring(0, rids.lastIndexOf(","));			
			AppPayable ara = new AppPayable();
			List aprv = ara.getTransPaymentLogById(rids);
			ArrayList als = new ArrayList();
			Payable rd = null;
			for (int i = 0; i < aprv.size(); i++) {
				rd = (Payable) aprv.get(i);
				PayableForm rdf = new PayableForm();
				rdf.setId(rd.getId());
				rdf.setPoid(rd.getPoid());
				rdf.setPayablesum(rd.getPayablesum());
				rdf.setPaymode(rd.getPaymode());
				rdf.setPaymodename(Internation.getStringByKeyPosition(
						"PayMode", request, rd.getPaymode(),
						"global.sys.SystemResource"));
				rdf.setBillno(rd.getBillno());
				rdf.setPayabledescribe(rd.getPayabledescribe());
				rdf.setAlreadysum(rd.getPayablesum() - rd.getAlreadysum());
				als.add(rdf);
			}

			request.setAttribute("als", als);			
			request.setAttribute("loglist", loglist);
			return mapping.findForward("totrans");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
}
