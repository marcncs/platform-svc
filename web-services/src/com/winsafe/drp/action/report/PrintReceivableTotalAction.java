package com.winsafe.drp.action.report;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppReceivable;
import com.winsafe.drp.dao.AppReceivableObject;
import com.winsafe.drp.dao.Receivable;
import com.winsafe.drp.dao.ReceivableForm;
import com.winsafe.drp.dao.ReceivableObject;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DataFormat;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.DbUtil;

public class PrintReceivableTotalAction extends BaseAction {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		initdata(request);
		try {
			String visitorgan = "";
			if (users.getVisitorgan() != null
					&& users.getVisitorgan().length() > 0) {
				visitorgan = getAndVisitOrgan("makeorganid");
			}
			String Condition = " 1=1 "+visitorgan;

			String[] tablename = { "Receivable" };
			String whereSql = getWhereSql(tablename);
			String timeCondition = getTimeCondition("MakeDate");
			whereSql = whereSql + timeCondition + Condition;
			whereSql = DbUtil.getWhereSql(whereSql);

			ArrayList str = new ArrayList();

			AppReceivableObject aso = new AppReceivableObject();
			AppReceivable asod = new AppReceivable();
			List pils = asod.getReceivable(whereSql);
			List sumobj = asod.getTotalSum(whereSql);
			for (Iterator it = pils.iterator(); it.hasNext();) {
				ReceivableForm rf = new ReceivableForm();
				Receivable pb = (Receivable) it.next();
				ReceivableObject ro = (ReceivableObject) aso
						.getReceivableObjectByIDOrgID(pb.getRoid(), pb
								.getMakeorganid());
				rf.setId(pb.getId());
				rf.setMakeorganid(pb.getMakeorganid());
				rf.setMakeid(pb.getMakeid());
				rf.setMakedate(DateUtil.formatDateTime(pb.getMakedate()));
				rf.setReceivabledescribe(pb.getReceivabledescribe());
				rf.setReceivablesum(pb.getReceivablesum());
				rf.setAlreadysum(pb.getAlreadysum());
				rf.setReceivableobjectname(ro != null ? ro.getPayer() : "");
				str.add(rf);
			}
			request.setAttribute("str", str);

			double allsum = 0;
			double allalreadysum = 0;
			if (sumobj != null) {
				Object[] ob = (Object[]) sumobj.get(0);
				allsum = Double.parseDouble(String.valueOf(ob[0] == null ? "0"
						: ob[0]));
				allalreadysum = Double.parseDouble(String
						.valueOf(ob[1] == null ? "0" : ob[1]));
			}
			request.setAttribute("allsum", DataFormat.currencyFormat(allsum));
			request.setAttribute("allalreadysum", DataFormat
					.currencyFormat(allalreadysum));
			DBUserLog.addUserLog(userid, 2, "报表分析>>打印应收款结算单汇总");
			return mapping.findForward("toprint");

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
