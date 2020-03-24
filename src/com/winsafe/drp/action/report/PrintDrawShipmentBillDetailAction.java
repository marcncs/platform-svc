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
import com.winsafe.drp.dao.AppDrawShipmentBillDetail;
import com.winsafe.drp.dao.AppWarehouse;
import com.winsafe.drp.dao.DetailReportForm;
import com.winsafe.drp.dao.DrawShipmentBillDetail;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.HtmlSelect;

public class PrintDrawShipmentBillDetailAction extends BaseAction {
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
			String Condition = " pw.id=pwd.dsid and  pw.isendcase=1 and pw.isblankout=0 "
					+ visitorgan
					+ "  ";

			String[] tablename = { "DrawShipmentBill", "DrawShipmentBillDetail" };
			String whereSql = getWhereSql(tablename);
			String timeCondition = getTimeCondition("MakeDate");
			String blur = getKeyWordCondition("ProductName","SpecMode");
			whereSql = whereSql + timeCondition +blur+ Condition; 
			whereSql = DbUtil.getWhereSql(whereSql); 

			AppDrawShipmentBillDetail asod = new AppDrawShipmentBillDetail();
			List sodls = asod.getDetailReport(whereSql);
			AppWarehouse aw = new AppWarehouse();
			ArrayList list = new ArrayList();
			Double totalsum = 0.00;
			for (int d = 0; d < sodls.size(); d++) {
				DetailReportForm sodf = new DetailReportForm();
				Object[] o = (Object[]) sodls.get(d);
				String wid = (String) o[0];

				String makedate = DateUtil.formatDateTime((Date)o[1]);
				DrawShipmentBillDetail pbd = (DrawShipmentBillDetail) o[2];
				sodf.setMakedate(makedate);
				sodf.setOname(aw.getWarehouseByID(wid).getWarehousename());
				sodf.setBillid(pbd.getDsid());
				sodf.setProductid(pbd.getProductid());
				sodf.setProductname(pbd.getProductname());
				sodf.setSpecmode(pbd.getSpecmode());
				sodf.setUnitidname(HtmlSelect.getResourceName(request, "CountUnit", pbd.getUnitid()));
				sodf.setQuantity(pbd.getQuantity());
				totalsum += sodf.getQuantity();
				list.add(sodf);
			}
			request.setAttribute("totalqt", totalsum);
			request.setAttribute("list", list);
			DBUserLog.addUserLog(userid, 10,"报表分析>>打印领用明细");
			return mapping.findForward("toprint");
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	

}
