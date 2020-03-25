package com.winsafe.drp.action.report;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppPurchaseTradesDetail;
import com.winsafe.drp.dao.DetailReportForm;
import com.winsafe.drp.dao.PurchaseTrades;
import com.winsafe.drp.dao.PurchaseTradesDetail;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.Internation;

public class PrintPurchaseTradesDetailAction extends BaseAction {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		initdata(request);
		try {
			String visitorgan = "";
			if (users.getVisitorgan() != null
					&& users.getVisitorgan().length() > 0) {
				visitorgan = getAndVisitOrgan("pt.makeorganid");
			}
			String Condition = " pt.id=ptd.ptid and isreceive=1 and pt.isblankout=0 "
					+ visitorgan + " ";

			String[] tablename = { "PurchaseTrades", "PurchaseTradesDetail" };
			String whereSql = getWhereSql(tablename);
			String timeCondition = getTimeCondition(" MakeDate");
			String blur = getKeyWordCondition("ProvideName", "ProductName",
					"ProductID", "ProvideID", "MakeDate", "MakeOrganID");
			whereSql = whereSql + timeCondition + blur + Condition; 
			whereSql = DbUtil.getWhereSql(whereSql); 

			AppPurchaseTradesDetail asod = new AppPurchaseTradesDetail();
			List sodls = asod.getDetailReport(whereSql);
			ArrayList list = new ArrayList();
			Double totalsum = 0.00;
			for (int d = 0; d < sodls.size(); d++) {
				DetailReportForm sodf = new DetailReportForm();
				Object[] o = (Object[]) sodls.get(d);
				PurchaseTrades pt = (PurchaseTrades) o[0];

				PurchaseTradesDetail pbd = (PurchaseTradesDetail) o[1];
	        	sodf.setMakedate(DateUtil.formatDate(pt.getMakedate()));
				sodf.setPid(pt.getProvideid());
				sodf.setOname(pt.getProvidename());
				sodf.setBillid(pbd.getPtid());
				sodf.setProductid(pbd.getProductid());
				sodf.setProductname(pbd.getProductname());
				sodf.setSpecmode(pbd.getSpecmode());
				sodf.setUnitidname(Internation.getStringByKeyPositionDB(
						"CountUnit", pbd.getUnitid()));
				sodf.setQuantity(pbd.getQuantity());
				totalsum += sodf.getQuantity();
				list.add(sodf);
			}
			request.setAttribute("totalqt", totalsum);
			request.setAttribute("list", list);
			DBUserLog.addUserLog(userid, 10,"报表分析>>打印采购换货明细");
			return mapping.findForward("toprint");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	
}
