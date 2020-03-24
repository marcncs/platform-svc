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
import com.winsafe.drp.dao.AppProductInterconvertDetail;
import com.winsafe.drp.dao.DetailReportForm;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.Internation;

public class PrintProductInterconvertTotalAction extends BaseAction {
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
			String Condition = " pw.id=pwd.piid and  pw.iscomplete=1"
					+ visitorgan
					+ " ";

			String[] tablename = { "ProductInterconvert", "ProductInterconvertDetail" };
			String whereSql = getWhereSql(tablename);
			String timeCondition = getTimeCondition("pw.MakeDate");
			whereSql = whereSql + timeCondition + Condition; 
			whereSql = DbUtil.getWhereSql(whereSql); 

			ArrayList list = new ArrayList();
			double  totalqt =0.00;
			AppProductInterconvertDetail asod = new AppProductInterconvertDetail();
			List pils = asod.getTotalReport(whereSql);
			for (Iterator it = pils.iterator(); it.hasNext();) {
				DetailReportForm sodf = new DetailReportForm();
				Object[] o = (Object[]) it.next();
				sodf.setProductid(String.valueOf(o[0]));
				sodf.setProductname(String.valueOf(o[1]));
				sodf.setSpecmode(String.valueOf(o[2]));
				sodf.setUnitidname(Internation.getStringByKeyPositionDB(
						"CountUnit", Integer.valueOf(o[3].toString())));
				sodf.setQuantity(Double.valueOf(o[4].toString()));
				totalqt+=sodf.getQuantity();
				list.add(sodf);
			}
			DBUserLog.addUserLog(userid, 10,"报表分析>>打印商品互转汇总");
			request.setAttribute("totalqt", totalqt);
			request.setAttribute("list", list);
			return mapping.findForward("toprint");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	

}
