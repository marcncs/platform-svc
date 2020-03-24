package com.winsafe.drp.action.report;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppStockMove;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DbUtil;

public class PrintStockMoveBillTotalAction extends BaseAction {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		super.initdata(request);
		try {
			String visitorgan = "";
			if (users.getVisitorgan() != null
					&& users.getVisitorgan().length() > 0) {
				visitorgan = getAndVisitOrgan("makeorganid");
			}

			String Condition = " sm.id = smd.smid and (iscomplete=1 and isblankout=0  "
				 + " )  "+
					//+ visitorgan + ")"+ 
					" and (sm.inwarehouseid in (select wid from Warehouse_Visit where userid="+userid+") "+
					" or sm.outwarehouseid in (select wid from Warehouse_Visit where userid="+userid+")) ";

			String[] tablename = { "StockMove" };
			String whereSql = getWhereSql(tablename);
			String timeCondition = getTimeCondition("MoveDate");
			String blur =getKeyWordCondition("MakeOrganIDName","MakeOrganID","ReceiveOrganID","ReceiveOrganIDName","ID");
			whereSql = whereSql + timeCondition +blur+ Condition; 
			whereSql = DbUtil.getWhereSql(whereSql); 

			AppStockMove aso = new AppStockMove();
			List list = aso.getStockMoveBillTotal(whereSql);
			request.setAttribute("list", list);
			DBUserLog.addUserLog(userid, 10,"报表分析>>打印转仓按单据汇总");
			return mapping.findForward("toprint");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}


}
