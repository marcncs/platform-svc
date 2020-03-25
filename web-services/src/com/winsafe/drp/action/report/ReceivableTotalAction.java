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
import com.winsafe.drp.dao.AppOrgan;
import com.winsafe.drp.dao.AppReceivable;
import com.winsafe.drp.dao.AppReceivableObject;
import com.winsafe.drp.dao.AppUsers;
import com.winsafe.drp.dao.Receivable;
import com.winsafe.drp.dao.ReceivableForm;
import com.winsafe.drp.dao.ReceivableObject;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DataFormat;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.DbUtil;

public class ReceivableTotalAction extends BaseAction {
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
			String Condition = " 1=1 "+visitorgan;

			String[] tablename = { "Receivable" };
			String whereSql = getWhereSql(tablename);
			String timeCondition = getTimeCondition("MakeDate");
			whereSql = whereSql + timeCondition + Condition;
			whereSql = DbUtil.getWhereSql(whereSql);

			ArrayList str = new ArrayList();

			AppReceivableObject aso = new AppReceivableObject();
			AppReceivable asod = new AppReceivable();
			AppUsers au = new AppUsers();
			List pils = asod.getReceivable(request, pagesize, whereSql);
			List sumobj = asod.getTotalSum(whereSql);
			double totalsum = 0.00;
			double totalalreadysum = 0.00;
			for (Iterator it = pils.iterator(); it.hasNext();) {
				ReceivableForm rf = new ReceivableForm();
				Receivable pb = (Receivable) it.next();
				ReceivableObject ro = (ReceivableObject) aso
						.getReceivableObjectByIDOrgID(pb.getRoid(), pb
								.getMakeorganid());
				rf.setId(pb.getId());
				rf.setRoid(pb.getRoid());
				rf.setMakeorganid(pb.getMakeorganid());
				rf.setMakeid(pb.getMakeid());
				// rf.setMakeidname(au.getUsersByid(pb.getMakedeptid()).getRealname());
				rf.setMakedate(DateUtil.formatDateTime(pb.getMakedate()));
				rf.setReceivabledescribe(pb.getReceivabledescribe());
				rf.setReceivablesum(pb.getReceivablesum());
				rf.setAlreadysum(pb.getAlreadysum());
				rf.setReceivableobjectname(ro != null ? ro.getPayer() : "");
				str.add(rf);
				totalsum += rf.getReceivablesum();
				totalalreadysum += rf.getAlreadysum();
			}
			request.setAttribute("totalsum", DataFormat
					.currencyFormat(totalsum));
			request.setAttribute("totalalreadysum", DataFormat
					.currencyFormat(totalalreadysum));
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

			AppOrgan ao = new AppOrgan();
			List alos = ao.getOrganToDown(users.getMakeorganid());

			List als = au.getIDAndLoginNameByOID2(users.getMakeorganid());

			request.setAttribute("alos", alos);
			request.setAttribute("als", als);
			request.setAttribute("MakeOrganID", request
					.getParameter("MakeOrganID"));
			request.setAttribute("MakeID", request.getParameter("MakeID"));
			request.setAttribute("BeginDate", map.get("BeginDate"));
			request.setAttribute("EndDate", map.get("EndDate"));
			request.setAttribute("ROID", request.getParameter("ROID"));
			request.setAttribute("payername", request.getParameter("payername"));
			DBUserLog.addUserLog(userid, 10,"报表分析>>财务>>应收款结算单汇总");
			return mapping.findForward("success");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
