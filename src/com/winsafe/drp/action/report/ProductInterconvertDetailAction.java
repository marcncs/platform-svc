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
import com.winsafe.drp.dao.AppProductInterconvertDetail;
import com.winsafe.drp.dao.DetailReportForm;
import com.winsafe.drp.dao.ProductInterconvertDetail;
import com.winsafe.drp.server.WarehouseService;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.HtmlSelect;

public class ProductInterconvertDetailAction extends BaseAction {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		initdata(request);
		try {
			int pagesize = 20;
			String visitorgan = "";
			if (users.getVisitorgan() != null
					&& users.getVisitorgan().length() > 0) {
				visitorgan = getAndVisitOrgan("makeorganid");
			}
			String Condition = " pw.id=pwd.piid and pw.iscomplete=1 "
					+ visitorgan + " ";

		
			String[] tablename = { "ProductInterconvert",
					"ProductInterconvertDetail" };
			String whereSql = getWhereSql(tablename);
			String timeCondition = getTimeCondition("pw.MakeDate");
			whereSql = whereSql + timeCondition + Condition; 
			whereSql = DbUtil.getWhereSql(whereSql); 

			AppProductInterconvertDetail asod = new AppProductInterconvertDetail();
			Double sumobj = asod.getTotalSubum(whereSql);
			List sodls = asod.getDetailReport(request, pagesize, whereSql);
			WarehouseService aw = new WarehouseService();
			ArrayList alsod = new ArrayList();
			Double totalsum = 0.00;
			for (int d = 0; d < sodls.size(); d++) {
				DetailReportForm sodf = new DetailReportForm();
				Object[] o = (Object[]) sodls.get(d);
				String outwid = (String) o[0];
				String inwid = (String) o[1];
				String makedate = DateUtil.formatDateTime((Date)o[2]);
				ProductInterconvertDetail pbd = (ProductInterconvertDetail) o[3];
				sodf.setMakedate(makedate);
				sodf.setOname(aw.getWarehouseName(outwid));
				sodf.setSoname(aw.getWarehouseName(inwid));
				sodf.setBillid(pbd.getPiid());
				sodf.setProductid(pbd.getProductid());
				sodf.setProductname(pbd.getProductname());
				sodf.setSpecmode(pbd.getSpecmode());
				sodf.setUnitidname(HtmlSelect.getResourceName(request, "CountUnit", pbd.getUnitid()));
				sodf.setUnitprice(pbd.getUnitprice());
				sodf.setQuantity(pbd.getQuantity());
				totalsum += sodf.getQuantity();
				alsod.add(sodf);
			}

			request.setAttribute("totalsum", totalsum);
			request.setAttribute("alsod", alsod);
			request.setAttribute("allsum", sumobj);
			
	
			request.setAttribute("BeginDate", map.get("BeginDate"));
			request.setAttribute("EndDate", map.get("EndDate"));
			DBUserLog.addUserLog(userid, 10,"报表分析>>出库>>列表产品互转明细");
			return mapping.findForward("success");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
