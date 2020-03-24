package com.winsafe.drp.action.report;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppProductIncomeDetail;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DbUtil;

public class PrintProductIncomeTotalAction extends BaseAction{
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		initdata(request);
		try{
			String visitorgan = "";
			if (users.getVisitorgan() != null
					&& users.getVisitorgan().length() > 0) {
				visitorgan = getAndVisitOrgan("makeorganid") ;
			}
			String Condition = "pw.id=pwd.piid and  pw.isaudit=1 " + visitorgan+ " ";

			String[] tablename = { "ProductIncome", "ProductIncomeDetail" };
			String whereSql = getWhereSql(tablename);
			String timeCondition = getTimeCondition("IncomeDate");
			String blur = getKeyWordCondition("ProductName","SpecMode","MakeID","WarehouseID");
			whereSql = whereSql + timeCondition +blur+ Condition; 
			whereSql = DbUtil.getWhereSql(whereSql); 
			
			AppProductIncomeDetail asod=new AppProductIncomeDetail(); 
		     
	        List sodls = asod.getTotalReport(whereSql);
	        DBUserLog.addUserLog(userid, 10,"报表分析>>打印产成品入库汇总");
			request.setAttribute("list", sodls);
			return mapping.findForward("toprint");
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}

}
