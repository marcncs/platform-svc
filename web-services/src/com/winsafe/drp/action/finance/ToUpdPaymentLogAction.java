package com.winsafe.drp.action.finance;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppCashBank;
import com.winsafe.drp.dao.AppPaymentLog;
import com.winsafe.drp.dao.AppPaymentLogDetail;
import com.winsafe.drp.dao.PaymentLog;
import com.winsafe.drp.dao.PaymentLogForm;
import com.winsafe.hbm.util.Internation;

public class ToUpdPaymentLogAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String id = request.getParameter("ID");

		super.initdata(request);super.initdata(request);try{
			AppPaymentLog apl = new AppPaymentLog();
			AppPaymentLogDetail appld = new AppPaymentLogDetail();
			PaymentLog pl = apl.getPaymentLogByID(id);

			if (pl.getIsaudit() == 1) {
				String result = "databases.record.lock";
				request.setAttribute("result", result);
				return new ActionForward("/sys/lockrecordclose.jsp");
			}

			if (pl.getPaymode() != 3) {
				String result = "databases.incomelog.yushou";
				request.setAttribute("result", result);
				return new ActionForward("/sys/lockrecordclose.jsp");
			}

			if (appld.getPaymentLogDetailByPLID(id).size() > 0) {
				String result = "databases.record.lock";
				request.setAttribute("result", result);
				return new ActionForward("/sys/lockrecordclose.jsp");
			}

			PaymentLogForm plf = new PaymentLogForm();
			plf.setId(pl.getId());
			plf.setPoid(pl.getPoid());
			plf.setPayee(pl.getPayee());
			plf.setPaypurpose(pl.getPaypurpose());
			plf.setPaymodename(Internation.getSelectTagByKeyAll("PayMode",
					request, "PayMode", String.valueOf(pl.getPaymode()), null));

			plf.setFundsrc(pl.getFundsrc());
			plf.setPaysum(pl.getPaysum());
			plf.setBillnum(pl.getBillnum());
			plf.setVoucher(pl.getVoucher());
			plf.setRemark(pl.getRemark());

			AppCashBank apcb = new AppCashBank();
			List cblist = apcb.getAllCashBank();

			request.setAttribute("cblist", cblist);
			request.setAttribute("plf", plf);
			return mapping.findForward("toupd");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
}
