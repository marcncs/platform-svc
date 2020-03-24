package com.winsafe.drp.action.report;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppStockAlterMove;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DbUtil;

public class PrintStockAlterMoveProductTotalAction extends BaseAction {
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

			String[] tablename = { "StockAlterMove", "StockAlterMoveDetail" };
			String whereSql = getWhereSql(tablename);
			String timeCondition = getTimeCondition("MoveDate");
			String Condition = " sam.id=samd.samid and (iscomplete=1 and isblankout=0  "
				     + " )  "+
					//+ visitorgan + ")"+ 
					" and (sam.inwarehouseid in (select wid from Warehouse_Visit where userid="+userid+") "+
					" or sam.outwarehouseid in (select wid from Warehouse_Visit where userid="+userid+")) ";
			String blur = getKeyWordCondition("ProductID","ProductName","MakeOrganID","ReceiveOrganIDName","MoveDate");
			whereSql = whereSql + timeCondition +blur+ Condition; 
			whereSql = DbUtil.getWhereSql(whereSql); 


			AppStockAlterMove aso = new AppStockAlterMove();
			List list = aso.getStockAlterProductTotal(whereSql);
			
			request.setAttribute("list", list);
			DBUserLog.addUserLog(userid, 10,"报表分析>>打印订购按产品汇总");
			return mapping.findForward("toprint");


		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
