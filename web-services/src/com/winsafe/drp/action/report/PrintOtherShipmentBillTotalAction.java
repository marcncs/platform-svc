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
import com.winsafe.drp.dao.AppOtherShipmentBillDetail;
import com.winsafe.drp.dao.DetailReportForm;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.Internation;

public class PrintOtherShipmentBillTotalAction extends BaseAction {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		initdata(request);
		try {
			String visitorgan = "";
			if (users.getVisitorgan() != null
					&& users.getVisitorgan().length() > 0) {
				visitorgan = getAndVisitOrgan("pw.makeorganid");
			}
			String Condition = " pw.id=pwd.osid and pw.isaudit=1 and pw.isblankout=0 "
					+ visitorgan + " ";

			String[] tablename = { "OtherShipmentBill",
					"OtherShipmentBillDetail" };
			String whereSql = getWhereSql(tablename);
			String timeCondition = getTimeCondition("MakeDate");
			whereSql = whereSql + timeCondition + Condition; 
			whereSql = DbUtil.getWhereSql(whereSql); 

			ArrayList list = new ArrayList();

			AppOtherShipmentBillDetail asod = new AppOtherShipmentBillDetail();
			List pils = asod.getTotalReport(whereSql);
			double totalqt = 0.00;
			for (Iterator it = pils.iterator(); it.hasNext();) {
				DetailReportForm sodf = new DetailReportForm();
				Object[] o = (Object[]) it.next();
				sodf.setOname(String.valueOf(o[0]));
				sodf.setProductid(String.valueOf(o[1]));
				sodf.setProductname(String.valueOf(o[2]));
				sodf.setSpecmode(String.valueOf(o[3]));
				sodf.setUnitidname(Internation.getStringByKeyPositionDB(
						"CountUnit", Integer.valueOf(o[4].toString())));
				sodf.setQuantity(Double.valueOf(o[5].toString()));
				totalqt += sodf.getQuantity();
				list.add(sodf);
			}
			request.setAttribute("totalqt", totalqt);
			request.setAttribute("list", list);
			DBUserLog.addUserLog(userid, 10,"报表分析>>打印盘亏汇总");
			return mapping.findForward("toprint");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
