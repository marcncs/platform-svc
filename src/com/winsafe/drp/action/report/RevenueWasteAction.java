package com.winsafe.drp.action.report;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppReceivableObject;
import com.winsafe.drp.dao.ReceivableObject;
import com.winsafe.drp.dao.RevenueWasteForm;
import com.winsafe.drp.dao.ViewRevenueWaste;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DataFormat;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.DbUtil;

public class RevenueWasteAction extends BaseAction {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		super.initdata(request);
		try {
			int pagesize = 20;

			String visitorgan = "";
			if (users.getVisitorgan() != null
					&& users.getVisitorgan().length() > 0) {
				visitorgan = getAndVisitOrgan("makeorganid");
			}
			String Condition = " 1=1 " + visitorgan ;
			String[] tablename = { "Receivable" };
			String whereSql = getWhereSql(tablename);
			String timeCondition = getTimeCondition("MakeDate");
			whereSql = whereSql + timeCondition + Condition; 
			whereSql = DbUtil.getWhereSql(whereSql); 

			AppReceivableObject aro = new AppReceivableObject();

			List rw = aro.getViewRevenueWaste(request,pagesize, whereSql);
			List sumobj = aro.getTotalSubum(whereSql);
			ArrayList rls = new ArrayList();
			double totalreceivablesum = 0.00;
			double totalincomesum = 0.00;
			for (int i = 0; i < rw.size(); i++) {
				RevenueWasteForm rwf = new RevenueWasteForm();
				ViewRevenueWaste o = (ViewRevenueWaste) rw.get(i);
				rwf.setMakedate(DateUtil.formatDateTime(o.getMakedate()));
				rwf.setId(o.getVstidPK().getId());
				ReceivableObject ro = aro.getReceivableObjectByIDOrgID(o
						.getRoid(), o.getMakeorganid());
				rwf.setRoid(o.getRoid());
				rwf.setRoidname(ro != null ? ro.getPayer() : "");
				rwf.setMakeorganid(o.getMakeorganid());
				rwf.setMakeid(o.getMakeid());
				rwf.setPaymentmode(o.getPaymentmode());
				rwf.setMemo(o.getMemo());
				rwf.setReceivablesum(o.getReceivablesum());
				rwf.setIncomesum(o.getIncomesum());
				totalreceivablesum += rwf.getReceivablesum();
				totalincomesum += rwf.getIncomesum();

				rls.add(rwf);
			}
			request.setAttribute("totalreceivablesum", DataFormat
					.currencyFormat(totalreceivablesum));
			request.setAttribute("totalincomesum", DataFormat
					.currencyFormat(totalincomesum));

			double allreceivablesum = 0;
			double allincomesum = 0;
			if (sumobj != null) {
				Object[] ob = (Object[]) sumobj.get(0);
				allreceivablesum = Double.parseDouble(String
						.valueOf(ob[0] == null ? "0" : ob[0]));
				allincomesum = Double.parseDouble(String
						.valueOf(ob[1] == null ? "0" : ob[1]));
			}
			request.setAttribute("allreceivablesum", DataFormat
					.currencyFormat(allreceivablesum));
			request.setAttribute("allincomesum", DataFormat
					.currencyFormat(allincomesum));

			request.setAttribute("MakeOrganID", request.getParameter("MakeOrganID"));
			request.setAttribute("MakeID", request.getParameter("MakeID"));
			request.setAttribute("BeginDate", map.get("BeginDate"));
			request.setAttribute("EndDate", map.get("EndDate"));
			request.setAttribute("ROID", request.getParameter("ROID"));
			request.setAttribute("rls", rls);
			DBUserLog.addUserLog(userid, 10,"报表分析>>财务>>收入明细");
			return mapping.findForward("success");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
