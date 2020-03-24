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
import com.winsafe.drp.dao.AppIncomeLog;
import com.winsafe.drp.dao.AppReceivable;
import com.winsafe.drp.dao.IncomeLog;
import com.winsafe.drp.dao.IncomeLogForm;
import com.winsafe.drp.dao.Receivable;
import com.winsafe.drp.dao.ReceivableForm;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.Internation;

public class ToDestoryIncomeLogAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String rids = request.getParameter("rids");

		super.initdata(request);super.initdata(request);try{
			String roid = (String) request.getSession().getAttribute("roid");
			AppIncomeLog apil = new AppIncomeLog();
			List<IncomeLog> illist = apil.getIncomeLogByRoid(roid);
			AppCashBank appcb = new AppCashBank();
			List loglist = new ArrayList();
			for (IncomeLog il : illist) {
				IncomeLogForm ilf = new IncomeLogForm();
				ilf.setId(il.getId());
				ilf.setRoid(il.getRoid());
				ilf.setDrawee(il.getDrawee());
				ilf.setFundattachname(appcb.getCashBankById(il.getFundattach())
						.getCbname());
				ilf.setPaymentmodename(Internation.getStringByPayPositionDB(il.getPaymentmode()));
				ilf.setIncomesum(il.getIncomesum());
				ilf.setAlreadyspend(il.getAlreadyspend());
				ilf.setBillnum(il.getBillnum());
				ilf.setMakedate(DateUtil.formatDate(il.getMakedate()));
				loglist.add(ilf);
			}

			rids = rids.substring(0, rids.lastIndexOf(","));			
			AppReceivable ara = new AppReceivable();
			List aprv = ara.getTransIncomeLogById(rids);
			ArrayList als = new ArrayList();
			Receivable rd = null;
			for (int i = 0; i < aprv.size(); i++) {
				rd = (Receivable) aprv.get(i);
				ReceivableForm rdf = new ReceivableForm();
				rdf.setId(rd.getId());
				rdf.setRoid(rd.getRoid());
				rdf.setReceivablesum(rd.getReceivablesum());
				rdf.setPaymentmode(rd.getPaymentmode());
				rdf.setPaymentmodename(Internation.getStringByPayPositionDB(rd.getPaymentmode()));
				rdf.setBillno(rd.getBillno());
				rdf.setReceivabledescribe(rd.getReceivabledescribe());
				rdf.setAlreadysum(rd.getReceivablesum() - rd.getAlreadysum());
				als.add(rdf);
			}

			request.setAttribute("als", als);
			request.setAttribute("roid", roid);
			request.setAttribute("loglist", loglist);
			return mapping.findForward("totrans");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
}
