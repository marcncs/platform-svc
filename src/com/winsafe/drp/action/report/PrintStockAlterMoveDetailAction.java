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
import com.winsafe.drp.dao.AppStockAlterMoveDetail;
import com.winsafe.drp.dao.DetailReportForm;
import com.winsafe.drp.dao.StockAlterMoveDetail;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.Internation;

public class PrintStockAlterMoveDetailAction extends BaseAction {
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
			String Condition = "pw.id=pwd.samid and (pw.iscomplete=1 and pw.isblankout=0 "
				 + " )  "+
					//+ visitorgan + ")"+ 
					" and (pw.inwarehouseid in (select wid from Warehouse_Visit where userid="+userid+") "+
					" or pw.outwarehouseid in (select wid from Warehouse_Visit where userid="+userid+")) ";
			String[] tablename = { "StockAlterMove", "StockAlterMoveDetail" };

			String whereSql = getWhereSql(tablename);
			String timeCondition = getTimeCondition("MoveDate");
			whereSql = whereSql + timeCondition + Condition; 
			whereSql = DbUtil.getWhereSql(whereSql); 

			AppStockAlterMoveDetail asod = new AppStockAlterMoveDetail();
			List sodls = asod.getDetailReport(whereSql);
			ArrayList list = new ArrayList();
			Double totalsum = 0.00;
			for (int d = 0; d < sodls.size(); d++) {
				DetailReportForm sodf = new DetailReportForm();
				Object[] o = (Object[]) sodls.get(d);
				String morganname = (String) o[1];
				String rorganname = (String) o[3];
				String makedate = DateUtil.formatDateTime((Date)o[4]);
	        	StockAlterMoveDetail pbd = (StockAlterMoveDetail)o[5];
	        	sodf.setOname(morganname);
	        	sodf.setSoname(rorganname);
	        	sodf.setMakedate(makedate);
				sodf.setBillid(pbd.getSamid());
				sodf.setProductid(pbd.getProductid());
				sodf.setProductname(pbd.getProductname());
				sodf.setSpecmode(pbd.getSpecmode());
				sodf.setUnitidname(Internation.getStringByKeyPositionDB(
						"CountUnit", Integer
								.valueOf(pbd.getUnitid().toString())));
				sodf.setUnitprice(pbd.getUnitprice());
				sodf.setQuantity(pbd.getQuantity());
				sodf.setSubsum(pbd.getSubsum());
				totalsum += sodf.getSubsum();
				list.add(sodf);
			}
			DBUserLog.addUserLog(userid, 10,"报表分析>>打印订购明细");
			request.setAttribute("list", list);

			return mapping.findForward("toprint");

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
