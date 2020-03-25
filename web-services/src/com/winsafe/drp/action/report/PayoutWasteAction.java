package com.winsafe.drp.action.report;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppOrgan;
import com.winsafe.drp.dao.AppPayableObject;
import com.winsafe.drp.dao.AppUsers;
import com.winsafe.drp.dao.PayableObject;
import com.winsafe.drp.dao.PayoutWasteForm;
import com.winsafe.drp.dao.ViewPayoutWaste;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DataFormat;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.Internation;

public class PayoutWasteAction extends BaseAction {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		super.initdata(request);
		AppUsers au = new AppUsers();
		try {
			int pagesize = 20;
			

			String visitorgan = "";
			if (users.getVisitorgan() != null
					&& users.getVisitorgan().length() > 0) {
				visitorgan = getAndVisitOrgan("p.makeorganid");
			}
			String Condition = " 1=1 " + visitorgan;


			String[] tablename = { "Payable" };
			String whereSql = getWhereSql(tablename);
			String timeCondition = getTimeCondition("MakeDate");
			whereSql = whereSql + timeCondition + Condition; 
			whereSql = DbUtil.getWhereSql(whereSql); 

			AppPayableObject aro = new AppPayableObject();

			List rw = aro.getViewPayoutWaste(request,pagesize, whereSql);
			List sumobj = aro.getTotalSubum(whereSql);
			ArrayList rls = new ArrayList();
			double totalpayablesum = 0;
			double totalpaysum = 0;
			for (int i = 0; i < rw.size(); i++) {
				PayoutWasteForm rwf = new PayoutWasteForm();
				ViewPayoutWaste o = (ViewPayoutWaste) rw.get(i);
				rwf.setMakedate(DateUtil.formatDateTime(o.getMakedate()));
				rwf.setId(o.getVstidPK().getId());
				PayableObject po = aro.getPayableObjectByOIDOrgID(o.getPoid(),
						o.getMakeorganid());
				rwf.setPoid(o.getPoid());
				rwf.setPoidname(po != null ? po.getPayee() : "");
				rwf.setMakeorganid(o.getMakeorganid());
//				rwf.setMakeidname(au.getUsersByid(o.getMakeid())
//						.getRealname());
				rwf.setPaymodename(Internation.getStringByKeyPosition(
						"PayMode", request, o.getPaymode(),
						"global.sys.SystemResource"));
				rwf.setMemo(o.getMemo());
				rwf.setPayablesum(o.getPayablesum());
				rwf.setPaysum(o.getPaysum());
				totalpayablesum +=rwf.getPayablesum();
				totalpaysum += rwf.getPaysum();

				rls.add(rwf);
			}
			request.setAttribute("totalpayablesum", DataFormat
					.currencyFormat(totalpayablesum));
			request.setAttribute("totalpaysum", DataFormat
					.currencyFormat(totalpaysum));


			double allpayablesum = 0;
			double allpaysum = 0;
			if (sumobj != null) {
				Object[] ob = (Object[]) sumobj.get(0);
				allpayablesum = Double.parseDouble(String
						.valueOf(ob[0] == null ? "0" : ob[0]));
				allpaysum = Double.parseDouble(String
						.valueOf(ob[1] == null ? "0" : ob[1]));
			}
			request.setAttribute("allpayablesum", DataFormat
					.currencyFormat(allpayablesum));
			request.setAttribute("allpaysum", DataFormat
					.currencyFormat(allpaysum));

			AppOrgan ao = new AppOrgan();
			List alos = ao.getOrganToDown(users.getMakeorganid());

			List als = au.getIDAndLoginNameByOID2(users.getMakeorganid());
			
			request.setAttribute("alos", alos);
			request.setAttribute("als", als);
			request.setAttribute("MakeOrganID", request
					.getParameter("MakeOrganID"));
			request.setAttribute("MakeID", request.getParameter("MakeID"));
			request.setAttribute("Begindate", map.get("BeginDate"));
			request.setAttribute("EndDate", map.get("EndDate"));
			request.setAttribute("POID", request.getParameter("POID"));
			request
					.setAttribute("payername", request
							.getParameter("payername"));
			request.setAttribute("rls", rls);
			DBUserLog.addUserLog(userid, 10,"报表分析>>财务>>支出明细");
			return mapping.findForward("success");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
