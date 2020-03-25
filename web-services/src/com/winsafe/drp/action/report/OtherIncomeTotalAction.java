package com.winsafe.drp.action.report;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppOtherIncomeDetail;
import com.winsafe.drp.dao.DetailReportForm;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.Internation;

public class OtherIncomeTotalAction extends BaseAction {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		initdata(request);
		try {
			int pagesize = 20;
			String visitorgan = "";
			if (users.getVisitorgan() != null
					&& users.getVisitorgan().length() > 0) {
				visitorgan = getAndVisitOrgan("pw.makeorganid");
			}
			String Condition = " pw.id=pwd.oiid and  pw.isaudit=1 "
					+ visitorgan
					+ "  ";

			String[] tablename = { "OtherIncome", "OtherIncomeDetail" };
			String whereSql =getWhereSql(tablename);
			String timeCondition = getTimeCondition("pw.MakeDate");
			whereSql = whereSql + timeCondition + Condition; 
			whereSql = DbUtil.getWhereSql(whereSql); 

			ArrayList str = new ArrayList();

			AppOtherIncomeDetail asod = new AppOtherIncomeDetail();
			List pils = asod.getTotalReport(request, pagesize, whereSql);
			double sumobj = asod.getTotalSubum(whereSql);
			double totalqt = 0.00;
			for (Iterator it = pils.iterator(); it.hasNext();) {
				DetailReportForm sodf = new DetailReportForm();
				Map o = (Map) it.next();
				sodf.setOname(o.get("makeorganid").toString());
				sodf.setProductid(o.get("productid").toString());
				sodf.setProductname(o.get("productname").toString());
				sodf.setSpecmode(o.get("specmode").toString());
				sodf.setUnitidname(Internation.getStringByKeyPositionDB(
						"CountUnit", Integer.valueOf(o.get("unitid").toString())));
				sodf.setQuantity(Double.valueOf(o.get("quantity").toString()));
				totalqt += sodf.getQuantity();
				str.add(sodf);
			}
			request.setAttribute("totalqt", totalqt);
			request.setAttribute("str", str);

			request.setAttribute("allqt", sumobj);

			request.setAttribute("MakeOrganID", request.getParameter("MakeOrganID"));
			request.setAttribute("MakeID", request.getParameter("MakeID"));
			request.setAttribute("BeginDate", map.get("BeginDate"));
			request.setAttribute("EndDate", map.get("EndDate"));
			request.setAttribute("WarehouseID", request.getParameter("WarehouseID"));
			request.setAttribute("ProductName", request.getParameter("ProductName"));
			DBUserLog.addUserLog(userid, 10,"报表分析>>盘点>>列表盘盈汇总");
			return mapping.findForward("success");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
