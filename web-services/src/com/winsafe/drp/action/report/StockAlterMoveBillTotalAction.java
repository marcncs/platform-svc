package com.winsafe.drp.action.report;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppFUnit;
import com.winsafe.drp.dao.AppProduct;
import com.winsafe.drp.dao.AppStockAlterMove;
import com.winsafe.drp.dao.Product;
import com.winsafe.drp.util.ArithDouble;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DbUtil;

public class StockAlterMoveBillTotalAction extends BaseAction {
	@SuppressWarnings("unchecked")
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		super.initdata(request);
		try {
			int pagesize = 20;
//			String visitorgan = "";
//			if (users.getVisitorgan() != null
//					&& users.getVisitorgan().length() > 0) {
//				visitorgan = getAndVisitOrgan("makeorganid");
//			}

			String[] tablename = { "StockAlterMove" };
			String whereSql = getWhereSql(tablename);
			String timeCondition = getTimeCondition("sm.MakeDate");
			String Condition = "sm.id = smd.samid  and(iscomplete=1 and isblankout=0  "
				     + ")"+ 
					//+ visitorgan + ")"+ 
					" and (inwarehouseid in (select wid from WarehouseVisit where userid="+userid+") "+
					" or outwarehouseid in (select wid from WarehouseVisit where userid="+userid+")) ";
			String blur =getKeyWordCondition("MakeOrganIDName","MakeOrganID","ReceiveOrganID","ReceiveOrganIDName","sm.ID");
			whereSql = whereSql + timeCondition +blur+ Condition; 
			whereSql = DbUtil.getWhereSql(whereSql); 


			AppStockAlterMove aso = new AppStockAlterMove();
			List pils = aso.getStockAlterMoveBillTotal(request, pagesize, 
					whereSql.replaceAll("WarehouseVisit", "Warehouse_Visit"));
//			Double allsum = aso.getStockAlterOrganTotalSum(whereSql);
			
	        Integer alltoalboxnum=aso.getStockAlterTotalBoxSum(whereSql.replaceAll("WarehouseVisit", "Warehouse_Visit"));
	        Double alltotalscatternum=aso.getStockAlterTotalScatterSum(whereSql.replaceAll("WarehouseVisit", "Warehouse_Visit"));
	        	
	        request.setAttribute("alltoalboxnum", alltoalboxnum);
	        request.setAttribute("alltotalscatternum", alltotalscatternum);
			request.setAttribute("list", pils);
			

			request.setAttribute("MakeOrganID", request.getParameter("MakeOrganID"));
			request.setAttribute("MakeOrganIDName", request.getParameter("MakeOrganIDName"));
			request.setAttribute("ReceiveOrganID", request.getParameter("ReceiveOrganID"));
			request.setAttribute("ReceiveOrganIDName", request.getParameter("ReceiveOrganIDName"));
			request.setAttribute("BeginDate", map.get("BeginDate"));
			request.setAttribute("EndDate", map.get("EndDate"));
			request.setAttribute("ID", request.getParameter("ID"));
			DBUserLog.addUserLog(userid, 10,"报表分析>>渠道>>列表订购按单据汇总");
			return mapping.findForward("show");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
