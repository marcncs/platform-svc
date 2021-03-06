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
import com.winsafe.drp.dao.AppHarmShipmentBillDetail;
import com.winsafe.drp.dao.AppWarehouse;
import com.winsafe.drp.dao.DetailReportForm;
import com.winsafe.drp.dao.HarmShipmentBillDetail;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.HtmlSelect;

public class HarmShipmentBillDetailAction extends BaseAction{
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		initdata(request);
		try{
			int pagesize = 20;
			String visitorgan = "";
			if (users.getVisitorgan() != null
					&& users.getVisitorgan().length() > 0) {
				visitorgan = getAndVisitOrgan("makeorganid");
			}
			String Condition = " pw.id=pwd.hsid and pw.isendcase=1 and pw.isblankout=0 " + visitorgan+ " ";


			String[] tablename = { "HarmShipmentBill", "HarmShipmentBillDetail"  };
			String whereSql = getWhereSql(tablename);
			String timeCondition = getTimeCondition("MakeDate");
			whereSql = whereSql + timeCondition + Condition; 
			whereSql = DbUtil.getWhereSql(whereSql); 

			AppHarmShipmentBillDetail asod=new AppHarmShipmentBillDetail(); 
			double sumobj = asod.getTotalSubum(whereSql);		     
	        List sodls = asod.getDetailReport(request,pagesize, whereSql);
	        AppWarehouse aw = new AppWarehouse();
	        ArrayList alsod=new ArrayList();
	        Double totalsum=0.00;
	        for(int d = 0;d<sodls.size();d++){
	        	DetailReportForm sodf = new DetailReportForm();
	        	Object[] o = (Object[])sodls.get(d);
	        	String wid = (String)o[0];
	        	
	        	String makedate = DateUtil.formatDateTime((Date)o[1]);
	        	HarmShipmentBillDetail pbd = (HarmShipmentBillDetail)o[2];
				sodf.setMakedate(makedate);
	        	sodf.setOname(aw.getWarehouseByID(wid).getWarehousename());
	        	sodf.setBillid(pbd.getHsid());
	        	sodf.setProductid(pbd.getProductid());
	        	sodf.setProductname(pbd.getProductname());
	        	sodf.setSpecmode(pbd.getSpecmode());
	        	sodf.setUnitidname(HtmlSelect.getResourceName(request, "CountUnit", pbd.getUnitid()));
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
			DBUserLog.addUserLog(userid, 10,"报表分析>>出库>>列表报损明细");
	      return mapping.findForward("success");
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}

}
