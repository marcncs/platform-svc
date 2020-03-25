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
import com.winsafe.drp.dao.AppWarehouse;
import com.winsafe.drp.dao.DetailReportForm;
import com.winsafe.drp.dao.ProductInterconvertDetail;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.Internation;

public class PrintProductInterconvertDetailAction extends BaseAction {
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
			String Condition = " pw.id=pwd.piid and  pw.iscomplete=1 "
					+ visitorgan + "  ";

			String[] tablename = { "ProductInterconvert",
					"ProductInterconvertDetail" };
			String whereSql = getWhereSql(tablename);
			String timeCondition = getTimeCondition("pw.MakeDate");
			whereSql = whereSql + timeCondition + Condition; 
			whereSql = DbUtil.getWhereSql(whereSql); 

			AppProductInterconvertDetail asod = new AppProductInterconvertDetail();
			Double sumobj = asod.getTotalSubum(whereSql);
			List sodls = asod.getDetailReport(whereSql);
			AppWarehouse aw = new AppWarehouse();
			ArrayList list = new ArrayList();
			Double totalsum = 0.00;
			for (int d = 0; d < sodls.size(); d++) {
				DetailReportForm sodf = new DetailReportForm();
				Object[] o = (Object[]) sodls.get(d);
				String outwid = (String) o[0];
				String inwid = (String) o[1];

				String makedate = DateUtil.formatDateTime((Date)o[2]);
				ProductInterconvertDetail pbd = (ProductInterconvertDetail) o[3];
				sodf.setMakedate(makedate);
				sodf.setOname(aw.getWarehouseByID(outwid).getWarehousename());
				sodf.setSoname(aw.getWarehouseByID(inwid).getWarehousename());
				sodf.setBillid(pbd.getPiid());
				sodf.setProductid(pbd.getProductid());
				sodf.setProductname(pbd.getProductname());
				sodf.setSpecmode(pbd.getSpecmode());
				sodf.setUnitidname(Internation.getStringByKeyPositionDB(
						"CountUnit", Integer
								.valueOf(pbd.getUnitid().toString())));
				sodf.setUnitprice(pbd.getUnitprice());
				sodf.setQuantity(pbd.getQuantity());
				totalsum += sodf.getQuantity();
				list.add(sodf);
				
				
			}
			request.setAttribute("totalsum", totalsum);
			request.setAttribute("list", list);
			DBUserLog.addUserLog(userid, 10,"报表分析>>打印商品互转明细");
			return mapping.findForward("toprint");
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	
}
