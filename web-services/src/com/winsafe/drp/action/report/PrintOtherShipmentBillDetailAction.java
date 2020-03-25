package com.winsafe.drp.action.report;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppOtherShipmentBillDetail;
import com.winsafe.drp.dao.DetailReportForm;
import com.winsafe.drp.dao.OtherShipmentBill;
import com.winsafe.drp.dao.OtherShipmentBillDetail;
import com.winsafe.drp.server.OrganService;
import com.winsafe.drp.server.WarehouseService;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.Internation;

public class PrintOtherShipmentBillDetailAction extends BaseAction{
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
					+ visitorgan + "  ";

			String[] tablename = { "OtherShipmentBill",
					"OtherShipmentBillDetail" };
			String whereSql = getWhereSql(tablename);
			String timeCondition = getTimeCondition("MakeDate");
			whereSql = whereSql + timeCondition + Condition; 
			whereSql = DbUtil.getWhereSql(whereSql); 

			AppOtherShipmentBillDetail asod = new AppOtherShipmentBillDetail();
			List sodls = asod.getDetailReport(whereSql);
			WarehouseService aw = new WarehouseService();
			OrganService os = new OrganService();
			ArrayList list = new ArrayList();
			double totalqt = 0.00;
			for (int d = 0; d < sodls.size(); d++) {
				DetailReportForm sodf = new DetailReportForm();
				Object[] o = (Object[]) sodls.get(d);
				OtherShipmentBill osb = (OtherShipmentBill)o[0];
				OtherShipmentBillDetail pbd = (OtherShipmentBillDetail) o[1];
				sodf.setSoname(os.getOrganName(osb.getMakeorganid()));
	        	sodf.setMakedate(DateUtil.formatDateTime(osb.getMakedate()));				
				sodf.setOname(aw.getWarehouseName(osb.getWarehouseid()));
				sodf.setBillid(pbd.getOsid());
				sodf.setProductid(pbd.getProductid());
				sodf.setProductname(pbd.getProductname());
				sodf.setSpecmode(pbd.getSpecmode());
				sodf.setUnitidname(Internation.getStringByKeyPositionDB(
						"CountUnit", Integer
								.valueOf(pbd.getUnitid().toString())));
				sodf.setQuantity(pbd.getQuantity());
				totalqt += sodf.getQuantity();
				list.add(sodf);
			}
			request.setAttribute("totalqt", totalqt);
			request.setAttribute("list", list);
			DBUserLog.addUserLog(userid, 10,"报表分析>>打印盘亏明细");
			return mapping.findForward("toprint");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
