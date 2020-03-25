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
import com.winsafe.hbm.util.Internation;

public class OtherIncomeDetailAction extends BaseAction{
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		initdata(request);
		try{
			int pagesize = 20;
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
			double sumobj = asod.getTotalSubum(whereSql);		     
	        List sodls = asod.getDetailReport(request,pagesize, whereSql);
	        WarehouseService aw = new WarehouseService();
	        OrganService organser = new OrganService();
	        ArrayList alsod=new ArrayList();
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
	        	sodf.setUnitidname(Internation.getStringByKeyPositionDB("CountUnit",		                  
	                    Integer.valueOf(pbd.getUnitid().toString())));
	        	sodf.setQuantity(pbd.getQuantity());
	        	totalsum += sodf.getQuantity();
	        	alsod.add(sodf);
	        }
		       
			request.setAttribute("totalsum", totalsum);
			request.setAttribute("alsod", alsod);
			  
			request.setAttribute("allsum", sumobj);
		      

			request.setAttribute("MakeOrganID", request.getParameter("MakeOrganID"));
			request.setAttribute("MakeID", request.getParameter("MakeID"));
			request.setAttribute("BeginDate", map.get("BeginDate"));
			request.setAttribute("EndDate", map.get("EndDate"));
			request.setAttribute("WarehouseID", request.getParameter("WarehouseID"));
			request.setAttribute("ProductName", request.getParameter("ProductName"));
			DBUserLog.addUserLog(userid, 10,"报表分析>>盘点>>列表盘盈明细");
	      return mapping.findForward("success");
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}

}
