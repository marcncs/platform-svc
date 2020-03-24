package com.winsafe.drp.action.report;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppStockMoveDetail;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DbUtil;

public class PrintStockMoveDetailAction extends BaseAction{
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		initdata(request);
		try{
			String visitorgan = "";
			if (users.getVisitorgan() != null
					&& users.getVisitorgan().length() > 0) {
				visitorgan = getAndVisitOrgan("makeorganid");
			}
			String Condition = " pw.id=pwd.smid and  (pw.iscomplete=1 and pw.isblankout=0 " 
				+ " )  "+
			//+ visitorgan + ")"+ 
			" and (pw.inwarehouseid in (select wid from Warehouse_Visit where userid="+userid+") "+
			" or pw.outwarehouseid in (select wid from Warehouse_Visit where userid="+userid+")) ";

			String[] tablename = { "StockMove", "StockMoveDetail"  };
			String whereSql = getWhereSql(tablename);
			String timeCondition = getTimeCondition("MoveDate");
			String blur = getKeyWordCondition("MoveDate","MakeOrganID","OutWarehouseID","ProductID","ProductName","InOrganID","InWarehouseID");
			whereSql = whereSql + timeCondition +blur+ Condition; 
			whereSql = DbUtil.getWhereSql(whereSql); 

			
			AppStockMoveDetail asod=new AppStockMoveDetail(); 
	        List list = asod.getDetailReport(whereSql);
	        request.setAttribute("list", list);
	        DBUserLog.addUserLog(userid, 10,"报表分析>>打印转仓明细");
			return mapping.findForward("toprint");
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	


}
