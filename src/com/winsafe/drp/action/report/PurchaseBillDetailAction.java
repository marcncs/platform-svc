package com.winsafe.drp.action.report;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppPurchaseBillDetail;
import com.winsafe.drp.dao.PurchaseBillDetail;
import com.winsafe.drp.dao.PurchaseBillDetailForm;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DataFormat;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.Internation;

public class PurchaseBillDetailAction extends BaseAction {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		initdata(request);
		try {
			int pagesize = 20;
			String visitorgan = "";
			if (users.getVisitorgan() != null
					&& users.getVisitorgan().length() > 0) {
				visitorgan = getAndVisitOrgan("pb.makeorganid");
			}
			String Condition = " pb.id=pbd.pbid and pb.isratify=1 "
					+ visitorgan + "   ";

			String[] tablename = { "PurchaseBill", "PurchaseBillDetail" };
			String whereSql = getWhereSql(tablename);
			String timeCondition = getTimeCondition("MakeDate");			
			whereSql = whereSql + timeCondition  + Condition; 
			whereSql = DbUtil.getWhereSql(whereSql); 

			AppPurchaseBillDetail asod = new AppPurchaseBillDetail();
			double totalsum = asod.getTotalSubum(whereSql);

			List sodls = asod.getPurchaseBillDetail2(request, pagesize,
					whereSql);
			ArrayList alsod = new ArrayList();
			Double subsum = 0.00;
			for (int d = 0; d < sodls.size(); d++) {
				PurchaseBillDetailForm sodf = new PurchaseBillDetailForm();
				Object[] o = (Object[]) sodls.get(d);
				String pid = (String) o[0];
				String pname = (String) o[1];
				String makedate = DateUtil.formatDateTime((Date)o[2]);
				PurchaseBillDetail pbd = (PurchaseBillDetail) o[3];
				sodf.setMakedate(makedate);
				sodf.setPid(pid);
				sodf.setPname(pname);
				sodf.setPbid(pbd.getPbid());
				sodf.setProductid(pbd.getProductid());
				sodf.setProductname(pbd.getProductname());
				sodf.setSpecmode(pbd.getSpecmode());
				sodf.setUnitname(Internation.getStringByKeyPositionDB(
						"CountUnit", pbd.getUnitid()));
				sodf.setUnitprice(pbd.getUnitprice());
				sodf.setQuantity(pbd.getQuantity());
				sodf.setSubsum(pbd.getSubsum());
				subsum += sodf.getSubsum();
				alsod.add(sodf);
			}

			request.setAttribute("totalsum", DataFormat
					.currencyFormat(totalsum));
			request.setAttribute("subsum", DataFormat.currencyFormat(subsum));
			request.setAttribute("alsod", alsod);

			request.setAttribute("MakeOrganID", request
					.getParameter("MakeOrganID"));
			request.setAttribute("MakeID", request.getParameter("MakeID"));
			request.setAttribute("BeginDate", map.get("BeginDate"));
			request.setAttribute("EndDate", map.get("EndDate"));
			request.setAttribute("PID", request.getParameter("PID"));
			request.setAttribute("ProductID", request
							.getParameter("ProductID"));
			DBUserLog.addUserLog(userid, 10,"报表分析>>采购>>列表采购订单明细");
			return mapping.findForward("purchasebilltotal");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
