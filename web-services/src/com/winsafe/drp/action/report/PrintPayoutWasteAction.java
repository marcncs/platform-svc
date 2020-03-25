package com.winsafe.drp.action.report;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppPayableObject;
import com.winsafe.drp.dao.PayableObject;
import com.winsafe.drp.dao.PayoutWasteForm;
import com.winsafe.drp.dao.ViewPayoutWaste;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DataFormat;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.DbUtil;

public class PrintPayoutWasteAction extends BaseAction {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		initdata(request);
		try {
			String visitorgan = "";
			if (users.getVisitorgan() != null
					&& users.getVisitorgan().length() > 0) {
				//visitorgan = " or r.makeorganid in(" + users.getVisitorgan()+ ")" ;
				visitorgan = getAndVisitOrgan("p.makeorganid");
			}
			String Condition = " 1=1 " + visitorgan;

			Map map = new HashMap(request.getParameterMap());
			Map tmpMap = EntityManager.scatterMap(map);
			String[] tablename = { "Payable" };
			String whereSql = EntityManager.getTmpWhereSql(map, tablename);
			String brur = DbUtil.getOrBlur(map, tmpMap, "POID");
			String timeCondition = DbUtil.getTimeCondition(map, tmpMap,
					" MakeDate");
			whereSql = whereSql + brur + timeCondition + Condition; 
			whereSql = DbUtil.getWhereSql(whereSql); 

			AppPayableObject aro = new AppPayableObject();

			List rw = aro.getViewPayoutWaste(whereSql);
			List sumobj = aro.getTotalSubum(whereSql);
			ArrayList rls = new ArrayList();
//			double totalreceivablesum = 0.00;
//			double totalincomesum = 0.00;
			for (int i = 0; i < rw.size(); i++) {
				PayoutWasteForm rwf = new PayoutWasteForm();
				ViewPayoutWaste o = (ViewPayoutWaste) rw.get(i);
				rwf.setMakedate(DateUtil.formatDateTime(o.getMakedate()));
				rwf.setId(o.getVstidPK().getId());
				PayableObject ro = aro.getPayableObjectByOIDOrgID(o
						.getPoid(), o.getMakeorganid());
				rwf.setPoid(o.getPoid());
				rwf.setPoidname(ro != null ? ro.getPayee() : "");
				rwf.setMakeorganid(o.getMakeorganid());
				rwf.setMakeid(o.getMakeid());
				rwf.setPaymode(o.getPaymode());
//				rwf.setPaymentmodename(Internation.getStringByKeyPosition(
//						"PaymentMode", request, o.getPaymentmode(),
//						"global.sys.SystemResource"));
				rwf.setMemo(o.getMemo());
				rwf.setPayablesum(o.getPayablesum());
				rwf.setPaysum(o.getPaysum());
//				totalreceivablesum += rwf.getReceivablesum();
//				totalincomesum += rwf.getIncomesum();

				rls.add(rwf);
			}
//			request.setAttribute("totalreceivablesum", DataFormat
//					.currencyFormat(totalreceivablesum));
//			request.setAttribute("totalincomesum", DataFormat
//					.currencyFormat(totalincomesum));
			request.setAttribute("rls", rls);

			double allpayablesum = 0;
			double allpaysum = 0;
			if (sumobj != null) {
				Object[] ob = (Object[]) sumobj.get(0);
				allpayablesum = Double.parseDouble(String
						.valueOf(ob[0] == null ? "0" : ob[0]));
				allpaysum = Double.parseDouble(String
						.valueOf(ob[1] == null ? "0" : ob[1]));
			}
			request.setAttribute("allreceivablesum", DataFormat
					.currencyFormat(allpayablesum));
			request.setAttribute("allincomesum", DataFormat
					.currencyFormat(allpaysum));
			DBUserLog.addUserLog(userid, 10,"报表分析>>打印支出明细");
			return mapping.findForward("toprint");

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	

}
