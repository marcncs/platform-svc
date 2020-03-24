package com.winsafe.drp.action.report;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppOtherIncomeDetail;
import com.winsafe.drp.dao.DetailReportForm;
import com.winsafe.drp.dao.OtherIncome;
import com.winsafe.drp.dao.OtherIncomeDetail;
import com.winsafe.drp.server.OrganService;
import com.winsafe.drp.server.WarehouseService;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.HtmlSelect;

public class PrintOtherIncomeDetailAction extends BaseAction{
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		initdata(request);
		try{
			String visitorgan = "";
			if (users.getVisitorgan() != null
					&& users.getVisitorgan().length() > 0) {
				visitorgan = getAndVisitOrgan("pw.makeorganid") ;

			}
			String Condition = " pw.id=pwd.oiid and pw.isaudit=1  " + visitorgan+ " ";

			String[] tablename = { "OtherIncome", "OtherIncomeDetail"  };
			String whereSql = getWhereSql(tablename);
			String timeCondition = getTimeCondition("pw.MakeDate");
			whereSql = whereSql + timeCondition + Condition; 
			whereSql = DbUtil.getWhereSql(whereSql); 
		      
			
			
			AppOtherIncomeDetail asod=new AppOtherIncomeDetail(); 
	        List sodls = asod.getDetailReport(whereSql);
	        WarehouseService aw = new WarehouseService();
	        OrganService organser = new OrganService();
	        ArrayList list=new ArrayList();
	        Double totalsum=0.00;
	        for(int d = 0;d<sodls.size();d++){
	        	DetailReportForm sodf = new DetailReportForm();
	        	Object[] o = (Object[])sodls.get(d);
	        	OtherIncome oi = (OtherIncome)o[0];
	        	OtherIncomeDetail pbd = (OtherIncomeDetail)o[1];
	        	sodf.setSoname(organser.getOrganName(oi.getMakeorganid()));
	        	sodf.setMakedate(DateUtil.formatDateTime(oi.getMakedate()));
	        	sodf.setOname(aw.getWarehouseName(oi.getWarehouseid()));
	        	sodf.setBillid(pbd.getOiid());
	        	sodf.setProductid(pbd.getProductid());
	        	sodf.setProductname(pbd.getProductname());
	        	sodf.setSpecmode(pbd.getSpecmode());
	        	sodf.setUnitidname(HtmlSelect.getResourceName(request, "CountUnit", pbd.getUnitid()));
	        	sodf.setQuantity(pbd.getQuantity());
	        	totalsum +=sodf.getQuantity();
	        	list.add(sodf);
	        }
	        request.setAttribute("totalsum", totalsum);
	        request.setAttribute("list", list);
	        DBUserLog.addUserLog(userid, 10,"报表分析>>打印盘盈明细");
			return mapping.findForward("toprint");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	

}
