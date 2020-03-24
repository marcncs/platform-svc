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

public class StockMoveBillTotalAction extends BaseAction {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		super.initdata(request);
		try {
			int pagesize = 20;
			String visitorgan = "";
			if (users.getVisitorgan() != null
					&& users.getVisitorgan().length() > 0) {
				visitorgan = getAndVisitOrgan("makeorganid");
			}

			String[] tablename = { "StockMove" };
			String whereSql = getWhereSql(tablename);
			String timeCondition = getTimeCondition("MoveDate");
			String Condition = "sm.id = smd.smid and (iscomplete=1 and isblankout=0  "
				    + " )  "+
					//+ visitorgan + ")"+ 
					" and (sm.inwarehouseid in (select wid from WarehouseVisit where userid="+userid+") "+
					" or sm.outwarehouseid in (select wid from WarehouseVisit where userid="+userid+")) ";
			String blur =getKeyWordCondition("MakeOrganID","InOrganID","sm.ID");
			whereSql = whereSql + timeCondition +blur+ Condition; 
			whereSql = DbUtil.getWhereSql(whereSql); 


			AppStockMove aso = new AppStockMove();
			List pils = aso.getStockMoveBillTotal(request, pagesize, whereSql.replaceAll("WarehouseVisit", "Warehouse_Visit"));
//			Double allsum = aso.getStockTotalSum(whereSql.replaceAll("WarehouseVisit", "Warehouse_Visit"));

			Integer alltotalboxnum=aso.getStockMoveTotalBoxSum(whereSql.replaceAll("WarehouseVisit", "Warehouse_Visit"));
	        Double alltotalscatternum=aso.getStockMoveTotalScatterSum(whereSql.replaceAll("WarehouseVisit", "Warehouse_Visit"));
	        	
	        request.setAttribute("alltotalboxnum", alltotalboxnum);
	        request.setAttribute("alltotalscatternum", alltotalscatternum);
			
//			request.setAttribute("allsum", allsum);
			request.setAttribute("list", pils);


			request.setAttribute("MakeOrganID", request.getParameter("MakeOrganID"));
			request.setAttribute("InOrganID", request.getParameter("InOrganID"));
			request.setAttribute("BeginDate", map.get("BeginDate"));
			request.setAttribute("EndDate", map.get("EndDate"));
			request.setAttribute("ID", request.getParameter("ID"));
			DBUserLog.addUserLog(userid, 10,"报表分析>>渠道>>列表转仓按单据汇总");
			return mapping.findForward("success");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
