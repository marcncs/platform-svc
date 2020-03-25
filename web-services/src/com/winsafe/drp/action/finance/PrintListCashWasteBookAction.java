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
import com.winsafe.drp.dao.AppCashWasteBook;
import com.winsafe.drp.dao.CashBank;
import com.winsafe.drp.dao.CashWasteBook;
import com.winsafe.drp.dao.CashWasteBookForm;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.DbUtil;

public class PrintListCashWasteBookAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		super.initdata(request);
		try {
			AppCashBank appcb = new AppCashBank();
			List cblist = appcb.getAllCashBankByOID(users.getMakeorganid());
			StringBuffer cbid = new StringBuffer();
			cbid.append("0");
			for (int i = 0; i < cblist.size(); i++) {
				CashBank cb = (CashBank) cblist.get(i);
				cbid.append(",").append(cb.getId());
			}

			String Condition = " cwb.cbid in (" + cbid.toString() + ") ";
			String[] tablename = { "CashWasteBook" };
			String whereSql = getWhereSql2(tablename);

			String timeCondition = getTimeCondition("RecordDate");
			whereSql = whereSql + timeCondition + Condition;
			whereSql = DbUtil.getWhereSql(whereSql);

			AppCashWasteBook ail = new AppCashWasteBook();

			List<CashWasteBook> slls = ail.searchCashWasteBook(whereSql);
			ArrayList arls = new ArrayList();

			for (CashWasteBook cwb : slls) {
				CashWasteBookForm ilf = new CashWasteBookForm();
				ilf.setId(cwb.getId());
				ilf.setCbid(cwb.getCbid());
				ilf.setCbidname(appcb.getCashBankById(cwb.getCbid())
						.getCbname());
				ilf.setBillno(cwb.getBillno());
				ilf.setMemo(cwb.getMemo());
				ilf.setCyclefirstsum(cwb.getCyclefirstsum());
				ilf.setCycleinsum(cwb.getCycleinsum());
				ilf.setCycleoutsum(cwb.getCycleoutsum());
				ilf.setCyclebalancesum(cwb.getCyclebalancesum());
				ilf.setRecorddate(DateUtil.formatDate(cwb.getRecorddate()));
				arls.add(ilf);
			}
			String CBID = request.getParameter("CBID");
			String cbname ="";
			if(!CBID.equals("")){
				cbname  = appcb.getCBName(Integer.valueOf(CBID));
			}
			
			request.setAttribute("cbname", cbname);
			request.setAttribute("arls", arls);

			DBUserLog.addUserLog(userid, 9, "资金台账>>打印资金台帐");
			return mapping.findForward("toprint");
		} catch (Exception e) {
			e.printStackTrace();
			String result = "databases.settlement.noexist";
			request.setAttribute("result", result);
			return new ActionForward("/sys/lockrecordclose.jsp");
		}
	}
}
